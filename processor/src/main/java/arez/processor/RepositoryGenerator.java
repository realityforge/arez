package arez.processor;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;

final class RepositoryGenerator
{
  private RepositoryGenerator()
  {
  }

  @Nonnull
  static TypeSpec buildRepository( @Nonnull final ProcessingEnvironment processingEnv,
                                   @Nonnull final RepositoryDescriptor repository )
    throws ProcessorException
  {
    final ComponentDescriptor component = repository.getComponent();
    final TypeElement element = component.getElement();

    final TypeSpec.Builder builder =
      TypeSpec.classBuilder( getRepositoryClassName( repository ).simpleName() ).
        addTypeVariables( GeneratorUtil.getTypeArgumentsAsNames( component.asDeclaredType() ) );
    Generator.copyWhitelistedAnnotations( component.getElement(), builder );
    GeneratorUtil.addOriginatingTypes( element, builder );

    Generator.addGeneratedAnnotation( processingEnv, builder );

    final String injectMode = repository.getInjectMode();
    final boolean addSingletonAnnotation =
      "CONSUME".equals( injectMode ) ||
      "PROVIDE".equals( injectMode ) ||
      ( "AUTODETECT".equals( injectMode ) &&
        null != processingEnv.getElementUtils().getTypeElement( Constants.INJECT_ANNOTATION_CLASSNAME ) );

    final AnnotationSpec.Builder arezComponent =
      AnnotationSpec.builder( ClassName.bestGuess( Constants.COMPONENT_ANNOTATION_CLASSNAME ) );
    if ( !"AUTODETECT".equals( injectMode ) )
    {
      arezComponent.addMember( "inject", "$T.$N", Generator.INJECT_MODE_CLASSNAME, injectMode );
    }
    final String daggerConfig = repository.getDaggerConfig();
    if ( !"AUTODETECT".equals( daggerConfig ) )
    {
      arezComponent.addMember( "dagger", "$T.$N", Generator.FEATURE_CLASSNAME, daggerConfig );
    }
    builder.addAnnotation( arezComponent.build() );
    if ( addSingletonAnnotation )
    {
      builder.addAnnotation( Generator.SINGLETON_CLASSNAME );
    }

    builder.superclass( ParameterizedTypeName.get( Generator.ABSTRACT_REPOSITORY_CLASSNAME,
                                                   component.getIdType().box(),
                                                   ClassName.get( element ),
                                                   getRepositoryClassName( repository ) ) );

    repository.getExtensions().forEach( e -> builder.addSuperinterface( TypeName.get( e.asType() ) ) );

    GeneratorUtil.copyAccessModifiers( element, builder );

    /*
     * If the repository will be generated as a PROVIDE inject mode when dagger is present
     * but the type is not public, we still need to generate a public repository due to
     * constraints imposed by dagger.
     */
    if ( addSingletonAnnotation &&
         !"CONSUME".equals( injectMode ) &&
         !element.getModifiers().contains( Modifier.PUBLIC ) )
    {
      builder.addModifiers( Modifier.PUBLIC );
    }

    builder.addModifiers( Modifier.ABSTRACT );

    //Add the default access, no-args constructor
    builder.addMethod( MethodSpec.constructorBuilder().build() );

    // Add the factory method
    builder.addMethod( buildFactoryMethod( repository ) );

    if ( repository.shouldRepositoryDefineCreate() )
    {
      for ( final ExecutableElement constructor : ProcessorUtil.getConstructors( element ) )
      {
        builder.addMethod( buildRepositoryCreate( processingEnv, repository, constructor ) );
      }
    }
    if ( repository.shouldRepositoryDefineAttach() )
    {
      builder.addMethod( buildRepositoryAttach( repository ) );
    }

    if ( null != component.getComponentId() )
    {
      builder.addMethod( buildFindByIdMethod( component ) );
      builder.addMethod( buildGetByIdMethod( repository ) );
    }

    if ( repository.shouldRepositoryDefineDestroy() )
    {
      builder.addMethod( buildRepositoryDestroy( repository ) );
    }
    if ( repository.shouldRepositoryDefineDetach() )
    {
      builder.addMethod( buildRepositoryDetach( repository ) );
    }
    return builder.build();
  }

  @Nonnull
  private static ClassName getRepositoryClassName( @Nonnull final RepositoryDescriptor repository )
  {
    return GeneratorUtil.getGeneratedClassName( repository.getComponent().getElement(), "", "Repository" );
  }

  @Nonnull
  private static MethodSpec buildRepositoryAttach( @Nonnull final RepositoryDescriptor repository )
  {
    final TypeElement element = repository.getComponent().getElement();
    return MethodSpec.methodBuilder( "attach" ).
      addAnnotation( Override.class ).
      addModifiers( element.getModifiers().contains( Modifier.PUBLIC ) ?
                    Modifier.PUBLIC :
                    Modifier.PROTECTED ).
      addAnnotation( AnnotationSpec.builder( Generator.ACTION_CLASSNAME )
                       .addMember( "reportParameters", "false" )
                       .build() ).
      addParameter( ParameterSpec.builder( TypeName.get( element.asType() ), "entity", Modifier.FINAL )
                      .addAnnotation( Generator.NONNULL_CLASSNAME )
                      .build() ).
      addStatement( "super.attach( entity )" ).build();
  }

  @Nonnull
  private static MethodSpec buildRepositoryDetach( @Nonnull final RepositoryDescriptor repository )
  {
    final TypeElement element = repository.getComponent().getElement();
    return MethodSpec.methodBuilder( "detach" ).
      addAnnotation( Override.class ).
      addModifiers( element.getModifiers().contains( Modifier.PUBLIC ) ?
                    Modifier.PUBLIC :
                    Modifier.PROTECTED ).
      addAnnotation( AnnotationSpec.builder( Generator.ACTION_CLASSNAME )
                       .addMember( "reportParameters", "false" )
                       .build() ).
      addParameter( ParameterSpec.builder( TypeName.get( element.asType() ), "entity", Modifier.FINAL )
                      .addAnnotation( Generator.NONNULL_CLASSNAME )
                      .build() ).
      addStatement( "super.detach( entity )" ).build();
  }

  @Nonnull
  private static MethodSpec buildRepositoryDestroy( @Nonnull final RepositoryDescriptor repository )
  {
    final TypeElement element = repository.getComponent().getElement();
    final TypeName entityType = TypeName.get( element.asType() );
    final MethodSpec.Builder method = MethodSpec.methodBuilder( "destroy" ).
      addAnnotation( Override.class ).
      addAnnotation( AnnotationSpec.builder( Generator.ACTION_CLASSNAME )
                       .addMember( "reportParameters", "false" )
                       .build() ).
      addParameter( ParameterSpec.builder( entityType, "entity", Modifier.FINAL )
                      .addAnnotation( Generator.NONNULL_CLASSNAME )
                      .build() ).
      addStatement( "super.destroy( entity )" );
    GeneratorUtil.copyAccessModifiers( element, method );
    final Set<Modifier> modifiers = element.getModifiers();
    if ( !modifiers.contains( Modifier.PUBLIC ) && !modifiers.contains( Modifier.PROTECTED ) )
    {
      /*
       * The destroy method inherited from AbstractContainer is protected and the override
       * must be at least the same access level.
       */
      method.addModifiers( Modifier.PROTECTED );
    }
    return method.build();
  }

  @Nonnull
  private static MethodSpec buildFindByIdMethod( @Nonnull final ComponentDescriptor component )
  {
    assert null != component.getComponentId();

    final MethodSpec.Builder method = MethodSpec.methodBuilder( "findBy" + getIdName( component ) ).
      addModifiers( Modifier.FINAL ).
      addParameter( ParameterSpec.builder( component.getIdType(), "id", Modifier.FINAL ).build() ).
      addAnnotation( Generator.NULLABLE_CLASSNAME ).
      returns( TypeName.get( component.getElement().asType() ) ).
      addStatement( "return findByArezId( id )" );
    GeneratorUtil.copyAccessModifiers( component.getElement(), method );
    return method.build();
  }

  @Nonnull
  private static MethodSpec buildGetByIdMethod( @Nonnull final RepositoryDescriptor repository )
  {
    final ComponentDescriptor component = repository.getComponent();
    final TypeElement element = component.getElement();
    final TypeName entityType = TypeName.get( element.asType() );
    final MethodSpec.Builder method = MethodSpec.methodBuilder( "getBy" + getIdName( component ) ).
      addModifiers( Modifier.FINAL ).
      addAnnotation( Generator.NONNULL_CLASSNAME ).
      addParameter( ParameterSpec.builder( component.getIdType(), "id", Modifier.FINAL ).build() ).
      returns( entityType ).
      addStatement( "return getByArezId( id )" );
    GeneratorUtil.copyAccessModifiers( element, method );
    return method.build();
  }

  @Nonnull
  private static MethodSpec buildRepositoryCreate( @Nonnull final ProcessingEnvironment processingEnv,
                                                   @Nonnull final RepositoryDescriptor repository,
                                                   @Nonnull final ExecutableElement constructor )
  {
    final ComponentDescriptor component = repository.getComponent();
    final ClassName enhancedClassName = component.getEnhancedClassName();
    final ExecutableType methodType =
      (ExecutableType) processingEnv.getTypeUtils()
        .asMemberOf( (DeclaredType) component.getElement().asType(), constructor );

    final String suffix = constructor.getParameters().stream().
      map( p -> p.getSimpleName().toString() ).collect( Collectors.joining( "_" ) );
    final String actionName = "create" + ( suffix.isEmpty() ? "" : "_" + suffix );
    final AnnotationSpec annotationSpec =
      AnnotationSpec.builder( ClassName.bestGuess( Constants.ACTION_ANNOTATION_CLASSNAME ) ).
        addMember( "name", "$S", actionName ).build();
    final MethodSpec.Builder builder =
      MethodSpec.methodBuilder( "create" ).
        addAnnotation( annotationSpec ).
        addAnnotation( Generator.NONNULL_CLASSNAME ).
        returns( TypeName.get( component.asDeclaredType() ) );

    GeneratorUtil.copyAccessModifiers( component.getElement(), builder );
    GeneratorUtil.copyExceptions( methodType, builder );
    GeneratorUtil.copyTypeParameters( methodType, builder );

    final StringBuilder newCall = new StringBuilder();
    newCall.append( "final $T entity = new $T(" );
    final ArrayList<Object> parameters = new ArrayList<>();
    parameters.add( enhancedClassName );
    parameters.add( enhancedClassName );

    boolean firstParam = true;
    for ( final VariableElement element : constructor.getParameters() )
    {
      final ParameterSpec.Builder param =
        ParameterSpec.builder( TypeName.get( element.asType() ), element.getSimpleName().toString(), Modifier.FINAL );
      Generator.copyWhitelistedAnnotations( element, param );
      builder.addParameter( param.build() );
      parameters.add( element.getSimpleName().toString() );
      if ( !firstParam )
      {
        newCall.append( "," );
      }
      firstParam = false;
      newCall.append( "$N" );
    }

    for ( final ObservableDescriptor observable : component.getInitializers() )
    {
      final String candidateName = observable.getName();
      final String name = ProcessorUtil.anyParametersNamed( constructor, candidateName ) ?
                          Generator.INITIALIZER_PREFIX + candidateName :
                          candidateName;
      final ParameterSpec.Builder param =
        ParameterSpec.builder( TypeName.get( observable.getGetterType().getReturnType() ),
                               name,
                               Modifier.FINAL );
      Generator.copyWhitelistedAnnotations( observable.getGetter(), param );
      builder.addParameter( param.build() );
      parameters.add( name );
      if ( !firstParam )
      {
        newCall.append( "," );
      }
      firstParam = false;
      newCall.append( "$N" );
    }

    newCall.append( ")" );
    builder.addStatement( newCall.toString(), parameters.toArray() );
    builder.addStatement( "attach( entity )" );
    builder.addStatement( "return entity" );
    return builder.build();
  }

  @Nonnull
  private static MethodSpec buildFactoryMethod( @Nonnull final RepositoryDescriptor repository )
  {
    final ComponentDescriptor component = repository.getComponent();
    final ClassName repositoryClassName = getRepositoryClassName( repository );
    final MethodSpec.Builder method = MethodSpec.methodBuilder( "newRepository" ).
      addModifiers( Modifier.STATIC ).
      addAnnotation( Generator.NONNULL_CLASSNAME ).
      returns( repositoryClassName ).
      addStatement( "return new $T()",
                    ClassName.get( component.getPackageName(), "Arez_" + repositoryClassName.simpleName() ) );
    GeneratorUtil.copyAccessModifiers( component.getElement(), method );
    return method.build();
  }

  @Nonnull
  private static String getIdName( @Nonnull final ComponentDescriptor component )
  {
    final ExecutableElement componentId = component.getComponentId();
    assert null != componentId;
    final String name = ProcessorUtil.deriveName( componentId, ArezProcessor.GETTER_PATTERN, Constants.SENTINEL );
    if ( null != name )
    {
      return Character.toUpperCase( name.charAt( 0 ) ) + ( name.length() > 1 ? name.substring( 1 ) : "" );
    }
    else
    {
      return "Id";
    }
  }
}

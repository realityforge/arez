package org.realityforge.arez.processor;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * The class that represents the parsed state of @Action methods on a @ArezComponent annotated class.
 */
@SuppressWarnings( "Duplicates" )
final class ActionDescriptor
{
  @Nonnull
  private final ComponentDescriptor _componentDescriptor;
  @Nonnull
  private final String _name;
  private final boolean _mutation;
  @Nonnull
  private final ExecutableElement _action;
  @Nonnull
  private final ExecutableType _actionType;

  ActionDescriptor( @Nonnull final ComponentDescriptor componentDescriptor,
                    @Nonnull final String name,
                    final boolean mutation,
                    @Nonnull final ExecutableElement action,
                    @Nonnull final ExecutableType actionType )
  {
    _componentDescriptor = Objects.requireNonNull( componentDescriptor );
    _name = Objects.requireNonNull( name );
    _mutation = mutation;
    _action = Objects.requireNonNull( action );
    _actionType = Objects.requireNonNull( actionType );
  }

  @Nonnull
  String getName()
  {
    return _name;
  }

  @Nonnull
  ExecutableElement getAction()
  {
    return _action;
  }

  void buildMethods( @Nonnull final TypeSpec.Builder builder )
    throws ArezProcessorException
  {
    builder.addMethod( buildAction() );
  }

  /**
   * Generate the action wrapper.
   */
  @Nonnull
  private MethodSpec buildAction()
    throws ArezProcessorException
  {
    final MethodSpec.Builder builder = MethodSpec.methodBuilder( _action.getSimpleName().toString() );
    ProcessorUtil.copyAccessModifiers( _action, builder );
    ProcessorUtil.copyExceptions( _actionType, builder );
    ProcessorUtil.copyTypeParameters( _actionType, builder );
    ProcessorUtil.copyDocumentedAnnotations( _action, builder );
    builder.addAnnotation( Override.class );
    final TypeMirror returnType = _actionType.getReturnType();
    builder.returns( TypeName.get( returnType ) );

    final boolean isProcedure = returnType.getKind() == TypeKind.VOID;
    final boolean isSafe = _action.getThrownTypes().isEmpty();

    final StringBuilder statement = new StringBuilder();
    final ArrayList<Object> parameterNames = new ArrayList<>();

    if ( !isProcedure )
    {
      statement.append( "final $T $N = " );
      parameterNames.add( TypeName.get( returnType ) );
      parameterNames.add( GeneratorUtil.RESULT_VARIABLE_NAME );
    }
    statement.append( "this.$N." );
    parameterNames.add( GeneratorUtil.CONTEXT_FIELD_NAME );

    if ( isProcedure && isSafe )
    {
      statement.append( "safeProcedure" );
    }
    else if ( isProcedure )
    {
      statement.append( "procedure" );
    }
    else if ( isSafe )
    {
      statement.append( "safeFunction" );
    }
    else
    {
      statement.append( "function" );
    }

    statement.append( "(" );

    if ( _componentDescriptor.isSingleton() )
    {
      statement.append( "this.$N.areNamesEnabled() ? $S : null" );
      parameterNames.add( GeneratorUtil.CONTEXT_FIELD_NAME );
      parameterNames.add( _componentDescriptor.getNamePrefix() + getName() );
    }
    else
    {
      statement.append( "this.$N.areNamesEnabled() ? $N() + $S : null" );
      parameterNames.add( GeneratorUtil.CONTEXT_FIELD_NAME );
      parameterNames.add( _componentDescriptor.getComponentNameMethodName() );
      parameterNames.add( "." + getName() );
    }

    statement.append( ", " );
    statement.append( _mutation );
    statement.append( ", () -> super." );
    statement.append( _action.getSimpleName() );
    statement.append( "(" );

    boolean firstParam = true;
    final List<? extends VariableElement> parameters = _action.getParameters();
    final int paramCount = parameters.size();
    for ( int i = 0; i < paramCount; i++ )
    {
      final VariableElement element = parameters.get( i );
      final TypeName parameterType = TypeName.get( _actionType.getParameterTypes().get( i ) );
      final ParameterSpec.Builder param =
        ParameterSpec.builder( parameterType, element.getSimpleName().toString(), Modifier.FINAL );
      ProcessorUtil.copyDocumentedAnnotations( element, param );
      builder.addParameter( param.build() );
      parameterNames.add( element.getSimpleName().toString() );
      if ( !firstParam )
      {
        statement.append( "," );
      }
      firstParam = false;
      statement.append( "$N" );
    }

    statement.append( ") )" );

    if ( _componentDescriptor.isDisposable() )
    {
      builder.addStatement( "assert !$N", GeneratorUtil.DISPOSED_FIELD_NAME );
    }

    builder.addStatement( "$T $N = null", Throwable.class, GeneratorUtil.THROWABLE_VARIABLE_NAME );
    builder.addStatement( "$T $N = false", boolean.class, GeneratorUtil.COMPLETED_VARIABLE_NAME );
    builder.addStatement( "$T $N = 0L", long.class, GeneratorUtil.STARTED_AT_VARIABLE_NAME );

    final CodeBlock.Builder codeBlock = CodeBlock.builder();
    codeBlock.beginControlFlow( "try" );

    GeneratorUtil.actionStartedSpyEvent( _componentDescriptor, _name, false, _action, codeBlock );
    codeBlock.addStatement( statement.toString(), parameterNames.toArray() );
    codeBlock.addStatement( "$N = true", GeneratorUtil.COMPLETED_VARIABLE_NAME );
    GeneratorUtil.actionCompletedSpyEvent( _componentDescriptor, _name, false, _action, isProcedure, codeBlock );
    if ( !isProcedure )
    {
      codeBlock.addStatement( "return $N", GeneratorUtil.RESULT_VARIABLE_NAME );
    }

    for ( final TypeMirror exception : _action.getThrownTypes() )
    {
      codeBlock.nextControlFlow( "catch( final $T e )", exception );
      codeBlock.addStatement( "throw e" );
    }

    if ( _action.getThrownTypes().stream().noneMatch( t -> t.toString().equals( "java.lang.Throwable" ) ) )
    {
      if ( _action.getThrownTypes().stream().noneMatch( t -> t.toString().equals( "java.lang.Exception" ) ) )
      {
        if ( _action.getThrownTypes().stream().noneMatch( t -> t.toString().equals( "java.lang.RuntimeException" ) ) )
        {
          codeBlock.nextControlFlow( "catch( final $T e )", RuntimeException.class );
          codeBlock.addStatement( "$N = e", GeneratorUtil.THROWABLE_VARIABLE_NAME );
          codeBlock.addStatement( "throw e" );
        }
        codeBlock.nextControlFlow( "catch( final $T e )", Exception.class );
        codeBlock.addStatement( "$N = e", GeneratorUtil.THROWABLE_VARIABLE_NAME );
        codeBlock.addStatement( "throw new $T( e )", IllegalStateException.class );
      }
      codeBlock.nextControlFlow( "catch( final $T e )", Error.class );
      codeBlock.addStatement( "$N = e", GeneratorUtil.THROWABLE_VARIABLE_NAME );
      codeBlock.addStatement( "throw e" );
      codeBlock.nextControlFlow( "catch( final $T e )", Throwable.class );
      codeBlock.addStatement( "$N = e", GeneratorUtil.THROWABLE_VARIABLE_NAME );
      codeBlock.addStatement( "throw new $T( e )", IllegalStateException.class );
    }
    codeBlock.nextControlFlow( "finally" );

    // Send completed spy event if necessary
    codeBlock.beginControlFlow( "if ( !$N )", GeneratorUtil.COMPLETED_VARIABLE_NAME );
    if ( !isProcedure )
    {
      codeBlock.addStatement( "final $T $N = null",
                              TypeName.get( returnType ).box(),
                              GeneratorUtil.RESULT_VARIABLE_NAME );
    }
    GeneratorUtil.actionCompletedSpyEvent( _componentDescriptor, _name, false, _action, isProcedure, codeBlock );
    codeBlock.endControlFlow();

    codeBlock.endControlFlow();
    builder.addCode( codeBlock.build() );

    return builder.build();
  }
}

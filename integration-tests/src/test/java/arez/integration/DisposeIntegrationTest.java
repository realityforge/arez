package arez.integration;

import arez.Arez;
import arez.ArezContext;
import arez.Disposable;
import arez.Observer;
import arez.annotations.ArezComponent;
import arez.annotations.Memoize;
import arez.annotations.Observable;
import arez.integration.util.SpyEventRecorder;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.testng.annotations.Test;

public final class DisposeIntegrationTest
  extends AbstractArezIntegrationTest
{
  /**
   * Another example that incorporates actions, computable and disposes.
   */
  @Test
  public void codeModelScenario()
    throws Throwable
  {
    final ArezContext context = Arez.context();

    final SpyEventRecorder recorder = SpyEventRecorder.beginRecording( context );

    final CodeModel codeModel = CodeModel.create( "com.example", "MyType" );

    final Observer observer =
      context.observer( "Printer",
                        () -> {
                          observeADependency();
                          recorder.mark( "qualifiedName", codeModel.getQualifiedName() );
                        } );

    context.action( "Specific Qualified Name", () -> codeModel.setQualifiedName( "com.biz.Fred" ) );
    context.action( "Reset Qualified Name to default", () -> codeModel.setQualifiedName( null ) );
    context.action( "Change Local Name", () -> codeModel.setName( "MyType2" ) );

    observer.dispose();
    Disposable.dispose( codeModel );

    assertMatchesFixture( recorder );
  }

  @SuppressWarnings( "WeakerAccess" )
  @ArezComponent
   static abstract class CodeModel
  {
    @Nonnull
    private String _name;
    @Nonnull
    private String _packageName;
    @Nullable
    private String _qualifiedName;

    @Nonnull
    static CodeModel create( @Nonnull final String packageName, @Nonnull final String name )
    {
      return new DisposeIntegrationTest_Arez_CodeModel( packageName, name );
    }

    CodeModel( @Nonnull final String packageName, @Nonnull final String name )
    {
      _packageName = packageName;
      _name = name;
    }

    @Observable
    @Nonnull
    String getName()
    {
      return _name;
    }

    void setName( @Nonnull final String name )
    {
      _name = name;
    }

    @Observable
    @Nonnull
    String getPackageName()
    {
      return _packageName;
    }

    void setPackageName( @Nonnull final String packageName )
    {
      _packageName = packageName;
    }

    @Memoize
    @Nonnull
    String getQualifiedName()
    {
      final String rawQualifiedName = getRawQualifiedName();
      if ( null == rawQualifiedName )
      {
        return getPackageName() + "." + getName();
      }
      else
      {
        return rawQualifiedName;
      }
    }

    @Nullable
    @Observable
    String getRawQualifiedName()
    {
      return _qualifiedName;
    }

    @Observable( name = "rawQualifiedName" )
    void setQualifiedName( @Nullable final String qualifiedName )
    {
      _qualifiedName = qualifiedName;
    }
  }
}

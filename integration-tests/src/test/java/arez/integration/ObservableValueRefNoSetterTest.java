package arez.integration;

import arez.Arez;
import arez.ArezContext;
import arez.ObservableValue;
import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Observable;
import arez.annotations.ObservableValueRef;
import arez.integration.util.SpyEventRecorder;
import java.util.concurrent.atomic.AtomicInteger;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class ObservableValueRefNoSetterTest
  extends AbstractArezIntegrationTest
{
  @ArezComponent
  static abstract class TestComponent
  {
    private int _otherID;
    private String _other;

    @ObservableValueRef
    abstract ObservableValue<Integer> getOtherIDObservableValue();

    String getOther()
    {
      getOtherIDObservableValue().reportObserved();
      if ( null == _other )
      {
        // Imagine that this looks up the other in a repository
        // and other is actually another ArezComponent. This is
        // the example used in replicant and HL libraries. The
        // network layer provides the ID and it is resovled locally
        _other = String.valueOf( _otherID );
      }
      return _other;
    }

    @Observable( expectSetter = false )
    int getOtherID()
    {
      return _otherID;
    }

    @Action
    void setOtherID( final int otherID )
    {
      _other = null;
      _otherID = otherID;
      getOtherIDObservableValue().reportChanged();
    }
  }

  @Test
  public void observableRef()
    throws Throwable
  {
    final ArezContext context = Arez.context();

    final TestComponent component = new ObservableValueRefNoSetterTest_Arez_TestComponent();
    component.setOtherID( 1 );

    final SpyEventRecorder recorder = SpyEventRecorder.beginRecording();

    final AtomicInteger ttCount = new AtomicInteger();
    final AtomicInteger rtCount = new AtomicInteger();

    context.observer( "TransportType",
                      () -> {
                        observeADependency();
                        recorder.mark( "TransportType", component.getOtherID() );
                        ttCount.incrementAndGet();
                      } );
    // This is verifying that the explicit reportObserved occurs
    context.observer( "ResolvedType",
                      () -> {
                        observeADependency();
                        recorder.mark( "ResolvedType", component.getOther() );
                        rtCount.incrementAndGet();
                      } );

    assertEquals( ttCount.get(), 1 );
    assertEquals( rtCount.get(), 1 );

    // This is verifying that the explicit reportChanged occurs
    component.setOtherID( 22 );

    assertMatchesFixture( recorder );

    assertEquals( ttCount.get(), 2 );
    assertEquals( rtCount.get(), 2 );
  }
}

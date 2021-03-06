package arez.integration.priority;

import arez.Arez;
import arez.ArezContext;
import arez.ObservableValue;
import arez.Observer;
import arez.Procedure;
import arez.integration.AbstractArezIntegrationTest;
import arez.integration.util.SpyEventRecorder;
import java.util.ArrayList;
import java.util.Arrays;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class PriorityOrderingIntegrationTest
  extends AbstractArezIntegrationTest
{
  @Test
  public void scenario()
    throws Throwable
  {
    final ArezContext context = Arez.context();

    final ObservableValue<?> observableValue = context.observable();
    final SpyEventRecorder recorder = SpyEventRecorder.beginRecording();

    final ArrayList<String> runOrder = new ArrayList<>();

    final Procedure action1 = () -> {
      observableValue.reportObserved();
      runOrder.add( "AR1" );
    };
    final Procedure action2 = () -> {
      observableValue.reportObserved();
      runOrder.add( "AR2" );
    };
    final Procedure action3 = () -> {
      observableValue.reportObserved();
      runOrder.add( "AR3" );
    };
    final Procedure action4 = () -> {
      observableValue.reportObserved();
      runOrder.add( "AR4" );
    };
    final Procedure action5 = () -> {
      observableValue.reportObserved();
      runOrder.add( "AR5" );
    };
    final Procedure action6 = () -> {
      observableValue.reportObserved();
      runOrder.add( "AR6" );
    };
    final Procedure action7 = () -> {
      observableValue.reportObserved();
      runOrder.add( "AR7" );
    };
    context.observer( "AR1", action1, Observer.Flags.PRIORITY_LOWEST | Observer.Flags.RUN_LATER );
    context.observer( "AR2", action2, Observer.Flags.PRIORITY_HIGH | Observer.Flags.RUN_LATER );
    context.observer( "AR3", action3, Observer.Flags.PRIORITY_LOW | Observer.Flags.RUN_LATER );
    context.observer( "AR4", action4, Observer.Flags.PRIORITY_NORMAL | Observer.Flags.RUN_LATER );
    context.observer( "AR5", action5, Observer.Flags.PRIORITY_NORMAL | Observer.Flags.RUN_LATER );
    context.observer( "AR6", action6, Observer.Flags.PRIORITY_HIGHEST | Observer.Flags.RUN_LATER );
    context.observer( "AR7", action7, Observer.Flags.PRIORITY_HIGH | Observer.Flags.RUN_LATER );

    assertEquals( runOrder.size(), 0 );

    safeAction( observableValue::reportChanged );

    assertEquals( runOrder.size(), 7 );
    assertEquals( runOrder, Arrays.asList( "AR6", "AR2", "AR7", "AR4", "AR5", "AR3", "AR1" ) );

    assertMatchesFixture( recorder );
  }
}

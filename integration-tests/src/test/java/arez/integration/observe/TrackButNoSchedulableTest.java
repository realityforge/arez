package arez.integration.observe;

import arez.Arez;
import arez.ArezContext;
import arez.Task;
import arez.annotations.ArezComponent;
import arez.annotations.Executor;
import arez.annotations.Observe;
import arez.annotations.OnDepsChange;
import arez.integration.AbstractArezIntegrationTest;
import java.util.concurrent.atomic.AtomicInteger;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class TrackButNoSchedulableTest
  extends AbstractArezIntegrationTest
{
  @ArezComponent
  static abstract class Model
  {
    @Observe( executor = Executor.EXTERNAL )
    int render()
    {
      return 23;
    }

    @OnDepsChange
    void onRenderDepsChange()
    {
    }
  }

  @Test
  public void scenario()
  {
    final ArezContext context = Arez.context();

    final AtomicInteger callCount = new AtomicInteger();
    context.task( callCount::incrementAndGet, Task.Flags.RUN_LATER );

    assertEquals( callCount.get(), 0 );

    // This has no schedulable elements and thus should not trigger the scheduler and thus task will not run
    new TrackButNoSchedulableTest_Arez_Model();

    assertEquals( callCount.get(), 0 );

    context.triggerScheduler();

    assertEquals( callCount.get(), 1 );
  }
}

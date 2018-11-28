package arez;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class RoundBasedTaskExecutorTest
  extends AbstractArezTest
{
  @Test
  public void construct()
  {
    final FifoTaskQueue taskQueue = new FifoTaskQueue( 10 );
    final RoundBasedTaskExecutor executor = new RoundBasedTaskExecutor( taskQueue, 2 );

    assertEquals( executor.getMaxRounds(), 2 );
    assertEquals( executor.getCurrentRound(), 0 );
    assertEquals( executor.getRemainingTasksInCurrentRound(), 0 );
    assertFalse( executor.areTasksExecuting() );
  }

  @Test
  public void runNextTask()
  {
    final FifoTaskQueue taskQueue = new FifoTaskQueue( 10 );
    final RoundBasedTaskExecutor executor = new RoundBasedTaskExecutor( taskQueue, 2 );

    final AtomicInteger task1CallCount = new AtomicInteger();
    final AtomicInteger task2CallCount = new AtomicInteger();
    final AtomicInteger task3CallCount = new AtomicInteger();
    final ArezContext context = Arez.context();
    final Task task1 = new Task( context, "A", task1CallCount::incrementAndGet );
    final Task task2 = new Task( context, "B", task2CallCount::incrementAndGet );
    final Task task3 = new Task( context, "C", task3CallCount::incrementAndGet );
    taskQueue.queueTask( task1 );
    taskQueue.queueTask( task2 );
    taskQueue.queueTask( task3 );

    assertEquals( executor.getMaxRounds(), 2 );

    assertFalse( executor.areTasksExecuting() );
    assertEquals( executor.getCurrentRound(), 0 );
    assertEquals( executor.getRemainingTasksInCurrentRound(), 0 );
    assertEquals( taskQueue.getQueueSize(), 3 );

    // task executions
    assertEquals( task1CallCount.get(), 0 );
    assertEquals( task2CallCount.get(), 0 );
    assertEquals( task3CallCount.get(), 0 );

    assertTrue( executor.runNextTask() );

    assertTrue( executor.areTasksExecuting() );
    assertEquals( executor.getCurrentRound(), 1 );
    assertEquals( executor.getRemainingTasksInCurrentRound(), 2 );
    assertEquals( taskQueue.getQueueSize(), 2 );

    // task executions
    assertEquals( task1CallCount.get(), 1 );
    assertEquals( task2CallCount.get(), 0 );
    assertEquals( task3CallCount.get(), 0 );

    assertTrue( executor.runNextTask() );

    assertTrue( executor.areTasksExecuting() );
    assertEquals( executor.getCurrentRound(), 1 );
    assertEquals( executor.getRemainingTasksInCurrentRound(), 1 );
    assertEquals( taskQueue.getQueueSize(), 1 );

    // task executions
    assertEquals( task1CallCount.get(), 1 );
    assertEquals( task2CallCount.get(), 1 );
    assertEquals( task3CallCount.get(), 0 );

    // Now we schedule some tasks again to push execution into round 2
    taskQueue.queueTask( task1 );
    taskQueue.queueTask( task2 );

    assertEquals( taskQueue.getQueueSize(), 3 );

    assertTrue( executor.runNextTask() );

    assertTrue( executor.areTasksExecuting() );
    assertEquals( executor.getCurrentRound(), 1 );
    assertEquals( executor.getRemainingTasksInCurrentRound(), 0 );
    assertEquals( taskQueue.getQueueSize(), 2 );

    // task executions
    assertEquals( task1CallCount.get(), 1 );
    assertEquals( task2CallCount.get(), 1 );
    assertEquals( task3CallCount.get(), 1 );

    assertTrue( executor.runNextTask() );

    assertTrue( executor.areTasksExecuting() );
    assertEquals( executor.getCurrentRound(), 2 );
    assertEquals( executor.getRemainingTasksInCurrentRound(), 1 );
    assertEquals( taskQueue.getQueueSize(), 1 );

    // task executions
    assertEquals( task1CallCount.get(), 2 );
    assertEquals( task2CallCount.get(), 1 );
    assertEquals( task3CallCount.get(), 1 );

    assertTrue( executor.runNextTask() );

    assertTrue( executor.areTasksExecuting() );
    assertEquals( executor.getCurrentRound(), 2 );
    assertEquals( executor.getRemainingTasksInCurrentRound(), 0 );
    assertEquals( taskQueue.getQueueSize(), 0 );

    // task executions
    assertEquals( task1CallCount.get(), 2 );
    assertEquals( task2CallCount.get(), 2 );
    assertEquals( task3CallCount.get(), 1 );

    assertFalse( executor.runNextTask() );

    assertFalse( executor.areTasksExecuting() );
  }

  @Test
  public void runTasks()
  {
    final FifoTaskQueue taskQueue = new FifoTaskQueue( 10 );
    final RoundBasedTaskExecutor executor = new RoundBasedTaskExecutor( taskQueue, 2 );

    final AtomicInteger task1CallCount = new AtomicInteger();
    final AtomicInteger task2CallCount = new AtomicInteger();
    final AtomicInteger task3CallCount = new AtomicInteger();
    final ArezContext context = Arez.context();
    final Task task1 = new Task( context, "A", task1CallCount::incrementAndGet );
    final Task task2 = new Task( context, "B", task2CallCount::incrementAndGet );
    final Task task3 = new Task( context, "C", task3CallCount::incrementAndGet );
    taskQueue.queueTask( task1 );
    taskQueue.queueTask( task2 );
    taskQueue.queueTask( task3 );

    assertEquals( executor.getMaxRounds(), 2 );
    assertEquals( executor.getCurrentRound(), 0 );
    assertEquals( executor.getRemainingTasksInCurrentRound(), 0 );
    assertEquals( taskQueue.getQueueSize(), 3 );

    // task executions
    assertEquals( task1CallCount.get(), 0 );
    assertEquals( task2CallCount.get(), 0 );
    assertEquals( task3CallCount.get(), 0 );

    assertFalse( executor.areTasksExecuting() );

    executor.runTasks();

    assertFalse( executor.areTasksExecuting() );

    // task executions
    assertEquals( task1CallCount.get(), 1 );
    assertEquals( task2CallCount.get(), 1 );
    assertEquals( task3CallCount.get(), 1 );

    assertEquals( executor.getCurrentRound(), 0 );
    assertEquals( executor.getRemainingTasksInCurrentRound(), 0 );
    assertEquals( taskQueue.getQueueSize(), 0 );

    assertFalse( executor.runNextTask() );
  }

  @Test
  public void runTasks_invoking_onRunawayReactionsDetected()
  {
    ArezTestUtil.purgeTasksWhenRunawayDetected();

    final FifoTaskQueue taskQueue = new FifoTaskQueue( 10 );
    final RoundBasedTaskExecutor executor = new RoundBasedTaskExecutor( taskQueue, 2 );

    final AtomicInteger task1CallCount = new AtomicInteger();
    final AtomicReference<Task> taskRef = new AtomicReference<>();
    final Task task1 = new Task( Arez.context(), "A", () -> {
      task1CallCount.incrementAndGet();
      final Task task = taskRef.get();
      taskQueue.queueTask( task );
    } );
    taskRef.set( task1 );
    taskQueue.queueTask( task1 );

    assertEquals( executor.getMaxRounds(), 2 );
    assertEquals( executor.getCurrentRound(), 0 );
    assertEquals( executor.getRemainingTasksInCurrentRound(), 0 );
    assertEquals( taskQueue.getQueueSize(), 1 );

    assertInvariantFailure( executor::runTasks,
                            "Arez-0101: Runaway task(s) detected. Tasks still running after 2 rounds. Current tasks include: [A]" );

    // Ensure tasks purged
    assertEquals( taskQueue.getQueueSize(), 0 );
    assertFalse( task1.isQueued() );

    assertEquals( task1CallCount.get(), 2 );
  }

  @Test
  public void runTasks_invoking_onRunawayReactionsDetected_noInvariantsEnabled()
  {
    ArezTestUtil.purgeTasksWhenRunawayDetected();
    ArezTestUtil.noCheckInvariants();

    final FifoTaskQueue taskQueue = new FifoTaskQueue( 10 );
    final RoundBasedTaskExecutor executor = new RoundBasedTaskExecutor( taskQueue, 2 );

    final AtomicInteger task1CallCount = new AtomicInteger();
    final AtomicReference<Task> taskRef = new AtomicReference<>();
    final Task task1 = new Task( Arez.context(), "A", () -> {
      task1CallCount.incrementAndGet();
      final Task task = taskRef.get();
      taskQueue.queueTask( task );
    } );
    taskRef.set( task1 );
    taskQueue.queueTask( task1 );

    assertEquals( executor.getMaxRounds(), 2 );
    assertEquals( executor.getCurrentRound(), 0 );
    assertEquals( executor.getRemainingTasksInCurrentRound(), 0 );
    assertEquals( taskQueue.getQueueSize(), 1 );

    executor.runTasks();

    // Ensure tasks purged
    assertEquals( taskQueue.getQueueSize(), 0 );
    assertFalse( task1.isQueued() );

    assertEquals( task1CallCount.get(), 2 );
  }

  @Test
  public void onRunawayReactionsDetected()
  {
    ArezTestUtil.purgeTasksWhenRunawayDetected();

    final FifoTaskQueue taskQueue = new FifoTaskQueue( 10 );
    final RoundBasedTaskExecutor executor = new RoundBasedTaskExecutor( taskQueue, 2 );

    final Task task1 = new Task( Arez.context(), "A", ValueUtil::randomString );
    taskQueue.queueTask( task1 );

    assertInvariantFailure( executor::onRunawayTasksDetected,
                            "Arez-0101: Runaway task(s) detected. Tasks still running after 2 rounds. Current tasks include: [A]" );

    // Ensure tasks purged
    assertEquals( taskQueue.getQueueSize(), 0 );
    assertFalse( task1.isQueued() );
  }

  @Test
  public void onRunawayReactionsDetected_noPurgeConfigured()
  {
    ArezTestUtil.noPurgeTasksWhenRunawayDetected();

    final FifoTaskQueue taskQueue = new FifoTaskQueue( 10 );
    final RoundBasedTaskExecutor executor = new RoundBasedTaskExecutor( taskQueue, 2 );

    final Task task1 = new Task( Arez.context(), "A", ValueUtil::randomString );
    taskQueue.queueTask( task1 );

    assertInvariantFailure( executor::onRunawayTasksDetected,
                            "Arez-0101: Runaway task(s) detected. Tasks still running after 2 rounds. Current tasks include: [A]" );

    // Ensure tasks not purged
    assertEquals( taskQueue.getQueueSize(), 1 );
    assertTrue( task1.isQueued() );
  }
}

package arez;

import arez.spy.ComponentInfo;
import arez.spy.ElementInfo;
import arez.spy.ObservableValueInfo;
import arez.spy.ObserverInfo;
import arez.spy.Priority;
import arez.spy.Spy;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class ObserverInfoImplTest
  extends AbstractTest
{
  @Test
  public void basicOperation()
  {
    final ArezContext context = Arez.context();
    final String name = ValueUtil.randomString();
    final ObservableValue<Object> observableValue = context.observable();
    final Observer observer = context.observer( name, observableValue::reportObserved );

    final ObserverInfo info = observer.asInfo();

    assertNull( info.getComponent() );
    assertEquals( info.getName(), name );
    assertEquals( info.toString(), name );

    assertEquals( info.getDependencies().size(), 1 );
    assertEquals( info.getDependencies().get( 0 ).getName(), observableValue.getName() );
    assertUnmodifiable( info.getDependencies() );

    assertTrue( info.isActive() );
    assertFalse( info.isComputableValue() );
    assertTrue( info.isReadOnly() );
    assertEquals( info.getPriority(), Priority.NORMAL );
    assertFalse( info.isRunning() );
    assertFalse( info.isScheduled() );
    assertFalse( info.isDisposed() );

    observer.dispose();

    assertTrue( info.isDisposed() );
    assertFalse( info.isActive() );
  }

  @Test
  public void isScheduled()
  {
    final ArezContext context = Arez.context();

    final Spy spy = context.getSpy();

    final Observer observer = context.observer( new CountAndObserveProcedure() );

    final ObserverInfo info = spy.asObserverInfo( observer );

    assertFalse( info.isScheduled() );

    observer.getTask().markAsQueued();

    assertTrue( info.isScheduled() );
  }

  @Test
  public void isRunning()
  {
    final ArezContext context = Arez.context();

    final AtomicInteger callCount = new AtomicInteger();
    final AtomicReference<ObserverInfo> ref = new AtomicReference<>();

    final Observer observer = context.observer( () -> {
      assertTrue( ref.get().isRunning() );
      callCount.incrementAndGet();
      observeADependency();
    }, Observer.Flags.RUN_LATER );
    final ObserverInfo info = context.getSpy().asObserverInfo( observer );
    ref.set( info );

    assertFalse( info.isRunning() );
    assertEquals( callCount.get(), 0 );

    context.triggerScheduler();

    assertEquals( callCount.get(), 1 );
  }

  @Test
  public void isReadOnly_on_READ_WRITE_observer()
  {
    final ArezContext context = Arez.context();
    final ObservableValue<Object> observableValue = context.observable();
    final Observer observer = context.observer( observableValue::reportObserved, Observer.Flags.READ_WRITE );

    assertFalse( observer.asInfo().isReadOnly() );
  }

  @Test
  public void getDependencies()
  {
    final ArezContext context = Arez.context();

    final Spy spy = context.getSpy();

    final ObservableValue<Object> observable = context.observable();
    final Observer observer = context.observer( observable::reportObserved );

    final List<ObservableValueInfo> dependencies = spy.asObserverInfo( observer ).getDependencies();
    assertEquals( dependencies.size(), 1 );
    assertEquals( dependencies.get( 0 ).getName(), observable.getName() );

    assertUnmodifiable( dependencies );
  }

  @Test
  public void Ovserver_getDependenciesWhileRunning()
  {
    final ArezContext context = Arez.context();

    final Spy spy = context.getSpy();

    final Observer observer = context.observer( new CountAndObserveProcedure() );

    final ObservableValue<?> observableValue = context.observable();
    final ObservableValue<?> observableValue2 = context.observable();
    final ObservableValue<?> observableValue3 = context.observable();

    observableValue.getObservers().add( observer );
    observer.getDependencies().add( observableValue );

    setCurrentTransaction( observer );

    assertEquals( spy.asObserverInfo( observer ).getDependencies().size(), 0 );

    context.getTransaction().safeGetObservables().add( observableValue2 );
    context.getTransaction().safeGetObservables().add( observableValue3 );
    context.getTransaction().safeGetObservables().add( observableValue2 );

    final List<String> dependencies = spy.asObserverInfo( observer ).getDependencies().stream().
      map( ElementInfo::getName ).collect( Collectors.toList() );
    assertEquals( dependencies.size(), 2 );
    assertTrue( dependencies.contains( observableValue2.getName() ) );
    assertTrue( dependencies.contains( observableValue3.getName() ) );

    assertUnmodifiable( spy.asObserverInfo( observer ).getDependencies() );
  }

  @Test
  public void asComputableValue()
  {
    final ArezContext context = Arez.context();
    final String name = ValueUtil.randomString();
    final ComputableValue<String> computableValue = context.computable( name, () -> "" );

    final Observer observer = computableValue.getObserver();

    final ObserverInfo info = observer.asInfo();

    assertEquals( info.getName(), name );

    assertTrue( info.isComputableValue() );
    assertEquals( info.asComputableValue().getName(), computableValue.getName() );

    // Not yet observed
    assertFalse( info.isActive() );
  }

  @Test
  public void getComponent_Observer()
  {
    final ArezContext context = Arez.context();
    final Spy spy = context.getSpy();

    final Component component =
      context.component( ValueUtil.randomString(), ValueUtil.randomString(), ValueUtil.randomString() );
    final Observer observer1 =
      context.observer( component, null, AbstractTest::observeADependency );
    final Observer observer2 = context.observer( AbstractTest::observeADependency );

    final ComponentInfo info = spy.asObserverInfo( observer1 ).getComponent();
    assertNotNull( info );
    assertEquals( info.getName(), component.getName() );
    assertNull( spy.asObserverInfo( observer2 ).getComponent() );
  }

  @Test
  public void getComponent_Observer_nativeComponentsDisabled()
  {
    ArezTestUtil.disableNativeComponents();

    final ArezContext context = Arez.context();
    final Spy spy = context.getSpy();

    final Observer observer = context.observer( AbstractTest::observeADependency );

    assertInvariantFailure( () -> spy.asObserverInfo( observer ).getComponent(),
                            "Arez-0108: Spy.getComponent invoked when Arez.areNativeComponentsEnabled() returns false." );
  }

  @SuppressWarnings( "EqualsWithItself" )
  @Test
  public void equalsAndHashCode()
  {
    final ArezContext context = Arez.context();
    final ObservableValue<Object> observableValue = context.observable();
    final Observer observer1 = context.observer( ValueUtil.randomString(), observableValue::reportObserved );
    final Observer observer2 = context.observer( ValueUtil.randomString(), observableValue::reportObserved );

    final ObserverInfo info1a = observer1.asInfo();
    final ObserverInfo info1b = new ObserverInfoImpl( context.getSpy(), observer1 );
    final ObserverInfo info2 = observer2.asInfo();

    //noinspection EqualsBetweenInconvertibleTypes
    assertNotEquals( info1a, "" );

    assertEquals( info1a, info1a );
    assertEquals( info1b, info1a );
    assertNotEquals( info2, info1a );

    assertEquals( info1a, info1b );
    assertEquals( info1b, info1b );
    assertNotEquals( info2, info1b );

    assertNotEquals( info1a, info2 );
    assertNotEquals( info1b, info2 );
    assertEquals( info2, info2 );

    assertEquals( info1a.hashCode(), observer1.hashCode() );
    assertEquals( info1a.hashCode(), info1b.hashCode() );
    assertEquals( info2.hashCode(), observer2.hashCode() );
  }

  private <T> void assertUnmodifiable( @Nonnull final Collection<T> collection )
  {
    assertThrows( UnsupportedOperationException.class, () -> collection.remove( collection.iterator().next() ) );
  }
}

package arez;

import arez.spy.ComponentInfo;
import arez.spy.ObservableValueInfo;
import arez.spy.Spy;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class ObservableValueInfoImplTest
  extends AbstractTest
{
  @Test
  public void basicOperation()
  {
    final ArezContext context = Arez.context();
    final String name = ValueUtil.randomString();
    final ObservableValue<Object> observableValue = context.observable( name );
    final Observer observer = context.observer( observableValue::reportObserved );

    final ObservableValueInfo info = observableValue.asInfo();

    assertNull( info.getComponent() );
    assertEquals( info.getName(), name );
    assertEquals( info.toString(), name );

    assertFalse( info.hasAccessor() );
    assertFalse( info.hasMutator() );

    assertEquals( info.getObservers().size(), 1 );
    assertEquals( info.getObservers().get( 0 ).getName(), observer.getName() );
    assertUnmodifiable( info.getObservers() );

    assertFalse( info.isComputableValue() );
    assertFalse( info.isDisposed() );

    // Dispose observer to avoid accessing observableValue when it is disposed
    observer.dispose();

    observableValue.dispose();

    assertTrue( info.isDisposed() );
  }

  @Test
  public void basicOperation_withIntrospectors()
    throws Throwable
  {
    final ArezContext context = Arez.context();
    final String name = ValueUtil.randomString();
    final Component component = context.component( ValueUtil.randomString(), ValueUtil.randomString() );
    final AtomicReference<String> value = new AtomicReference<>();
    String initialValue = ValueUtil.randomString();
    value.set( initialValue );
    final ObservableValue<String> observableValue = context.observable( component, name, value::get, value::set );
    final Observer observer = context.observer( observableValue::reportObserved );

    final ObservableValueInfo info = observableValue.asInfo();

    final ComponentInfo componentInfo = info.getComponent();
    assertNotNull( componentInfo );
    assertEquals( componentInfo.getType(), component.getType() );
    assertEquals( info.getName(), name );
    assertEquals( info.toString(), name );

    assertTrue( info.hasAccessor() );
    assertEquals( info.getValue(), initialValue );

    final String newValue = ValueUtil.randomString();

    assertTrue( info.hasMutator() );
    info.setValue( newValue );
    assertEquals( info.getValue(), newValue );

    assertEquals( info.getObservers().size(), 1 );
    assertEquals( info.getObservers().get( 0 ).getName(), observer.getName() );
    assertUnmodifiable( info.getObservers() );

    assertFalse( info.isComputableValue() );
    assertFalse( info.isDisposed() );

    // Dispose observer to avoid accessing observableValue when it is disposed
    observer.dispose();

    observableValue.dispose();

    assertTrue( info.isDisposed() );
  }

  @Test
  public void asComputableValue()
  {
    final ArezContext context = Arez.context();
    final String name = ValueUtil.randomString();
    final ComputableValue<String> computableValue = context.computable( name, () -> "" );

    final ObservableValue<String> observableValue = computableValue.getObservableValue();

    final ObservableValueInfo info = observableValue.asInfo();

    assertEquals( info.getName(), name );

    assertTrue( info.isComputableValue() );
    assertEquals( info.asComputableValue().getName(), computableValue.getName() );
  }

  @SuppressWarnings( "EqualsWithItself" )
  @Test
  public void equalsAndHashCode()
  {
    final ArezContext context = Arez.context();
    final ObservableValue<Object> observableValue1 = context.observable();
    final ObservableValue<Object> observableValue2 = context.observable();

    final ObservableValueInfo info1a = observableValue1.asInfo();
    final ObservableValueInfo info1b = new ObservableValueInfoImpl( observableValue1 );
    final ObservableValueInfo info2 = observableValue2.asInfo();

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

    assertEquals( info1a.hashCode(), observableValue1.hashCode() );
    assertEquals( info1a.hashCode(), info1b.hashCode() );
    assertEquals( info2.hashCode(), observableValue2.hashCode() );
  }

  private <T> void assertUnmodifiable( @Nonnull final Collection<T> collection )
  {
    assertThrows( UnsupportedOperationException.class, () -> collection.remove( collection.iterator().next() ) );
  }

  @Test
  public void isComputableValue()
  {
    final ArezContext context = Arez.context();

    final Spy spy = context.getSpy();

    assertTrue( spy.asObservableValueInfo( context.computable( () -> "" ).getObservableValue() ).isComputableValue() );
    assertFalse( spy.asObservableValueInfo( context.observable() ).isComputableValue() );
  }

  @Test
  public void getComponent_Observable()
  {
    final ArezContext context = Arez.context();
    final Spy spy = context.getSpy();

    final Component component =
      context.component( ValueUtil.randomString(), ValueUtil.randomString(), ValueUtil.randomString() );
    final ObservableValue<Object> observableValue1 =
      context.observable( component, ValueUtil.randomString(), null, null );
    final ObservableValue<Object> observableValue2 = context.observable();

    final ComponentInfo info = spy.asObservableValueInfo( observableValue1 ).getComponent();
    assertNotNull( info );
    assertEquals( info.getName(), component.getName() );
    assertNull( spy.asObservableValueInfo( observableValue2 ).getComponent() );
  }

  @Test
  public void getComponent_Observable_nativeComponentsDisabled()
  {
    ArezTestUtil.disableNativeComponents();

    final ArezContext context = Arez.context();

    final ObservableValue<Object> value = context.observable();

    assertInvariantFailure( () -> value.asInfo().getComponent(),
                            "Arez-0107: Spy.getComponent invoked when Arez.areNativeComponentsEnabled() returns false." );

  }

  @Test
  public void observable_introspection()
    throws Throwable
  {
    final ArezContext context = Arez.context();
    final Spy spy = context.getSpy();

    final AtomicReference<String> value1 = new AtomicReference<>();
    value1.set( "23" );
    final AtomicReference<String> value2 = new AtomicReference<>();
    value2.set( "42" );

    final ObservableValue<String> observableValue1 =
      context.observable( ValueUtil.randomString(), value1::get, value1::set );
    final ObservableValue<String> observableValue2 = context.observable( ValueUtil.randomString(), value2::get, null );
    final ObservableValue<String> observableValue3 = context.observable( ValueUtil.randomString(), null, null );

    assertTrue( observableValue1.asInfo().hasAccessor() );
    assertTrue( observableValue2.asInfo().hasAccessor() );
    assertFalse( observableValue3.asInfo().hasAccessor() );

    assertTrue( observableValue1.asInfo().hasMutator() );
    assertFalse( observableValue2.asInfo().hasMutator() );
    assertFalse( observableValue3.asInfo().hasMutator() );

    assertEquals( spy.asObservableValueInfo( observableValue1 ).getValue(), "23" );
    assertEquals( spy.asObservableValueInfo( observableValue2 ).getValue(), "42" );

    assertInvariantFailure( () -> spy.asObservableValueInfo( observableValue3 ).getValue(),
                            "Arez-0112: Spy.getValue invoked on ObservableValue named '" + observableValue3.getName() +
                            "' but ObservableValue has no property accessor." );

    observableValue1.asInfo().setValue( "71" );

    assertEquals( spy.asObservableValueInfo( observableValue1 ).getValue(), "71" );

    assertInvariantFailure( () -> observableValue2.asInfo().setValue( "71" ),
                            "Arez-0115: Spy.setValue invoked on ObservableValue named '" + observableValue2.getName() +
                            "' but ObservableValue has no property mutator." );
  }

  @Test
  public void observable_getValue_introspectorsDisabled()
  {
    ArezTestUtil.disablePropertyIntrospectors();

    final ArezContext context = Arez.context();

    final ObservableValue<Integer> computableValue1 = context.observable();

    assertInvariantFailure( () -> context.action( () -> computableValue1.asInfo().getValue() ),
                            "Arez-0111: Spy.getValue invoked when Arez.arePropertyIntrospectorsEnabled() returns false." );
  }

  @Test
  public void observable_hasAccessor_introspectorsDisabled()
  {
    ArezTestUtil.disablePropertyIntrospectors();

    final ArezContext context = Arez.context();

    final ObservableValue<Integer> observableValue = context.observable();

    assertInvariantFailure( () -> context.action( () -> observableValue.asInfo().hasAccessor() ),
                            "Arez-0110: Spy.hasAccessor invoked when Arez.arePropertyIntrospectorsEnabled() returns false." );
  }

  @Test
  public void observable_hasMutator_introspectorsDisabled()
  {
    ArezTestUtil.disablePropertyIntrospectors();

    final ArezContext context = Arez.context();

    final ObservableValue<Integer> observableValue = context.observable();

    assertInvariantFailure( () -> context.action( () -> observableValue.asInfo().hasMutator() ),
                            "Arez-0113: Spy.hasMutator invoked when Arez.arePropertyIntrospectorsEnabled() returns false." );
  }

  @Test
  public void observable_setValue_introspectorsDisabled()
  {
    ArezTestUtil.disablePropertyIntrospectors();

    final ArezContext context = Arez.context();

    final ObservableValue<Integer> observableValue = context.observable();

    assertInvariantFailure( () -> context.action( () -> observableValue.asInfo().setValue( 44 ) ),
                            "Arez-0114: Spy.setValue invoked when Arez.arePropertyIntrospectorsEnabled() returns false." );
  }

  @Test
  public void observable_setValue_noMutator()
  {
    final ArezContext context = Arez.context();

    final ObservableValue<Integer> observableValue = context.observable();

    assertInvariantFailure( () -> context.action( () -> observableValue.asInfo().setValue( 44 ) ),
                            "Arez-0115: Spy.setValue invoked on ObservableValue named '" + observableValue.getName() +
                            "' but ObservableValue has no property mutator." );
  }
}

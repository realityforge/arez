import arez.Arez;
import arez.ArezContext;
import arez.Component;
import arez.ComputedValue;
import arez.Disposable;
import arez.Flags;
import arez.ObservableValue;
import arez.Observer;
import arez.component.ComponentState;
import arez.component.Identifiable;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.realityforge.braincheck.Guards;

@Generated("arez.processor.ArezProcessor")
@SuppressWarnings("unchecked")
@Singleton
public final class Arez_SingletonWithIdModel extends SingletonWithIdModel implements Disposable, Identifiable<Integer> {
  private static volatile int $$arezi$$_nextId;

  private final int $$arezi$$_id;

  private byte $$arezi$$_state;

  @Nullable
  private final ArezContext $$arezi$$_context;

  private final Component $$arezi$$_component;

  @Nonnull
  private final ObservableValue<Long> $$arez$$_time;

  @Nonnull
  private final ComputedValue<Integer> $$arez$$_someValue;

  @Nonnull
  private final Observer $$arez$$_render;

  @Nonnull
  private final Observer $$arez$$_render2;

  @Nonnull
  private final Observer $$arez$$_myAutorun;

  @Inject
  public Arez_SingletonWithIdModel() {
    super();
    this.$$arezi$$_context = Arez.areZonesEnabled() ? Arez.context() : null;
    this.$$arezi$$_id = ( Arez.areNamesEnabled() || Arez.areRegistriesEnabled() || Arez.areNativeComponentsEnabled() ) ? $$arezi$$_nextId++ : 0;
    if ( Arez.shouldCheckApiInvariants() ) {
      this.$$arezi$$_state = ComponentState.COMPONENT_INITIALIZED;
    }
    this.$$arezi$$_component = Arez.areNativeComponentsEnabled() ? $$arezi$$_context().component( "SingletonWithIdModel", $$arezi$$_id(), Arez.areNamesEnabled() ? $$arezi$$_name() : null ) : null;
    this.$$arez$$_time = $$arezi$$_context().observable( Arez.areNativeComponentsEnabled() ? this.$$arezi$$_component : null, Arez.areNamesEnabled() ? $$arezi$$_name() + ".time" : null, Arez.arePropertyIntrospectorsEnabled() ? () -> super.getTime() : null, Arez.arePropertyIntrospectorsEnabled() ? v -> super.setTime( v ) : null );
    this.$$arez$$_someValue = $$arezi$$_context().computed( Arez.areNativeComponentsEnabled() ? this.$$arezi$$_component : null, Arez.areNamesEnabled() ? $$arezi$$_name() + ".someValue" : null, () -> super.someValue(), Flags.RUN_LATER );
    this.$$arez$$_render = $$arezi$$_context().observer( Arez.areNativeComponentsEnabled() ? this.$$arezi$$_component : null, Arez.areNamesEnabled() ? $$arezi$$_name() + ".render" : null, () -> super.render(), () -> super.onRenderDepsChanged(), Flags.RUN_LATER | Flags.NESTED_ACTIONS_DISALLOWED | Flags.AREZ_DEPENDENCIES_ONLY );
    this.$$arez$$_render2 = $$arezi$$_context().tracker( Arez.areNativeComponentsEnabled() ? this.$$arezi$$_component : null, Arez.areNamesEnabled() ? $$arezi$$_name() + ".render2" : null, () -> super.onRender2DepsChanged(), Flags.RUN_LATER | Flags.NESTED_ACTIONS_DISALLOWED | Flags.AREZ_DEPENDENCIES_ONLY );
    this.$$arez$$_myAutorun = $$arezi$$_context().observer( Arez.areNativeComponentsEnabled() ? this.$$arezi$$_component : null, Arez.areNamesEnabled() ? $$arezi$$_name() + ".myAutorun" : null, () -> super.myAutorun(), Flags.RUN_LATER | Flags.NESTED_ACTIONS_DISALLOWED | Flags.AREZ_DEPENDENCIES_ONLY );
    if ( Arez.shouldCheckApiInvariants() ) {
      this.$$arezi$$_state = ComponentState.COMPONENT_CONSTRUCTED;
    }
    if ( Arez.areNativeComponentsEnabled() ) {
      this.$$arezi$$_component.complete();
    }
    if ( Arez.shouldCheckApiInvariants() ) {
      this.$$arezi$$_state = ComponentState.COMPONENT_COMPLETE;
    }
    $$arezi$$_context().triggerScheduler();
    if ( Arez.shouldCheckApiInvariants() ) {
      this.$$arezi$$_state = ComponentState.COMPONENT_READY;
    }
  }

  final ArezContext $$arezi$$_context() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.hasBeenInitialized( this.$$arezi$$_state ), () -> "Method named '$$arezi$$_context' invoked on uninitialized component of type 'SingletonWithIdModel'" );
    }
    return Arez.areZonesEnabled() ? this.$$arezi$$_context : Arez.context();
  }

  final int $$arezi$$_id() {
    if ( Arez.shouldCheckInvariants() && !Arez.areNamesEnabled() && !Arez.areRegistriesEnabled() && !Arez.areNativeComponentsEnabled() ) {
      Guards.fail( () -> "Method invoked to access id when id not expected on component named '" + $$arezi$$_name() + "'." );
    }
    return this.$$arezi$$_id;
  }

  @Override
  @Nonnull
  public final Integer getArezId() {
    return $$arezi$$_id();
  }

  String $$arezi$$_name() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.hasBeenInitialized( this.$$arezi$$_state ), () -> "Method named '$$arezi$$_name' invoked on uninitialized component of type 'SingletonWithIdModel'" );
    }
    return "SingletonWithIdModel." + $$arezi$$_id();
  }

  @Override
  public boolean isDisposed() {
    return ComponentState.isDisposingOrDisposed( this.$$arezi$$_state );
  }

  @Override
  public void dispose() {
    if ( !ComponentState.isDisposingOrDisposed( this.$$arezi$$_state ) ) {
      this.$$arezi$$_state = ComponentState.COMPONENT_DISPOSING;
      if ( Arez.areNativeComponentsEnabled() ) {
        this.$$arezi$$_component.dispose();
      } else {
        $$arezi$$_context().safeAction( Arez.areNamesEnabled() ? $$arezi$$_name() + ".dispose" : null, () -> { {
          this.$$arez$$_render.dispose();
          this.$$arez$$_render2.dispose();
          this.$$arez$$_myAutorun.dispose();
          this.$$arez$$_someValue.dispose();
          this.$$arez$$_time.dispose();
        } }, Flags.NO_VERIFY_ACTION_REQUIRED );
      }
      if ( Arez.shouldCheckApiInvariants() ) {
        this.$$arezi$$_state = ComponentState.COMPONENT_DISPOSED;
      }
    }
  }

  @Override
  public long getTime() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.isActive( this.$$arezi$$_state ), () -> "Method named 'getTime' invoked on " + ComponentState.describe( this.$$arezi$$_state ) + " component named '" + $$arezi$$_name() + "'" );
    }
    this.$$arez$$_time.reportObserved();
    return super.getTime();
  }

  @Override
  public void setTime(final long time) {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.isActive( this.$$arezi$$_state ), () -> "Method named 'setTime' invoked on " + ComponentState.describe( this.$$arezi$$_state ) + " component named '" + $$arezi$$_name() + "'" );
    }
    this.$$arez$$_time.preReportChanged();
    final long $$arezv$$_currentValue = super.getTime();
    if ( time != $$arezv$$_currentValue ) {
      super.setTime( time );
      this.$$arez$$_time.reportChanged();
    }
  }

  @Override
  void render() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.fail( () -> "Observed method named 'render' invoked but @Observed(executor=AREZ) annotated methods should only be invoked by the runtime." );
    }
    super.render();
  }

  @Override
  Observer getRenderObserver() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.isActive( this.$$arezi$$_state ), () -> "Method named 'getRenderObserver' invoked on " + ComponentState.describe( this.$$arezi$$_state ) + " component named '" + $$arezi$$_name() + "'" );
    }
    return $$arez$$_render;
  }

  @Override
  public void render2(final int i) {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.isActive( this.$$arezi$$_state ), () -> "Method named 'render2' invoked on " + ComponentState.describe( this.$$arezi$$_state ) + " component named '" + $$arezi$$_name() + "'" );
    }
    try {
      $$arezi$$_context().safeObserve( this.$$arez$$_render2, () -> super.render2( i ), Arez.areSpiesEnabled() ? new Object[] { i } : null );
    } catch( final RuntimeException | Error $$arez_exception$$ ) {
      throw $$arez_exception$$;
    } catch( final Throwable $$arez_exception$$ ) {
      throw new IllegalStateException( $$arez_exception$$ );
    }
  }

  @Override
  protected void myAutorun() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.fail( () -> "Observed method named 'myAutorun' invoked but @Observed(executor=AREZ) annotated methods should only be invoked by the runtime." );
    }
    super.myAutorun();
  }

  @Override
  public void doStuff(final long time) {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.isActive( this.$$arezi$$_state ), () -> "Method named 'doStuff' invoked on " + ComponentState.describe( this.$$arezi$$_state ) + " component named '" + $$arezi$$_name() + "'" );
    }
    try {
      $$arezi$$_context().safeAction(Arez.areNamesEnabled() ? $$arezi$$_name() + ".doStuff" : null, () -> super.doStuff( time ), Flags.READ_WRITE | Flags.VERIFY_ACTION_REQUIRED, Arez.areSpiesEnabled() ? new Object[] { time } : null );
    } catch( final RuntimeException | Error $$arez_exception$$ ) {
      throw $$arez_exception$$;
    } catch( final Throwable $$arez_exception$$ ) {
      throw new IllegalStateException( $$arez_exception$$ );
    }
  }

  @Override
  public int someValue() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.isActive( this.$$arezi$$_state ), () -> "Method named 'someValue' invoked on " + ComponentState.describe( this.$$arezi$$_state ) + " component named '" + $$arezi$$_name() + "'" );
    }
    return this.$$arez$$_someValue.get();
  }

  @Override
  public final int hashCode() {
    if ( Arez.areNativeComponentsEnabled() ) {
      return Integer.hashCode( $$arezi$$_id() );
    } else {
      return super.hashCode();
    }
  }

  @Override
  public final boolean equals(final Object o) {
    if ( Arez.areNativeComponentsEnabled() ) {
      if ( this == o ) {
        return true;
      } else if ( null == o || !(o instanceof Arez_SingletonWithIdModel) ) {
        return false;
      } else {
        final Arez_SingletonWithIdModel that = (Arez_SingletonWithIdModel) o;
        return $$arezi$$_id() == that.$$arezi$$_id();
      }
    } else {
      return super.equals( o );
    }
  }

  @Override
  public final String toString() {
    if ( Arez.areNamesEnabled() ) {
      return "ArezComponent[" + $$arezi$$_name() + "]";
    } else {
      return super.toString();
    }
  }
}

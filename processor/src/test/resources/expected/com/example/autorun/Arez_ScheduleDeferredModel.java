package com.example.autorun;

import arez.Arez;
import arez.ArezContext;
import arez.Component;
import arez.Disposable;
import arez.Observable;
import arez.Observer;
import arez.component.ComponentObservable;
import arez.component.ComponentState;
import arez.component.Identifiable;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.realityforge.braincheck.Guards;

@Generated("arez.processor.ArezProcessor")
public final class Arez_ScheduleDeferredModel extends ScheduleDeferredModel implements Disposable, Identifiable<Integer>, ComponentObservable {
  private static volatile int $$arezi$$_nextId;

  private final int $$arezi$$_id;

  private byte $$arezi$$_state;

  @Nullable
  private final ArezContext $$arezi$$_context;

  private final Component $$arezi$$_component;

  private final Observable<Boolean> $$arezi$$_disposedObservable;

  @Nonnull
  private final Observer $$arez$$_doStuff;

  public Arez_ScheduleDeferredModel() {
    super();
    this.$$arezi$$_context = Arez.areZonesEnabled() ? Arez.context() : null;
    this.$$arezi$$_id = ( Arez.areNamesEnabled() || Arez.areNativeComponentsEnabled() ) ? $$arezi$$_nextId++ : 0;
    if ( Arez.shouldCheckInvariants() ) {
      this.$$arezi$$_state = ComponentState.COMPONENT_INITIALIZED;
    }
    this.$$arezi$$_component = Arez.areNativeComponentsEnabled() ? $$arezi$$_context().createComponent( "ScheduleDeferredModel", $$arezi$$_id(), Arez.areNamesEnabled() ? $$arezi$$_name() : null ) : null;
    this.$$arezi$$_disposedObservable = $$arezi$$_context().createObservable( Arez.areNativeComponentsEnabled() ? this.$$arezi$$_component : null, Arez.areNamesEnabled() ? $$arezi$$_name() + ".isDisposed" : null, Arez.arePropertyIntrospectorsEnabled() ? () -> this.$$arezi$$_state >= 0 : null );
    this.$$arez$$_doStuff = $$arezi$$_context().autorun( Arez.areNativeComponentsEnabled() ? this.$$arezi$$_component : null, Arez.areNamesEnabled() ? $$arezi$$_name() + ".doStuff" : null, false, () -> super.doStuff() );
    if ( Arez.shouldCheckInvariants() ) {
      this.$$arezi$$_state = ComponentState.COMPONENT_CONSTRUCTED;
    }
    if ( Arez.areNativeComponentsEnabled() ) {
      this.$$arezi$$_component.complete();
    }
    if ( Arez.shouldCheckInvariants() ) {
      this.$$arezi$$_state = ComponentState.COMPONENT_READY;
    }
  }

  final ArezContext $$arezi$$_context() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.hasBeenInitialized( this.$$arezi$$_state ), () -> "Method named '$$arezi$$_context' invoked on uninitialized component of type 'ScheduleDeferredModel'" );
    }
    return Arez.areZonesEnabled() ? this.$$arezi$$_context : Arez.context();
  }

  final int $$arezi$$_id() {
    if ( Arez.shouldCheckInvariants() && !Arez.areNamesEnabled() && !Arez.areNativeComponentsEnabled() ) {
      Guards.fail( () -> "Method invoked to access id when id not expected." );
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
      Guards.apiInvariant( () -> ComponentState.hasBeenInitialized( this.$$arezi$$_state ), () -> "Method named '$$arezi$$_name' invoked on uninitialized component of type 'ScheduleDeferredModel'" );
    }
    return "ScheduleDeferredModel." + $$arezi$$_id();
  }

  private boolean $$arezi$$_observe() {
    final boolean isNotDisposed = isNotDisposed();
    if ( isNotDisposed )  {
      this.$$arezi$$_disposedObservable.reportObserved();
    }
    return isNotDisposed;
  }

  @Override
  public boolean observe() {
    return $$arezi$$_observe();
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
        $$arezi$$_context().dispose( Arez.areNamesEnabled() ? $$arezi$$_name() : null, () -> { {
          this.$$arezi$$_disposedObservable.dispose();
          this.$$arez$$_doStuff.dispose();
        } } );
      }
      if ( Arez.shouldCheckInvariants() ) {
        this.$$arezi$$_state = ComponentState.COMPONENT_DISPOSED;
      }
    }
  }

  @Override
  protected void doStuff() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.fail( () -> "Autorun method named 'doStuff' invoked but @Autorun annotated methods should only be invoked by the runtime." );
    }
    super.doStuff();
  }

  @Override
  public final int hashCode() {
    if ( Arez.areNativeComponentsEnabled() ) {
      return Double.hashCode( $$arezi$$_id() );
    } else {
      return super.hashCode();
    }
  }

  @Override
  public final boolean equals(final Object o) {
    if ( Arez.areNativeComponentsEnabled() ) {
      if ( this == o ) {
        return true;
      } else if ( null == o || !(o instanceof Arez_ScheduleDeferredModel) ) {
        return false;
      } else {
        final Arez_ScheduleDeferredModel that = (Arez_ScheduleDeferredModel) o;;
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

package com.example.reference;

import arez.Arez;
import arez.ArezContext;
import arez.Component;
import arez.Disposable;
import arez.Locator;
import arez.ObservableValue;
import arez.component.DisposeNotifier;
import arez.component.DisposeTrackable;
import arez.component.Identifiable;
import arez.component.Verifiable;
import arez.component.internal.ComponentKernel;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.realityforge.braincheck.Guards;

@Generated("arez.processor.ArezProcessor")
final class Arez_EagerLoadObservableReferenceModel extends EagerLoadObservableReferenceModel implements Disposable, Identifiable<Integer>, Verifiable, DisposeTrackable {
  private static volatile int $$arezi$$_nextId;

  private final ComponentKernel $$arezi$$_kernel;

  @Nonnull
  private final ObservableValue<Integer> $$arez$$_myEntityId;

  private int $$arezd$$_myEntityId;

  @Nullable
  private EagerLoadObservableReferenceModel.MyEntity $$arezr$$_myEntity;

  Arez_EagerLoadObservableReferenceModel() {
    super();
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> Arez.areReferencesEnabled(), () -> "Attempted to create instance of component of type 'EagerLoadObservableReferenceModel' that contains references but Arez.areReferencesEnabled() returns false. References need to be enabled to use this component" );
    }
    final ArezContext $$arezv$$_context = Arez.context();
    final int $$arezv$$_id = ( Arez.areNamesEnabled() || Arez.areRegistriesEnabled() || Arez.areNativeComponentsEnabled() ) ? ++$$arezi$$_nextId : 0;
    final String $$arezv$$_name = Arez.areNamesEnabled() ? "EagerLoadObservableReferenceModel." + $$arezv$$_id : null;
    final Component $$arezv$$_component = Arez.areNativeComponentsEnabled() ? $$arezv$$_context.component( "EagerLoadObservableReferenceModel", $$arezv$$_id, $$arezv$$_name, () -> $$arezi$$_nativeComponentPreDispose() ) : null;
    this.$$arezi$$_kernel = new ComponentKernel( Arez.areZonesEnabled() ? $$arezv$$_context : null, $$arezv$$_name, $$arezv$$_id, $$arezv$$_component, Arez.areNativeComponentsEnabled() ? null : this::$$arezi$$_preDispose, Arez.areNativeComponentsEnabled() ? null : this::$$arezi$$_dispose, null, true, false, false );
    this.$$arez$$_myEntityId = $$arezv$$_context.observable( Arez.areNativeComponentsEnabled() ? $$arezv$$_component : null, Arez.areNamesEnabled() ? $$arezv$$_name + ".myEntityId" : null, Arez.arePropertyIntrospectorsEnabled() ? () -> this.$$arezd$$_myEntityId : null, Arez.arePropertyIntrospectorsEnabled() ? v -> this.$$arezd$$_myEntityId = v : null );
    this.$$arezi$$_kernel.componentConstructed();
    this.$$arezi$$_link_myEntity();
    this.$$arezi$$_kernel.componentReady();
  }

  final Locator $$arezi$$_locator() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.hasBeenInitialized(), () -> "Method named '$$arezi$$_locator' invoked on uninitialized component of type 'EagerLoadObservableReferenceModel'" );
    }
    return this.$$arezi$$_kernel.getContext().locator();
  }

  final int $$arezi$$_id() {
    return this.$$arezi$$_kernel.getId();
  }

  @Override
  @Nonnull
  public final Integer getArezId() {
    return $$arezi$$_id();
  }

  private void $$arezi$$_preDispose() {
  }

  private void $$arezi$$_nativeComponentPreDispose() {
    this.$$arezi$$_preDispose();
    this.$$arezi$$_kernel.getDisposeNotifier().dispose();
  }

  @Override
  @Nonnull
  public DisposeNotifier getNotifier() {
    return this.$$arezi$$_kernel.getDisposeNotifier();
  }

  @Override
  public boolean isDisposed() {
    return this.$$arezi$$_kernel.isDisposed();
  }

  @Override
  public void dispose() {
    this.$$arezi$$_kernel.dispose();
  }

  private void $$arezi$$_dispose() {
    this.$$arezi$$_preDispose();
    this.$$arez$$_myEntityId.dispose();
  }

  @Override
  public void verify() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named 'verify' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? '?' : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    if ( Arez.shouldCheckApiInvariants() && Arez.isVerifyEnabled() ) {
      Guards.apiInvariant( () -> this == $$arezi$$_locator().findById( EagerLoadObservableReferenceModel.class, $$arezi$$_id() ), () -> "Attempted to lookup self in Locator with type EagerLoadObservableReferenceModel and id '" + $$arezi$$_id() + "' but unable to locate self. Actual value: " + $$arezi$$_locator().findById( EagerLoadObservableReferenceModel.class, $$arezi$$_id() ) );
      final int $$arezv$$_myEntityId = this.getMyEntityId();
      final EagerLoadObservableReferenceModel.MyEntity $$arezv$$_myEntity = this.$$arezi$$_locator().findById( EagerLoadObservableReferenceModel.MyEntity.class, $$arezv$$_myEntityId );
      Guards.apiInvariant( () -> null != $$arezv$$_myEntity, () -> "Reference named 'myEntity' on component named '" + this.$$arezi$$_kernel.getName() + "' is unable to resolve entity of type com.example.reference.EagerLoadObservableReferenceModel.MyEntity and id = " + getMyEntityId() );
    }
  }

  @Override
  int getMyEntityId() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named 'getMyEntityId' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? '?' : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    this.$$arez$$_myEntityId.reportObserved();
    return this.$$arezd$$_myEntityId;
  }

  @Override
  void setMyEntityId(final int id) {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named 'setMyEntityId' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? '?' : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    this.$$arez$$_myEntityId.preReportChanged();
    final int $$arezv$$_currentValue = this.$$arezd$$_myEntityId;
    if ( id != $$arezv$$_currentValue ) {
      this.$$arezd$$_myEntityId = id;
      this.$$arez$$_myEntityId.reportChanged();
      this.$$arezi$$_link_myEntity();
    }
  }

  @Override
  EagerLoadObservableReferenceModel.MyEntity getMyEntity() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named 'getMyEntity' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? '?' : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != $$arezr$$_myEntity, () -> "Nonnull reference method named 'getMyEntity' invoked on component named '" + this.$$arezi$$_kernel.getName() + "' but reference has not been resolved yet is not lazy. Id = " + getMyEntityId() );
    }
    this.$$arez$$_myEntityId.reportObserved();
    return this.$$arezr$$_myEntity;
  }

  private void $$arezi$$_link_myEntity() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named '$$arezi$$_link_myEntity' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? '?' : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    final int id = this.getMyEntityId();
    this.$$arezr$$_myEntity = this.$$arezi$$_locator().findById( EagerLoadObservableReferenceModel.MyEntity.class, id );
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != $$arezr$$_myEntity, () -> "Reference named 'myEntity' on component named '" + this.$$arezi$$_kernel.getName() + "' is unable to resolve entity of type com.example.reference.EagerLoadObservableReferenceModel.MyEntity and id = " + getMyEntityId() );
    }
  }

  private void $$arezi$$_delink_myEntity() {
    this.$$arezr$$_myEntity = null;
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
      if ( o instanceof Arez_EagerLoadObservableReferenceModel ) {
        final Arez_EagerLoadObservableReferenceModel that = (Arez_EagerLoadObservableReferenceModel) o;
        return $$arezi$$_id() == that.$$arezi$$_id();
      } else {
        return false;
      }
    } else {
      return super.equals( o );
    }
  }

  @Override
  public final String toString() {
    if ( Arez.areNamesEnabled() ) {
      return "ArezComponent[" + this.$$arezi$$_kernel.getName() + "]";
    } else {
      return super.toString();
    }
  }
}

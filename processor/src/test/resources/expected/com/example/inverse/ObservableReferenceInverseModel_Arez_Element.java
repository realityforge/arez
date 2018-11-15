package com.example.inverse;

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
final class ObservableReferenceInverseModel_Arez_Element extends ObservableReferenceInverseModel.Element implements Disposable, Identifiable<Integer>, Verifiable, DisposeTrackable {
  private static volatile int $$arezi$$_nextId;

  private final ComponentKernel $$arezi$$_kernel;

  @Nonnull
  private final ObservableValue<Integer> $$arez$$_observableReferenceInverseModelId;

  private int $$arezd$$_observableReferenceInverseModelId;

  @Nullable
  private ObservableReferenceInverseModel $$arezr$$_observableReferenceInverseModel;

  ObservableReferenceInverseModel_Arez_Element() {
    super();
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> Arez.areReferencesEnabled(), () -> "Attempted to create instance of component of type 'Element' that contains references but Arez.areReferencesEnabled() returns false. References need to be enabled to use this component" );
    }
    final ArezContext $$arezv$$_context = Arez.context();
    final int $$arezv$$_id = ( Arez.areNamesEnabled() || Arez.areRegistriesEnabled() || Arez.areNativeComponentsEnabled() ) ? ++$$arezi$$_nextId : 0;
    final String $$arezv$$_name = Arez.areNamesEnabled() ? "Element." + $$arezv$$_id : null;
    final Component $$arezv$$_component = Arez.areNativeComponentsEnabled() ? $$arezv$$_context.component( "Element", $$arezv$$_id, $$arezv$$_name, () -> $$arezi$$_nativeComponentPreDispose() ) : null;
    this.$$arezi$$_kernel = new ComponentKernel( Arez.areZonesEnabled() ? $$arezv$$_context : null, $$arezv$$_name, $$arezv$$_id, $$arezv$$_component, Arez.areNativeComponentsEnabled() ? null : this::$$arezi$$_preDispose, Arez.areNativeComponentsEnabled() ? null : this::$$arezi$$_dispose, null, true, false, false );
    this.$$arez$$_observableReferenceInverseModelId = $$arezv$$_context.observable( Arez.areNativeComponentsEnabled() ? $$arezv$$_component : null, Arez.areNamesEnabled() ? $$arezv$$_name + ".observableReferenceInverseModelId" : null, Arez.arePropertyIntrospectorsEnabled() ? () -> this.$$arezd$$_observableReferenceInverseModelId : null, Arez.arePropertyIntrospectorsEnabled() ? v -> this.$$arezd$$_observableReferenceInverseModelId = v : null );
    this.$$arezi$$_kernel.componentConstructed();
    this.$$arezi$$_link_observableReferenceInverseModel();
    this.$$arezi$$_kernel.componentReady();
  }

  final Locator $$arezi$$_locator() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.hasBeenInitialized(), () -> "Method named '$$arezi$$_locator' invoked on uninitialized component of type 'Element'" );
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
    this.$$arezi$$_delink_observableReferenceInverseModel();
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
    this.$$arez$$_observableReferenceInverseModelId.dispose();
  }

  @Override
  public void verify() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named 'verify' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? '?' : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    if ( Arez.shouldCheckApiInvariants() && Arez.isVerifyEnabled() ) {
      Guards.apiInvariant( () -> this == $$arezi$$_locator().findById( ObservableReferenceInverseModel.Element.class, $$arezi$$_id() ), () -> "Attempted to lookup self in Locator with type ObservableReferenceInverseModel.Element and id '" + $$arezi$$_id() + "' but unable to locate self. Actual value: " + $$arezi$$_locator().findById( ObservableReferenceInverseModel.Element.class, $$arezi$$_id() ) );
      final int $$arezv$$_observableReferenceInverseModelId = this.getObservableReferenceInverseModelId();
      final ObservableReferenceInverseModel $$arezv$$_observableReferenceInverseModel = this.$$arezi$$_locator().findById( ObservableReferenceInverseModel.class, $$arezv$$_observableReferenceInverseModelId );
      Guards.apiInvariant( () -> null != $$arezv$$_observableReferenceInverseModel, () -> "Reference named 'observableReferenceInverseModel' on component named '" + this.$$arezi$$_kernel.getName() + "' is unable to resolve entity of type com.example.inverse.ObservableReferenceInverseModel and id = " + getObservableReferenceInverseModelId() );
    }
  }

  @Override
  int getObservableReferenceInverseModelId() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named 'getObservableReferenceInverseModelId' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? '?' : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    this.$$arez$$_observableReferenceInverseModelId.reportObserved();
    return this.$$arezd$$_observableReferenceInverseModelId;
  }

  @Override
  void setObservableReferenceInverseModelId(final int id) {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named 'setObservableReferenceInverseModelId' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? '?' : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    this.$$arez$$_observableReferenceInverseModelId.preReportChanged();
    final int $$arezv$$_currentValue = this.$$arezd$$_observableReferenceInverseModelId;
    if ( id != $$arezv$$_currentValue ) {
      this.$$arezd$$_observableReferenceInverseModelId = id;
      this.$$arez$$_observableReferenceInverseModelId.reportChanged();
      this.$$arezi$$_delink_observableReferenceInverseModel();
      this.$$arezi$$_link_observableReferenceInverseModel();
    }
  }

  @Override
  ObservableReferenceInverseModel getObservableReferenceInverseModel() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named 'getObservableReferenceInverseModel' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? '?' : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != $$arezr$$_observableReferenceInverseModel, () -> "Nonnull reference method named 'getObservableReferenceInverseModel' invoked on component named '" + this.$$arezi$$_kernel.getName() + "' but reference has not been resolved yet is not lazy. Id = " + getObservableReferenceInverseModelId() );
    }
    this.$$arez$$_observableReferenceInverseModelId.reportObserved();
    return this.$$arezr$$_observableReferenceInverseModel;
  }

  private void $$arezi$$_link_observableReferenceInverseModel() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named '$$arezi$$_link_observableReferenceInverseModel' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? '?' : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    final int id = this.getObservableReferenceInverseModelId();
    this.$$arezr$$_observableReferenceInverseModel = this.$$arezi$$_locator().findById( ObservableReferenceInverseModel.class, id );
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != $$arezr$$_observableReferenceInverseModel, () -> "Reference named 'observableReferenceInverseModel' on component named '" + this.$$arezi$$_kernel.getName() + "' is unable to resolve entity of type com.example.inverse.ObservableReferenceInverseModel and id = " + getObservableReferenceInverseModelId() );
    }
    ( (Arez_ObservableReferenceInverseModel) this.$$arezr$$_observableReferenceInverseModel ).$$arezir$$_elements_add( this );
  }

  void $$arezi$$_delink_observableReferenceInverseModel() {
    if ( null != $$arezr$$_observableReferenceInverseModel && Disposable.isNotDisposed( $$arezr$$_observableReferenceInverseModel ) ) {
      ( (Arez_ObservableReferenceInverseModel) this.$$arezr$$_observableReferenceInverseModel ).$$arezir$$_elements_remove( this );
    }
    this.$$arezr$$_observableReferenceInverseModel = null;
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
      if ( o instanceof ObservableReferenceInverseModel_Arez_Element ) {
        final ObservableReferenceInverseModel_Arez_Element that = (ObservableReferenceInverseModel_Arez_Element) o;
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

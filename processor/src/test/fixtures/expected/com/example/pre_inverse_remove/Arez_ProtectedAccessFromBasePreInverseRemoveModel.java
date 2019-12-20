package com.example.pre_inverse_remove;

import arez.Arez;
import arez.ArezContext;
import arez.Component;
import arez.Disposable;
import arez.Locator;
import arez.ObservableValue;
import arez.SafeProcedure;
import arez.component.CollectionsUtil;
import arez.component.DisposeNotifier;
import arez.component.Identifiable;
import arez.component.Verifiable;
import arez.component.internal.ComponentKernel;
import com.example.pre_inverse_remove.other.BaseProtectedAccessPreInverseRemoveModel;
import com.example.pre_inverse_remove.other.BaseProtectedAccessPreInverseRemoveModel_Arez_Element;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import org.realityforge.braincheck.Guards;

@Generated("arez.processor.ArezProcessor")
public final class Arez_ProtectedAccessFromBasePreInverseRemoveModel extends ProtectedAccessFromBasePreInverseRemoveModel implements Disposable, Identifiable<Integer>, Verifiable, DisposeNotifier {
  private static volatile int $$arezi$$_nextId;

  private final ComponentKernel $$arezi$$_kernel;

  @Nonnull
  private final ObservableValue<Collection<BaseProtectedAccessPreInverseRemoveModel.Element>> $$arez$$_elements;

  private Collection<BaseProtectedAccessPreInverseRemoveModel.Element> $$arezd$$_elements;

  private Collection<BaseProtectedAccessPreInverseRemoveModel.Element> $$arezd$$_$$cache$$_elements;

  public Arez_ProtectedAccessFromBasePreInverseRemoveModel() {
    super();
    final ArezContext $$arezv$$_context = Arez.context();
    final int $$arezv$$_id = ++$$arezi$$_nextId;
    final String $$arezv$$_name = Arez.areNamesEnabled() ? "ProtectedAccessFromBasePreInverseRemoveModel." + $$arezv$$_id : null;
    final Component $$arezv$$_component = Arez.areNativeComponentsEnabled() ? $$arezv$$_context.component( "ProtectedAccessFromBasePreInverseRemoveModel", $$arezv$$_id, $$arezv$$_name, () -> $$arezi$$_nativeComponentPreDispose() ) : null;
    this.$$arezi$$_kernel = new ComponentKernel( Arez.areZonesEnabled() ? $$arezv$$_context : null, Arez.areNamesEnabled() ? $$arezv$$_name : null, $$arezv$$_id, Arez.areNativeComponentsEnabled() ? $$arezv$$_component : null, Arez.areNativeComponentsEnabled() ? null : this::$$arezi$$_preDispose, Arez.areNativeComponentsEnabled() ? null : this::$$arezi$$_dispose, null, true, false, false );
    this.$$arez$$_elements = $$arezv$$_context.observable( Arez.areNativeComponentsEnabled() ? $$arezv$$_component : null, Arez.areNamesEnabled() ? $$arezv$$_name + ".elements" : null, Arez.arePropertyIntrospectorsEnabled() ? () -> this.$$arezd$$_elements : null, null );
    this.$$arezd$$_elements = new HashSet<>();
    this.$$arezd$$_$$cache$$_elements = null;
    this.$$arezi$$_kernel.componentConstructed();
    this.$$arezi$$_kernel.componentReady();
  }

  @Nonnull
  final Locator $$arezi$$_locator() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.hasBeenInitialized(), () -> "Method named '$$arezi$$_locator' invoked on uninitialized component of type 'ProtectedAccessFromBasePreInverseRemoveModel'" );
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
    for ( final BaseProtectedAccessPreInverseRemoveModel.Element other : new ArrayList<>( $$arezd$$_elements ) ) {
      ( (BaseProtectedAccessPreInverseRemoveModel_Arez_Element) other ).$$arezi$$_delink_other();
    }
  }

  private void $$arezi$$_nativeComponentPreDispose() {
    this.$$arezi$$_preDispose();
    this.$$arezi$$_kernel.notifyOnDisposeListeners();
  }

  @Override
  public final void addOnDisposeListener(@Nonnull final Object key,
      @Nonnull final SafeProcedure action) {
    this.$$arezi$$_kernel.addOnDisposeListener( key, action );
  }

  @Override
  public final void removeOnDisposeListener(@Nonnull final Object key) {
    this.$$arezi$$_kernel.removeOnDisposeListener( key );
  }

  @Override
  public final boolean isDisposed() {
    return this.$$arezi$$_kernel.isDisposed();
  }

  @Override
  public final void dispose() {
    this.$$arezi$$_kernel.dispose();
  }

  private void $$arezi$$_dispose() {
    this.$$arez$$_elements.dispose();
  }

  @Override
  public final void verify() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named 'verify' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? "?" : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    if ( Arez.shouldCheckApiInvariants() && Arez.isVerifyEnabled() ) {
      Guards.apiInvariant( () -> this == $$arezi$$_locator().findById( ProtectedAccessFromBasePreInverseRemoveModel.class, $$arezi$$_id() ), () -> "Attempted to lookup self in Locator with type ProtectedAccessFromBasePreInverseRemoveModel and id '" + $$arezi$$_id() + "' but unable to locate self. Actual value: " + $$arezi$$_locator().findById( ProtectedAccessFromBasePreInverseRemoveModel.class, $$arezi$$_id() ) );
      for( final BaseProtectedAccessPreInverseRemoveModel.Element element : this.$$arezd$$_elements ) {
        if ( Arez.shouldCheckApiInvariants() ) {
          Guards.apiInvariant( () -> Disposable.isNotDisposed( element ), () -> "Inverse relationship named 'elements' on component named '" + this.$$arezi$$_kernel.getName() + "' contains disposed element '" + element + "'" );
        }
      }
    }
  }

  @Override
  protected final Collection<BaseProtectedAccessPreInverseRemoveModel.Element> getElements() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named 'getElements' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? "?" : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    this.$$arez$$_elements.reportObserved();
    if ( Arez.areCollectionsPropertiesUnmodifiable() ) {
      final Collection<BaseProtectedAccessPreInverseRemoveModel.Element> $$ar$$_result = this.$$arezd$$_elements;
      if ( null == this.$$arezd$$_$$cache$$_elements && null != $$ar$$_result ) {
        this.$$arezd$$_$$cache$$_elements = CollectionsUtil.wrap( $$ar$$_result );
      }
      return $$arezd$$_$$cache$$_elements;
    } else {
      return this.$$arezd$$_elements;
    }
  }

  public final void $$arezir$$_elements_add(
      @Nonnull final BaseProtectedAccessPreInverseRemoveModel.Element element) {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named '$$arezir$$_elements_add' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? "?" : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    this.$$arez$$_elements.preReportChanged();
    if ( Arez.shouldCheckInvariants() ) {
      Guards.invariant( () -> !this.$$arezd$$_elements.contains( element ), () -> "Attempted to add reference 'element' to inverse 'elements' but inverse already contained element. Inverse = " + $$arez$$_elements );
    }
    this.$$arezd$$_elements.add( element );
    if ( Arez.areCollectionsPropertiesUnmodifiable() ) {
      this.$$arezd$$_$$cache$$_elements = null;
    }
    this.$$arez$$_elements.reportChanged();
  }

  public final void $$arezir$$_elements_remove(
      @Nonnull final BaseProtectedAccessPreInverseRemoveModel.Element element) {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> null != this.$$arezi$$_kernel && this.$$arezi$$_kernel.isActive(), () -> "Method named '$$arezir$$_elements_remove' invoked on " + this.$$arezi$$_kernel.describeState() + " component named '" + ( null == this.$$arezi$$_kernel ? "?" : this.$$arezi$$_kernel.getName() ) + "'" );
    }
    this.$$arez$$_elements.preReportChanged();
    if ( Arez.shouldCheckInvariants() ) {
      Guards.invariant( () -> this.$$arezd$$_elements.contains( element ), () -> "Attempted to remove reference 'element' from inverse 'elements' but inverse does not contain element. Inverse = " + $$arez$$_elements );
    }
    preElementsRemove( element );
    this.$$arezd$$_elements.remove( element );
    if ( Arez.areCollectionsPropertiesUnmodifiable() ) {
      this.$$arezd$$_$$cache$$_elements = null;
    }
    this.$$arez$$_elements.reportChanged();
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
      if ( o instanceof Arez_ProtectedAccessFromBasePreInverseRemoveModel ) {
        final Arez_ProtectedAccessFromBasePreInverseRemoveModel that = (Arez_ProtectedAccessFromBasePreInverseRemoveModel) o;
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

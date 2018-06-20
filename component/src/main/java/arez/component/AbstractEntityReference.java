package arez.component;

import arez.Disposable;
import arez.annotations.Observable;
import arez.annotations.ObservableRef;
import arez.annotations.PreDispose;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Abstract base class for reference to a component where reference should be cleared when the component is disposed.
 */
public abstract class AbstractEntityReference<T>
  extends AbstractEntryContainer<T>
{
  @Nullable
  private EntityEntry<T> _entry;

  /**
   * Dispose or detach the associated entity if any.
   */
  @PreDispose
  protected void preDispose()
  {
    doSetEntity( null );
  }

  /**
   * Return the entity that this reference points to.
   *
   * @return the associated entity.
   */
  @Nullable
  protected T getEntity()
  {
    return getEntityUnlessDisposed();
  }

  /**
   * Return true if reference for entity exists.
   *
   * @return true if reference for entity exists.
   */
  protected boolean hasEntity()
  {
    getEntityObservable().reportObserved();
    return null != getEntityUnlessDisposed();
  }

  /**
   * Return the entity if reference exists and entity is not disposed.
   *
   * @return the entity if it is not disposed.
   */
  @Nullable
  private T getEntityUnlessDisposed()
  {
    return null == _entry ?
           null :
           Disposable.isNotDisposed( _entry.getEntity() ) ? _entry.getEntity() : null;
  }

  /**
   * Return the observable associated with entity.
   *
   * @return the Arez observable associated with entities observable property.
   */
  @ObservableRef
  @Nonnull
  protected abstract arez.Observable getEntityObservable();

  /**
   * Set the entity that this reference points to.
   *
   * @param entity the associated entity.
   */
  @Observable
  protected void setEntity( @Nullable final T entity )
  {
    doSetEntity( entity );
  }

  private void doSetEntity( @Nullable final T entity )
  {
    if ( null != _entry )
    {
      detachEntry( _entry, false );
    }
    if ( null == entity )
    {
      _entry = null;
    }
    else
    {
      _entry = createEntityEntry( entity, reference -> {
        /*
         * Can not use setEntity( null ) here as the generated method will check equality
         * prior to mutating. getEntity will return null as entity has been disposed so
         * setter will not actually run.
         */
        getEntityObservable().preReportChanged();
        detachEntry( _entry, false );
        getEntityObservable().reportChanged();
      } );
    }
  }
}

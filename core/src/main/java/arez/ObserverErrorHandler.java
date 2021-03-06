package arez;

import grim.annotations.OmitType;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Interface for handling errors in observers.
 */
@FunctionalInterface
@OmitType( unless = "arez.enable_observer_error_handlers" )
public interface ObserverErrorHandler
{
  /**
   * Report an error in observer.
   *
   * @param observer  the observer that generated error.
   * @param error     the type of the error.
   * @param throwable the exception that caused error if any.
   */
  void onObserverError( @Nonnull Observer observer, @Nonnull ObserverError error, @Nullable Throwable throwable );
}

package arez.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

/**
 * Annotation that marks a method as observed.
 * Observed methods are invoked by the Arez runtime and should never be invoked directly by user code.
 *
 * <p>The method that is annotated with this annotation must comply with the additional constraints:</p>
 * <ul>
 * <li>Must not be annotated with any other arez annotation</li>
 * <li>Must not be private</li>
 * <li>Must not be public</li>
 * <li>Must not be static</li>
 * <li>Must not be final</li>
 * <li>Must not be abstract</li>
 * </ul>
 */
@Documented
@Target( ElementType.METHOD )
public @interface Observed
{
  /**
   * Return the name of the Observer relative to the component.
   * The value must conform to the requirements of a java identifier.
   * The name must also be unique across {@link Observable}s,
   * {@link Computed}s and {@link Action}s within the scope of the
   * {@link ArezComponent} annotated element.
   *
   * @return the name of the Observer relative to the component.
   */
  @Nonnull
  String name() default "<default>";

  /**
   * Does the observer's tracking method change arez state or not.
   * Observers are primarily used to reflect Arez state onto external systems (i.e. views, network layers etc.)
   * and thus the default value is false thus making the transaction mode read-only.
   *
   * @return true if the observer should use a read-write transaction, false if it should use a read-only transaction.
   */
  boolean mutation() default false;

  /**
   * The priority of the underlying observer
   *
   * @return the priority of the observer.
   */
  Priority priority() default Priority.NORMAL;

  /**
   * Flag controlling whether the observer can observe ComputedValue instances with lower priorities.
   * The default value of false will result in an invariant failure (in development mode) if a lower priority
   * dependency is observed by the observer. This is to prevent priority inversion when scheduling a higher
   * priority observer that is dependent upon a lower priority computed value. If the value is true then the no
   * invariant failure is triggered and the component relies on the component author to handle possible priority
   * inversion.
   *
   * @return false if observing lower priority dependencies should result in invariant failure in development mode.
   */
  boolean observeLowerPriorityDependencies() default false;

  /**
   * Can the observer invoke actions.
   * An action that specifies {@link Action#requireNewTransaction()} as true will start a new transaction
   * and any observables accessed within the action will not be dependencies of the observer. Sometimes this
   * behaviour is desired. Sometimes an action that specifies {@link Action#requireNewTransaction()} as false
   * will be used instead and any observable accessed within the scope of the action will be a dependency of
   * the observer and thus changes in the observable will reschedule the observer. Sometimes this
   * behaviour is desired. Either way the developer must be conscious of these decisions and thus must explicitly
   * set this flag to true to invoke any actions within the scope of the observers reaction.
   *
   * @return true if the observer can invoke actions, false otherwise.
   */
  boolean nestedActionsAllowed() default false;
}
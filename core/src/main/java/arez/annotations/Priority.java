package arez.annotations;

import arez.ComputableValue;
import arez.Disposable;

/**
 * Enum to control scheduling priority of observers/reactions.
 * Observers with higher priorities will react first. If observers have equal priorities then observers
 * scheduled first will react first. Observers must not depend upon ComputableValue instances with
 * a lower priority otherwise priority is ignored.
 *
 * <p>A user should be very careful when specifying a {@link #HIGH} priority as it is possible that
 * the the reaction will be scheduled part way through the process of disposing and/or unlinking one-or-more
 * components. Dispose reactions will often be scheduled with a higher priority but reactions unlinking disposed
 * arez components from remaining arez components. In many cases this may mean invoking
 * {@link Disposable#isDisposed(Object)} before accessing arez components.</p>
 */
public enum Priority
{
  /**
   * Highest priority.
   * This priority should be used when the reaction will dispose other reactive elements (and thus they
   * need not be scheduled).
   */
  HIGHEST,
  /**
   * High priority.
   * This priority should be used when the reaction will trigger many downstream reactions.
   */
  HIGH,
  /**
   * Normal priority.
   */
  NORMAL,
  /**
   * Low priority.
   * Usually used to schedule observers that reflect state onto non-reactive
   * application components. i.e. Observers that are used to build html views,
   * perform network operations etc. These reactions are often at low priority
   * to avoid recalculation of dependencies (i.e. {@link ComputableValue}s) triggering
   * this reaction multiple times within a single reaction round.
   */
  LOW,
  /**
   * Lowest priority.
   * This is low-priority reactions that reflect onto non-reactive applications. It is
   * also used for (i.e. {@link ComputableValue}s) that may be unobserved when a {@link #LOW}
   * priority reaction runs.
   */
  LOWEST,
  /**
   * Default priority.
   * Use the value of the {@link ArezComponent#defaultPriority} specified at the component level or {@link #NORMAL}
   * if for the value of {@link ArezComponent#defaultPriority}.
   */
  DEFAULT
}

package arez.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Marks a template method that returns the {@link arez.ArezContext} instance for component.
 *
 * <p>The method that is annotated with this annotation must also comply with the following constraints:</p>
 * <ul>
 * <li>Must not be annotated with any other arez annotation</li>
 * <li>Must not have any parameters</li>
 * <li>Must be abstract</li>
 * <li>Must not throw any exceptions</li>
 * <li>Must return an instance of {@link arez.ArezContext}.</li>
 * <li>Must be accessible to the class annotated by the {@link ArezComponent} annotation.</li>
 * <li>
 *   Should not be public as not expected to be invoked outside the component. A warning will be generated but can
 *   be suppressed by the {@link SuppressWarnings} or {@link SuppressArezWarnings} annotations with a key
 *   "Arez:PublicRefMethod". This warning is also suppressed by the annotation processor if it is implementing
 *   an interface method.
 * </li>
 * <li>
 *   Should not be protected if in the class annotated with the {@link ArezComponent} annotation as the method is not
 *   expected to be invoked outside the component. A warning will be generated but can be suppressed by the
 *   {@link SuppressWarnings} or {@link SuppressArezWarnings} annotations with a key "Arez:ProtectedMethod".
 * </li>
 * </ul>
 */
@Documented
@Target( ElementType.METHOD )
public @interface ContextRef
{
}

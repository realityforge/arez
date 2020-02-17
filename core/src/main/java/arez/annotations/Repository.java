package arez.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Annotation that indicates that a repository must be generated for component.
 * A repository is the default mechanism for managing instances of classes
 * annotated with {@link ArezComponent}. The repository provides mechanisms to
 * create a component, lookup a component by id or using a query and dispose
 * the instance. These activities can be done by the user through other means
 * but the repositories provide an easy solution.
 *
 * <p>This annotation can only be added to classes that have been annotated with the
 * {@link ArezComponent} annotation.</p>
 *
 * <p>Annotating a class with this annotation will result in a class named "[MyComponent]Repository"</p>
 *
 * <p>The way to add custom queries or {@link Memoize} queries is to define an interface that
 * defines an abstract <code>self()</code> that returns the underlying repository. Using this
 * combined with default methods, you can define as many new queries and mutations as is desired.
 * The extension class then needs to be registered by setting the appropriate parameter on this
 * annotation.</p>
 *
 * <p>An example of what an extension may look like for a <code>Issue</code> component. See below:</p>
 *
 * <pre>{@code
 * public interface MyIssueRepositoryExtension
 * {
 *   default Issue findByTitle( final String title )
 *   {
 *     return self().findByQuery( issue -> issue.getTitle().equals( title ) );
 *   }
 *
 *   \@Memoize
 *   default List<Issue> findAllCompleted()
 *   {
 *     return self().findAllByQuery( Issue::isCompleted );
 *   }
 *
 *   MyIssueRepository self();
 * }
 * }</pre>
 */
@Documented
@Target( ElementType.TYPE )
public @interface Repository
{
  /**
   * Return the list of extension interfaces that the repository will implement.
   * The extension interfaces should not define any non-default methods besides
   * self().
   *
   * @return the list of extension interfaces that the repository will implement.
   */
  Class<?>[] extensions() default {};

  /**
   * Enum controlling whether dagger integration is enabled. If enabled, the annotation processor will
   * generate a dagger module named "[MyComponent]DaggerModule". If the value of this parameter is
   * {@link Feature#AUTODETECT} then dagger integration will be enabled if the {@code dagger.Module}
   * class is present on the classpath.
   *
   * @return an enum controlling whether a dagger integration is enabled.
   */
  Feature dagger() default Feature.AUTODETECT;

  /**
   * Enum controlling whether sting integration is enabled. If enabled, the annotation processor will
   * generate a sting module named "[MyComponent]Fragment". If the value of this parameter is
   * {@link Feature#AUTODETECT} then sting integration will be enabled if the {@code sting.Injector}
   * class is present on the classpath.
   *
   * @return an enum controlling whether a sting integration is enabled.
   */
  Feature sting() default Feature.AUTODETECT;

  /**
   * Indicate the strategy for attaching entities to the repository.
   *
   * @return the strategy for attaching entities to the repository.
   */
  AttachType attach() default AttachType.CREATE_ONLY;

  /**
   * Indicate the strategy for detaching entities from the repository.
   *
   * @return the strategy for detaching entities from the repository.
   */
  DetachType detach() default DetachType.DESTROY_ONLY;

  /**
   * Enum to control how entities can be attached to the repository.
   */
  enum AttachType
  {
    /**
     * Entities are created by create methods on the repository.
     * The create methods accept the same parameters as the constructors on the entities.
     */
    CREATE_ONLY,
    /**
     * Entities are created outside repository but can be manually attached to the repository.
     */
    ATTACH_ONLY,
    /**
     * Entities can be created or manually attached as required.
     */
    CREATE_OR_ATTACH
  }

  /**
   * Enum to control how entities can be detached from the repository.
   */
  enum DetachType
  {
    /**
     * Entities are disposed by detach methods on repository.
     */
    DESTROY_ONLY,
    /**
     * Entities can be detached from the repository manually and the lifecycle of the entity managed externally.
     */
    DETACH_ONLY,
    /**
     * Entities can be destroyed or manually detached as required.
     */
    DESTROY_OR_DETACH,
    /**
     * Entities can only be detached from the repository by directly disposing entity.
     */
    NONE
  }
}

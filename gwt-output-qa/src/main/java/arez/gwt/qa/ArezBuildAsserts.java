package arez.gwt.qa;

import javax.annotation.Nonnull;
import org.intellij.lang.annotations.RegExp;
import org.realityforge.gwt.symbolmap.SymbolEntryIndex;

/**
 * A collection of assertions about the expected symbols present in GWT javascript output.
 */
public final class ArezBuildAsserts
{
  private ArezBuildAsserts()
  {
  }

  /**
   * This assertion verifies that the standard inlines have occurred.
   *
   * @param index the index that contains all symbols for output target.
   */
  public static void assertStandardOutputs( @Nonnull final SymbolEntryIndex index )
  {
    assertNoTestOnlyElements( index );

    // This should be optimized out completely
    index.assertNoClassNameMatches( "arez\\.ArezConfig" );

    // This should be eliminated as it will improve the ability for GWT compiler to dead-code-eliminate
    index.assertNoMemberMatches( "arez\\.Arez", "$clinit" );

    index.assertNoMemberMatches( "arez\\.component\\.ComponentKernel", "$clinit" );

    // This should be eliminated as only used during invariant checking
    index.assertNoMemberMatches( "arez\\.ObservableValue", "preReportChanged" );

    // No repository should have equals defined
    assertEquals( index, ".*(\\.|_)Arez_[^\\.]Repository", false );
  }

  /**
   * This assertion verifies that there is no elements that we have annotated as TestOnly.
   *
   * @param index the index that contains all symbols for output target.
   */
  private static void assertNoTestOnlyElements( @Nonnull final SymbolEntryIndex index )
  {
    // TestOnly annotated methods
    index.assertNoMemberMatches( "arez\\.ArezContext", "getObserverErrorHandlerSupport" );
    index.assertNoMemberMatches( "arez\\.ArezContext", "currentNextTransactionId" );
    index.assertNoMemberMatches( "arez\\.ArezContext", "getScheduler" );
    index.assertNoMemberMatches( "arez\\.ArezContext", "setNextNodeId" );
    index.assertNoMemberMatches( "arez\\.ArezContext", "getNextNodeId" );
    index.assertNoMemberMatches( "arez\\.ArezContext", "getSchedulerLockCount" );
    index.assertNoMemberMatches( "arez\\.ArezContext", "setSchedulerLockCount" );
    index.assertNoMemberMatches( "arez\\.ArezContextHolder", "reset" );
    index.assertNoMemberMatches( "arez\\.ArezLogger", "getLogger" );
    index.assertNoClassNameMatches( "arez\\.ArezTestUtil" );
    index.assertNoMemberMatches( "arez\\.ArezZoneHolder", "getDefaultZone" );
    index.assertNoMemberMatches( "arez\\.ArezZoneHolder", "getZoneStack" );
    index.assertNoMemberMatches( "arez\\.ArezZoneHolder", "reset" );
    index.assertNoMemberMatches( "arez\\.Component", "getPreDispose" );
    index.assertNoMemberMatches( "arez\\.Component", "getPostDispose" );
    index.assertNoMemberMatches( "arez\\.ComputableValue", "setValue" );
    index.assertNoMemberMatches( "arez\\.ComputableValue", "getError" );
    index.assertNoMemberMatches( "arez\\.ComputableValue", "setError" );
    index.assertNoMemberMatches( "arez\\.ComputableValue", "setComputing" );
    index.assertNoMemberMatches( "arez\\.ObservableValue", "getWorkState" );
    index.assertNoMemberMatches( "arez\\.ObserverErrorHandlerSupport", "getObserverErrorHandlers" );
    index.assertNoMemberMatches( "arez\\.SpyImpl", "getSpyEventHandlers" );
    index.assertNoMemberMatches( "arez\\.Transaction", "getPendingDeactivations" );
    index.assertNoMemberMatches( "arez\\.Transaction", "setTransaction" );
    index.assertNoMemberMatches( "arez\\.Transaction", "isSuspended" );
    index.assertNoMemberMatches( "arez\\.Transaction", "markAsSuspended" );
    index.assertNoMemberMatches( "arez\\.Transaction", "resetSuspendedFlag" );
    index.assertNoMemberMatches( "arez\\.component\\.MemoizeCache", "getCache" );
    index.assertNoMemberMatches( "arez\\.component\\.MemoizeCache", "getNextIndex" );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enforce_transaction_type`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertShouldEnforceTransactionTypeOutputs( @Nonnull final SymbolEntryIndex index,
                                                                final boolean enabled )
  {
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.collections_properties_unmodifiable`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertCollectionPropertiesUnmodifiableOutputs( @Nonnull final SymbolEntryIndex index,
                                                                    final boolean enabled )
  {
    // Assert RepositoryUtil is eliminated once !Arez.areCollectionsPropertiesUnmodifiable() in the build
    index.assertSymbol( "arez\\.component\\.CollectionsUtil", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_observer_error_handlers`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertAreObserverErrorHandlersEnabledOutputs( @Nonnull final SymbolEntryIndex index,
                                                                   final boolean enabled )
  {
    index.assertSymbol( "arez\\.ObserverErrorHandler", enabled );
    index.assertSymbol( "arez\\.ObserverErrorHandlerSupport", enabled );
    // This is actually only elided when both Spy and ObserverErrorHandler are both disabled but mostly
    // if observer error handlers are disabled then spies are disabled so adding this assertion here
    index.assertSymbol( "arez\\.ObserverError", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_names`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertAreNamesEnabled( @Nonnull final SymbolEntryIndex index,
                                            final boolean enabled )
  {
    index.assertSymbol( "arez\\.ThrowableUtil", enabled );
    index.assertSymbol( "arez\\.Component", "_name", enabled );
    index.assertSymbol( "arez\\.component\\.ComponentKernel", "_name", enabled );
    index.assertSymbol( "arez\\.Node", "_name", enabled );
    index.assertSymbol( "arez\\.Task", "_name", enabled );
    index.assertSymbol( "arez\\.Transaction", "_name", enabled );
    index.assertSymbol( "arez\\.component\\.MemoizeCache", "_name", enabled );
    index.assertSymbol( ".*\\.Arez_.*Repository", "getRepositoryName", enabled );
    index.assertSymbol( ".*\\.Arez_.*", "toString", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_registries`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertAreRegistriesEnabled( @Nonnull final SymbolEntryIndex index,
                                                 final boolean enabled )
  {
    index.assertSymbol( "arez\\.ArezContext", "_observableValues", enabled );
    index.assertSymbol( "arez\\.ArezContext", "_computableValues", enabled );
    index.assertSymbol( "arez\\.ArezContext", "_observers", enabled );
    index.assertSymbol( "arez\\.ArezContext", "_tasks", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_spies`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertSpyOutputs( @Nonnull final SymbolEntryIndex index, final boolean enabled )
  {
    index.assertSymbol( "arez\\.spy\\..*", enabled );
    index.assertSymbol( "arez\\..*InfoImpl", enabled );
    index.assertSymbol( "arez\\.ObservableValue", "_info", enabled );
    index.assertSymbol( "arez\\.ComputableValue", "_info", enabled );
    index.assertSymbol( "arez\\.Observer", "_info", enabled );
    index.assertSymbol( "arez\\.Task", "_info", enabled );
    index.assertSymbol( "arez\\.Transaction", "_info", enabled );
    index.assertSymbol( "arez\\.Component", "_info", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_zones`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertZoneOutputs( @Nonnull final SymbolEntryIndex index, final boolean enabled )
  {
    index.assertSymbol( "arez\\.Zone", enabled );
    index.assertSymbol( "arez\\.ArezZoneHolder", enabled );
    index.assertSymbol( "arez\\.Arez", "createZone", enabled );
    index.assertSymbol( "arez\\.Arez", "activateZone", enabled );
    index.assertSymbol( "arez\\.Arez", "deactivateZone", enabled );
    index.assertSymbol( "arez\\.Arez", "currentZone", enabled );
    index.assertSymbol( "arez\\.component\\.ComponentKernel", "_context", enabled );
    index.assertSymbol( "arez\\.Node", "_context", enabled );
    index.assertSymbol( "arez\\.Component", "_context", enabled );
    index.assertSymbol( "arez\\.Node", "_context", enabled );
    index.assertSymbol( "arez\\.SchedulerLock", "_context", enabled );
    index.assertSymbol( "arez\\.SpyImpl", "_context", enabled );
    index.assertSymbol( "arez\\.Transaction", "_context", enabled );
    index.assertSymbol( "arez\\.component\\.MemoizeCache", "_context", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_property_introspection`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertPropertyIntrospectorOutputs( @Nonnull final SymbolEntryIndex index, final boolean enabled )
  {
    index.assertSymbol( "arez\\.spy\\.PropertyAccessor", enabled );
    index.assertSymbol( "arez\\.ComputableValueInfoImpl", "getValue", enabled );
    index.assertSymbol( "arez\\.ComputableValueInfoImpl", "_accessor", enabled );
    index.assertSymbol( "arez\\.ObservableValue", "getAccessor", enabled );
    index.assertSymbol( "arez\\.ObservableValue", "_mutator", enabled );
    index.assertSymbol( "arez\\.ObservableValue", "getMutator", enabled );
    index.assertSymbol( "arez\\.ObservableValue", "getObservableValue", enabled );
    index.assertSymbol( "arez\\.ObservableValueInfoImpl", "hasAccessor", enabled );
    index.assertSymbol( "arez\\.ObservableValueInfoImpl", "getValue", enabled );
    index.assertSymbol( "arez\\.ObservableValueInfoImpl", "hasMutator", enabled );
    index.assertSymbol( "arez\\.ObservableValueInfoImpl", "setValue", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_references`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertReferencesOutputs( @Nonnull final SymbolEntryIndex index, final boolean enabled )
  {
    index.assertSymbol( "arez\\.AggregateLocator", enabled );
    index.assertSymbol( "arez\\.Locator", enabled );
    index.assertSymbol( "arez\\.component\\.TypeBasedLocator", enabled );
    index.assertSymbol( "arez\\.ArezContext", "_locator", enabled );
    index.assertSymbol( "arez\\.ArezContext", "registerLocator", enabled );
    index.assertSymbol( "arez\\.ArezContext", "locator", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_native_components`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertNativeComponentOutputs( @Nonnull final SymbolEntryIndex index, final boolean enabled )
  {
    // Assert no Component cruft is enabled as !Arez.areNativeComponentsEnabled() in the build
    index.assertSymbol( "arez\\.Component.*", enabled );
    index.assertSymbol( "arez\\.component\\.ComponentKernel", "_component", enabled );

    // No repositories need their own identity if native components disabled
    assertSyntheticId( index, ".*\\.Arez_[^\\.]+Repository", false );
  }

  /**
   * Assert that a synthetic id is present or not present in classes specified by pattern.
   *
   * @param index            the index that contains all symbols for output target.
   * @param classNamePattern the pattern that determine which classes should be matched.
   * @param enabled          true if syntheticId should be present, false otherwise.
   */
  public static void assertSyntheticId( @Nonnull final SymbolEntryIndex index,
                                        @RegExp( prefix = "^", suffix = "$" ) @Nonnull final String classNamePattern,
                                        final boolean enabled )
  {
    index.assertSymbol( classNamePattern, "$$arezi$$_id", enabled );
    index.assertSymbol( classNamePattern, "$$arezi$$_nextId", enabled );
  }

  /**
   * Assert that the equals method is not present, usually applied to select generated classes.
   *
   * @param index            the index that contains all symbols for output target.
   * @param classNamePattern the pattern that determine which classes should be matched.
   * @param enabled          true if equals should be present, false otherwise.
   */
  public static void assertEquals( @Nonnull final SymbolEntryIndex index,
                                   @RegExp( prefix = "^", suffix = "$" ) @Nonnull final String classNamePattern,
                                   final boolean enabled )
  {
    index.assertSymbol( classNamePattern, "\\$equals", enabled );
  }

  /**
   * Assert normal arez outputs based on specified Arez compile time settings.
   *
   * @param index                                the index that contains all symbols for output target.
   * @param areNamesEnabled                      the value of the `arez.enable_names` setting.
   * @param areSpiesEnabled                      the value of the `arez.enable_spies` setting.
   * @param areNativeComponentsEnabled           the value of the `arez.enable_native_components` setting.
   * @param areRegistriesEnabled                 the value of the `arez.enable_registries` setting.
   * @param areObserverErrorHandlersEnabled      the value of the `arez.enable_observer_error_handlers` setting.
   * @param areZonesEnabled                      the value of the `arez.enable_zones` setting.
   * @param shouldEnforceTransactionType         the value of the `arez.enforce_transaction_type` setting.
   * @param areCollectionsPropertiesUnmodifiable the value of the `arez.collections_properties_unmodifiable` setting.
   */
  public static void assertArezOutputs( @Nonnull final SymbolEntryIndex index,
                                        final boolean areNamesEnabled,
                                        final boolean areReferencesEnabled,
                                        final boolean arePropertyIntrospectorsEnabled,
                                        final boolean areSpiesEnabled,
                                        final boolean areNativeComponentsEnabled,
                                        final boolean areRegistriesEnabled,
                                        final boolean areObserverErrorHandlersEnabled,
                                        final boolean areZonesEnabled,
                                        final boolean shouldEnforceTransactionType,
                                        final boolean areCollectionsPropertiesUnmodifiable )
  {
    assertStandardOutputs( index );
    assertAreNamesEnabled( index, areNamesEnabled );
    assertReferencesOutputs( index, areReferencesEnabled );
    assertPropertyIntrospectorOutputs( index, arePropertyIntrospectorsEnabled );
    assertSpyOutputs( index, areSpiesEnabled );
    assertNativeComponentOutputs( index, areNativeComponentsEnabled );
    assertAreRegistriesEnabled( index, areRegistriesEnabled );
    assertAreObserverErrorHandlersEnabledOutputs( index, areObserverErrorHandlersEnabled );
    assertZoneOutputs( index, areZonesEnabled );
    assertShouldEnforceTransactionTypeOutputs( index, shouldEnforceTransactionType );
    assertCollectionPropertiesUnmodifiableOutputs( index, areCollectionsPropertiesUnmodifiable );
  }
}

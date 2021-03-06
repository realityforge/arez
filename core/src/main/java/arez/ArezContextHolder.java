package arez;

import grim.annotations.OmitSymbol;
import grim.annotations.OmitType;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A utility class that contains reference to singleton context when zones are disabled.
 * This is extracted to a separate class to eliminate the <clinit> from {@link Arez} and thus
 * make it much easier for GWT to optimize out code based on build time compilation parameters.
 */
@OmitType( when = "arez.enable_zones" )
final class ArezContextHolder
{
  @Nullable
  private static ArezContext c_context = Arez.areZonesEnabled() ? null : new ArezContext( null );

  private ArezContextHolder()
  {
  }

  /**
   * Return the ArezContext.
   *
   * @return the ArezContext.
   */
  @Nonnull
  static ArezContext context()
  {
    assert null != c_context;
    return c_context;
  }

  /**
   * cleanup context.
   * This is dangerous as it may leave dangling references and should only be done in tests.
   */
  @OmitSymbol
  static void reset()
  {
    c_context = Arez.areZonesEnabled() ? null : new ArezContext( null );
  }
}

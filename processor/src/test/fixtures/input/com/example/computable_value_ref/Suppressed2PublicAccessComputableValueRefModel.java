package com.example.computable_value_ref;

import arez.ComputableValue;
import arez.annotations.ArezComponent;
import arez.annotations.ComputableValueRef;
import arez.annotations.Memoize;
import arez.annotations.SuppressArezWarnings;
import javax.annotation.Nonnull;

@ArezComponent
abstract class Suppressed2PublicAccessComputableValueRefModel
{
  @Memoize
  public long getTime()
  {
    return 0;
  }

  // This uses the CLASS retention suppression
  @SuppressArezWarnings( "Arez:PublicRefMethod" )
  @Nonnull
  @ComputableValueRef
  public abstract ComputableValue<Long> getTimeComputableValue();
}

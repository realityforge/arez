package com.example.computable_value_ref;

import arez.ComputableValue;
import arez.annotations.ArezComponent;
import arez.annotations.ComputableValueRef;
import arez.annotations.Memoize;
import javax.annotation.Nonnull;

@ArezComponent
abstract class Suppressed1ProtectedAccessComputableValueRefModel
{
  @Memoize
  public long getTime()
  {
    return 0;
  }

  // This uses the SOURCE retention suppression
  @SuppressWarnings( "Arez:ProtectedMethod" )
  @Nonnull
  @ComputableValueRef
  protected abstract ComputableValue<Long> getTimeComputableValue();
}

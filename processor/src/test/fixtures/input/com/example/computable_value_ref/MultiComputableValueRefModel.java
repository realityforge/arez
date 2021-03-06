package com.example.computable_value_ref;

import arez.ComputableValue;
import arez.annotations.ArezComponent;
import arez.annotations.ComputableValueRef;
import arez.annotations.Memoize;
import javax.annotation.Nonnull;

@ArezComponent
public abstract class MultiComputableValueRefModel
{
  @Memoize
  public long getTime()
  {
    return 0;
  }

  @Nonnull
  @ComputableValueRef
  abstract ComputableValue<Long> getTimeComputableValue();

  @ComputableValueRef( name = "time" )
  abstract ComputableValue<Long> getTimeComputableValue2();
}

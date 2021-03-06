package com.example.computable_value_ref;

import arez.ComputableValue;
import arez.annotations.ArezComponent;
import arez.annotations.ComputableValueRef;
import arez.annotations.Memoize;
import javax.annotation.Nonnull;

@ArezComponent
public abstract class BadNameModel
{
  @Memoize
  public long getTime()
  {
    return 0;
  }

  @Nonnull
  @ComputableValueRef( name = "-ace" )
  abstract ComputableValue<Long> getTimeComputableValue();
}

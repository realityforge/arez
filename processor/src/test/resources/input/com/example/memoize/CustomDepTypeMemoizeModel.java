package com.example.memoize;

import arez.annotations.ArezComponent;
import arez.annotations.DepType;
import arez.annotations.Memoize;

@ArezComponent
public abstract class CustomDepTypeMemoizeModel
{
  @Memoize( depType = DepType.AREZ_OR_NONE )
  public long count( final long time, float someOtherParameter )
  {
    return time;
  }
}

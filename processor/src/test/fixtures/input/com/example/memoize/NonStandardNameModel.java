package com.example.memoize;

import arez.annotations.ArezComponent;
import arez.annotations.Memoize;

@ArezComponent
abstract class NonStandardNameModel
{
  @Memoize
  public long $$$getTime()
  {
    return 0;
  }

  @Memoize
  public long count$$( final long time$$, float $$someOtherParameter )
  {
    return time$$;
  }
}

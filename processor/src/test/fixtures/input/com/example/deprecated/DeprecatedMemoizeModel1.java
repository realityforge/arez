package com.example.deprecated;

import arez.annotations.ArezComponent;
import arez.annotations.Memoize;

@ArezComponent
abstract class DeprecatedMemoizeModel1
{
  @Memoize
  public long getTime()
  {
    return 0;
  }
}

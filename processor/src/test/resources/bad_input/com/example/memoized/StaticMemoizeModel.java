package com.example.memoized;

import arez.annotations.ArezComponent;
import arez.annotations.Memoize;

@ArezComponent
public abstract class StaticMemoizeModel
{
  @Memoize
  public static int getField( int key )
  {
    return 0;
  }
}

package com.example.observable;

import arez.annotations.ArezComponent;
import arez.annotations.Observable;

@ArezComponent
public abstract class AbstractGetterNoSetterModel
{
  private long _field;

  @Observable( expectSetter = false )
  public abstract long getField();
}

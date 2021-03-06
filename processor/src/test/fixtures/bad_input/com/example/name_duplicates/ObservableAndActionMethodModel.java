package com.example.name_duplicates;

import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Observable;

@ArezComponent
public abstract class ObservableAndActionMethodModel
{
  private long _field;

  @Action
  @Observable
  public long getField()
  {
    return _field;
  }

  @Observable
  public void setField( final long field )
  {
    _field = field;
  }
}

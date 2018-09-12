package com.example.name_duplicates;

import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Observed;

@ArezComponent
public abstract class ActionAndObservedMethodModel
{
  @Observed
  @Action
  public void doStuff()
  {
  }
}
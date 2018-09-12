package com.example.observed;

import arez.annotations.ArezComponent;
import arez.annotations.Observed;

@ArezComponent
public abstract class ObserveLowerPriorityObservedModel
{
  @Observed( observeLowerPriorityDependencies = true )
  protected void doStuff()
  {
  }
}

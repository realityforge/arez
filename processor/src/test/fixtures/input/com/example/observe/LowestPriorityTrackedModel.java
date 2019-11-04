package com.example.observe;

import arez.annotations.ArezComponent;
import arez.annotations.Executor;
import arez.annotations.Observe;
import arez.annotations.OnDepsChange;
import arez.annotations.Priority;

@ArezComponent
public abstract class LowestPriorityTrackedModel
{
  @Observe( executor = Executor.EXTERNAL, priority = Priority.LOWEST )
  public void render( final long time, float someOtherParameter )
  {
  }

  @OnDepsChange
  public void onRenderDepsChange()
  {
  }
}
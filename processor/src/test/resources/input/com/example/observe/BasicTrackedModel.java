package com.example.observe;

import arez.annotations.ArezComponent;
import arez.annotations.Executor;
import arez.annotations.Observe;
import arez.annotations.OnDepsChanged;

@ArezComponent
public abstract class BasicTrackedModel
{
  @Observe( executor = Executor.APPLICATION )
  public void render( final long time, float someOtherParameter )
  {
  }

  @OnDepsChanged
  public void onRenderDepsChanged()
  {
  }
}
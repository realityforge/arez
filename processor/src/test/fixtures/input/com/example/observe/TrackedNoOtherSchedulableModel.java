package com.example.observe;

import arez.annotations.ArezComponent;
import arez.annotations.Executor;
import arez.annotations.Observe;
import arez.annotations.OnDepsChange;

@ArezComponent
abstract class TrackedNoOtherSchedulableModel
{
  @Observe( executor = Executor.EXTERNAL )
  public void render1()
  {
  }

  @OnDepsChange
  void onRender1DepsChange()
  {
  }
}

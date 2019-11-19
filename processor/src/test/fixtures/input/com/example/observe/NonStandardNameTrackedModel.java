package com.example.observe;

import arez.annotations.ArezComponent;
import arez.annotations.Executor;
import arez.annotations.Observe;
import arez.annotations.OnDepsChange;

@ArezComponent
abstract class NonStandardNameTrackedModel
{
  @Observe( executor = Executor.EXTERNAL, name = "render" )
  public void ren$$$der( final long $$time, float $$someOtherParameter )
  {
  }

  @OnDepsChange( name = "render" )
  public void onRenderDepsCha$$$$$$nge()
  {
  }
}

package com.example.observe;

import arez.annotations.ArezComponent;
import arez.annotations.Executor;
import arez.annotations.Observe;
import arez.annotations.OnDepsChanged;

@ArezComponent
public abstract class NoReportParametersModel
{
  @Observe( executor = Executor.APPLICATION, reportParameters = false )
  public void render( final long time, float someOtherParameter )
  {
  }

  @OnDepsChanged
  public void onRenderDepsChanged()
  {
  }
}
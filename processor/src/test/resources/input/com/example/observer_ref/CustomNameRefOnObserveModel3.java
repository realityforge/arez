package com.example.observer_ref;

import arez.Observer;
import arez.annotations.ArezComponent;
import arez.annotations.Executor;
import arez.annotations.Observe;
import arez.annotations.ObserverRef;
import arez.annotations.OnDepsChange;

@ArezComponent
public abstract class CustomNameRefOnObserveModel3
{
  @Observe( executor = Executor.APPLICATION, name = "render" )
  public void $$$rende$$$r()
  {
  }

  @OnDepsChange( name = "render" )
  public void onRenderDepsChan$$$$$ge()
  {
  }

  @ObserverRef( name = "render" )
  abstract Observer observe$$$r();
}
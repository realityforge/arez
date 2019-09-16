package com.example.component;

import arez.annotations.ArezComponent;
import arez.annotations.Observable;

@ArezComponent
public abstract class UnmanagedObservableComponentReference
{
  @Observable
  abstract MyComponent getMyComponent();

  abstract void setMyComponent( MyComponent component );

  @ArezComponent( allowEmpty = true )
  public static abstract class MyComponent
  {
  }
}

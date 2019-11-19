package com.example.cascade_dispose;

import arez.Disposable;
import arez.annotations.ArezComponent;
import arez.annotations.CascadeDispose;

@ArezComponent
abstract class DisposableCascadeDisposeMethodModel
{
  @CascadeDispose
  protected final Disposable myElement()
  {
    return null;
  }
}

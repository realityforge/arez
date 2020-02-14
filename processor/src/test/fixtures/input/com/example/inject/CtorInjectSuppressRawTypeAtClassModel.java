package com.example.inject;

import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.InjectMode;
import java.util.concurrent.Callable;
import javax.annotation.Nonnull;
import javax.inject.Singleton;

@SuppressWarnings( "rawtypes" )
@Singleton
@ArezComponent( inject = InjectMode.PROVIDE )
public abstract class CtorInjectSuppressRawTypeAtClassModel
{
  CtorInjectSuppressRawTypeAtClassModel( @Nonnull final Callable action )
  {
  }

  @Action
  public void myActionStuff()
  {
  }
}

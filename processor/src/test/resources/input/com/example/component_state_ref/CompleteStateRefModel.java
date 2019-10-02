package com.example.component_state_ref;

import arez.annotations.ArezComponent;
import arez.annotations.ComponentStateRef;
import arez.annotations.State;

@ArezComponent( allowEmpty = true )
public abstract class CompleteStateRefModel
{
  @ComponentStateRef( State.COMPLETE )
  protected abstract boolean isComplete();
}

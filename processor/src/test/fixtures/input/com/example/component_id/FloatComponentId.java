package com.example.component_id;

import arez.annotations.ArezComponent;
import arez.annotations.ComponentId;

@ArezComponent( allowEmpty = true )
abstract class FloatComponentId
{
  @ComponentId
  public final float getId()
  {
    return 0;
  }
}

package com.example.component_id;

import arez.annotations.ArezComponent;
import arez.annotations.ComponentId;

@ArezComponent( allowEmpty = true )
abstract class CharComponentId
{
  @ComponentId
  public final char getId()
  {
    return 'A';
  }
}

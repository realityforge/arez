package com.example.computed;

import org.realityforge.arez.annotations.ArezComponent;
import org.realityforge.arez.annotations.Computed;

@ArezComponent
public class TypeParametersModel
{
  @Computed
  public <T extends Integer> T getTime()
  {
    return null;
  }
}

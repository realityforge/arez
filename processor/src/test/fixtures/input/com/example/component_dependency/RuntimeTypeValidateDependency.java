package com.example.component_dependency;

import arez.annotations.ArezComponent;
import arez.annotations.ComponentDependency;

@ArezComponent
abstract class RuntimeTypeValidateDependency
{
  @ComponentDependency( validateTypeAtRuntime = true )
  public final Object getValue()
  {
    return null;
  }
}

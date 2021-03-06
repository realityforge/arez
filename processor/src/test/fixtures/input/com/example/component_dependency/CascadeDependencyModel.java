package com.example.component_dependency;

import arez.annotations.ArezComponent;
import arez.annotations.ComponentDependency;
import arez.component.DisposeNotifier;

@ArezComponent
abstract class CascadeDependencyModel
{
  @ComponentDependency( action = ComponentDependency.Action.CASCADE )
  public DisposeNotifier getTime()
  {
    return null;
  }
}

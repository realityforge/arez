package com.example.component_dependency;

import arez.annotations.ArezComponent;
import arez.annotations.ComponentDependency;
import arez.component.DisposeNotifier;

@ArezComponent
public abstract class NonStandardNameDependencyModel
{
  @ComponentDependency
  public final DisposeNotifier $$MYDEP$$()
  {
    return null;
  }
}

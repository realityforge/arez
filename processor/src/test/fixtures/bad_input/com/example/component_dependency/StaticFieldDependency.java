package com.example.component_dependency;

import arez.annotations.ArezComponent;
import arez.annotations.ComponentDependency;
import arez.component.DisposeNotifier;

@ArezComponent( allowEmpty = true )
public abstract class StaticFieldDependency
{
  @ComponentDependency
  static final DisposeNotifier time = null;
}

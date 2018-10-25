package com.example.component_dependency;

import arez.annotations.ArezComponent;
import arez.annotations.ComponentDependency;

@ArezComponent( allowEmpty = true )
public abstract class PrimitiveFieldDependency
{
  @ComponentDependency
  final int time = 0;
}

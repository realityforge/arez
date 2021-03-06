package com.example.component_type_name_ref;

import arez.annotations.ArezComponent;
import arez.annotations.ComponentTypeNameRef;

@ArezComponent( allowEmpty = true )
public abstract class ParameterizedComponentTypeNameRefModel
{
  @ComponentTypeNameRef
  abstract String getTypeName( int i );
}

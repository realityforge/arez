package com.example.component_type_name_ref;

import arez.annotations.ArezComponent;
import arez.annotations.ComponentTypeNameRef;

@ArezComponent( allowEmpty = true )
abstract class PublicAccessComponentTypeNameRefModel
{
  @ComponentTypeNameRef
  public abstract String getTypeName();
}

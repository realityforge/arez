package com.example.reference;

import arez.annotations.ArezComponent;
import arez.annotations.Reference;
import arez.annotations.ReferenceId;

@ArezComponent
abstract class NonJavabeanNameReferenceModel
{
  @Reference
  abstract MyEntity myEntity();

  @ReferenceId
  int myEntityId()
  {
    return 0;
  }

  static class MyEntity
  {
  }
}

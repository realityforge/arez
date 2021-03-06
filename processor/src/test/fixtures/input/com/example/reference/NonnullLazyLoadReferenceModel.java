package com.example.reference;

import arez.annotations.ArezComponent;
import arez.annotations.LinkType;
import arez.annotations.Reference;
import arez.annotations.ReferenceId;
import javax.annotation.Nonnull;

@ArezComponent
abstract class NonnullLazyLoadReferenceModel
{
  @Reference( load = LinkType.LAZY )
  abstract MyEntity getMyEntity();

  @ReferenceId
  @Nonnull
  Integer getMyEntityId()
  {
    return 0;
  }

  static class MyEntity
  {
  }
}

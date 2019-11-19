package com.example.repository;

import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.component.internal.AbstractRepository;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.inject.Singleton;

@Generated("arez.processor.ArezProcessor")
@ArezComponent
@Singleton
public abstract class RepositoryWithMultipleInitializersModelRepository extends AbstractRepository<Integer, RepositoryWithMultipleInitializersModel, RepositoryWithMultipleInitializersModelRepository> {
  RepositoryWithMultipleInitializersModelRepository() {
  }

  @Nonnull
  static RepositoryWithMultipleInitializersModelRepository newRepository() {
    return new Arez_RepositoryWithMultipleInitializersModelRepository();
  }

  @Action(
      name = "create"
  )
  @Nonnull
  RepositoryWithMultipleInitializersModel create(final long time, final long value) {
    final Arez_RepositoryWithMultipleInitializersModel entity = new Arez_RepositoryWithMultipleInitializersModel(time,value);
    attach( entity );
    return entity;
  }

  @Override
  @Action(
      reportParameters = false
  )
  protected void destroy(@Nonnull final RepositoryWithMultipleInitializersModel entity) {
    super.destroy( entity );
  }
}

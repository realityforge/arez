package com.example.repository;

import dagger.Binds;
import dagger.Module;
import javax.annotation.Generated;
import javax.inject.Singleton;

@SuppressWarnings("rawtypes")
@Generated("arez.processor.ArezProcessor")
@Module
public interface RepositoryWithRawTypeRepositoryDaggerModule {
  @Binds
  @Singleton
  RepositoryWithRawTypeRepository bindComponent(Arez_RepositoryWithRawTypeRepository component);
}
package com.example.inject;

import dagger.Binds;
import dagger.Module;
import javax.annotation.Generated;
import javax.inject.Singleton;

@Generated("arez.processor.ArezProcessor")
@Module
public interface CtorInjectWithPostConstructModelDaggerModule {
  @Binds
  @Singleton
  CtorInjectWithPostConstructModel bindComponent(Arez_CtorInjectWithPostConstructModel component);
}

package com.example.computed;

import java.util.Objects;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import org.realityforge.arez.Arez;
import org.realityforge.arez.ArezContext;
import org.realityforge.arez.ComputedValue;
import org.realityforge.arez.Disposable;

@Generated("org.realityforge.arez.processor.ArezProcessor")
@SuppressWarnings("unchecked")
public final class Arez_ComputedWithNameVariationsModel extends ComputedWithNameVariationsModel implements Disposable {
  private static volatile long $$arez$$_nextId;

  private final long $$arez$$_id;

  private boolean $$arez$$_disposed;

  @Nonnull
  private final ArezContext $$arez$$_context;

  @Nonnull
  private final ComputedValue<String> $$arez$$_helper;

  @Nonnull
  private final ComputedValue<Boolean> $$arez$$_ready;

  @Nonnull
  private final ComputedValue<String> $$arez$$_foo;

  @Nonnull
  private final ComputedValue<Long> $$arez$$_time;

  public Arez_ComputedWithNameVariationsModel() {
    super();
    this.$$arez$$_context = Arez.context();
    this.$$arez$$_id = $$arez$$_nextId++;
    this.$$arez$$_helper = this.$$arez$$_context.createComputedValue( this.$$arez$$_context.areNamesEnabled() ? $$arez$$_name() + ".helper" : null, super::helper, Objects::equals, null, null, null, null );
    this.$$arez$$_ready = this.$$arez$$_context.createComputedValue( this.$$arez$$_context.areNamesEnabled() ? $$arez$$_name() + ".ready" : null, super::isReady, Objects::equals, null, null, null, null );
    this.$$arez$$_foo = this.$$arez$$_context.createComputedValue( this.$$arez$$_context.areNamesEnabled() ? $$arez$$_name() + ".foo" : null, super::myFooHelperMethod, Objects::equals, null, null, null, null );
    this.$$arez$$_time = this.$$arez$$_context.createComputedValue( this.$$arez$$_context.areNamesEnabled() ? $$arez$$_name() + ".time" : null, super::getTime, Objects::equals, null, null, null, null );
  }

  final long $$arez$$_id() {
    return $$arez$$_id;
  }

  String $$arez$$_name() {
    return "ComputedWithNameVariationsModel." + $$arez$$_id();
  }

  @Override
  public boolean isDisposed() {
    return $$arez$$_disposed;
  }

  @Override
  public void dispose() {
    if ( !isDisposed() ) {
      $$arez$$_disposed = true;
      $$arez$$_helper.dispose();
      $$arez$$_ready.dispose();
      $$arez$$_foo.dispose();
      $$arez$$_time.dispose();
    }
  }

  @Override
  public String helper() {
    return this.$$arez$$_helper.get();
  }

  @Override
  public boolean isReady() {
    return this.$$arez$$_ready.get();
  }

  @Override
  public String myFooHelperMethod() {
    return this.$$arez$$_foo.get();
  }

  @Override
  public long getTime() {
    return this.$$arez$$_time.get();
  }
}

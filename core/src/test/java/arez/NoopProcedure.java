package arez;

final class NoopProcedure
  implements Procedure
{
  @Override
  public void call()
  {
    AbstractTest.observeADependency();
  }
}

package arez.component;

import arez.AbstractTest;
import javax.annotation.Nonnull;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class IdentifiableTest
  extends AbstractTest
{
  static class TestElement
    implements Identifiable<String>
  {
    @Nonnull
    @Override
    public String getArezId()
    {
      return "X";
    }
  }

  @Test
  public void identifiableElement()
  {
    final TestElement element = new TestElement();
    assertEquals( Identifiable.getArezId( element ), "X" );
    assertEquals( Identifiable.<String>asIdentifiable( element ), element );
  }

  @Test
  public void nonIdentifiableElement()
  {
    final Object element = new Object();
    assertNull( Identifiable.getArezId( element ) );
    assertInvariantFailure( () -> Identifiable.asIdentifiable( element ),
                            "Arez-0158: Object passed to asIdentifiable does not " +
                            "implement Identifiable. Object: " + element );
  }
}

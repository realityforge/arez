package arez.spy;

import arez.AbstractTest;
import arez.Arez;
import arez.ArezContext;
import arez.Observer;
import arez.ObserverError;
import java.util.HashMap;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class ObserverErrorEventTest
  extends AbstractTest
{
  @Test
  public void basicOperation()
  {
    final ArezContext context = Arez.context();
    final String name = "Foo@1";
    final Observer observer = context.tracker( name, ValueUtil::randomString );
    final ObserverInfo info = context.getSpy().asObserverInfo( observer );
    final ObserverError error = ObserverError.REACTION_ERROR;
    final Throwable throwable = new Throwable( "Blah" );
    final ObserverErrorEvent event = new ObserverErrorEvent( info, error, throwable );

    assertEquals( event.getObserver(), info );
    assertEquals( event.getError(), error );
    assertEquals( event.getThrowable(), throwable );

    final HashMap<String, Object> data = new HashMap<>();
    event.toMap( data );

    assertEquals( data.get( "type" ), "ObserverError" );
    assertEquals( data.get( "name" ), name );
    assertEquals( data.get( "errorType" ), "REACTION_ERROR" );
    assertEquals( data.get( "message" ), "Blah" );
    assertEquals( data.size(), 4 );
  }
}

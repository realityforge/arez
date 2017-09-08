import org.realityforge.arez.annotations.Container;
import org.realityforge.arez.annotations.Observable;

@SuppressWarnings( "WeakerAccess" )
@Container
public class ObservableWithExceptingCtorModel
{
  public ObservableWithExceptingCtorModel()
    throws Exception
  {
  }

  @Observable
  public long getTime()
  {
    return 0;
  }

  @Observable
  public void setTime( final long time )
  {
  }
}

package org.realityforge.arez.doc.examples.step5;

import org.realityforge.arez.annotations.Action;
import org.realityforge.arez.annotations.ArezComponent;
import org.realityforge.arez.annotations.Autorun;
import org.realityforge.arez.annotations.Computed;
import org.realityforge.arez.annotations.Observable;
import org.realityforge.arez.annotations.OnDepsChanged;
import org.realityforge.arez.annotations.Track;

@ArezComponent
public class TrainTicket
{
  private int _remainingRides;

  public static TrainTicket create( int remainingRides )
  {
    return new Arez_TrainTicket( remainingRides );
  }

  TrainTicket( int remainingRides )
  {
    _remainingRides = remainingRides;
  }

  @Observable
  public int getRemainingRides()
  {
    return _remainingRides;
  }

  public void setRemainingRides( int remainingRides )
  {
    _remainingRides = remainingRides;
  }

  @Action
  public void rideTrain()
  {
    setRemainingRides( getRemainingRides() - 1 );
  }

  @Track
  public String render()
  {
    return "<table class='ticket'>" +
           "  <tr>" +
           "    <th>Remaining Rides</th>" +
           "    <td>" + getRemainingRides() + "</td>" +
           "  </tr>" +
           "</table>";
  }

  @OnDepsChanged
  void onRenderDepsChanged()
  {
    Renderer.scheduleRender( this );
  }

  @Computed
  public boolean isTicketExpired()
  {
    return 0 == getRemainingRides();
  }

  @Autorun
  public void notifyUserWhenTicketExpires()
  {
    if ( isTicketExpired() )
    {
      NotifyTool.notifyUserTicketExpired( this );
    }
  }
}

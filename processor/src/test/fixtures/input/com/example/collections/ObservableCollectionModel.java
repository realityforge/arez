package com.example.collections;

import arez.annotations.ArezComponent;
import arez.annotations.Observable;
import java.util.Collection;

@ArezComponent
abstract class ObservableCollectionModel
{
  @Observable
  public Collection<String> getMyValue()
  {
    return null;
  }

  public void setMyValue( final Collection<String> value )
  {
  }
}

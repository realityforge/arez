package arez.doc.examples.at_observable;

import arez.ObservableValue;
import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Observable;
import arez.annotations.ObservableValueRef;
import javax.annotation.Nonnull;

@ArezComponent
public abstract class ReactComponent
{
  @Observable( expectSetter = false )
  Props props()
  {
    //Return the native props object here.
    //DOC ELIDE START
    return null;
    //DOC ELIDE END
  }

  //This will be overridden and implemented in the Arez subclass
  @ObservableValueRef
  abstract ObservableValue<Props> getPropsObservableValue();

  // This method is wrapped in an Action to ensure change is propagated
  // correctly in arez system.
  @Action
  void reportPropsChanged()
  {
    getPropsObservableValue().reportChanged();
  }

  // This method is invoked by the React runtime
  void componentWillReceiveProps( @Nonnull final Props nextProps )
  {
    reportPropsChanged();
    //DOC ELIDE START
    //DOC ELIDE END
  }

  //DOC ELIDE START
  static class Props
  {
  }
  //DOC ELIDE END
}

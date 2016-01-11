package io.fabric.sdk.android.services.events;

public class DisabledEventsStrategy<T>
  implements EventsStrategy<T>
{
  public void cancelTimeBasedFileRollOver() {}
  
  public void deleteAllEvents() {}
  
  public FilesSender getFilesSender()
  {
    return null;
  }
  
  public void recordEvent(T paramT) {}
  
  public boolean rollFileOver()
  {
    return false;
  }
  
  public void scheduleTimeBasedRollOverIfNeeded() {}
  
  public void sendEvents() {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/events/DisabledEventsStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
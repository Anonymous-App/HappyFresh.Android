package io.fabric.sdk.android.services.events;

public abstract interface EventsStrategy<T>
  extends FileRollOverManager, EventsManager<T>
{
  public abstract FilesSender getFilesSender();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/events/EventsStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
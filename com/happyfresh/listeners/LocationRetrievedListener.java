package com.happyfresh.listeners;

import android.location.Location;

public abstract interface LocationRetrievedListener
{
  public abstract void onLocationNotRetrieved();
  
  public abstract void onLocationRetrieved(Location paramLocation);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/listeners/LocationRetrievedListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
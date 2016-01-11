package com.happyfresh.listeners;

public abstract interface ApplicationStateChangedListener
{
  public abstract void onEnterBackground();
  
  public abstract void onEnterForeground();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/listeners/ApplicationStateChangedListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
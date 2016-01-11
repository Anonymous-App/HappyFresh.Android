package com.appsee;

public abstract interface AppseeListener
{
  public abstract void onAppseeScreenDetected(AppseeScreenInfo paramAppseeScreenInfo);
  
  public abstract void onAppseeSessionEnded(AppseeSessionEndInfo paramAppseeSessionEndInfo);
  
  public abstract void onAppseeSessionStarted(AppseeSessionStartInfo paramAppseeSessionStartInfo);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/AppseeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.activeandroid.app;

import com.activeandroid.ActiveAndroid;

public class Application
  extends android.app.Application
{
  public void onCreate()
  {
    super.onCreate();
    ActiveAndroid.initialize(this);
  }
  
  public void onTerminate()
  {
    super.onTerminate();
    ActiveAndroid.dispose();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/app/Application.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
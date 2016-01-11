package com.ad4screen.sdk;

import android.app.Application;
import android.content.res.Configuration;
import com.ad4screen.sdk.common.annotations.API;

@API
public class A4SApplication
  extends Application
{
  @API
  public void onApplicationConfigurationChanged(Configuration paramConfiguration) {}
  
  @API
  public void onApplicationCreate() {}
  
  @API
  public void onApplicationLowMemory() {}
  
  @API
  public void onApplicationTerminate() {}
  
  public final void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (!a.isInA4SProcess(this)) {
      onApplicationConfigurationChanged(paramConfiguration);
    }
  }
  
  public final void onCreate()
  {
    if (!a.isInA4SProcess(this)) {
      onApplicationCreate();
    }
  }
  
  public final void onLowMemory()
  {
    if (!a.isInA4SProcess(this)) {
      onApplicationLowMemory();
    }
  }
  
  public final void onTerminate()
  {
    if (!a.isInA4SProcess(this)) {
      onApplicationTerminate();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/A4SApplication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
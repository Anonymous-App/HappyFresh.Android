package com.parse;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import java.util.concurrent.atomic.AtomicInteger;

class ParseNotificationManager
{
  public static final String TAG = "com.parse.ParseNotificationManager";
  private final AtomicInteger notificationCount = new AtomicInteger(0);
  private volatile boolean shouldShowNotifications = true;
  
  public static ParseNotificationManager getInstance()
  {
    return Singleton.INSTANCE;
  }
  
  public int getNotificationCount()
  {
    return this.notificationCount.get();
  }
  
  public void setShouldShowNotifications(boolean paramBoolean)
  {
    this.shouldShowNotifications = paramBoolean;
  }
  
  public void showNotification(Context paramContext, Notification paramNotification)
  {
    int i;
    if ((paramContext != null) && (paramNotification != null))
    {
      this.notificationCount.incrementAndGet();
      if (this.shouldShowNotifications)
      {
        paramContext = (NotificationManager)paramContext.getSystemService("notification");
        i = (int)System.currentTimeMillis();
      }
    }
    try
    {
      paramContext.notify(i, paramNotification);
      return;
    }
    catch (SecurityException localSecurityException)
    {
      paramNotification.defaults = 5;
      paramContext.notify(i, paramNotification);
    }
  }
  
  public static class Singleton
  {
    private static final ParseNotificationManager INSTANCE = new ParseNotificationManager();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseNotificationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.parse;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import bolts.Capture;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class LocationNotifier
{
  private static Location fakeLocation = null;
  
  static Task<Location> getCurrentLocationAsync(final Context paramContext, long paramLong, Criteria paramCriteria)
  {
    final Task.TaskCompletionSource localTaskCompletionSource = Task.create();
    Capture localCapture = new Capture();
    paramContext = (LocationManager)paramContext.getSystemService("location");
    final LocationListener local1 = new LocationListener()
    {
      public void onLocationChanged(Location paramAnonymousLocation)
      {
        if (paramAnonymousLocation == null) {
          return;
        }
        ((ScheduledFuture)this.val$timeoutFuture.get()).cancel(true);
        localTaskCompletionSource.trySetResult(paramAnonymousLocation);
        paramContext.removeUpdates(this);
      }
      
      public void onProviderDisabled(String paramAnonymousString) {}
      
      public void onProviderEnabled(String paramAnonymousString) {}
      
      public void onStatusChanged(String paramAnonymousString, int paramAnonymousInt, Bundle paramAnonymousBundle) {}
    };
    localCapture.set(ParseExecutors.scheduled().schedule(new Runnable()
    {
      public void run()
      {
        this.val$tcs.trySetError(new ParseException(124, "Location fetch timed out."));
        paramContext.removeUpdates(local1);
      }
    }, paramLong, TimeUnit.MILLISECONDS));
    paramCriteria = paramContext.getBestProvider(paramCriteria, true);
    if (paramCriteria != null) {
      paramContext.requestLocationUpdates(paramCriteria, 0L, 0.0F, local1);
    }
    if (fakeLocation != null) {
      local1.onLocationChanged(fakeLocation);
    }
    return localTaskCompletionSource.getTask();
  }
  
  static void setFakeLocation(Location paramLocation)
  {
    fakeLocation = paramLocation;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/LocationNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
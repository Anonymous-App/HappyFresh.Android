package com.ad4screen.sdk.systems;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.plugins.b;
import com.ad4screen.sdk.plugins.LocationPlugin;

public final class a
  implements g
{
  private static a g;
  private boolean a = false;
  private final Context b;
  private Location c;
  private boolean d;
  private LocationPlugin e;
  private final LocationListener f = new LocationListener()
  {
    public void onLocationChanged(Location paramAnonymousLocation)
    {
      if (paramAnonymousLocation == null) {
        return;
      }
      Log.debug("Geolocation|Location ping from provider '" + paramAnonymousLocation.getProvider() + "' -> lat:" + paramAnonymousLocation.getLatitude() + " long:" + paramAnonymousLocation.getLongitude() + " (accuracy: " + paramAnonymousLocation.getAccuracy() + "m)");
      a.a(a.this, paramAnonymousLocation);
    }
    
    public void onProviderDisabled(String paramAnonymousString)
    {
      Log.debug("Geolocation|Provider '" + paramAnonymousString + "' was disabled by user");
    }
    
    public void onProviderEnabled(String paramAnonymousString)
    {
      Log.debug("Geolocation|Provider '" + paramAnonymousString + "' was enabled by user");
    }
    
    public void onStatusChanged(String paramAnonymousString, int paramAnonymousInt, Bundle paramAnonymousBundle)
    {
      switch (paramAnonymousInt)
      {
      default: 
        return;
      case 2: 
        Log.debug("Geolocation|Provider '" + paramAnonymousString + "' is now available");
        return;
      case 0: 
        Log.debug("Geolocation|Provider '" + paramAnonymousString + "' is out of service");
        return;
      }
      Log.debug("Geolocation|Provider '" + paramAnonymousString + "' is temporary unavailable");
    }
  };
  
  private a(Context paramContext)
  {
    this.b = paramContext;
    this.d = false;
  }
  
  public static a a(Context paramContext)
  {
    try
    {
      if (g == null) {
        g = new a(paramContext.getApplicationContext());
      }
      paramContext = g;
      return paramContext;
    }
    finally {}
  }
  
  private boolean a(Location paramLocation1, Location paramLocation2)
  {
    if (paramLocation1 == null) {
      return false;
    }
    if (paramLocation2 == null)
    {
      Log.debug("Geolocation|Previous location was unknown");
      return true;
    }
    long l = paramLocation1.getTime() - paramLocation2.getTime();
    int j;
    int k;
    if (l > 180000L)
    {
      j = 1;
      if (l >= -180000L) {
        break label79;
      }
      k = 1;
      label52:
      if (l <= 0L) {
        break label85;
      }
    }
    label79:
    label85:
    for (int i = 1;; i = 0)
    {
      if (j == 0) {
        break label90;
      }
      Log.debug("Geolocation|New location is significantly newer");
      return true;
      j = 0;
      break;
      k = 0;
      break label52;
    }
    label90:
    if (k != 0)
    {
      Log.debug("Geolocation|New location is significantly older");
      return false;
    }
    int m = (int)(paramLocation1.getAccuracy() - paramLocation2.getAccuracy());
    if (m > 0)
    {
      j = 1;
      if (m >= 0) {
        break label173;
      }
      k = 1;
      label130:
      if (m <= 200) {
        break label179;
      }
    }
    boolean bool;
    label173:
    label179:
    for (m = 1;; m = 0)
    {
      bool = a(paramLocation1.getProvider(), paramLocation2.getProvider());
      if (k == 0) {
        break label185;
      }
      Log.debug("Geolocation|New location is more accurate");
      return true;
      j = 0;
      break;
      k = 0;
      break label130;
    }
    label185:
    if ((i != 0) && (j == 0))
    {
      Log.debug("Geolocation|New location is more accurate");
      return true;
    }
    if ((i != 0) && (m == 0) && (bool))
    {
      Log.debug("Geolocation|New location is newer despite less accurate");
      return true;
    }
    Log.debug("Geolocation|Location is inaccurate");
    return false;
  }
  
  private boolean a(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      return paramString2 == null;
    }
    return paramString1.equals(paramString2);
  }
  
  private void b(Location paramLocation)
  {
    if (a(paramLocation, this.c))
    {
      Log.debug("Geolocation|Current location updated to lat:" + paramLocation.getLatitude() + " long:" + paramLocation.getLongitude() + " (accuracy: " + paramLocation.getAccuracy() + "m)");
      this.c = paramLocation;
      f.a().a(new a());
    }
  }
  
  public void a(Location paramLocation)
  {
    this.c = paramLocation;
  }
  
  public boolean a()
  {
    return this.d;
  }
  
  public void b()
  {
    Log.debug("Geolocation|Starting location updates");
    this.e = b.f();
    if (this.e == null) {
      this.a = true;
    }
    if ((!this.a) && (this.e.getPluginVersion() != 1))
    {
      Log.error("Geolocation|Google Play Services Location Plugin version is too old ! Please update it");
      this.a = true;
    }
    if (!this.a)
    {
      Log.debug("Geolocation|Google Play Services Location Plugin Found.");
      if (this.e.connect(this.b, 180000L, 60000L, new A4S.Callback()
      {
        public void a(Location paramAnonymousLocation)
        {
          a.a(a.this).onLocationChanged(paramAnonymousLocation);
        }
        
        public void onError(int paramAnonymousInt, String paramAnonymousString) {}
      })) {
        break label121;
      }
    }
    label121:
    for (boolean bool = true;; bool = false)
    {
      this.a = bool;
      if (this.a) {
        break;
      }
      Log.debug("Geolocation|Connected to Google Play Services Location.");
      this.d = true;
      return;
    }
    try
    {
      LocationManager localLocationManager = (LocationManager)this.b.getSystemService("location");
      b(localLocationManager.getLastKnownLocation("network"));
      b(localLocationManager.getLastKnownLocation("gps"));
      localLocationManager.requestLocationUpdates("network", 180000L, 25.0F, this.f);
      localLocationManager.requestLocationUpdates("gps", 180000L, 25.0F, this.f);
      this.d = true;
      return;
    }
    catch (Exception localException)
    {
      Log.error("Geolocation|Could not start location updates", localException);
      c();
    }
  }
  
  public void c()
  {
    if ((!this.a) && (this.e != null))
    {
      Log.debug("Geolocation|Stopping location updates and disconnecting to Google Play Services Location");
      this.e.disconnect();
    }
    for (;;)
    {
      this.d = false;
      return;
      if (this.b != null) {
        try
        {
          Log.debug("Geolocation|Stopping location updates");
          ((LocationManager)this.b.getSystemService("location")).removeUpdates(this.f);
        }
        catch (Exception localException)
        {
          Log.error("Geolocation|Could not stop location updates", localException);
        }
      }
    }
  }
  
  public Location d()
  {
    return this.c;
  }
  
  public static final class a
    implements f.a<a.b>
  {
    public void a(a.b paramb)
    {
      paramb.a();
    }
  }
  
  public static abstract interface b
  {
    public abstract void a();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/systems/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import com.google.android.gms.location.internal.ParcelableGeofence;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeofencingEvent
{
  private final int zzBv;
  private final int zzaxG;
  private final List<Geofence> zzaxH;
  private final Location zzaxI;
  
  private GeofencingEvent(int paramInt1, int paramInt2, List<Geofence> paramList, Location paramLocation)
  {
    this.zzBv = paramInt1;
    this.zzaxG = paramInt2;
    this.zzaxH = paramList;
    this.zzaxI = paramLocation;
  }
  
  public static GeofencingEvent fromIntent(Intent paramIntent)
  {
    if (paramIntent == null) {
      return null;
    }
    return new GeofencingEvent(paramIntent.getIntExtra("gms_error_code", -1), zzs(paramIntent), zzt(paramIntent), (Location)paramIntent.getParcelableExtra("com.google.android.location.intent.extra.triggering_location"));
  }
  
  private static int zzs(Intent paramIntent)
  {
    int i = paramIntent.getIntExtra("com.google.android.location.intent.extra.transition", -1);
    if (i == -1) {}
    while ((i != 1) && (i != 2) && (i != 4)) {
      return -1;
    }
    return i;
  }
  
  private static List<Geofence> zzt(Intent paramIntent)
  {
    Object localObject = (ArrayList)paramIntent.getSerializableExtra("com.google.android.location.intent.extra.geofence_list");
    if (localObject == null) {
      return null;
    }
    paramIntent = new ArrayList(((ArrayList)localObject).size());
    localObject = ((ArrayList)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      paramIntent.add(ParcelableGeofence.zzn((byte[])((Iterator)localObject).next()));
    }
    return paramIntent;
  }
  
  public int getErrorCode()
  {
    return this.zzBv;
  }
  
  public int getGeofenceTransition()
  {
    return this.zzaxG;
  }
  
  public List<Geofence> getTriggeringGeofences()
  {
    return this.zzaxH;
  }
  
  public Location getTriggeringLocation()
  {
    return this.zzaxI;
  }
  
  public boolean hasError()
  {
    return this.zzBv != -1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/GeofencingEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.location.internal.ParcelableGeofence;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeofencingRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<GeofencingRequest> CREATOR = new zza();
  public static final int INITIAL_TRIGGER_DWELL = 4;
  public static final int INITIAL_TRIGGER_ENTER = 1;
  public static final int INITIAL_TRIGGER_EXIT = 2;
  private final int zzCY;
  private final List<ParcelableGeofence> zzaxJ;
  private final int zzaxK;
  
  GeofencingRequest(int paramInt1, List<ParcelableGeofence> paramList, int paramInt2)
  {
    this.zzCY = paramInt1;
    this.zzaxJ = paramList;
    this.zzaxK = paramInt2;
  }
  
  private GeofencingRequest(List<ParcelableGeofence> paramList, int paramInt)
  {
    this(1, paramList, paramInt);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public List<Geofence> getGeofences()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(this.zzaxJ);
    return localArrayList;
  }
  
  public int getInitialTrigger()
  {
    return this.zzaxK;
  }
  
  public int getVersionCode()
  {
    return this.zzCY;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
  
  public List<ParcelableGeofence> zzun()
  {
    return this.zzaxJ;
  }
  
  public static final class Builder
  {
    private final List<ParcelableGeofence> zzaxJ = new ArrayList();
    private int zzaxK = 5;
    
    public static int zzgr(int paramInt)
    {
      return paramInt & 0x7;
    }
    
    public Builder addGeofence(Geofence paramGeofence)
    {
      zzu.zzb(paramGeofence, "geofence can't be null.");
      zzu.zzb(paramGeofence instanceof ParcelableGeofence, "Geofence must be created using Geofence.Builder.");
      this.zzaxJ.add((ParcelableGeofence)paramGeofence);
      return this;
    }
    
    public Builder addGeofences(List<Geofence> paramList)
    {
      if ((paramList == null) || (paramList.isEmpty())) {}
      for (;;)
      {
        return this;
        paramList = paramList.iterator();
        while (paramList.hasNext())
        {
          Geofence localGeofence = (Geofence)paramList.next();
          if (localGeofence != null) {
            addGeofence(localGeofence);
          }
        }
      }
    }
    
    public GeofencingRequest build()
    {
      if (!this.zzaxJ.isEmpty()) {}
      for (boolean bool = true;; bool = false)
      {
        zzu.zzb(bool, "No geofence has been added to this request.");
        return new GeofencingRequest(this.zzaxJ, this.zzaxK, null);
      }
    }
    
    public Builder setInitialTrigger(int paramInt)
    {
      this.zzaxK = zzgr(paramInt);
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/GeofencingRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
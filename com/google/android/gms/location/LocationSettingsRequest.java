package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class LocationSettingsRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<LocationSettingsRequest> CREATOR = new zzg();
  private final int zzCY;
  private final List<LocationRequest> zzamw;
  private final boolean zzayb;
  private final boolean zzayc;
  private final boolean zzayd;
  
  LocationSettingsRequest(int paramInt, List<LocationRequest> paramList, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.zzCY = paramInt;
    this.zzamw = paramList;
    this.zzayb = paramBoolean1;
    this.zzayc = paramBoolean2;
    this.zzayd = paramBoolean3;
  }
  
  private LocationSettingsRequest(List<LocationRequest> paramList, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this(2, paramList, paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getVersionCode()
  {
    return this.zzCY;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzg.zza(this, paramParcel, paramInt);
  }
  
  public List<LocationRequest> zzrj()
  {
    return Collections.unmodifiableList(this.zzamw);
  }
  
  public boolean zzup()
  {
    return this.zzayb;
  }
  
  public boolean zzuq()
  {
    return this.zzayc;
  }
  
  public boolean zzur()
  {
    return this.zzayd;
  }
  
  public static final class Builder
  {
    private boolean zzayb = false;
    private boolean zzayc = false;
    private boolean zzayd = false;
    private final ArrayList<LocationRequest> zzaye = new ArrayList();
    
    public Builder addAllLocationRequests(Collection<LocationRequest> paramCollection)
    {
      this.zzaye.addAll(paramCollection);
      return this;
    }
    
    public Builder addLocationRequest(LocationRequest paramLocationRequest)
    {
      this.zzaye.add(paramLocationRequest);
      return this;
    }
    
    public LocationSettingsRequest build()
    {
      return new LocationSettingsRequest(this.zzaye, this.zzayb, this.zzayc, this.zzayd, null);
    }
    
    public Builder setAlwaysShow(boolean paramBoolean)
    {
      this.zzayb = paramBoolean;
      return this;
    }
    
    public Builder setNeedBle(boolean paramBoolean)
    {
      this.zzayc = paramBoolean;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/LocationSettingsRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
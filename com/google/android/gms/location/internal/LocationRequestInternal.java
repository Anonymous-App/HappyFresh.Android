package com.google.android.gms.location.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

public class LocationRequestInternal
  implements SafeParcelable
{
  public static final zzk CREATOR = new zzk();
  static final List<ClientIdentity> zzaza = ;
  final String mTag;
  private final int zzCY;
  LocationRequest zzamz;
  boolean zzazb;
  boolean zzazc;
  boolean zzazd;
  List<ClientIdentity> zzaze;
  
  LocationRequestInternal(int paramInt, LocationRequest paramLocationRequest, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, List<ClientIdentity> paramList, String paramString)
  {
    this.zzCY = paramInt;
    this.zzamz = paramLocationRequest;
    this.zzazb = paramBoolean1;
    this.zzazc = paramBoolean2;
    this.zzazd = paramBoolean3;
    this.zzaze = paramList;
    this.mTag = paramString;
  }
  
  public static LocationRequestInternal zza(String paramString, LocationRequest paramLocationRequest)
  {
    return new LocationRequestInternal(1, paramLocationRequest, false, true, true, zzaza, paramString);
  }
  
  public static LocationRequestInternal zzb(LocationRequest paramLocationRequest)
  {
    return zza(null, paramLocationRequest);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof LocationRequestInternal)) {}
    do
    {
      return false;
      paramObject = (LocationRequestInternal)paramObject;
    } while ((!zzt.equal(this.zzamz, ((LocationRequestInternal)paramObject).zzamz)) || (this.zzazb != ((LocationRequestInternal)paramObject).zzazb) || (this.zzazc != ((LocationRequestInternal)paramObject).zzazc) || (this.zzazd != ((LocationRequestInternal)paramObject).zzazd) || (!zzt.equal(this.zzaze, ((LocationRequestInternal)paramObject).zzaze)));
    return true;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public int hashCode()
  {
    return this.zzamz.hashCode();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.zzamz.toString());
    localStringBuilder.append(" requestNlpDebugInfo=");
    localStringBuilder.append(this.zzazb);
    localStringBuilder.append(" restorePendingIntentListeners=");
    localStringBuilder.append(this.zzazc);
    localStringBuilder.append(" triggerUpdate=");
    localStringBuilder.append(this.zzazd);
    localStringBuilder.append(" clients=");
    localStringBuilder.append(this.zzaze);
    if (this.mTag != null)
    {
      localStringBuilder.append(" tag=");
      localStringBuilder.append(this.mTag);
    }
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzk.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/internal/LocationRequestInternal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
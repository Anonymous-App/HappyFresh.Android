package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import com.google.android.gms.common.internal.zzu;

public class PlaceReport
  implements SafeParcelable
{
  public static final Parcelable.Creator<PlaceReport> CREATOR = new zzk();
  private final String mTag;
  final int zzCY;
  private final String zzazK;
  private final String zzazL;
  
  PlaceReport(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    this.zzCY = paramInt;
    this.zzazK = paramString1;
    this.mTag = paramString2;
    this.zzazL = paramString3;
  }
  
  public static PlaceReport create(String paramString1, String paramString2)
  {
    return zzi(paramString1, paramString2, "unknown");
  }
  
  private static boolean zzdo(String paramString)
  {
    boolean bool = true;
    int i = -1;
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      switch (i)
      {
      default: 
        bool = false;
      }
      return bool;
      if (paramString.equals("unknown"))
      {
        i = 0;
        continue;
        if (paramString.equals("userReported"))
        {
          i = 1;
          continue;
          if (paramString.equals("inferredGeofencing"))
          {
            i = 2;
            continue;
            if (paramString.equals("inferredRadioSignals"))
            {
              i = 3;
              continue;
              if (paramString.equals("inferredReverseGeocoding")) {
                i = 4;
              }
            }
          }
        }
      }
    }
  }
  
  public static PlaceReport zzi(String paramString1, String paramString2, String paramString3)
  {
    zzu.zzcj(paramString1);
    zzu.zzcj(paramString2);
    zzu.zzcj(paramString3);
    zzu.zzb(zzdo(paramString3), "Invalid source");
    return new PlaceReport(1, paramString1, paramString2, paramString3);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PlaceReport)) {}
    do
    {
      return false;
      paramObject = (PlaceReport)paramObject;
    } while ((!zzt.equal(this.zzazK, ((PlaceReport)paramObject).zzazK)) || (!zzt.equal(this.mTag, ((PlaceReport)paramObject).mTag)) || (!zzt.equal(this.zzazL, ((PlaceReport)paramObject).zzazL)));
    return true;
  }
  
  public String getPlaceId()
  {
    return this.zzazK;
  }
  
  public String getSource()
  {
    return this.zzazL;
  }
  
  public String getTag()
  {
    return this.mTag;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { this.zzazK, this.mTag, this.zzazL });
  }
  
  public String toString()
  {
    zzt.zza localzza = zzt.zzt(this);
    localzza.zzg("placeId", this.zzazK);
    localzza.zzg("tag", this.mTag);
    if (!"unknown".equals(this.zzazL)) {
      localzza.zzg("source", this.zzazL);
    }
    return localzza.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzk.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/PlaceReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
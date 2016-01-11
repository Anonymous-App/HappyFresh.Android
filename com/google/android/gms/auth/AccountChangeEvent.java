package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzu;

public class AccountChangeEvent
  implements SafeParcelable
{
  public static final Parcelable.Creator<AccountChangeEvent> CREATOR = new zza();
  final int mVersion;
  final String zzOA;
  final long zzOw;
  final String zzOx;
  final int zzOy;
  final int zzOz;
  
  AccountChangeEvent(int paramInt1, long paramLong, String paramString1, int paramInt2, int paramInt3, String paramString2)
  {
    this.mVersion = paramInt1;
    this.zzOw = paramLong;
    this.zzOx = ((String)zzu.zzu(paramString1));
    this.zzOy = paramInt2;
    this.zzOz = paramInt3;
    this.zzOA = paramString2;
  }
  
  public AccountChangeEvent(long paramLong, String paramString1, int paramInt1, int paramInt2, String paramString2)
  {
    this.mVersion = 1;
    this.zzOw = paramLong;
    this.zzOx = ((String)zzu.zzu(paramString1));
    this.zzOy = paramInt1;
    this.zzOz = paramInt2;
    this.zzOA = paramString2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    do
    {
      return true;
      if (!(paramObject instanceof AccountChangeEvent)) {
        break;
      }
      paramObject = (AccountChangeEvent)paramObject;
    } while ((this.mVersion == ((AccountChangeEvent)paramObject).mVersion) && (this.zzOw == ((AccountChangeEvent)paramObject).zzOw) && (zzt.equal(this.zzOx, ((AccountChangeEvent)paramObject).zzOx)) && (this.zzOy == ((AccountChangeEvent)paramObject).zzOy) && (this.zzOz == ((AccountChangeEvent)paramObject).zzOz) && (zzt.equal(this.zzOA, ((AccountChangeEvent)paramObject).zzOA)));
    return false;
    return false;
  }
  
  public String getAccountName()
  {
    return this.zzOx;
  }
  
  public String getChangeData()
  {
    return this.zzOA;
  }
  
  public int getChangeType()
  {
    return this.zzOy;
  }
  
  public int getEventIndex()
  {
    return this.zzOz;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { Integer.valueOf(this.mVersion), Long.valueOf(this.zzOw), this.zzOx, Integer.valueOf(this.zzOy), Integer.valueOf(this.zzOz), this.zzOA });
  }
  
  public String toString()
  {
    String str = "UNKNOWN";
    switch (this.zzOy)
    {
    }
    for (;;)
    {
      return "AccountChangeEvent {accountName = " + this.zzOx + ", changeType = " + str + ", changeData = " + this.zzOA + ", eventIndex = " + this.zzOz + "}";
      str = "ADDED";
      continue;
      str = "REMOVED";
      continue;
      str = "RENAMED_TO";
      continue;
      str = "RENAMED_FROM";
    }
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/AccountChangeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
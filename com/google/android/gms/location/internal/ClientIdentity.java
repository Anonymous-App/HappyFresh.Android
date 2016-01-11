package com.google.android.gms.location.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;

public class ClientIdentity
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  public final String packageName;
  public final int uid;
  private final int zzCY;
  
  ClientIdentity(int paramInt1, int paramInt2, String paramString)
  {
    this.zzCY = paramInt1;
    this.uid = paramInt2;
    this.packageName = paramString;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ClientIdentity)) {}
    do
    {
      return false;
      paramObject = (ClientIdentity)paramObject;
    } while ((((ClientIdentity)paramObject).uid != this.uid) || (!zzt.equal(((ClientIdentity)paramObject).packageName, this.packageName)));
    return true;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public int hashCode()
  {
    return this.uid;
  }
  
  public String toString()
  {
    return String.format("%d:%s", new Object[] { Integer.valueOf(this.uid), this.packageName });
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/internal/ClientIdentity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;

public final class Scope
  implements SafeParcelable
{
  public static final Parcelable.Creator<Scope> CREATOR = new zzj();
  final int zzCY;
  private final String zzXO;
  
  Scope(int paramInt, String paramString)
  {
    zzu.zzh(paramString, "scopeUri must not be null or empty");
    this.zzCY = paramInt;
    this.zzXO = paramString;
  }
  
  public Scope(String paramString)
  {
    this(1, paramString);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Scope)) {
      return false;
    }
    return this.zzXO.equals(((Scope)paramObject).zzXO);
  }
  
  public int hashCode()
  {
    return this.zzXO.hashCode();
  }
  
  public String toString()
  {
    return this.zzXO;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzj.zza(this, paramParcel, paramInt);
  }
  
  public String zzmS()
  {
    return this.zzXO;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/Scope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
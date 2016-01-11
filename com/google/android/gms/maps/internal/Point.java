package com.google.android.gms.maps.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class Point
  implements SafeParcelable
{
  public static final zzz CREATOR = new zzz();
  private final int versionCode;
  private final android.graphics.Point zzaCO;
  
  public Point(int paramInt, android.graphics.Point paramPoint)
  {
    this.versionCode = paramInt;
    this.zzaCO = paramPoint;
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
    if (!(paramObject instanceof Point)) {
      return false;
    }
    paramObject = (Point)paramObject;
    return this.zzaCO.equals(((Point)paramObject).zzaCO);
  }
  
  int getVersionCode()
  {
    return this.versionCode;
  }
  
  public int hashCode()
  {
    return this.zzaCO.hashCode();
  }
  
  public String toString()
  {
    return this.zzaCO.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzz.zza(this, paramParcel, paramInt);
  }
  
  public android.graphics.Point zzvG()
  {
    return this.zzaCO;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/internal/Point.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import com.google.android.gms.common.internal.zzu;

public final class LatLngBounds
  implements SafeParcelable
{
  public static final zzd CREATOR = new zzd();
  public final LatLng northeast;
  public final LatLng southwest;
  private final int zzCY;
  
  LatLngBounds(int paramInt, LatLng paramLatLng1, LatLng paramLatLng2)
  {
    zzu.zzb(paramLatLng1, "null southwest");
    zzu.zzb(paramLatLng2, "null northeast");
    if (paramLatLng2.latitude >= paramLatLng1.latitude) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zzb(bool, "southern latitude exceeds northern latitude (%s > %s)", new Object[] { Double.valueOf(paramLatLng1.latitude), Double.valueOf(paramLatLng2.latitude) });
      this.zzCY = paramInt;
      this.southwest = paramLatLng1;
      this.northeast = paramLatLng2;
      return;
    }
  }
  
  public LatLngBounds(LatLng paramLatLng1, LatLng paramLatLng2)
  {
    this(1, paramLatLng1, paramLatLng2);
  }
  
  public static Builder builder()
  {
    return new Builder();
  }
  
  private static double zzb(double paramDouble1, double paramDouble2)
  {
    return (paramDouble1 - paramDouble2 + 360.0D) % 360.0D;
  }
  
  private static double zzc(double paramDouble1, double paramDouble2)
  {
    return (paramDouble2 - paramDouble1 + 360.0D) % 360.0D;
  }
  
  private boolean zzf(double paramDouble)
  {
    return (this.southwest.latitude <= paramDouble) && (paramDouble <= this.northeast.latitude);
  }
  
  private boolean zzg(double paramDouble)
  {
    boolean bool = false;
    if (this.southwest.longitude <= this.northeast.longitude) {
      return (this.southwest.longitude <= paramDouble) && (paramDouble <= this.northeast.longitude);
    }
    if ((this.southwest.longitude <= paramDouble) || (paramDouble <= this.northeast.longitude)) {
      bool = true;
    }
    return bool;
  }
  
  public boolean contains(LatLng paramLatLng)
  {
    return (zzf(paramLatLng.latitude)) && (zzg(paramLatLng.longitude));
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof LatLngBounds)) {
        return false;
      }
      paramObject = (LatLngBounds)paramObject;
    } while ((this.southwest.equals(((LatLngBounds)paramObject).southwest)) && (this.northeast.equals(((LatLngBounds)paramObject).northeast)));
    return false;
  }
  
  public LatLng getCenter()
  {
    double d2 = (this.southwest.latitude + this.northeast.latitude) / 2.0D;
    double d1 = this.northeast.longitude;
    double d3 = this.southwest.longitude;
    if (d3 <= d1) {}
    for (d1 = (d1 + d3) / 2.0D;; d1 = (d1 + 360.0D + d3) / 2.0D) {
      return new LatLng(d2, d1);
    }
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { this.southwest, this.northeast });
  }
  
  public LatLngBounds including(LatLng paramLatLng)
  {
    double d4 = Math.min(this.southwest.latitude, paramLatLng.latitude);
    double d5 = Math.max(this.northeast.latitude, paramLatLng.latitude);
    double d2 = this.northeast.longitude;
    double d3 = this.southwest.longitude;
    double d1 = paramLatLng.longitude;
    if (!zzg(d1)) {
      if (zzb(d3, d1) >= zzc(d2, d1)) {}
    }
    for (;;)
    {
      return new LatLngBounds(new LatLng(d4, d1), new LatLng(d5, d2));
      d2 = d1;
      d1 = d3;
      continue;
      d1 = d3;
    }
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("southwest", this.southwest).zzg("northeast", this.northeast).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzd.zza(this, paramParcel, paramInt);
  }
  
  public static final class Builder
  {
    private double zzaDn = Double.POSITIVE_INFINITY;
    private double zzaDo = Double.NEGATIVE_INFINITY;
    private double zzaDp = NaN.0D;
    private double zzaDq = NaN.0D;
    
    private boolean zzg(double paramDouble)
    {
      boolean bool = false;
      if (this.zzaDp <= this.zzaDq) {
        return (this.zzaDp <= paramDouble) && (paramDouble <= this.zzaDq);
      }
      if ((this.zzaDp <= paramDouble) || (paramDouble <= this.zzaDq)) {
        bool = true;
      }
      return bool;
    }
    
    public LatLngBounds build()
    {
      if (!Double.isNaN(this.zzaDp)) {}
      for (boolean bool = true;; bool = false)
      {
        zzu.zza(bool, "no included points");
        return new LatLngBounds(new LatLng(this.zzaDn, this.zzaDp), new LatLng(this.zzaDo, this.zzaDq));
      }
    }
    
    public Builder include(LatLng paramLatLng)
    {
      this.zzaDn = Math.min(this.zzaDn, paramLatLng.latitude);
      this.zzaDo = Math.max(this.zzaDo, paramLatLng.latitude);
      double d = paramLatLng.longitude;
      if (Double.isNaN(this.zzaDp))
      {
        this.zzaDp = d;
        this.zzaDq = d;
      }
      while (zzg(d)) {
        return this;
      }
      if (LatLngBounds.zzd(this.zzaDp, d) < LatLngBounds.zze(this.zzaDq, d))
      {
        this.zzaDp = d;
        return this;
      }
      this.zzaDq = d;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/LatLngBounds.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
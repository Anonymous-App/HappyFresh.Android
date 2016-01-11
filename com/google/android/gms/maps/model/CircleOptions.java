package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class CircleOptions
  implements SafeParcelable
{
  public static final zzb CREATOR = new zzb();
  private final int zzCY;
  private LatLng zzaCV = null;
  private double zzaCW = 0.0D;
  private float zzaCX = 10.0F;
  private int zzaCY = -16777216;
  private int zzaCZ = 0;
  private float zzaDa = 0.0F;
  private boolean zzaDb = true;
  
  public CircleOptions()
  {
    this.zzCY = 1;
  }
  
  CircleOptions(int paramInt1, LatLng paramLatLng, double paramDouble, float paramFloat1, int paramInt2, int paramInt3, float paramFloat2, boolean paramBoolean)
  {
    this.zzCY = paramInt1;
    this.zzaCV = paramLatLng;
    this.zzaCW = paramDouble;
    this.zzaCX = paramFloat1;
    this.zzaCY = paramInt2;
    this.zzaCZ = paramInt3;
    this.zzaDa = paramFloat2;
    this.zzaDb = paramBoolean;
  }
  
  public CircleOptions center(LatLng paramLatLng)
  {
    this.zzaCV = paramLatLng;
    return this;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public CircleOptions fillColor(int paramInt)
  {
    this.zzaCZ = paramInt;
    return this;
  }
  
  public LatLng getCenter()
  {
    return this.zzaCV;
  }
  
  public int getFillColor()
  {
    return this.zzaCZ;
  }
  
  public double getRadius()
  {
    return this.zzaCW;
  }
  
  public int getStrokeColor()
  {
    return this.zzaCY;
  }
  
  public float getStrokeWidth()
  {
    return this.zzaCX;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public float getZIndex()
  {
    return this.zzaDa;
  }
  
  public boolean isVisible()
  {
    return this.zzaDb;
  }
  
  public CircleOptions radius(double paramDouble)
  {
    this.zzaCW = paramDouble;
    return this;
  }
  
  public CircleOptions strokeColor(int paramInt)
  {
    this.zzaCY = paramInt;
    return this;
  }
  
  public CircleOptions strokeWidth(float paramFloat)
  {
    this.zzaCX = paramFloat;
    return this;
  }
  
  public CircleOptions visible(boolean paramBoolean)
  {
    this.zzaDb = paramBoolean;
    return this;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
  
  public CircleOptions zIndex(float paramFloat)
  {
    this.zzaDa = paramFloat;
    return this;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/CircleOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
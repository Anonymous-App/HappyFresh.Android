package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class PolylineOptions
  implements SafeParcelable
{
  public static final zzh CREATOR = new zzh();
  private int mColor = -16777216;
  private final int zzCY;
  private final List<LatLng> zzaDA;
  private boolean zzaDC = false;
  private float zzaDa = 0.0F;
  private boolean zzaDb = true;
  private float zzaDf = 10.0F;
  
  public PolylineOptions()
  {
    this.zzCY = 1;
    this.zzaDA = new ArrayList();
  }
  
  PolylineOptions(int paramInt1, List paramList, float paramFloat1, int paramInt2, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.zzCY = paramInt1;
    this.zzaDA = paramList;
    this.zzaDf = paramFloat1;
    this.mColor = paramInt2;
    this.zzaDa = paramFloat2;
    this.zzaDb = paramBoolean1;
    this.zzaDC = paramBoolean2;
  }
  
  public PolylineOptions add(LatLng paramLatLng)
  {
    this.zzaDA.add(paramLatLng);
    return this;
  }
  
  public PolylineOptions add(LatLng... paramVarArgs)
  {
    this.zzaDA.addAll(Arrays.asList(paramVarArgs));
    return this;
  }
  
  public PolylineOptions addAll(Iterable<LatLng> paramIterable)
  {
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext())
    {
      LatLng localLatLng = (LatLng)paramIterable.next();
      this.zzaDA.add(localLatLng);
    }
    return this;
  }
  
  public PolylineOptions color(int paramInt)
  {
    this.mColor = paramInt;
    return this;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public PolylineOptions geodesic(boolean paramBoolean)
  {
    this.zzaDC = paramBoolean;
    return this;
  }
  
  public int getColor()
  {
    return this.mColor;
  }
  
  public List<LatLng> getPoints()
  {
    return this.zzaDA;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public float getWidth()
  {
    return this.zzaDf;
  }
  
  public float getZIndex()
  {
    return this.zzaDa;
  }
  
  public boolean isGeodesic()
  {
    return this.zzaDC;
  }
  
  public boolean isVisible()
  {
    return this.zzaDb;
  }
  
  public PolylineOptions visible(boolean paramBoolean)
  {
    this.zzaDb = paramBoolean;
    return this;
  }
  
  public PolylineOptions width(float paramFloat)
  {
    this.zzaDf = paramFloat;
    return this;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzh.zza(this, paramParcel, paramInt);
  }
  
  public PolylineOptions zIndex(float paramFloat)
  {
    this.zzaDa = paramFloat;
    return this;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/PolylineOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
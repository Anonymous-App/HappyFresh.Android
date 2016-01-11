package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class PolygonOptions
  implements SafeParcelable
{
  public static final zzg CREATOR = new zzg();
  private final int zzCY;
  private float zzaCX = 10.0F;
  private int zzaCY = -16777216;
  private int zzaCZ = 0;
  private final List<LatLng> zzaDA;
  private final List<List<LatLng>> zzaDB;
  private boolean zzaDC = false;
  private float zzaDa = 0.0F;
  private boolean zzaDb = true;
  
  public PolygonOptions()
  {
    this.zzCY = 1;
    this.zzaDA = new ArrayList();
    this.zzaDB = new ArrayList();
  }
  
  PolygonOptions(int paramInt1, List<LatLng> paramList, List paramList1, float paramFloat1, int paramInt2, int paramInt3, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.zzCY = paramInt1;
    this.zzaDA = paramList;
    this.zzaDB = paramList1;
    this.zzaCX = paramFloat1;
    this.zzaCY = paramInt2;
    this.zzaCZ = paramInt3;
    this.zzaDa = paramFloat2;
    this.zzaDb = paramBoolean1;
    this.zzaDC = paramBoolean2;
  }
  
  public PolygonOptions add(LatLng paramLatLng)
  {
    this.zzaDA.add(paramLatLng);
    return this;
  }
  
  public PolygonOptions add(LatLng... paramVarArgs)
  {
    this.zzaDA.addAll(Arrays.asList(paramVarArgs));
    return this;
  }
  
  public PolygonOptions addAll(Iterable<LatLng> paramIterable)
  {
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext())
    {
      LatLng localLatLng = (LatLng)paramIterable.next();
      this.zzaDA.add(localLatLng);
    }
    return this;
  }
  
  public PolygonOptions addHole(Iterable<LatLng> paramIterable)
  {
    ArrayList localArrayList = new ArrayList();
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      localArrayList.add((LatLng)paramIterable.next());
    }
    this.zzaDB.add(localArrayList);
    return this;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public PolygonOptions fillColor(int paramInt)
  {
    this.zzaCZ = paramInt;
    return this;
  }
  
  public PolygonOptions geodesic(boolean paramBoolean)
  {
    this.zzaDC = paramBoolean;
    return this;
  }
  
  public int getFillColor()
  {
    return this.zzaCZ;
  }
  
  public List<List<LatLng>> getHoles()
  {
    return this.zzaDB;
  }
  
  public List<LatLng> getPoints()
  {
    return this.zzaDA;
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
  
  public boolean isGeodesic()
  {
    return this.zzaDC;
  }
  
  public boolean isVisible()
  {
    return this.zzaDb;
  }
  
  public PolygonOptions strokeColor(int paramInt)
  {
    this.zzaCY = paramInt;
    return this;
  }
  
  public PolygonOptions strokeWidth(float paramFloat)
  {
    this.zzaCX = paramFloat;
    return this;
  }
  
  public PolygonOptions visible(boolean paramBoolean)
  {
    this.zzaDb = paramBoolean;
    return this;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzg.zza(this, paramParcel, paramInt);
  }
  
  public PolygonOptions zIndex(float paramFloat)
  {
    this.zzaDa = paramFloat;
    return this;
  }
  
  List zzvK()
  {
    return this.zzaDB;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/PolygonOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
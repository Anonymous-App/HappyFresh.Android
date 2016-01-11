package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zzd.zza;

public final class MarkerOptions
  implements SafeParcelable
{
  public static final zzf CREATOR = new zzf();
  private float mAlpha = 1.0F;
  private final int zzCY;
  private LatLng zzaCx;
  private boolean zzaDb = true;
  private float zzaDj = 0.5F;
  private float zzaDk = 1.0F;
  private String zzaDs;
  private BitmapDescriptor zzaDt;
  private boolean zzaDu;
  private boolean zzaDv = false;
  private float zzaDw = 0.0F;
  private float zzaDx = 0.5F;
  private float zzaDy = 0.0F;
  private String zzadv;
  
  public MarkerOptions()
  {
    this.zzCY = 1;
  }
  
  MarkerOptions(int paramInt, LatLng paramLatLng, String paramString1, String paramString2, IBinder paramIBinder, float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.zzCY = paramInt;
    this.zzaCx = paramLatLng;
    this.zzadv = paramString1;
    this.zzaDs = paramString2;
    if (paramIBinder == null) {}
    for (paramLatLng = null;; paramLatLng = new BitmapDescriptor(zzd.zza.zzbg(paramIBinder)))
    {
      this.zzaDt = paramLatLng;
      this.zzaDj = paramFloat1;
      this.zzaDk = paramFloat2;
      this.zzaDu = paramBoolean1;
      this.zzaDb = paramBoolean2;
      this.zzaDv = paramBoolean3;
      this.zzaDw = paramFloat3;
      this.zzaDx = paramFloat4;
      this.zzaDy = paramFloat5;
      this.mAlpha = paramFloat6;
      return;
    }
  }
  
  public MarkerOptions alpha(float paramFloat)
  {
    this.mAlpha = paramFloat;
    return this;
  }
  
  public MarkerOptions anchor(float paramFloat1, float paramFloat2)
  {
    this.zzaDj = paramFloat1;
    this.zzaDk = paramFloat2;
    return this;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public MarkerOptions draggable(boolean paramBoolean)
  {
    this.zzaDu = paramBoolean;
    return this;
  }
  
  public MarkerOptions flat(boolean paramBoolean)
  {
    this.zzaDv = paramBoolean;
    return this;
  }
  
  public float getAlpha()
  {
    return this.mAlpha;
  }
  
  public float getAnchorU()
  {
    return this.zzaDj;
  }
  
  public float getAnchorV()
  {
    return this.zzaDk;
  }
  
  public BitmapDescriptor getIcon()
  {
    return this.zzaDt;
  }
  
  public float getInfoWindowAnchorU()
  {
    return this.zzaDx;
  }
  
  public float getInfoWindowAnchorV()
  {
    return this.zzaDy;
  }
  
  public LatLng getPosition()
  {
    return this.zzaCx;
  }
  
  public float getRotation()
  {
    return this.zzaDw;
  }
  
  public String getSnippet()
  {
    return this.zzaDs;
  }
  
  public String getTitle()
  {
    return this.zzadv;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public MarkerOptions icon(BitmapDescriptor paramBitmapDescriptor)
  {
    this.zzaDt = paramBitmapDescriptor;
    return this;
  }
  
  public MarkerOptions infoWindowAnchor(float paramFloat1, float paramFloat2)
  {
    this.zzaDx = paramFloat1;
    this.zzaDy = paramFloat2;
    return this;
  }
  
  public boolean isDraggable()
  {
    return this.zzaDu;
  }
  
  public boolean isFlat()
  {
    return this.zzaDv;
  }
  
  public boolean isVisible()
  {
    return this.zzaDb;
  }
  
  public MarkerOptions position(LatLng paramLatLng)
  {
    this.zzaCx = paramLatLng;
    return this;
  }
  
  public MarkerOptions rotation(float paramFloat)
  {
    this.zzaDw = paramFloat;
    return this;
  }
  
  public MarkerOptions snippet(String paramString)
  {
    this.zzaDs = paramString;
    return this;
  }
  
  public MarkerOptions title(String paramString)
  {
    this.zzadv = paramString;
    return this;
  }
  
  public MarkerOptions visible(boolean paramBoolean)
  {
    this.zzaDb = paramBoolean;
    return this;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzf.zza(this, paramParcel, paramInt);
  }
  
  IBinder zzvJ()
  {
    if (this.zzaDt == null) {
      return null;
    }
    return this.zzaDt.zzvg().asBinder();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/MarkerOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
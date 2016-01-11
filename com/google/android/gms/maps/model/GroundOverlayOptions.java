package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zzd.zza;

public final class GroundOverlayOptions
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  public static final float NO_DIMENSION = -1.0F;
  private final int zzCY;
  private float zzaCT;
  private float zzaDa;
  private boolean zzaDb = true;
  private BitmapDescriptor zzaDd;
  private LatLng zzaDe;
  private float zzaDf;
  private float zzaDg;
  private LatLngBounds zzaDh;
  private float zzaDi = 0.0F;
  private float zzaDj = 0.5F;
  private float zzaDk = 0.5F;
  
  public GroundOverlayOptions()
  {
    this.zzCY = 1;
  }
  
  GroundOverlayOptions(int paramInt, IBinder paramIBinder, LatLng paramLatLng, float paramFloat1, float paramFloat2, LatLngBounds paramLatLngBounds, float paramFloat3, float paramFloat4, boolean paramBoolean, float paramFloat5, float paramFloat6, float paramFloat7)
  {
    this.zzCY = paramInt;
    this.zzaDd = new BitmapDescriptor(zzd.zza.zzbg(paramIBinder));
    this.zzaDe = paramLatLng;
    this.zzaDf = paramFloat1;
    this.zzaDg = paramFloat2;
    this.zzaDh = paramLatLngBounds;
    this.zzaCT = paramFloat3;
    this.zzaDa = paramFloat4;
    this.zzaDb = paramBoolean;
    this.zzaDi = paramFloat5;
    this.zzaDj = paramFloat6;
    this.zzaDk = paramFloat7;
  }
  
  private GroundOverlayOptions zza(LatLng paramLatLng, float paramFloat1, float paramFloat2)
  {
    this.zzaDe = paramLatLng;
    this.zzaDf = paramFloat1;
    this.zzaDg = paramFloat2;
    return this;
  }
  
  public GroundOverlayOptions anchor(float paramFloat1, float paramFloat2)
  {
    this.zzaDj = paramFloat1;
    this.zzaDk = paramFloat2;
    return this;
  }
  
  public GroundOverlayOptions bearing(float paramFloat)
  {
    this.zzaCT = ((paramFloat % 360.0F + 360.0F) % 360.0F);
    return this;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public float getAnchorU()
  {
    return this.zzaDj;
  }
  
  public float getAnchorV()
  {
    return this.zzaDk;
  }
  
  public float getBearing()
  {
    return this.zzaCT;
  }
  
  public LatLngBounds getBounds()
  {
    return this.zzaDh;
  }
  
  public float getHeight()
  {
    return this.zzaDg;
  }
  
  public BitmapDescriptor getImage()
  {
    return this.zzaDd;
  }
  
  public LatLng getLocation()
  {
    return this.zzaDe;
  }
  
  public float getTransparency()
  {
    return this.zzaDi;
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
  
  public GroundOverlayOptions image(BitmapDescriptor paramBitmapDescriptor)
  {
    this.zzaDd = paramBitmapDescriptor;
    return this;
  }
  
  public boolean isVisible()
  {
    return this.zzaDb;
  }
  
  public GroundOverlayOptions position(LatLng paramLatLng, float paramFloat)
  {
    boolean bool2 = true;
    if (this.zzaDh == null)
    {
      bool1 = true;
      zzu.zza(bool1, "Position has already been set using positionFromBounds");
      if (paramLatLng == null) {
        break label59;
      }
      bool1 = true;
      label24:
      zzu.zzb(bool1, "Location must be specified");
      if (paramFloat < 0.0F) {
        break label64;
      }
    }
    label59:
    label64:
    for (boolean bool1 = bool2;; bool1 = false)
    {
      zzu.zzb(bool1, "Width must be non-negative");
      return zza(paramLatLng, paramFloat, -1.0F);
      bool1 = false;
      break;
      bool1 = false;
      break label24;
    }
  }
  
  public GroundOverlayOptions position(LatLng paramLatLng, float paramFloat1, float paramFloat2)
  {
    boolean bool2 = true;
    if (this.zzaDh == null)
    {
      bool1 = true;
      zzu.zza(bool1, "Position has already been set using positionFromBounds");
      if (paramLatLng == null) {
        break label81;
      }
      bool1 = true;
      label27:
      zzu.zzb(bool1, "Location must be specified");
      if (paramFloat1 < 0.0F) {
        break label87;
      }
      bool1 = true;
      label43:
      zzu.zzb(bool1, "Width must be non-negative");
      if (paramFloat2 < 0.0F) {
        break label93;
      }
    }
    label81:
    label87:
    label93:
    for (boolean bool1 = bool2;; bool1 = false)
    {
      zzu.zzb(bool1, "Height must be non-negative");
      return zza(paramLatLng, paramFloat1, paramFloat2);
      bool1 = false;
      break;
      bool1 = false;
      break label27;
      bool1 = false;
      break label43;
    }
  }
  
  public GroundOverlayOptions positionFromBounds(LatLngBounds paramLatLngBounds)
  {
    if (this.zzaDe == null) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zza(bool, "Position has already been set using position: " + this.zzaDe);
      this.zzaDh = paramLatLngBounds;
      return this;
    }
  }
  
  public GroundOverlayOptions transparency(float paramFloat)
  {
    if ((paramFloat >= 0.0F) && (paramFloat <= 1.0F)) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zzb(bool, "Transparency must be in the range [0..1]");
      this.zzaDi = paramFloat;
      return this;
    }
  }
  
  public GroundOverlayOptions visible(boolean paramBoolean)
  {
    this.zzaDb = paramBoolean;
    return this;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
  
  public GroundOverlayOptions zIndex(float paramFloat)
  {
    this.zzaDa = paramFloat;
    return this;
  }
  
  IBinder zzvI()
  {
    return this.zzaDd.zzvg().asBinder();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/GroundOverlayOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
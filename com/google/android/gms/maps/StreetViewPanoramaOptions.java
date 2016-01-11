package com.google.android.gms.maps;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.zza;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public final class StreetViewPanoramaOptions
  implements SafeParcelable
{
  public static final zzb CREATOR = new zzb();
  private final int zzCY;
  private Boolean zzaBJ;
  private Boolean zzaBP = Boolean.valueOf(true);
  private Boolean zzaCA = Boolean.valueOf(true);
  private Boolean zzaCB = Boolean.valueOf(true);
  private StreetViewPanoramaCamera zzaCv;
  private String zzaCw;
  private LatLng zzaCx;
  private Integer zzaCy;
  private Boolean zzaCz = Boolean.valueOf(true);
  
  public StreetViewPanoramaOptions()
  {
    this.zzCY = 1;
  }
  
  StreetViewPanoramaOptions(int paramInt, StreetViewPanoramaCamera paramStreetViewPanoramaCamera, String paramString, LatLng paramLatLng, Integer paramInteger, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5)
  {
    this.zzCY = paramInt;
    this.zzaCv = paramStreetViewPanoramaCamera;
    this.zzaCx = paramLatLng;
    this.zzaCy = paramInteger;
    this.zzaCw = paramString;
    this.zzaCz = zza.zza(paramByte1);
    this.zzaBP = zza.zza(paramByte2);
    this.zzaCA = zza.zza(paramByte3);
    this.zzaCB = zza.zza(paramByte4);
    this.zzaBJ = zza.zza(paramByte5);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Boolean getPanningGesturesEnabled()
  {
    return this.zzaCA;
  }
  
  public String getPanoramaId()
  {
    return this.zzaCw;
  }
  
  public LatLng getPosition()
  {
    return this.zzaCx;
  }
  
  public Integer getRadius()
  {
    return this.zzaCy;
  }
  
  public Boolean getStreetNamesEnabled()
  {
    return this.zzaCB;
  }
  
  public StreetViewPanoramaCamera getStreetViewPanoramaCamera()
  {
    return this.zzaCv;
  }
  
  public Boolean getUseViewLifecycleInFragment()
  {
    return this.zzaBJ;
  }
  
  public Boolean getUserNavigationEnabled()
  {
    return this.zzaCz;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public Boolean getZoomGesturesEnabled()
  {
    return this.zzaBP;
  }
  
  public StreetViewPanoramaOptions panningGesturesEnabled(boolean paramBoolean)
  {
    this.zzaCA = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public StreetViewPanoramaOptions panoramaCamera(StreetViewPanoramaCamera paramStreetViewPanoramaCamera)
  {
    this.zzaCv = paramStreetViewPanoramaCamera;
    return this;
  }
  
  public StreetViewPanoramaOptions panoramaId(String paramString)
  {
    this.zzaCw = paramString;
    return this;
  }
  
  public StreetViewPanoramaOptions position(LatLng paramLatLng)
  {
    this.zzaCx = paramLatLng;
    return this;
  }
  
  public StreetViewPanoramaOptions position(LatLng paramLatLng, Integer paramInteger)
  {
    this.zzaCx = paramLatLng;
    this.zzaCy = paramInteger;
    return this;
  }
  
  public StreetViewPanoramaOptions streetNamesEnabled(boolean paramBoolean)
  {
    this.zzaCB = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public StreetViewPanoramaOptions useViewLifecycleInFragment(boolean paramBoolean)
  {
    this.zzaBJ = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public StreetViewPanoramaOptions userNavigationEnabled(boolean paramBoolean)
  {
    this.zzaCz = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
  
  public StreetViewPanoramaOptions zoomGesturesEnabled(boolean paramBoolean)
  {
    this.zzaBP = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  byte zzvA()
  {
    return zza.zze(this.zzaCB);
  }
  
  byte zzvk()
  {
    return zza.zze(this.zzaBJ);
  }
  
  byte zzvo()
  {
    return zza.zze(this.zzaBP);
  }
  
  byte zzvy()
  {
    return zza.zze(this.zzaCz);
  }
  
  byte zzvz()
  {
    return zza.zze(this.zzaCA);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/StreetViewPanoramaOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
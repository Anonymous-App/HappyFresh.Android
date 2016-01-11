package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.R.styleable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.CameraPosition;

public final class GoogleMapOptions
  implements SafeParcelable
{
  public static final zza CREATOR = new zza();
  private final int zzCY;
  private Boolean zzaBI;
  private Boolean zzaBJ;
  private int zzaBK = -1;
  private CameraPosition zzaBL;
  private Boolean zzaBM;
  private Boolean zzaBN;
  private Boolean zzaBO;
  private Boolean zzaBP;
  private Boolean zzaBQ;
  private Boolean zzaBR;
  private Boolean zzaBS;
  private Boolean zzaBT;
  
  public GoogleMapOptions()
  {
    this.zzCY = 1;
  }
  
  GoogleMapOptions(int paramInt1, byte paramByte1, byte paramByte2, int paramInt2, CameraPosition paramCameraPosition, byte paramByte3, byte paramByte4, byte paramByte5, byte paramByte6, byte paramByte7, byte paramByte8, byte paramByte9, byte paramByte10)
  {
    this.zzCY = paramInt1;
    this.zzaBI = com.google.android.gms.maps.internal.zza.zza(paramByte1);
    this.zzaBJ = com.google.android.gms.maps.internal.zza.zza(paramByte2);
    this.zzaBK = paramInt2;
    this.zzaBL = paramCameraPosition;
    this.zzaBM = com.google.android.gms.maps.internal.zza.zza(paramByte3);
    this.zzaBN = com.google.android.gms.maps.internal.zza.zza(paramByte4);
    this.zzaBO = com.google.android.gms.maps.internal.zza.zza(paramByte5);
    this.zzaBP = com.google.android.gms.maps.internal.zza.zza(paramByte6);
    this.zzaBQ = com.google.android.gms.maps.internal.zza.zza(paramByte7);
    this.zzaBR = com.google.android.gms.maps.internal.zza.zza(paramByte8);
    this.zzaBS = com.google.android.gms.maps.internal.zza.zza(paramByte9);
    this.zzaBT = com.google.android.gms.maps.internal.zza.zza(paramByte10);
  }
  
  public static GoogleMapOptions createFromAttributes(Context paramContext, AttributeSet paramAttributeSet)
  {
    if (paramAttributeSet == null) {
      return null;
    }
    TypedArray localTypedArray = paramContext.getResources().obtainAttributes(paramAttributeSet, R.styleable.MapAttrs);
    GoogleMapOptions localGoogleMapOptions = new GoogleMapOptions();
    if (localTypedArray.hasValue(R.styleable.MapAttrs_mapType)) {
      localGoogleMapOptions.mapType(localTypedArray.getInt(R.styleable.MapAttrs_mapType, -1));
    }
    if (localTypedArray.hasValue(R.styleable.MapAttrs_zOrderOnTop)) {
      localGoogleMapOptions.zOrderOnTop(localTypedArray.getBoolean(R.styleable.MapAttrs_zOrderOnTop, false));
    }
    if (localTypedArray.hasValue(R.styleable.MapAttrs_useViewLifecycle)) {
      localGoogleMapOptions.useViewLifecycleInFragment(localTypedArray.getBoolean(R.styleable.MapAttrs_useViewLifecycle, false));
    }
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiCompass)) {
      localGoogleMapOptions.compassEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiCompass, true));
    }
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiRotateGestures)) {
      localGoogleMapOptions.rotateGesturesEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiRotateGestures, true));
    }
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiScrollGestures)) {
      localGoogleMapOptions.scrollGesturesEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiScrollGestures, true));
    }
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiTiltGestures)) {
      localGoogleMapOptions.tiltGesturesEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiTiltGestures, true));
    }
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiZoomGestures)) {
      localGoogleMapOptions.zoomGesturesEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiZoomGestures, true));
    }
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiZoomControls)) {
      localGoogleMapOptions.zoomControlsEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiZoomControls, true));
    }
    if (localTypedArray.hasValue(R.styleable.MapAttrs_liteMode)) {
      localGoogleMapOptions.liteMode(localTypedArray.getBoolean(R.styleable.MapAttrs_liteMode, false));
    }
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiMapToolbar)) {
      localGoogleMapOptions.mapToolbarEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiMapToolbar, true));
    }
    localGoogleMapOptions.camera(CameraPosition.createFromAttributes(paramContext, paramAttributeSet));
    localTypedArray.recycle();
    return localGoogleMapOptions;
  }
  
  public GoogleMapOptions camera(CameraPosition paramCameraPosition)
  {
    this.zzaBL = paramCameraPosition;
    return this;
  }
  
  public GoogleMapOptions compassEnabled(boolean paramBoolean)
  {
    this.zzaBN = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public CameraPosition getCamera()
  {
    return this.zzaBL;
  }
  
  public Boolean getCompassEnabled()
  {
    return this.zzaBN;
  }
  
  public Boolean getLiteMode()
  {
    return this.zzaBS;
  }
  
  public Boolean getMapToolbarEnabled()
  {
    return this.zzaBT;
  }
  
  public int getMapType()
  {
    return this.zzaBK;
  }
  
  public Boolean getRotateGesturesEnabled()
  {
    return this.zzaBR;
  }
  
  public Boolean getScrollGesturesEnabled()
  {
    return this.zzaBO;
  }
  
  public Boolean getTiltGesturesEnabled()
  {
    return this.zzaBQ;
  }
  
  public Boolean getUseViewLifecycleInFragment()
  {
    return this.zzaBJ;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public Boolean getZOrderOnTop()
  {
    return this.zzaBI;
  }
  
  public Boolean getZoomControlsEnabled()
  {
    return this.zzaBM;
  }
  
  public Boolean getZoomGesturesEnabled()
  {
    return this.zzaBP;
  }
  
  public GoogleMapOptions liteMode(boolean paramBoolean)
  {
    this.zzaBS = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public GoogleMapOptions mapToolbarEnabled(boolean paramBoolean)
  {
    this.zzaBT = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public GoogleMapOptions mapType(int paramInt)
  {
    this.zzaBK = paramInt;
    return this;
  }
  
  public GoogleMapOptions rotateGesturesEnabled(boolean paramBoolean)
  {
    this.zzaBR = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public GoogleMapOptions scrollGesturesEnabled(boolean paramBoolean)
  {
    this.zzaBO = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public GoogleMapOptions tiltGesturesEnabled(boolean paramBoolean)
  {
    this.zzaBQ = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public GoogleMapOptions useViewLifecycleInFragment(boolean paramBoolean)
  {
    this.zzaBJ = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
  
  public GoogleMapOptions zOrderOnTop(boolean paramBoolean)
  {
    this.zzaBI = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public GoogleMapOptions zoomControlsEnabled(boolean paramBoolean)
  {
    this.zzaBM = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  public GoogleMapOptions zoomGesturesEnabled(boolean paramBoolean)
  {
    this.zzaBP = Boolean.valueOf(paramBoolean);
    return this;
  }
  
  byte zzvj()
  {
    return com.google.android.gms.maps.internal.zza.zze(this.zzaBI);
  }
  
  byte zzvk()
  {
    return com.google.android.gms.maps.internal.zza.zze(this.zzaBJ);
  }
  
  byte zzvl()
  {
    return com.google.android.gms.maps.internal.zza.zze(this.zzaBM);
  }
  
  byte zzvm()
  {
    return com.google.android.gms.maps.internal.zza.zze(this.zzaBN);
  }
  
  byte zzvn()
  {
    return com.google.android.gms.maps.internal.zza.zze(this.zzaBO);
  }
  
  byte zzvo()
  {
    return com.google.android.gms.maps.internal.zza.zze(this.zzaBP);
  }
  
  byte zzvp()
  {
    return com.google.android.gms.maps.internal.zza.zze(this.zzaBQ);
  }
  
  byte zzvq()
  {
    return com.google.android.gms.maps.internal.zza.zze(this.zzaBR);
  }
  
  byte zzvr()
  {
    return com.google.android.gms.maps.internal.zza.zze(this.zzaBS);
  }
  
  byte zzvs()
  {
    return com.google.android.gms.maps.internal.zza.zze(this.zzaBT);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/GoogleMapOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
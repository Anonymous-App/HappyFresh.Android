package com.google.android.gms.maps.model.internal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class BitmapDescriptorParcelable
  implements SafeParcelable
{
  public static final zza CREATOR = new zza();
  private final int zzCY;
  private byte zzaDM;
  private Bundle zzaDN;
  private Bitmap zzaDO;
  
  BitmapDescriptorParcelable(int paramInt, byte paramByte, Bundle paramBundle, Bitmap paramBitmap)
  {
    this.zzCY = paramInt;
    this.zzaDM = paramByte;
    this.zzaDN = paramBundle;
    this.zzaDO = paramBitmap;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Bitmap getBitmap()
  {
    return this.zzaDO;
  }
  
  public Bundle getParameters()
  {
    return this.zzaDN;
  }
  
  public byte getType()
  {
    return this.zzaDM;
  }
  
  public int getVersionCode()
  {
    return this.zzCY;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/internal/BitmapDescriptorParcelable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
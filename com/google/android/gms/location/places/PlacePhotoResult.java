package com.google.android.gms.location.places;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;

public class PlacePhotoResult
  implements Result, SafeParcelable
{
  public static final Parcelable.Creator<PlacePhotoResult> CREATOR = new zzj();
  private final Bitmap mBitmap;
  final int zzCY;
  private final Status zzOt;
  final BitmapTeleporter zzazJ;
  
  PlacePhotoResult(int paramInt, Status paramStatus, BitmapTeleporter paramBitmapTeleporter)
  {
    this.zzCY = paramInt;
    this.zzOt = paramStatus;
    this.zzazJ = paramBitmapTeleporter;
    if (this.zzazJ != null)
    {
      this.mBitmap = paramBitmapTeleporter.zznc();
      return;
    }
    this.mBitmap = null;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Status getStatus()
  {
    return this.zzOt;
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("status", this.zzOt).zzg("bitmap", this.mBitmap).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzj.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/PlacePhotoResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
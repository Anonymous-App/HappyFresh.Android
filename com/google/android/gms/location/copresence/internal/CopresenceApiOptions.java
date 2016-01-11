package com.google.android.gms.location.copresence.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class CopresenceApiOptions
  implements SafeParcelable
{
  public static final Parcelable.Creator<CopresenceApiOptions> CREATOR = new zza();
  public static final CopresenceApiOptions zzayn = new CopresenceApiOptions(true, null);
  final int zzCY;
  public final boolean zzayo;
  public final String zzayp;
  
  CopresenceApiOptions(int paramInt, boolean paramBoolean, String paramString)
  {
    this.zzCY = paramInt;
    this.zzayo = paramBoolean;
    this.zzayp = paramString;
  }
  
  public CopresenceApiOptions(boolean paramBoolean, String paramString)
  {
    this(1, paramBoolean, paramString);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/copresence/internal/CopresenceApiOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
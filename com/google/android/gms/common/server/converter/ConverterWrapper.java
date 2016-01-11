package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse.zza;

public class ConverterWrapper
  implements SafeParcelable
{
  public static final zza CREATOR = new zza();
  private final int zzCY;
  private final StringToIntConverter zzabA;
  
  ConverterWrapper(int paramInt, StringToIntConverter paramStringToIntConverter)
  {
    this.zzCY = paramInt;
    this.zzabA = paramStringToIntConverter;
  }
  
  private ConverterWrapper(StringToIntConverter paramStringToIntConverter)
  {
    this.zzCY = 1;
    this.zzabA = paramStringToIntConverter;
  }
  
  public static ConverterWrapper zza(FastJsonResponse.zza<?, ?> paramzza)
  {
    if ((paramzza instanceof StringToIntConverter)) {
      return new ConverterWrapper((StringToIntConverter)paramzza);
    }
    throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
  }
  
  public int describeContents()
  {
    zza localzza = CREATOR;
    return 0;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza localzza = CREATOR;
    zza.zza(this, paramParcel, paramInt);
  }
  
  StringToIntConverter zzoh()
  {
    return this.zzabA;
  }
  
  public FastJsonResponse.zza<?, ?> zzoi()
  {
    if (this.zzabA != null) {
      return this.zzabA;
    }
    throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/server/converter/ConverterWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
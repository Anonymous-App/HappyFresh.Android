package com.google.android.gms.location.places;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import com.google.android.gms.location.places.internal.zzb;

public class AutocompletePredictionBuffer
  extends AbstractDataBuffer<AutocompletePrediction>
  implements Result
{
  public AutocompletePredictionBuffer(DataHolder paramDataHolder)
  {
    super(paramDataHolder);
  }
  
  public AutocompletePrediction get(int paramInt)
  {
    return new zzb(this.zzWu, paramInt);
  }
  
  public Status getStatus()
  {
    return PlacesStatusCodes.zzgU(this.zzWu.getStatusCode());
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("status", getStatus()).toString();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/AutocompletePredictionBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
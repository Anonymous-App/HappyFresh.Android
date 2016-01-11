package com.google.android.gms.location.places;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.places.internal.zzq;

public class zzh
  extends AbstractDataBuffer<zzg>
{
  public zzh(DataHolder paramDataHolder)
  {
    super(paramDataHolder);
  }
  
  public zzg zzgP(int paramInt)
  {
    return new zzq(this.zzWu, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/zzh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
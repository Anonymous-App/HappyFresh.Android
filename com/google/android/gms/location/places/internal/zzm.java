package com.google.android.gms.location.places.internal;

import android.content.Context;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;

public class zzm
  extends zzt
  implements PlaceLikelihood
{
  private final Context mContext;
  
  public zzm(DataHolder paramDataHolder, int paramInt, Context paramContext)
  {
    super(paramDataHolder, paramInt);
    this.mContext = paramContext;
  }
  
  public float getLikelihood()
  {
    return zzb("place_likelihood", -1.0F);
  }
  
  public Place getPlace()
  {
    return new zzr(this.zzWu, this.zzYs, this.mContext);
  }
  
  public PlaceLikelihood zzuY()
  {
    return PlaceLikelihoodEntity.zza((PlaceImpl)getPlace().freeze(), getLikelihood());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
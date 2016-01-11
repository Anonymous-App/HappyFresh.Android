package com.google.android.gms.location.places;

import com.google.android.gms.common.data.Freezable;

public abstract interface PlaceLikelihood
  extends Freezable<PlaceLikelihood>
{
  public abstract float getLikelihood();
  
  public abstract Place getPlace();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/PlaceLikelihood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
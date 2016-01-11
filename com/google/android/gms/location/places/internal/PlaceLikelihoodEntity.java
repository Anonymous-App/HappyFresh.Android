package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;

public class PlaceLikelihoodEntity
  implements SafeParcelable, PlaceLikelihood
{
  public static final Parcelable.Creator<PlaceLikelihoodEntity> CREATOR = new zzl();
  final int zzCY;
  final PlaceImpl zzaAK;
  final float zzaAL;
  
  PlaceLikelihoodEntity(int paramInt, PlaceImpl paramPlaceImpl, float paramFloat)
  {
    this.zzCY = paramInt;
    this.zzaAK = paramPlaceImpl;
    this.zzaAL = paramFloat;
  }
  
  public static PlaceLikelihoodEntity zza(PlaceImpl paramPlaceImpl, float paramFloat)
  {
    return new PlaceLikelihoodEntity(0, (PlaceImpl)zzu.zzu(paramPlaceImpl), paramFloat);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof PlaceLikelihoodEntity)) {
        return false;
      }
      paramObject = (PlaceLikelihoodEntity)paramObject;
    } while ((this.zzaAK.equals(((PlaceLikelihoodEntity)paramObject).zzaAK)) && (this.zzaAL == ((PlaceLikelihoodEntity)paramObject).zzaAL));
    return false;
  }
  
  public float getLikelihood()
  {
    return this.zzaAL;
  }
  
  public Place getPlace()
  {
    return this.zzaAK;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { this.zzaAK, Float.valueOf(this.zzaAL) });
  }
  
  public boolean isDataValid()
  {
    return true;
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("place", this.zzaAK).zzg("likelihood", Float.valueOf(this.zzaAL)).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzl.zza(this, paramParcel, paramInt);
  }
  
  public PlaceLikelihood zzuY()
  {
    return this;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/PlaceLikelihoodEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
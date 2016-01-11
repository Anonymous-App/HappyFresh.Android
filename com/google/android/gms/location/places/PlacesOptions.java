package com.google.android.gms.location.places;

import com.google.android.gms.common.api.Api.ApiOptions.Optional;

public final class PlacesOptions
  implements Api.ApiOptions.Optional
{
  public final String zzazX;
  
  private PlacesOptions(Builder paramBuilder)
  {
    this.zzazX = Builder.zza(paramBuilder);
  }
  
  public static class Builder
  {
    private String zzazY;
    
    public PlacesOptions build()
    {
      return new PlacesOptions(this, null);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/PlacesOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
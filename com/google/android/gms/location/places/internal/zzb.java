package com.google.android.gms.location.places.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.places.AutocompletePrediction;
import java.util.Collections;
import java.util.List;

public class zzb
  extends zzt
  implements AutocompletePrediction
{
  public zzb(DataHolder paramDataHolder, int paramInt)
  {
    super(paramDataHolder, paramInt);
  }
  
  public String getDescription()
  {
    return zzB("ap_description", "");
  }
  
  public List<AutocompletePredictionEntity.SubstringEntity> getMatchedSubstrings()
  {
    return zza("ap_matched_subscriptions", AutocompletePredictionEntity.SubstringEntity.CREATOR, Collections.emptyList());
  }
  
  public String getPlaceId()
  {
    return zzB("ap_place_id", null);
  }
  
  public List<Integer> getPlaceTypes()
  {
    return zza("ap_place_types", Collections.emptyList());
  }
  
  public AutocompletePrediction zzuL()
  {
    return AutocompletePredictionEntity.zza(getDescription(), getPlaceId(), getPlaceTypes(), getMatchedSubstrings(), zzuM());
  }
  
  public int zzuM()
  {
    return zzz("ap_personalization_type", 6);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
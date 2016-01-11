package com.google.android.gms.location.places.personalized;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.location.places.PlacesStatusCodes;

public final class zzd
  extends com.google.android.gms.common.data.zzd<PlaceUserData>
  implements Result
{
  private final Status zzOt;
  
  public zzd(DataHolder paramDataHolder)
  {
    this(paramDataHolder, PlacesStatusCodes.zzgU(paramDataHolder.getStatusCode()));
  }
  
  private zzd(DataHolder paramDataHolder, Status paramStatus)
  {
    super(paramDataHolder, PlaceUserData.CREATOR);
    if ((paramDataHolder == null) || (paramDataHolder.getStatusCode() == paramStatus.getStatusCode())) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zzV(bool);
      this.zzOt = paramStatus;
      return;
    }
  }
  
  public static zzd zzaK(Status paramStatus)
  {
    return new zzd(null, paramStatus);
  }
  
  public Status getStatus()
  {
    return this.zzOt;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/personalized/zzd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
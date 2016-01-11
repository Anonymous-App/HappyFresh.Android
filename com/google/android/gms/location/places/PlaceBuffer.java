package com.google.android.gms.location.places;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.places.internal.zzr;

public class PlaceBuffer
  extends AbstractDataBuffer<Place>
  implements Result
{
  private final Context mContext;
  private final Status zzOt;
  private final String zzazB;
  
  public PlaceBuffer(DataHolder paramDataHolder, Context paramContext)
  {
    super(paramDataHolder);
    this.mContext = paramContext;
    this.zzOt = PlacesStatusCodes.zzgU(paramDataHolder.getStatusCode());
    if ((paramDataHolder != null) && (paramDataHolder.zznb() != null))
    {
      this.zzazB = paramDataHolder.zznb().getString("com.google.android.gms.location.places.PlaceBuffer.ATTRIBUTIONS_EXTRA_KEY");
      return;
    }
    this.zzazB = null;
  }
  
  public Place get(int paramInt)
  {
    return new zzr(this.zzWu, paramInt, this.mContext);
  }
  
  public CharSequence getAttributions()
  {
    return this.zzazB;
  }
  
  public Status getStatus()
  {
    return this.zzOt;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/PlaceBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
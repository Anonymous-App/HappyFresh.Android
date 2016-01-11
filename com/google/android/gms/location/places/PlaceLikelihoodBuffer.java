package com.google.android.gms.location.places;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import com.google.android.gms.location.places.internal.zzm;

public class PlaceLikelihoodBuffer
  extends AbstractDataBuffer<PlaceLikelihood>
  implements Result
{
  private final Context mContext;
  private final Status zzOt;
  private final String zzazB;
  private final int zzazG;
  
  public PlaceLikelihoodBuffer(DataHolder paramDataHolder, int paramInt, Context paramContext)
  {
    super(paramDataHolder);
    this.mContext = paramContext;
    this.zzOt = PlacesStatusCodes.zzgU(paramDataHolder.getStatusCode());
    this.zzazG = zza.zzgO(paramInt);
    if ((paramDataHolder != null) && (paramDataHolder.zznb() != null))
    {
      this.zzazB = paramDataHolder.zznb().getString("com.google.android.gms.location.places.PlaceLikelihoodBuffer.ATTRIBUTIONS_EXTRA_KEY");
      return;
    }
    this.zzazB = null;
  }
  
  public PlaceLikelihood get(int paramInt)
  {
    return new zzm(this.zzWu, paramInt, this.mContext);
  }
  
  public CharSequence getAttributions()
  {
    return this.zzazB;
  }
  
  public Status getStatus()
  {
    return this.zzOt;
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("status", getStatus()).zzg("attributions", this.zzazB).toString();
  }
  
  public static class zza
  {
    static int zzgO(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        throw new IllegalArgumentException("invalid source: " + paramInt);
      }
      return paramInt;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/PlaceLikelihoodBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
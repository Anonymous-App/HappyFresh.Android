package com.google.android.gms.location.places.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.places.zzg;

public class zzq
  extends zzt
  implements zzg
{
  private final String zzaAW = getString("photo_fife_url");
  
  public zzq(DataHolder paramDataHolder, int paramInt)
  {
    super(paramDataHolder, paramInt);
  }
  
  public CharSequence getAttributions()
  {
    return zzB("photo_attributions", null);
  }
  
  public int getMaxHeight()
  {
    return zzz("photo_max_height", 0);
  }
  
  public int getMaxWidth()
  {
    return zzz("photo_max_width", 0);
  }
  
  public zzg zzuZ()
  {
    return new zzp(this.zzaAW, getMaxWidth(), getMaxHeight(), getAttributions(), this.zzYs);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
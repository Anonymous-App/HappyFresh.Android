package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import com.google.android.gms.common.internal.zzu;

public class StreetViewPanoramaOrientation
  implements SafeParcelable
{
  public static final zzl CREATOR = new zzl();
  public final float bearing;
  public final float tilt;
  private final int zzCY;
  
  public StreetViewPanoramaOrientation(float paramFloat1, float paramFloat2)
  {
    this(1, paramFloat1, paramFloat2);
  }
  
  StreetViewPanoramaOrientation(int paramInt, float paramFloat1, float paramFloat2)
  {
    if ((-90.0F <= paramFloat1) && (paramFloat1 <= 90.0F)) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zzb(bool, "Tilt needs to be between -90 and 90 inclusive");
      this.zzCY = paramInt;
      this.tilt = (0.0F + paramFloat1);
      paramFloat1 = paramFloat2;
      if (paramFloat2 <= 0.0D) {
        paramFloat1 = paramFloat2 % 360.0F + 360.0F;
      }
      this.bearing = (paramFloat1 % 360.0F);
      return;
    }
  }
  
  public static Builder builder()
  {
    return new Builder();
  }
  
  public static Builder builder(StreetViewPanoramaOrientation paramStreetViewPanoramaOrientation)
  {
    return new Builder(paramStreetViewPanoramaOrientation);
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
      if (!(paramObject instanceof StreetViewPanoramaOrientation)) {
        return false;
      }
      paramObject = (StreetViewPanoramaOrientation)paramObject;
    } while ((Float.floatToIntBits(this.tilt) == Float.floatToIntBits(((StreetViewPanoramaOrientation)paramObject).tilt)) && (Float.floatToIntBits(this.bearing) == Float.floatToIntBits(((StreetViewPanoramaOrientation)paramObject).bearing)));
    return false;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { Float.valueOf(this.tilt), Float.valueOf(this.bearing) });
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("tilt", Float.valueOf(this.tilt)).zzg("bearing", Float.valueOf(this.bearing)).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzl.zza(this, paramParcel, paramInt);
  }
  
  public static final class Builder
  {
    public float bearing;
    public float tilt;
    
    public Builder() {}
    
    public Builder(StreetViewPanoramaOrientation paramStreetViewPanoramaOrientation)
    {
      this.bearing = paramStreetViewPanoramaOrientation.bearing;
      this.tilt = paramStreetViewPanoramaOrientation.tilt;
    }
    
    public Builder bearing(float paramFloat)
    {
      this.bearing = paramFloat;
      return this;
    }
    
    public StreetViewPanoramaOrientation build()
    {
      return new StreetViewPanoramaOrientation(this.tilt, this.bearing);
    }
    
    public Builder tilt(float paramFloat)
    {
      this.tilt = paramFloat;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/StreetViewPanoramaOrientation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import java.util.Locale;

public class PlacesParams
  implements SafeParcelable
{
  public static final zzs CREATOR = new zzs();
  public static final PlacesParams zzaAY = new PlacesParams("com.google.android.gms", Locale.getDefault(), null);
  public final int versionCode;
  public final String zzaAZ;
  public final String zzaBa;
  public final String zzaBb;
  public final String zzaBc;
  public final int zzaBd;
  public final String zzazX;
  
  public PlacesParams(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt2)
  {
    this.versionCode = paramInt1;
    this.zzaAZ = paramString1;
    this.zzaBa = paramString2;
    this.zzaBb = paramString3;
    this.zzazX = paramString4;
    this.zzaBc = paramString5;
    this.zzaBd = paramInt2;
  }
  
  public PlacesParams(String paramString1, Locale paramLocale, String paramString2)
  {
    this(1, paramString1, paramLocale.toString(), paramString2, null, null, GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE);
  }
  
  public PlacesParams(String paramString1, Locale paramLocale, String paramString2, String paramString3, String paramString4)
  {
    this(1, paramString1, paramLocale.toString(), paramString2, paramString3, paramString4, GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE);
  }
  
  public int describeContents()
  {
    zzs localzzs = CREATOR;
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if ((paramObject == null) || (!(paramObject instanceof PlacesParams))) {
        return false;
      }
      paramObject = (PlacesParams)paramObject;
    } while ((this.zzaBa.equals(((PlacesParams)paramObject).zzaBa)) && (this.zzaAZ.equals(((PlacesParams)paramObject).zzaAZ)) && (zzt.equal(this.zzaBb, ((PlacesParams)paramObject).zzaBb)) && (zzt.equal(this.zzazX, ((PlacesParams)paramObject).zzazX)) && (zzt.equal(this.zzaBc, ((PlacesParams)paramObject).zzaBc)));
    return false;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { this.zzaAZ, this.zzaBa, this.zzaBb, this.zzazX, this.zzaBc });
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("clientPackageName", this.zzaAZ).zzg("locale", this.zzaBa).zzg("accountName", this.zzaBb).zzg("gCoreClientName", this.zzazX).zzg("chargedPackageName", this.zzaBc).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzs localzzs = CREATOR;
    zzs.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/PlacesParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
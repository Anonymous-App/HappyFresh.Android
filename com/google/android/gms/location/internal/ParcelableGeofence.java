package com.google.android.gms.location.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.Geofence;
import java.util.Locale;

public class ParcelableGeofence
  implements SafeParcelable, Geofence
{
  public static final zzm CREATOR = new zzm();
  private final int zzCY;
  private final String zzDK;
  private final short zzaxA;
  private final double zzaxB;
  private final double zzaxC;
  private final float zzaxD;
  private final int zzaxE;
  private final int zzaxF;
  private final int zzaxy;
  private final long zzazj;
  
  public ParcelableGeofence(int paramInt1, String paramString, int paramInt2, short paramShort, double paramDouble1, double paramDouble2, float paramFloat, long paramLong, int paramInt3, int paramInt4)
  {
    zzdn(paramString);
    zze(paramFloat);
    zza(paramDouble1, paramDouble2);
    paramInt2 = zzgG(paramInt2);
    this.zzCY = paramInt1;
    this.zzaxA = paramShort;
    this.zzDK = paramString;
    this.zzaxB = paramDouble1;
    this.zzaxC = paramDouble2;
    this.zzaxD = paramFloat;
    this.zzazj = paramLong;
    this.zzaxy = paramInt2;
    this.zzaxE = paramInt3;
    this.zzaxF = paramInt4;
  }
  
  public ParcelableGeofence(String paramString, int paramInt1, short paramShort, double paramDouble1, double paramDouble2, float paramFloat, long paramLong, int paramInt2, int paramInt3)
  {
    this(1, paramString, paramInt1, paramShort, paramDouble1, paramDouble2, paramFloat, paramLong, paramInt2, paramInt3);
  }
  
  private static void zza(double paramDouble1, double paramDouble2)
  {
    if ((paramDouble1 > 90.0D) || (paramDouble1 < -90.0D)) {
      throw new IllegalArgumentException("invalid latitude: " + paramDouble1);
    }
    if ((paramDouble2 > 180.0D) || (paramDouble2 < -180.0D)) {
      throw new IllegalArgumentException("invalid longitude: " + paramDouble2);
    }
  }
  
  private static void zzdn(String paramString)
  {
    if ((paramString == null) || (paramString.length() > 100)) {
      throw new IllegalArgumentException("requestId is null or too long: " + paramString);
    }
  }
  
  private static void zze(float paramFloat)
  {
    if (paramFloat <= 0.0F) {
      throw new IllegalArgumentException("invalid radius: " + paramFloat);
    }
  }
  
  private static int zzgG(int paramInt)
  {
    int i = paramInt & 0x7;
    if (i == 0) {
      throw new IllegalArgumentException("No supported transition specified: " + paramInt);
    }
    return i;
  }
  
  private static String zzgH(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return null;
    }
    return "CIRCLE";
  }
  
  public static ParcelableGeofence zzn(byte[] paramArrayOfByte)
  {
    Parcel localParcel = Parcel.obtain();
    localParcel.unmarshall(paramArrayOfByte, 0, paramArrayOfByte.length);
    localParcel.setDataPosition(0);
    paramArrayOfByte = CREATOR.zzem(localParcel);
    localParcel.recycle();
    return paramArrayOfByte;
  }
  
  public int describeContents()
  {
    zzm localzzm = CREATOR;
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (!(paramObject instanceof ParcelableGeofence)) {
        return false;
      }
      paramObject = (ParcelableGeofence)paramObject;
      if (this.zzaxD != ((ParcelableGeofence)paramObject).zzaxD) {
        return false;
      }
      if (this.zzaxB != ((ParcelableGeofence)paramObject).zzaxB) {
        return false;
      }
      if (this.zzaxC != ((ParcelableGeofence)paramObject).zzaxC) {
        return false;
      }
    } while (this.zzaxA == ((ParcelableGeofence)paramObject).zzaxA);
    return false;
  }
  
  public long getExpirationTime()
  {
    return this.zzazj;
  }
  
  public double getLatitude()
  {
    return this.zzaxB;
  }
  
  public double getLongitude()
  {
    return this.zzaxC;
  }
  
  public int getNotificationResponsiveness()
  {
    return this.zzaxE;
  }
  
  public String getRequestId()
  {
    return this.zzDK;
  }
  
  public int getVersionCode()
  {
    return this.zzCY;
  }
  
  public int hashCode()
  {
    long l = Double.doubleToLongBits(this.zzaxB);
    int i = (int)(l ^ l >>> 32);
    l = Double.doubleToLongBits(this.zzaxC);
    return ((((i + 31) * 31 + (int)(l ^ l >>> 32)) * 31 + Float.floatToIntBits(this.zzaxD)) * 31 + this.zzaxA) * 31 + this.zzaxy;
  }
  
  public String toString()
  {
    return String.format(Locale.US, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", new Object[] { zzgH(this.zzaxA), this.zzDK, Integer.valueOf(this.zzaxy), Double.valueOf(this.zzaxB), Double.valueOf(this.zzaxC), Float.valueOf(this.zzaxD), Integer.valueOf(this.zzaxE / 1000), Integer.valueOf(this.zzaxF), Long.valueOf(this.zzazj) });
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzm localzzm = CREATOR;
    zzm.zza(this, paramParcel, paramInt);
  }
  
  public short zzuA()
  {
    return this.zzaxA;
  }
  
  public float zzuB()
  {
    return this.zzaxD;
  }
  
  public int zzuC()
  {
    return this.zzaxy;
  }
  
  public int zzuD()
  {
    return this.zzaxF;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/internal/ParcelableGeofence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.google.android.gms.location.places;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzu;

public class PlacesStatusCodes
  extends CommonStatusCodes
{
  public static final int ACCESS_NOT_CONFIGURED = 9003;
  public static final int DEVICE_RATE_LIMIT_EXCEEDED = 9006;
  public static final int INVALID_ARGUMENT = 9004;
  public static final int KEY_EXPIRED = 9007;
  public static final int KEY_INVALID = 9002;
  public static final int RATE_LIMIT_EXCEEDED = 9005;
  public static final int USAGE_LIMIT_EXCEEDED = 9001;
  
  public static String getStatusCodeString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return CommonStatusCodes.getStatusCodeString(paramInt);
    case 9000: 
      return "PLACES_API_QUOTA_FAILED";
    case 9001: 
      return "PLACES_API_USAGE_LIMIT_EXCEEDED";
    case 9002: 
      return "PLACES_API_KEY_INVALID";
    case 9003: 
      return "PLACES_API_ACCESS_NOT_CONFIGURED";
    case 9004: 
      return "PLACES_API_INVALID_ARGUMENT";
    case 9005: 
      return "PLACES_API_RATE_LIMIT_EXCEEDED";
    case 9006: 
      return "PLACES_API_DEVICE_RATE_LIMIT_EXCEEDED";
    }
    return "PLACES_API_KEY_EXPIRED";
  }
  
  public static Status zzgU(int paramInt)
  {
    return zzi(paramInt, getStatusCodeString(paramInt));
  }
  
  public static Status zzi(int paramInt, String paramString)
  {
    zzu.zzu(paramString);
    return new Status(paramInt, paramString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/PlacesStatusCodes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
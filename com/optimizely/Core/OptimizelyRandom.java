package com.optimizely.Core;

import android.support.annotation.NonNull;

class OptimizelyRandom
{
  public static final int BUCKETING_SEED = 1;
  public static final int IGNORING_SEED = 0;
  private static final long MAX = 4294967296L;
  private static final int TOTAL_TRAFFIC_VALUE = 10000;
  
  private static int hash(@NonNull String paramString, int paramInt)
  {
    return MurmurHash3.murmurhash3_x86_32(paramString, 0, paramString.length(), paramInt);
  }
  
  private static int hash(@NonNull String paramString, int paramInt1, int paramInt2)
  {
    double d = (hash(paramString, paramInt1) & 0xFFFFFFFF) * 1.0D / 4.294967296E9D;
    return (int)Math.floor(paramInt2 * d);
  }
  
  public static int optimizelyRandom(String paramString1, int paramInt, String paramString2)
  {
    return hash(String.format("%1$s%2$s", new Object[] { paramString2, paramString1 }), paramInt, 10000);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Core/OptimizelyRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;

class zzm<K, V>
{
  final zza<K, V> zzaKw = new zza()
  {
    public int sizeOf(K paramAnonymousK, V paramAnonymousV)
    {
      return 1;
    }
  };
  
  public zzl<K, V> zza(int paramInt, zza<K, V> paramzza)
  {
    if (paramInt <= 0) {
      throw new IllegalArgumentException("maxSize <= 0");
    }
    if (zzyj() < 12) {
      return new zzcw(paramInt, paramzza);
    }
    return new zzba(paramInt, paramzza);
  }
  
  int zzyj()
  {
    return Build.VERSION.SDK_INT;
  }
  
  public static abstract interface zza<K, V>
  {
    public abstract int sizeOf(K paramK, V paramV);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
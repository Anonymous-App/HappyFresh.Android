package com.google.android.gms.tagmanager;

import android.util.LruCache;

class zzba<K, V>
  implements zzl<K, V>
{
  private LruCache<K, V> zzaMe;
  
  zzba(int paramInt, final zzm.zza<K, V> paramzza)
  {
    this.zzaMe = new LruCache(paramInt)
    {
      protected int sizeOf(K paramAnonymousK, V paramAnonymousV)
      {
        return paramzza.sizeOf(paramAnonymousK, paramAnonymousV);
      }
    };
  }
  
  public V get(K paramK)
  {
    return (V)this.zzaMe.get(paramK);
  }
  
  public void zzf(K paramK, V paramV)
  {
    this.zzaMe.put(paramK, paramV);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzba.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
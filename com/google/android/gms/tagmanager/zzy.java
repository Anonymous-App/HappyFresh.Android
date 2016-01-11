package com.google.android.gms.tagmanager;

import android.util.Log;

public class zzy
  implements zzbh
{
  private int zzKR = 5;
  
  public void setLogLevel(int paramInt)
  {
    this.zzKR = paramInt;
  }
  
  public void zzaA(String paramString)
  {
    if (this.zzKR <= 4) {
      Log.i("GoogleTagManager", paramString);
    }
  }
  
  public void zzaB(String paramString)
  {
    if (this.zzKR <= 2) {
      Log.v("GoogleTagManager", paramString);
    }
  }
  
  public void zzaC(String paramString)
  {
    if (this.zzKR <= 5) {
      Log.w("GoogleTagManager", paramString);
    }
  }
  
  public void zzay(String paramString)
  {
    if (this.zzKR <= 3) {
      Log.d("GoogleTagManager", paramString);
    }
  }
  
  public void zzaz(String paramString)
  {
    if (this.zzKR <= 6) {
      Log.e("GoogleTagManager", paramString);
    }
  }
  
  public void zzb(String paramString, Throwable paramThrowable)
  {
    if (this.zzKR <= 6) {
      Log.e("GoogleTagManager", paramString, paramThrowable);
    }
  }
  
  public void zzd(String paramString, Throwable paramThrowable)
  {
    if (this.zzKR <= 5) {
      Log.w("GoogleTagManager", paramString, paramThrowable);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.GoogleApiClient.ServerAuthCodeCallbacks;
import com.google.android.gms.common.internal.zzu;

public final class zzpt
  implements Api.ApiOptions.Optional
{
  public static final zzpt zzaJQ = new zza().zzyc();
  private final boolean zzaJR;
  private final boolean zzaJS;
  private final String zzaJT;
  private final GoogleApiClient.ServerAuthCodeCallbacks zzaJU;
  
  private zzpt(boolean paramBoolean1, boolean paramBoolean2, String paramString, GoogleApiClient.ServerAuthCodeCallbacks paramServerAuthCodeCallbacks)
  {
    this.zzaJR = paramBoolean1;
    this.zzaJS = paramBoolean2;
    this.zzaJT = paramString;
    this.zzaJU = paramServerAuthCodeCallbacks;
  }
  
  public boolean zzxZ()
  {
    return this.zzaJR;
  }
  
  public String zzxt()
  {
    return this.zzaJT;
  }
  
  public boolean zzya()
  {
    return this.zzaJS;
  }
  
  public GoogleApiClient.ServerAuthCodeCallbacks zzyb()
  {
    return this.zzaJU;
  }
  
  public static final class zza
  {
    private String zzaHb;
    private boolean zzaJV;
    private boolean zzaJW;
    private GoogleApiClient.ServerAuthCodeCallbacks zzaJX;
    
    private String zzea(String paramString)
    {
      zzu.zzu(paramString);
      if ((this.zzaHb == null) || (this.zzaHb.equals(paramString))) {}
      for (boolean bool = true;; bool = false)
      {
        zzu.zzb(bool, "two different server client ids provided");
        return paramString;
      }
    }
    
    public zza zza(String paramString, GoogleApiClient.ServerAuthCodeCallbacks paramServerAuthCodeCallbacks)
    {
      this.zzaJV = true;
      this.zzaJW = true;
      this.zzaHb = zzea(paramString);
      this.zzaJX = ((GoogleApiClient.ServerAuthCodeCallbacks)zzu.zzu(paramServerAuthCodeCallbacks));
      return this;
    }
    
    public zzpt zzyc()
    {
      return new zzpt(this.zzaJV, this.zzaJW, this.zzaHb, this.zzaJX, null);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzpt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
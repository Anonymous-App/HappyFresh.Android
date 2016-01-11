package com.google.android.gms.tagmanager;

import android.net.Uri;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

class zzcb
{
  private static zzcb zzaME;
  private volatile String zzaKy;
  private volatile zza zzaMF;
  private volatile String zzaMG;
  private volatile String zzaMH;
  
  zzcb()
  {
    clear();
  }
  
  private String zzeA(String paramString)
  {
    return paramString.split("&")[0].split("=")[1];
  }
  
  private String zzm(Uri paramUri)
  {
    return paramUri.getQuery().replace("&gtm_debug=x", "");
  }
  
  static zzcb zzzf()
  {
    try
    {
      if (zzaME == null) {
        zzaME = new zzcb();
      }
      zzcb localzzcb = zzaME;
      return localzzcb;
    }
    finally {}
  }
  
  void clear()
  {
    this.zzaMF = zza.zzaMI;
    this.zzaMG = null;
    this.zzaKy = null;
    this.zzaMH = null;
  }
  
  String getContainerId()
  {
    return this.zzaKy;
  }
  
  boolean zzl(Uri paramUri)
  {
    boolean bool = true;
    String str;
    try
    {
      str = URLDecoder.decode(paramUri.toString(), "UTF-8");
      if (!str.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) {
        break label153;
      }
      zzbg.zzaB("Container preview url: " + str);
      if (!str.matches(".*?&gtm_debug=x$")) {
        break label138;
      }
      this.zzaMF = zza.zzaMK;
    }
    catch (UnsupportedEncodingException paramUri)
    {
      for (;;)
      {
        bool = false;
        continue;
        this.zzaMF = zza.zzaMJ;
      }
    }
    finally {}
    this.zzaMH = zzm(paramUri);
    if ((this.zzaMF == zza.zzaMJ) || (this.zzaMF == zza.zzaMK)) {
      this.zzaMG = ("/r?" + this.zzaMH);
    }
    this.zzaKy = zzeA(this.zzaMH);
    for (;;)
    {
      return bool;
      label138:
      label153:
      if (str.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$"))
      {
        if (zzeA(paramUri.getQuery()).equals(this.zzaKy))
        {
          zzbg.zzaB("Exit preview mode for container: " + this.zzaKy);
          this.zzaMF = zza.zzaMI;
          this.zzaMG = null;
        }
      }
      else
      {
        zzbg.zzaC("Invalid preview uri: " + str);
        bool = false;
        continue;
      }
      bool = false;
    }
  }
  
  zza zzzg()
  {
    return this.zzaMF;
  }
  
  String zzzh()
  {
    return this.zzaMG;
  }
  
  static enum zza
  {
    private zza() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzcb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
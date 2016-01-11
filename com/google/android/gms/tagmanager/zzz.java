package com.google.android.gms.tagmanager;

import android.content.Context;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class zzz
  implements zzar
{
  private static final Object zzaKl = new Object();
  private static zzz zzaLA;
  private zzcd zzaKO;
  private String zzaLB;
  private String zzaLC;
  private zzas zzaLD;
  
  private zzz(Context paramContext)
  {
    this(zzat.zzaH(paramContext), new zzcs());
  }
  
  zzz(zzas paramzzas, zzcd paramzzcd)
  {
    this.zzaLD = paramzzas;
    this.zzaKO = paramzzcd;
  }
  
  public static zzar zzaF(Context paramContext)
  {
    synchronized (zzaKl)
    {
      if (zzaLA == null) {
        zzaLA = new zzz(paramContext);
      }
      paramContext = zzaLA;
      return paramContext;
    }
  }
  
  public boolean zzes(String paramString)
  {
    if (!this.zzaKO.zzkb())
    {
      zzbg.zzaC("Too many urls sent too quickly with the TagManagerSender, rate limiting invoked.");
      return false;
    }
    String str = paramString;
    if (this.zzaLB != null)
    {
      str = paramString;
      if (this.zzaLC == null) {}
    }
    try
    {
      str = this.zzaLB + "?" + this.zzaLC + "=" + URLEncoder.encode(paramString, "UTF-8");
      zzbg.zzaB("Sending wrapped url hit: " + str);
      this.zzaLD.zzew(str);
      return true;
    }
    catch (UnsupportedEncodingException paramString)
    {
      zzbg.zzd("Error wrapping URL for testing.", paramString);
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
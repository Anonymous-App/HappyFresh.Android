package com.google.android.gms.iid;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class InstanceID
{
  public static final String ERROR_BACKOFF = "RETRY_LATER";
  public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
  public static final String ERROR_MISSING_INSTANCEID_SERVICE = "MISSING_INSTANCEID_SERVICE";
  public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
  public static final String ERROR_TIMEOUT = "TIMEOUT";
  static Map<String, InstanceID> zzawN = new HashMap();
  private static zzd zzawO;
  private static zzc zzawP;
  static String zzawT;
  Context mContext;
  KeyPair zzawQ;
  String zzawR = "";
  long zzawS;
  
  protected InstanceID(Context paramContext, String paramString, Bundle paramBundle)
  {
    this.mContext = paramContext.getApplicationContext();
    this.zzawR = paramString;
  }
  
  public static InstanceID getInstance(Context paramContext)
  {
    return zza(paramContext, null);
  }
  
  public static InstanceID zza(Context paramContext, Bundle paramBundle)
  {
    String str;
    if (paramBundle == null) {
      str = "";
    }
    for (;;)
    {
      try
      {
        Context localContext = paramContext.getApplicationContext();
        if (zzawO == null)
        {
          zzawO = new zzd(localContext);
          zzawP = new zzc(localContext);
        }
        zzawT = Integer.toString(zzau(localContext));
        InstanceID localInstanceID = (InstanceID)zzawN.get(str);
        paramContext = localInstanceID;
        if (localInstanceID == null)
        {
          paramContext = new InstanceID(localContext, str, paramBundle);
          zzawN.put(str, paramContext);
        }
        return paramContext;
      }
      finally {}
      str = paramBundle.getString("subtype");
      while (str != null) {
        break;
      }
      str = "";
    }
  }
  
  static String zza(KeyPair paramKeyPair)
  {
    paramKeyPair = paramKeyPair.getPublic().getEncoded();
    try
    {
      paramKeyPair = MessageDigest.getInstance("SHA1").digest(paramKeyPair);
      paramKeyPair[0] = ((byte)((paramKeyPair[0] & 0xF) + 112 & 0xFF));
      paramKeyPair = Base64.encodeToString(paramKeyPair, 0, 8, 11);
      return paramKeyPair;
    }
    catch (NoSuchAlgorithmException paramKeyPair)
    {
      Log.w("InstanceID", "Unexpected error, device missing required alghorithms");
    }
    return null;
  }
  
  static int zzau(Context paramContext)
  {
    try
    {
      int i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
      return i;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      Log.w("InstanceID", "Never happens: can't find own package " + paramContext);
    }
    return 0;
  }
  
  static String zzm(byte[] paramArrayOfByte)
  {
    return Base64.encodeToString(paramArrayOfByte, 11);
  }
  
  public void deleteInstanceID()
    throws IOException
  {
    zzb("*", "*", null);
    zzuf();
  }
  
  public void deleteToken(String paramString1, String paramString2)
    throws IOException
  {
    zzb(paramString1, paramString2, null);
  }
  
  public long getCreationTime()
  {
    if (this.zzawS == 0L)
    {
      String str = zzawO.get(this.zzawR, "cre");
      if (str != null) {
        this.zzawS = Long.parseLong(str);
      }
    }
    return this.zzawS;
  }
  
  public String getId()
  {
    return zza(zzue());
  }
  
  public String getToken(String paramString1, String paramString2)
    throws IOException
  {
    return getToken(paramString1, paramString2, null);
  }
  
  public String getToken(String paramString1, String paramString2, Bundle paramBundle)
    throws IOException
  {
    if (Looper.getMainLooper() == Looper.myLooper()) {
      throw new IOException("MAIN_THREAD");
    }
    Object localObject;
    if (zzui())
    {
      localObject = null;
      if (localObject == null) {
        break label54;
      }
    }
    label54:
    do
    {
      return (String)localObject;
      localObject = zzawO.zzg(this.zzawR, paramString1, paramString2);
      break;
      localObject = paramBundle;
      if (paramBundle == null) {
        localObject = new Bundle();
      }
      paramBundle = zzc(paramString1, paramString2, (Bundle)localObject);
      localObject = paramBundle;
    } while (paramBundle == null);
    zzawO.zza(this.zzawR, paramString1, paramString2, paramBundle, zzawT);
    return paramBundle;
  }
  
  public void zzb(String paramString1, String paramString2, Bundle paramBundle)
    throws IOException
  {
    if (Looper.getMainLooper() == Looper.myLooper()) {
      throw new IOException("MAIN_THREAD");
    }
    zzawO.zzh(this.zzawR, paramString1, paramString2);
    Bundle localBundle = paramBundle;
    if (paramBundle == null) {
      localBundle = new Bundle();
    }
    localBundle.putString("sender", paramString1);
    if (paramString2 != null) {
      localBundle.putString("scope", paramString2);
    }
    localBundle.putString("subscription", paramString1);
    localBundle.putString("delete", "1");
    localBundle.putString("X-delete", "1");
    if ("".equals(this.zzawR))
    {
      paramString2 = paramString1;
      localBundle.putString("subtype", paramString2);
      if (!"".equals(this.zzawR)) {
        break label173;
      }
    }
    for (;;)
    {
      localBundle.putString("X-subtype", paramString1);
      paramString1 = zzawP.zza(localBundle, zzue());
      zzawP.zzp(paramString1);
      return;
      paramString2 = this.zzawR;
      break;
      label173:
      paramString1 = this.zzawR;
    }
  }
  
  public String zzc(String paramString1, String paramString2, Bundle paramBundle)
    throws IOException
  {
    if (paramString2 != null) {
      paramBundle.putString("scope", paramString2);
    }
    paramBundle.putString("sender", paramString1);
    if ("".equals(this.zzawR)) {}
    for (paramString2 = paramString1;; paramString2 = this.zzawR)
    {
      if (!paramBundle.containsKey("legacy.register"))
      {
        paramBundle.putString("subscription", paramString1);
        paramBundle.putString("subtype", paramString2);
        paramBundle.putString("X-subscription", paramString1);
        paramBundle.putString("X-subtype", paramString2);
      }
      paramString1 = zzawP.zza(paramBundle, zzue());
      return zzawP.zzp(paramString1);
    }
  }
  
  KeyPair zzue()
  {
    if (this.zzawQ == null) {
      this.zzawQ = zzawO.zzdg(this.zzawR);
    }
    if (this.zzawQ == null)
    {
      this.zzawS = System.currentTimeMillis();
      this.zzawQ = zzawO.zze(this.zzawR, this.zzawS);
    }
    return this.zzawQ;
  }
  
  void zzuf()
  {
    this.zzawS = 0L;
    zzawO.zzdh(this.zzawR);
    this.zzawQ = null;
  }
  
  zzd zzug()
  {
    return zzawO;
  }
  
  zzc zzuh()
  {
    return zzawP;
  }
  
  boolean zzui()
  {
    String str = zzawO.get("appVersion");
    if ((str == null) || (!str.equals(zzawT))) {}
    long l;
    do
    {
      do
      {
        return true;
        str = zzawO.get("lastToken");
      } while (str == null);
      l = Long.parseLong(str);
    } while (System.currentTimeMillis() / 1000L - Long.valueOf(l).longValue() > 604800L);
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/iid/InstanceID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
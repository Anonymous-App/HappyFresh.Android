package com.google.android.gms.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class zzd
{
  SharedPreferences zzaxq;
  Context zzpH;
  
  public zzd(Context paramContext)
  {
    this(paramContext, "com.google.android.gms.appid");
  }
  
  public zzd(Context paramContext, String paramString)
  {
    this.zzpH = paramContext;
    this.zzaxq = paramContext.getSharedPreferences(paramString, 4);
    zzde(paramString + "-no-backup");
  }
  
  private void zzde(String paramString)
  {
    paramString = new File(new ContextCompat().getNoBackupFilesDir(this.zzpH), paramString);
    if (paramString.exists()) {}
    do
    {
      for (;;)
      {
        return;
        try
        {
          if ((paramString.createNewFile()) && (!isEmpty()))
          {
            Log.i("InstanceID/Store", "App restored, clearing state");
            InstanceIDListenerService.zza(this.zzpH, this);
            return;
          }
        }
        catch (IOException paramString) {}
      }
    } while (!Log.isLoggable("InstanceID/Store", 3));
    Log.d("InstanceID/Store", "Error creating file in no backup dir: " + paramString.getMessage());
  }
  
  private String zzf(String paramString1, String paramString2, String paramString3)
  {
    return paramString1 + "|T|" + paramString2 + "|" + paramString3;
  }
  
  String get(String paramString)
  {
    try
    {
      paramString = this.zzaxq.getString(paramString, null);
      return paramString;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  String get(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = this.zzaxq.getString(paramString1 + "|S|" + paramString2, null);
      return paramString1;
    }
    finally
    {
      paramString1 = finally;
      throw paramString1;
    }
  }
  
  boolean isEmpty()
  {
    return this.zzaxq.getAll().isEmpty();
  }
  
  public void zza(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    try
    {
      paramString1 = zzf(paramString1, paramString2, paramString3);
      paramString2 = this.zzaxq.edit();
      paramString2.putString(paramString1, paramString4);
      paramString2.putString("appVersion", paramString5);
      paramString2.putString("lastToken", Long.toString(System.currentTimeMillis() / 1000L));
      paramString2.commit();
      return;
    }
    finally
    {
      paramString1 = finally;
      throw paramString1;
    }
  }
  
  public void zzdf(String paramString)
  {
    try
    {
      SharedPreferences.Editor localEditor = this.zzaxq.edit();
      Iterator localIterator = this.zzaxq.getAll().keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (str.startsWith(paramString)) {
          localEditor.remove(str);
        }
      }
      localEditor.commit();
    }
    finally {}
  }
  
  public KeyPair zzdg(String paramString)
  {
    return zzdj(paramString);
  }
  
  void zzdh(String paramString)
  {
    zzdf(paramString + "|");
  }
  
  public void zzdi(String paramString)
  {
    zzdf(paramString + "|T|");
  }
  
  KeyPair zzdj(String paramString)
  {
    Object localObject1 = get(paramString, "|P|");
    Object localObject2 = get(paramString, "|K|");
    if (localObject2 == null) {
      return null;
    }
    try
    {
      paramString = Base64.decode((String)localObject1, 8);
      localObject1 = Base64.decode((String)localObject2, 8);
      localObject2 = KeyFactory.getInstance("RSA");
      paramString = new KeyPair(((KeyFactory)localObject2).generatePublic(new X509EncodedKeySpec(paramString)), ((KeyFactory)localObject2).generatePrivate(new PKCS8EncodedKeySpec((byte[])localObject1)));
      return paramString;
    }
    catch (InvalidKeySpecException paramString)
    {
      Log.w("InstanceID/Store", "Invalid key stored " + paramString);
      InstanceIDListenerService.zza(this.zzpH, this);
      return null;
    }
    catch (NoSuchAlgorithmException paramString)
    {
      for (;;) {}
    }
  }
  
  KeyPair zze(String paramString, long paramLong)
  {
    try
    {
      KeyPair localKeyPair = zza.zzud();
      this.zzaxq.edit().putString(paramString + "|P|", InstanceID.zzm(localKeyPair.getPublic().getEncoded())).putString(paramString + "|K|", InstanceID.zzm(localKeyPair.getPrivate().getEncoded())).putString(paramString + "|S|" + "cre", Long.toString(paramLong)).commit();
      return localKeyPair;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public String zzg(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      paramString1 = zzf(paramString1, paramString2, paramString3);
      paramString1 = this.zzaxq.getString(paramString1, null);
      return paramString1;
    }
    finally
    {
      paramString1 = finally;
      throw paramString1;
    }
  }
  
  public void zzh(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      paramString1 = zzf(paramString1, paramString2, paramString3);
      paramString2 = this.zzaxq.edit();
      paramString2.remove(paramString1);
      paramString2.commit();
      return;
    }
    finally
    {
      paramString1 = finally;
      throw paramString1;
    }
  }
  
  public void zzul()
  {
    try
    {
      this.zzaxq.edit().clear().commit();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/iid/zzd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
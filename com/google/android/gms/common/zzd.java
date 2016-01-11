package com.google.android.gms.common;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import java.util.Set;

public class zzd
{
  private static final zzd zzVY = new zzd();
  
  private boolean zza(PackageInfo paramPackageInfo, boolean paramBoolean)
  {
    if (paramPackageInfo.signatures.length != 1)
    {
      Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
      return false;
    }
    zzc.zzb localzzb = new zzc.zzb(paramPackageInfo.signatures[0].toByteArray());
    if (paramBoolean) {}
    for (paramPackageInfo = zzc.zzmi(); paramPackageInfo.contains(localzzb); paramPackageInfo = zzc.zzmj()) {
      return true;
    }
    if (Log.isLoggable("GoogleSignatureVerifier", 2)) {
      Log.v("GoogleSignatureVerifier", "Signature not valid.  Found: \n" + Base64.encodeToString(localzzb.getBytes(), 0));
    }
    return false;
  }
  
  public static zzd zzmn()
  {
    return zzVY;
  }
  
  zzc.zza zza(PackageInfo paramPackageInfo, zzc.zza... paramVarArgs)
  {
    if (paramPackageInfo.signatures.length != 1)
    {
      Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
      return null;
    }
    paramPackageInfo = new zzc.zzb(paramPackageInfo.signatures[0].toByteArray());
    int i = 0;
    while (i < paramVarArgs.length)
    {
      if (paramVarArgs[i].equals(paramPackageInfo)) {
        return paramVarArgs[i];
      }
      i += 1;
    }
    if (Log.isLoggable("GoogleSignatureVerifier", 2)) {
      Log.v("GoogleSignatureVerifier", "Signature not valid.  Found: \n" + Base64.encodeToString(paramPackageInfo.getBytes(), 0));
    }
    return null;
  }
  
  public boolean zza(PackageManager paramPackageManager, PackageInfo paramPackageInfo)
  {
    boolean bool1 = false;
    if (paramPackageInfo == null) {}
    boolean bool2;
    do
    {
      do
      {
        return bool1;
        if (GooglePlayServicesUtil.zzc(paramPackageManager)) {
          return zza(paramPackageInfo, true);
        }
        bool2 = zza(paramPackageInfo, false);
        bool1 = bool2;
      } while (bool2);
      bool1 = bool2;
    } while (!zza(paramPackageInfo, true));
    Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
    return bool2;
  }
  
  public boolean zzb(PackageManager paramPackageManager, String paramString)
  {
    try
    {
      PackageInfo localPackageInfo = paramPackageManager.getPackageInfo(paramString, 64);
      return zza(paramPackageManager, localPackageInfo);
    }
    catch (PackageManager.NameNotFoundException paramPackageManager)
    {
      if (Log.isLoggable("GoogleSignatureVerifier", 3)) {
        Log.d("GoogleSignatureVerifier", "Package manager can't find package " + paramString + ", defaulting to false");
      }
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/zzd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
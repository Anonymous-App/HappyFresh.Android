package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzg
  extends zzak
{
  private static final String ID = zzad.zzbf.toString();
  private final Context mContext;
  
  public zzg(Context paramContext)
  {
    super(ID, new String[0]);
    this.mContext = paramContext;
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    try
    {
      paramMap = this.mContext.getPackageManager();
      paramMap = zzdf.zzI(paramMap.getApplicationLabel(paramMap.getApplicationInfo(this.mContext.getPackageName(), 0)).toString());
      return paramMap;
    }
    catch (PackageManager.NameNotFoundException paramMap)
    {
      zzbg.zzb("App name is not found.", paramMap);
    }
    return zzdf.zzzQ();
  }
  
  public boolean zzyh()
  {
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
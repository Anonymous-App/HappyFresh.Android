package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzaf.zzf;
import com.google.android.gms.internal.zzaf.zzi;
import com.google.android.gms.internal.zzaf.zzj;
import com.google.android.gms.internal.zzag.zza;
import com.google.android.gms.internal.zzqf;
import com.google.android.gms.internal.zzqf.zzc;
import com.google.android.gms.internal.zzqf.zzg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container
{
  private final Context mContext;
  private zzcp zzaKA;
  private Map<String, FunctionCallMacroCallback> zzaKB = new HashMap();
  private Map<String, FunctionCallTagCallback> zzaKC = new HashMap();
  private volatile long zzaKD;
  private volatile String zzaKE = "";
  private final String zzaKy;
  private final DataLayer zzaKz;
  
  Container(Context paramContext, DataLayer paramDataLayer, String paramString, long paramLong, zzaf.zzj paramzzj)
  {
    this.mContext = paramContext;
    this.zzaKz = paramDataLayer;
    this.zzaKy = paramString;
    this.zzaKD = paramLong;
    zza(paramzzj.zziO);
    if (paramzzj.zziN != null) {
      zza(paramzzj.zziN);
    }
  }
  
  Container(Context paramContext, DataLayer paramDataLayer, String paramString, long paramLong, zzqf.zzc paramzzc)
  {
    this.mContext = paramContext;
    this.zzaKz = paramDataLayer;
    this.zzaKy = paramString;
    this.zzaKD = paramLong;
    zza(paramzzc);
  }
  
  private void zza(zzaf.zzf paramzzf)
  {
    if (paramzzf == null) {
      throw new NullPointerException();
    }
    try
    {
      zzqf.zzc localzzc = zzqf.zzb(paramzzf);
      zza(localzzc);
      return;
    }
    catch (zzqf.zzg localzzg)
    {
      zzbg.zzaz("Not loading resource: " + paramzzf + " because it is invalid: " + localzzg.toString());
    }
  }
  
  private void zza(zzqf.zzc paramzzc)
  {
    this.zzaKE = paramzzc.getVersion();
    zzah localzzah = zzei(this.zzaKE);
    zza(new zzcp(this.mContext, paramzzc, this.zzaKz, new zza(null), new zzb(null), localzzah));
    if (getBoolean("_gtm.loadEventEnabled")) {
      this.zzaKz.pushEvent("gtm.load", DataLayer.mapOf(new Object[] { "gtm.id", this.zzaKy }));
    }
  }
  
  private void zza(zzcp paramzzcp)
  {
    try
    {
      this.zzaKA = paramzzcp;
      return;
    }
    finally
    {
      paramzzcp = finally;
      throw paramzzcp;
    }
  }
  
  private void zza(zzaf.zzi[] paramArrayOfzzi)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramArrayOfzzi.length;
    int i = 0;
    while (i < j)
    {
      localArrayList.add(paramArrayOfzzi[i]);
      i += 1;
    }
    zzyn().zzs(localArrayList);
  }
  
  private zzcp zzyn()
  {
    try
    {
      zzcp localzzcp = this.zzaKA;
      return localzzcp;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getBoolean(String paramString)
  {
    zzcp localzzcp = zzyn();
    if (localzzcp == null)
    {
      zzbg.zzaz("getBoolean called for closed container.");
      return zzdf.zzzN().booleanValue();
    }
    try
    {
      boolean bool = zzdf.zzk((zzag.zza)localzzcp.zzeD(paramString).getObject()).booleanValue();
      return bool;
    }
    catch (Exception paramString)
    {
      zzbg.zzaz("Calling getBoolean() threw an exception: " + paramString.getMessage() + " Returning default value.");
    }
    return zzdf.zzzN().booleanValue();
  }
  
  public String getContainerId()
  {
    return this.zzaKy;
  }
  
  public double getDouble(String paramString)
  {
    zzcp localzzcp = zzyn();
    if (localzzcp == null)
    {
      zzbg.zzaz("getDouble called for closed container.");
      return zzdf.zzzM().doubleValue();
    }
    try
    {
      double d = zzdf.zzj((zzag.zza)localzzcp.zzeD(paramString).getObject()).doubleValue();
      return d;
    }
    catch (Exception paramString)
    {
      zzbg.zzaz("Calling getDouble() threw an exception: " + paramString.getMessage() + " Returning default value.");
    }
    return zzdf.zzzM().doubleValue();
  }
  
  public long getLastRefreshTime()
  {
    return this.zzaKD;
  }
  
  public long getLong(String paramString)
  {
    zzcp localzzcp = zzyn();
    if (localzzcp == null)
    {
      zzbg.zzaz("getLong called for closed container.");
      return zzdf.zzzL().longValue();
    }
    try
    {
      long l = zzdf.zzi((zzag.zza)localzzcp.zzeD(paramString).getObject()).longValue();
      return l;
    }
    catch (Exception paramString)
    {
      zzbg.zzaz("Calling getLong() threw an exception: " + paramString.getMessage() + " Returning default value.");
    }
    return zzdf.zzzL().longValue();
  }
  
  public String getString(String paramString)
  {
    zzcp localzzcp = zzyn();
    if (localzzcp == null)
    {
      zzbg.zzaz("getString called for closed container.");
      return zzdf.zzzP();
    }
    try
    {
      paramString = zzdf.zzg((zzag.zza)localzzcp.zzeD(paramString).getObject());
      return paramString;
    }
    catch (Exception paramString)
    {
      zzbg.zzaz("Calling getString() threw an exception: " + paramString.getMessage() + " Returning default value.");
    }
    return zzdf.zzzP();
  }
  
  public boolean isDefault()
  {
    return getLastRefreshTime() == 0L;
  }
  
  public void registerFunctionCallMacroCallback(String paramString, FunctionCallMacroCallback paramFunctionCallMacroCallback)
  {
    if (paramFunctionCallMacroCallback == null) {
      throw new NullPointerException("Macro handler must be non-null");
    }
    synchronized (this.zzaKB)
    {
      this.zzaKB.put(paramString, paramFunctionCallMacroCallback);
      return;
    }
  }
  
  public void registerFunctionCallTagCallback(String paramString, FunctionCallTagCallback paramFunctionCallTagCallback)
  {
    if (paramFunctionCallTagCallback == null) {
      throw new NullPointerException("Tag callback must be non-null");
    }
    synchronized (this.zzaKC)
    {
      this.zzaKC.put(paramString, paramFunctionCallTagCallback);
      return;
    }
  }
  
  void release()
  {
    this.zzaKA = null;
  }
  
  public void unregisterFunctionCallMacroCallback(String paramString)
  {
    synchronized (this.zzaKB)
    {
      this.zzaKB.remove(paramString);
      return;
    }
  }
  
  public void unregisterFunctionCallTagCallback(String paramString)
  {
    synchronized (this.zzaKC)
    {
      this.zzaKC.remove(paramString);
      return;
    }
  }
  
  FunctionCallMacroCallback zzef(String paramString)
  {
    synchronized (this.zzaKB)
    {
      paramString = (FunctionCallMacroCallback)this.zzaKB.get(paramString);
      return paramString;
    }
  }
  
  FunctionCallTagCallback zzeg(String paramString)
  {
    synchronized (this.zzaKC)
    {
      paramString = (FunctionCallTagCallback)this.zzaKC.get(paramString);
      return paramString;
    }
  }
  
  void zzeh(String paramString)
  {
    zzyn().zzeh(paramString);
  }
  
  zzah zzei(String paramString)
  {
    if (zzcb.zzzf().zzzg().equals(zzcb.zza.zzaMK)) {}
    return new zzbo();
  }
  
  String zzym()
  {
    return this.zzaKE;
  }
  
  public static abstract interface FunctionCallMacroCallback
  {
    public abstract Object getValue(String paramString, Map<String, Object> paramMap);
  }
  
  public static abstract interface FunctionCallTagCallback
  {
    public abstract void execute(String paramString, Map<String, Object> paramMap);
  }
  
  private class zza
    implements zzt.zza
  {
    private zza() {}
    
    public Object zzd(String paramString, Map<String, Object> paramMap)
    {
      Container.FunctionCallMacroCallback localFunctionCallMacroCallback = Container.this.zzef(paramString);
      if (localFunctionCallMacroCallback == null) {
        return null;
      }
      return localFunctionCallMacroCallback.getValue(paramString, paramMap);
    }
  }
  
  private class zzb
    implements zzt.zza
  {
    private zzb() {}
    
    public Object zzd(String paramString, Map<String, Object> paramMap)
    {
      Container.FunctionCallTagCallback localFunctionCallTagCallback = Container.this.zzeg(paramString);
      if (localFunctionCallTagCallback != null) {
        localFunctionCallTagCallback.execute(paramString, paramMap);
      }
      return zzdf.zzzP();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/Container.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
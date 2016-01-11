package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import com.google.android.gms.internal.zzqf.zza;
import com.google.android.gms.internal.zzqf.zzb;
import com.google.android.gms.internal.zzqf.zzc;
import com.google.android.gms.internal.zzqf.zzd;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class zzaz
{
  private static zzag.zza zzB(Object paramObject)
    throws JSONException
  {
    return zzdf.zzI(zzC(paramObject));
  }
  
  static Object zzC(Object paramObject)
    throws JSONException
  {
    if ((paramObject instanceof JSONArray)) {
      throw new RuntimeException("JSONArrays are not supported");
    }
    if (JSONObject.NULL.equals(paramObject)) {
      throw new RuntimeException("JSON nulls are not supported");
    }
    Object localObject = paramObject;
    if ((paramObject instanceof JSONObject))
    {
      paramObject = (JSONObject)paramObject;
      localObject = new HashMap();
      Iterator localIterator = ((JSONObject)paramObject).keys();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        ((Map)localObject).put(str, zzC(((JSONObject)paramObject).get(str)));
      }
    }
    return localObject;
  }
  
  public static zzqf.zzc zzey(String paramString)
    throws JSONException
  {
    paramString = zzB(new JSONObject(paramString));
    zzqf.zzd localzzd = zzqf.zzc.zzAp();
    int i = 0;
    while (i < paramString.zziT.length)
    {
      localzzd.zzc(zzqf.zza.zzAm().zzb(zzae.zzfr.toString(), paramString.zziT[i]).zzb(zzae.zzfg.toString(), zzdf.zzeJ(zzn.zzyk())).zzb(zzn.zzyl(), paramString.zziU[i]).zzAo());
      i += 1;
    }
    return localzzd.zzAs();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzaz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
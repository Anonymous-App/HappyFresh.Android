package com.google.android.gms.analytics.internal;

import android.util.Log;
import com.google.android.gms.common.internal.zzu;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class zzaf
  extends zzd
{
  private static String zzMl = "3";
  private static String zzMm = "01VDIWEA?";
  private static zzaf zzMn;
  
  public zzaf(zzf paramzzf)
  {
    super(paramzzf);
  }
  
  public static zzaf zzkc()
  {
    return zzMn;
  }
  
  public void zza(int paramInt, String paramString, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    String str = (String)zzy.zzLb.get();
    if (Log.isLoggable(str, paramInt)) {
      Log.println(paramInt, str, zzc(paramString, paramObject1, paramObject2, paramObject3));
    }
    if (paramInt >= 5) {
      zzb(paramInt, paramString, paramObject1, paramObject2, paramObject3);
    }
  }
  
  public void zza(zzab paramzzab, String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "no reason provided";
    }
    if (paramzzab != null) {}
    for (paramzzab = paramzzab.toString();; paramzzab = "no hit data")
    {
      zzd("Discarding hit. " + str, paramzzab);
      return;
    }
  }
  
  public void zzb(int paramInt, String paramString, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    int i = 0;
    for (;;)
    {
      try
      {
        zzu.zzu(paramString);
        if (paramInt < 0)
        {
          paramInt = i;
          if (paramInt >= zzMm.length())
          {
            paramInt = zzMm.length() - 1;
            if (zzhR().zziX())
            {
              if (zzhR().zziW())
              {
                c = 'P';
                paramObject1 = zzMl + zzMm.charAt(paramInt) + c + zze.VERSION + ":" + zzc(paramString, zzk(paramObject1), zzk(paramObject2), zzk(paramObject3));
                paramString = (String)paramObject1;
                if (((String)paramObject1).length() > 1024) {
                  paramString = ((String)paramObject1).substring(0, 1024);
                }
                paramObject1 = zzhM().zzif();
                if (paramObject1 != null) {
                  ((zzai)paramObject1).zzkp().zzbg(paramString);
                }
                return;
              }
              c = 'C';
              continue;
            }
            boolean bool = zzhR().zziW();
            if (bool)
            {
              c = 'p';
              continue;
            }
            char c = 'c';
            continue;
          }
        }
      }
      finally {}
    }
  }
  
  public void zzg(Map<String, String> paramMap, String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "no reason provided";
    }
    if (paramMap != null)
    {
      paramString = new StringBuilder();
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        if (paramString.length() > 0) {
          paramString.append(',');
        }
        paramString.append((String)localEntry.getKey());
        paramString.append('=');
        paramString.append((String)localEntry.getValue());
      }
    }
    for (paramMap = paramString.toString();; paramMap = "no hit data")
    {
      zzd("Discarding hit. " + str, paramMap);
      return;
    }
  }
  
  protected void zzhn()
  {
    try
    {
      zzMn = this;
      return;
    }
    finally {}
  }
  
  protected String zzk(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    if ((paramObject instanceof Integer)) {
      paramObject = new Long(((Integer)paramObject).intValue());
    }
    for (;;)
    {
      if ((paramObject instanceof Long))
      {
        if (Math.abs(((Long)paramObject).longValue()) < 100L) {
          return String.valueOf(paramObject);
        }
        if (String.valueOf(paramObject).charAt(0) == '-') {}
        for (String str = "-";; str = "")
        {
          paramObject = String.valueOf(Math.abs(((Long)paramObject).longValue()));
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(str);
          localStringBuilder.append(Math.round(Math.pow(10.0D, ((String)paramObject).length() - 1)));
          localStringBuilder.append("...");
          localStringBuilder.append(str);
          localStringBuilder.append(Math.round(Math.pow(10.0D, ((String)paramObject).length()) - 1.0D));
          return localStringBuilder.toString();
        }
      }
      if ((paramObject instanceof Boolean)) {
        return String.valueOf(paramObject);
      }
      if ((paramObject instanceof Throwable)) {
        return paramObject.getClass().getCanonicalName();
      }
      return "-";
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzaf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
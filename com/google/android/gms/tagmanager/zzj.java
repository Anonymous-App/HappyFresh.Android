package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.Uri.Builder;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzj
  extends zzdd
{
  private static final String ID = zzad.zzcl.toString();
  private static final String URL = zzae.zzhu.toString();
  private static final String zzaKr = zzae.zzdk.toString();
  private static final String zzaKs = zzae.zzht.toString();
  static final String zzaKt = "gtm_" + ID + "_unrepeatable";
  private static final Set<String> zzaKu = new HashSet();
  private final Context mContext;
  private final zza zzaKv;
  
  public zzj(Context paramContext)
  {
    this(paramContext, new zza()
    {
      public zzar zzyi()
      {
        return zzz.zzaF(zzj.this);
      }
    });
  }
  
  zzj(Context paramContext, zza paramzza)
  {
    super(ID, new String[] { URL });
    this.zzaKv = paramzza;
    this.mContext = paramContext;
  }
  
  private boolean zzeb(String paramString)
  {
    boolean bool1 = true;
    for (;;)
    {
      try
      {
        boolean bool2 = zzed(paramString);
        if (bool2) {
          return bool1;
        }
        if (zzec(paramString)) {
          zzaKu.add(paramString);
        } else {
          bool1 = false;
        }
      }
      finally {}
    }
  }
  
  public void zzG(Map<String, zzag.zza> paramMap)
  {
    String str;
    if (paramMap.get(zzaKs) != null)
    {
      str = zzdf.zzg((zzag.zza)paramMap.get(zzaKs));
      if ((str == null) || (!zzeb(str))) {
        break label46;
      }
    }
    label46:
    do
    {
      return;
      str = null;
      break;
      Uri.Builder localBuilder = Uri.parse(zzdf.zzg((zzag.zza)paramMap.get(URL))).buildUpon();
      paramMap = (zzag.zza)paramMap.get(zzaKr);
      if (paramMap != null)
      {
        paramMap = zzdf.zzl(paramMap);
        if (!(paramMap instanceof List))
        {
          zzbg.zzaz("ArbitraryPixel: additional params not a list: not sending partial hit: " + localBuilder.build().toString());
          return;
        }
        paramMap = ((List)paramMap).iterator();
        while (paramMap.hasNext())
        {
          Object localObject = paramMap.next();
          if (!(localObject instanceof Map))
          {
            zzbg.zzaz("ArbitraryPixel: additional params contains non-map: not sending partial hit: " + localBuilder.build().toString());
            return;
          }
          localObject = ((Map)localObject).entrySet().iterator();
          while (((Iterator)localObject).hasNext())
          {
            Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
            localBuilder.appendQueryParameter(localEntry.getKey().toString(), localEntry.getValue().toString());
          }
        }
      }
      paramMap = localBuilder.build().toString();
      this.zzaKv.zzyi().zzes(paramMap);
      zzbg.zzaB("ArbitraryPixel: url = " + paramMap);
    } while (str == null);
    try
    {
      zzaKu.add(str);
      zzcv.zza(this.mContext, zzaKt, str, "true");
      return;
    }
    finally {}
  }
  
  boolean zzec(String paramString)
  {
    return this.mContext.getSharedPreferences(zzaKt, 0).contains(paramString);
  }
  
  boolean zzed(String paramString)
  {
    return zzaKu.contains(paramString);
  }
  
  public static abstract interface zza
  {
    public abstract zzar zzyi();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzj.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
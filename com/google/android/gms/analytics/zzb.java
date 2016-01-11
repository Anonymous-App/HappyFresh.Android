package com.google.android.gms.analytics;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzim;
import com.google.android.gms.internal.zzin;
import com.google.android.gms.internal.zzio;
import com.google.android.gms.internal.zzip;
import com.google.android.gms.internal.zzno;
import com.google.android.gms.internal.zznu;
import com.google.android.gms.internal.zznx;
import com.google.android.gms.internal.zzny;
import com.google.android.gms.internal.zznz;
import com.google.android.gms.internal.zzoa;
import com.google.android.gms.internal.zzob;
import com.google.android.gms.internal.zzoc;
import com.google.android.gms.internal.zzod;
import com.google.android.gms.internal.zzoe;
import com.google.android.gms.internal.zzof;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class zzb
  extends com.google.android.gms.analytics.internal.zzc
  implements zznu
{
  private static DecimalFormat zzIk;
  private final zzf zzIa;
  private final Uri zzIl;
  private final boolean zzIm;
  private final boolean zzIn;
  private final String zztd;
  
  public zzb(zzf paramzzf, String paramString)
  {
    this(paramzzf, paramString, true, false);
  }
  
  public zzb(zzf paramzzf, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramzzf);
    zzu.zzcj(paramString);
    this.zzIa = paramzzf;
    this.zztd = paramString;
    this.zzIm = paramBoolean1;
    this.zzIn = paramBoolean2;
    this.zzIl = zzaK(this.zztd);
  }
  
  static String zza(double paramDouble)
  {
    if (zzIk == null) {
      zzIk = new DecimalFormat("0.######");
    }
    return zzIk.format(paramDouble);
  }
  
  private static void zza(Map<String, String> paramMap, String paramString, double paramDouble)
  {
    if (paramDouble != 0.0D) {
      paramMap.put(paramString, zza(paramDouble));
    }
  }
  
  private static void zza(Map<String, String> paramMap, String paramString, int paramInt1, int paramInt2)
  {
    if ((paramInt1 > 0) && (paramInt2 > 0)) {
      paramMap.put(paramString, paramInt1 + "x" + paramInt2);
    }
  }
  
  private static void zza(Map<String, String> paramMap, String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString2)) {
      paramMap.put(paramString1, paramString2);
    }
  }
  
  private static void zza(Map<String, String> paramMap, String paramString, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramMap.put(paramString, "1");
    }
  }
  
  static Uri zzaK(String paramString)
  {
    zzu.zzcj(paramString);
    Uri.Builder localBuilder = new Uri.Builder();
    localBuilder.scheme("uri");
    localBuilder.authority("google-analytics.com");
    localBuilder.path(paramString);
    return localBuilder.build();
  }
  
  public static Map<String, String> zzc(zzno paramzzno)
  {
    HashMap localHashMap = new HashMap();
    Object localObject1 = (zzio)paramzzno.zzd(zzio.class);
    Object localObject2;
    Object localObject3;
    if (localObject1 != null)
    {
      localObject1 = ((zzio)localObject1).zzhv().entrySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Map.Entry)((Iterator)localObject1).next();
        localObject3 = zzh(((Map.Entry)localObject2).getValue());
        if (localObject3 != null) {
          localHashMap.put(((Map.Entry)localObject2).getKey(), localObject3);
        }
      }
    }
    localObject1 = (zzip)paramzzno.zzd(zzip.class);
    if (localObject1 != null)
    {
      zza(localHashMap, "t", ((zzip)localObject1).zzhw());
      zza(localHashMap, "cid", ((zzip)localObject1).getClientId());
      zza(localHashMap, "uid", ((zzip)localObject1).getUserId());
      zza(localHashMap, "sc", ((zzip)localObject1).zzhz());
      zza(localHashMap, "sf", ((zzip)localObject1).zzhB());
      zza(localHashMap, "ni", ((zzip)localObject1).zzhA());
      zza(localHashMap, "adid", ((zzip)localObject1).zzhx());
      zza(localHashMap, "ate", ((zzip)localObject1).zzhy());
    }
    localObject1 = (zzod)paramzzno.zzd(zzod.class);
    if (localObject1 != null)
    {
      zza(localHashMap, "cd", ((zzod)localObject1).zzwB());
      zza(localHashMap, "a", ((zzod)localObject1).zzbn());
      zza(localHashMap, "dr", ((zzod)localObject1).zzwE());
    }
    localObject1 = (zzob)paramzzno.zzd(zzob.class);
    if (localObject1 != null)
    {
      zza(localHashMap, "ec", ((zzob)localObject1).zzwy());
      zza(localHashMap, "ea", ((zzob)localObject1).getAction());
      zza(localHashMap, "el", ((zzob)localObject1).getLabel());
      zza(localHashMap, "ev", ((zzob)localObject1).getValue());
    }
    localObject1 = (zzny)paramzzno.zzd(zzny.class);
    if (localObject1 != null)
    {
      zza(localHashMap, "cn", ((zzny)localObject1).getName());
      zza(localHashMap, "cs", ((zzny)localObject1).getSource());
      zza(localHashMap, "cm", ((zzny)localObject1).zzwj());
      zza(localHashMap, "ck", ((zzny)localObject1).zzwk());
      zza(localHashMap, "cc", ((zzny)localObject1).getContent());
      zza(localHashMap, "ci", ((zzny)localObject1).getId());
      zza(localHashMap, "anid", ((zzny)localObject1).zzwl());
      zza(localHashMap, "gclid", ((zzny)localObject1).zzwm());
      zza(localHashMap, "dclid", ((zzny)localObject1).zzwn());
      zza(localHashMap, "aclid", ((zzny)localObject1).zzwo());
    }
    localObject1 = (zzoc)paramzzno.zzd(zzoc.class);
    if (localObject1 != null)
    {
      zza(localHashMap, "exd", ((zzoc)localObject1).getDescription());
      zza(localHashMap, "exf", ((zzoc)localObject1).zzwz());
    }
    localObject1 = (zzoe)paramzzno.zzd(zzoe.class);
    if (localObject1 != null)
    {
      zza(localHashMap, "sn", ((zzoe)localObject1).zzwI());
      zza(localHashMap, "sa", ((zzoe)localObject1).getAction());
      zza(localHashMap, "st", ((zzoe)localObject1).getTarget());
    }
    localObject1 = (zzof)paramzzno.zzd(zzof.class);
    if (localObject1 != null)
    {
      zza(localHashMap, "utv", ((zzof)localObject1).zzwJ());
      zza(localHashMap, "utt", ((zzof)localObject1).getTimeInMillis());
      zza(localHashMap, "utc", ((zzof)localObject1).zzwy());
      zza(localHashMap, "utl", ((zzof)localObject1).getLabel());
    }
    localObject1 = (zzim)paramzzno.zzd(zzim.class);
    if (localObject1 != null)
    {
      localObject1 = ((zzim)localObject1).zzht().entrySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Map.Entry)((Iterator)localObject1).next();
        localObject3 = zzc.zzO(((Integer)((Map.Entry)localObject2).getKey()).intValue());
        if (!TextUtils.isEmpty((CharSequence)localObject3)) {
          localHashMap.put(localObject3, ((Map.Entry)localObject2).getValue());
        }
      }
    }
    localObject1 = (zzin)paramzzno.zzd(zzin.class);
    if (localObject1 != null)
    {
      localObject1 = ((zzin)localObject1).zzhu().entrySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Map.Entry)((Iterator)localObject1).next();
        localObject3 = zzc.zzQ(((Integer)((Map.Entry)localObject2).getKey()).intValue());
        if (!TextUtils.isEmpty((CharSequence)localObject3)) {
          localHashMap.put(localObject3, zza(((Double)((Map.Entry)localObject2).getValue()).doubleValue()));
        }
      }
    }
    localObject1 = (zzoa)paramzzno.zzd(zzoa.class);
    if (localObject1 != null)
    {
      localObject2 = ((zzoa)localObject1).zzwu();
      if (localObject2 != null)
      {
        localObject2 = ((ProductAction)localObject2).build().entrySet().iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (Map.Entry)((Iterator)localObject2).next();
          if (((String)((Map.Entry)localObject3).getKey()).startsWith("&")) {
            localHashMap.put(((String)((Map.Entry)localObject3).getKey()).substring(1), ((Map.Entry)localObject3).getValue());
          } else {
            localHashMap.put(((Map.Entry)localObject3).getKey(), ((Map.Entry)localObject3).getValue());
          }
        }
      }
      localObject2 = ((zzoa)localObject1).zzwx().iterator();
      int i = 1;
      while (((Iterator)localObject2).hasNext())
      {
        localHashMap.putAll(((Promotion)((Iterator)localObject2).next()).zzaQ(zzc.zzU(i)));
        i += 1;
      }
      localObject2 = ((zzoa)localObject1).zzwv().iterator();
      i = 1;
      while (((Iterator)localObject2).hasNext())
      {
        localHashMap.putAll(((Product)((Iterator)localObject2).next()).zzaQ(zzc.zzS(i)));
        i += 1;
      }
      localObject1 = ((zzoa)localObject1).zzww().entrySet().iterator();
      i = 1;
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Map.Entry)((Iterator)localObject1).next();
        Object localObject4 = (List)((Map.Entry)localObject2).getValue();
        localObject3 = zzc.zzX(i);
        localObject4 = ((List)localObject4).iterator();
        int j = 1;
        while (((Iterator)localObject4).hasNext())
        {
          localHashMap.putAll(((Product)((Iterator)localObject4).next()).zzaQ((String)localObject3 + zzc.zzV(j)));
          j += 1;
        }
        if (!TextUtils.isEmpty((CharSequence)((Map.Entry)localObject2).getKey())) {
          localHashMap.put((String)localObject3 + "nm", ((Map.Entry)localObject2).getKey());
        }
        i += 1;
      }
    }
    localObject1 = (zznz)paramzzno.zzd(zznz.class);
    if (localObject1 != null)
    {
      zza(localHashMap, "ul", ((zznz)localObject1).getLanguage());
      zza(localHashMap, "sd", ((zznz)localObject1).zzwp());
      zza(localHashMap, "sr", ((zznz)localObject1).zzwq(), ((zznz)localObject1).zzwr());
      zza(localHashMap, "vp", ((zznz)localObject1).zzws(), ((zznz)localObject1).zzwt());
    }
    paramzzno = (zznx)paramzzno.zzd(zznx.class);
    if (paramzzno != null)
    {
      zza(localHashMap, "an", paramzzno.zzjL());
      zza(localHashMap, "aid", paramzzno.zzsK());
      zza(localHashMap, "aiid", paramzzno.zzwi());
      zza(localHashMap, "av", paramzzno.zzjN());
    }
    return localHashMap;
  }
  
  private static String zzh(Object paramObject)
  {
    if (paramObject == null) {
      paramObject = null;
    }
    String str;
    do
    {
      return (String)paramObject;
      if (!(paramObject instanceof String)) {
        break;
      }
      str = (String)paramObject;
      paramObject = str;
    } while (!TextUtils.isEmpty(str));
    return null;
    if ((paramObject instanceof Double))
    {
      paramObject = (Double)paramObject;
      if (((Double)paramObject).doubleValue() != 0.0D) {
        return zza(((Double)paramObject).doubleValue());
      }
      return null;
    }
    if ((paramObject instanceof Boolean))
    {
      if (paramObject != Boolean.FALSE) {
        return "1";
      }
      return null;
    }
    return String.valueOf(paramObject);
  }
  
  private static String zzz(Map<String, String> paramMap)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if (localStringBuilder.length() != 0) {
        localStringBuilder.append(", ");
      }
      localStringBuilder.append((String)localEntry.getKey());
      localStringBuilder.append("=");
      localStringBuilder.append((String)localEntry.getValue());
    }
    return localStringBuilder.toString();
  }
  
  public void zzb(zzno paramzzno)
  {
    zzu.zzu(paramzzno);
    zzu.zzb(paramzzno.zzvU(), "Can't deliver not submitted measurement");
    zzu.zzbZ("deliver should be called on worker thread");
    Object localObject2 = paramzzno.zzvP();
    Object localObject1 = (zzip)((zzno)localObject2).zze(zzip.class);
    if (TextUtils.isEmpty(((zzip)localObject1).zzhw())) {
      zzhQ().zzg(zzc((zzno)localObject2), "Ignoring measurement without type");
    }
    do
    {
      return;
      if (TextUtils.isEmpty(((zzip)localObject1).getClientId()))
      {
        zzhQ().zzg(zzc((zzno)localObject2), "Ignoring measurement without client id");
        return;
      }
    } while (this.zzIa.zzie().getAppOptOut());
    double d = ((zzip)localObject1).zzhB();
    if (zzam.zza(d, ((zzip)localObject1).getClientId()))
    {
      zzb("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(d));
      return;
    }
    localObject2 = zzc((zzno)localObject2);
    ((Map)localObject2).put("v", "1");
    ((Map)localObject2).put("_v", zze.zzJB);
    ((Map)localObject2).put("tid", this.zztd);
    if (this.zzIa.zzie().isDryRunEnabled())
    {
      zzc("Dry run is enabled. GoogleAnalytics would have sent", zzz((Map)localObject2));
      return;
    }
    HashMap localHashMap = new HashMap();
    zzam.zzb(localHashMap, "uid", ((zzip)localObject1).getUserId());
    Object localObject3 = (zznx)paramzzno.zzd(zznx.class);
    if (localObject3 != null)
    {
      zzam.zzb(localHashMap, "an", ((zznx)localObject3).zzjL());
      zzam.zzb(localHashMap, "aid", ((zznx)localObject3).zzsK());
      zzam.zzb(localHashMap, "av", ((zznx)localObject3).zzjN());
      zzam.zzb(localHashMap, "aiid", ((zznx)localObject3).zzwi());
    }
    localObject3 = ((zzip)localObject1).getClientId();
    String str = this.zztd;
    if (!TextUtils.isEmpty(((zzip)localObject1).zzhx())) {}
    for (boolean bool = true;; bool = false)
    {
      localObject1 = new zzh(0L, (String)localObject3, str, bool, 0L, localHashMap);
      ((Map)localObject2).put("_s", String.valueOf(zzhl().zza((zzh)localObject1)));
      paramzzno = new zzab(zzhQ(), (Map)localObject2, paramzzno.zzvS(), true);
      zzhl().zza(paramzzno);
      return;
    }
  }
  
  public Uri zzhe()
  {
    return this.zzIl;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
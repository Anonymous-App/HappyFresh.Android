package com.google.android.gms.analytics.internal;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzny;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class zzam
{
  private static final char[] zzMK = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
  
  public static String zzH(boolean paramBoolean)
  {
    if (paramBoolean) {
      return "1";
    }
    return "0";
  }
  
  public static double zza(String paramString, double paramDouble)
  {
    if (paramString == null) {
      return paramDouble;
    }
    try
    {
      double d = Double.parseDouble(paramString);
      return d;
    }
    catch (NumberFormatException paramString) {}
    return paramDouble;
  }
  
  public static zzny zza(zzaf paramzzaf, String paramString)
  {
    zzu.zzu(paramzzaf);
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    try
    {
      paramString = URLEncodedUtils.parse(new URI("?" + paramString), "UTF-8");
      paramzzaf = new HashMap(paramString.size());
      paramString = paramString.iterator();
      while (paramString.hasNext())
      {
        NameValuePair localNameValuePair = (NameValuePair)paramString.next();
        paramzzaf.put(localNameValuePair.getName(), localNameValuePair.getValue());
      }
      paramString = new zzny();
    }
    catch (URISyntaxException paramString)
    {
      paramzzaf.zzd("No valid campaign data found", paramString);
      return null;
    }
    paramString.zzdA((String)paramzzaf.get("utm_content"));
    paramString.zzdy((String)paramzzaf.get("utm_medium"));
    paramString.setName((String)paramzzaf.get("utm_campaign"));
    paramString.zzdx((String)paramzzaf.get("utm_source"));
    paramString.zzdz((String)paramzzaf.get("utm_term"));
    paramString.zzdB((String)paramzzaf.get("utm_id"));
    paramString.zzdC((String)paramzzaf.get("anid"));
    paramString.zzdD((String)paramzzaf.get("gclid"));
    paramString.zzdE((String)paramzzaf.get("dclid"));
    paramString.zzdF((String)paramzzaf.get("aclid"));
    return paramString;
  }
  
  public static String zza(Locale paramLocale)
  {
    if (paramLocale == null) {}
    String str;
    do
    {
      return null;
      str = paramLocale.getLanguage();
    } while (TextUtils.isEmpty(str));
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str.toLowerCase());
    if (!TextUtils.isEmpty(paramLocale.getCountry())) {
      localStringBuilder.append("-").append(paramLocale.getCountry().toLowerCase());
    }
    return localStringBuilder.toString();
  }
  
  public static void zza(Map<String, String> paramMap1, String paramString, Map<String, String> paramMap2)
  {
    zzb(paramMap1, paramString, (String)paramMap2.get(paramString));
  }
  
  public static boolean zza(double paramDouble, String paramString)
  {
    if ((paramDouble <= 0.0D) || (paramDouble >= 100.0D)) {}
    while (zzbm(paramString) % 10000 < 100.0D * paramDouble) {
      return false;
    }
    return true;
  }
  
  public static boolean zza(Context paramContext, Class<? extends Service> paramClass)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getServiceInfo(new ComponentName(paramContext, paramClass), 4);
      if (paramContext != null)
      {
        boolean bool = paramContext.enabled;
        if (bool) {
          return true;
        }
      }
    }
    catch (PackageManager.NameNotFoundException paramContext) {}
    return false;
  }
  
  public static boolean zza(Context paramContext, Class<? extends BroadcastReceiver> paramClass, boolean paramBoolean)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getReceiverInfo(new ComponentName(paramContext, paramClass), 2);
      if ((paramContext != null) && (paramContext.enabled)) {
        if (paramBoolean)
        {
          paramBoolean = paramContext.exported;
          if (!paramBoolean) {}
        }
        else
        {
          return true;
        }
      }
    }
    catch (PackageManager.NameNotFoundException paramContext) {}
    return false;
  }
  
  public static void zzb(Map<String, String> paramMap, String paramString1, String paramString2)
  {
    if ((paramString2 != null) && (!paramMap.containsKey(paramString1))) {
      paramMap.put(paramString1, paramString2);
    }
  }
  
  public static void zzb(Map<String, String> paramMap, String paramString, boolean paramBoolean)
  {
    if (!paramMap.containsKey(paramString)) {
      if (!paramBoolean) {
        break label27;
      }
    }
    label27:
    for (String str = "1";; str = "0")
    {
      paramMap.put(paramString, str);
      return;
    }
  }
  
  public static Map<String, String> zzbi(String paramString)
  {
    HashMap localHashMap = new HashMap();
    String[] arrayOfString1 = paramString.split("&");
    int j = arrayOfString1.length;
    int i = 0;
    if (i < j)
    {
      String[] arrayOfString2 = arrayOfString1[i].split("=", 3);
      if (arrayOfString2.length > 1)
      {
        String str = arrayOfString2[0];
        if (TextUtils.isEmpty(arrayOfString2[1]))
        {
          paramString = null;
          label66:
          localHashMap.put(str, paramString);
          if ((arrayOfString2.length == 3) && (!TextUtils.isEmpty(arrayOfString2[1])) && (!localHashMap.containsKey(arrayOfString2[1])))
          {
            str = arrayOfString2[1];
            if (!TextUtils.isEmpty(arrayOfString2[2])) {
              break label149;
            }
            paramString = null;
            label124:
            localHashMap.put(str, paramString);
          }
        }
      }
      for (;;)
      {
        i += 1;
        break;
        paramString = arrayOfString2[1];
        break label66;
        label149:
        paramString = arrayOfString2[2];
        break label124;
        if ((arrayOfString2.length == 1) && (arrayOfString2[0].length() != 0)) {
          localHashMap.put(arrayOfString2[0], null);
        }
      }
    }
    return localHashMap;
  }
  
  public static long zzbj(String paramString)
  {
    if (paramString == null) {
      return 0L;
    }
    try
    {
      long l = Long.parseLong(paramString);
      return l;
    }
    catch (NumberFormatException paramString) {}
    return 0L;
  }
  
  public static String zzbk(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    Object localObject1 = paramString;
    Object localObject2;
    if (paramString.contains("?"))
    {
      localObject2 = paramString.split("[\\?]");
      localObject1 = paramString;
      if (localObject2.length > 1) {
        localObject1 = localObject2[1];
      }
    }
    if (((String)localObject1).contains("%3D")) {}
    while (((String)localObject1).contains("=")) {
      try
      {
        paramString = URLDecoder.decode((String)localObject1, "UTF-8");
        paramString = zzbi(paramString);
        localObject1 = new String[11];
        localObject1[0] = "dclid";
        localObject1[1] = "utm_source";
        localObject1[2] = "gclid";
        localObject1[3] = "aclid";
        localObject1[4] = "utm_campaign";
        localObject1[5] = "utm_medium";
        localObject1[6] = "utm_term";
        localObject1[7] = "utm_content";
        localObject1[8] = "utm_id";
        localObject1[9] = "anid";
        localObject1[10] = "gmob_t";
        localObject2 = new StringBuilder();
        int i = 0;
        while (i < localObject1.length)
        {
          if (!TextUtils.isEmpty((CharSequence)paramString.get(localObject1[i])))
          {
            if (((StringBuilder)localObject2).length() > 0) {
              ((StringBuilder)localObject2).append("&");
            }
            ((StringBuilder)localObject2).append(localObject1[i]).append("=").append((String)paramString.get(localObject1[i]));
          }
          i += 1;
        }
        paramString = (String)localObject1;
      }
      catch (UnsupportedEncodingException paramString)
      {
        return null;
      }
    }
    return null;
    return ((StringBuilder)localObject2).toString();
  }
  
  public static MessageDigest zzbl(String paramString)
  {
    int i = 0;
    while (i < 2) {
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance(paramString);
        if (localMessageDigest != null) {
          return localMessageDigest;
        }
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        i += 1;
      }
    }
    return null;
  }
  
  public static int zzbm(String paramString)
  {
    int k = 1;
    if (!TextUtils.isEmpty(paramString))
    {
      int j = paramString.length();
      int i = 0;
      j -= 1;
      for (;;)
      {
        k = i;
        if (j < 0) {
          break;
        }
        k = paramString.charAt(j);
        k = (i << 6 & 0xFFFFFFF) + k + (k << 14);
        int m = 0xFE00000 & k;
        i = k;
        if (m != 0) {
          i = k ^ m >> 21;
        }
        j -= 1;
      }
    }
    return k;
  }
  
  public static boolean zzbn(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {}
    while (!paramString.startsWith("http:")) {
      return true;
    }
    return false;
  }
  
  public static void zzc(Map<String, String> paramMap, String paramString1, String paramString2)
  {
    if ((paramString2 != null) && (TextUtils.isEmpty((CharSequence)paramMap.get(paramString1)))) {
      paramMap.put(paramString1, paramString2);
    }
  }
  
  public static boolean zze(String paramString, boolean paramBoolean)
  {
    boolean bool = paramBoolean;
    if (paramString != null)
    {
      if ((!paramString.equalsIgnoreCase("true")) && (!paramString.equalsIgnoreCase("yes")) && (!paramString.equalsIgnoreCase("1"))) {
        break label39;
      }
      bool = true;
    }
    label39:
    do
    {
      return bool;
      if ((paramString.equalsIgnoreCase("false")) || (paramString.equalsIgnoreCase("no"))) {
        break;
      }
      bool = paramBoolean;
    } while (!paramString.equalsIgnoreCase("0"));
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
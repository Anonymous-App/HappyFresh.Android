package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.tagmanager.zzbg;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class zzqi
{
  private String zzaLc = "https://www.google-analytics.com";
  
  private String zzeQ(String paramString)
  {
    try
    {
      String str = URLEncoder.encode(paramString, "UTF-8").replaceAll("\\+", "%20");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      zzbg.zzaz("Cannot encode the string: " + paramString);
    }
    return "";
  }
  
  public void zzeU(String paramString)
  {
    this.zzaLc = paramString;
    zzbg.zzaA("The Ctfe server endpoint was changed to: " + paramString);
  }
  
  public String zzt(List<zzpy> paramList)
  {
    return this.zzaLc + "/gtm/android?" + zzu(paramList);
  }
  
  String zzu(List<zzpy> paramList)
  {
    boolean bool = true;
    if (paramList.size() <= 1) {}
    for (;;)
    {
      zzu.zzV(bool);
      if (!paramList.isEmpty()) {
        break;
      }
      return "";
      bool = false;
    }
    zzpy localzzpy = (zzpy)paramList.get(0);
    StringBuilder localStringBuilder;
    if (!localzzpy.zzAd().trim().equals(""))
    {
      paramList = localzzpy.zzAd().trim();
      localStringBuilder = new StringBuilder();
      if (localzzpy.zzAa() == null) {
        break label162;
      }
      localStringBuilder.append(localzzpy.zzAa());
    }
    for (;;)
    {
      localStringBuilder.append("=").append(zzeQ(localzzpy.getContainerId())).append("&").append("pv").append("=").append(zzeQ(paramList));
      if (localzzpy.zzAc()) {
        localStringBuilder.append("&gtm_debug=x");
      }
      return localStringBuilder.toString();
      paramList = "-1";
      break;
      label162:
      localStringBuilder.append("id");
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzqi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
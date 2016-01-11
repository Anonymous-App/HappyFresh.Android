package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

class zzcx
  implements zzac
{
  private final Context zzaNE;
  private final String zzaNV;
  private final HttpClient zzaNW;
  private zza zzaNX;
  
  zzcx(HttpClient paramHttpClient, Context paramContext, zza paramzza)
  {
    this.zzaNE = paramContext.getApplicationContext();
    this.zzaNV = zza("GoogleTagManager", "4.00", Build.VERSION.RELEASE, zzc(Locale.getDefault()), Build.MODEL, Build.ID);
    this.zzaNW = paramHttpClient;
    this.zzaNX = paramzza;
  }
  
  private void zza(HttpEntityEnclosingRequest paramHttpEntityEnclosingRequest)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Object localObject = paramHttpEntityEnclosingRequest.getAllHeaders();
    int j = localObject.length;
    int i = 0;
    while (i < j)
    {
      localStringBuffer.append(localObject[i].toString()).append("\n");
      i += 1;
    }
    localStringBuffer.append(paramHttpEntityEnclosingRequest.getRequestLine().toString()).append("\n");
    if (paramHttpEntityEnclosingRequest.getEntity() != null) {}
    try
    {
      paramHttpEntityEnclosingRequest = paramHttpEntityEnclosingRequest.getEntity().getContent();
      if (paramHttpEntityEnclosingRequest != null)
      {
        i = paramHttpEntityEnclosingRequest.available();
        if (i > 0)
        {
          localObject = new byte[i];
          paramHttpEntityEnclosingRequest.read((byte[])localObject);
          localStringBuffer.append("POST:\n");
          localStringBuffer.append(new String((byte[])localObject)).append("\n");
        }
      }
    }
    catch (IOException paramHttpEntityEnclosingRequest)
    {
      for (;;)
      {
        zzbg.zzaB("Error Writing hit to log...");
      }
    }
    zzbg.zzaB(localStringBuffer.toString());
  }
  
  static String zzc(Locale paramLocale)
  {
    if (paramLocale == null) {}
    while ((paramLocale.getLanguage() == null) || (paramLocale.getLanguage().length() == 0)) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramLocale.getLanguage().toLowerCase());
    if ((paramLocale.getCountry() != null) && (paramLocale.getCountry().length() != 0)) {
      localStringBuilder.append("-").append(paramLocale.getCountry().toLowerCase());
    }
    return localStringBuilder.toString();
  }
  
  private HttpEntityEnclosingRequest zzd(URL paramURL)
  {
    try
    {
      paramURL = new BasicHttpEntityEnclosingRequest("GET", paramURL.toURI().toString());
      zzbg.zzaC("Exception sending hit: " + localURISyntaxException1.getClass().getSimpleName());
    }
    catch (URISyntaxException localURISyntaxException1)
    {
      try
      {
        paramURL.addHeader("User-Agent", this.zzaNV);
        return paramURL;
      }
      catch (URISyntaxException localURISyntaxException2)
      {
        for (;;) {}
      }
      localURISyntaxException1 = localURISyntaxException1;
      paramURL = null;
    }
    zzbg.zzaC(localURISyntaxException1.getMessage());
    return paramURL;
  }
  
  String zza(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[] { paramString1, paramString2, paramString3, paramString4, paramString5, paramString6 });
  }
  
  URL zzd(zzaq paramzzaq)
  {
    paramzzaq = paramzzaq.zzyQ();
    try
    {
      paramzzaq = new URL(paramzzaq);
      return paramzzaq;
    }
    catch (MalformedURLException paramzzaq)
    {
      zzbg.zzaz("Error trying to parse the GTM url.");
    }
    return null;
  }
  
  public void zzr(List<zzaq> paramList)
  {
    int n = Math.min(paramList.size(), 40);
    int i = 1;
    int m = 0;
    zzaq localzzaq;
    Object localObject2;
    if (m < n)
    {
      localzzaq = (zzaq)paramList.get(m);
      localObject2 = zzd(localzzaq);
      if (localObject2 == null)
      {
        zzbg.zzaC("No destination: discarding hit.");
        this.zzaNX.zzb(localzzaq);
      }
    }
    for (;;)
    {
      m += 1;
      break;
      Object localObject1 = zzd((URL)localObject2);
      if (localObject1 == null)
      {
        this.zzaNX.zzb(localzzaq);
      }
      else
      {
        localObject2 = new HttpHost(((URL)localObject2).getHost(), ((URL)localObject2).getPort(), ((URL)localObject2).getProtocol());
        ((HttpEntityEnclosingRequest)localObject1).addHeader("Host", ((HttpHost)localObject2).toHostString());
        zza((HttpEntityEnclosingRequest)localObject1);
        int j = i;
        int k;
        if (i != 0) {
          k = i;
        }
        try
        {
          zzbl.zzaJ(this.zzaNE);
          j = 0;
          k = j;
          i = j;
          localObject1 = this.zzaNW.execute((HttpHost)localObject2, (HttpRequest)localObject1);
          k = j;
          i = j;
          int i1 = ((HttpResponse)localObject1).getStatusLine().getStatusCode();
          k = j;
          i = j;
          localObject2 = ((HttpResponse)localObject1).getEntity();
          if (localObject2 != null)
          {
            k = j;
            i = j;
            ((HttpEntity)localObject2).consumeContent();
          }
          if (i1 != 200)
          {
            k = j;
            i = j;
            zzbg.zzaC("Bad response: " + ((HttpResponse)localObject1).getStatusLine().getStatusCode());
            k = j;
            i = j;
            this.zzaNX.zzc(localzzaq);
          }
          else
          {
            k = j;
            i = j;
            this.zzaNX.zza(localzzaq);
          }
        }
        catch (ClientProtocolException localClientProtocolException)
        {
          zzbg.zzaC("ClientProtocolException sending hit; discarding hit...");
          this.zzaNX.zzb(localzzaq);
          i = k;
          continue;
        }
        catch (IOException localIOException)
        {
          zzbg.zzaC("Exception sending hit: " + localIOException.getClass().getSimpleName());
          zzbg.zzaC(localIOException.getMessage());
          this.zzaNX.zzc(localzzaq);
        }
        continue;
        return;
        i = j;
      }
    }
  }
  
  public boolean zzyH()
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)this.zzaNE.getSystemService("connectivity")).getActiveNetworkInfo();
    if ((localNetworkInfo == null) || (!localNetworkInfo.isConnected()))
    {
      zzbg.zzaB("...no network connectivity");
      return false;
    }
    return true;
  }
  
  public static abstract interface zza
  {
    public abstract void zza(zzaq paramzzaq);
    
    public abstract void zzb(zzaq paramzzaq);
    
    public abstract void zzc(zzaq paramzzaq);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzcx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
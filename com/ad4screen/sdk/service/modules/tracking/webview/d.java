package com.ad4screen.sdk.service.modules.tracking.webview;

import android.net.Uri;
import com.ad4screen.sdk.Log;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class d
{
  private Uri a = null;
  private Map<String, List<String>> b = new HashMap();
  
  private d(Uri paramUri)
  {
    this.a = paramUri;
    this.b = a();
  }
  
  public static d a(Uri paramUri)
  {
    return new d(paramUri);
  }
  
  public static d a(String paramString)
  {
    Uri localUri = null;
    if (paramString != null) {
      localUri = Uri.parse(paramString);
    }
    return a(localUri);
  }
  
  private Map<String, List<String>> a()
  {
    HashMap localHashMap = new HashMap();
    try
    {
      Map localMap = b(this.a);
      return localMap;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      Log.internal("Impossible to parse query actions", localUnsupportedEncodingException);
    }
    return localHashMap;
  }
  
  private Map<String, List<String>> b(Uri paramUri)
    throws UnsupportedEncodingException
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    if (paramUri != null)
    {
      paramUri = paramUri.getQuery();
      if (paramUri != null)
      {
        String[] arrayOfString = paramUri.split("&");
        int j = arrayOfString.length;
        int i = 0;
        if (i < j)
        {
          String str = arrayOfString[i];
          int k = str.indexOf("=");
          if (k > 0)
          {
            paramUri = URLDecoder.decode(str.substring(0, k), "UTF-8");
            label75:
            if (!localLinkedHashMap.containsKey(paramUri)) {
              localLinkedHashMap.put(paramUri, new LinkedList());
            }
            if ((k <= 0) || (str.length() <= k + 1)) {
              break label167;
            }
          }
          label167:
          for (str = URLDecoder.decode(str.substring(k + 1), "UTF-8");; str = null)
          {
            ((List)localLinkedHashMap.get(paramUri)).add(str);
            i += 1;
            break;
            paramUri = str;
            break label75;
          }
        }
      }
    }
    return localLinkedHashMap;
  }
  
  public void a(b paramb)
  {
    if (a(a.a)) {
      paramb.a(b(a.a));
    }
    if (a(a.b)) {
      paramb.b(b(a.b));
    }
  }
  
  public boolean a(a parama)
  {
    return this.b.containsKey(parama.a());
  }
  
  public List<String> b(a parama)
  {
    parama = (List)this.b.get(parama.a());
    if (parama != null) {
      return parama;
    }
    return new ArrayList();
  }
  
  public static enum a
  {
    private String c;
    
    private a(String paramString)
    {
      this.c = paramString;
    }
    
    public String a()
    {
      return this.c;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/webview/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
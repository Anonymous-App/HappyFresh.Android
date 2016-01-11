package com.appsee;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;

@TargetApi(9)
class sb
  extends ib
{
  private CookieManager G = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
  private HttpURLConnection b;
  private boolean i;
  private boolean k = false;
  
  public sb()
  {
    this.jdField_b_of_type_JavaNetHttpURLConnection = null;
    this.jdField_i_of_type_Boolean = false;
    if (Build.VERSION.SDK_INT < 23) {
      throw new UnsupportedOperationException(AppseeBackgroundUploader.i("zi\r/\031#P At\006~K=E7Sl@)\037;Ip\035 H?TfF9]=MvU9AlD\035W4G=^uT3Z=Y'G-E6P-\037p\032(\t(H"));
    }
  }
  
  protected JSONObject i(String paramString, JSONObject paramJSONObject)
    throws Exception
  {
    try
    {
      i(true);
      HashMap localHashMap = new HashMap();
      localHashMap.put(AppseeBackgroundUploader.i("\017F4K?QeY8P-]"), AppseeBackgroundUploader.i(".Z?F%J;K3P[&Z2V"));
      paramString = i(paramString, localHashMap, paramJSONObject.toString().getBytes(AppseeBackgroundUploader.i("!\030op\000")));
      return paramString;
    }
    finally
    {
      i(false);
    }
  }
  
  protected JSONObject i(String paramString1, byte[] paramArrayOfByte, String paramString2, Map<String, String> paramMap)
    throws Exception
  {
    try
    {
      i(true);
      String str = UUID.randomUUID().toString().replace(AppseeBackgroundUploader.i("\025"), "");
      paramArrayOfByte = i(paramArrayOfByte, paramString2, paramMap, str);
      paramString2 = new HashMap();
      paramString2.put(AppseeBackgroundUploader.i("\017F4K?QeY8P-]"), str);
      paramString2.put(AppseeBackgroundUploader.i("i#G.Z4K]\021\"N)P"), Integer.toString(paramArrayOfByte.length));
      paramString1 = i(paramString1, paramString2, paramArrayOfByte);
      return paramString1;
    }
    finally
    {
      i(false);
    }
  }
  
  public void i()
  {
    for (;;)
    {
      synchronized (jdField_b_of_type_JavaLangObject)
      {
        if (this.jdField_b_of_type_JavaNetHttpURLConnection != null)
        {
          Thread localThread = new Thread(new hb(this), AppseeBackgroundUploader.i("r,I#P7yt\000)T=A\034^#Y*V4XE\034>L<\\"));
          try
          {
            localThread.start();
            localThread.join();
            return;
          }
          catch (InterruptedException localInterruptedException)
          {
            vd.l(localInterruptedException, AppseeBackgroundUploader.i("v.K?Grde\033.K&D(\n\"L.H5MzT/H1T"));
            continue;
          }
        }
      }
      if (this.jdField_i_of_type_Boolean) {
        this.k = true;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/sb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.appsee;

import java.util.HashMap;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;

class mb
{
  public static ab i(String paramString)
  {
    boolean bool2 = true;
    ab localab = new ab();
    paramString = paramString.substring("Appsee_3p".length() + 1);
    int j = paramString.lastIndexOf(AppseeBackgroundUploader.i("g"));
    localab.i(paramString.substring(0, j));
    paramString = paramString.substring(j + 1);
    if (paramString.charAt(1) == '1')
    {
      bool1 = true;
      localab.i(bool1);
      if (paramString.charAt(3) != '1') {
        break label96;
      }
    }
    label96:
    for (boolean bool1 = bool2;; bool1 = false)
    {
      localab.l(bool1);
      return localab;
      bool1 = false;
      break;
    }
  }
  
  public static mb i()
  {
    try
    {
      if (k == null) {
        k = new mb();
      }
      mb localmb = k;
      return localmb;
    }
    finally {}
  }
  
  public static String i(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    int m = 1;
    String str = AppseeBackgroundUploader.i("iZ\005\032)`tQ6Gx\\");
    int j;
    if (paramBoolean1)
    {
      j = 1;
      if (!paramBoolean2) {
        break label59;
      }
    }
    for (;;)
    {
      return String.format(str, new Object[] { "Appsee_3p", paramString, Integer.valueOf(j), Integer.valueOf(m) });
      j = 0;
      break;
      label59:
      m = 0;
    }
  }
  
  public String i(String arg1, boolean paramBoolean)
  {
    if (lb.i(???))
    {
      mc.l(AppseeBackgroundUploader.i("\035Z!\r;\n>L.M3Zg\021r~\031\0300\\.\031cG6\027a\025,O6\nb\n\005G,^6VuT<V0]"), 3);
      return null;
    }
    String str1 = ???.trim().toLowerCase();
    synchronized (this.G)
    {
      String str2 = i(str1, false, paramBoolean);
      if (!this.G.containsKey(str2))
      {
        String str3 = UUID.randomUUID().toString().replace(AppseeBackgroundUploader.i("\025"), "");
        this.G.put(str2, new ab(str1, str3, false, paramBoolean));
        if (paramBoolean) {
          gb.i(str2, str3);
        }
      }
      str1 = ((ab)this.G.get(str2)).i();
      return str1;
    }
  }
  
  public JSONArray i()
    throws JSONException
  {
    return i(true);
  }
  
  public void i()
  {
    synchronized (this.G)
    {
      this.G.putAll(gb.i("Appsee_3p"));
      return;
    }
  }
  
  public void i(String arg1, String paramString2, boolean paramBoolean)
  {
    if (lb.i(???))
    {
      mc.l(AppseeBackgroundUploader.i("|;Q6\000rD8Lv\000.]pE3Ee\r~r\013\nb\n\005G,^6VuT<V0]"), 3);
      return;
    }
    String str1 = ???.trim().toLowerCase();
    for (;;)
    {
      synchronized (this.G)
      {
        String str2 = i(str1, true, paramBoolean);
        if (lb.i(paramString2))
        {
          this.G.remove(str1);
          if (paramBoolean) {
            gb.i(str2, paramString2);
          }
          return;
        }
      }
      this.G.put(str1, new ab(str1, paramString2, true, paramBoolean));
    }
  }
  
  public void l()
  {
    synchronized (this.G)
    {
      this.G.clear();
      i();
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/mb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
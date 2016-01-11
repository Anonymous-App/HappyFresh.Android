package com.appsee;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class gb
{
  public static void F()
    throws JSONException
  {
    synchronized (m)
    {
      SharedPreferences localSharedPreferences = i();
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put(AppseeBackgroundUploader.i("\017F4Q?\\e\035.T)A"), rj.i().ordinal());
      localJSONObject.put(AppseeBackgroundUploader.i("l.^c\000\fT0]"), lb.i(new Date()));
      Object localObject1 = localSharedPreferences.getString("Appsee_OfflineSessions", null);
      if (localObject1 == null)
      {
        localObject1 = new JSONArray();
        Object localObject3 = localObject1;
        ((JSONArray)localObject1).put(localJSONObject);
        localSharedPreferences.edit().putString("Appsee_OfflineSessions", ((JSONArray)localObject3).toString()).commit();
        mc.l(((JSONArray)localObject3).toString(), 1);
        return;
      }
      localObject1 = new JSONArray((String)localObject1);
    }
  }
  
  public static void a()
  {
    if (ge.i().a() != null) {
      return;
    }
    synchronized (m)
    {
      SharedPreferences localSharedPreferences = i();
      ge.i().a(localSharedPreferences.getString("Appsee_ClientId", AppseeBackgroundUploader.i("\b")));
      return;
    }
  }
  
  public static Object i()
    throws JSONException
  {
    synchronized (m)
    {
      Object localObject2 = i().getString("Appsee_OfflineSessions", null);
      if (localObject2 != null)
      {
        localObject2 = new JSONArray((String)localObject2);
        return localObject2;
      }
      return null;
    }
  }
  
  public static HashMap<String, ab> i(String paramString)
  {
    
    HashMap localHashMap;
    synchronized (m)
    {
      Map localMap = i().getAll();
      localHashMap = new HashMap();
      Iterator localIterator = localMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (str.startsWith(paramString))
        {
          ab localab = mb.i(str);
          localab.l(localMap.get(str).toString());
          localHashMap.put(str, localab);
        }
      }
    }
    return localHashMap;
  }
  
  public static void i(String paramString1, String paramString2)
  {
    
    synchronized (m)
    {
      SharedPreferences localSharedPreferences = i();
      if (lb.i(paramString2))
      {
        localSharedPreferences.edit().remove(paramString1);
        return;
      }
      localSharedPreferences.edit().putString(paramString1, paramString2).commit();
    }
  }
  
  public static boolean i()
  {
    
    synchronized (m)
    {
      boolean bool = i().getBoolean("Appsee_IsOptOut", false);
      return bool;
    }
  }
  
  public static boolean i(boolean paramBoolean)
  {
    
    synchronized (m)
    {
      paramBoolean = i().edit().putBoolean("Appsee_IsOptOut", paramBoolean).commit();
      return paramBoolean;
    }
  }
  
  public static void k()
  {
    synchronized (m)
    {
      SharedPreferences localSharedPreferences = i();
      if (localSharedPreferences.contains("Appsee_OfflineSessions"))
      {
        localSharedPreferences.edit().remove("Appsee_OfflineSessions").commit();
        mc.l(AppseeBackgroundUploader.i("j1\\3O5QrXw\0222R!OoY)Z)V5QbT<\\)Y"), 1);
      }
      return;
    }
  }
  
  public static void l()
  {
    synchronized (m)
    {
      i().edit().putString("Appsee_ClientId", ge.i().a()).commit();
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/gb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.ad4screen.sdk.common;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings.Secure;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public final class c
{
  private static final Uri a = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
  
  public static String a(Context paramContext)
  {
    try
    {
      Cursor localCursor = paramContext.getContentResolver().query(a, new String[] { "aid" }, null, null, null);
      if ((localCursor != null) && (localCursor.moveToFirst()))
      {
        paramContext = localCursor.getString(localCursor.getColumnIndex("aid"));
        try
        {
          localCursor.close();
          return paramContext;
        }
        catch (Exception localException)
        {
          return paramContext;
        }
      }
      return null;
    }
    catch (Exception paramContext)
    {
      return null;
    }
  }
  
  public static String a(Context paramContext, String paramString)
  {
    paramContext = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    if (paramContext == null) {
      return null;
    }
    return a(paramContext + paramString);
  }
  
  private static String a(String paramString)
  {
    return a("SHA-1", paramString);
  }
  
  private static String a(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = MessageDigest.getInstance(paramString1);
      paramString1.update(paramString2.getBytes());
      paramString1 = paramString1.digest();
      paramString2 = new StringBuilder();
      int j = paramString1.length;
      int i = 0;
      while (i < j)
      {
        int k = paramString1[i];
        paramString2.append(Integer.toHexString(k >> 4 & 0xF));
        paramString2.append(Integer.toHexString(k >> 0 & 0xF));
        i += 1;
      }
      return paramString2.toString();
    }
    catch (NoSuchAlgorithmException paramString1)
    {
      return null;
    }
  }
  
  public static String b(Context paramContext)
  {
    try
    {
      Object localObject = Class.forName("com.facebook.Session");
      paramContext = ((Class)localObject).getMethod("getActiveSession", new Class[0]).invoke(localObject, new Object[0]);
      localObject = ((Class)localObject).getMethod("getApplicationId", new Class[0]);
      if (paramContext != null)
      {
        paramContext = (String)((Method)localObject).invoke(paramContext, new Object[0]);
        return paramContext;
      }
    }
    catch (Exception paramContext)
    {
      return null;
    }
    return null;
  }
  
  public static String c(Context paramContext)
  {
    for (;;)
    {
      try
      {
        Object localObject = Class.forName("com.facebook.Session");
        paramContext = ((Class)localObject).getMethod("getActiveSession", new Class[0]).invoke(localObject, new Object[0]);
        localObject = ((Class)localObject).getMethod("getAccessToken", new Class[0]);
        if (paramContext != null)
        {
          paramContext = (String)((Method)localObject).invoke(paramContext, new Object[0]);
          localObject = paramContext;
          if (paramContext == null) {}
        }
        int i;
        paramContext = null;
      }
      catch (Exception paramContext)
      {
        try
        {
          i = paramContext.length();
          localObject = paramContext;
          if (i < 2) {
            localObject = null;
          }
          return (String)localObject;
        }
        catch (Exception localException)
        {
          return paramContext;
        }
        paramContext = paramContext;
        return null;
      }
    }
  }
  
  public static String[] d(Context paramContext)
  {
    try
    {
      Object localObject = Class.forName("com.facebook.Session");
      paramContext = ((Class)localObject).getMethod("getActiveSession", new Class[0]).invoke(localObject, new Object[0]);
      localObject = ((Class)localObject).getMethod("getPermissions", new Class[0]);
      if (paramContext != null)
      {
        paramContext = (List)((Method)localObject).invoke(paramContext, new Object[0]);
        paramContext = (String[])paramContext.toArray(new String[paramContext.size()]);
        return paramContext;
      }
    }
    catch (Exception paramContext)
    {
      return null;
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
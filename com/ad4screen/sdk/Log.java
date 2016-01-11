package com.ad4screen.sdk;

import com.ad4screen.sdk.common.annotations.API;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@API
public final class Log
{
  public static final String TAG = "com.ad4screen.sdk";
  private static boolean a;
  private static boolean b = false;
  
  private static void a(int paramInt, String paramString)
  {
    int i;
    for (int j = 0; j < paramString.length(); j = i)
    {
      int k = j + 1000;
      i = k;
      if (k > paramString.length()) {
        i = paramString.length();
      }
      android.util.Log.println(paramInt, "com.ad4screen.sdk", paramString.substring(j, i));
    }
  }
  
  private static void a(int paramInt, String paramString, JSONArray paramJSONArray)
  {
    int i = 0;
    for (;;)
    {
      if (i < paramJSONArray.length())
      {
        try
        {
          Object localObject = paramJSONArray.get(i);
          if ((localObject instanceof JSONArray)) {
            a(paramInt, paramString, (JSONArray)localObject);
          } else if ((localObject instanceof JSONObject)) {
            a(paramInt, paramString, (JSONObject)localObject);
          }
        }
        catch (JSONException localJSONException)
        {
          error(paramString + "|Can't properly log array value for index : " + i);
        }
        b(paramInt, paramString + "|" + localJSONException);
      }
      else
      {
        return;
      }
      i += 1;
    }
  }
  
  private static void a(int paramInt, String paramString, JSONObject paramJSONObject)
  {
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      try
      {
        if (!(paramJSONObject.get(str) instanceof JSONObject)) {
          break label86;
        }
        a(paramInt, paramString, (JSONObject)paramJSONObject.get(str));
      }
      catch (JSONException localJSONException)
      {
        error(paramString + "|Can't properly log value for key : " + str);
      }
      continue;
      label86:
      if ((paramJSONObject.get(str) instanceof JSONArray))
      {
        b(paramInt, paramString + "|Sending : " + str + " with value : ");
        a(paramInt, paramString, (JSONArray)paramJSONObject.get(str));
      }
      else
      {
        b(paramInt, paramString + "|Sending : " + str + " with value : " + paramJSONObject.get(str));
      }
    }
  }
  
  private static void b(int paramInt, String paramString)
  {
    if (paramInt == 6)
    {
      error(paramString);
      return;
    }
    if (paramInt == 5)
    {
      warn(paramString);
      return;
    }
    if (paramInt == 4)
    {
      info(paramString);
      return;
    }
    if (paramInt == 3)
    {
      debug(paramString);
      return;
    }
    if (paramInt == 2)
    {
      verbose(paramString);
      return;
    }
    internal(paramString);
  }
  
  public static void debug(String paramString)
  {
    if (a) {
      a(3, "A4S|DEBUG|" + paramString);
    }
  }
  
  public static void debug(String paramString, Throwable paramThrowable)
  {
    if (a) {
      a(3, "A4S|DEBUG|" + paramString + "\n" + android.util.Log.getStackTraceString(paramThrowable));
    }
  }
  
  public static void debug(String paramString, JSONArray paramJSONArray)
  {
    if (a) {
      a(3, paramString, paramJSONArray);
    }
  }
  
  public static void debug(String paramString, JSONObject paramJSONObject)
  {
    if (a) {
      a(3, paramString, paramJSONObject);
    }
  }
  
  public static void error(String paramString)
  {
    if (a) {
      a(6, "A4S|ERROR|" + paramString);
    }
  }
  
  public static void error(String paramString, Throwable paramThrowable)
  {
    if (a) {
      a(6, "A4S|ERROR|" + paramString + "\n" + android.util.Log.getStackTraceString(paramThrowable));
    }
  }
  
  public static void error(String paramString, JSONArray paramJSONArray)
  {
    if (a) {
      a(6, paramString, paramJSONArray);
    }
  }
  
  public static void error(String paramString, JSONObject paramJSONObject)
  {
    if (a) {
      a(6, paramString, paramJSONObject);
    }
  }
  
  public static void info(String paramString)
  {
    if (a) {
      a(4, "A4S|INFO|" + paramString);
    }
  }
  
  public static void info(String paramString, Throwable paramThrowable)
  {
    if (a) {
      a(4, "A4S|INFO|" + paramString + "\n" + android.util.Log.getStackTraceString(paramThrowable));
    }
  }
  
  public static void info(String paramString, JSONArray paramJSONArray)
  {
    if (a) {
      a(4, paramString, paramJSONArray);
    }
  }
  
  public static void info(String paramString, JSONObject paramJSONObject)
  {
    if (a) {
      a(4, paramString, paramJSONObject);
    }
  }
  
  public static void internal(String paramString)
  {
    if ((b) && (a)) {
      a(2, "A4S|INTERNAL|" + paramString);
    }
  }
  
  public static void internal(String paramString, Throwable paramThrowable)
  {
    if ((b) && (a)) {
      a(2, "A4S|INTERNAL|" + paramString + "\n" + android.util.Log.getStackTraceString(paramThrowable));
    }
  }
  
  public static void internal(String paramString, JSONArray paramJSONArray)
  {
    if ((b) && (a)) {
      a(2, paramString, paramJSONArray);
    }
  }
  
  public static void internal(String paramString, JSONObject paramJSONObject)
  {
    if ((b) && (a)) {
      a(2, paramString, paramJSONObject);
    }
  }
  
  public static void setEnabled(boolean paramBoolean)
  {
    a = paramBoolean;
  }
  
  public static void setInternalLoggingEnabled(boolean paramBoolean)
  {
    b = paramBoolean;
  }
  
  public static void verbose(String paramString)
  {
    if (a) {
      a(2, "A4S|VERBOSE|" + paramString);
    }
  }
  
  public static void verbose(String paramString, JSONArray paramJSONArray)
  {
    if (a) {
      a(2, paramString, paramJSONArray);
    }
  }
  
  public static void verbose(String paramString, JSONObject paramJSONObject)
  {
    if (a) {
      a(2, paramString, paramJSONObject);
    }
  }
  
  public static void warn(String paramString)
  {
    if (a) {
      a(5, "A4S|WARNING|" + paramString);
    }
  }
  
  public static void warn(String paramString, Throwable paramThrowable)
  {
    if (a) {
      a(5, "A4S|WARNING|" + paramString + "\n" + android.util.Log.getStackTraceString(paramThrowable));
    }
  }
  
  public static void warn(String paramString, JSONArray paramJSONArray)
  {
    if (a) {
      a(5, paramString, paramJSONArray);
    }
  }
  
  public static void warn(String paramString, JSONObject paramJSONObject)
  {
    if (a) {
      a(5, paramString, paramJSONObject);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/Log.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
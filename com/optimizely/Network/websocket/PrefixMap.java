package com.optimizely.Network.websocket;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.HashMap;

class PrefixMap
{
  private final HashMap<String, String> mPrefixes = new HashMap();
  private final HashMap<String, String> mUris = new HashMap();
  
  @Nullable
  private String resolve(@NonNull String paramString)
  {
    int i = paramString.indexOf(':');
    if (i > 0)
    {
      String str = paramString.substring(0, i);
      if (this.mPrefixes.containsKey(str)) {
        return (String)this.mPrefixes.get(str) + paramString.substring(i + 1);
      }
    }
    return null;
  }
  
  public void clear()
  {
    this.mPrefixes.clear();
    this.mUris.clear();
  }
  
  public String get(String paramString)
  {
    return (String)this.mPrefixes.get(paramString);
  }
  
  @Nullable
  public String remove(String paramString)
  {
    if (this.mPrefixes.containsKey(paramString))
    {
      String str = (String)this.mPrefixes.get(paramString);
      this.mPrefixes.remove(paramString);
      this.mUris.remove(str);
      return str;
    }
    return null;
  }
  
  @Nullable
  public String resolveOrPass(@NonNull String paramString)
  {
    String str = resolve(paramString);
    if (str != null) {
      return str;
    }
    return paramString;
  }
  
  public void set(String paramString1, String paramString2)
  {
    this.mPrefixes.put(paramString1, paramString2);
    this.mUris.put(paramString2, paramString1);
  }
  
  @NonNull
  public String shrink(@NonNull String paramString)
  {
    int i = paramString.length();
    for (;;)
    {
      String str = paramString;
      if (i > 0)
      {
        str = paramString.substring(0, i);
        str = (String)this.mUris.get(str);
        if (str != null) {
          str = str + ':' + paramString.substring(i);
        }
      }
      else
      {
        return str;
      }
      i -= 1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/websocket/PrefixMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
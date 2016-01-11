package com.afollestad.materialdialogs.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.SimpleArrayMap;

public class TypefaceHelper
{
  private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap();
  
  public static Typeface get(Context paramContext, String paramString)
  {
    synchronized (cache)
    {
      boolean bool = cache.containsKey(paramString);
      if (!bool) {
        try
        {
          paramContext = Typeface.createFromAsset(paramContext.getAssets(), String.format("fonts/%s", new Object[] { paramString }));
          cache.put(paramString, paramContext);
          return paramContext;
        }
        catch (RuntimeException paramContext)
        {
          return null;
        }
      }
    }
    paramContext = (Typeface)cache.get(paramString);
    return paramContext;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/afollestad/materialdialogs/util/TypefaceHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
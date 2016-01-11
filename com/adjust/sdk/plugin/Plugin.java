package com.adjust.sdk.plugin;

import android.content.Context;
import java.util.Map.Entry;

public abstract interface Plugin
{
  public abstract Map.Entry<String, String> getParameter(Context paramContext);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/plugin/Plugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
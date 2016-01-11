package io.fabric.sdk.android.services.settings;

import org.json.JSONObject;

public abstract interface CachedSettingsIo
{
  public abstract JSONObject readCachedSettings();
  
  public abstract void writeCachedSettings(long paramLong, JSONObject paramJSONObject);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/settings/CachedSettingsIo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
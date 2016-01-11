package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import org.json.JSONException;
import org.json.JSONObject;

public abstract interface SettingsJsonTransform
{
  public abstract SettingsData buildFromJson(CurrentTimeProvider paramCurrentTimeProvider, JSONObject paramJSONObject)
    throws JSONException;
  
  public abstract JSONObject toJson(SettingsData paramSettingsData)
    throws JSONException;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/settings/SettingsJsonTransform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
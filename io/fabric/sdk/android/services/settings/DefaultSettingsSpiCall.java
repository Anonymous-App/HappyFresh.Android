package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class DefaultSettingsSpiCall
  extends AbstractSpiCall
  implements SettingsSpiCall
{
  static final String BUILD_VERSION_PARAM = "build_version";
  static final String DISPLAY_VERSION_PARAM = "display_version";
  static final String ICON_HASH = "icon_hash";
  static final String INSTANCE_PARAM = "instance";
  static final String SOURCE_PARAM = "source";
  
  public DefaultSettingsSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory)
  {
    this(paramKit, paramString1, paramString2, paramHttpRequestFactory, HttpMethod.GET);
  }
  
  DefaultSettingsSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory, HttpMethod paramHttpMethod)
  {
    super(paramKit, paramString1, paramString2, paramHttpRequestFactory, paramHttpMethod);
  }
  
  private HttpRequest applyHeadersTo(HttpRequest paramHttpRequest, SettingsRequest paramSettingsRequest)
  {
    return paramHttpRequest.header("X-CRASHLYTICS-API-KEY", paramSettingsRequest.apiKey).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-D", paramSettingsRequest.deviceId).header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).header("Accept", "application/json");
  }
  
  private JSONObject getJsonObjectFrom(String paramString)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      return localJSONObject;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().d("Fabric", "Failed to parse settings JSON from " + getUrl(), localException);
      Fabric.getLogger().d("Fabric", "Settings response " + paramString);
    }
    return null;
  }
  
  private Map<String, String> getQueryParamsFor(SettingsRequest paramSettingsRequest)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("build_version", paramSettingsRequest.buildVersion);
    localHashMap.put("display_version", paramSettingsRequest.displayVersion);
    localHashMap.put("source", Integer.toString(paramSettingsRequest.source));
    if (paramSettingsRequest.iconHash != null) {
      localHashMap.put("icon_hash", paramSettingsRequest.iconHash);
    }
    paramSettingsRequest = paramSettingsRequest.instanceId;
    if (!CommonUtils.isNullOrEmpty(paramSettingsRequest)) {
      localHashMap.put("instance", paramSettingsRequest);
    }
    return localHashMap;
  }
  
  JSONObject handleResponse(HttpRequest paramHttpRequest)
  {
    int i = paramHttpRequest.code();
    Fabric.getLogger().d("Fabric", "Settings result was: " + i);
    if (requestWasSuccessful(i)) {
      return getJsonObjectFrom(paramHttpRequest.body());
    }
    Fabric.getLogger().e("Fabric", "Failed to retrieve settings from " + getUrl());
    return null;
  }
  
  public JSONObject invoke(SettingsRequest paramSettingsRequest)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    try
    {
      Map localMap = getQueryParamsFor(paramSettingsRequest);
      localObject1 = localObject2;
      localObject2 = getHttpRequest(localMap);
      localObject1 = localObject2;
      paramSettingsRequest = applyHeadersTo((HttpRequest)localObject2, paramSettingsRequest);
      localObject1 = paramSettingsRequest;
      Fabric.getLogger().d("Fabric", "Requesting settings from " + getUrl());
      localObject1 = paramSettingsRequest;
      Fabric.getLogger().d("Fabric", "Settings query params were: " + localMap);
      localObject1 = paramSettingsRequest;
      localObject2 = handleResponse(paramSettingsRequest);
      if (paramSettingsRequest != null) {
        Fabric.getLogger().d("Fabric", "Settings request ID: " + paramSettingsRequest.header("X-REQUEST-ID"));
      }
      return (JSONObject)localObject2;
    }
    finally
    {
      if (localObject1 != null) {
        Fabric.getLogger().d("Fabric", "Settings request ID: " + ((HttpRequest)localObject1).header("X-REQUEST-ID"));
      }
    }
  }
  
  boolean requestWasSuccessful(int paramInt)
  {
    return (paramInt == 200) || (paramInt == 201) || (paramInt == 202) || (paramInt == 203);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/settings/DefaultSettingsSpiCall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
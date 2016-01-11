package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.facebook.internal.Validate;
import org.json.JSONException;
import org.json.JSONObject;

class AccessTokenCache
{
  static final String CACHED_ACCESS_TOKEN_KEY = "com.facebook.AccessTokenManager.CachedAccessToken";
  private final SharedPreferences sharedPreferences;
  private LegacyTokenHelper tokenCachingStrategy;
  private final SharedPreferencesTokenCachingStrategyFactory tokenCachingStrategyFactory;
  
  public AccessTokenCache()
  {
    this(FacebookSdk.getApplicationContext().getSharedPreferences("com.facebook.AccessTokenManager.SharedPreferences", 0), new SharedPreferencesTokenCachingStrategyFactory());
  }
  
  AccessTokenCache(SharedPreferences paramSharedPreferences, SharedPreferencesTokenCachingStrategyFactory paramSharedPreferencesTokenCachingStrategyFactory)
  {
    this.sharedPreferences = paramSharedPreferences;
    this.tokenCachingStrategyFactory = paramSharedPreferencesTokenCachingStrategyFactory;
  }
  
  private AccessToken getCachedAccessToken()
  {
    AccessToken localAccessToken = null;
    String str = this.sharedPreferences.getString("com.facebook.AccessTokenManager.CachedAccessToken", null);
    if (str != null) {}
    try
    {
      localAccessToken = AccessToken.createFromJSONObject(new JSONObject(str));
      return localAccessToken;
    }
    catch (JSONException localJSONException) {}
    return null;
  }
  
  private AccessToken getLegacyAccessToken()
  {
    Object localObject2 = null;
    Bundle localBundle = getTokenCachingStrategy().load();
    Object localObject1 = localObject2;
    if (localBundle != null)
    {
      localObject1 = localObject2;
      if (LegacyTokenHelper.hasTokenInformation(localBundle)) {
        localObject1 = AccessToken.createFromLegacyCache(localBundle);
      }
    }
    return (AccessToken)localObject1;
  }
  
  private LegacyTokenHelper getTokenCachingStrategy()
  {
    if (this.tokenCachingStrategy == null) {}
    try
    {
      if (this.tokenCachingStrategy == null) {
        this.tokenCachingStrategy = this.tokenCachingStrategyFactory.create();
      }
      return this.tokenCachingStrategy;
    }
    finally {}
  }
  
  private boolean hasCachedAccessToken()
  {
    return this.sharedPreferences.contains("com.facebook.AccessTokenManager.CachedAccessToken");
  }
  
  private boolean shouldCheckLegacyToken()
  {
    return FacebookSdk.isLegacyTokenUpgradeSupported();
  }
  
  public void clear()
  {
    this.sharedPreferences.edit().remove("com.facebook.AccessTokenManager.CachedAccessToken").apply();
    if (shouldCheckLegacyToken()) {
      getTokenCachingStrategy().clear();
    }
  }
  
  public AccessToken load()
  {
    Object localObject = null;
    if (hasCachedAccessToken()) {
      localObject = getCachedAccessToken();
    }
    AccessToken localAccessToken;
    do
    {
      do
      {
        return (AccessToken)localObject;
      } while (!shouldCheckLegacyToken());
      localAccessToken = getLegacyAccessToken();
      localObject = localAccessToken;
    } while (localAccessToken == null);
    save(localAccessToken);
    getTokenCachingStrategy().clear();
    return localAccessToken;
  }
  
  public void save(AccessToken paramAccessToken)
  {
    Validate.notNull(paramAccessToken, "accessToken");
    try
    {
      paramAccessToken = paramAccessToken.toJSONObject();
      this.sharedPreferences.edit().putString("com.facebook.AccessTokenManager.CachedAccessToken", paramAccessToken.toString()).apply();
      return;
    }
    catch (JSONException paramAccessToken) {}
  }
  
  static class SharedPreferencesTokenCachingStrategyFactory
  {
    public LegacyTokenHelper create()
    {
      return new LegacyTokenHelper(FacebookSdk.getApplicationContext());
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/AccessTokenCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
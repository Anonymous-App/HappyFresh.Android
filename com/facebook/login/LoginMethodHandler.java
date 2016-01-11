package com.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.Utility;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

abstract class LoginMethodHandler
  implements Parcelable
{
  protected LoginClient loginClient;
  Map<String, String> methodLoggingExtras;
  
  LoginMethodHandler(Parcel paramParcel)
  {
    this.methodLoggingExtras = Utility.readStringMapFromParcel(paramParcel);
  }
  
  LoginMethodHandler(LoginClient paramLoginClient)
  {
    this.loginClient = paramLoginClient;
  }
  
  static AccessToken createAccessTokenFromNativeLogin(Bundle paramBundle, AccessTokenSource paramAccessTokenSource, String paramString)
  {
    Date localDate = Utility.getBundleLongAsDate(paramBundle, "com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH", new Date(0L));
    ArrayList localArrayList = paramBundle.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
    String str = paramBundle.getString("com.facebook.platform.extra.ACCESS_TOKEN");
    if (Utility.isNullOrEmpty(str)) {
      return null;
    }
    return new AccessToken(str, paramString, paramBundle.getString("com.facebook.platform.extra.USER_ID"), localArrayList, null, paramAccessTokenSource, localDate, new Date());
  }
  
  public static AccessToken createAccessTokenFromWebBundle(Collection<String> paramCollection, Bundle paramBundle, AccessTokenSource paramAccessTokenSource, String paramString)
    throws FacebookException
  {
    Date localDate = Utility.getBundleLongAsDate(paramBundle, "expires_in", new Date());
    String str1 = paramBundle.getString("access_token");
    Object localObject = paramBundle.getString("granted_scopes");
    if (!Utility.isNullOrEmpty((String)localObject)) {
      paramCollection = new ArrayList(Arrays.asList(((String)localObject).split(",")));
    }
    String str2 = paramBundle.getString("denied_scopes");
    localObject = null;
    if (!Utility.isNullOrEmpty(str2)) {
      localObject = new ArrayList(Arrays.asList(str2.split(",")));
    }
    if (Utility.isNullOrEmpty(str1)) {
      return null;
    }
    return new AccessToken(str1, paramString, getUserIDFromSignedRequest(paramBundle.getString("signed_request")), paramCollection, (Collection)localObject, paramAccessTokenSource, localDate, new Date());
  }
  
  private static String getUserIDFromSignedRequest(String paramString)
    throws FacebookException
  {
    if ((paramString == null) || (paramString.isEmpty())) {
      throw new FacebookException("Authorization response does not contain the signed_request");
    }
    try
    {
      paramString = paramString.split("\\.");
      if (paramString.length == 2)
      {
        paramString = new JSONObject(new String(Base64.decode(paramString[1], 0), "UTF-8")).getString("user_id");
        return paramString;
      }
    }
    catch (JSONException paramString)
    {
      throw new FacebookException("Failed to retrieve user_id from signed_request");
    }
    catch (UnsupportedEncodingException paramString)
    {
      for (;;) {}
    }
  }
  
  protected void addLoggingExtra(String paramString, Object paramObject)
  {
    if (this.methodLoggingExtras == null) {
      this.methodLoggingExtras = new HashMap();
    }
    Map localMap = this.methodLoggingExtras;
    if (paramObject == null) {}
    for (paramObject = null;; paramObject = paramObject.toString())
    {
      localMap.put(paramString, paramObject);
      return;
    }
  }
  
  void cancel() {}
  
  abstract String getNameForLogging();
  
  protected void logWebLoginCompleted(String paramString)
  {
    String str = this.loginClient.getPendingRequest().getApplicationId();
    AppEventsLogger localAppEventsLogger = AppEventsLogger.newLogger(this.loginClient.getActivity(), str);
    Bundle localBundle = new Bundle();
    localBundle.putString("fb_web_login_e2e", paramString);
    localBundle.putLong("fb_web_login_switchback_time", System.currentTimeMillis());
    localBundle.putString("app_id", str);
    localAppEventsLogger.logSdkEvent("fb_dialogs_web_login_dialog_complete", null, localBundle);
  }
  
  boolean needsInternetPermission()
  {
    return false;
  }
  
  boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    return false;
  }
  
  void setLoginClient(LoginClient paramLoginClient)
  {
    if (this.loginClient != null) {
      throw new FacebookException("Can't set LoginClient if it is already set.");
    }
    this.loginClient = paramLoginClient;
  }
  
  abstract boolean tryAuthorize(LoginClient.Request paramRequest);
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    Utility.writeStringMapToParcel(paramParcel, this.methodLoggingExtras);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/login/LoginMethodHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
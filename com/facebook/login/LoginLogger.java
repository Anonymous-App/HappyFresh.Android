package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsLogger;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class LoginLogger
{
  static final String EVENT_EXTRAS_DEFAULT_AUDIENCE = "default_audience";
  static final String EVENT_EXTRAS_IS_REAUTHORIZE = "isReauthorize";
  static final String EVENT_EXTRAS_LOGIN_BEHAVIOR = "login_behavior";
  static final String EVENT_EXTRAS_MISSING_INTERNET_PERMISSION = "no_internet_permission";
  static final String EVENT_EXTRAS_NEW_PERMISSIONS = "new_permissions";
  static final String EVENT_EXTRAS_NOT_TRIED = "not_tried";
  static final String EVENT_EXTRAS_PERMISSIONS = "permissions";
  static final String EVENT_EXTRAS_REQUEST_CODE = "request_code";
  static final String EVENT_EXTRAS_TRY_LOGIN_ACTIVITY = "try_login_activity";
  static final String EVENT_NAME_LOGIN_COMPLETE = "fb_mobile_login_complete";
  static final String EVENT_NAME_LOGIN_METHOD_COMPLETE = "fb_mobile_login_method_complete";
  static final String EVENT_NAME_LOGIN_METHOD_START = "fb_mobile_login_method_start";
  static final String EVENT_NAME_LOGIN_START = "fb_mobile_login_start";
  static final String EVENT_PARAM_AUTH_LOGGER_ID = "0_auth_logger_id";
  static final String EVENT_PARAM_ERROR_CODE = "4_error_code";
  static final String EVENT_PARAM_ERROR_MESSAGE = "5_error_message";
  static final String EVENT_PARAM_EXTRAS = "6_extras";
  static final String EVENT_PARAM_LOGIN_RESULT = "2_result";
  static final String EVENT_PARAM_METHOD = "3_method";
  static final String EVENT_PARAM_METHOD_RESULT_SKIPPED = "skipped";
  static final String EVENT_PARAM_TIMESTAMP = "1_timestamp_ms";
  private final AppEventsLogger appEventsLogger;
  private String applicationId;
  
  LoginLogger(Context paramContext, String paramString)
  {
    this.applicationId = paramString;
    this.appEventsLogger = AppEventsLogger.newLogger(paramContext, paramString);
  }
  
  static Bundle newAuthorizationLoggingBundle(String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putLong("1_timestamp_ms", System.currentTimeMillis());
    localBundle.putString("0_auth_logger_id", paramString);
    localBundle.putString("3_method", "");
    localBundle.putString("2_result", "");
    localBundle.putString("5_error_message", "");
    localBundle.putString("4_error_code", "");
    localBundle.putString("6_extras", "");
    return localBundle;
  }
  
  public String getApplicationId()
  {
    return this.applicationId;
  }
  
  public void logAuthorizationMethodComplete(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Map<String, String> paramMap)
  {
    paramString1 = newAuthorizationLoggingBundle(paramString1);
    if (paramString3 != null) {
      paramString1.putString("2_result", paramString3);
    }
    if (paramString4 != null) {
      paramString1.putString("5_error_message", paramString4);
    }
    if (paramString5 != null) {
      paramString1.putString("4_error_code", paramString5);
    }
    if ((paramMap != null) && (!paramMap.isEmpty())) {
      paramString1.putString("6_extras", new JSONObject(paramMap).toString());
    }
    paramString1.putString("3_method", paramString2);
    this.appEventsLogger.logSdkEvent("fb_mobile_login_method_complete", null, paramString1);
  }
  
  public void logAuthorizationMethodStart(String paramString1, String paramString2)
  {
    paramString1 = newAuthorizationLoggingBundle(paramString1);
    paramString1.putString("3_method", paramString2);
    this.appEventsLogger.logSdkEvent("fb_mobile_login_method_start", null, paramString1);
  }
  
  public void logCompleteLogin(String paramString, Map<String, String> paramMap1, LoginClient.Result.Code paramCode, Map<String, String> paramMap2, Exception paramException)
  {
    Bundle localBundle = newAuthorizationLoggingBundle(paramString);
    if (paramCode != null) {
      localBundle.putString("2_result", paramCode.getLoggingValue());
    }
    if ((paramException != null) && (paramException.getMessage() != null)) {
      localBundle.putString("5_error_message", paramException.getMessage());
    }
    paramString = null;
    if (!paramMap1.isEmpty()) {
      paramString = new JSONObject(paramMap1);
    }
    paramCode = paramString;
    if (paramMap2 != null)
    {
      paramMap1 = paramString;
      if (paramString == null) {
        paramMap1 = new JSONObject();
      }
      try
      {
        paramString = paramMap2.entrySet().iterator();
        for (;;)
        {
          paramCode = paramMap1;
          if (!paramString.hasNext()) {
            break;
          }
          paramCode = (Map.Entry)paramString.next();
          paramMap1.put((String)paramCode.getKey(), paramCode.getValue());
        }
        if (paramCode == null) {
          break label162;
        }
      }
      catch (JSONException paramString)
      {
        paramCode = paramMap1;
      }
    }
    localBundle.putString("6_extras", paramCode.toString());
    label162:
    this.appEventsLogger.logSdkEvent("fb_mobile_login_complete", null, localBundle);
  }
  
  public void logStartLogin(LoginClient.Request paramRequest)
  {
    Bundle localBundle = newAuthorizationLoggingBundle(paramRequest.getAuthId());
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("login_behavior", paramRequest.getLoginBehavior().toString());
      localJSONObject.put("request_code", LoginClient.getLoginRequestCode());
      localJSONObject.put("permissions", TextUtils.join(",", paramRequest.getPermissions()));
      localJSONObject.put("default_audience", paramRequest.getDefaultAudience().toString());
      localJSONObject.put("isReauthorize", paramRequest.isRerequest());
      localBundle.putString("6_extras", localJSONObject.toString());
      this.appEventsLogger.logSdkEvent("fb_mobile_login_start", null, localBundle);
      return;
    }
    catch (JSONException paramRequest)
    {
      for (;;) {}
    }
  }
  
  public void logUnexpectedError(String paramString1, String paramString2)
  {
    logUnexpectedError(paramString1, paramString2, "");
  }
  
  public void logUnexpectedError(String paramString1, String paramString2, String paramString3)
  {
    Bundle localBundle = newAuthorizationLoggingBundle("");
    localBundle.putString("2_result", LoginClient.Result.Code.ERROR.getLoggingValue());
    localBundle.putString("5_error_message", paramString2);
    localBundle.putString("3_method", paramString3);
    this.appEventsLogger.logSdkEvent(paramString1, null, localBundle);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/login/LoginLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.happyfresh.managers;

import android.text.TextUtils;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartRetrofitCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartRestClient;
import com.happyfresh.models.User;
import com.happyfresh.services.LoginService;
import com.happyfresh.services.UserService;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class LoginManager
  extends BaseManager
{
  private static final String TAG = LoginManager.class.getSimpleName();
  
  public LoginManager(ICartApplication paramICartApplication)
  {
    super(paramICartApplication);
  }
  
  public void login(String paramString1, String paramString2, String paramString3, String paramString4, final ICartCallback paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    paramString1 = (LoginService)this.mICartRestClient.buildLoginAdapter(paramString1, paramString2).create(LoginService.class);
    paramString2 = new JSONObject();
    if (!TextUtils.isEmpty(paramString4)) {
      paramString2.put("device_advertising_id", paramString4);
    }
    paramString1.login(paramString3, new TypedByteArray("application/json", paramString2.toString().getBytes("UTF-8")), new Callback()
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(User paramAnonymousUser, Response paramAnonymousResponse)
      {
        LoginManager.this.mICartApplication.getSharedPreferencesManager().saveUserAttributes(paramAnonymousUser);
        LoginManager.this.mICartApplication.setCurrentOrder();
        if (Locale.ENGLISH.getLanguage().equals(paramAnonymousUser.language)) {
          LoginManager.this.mICartApplication.getSharedPreferencesManager().saveLocale(Locale.ENGLISH);
        }
        for (;;)
        {
          LoginManager.this.mICartRestClient.reset();
          LoginManager.this.mICartApplication.getICartNotification().raiseCurrentUserSetEvent(this, paramAnonymousUser);
          if (paramICartCallback != null) {
            paramICartCallback.onSuccess(paramAnonymousUser);
          }
          return;
          paramAnonymousResponse = new Locale(paramAnonymousUser.language, LoginManager.this.mICartApplication.getCountryCode());
          LoginManager.this.mICartApplication.getSharedPreferencesManager().saveLocale(paramAnonymousResponse);
        }
      }
    });
  }
  
  public void logout(final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getUserService().logout(new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse)
      {
        LoginManager.this.mICartApplication.onLogout();
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
  
  public void signUp(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, final ICartCallback paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    LoginService localLoginService = (LoginService)this.mICartRestClient.buildLoginAdapter(paramString3, paramString4).create(LoginService.class);
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("first_name", paramString1);
    localJSONObject.put("last_name", paramString2);
    localJSONObject.put("email", paramString3);
    localJSONObject.put("password", paramString4);
    localJSONObject.put("password_confirmation", paramString4);
    if (!TextUtils.isEmpty(paramString5)) {
      localJSONObject.put("referral_code", paramString5);
    }
    paramString1 = new JSONObject();
    paramString1.put("user", localJSONObject);
    if (!TextUtils.isEmpty(paramString7)) {
      paramString1.put("device_advertising_id", paramString7);
    }
    localLoginService.signUp(paramString6, new TypedByteArray("application/json", paramString1.toString().getBytes("UTF-8")), new Callback()
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(User paramAnonymousUser, Response paramAnonymousResponse)
      {
        LoginManager.this.mICartApplication.getSharedPreferencesManager().saveUserAttributes(paramAnonymousUser);
        LoginManager.this.mICartApplication.setCurrentOrder();
        if (Locale.ENGLISH.getLanguage().equals(paramAnonymousUser.language)) {
          LoginManager.this.mICartApplication.getSharedPreferencesManager().saveLocale(Locale.ENGLISH);
        }
        for (;;)
        {
          LoginManager.this.mICartRestClient.reset();
          LoginManager.this.mICartApplication.getICartNotification().raiseCurrentUserSetEvent(this, paramAnonymousUser);
          if (paramICartCallback != null) {
            paramICartCallback.onSuccess(paramAnonymousUser);
          }
          return;
          paramAnonymousResponse = new Locale(paramAnonymousUser.language, LoginManager.this.mICartApplication.getCountryCode());
          LoginManager.this.mICartApplication.getSharedPreferencesManager().saveLocale(paramAnonymousResponse);
        }
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/managers/LoginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
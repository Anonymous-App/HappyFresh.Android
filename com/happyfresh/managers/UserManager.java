package com.happyfresh.managers;

import android.text.TextUtils;
import android.widget.Toast;
import com.appsee.Appsee;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartRetrofitCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartRestClient;
import com.happyfresh.models.Address;
import com.happyfresh.models.AddressResponse;
import com.happyfresh.models.RestError;
import com.happyfresh.models.StoreCreditResponse;
import com.happyfresh.models.User;
import com.happyfresh.services.UserService;
import com.happyfresh.utils.AdjustUtils;
import com.happyfresh.utils.GAUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;
import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class UserManager
  extends BaseManager
{
  public UserManager(ICartApplication paramICartApplication)
  {
    super(paramICartApplication);
  }
  
  private void trackSignUp(User paramUser)
  {
    GAUtils.trackSignUpFB(this.mICartApplication, paramUser);
    AdjustUtils.trackSignUp(paramUser);
    MixpanelTrackerUtils.trackFBSignUp(this.mICartApplication);
  }
  
  public void addAddress(String paramString1, String paramString2, String paramString3, String paramString4, Long paramLong1, Long paramLong2, Long paramLong3, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, boolean paramBoolean, int paramInt, String paramString14, final ICartCallback<Address> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("firstname", paramString1);
    localJSONObject.put("lastname", paramString2);
    localJSONObject.put("address1", paramString3);
    localJSONObject.put("address2", paramString4);
    localJSONObject.put("city", paramString5);
    localJSONObject.put("country_id", paramLong1);
    localJSONObject.put("state_id", paramLong2);
    localJSONObject.put("sub_district_id", paramLong3);
    localJSONObject.put("zipcode", paramString6);
    localJSONObject.put("phone", paramString7);
    localJSONObject.put("primary", paramBoolean);
    localJSONObject.put("address_type", paramInt);
    localJSONObject.put("number", paramString8);
    localJSONObject.put("floor", paramString9);
    localJSONObject.put("unit", paramString10);
    localJSONObject.put("lane", paramString11);
    localJSONObject.put("alley", paramString12);
    localJSONObject.put("company_tax_id", paramString13);
    if (paramString14 != null) {
      localJSONObject.put("delivery_instruction", paramString14);
    }
    paramString1 = new TypedByteArray("application/json", localJSONObject.toString().getBytes("UTF-8"));
    this.mICartRestClient.getUserService().createAddress(paramString1, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Address paramAnonymousAddress, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousAddress);
        }
      }
    });
  }
  
  public void checkVerificationCode(String paramString, final ICartCallback<Object> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("verification_code", paramString);
    paramString = new TypedByteArray("application/json", localJSONObject.toString().getBytes("UTF-8"));
    this.mICartRestClient.getUserService().checkVerificationCode(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        RestError localRestError;
        String str;
        if (paramAnonymousRetrofitError != null)
        {
          localRestError = RestError.getRestError(paramAnonymousRetrofitError);
          if (localRestError != null)
          {
            str = UserManager.this.mICartApplication.getString(2131165565);
            if (TextUtils.isEmpty(localRestError.message)) {
              break label77;
            }
            str = localRestError.message;
          }
        }
        for (;;)
        {
          Toast.makeText(UserManager.this.mICartApplication, str, 1).show();
          if (paramICartCallback != null) {
            paramICartCallback.onFailure(paramAnonymousRetrofitError);
          }
          return;
          label77:
          if (!TextUtils.isEmpty(localRestError.exception)) {
            str = localRestError.exception;
          }
        }
      }
      
      public void success(User paramAnonymousUser, Response paramAnonymousResponse)
      {
        if (paramAnonymousUser != null) {
          UserManager.this.mICartApplication.getSharedPreferencesManager().saveUserAttributes(paramAnonymousUser);
        }
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousUser);
        }
      }
    });
  }
  
  public void deleteAccengageDeviceId(String paramString, final ICartCallback<Object> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    this.mICartRestClient.getUserService().deleteAccengageDevice(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramAnonymousRetrofitError != null) {
          RestError.showExceptionToast(UserManager.this.mICartApplication, paramAnonymousRetrofitError);
        }
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
  
  public void deleteAddress(Long paramLong, final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getUserService().deleteAddress(paramLong, new ICartRetrofitCallback(this.mICartApplication)
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
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
  
  public void fetchCredits(final ICartCallback<StoreCreditResponse> paramICartCallback)
  {
    this.mICartRestClient.getUserService().fetchStoreCredits(new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(StoreCreditResponse paramAnonymousStoreCreditResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousStoreCreditResponse);
        }
      }
    });
  }
  
  public void getMyAddresses(String paramString, Integer paramInteger1, Integer paramInteger2, final ICartCallback<AddressResponse> paramICartCallback)
  {
    this.mICartRestClient.getUserService().getAddresses(paramString, paramInteger1, paramInteger2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(AddressResponse paramAnonymousAddressResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousAddressResponse);
        }
      }
    });
  }
  
  public void loginFacebook(String paramString, final ICartCallback<Object> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("access_token", paramString);
    paramString = new TypedByteArray("application/json", localJSONObject.toString().getBytes("UTF-8"));
    this.mICartRestClient.getUserService().loginFacebook(paramString, new Callback()
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        if (paramAnonymousRetrofitError != null)
        {
          RestError localRestError = RestError.getRestError(paramAnonymousRetrofitError);
          if ((localRestError != null) && (!TextUtils.isEmpty(localRestError.message))) {
            Toast.makeText(UserManager.this.mICartApplication, localRestError.message, 0).show();
          }
        }
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(User paramAnonymousUser, Response paramAnonymousResponse)
      {
        if ((paramAnonymousResponse != null) && (paramAnonymousUser != null))
        {
          if (paramAnonymousResponse.getStatus() == 201)
          {
            UserManager.this.mICartApplication.getSharedPreferencesManager().markingNeedReferralCode(true);
            UserManager.this.mICartApplication.getSharedPreferencesManager().markingNewUser(true);
            UserManager.this.trackSignUp(paramAnonymousUser);
          }
          UserManager.this.mICartApplication.getSharedPreferencesManager().saveUserAttributes(paramAnonymousUser);
          UserManager.this.mICartApplication.setCurrentOrder();
          UserManager.this.mICartApplication.getICartNotification().raiseCurrentUserSetEvent(this, paramAnonymousUser);
          UserManager.this.mICartRestClient.reset();
          UserManager.this.mICartApplication.getShippingAddress();
          UserManager.this.mICartApplication.subscribeParseChannels();
          UserManager.this.mICartApplication.setMixpanelUser();
          UserManager.this.mICartApplication.setMixpanelPushNotifications();
          UserManager.this.mICartApplication.setAccengageUser();
        }
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousUser);
        }
      }
    });
  }
  
  public void resetPassword(String paramString, final ICartCallback<Object> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    new JSONArray();
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("email", paramString);
    paramString = new TypedByteArray("application/json", localJSONObject.toString().getBytes("UTF-8"));
    this.mICartRestClient.getUserService().resetPassword(paramString, new ICartRetrofitCallback(this.mICartApplication)
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
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
  
  public void sendPreferredLanguage(String paramString, final ICartCallback<User> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("language", paramString);
    paramString = new TypedByteArray("application/json", localJSONObject.toString().getBytes("UTF-8"));
    this.mICartRestClient.getUserService().sendPreferredLanguage(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(User paramAnonymousUser, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousUser);
        }
      }
    });
  }
  
  public void sendUserData(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, final ICartCallback<Object> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("device_advertising_id", paramString1);
    if (!TextUtils.isEmpty(paramString2)) {
      localJSONObject.put("city", paramString2);
    }
    if (!TextUtils.isEmpty(paramString3)) {
      localJSONObject.put("sublocality", paramString3);
    }
    if (!TextUtils.isEmpty(paramString4)) {
      localJSONObject.put("zipcode", paramString4);
    }
    paramString1 = new JSONObject();
    paramString1.put("user_data", localJSONObject);
    if (!TextUtils.isEmpty(paramString5)) {
      paramString1.put("accengage_device_id", paramString5);
    }
    paramString1 = new TypedByteArray("application/json", paramString1.toString().getBytes("UTF-8"));
    this.mICartRestClient.getUserService().sendUserData(paramString1, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
  
  public void setAccengageDeviceId(String paramString, final ICartCallback<Object> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("accengage_device_id", paramString);
    paramString = new TypedByteArray("application/json", localJSONObject.toString().getBytes("UTF-8"));
    this.mICartRestClient.getUserService().setAccengageDevice(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramAnonymousRetrofitError != null) {
          RestError.showExceptionToast(UserManager.this.mICartApplication, paramAnonymousRetrofitError);
        }
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
  
  public void setReferral(final String paramString, final ICartCallback<Object> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    Object localObject = new JSONObject();
    ((JSONObject)localObject).put("referral_code", paramString);
    localObject = new TypedByteArray("application/json", ((JSONObject)localObject).toString().getBytes("UTF-8"));
    this.mICartRestClient.getUserService().setReferral((TypedInput)localObject, new Callback()
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        if (paramAnonymousRetrofitError != null)
        {
          RestError localRestError = RestError.getRestError(paramAnonymousRetrofitError);
          if ((localRestError != null) && (!TextUtils.isEmpty(localRestError.message)))
          {
            Toast.makeText(UserManager.this.mICartApplication, localRestError.message, 0).show();
            Appsee.addEvent("FB Invite Code Error Toast");
          }
        }
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(User paramAnonymousUser, Response paramAnonymousResponse)
      {
        if (paramAnonymousUser != null)
        {
          UserManager.this.mICartApplication.getSharedPreferencesManager().saveUserAttributes(paramAnonymousUser);
          UserManager.this.mICartApplication.setCurrentOrder();
          UserManager.this.mICartRestClient.reset();
          MixpanelTrackerUtils.trackFBReferral(UserManager.this.mICartApplication, paramString);
          UserManager.this.mICartApplication.getICartNotification().raiseCurrentUserSetEvent(this, paramAnonymousUser);
        }
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousUser);
        }
      }
    });
  }
  
  public void updateAddress(Long paramLong1, String paramString1, String paramString2, String paramString3, String paramString4, Long paramLong2, Long paramLong3, Long paramLong4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, boolean paramBoolean, int paramInt, String paramString14, final ICartCallback<Address> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("firstname", paramString1);
    localJSONObject.put("lastname", paramString2);
    localJSONObject.put("address1", paramString3);
    localJSONObject.put("address2", paramString4);
    if (paramString5 != null) {
      localJSONObject.put("city", paramString5);
    }
    if (paramLong2 != null) {
      localJSONObject.put("country_id", paramLong2);
    }
    if (paramLong3 != null) {
      localJSONObject.put("state_id", paramLong3);
    }
    localJSONObject.put("sub_district_id", paramLong4);
    localJSONObject.put("zipcode", paramString6);
    localJSONObject.put("phone", paramString7);
    localJSONObject.put("primary", paramBoolean);
    localJSONObject.put("address_type", paramInt);
    if (paramString14 != null) {
      localJSONObject.put("delivery_instruction", paramString14);
    }
    localJSONObject.put("number", paramString8);
    localJSONObject.put("floor", paramString9);
    localJSONObject.put("unit", paramString10);
    localJSONObject.put("lane", paramString11);
    localJSONObject.put("alley", paramString12);
    localJSONObject.put("company_tax_id", paramString13);
    paramString1 = new TypedByteArray("application/json", localJSONObject.toString().getBytes("UTF-8"));
    this.mICartRestClient.getUserService().updateAddress(paramLong1, paramString1, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Address paramAnonymousAddress, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousAddress);
        }
      }
    });
  }
  
  public void validateReferrals(String paramString, final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getUserService().validateReferrals(paramString, new ICartRetrofitCallback(this.mICartApplication)
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
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
  
  public void verifyNumber(String paramString, final ICartCallback<Object> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("phone", paramString);
    paramString = new TypedByteArray("application/json", localJSONObject.toString().getBytes("UTF-8"));
    this.mICartRestClient.getUserService().verifyNumber(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        RestError localRestError;
        String str;
        if (paramAnonymousRetrofitError != null)
        {
          localRestError = RestError.getRestError(paramAnonymousRetrofitError);
          if (localRestError != null)
          {
            str = UserManager.this.mICartApplication.getString(2131165563);
            if (TextUtils.isEmpty(localRestError.message)) {
              break label77;
            }
            str = localRestError.message;
          }
        }
        for (;;)
        {
          Toast.makeText(UserManager.this.mICartApplication, str, 1).show();
          if (paramICartCallback != null) {
            paramICartCallback.onFailure(paramAnonymousRetrofitError);
          }
          return;
          label77:
          if (!TextUtils.isEmpty(localRestError.exception)) {
            str = localRestError.exception;
          }
        }
      }
      
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/managers/UserManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
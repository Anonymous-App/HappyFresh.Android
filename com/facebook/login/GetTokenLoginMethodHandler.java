package com.facebook.login;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.PlatformServiceClient.CompletedListener;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.GraphMeRequestWithCacheCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class GetTokenLoginMethodHandler
  extends LoginMethodHandler
{
  public static final Parcelable.Creator<GetTokenLoginMethodHandler> CREATOR = new Parcelable.Creator()
  {
    public GetTokenLoginMethodHandler createFromParcel(Parcel paramAnonymousParcel)
    {
      return new GetTokenLoginMethodHandler(paramAnonymousParcel);
    }
    
    public GetTokenLoginMethodHandler[] newArray(int paramAnonymousInt)
    {
      return new GetTokenLoginMethodHandler[paramAnonymousInt];
    }
  };
  private GetTokenClient getTokenClient;
  
  GetTokenLoginMethodHandler(Parcel paramParcel)
  {
    super(paramParcel);
  }
  
  GetTokenLoginMethodHandler(LoginClient paramLoginClient)
  {
    super(paramLoginClient);
  }
  
  void cancel()
  {
    if (this.getTokenClient != null)
    {
      this.getTokenClient.cancel();
      this.getTokenClient = null;
    }
  }
  
  void complete(final LoginClient.Request paramRequest, final Bundle paramBundle)
  {
    String str = paramBundle.getString("com.facebook.platform.extra.USER_ID");
    if ((str == null) || (str.isEmpty()))
    {
      this.loginClient.notifyBackgroundProcessingStart();
      Utility.getGraphMeRequestWithCacheAsync(paramBundle.getString("com.facebook.platform.extra.ACCESS_TOKEN"), new Utility.GraphMeRequestWithCacheCallback()
      {
        public void onFailure(FacebookException paramAnonymousFacebookException)
        {
          GetTokenLoginMethodHandler.this.loginClient.complete(LoginClient.Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", paramAnonymousFacebookException.getMessage()));
        }
        
        public void onSuccess(JSONObject paramAnonymousJSONObject)
        {
          try
          {
            paramAnonymousJSONObject = paramAnonymousJSONObject.getString("id");
            paramBundle.putString("com.facebook.platform.extra.USER_ID", paramAnonymousJSONObject);
            GetTokenLoginMethodHandler.this.onComplete(paramRequest, paramBundle);
            return;
          }
          catch (JSONException paramAnonymousJSONObject)
          {
            GetTokenLoginMethodHandler.this.loginClient.complete(LoginClient.Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", paramAnonymousJSONObject.getMessage()));
          }
        }
      });
      return;
    }
    onComplete(paramRequest, paramBundle);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  String getNameForLogging()
  {
    return "get_token";
  }
  
  void getTokenCompleted(LoginClient.Request paramRequest, Bundle paramBundle)
  {
    this.getTokenClient = null;
    this.loginClient.notifyBackgroundProcessingStop();
    if (paramBundle != null)
    {
      ArrayList localArrayList = paramBundle.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
      Object localObject = paramRequest.getPermissions();
      if ((localArrayList != null) && ((localObject == null) || (localArrayList.containsAll((Collection)localObject))))
      {
        complete(paramRequest, paramBundle);
        return;
      }
      paramBundle = new HashSet();
      localObject = ((Set)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        String str = (String)((Iterator)localObject).next();
        if (!localArrayList.contains(str)) {
          paramBundle.add(str);
        }
      }
      if (!paramBundle.isEmpty()) {
        addLoggingExtra("new_permissions", TextUtils.join(",", paramBundle));
      }
      paramRequest.setPermissions(paramBundle);
    }
    this.loginClient.tryNextHandler();
  }
  
  void onComplete(LoginClient.Request paramRequest, Bundle paramBundle)
  {
    paramRequest = createAccessTokenFromNativeLogin(paramBundle, AccessTokenSource.FACEBOOK_APPLICATION_SERVICE, paramRequest.getApplicationId());
    paramRequest = LoginClient.Result.createTokenResult(this.loginClient.getPendingRequest(), paramRequest);
    this.loginClient.completeAndValidate(paramRequest);
  }
  
  boolean tryAuthorize(final LoginClient.Request paramRequest)
  {
    this.getTokenClient = new GetTokenClient(this.loginClient.getActivity(), paramRequest.getApplicationId());
    if (!this.getTokenClient.start()) {
      return false;
    }
    this.loginClient.notifyBackgroundProcessingStart();
    paramRequest = new PlatformServiceClient.CompletedListener()
    {
      public void completed(Bundle paramAnonymousBundle)
      {
        GetTokenLoginMethodHandler.this.getTokenCompleted(paramRequest, paramAnonymousBundle);
      }
    };
    this.getTokenClient.setCompletedListener(paramRequest);
    return true;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/login/GetTokenLoginMethodHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
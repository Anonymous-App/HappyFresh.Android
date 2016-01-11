package com.facebook.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookServiceException;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;
import com.facebook.internal.WebDialog.Builder;
import com.facebook.internal.WebDialog.OnCompleteListener;
import java.util.Locale;

class WebViewLoginMethodHandler
  extends LoginMethodHandler
{
  public static final Parcelable.Creator<WebViewLoginMethodHandler> CREATOR = new Parcelable.Creator()
  {
    public WebViewLoginMethodHandler createFromParcel(Parcel paramAnonymousParcel)
    {
      return new WebViewLoginMethodHandler(paramAnonymousParcel);
    }
    
    public WebViewLoginMethodHandler[] newArray(int paramAnonymousInt)
    {
      return new WebViewLoginMethodHandler[paramAnonymousInt];
    }
  };
  private static final String WEB_VIEW_AUTH_HANDLER_STORE = "com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY";
  private static final String WEB_VIEW_AUTH_HANDLER_TOKEN_KEY = "TOKEN";
  private String e2e;
  private WebDialog loginDialog;
  
  WebViewLoginMethodHandler(Parcel paramParcel)
  {
    super(paramParcel);
    this.e2e = paramParcel.readString();
  }
  
  WebViewLoginMethodHandler(LoginClient paramLoginClient)
  {
    super(paramLoginClient);
  }
  
  private String loadCookieToken()
  {
    return this.loginClient.getActivity().getSharedPreferences("com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY", 0).getString("TOKEN", "");
  }
  
  private void saveCookieToken(String paramString)
  {
    this.loginClient.getActivity().getSharedPreferences("com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY", 0).edit().putString("TOKEN", paramString).apply();
  }
  
  void cancel()
  {
    if (this.loginDialog != null)
    {
      this.loginDialog.cancel();
      this.loginDialog = null;
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  String getNameForLogging()
  {
    return "web_view";
  }
  
  boolean needsInternetPermission()
  {
    return true;
  }
  
  void onWebDialogComplete(LoginClient.Request paramRequest, Bundle paramBundle, FacebookException paramFacebookException)
  {
    if (paramBundle != null) {
      if (paramBundle.containsKey("e2e")) {
        this.e2e = paramBundle.getString("e2e");
      }
    }
    for (;;)
    {
      try
      {
        paramBundle = createAccessTokenFromWebBundle(paramRequest.getPermissions(), paramBundle, AccessTokenSource.WEB_VIEW, paramRequest.getApplicationId());
        paramRequest = LoginClient.Result.createTokenResult(this.loginClient.getPendingRequest(), paramBundle);
        CookieSyncManager.createInstance(this.loginClient.getActivity()).sync();
        saveCookieToken(paramBundle.getToken());
        if (!Utility.isNullOrEmpty(this.e2e)) {
          logWebLoginCompleted(this.e2e);
        }
        this.loginClient.completeAndValidate(paramRequest);
        return;
      }
      catch (FacebookException paramRequest)
      {
        paramRequest = LoginClient.Result.createErrorResult(this.loginClient.getPendingRequest(), null, paramRequest.getMessage());
        continue;
      }
      if ((paramFacebookException instanceof FacebookOperationCanceledException))
      {
        paramRequest = LoginClient.Result.createCancelResult(this.loginClient.getPendingRequest(), "User canceled log in.");
      }
      else
      {
        this.e2e = null;
        paramRequest = null;
        paramBundle = paramFacebookException.getMessage();
        if ((paramFacebookException instanceof FacebookServiceException))
        {
          paramBundle = ((FacebookServiceException)paramFacebookException).getRequestError();
          paramRequest = String.format(Locale.ROOT, "%d", new Object[] { Integer.valueOf(paramBundle.getErrorCode()) });
          paramBundle = paramBundle.toString();
        }
        paramRequest = LoginClient.Result.createErrorResult(this.loginClient.getPendingRequest(), null, paramBundle, paramRequest);
      }
    }
  }
  
  boolean tryAuthorize(final LoginClient.Request paramRequest)
  {
    Bundle localBundle = new Bundle();
    if (!Utility.isNullOrEmpty(paramRequest.getPermissions()))
    {
      localObject = TextUtils.join(",", paramRequest.getPermissions());
      localBundle.putString("scope", (String)localObject);
      addLoggingExtra("scope", localObject);
    }
    localBundle.putString("default_audience", paramRequest.getDefaultAudience().getNativeProtocolAudience());
    Object localObject = AccessToken.getCurrentAccessToken();
    if (localObject != null)
    {
      localObject = ((AccessToken)localObject).getToken();
      if ((localObject == null) || (!((String)localObject).equals(loadCookieToken()))) {
        break label215;
      }
      localBundle.putString("access_token", (String)localObject);
      addLoggingExtra("access_token", "1");
    }
    for (;;)
    {
      WebDialog.OnCompleteListener local1 = new WebDialog.OnCompleteListener()
      {
        public void onComplete(Bundle paramAnonymousBundle, FacebookException paramAnonymousFacebookException)
        {
          WebViewLoginMethodHandler.this.onWebDialogComplete(paramRequest, paramAnonymousBundle, paramAnonymousFacebookException);
        }
      };
      this.e2e = LoginClient.getE2E();
      addLoggingExtra("e2e", this.e2e);
      localObject = this.loginClient.getActivity();
      this.loginDialog = new AuthDialogBuilder((Context)localObject, paramRequest.getApplicationId(), localBundle).setE2E(this.e2e).setIsRerequest(paramRequest.isRerequest()).setOnCompleteListener(local1).build();
      paramRequest = new FacebookDialogFragment();
      paramRequest.setRetainInstance(true);
      paramRequest.setDialog(this.loginDialog);
      paramRequest.show(((FragmentActivity)localObject).getSupportFragmentManager(), "FacebookDialogFragment");
      return true;
      localObject = null;
      break;
      label215:
      Utility.clearFacebookCookies(this.loginClient.getActivity());
      addLoggingExtra("access_token", "0");
    }
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeString(this.e2e);
  }
  
  static class AuthDialogBuilder
    extends WebDialog.Builder
  {
    private static final String OAUTH_DIALOG = "oauth";
    static final String REDIRECT_URI = "fbconnect://success";
    private String e2e;
    private boolean isRerequest;
    
    public AuthDialogBuilder(Context paramContext, String paramString, Bundle paramBundle)
    {
      super(paramString, "oauth", paramBundle);
    }
    
    public WebDialog build()
    {
      Bundle localBundle = getParameters();
      localBundle.putString("redirect_uri", "fbconnect://success");
      localBundle.putString("client_id", getApplicationId());
      localBundle.putString("e2e", this.e2e);
      localBundle.putString("response_type", "token,signed_request");
      localBundle.putString("return_scopes", "true");
      if (this.isRerequest) {
        localBundle.putString("auth_type", "rerequest");
      }
      return new WebDialog(getContext(), "oauth", localBundle, getTheme(), getListener());
    }
    
    public AuthDialogBuilder setE2E(String paramString)
    {
      this.e2e = paramString;
      return this;
    }
    
    public AuthDialogBuilder setIsRerequest(boolean paramBoolean)
    {
      this.isRerequest = paramBoolean;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/login/WebViewLoginMethodHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
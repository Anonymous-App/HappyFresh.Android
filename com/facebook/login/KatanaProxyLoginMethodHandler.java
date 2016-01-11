package com.facebook.login;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import java.util.Collection;

class KatanaProxyLoginMethodHandler
  extends LoginMethodHandler
{
  public static final Parcelable.Creator<KatanaProxyLoginMethodHandler> CREATOR = new Parcelable.Creator()
  {
    public KatanaProxyLoginMethodHandler createFromParcel(Parcel paramAnonymousParcel)
    {
      return new KatanaProxyLoginMethodHandler(paramAnonymousParcel);
    }
    
    public KatanaProxyLoginMethodHandler[] newArray(int paramAnonymousInt)
    {
      return new KatanaProxyLoginMethodHandler[paramAnonymousInt];
    }
  };
  
  KatanaProxyLoginMethodHandler(Parcel paramParcel)
  {
    super(paramParcel);
  }
  
  KatanaProxyLoginMethodHandler(LoginClient paramLoginClient)
  {
    super(paramLoginClient);
  }
  
  private LoginClient.Result handleResultOk(LoginClient.Request paramRequest, Intent paramIntent)
  {
    LoginClient.Result localResult = null;
    Bundle localBundle = paramIntent.getExtras();
    Object localObject = localBundle.getString("error");
    paramIntent = (Intent)localObject;
    if (localObject == null) {
      paramIntent = localBundle.getString("error_type");
    }
    String str2 = localBundle.getString("error_code");
    String str1 = localBundle.getString("error_message");
    localObject = str1;
    if (str1 == null) {
      localObject = localBundle.getString("error_description");
    }
    str1 = localBundle.getString("e2e");
    if (!Utility.isNullOrEmpty(str1)) {
      logWebLoginCompleted(str1);
    }
    if ((paramIntent == null) && (str2 == null) && (localObject == null)) {}
    while (ServerProtocol.errorsProxyAuthDisabled.contains(paramIntent)) {
      try
      {
        localResult = LoginClient.Result.createTokenResult(paramRequest, createAccessTokenFromWebBundle(paramRequest.getPermissions(), localBundle, AccessTokenSource.FACEBOOK_APPLICATION_WEB, paramRequest.getApplicationId()));
        return localResult;
      }
      catch (FacebookException paramIntent)
      {
        return LoginClient.Result.createErrorResult(paramRequest, null, paramIntent.getMessage());
      }
    }
    if (ServerProtocol.errorsUserCanceled.contains(paramIntent)) {
      return LoginClient.Result.createCancelResult(paramRequest, null);
    }
    return LoginClient.Result.createErrorResult(paramRequest, paramIntent, (String)localObject, str2);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  String getNameForLogging()
  {
    return "katana_proxy_auth";
  }
  
  boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    LoginClient.Request localRequest = this.loginClient.getPendingRequest();
    if (paramIntent == null)
    {
      paramIntent = LoginClient.Result.createCancelResult(localRequest, "Operation canceled");
      if (paramIntent == null) {
        break label82;
      }
      this.loginClient.completeAndValidate(paramIntent);
    }
    for (;;)
    {
      return true;
      if (paramInt2 == 0)
      {
        paramIntent = LoginClient.Result.createCancelResult(localRequest, paramIntent.getStringExtra("error"));
        break;
      }
      if (paramInt2 != -1)
      {
        paramIntent = LoginClient.Result.createErrorResult(localRequest, "Unexpected resultCode from authorization.", null);
        break;
      }
      paramIntent = handleResultOk(localRequest, paramIntent);
      break;
      label82:
      this.loginClient.tryNextHandler();
    }
  }
  
  boolean tryAuthorize(LoginClient.Request paramRequest)
  {
    String str = LoginClient.getE2E();
    paramRequest = NativeProtocol.createProxyAuthIntent(this.loginClient.getActivity(), paramRequest.getApplicationId(), paramRequest.getPermissions(), str, paramRequest.isRerequest(), paramRequest.hasPublishPermission(), paramRequest.getDefaultAudience());
    addLoggingExtra("e2e", str);
    return tryIntent(paramRequest, LoginClient.getLoginRequestCode());
  }
  
  protected boolean tryIntent(Intent paramIntent, int paramInt)
  {
    if (paramIntent == null) {
      return false;
    }
    try
    {
      this.loginClient.getFragment().startActivityForResult(paramIntent, paramInt);
      return true;
    }
    catch (ActivityNotFoundException paramIntent) {}
    return false;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/login/KatanaProxyLoginMethodHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
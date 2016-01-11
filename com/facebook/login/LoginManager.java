package com.facebook.login;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.Validate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LoginManager
{
  private static final String MANAGE_PERMISSION_PREFIX = "manage";
  private static final Set<String> OTHER_PUBLISH_PERMISSIONS = ;
  private static final String PUBLISH_PERMISSION_PREFIX = "publish";
  private static volatile LoginManager instance;
  private Context context;
  private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
  private LoginBehavior loginBehavior = LoginBehavior.SSO_WITH_FALLBACK;
  private LoginLogger loginLogger;
  private HashMap<String, String> pendingLoggingExtras;
  private LoginClient.Request pendingLoginRequest;
  
  LoginManager()
  {
    Validate.sdkInitialized();
  }
  
  static LoginResult computeLoginResult(LoginClient.Request paramRequest, AccessToken paramAccessToken)
  {
    Set localSet = paramRequest.getPermissions();
    HashSet localHashSet = new HashSet(paramAccessToken.getPermissions());
    if (paramRequest.isRerequest()) {
      localHashSet.retainAll(localSet);
    }
    paramRequest = new HashSet(localSet);
    paramRequest.removeAll(localHashSet);
    return new LoginResult(paramAccessToken, localHashSet, paramRequest);
  }
  
  private LoginClient.Request createLoginRequest(Collection<String> paramCollection)
  {
    LoginBehavior localLoginBehavior = this.loginBehavior;
    if (paramCollection != null)
    {
      paramCollection = new HashSet(paramCollection);
      paramCollection = new LoginClient.Request(localLoginBehavior, Collections.unmodifiableSet(paramCollection), this.defaultAudience, FacebookSdk.getApplicationId(), UUID.randomUUID().toString());
      if (AccessToken.getCurrentAccessToken() == null) {
        break label70;
      }
    }
    label70:
    for (boolean bool = true;; bool = false)
    {
      paramCollection.setRerequest(bool);
      return paramCollection;
      paramCollection = new HashSet();
      break;
    }
  }
  
  private LoginClient.Request createLoginRequestFromResponse(GraphResponse paramGraphResponse)
  {
    Validate.notNull(paramGraphResponse, "response");
    paramGraphResponse = paramGraphResponse.getRequest().getAccessToken();
    if (paramGraphResponse != null) {}
    for (paramGraphResponse = paramGraphResponse.getPermissions();; paramGraphResponse = null) {
      return createLoginRequest(paramGraphResponse);
    }
  }
  
  private void finishLogin(AccessToken paramAccessToken, FacebookException paramFacebookException, boolean paramBoolean, FacebookCallback<LoginResult> paramFacebookCallback)
  {
    if (paramAccessToken != null)
    {
      AccessToken.setCurrentAccessToken(paramAccessToken);
      Profile.fetchProfileForCurrentAccessToken();
    }
    LoginResult localLoginResult;
    if (paramFacebookCallback != null)
    {
      if (paramAccessToken == null) {
        break label60;
      }
      localLoginResult = computeLoginResult(this.pendingLoginRequest, paramAccessToken);
      if ((!paramBoolean) && ((localLoginResult == null) || (localLoginResult.getRecentlyGrantedPermissions().size() != 0))) {
        break label66;
      }
      paramFacebookCallback.onCancel();
    }
    label60:
    label66:
    do
    {
      return;
      localLoginResult = null;
      break;
      if (paramFacebookException != null)
      {
        paramFacebookCallback.onError(paramFacebookException);
        return;
      }
    } while (paramAccessToken == null);
    paramFacebookCallback.onSuccess(localLoginResult);
  }
  
  private Intent getFacebookActivityIntent(LoginClient.Request paramRequest)
  {
    Intent localIntent = new Intent();
    localIntent.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
    localIntent.setAction(paramRequest.getLoginBehavior().toString());
    localIntent.putExtras(LoginFragment.populateIntentExtras(paramRequest));
    return localIntent;
  }
  
  public static LoginManager getInstance()
  {
    if (instance == null) {}
    try
    {
      if (instance == null) {
        instance = new LoginManager();
      }
      return instance;
    }
    finally {}
  }
  
  private LoginLogger getLogger()
  {
    if ((this.loginLogger == null) || (!this.loginLogger.getApplicationId().equals(this.pendingLoginRequest.getApplicationId()))) {
      this.loginLogger = new LoginLogger(this.context, this.pendingLoginRequest.getApplicationId());
    }
    return this.loginLogger;
  }
  
  private static Set<String> getOtherPublishPermissions()
  {
    Collections.unmodifiableSet(new HashSet() {});
  }
  
  static boolean isPublishPermission(String paramString)
  {
    return (paramString != null) && ((paramString.startsWith("publish")) || (paramString.startsWith("manage")) || (OTHER_PUBLISH_PERMISSIONS.contains(paramString)));
  }
  
  private void logCompleteLogin(LoginClient.Result.Code paramCode, Map<String, String> paramMap, Exception paramException)
  {
    if (this.pendingLoginRequest == null)
    {
      getLogger().logUnexpectedError("fb_mobile_login_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.");
      return;
    }
    getLogger().logCompleteLogin(this.pendingLoginRequest.getAuthId(), this.pendingLoggingExtras, paramCode, paramMap, paramException);
  }
  
  private void logStartLogin()
  {
    getLogger().logStartLogin(this.pendingLoginRequest);
  }
  
  private boolean resolveIntent(Intent paramIntent)
  {
    return FacebookSdk.getApplicationContext().getPackageManager().resolveActivity(paramIntent, 0) != null;
  }
  
  private void startLogin(StartActivityDelegate paramStartActivityDelegate, LoginClient.Request paramRequest)
    throws FacebookException
  {
    this.pendingLoginRequest = paramRequest;
    this.pendingLoggingExtras = new HashMap();
    this.context = paramStartActivityDelegate.getActivityContext();
    logStartLogin();
    CallbackManagerImpl.registerStaticCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback()
    {
      public boolean onActivityResult(int paramAnonymousInt, Intent paramAnonymousIntent)
      {
        return LoginManager.this.onActivityResult(paramAnonymousInt, paramAnonymousIntent);
      }
    });
    boolean bool = tryFacebookActivity(paramStartActivityDelegate, paramRequest);
    paramRequest = this.pendingLoggingExtras;
    if (bool) {}
    for (paramStartActivityDelegate = "1";; paramStartActivityDelegate = "0")
    {
      paramRequest.put("try_login_activity", paramStartActivityDelegate);
      if (bool) {
        break;
      }
      paramStartActivityDelegate = new FacebookException("Log in attempt failed: FacebookActivity could not be started. Please make sure you added FacebookActivity to the AndroidManifest.");
      logCompleteLogin(LoginClient.Result.Code.ERROR, null, paramStartActivityDelegate);
      this.pendingLoginRequest = null;
      throw paramStartActivityDelegate;
    }
  }
  
  private boolean tryFacebookActivity(StartActivityDelegate paramStartActivityDelegate, LoginClient.Request paramRequest)
  {
    paramRequest = getFacebookActivityIntent(paramRequest);
    if (!resolveIntent(paramRequest)) {
      return false;
    }
    try
    {
      paramStartActivityDelegate.startActivityForResult(paramRequest, LoginClient.getLoginRequestCode());
      return true;
    }
    catch (ActivityNotFoundException paramStartActivityDelegate) {}
    return false;
  }
  
  private void validatePublishPermissions(Collection<String> paramCollection)
  {
    if (paramCollection == null) {}
    String str;
    do
    {
      return;
      while (!paramCollection.hasNext()) {
        paramCollection = paramCollection.iterator();
      }
      str = (String)paramCollection.next();
    } while (isPublishPermission(str));
    throw new FacebookException(String.format("Cannot pass a read permission (%s) to a request for publish authorization", new Object[] { str }));
  }
  
  private void validateReadPermissions(Collection<String> paramCollection)
  {
    if (paramCollection == null) {}
    String str;
    do
    {
      return;
      while (!paramCollection.hasNext()) {
        paramCollection = paramCollection.iterator();
      }
      str = (String)paramCollection.next();
    } while (!isPublishPermission(str));
    throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", new Object[] { str }));
  }
  
  public DefaultAudience getDefaultAudience()
  {
    return this.defaultAudience;
  }
  
  public LoginBehavior getLoginBehavior()
  {
    return this.loginBehavior;
  }
  
  LoginClient.Request getPendingLoginRequest()
  {
    return this.pendingLoginRequest;
  }
  
  public void logInWithPublishPermissions(Activity paramActivity, Collection<String> paramCollection)
  {
    validatePublishPermissions(paramCollection);
    paramCollection = createLoginRequest(paramCollection);
    startLogin(new ActivityStartActivityDelegate(paramActivity), paramCollection);
  }
  
  public void logInWithPublishPermissions(Fragment paramFragment, Collection<String> paramCollection)
  {
    validatePublishPermissions(paramCollection);
    paramCollection = createLoginRequest(paramCollection);
    startLogin(new FragmentStartActivityDelegate(paramFragment), paramCollection);
  }
  
  public void logInWithReadPermissions(Activity paramActivity, Collection<String> paramCollection)
  {
    validateReadPermissions(paramCollection);
    paramCollection = createLoginRequest(paramCollection);
    startLogin(new ActivityStartActivityDelegate(paramActivity), paramCollection);
  }
  
  public void logInWithReadPermissions(Fragment paramFragment, Collection<String> paramCollection)
  {
    validateReadPermissions(paramCollection);
    paramCollection = createLoginRequest(paramCollection);
    startLogin(new FragmentStartActivityDelegate(paramFragment), paramCollection);
  }
  
  public void logOut()
  {
    AccessToken.setCurrentAccessToken(null);
    Profile.setCurrentProfile(null);
  }
  
  boolean onActivityResult(int paramInt, Intent paramIntent)
  {
    return onActivityResult(paramInt, paramIntent, null);
  }
  
  boolean onActivityResult(int paramInt, Intent paramIntent, FacebookCallback<LoginResult> paramFacebookCallback)
  {
    if (this.pendingLoginRequest == null) {
      return false;
    }
    Object localObject6 = null;
    Object localObject4 = null;
    Object localObject7 = null;
    Object localObject5 = null;
    Object localObject1 = LoginClient.Result.Code.ERROR;
    LoginClient.Result.Code localCode = null;
    boolean bool1 = false;
    boolean bool2 = false;
    LoginClient.Result localResult;
    Object localObject3;
    Object localObject2;
    if (paramIntent != null)
    {
      localResult = (LoginClient.Result)paramIntent.getParcelableExtra("com.facebook.LoginFragment:Result");
      paramIntent = (Intent)localObject1;
      localObject1 = localObject6;
      localObject3 = localCode;
      localObject2 = localObject7;
      if (localResult != null)
      {
        localCode = localResult.code;
        if (paramInt != -1) {
          break label206;
        }
        if (localResult.code != LoginClient.Result.Code.SUCCESS) {
          break label182;
        }
        paramIntent = localResult.token;
        bool1 = bool2;
        localObject1 = localObject4;
        localObject3 = localResult.loggingExtras;
        localObject2 = paramIntent;
        paramIntent = localCode;
      }
    }
    for (;;)
    {
      localObject4 = localObject1;
      if (localObject1 == null)
      {
        localObject4 = localObject1;
        if (localObject2 == null)
        {
          localObject4 = localObject1;
          if (!bool1) {
            localObject4 = new FacebookException("Unexpected call to LoginManager.onActivityResult");
          }
        }
      }
      logCompleteLogin(paramIntent, (Map)localObject3, (Exception)localObject4);
      finishLogin((AccessToken)localObject2, (FacebookException)localObject4, bool1, paramFacebookCallback);
      return true;
      label182:
      localObject1 = new FacebookAuthorizationException(localResult.errorMessage);
      bool1 = bool2;
      paramIntent = (Intent)localObject5;
      break;
      label206:
      localObject1 = localObject4;
      bool1 = bool2;
      paramIntent = (Intent)localObject5;
      if (paramInt != 0) {
        break;
      }
      bool1 = true;
      localObject1 = localObject4;
      paramIntent = (Intent)localObject5;
      break;
      paramIntent = (Intent)localObject1;
      localObject1 = localObject6;
      localObject3 = localCode;
      localObject2 = localObject7;
      if (paramInt == 0)
      {
        bool1 = true;
        paramIntent = LoginClient.Result.Code.CANCEL;
        localObject1 = localObject6;
        localObject3 = localCode;
        localObject2 = localObject7;
      }
    }
  }
  
  public void registerCallback(CallbackManager paramCallbackManager, final FacebookCallback<LoginResult> paramFacebookCallback)
  {
    if (!(paramCallbackManager instanceof CallbackManagerImpl)) {
      throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
    }
    ((CallbackManagerImpl)paramCallbackManager).registerCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback()
    {
      public boolean onActivityResult(int paramAnonymousInt, Intent paramAnonymousIntent)
      {
        return LoginManager.this.onActivityResult(paramAnonymousInt, paramAnonymousIntent, paramFacebookCallback);
      }
    });
  }
  
  public void resolveError(Activity paramActivity, GraphResponse paramGraphResponse)
  {
    startLogin(new ActivityStartActivityDelegate(paramActivity), createLoginRequestFromResponse(paramGraphResponse));
  }
  
  public void resolveError(Fragment paramFragment, GraphResponse paramGraphResponse)
  {
    startLogin(new FragmentStartActivityDelegate(paramFragment), createLoginRequestFromResponse(paramGraphResponse));
  }
  
  public LoginManager setDefaultAudience(DefaultAudience paramDefaultAudience)
  {
    this.defaultAudience = paramDefaultAudience;
    return this;
  }
  
  public LoginManager setLoginBehavior(LoginBehavior paramLoginBehavior)
  {
    this.loginBehavior = paramLoginBehavior;
    return this;
  }
  
  private static class ActivityStartActivityDelegate
    implements StartActivityDelegate
  {
    private final Activity activity;
    
    ActivityStartActivityDelegate(Activity paramActivity)
    {
      Validate.notNull(paramActivity, "activity");
      this.activity = paramActivity;
    }
    
    public Activity getActivityContext()
    {
      return this.activity;
    }
    
    public void startActivityForResult(Intent paramIntent, int paramInt)
    {
      this.activity.startActivityForResult(paramIntent, paramInt);
    }
  }
  
  private static class FragmentStartActivityDelegate
    implements StartActivityDelegate
  {
    private final Fragment fragment;
    
    FragmentStartActivityDelegate(Fragment paramFragment)
    {
      Validate.notNull(paramFragment, "fragment");
      this.fragment = paramFragment;
    }
    
    public Activity getActivityContext()
    {
      return this.fragment.getActivity();
    }
    
    public void startActivityForResult(Intent paramIntent, int paramInt)
    {
      this.fragment.startActivityForResult(paramIntent, paramInt);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/login/LoginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
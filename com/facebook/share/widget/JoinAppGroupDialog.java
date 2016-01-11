package com.facebook.share.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FacebookDialogBase.ModeHandler;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import java.util.ArrayList;
import java.util.List;

public class JoinAppGroupDialog
  extends FacebookDialogBase<String, Result>
{
  private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.AppGroupJoin.toRequestCode();
  private static final String JOIN_GAME_GROUP_DIALOG = "game_group_join";
  
  public JoinAppGroupDialog(Activity paramActivity)
  {
    super(paramActivity, DEFAULT_REQUEST_CODE);
  }
  
  public JoinAppGroupDialog(Fragment paramFragment)
  {
    super(paramFragment, DEFAULT_REQUEST_CODE);
  }
  
  public static boolean canShow()
  {
    return true;
  }
  
  public static void show(Activity paramActivity, String paramString)
  {
    new JoinAppGroupDialog(paramActivity).show(paramString);
  }
  
  public static void show(Fragment paramFragment, String paramString)
  {
    new JoinAppGroupDialog(paramFragment).show(paramString);
  }
  
  protected AppCall createBaseAppCall()
  {
    return new AppCall(getRequestCode());
  }
  
  protected List<FacebookDialogBase<String, Result>.ModeHandler> getOrderedModeHandlers()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new WebHandler(null));
    return localArrayList;
  }
  
  protected void registerCallbackImpl(CallbackManagerImpl paramCallbackManagerImpl, final FacebookCallback<Result> paramFacebookCallback)
  {
    if (paramFacebookCallback == null) {}
    for (paramFacebookCallback = null;; paramFacebookCallback = new ResultProcessor(paramFacebookCallback)
        {
          public void onSuccess(AppCall paramAnonymousAppCall, Bundle paramAnonymousBundle)
          {
            paramFacebookCallback.onSuccess(new JoinAppGroupDialog.Result(paramAnonymousBundle, null));
          }
        })
    {
      paramFacebookCallback = new CallbackManagerImpl.Callback()
      {
        public boolean onActivityResult(int paramAnonymousInt, Intent paramAnonymousIntent)
        {
          return ShareInternalUtility.handleActivityResult(JoinAppGroupDialog.this.getRequestCode(), paramAnonymousInt, paramAnonymousIntent, paramFacebookCallback);
        }
      };
      paramCallbackManagerImpl.registerCallback(getRequestCode(), paramFacebookCallback);
      return;
    }
  }
  
  public static final class Result
  {
    private final Bundle data;
    
    private Result(Bundle paramBundle)
    {
      this.data = paramBundle;
    }
    
    public Bundle getData()
    {
      return this.data;
    }
  }
  
  private class WebHandler
    extends FacebookDialogBase<String, JoinAppGroupDialog.Result>.ModeHandler
  {
    private WebHandler()
    {
      super();
    }
    
    public boolean canShow(String paramString)
    {
      return true;
    }
    
    public AppCall createAppCall(String paramString)
    {
      AppCall localAppCall = JoinAppGroupDialog.this.createBaseAppCall();
      Bundle localBundle = new Bundle();
      localBundle.putString("id", paramString);
      DialogPresenter.setupAppCallForWebDialog(localAppCall, "game_group_join", localBundle);
      return localAppCall;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/widget/JoinAppGroupDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
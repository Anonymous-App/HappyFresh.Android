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
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.AppGroupCreationContent;
import java.util.ArrayList;
import java.util.List;

public class CreateAppGroupDialog
  extends FacebookDialogBase<AppGroupCreationContent, Result>
{
  private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.AppGroupCreate.toRequestCode();
  private static final String GAME_GROUP_CREATION_DIALOG = "game_group_create";
  
  public CreateAppGroupDialog(Activity paramActivity)
  {
    super(paramActivity, DEFAULT_REQUEST_CODE);
  }
  
  public CreateAppGroupDialog(Fragment paramFragment)
  {
    super(paramFragment, DEFAULT_REQUEST_CODE);
  }
  
  public static boolean canShow()
  {
    return true;
  }
  
  public static void show(Activity paramActivity, AppGroupCreationContent paramAppGroupCreationContent)
  {
    new CreateAppGroupDialog(paramActivity).show(paramAppGroupCreationContent);
  }
  
  public static void show(Fragment paramFragment, AppGroupCreationContent paramAppGroupCreationContent)
  {
    new CreateAppGroupDialog(paramFragment).show(paramAppGroupCreationContent);
  }
  
  protected AppCall createBaseAppCall()
  {
    return new AppCall(getRequestCode());
  }
  
  protected List<FacebookDialogBase<AppGroupCreationContent, Result>.ModeHandler> getOrderedModeHandlers()
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
            paramFacebookCallback.onSuccess(new CreateAppGroupDialog.Result(paramAnonymousBundle.getString("id"), null));
          }
        })
    {
      paramFacebookCallback = new CallbackManagerImpl.Callback()
      {
        public boolean onActivityResult(int paramAnonymousInt, Intent paramAnonymousIntent)
        {
          return ShareInternalUtility.handleActivityResult(CreateAppGroupDialog.this.getRequestCode(), paramAnonymousInt, paramAnonymousIntent, paramFacebookCallback);
        }
      };
      paramCallbackManagerImpl.registerCallback(getRequestCode(), paramFacebookCallback);
      return;
    }
  }
  
  public static final class Result
  {
    private final String id;
    
    private Result(String paramString)
    {
      this.id = paramString;
    }
    
    public String getId()
    {
      return this.id;
    }
  }
  
  private class WebHandler
    extends FacebookDialogBase<AppGroupCreationContent, CreateAppGroupDialog.Result>.ModeHandler
  {
    private WebHandler()
    {
      super();
    }
    
    public boolean canShow(AppGroupCreationContent paramAppGroupCreationContent)
    {
      return true;
    }
    
    public AppCall createAppCall(AppGroupCreationContent paramAppGroupCreationContent)
    {
      AppCall localAppCall = CreateAppGroupDialog.this.createBaseAppCall();
      DialogPresenter.setupAppCallForWebDialog(localAppCall, "game_group_create", WebDialogParameters.create(paramAppGroupCreationContent));
      return localAppCall;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/widget/CreateAppGroupDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
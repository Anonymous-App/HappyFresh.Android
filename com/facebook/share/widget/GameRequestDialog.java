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
import com.facebook.share.internal.GameRequestValidation;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.GameRequestContent;
import java.util.ArrayList;
import java.util.List;

public class GameRequestDialog
  extends FacebookDialogBase<GameRequestContent, Result>
{
  private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.GameRequest.toRequestCode();
  private static final String GAME_REQUEST_DIALOG = "apprequests";
  
  public GameRequestDialog(Activity paramActivity)
  {
    super(paramActivity, DEFAULT_REQUEST_CODE);
  }
  
  public GameRequestDialog(Fragment paramFragment)
  {
    super(paramFragment, DEFAULT_REQUEST_CODE);
  }
  
  public static boolean canShow()
  {
    return true;
  }
  
  public static void show(Activity paramActivity, GameRequestContent paramGameRequestContent)
  {
    new GameRequestDialog(paramActivity).show(paramGameRequestContent);
  }
  
  public static void show(Fragment paramFragment, GameRequestContent paramGameRequestContent)
  {
    new GameRequestDialog(paramFragment).show(paramGameRequestContent);
  }
  
  protected AppCall createBaseAppCall()
  {
    return new AppCall(getRequestCode());
  }
  
  protected List<FacebookDialogBase<GameRequestContent, Result>.ModeHandler> getOrderedModeHandlers()
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
            if (paramAnonymousBundle != null)
            {
              paramFacebookCallback.onSuccess(new GameRequestDialog.Result(paramAnonymousBundle, null));
              return;
            }
            onCancel(paramAnonymousAppCall);
          }
        })
    {
      paramCallbackManagerImpl.registerCallback(getRequestCode(), new CallbackManagerImpl.Callback()
      {
        public boolean onActivityResult(int paramAnonymousInt, Intent paramAnonymousIntent)
        {
          return ShareInternalUtility.handleActivityResult(GameRequestDialog.this.getRequestCode(), paramAnonymousInt, paramAnonymousIntent, paramFacebookCallback);
        }
      });
      return;
    }
  }
  
  public static final class Result
  {
    String requestId;
    List<String> to;
    
    private Result(Bundle paramBundle)
    {
      this.requestId = paramBundle.getString("request");
      this.to = new ArrayList();
      while (paramBundle.containsKey(String.format("to[%d]", new Object[] { Integer.valueOf(this.to.size()) }))) {
        this.to.add(paramBundle.getString(String.format("to[%d]", new Object[] { Integer.valueOf(this.to.size()) })));
      }
    }
    
    public String getRequestId()
    {
      return this.requestId;
    }
    
    public List<String> getRequestRecipients()
    {
      return this.to;
    }
  }
  
  private class WebHandler
    extends FacebookDialogBase<GameRequestContent, GameRequestDialog.Result>.ModeHandler
  {
    private WebHandler()
    {
      super();
    }
    
    public boolean canShow(GameRequestContent paramGameRequestContent)
    {
      return true;
    }
    
    public AppCall createAppCall(GameRequestContent paramGameRequestContent)
    {
      GameRequestValidation.validate(paramGameRequestContent);
      AppCall localAppCall = GameRequestDialog.this.createBaseAppCall();
      DialogPresenter.setupAppCallForWebDialog(localAppCall, "apprequests", WebDialogParameters.create(paramGameRequestContent));
      return localAppCall;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/widget/GameRequestDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
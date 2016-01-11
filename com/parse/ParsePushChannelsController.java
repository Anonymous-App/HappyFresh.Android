package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.Collections;
import java.util.List;

class ParsePushChannelsController
{
  private static final String TAG = "com.parse.ParsePushChannelsController";
  private static boolean loggedManifestError = false;
  
  private static void checkManifestAndLogErrorIfNecessary()
  {
    if ((!loggedManifestError) && (ManifestInfo.getPushType() == PushType.NONE))
    {
      loggedManifestError = true;
      PLog.e("com.parse.ParsePushChannelsController", "Tried to subscribe or unsubscribe from a channel, but push is not enabled correctly. " + ManifestInfo.getNonePushTypeLogMessage());
    }
  }
  
  private static ParseCurrentInstallationController getCurrentInstallationController()
  {
    return ParseCorePlugins.getInstance().getCurrentInstallationController();
  }
  
  public Task<Void> subscribeInBackground(final String paramString)
  {
    
    if (paramString == null) {
      throw new IllegalArgumentException("Can't subscribe to null channel.");
    }
    getCurrentInstallationController().getAsync().onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<ParseInstallation> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseInstallation)paramAnonymousTask.getResult();
        List localList = paramAnonymousTask.getList("channels");
        if ((localList == null) || (paramAnonymousTask.isDirty("channels")) || (!localList.contains(paramString)))
        {
          paramAnonymousTask.addUnique("channels", paramString);
          return paramAnonymousTask.saveInBackground();
        }
        return Task.forResult(null);
      }
    });
  }
  
  public Task<Void> unsubscribeInBackground(final String paramString)
  {
    
    if (paramString == null) {
      throw new IllegalArgumentException("Can't unsubscribe from null channel.");
    }
    getCurrentInstallationController().getAsync().onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<ParseInstallation> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseInstallation)paramAnonymousTask.getResult();
        List localList = paramAnonymousTask.getList("channels");
        if ((localList != null) && (localList.contains(paramString)))
        {
          paramAnonymousTask.removeAll("channels", Collections.singletonList(paramString));
          return paramAnonymousTask.saveInBackground();
        }
        return Task.forResult(null);
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParsePushChannelsController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
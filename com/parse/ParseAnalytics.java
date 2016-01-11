package com.parse;

import android.content.Intent;
import android.os.Bundle;
import bolts.Capture;
import bolts.Continuation;
import bolts.Task;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseAnalytics
{
  private static final String TAG = "com.parse.ParseAnalytics";
  private static final Map<String, Boolean> lruSeenPushes = new LinkedHashMap()
  {
    protected boolean removeEldestEntry(Map.Entry<String, Boolean> paramAnonymousEntry)
    {
      return size() > 10;
    }
  };
  
  static void clear()
  {
    synchronized (lruSeenPushes)
    {
      lruSeenPushes.clear();
      return;
    }
  }
  
  static ParseAnalyticsController getAnalyticsController()
  {
    return ParseCorePlugins.getInstance().getAnalyticsController();
  }
  
  static String getPushHashFromIntent(Intent paramIntent)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (paramIntent != null)
    {
      localObject1 = localObject2;
      if (paramIntent.getExtras() != null) {
        localObject1 = paramIntent.getExtras().getString("com.parse.Data");
      }
    }
    if (localObject1 == null) {
      return null;
    }
    try
    {
      paramIntent = new JSONObject((String)localObject1).optString("push_hash");
      return paramIntent;
    }
    catch (JSONException paramIntent)
    {
      PLog.e("com.parse.ParseAnalytics", "Failed to parse push data: " + paramIntent.getMessage());
    }
    return null;
  }
  
  @Deprecated
  public static void trackAppOpened(Intent paramIntent)
  {
    trackAppOpenedInBackground(paramIntent);
  }
  
  public static Task<Void> trackAppOpenedInBackground(Intent arg0)
  {
    Object localObject1 = getPushHashFromIntent(???);
    Capture localCapture = new Capture();
    if ((localObject1 != null) && (((String)localObject1).length() > 0)) {}
    synchronized (lruSeenPushes)
    {
      if (lruSeenPushes.containsKey(localObject1))
      {
        localObject1 = Task.forResult(null);
        return (Task<Void>)localObject1;
      }
      lruSeenPushes.put(localObject1, Boolean.valueOf(true));
      localCapture.set(localObject1);
      ParseUser.getCurrentSessionTokenAsync().onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<String> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = (String)paramAnonymousTask.getResult();
          return ParseAnalytics.getAnalyticsController().trackAppOpenedInBackground((String)this.val$pushHash.get(), paramAnonymousTask);
        }
      });
    }
  }
  
  public static void trackAppOpenedInBackground(Intent paramIntent, SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(trackAppOpenedInBackground(paramIntent), paramSaveCallback);
  }
  
  @Deprecated
  public static void trackEvent(String paramString)
  {
    trackEventInBackground(paramString);
  }
  
  @Deprecated
  public static void trackEvent(String paramString, Map<String, String> paramMap)
  {
    trackEventInBackground(paramString, paramMap);
  }
  
  public static Task<Void> trackEventInBackground(String paramString)
  {
    return trackEventInBackground(paramString, (Map)null);
  }
  
  public static Task<Void> trackEventInBackground(String paramString, final Map<String, String> paramMap)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      throw new IllegalArgumentException("A name for the custom event must be provided.");
    }
    if (paramMap != null) {}
    for (paramMap = (JSONObject)NoObjectsEncoder.get().encode(paramMap);; paramMap = null) {
      ParseUser.getCurrentSessionTokenAsync().onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<String> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = (String)paramAnonymousTask.getResult();
          return ParseAnalytics.getAnalyticsController().trackEventInBackground(this.val$name, paramMap, paramAnonymousTask);
        }
      });
    }
  }
  
  public static void trackEventInBackground(String paramString, SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(trackEventInBackground(paramString), paramSaveCallback);
  }
  
  public static void trackEventInBackground(String paramString, Map<String, String> paramMap, SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(trackEventInBackground(paramString, paramMap), paramSaveCallback);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseAnalytics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
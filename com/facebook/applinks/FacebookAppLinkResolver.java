package com.facebook.applinks;

import android.net.Uri;
import android.os.Bundle;
import bolts.AppLink;
import bolts.AppLink.Target;
import bolts.AppLinkResolver;
import bolts.Continuation;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookAppLinkResolver
  implements AppLinkResolver
{
  private static final String APP_LINK_ANDROID_TARGET_KEY = "android";
  private static final String APP_LINK_KEY = "app_links";
  private static final String APP_LINK_TARGET_APP_NAME_KEY = "app_name";
  private static final String APP_LINK_TARGET_CLASS_KEY = "class";
  private static final String APP_LINK_TARGET_PACKAGE_KEY = "package";
  private static final String APP_LINK_TARGET_SHOULD_FALLBACK_KEY = "should_fallback";
  private static final String APP_LINK_TARGET_URL_KEY = "url";
  private static final String APP_LINK_WEB_TARGET_KEY = "web";
  private final HashMap<Uri, AppLink> cachedAppLinks = new HashMap();
  
  private static AppLink.Target getAndroidTargetFromJson(JSONObject paramJSONObject)
  {
    String str1 = tryGetStringFromJson(paramJSONObject, "package", null);
    if (str1 == null) {
      return null;
    }
    String str2 = tryGetStringFromJson(paramJSONObject, "class", null);
    String str3 = tryGetStringFromJson(paramJSONObject, "app_name", null);
    String str4 = tryGetStringFromJson(paramJSONObject, "url", null);
    paramJSONObject = null;
    if (str4 != null) {
      paramJSONObject = Uri.parse(str4);
    }
    return new AppLink.Target(str1, str2, paramJSONObject, str3);
  }
  
  private static Uri getWebFallbackUriFromJson(Uri paramUri, JSONObject paramJSONObject)
  {
    Object localObject;
    try
    {
      paramJSONObject = paramJSONObject.getJSONObject("web");
      if (!tryGetBooleanFromJson(paramJSONObject, "should_fallback", true)) {
        return null;
      }
      localObject = tryGetStringFromJson(paramJSONObject, "url", null);
      paramJSONObject = null;
      if (localObject != null) {
        paramJSONObject = Uri.parse((String)localObject);
      }
      localObject = paramJSONObject;
      if (paramJSONObject == null) {
        return paramUri;
      }
    }
    catch (JSONException paramJSONObject)
    {
      localObject = paramUri;
    }
    return (Uri)localObject;
  }
  
  private static boolean tryGetBooleanFromJson(JSONObject paramJSONObject, String paramString, boolean paramBoolean)
  {
    try
    {
      boolean bool = paramJSONObject.getBoolean(paramString);
      return bool;
    }
    catch (JSONException paramJSONObject) {}
    return paramBoolean;
  }
  
  private static String tryGetStringFromJson(JSONObject paramJSONObject, String paramString1, String paramString2)
  {
    try
    {
      paramJSONObject = paramJSONObject.getString(paramString1);
      return paramJSONObject;
    }
    catch (JSONException paramJSONObject) {}
    return paramString2;
  }
  
  public Task<AppLink> getAppLinkFromUrlInBackground(final Uri paramUri)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramUri);
    getAppLinkFromUrlsInBackground(localArrayList).onSuccess(new Continuation()
    {
      public AppLink then(Task<Map<Uri, AppLink>> paramAnonymousTask)
        throws Exception
      {
        return (AppLink)((Map)paramAnonymousTask.getResult()).get(paramUri);
      }
    });
  }
  
  public Task<Map<Uri, AppLink>> getAppLinkFromUrlsInBackground(List<Uri> arg1)
  {
    HashMap localHashMap = new HashMap();
    final HashSet localHashSet = new HashSet();
    StringBuilder localStringBuilder = new StringBuilder();
    Object localObject2 = ???.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      Uri localUri = (Uri)((Iterator)localObject2).next();
      synchronized (this.cachedAppLinks)
      {
        AppLink localAppLink = (AppLink)this.cachedAppLinks.get(localUri);
        if (localAppLink != null) {
          localHashMap.put(localUri, localAppLink);
        }
      }
      if (!localHashSet.isEmpty()) {
        localStringBuilder.append(',');
      }
      localStringBuilder.append(localUri.toString());
      localHashSet.add(localUri);
    }
    if (localHashSet.isEmpty()) {
      return Task.forResult(localObject1);
    }
    ??? = Task.create();
    localObject2 = new Bundle();
    ((Bundle)localObject2).putString("ids", localStringBuilder.toString());
    ((Bundle)localObject2).putString("fields", String.format("%s.fields(%s,%s)", new Object[] { "app_links", "android", "web" }));
    new GraphRequest(null, "", (Bundle)localObject2, null, new GraphRequest.Callback()
    {
      public void onCompleted(GraphResponse paramAnonymousGraphResponse)
      {
        Object localObject1 = paramAnonymousGraphResponse.getError();
        if (localObject1 != null)
        {
          paramList.setError(((FacebookRequestError)localObject1).getException());
          return;
        }
        paramAnonymousGraphResponse = paramAnonymousGraphResponse.getJSONObject();
        if (paramAnonymousGraphResponse == null)
        {
          paramList.setResult(localObject1);
          return;
        }
        localObject1 = localHashSet.iterator();
        Uri localUri;
        do
        {
          if (!((Iterator)localObject1).hasNext()) {
            break;
          }
          localUri = (Uri)((Iterator)localObject1).next();
        } while (!paramAnonymousGraphResponse.has(localUri.toString()));
        for (;;)
        {
          int i;
          try
          {
            ??? = paramAnonymousGraphResponse.getJSONObject(localUri.toString()).getJSONObject("app_links");
            Object localObject4 = ((JSONObject)???).getJSONArray("android");
            int j = ((JSONArray)localObject4).length();
            ArrayList localArrayList = new ArrayList(j);
            i = 0;
            if (i < j)
            {
              AppLink.Target localTarget = FacebookAppLinkResolver.getAndroidTargetFromJson(((JSONArray)localObject4).getJSONObject(i));
              if (localTarget == null) {
                break label257;
              }
              localArrayList.add(localTarget);
              break label257;
            }
            localObject4 = new AppLink(localUri, localArrayList, FacebookAppLinkResolver.getWebFallbackUriFromJson(localUri, (JSONObject)???));
            localObject1.put(localUri, localObject4);
            synchronized (FacebookAppLinkResolver.this.cachedAppLinks)
            {
              FacebookAppLinkResolver.this.cachedAppLinks.put(localUri, localObject4);
            }
          }
          catch (JSONException localJSONException) {}
          paramList.setResult(localObject1);
          return;
          label257:
          i += 1;
        }
      }
    }).executeAsync();
    return ???.getTask();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/applinks/FacebookAppLinkResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
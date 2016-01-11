package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class NetworkQueryController
  extends AbstractQueryController
{
  private static final String TAG = "NetworkQueryController";
  private final ParseHttpClient restClient;
  
  public NetworkQueryController(ParseHttpClient paramParseHttpClient)
  {
    this.restClient = paramParseHttpClient;
  }
  
  <T extends ParseObject> List<T> convertFindResponse(ParseQuery.State<T> paramState, JSONObject paramJSONObject)
    throws JSONException
  {
    ArrayList localArrayList = new ArrayList();
    JSONArray localJSONArray = paramJSONObject.getJSONArray("results");
    if (localJSONArray == null)
    {
      PLog.d("NetworkQueryController", "null results in find response");
      return localArrayList;
    }
    Object localObject = paramJSONObject.optString("className", null);
    paramJSONObject = (JSONObject)localObject;
    if (localObject == null) {
      paramJSONObject = paramState.className();
    }
    int i = 0;
    label56:
    if (i < localJSONArray.length())
    {
      localObject = localJSONArray.getJSONObject(i);
      if (paramState.selectedKeys() != null) {
        break label137;
      }
    }
    label137:
    for (boolean bool = true;; bool = false)
    {
      localObject = ParseObject.fromJSON((JSONObject)localObject, paramJSONObject, bool);
      localArrayList.add(localObject);
      ParseQuery.RelationConstraint localRelationConstraint = (ParseQuery.RelationConstraint)paramState.constraints().get("$relatedTo");
      if (localRelationConstraint != null) {
        localRelationConstraint.getRelation().addKnownObject((ParseObject)localObject);
      }
      i += 1;
      break label56;
      break;
    }
  }
  
  public <T extends ParseObject> Task<Integer> countAsync(ParseQuery.State<T> paramState, ParseUser paramParseUser, Task<Void> paramTask)
  {
    if (paramParseUser != null) {}
    for (paramParseUser = paramParseUser.getSessionToken();; paramParseUser = null) {
      return countAsync(paramState, paramParseUser, true, paramTask);
    }
  }
  
  <T extends ParseObject> Task<Integer> countAsync(final ParseQuery.State<T> paramState, final String paramString, boolean paramBoolean, Task<Void> paramTask)
  {
    paramString = ParseRESTQueryCommand.countCommand(paramState, paramString);
    if (paramBoolean) {
      paramString.enableRetrying();
    }
    paramString.executeAsync(this.restClient, paramTask).onSuccessTask(new Continuation()
    {
      public Task<JSONObject> then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        Object localObject = paramState.cachePolicy();
        if ((localObject != null) && (localObject != ParseQuery.CachePolicy.IGNORE_CACHE))
        {
          localObject = (JSONObject)paramAnonymousTask.getResult();
          ParseKeyValueCache.saveToKeyValueCache(paramString.getCacheKey(), ((JSONObject)localObject).toString());
        }
        return paramAnonymousTask;
      }
    }, Task.BACKGROUND_EXECUTOR).onSuccess(new Continuation()
    {
      public Integer then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        return Integer.valueOf(((JSONObject)paramAnonymousTask.getResult()).optInt("count"));
      }
    });
  }
  
  public <T extends ParseObject> Task<List<T>> findAsync(ParseQuery.State<T> paramState, ParseUser paramParseUser, Task<Void> paramTask)
  {
    if (paramParseUser != null) {}
    for (paramParseUser = paramParseUser.getSessionToken();; paramParseUser = null) {
      return findAsync(paramState, paramParseUser, true, paramTask);
    }
  }
  
  <T extends ParseObject> Task<List<T>> findAsync(final ParseQuery.State<T> paramState, final String paramString, boolean paramBoolean, Task<Void> paramTask)
  {
    long l1 = System.nanoTime();
    paramString = ParseRESTQueryCommand.findCommand(paramState, paramString);
    if (paramBoolean) {
      paramString.enableRetrying();
    }
    final long l2 = System.nanoTime();
    paramString.executeAsync(this.restClient, paramTask).onSuccess(new Continuation()
    {
      public List<T> then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        Object localObject = (JSONObject)paramAnonymousTask.getResult();
        ParseQuery.CachePolicy localCachePolicy = paramState.cachePolicy();
        if ((localCachePolicy != null) && (localCachePolicy != ParseQuery.CachePolicy.IGNORE_CACHE)) {
          ParseKeyValueCache.saveToKeyValueCache(paramString.getCacheKey(), ((JSONObject)localObject).toString());
        }
        long l1 = System.nanoTime();
        paramAnonymousTask = NetworkQueryController.this.convertFindResponse(paramState, (JSONObject)paramAnonymousTask.getResult());
        long l2 = System.nanoTime();
        if (((JSONObject)localObject).has("trace"))
        {
          localObject = ((JSONObject)localObject).get("trace");
          PLog.d("ParseQuery", String.format("Query pre-processing took %f seconds\n%s\nClient side parsing took %f seconds\n", new Object[] { Float.valueOf((float)(l2 - this.val$queryStart) / 1000000.0F), localObject, Float.valueOf((float)(l2 - l1) / 1000000.0F) }));
        }
        return paramAnonymousTask;
      }
    }, Task.BACKGROUND_EXECUTOR);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/NetworkQueryController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.parse;

import bolts.Continuation;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import com.parse.http.ParseHttpRequest.Method;
import com.parse.http.ParseHttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ParseRESTObjectBatchCommand
  extends ParseRESTCommand
{
  public static final int COMMAND_OBJECT_BATCH_MAX_SIZE = 50;
  private static final String KEY_RESULTS = "results";
  
  private ParseRESTObjectBatchCommand(String paramString1, ParseHttpRequest.Method paramMethod, Map<String, ?> paramMap, String paramString2)
  {
    super(paramString1, paramMethod, paramMap, paramString2);
  }
  
  public static List<Task<JSONObject>> executeBatch(ParseHttpClient paramParseHttpClient, List<ParseRESTObjectCommand> paramList, String paramString)
  {
    int j = paramList.size();
    ArrayList localArrayList1 = new ArrayList(j);
    if (j == 1) {
      localArrayList1.add(((ParseRESTObjectCommand)paramList.get(0)).executeAsync(paramParseHttpClient));
    }
    for (;;)
    {
      return localArrayList1;
      if (j <= 50) {
        break;
      }
      paramList = Lists.partition(paramList, 50);
      i = 0;
      j = paramList.size();
      while (i < j)
      {
        localArrayList1.addAll(executeBatch(paramParseHttpClient, (List)paramList.get(i), paramString));
        i += 1;
      }
    }
    final ArrayList localArrayList2 = new ArrayList(j);
    int i = 0;
    while (i < j)
    {
      localObject1 = Task.create();
      localArrayList2.add(localObject1);
      localArrayList1.add(((Task.TaskCompletionSource)localObject1).getTask());
      i += 1;
    }
    Object localObject1 = new ArrayList(j);
    try
    {
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        Object localObject2 = (ParseRESTObjectCommand)paramList.next();
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("method", ((ParseRESTObjectCommand)localObject2).method.toString());
        localJSONObject.put("path", String.format("/1/%s", new Object[] { ((ParseRESTObjectCommand)localObject2).httpPath }));
        localObject2 = ((ParseRESTObjectCommand)localObject2).jsonParameters;
        if (localObject2 != null) {
          localJSONObject.put("body", localObject2);
        }
        ((List)localObject1).add(localJSONObject);
      }
      paramList = new HashMap();
    }
    catch (JSONException paramParseHttpClient)
    {
      throw new RuntimeException(paramParseHttpClient);
    }
    paramList.put("requests", localObject1);
    new ParseRESTObjectBatchCommand("batch", ParseHttpRequest.Method.POST, paramList, paramString).executeAsync(paramParseHttpClient).continueWith(new Continuation()
    {
      public Void then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        Task.TaskCompletionSource localTaskCompletionSource;
        if ((paramAnonymousTask.isFaulted()) || (paramAnonymousTask.isCancelled()))
        {
          i = 0;
          if (i < this.val$batchSize)
          {
            localTaskCompletionSource = (Task.TaskCompletionSource)localArrayList2.get(i);
            if (paramAnonymousTask.isFaulted()) {
              localTaskCompletionSource.setError(paramAnonymousTask.getError());
            }
            for (;;)
            {
              i += 1;
              break;
              localTaskCompletionSource.setCancelled();
            }
          }
        }
        paramAnonymousTask = ((JSONObject)paramAnonymousTask.getResult()).getJSONArray("results");
        int j = paramAnonymousTask.length();
        if (j != this.val$batchSize)
        {
          i = 0;
          while (i < this.val$batchSize)
          {
            ((Task.TaskCompletionSource)localArrayList2.get(i)).setError(new IllegalStateException("Batch command result count expected: " + this.val$batchSize + " but was: " + j));
            i += 1;
          }
        }
        int i = 0;
        if (i < this.val$batchSize)
        {
          JSONObject localJSONObject = paramAnonymousTask.getJSONObject(i);
          localTaskCompletionSource = (Task.TaskCompletionSource)localArrayList2.get(i);
          if (localJSONObject.has("success")) {
            localTaskCompletionSource.setResult(localJSONObject.getJSONObject("success"));
          }
          for (;;)
          {
            i += 1;
            break;
            if (localJSONObject.has("error"))
            {
              localJSONObject = localJSONObject.getJSONObject("error");
              localTaskCompletionSource.setError(new ParseException(localJSONObject.getInt("code"), localJSONObject.getString("error")));
            }
          }
        }
        return null;
      }
    });
    return localArrayList1;
  }
  
  protected Task<JSONObject> onResponseAsync(ParseHttpResponse paramParseHttpResponse, ProgressCallback paramProgressCallback)
  {
    Object localObject = null;
    paramProgressCallback = null;
    try
    {
      paramParseHttpResponse = paramParseHttpResponse.getContent();
      paramProgressCallback = paramParseHttpResponse;
      localObject = paramParseHttpResponse;
      String str = new String(ParseIOUtils.toByteArray(paramParseHttpResponse));
      ParseIOUtils.closeQuietly(paramParseHttpResponse);
      return Task.forError(newTemporaryException("bad json response", paramParseHttpResponse));
    }
    catch (IOException paramParseHttpResponse)
    {
      paramParseHttpResponse = paramParseHttpResponse;
      localObject = paramProgressCallback;
      paramParseHttpResponse = Task.forError(paramParseHttpResponse);
      ParseIOUtils.closeQuietly(paramProgressCallback);
      return paramParseHttpResponse;
    }
    finally
    {
      ParseIOUtils.closeQuietly((InputStream)localObject);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRESTObjectBatchCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
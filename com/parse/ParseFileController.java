package com.parse;

import bolts.Continuation;
import bolts.Task;
import com.parse.http.ParseHttpRequest.Method;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import org.json.JSONObject;

class ParseFileController
{
  private ParseHttpClient awsClient;
  private final File cachePath;
  private final Object lock = new Object();
  private final ParseHttpClient restClient;
  
  public ParseFileController(ParseHttpClient paramParseHttpClient, File paramFile)
  {
    this.restClient = paramParseHttpClient;
    this.cachePath = paramFile;
  }
  
  ParseFileController awsClient(ParseHttpClient paramParseHttpClient)
  {
    synchronized (this.lock)
    {
      this.awsClient = paramParseHttpClient;
      return this;
    }
  }
  
  ParseHttpClient awsClient()
  {
    synchronized (this.lock)
    {
      if (this.awsClient == null) {
        this.awsClient = ParsePlugins.get().newHttpClient();
      }
      ParseHttpClient localParseHttpClient = this.awsClient;
      return localParseHttpClient;
    }
  }
  
  public void clearCache()
  {
    File[] arrayOfFile = this.cachePath.listFiles();
    if (arrayOfFile == null) {}
    for (;;)
    {
      return;
      int j = arrayOfFile.length;
      int i = 0;
      while (i < j)
      {
        ParseFileUtils.deleteQuietly(arrayOfFile[i]);
        i += 1;
      }
    }
  }
  
  public Task<File> fetchAsync(final ParseFile.State paramState, final String paramString, final ProgressCallback paramProgressCallback, final Task<Void> paramTask)
  {
    if ((paramTask != null) && (paramTask.isCancelled())) {
      return Task.cancelled();
    }
    paramString = getCacheFile(paramState);
    Task.call(new Callable()
    {
      public Boolean call()
        throws Exception
      {
        return Boolean.valueOf(paramString.exists());
      }
    }, ParseExecutors.io()).continueWithTask(new Continuation()
    {
      public Task<File> then(final Task<Boolean> paramAnonymousTask)
        throws Exception
      {
        if (((Boolean)paramAnonymousTask.getResult()).booleanValue()) {
          return Task.forResult(paramString);
        }
        if ((paramTask != null) && (paramTask.isCancelled())) {
          return Task.cancelled();
        }
        paramAnonymousTask = ParseFileController.this.getTempFile(paramState);
        new ParseAWSRequest(ParseHttpRequest.Method.GET, paramState.url(), paramAnonymousTask).executeAsync(ParseFileController.this.awsClient(), null, paramProgressCallback, paramTask).continueWithTask(new Continuation()
        {
          public Task<File> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            if ((ParseFileController.3.this.val$cancellationToken != null) && (ParseFileController.3.this.val$cancellationToken.isCancelled())) {
              throw new CancellationException();
            }
            if (paramAnonymous2Task.isFaulted())
            {
              ParseFileUtils.deleteQuietly(paramAnonymousTask);
              return paramAnonymous2Task.cast();
            }
            ParseFileUtils.deleteQuietly(ParseFileController.3.this.val$cacheFile);
            ParseFileUtils.moveFile(paramAnonymousTask, ParseFileController.3.this.val$cacheFile);
            return Task.forResult(ParseFileController.3.this.val$cacheFile);
          }
        }, ParseExecutors.io());
      }
    });
  }
  
  public File getCacheFile(ParseFile.State paramState)
  {
    return new File(this.cachePath, paramState.name());
  }
  
  File getTempFile(ParseFile.State paramState)
  {
    if (paramState.url() == null) {
      return null;
    }
    return new File(this.cachePath, paramState.url() + ".tmp");
  }
  
  public boolean isDataAvailable(ParseFile.State paramState)
  {
    return getCacheFile(paramState).exists();
  }
  
  public Task<ParseFile.State> saveAsync(final ParseFile.State paramState, final File paramFile, String paramString, ProgressCallback paramProgressCallback, Task<Void> paramTask)
  {
    if (paramState.url() != null) {
      return Task.forResult(paramState);
    }
    if ((paramTask != null) && (paramTask.isCancelled())) {
      return Task.cancelled();
    }
    paramString = ((ParseRESTFileCommand.Builder)new ParseRESTFileCommand.Builder().fileName(paramState.name()).file(paramFile).contentType(paramState.mimeType()).sessionToken(paramString)).build();
    paramString.enableRetrying();
    paramString.executeAsync(this.restClient, paramProgressCallback, null, paramTask).onSuccess(new Continuation()
    {
      public ParseFile.State then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (JSONObject)paramAnonymousTask.getResult();
        paramAnonymousTask = new ParseFile.State.Builder(paramState).name(paramAnonymousTask.getString("name")).url(paramAnonymousTask.getString("url")).build();
        try
        {
          ParseFileUtils.copyFile(paramFile, ParseFileController.this.getCacheFile(paramAnonymousTask));
          return paramAnonymousTask;
        }
        catch (IOException localIOException) {}
        return paramAnonymousTask;
      }
    }, ParseExecutors.io());
  }
  
  public Task<ParseFile.State> saveAsync(final ParseFile.State paramState, final byte[] paramArrayOfByte, String paramString, ProgressCallback paramProgressCallback, Task<Void> paramTask)
  {
    if (paramState.url() != null) {
      return Task.forResult(paramState);
    }
    if ((paramTask != null) && (paramTask.isCancelled())) {
      return Task.cancelled();
    }
    paramString = ((ParseRESTFileCommand.Builder)new ParseRESTFileCommand.Builder().fileName(paramState.name()).data(paramArrayOfByte).contentType(paramState.mimeType()).sessionToken(paramString)).build();
    paramString.enableRetrying();
    paramString.executeAsync(this.restClient, paramProgressCallback, null, paramTask).onSuccess(new Continuation()
    {
      public ParseFile.State then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (JSONObject)paramAnonymousTask.getResult();
        paramAnonymousTask = new ParseFile.State.Builder(paramState).name(paramAnonymousTask.getString("name")).url(paramAnonymousTask.getString("url")).build();
        try
        {
          ParseFileUtils.writeByteArrayToFile(ParseFileController.this.getCacheFile(paramAnonymousTask), paramArrayOfByte);
          return paramAnonymousTask;
        }
        catch (IOException localIOException) {}
        return paramAnonymousTask;
      }
    }, ParseExecutors.io());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseFileController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.parse;

import bolts.Continuation;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseFile
{
  static final int MAX_FILE_SIZE = 10485760;
  private Set<Task<?>.TaskCompletionSource> currentTasks = Collections.synchronizedSet(new HashSet());
  byte[] data;
  private State state;
  final TaskQueue taskQueue = new TaskQueue();
  
  ParseFile(State paramState)
  {
    this.state = paramState;
  }
  
  public ParseFile(String paramString, byte[] paramArrayOfByte)
  {
    this(paramString, paramArrayOfByte, null);
  }
  
  public ParseFile(String paramString1, byte[] paramArrayOfByte, String paramString2)
  {
    this(new ParseFile.State.Builder().name(paramString1).mimeType(paramString2).build());
    if (paramArrayOfByte.length > 10485760) {
      throw new IllegalArgumentException(String.format("ParseFile must be less than %d bytes", new Object[] { Integer.valueOf(10485760) }));
    }
    this.data = paramArrayOfByte;
  }
  
  ParseFile(JSONObject paramJSONObject, ParseDecoder paramParseDecoder)
  {
    this(new ParseFile.State.Builder().name(paramJSONObject.optString("name")).url(paramJSONObject.optString("url")).build());
  }
  
  public ParseFile(byte[] paramArrayOfByte)
  {
    this(null, paramArrayOfByte, null);
  }
  
  public ParseFile(byte[] paramArrayOfByte, String paramString)
  {
    this(null, paramArrayOfByte, paramString);
  }
  
  private Task<byte[]> getDataAsync(final ProgressCallback paramProgressCallback, Task<Void> paramTask1, final Task<Void> paramTask2)
  {
    if (this.data != null) {
      return Task.forResult(this.data);
    }
    if ((paramTask2 != null) && (paramTask2.isCancelled())) {
      return Task.cancelled();
    }
    paramTask1.continueWithTask(new Continuation()
    {
      public Task<byte[]> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        if (ParseFile.this.data != null) {
          return Task.forResult(ParseFile.this.data);
        }
        if ((paramTask2 != null) && (paramTask2.isCancelled())) {
          return Task.cancelled();
        }
        ParseFile.getFileController().fetchAsync(ParseFile.this.state, null, ParseFile.progressCallbackOnMainThread(paramProgressCallback), paramTask2).onSuccess(new Continuation()
        {
          public byte[] then(Task<File> paramAnonymous2Task)
            throws Exception
          {
            paramAnonymous2Task = (File)paramAnonymous2Task.getResult();
            try
            {
              ParseFile.this.data = ParseFileUtils.readFileToByteArray(paramAnonymous2Task);
              paramAnonymous2Task = ParseFile.this.data;
              return paramAnonymous2Task;
            }
            catch (IOException paramAnonymous2Task) {}
            return null;
          }
        });
      }
    });
  }
  
  static ParseFileController getFileController()
  {
    return ParseCorePlugins.getInstance().getFileController();
  }
  
  private static ProgressCallback progressCallbackOnMainThread(ProgressCallback paramProgressCallback)
  {
    if (paramProgressCallback == null) {
      return null;
    }
    new ProgressCallback()
    {
      public void done(final Integer paramAnonymousInteger)
      {
        Task.call(new Callable()
        {
          public Void call()
            throws Exception
          {
            ParseFile.1.this.val$progressCallback.done(paramAnonymousInteger);
            return null;
          }
        }, ParseExecutors.main());
      }
    };
  }
  
  private Task<Void> saveAsync(final String paramString, final ProgressCallback paramProgressCallback, Task<Void> paramTask1, final Task<Void> paramTask2)
  {
    if (!isDirty()) {
      return Task.forResult(null);
    }
    if ((paramTask2 != null) && (paramTask2.isCancelled())) {
      return Task.cancelled();
    }
    paramTask1.continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        if (!ParseFile.this.isDirty()) {
          return Task.forResult(null);
        }
        if ((paramTask2 != null) && (paramTask2.isCancelled())) {
          return Task.cancelled();
        }
        ParseFile.getFileController().saveAsync(ParseFile.this.state, ParseFile.this.data, paramString, ParseFile.progressCallbackOnMainThread(paramProgressCallback), paramTask2).onSuccessTask(new Continuation()
        {
          public Task<Void> then(Task<ParseFile.State> paramAnonymous2Task)
            throws Exception
          {
            ParseFile.access$402(ParseFile.this, (ParseFile.State)paramAnonymous2Task.getResult());
            return paramAnonymous2Task.makeVoid();
          }
        });
      }
    });
  }
  
  public void cancel()
  {
    HashSet localHashSet = new HashSet(this.currentTasks);
    Iterator localIterator = localHashSet.iterator();
    while (localIterator.hasNext()) {
      ((Task.TaskCompletionSource)localIterator.next()).trySetCancelled();
    }
    this.currentTasks.removeAll(localHashSet);
  }
  
  JSONObject encode()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("__type", "File");
    localJSONObject.put("name", getName());
    if (getUrl() == null) {
      throw new IllegalStateException("Unable to encode an unsaved ParseFile.");
    }
    localJSONObject.put("url", getUrl());
    return localJSONObject;
  }
  
  public byte[] getData()
    throws ParseException
  {
    return (byte[])ParseTaskUtils.wait(getDataInBackground());
  }
  
  public Task<byte[]> getDataInBackground()
  {
    return getDataInBackground((ProgressCallback)null);
  }
  
  public Task<byte[]> getDataInBackground(final ProgressCallback paramProgressCallback)
  {
    final Task.TaskCompletionSource localTaskCompletionSource = Task.create();
    this.currentTasks.add(localTaskCompletionSource);
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<byte[]> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseFile.this.getDataAsync(paramProgressCallback, paramAnonymousTask, localTaskCompletionSource.getTask());
      }
    }).continueWithTask(new Continuation()
    {
      public Task<byte[]> then(Task<byte[]> paramAnonymousTask)
        throws Exception
      {
        localTaskCompletionSource.trySetResult(null);
        ParseFile.this.currentTasks.remove(localTaskCompletionSource);
        return paramAnonymousTask;
      }
    });
  }
  
  public void getDataInBackground(GetDataCallback paramGetDataCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(getDataInBackground(), paramGetDataCallback);
  }
  
  public void getDataInBackground(GetDataCallback paramGetDataCallback, ProgressCallback paramProgressCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(getDataInBackground(paramProgressCallback), paramGetDataCallback);
  }
  
  public String getName()
  {
    return this.state.name();
  }
  
  State getState()
  {
    return this.state;
  }
  
  public String getUrl()
  {
    return this.state.url();
  }
  
  public boolean isDataAvailable()
  {
    return (this.data != null) || (getFileController().isDataAvailable(this.state));
  }
  
  public boolean isDirty()
  {
    return this.state.url() == null;
  }
  
  public void save()
    throws ParseException
  {
    ParseTaskUtils.wait(saveInBackground());
  }
  
  Task<Void> saveAsync(final String paramString, final ProgressCallback paramProgressCallback, final Task<Void> paramTask)
  {
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseFile.this.saveAsync(paramString, paramProgressCallback, paramAnonymousTask, paramTask);
      }
    });
  }
  
  public Task<Void> saveInBackground()
  {
    return saveInBackground((ProgressCallback)null);
  }
  
  public Task<Void> saveInBackground(final ProgressCallback paramProgressCallback)
  {
    final Task.TaskCompletionSource localTaskCompletionSource = Task.create();
    this.currentTasks.add(localTaskCompletionSource);
    ParseUser.getCurrentSessionTokenAsync().onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        return ParseFile.this.saveAsync(paramAnonymousTask, paramProgressCallback, localTaskCompletionSource.getTask());
      }
    }).continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        localTaskCompletionSource.trySetResult(null);
        ParseFile.this.currentTasks.remove(localTaskCompletionSource);
        return paramAnonymousTask;
      }
    });
  }
  
  public void saveInBackground(SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(saveInBackground(), paramSaveCallback);
  }
  
  public void saveInBackground(SaveCallback paramSaveCallback, ProgressCallback paramProgressCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(saveInBackground(paramProgressCallback), paramSaveCallback);
  }
  
  static class State
  {
    private final String contentType;
    private final String name;
    private final String url;
    
    private State(Builder paramBuilder)
    {
      if (paramBuilder.name != null) {}
      for (String str = paramBuilder.name;; str = "file")
      {
        this.name = str;
        this.contentType = paramBuilder.mimeType;
        this.url = paramBuilder.url;
        return;
      }
    }
    
    public String mimeType()
    {
      return this.contentType;
    }
    
    public String name()
    {
      return this.name;
    }
    
    public String url()
    {
      return this.url;
    }
    
    static class Builder
    {
      private String mimeType;
      private String name;
      private String url;
      
      public Builder() {}
      
      public Builder(ParseFile.State paramState)
      {
        this.name = paramState.name();
        this.mimeType = paramState.mimeType();
        this.url = paramState.url();
      }
      
      public ParseFile.State build()
      {
        return new ParseFile.State(this, null);
      }
      
      public Builder mimeType(String paramString)
      {
        this.mimeType = paramString;
        return this;
      }
      
      public Builder name(String paramString)
      {
        this.name = paramString;
        return this;
      }
      
      public Builder url(String paramString)
      {
        this.url = paramString;
        return this;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
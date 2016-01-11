package com.parse;

import bolts.Task;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import org.json.JSONException;

class FileObjectStore<T extends ParseObject>
  implements ParseObjectStore<T>
{
  private final String className;
  private final ParseObjectCurrentCoder coder;
  private final File file;
  
  public FileObjectStore(Class<T> paramClass, File paramFile, ParseObjectCurrentCoder paramParseObjectCurrentCoder)
  {
    this(ParseObject.getClassName(paramClass), paramFile, paramParseObjectCurrentCoder);
  }
  
  public FileObjectStore(String paramString, File paramFile, ParseObjectCurrentCoder paramParseObjectCurrentCoder)
  {
    this.className = paramString;
    this.file = paramFile;
    this.coder = paramParseObjectCurrentCoder;
  }
  
  private static <T extends ParseObject> T getFromDisk(ParseObjectCurrentCoder paramParseObjectCurrentCoder, File paramFile, ParseObject.State.Init paramInit)
  {
    try
    {
      paramFile = ParseFileUtils.readFileToJSONObject(paramFile);
      return ParseObject.from(paramParseObjectCurrentCoder.decode(paramInit, paramFile, ParseDecoder.get()).isComplete(true).build());
    }
    catch (IOException paramParseObjectCurrentCoder)
    {
      return null;
    }
    catch (JSONException paramParseObjectCurrentCoder)
    {
      for (;;) {}
    }
  }
  
  private static void saveToDisk(ParseObjectCurrentCoder paramParseObjectCurrentCoder, ParseObject paramParseObject, File paramFile)
  {
    paramParseObjectCurrentCoder = paramParseObjectCurrentCoder.encode(paramParseObject.getState(), null, PointerEncoder.get());
    try
    {
      ParseFileUtils.writeJSONObjectToFile(paramFile, paramParseObjectCurrentCoder);
      return;
    }
    catch (IOException paramParseObjectCurrentCoder) {}
  }
  
  public Task<Void> deleteAsync()
  {
    Task.call(new Callable()
    {
      public Void call()
        throws Exception
      {
        if ((FileObjectStore.this.file.exists()) && (!ParseFileUtils.deleteQuietly(FileObjectStore.this.file))) {
          throw new RuntimeException("Unable to delete");
        }
        return null;
      }
    }, ParseExecutors.io());
  }
  
  public Task<Boolean> existsAsync()
  {
    Task.call(new Callable()
    {
      public Boolean call()
        throws Exception
      {
        return Boolean.valueOf(FileObjectStore.this.file.exists());
      }
    }, ParseExecutors.io());
  }
  
  public Task<T> getAsync()
  {
    Task.call(new Callable()
    {
      public T call()
        throws Exception
      {
        if (!FileObjectStore.this.file.exists()) {
          return null;
        }
        return FileObjectStore.getFromDisk(FileObjectStore.this.coder, FileObjectStore.this.file, ParseObject.State.newBuilder(FileObjectStore.this.className));
      }
    }, ParseExecutors.io());
  }
  
  public Task<Void> setAsync(final T paramT)
  {
    Task.call(new Callable()
    {
      public Void call()
        throws Exception
      {
        FileObjectStore.saveToDisk(FileObjectStore.this.coder, paramT, FileObjectStore.this.file);
        return null;
      }
    }, ParseExecutors.io());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/FileObjectStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
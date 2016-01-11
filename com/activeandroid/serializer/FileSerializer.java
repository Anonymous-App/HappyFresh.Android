package com.activeandroid.serializer;

import java.io.File;

public final class FileSerializer
  extends TypeSerializer
{
  public File deserialize(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    return new File((String)paramObject);
  }
  
  public Class<?> getDeserializedType()
  {
    return File.class;
  }
  
  public Class<?> getSerializedType()
  {
    return String.class;
  }
  
  public String serialize(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    return ((File)paramObject).toString();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/serializer/FileSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
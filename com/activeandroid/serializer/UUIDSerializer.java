package com.activeandroid.serializer;

import java.util.UUID;

public final class UUIDSerializer
  extends TypeSerializer
{
  public UUID deserialize(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    return UUID.fromString((String)paramObject);
  }
  
  public Class<?> getDeserializedType()
  {
    return UUID.class;
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
    return ((UUID)paramObject).toString();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/serializer/UUIDSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
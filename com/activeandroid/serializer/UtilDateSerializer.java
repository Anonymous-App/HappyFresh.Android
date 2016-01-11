package com.activeandroid.serializer;

import java.util.Date;

public final class UtilDateSerializer
  extends TypeSerializer
{
  public Date deserialize(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    return new Date(((Long)paramObject).longValue());
  }
  
  public Class<?> getDeserializedType()
  {
    return Date.class;
  }
  
  public Class<?> getSerializedType()
  {
    return Long.TYPE;
  }
  
  public Long serialize(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    return Long.valueOf(((Date)paramObject).getTime());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/serializer/UtilDateSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
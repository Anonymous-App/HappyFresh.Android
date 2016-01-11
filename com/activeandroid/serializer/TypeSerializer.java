package com.activeandroid.serializer;

public abstract class TypeSerializer
{
  public abstract Object deserialize(Object paramObject);
  
  public abstract Class<?> getDeserializedType();
  
  public abstract Class<?> getSerializedType();
  
  public abstract Object serialize(Object paramObject);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/serializer/TypeSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
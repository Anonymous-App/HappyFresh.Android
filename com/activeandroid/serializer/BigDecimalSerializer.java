package com.activeandroid.serializer;

import java.math.BigDecimal;

public final class BigDecimalSerializer
  extends TypeSerializer
{
  public BigDecimal deserialize(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    return new BigDecimal((String)paramObject);
  }
  
  public Class<?> getDeserializedType()
  {
    return BigDecimal.class;
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
    return ((BigDecimal)paramObject).toString();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/serializer/BigDecimalSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
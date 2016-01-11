package com.optimizely.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptimizelyView
{
  protected static final Object NOT_FOUND_VALUE = new Object();
  @Expose
  @SerializedName("key")
  private String key;
  @Expose
  @SerializedName("optimizely_id")
  private String optimizelyId;
  @Expose
  @SerializedName("type")
  private String type;
  @Expose
  @SerializedName("value")
  private Object value;
  
  protected boolean declaredProperty(String paramString, Object paramObject)
  {
    if ("value".equals(paramString))
    {
      if ((paramObject instanceof Object))
      {
        setValue(paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"value\" is of type \"Object\", but got " + paramObject.getClass().toString());
    }
    if ("key".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setKey((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"key\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    if ("optimizely_id".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setOptimizelyId((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"optimizely_id\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    if ("type".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setType((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"type\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    return false;
  }
  
  protected Object declaredPropertyOrNotFound(String paramString, Object paramObject)
  {
    if ("value".equals(paramString)) {
      paramObject = getValue();
    }
    do
    {
      return paramObject;
      if ("key".equals(paramString)) {
        return getKey();
      }
      if ("optimizely_id".equals(paramString)) {
        return getOptimizelyId();
      }
    } while (!"type".equals(paramString));
    return getType();
  }
  
  public <T> T get(String paramString)
  {
    Object localObject = declaredPropertyOrNotFound(paramString, NOT_FOUND_VALUE);
    if (NOT_FOUND_VALUE != localObject) {
      return (T)localObject;
    }
    throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public String getOptimizelyId()
  {
    return this.optimizelyId;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public Object getValue()
  {
    return this.value;
  }
  
  public void set(String paramString, Object paramObject)
  {
    if (!declaredProperty(paramString, paramObject)) {
      throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
    }
  }
  
  public void setKey(String paramString)
  {
    this.key = paramString;
  }
  
  public void setOptimizelyId(String paramString)
  {
    this.optimizelyId = paramString;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public void setValue(Object paramObject)
  {
    this.value = paramObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/JSON/OptimizelyView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
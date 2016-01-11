package com.optimizely.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptimizelyVariable
{
  protected static final Object NOT_FOUND_VALUE = new Object();
  @Expose
  @SerializedName("defaultValue")
  private Object defaultValue;
  @Expose
  @SerializedName("type")
  private String type;
  @Expose
  @SerializedName("value")
  private Object value;
  @Expose
  @SerializedName("variableKey")
  private String variableKey;
  
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
    if ("defaultValue".equals(paramString))
    {
      if ((paramObject instanceof Object))
      {
        setDefaultValue(paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"defaultValue\" is of type \"Object\", but got " + paramObject.getClass().toString());
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
    if ("variableKey".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setVariableKey((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"variableKey\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
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
      if ("defaultValue".equals(paramString)) {
        return getDefaultValue();
      }
      if ("type".equals(paramString)) {
        return getType();
      }
    } while (!"variableKey".equals(paramString));
    return getVariableKey();
  }
  
  public <T> T get(String paramString)
  {
    Object localObject = declaredPropertyOrNotFound(paramString, NOT_FOUND_VALUE);
    if (NOT_FOUND_VALUE != localObject) {
      return (T)localObject;
    }
    throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
  }
  
  public Object getDefaultValue()
  {
    return this.defaultValue;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public Object getValue()
  {
    return this.value;
  }
  
  public String getVariableKey()
  {
    return this.variableKey;
  }
  
  public void set(String paramString, Object paramObject)
  {
    if (!declaredProperty(paramString, paramObject)) {
      throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
    }
  }
  
  public void setDefaultValue(Object paramObject)
  {
    this.defaultValue = paramObject;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public void setValue(Object paramObject)
  {
    this.value = paramObject;
  }
  
  public void setVariableKey(String paramString)
  {
    this.variableKey = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/JSON/OptimizelyVariable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.optimizely.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptimizelyCodeTest
{
  protected static final Object NOT_FOUND_VALUE = new Object();
  @Expose
  @SerializedName("block_key")
  private String blockKey;
  @Expose
  @SerializedName("block_name")
  private String blockName;
  
  protected boolean declaredProperty(String paramString, Object paramObject)
  {
    if ("block_key".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setBlockKey((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"block_key\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    if ("block_name".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setBlockName((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"block_name\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    return false;
  }
  
  protected Object declaredPropertyOrNotFound(String paramString, Object paramObject)
  {
    if ("block_key".equals(paramString)) {
      paramObject = getBlockKey();
    }
    while (!"block_name".equals(paramString)) {
      return paramObject;
    }
    return getBlockName();
  }
  
  public <T> T get(String paramString)
  {
    Object localObject = declaredPropertyOrNotFound(paramString, NOT_FOUND_VALUE);
    if (NOT_FOUND_VALUE != localObject) {
      return (T)localObject;
    }
    throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
  }
  
  public String getBlockKey()
  {
    return this.blockKey;
  }
  
  public String getBlockName()
  {
    return this.blockName;
  }
  
  public void set(String paramString, Object paramObject)
  {
    if (!declaredProperty(paramString, paramObject)) {
      throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
    }
  }
  
  public void setBlockKey(String paramString)
  {
    this.blockKey = paramString;
  }
  
  public void setBlockName(String paramString)
  {
    this.blockName = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/JSON/OptimizelyCodeTest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
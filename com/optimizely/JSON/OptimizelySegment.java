package com.optimizely.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptimizelySegment
{
  protected static final Object NOT_FOUND_VALUE = new Object();
  @Expose
  @SerializedName("api_name")
  private String apiName;
  @Expose
  @SerializedName("segment_id")
  private String segmentId;
  
  protected boolean declaredProperty(String paramString, Object paramObject)
  {
    if ("api_name".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setApiName((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"api_name\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    if ("segment_id".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setSegmentId((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"segment_id\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    return false;
  }
  
  protected Object declaredPropertyOrNotFound(String paramString, Object paramObject)
  {
    if ("api_name".equals(paramString)) {
      paramObject = getApiName();
    }
    while (!"segment_id".equals(paramString)) {
      return paramObject;
    }
    return getSegmentId();
  }
  
  public <T> T get(String paramString)
  {
    Object localObject = declaredPropertyOrNotFound(paramString, NOT_FOUND_VALUE);
    if (NOT_FOUND_VALUE != localObject) {
      return (T)localObject;
    }
    throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
  }
  
  public String getApiName()
  {
    return this.apiName;
  }
  
  public String getSegmentId()
  {
    return this.segmentId;
  }
  
  public void set(String paramString, Object paramObject)
  {
    if (!declaredProperty(paramString, paramObject)) {
      throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
    }
  }
  
  public void setApiName(String paramString)
  {
    this.apiName = paramString;
  }
  
  public void setSegmentId(String paramString)
  {
    this.segmentId = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/JSON/OptimizelySegment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
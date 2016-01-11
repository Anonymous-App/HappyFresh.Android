package com.optimizely.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptimizelyConfig
{
  protected static final Object NOT_FOUND_VALUE = new Object();
  @Expose
  @SerializedName("ip_anonymization")
  private boolean ipAnonymization;
  
  protected boolean declaredProperty(String paramString, Object paramObject)
  {
    if ("ip_anonymization".equals(paramString))
    {
      if ((paramObject instanceof Boolean))
      {
        setIpAnonymization(((Boolean)paramObject).booleanValue());
        return true;
      }
      throw new IllegalArgumentException("property \"ip_anonymization\" is of type \"boolean\", but got " + paramObject.getClass().toString());
    }
    return false;
  }
  
  protected Object declaredPropertyOrNotFound(String paramString, Object paramObject)
  {
    if ("ip_anonymization".equals(paramString)) {
      paramObject = Boolean.valueOf(isIpAnonymization());
    }
    return paramObject;
  }
  
  public <T> T get(String paramString)
  {
    Object localObject = declaredPropertyOrNotFound(paramString, NOT_FOUND_VALUE);
    if (NOT_FOUND_VALUE != localObject) {
      return (T)localObject;
    }
    throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
  }
  
  public boolean isIpAnonymization()
  {
    return this.ipAnonymization;
  }
  
  public void set(String paramString, Object paramObject)
  {
    if (!declaredProperty(paramString, paramObject)) {
      throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
    }
  }
  
  public void setIpAnonymization(boolean paramBoolean)
  {
    this.ipAnonymization = paramBoolean;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/JSON/OptimizelyConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
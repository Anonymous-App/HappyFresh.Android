package com.optimizely.JSON;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptimizelyPluginConfig
{
  protected static final Object NOT_FOUND_VALUE = new Object();
  @Expose
  @SerializedName("config")
  private JsonObject config;
  @Expose
  @SerializedName("enabled")
  private Boolean enabled;
  @Expose
  @SerializedName("id")
  private String id;
  
  protected boolean declaredProperty(String paramString, Object paramObject)
  {
    if ("config".equals(paramString))
    {
      if ((paramObject instanceof JsonObject))
      {
        setConfig((JsonObject)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"config\" is of type \"com.google.gson.JsonObject\", but got " + paramObject.getClass().toString());
    }
    if ("enabled".equals(paramString))
    {
      if ((paramObject instanceof Boolean))
      {
        setEnabled((Boolean)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"enabled\" is of type \"java.lang.Boolean\", but got " + paramObject.getClass().toString());
    }
    if ("id".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setId((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"id\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    return false;
  }
  
  protected Object declaredPropertyOrNotFound(String paramString, Object paramObject)
  {
    if ("config".equals(paramString)) {
      paramObject = getConfig();
    }
    do
    {
      return paramObject;
      if ("enabled".equals(paramString)) {
        return getEnabled();
      }
    } while (!"id".equals(paramString));
    return getId();
  }
  
  public <T> T get(String paramString)
  {
    Object localObject = declaredPropertyOrNotFound(paramString, NOT_FOUND_VALUE);
    if (NOT_FOUND_VALUE != localObject) {
      return (T)localObject;
    }
    throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
  }
  
  public JsonObject getConfig()
  {
    return this.config;
  }
  
  public Boolean getEnabled()
  {
    return this.enabled;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void set(String paramString, Object paramObject)
  {
    if (!declaredProperty(paramString, paramObject)) {
      throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
    }
  }
  
  public void setConfig(JsonObject paramJsonObject)
  {
    this.config = paramJsonObject;
  }
  
  public void setEnabled(Boolean paramBoolean)
  {
    this.enabled = paramBoolean;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/JSON/OptimizelyPluginConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
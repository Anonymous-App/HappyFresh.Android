package com.optimizely.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class OptimizelyGoal
{
  protected static final Object NOT_FOUND_VALUE = new Object();
  @Expose
  @SerializedName("element_ids")
  private List<String> elementIds = new ArrayList();
  @Expose
  @SerializedName("event")
  private String event;
  @Expose
  @SerializedName("experiment_ids")
  private List<String> experimentIds = new ArrayList();
  @Expose
  @SerializedName("id")
  private Long id;
  @Expose
  @SerializedName("revenue_tracking")
  private Boolean revenueTracking;
  @Expose
  @SerializedName("type")
  private String type;
  
  protected boolean declaredProperty(String paramString, Object paramObject)
  {
    if ("element_ids".equals(paramString))
    {
      if ((paramObject instanceof List))
      {
        setElementIds((List)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"element_ids\" is of type \"java.util.List<java.lang.String>\", but got " + paramObject.getClass().toString());
    }
    if ("experiment_ids".equals(paramString))
    {
      if ((paramObject instanceof List))
      {
        setExperimentIds((List)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"experiment_ids\" is of type \"java.util.List<java.lang.String>\", but got " + paramObject.getClass().toString());
    }
    if ("id".equals(paramString))
    {
      if ((paramObject instanceof Long))
      {
        setId((Long)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"id\" is of type \"java.lang.Long\", but got " + paramObject.getClass().toString());
    }
    if ("event".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setEvent((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"event\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    if ("revenue_tracking".equals(paramString))
    {
      if ((paramObject instanceof Boolean))
      {
        setRevenueTracking((Boolean)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"revenue_tracking\" is of type \"java.lang.Boolean\", but got " + paramObject.getClass().toString());
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
    if ("element_ids".equals(paramString)) {
      paramObject = getElementIds();
    }
    do
    {
      return paramObject;
      if ("experiment_ids".equals(paramString)) {
        return getExperimentIds();
      }
      if ("id".equals(paramString)) {
        return getId();
      }
      if ("event".equals(paramString)) {
        return getEvent();
      }
      if ("revenue_tracking".equals(paramString)) {
        return getRevenueTracking();
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
  
  public List<String> getElementIds()
  {
    return this.elementIds;
  }
  
  public String getEvent()
  {
    return this.event;
  }
  
  public List<String> getExperimentIds()
  {
    return this.experimentIds;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public Boolean getRevenueTracking()
  {
    return this.revenueTracking;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void set(String paramString, Object paramObject)
  {
    if (!declaredProperty(paramString, paramObject)) {
      throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
    }
  }
  
  public void setElementIds(List<String> paramList)
  {
    this.elementIds = paramList;
  }
  
  public void setEvent(String paramString)
  {
    this.event = paramString;
  }
  
  public void setExperimentIds(List<String> paramList)
  {
    this.experimentIds = paramList;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public void setRevenueTracking(Boolean paramBoolean)
  {
    this.revenueTracking = paramBoolean;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/JSON/OptimizelyGoal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
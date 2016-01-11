package com.optimizely.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class OptimizelyExperiment
{
  protected static final Object NOT_FOUND_VALUE = new Object();
  @Expose
  @SerializedName("active")
  private boolean active;
  @Expose
  @SerializedName("active_variation")
  private OptimizelyVariation activeVariation;
  @Expose
  @SerializedName("audiences")
  private List<String> audiences = new ArrayList();
  @Expose
  @SerializedName("conditions")
  private List<Object> conditions = new ArrayList();
  @Expose
  @SerializedName("description")
  private String description;
  @Expose
  @SerializedName("experiment_id")
  private String experimentId;
  @Expose
  @SerializedName("last_modified")
  private String lastModified;
  @Expose
  @SerializedName("locked")
  private boolean locked;
  @Expose
  @SerializedName("manual")
  private boolean manual;
  @Expose
  @SerializedName("passes_targeting")
  private boolean passesTargeting;
  @Expose
  @SerializedName("percentage_included")
  private Double percentageIncluded;
  @Expose
  @SerializedName("state")
  private String state = "ExperimentStateDeactivated";
  @Expose
  @SerializedName("variations")
  private List<OptimizelyVariation> variations = new ArrayList();
  @Expose
  @SerializedName("visited_count")
  private int visitedCount;
  
  protected boolean declaredProperty(String paramString, Object paramObject)
  {
    if ("audiences".equals(paramString))
    {
      if ((paramObject instanceof List))
      {
        setAudiences((List)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"audiences\" is of type \"java.util.List<java.lang.String>\", but got " + paramObject.getClass().toString());
    }
    if ("passes_targeting".equals(paramString))
    {
      if ((paramObject instanceof Boolean))
      {
        setPassesTargeting(((Boolean)paramObject).booleanValue());
        return true;
      }
      throw new IllegalArgumentException("property \"passes_targeting\" is of type \"boolean\", but got " + paramObject.getClass().toString());
    }
    if ("locked".equals(paramString))
    {
      if ((paramObject instanceof Boolean))
      {
        setLocked(((Boolean)paramObject).booleanValue());
        return true;
      }
      throw new IllegalArgumentException("property \"locked\" is of type \"boolean\", but got " + paramObject.getClass().toString());
    }
    if ("manual".equals(paramString))
    {
      if ((paramObject instanceof Boolean))
      {
        setManual(((Boolean)paramObject).booleanValue());
        return true;
      }
      throw new IllegalArgumentException("property \"manual\" is of type \"boolean\", but got " + paramObject.getClass().toString());
    }
    if ("active".equals(paramString))
    {
      if ((paramObject instanceof Boolean))
      {
        setActive(((Boolean)paramObject).booleanValue());
        return true;
      }
      throw new IllegalArgumentException("property \"active\" is of type \"boolean\", but got " + paramObject.getClass().toString());
    }
    if ("conditions".equals(paramString))
    {
      if ((paramObject instanceof List))
      {
        setConditions((List)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"conditions\" is of type \"java.util.List<java.lang.Object>\", but got " + paramObject.getClass().toString());
    }
    if ("description".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setDescription((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"description\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    if ("experiment_id".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setExperimentId((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"experiment_id\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    if ("last_modified".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setLastModified((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"last_modified\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    if ("percentage_included".equals(paramString))
    {
      if ((paramObject instanceof Double))
      {
        setPercentageIncluded((Double)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"percentage_included\" is of type \"java.lang.Double\", but got " + paramObject.getClass().toString());
    }
    if ("state".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setState((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"state\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    if ("visited_count".equals(paramString))
    {
      if ((paramObject instanceof Integer))
      {
        setVisitedCount(((Integer)paramObject).intValue());
        return true;
      }
      throw new IllegalArgumentException("property \"visited_count\" is of type \"int\", but got " + paramObject.getClass().toString());
    }
    if ("variations".equals(paramString))
    {
      if ((paramObject instanceof List))
      {
        setVariations((List)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"variations\" is of type \"java.util.List<com.optimizely.JSON.OptimizelyVariation>\", but got " + paramObject.getClass().toString());
    }
    if ("active_variation".equals(paramString))
    {
      if ((paramObject instanceof OptimizelyVariation))
      {
        setActiveVariation((OptimizelyVariation)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"active_variation\" is of type \"com.optimizely.JSON.OptimizelyVariation\", but got " + paramObject.getClass().toString());
    }
    return false;
  }
  
  protected Object declaredPropertyOrNotFound(String paramString, Object paramObject)
  {
    if ("audiences".equals(paramString)) {
      paramObject = getAudiences();
    }
    do
    {
      return paramObject;
      if ("passes_targeting".equals(paramString)) {
        return Boolean.valueOf(isPassesTargeting());
      }
      if ("locked".equals(paramString)) {
        return Boolean.valueOf(isLocked());
      }
      if ("manual".equals(paramString)) {
        return Boolean.valueOf(isManual());
      }
      if ("active".equals(paramString)) {
        return Boolean.valueOf(isActive());
      }
      if ("conditions".equals(paramString)) {
        return getConditions();
      }
      if ("description".equals(paramString)) {
        return getDescription();
      }
      if ("experiment_id".equals(paramString)) {
        return getExperimentId();
      }
      if ("last_modified".equals(paramString)) {
        return getLastModified();
      }
      if ("percentage_included".equals(paramString)) {
        return getPercentageIncluded();
      }
      if ("state".equals(paramString)) {
        return getState();
      }
      if ("visited_count".equals(paramString)) {
        return Integer.valueOf(getVisitedCount());
      }
      if ("variations".equals(paramString)) {
        return getVariations();
      }
    } while (!"active_variation".equals(paramString));
    return getActiveVariation();
  }
  
  public <T> T get(String paramString)
  {
    Object localObject = declaredPropertyOrNotFound(paramString, NOT_FOUND_VALUE);
    if (NOT_FOUND_VALUE != localObject) {
      return (T)localObject;
    }
    throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
  }
  
  public OptimizelyVariation getActiveVariation()
  {
    return this.activeVariation;
  }
  
  public List<String> getAudiences()
  {
    return this.audiences;
  }
  
  public List<Object> getConditions()
  {
    return this.conditions;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public String getExperimentId()
  {
    return this.experimentId;
  }
  
  public String getLastModified()
  {
    return this.lastModified;
  }
  
  public Double getPercentageIncluded()
  {
    return this.percentageIncluded;
  }
  
  public String getState()
  {
    return this.state;
  }
  
  public List<OptimizelyVariation> getVariations()
  {
    return this.variations;
  }
  
  public int getVisitedCount()
  {
    return this.visitedCount;
  }
  
  public boolean isActive()
  {
    return this.active;
  }
  
  public boolean isLocked()
  {
    return this.locked;
  }
  
  public boolean isManual()
  {
    return this.manual;
  }
  
  public boolean isPassesTargeting()
  {
    return this.passesTargeting;
  }
  
  public void set(String paramString, Object paramObject)
  {
    if (!declaredProperty(paramString, paramObject)) {
      throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
    }
  }
  
  public void setActive(boolean paramBoolean)
  {
    this.active = paramBoolean;
  }
  
  public void setActiveVariation(OptimizelyVariation paramOptimizelyVariation)
  {
    this.activeVariation = paramOptimizelyVariation;
  }
  
  public void setAudiences(List<String> paramList)
  {
    this.audiences = paramList;
  }
  
  public void setConditions(List<Object> paramList)
  {
    this.conditions = paramList;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public void setExperimentId(String paramString)
  {
    this.experimentId = paramString;
  }
  
  public void setLastModified(String paramString)
  {
    this.lastModified = paramString;
  }
  
  public void setLocked(boolean paramBoolean)
  {
    this.locked = paramBoolean;
  }
  
  public void setManual(boolean paramBoolean)
  {
    this.manual = paramBoolean;
  }
  
  public void setPassesTargeting(boolean paramBoolean)
  {
    this.passesTargeting = paramBoolean;
  }
  
  public void setPercentageIncluded(Double paramDouble)
  {
    this.percentageIncluded = paramDouble;
  }
  
  public void setState(String paramString)
  {
    this.state = paramString;
  }
  
  public void setVariations(List<OptimizelyVariation> paramList)
  {
    this.variations = paramList;
  }
  
  public void setVisitedCount(int paramInt)
  {
    this.visitedCount = paramInt;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/JSON/OptimizelyExperiment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.optimizely.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class OptimizelyJSON
{
  protected static final Object NOT_FOUND_VALUE = new Object();
  @Expose
  @SerializedName("account_id")
  private String accountId;
  @Expose
  @SerializedName("audiences")
  private Object audiences;
  @Expose
  @SerializedName("code_revision")
  private Double codeRevision;
  @Expose
  @SerializedName("config")
  private OptimizelyConfig config;
  @Expose
  @SerializedName("experiments")
  private List<OptimizelyExperiment> experiments = new ArrayList();
  @Expose
  @SerializedName("goals")
  private List<OptimizelyGoal> goals = new ArrayList();
  @Expose
  @SerializedName("plugins")
  private List<OptimizelyPluginConfig> plugins = new ArrayList();
  @Expose
  @SerializedName("segments")
  private List<OptimizelySegment> segments = new ArrayList();
  
  protected boolean declaredProperty(String paramString, Object paramObject)
  {
    if ("account_id".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setAccountId((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"account_id\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    if ("code_revision".equals(paramString))
    {
      if ((paramObject instanceof Double))
      {
        setCodeRevision((Double)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"code_revision\" is of type \"java.lang.Double\", but got " + paramObject.getClass().toString());
    }
    if ("experiments".equals(paramString))
    {
      if ((paramObject instanceof List))
      {
        setExperiments((List)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"experiments\" is of type \"java.util.List<com.optimizely.JSON.OptimizelyExperiment>\", but got " + paramObject.getClass().toString());
    }
    if ("goals".equals(paramString))
    {
      if ((paramObject instanceof List))
      {
        setGoals((List)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"goals\" is of type \"java.util.List<com.optimizely.JSON.OptimizelyGoal>\", but got " + paramObject.getClass().toString());
    }
    if ("plugins".equals(paramString))
    {
      if ((paramObject instanceof List))
      {
        setPlugins((List)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"plugins\" is of type \"java.util.List<com.optimizely.JSON.OptimizelyPluginConfig>\", but got " + paramObject.getClass().toString());
    }
    if ("segments".equals(paramString))
    {
      if ((paramObject instanceof List))
      {
        setSegments((List)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"segments\" is of type \"java.util.List<com.optimizely.JSON.OptimizelySegment>\", but got " + paramObject.getClass().toString());
    }
    if ("audiences".equals(paramString))
    {
      if ((paramObject instanceof Object))
      {
        setAudiences(paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"audiences\" is of type \"java.lang.Object\", but got " + paramObject.getClass().toString());
    }
    if ("config".equals(paramString))
    {
      if ((paramObject instanceof OptimizelyConfig))
      {
        setConfig((OptimizelyConfig)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"config\" is of type \"com.optimizely.JSON.OptimizelyConfig\", but got " + paramObject.getClass().toString());
    }
    return false;
  }
  
  protected Object declaredPropertyOrNotFound(String paramString, Object paramObject)
  {
    if ("account_id".equals(paramString)) {
      paramObject = getAccountId();
    }
    do
    {
      return paramObject;
      if ("code_revision".equals(paramString)) {
        return getCodeRevision();
      }
      if ("experiments".equals(paramString)) {
        return getExperiments();
      }
      if ("goals".equals(paramString)) {
        return getGoals();
      }
      if ("plugins".equals(paramString)) {
        return getPlugins();
      }
      if ("segments".equals(paramString)) {
        return getSegments();
      }
      if ("audiences".equals(paramString)) {
        return getAudiences();
      }
    } while (!"config".equals(paramString));
    return getConfig();
  }
  
  public <T> T get(String paramString)
  {
    Object localObject = declaredPropertyOrNotFound(paramString, NOT_FOUND_VALUE);
    if (NOT_FOUND_VALUE != localObject) {
      return (T)localObject;
    }
    throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
  }
  
  public String getAccountId()
  {
    return this.accountId;
  }
  
  public Object getAudiences()
  {
    return this.audiences;
  }
  
  public Double getCodeRevision()
  {
    return this.codeRevision;
  }
  
  public OptimizelyConfig getConfig()
  {
    return this.config;
  }
  
  public List<OptimizelyExperiment> getExperiments()
  {
    return this.experiments;
  }
  
  public List<OptimizelyGoal> getGoals()
  {
    return this.goals;
  }
  
  public List<OptimizelyPluginConfig> getPlugins()
  {
    return this.plugins;
  }
  
  public List<OptimizelySegment> getSegments()
  {
    return this.segments;
  }
  
  public void set(String paramString, Object paramObject)
  {
    if (!declaredProperty(paramString, paramObject)) {
      throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
    }
  }
  
  public void setAccountId(String paramString)
  {
    this.accountId = paramString;
  }
  
  public void setAudiences(Object paramObject)
  {
    this.audiences = paramObject;
  }
  
  public void setCodeRevision(Double paramDouble)
  {
    this.codeRevision = paramDouble;
  }
  
  public void setConfig(OptimizelyConfig paramOptimizelyConfig)
  {
    this.config = paramOptimizelyConfig;
  }
  
  public void setExperiments(List<OptimizelyExperiment> paramList)
  {
    this.experiments = paramList;
  }
  
  public void setGoals(List<OptimizelyGoal> paramList)
  {
    this.goals = paramList;
  }
  
  public void setPlugins(List<OptimizelyPluginConfig> paramList)
  {
    this.plugins = paramList;
  }
  
  public void setSegments(List<OptimizelySegment> paramList)
  {
    this.segments = paramList;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/JSON/OptimizelyJSON.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
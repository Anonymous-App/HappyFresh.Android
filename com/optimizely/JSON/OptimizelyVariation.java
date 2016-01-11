package com.optimizely.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class OptimizelyVariation
{
  protected static final Object NOT_FOUND_VALUE = new Object();
  @Expose
  @SerializedName("code_tests")
  private List<OptimizelyCodeTest> codeTests = new ArrayList();
  @Expose
  @SerializedName("description")
  private String description;
  @Expose
  @SerializedName("traffic")
  private Double traffic;
  @Expose
  @SerializedName("type")
  private String type;
  @Expose
  @SerializedName("variables")
  private List<OptimizelyVariable> variables = new ArrayList();
  @Expose
  @SerializedName("variation_id")
  private String variationId;
  @Expose
  @SerializedName("views")
  private List<OptimizelyView> views = new ArrayList();
  
  protected boolean declaredProperty(String paramString, Object paramObject)
  {
    if ("code_tests".equals(paramString))
    {
      if ((paramObject instanceof List))
      {
        setCodeTests((List)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"code_tests\" is of type \"java.util.List<com.optimizely.JSON.OptimizelyCodeTest>\", but got " + paramObject.getClass().toString());
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
    if ("traffic".equals(paramString))
    {
      if ((paramObject instanceof Double))
      {
        setTraffic((Double)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"traffic\" is of type \"java.lang.Double\", but got " + paramObject.getClass().toString());
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
    if ("variables".equals(paramString))
    {
      if ((paramObject instanceof List))
      {
        setVariables((List)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"variables\" is of type \"java.util.List<com.optimizely.JSON.OptimizelyVariable>\", but got " + paramObject.getClass().toString());
    }
    if ("variation_id".equals(paramString))
    {
      if ((paramObject instanceof String))
      {
        setVariationId((String)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"variation_id\" is of type \"java.lang.String\", but got " + paramObject.getClass().toString());
    }
    if ("views".equals(paramString))
    {
      if ((paramObject instanceof List))
      {
        setViews((List)paramObject);
        return true;
      }
      throw new IllegalArgumentException("property \"views\" is of type \"java.util.List<com.optimizely.JSON.OptimizelyView>\", but got " + paramObject.getClass().toString());
    }
    return false;
  }
  
  protected Object declaredPropertyOrNotFound(String paramString, Object paramObject)
  {
    if ("code_tests".equals(paramString)) {
      paramObject = getCodeTests();
    }
    do
    {
      return paramObject;
      if ("description".equals(paramString)) {
        return getDescription();
      }
      if ("traffic".equals(paramString)) {
        return getTraffic();
      }
      if ("type".equals(paramString)) {
        return getType();
      }
      if ("variables".equals(paramString)) {
        return getVariables();
      }
      if ("variation_id".equals(paramString)) {
        return getVariationId();
      }
    } while (!"views".equals(paramString));
    return getViews();
  }
  
  public <T> T get(String paramString)
  {
    Object localObject = declaredPropertyOrNotFound(paramString, NOT_FOUND_VALUE);
    if (NOT_FOUND_VALUE != localObject) {
      return (T)localObject;
    }
    throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
  }
  
  public List<OptimizelyCodeTest> getCodeTests()
  {
    return this.codeTests;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public Double getTraffic()
  {
    return this.traffic;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public List<OptimizelyVariable> getVariables()
  {
    return this.variables;
  }
  
  public String getVariationId()
  {
    return this.variationId;
  }
  
  public List<OptimizelyView> getViews()
  {
    return this.views;
  }
  
  public void set(String paramString, Object paramObject)
  {
    if (!declaredProperty(paramString, paramObject)) {
      throw new IllegalArgumentException("property \"" + paramString + "\" is not defined");
    }
  }
  
  public void setCodeTests(List<OptimizelyCodeTest> paramList)
  {
    this.codeTests = paramList;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public void setTraffic(Double paramDouble)
  {
    this.traffic = paramDouble;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public void setVariables(List<OptimizelyVariable> paramList)
  {
    this.variables = paramList;
  }
  
  public void setVariationId(String paramString)
  {
    this.variationId = paramString;
  }
  
  public void setViews(List<OptimizelyView> paramList)
  {
    this.views = paramList;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/JSON/OptimizelyVariation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
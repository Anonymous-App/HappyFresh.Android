package com.ad4screen.sdk.service.modules.inapp.model;

import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import org.json.JSONException;
import org.json.JSONObject;

public class f
  implements c<f>, d
{
  private double a;
  private double b;
  private double c;
  
  public double a()
  {
    return this.a;
  }
  
  public f a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject(getClassKey());
    this.a = paramString.getDouble("latitude");
    this.b = paramString.getDouble("longitude");
    this.c = paramString.getDouble("radius");
    return this;
  }
  
  public void a(double paramDouble)
  {
    this.a = paramDouble;
  }
  
  public double b()
  {
    return this.b;
  }
  
  public void b(double paramDouble)
  {
    this.b = paramDouble;
  }
  
  public double c()
  {
    return this.c;
  }
  
  public void c(double paramDouble)
  {
    this.c = paramDouble;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.LocationRule";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("latitude", this.a);
    localJSONObject2.put("longitude", this.b);
    localJSONObject2.put("radius", this.c);
    localJSONObject1.put(getClassKey(), localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
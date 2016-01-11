package com.appsee;

import org.json.JSONException;
import org.json.JSONObject;

class pd
{
  public pd(double paramDouble1, double paramDouble2, float paramFloat1, float paramFloat2)
  {
    this.k = paramDouble1;
    this.G = paramDouble2;
    this.i = paramFloat1;
    this.b = paramFloat2;
  }
  
  public double i()
  {
    return this.G;
  }
  
  public float i()
  {
    return this.i;
  }
  
  public JSONObject i()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put(AppseeBackgroundUploader.i("f:J"), this.k);
    localJSONObject.put(AppseeBackgroundUploader.i("f5Y"), this.G);
    localJSONObject.put(AppseeBackgroundUploader.i("w\033\\r\001=K8G"), this.i);
    localJSONObject.put(AppseeBackgroundUploader.i("i\033\\r\001=K8G"), this.b);
    return localJSONObject;
  }
  
  public void i(double paramDouble)
  {
    this.G = paramDouble;
  }
  
  public void i(float paramFloat)
  {
    this.b = paramFloat;
  }
  
  public double l()
  {
    return this.k;
  }
  
  public float l()
  {
    return this.b;
  }
  
  public void l(double paramDouble)
  {
    this.k = paramDouble;
  }
  
  public void l(float paramFloat)
  {
    this.i = paramFloat;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/pd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
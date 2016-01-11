package com.appsee;

import org.json.JSONException;
import org.json.JSONObject;

class ab
{
  public ab() {}
  
  public ab(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.i = paramString1;
    this.k = paramString2;
    this.G = paramBoolean1;
    this.b = paramBoolean2;
  }
  
  public String i()
  {
    return this.k;
  }
  
  public JSONObject i()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put(AppseeBackgroundUploader.i("V"), this.i);
    localJSONObject.put(AppseeBackgroundUploader.i("N"), this.k);
    localJSONObject.put(AppseeBackgroundUploader.i("]"), this.G);
    localJSONObject.put(AppseeBackgroundUploader.i("H"), this.b);
    return localJSONObject;
  }
  
  public void i(String paramString)
  {
    this.i = paramString;
  }
  
  public void i(boolean paramBoolean)
  {
    this.G = paramBoolean;
  }
  
  public boolean i()
  {
    return this.G;
  }
  
  public String l()
  {
    return this.i;
  }
  
  public void l(String paramString)
  {
    this.k = paramString;
  }
  
  public void l(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }
  
  public boolean l()
  {
    return this.b;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/ab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
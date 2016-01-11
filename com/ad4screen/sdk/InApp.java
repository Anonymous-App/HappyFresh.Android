package com.ad4screen.sdk;

import com.ad4screen.sdk.common.annotations.API;
import java.util.HashMap;

@API
public class InApp
{
  private String a;
  private String b;
  private int c;
  private HashMap<String, String> d;
  
  private InApp() {}
  
  protected InApp(String paramString1, int paramInt, String paramString2, HashMap<String, String> paramHashMap)
  {
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramInt;
    this.d = paramHashMap;
  }
  
  public int getContainer()
  {
    return this.c;
  }
  
  public HashMap<String, String> getCustomParameters()
  {
    return this.d;
  }
  
  public String getDisplayTemplate()
  {
    return this.b;
  }
  
  public String getId()
  {
    return this.a;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/InApp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
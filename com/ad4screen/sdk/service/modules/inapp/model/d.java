package com.ad4screen.sdk.service.modules.inapp.model;

public enum d
{
  private String c;
  
  private d(String paramString)
  {
    this.c = paramString;
  }
  
  public static d a(String paramString)
    throws IllegalArgumentException
  {
    String str = paramString;
    if (paramString != null)
    {
      paramString = paramString.trim();
      d[] arrayOfd = values();
      int j = arrayOfd.length;
      int i = 0;
      for (;;)
      {
        str = paramString;
        if (i >= j) {
          break;
        }
        str = arrayOfd[i];
        if (paramString.equalsIgnoreCase(str.a())) {
          return str;
        }
        i += 1;
      }
    }
    throw new IllegalArgumentException("No enum with text " + str + " found");
  }
  
  public String a()
  {
    return this.c;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
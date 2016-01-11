package com.ad4screen.sdk.service.modules.inapp.model.events.regular;

import java.util.regex.Pattern;

public class c
  extends a
{
  private Pattern c;
  
  public c() {}
  
  public c(Long paramLong, String paramString)
  {
    super(paramLong, paramString);
  }
  
  public Pattern c()
  {
    if (this.c == null) {
      this.c = Pattern.compile(this.b, 2);
    }
    return this.c;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.events.RegexEvent";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/events/regular/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
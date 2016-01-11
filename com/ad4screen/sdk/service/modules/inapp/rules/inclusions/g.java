package com.ad4screen.sdk.service.modules.inapp.rules.inclusions;

import android.content.Context;
import android.text.TextUtils;
import com.ad4screen.sdk.service.modules.inapp.model.j;
import com.ad4screen.sdk.service.modules.inapp.model.k;
import java.util.Iterator;
import java.util.List;

public class g
  implements com.ad4screen.sdk.service.modules.inapp.rules.g
{
  private String a;
  
  private boolean a(String paramString, List<k> paramList)
  {
    if (paramString != null)
    {
      paramList = paramList.iterator();
      while (paramList.hasNext()) {
        if (paramString.equals(((k)paramList.next()).a())) {
          return true;
        }
      }
    }
    return false;
  }
  
  private boolean a(List<k> paramList)
  {
    return (paramList != null) && (!paramList.isEmpty());
  }
  
  public String a()
  {
    return "ViewNameInclusionCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami)
  {
    this.a = parami.e();
  }
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg) {}
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    parami = parami.m().c();
    if (!a(parami)) {}
    do
    {
      return true;
      if (TextUtils.isEmpty(this.a)) {
        return false;
      }
    } while (a(this.a, parami));
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/inclusions/g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
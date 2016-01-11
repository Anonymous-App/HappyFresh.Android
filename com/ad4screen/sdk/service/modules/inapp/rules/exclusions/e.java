package com.ad4screen.sdk.service.modules.inapp.rules.exclusions;

import android.content.Context;
import android.location.Location;
import com.ad4screen.sdk.service.modules.inapp.model.f;
import com.ad4screen.sdk.service.modules.inapp.model.j;
import java.util.Iterator;
import java.util.List;

public class e
  implements com.ad4screen.sdk.service.modules.inapp.rules.g
{
  private Location a;
  private com.ad4screen.sdk.systems.g b;
  
  public e(com.ad4screen.sdk.systems.g paramg)
  {
    this.b = paramg;
  }
  
  private boolean a(Location paramLocation, List<f> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      f localf = (f)paramList.next();
      if (com.ad4screen.sdk.service.modules.inapp.e.a(paramLocation, localf) <= localf.c() + paramLocation.getAccuracy()) {
        return true;
      }
    }
    return false;
  }
  
  private boolean a(List<f> paramList)
  {
    return (paramList != null) && (!paramList.isEmpty());
  }
  
  public String a()
  {
    return "LocationExclusionCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami)
  {
    this.a = this.b.d();
  }
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg) {}
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    parami = parami.n().a();
    if (!a(parami)) {}
    do
    {
      return true;
      if (this.a == null) {
        return false;
      }
    } while (!a(this.a, parami));
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/exclusions/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
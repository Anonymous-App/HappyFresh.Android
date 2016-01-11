package com.ad4screen.sdk.service.modules.inapp.rules.inclusions;

import android.content.Context;
import android.location.Location;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.model.Geofence;
import com.ad4screen.sdk.service.modules.geofencing.a.b;
import com.ad4screen.sdk.service.modules.geofencing.a.c;
import com.ad4screen.sdk.service.modules.inapp.model.j;
import com.ad4screen.sdk.systems.f;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class c
  implements com.ad4screen.sdk.service.modules.inapp.rules.g
{
  private HashMap<String, Geofence> a;
  private Location b;
  private com.ad4screen.sdk.systems.g c;
  
  public c(final Context paramContext, com.ad4screen.sdk.systems.g paramg)
  {
    this.c = paramg;
    this.a = new HashMap();
    a(paramContext);
    f.a().a(a.b.class, new a.c()
    {
      public void a() {}
      
      public void a(boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, Geofence[] paramAnonymousArrayOfGeofence, Date paramAnonymousDate)
      {
        c.a(c.this).clear();
        c.a(c.this, paramContext);
      }
    });
  }
  
  private void a(Context paramContext)
  {
    paramContext = new com.ad4screen.sdk.service.modules.tracking.d(paramContext).a();
    com.ad4screen.sdk.common.persistence.e locale = new com.ad4screen.sdk.common.persistence.e();
    int i = 0;
    for (;;)
    {
      if (i < paramContext.size()) {
        try
        {
          Geofence localGeofence = (Geofence)locale.a(((JSONObject)paramContext.get(i)).toString(), new Geofence(""));
          this.a.put(localGeofence.id, localGeofence);
          i += 1;
        }
        catch (JSONException localJSONException)
        {
          for (;;)
          {
            Log.debug("GeofenceCheck|Error while retrieving geofences", localJSONException);
          }
        }
      }
    }
  }
  
  private boolean a(com.ad4screen.sdk.service.modules.inapp.model.i parami)
  {
    parami = parami.m().f();
    return (parami != null) && (!parami.isEmpty());
  }
  
  public String a()
  {
    return "GeofenceInclusionCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami)
  {
    this.b = this.c.d();
  }
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg) {}
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    if (!a(parami)) {
      return true;
    }
    parami = parami.m().f().iterator();
    while (parami.hasNext())
    {
      paramg = (com.ad4screen.sdk.service.modules.inapp.model.c)parami.next();
      if (paramg.b() == com.ad4screen.sdk.service.modules.inapp.model.d.a)
      {
        paramg = paramg.a();
        if (this.a.containsKey(paramg))
        {
          paramg = (Geofence)this.a.get(paramg);
          if (com.ad4screen.sdk.service.modules.inapp.e.a(this.b, paramg) <= paramg.radius + this.b.getAccuracy()) {
            return true;
          }
        }
      }
      else if (paramg.b() != com.ad4screen.sdk.service.modules.inapp.model.d.b) {}
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/inclusions/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
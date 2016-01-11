package com.ad4screen.sdk.service.modules.alarm;

import android.content.Context;
import com.ad4screen.sdk.common.persistence.b;
import org.json.JSONObject;

public class a
  extends b
{
  public a(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.service.modules.inapp.Alarm");
  }
  
  public com.ad4screen.sdk.service.modules.alarm.model.a a()
  {
    return (com.ad4screen.sdk.service.modules.alarm.model.a)b("com.ad4screen.Alarms", new com.ad4screen.sdk.service.modules.alarm.model.a());
  }
  
  public void a(com.ad4screen.sdk.service.modules.alarm.model.a parama)
  {
    a("com.ad4screen.Alarms", parama);
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    return false;
  }
  
  public int b()
  {
    return 4;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/alarm/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
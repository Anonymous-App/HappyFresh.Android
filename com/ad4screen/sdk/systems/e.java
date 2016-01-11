package com.ad4screen.sdk.systems;

import android.content.Context;
import com.ad4screen.sdk.common.persistence.b;
import org.json.JSONObject;

public class e
  extends b
{
  public e(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.common.Environment");
  }
  
  public String a(d.b paramb)
  {
    return a(paramb.toString() + ".url", null);
  }
  
  public void a(d.b paramb, int paramInt)
  {
    a(paramb.toString() + ".frequency", Integer.valueOf(paramInt));
  }
  
  public void a(d.b paramb, long paramLong)
  {
    a(paramb.toString() + ".lastSuccess", Long.valueOf(paramLong));
  }
  
  public void a(d.b paramb, String paramString)
  {
    a(paramb.toString() + ".url", paramString);
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    return false;
  }
  
  public int b()
  {
    return 4;
  }
  
  public int b(d.b paramb, int paramInt)
  {
    return a(paramb.toString() + ".frequency", paramInt);
  }
  
  public long b(d.b paramb)
  {
    return a(paramb.toString() + ".lastSuccess", 0L);
  }
  
  public void b(d.b paramb, long paramLong)
  {
    a(paramb.toString() + ".date", Long.valueOf(paramLong));
  }
  
  public int c(d.b paramb)
  {
    return b(paramb, 0);
  }
  
  public long c(d.b paramb, long paramLong)
  {
    return a(paramb.toString() + ".date", paramLong);
  }
  
  public void c(d.b paramb, int paramInt)
  {
    a(paramb.toString() + ".callCount", Integer.valueOf(paramInt));
  }
  
  public int d(d.b paramb)
  {
    return a(paramb.toString() + ".callCount", 0);
  }
  
  public long e(d.b paramb)
  {
    return c(paramb, 0L);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/systems/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
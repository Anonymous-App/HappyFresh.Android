package com.ad4screen.sdk.systems;

import android.content.Context;
import android.widget.FrameLayout.LayoutParams;
import com.ad4screen.sdk.common.persistence.b;
import org.json.JSONObject;

public class l
  extends b
{
  public l(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.systems.UI");
  }
  
  public FrameLayout.LayoutParams a()
  {
    return (FrameLayout.LayoutParams)b("overlayPosition", new FrameLayout.LayoutParams(-1, -2, 80));
  }
  
  public void a(FrameLayout.LayoutParams paramLayoutParams)
  {
    a("overlayPosition", paramLayoutParams);
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    return false;
  }
  
  public int b()
  {
    return 1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/systems/l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
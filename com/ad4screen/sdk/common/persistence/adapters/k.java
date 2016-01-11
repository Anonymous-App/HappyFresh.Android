package com.ad4screen.sdk.common.persistence.adapters;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.widget.FrameLayout.LayoutParams;
import com.ad4screen.sdk.common.persistence.adapters.model.a;
import org.json.JSONException;
import org.json.JSONObject;

public class k
  extends a<FrameLayout.LayoutParams>
{
  private final String a = "android.widget.FrameLayout.LayoutParameters";
  private final String b = "width";
  private final String c = "height";
  private final String d = "gravity";
  private final String e = "bottom";
  private final String f = "left";
  private final String g = "right";
  private final String h = "top";
  private final String i = "direction";
  private final String j = "end";
  private final String k = "start";
  
  @SuppressLint({"NewApi"})
  public FrameLayout.LayoutParams a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject("android.widget.FrameLayout.LayoutParameters");
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(paramString.getInt("width"), paramString.getInt("height"), paramString.getInt("gravity"));
    localLayoutParams.setMargins(paramString.getInt("left"), paramString.getInt("top"), paramString.getInt("right"), paramString.getInt("bottom"));
    if (Build.VERSION.SDK_INT >= 17)
    {
      localLayoutParams.setLayoutDirection(paramString.getInt("direction"));
      localLayoutParams.setMarginEnd(paramString.getInt("end"));
      localLayoutParams.setMarginStart(paramString.getInt("start"));
    }
    return localLayoutParams;
  }
  
  public String a()
  {
    return "android.widget.FrameLayout.LayoutParameters";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
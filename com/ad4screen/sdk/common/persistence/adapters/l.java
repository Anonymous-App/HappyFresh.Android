package com.ad4screen.sdk.common.persistence.adapters;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.widget.FrameLayout.LayoutParams;
import com.ad4screen.sdk.common.persistence.adapters.model.b;
import org.json.JSONException;
import org.json.JSONObject;

public class l
  extends b<FrameLayout.LayoutParams>
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
  public JSONObject a(FrameLayout.LayoutParams paramLayoutParams)
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("width", paramLayoutParams.width);
    localJSONObject2.put("height", paramLayoutParams.height);
    localJSONObject2.put("gravity", paramLayoutParams.gravity);
    localJSONObject2.put("bottom", paramLayoutParams.bottomMargin);
    localJSONObject2.put("left", paramLayoutParams.leftMargin);
    localJSONObject2.put("right", paramLayoutParams.rightMargin);
    localJSONObject2.put("top", paramLayoutParams.topMargin);
    if (Build.VERSION.SDK_INT >= 17)
    {
      localJSONObject2.put("direction", paramLayoutParams.getLayoutDirection());
      localJSONObject2.put("end", paramLayoutParams.getMarginEnd());
      localJSONObject2.put("start", paramLayoutParams.getMarginStart());
    }
    localJSONObject1.put("type", "android.widget.FrameLayout.LayoutParameters");
    localJSONObject1.put("android.widget.FrameLayout.LayoutParameters", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
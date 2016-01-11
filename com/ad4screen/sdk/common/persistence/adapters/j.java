package com.ad4screen.sdk.common.persistence.adapters;

import android.content.Intent;
import com.ad4screen.sdk.common.persistence.adapters.model.b;
import com.ad4screen.sdk.common.persistence.e;
import org.json.JSONException;
import org.json.JSONObject;

public class j
  extends b<Intent>
{
  private final String a = "android.content.Intent";
  private final String b = "uri";
  private final String c = "extras";
  
  public JSONObject a(Intent paramIntent)
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("uri", paramIntent.toUri(1));
    localJSONObject2.put("extras", new e().a(paramIntent.getExtras()));
    localJSONObject1.put("type", "android.content.Intent");
    localJSONObject1.put("android.content.Intent", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
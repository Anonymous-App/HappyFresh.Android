package com.ad4screen.sdk.model.displayformats;

import android.content.Intent;
import com.ad4screen.sdk.common.persistence.e;
import org.json.JSONException;
import org.json.JSONObject;

public class f
  extends d
{
  public Intent a;
  private final String b = "com.ad4screen.sdk.model.displayformats.LaunchActivity";
  private final String c = "intent";
  
  public f a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    if (!paramString.isNull("intent")) {
      this.a = ((Intent)this.m.a(paramString.getString("intent"), new Intent()));
    }
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.model.displayformats.LaunchActivity";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = super.toJSON();
    localJSONObject.put("type", "com.ad4screen.sdk.model.displayformats.LaunchActivity");
    if (this.a != null) {
      localJSONObject.put("intent", this.m.a(this.a));
    }
    return localJSONObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/model/displayformats/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
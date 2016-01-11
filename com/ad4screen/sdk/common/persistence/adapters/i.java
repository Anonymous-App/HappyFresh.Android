package com.ad4screen.sdk.common.persistence.adapters;

import android.content.Intent;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.persistence.adapters.model.a;
import java.net.URISyntaxException;
import org.json.JSONException;
import org.json.JSONObject;

public class i
  extends a<Intent>
{
  private final String a = "android.content.Intent";
  private final String b = "uri";
  private final String c = "extras";
  
  public Intent a(String paramString)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject(paramString).getJSONObject("android.content.Intent");
    try
    {
      paramString = Intent.parseUri(localJSONObject.getString("uri"), 1);
      Log.internal("IntentDeserializer|URI is invalid", localURISyntaxException1);
    }
    catch (URISyntaxException localURISyntaxException1)
    {
      try
      {
        paramString.putExtras(new c().a(localJSONObject.getString("extras")));
        return paramString;
      }
      catch (URISyntaxException localURISyntaxException2)
      {
        for (;;) {}
      }
      localURISyntaxException1 = localURISyntaxException1;
      paramString = null;
    }
    return paramString;
  }
  
  public String a()
  {
    return "android.content.Intent";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
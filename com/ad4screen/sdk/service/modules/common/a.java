package com.ad4screen.sdk.service.modules.common;

import android.os.Bundle;
import android.os.ResultReceiver;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.model.displayformats.d;
import org.json.JSONException;
import org.json.JSONObject;

public class a
{
  private ResultReceiver a;
  
  public void a(ResultReceiver paramResultReceiver)
  {
    this.a = paramResultReceiver;
  }
  
  public void a(d paramd)
  {
    if (this.a != null) {
      try
      {
        String str = new e().a(paramd).toString();
        if (str != null)
        {
          paramd = new Bundle();
          paramd.putString("com.ad4screen.sdk.A4SClient.inAppFormat", str);
          this.a.send(2, paramd);
          return;
        }
        Log.error("InApp|InApp #" + paramd.i + " format could not be serialized to JSON.");
        return;
      }
      catch (JSONException paramd)
      {
        Log.internal("InApp|Error while serializing InApp Format.", paramd);
      }
    }
  }
  
  public void a(String paramString1, String paramString2)
  {
    if (this.a != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("com.ad4screen.sdk.A4SClient.inAppId", paramString1);
      if (paramString2 != null) {
        localBundle.putString("com.ad4screen.sdk.A4SClient.activityInstance", paramString2);
      }
      this.a.send(1, localBundle);
    }
  }
  
  public boolean a(d paramd, int paramInt)
  {
    if (this.a != null) {}
    try
    {
      String str = new e().a(paramd).toString();
      if (str != null)
      {
        paramd = new Bundle();
        paramd.putString("com.ad4screen.sdk.A4SClient.inAppFormat", str);
        paramd.putInt("com.ad4screen.sdk.A4SClient.inAppTemplate", paramInt);
        this.a.send(3, paramd);
        return true;
      }
      Log.error("InApp|InApp #" + paramd.i + " format could not be serialized to JSON.");
    }
    catch (JSONException paramd)
    {
      for (;;)
      {
        Log.internal("InApp|Error while serializing InApp Format.", paramd);
      }
    }
    return false;
  }
  
  public boolean a(d paramd, String paramString)
  {
    if (this.a != null) {
      try
      {
        String str = new e().a(paramd).toString();
        if (str != null)
        {
          paramd = new Bundle();
          paramd.putString("com.ad4screen.sdk.A4SClient.inAppFormat", str);
          paramd.putString("com.ad4screen.sdk.A4SClient.activityInstance", paramString);
          this.a.send(0, paramd);
          return true;
        }
        Log.error("InApp|InApp #" + paramd.i + " format could not be serialized to JSON.");
        return false;
      }
      catch (JSONException paramd)
      {
        Log.internal("InApp|Error while serializing InApp Format.", paramd);
      }
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/common/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
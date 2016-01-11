package com.ad4screen.sdk.service.modules.member;

import com.ad4screen.sdk.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class e
{
  public String[] a;
  
  private String[] a(JSONArray paramJSONArray)
    throws JSONException
  {
    String[] arrayOfString = new String[paramJSONArray.length()];
    int i = 0;
    while (i < arrayOfString.length)
    {
      arrayOfString[i] = paramJSONArray.getString(i);
      i += 1;
    }
    return arrayOfString;
  }
  
  public void a(JSONObject paramJSONObject)
  {
    try
    {
      JSONObject localJSONObject = paramJSONObject.getJSONObject("listMembersResponse");
      if (localJSONObject.getInt("returnCode") != 0)
      {
        Log.internal("MemberManager|Members List WebService returned an error : " + paramJSONObject.getString("returnCode") + " : " + paramJSONObject.getString("returnLabel"));
        this.a = null;
        return;
      }
      this.a = a(localJSONObject.getJSONArray("members"));
      return;
    }
    catch (JSONException paramJSONObject)
    {
      Log.internal("MemberManager|Members JSON Parsing error!", paramJSONObject);
      this.a = null;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/member/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
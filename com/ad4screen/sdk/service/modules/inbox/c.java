package com.ad4screen.sdk.service.modules.inbox;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.inbox.Button;
import com.ad4screen.sdk.inbox.Message;
import com.ad4screen.sdk.inbox.Message.a;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c
{
  public Message[] a;
  
  private Message.a a(String paramString)
  {
    if (paramString.equals("text")) {
      return Message.a.b;
    }
    if (paramString.equals("system")) {
      return Message.a.a;
    }
    if (paramString.equals("richpush")) {
      return Message.a.c;
    }
    if (paramString.equals("web")) {
      return Message.a.d;
    }
    if (paramString.equals("event")) {
      return Message.a.e;
    }
    if (paramString.equals("url")) {
      return Message.a.f;
    }
    return Message.a.g;
  }
  
  private Message a(JSONArray paramJSONArray, Message paramMessage)
    throws JSONException
  {
    Button[] arrayOfButton = new Button[paramJSONArray.length()];
    int i = 0;
    if (i < paramJSONArray.length())
    {
      JSONObject localJSONObject = paramJSONArray.getJSONObject(i);
      Button localButton = new Button();
      localButton.a = localJSONObject.getString("id");
      localButton.b = localJSONObject.getString("title");
      localJSONObject = localJSONObject.getJSONObject("action");
      localButton.e = localJSONObject.getString("trk");
      if (!localJSONObject.isNull("type")) {}
      HashMap localHashMap;
      for (localButton.c = a(localJSONObject.getString("type"));; localButton.c = a(""))
      {
        if (!localJSONObject.isNull("data")) {
          localButton.d = localJSONObject.getString("data");
        }
        if ((localJSONObject.isNull("params")) || (localJSONObject.optJSONArray("params") != null)) {
          break label247;
        }
        localJSONObject = localJSONObject.getJSONObject("params");
        Iterator localIterator = localJSONObject.keys();
        localHashMap = new HashMap();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          localHashMap.put(str, localJSONObject.getString(str));
        }
      }
      label247:
      for (localButton.f = localHashMap;; localButton.f = new HashMap())
      {
        arrayOfButton[i] = localButton;
        i += 1;
        break;
      }
    }
    paramMessage.q = arrayOfButton;
    return paramMessage;
  }
  
  private Message a(JSONObject paramJSONObject, Message paramMessage)
    throws JSONException
  {
    paramMessage.n = true;
    try
    {
      Object localObject1 = Calendar.getInstance();
      ((Calendar)localObject1).setTimeZone(TimeZone.getTimeZone(paramJSONObject.getString("timezone")));
      Object localObject2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
      ((SimpleDateFormat)localObject2).setCalendar((Calendar)localObject1);
      paramMessage.c = ((SimpleDateFormat)localObject2).parse(paramJSONObject.getString("date"));
      paramMessage.f = paramJSONObject.getString("from");
      paramMessage.b = paramJSONObject.getString("title");
      paramMessage.i = paramJSONObject.getString("text");
      paramMessage.g = paramJSONObject.getString("category");
      paramMessage.p = paramJSONObject.getString("icon");
      paramMessage.h = paramJSONObject.getString("trk");
      if (!paramJSONObject.isNull("params"))
      {
        localObject1 = paramJSONObject.getJSONObject("params");
        localObject2 = ((JSONObject)localObject1).keys();
        localHashMap = new HashMap();
        while (((Iterator)localObject2).hasNext())
        {
          String str = (String)((Iterator)localObject2).next();
          localHashMap.put(str, ((JSONObject)localObject1).getString(str));
        }
      }
    }
    catch (ParseException localParseException)
    {
      for (;;)
      {
        Log.internal("Date is incorrect for this message", localParseException);
        localParseException.printStackTrace();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      HashMap localHashMap;
      for (;;)
      {
        Log.internal("Date or Timezone is null or not found for this message", localNullPointerException);
        localNullPointerException.printStackTrace();
      }
      paramMessage.r = localHashMap;
    }
    for (;;)
    {
      Message localMessage = paramMessage;
      if (!paramJSONObject.isNull("content")) {
        localMessage = c(paramJSONObject.getJSONObject("content"), paramMessage);
      }
      paramMessage = localMessage;
      if (!paramJSONObject.isNull("action")) {
        paramMessage = b(paramJSONObject.getJSONObject("action"), localMessage);
      }
      return paramMessage;
      paramMessage.r = new HashMap();
    }
  }
  
  private void a(JSONArray paramJSONArray)
    throws JSONException
  {
    int i = 0;
    int j = 0;
    while (i < paramJSONArray.length())
    {
      j += paramJSONArray.getJSONObject(i).getJSONArray("messages").length();
      i += 1;
    }
    this.a = new Message[j];
    i = 0;
    j = 0;
    while (i < paramJSONArray.length())
    {
      JSONArray localJSONArray = paramJSONArray.getJSONObject(i).getJSONArray("messages");
      int k = 0;
      while (k < localJSONArray.length())
      {
        this.a[j] = b(localJSONArray.getJSONObject(k));
        k += 1;
        j += 1;
      }
      i += 1;
    }
  }
  
  private Message b(JSONObject paramJSONObject)
    throws JSONException
  {
    Message localMessage = new Message();
    localMessage.a = paramJSONObject.getString("id");
    JSONObject localJSONObject = paramJSONObject.getJSONObject("status");
    localMessage.l = localJSONObject.getBoolean("read");
    if (localJSONObject.has("expired")) {
      localMessage.k = localJSONObject.getBoolean("expired");
    }
    if (localJSONObject.has("archived")) {
      localMessage.m = localJSONObject.getBoolean("archived");
    }
    if (!paramJSONObject.isNull("details")) {
      return a(paramJSONObject.getJSONObject("details"), localMessage);
    }
    localMessage.f = "";
    localMessage.b = "";
    localMessage.i = "";
    localMessage.g = "";
    localMessage.p = "";
    localMessage.h = "";
    localMessage.r = new HashMap();
    localMessage.j = Message.a.b;
    localMessage.d = "";
    localMessage.c = new Date();
    localMessage.e = "";
    localMessage.q = new Button[0];
    return localMessage;
  }
  
  private Message b(JSONObject paramJSONObject, Message paramMessage)
    throws JSONException
  {
    paramMessage.d = "";
    paramMessage.q = new Button[0];
    paramMessage.j = a(paramJSONObject.getString("type"));
    paramMessage.e = paramJSONObject.getString("data");
    return paramMessage;
  }
  
  private void b(JSONArray paramJSONArray)
    throws JSONException
  {
    int i = 0;
    this.a = new Message[paramJSONArray.length()];
    int j = 0;
    while (i < paramJSONArray.length())
    {
      this.a[j] = b(paramJSONArray.getJSONObject(i));
      j += 1;
      i += 1;
    }
  }
  
  private Message c(JSONObject paramJSONObject, Message paramMessage)
    throws JSONException
  {
    paramMessage.j = a(paramJSONObject.getString("type"));
    paramMessage.d = paramJSONObject.getString("body");
    if (!paramJSONObject.isNull("buttons")) {
      return a(paramJSONObject.getJSONArray("buttons"), paramMessage);
    }
    paramMessage.q = new Button[0];
    return paramMessage;
  }
  
  public void a(JSONObject paramJSONObject)
  {
    try
    {
      if (!paramJSONObject.isNull("Response"))
      {
        paramJSONObject = paramJSONObject.getJSONObject("Response");
        Log.error("Inbox|Server returned an error while querying Inbox : ", new Exception(paramJSONObject.getString("returnCode") + " - " + paramJSONObject.getString("returnLabel")));
        return;
      }
      if (paramJSONObject.isNull("inboxMessageList"))
      {
        b(paramJSONObject.getJSONArray("inboxMessageDetail"));
        return;
      }
    }
    catch (JSONException paramJSONObject)
    {
      Log.internal("Inbox|Messages JSON Parsing error!", paramJSONObject);
      this.a = null;
      return;
    }
    a(paramJSONObject.getJSONArray("inboxMessageList"));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inbox/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
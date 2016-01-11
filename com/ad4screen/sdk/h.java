package com.ad4screen.sdk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import com.ad4screen.sdk.client.b;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.inbox.Button;
import com.ad4screen.sdk.inbox.Message.a;
import com.ad4screen.sdk.inbox.a;
import com.ad4screen.sdk.service.modules.inapp.e;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

class h
{
  protected static Inbox a(Context paramContext, a parama)
  {
    Message[] arrayOfMessage = new Message[parama.a.length];
    int i = 0;
    while (i < parama.a.length)
    {
      arrayOfMessage[i] = a(parama.a[i]);
      i += 1;
    }
    return new Inbox(paramContext, arrayOfMessage);
  }
  
  protected static Message a(com.ad4screen.sdk.inbox.Message paramMessage)
  {
    Message.Button[] arrayOfButton = new Message.Button[paramMessage.q.length];
    int i = 0;
    while (i < paramMessage.q.length)
    {
      arrayOfButton[i] = new Message.Button(paramMessage.q[i].a, paramMessage.q[i].b);
      i += 1;
    }
    return new Message(paramMessage.a, paramMessage.b, paramMessage.c, paramMessage.d, paramMessage.f, paramMessage.g, paramMessage.i, Message.MessageContentType.valueOf(paramMessage.j.name()), paramMessage.k, paramMessage.l, paramMessage.m, false, paramMessage.p, arrayOfButton, paramMessage.n);
  }
  
  protected static void a(Context paramContext, String paramString, HashMap<String, String> paramHashMap)
  {
    if (paramString == null) {
      return;
    }
    paramString = new Intent(paramString);
    paramString.addCategory("com.ad4screen.sdk.intent.category.INBOX_NOTIFICATIONS");
    e.a(paramString, paramHashMap);
    i.a(paramContext, paramString);
  }
  
  protected static void a(Handler paramHandler, final Context paramContext, final b paramb, Message.a parama, final String paramString1, String paramString2)
  {
    if (parama == Message.a.e) {
      try
      {
        paramHandler = new JSONObject(paramString1);
        if (!paramHandler.isNull("value"))
        {
          A4S.get(paramContext).trackEvent(paramHandler.getInt("id"), new String[] { paramHandler.getString("value") });
          return;
        }
        A4S.get(paramContext).trackEvent(paramHandler.getInt("id"), new String[0]);
        return;
      }
      catch (JSONException paramHandler)
      {
        Log.internal("Inbox|Invalid events arguments", paramHandler);
        return;
      }
    }
    if (parama == Message.a.c)
    {
      paramHandler = new Bundle();
      try
      {
        paramContext = new JSONObject(paramString1);
        parama = paramContext.keys();
        while (parama.hasNext())
        {
          paramString1 = (String)parama.next();
          paramHandler.putString(paramString1, paramContext.getString(paramString1));
          continue;
          paramb.a(paramHandler);
        }
      }
      catch (JSONException paramContext)
      {
        Log.internal("InboxUtil|Error while serializing Action to JSON", paramContext);
      }
      for (;;)
      {
        return;
        paramHandler.putString("a4stitle", paramString2);
        paramHandler.putString("a4scontent", paramString2);
      }
    }
    paramHandler.post(new Runnable()
    {
      public void run()
      {
        if (this.a == Message.a.a)
        {
          localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString1));
          localIntent.setFlags(268435456);
        }
        while (this.a != Message.a.g) {
          try
          {
            Intent localIntent;
            paramContext.startActivity(localIntent);
            return;
          }
          catch (Exception localException)
          {
            Log.error("Url Scheme seems to be invalid", localException);
            return;
          }
        }
        paramb.d();
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
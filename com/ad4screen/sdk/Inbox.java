package com.ad4screen.sdk;

import android.content.Context;
import com.ad4screen.sdk.common.annotations.API;

@API
public class Inbox
{
  protected Message[] a;
  private Context b;
  
  protected Inbox(Context paramContext, Message[] paramArrayOfMessage)
  {
    this.a = paramArrayOfMessage;
    this.b = paramContext;
  }
  
  public int countMessages()
  {
    return this.a.length;
  }
  
  public int countMessagesOfCategory(String paramString)
  {
    int i = 0;
    int k;
    for (int j = 0; i < this.a.length; j = k)
    {
      k = j;
      if (this.a[i].getCategory().equals(paramString)) {
        k = j + 1;
      }
      i += 1;
    }
    return j;
  }
  
  public int countReadMessages()
  {
    int i = 0;
    int k;
    for (int j = 0; i < this.a.length; j = k)
    {
      k = j;
      if (this.a[i].isRead()) {
        k = j + 1;
      }
      i += 1;
    }
    return j;
  }
  
  public int countUnReadMessages()
  {
    int i = 0;
    int k;
    for (int j = 0; i < this.a.length; j = k)
    {
      k = j;
      if (!this.a[i].isRead()) {
        k = j + 1;
      }
      i += 1;
    }
    return j;
  }
  
  public void getMessage(final int paramInt, final A4S.MessageCallback paramMessageCallback)
  {
    if (this.a[paramInt].isDownloaded())
    {
      paramMessageCallback.onResult(this.a[paramInt], paramInt);
      return;
    }
    A4S.get(this.b).a(paramInt, new A4S.Callback()
    {
      public void a(Inbox paramAnonymousInbox)
      {
        Inbox.this.a = paramAnonymousInbox.a;
        paramMessageCallback.onResult(Inbox.this.a[paramInt], paramInt);
      }
      
      public void onError(int paramAnonymousInt, String paramAnonymousString)
      {
        paramMessageCallback.onError(paramAnonymousInt, paramAnonymousString);
      }
    }, false);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/Inbox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
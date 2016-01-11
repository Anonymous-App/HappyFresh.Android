package com.ad4screen.sdk.service.modules.inbox;

import android.os.RemoteException;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.g;
import com.ad4screen.sdk.inbox.Message;
import com.ad4screen.sdk.systems.f;

public class b
{
  private final A4SService.a a;
  private g b;
  private final a.c c = new a.c()
  {
    public void a()
    {
      try
      {
        b.a(b.this).a();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        Log.internal("Inbox|Error in callback ! Is InboxCallback defined correctly?", localRemoteException);
      }
    }
    
    public void a(Message[] paramAnonymousArrayOfMessage)
    {
      try
      {
        b.a(b.this).a(paramAnonymousArrayOfMessage);
        return;
      }
      catch (RemoteException paramAnonymousArrayOfMessage)
      {
        Log.internal("Inbox|Error in callback ! Is InboxCallback defined correctly?", paramAnonymousArrayOfMessage);
      }
    }
  };
  
  public b(A4SService.a parama)
  {
    this.a = parama;
    f.a().a(a.b.class, this.c);
    f.a().a(a.a.class, this.c);
  }
  
  public void a(g paramg)
  {
    this.b = paramg;
    new d(this.a.a()).run();
  }
  
  public void a(Message[] paramArrayOfMessage, g paramg)
  {
    new e(paramArrayOfMessage, this.a.a(), paramg).run();
  }
  
  public void a(String[] paramArrayOfString, g paramg)
  {
    this.b = paramg;
    new d(paramArrayOfString, this.a.a()).run();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inbox/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
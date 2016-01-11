package com.ad4screen.sdk.service.modules.inbox;

import com.ad4screen.sdk.inbox.Message;
import com.ad4screen.sdk.systems.f.a;

public final class a
{
  public static final class a
    implements f.a<a.c>
  {
    public void a(a.c paramc)
    {
      paramc.a();
    }
  }
  
  public static final class b
    implements f.a<a.c>
  {
    Message[] a;
    
    public b(Message[] paramArrayOfMessage)
    {
      this.a = paramArrayOfMessage;
    }
    
    public void a(a.c paramc)
    {
      paramc.a(this.a);
    }
  }
  
  public static abstract interface c
  {
    public abstract void a();
    
    public abstract void a(Message[] paramArrayOfMessage);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inbox/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.ad4screen.sdk.service.modules.push;

import com.ad4screen.sdk.systems.f.a;

public final class d
{
  public static abstract interface a
  {
    public abstract void a();
    
    public abstract void b();
  }
  
  public static abstract interface b
  {
    public abstract void a();
  }
  
  public static final class c
    implements f.a<d.a>
  {
    public void a(d.a parama)
    {
      parama.b();
    }
  }
  
  public static final class d
    implements f.a<d.a>
  {
    public void a(d.a parama)
    {
      parama.a();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/push/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
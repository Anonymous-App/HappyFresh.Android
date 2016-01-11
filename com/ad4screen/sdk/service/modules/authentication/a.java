package com.ad4screen.sdk.service.modules.authentication;

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
    private com.ad4screen.sdk.service.modules.authentication.model.a a;
    private boolean b;
    
    public b(com.ad4screen.sdk.service.modules.authentication.model.a parama, boolean paramBoolean)
    {
      this.a = parama;
      this.b = paramBoolean;
    }
    
    public void a(a.c paramc)
    {
      paramc.a(this.a, this.b);
    }
  }
  
  public static abstract interface c
  {
    public abstract void a();
    
    public abstract void a(com.ad4screen.sdk.service.modules.authentication.model.a parama, boolean paramBoolean);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/authentication/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
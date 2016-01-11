package com.ad4screen.sdk.common.tasks;

import com.ad4screen.sdk.systems.f.a;

public final class d
{
  public static final class a
    implements f.a<d.c>
  {
    c a;
    String b;
    
    public a(c paramc, String paramString)
    {
      this.a = paramc;
      this.b = paramString;
    }
    
    public void a(d.c paramc)
    {
      paramc.b(this.a, this.b);
    }
  }
  
  public static final class b
    implements f.a<d.c>
  {
    c a;
    String b;
    
    public b(c paramc, String paramString)
    {
      this.a = paramc;
      this.b = paramString;
    }
    
    public void a(d.c paramc)
    {
      paramc.a(this.a, this.b);
    }
  }
  
  public static abstract interface c
  {
    public abstract void a(c paramc, String paramString);
    
    public abstract void b(c paramc, String paramString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/tasks/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
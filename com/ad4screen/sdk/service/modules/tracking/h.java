package com.ad4screen.sdk.service.modules.tracking;

import com.ad4screen.sdk.service.modules.tracking.model.c;
import com.ad4screen.sdk.systems.f.a;

public final class h
{
  public static final class a
    implements f.a<h.g>
  {
    c a;
    
    public a(c paramc)
    {
      this.a = paramc;
    }
    
    public void a(h.g paramg)
    {
      paramg.a(this.a);
    }
  }
  
  public static final class b
    implements f.a<h.g>
  {
    String a;
    
    public b(String paramString)
    {
      this.a = paramString;
    }
    
    public void a(h.g paramg)
    {
      paramg.a(this.a);
    }
  }
  
  public static abstract interface c
  {
    public abstract void a(long paramLong, String[] paramArrayOfString);
  }
  
  public static abstract interface d
  {
    public abstract void a();
  }
  
  public static final class e
    implements f.a<h.c>
  {
    private long a;
    private String[] b;
    
    public e(long paramLong, String[] paramArrayOfString)
    {
      this.a = paramLong;
      this.b = paramArrayOfString;
    }
    
    public void a(h.c paramc)
    {
      paramc.a(this.a, this.b);
    }
  }
  
  public static final class f
    implements f.a<h.d>
  {
    public void a(h.d paramd)
    {
      paramd.a();
    }
  }
  
  public static abstract interface g
  {
    public abstract void a(c paramc);
    
    public abstract void a(String paramString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
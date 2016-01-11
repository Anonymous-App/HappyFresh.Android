package com.ad4screen.sdk.service.modules.inapp;

import com.ad4screen.sdk.service.modules.inapp.model.e;
import com.ad4screen.sdk.systems.f.a;
import java.util.HashMap;

public final class c
{
  static final class a
    implements f.a<c.h>
  {
    String a;
    
    public a(String paramString)
    {
      this.a = paramString;
    }
    
    public void a(c.h paramh)
    {
      paramh.a(this.a);
    }
  }
  
  static final class b
    implements f.a<c.i>
  {
    public void a(c.i parami)
    {
      parami.a();
    }
  }
  
  public static final class c
    implements f.a<c.j>
  {
    private String a;
    private String b;
    private int c;
    private String d;
    private HashMap<String, String> e;
    
    public c(String paramString1, int paramInt, String paramString2, String paramString3, HashMap<String, String> paramHashMap)
    {
      this.a = paramString1;
      this.b = paramString3;
      this.d = paramString2;
      this.e = paramHashMap;
      this.c = paramInt;
    }
    
    public void a(c.j paramj)
    {
      paramj.a(this.a, this.c, this.d, this.b, this.e);
    }
  }
  
  public static final class d
    implements f.a<c.k>
  {
    private String a;
    private boolean b;
    private int c;
    private String d;
    
    public d(String paramString1, int paramInt, String paramString2, boolean paramBoolean)
    {
      this.a = paramString1;
      this.b = paramBoolean;
      this.d = paramString2;
      this.c = paramInt;
    }
    
    public void a(c.k paramk)
    {
      paramk.a(this.a, this.c, this.d, this.b);
    }
  }
  
  public static final class e
    implements f.a<c.l>
  {
    private e a;
    private boolean b;
    
    public e(e parame, boolean paramBoolean)
    {
      this.a = parame;
      this.b = paramBoolean;
    }
    
    public void a(c.l paraml)
    {
      paraml.a(this.a, this.b);
    }
  }
  
  public static final class f
    implements f.a<c.m>
  {
    private String a;
    private int b;
    private String c;
    private HashMap<String, String> d;
    
    public f(String paramString1, int paramInt, String paramString2, HashMap<String, String> paramHashMap)
    {
      this.a = paramString1;
      this.c = paramString2;
      this.d = paramHashMap;
      this.b = paramInt;
    }
    
    public void a(c.m paramm)
    {
      paramm.a(this.a, this.b, this.c, this.d);
    }
  }
  
  public static final class g
    implements f.a<c.n>
  {
    private String a;
    private int b;
    private String c;
    
    public g(String paramString1, int paramInt, String paramString2)
    {
      this.a = paramString1;
      this.c = paramString2;
      this.b = paramInt;
    }
    
    public void a(c.n paramn)
    {
      paramn.a(this.a, this.b, this.c);
    }
  }
  
  static abstract interface h
  {
    public abstract void a(String paramString);
  }
  
  static abstract interface i
  {
    public abstract void a();
  }
  
  public static abstract interface j
  {
    public abstract void a(String paramString1, int paramInt, String paramString2, String paramString3, HashMap<String, String> paramHashMap);
  }
  
  public static abstract interface k
  {
    public abstract void a(String paramString1, int paramInt, String paramString2, boolean paramBoolean);
  }
  
  public static abstract interface l
  {
    public abstract void a(e parame, boolean paramBoolean);
  }
  
  public static abstract interface m
  {
    public abstract void a(String paramString1, int paramInt, String paramString2, HashMap<String, String> paramHashMap);
  }
  
  public static abstract interface n
  {
    public abstract void a(String paramString1, int paramInt, String paramString2);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
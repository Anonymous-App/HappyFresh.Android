package com.ad4screen.sdk.service.modules.inapp;

import android.os.Handler;
import android.os.HandlerThread;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.compatibility.k.h;
import java.util.ArrayList;

public final class f
{
  private HandlerThread a;
  private Handler b;
  private boolean c;
  private boolean d;
  private ArrayList<a> e = new ArrayList();
  private final Runnable f = new Runnable()
  {
    public void run()
    {
      for (;;)
      {
        synchronized (f.this)
        {
          if (f.a(f.this) == null) {
            return;
          }
          if (!f.b(f.this))
          {
            com.ad4screen.sdk.systems.f.a().a(new c.b());
            f.a(f.this).postDelayed(f.c(f.this), 10000L);
            return;
          }
        }
        f.a(f.this, false);
        f.a(f.this).postDelayed(f.c(f.this), 0L);
      }
    }
  };
  
  public void a()
  {
    try
    {
      if (this.b != null)
      {
        int i = 0;
        while (i < this.e.size())
        {
          this.b.removeCallbacks((Runnable)this.e.get(i));
          i += 1;
        }
        this.e.clear();
        this.b.removeCallbacks(this.f);
        this.b = null;
      }
      if (this.a != null)
      {
        k.h.a(this.a);
        this.a = null;
      }
      return;
    }
    finally {}
  }
  
  public void a(long paramLong)
  {
    try
    {
      c();
      b(paramLong);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public void a(com.ad4screen.sdk.model.displayformats.d paramd)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 38	com/ad4screen/sdk/service/modules/inapp/f:b	Landroid/os/Handler;
    //   6: ifnonnull +11 -> 17
    //   9: ldc 77
    //   11: invokestatic 83	com/ad4screen/sdk/Log:warn	(Ljava/lang/String;)V
    //   14: aload_0
    //   15: monitorexit
    //   16: return
    //   17: aload_1
    //   18: instanceof 85
    //   21: ifeq -7 -> 14
    //   24: aload_1
    //   25: checkcast 85	com/ad4screen/sdk/model/displayformats/a
    //   28: astore_1
    //   29: aload_1
    //   30: getfield 88	com/ad4screen/sdk/model/displayformats/a:a	Ljava/lang/Integer;
    //   33: ifnull -19 -> 14
    //   36: aload_1
    //   37: getfield 88	com/ad4screen/sdk/model/displayformats/a:a	Ljava/lang/Integer;
    //   40: invokevirtual 93	java/lang/Integer:intValue	()I
    //   43: istore_2
    //   44: new 8	com/ad4screen/sdk/service/modules/inapp/f$a
    //   47: dup
    //   48: aload_0
    //   49: aload_1
    //   50: getfield 97	com/ad4screen/sdk/model/displayformats/a:i	Ljava/lang/String;
    //   53: invokespecial 100	com/ad4screen/sdk/service/modules/inapp/f$a:<init>	(Lcom/ad4screen/sdk/service/modules/inapp/f;Ljava/lang/String;)V
    //   56: astore_3
    //   57: aload_0
    //   58: getfield 38	com/ad4screen/sdk/service/modules/inapp/f:b	Landroid/os/Handler;
    //   61: aload_3
    //   62: iload_2
    //   63: sipush 1000
    //   66: imul
    //   67: i2l
    //   68: invokevirtual 104	android/os/Handler:postDelayed	(Ljava/lang/Runnable;J)Z
    //   71: pop
    //   72: aload_0
    //   73: getfield 29	com/ad4screen/sdk/service/modules/inapp/f:e	Ljava/util/ArrayList;
    //   76: aload_3
    //   77: invokevirtual 108	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   80: pop
    //   81: new 110	java/lang/StringBuilder
    //   84: dup
    //   85: invokespecial 111	java/lang/StringBuilder:<init>	()V
    //   88: ldc 113
    //   90: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: iload_2
    //   94: invokevirtual 120	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   97: ldc 122
    //   99: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: aload_1
    //   103: getfield 97	com/ad4screen/sdk/model/displayformats/a:i	Ljava/lang/String;
    //   106: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   112: invokestatic 129	com/ad4screen/sdk/Log:debug	(Ljava/lang/String;)V
    //   115: goto -101 -> 14
    //   118: astore_1
    //   119: aload_0
    //   120: monitorexit
    //   121: aload_1
    //   122: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	123	0	this	f
    //   0	123	1	paramd	com.ad4screen.sdk.model.displayformats.d
    //   43	51	2	i	int
    //   56	21	3	locala	a
    // Exception table:
    //   from	to	target	type
    //   2	14	118	finally
    //   17	115	118	finally
  }
  
  /* Error */
  public void a(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 38	com/ad4screen/sdk/service/modules/inapp/f:b	Landroid/os/Handler;
    //   6: astore_3
    //   7: aload_3
    //   8: ifnonnull +6 -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: iconst_0
    //   15: istore_2
    //   16: iload_2
    //   17: aload_0
    //   18: getfield 29	com/ad4screen/sdk/service/modules/inapp/f:e	Ljava/util/ArrayList;
    //   21: invokevirtual 47	java/util/ArrayList:size	()I
    //   24: if_icmpge -13 -> 11
    //   27: aload_0
    //   28: getfield 29	com/ad4screen/sdk/service/modules/inapp/f:e	Ljava/util/ArrayList;
    //   31: iload_2
    //   32: invokevirtual 51	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   35: checkcast 8	com/ad4screen/sdk/service/modules/inapp/f$a
    //   38: getfield 131	com/ad4screen/sdk/service/modules/inapp/f$a:a	Ljava/lang/String;
    //   41: aload_1
    //   42: invokevirtual 136	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   45: ifeq +21 -> 66
    //   48: aload_0
    //   49: getfield 38	com/ad4screen/sdk/service/modules/inapp/f:b	Landroid/os/Handler;
    //   52: aload_0
    //   53: getfield 29	com/ad4screen/sdk/service/modules/inapp/f:e	Ljava/util/ArrayList;
    //   56: iload_2
    //   57: invokevirtual 51	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   60: checkcast 53	java/lang/Runnable
    //   63: invokevirtual 59	android/os/Handler:removeCallbacks	(Ljava/lang/Runnable;)V
    //   66: iload_2
    //   67: iconst_1
    //   68: iadd
    //   69: istore_2
    //   70: goto -54 -> 16
    //   73: astore_1
    //   74: aload_0
    //   75: monitorexit
    //   76: aload_1
    //   77: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	78	0	this	f
    //   0	78	1	paramString	String
    //   15	55	2	i	int
    //   6	2	3	localHandler	Handler
    // Exception table:
    //   from	to	target	type
    //   2	7	73	finally
    //   16	66	73	finally
  }
  
  public void b()
  {
    try
    {
      if (this.b == null)
      {
        this.a = new HandlerThread("InAppNotification.worker");
        this.a.start();
        this.b = new Handler(this.a.getLooper());
        this.c = false;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void b(long paramLong)
  {
    for (;;)
    {
      try
      {
        if (this.b == null)
        {
          Log.warn("InApp|Cannot start rules checking while worker is stopped");
          return;
        }
        if (!this.c)
        {
          this.c = true;
          this.b.postDelayed(this.f, paramLong);
          Log.debug("InApp|Start checking rules in " + paramLong / 1000L + "s");
        }
        else
        {
          this.d = true;
        }
      }
      finally {}
    }
  }
  
  /* Error */
  public void c()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 38	com/ad4screen/sdk/service/modules/inapp/f:b	Landroid/os/Handler;
    //   6: astore_1
    //   7: aload_1
    //   8: ifnonnull +6 -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: getfield 154	com/ad4screen/sdk/service/modules/inapp/f:c	Z
    //   18: ifeq +13 -> 31
    //   21: ldc -89
    //   23: invokestatic 129	com/ad4screen/sdk/Log:debug	(Ljava/lang/String;)V
    //   26: aload_0
    //   27: iconst_0
    //   28: putfield 154	com/ad4screen/sdk/service/modules/inapp/f:c	Z
    //   31: aload_0
    //   32: getfield 38	com/ad4screen/sdk/service/modules/inapp/f:b	Landroid/os/Handler;
    //   35: aload_0
    //   36: getfield 34	com/ad4screen/sdk/service/modules/inapp/f:f	Ljava/lang/Runnable;
    //   39: invokevirtual 59	android/os/Handler:removeCallbacks	(Ljava/lang/Runnable;)V
    //   42: goto -31 -> 11
    //   45: astore_1
    //   46: aload_0
    //   47: monitorexit
    //   48: aload_1
    //   49: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	50	0	this	f
    //   6	2	1	localHandler	Handler
    //   45	4	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	7	45	finally
    //   14	31	45	finally
    //   31	42	45	finally
  }
  
  public final class a
    implements Runnable
  {
    public String a;
    
    public a(String paramString)
    {
      this.a = paramString;
    }
    
    public void run()
    {
      com.ad4screen.sdk.systems.f.a().a(new c.a(this.a));
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
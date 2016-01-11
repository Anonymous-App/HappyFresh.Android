package com.appsee;

import java.util.List;

class fc
{
  public static fc i()
  {
    try
    {
      if (b == null) {
        b = new fc();
      }
      fc localfc = b;
      return localfc;
    }
    finally {}
  }
  
  public void F()
  {
    mc.l(AppseeBackgroundUploader.i("y;E<Y3Q=\037d\0045S=]"), 1);
    if (!this.m)
    {
      mc.l(AppseeBackgroundUploader.i("d \n\"L?[zK~T*H3I"), 1);
      return;
    }
    synchronized (this.k)
    {
      this.k.clear();
      this.G.i();
      return;
    }
  }
  
  public void a()
  {
    if (this.m)
    {
      mc.l(AppseeBackgroundUploader.i("z#X*K(PzJ*S~\025=U2^"), 1);
      return;
    }
    this.m = true;
    l();
    i();
  }
  
  /* Error */
  public void k()
    throws org.json.JSONException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 25	com/appsee/fc:m	Z
    //   4: ifeq +13 -> 17
    //   7: ldc_w 343
    //   10: invokestatic 79	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   13: invokestatic 345	com/appsee/vd:i	(Ljava/lang/String;)V
    //   16: return
    //   17: invokestatic 131	com/appsee/ge:i	()Lcom/appsee/ge;
    //   20: invokevirtual 347	com/appsee/ge:k	()Ljava/lang/String;
    //   23: astore_2
    //   24: aload_0
    //   25: getfield 38	com/appsee/fc:G	Lcom/appsee/ib;
    //   28: astore_1
    //   29: aload_1
    //   30: monitorenter
    //   31: aload_0
    //   32: getfield 38	com/appsee/fc:G	Lcom/appsee/ib;
    //   35: aload_2
    //   36: invokevirtual 350	com/appsee/ib:l	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   39: astore_2
    //   40: invokestatic 354	com/appsee/gb:k	()V
    //   43: new 71	java/lang/StringBuilder
    //   46: dup
    //   47: invokespecial 72	java/lang/StringBuilder:<init>	()V
    //   50: iconst_0
    //   51: ldc_w 356
    //   54: invokestatic 79	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   57: invokevirtual 83	java/lang/StringBuilder:insert	(ILjava/lang/String;)Ljava/lang/StringBuilder;
    //   60: aload_2
    //   61: invokevirtual 357	org/json/JSONObject:toString	()Ljava/lang/String;
    //   64: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: invokevirtual 91	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   70: iconst_1
    //   71: invokestatic 97	com/appsee/mc:l	(Ljava/lang/String;I)V
    //   74: invokestatic 131	com/appsee/ge:i	()Lcom/appsee/ge;
    //   77: aload_2
    //   78: invokevirtual 360	com/appsee/ge:i	(Lorg/json/JSONObject;)V
    //   81: aload_1
    //   82: monitorexit
    //   83: invokestatic 365	com/appsee/xc:i	()Lcom/appsee/xc;
    //   86: invokevirtual 368	com/appsee/xc:M	()V
    //   89: return
    //   90: astore_1
    //   91: aload_1
    //   92: ldc_w 370
    //   95: invokestatic 79	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   98: iconst_0
    //   99: invokestatic 196	com/appsee/vd:i	(Ljava/lang/Throwable;Ljava/lang/String;Z)V
    //   102: invokestatic 365	com/appsee/xc:i	()Lcom/appsee/xc;
    //   105: invokevirtual 371	com/appsee/xc:k	()V
    //   108: invokestatic 373	com/appsee/gb:F	()V
    //   111: return
    //   112: astore_2
    //   113: aload_1
    //   114: monitorexit
    //   115: aload_2
    //   116: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	117	0	this	fc
    //   90	24	1	localException	Exception
    //   23	55	2	localObject1	Object
    //   112	4	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   24	31	90	java/lang/Exception
    //   83	89	90	java/lang/Exception
    //   115	117	90	java/lang/Exception
    //   31	83	112	finally
    //   113	115	112	finally
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/fc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
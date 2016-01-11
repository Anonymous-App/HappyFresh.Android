package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

class zzcu
  extends zzct
{
  private static final Object zzaND = new Object();
  private static zzcu zzaNO;
  private boolean connected = true;
  private Handler handler;
  private Context zzaNE;
  private zzau zzaNF;
  private volatile zzas zzaNG;
  private int zzaNH = 1800000;
  private boolean zzaNI = true;
  private boolean zzaNJ = false;
  private boolean zzaNK = true;
  private zzav zzaNL = new zzav()
  {
    public void zzan(boolean paramAnonymousBoolean)
    {
      zzcu.this.zzd(paramAnonymousBoolean, zzcu.zza(zzcu.this));
    }
  };
  private zzbl zzaNM;
  private boolean zzaNN = false;
  
  private void zzzA()
  {
    this.zzaNM = new zzbl(this);
    this.zzaNM.zzaI(this.zzaNE);
  }
  
  private void zzzB()
  {
    this.handler = new Handler(this.zzaNE.getMainLooper(), new Handler.Callback()
    {
      public boolean handleMessage(Message paramAnonymousMessage)
      {
        if ((1 == paramAnonymousMessage.what) && (zzcu.zzzD().equals(paramAnonymousMessage.obj)))
        {
          zzcu.this.dispatch();
          if ((zzcu.zzb(zzcu.this) > 0) && (!zzcu.zzc(zzcu.this))) {
            zzcu.zzd(zzcu.this).sendMessageDelayed(zzcu.zzd(zzcu.this).obtainMessage(1, zzcu.zzzD()), zzcu.zzb(zzcu.this));
          }
        }
        return true;
      }
    });
    if (this.zzaNH > 0) {
      this.handler.sendMessageDelayed(this.handler.obtainMessage(1, zzaND), this.zzaNH);
    }
  }
  
  public static zzcu zzzz()
  {
    if (zzaNO == null) {
      zzaNO = new zzcu();
    }
    return zzaNO;
  }
  
  /* Error */
  public void dispatch()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 52	com/google/android/gms/tagmanager/zzcu:zzaNJ	Z
    //   6: ifne +16 -> 22
    //   9: ldc 121
    //   11: invokestatic 127	com/google/android/gms/tagmanager/zzbg:zzaB	(Ljava/lang/String;)V
    //   14: aload_0
    //   15: iconst_1
    //   16: putfield 50	com/google/android/gms/tagmanager/zzcu:zzaNI	Z
    //   19: aload_0
    //   20: monitorexit
    //   21: return
    //   22: aload_0
    //   23: getfield 129	com/google/android/gms/tagmanager/zzcu:zzaNG	Lcom/google/android/gms/tagmanager/zzas;
    //   26: new 10	com/google/android/gms/tagmanager/zzcu$3
    //   29: dup
    //   30: aload_0
    //   31: invokespecial 130	com/google/android/gms/tagmanager/zzcu$3:<init>	(Lcom/google/android/gms/tagmanager/zzcu;)V
    //   34: invokeinterface 136 2 0
    //   39: goto -20 -> 19
    //   42: astore_1
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_1
    //   46: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	47	0	this	zzcu
    //   42	4	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	19	42	finally
    //   22	39	42	finally
  }
  
  /* Error */
  void zza(Context paramContext, zzas paramzzas)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 86	com/google/android/gms/tagmanager/zzcu:zzaNE	Landroid/content/Context;
    //   6: astore_3
    //   7: aload_3
    //   8: ifnull +6 -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: aload_1
    //   16: invokevirtual 141	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   19: putfield 86	com/google/android/gms/tagmanager/zzcu:zzaNE	Landroid/content/Context;
    //   22: aload_0
    //   23: getfield 129	com/google/android/gms/tagmanager/zzcu:zzaNG	Lcom/google/android/gms/tagmanager/zzas;
    //   26: ifnonnull -15 -> 11
    //   29: aload_0
    //   30: aload_2
    //   31: putfield 129	com/google/android/gms/tagmanager/zzcu:zzaNG	Lcom/google/android/gms/tagmanager/zzas;
    //   34: goto -23 -> 11
    //   37: astore_1
    //   38: aload_0
    //   39: monitorexit
    //   40: aload_1
    //   41: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	42	0	this	zzcu
    //   0	42	1	paramContext	Context
    //   0	42	2	paramzzas	zzas
    //   6	2	3	localContext	Context
    // Exception table:
    //   from	to	target	type
    //   2	7	37	finally
    //   14	34	37	finally
  }
  
  void zzao(boolean paramBoolean)
  {
    try
    {
      zzd(this.zzaNN, paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  void zzd(boolean paramBoolean1, boolean paramBoolean2)
  {
    for (;;)
    {
      StringBuilder localStringBuilder;
      String str1;
      try
      {
        if (this.zzaNN == paramBoolean1)
        {
          boolean bool = this.connected;
          if (bool == paramBoolean2) {
            return;
          }
        }
        if (((paramBoolean1) || (!paramBoolean2)) && (this.zzaNH > 0)) {
          this.handler.removeMessages(1, zzaND);
        }
        if ((!paramBoolean1) && (paramBoolean2) && (this.zzaNH > 0)) {
          this.handler.sendMessageDelayed(this.handler.obtainMessage(1, zzaND), this.zzaNH);
        }
        localStringBuilder = new StringBuilder().append("PowerSaveMode ");
        if (paramBoolean1) {
          break label153;
        }
        if (paramBoolean2) {
          break label146;
        }
      }
      finally {}
      zzbg.zzaB(str1);
      this.zzaNN = paramBoolean1;
      this.connected = paramBoolean2;
      continue;
      label146:
      String str2 = "terminated.";
      continue;
      label153:
      str2 = "initiated.";
    }
  }
  
  void zzhK()
  {
    try
    {
      if ((!this.zzaNN) && (this.connected) && (this.zzaNH > 0))
      {
        this.handler.removeMessages(1, zzaND);
        this.handler.sendMessage(this.handler.obtainMessage(1, zzaND));
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  zzau zzzC()
  {
    try
    {
      if (this.zzaNF != null) {
        break label50;
      }
      if (this.zzaNE == null) {
        throw new IllegalStateException("Cant get a store unless we have a context");
      }
    }
    finally {}
    this.zzaNF = new zzby(this.zzaNL, this.zzaNE);
    label50:
    if (this.handler == null) {
      zzzB();
    }
    this.zzaNJ = true;
    if (this.zzaNI)
    {
      dispatch();
      this.zzaNI = false;
    }
    if ((this.zzaNM == null) && (this.zzaNK)) {
      zzzA();
    }
    zzau localzzau = this.zzaNF;
    return localzzau;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzcu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
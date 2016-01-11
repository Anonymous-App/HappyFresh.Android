package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.api.Status;

class zzo
  implements ContainerHolder
{
  private Status zzOt;
  private final Looper zzWt;
  private Container zzaKG;
  private Container zzaKH;
  private zzb zzaKI;
  private zza zzaKJ;
  private TagManager zzaKK;
  private boolean zzaea;
  
  public zzo(Status paramStatus)
  {
    this.zzOt = paramStatus;
    this.zzWt = null;
  }
  
  public zzo(TagManager paramTagManager, Looper paramLooper, Container paramContainer, zza paramzza)
  {
    this.zzaKK = paramTagManager;
    if (paramLooper != null) {}
    for (;;)
    {
      this.zzWt = paramLooper;
      this.zzaKG = paramContainer;
      this.zzaKJ = paramzza;
      this.zzOt = Status.zzXP;
      paramTagManager.zza(this);
      return;
      paramLooper = Looper.getMainLooper();
    }
  }
  
  private void zzyp()
  {
    if (this.zzaKI != null) {
      this.zzaKI.zzek(this.zzaKH.zzym());
    }
  }
  
  /* Error */
  public Container getContainer()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: monitorenter
    //   4: aload_0
    //   5: getfield 79	com/google/android/gms/tagmanager/zzo:zzaea	Z
    //   8: ifeq +12 -> 20
    //   11: ldc 81
    //   13: invokestatic 86	com/google/android/gms/tagmanager/zzbg:zzaz	(Ljava/lang/String;)V
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_1
    //   19: areturn
    //   20: aload_0
    //   21: getfield 65	com/google/android/gms/tagmanager/zzo:zzaKH	Lcom/google/android/gms/tagmanager/Container;
    //   24: ifnull +16 -> 40
    //   27: aload_0
    //   28: aload_0
    //   29: getfield 65	com/google/android/gms/tagmanager/zzo:zzaKH	Lcom/google/android/gms/tagmanager/Container;
    //   32: putfield 42	com/google/android/gms/tagmanager/zzo:zzaKG	Lcom/google/android/gms/tagmanager/Container;
    //   35: aload_0
    //   36: aconst_null
    //   37: putfield 65	com/google/android/gms/tagmanager/zzo:zzaKH	Lcom/google/android/gms/tagmanager/Container;
    //   40: aload_0
    //   41: getfield 42	com/google/android/gms/tagmanager/zzo:zzaKG	Lcom/google/android/gms/tagmanager/Container;
    //   44: astore_1
    //   45: goto -29 -> 16
    //   48: astore_1
    //   49: aload_0
    //   50: monitorexit
    //   51: aload_1
    //   52: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	53	0	this	zzo
    //   1	44	1	localContainer	Container
    //   48	4	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   4	16	48	finally
    //   20	40	48	finally
    //   40	45	48	finally
  }
  
  String getContainerId()
  {
    if (this.zzaea)
    {
      zzbg.zzaz("getContainerId called on a released ContainerHolder.");
      return "";
    }
    return this.zzaKG.getContainerId();
  }
  
  public Status getStatus()
  {
    return this.zzOt;
  }
  
  /* Error */
  public void refresh()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 79	com/google/android/gms/tagmanager/zzo:zzaea	Z
    //   6: ifeq +11 -> 17
    //   9: ldc 98
    //   11: invokestatic 86	com/google/android/gms/tagmanager/zzbg:zzaz	(Ljava/lang/String;)V
    //   14: aload_0
    //   15: monitorexit
    //   16: return
    //   17: aload_0
    //   18: getfield 44	com/google/android/gms/tagmanager/zzo:zzaKJ	Lcom/google/android/gms/tagmanager/zzo$zza;
    //   21: invokeinterface 101 1 0
    //   26: goto -12 -> 14
    //   29: astore_1
    //   30: aload_0
    //   31: monitorexit
    //   32: aload_1
    //   33: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	34	0	this	zzo
    //   29	4	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	14	29	finally
    //   17	26	29	finally
  }
  
  /* Error */
  public void release()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 79	com/google/android/gms/tagmanager/zzo:zzaea	Z
    //   6: ifeq +11 -> 17
    //   9: ldc 104
    //   11: invokestatic 86	com/google/android/gms/tagmanager/zzbg:zzaz	(Ljava/lang/String;)V
    //   14: aload_0
    //   15: monitorexit
    //   16: return
    //   17: aload_0
    //   18: iconst_1
    //   19: putfield 79	com/google/android/gms/tagmanager/zzo:zzaea	Z
    //   22: aload_0
    //   23: getfield 40	com/google/android/gms/tagmanager/zzo:zzaKK	Lcom/google/android/gms/tagmanager/TagManager;
    //   26: aload_0
    //   27: invokevirtual 107	com/google/android/gms/tagmanager/TagManager:zzb	(Lcom/google/android/gms/tagmanager/zzo;)Z
    //   30: pop
    //   31: aload_0
    //   32: getfield 42	com/google/android/gms/tagmanager/zzo:zzaKG	Lcom/google/android/gms/tagmanager/Container;
    //   35: invokevirtual 109	com/google/android/gms/tagmanager/Container:release	()V
    //   38: aload_0
    //   39: aconst_null
    //   40: putfield 42	com/google/android/gms/tagmanager/zzo:zzaKG	Lcom/google/android/gms/tagmanager/Container;
    //   43: aload_0
    //   44: aconst_null
    //   45: putfield 65	com/google/android/gms/tagmanager/zzo:zzaKH	Lcom/google/android/gms/tagmanager/Container;
    //   48: aload_0
    //   49: aconst_null
    //   50: putfield 44	com/google/android/gms/tagmanager/zzo:zzaKJ	Lcom/google/android/gms/tagmanager/zzo$zza;
    //   53: aload_0
    //   54: aconst_null
    //   55: putfield 63	com/google/android/gms/tagmanager/zzo:zzaKI	Lcom/google/android/gms/tagmanager/zzo$zzb;
    //   58: goto -44 -> 14
    //   61: astore_1
    //   62: aload_0
    //   63: monitorexit
    //   64: aload_1
    //   65: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	66	0	this	zzo
    //   61	4	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	14	61	finally
    //   17	58	61	finally
  }
  
  public void setContainerAvailableListener(ContainerHolder.ContainerAvailableListener paramContainerAvailableListener)
  {
    for (;;)
    {
      try
      {
        if (this.zzaea)
        {
          zzbg.zzaz("ContainerHolder is released.");
          return;
        }
        if (paramContainerAvailableListener == null)
        {
          this.zzaKI = null;
          continue;
        }
        this.zzaKI = new zzb(paramContainerAvailableListener, this.zzWt);
      }
      finally {}
      if (this.zzaKH != null) {
        zzyp();
      }
    }
  }
  
  public void zza(Container paramContainer)
  {
    for (;;)
    {
      try
      {
        boolean bool = this.zzaea;
        if (bool) {
          return;
        }
        if (paramContainer == null)
        {
          zzbg.zzaz("Unexpected null container.");
          continue;
        }
        this.zzaKH = paramContainer;
      }
      finally {}
      zzyp();
    }
  }
  
  /* Error */
  public void zzeh(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 79	com/google/android/gms/tagmanager/zzo:zzaea	Z
    //   6: istore_2
    //   7: iload_2
    //   8: ifeq +6 -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: getfield 42	com/google/android/gms/tagmanager/zzo:zzaKG	Lcom/google/android/gms/tagmanager/Container;
    //   18: aload_1
    //   19: invokevirtual 122	com/google/android/gms/tagmanager/Container:zzeh	(Ljava/lang/String;)V
    //   22: goto -11 -> 11
    //   25: astore_1
    //   26: aload_0
    //   27: monitorexit
    //   28: aload_1
    //   29: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	30	0	this	zzo
    //   0	30	1	paramString	String
    //   6	2	2	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	7	25	finally
    //   14	22	25	finally
  }
  
  void zzej(String paramString)
  {
    if (this.zzaea)
    {
      zzbg.zzaz("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
      return;
    }
    this.zzaKJ.zzej(paramString);
  }
  
  String zzyo()
  {
    if (this.zzaea)
    {
      zzbg.zzaz("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
      return "";
    }
    return this.zzaKJ.zzyo();
  }
  
  public static abstract interface zza
  {
    public abstract void zzej(String paramString);
    
    public abstract String zzyo();
    
    public abstract void zzyq();
  }
  
  private class zzb
    extends Handler
  {
    private final ContainerHolder.ContainerAvailableListener zzaKL;
    
    public zzb(ContainerHolder.ContainerAvailableListener paramContainerAvailableListener, Looper paramLooper)
    {
      super();
      this.zzaKL = paramContainerAvailableListener;
    }
    
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default: 
        zzbg.zzaz("Don't know how to handle this message.");
        return;
      }
      zzel((String)paramMessage.obj);
    }
    
    public void zzek(String paramString)
    {
      sendMessage(obtainMessage(1, paramString));
    }
    
    protected void zzel(String paramString)
    {
      this.zzaKL.onContainerAvailable(zzo.this, paramString);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.ad4screen.sdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface d
  extends IInterface
{
  public abstract void a(boolean paramBoolean)
    throws RemoteException;
  
  public static abstract class a
    extends Binder
    implements d
  {
    public a()
    {
      attachInterface(this, "com.ad4screen.sdk.IFlushCallback");
    }
    
    public static d a(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.ad4screen.sdk.IFlushCallback");
      if ((localIInterface != null) && ((localIInterface instanceof d))) {
        return (d)localIInterface;
      }
      return new a(paramIBinder);
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.ad4screen.sdk.IFlushCallback");
        return true;
      }
      paramParcel1.enforceInterface("com.ad4screen.sdk.IFlushCallback");
      if (paramParcel1.readInt() != 0) {}
      for (boolean bool = true;; bool = false)
      {
        a(bool);
        return true;
      }
    }
    
    private static class a
      implements d
    {
      private IBinder a;
      
      a(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }
      
      /* Error */
      public void a(boolean paramBoolean)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore_2
        //   2: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   5: astore_3
        //   6: aload_3
        //   7: ldc 29
        //   9: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   12: iload_1
        //   13: ifeq +27 -> 40
        //   16: aload_3
        //   17: iload_2
        //   18: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   21: aload_0
        //   22: getfield 17	com/ad4screen/sdk/d$a$a:a	Landroid/os/IBinder;
        //   25: iconst_1
        //   26: aload_3
        //   27: aconst_null
        //   28: iconst_1
        //   29: invokeinterface 43 5 0
        //   34: pop
        //   35: aload_3
        //   36: invokevirtual 46	android/os/Parcel:recycle	()V
        //   39: return
        //   40: iconst_0
        //   41: istore_2
        //   42: goto -26 -> 16
        //   45: astore 4
        //   47: aload_3
        //   48: invokevirtual 46	android/os/Parcel:recycle	()V
        //   51: aload 4
        //   53: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	54	0	this	a
        //   0	54	1	paramBoolean	boolean
        //   1	41	2	i	int
        //   5	43	3	localParcel	Parcel
        //   45	7	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   6	12	45	finally
        //   16	35	45	finally
      }
      
      public IBinder asBinder()
      {
        return this.a;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
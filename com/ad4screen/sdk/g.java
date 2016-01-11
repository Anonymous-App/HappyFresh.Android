package com.ad4screen.sdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.ad4screen.sdk.inbox.Message;

public abstract interface g
  extends IInterface
{
  public abstract void a()
    throws RemoteException;
  
  public abstract void a(Message[] paramArrayOfMessage)
    throws RemoteException;
  
  public static abstract class a
    extends Binder
    implements g
  {
    public a()
    {
      attachInterface(this, "com.ad4screen.sdk.InboxCallback");
    }
    
    public static g a(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.ad4screen.sdk.InboxCallback");
      if ((localIInterface != null) && ((localIInterface instanceof g))) {
        return (g)localIInterface;
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
        paramParcel2.writeString("com.ad4screen.sdk.InboxCallback");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.InboxCallback");
        a((Message[])paramParcel1.createTypedArray(Message.CREATOR));
        return true;
      }
      paramParcel1.enforceInterface("com.ad4screen.sdk.InboxCallback");
      a();
      return true;
    }
    
    private static class a
      implements g
    {
      private IBinder a;
      
      a(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }
      
      public void a()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.InboxCallback");
          this.a.transact(2, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void a(Message[] paramArrayOfMessage)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.InboxCallback");
          localParcel.writeTypedArray(paramArrayOfMessage, 0);
          this.a.transact(1, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public IBinder asBinder()
      {
        return this.a;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
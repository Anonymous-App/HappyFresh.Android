package android.support.v4.media;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.v4.media.session.MediaSessionCompat.Token;
import java.util.List;

public abstract interface IMediaBrowserServiceCompatCallbacks
  extends IInterface
{
  public abstract void onConnect(String paramString, MediaSessionCompat.Token paramToken, Bundle paramBundle)
    throws RemoteException;
  
  public abstract void onConnectFailed()
    throws RemoteException;
  
  public abstract void onLoadChildren(String paramString, List paramList)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IMediaBrowserServiceCompatCallbacks
  {
    private static final String DESCRIPTOR = "android.support.v4.media.IMediaBrowserServiceCompatCallbacks";
    static final int TRANSACTION_onConnect = 1;
    static final int TRANSACTION_onConnectFailed = 2;
    static final int TRANSACTION_onLoadChildren = 3;
    
    public Stub()
    {
      attachInterface(this, "android.support.v4.media.IMediaBrowserServiceCompatCallbacks");
    }
    
    public static IMediaBrowserServiceCompatCallbacks asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("android.support.v4.media.IMediaBrowserServiceCompatCallbacks");
      if ((localIInterface != null) && ((localIInterface instanceof IMediaBrowserServiceCompatCallbacks))) {
        return (IMediaBrowserServiceCompatCallbacks)localIInterface;
      }
      return new Proxy(paramIBinder);
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
        paramParcel2.writeString("android.support.v4.media.IMediaBrowserServiceCompatCallbacks");
        return true;
      case 1: 
        paramParcel1.enforceInterface("android.support.v4.media.IMediaBrowserServiceCompatCallbacks");
        String str = paramParcel1.readString();
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = (MediaSessionCompat.Token)MediaSessionCompat.Token.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label129;
          }
        }
        for (paramParcel1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          onConnect(str, paramParcel2, paramParcel1);
          return true;
          paramParcel2 = null;
          break;
        }
      case 2: 
        label129:
        paramParcel1.enforceInterface("android.support.v4.media.IMediaBrowserServiceCompatCallbacks");
        onConnectFailed();
        return true;
      }
      paramParcel1.enforceInterface("android.support.v4.media.IMediaBrowserServiceCompatCallbacks");
      onLoadChildren(paramParcel1.readString(), paramParcel1.readArrayList(getClass().getClassLoader()));
      return true;
    }
    
    private static class Proxy
      implements IMediaBrowserServiceCompatCallbacks
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor()
      {
        return "android.support.v4.media.IMediaBrowserServiceCompatCallbacks";
      }
      
      public void onConnect(String paramString, MediaSessionCompat.Token paramToken, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("android.support.v4.media.IMediaBrowserServiceCompatCallbacks");
            localParcel.writeString(paramString);
            if (paramToken != null)
            {
              localParcel.writeInt(1);
              paramToken.writeToParcel(localParcel, 0);
              if (paramBundle != null)
              {
                localParcel.writeInt(1);
                paramBundle.writeToParcel(localParcel, 0);
                this.mRemote.transact(1, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
      
      public void onConnectFailed()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("android.support.v4.media.IMediaBrowserServiceCompatCallbacks");
          this.mRemote.transact(2, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void onLoadChildren(String paramString, List paramList)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("android.support.v4.media.IMediaBrowserServiceCompatCallbacks");
          localParcel.writeString(paramString);
          localParcel.writeList(paramList);
          this.mRemote.transact(3, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/android/support/v4/media/IMediaBrowserServiceCompatCallbacks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
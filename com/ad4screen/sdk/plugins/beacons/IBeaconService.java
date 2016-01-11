package com.ad4screen.sdk.plugins.beacons;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.ad4screen.sdk.plugins.model.Beacon;

public abstract interface IBeaconService
  extends IInterface
{
  public abstract void add(String paramString, Beacon[] paramArrayOfBeacon)
    throws RemoteException;
  
  public abstract long getVersion()
    throws RemoteException;
  
  public abstract void remove(String paramString, String[] paramArrayOfString)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IBeaconService
  {
    private static final String DESCRIPTOR = "com.ad4screen.sdk.plugins.beacons.IBeaconService";
    static final int TRANSACTION_add = 3;
    static final int TRANSACTION_getVersion = 1;
    static final int TRANSACTION_remove = 2;
    
    public Stub()
    {
      attachInterface(this, "com.ad4screen.sdk.plugins.beacons.IBeaconService");
    }
    
    public static IBeaconService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.ad4screen.sdk.plugins.beacons.IBeaconService");
      if ((localIInterface != null) && ((localIInterface instanceof IBeaconService))) {
        return (IBeaconService)localIInterface;
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
        paramParcel2.writeString("com.ad4screen.sdk.plugins.beacons.IBeaconService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.plugins.beacons.IBeaconService");
        long l = getVersion();
        paramParcel2.writeNoException();
        paramParcel2.writeLong(l);
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.plugins.beacons.IBeaconService");
        remove(paramParcel1.readString(), paramParcel1.createStringArray());
        return true;
      }
      paramParcel1.enforceInterface("com.ad4screen.sdk.plugins.beacons.IBeaconService");
      add(paramParcel1.readString(), (Beacon[])paramParcel1.createTypedArray(Beacon.CREATOR));
      return true;
    }
    
    private static class Proxy
      implements IBeaconService
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public void add(String paramString, Beacon[] paramArrayOfBeacon)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.plugins.beacons.IBeaconService");
          localParcel.writeString(paramString);
          localParcel.writeTypedArray(paramArrayOfBeacon, 0);
          this.mRemote.transact(3, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public IBinder asBinder()
      {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor()
      {
        return "com.ad4screen.sdk.plugins.beacons.IBeaconService";
      }
      
      public long getVersion()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.ad4screen.sdk.plugins.beacons.IBeaconService");
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          long l = localParcel2.readLong();
          return l;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void remove(String paramString, String[] paramArrayOfString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.plugins.beacons.IBeaconService");
          localParcel.writeString(paramString);
          localParcel.writeStringArray(paramArrayOfString);
          this.mRemote.transact(2, localParcel, null, 1);
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


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/beacons/IBeaconService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
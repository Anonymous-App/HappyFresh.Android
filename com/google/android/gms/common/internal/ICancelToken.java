package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ICancelToken
  extends IInterface
{
  public abstract void cancel()
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements ICancelToken
  {
    public static ICancelToken zzaE(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.common.internal.ICancelToken");
      if ((localIInterface != null) && ((localIInterface instanceof ICancelToken))) {
        return (ICancelToken)localIInterface;
      }
      return new zza(paramIBinder);
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.google.android.gms.common.internal.ICancelToken");
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.common.internal.ICancelToken");
      cancel();
      return true;
    }
    
    private static class zza
      implements ICancelToken
    {
      private IBinder zznF;
      
      zza(IBinder paramIBinder)
      {
        this.zznF = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.zznF;
      }
      
      public void cancel()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.common.internal.ICancelToken");
          this.zznF.transact(2, localParcel, null, 1);
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


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/ICancelToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
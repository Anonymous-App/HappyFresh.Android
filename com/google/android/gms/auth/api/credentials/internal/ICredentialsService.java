package com.google.android.gms.auth.api.credentials.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.CredentialRequest;

public abstract interface ICredentialsService
  extends IInterface
{
  public abstract void performCredentialsDeleteOperation(ICredentialsCallbacks paramICredentialsCallbacks, DeleteRequest paramDeleteRequest)
    throws RemoteException;
  
  public abstract void performCredentialsRequestOperation(ICredentialsCallbacks paramICredentialsCallbacks, CredentialRequest paramCredentialRequest)
    throws RemoteException;
  
  public abstract void performCredentialsSaveOperation(ICredentialsCallbacks paramICredentialsCallbacks, SaveRequest paramSaveRequest)
    throws RemoteException;
  
  public abstract void performDisableAutoSignInOperation(ICredentialsCallbacks paramICredentialsCallbacks)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements ICredentialsService
  {
    public static ICredentialsService zzaq(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
      if ((localIInterface != null) && ((localIInterface instanceof ICredentialsService))) {
        return (ICredentialsService)localIInterface;
      }
      return new zza(paramIBinder);
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      ICredentialsCallbacks localICredentialsCallbacks1 = null;
      ICredentialsCallbacks localICredentialsCallbacks2 = null;
      Object localObject = null;
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
        localICredentialsCallbacks1 = ICredentialsCallbacks.zza.zzap(paramParcel1.readStrongBinder());
        if (paramParcel1.readInt() != 0) {
          localObject = (CredentialRequest)CredentialRequest.CREATOR.createFromParcel(paramParcel1);
        }
        performCredentialsRequestOperation(localICredentialsCallbacks1, (CredentialRequest)localObject);
        paramParcel2.writeNoException();
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
        localICredentialsCallbacks2 = ICredentialsCallbacks.zza.zzap(paramParcel1.readStrongBinder());
        localObject = localICredentialsCallbacks1;
        if (paramParcel1.readInt() != 0) {
          localObject = (SaveRequest)SaveRequest.CREATOR.createFromParcel(paramParcel1);
        }
        performCredentialsSaveOperation(localICredentialsCallbacks2, (SaveRequest)localObject);
        paramParcel2.writeNoException();
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
        localICredentialsCallbacks1 = ICredentialsCallbacks.zza.zzap(paramParcel1.readStrongBinder());
        localObject = localICredentialsCallbacks2;
        if (paramParcel1.readInt() != 0) {
          localObject = (DeleteRequest)DeleteRequest.CREATOR.createFromParcel(paramParcel1);
        }
        performCredentialsDeleteOperation(localICredentialsCallbacks1, (DeleteRequest)localObject);
        paramParcel2.writeNoException();
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
      performDisableAutoSignInOperation(ICredentialsCallbacks.zza.zzap(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      return true;
    }
    
    private static class zza
      implements ICredentialsService
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
      
      /* Error */
      public void performCredentialsDeleteOperation(ICredentialsCallbacks paramICredentialsCallbacks, DeleteRequest paramDeleteRequest)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore 4
        //   9: aload_3
        //   10: ldc 33
        //   12: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload_1
        //   16: ifnull +60 -> 76
        //   19: aload_1
        //   20: invokeinterface 41 1 0
        //   25: astore_1
        //   26: aload_3
        //   27: aload_1
        //   28: invokevirtual 44	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   31: aload_2
        //   32: ifnull +49 -> 81
        //   35: aload_3
        //   36: iconst_1
        //   37: invokevirtual 48	android/os/Parcel:writeInt	(I)V
        //   40: aload_2
        //   41: aload_3
        //   42: iconst_0
        //   43: invokevirtual 54	com/google/android/gms/auth/api/credentials/internal/DeleteRequest:writeToParcel	(Landroid/os/Parcel;I)V
        //   46: aload_0
        //   47: getfield 18	com/google/android/gms/auth/api/credentials/internal/ICredentialsService$zza$zza:zznF	Landroid/os/IBinder;
        //   50: iconst_3
        //   51: aload_3
        //   52: aload 4
        //   54: iconst_0
        //   55: invokeinterface 60 5 0
        //   60: pop
        //   61: aload 4
        //   63: invokevirtual 63	android/os/Parcel:readException	()V
        //   66: aload 4
        //   68: invokevirtual 66	android/os/Parcel:recycle	()V
        //   71: aload_3
        //   72: invokevirtual 66	android/os/Parcel:recycle	()V
        //   75: return
        //   76: aconst_null
        //   77: astore_1
        //   78: goto -52 -> 26
        //   81: aload_3
        //   82: iconst_0
        //   83: invokevirtual 48	android/os/Parcel:writeInt	(I)V
        //   86: goto -40 -> 46
        //   89: astore_1
        //   90: aload 4
        //   92: invokevirtual 66	android/os/Parcel:recycle	()V
        //   95: aload_3
        //   96: invokevirtual 66	android/os/Parcel:recycle	()V
        //   99: aload_1
        //   100: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	101	0	this	zza
        //   0	101	1	paramICredentialsCallbacks	ICredentialsCallbacks
        //   0	101	2	paramDeleteRequest	DeleteRequest
        //   3	93	3	localParcel1	Parcel
        //   7	84	4	localParcel2	Parcel
        // Exception table:
        //   from	to	target	type
        //   9	15	89	finally
        //   19	26	89	finally
        //   26	31	89	finally
        //   35	46	89	finally
        //   46	66	89	finally
        //   81	86	89	finally
      }
      
      /* Error */
      public void performCredentialsRequestOperation(ICredentialsCallbacks paramICredentialsCallbacks, CredentialRequest paramCredentialRequest)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore 4
        //   9: aload_3
        //   10: ldc 33
        //   12: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload_1
        //   16: ifnull +60 -> 76
        //   19: aload_1
        //   20: invokeinterface 41 1 0
        //   25: astore_1
        //   26: aload_3
        //   27: aload_1
        //   28: invokevirtual 44	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   31: aload_2
        //   32: ifnull +49 -> 81
        //   35: aload_3
        //   36: iconst_1
        //   37: invokevirtual 48	android/os/Parcel:writeInt	(I)V
        //   40: aload_2
        //   41: aload_3
        //   42: iconst_0
        //   43: invokevirtual 72	com/google/android/gms/auth/api/credentials/CredentialRequest:writeToParcel	(Landroid/os/Parcel;I)V
        //   46: aload_0
        //   47: getfield 18	com/google/android/gms/auth/api/credentials/internal/ICredentialsService$zza$zza:zznF	Landroid/os/IBinder;
        //   50: iconst_1
        //   51: aload_3
        //   52: aload 4
        //   54: iconst_0
        //   55: invokeinterface 60 5 0
        //   60: pop
        //   61: aload 4
        //   63: invokevirtual 63	android/os/Parcel:readException	()V
        //   66: aload 4
        //   68: invokevirtual 66	android/os/Parcel:recycle	()V
        //   71: aload_3
        //   72: invokevirtual 66	android/os/Parcel:recycle	()V
        //   75: return
        //   76: aconst_null
        //   77: astore_1
        //   78: goto -52 -> 26
        //   81: aload_3
        //   82: iconst_0
        //   83: invokevirtual 48	android/os/Parcel:writeInt	(I)V
        //   86: goto -40 -> 46
        //   89: astore_1
        //   90: aload 4
        //   92: invokevirtual 66	android/os/Parcel:recycle	()V
        //   95: aload_3
        //   96: invokevirtual 66	android/os/Parcel:recycle	()V
        //   99: aload_1
        //   100: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	101	0	this	zza
        //   0	101	1	paramICredentialsCallbacks	ICredentialsCallbacks
        //   0	101	2	paramCredentialRequest	CredentialRequest
        //   3	93	3	localParcel1	Parcel
        //   7	84	4	localParcel2	Parcel
        // Exception table:
        //   from	to	target	type
        //   9	15	89	finally
        //   19	26	89	finally
        //   26	31	89	finally
        //   35	46	89	finally
        //   46	66	89	finally
        //   81	86	89	finally
      }
      
      /* Error */
      public void performCredentialsSaveOperation(ICredentialsCallbacks paramICredentialsCallbacks, SaveRequest paramSaveRequest)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore 4
        //   9: aload_3
        //   10: ldc 33
        //   12: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload_1
        //   16: ifnull +60 -> 76
        //   19: aload_1
        //   20: invokeinterface 41 1 0
        //   25: astore_1
        //   26: aload_3
        //   27: aload_1
        //   28: invokevirtual 44	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   31: aload_2
        //   32: ifnull +49 -> 81
        //   35: aload_3
        //   36: iconst_1
        //   37: invokevirtual 48	android/os/Parcel:writeInt	(I)V
        //   40: aload_2
        //   41: aload_3
        //   42: iconst_0
        //   43: invokevirtual 77	com/google/android/gms/auth/api/credentials/internal/SaveRequest:writeToParcel	(Landroid/os/Parcel;I)V
        //   46: aload_0
        //   47: getfield 18	com/google/android/gms/auth/api/credentials/internal/ICredentialsService$zza$zza:zznF	Landroid/os/IBinder;
        //   50: iconst_2
        //   51: aload_3
        //   52: aload 4
        //   54: iconst_0
        //   55: invokeinterface 60 5 0
        //   60: pop
        //   61: aload 4
        //   63: invokevirtual 63	android/os/Parcel:readException	()V
        //   66: aload 4
        //   68: invokevirtual 66	android/os/Parcel:recycle	()V
        //   71: aload_3
        //   72: invokevirtual 66	android/os/Parcel:recycle	()V
        //   75: return
        //   76: aconst_null
        //   77: astore_1
        //   78: goto -52 -> 26
        //   81: aload_3
        //   82: iconst_0
        //   83: invokevirtual 48	android/os/Parcel:writeInt	(I)V
        //   86: goto -40 -> 46
        //   89: astore_1
        //   90: aload 4
        //   92: invokevirtual 66	android/os/Parcel:recycle	()V
        //   95: aload_3
        //   96: invokevirtual 66	android/os/Parcel:recycle	()V
        //   99: aload_1
        //   100: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	101	0	this	zza
        //   0	101	1	paramICredentialsCallbacks	ICredentialsCallbacks
        //   0	101	2	paramSaveRequest	SaveRequest
        //   3	93	3	localParcel1	Parcel
        //   7	84	4	localParcel2	Parcel
        // Exception table:
        //   from	to	target	type
        //   9	15	89	finally
        //   19	26	89	finally
        //   26	31	89	finally
        //   35	46	89	finally
        //   46	66	89	finally
        //   81	86	89	finally
      }
      
      /* Error */
      public void performDisableAutoSignInOperation(ICredentialsCallbacks paramICredentialsCallbacks)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 33
        //   11: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +42 -> 57
        //   18: aload_1
        //   19: invokeinterface 41 1 0
        //   24: astore_1
        //   25: aload_2
        //   26: aload_1
        //   27: invokevirtual 44	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   30: aload_0
        //   31: getfield 18	com/google/android/gms/auth/api/credentials/internal/ICredentialsService$zza$zza:zznF	Landroid/os/IBinder;
        //   34: iconst_4
        //   35: aload_2
        //   36: aload_3
        //   37: iconst_0
        //   38: invokeinterface 60 5 0
        //   43: pop
        //   44: aload_3
        //   45: invokevirtual 63	android/os/Parcel:readException	()V
        //   48: aload_3
        //   49: invokevirtual 66	android/os/Parcel:recycle	()V
        //   52: aload_2
        //   53: invokevirtual 66	android/os/Parcel:recycle	()V
        //   56: return
        //   57: aconst_null
        //   58: astore_1
        //   59: goto -34 -> 25
        //   62: astore_1
        //   63: aload_3
        //   64: invokevirtual 66	android/os/Parcel:recycle	()V
        //   67: aload_2
        //   68: invokevirtual 66	android/os/Parcel:recycle	()V
        //   71: aload_1
        //   72: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	73	0	this	zza
        //   0	73	1	paramICredentialsCallbacks	ICredentialsCallbacks
        //   3	65	2	localParcel1	Parcel
        //   7	57	3	localParcel2	Parcel
        // Exception table:
        //   from	to	target	type
        //   8	14	62	finally
        //   18	25	62	finally
        //   25	48	62	finally
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/credentials/internal/ICredentialsService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
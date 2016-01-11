package io.fabric.sdk.android.services.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class AdvertisingInfoServiceStrategy
  implements AdvertisingInfoStrategy
{
  public static final String GOOGLE_PLAY_SERVICES_INTENT = "com.google.android.gms.ads.identifier.service.START";
  public static final String GOOGLE_PLAY_SERVICES_INTENT_PACKAGE_NAME = "com.google.android.gms";
  private static final String GOOGLE_PLAY_SERVICE_PACKAGE_NAME = "com.android.vending";
  private final Context context;
  
  public AdvertisingInfoServiceStrategy(Context paramContext)
  {
    this.context = paramContext.getApplicationContext();
  }
  
  /* Error */
  public AdvertisingInfo getAdvertisingInfo()
  {
    // Byte code:
    //   0: invokestatic 54	android/os/Looper:myLooper	()Landroid/os/Looper;
    //   3: invokestatic 57	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   6: if_acmpne +17 -> 23
    //   9: invokestatic 63	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   12: ldc 65
    //   14: ldc 67
    //   16: invokeinterface 73 3 0
    //   21: aconst_null
    //   22: areturn
    //   23: aload_0
    //   24: getfield 39	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   27: invokevirtual 77	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   30: ldc 24
    //   32: iconst_0
    //   33: invokevirtual 83	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   36: pop
    //   37: new 10	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingConnection
    //   40: dup
    //   41: aconst_null
    //   42: invokespecial 86	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingConnection:<init>	(Lio/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$1;)V
    //   45: astore_2
    //   46: new 88	android/content/Intent
    //   49: dup
    //   50: ldc 18
    //   52: invokespecial 91	android/content/Intent:<init>	(Ljava/lang/String;)V
    //   55: astore_3
    //   56: aload_3
    //   57: ldc 21
    //   59: invokevirtual 95	android/content/Intent:setPackage	(Ljava/lang/String;)Landroid/content/Intent;
    //   62: pop
    //   63: aload_0
    //   64: getfield 39	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   67: aload_3
    //   68: aload_2
    //   69: iconst_1
    //   70: invokevirtual 99	android/content/Context:bindService	(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z
    //   73: istore_1
    //   74: iload_1
    //   75: ifeq +123 -> 198
    //   78: new 13	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface
    //   81: dup
    //   82: aload_2
    //   83: invokevirtual 103	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingConnection:getBinder	()Landroid/os/IBinder;
    //   86: invokespecial 106	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface:<init>	(Landroid/os/IBinder;)V
    //   89: astore_3
    //   90: new 108	io/fabric/sdk/android/services/common/AdvertisingInfo
    //   93: dup
    //   94: aload_3
    //   95: invokevirtual 112	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface:getId	()Ljava/lang/String;
    //   98: aload_3
    //   99: invokevirtual 116	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface:isLimitAdTrackingEnabled	()Z
    //   102: invokespecial 119	io/fabric/sdk/android/services/common/AdvertisingInfo:<init>	(Ljava/lang/String;Z)V
    //   105: astore_3
    //   106: aload_0
    //   107: getfield 39	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   110: aload_2
    //   111: invokevirtual 123	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   114: aload_3
    //   115: areturn
    //   116: astore_2
    //   117: invokestatic 63	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   120: ldc 65
    //   122: ldc 125
    //   124: invokeinterface 73 3 0
    //   129: aconst_null
    //   130: areturn
    //   131: astore_2
    //   132: invokestatic 63	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   135: ldc 65
    //   137: ldc 127
    //   139: aload_2
    //   140: invokeinterface 130 4 0
    //   145: aconst_null
    //   146: areturn
    //   147: astore_3
    //   148: invokestatic 63	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   151: ldc 65
    //   153: ldc -124
    //   155: aload_3
    //   156: invokeinterface 135 4 0
    //   161: aload_0
    //   162: getfield 39	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   165: aload_2
    //   166: invokevirtual 123	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   169: aconst_null
    //   170: areturn
    //   171: astore_2
    //   172: invokestatic 63	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   175: ldc 65
    //   177: ldc -119
    //   179: aload_2
    //   180: invokeinterface 130 4 0
    //   185: aconst_null
    //   186: areturn
    //   187: astore_3
    //   188: aload_0
    //   189: getfield 39	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   192: aload_2
    //   193: invokevirtual 123	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   196: aload_3
    //   197: athrow
    //   198: invokestatic 63	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   201: ldc 65
    //   203: ldc -119
    //   205: invokeinterface 73 3 0
    //   210: aconst_null
    //   211: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	212	0	this	AdvertisingInfoServiceStrategy
    //   73	2	1	bool	boolean
    //   45	66	2	localAdvertisingConnection	AdvertisingConnection
    //   116	1	2	localNameNotFoundException	android.content.pm.PackageManager.NameNotFoundException
    //   131	35	2	localException1	Exception
    //   171	22	2	localThrowable	Throwable
    //   55	60	3	localObject1	Object
    //   147	9	3	localException2	Exception
    //   187	10	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   23	37	116	android/content/pm/PackageManager$NameNotFoundException
    //   23	37	131	java/lang/Exception
    //   78	106	147	java/lang/Exception
    //   63	74	171	java/lang/Throwable
    //   106	114	171	java/lang/Throwable
    //   161	169	171	java/lang/Throwable
    //   188	198	171	java/lang/Throwable
    //   198	210	171	java/lang/Throwable
    //   78	106	187	finally
    //   148	161	187	finally
  }
  
  private static final class AdvertisingConnection
    implements ServiceConnection
  {
    private static final int QUEUE_TIMEOUT_IN_MS = 200;
    private final LinkedBlockingQueue<IBinder> queue = new LinkedBlockingQueue(1);
    private boolean retrieved = false;
    
    public IBinder getBinder()
    {
      if (this.retrieved) {
        Fabric.getLogger().e("Fabric", "getBinder already called");
      }
      this.retrieved = true;
      try
      {
        IBinder localIBinder = (IBinder)this.queue.poll(200L, TimeUnit.MILLISECONDS);
        return localIBinder;
      }
      catch (InterruptedException localInterruptedException) {}
      return null;
    }
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      try
      {
        this.queue.put(paramIBinder);
        return;
      }
      catch (InterruptedException paramComponentName) {}
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      this.queue.clear();
    }
  }
  
  private static final class AdvertisingInterface
    implements IInterface
  {
    public static final String ADVERTISING_ID_SERVICE_INTERFACE_TOKEN = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";
    private static final int AD_TRANSACTION_CODE_ID = 1;
    private static final int AD_TRANSACTION_CODE_LIMIT_AD_TRACKING = 2;
    private static final int FLAGS_NONE = 0;
    private final IBinder binder;
    
    public AdvertisingInterface(IBinder paramIBinder)
    {
      this.binder = paramIBinder;
    }
    
    public IBinder asBinder()
    {
      return this.binder;
    }
    
    public String getId()
      throws RemoteException
    {
      Parcel localParcel1 = Parcel.obtain();
      Parcel localParcel2 = Parcel.obtain();
      try
      {
        localParcel1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
        this.binder.transact(1, localParcel1, localParcel2, 0);
        localParcel2.readException();
        String str = localParcel2.readString();
        return str;
      }
      catch (Exception localException)
      {
        Fabric.getLogger().d("Fabric", "Could not get parcel from Google Play Service to capture AdvertisingId");
        return null;
      }
      finally
      {
        localParcel2.recycle();
        localParcel1.recycle();
      }
    }
    
    /* Error */
    public boolean isLimitAdTrackingEnabled()
      throws RemoteException
    {
      // Byte code:
      //   0: invokestatic 44	android/os/Parcel:obtain	()Landroid/os/Parcel;
      //   3: astore_3
      //   4: invokestatic 44	android/os/Parcel:obtain	()Landroid/os/Parcel;
      //   7: astore 4
      //   9: aload_3
      //   10: ldc 13
      //   12: invokevirtual 48	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
      //   15: aload_3
      //   16: iconst_1
      //   17: invokevirtual 86	android/os/Parcel:writeInt	(I)V
      //   20: aload_0
      //   21: getfield 29	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface:binder	Landroid/os/IBinder;
      //   24: iconst_2
      //   25: aload_3
      //   26: aload 4
      //   28: iconst_0
      //   29: invokeinterface 54 5 0
      //   34: pop
      //   35: aload 4
      //   37: invokevirtual 57	android/os/Parcel:readException	()V
      //   40: aload 4
      //   42: invokevirtual 90	android/os/Parcel:readInt	()I
      //   45: istore_1
      //   46: iload_1
      //   47: ifeq +16 -> 63
      //   50: iconst_1
      //   51: istore_2
      //   52: aload 4
      //   54: invokevirtual 63	android/os/Parcel:recycle	()V
      //   57: aload_3
      //   58: invokevirtual 63	android/os/Parcel:recycle	()V
      //   61: iload_2
      //   62: ireturn
      //   63: iconst_0
      //   64: istore_2
      //   65: goto -13 -> 52
      //   68: astore 5
      //   70: invokestatic 69	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
      //   73: ldc 71
      //   75: ldc 92
      //   77: invokeinterface 79 3 0
      //   82: aload 4
      //   84: invokevirtual 63	android/os/Parcel:recycle	()V
      //   87: aload_3
      //   88: invokevirtual 63	android/os/Parcel:recycle	()V
      //   91: iconst_0
      //   92: ireturn
      //   93: astore 5
      //   95: aload 4
      //   97: invokevirtual 63	android/os/Parcel:recycle	()V
      //   100: aload_3
      //   101: invokevirtual 63	android/os/Parcel:recycle	()V
      //   104: aload 5
      //   106: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	107	0	this	AdvertisingInterface
      //   45	2	1	i	int
      //   51	14	2	bool	boolean
      //   3	98	3	localParcel1	Parcel
      //   7	89	4	localParcel2	Parcel
      //   68	1	5	localException	Exception
      //   93	12	5	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   9	46	68	java/lang/Exception
      //   9	46	93	finally
      //   70	82	93	finally
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
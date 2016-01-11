package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionProgressReportCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class zzi<T extends IInterface>
  implements Api.Client, zzj.zza
{
  public static final String[] zzaav = { "service_esmobile", "service_googleme" };
  private final Context mContext;
  final Handler mHandler;
  private final Account zzMY;
  private final Set<Scope> zzWJ;
  private final Looper zzWt;
  private final zze zzXa;
  private final zzk zzaak;
  private zzp zzaal;
  private GoogleApiClient.ConnectionProgressReportCallbacks zzaam;
  private T zzaan;
  private final ArrayList<zzi<T>.zzc<?>> zzaao = new ArrayList();
  private zzi<T>.zze zzaap;
  private int zzaaq = 1;
  private GoogleApiClient.ConnectionCallbacks zzaar;
  private GoogleApiClient.OnConnectionFailedListener zzaas;
  private final int zzaat;
  protected AtomicInteger zzaau = new AtomicInteger(0);
  private final Object zzqt = new Object();
  
  @Deprecated
  protected zzi(Context paramContext, Looper paramLooper, int paramInt, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    this.mContext = ((Context)zzu.zzu(paramContext));
    this.zzWt = ((Looper)zzu.zzb(paramLooper, "Looper must not be null"));
    this.zzaak = zzk.zzah(paramContext);
    this.mHandler = new zzb(paramLooper);
    this.zzaat = paramInt;
    this.zzMY = null;
    this.zzWJ = Collections.emptySet();
    this.zzXa = new GoogleApiClient.Builder(paramContext).zzmx();
    this.zzaar = ((GoogleApiClient.ConnectionCallbacks)zzu.zzu(paramConnectionCallbacks));
    this.zzaas = ((GoogleApiClient.OnConnectionFailedListener)zzu.zzu(paramOnConnectionFailedListener));
  }
  
  protected zzi(Context paramContext, Looper paramLooper, int paramInt, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, zze paramzze)
  {
    this(paramContext, paramLooper, zzk.zzah(paramContext), paramInt, paramzze, paramConnectionCallbacks, paramOnConnectionFailedListener);
  }
  
  protected zzi(Context paramContext, Looper paramLooper, zzk paramzzk, int paramInt, zze paramzze)
  {
    this.mContext = ((Context)zzu.zzb(paramContext, "Context must not be null"));
    this.zzWt = ((Looper)zzu.zzb(paramLooper, "Looper must not be null"));
    this.zzaak = ((zzk)zzu.zzb(paramzzk, "Supervisor must not be null"));
    this.mHandler = new zzb(paramLooper);
    this.zzaat = paramInt;
    this.zzXa = ((zze)zzu.zzu(paramzze));
    this.zzMY = paramzze.getAccount();
    this.zzWJ = zzb(paramzze.zznw());
  }
  
  protected zzi(Context paramContext, Looper paramLooper, zzk paramzzk, int paramInt, zze paramzze, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    this(paramContext, paramLooper, paramzzk, paramInt, paramzze);
    this.zzaar = ((GoogleApiClient.ConnectionCallbacks)zzu.zzu(paramConnectionCallbacks));
    this.zzaas = ((GoogleApiClient.OnConnectionFailedListener)zzu.zzu(paramOnConnectionFailedListener));
  }
  
  private void zza(int paramInt, T paramT)
  {
    boolean bool = true;
    int i;
    int j;
    if (paramInt == 3)
    {
      i = 1;
      if (paramT == null) {
        break label115;
      }
      j = 1;
      label17:
      if (i != j) {
        break label121;
      }
    }
    for (;;)
    {
      zzu.zzV(bool);
      for (;;)
      {
        synchronized (this.zzqt)
        {
          this.zzaaq = paramInt;
          this.zzaan = paramT;
          switch (paramInt)
          {
          case 2: 
            return;
            zznH();
          }
        }
        zznG();
        continue;
        zznI();
      }
      i = 0;
      break;
      label115:
      j = 0;
      break label17;
      label121:
      bool = false;
    }
  }
  
  private void zza(GoogleApiClient.ConnectionProgressReportCallbacks paramConnectionProgressReportCallbacks)
  {
    this.zzaam = ((GoogleApiClient.ConnectionProgressReportCallbacks)zzu.zzb(paramConnectionProgressReportCallbacks, "Connection progress callbacks cannot be null."));
  }
  
  private boolean zza(int paramInt1, int paramInt2, T paramT)
  {
    synchronized (this.zzqt)
    {
      if (this.zzaaq != paramInt1) {
        return false;
      }
      zza(paramInt2, paramT);
      return true;
    }
  }
  
  private Set<Scope> zzb(Set<Scope> paramSet)
  {
    Set localSet = zza(paramSet);
    if (localSet == null) {
      return localSet;
    }
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext()) {
      if (!paramSet.contains((Scope)localIterator.next())) {
        throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
      }
    }
    return localSet;
  }
  
  private void zznH()
  {
    if (this.zzaap != null)
    {
      Log.e("GmsClient", "Calling connect() while still connected, missing disconnect() for " + getStartServiceAction());
      this.zzaak.zzb(getStartServiceAction(), this.zzaap, zzkQ());
      this.zzaau.incrementAndGet();
    }
    this.zzaap = new zze(this.zzaau.get());
    if (!this.zzaak.zza(getStartServiceAction(), this.zzaap, zzkQ()))
    {
      Log.e("GmsClient", "unable to connect to service: " + getStartServiceAction());
      this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzaau.get(), 9));
    }
  }
  
  private void zznI()
  {
    if (this.zzaap != null)
    {
      this.zzaak.zzb(getStartServiceAction(), this.zzaap, zzkQ());
      this.zzaap = null;
    }
  }
  
  public void connect(GoogleApiClient.ConnectionProgressReportCallbacks paramConnectionProgressReportCallbacks)
  {
    zza(paramConnectionProgressReportCallbacks);
    zza(2, null);
  }
  
  public void disconnect()
  {
    this.zzaau.incrementAndGet();
    synchronized (this.zzaao)
    {
      int j = this.zzaao.size();
      int i = 0;
      while (i < j)
      {
        ((zzc)this.zzaao.get(i)).zznR();
        i += 1;
      }
      this.zzaao.clear();
      zza(1, null);
      return;
    }
  }
  
  public void dump(String paramString, FileDescriptor arg2, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    for (;;)
    {
      synchronized (this.zzqt)
      {
        int i = this.zzaaq;
        paramArrayOfString = this.zzaan;
        paramPrintWriter.append(paramString).append("mConnectState=");
        switch (i)
        {
        default: 
          paramPrintWriter.print("UNKNOWN");
          paramPrintWriter.append(" mService=");
          if (paramArrayOfString != null) {
            break label137;
          }
          paramPrintWriter.println("null");
          return;
        }
      }
      paramPrintWriter.print("CONNECTING");
      continue;
      paramPrintWriter.print("CONNECTED");
      continue;
      paramPrintWriter.print("DISCONNECTING");
      continue;
      paramPrintWriter.print("DISCONNECTED");
    }
    label137:
    paramPrintWriter.append(getServiceDescriptor()).append("@").println(Integer.toHexString(System.identityHashCode(paramArrayOfString.asBinder())));
  }
  
  public final Context getContext()
  {
    return this.mContext;
  }
  
  public final Looper getLooper()
  {
    return this.zzWt;
  }
  
  public void getRemoteService(IAccountAccessor paramIAccountAccessor, Set<Scope> paramSet)
  {
    try
    {
      Object localObject = zzkR();
      localObject = new GetServiceRequest(this.zzaat).zzcb(this.mContext.getPackageName()).zzf((Bundle)localObject);
      if (paramSet != null) {
        ((GetServiceRequest)localObject).zzb(paramSet);
      }
      if (requiresSignIn()) {
        ((GetServiceRequest)localObject).zzb(zznt()).zzb(paramIAccountAccessor);
      }
      for (;;)
      {
        this.zzaal.zza(new zzd(this, this.zzaau.get()), (GetServiceRequest)localObject);
        return;
        if (requiresAccount()) {
          ((GetServiceRequest)localObject).zzb(this.zzMY);
        }
      }
      return;
    }
    catch (DeadObjectException paramIAccountAccessor)
    {
      Log.w("GmsClient", "service died");
      zzbs(1);
      return;
    }
    catch (RemoteException paramIAccountAccessor)
    {
      Log.w("GmsClient", "Remote exception occurred", paramIAccountAccessor);
    }
  }
  
  protected abstract String getServiceDescriptor();
  
  protected abstract String getStartServiceAction();
  
  public boolean isConnected()
  {
    for (;;)
    {
      synchronized (this.zzqt)
      {
        if (this.zzaaq == 3)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public boolean isConnecting()
  {
    for (;;)
    {
      synchronized (this.zzqt)
      {
        if (this.zzaaq == 2)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  protected void onConnectionFailed(ConnectionResult paramConnectionResult) {}
  
  protected void onConnectionSuspended(int paramInt) {}
  
  public boolean requiresAccount()
  {
    return false;
  }
  
  public boolean requiresSignIn()
  {
    return false;
  }
  
  public void validateAccount(IAccountAccessor paramIAccountAccessor)
  {
    Bundle localBundle = zznN();
    paramIAccountAccessor = new ValidateAccountRequest(paramIAccountAccessor, (Scope[])this.zzWJ.toArray(new Scope[this.zzWJ.size()]), this.mContext.getPackageName(), localBundle);
    try
    {
      this.zzaal.zza(new zzd(this, this.zzaau.get()), paramIAccountAccessor);
      return;
    }
    catch (DeadObjectException paramIAccountAccessor)
    {
      Log.w("GmsClient", "service died");
      zzbs(1);
      return;
    }
    catch (RemoteException paramIAccountAccessor)
    {
      Log.w("GmsClient", "Remote exception occurred", paramIAccountAccessor);
    }
  }
  
  protected abstract T zzT(IBinder paramIBinder);
  
  protected Set<Scope> zza(Set<Scope> paramSet)
  {
    return paramSet;
  }
  
  protected void zza(int paramInt1, Bundle paramBundle, int paramInt2)
  {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(5, paramInt2, -1, new zzi(paramInt1, paramBundle)));
  }
  
  protected void zza(int paramInt1, IBinder paramIBinder, Bundle paramBundle, int paramInt2)
  {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(1, paramInt2, -1, new zzg(paramInt1, paramIBinder, paramBundle)));
  }
  
  @Deprecated
  public final void zza(zzi<T>.zzc<?> paramzzi)
  {
    synchronized (this.zzaao)
    {
      this.zzaao.add(paramzzi);
      this.mHandler.sendMessage(this.mHandler.obtainMessage(2, this.zzaau.get(), -1, paramzzi));
      return;
    }
  }
  
  public void zzbs(int paramInt)
  {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(4, this.zzaau.get(), paramInt));
  }
  
  protected void zzbt(int paramInt)
  {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(6, paramInt, -1, new zzh()));
  }
  
  protected String zzkQ()
  {
    return this.zzXa.zznz();
  }
  
  protected Bundle zzkR()
  {
    return new Bundle();
  }
  
  public Bundle zzlM()
  {
    return null;
  }
  
  protected void zznG() {}
  
  public void zznJ()
  {
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
    if (i != 0)
    {
      zza(1, null);
      this.zzaam = new zzf();
      this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzaau.get(), i));
      return;
    }
    connect(new zzf());
  }
  
  protected final zze zznK()
  {
    return this.zzXa;
  }
  
  protected final void zznL()
  {
    if (!isConnected()) {
      throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
    }
  }
  
  public final T zznM()
    throws DeadObjectException
  {
    synchronized (this.zzqt)
    {
      if (this.zzaaq == 4) {
        throw new DeadObjectException();
      }
    }
    zznL();
    if (this.zzaan != null) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zza(bool, "Client is connected but service is null");
      IInterface localIInterface = this.zzaan;
      return localIInterface;
    }
  }
  
  protected Bundle zznN()
  {
    return null;
  }
  
  public final Account zznt()
  {
    if (this.zzMY != null) {
      return this.zzMY;
    }
    return new Account("<<default account>>", "com.google");
  }
  
  private abstract class zza
    extends zzi<T>.zzc<Boolean>
  {
    public final int statusCode;
    public final Bundle zzaaw;
    
    protected zza(int paramInt, Bundle paramBundle)
    {
      super(Boolean.valueOf(true));
      this.statusCode = paramInt;
      this.zzaaw = paramBundle;
    }
    
    protected void zzc(Boolean paramBoolean)
    {
      Object localObject = null;
      if (paramBoolean == null) {
        zzi.zza(zzi.this, 1, null);
      }
      do
      {
        return;
        switch (this.statusCode)
        {
        default: 
          zzi.zza(zzi.this, 1, null);
          paramBoolean = (Boolean)localObject;
          if (this.zzaaw != null) {
            paramBoolean = (PendingIntent)this.zzaaw.getParcelable("pendingIntent");
          }
          zzg(new ConnectionResult(this.statusCode, paramBoolean));
          return;
        }
      } while (zznO());
      zzi.zza(zzi.this, 1, null);
      zzg(new ConnectionResult(8, null));
      return;
      zzi.zza(zzi.this, 1, null);
      throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
    }
    
    protected abstract void zzg(ConnectionResult paramConnectionResult);
    
    protected abstract boolean zznO();
    
    protected void zznP() {}
  }
  
  final class zzb
    extends Handler
  {
    public zzb(Looper paramLooper)
    {
      super();
    }
    
    private void zza(Message paramMessage)
    {
      paramMessage = (zzi.zzc)paramMessage.obj;
      paramMessage.zznP();
      paramMessage.unregister();
    }
    
    private boolean zzb(Message paramMessage)
    {
      return (paramMessage.what == 2) || (paramMessage.what == 1) || (paramMessage.what == 5) || (paramMessage.what == 6);
    }
    
    public void handleMessage(Message paramMessage)
    {
      if (zzi.this.zzaau.get() != paramMessage.arg1)
      {
        if (zzb(paramMessage)) {
          zza(paramMessage);
        }
        return;
      }
      if (((paramMessage.what == 1) || (paramMessage.what == 5) || (paramMessage.what == 6)) && (!zzi.this.isConnecting()))
      {
        zza(paramMessage);
        return;
      }
      if (paramMessage.what == 3)
      {
        paramMessage = new ConnectionResult(paramMessage.arg2, null);
        zzi.zza(zzi.this).onReportServiceBinding(paramMessage);
        zzi.this.onConnectionFailed(paramMessage);
        return;
      }
      if (paramMessage.what == 4)
      {
        zzi.zza(zzi.this, 4, null);
        if (zzi.zzb(zzi.this) != null) {
          zzi.zzb(zzi.this).onConnectionSuspended(paramMessage.arg2);
        }
        zzi.this.onConnectionSuspended(paramMessage.arg2);
        zzi.zza(zzi.this, 4, 1, null);
        return;
      }
      if ((paramMessage.what == 2) && (!zzi.this.isConnected()))
      {
        zza(paramMessage);
        return;
      }
      if (zzb(paramMessage))
      {
        ((zzi.zzc)paramMessage.obj).zznQ();
        return;
      }
      Log.wtf("GmsClient", "Don't know how to handle this message.");
    }
  }
  
  protected abstract class zzc<TListener>
  {
    private TListener mListener;
    private boolean zzaay;
    
    public zzc()
    {
      Object localObject;
      this.mListener = localObject;
      this.zzaay = false;
    }
    
    public void unregister()
    {
      zznR();
      synchronized (zzi.zzc(zzi.this))
      {
        zzi.zzc(zzi.this).remove(this);
        return;
      }
    }
    
    protected abstract void zznP();
    
    /* Error */
    public void zznQ()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 24	com/google/android/gms/common/internal/zzi$zzc:mListener	Ljava/lang/Object;
      //   6: astore_1
      //   7: aload_0
      //   8: getfield 26	com/google/android/gms/common/internal/zzi$zzc:zzaay	Z
      //   11: ifeq +33 -> 44
      //   14: ldc 48
      //   16: new 50	java/lang/StringBuilder
      //   19: dup
      //   20: invokespecial 51	java/lang/StringBuilder:<init>	()V
      //   23: ldc 53
      //   25: invokevirtual 57	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   28: aload_0
      //   29: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   32: ldc 62
      //   34: invokevirtual 57	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   37: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   40: invokestatic 72	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
      //   43: pop
      //   44: aload_0
      //   45: monitorexit
      //   46: aload_1
      //   47: ifnull +34 -> 81
      //   50: aload_0
      //   51: aload_1
      //   52: invokevirtual 76	com/google/android/gms/common/internal/zzi$zzc:zzr	(Ljava/lang/Object;)V
      //   55: aload_0
      //   56: monitorenter
      //   57: aload_0
      //   58: iconst_1
      //   59: putfield 26	com/google/android/gms/common/internal/zzi$zzc:zzaay	Z
      //   62: aload_0
      //   63: monitorexit
      //   64: aload_0
      //   65: invokevirtual 78	com/google/android/gms/common/internal/zzi$zzc:unregister	()V
      //   68: return
      //   69: astore_1
      //   70: aload_0
      //   71: monitorexit
      //   72: aload_1
      //   73: athrow
      //   74: astore_1
      //   75: aload_0
      //   76: invokevirtual 80	com/google/android/gms/common/internal/zzi$zzc:zznP	()V
      //   79: aload_1
      //   80: athrow
      //   81: aload_0
      //   82: invokevirtual 80	com/google/android/gms/common/internal/zzi$zzc:zznP	()V
      //   85: goto -30 -> 55
      //   88: astore_1
      //   89: aload_0
      //   90: monitorexit
      //   91: aload_1
      //   92: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	93	0	this	zzc
      //   6	46	1	localObject1	Object
      //   69	4	1	localObject2	Object
      //   74	6	1	localRuntimeException	RuntimeException
      //   88	4	1	localObject3	Object
      // Exception table:
      //   from	to	target	type
      //   2	44	69	finally
      //   44	46	69	finally
      //   70	72	69	finally
      //   50	55	74	java/lang/RuntimeException
      //   57	64	88	finally
      //   89	91	88	finally
    }
    
    public void zznR()
    {
      try
      {
        this.mListener = null;
        return;
      }
      finally {}
    }
    
    protected abstract void zzr(TListener paramTListener);
  }
  
  public static final class zzd
    extends zzo.zza
  {
    private final int zzaaA;
    private zzi zzaaz;
    
    public zzd(zzi paramzzi, int paramInt)
    {
      this.zzaaz = paramzzi;
      this.zzaaA = paramInt;
    }
    
    private void zznS()
    {
      this.zzaaz = null;
    }
    
    public void zza(int paramInt, IBinder paramIBinder, Bundle paramBundle)
    {
      zzu.zzb(this.zzaaz, "onPostInitComplete can be called only once per call to getRemoteService");
      this.zzaaz.zza(paramInt, paramIBinder, paramBundle, this.zzaaA);
      zznS();
    }
    
    public void zzb(int paramInt, Bundle paramBundle)
    {
      zzu.zzb(this.zzaaz, "onAccountValidationComplete can be called only once per call to validateAccount");
      this.zzaaz.zza(paramInt, paramBundle, this.zzaaA);
      zznS();
    }
  }
  
  public final class zze
    implements ServiceConnection
  {
    private final int zzaaA;
    
    public zze(int paramInt)
    {
      this.zzaaA = paramInt;
    }
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      zzu.zzb(paramIBinder, "Expecting a valid IBinder");
      zzi.zza(zzi.this, zzp.zza.zzaG(paramIBinder));
      zzi.this.zzbt(this.zzaaA);
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      zzi.this.mHandler.sendMessage(zzi.this.mHandler.obtainMessage(4, this.zzaaA, 1));
    }
  }
  
  protected class zzf
    implements GoogleApiClient.ConnectionProgressReportCallbacks
  {
    public zzf() {}
    
    public void onReportAccountValidation(ConnectionResult paramConnectionResult)
    {
      throw new IllegalStateException("Legacy GmsClient received onReportAccountValidation callback.");
    }
    
    public void onReportServiceBinding(ConnectionResult paramConnectionResult)
    {
      if (paramConnectionResult.isSuccess()) {
        zzi.this.getRemoteService(null, zzi.zzd(zzi.this));
      }
      while (zzi.zze(zzi.this) == null) {
        return;
      }
      zzi.zze(zzi.this).onConnectionFailed(paramConnectionResult);
    }
  }
  
  protected final class zzg
    extends zzi<T>.zza
  {
    public final IBinder zzaaB;
    
    public zzg(int paramInt, IBinder paramIBinder, Bundle paramBundle)
    {
      super(paramInt, paramBundle);
      this.zzaaB = paramIBinder;
    }
    
    protected void zzg(ConnectionResult paramConnectionResult)
    {
      if (zzi.zze(zzi.this) != null) {
        zzi.zze(zzi.this).onConnectionFailed(paramConnectionResult);
      }
      zzi.this.onConnectionFailed(paramConnectionResult);
    }
    
    protected boolean zznO()
    {
      do
      {
        try
        {
          String str = this.zzaaB.getInterfaceDescriptor();
          if (!zzi.this.getServiceDescriptor().equals(str))
          {
            Log.e("GmsClient", "service descriptor mismatch: " + zzi.this.getServiceDescriptor() + " vs. " + str);
            return false;
          }
        }
        catch (RemoteException localRemoteException)
        {
          Log.w("GmsClient", "service probably died");
          return false;
        }
        localObject = zzi.this.zzT(this.zzaaB);
      } while ((localObject == null) || (!zzi.zza(zzi.this, 2, 3, (IInterface)localObject)));
      Object localObject = zzi.this.zzlM();
      if (zzi.zzb(zzi.this) != null) {
        zzi.zzb(zzi.this).onConnected((Bundle)localObject);
      }
      GooglePlayServicesUtil.zzac(zzi.zzf(zzi.this));
      return true;
    }
  }
  
  protected final class zzh
    extends zzi<T>.zza
  {
    public zzh()
    {
      super(0, null);
    }
    
    protected void zzg(ConnectionResult paramConnectionResult)
    {
      zzi.zza(zzi.this).onReportServiceBinding(paramConnectionResult);
      zzi.this.onConnectionFailed(paramConnectionResult);
    }
    
    protected boolean zznO()
    {
      zzi.zza(zzi.this).onReportServiceBinding(ConnectionResult.zzVG);
      return true;
    }
  }
  
  protected final class zzi
    extends zzi<T>.zza
  {
    public zzi(int paramInt, Bundle paramBundle)
    {
      super(paramInt, paramBundle);
    }
    
    protected void zzg(ConnectionResult paramConnectionResult)
    {
      zzi.zza(zzi.this).onReportAccountValidation(paramConnectionResult);
      zzi.this.onConnectionFailed(paramConnectionResult);
    }
    
    protected boolean zznO()
    {
      zzi.zza(zzi.this).onReportAccountValidation(ConnectionResult.zzVG);
      return true;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/zzi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.google.android.gms.gcm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.util.HashSet;
import java.util.Set;

public abstract class GcmTaskService
  extends Service
{
  public static final String SERVICE_ACTION_EXECUTE_TASK = "com.google.android.gms.gcm.ACTION_TASK_READY";
  public static final String SERVICE_ACTION_INITIALIZE = "com.google.android.gms.gcm.SERVICE_ACTION_INITIALIZE";
  public static final String SERVICE_PERMISSION = "com.google.android.gms.permission.BIND_NETWORK_TASK_SERVICE";
  private final Set<String> zzavL = new HashSet();
  private int zzavM;
  
  private void zzdc(String paramString)
  {
    synchronized (this.zzavL)
    {
      this.zzavL.remove(paramString);
      if (this.zzavL.size() == 0) {
        stopSelf(this.zzavM);
      }
      return;
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onInitializeTasks() {}
  
  public abstract int onRunTask(TaskParams paramTaskParams);
  
  public int onStartCommand(Intent arg1, int paramInt1, int paramInt2)
  {
    if ("com.google.android.gms.gcm.ACTION_TASK_READY".equals(???.getAction()))
    {
      str = ???.getStringExtra("tag");
      localParcelable = ???.getParcelableExtra("callback");
      if ((localParcelable == null) || (!(localParcelable instanceof PendingCallback))) {
        Log.e("GcmTaskService", getPackageName() + " " + str + ": Could not process request, invalid callback.");
      }
    }
    while (!"com.google.android.gms.gcm.SERVICE_ACTION_INITIALIZE".equals(???.getAction()))
    {
      String str;
      Parcelable localParcelable;
      return 2;
      synchronized (this.zzavL)
      {
        this.zzavL.add(str);
        stopSelf(this.zzavM);
        this.zzavM = paramInt2;
        new zza(str, ((PendingCallback)localParcelable).getIBinder()).start();
        return 2;
      }
    }
    onInitializeTasks();
    synchronized (this.zzavL)
    {
      this.zzavM = paramInt2;
      if (this.zzavL.size() == 0) {
        stopSelf(this.zzavM);
      }
      return 2;
    }
  }
  
  private class zza
    extends Thread
  {
    private final String mTag;
    private final zzb zzavN;
    
    zza(String paramString, IBinder paramIBinder)
    {
      this.mTag = paramString;
      this.zzavN = zzb.zza.zzbN(paramIBinder);
    }
    
    public void run()
    {
      int i = GcmTaskService.this.onRunTask(new TaskParams(this.mTag));
      try
      {
        this.zzavN.zzgg(i);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        Log.e("GcmTaskService", "Error reporting result of operation to scheduler for " + this.mTag);
        return;
      }
      finally
      {
        GcmTaskService.zza(GcmTaskService.this, this.mTag);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/gcm/GcmTaskService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
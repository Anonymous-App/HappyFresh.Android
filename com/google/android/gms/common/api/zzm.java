package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzu;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class zzm
  extends Fragment
  implements DialogInterface.OnCancelListener
{
  private boolean mStarted;
  private boolean zzXV;
  private int zzXW = -1;
  private ConnectionResult zzXX;
  private final Handler zzXY = new Handler(Looper.getMainLooper());
  private final SparseArray<zza> zzXZ = new SparseArray();
  
  public static zzm zza(FragmentActivity paramFragmentActivity)
  {
    zzu.zzbY("Must be called from main thread of process");
    FragmentManager localFragmentManager = paramFragmentActivity.getSupportFragmentManager();
    try
    {
      zzm localzzm = (zzm)localFragmentManager.findFragmentByTag("GmsSupportLifecycleFragment");
      if (localzzm != null)
      {
        paramFragmentActivity = localzzm;
        if (!localzzm.isRemoving()) {}
      }
      else
      {
        paramFragmentActivity = new zzm();
        localFragmentManager.beginTransaction().add(paramFragmentActivity, "GmsSupportLifecycleFragment").commit();
        localFragmentManager.executePendingTransactions();
      }
      return paramFragmentActivity;
    }
    catch (ClassCastException paramFragmentActivity)
    {
      throw new IllegalStateException("Fragment with tag GmsSupportLifecycleFragment is not a SupportLifecycleFragment", paramFragmentActivity);
    }
  }
  
  private void zza(int paramInt, ConnectionResult paramConnectionResult)
  {
    Log.w("GmsSupportLifecycleFragment", "Unresolved error while connecting client. Stopping auto-manage.");
    Object localObject = (zza)this.zzXZ.get(paramInt);
    if (localObject != null)
    {
      zzbb(paramInt);
      localObject = ((zza)localObject).zzYc;
      if (localObject != null) {
        ((GoogleApiClient.OnConnectionFailedListener)localObject).onConnectionFailed(paramConnectionResult);
      }
    }
    zzmV();
  }
  
  private void zzmV()
  {
    this.zzXV = false;
    this.zzXW = -1;
    this.zzXX = null;
    int i = 0;
    while (i < this.zzXZ.size())
    {
      ((zza)this.zzXZ.valueAt(i)).zzYb.connect();
      i += 1;
    }
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    super.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    int i = 0;
    while (i < this.zzXZ.size())
    {
      ((zza)this.zzXZ.valueAt(i)).dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      i += 1;
    }
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    int i = 1;
    switch (paramInt1)
    {
    default: 
      paramInt1 = 0;
    }
    for (;;)
    {
      if (paramInt1 == 0) {
        break label66;
      }
      zzmV();
      return;
      if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) != 0) {
        break;
      }
      paramInt1 = i;
      continue;
      if (paramInt2 != -1) {
        break;
      }
      paramInt1 = i;
    }
    label66:
    zza(this.zzXW, this.zzXX);
  }
  
  public void onCancel(DialogInterface paramDialogInterface)
  {
    zza(this.zzXW, new ConnectionResult(13, null));
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.zzXV = paramBundle.getBoolean("resolving_error", false);
      this.zzXW = paramBundle.getInt("failed_client_id", -1);
      if (this.zzXW >= 0) {
        this.zzXX = new ConnectionResult(paramBundle.getInt("failed_status"), (PendingIntent)paramBundle.getParcelable("failed_resolution"));
      }
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("resolving_error", this.zzXV);
    if (this.zzXW >= 0)
    {
      paramBundle.putInt("failed_client_id", this.zzXW);
      paramBundle.putInt("failed_status", this.zzXX.getErrorCode());
      paramBundle.putParcelable("failed_resolution", this.zzXX.getResolution());
    }
  }
  
  public void onStart()
  {
    super.onStart();
    this.mStarted = true;
    if (!this.zzXV)
    {
      int i = 0;
      while (i < this.zzXZ.size())
      {
        ((zza)this.zzXZ.valueAt(i)).zzYb.connect();
        i += 1;
      }
    }
  }
  
  public void onStop()
  {
    super.onStop();
    this.mStarted = false;
    int i = 0;
    while (i < this.zzXZ.size())
    {
      ((zza)this.zzXZ.valueAt(i)).zzYb.disconnect();
      i += 1;
    }
  }
  
  public void zza(int paramInt, GoogleApiClient paramGoogleApiClient, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    zzu.zzb(paramGoogleApiClient, "GoogleApiClient instance cannot be null");
    if (this.zzXZ.indexOfKey(paramInt) < 0) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zza(bool, "Already managing a GoogleApiClient with id " + paramInt);
      paramOnConnectionFailedListener = new zza(paramInt, paramGoogleApiClient, paramOnConnectionFailedListener);
      this.zzXZ.put(paramInt, paramOnConnectionFailedListener);
      if ((this.mStarted) && (!this.zzXV)) {
        paramGoogleApiClient.connect();
      }
      return;
    }
  }
  
  public void zzbb(int paramInt)
  {
    zza localzza = (zza)this.zzXZ.get(paramInt);
    this.zzXZ.remove(paramInt);
    if (localzza != null) {
      localzza.zzmW();
    }
  }
  
  private class zza
    implements GoogleApiClient.OnConnectionFailedListener
  {
    public final int zzYa;
    public final GoogleApiClient zzYb;
    public final GoogleApiClient.OnConnectionFailedListener zzYc;
    
    public zza(int paramInt, GoogleApiClient paramGoogleApiClient, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      this.zzYa = paramInt;
      this.zzYb = paramGoogleApiClient;
      this.zzYc = paramOnConnectionFailedListener;
      paramGoogleApiClient.registerConnectionFailedListener(this);
    }
    
    public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
    {
      paramPrintWriter.append(paramString).append("GoogleApiClient #").print(this.zzYa);
      paramPrintWriter.println(":");
      this.zzYb.dump(paramString + "  ", paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    }
    
    public void onConnectionFailed(ConnectionResult paramConnectionResult)
    {
      zzm.zzd(zzm.this).post(new zzm.zzb(zzm.this, this.zzYa, paramConnectionResult));
    }
    
    public void zzmW()
    {
      this.zzYb.unregisterConnectionFailedListener(this);
      this.zzYb.disconnect();
    }
  }
  
  private class zzb
    implements Runnable
  {
    private final int zzYe;
    private final ConnectionResult zzYf;
    
    public zzb(int paramInt, ConnectionResult paramConnectionResult)
    {
      this.zzYe = paramInt;
      this.zzYf = paramConnectionResult;
    }
    
    public void run()
    {
      if ((!zzm.zza(zzm.this)) || (zzm.zzb(zzm.this))) {
        return;
      }
      zzm.zza(zzm.this, true);
      zzm.zza(zzm.this, this.zzYe);
      zzm.zza(zzm.this, this.zzYf);
      if (this.zzYf.hasResolution()) {
        try
        {
          int i = zzm.this.getActivity().getSupportFragmentManager().getFragments().indexOf(zzm.this);
          this.zzYf.startResolutionForResult(zzm.this.getActivity(), (i + 1 << 16) + 1);
          return;
        }
        catch (IntentSender.SendIntentException localSendIntentException)
        {
          zzm.zzc(zzm.this);
          return;
        }
      }
      if (GooglePlayServicesUtil.isUserRecoverableError(this.zzYf.getErrorCode()))
      {
        GooglePlayServicesUtil.showErrorDialogFragment(this.zzYf.getErrorCode(), zzm.this.getActivity(), zzm.this, 2, zzm.this);
        return;
      }
      zzm.zza(zzm.this, this.zzYe, this.zzYf);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/zzm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
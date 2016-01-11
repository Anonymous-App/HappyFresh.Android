package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
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
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzu;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class zzn
  extends Fragment
  implements DialogInterface.OnCancelListener, LoaderManager.LoaderCallbacks<ConnectionResult>
{
  private boolean zzXV;
  private int zzXW = -1;
  private ConnectionResult zzXX;
  private final Handler zzXY = new Handler(Looper.getMainLooper());
  private final SparseArray<zzb> zzXZ = new SparseArray();
  
  private void zza(int paramInt, ConnectionResult paramConnectionResult)
  {
    Log.w("GmsSupportLoaderLifecycleFragment", "Unresolved error while connecting client. Stopping auto-manage.");
    Object localObject = (zzb)this.zzXZ.get(paramInt);
    if (localObject != null)
    {
      zzbb(paramInt);
      localObject = ((zzb)localObject).zzYc;
      if (localObject != null) {
        ((GoogleApiClient.OnConnectionFailedListener)localObject).onConnectionFailed(paramConnectionResult);
      }
    }
    zzmV();
  }
  
  public static zzn zzb(FragmentActivity paramFragmentActivity)
  {
    zzu.zzbY("Must be called from main thread of process");
    FragmentManager localFragmentManager = paramFragmentActivity.getSupportFragmentManager();
    try
    {
      zzn localzzn = (zzn)localFragmentManager.findFragmentByTag("GmsSupportLoaderLifecycleFragment");
      if (localzzn != null)
      {
        paramFragmentActivity = localzzn;
        if (!localzzn.isRemoving()) {}
      }
      else
      {
        paramFragmentActivity = new zzn();
        localFragmentManager.beginTransaction().add(paramFragmentActivity, "GmsSupportLoaderLifecycleFragment").commit();
        localFragmentManager.executePendingTransactions();
      }
      return paramFragmentActivity;
    }
    catch (ClassCastException paramFragmentActivity)
    {
      throw new IllegalStateException("Fragment with tag GmsSupportLoaderLifecycleFragment is not a SupportLoaderLifecycleFragment", paramFragmentActivity);
    }
  }
  
  private void zzb(int paramInt, ConnectionResult paramConnectionResult)
  {
    if (!this.zzXV)
    {
      this.zzXV = true;
      this.zzXW = paramInt;
      this.zzXX = paramConnectionResult;
      this.zzXY.post(new zzc(paramInt, paramConnectionResult));
    }
  }
  
  private void zzmV()
  {
    int i = 0;
    this.zzXV = false;
    this.zzXW = -1;
    this.zzXX = null;
    LoaderManager localLoaderManager = getLoaderManager();
    while (i < this.zzXZ.size())
    {
      int j = this.zzXZ.keyAt(i);
      zza localzza = zzbd(j);
      if ((localzza != null) && (localzza.zzmX()))
      {
        localLoaderManager.destroyLoader(j);
        localLoaderManager.initLoader(j, null, this);
      }
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
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    int i = 0;
    if (i < this.zzXZ.size())
    {
      int j = this.zzXZ.keyAt(i);
      paramActivity = zzbd(j);
      if ((paramActivity != null) && (((zzb)this.zzXZ.valueAt(i)).zzYb != paramActivity.zzYb)) {
        getLoaderManager().restartLoader(j, null, this);
      }
      for (;;)
      {
        i += 1;
        break;
        getLoaderManager().initLoader(j, null, this);
      }
    }
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
  
  public Loader<ConnectionResult> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new zza(getActivity(), ((zzb)this.zzXZ.get(paramInt)).zzYb);
  }
  
  public void onLoaderReset(Loader<ConnectionResult> paramLoader) {}
  
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
    if (!this.zzXV)
    {
      int i = 0;
      while (i < this.zzXZ.size())
      {
        getLoaderManager().initLoader(this.zzXZ.keyAt(i), null, this);
        i += 1;
      }
    }
  }
  
  public void zza(int paramInt, GoogleApiClient paramGoogleApiClient, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    zzu.zzb(paramGoogleApiClient, "GoogleApiClient instance cannot be null");
    if (this.zzXZ.indexOfKey(paramInt) < 0) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zza(bool, "Already managing a GoogleApiClient with id " + paramInt);
      paramGoogleApiClient = new zzb(paramGoogleApiClient, paramOnConnectionFailedListener, null);
      this.zzXZ.put(paramInt, paramGoogleApiClient);
      if (getActivity() != null)
      {
        LoaderManager.enableDebugLogging(false);
        getLoaderManager().initLoader(paramInt, null, this);
      }
      return;
    }
  }
  
  public void zza(Loader<ConnectionResult> paramLoader, ConnectionResult paramConnectionResult)
  {
    if (!paramConnectionResult.isSuccess()) {
      zzb(paramLoader.getId(), paramConnectionResult);
    }
  }
  
  public void zzbb(int paramInt)
  {
    this.zzXZ.remove(paramInt);
    getLoaderManager().destroyLoader(paramInt);
  }
  
  public GoogleApiClient zzbc(int paramInt)
  {
    if (getActivity() != null)
    {
      zza localzza = zzbd(paramInt);
      if (localzza != null) {
        return localzza.zzYb;
      }
    }
    return null;
  }
  
  zza zzbd(int paramInt)
  {
    try
    {
      zza localzza = (zza)getLoaderManager().getLoader(paramInt);
      return localzza;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new IllegalStateException("Unknown loader in SupportLoaderLifecycleFragment", localClassCastException);
    }
  }
  
  static class zza
    extends Loader<ConnectionResult>
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
  {
    public final GoogleApiClient zzYb;
    private boolean zzYg;
    private ConnectionResult zzYh;
    
    public zza(Context paramContext, GoogleApiClient paramGoogleApiClient)
    {
      super();
      this.zzYb = paramGoogleApiClient;
    }
    
    private void zzf(ConnectionResult paramConnectionResult)
    {
      this.zzYh = paramConnectionResult;
      if ((isStarted()) && (!isAbandoned())) {
        deliverResult(paramConnectionResult);
      }
    }
    
    public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
    {
      super.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      this.zzYb.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    }
    
    public void onConnected(Bundle paramBundle)
    {
      this.zzYg = false;
      zzf(ConnectionResult.zzVG);
    }
    
    public void onConnectionFailed(ConnectionResult paramConnectionResult)
    {
      this.zzYg = true;
      zzf(paramConnectionResult);
    }
    
    public void onConnectionSuspended(int paramInt) {}
    
    protected void onReset()
    {
      this.zzYh = null;
      this.zzYg = false;
      this.zzYb.unregisterConnectionCallbacks(this);
      this.zzYb.unregisterConnectionFailedListener(this);
      this.zzYb.disconnect();
    }
    
    protected void onStartLoading()
    {
      super.onStartLoading();
      this.zzYb.registerConnectionCallbacks(this);
      this.zzYb.registerConnectionFailedListener(this);
      if (this.zzYh != null) {
        deliverResult(this.zzYh);
      }
      if ((!this.zzYb.isConnected()) && (!this.zzYb.isConnecting()) && (!this.zzYg)) {
        this.zzYb.connect();
      }
    }
    
    protected void onStopLoading()
    {
      this.zzYb.disconnect();
    }
    
    public boolean zzmX()
    {
      return this.zzYg;
    }
  }
  
  private static class zzb
  {
    public final GoogleApiClient zzYb;
    public final GoogleApiClient.OnConnectionFailedListener zzYc;
    
    private zzb(GoogleApiClient paramGoogleApiClient, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      this.zzYb = paramGoogleApiClient;
      this.zzYc = paramOnConnectionFailedListener;
    }
  }
  
  private class zzc
    implements Runnable
  {
    private final int zzYe;
    private final ConnectionResult zzYf;
    
    public zzc(int paramInt, ConnectionResult paramConnectionResult)
    {
      this.zzYe = paramInt;
      this.zzYf = paramConnectionResult;
    }
    
    public void run()
    {
      if (this.zzYf.hasResolution()) {
        try
        {
          int i = zzn.this.getActivity().getSupportFragmentManager().getFragments().indexOf(zzn.this);
          this.zzYf.startResolutionForResult(zzn.this.getActivity(), (i + 1 << 16) + 1);
          return;
        }
        catch (IntentSender.SendIntentException localSendIntentException)
        {
          zzn.zza(zzn.this);
          return;
        }
      }
      if (GooglePlayServicesUtil.isUserRecoverableError(this.zzYf.getErrorCode()))
      {
        GooglePlayServicesUtil.showErrorDialogFragment(this.zzYf.getErrorCode(), zzn.this.getActivity(), zzn.this, 2, zzn.this);
        return;
      }
      zzn.zza(zzn.this, this.zzYe, this.zzYf);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/zzn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
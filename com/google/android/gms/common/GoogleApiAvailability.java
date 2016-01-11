package com.google.android.gms.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;

public class GoogleApiAvailability
{
  public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
  public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE;
  private static final GoogleApiAvailability zzVI = new GoogleApiAvailability();
  
  public static GoogleApiAvailability getInstance()
  {
    return zzVI;
  }
  
  public Dialog getErrorDialog(Activity paramActivity, int paramInt1, int paramInt2)
  {
    return GooglePlayServicesUtil.getErrorDialog(paramInt1, paramActivity, paramInt2);
  }
  
  public Dialog getErrorDialog(Activity paramActivity, int paramInt1, int paramInt2, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    return GooglePlayServicesUtil.getErrorDialog(paramInt1, paramActivity, paramInt2, paramOnCancelListener);
  }
  
  public PendingIntent getErrorResolutionPendingIntent(Context paramContext, int paramInt1, int paramInt2)
  {
    return GooglePlayServicesUtil.getErrorPendingIntent(paramInt1, paramContext, paramInt2);
  }
  
  public final String getErrorString(int paramInt)
  {
    return GooglePlayServicesUtil.getErrorString(paramInt);
  }
  
  public String getOpenSourceSoftwareLicenseInfo(Context paramContext)
  {
    return GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(paramContext);
  }
  
  public int isGooglePlayServicesAvailable(Context paramContext)
  {
    int j = GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext);
    int i = j;
    if (GooglePlayServicesUtil.zze(paramContext, j)) {
      i = 18;
    }
    return i;
  }
  
  public final boolean isUserResolvableError(int paramInt)
  {
    return GooglePlayServicesUtil.isUserRecoverableError(paramInt);
  }
  
  public boolean showErrorDialogFragment(Activity paramActivity, int paramInt1, int paramInt2)
  {
    return GooglePlayServicesUtil.showErrorDialogFragment(paramInt1, paramActivity, paramInt2);
  }
  
  public boolean showErrorDialogFragment(Activity paramActivity, int paramInt1, int paramInt2, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    return GooglePlayServicesUtil.showErrorDialogFragment(paramInt1, paramActivity, paramInt2, paramOnCancelListener);
  }
  
  public void showErrorNotification(Context paramContext, int paramInt)
  {
    GooglePlayServicesUtil.showErrorNotification(paramInt, paramContext);
  }
  
  public void zzZ(Context paramContext)
    throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException
  {
    GooglePlayServicesUtil.zzY(paramContext);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/GoogleApiAvailability.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
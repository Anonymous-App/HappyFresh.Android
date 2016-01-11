package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

public class zzg
  implements DialogInterface.OnClickListener
{
  private final Activity mActivity;
  private final Intent mIntent;
  private final Fragment zzZX;
  private final int zzZY;
  
  public zzg(Activity paramActivity, Intent paramIntent, int paramInt)
  {
    this.mActivity = paramActivity;
    this.zzZX = null;
    this.mIntent = paramIntent;
    this.zzZY = paramInt;
  }
  
  public zzg(Fragment paramFragment, Intent paramIntent, int paramInt)
  {
    this.mActivity = null;
    this.zzZX = paramFragment;
    this.mIntent = paramIntent;
    this.zzZY = paramInt;
  }
  
  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    try
    {
      if ((this.mIntent != null) && (this.zzZX != null)) {
        this.zzZX.startActivityForResult(this.mIntent, this.zzZY);
      }
      for (;;)
      {
        paramDialogInterface.dismiss();
        return;
        if (this.mIntent != null) {
          this.mActivity.startActivityForResult(this.mIntent, this.zzZY);
        }
      }
      return;
    }
    catch (ActivityNotFoundException paramDialogInterface)
    {
      Log.e("SettingsRedirect", "Can't redirect to app settings for Google Play services");
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/zzg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
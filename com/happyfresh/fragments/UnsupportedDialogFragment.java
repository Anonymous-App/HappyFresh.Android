package com.happyfresh.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.managers.LocationManager;
import com.happyfresh.models.SubDistrict;

public class UnsupportedDialogFragment
  extends BaseDialogFragment
{
  public static final String LAT = "LAT";
  public static final String LON = "LON";
  public static final String SUB_DISTRICT = "SUB_DISTRICT";
  public static final String TITLE = "TITLE";
  @InjectView(2131558617)
  TextView mDialogTitle;
  private boolean mDismiss = false;
  private ICartApplication mICartApplication;
  private Double mLatitude;
  private Double mLongitude;
  private DialogInterface.OnDismissListener mOnDismissListener;
  private boolean mOnSaveInstance = false;
  private Long mSubDistrict;
  private String mTitle;
  
  public static UnsupportedDialogFragment newInstance(String paramString, SubDistrict paramSubDistrict, Double paramDouble1, Double paramDouble2)
  {
    UnsupportedDialogFragment localUnsupportedDialogFragment = new UnsupportedDialogFragment();
    Bundle localBundle = new Bundle();
    if (paramSubDistrict != null)
    {
      localBundle.putString("TITLE", paramString);
      localBundle.putLong("SUB_DISTRICT", paramSubDistrict.remoteId.longValue());
    }
    if (paramDouble1 != null) {
      localBundle.putDouble("LAT", paramDouble1.doubleValue());
    }
    if (paramDouble2 != null) {
      localBundle.putDouble("LON", paramDouble2.doubleValue());
    }
    localUnsupportedDialogFragment.setArguments(localBundle);
    return localUnsupportedDialogFragment;
  }
  
  public boolean changeContent(String paramString, SubDistrict paramSubDistrict, Double paramDouble1, Double paramDouble2)
  {
    if ((!isVisible()) || (this.mDialogTitle == null)) {
      return false;
    }
    this.mTitle = paramString;
    this.mDialogTitle.setText(this.mTitle);
    if (paramSubDistrict != null) {}
    for (paramString = paramSubDistrict.remoteId;; paramString = null)
    {
      this.mSubDistrict = paramString;
      this.mLatitude = paramDouble1;
      this.mLongitude = paramDouble2;
      return true;
    }
  }
  
  @OnClick({2131558717})
  void chooseOtherLocation()
  {
    if (this.mOnDismissListener != null) {
      this.mOnDismissListener.onDismiss(getDialog());
    }
    dismissDelayed();
  }
  
  @OnClick({2131558711})
  void close()
  {
    dismissDelayed();
    sendTracker("Choose Location");
    if (this.mOnDismissListener != null) {
      this.mOnDismissListener.onDismiss(null);
    }
  }
  
  public void dismissDelayed()
  {
    if (this.mDismiss) {
      return;
    }
    this.mDismiss = true;
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        if ((UnsupportedDialogFragment.this.getActivity() == null) || (UnsupportedDialogFragment.this.getFragmentManager() == null)) {}
        while (UnsupportedDialogFragment.this.mOnSaveInstance) {
          return;
        }
        UnsupportedDialogFragment.this.dismiss();
      }
    }, 100L);
  }
  
  public boolean isDismiss()
  {
    return this.mDismiss;
  }
  
  @OnClick({2131558716})
  void notified()
  {
    Object localObject = ICartApplication.get(this);
    if (this.mSubDistrict != null) {}
    for (localObject = ((ICartApplication)localObject).getLocationManager().showGetNotifedDialog(getActivity(), 0.0D, 0.0D, this.mSubDistrict.longValue());; localObject = ((ICartApplication)localObject).getLocationManager().showGetNotifedDialog(getActivity(), this.mLatitude.doubleValue(), this.mLongitude.doubleValue(), -1L))
    {
      sendTracker("Request Area Notification");
      ((MaterialDialog)localObject).setOnDismissListener(new DialogInterface.OnDismissListener()
      {
        public void onDismiss(DialogInterface paramAnonymousDialogInterface)
        {
          UnsupportedDialogFragment.this.sendTracker("Choose Location");
          if (UnsupportedDialogFragment.this.mOnDismissListener != null) {
            UnsupportedDialogFragment.this.mOnDismissListener.onDismiss(null);
          }
        }
      });
      dismissDelayed();
      return;
    }
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mTitle = getArguments().getString("TITLE");
    this.mDialogTitle.setText(this.mTitle);
    this.mSubDistrict = Long.valueOf(getArguments().getLong("SUB_DISTRICT"));
    this.mLatitude = Double.valueOf(getArguments().getDouble("LAT"));
    this.mLongitude = Double.valueOf(getArguments().getDouble("LON"));
  }
  
  public void onCancel(DialogInterface paramDialogInterface)
  {
    super.onCancel(paramDialogInterface);
    sendTracker("Choose Location");
    if (this.mOnDismissListener != null) {
      this.mOnDismissListener.onDismiss(null);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mICartApplication = ICartApplication.get(this);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903122, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    getDialog().getWindow().requestFeature(1);
    return paramLayoutInflater;
  }
  
  public void onResume()
  {
    super.onResume();
    if (this.mOnSaveInstance)
    {
      this.mOnSaveInstance = false;
      if (this.mDismiss)
      {
        this.mDismiss = false;
        dismiss();
      }
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    this.mOnSaveInstance = true;
  }
  
  public void setOnDismissListener(DialogInterface.OnDismissListener paramOnDismissListener)
  {
    this.mOnDismissListener = paramOnDismissListener;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/UnsupportedDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
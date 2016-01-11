package com.happyfresh.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.ContactUsActivity;
import com.happyfresh.common.ICartApplication;

public class CannotEditOrderDialogFragment
  extends BaseDialogFragment
{
  private static CannotEditOrderDialogFragment sInstance;
  @InjectView(2131558713)
  TextView mContactUs;
  private boolean mDismiss = false;
  private ICartApplication mICartApplication;
  private DialogInterface.OnDismissListener mOnDismissListener;
  private boolean mOnSaveInstance = false;
  
  public static CannotEditOrderDialogFragment getInstance()
  {
    if (sInstance == null) {
      sInstance = new CannotEditOrderDialogFragment();
    }
    return sInstance;
  }
  
  @OnClick({2131558711})
  void close()
  {
    dismissDelayed();
    if (this.mOnDismissListener != null) {
      this.mOnDismissListener.onDismiss(null);
    }
  }
  
  @OnClick({2131558713})
  void contactUs()
  {
    if (this.mOnDismissListener != null) {
      this.mOnDismissListener.onDismiss(getDialog());
    }
    dismissDelayed();
    startActivity(new Intent(getActivity(), ContactUsActivity.class));
  }
  
  public void dismissDelayed()
  {
    this.mDismiss = true;
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        if ((CannotEditOrderDialogFragment.this.getActivity() == null) || (CannotEditOrderDialogFragment.this.getFragmentManager() == null) || (!CannotEditOrderDialogFragment.this.isAdded())) {}
        while (CannotEditOrderDialogFragment.this.mOnSaveInstance) {
          return;
        }
        CannotEditOrderDialogFragment.this.dismiss();
      }
    }, 100L);
  }
  
  @OnClick({2131558712})
  void gotIt()
  {
    if (this.mOnDismissListener != null) {
      this.mOnDismissListener.onDismiss(getDialog());
    }
    dismissDelayed();
  }
  
  public boolean isDismiss()
  {
    return this.mDismiss;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    paramBundle = "<a href=\"\">" + getResources().getString(2131165327) + "</a>";
    this.mContactUs.setText(Html.fromHtml(paramBundle));
  }
  
  public void onCancel(DialogInterface paramDialogInterface)
  {
    super.onCancel(paramDialogInterface);
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
    paramLayoutInflater = paramLayoutInflater.inflate(2130903120, paramViewGroup, false);
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


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CannotEditOrderDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
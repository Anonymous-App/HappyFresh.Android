package com.happyfresh.fragments;

import android.app.Dialog;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FreeDeliveryFragment
  extends BaseDialogFragment
{
  private static final String DURATION_TEXT = "DURATION_TEXT";
  private static final String FREE_DELIVERY_TEXT = "DELIVERY_TEXT";
  private static final String FREE_DETAIL_TEXT = "FREE_DETAIL_TEXT";
  private static final String GOT_IT_TEXT = "GOT_IT";
  private static final String TITLE = "TITLE";
  @InjectView(2131558475)
  TextView mDialogTitle;
  @InjectView(2131558735)
  TextView mDurationText;
  @InjectView(2131558734)
  TextView mFreeDeliveryText;
  @InjectView(2131558736)
  TextView mFreeDetailText;
  @InjectView(2131558712)
  Button mGotItButton;
  private DialogInterface.OnDismissListener mOnDismissListener;
  
  private void closeDialog()
  {
    if (this.mOnDismissListener != null) {
      this.mOnDismissListener.onDismiss(getDialog());
    }
    dismiss();
  }
  
  public static FreeDeliveryFragment newInstance(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    FreeDeliveryFragment localFreeDeliveryFragment = new FreeDeliveryFragment();
    Bundle localBundle = new Bundle();
    if (paramString1 != null) {
      localBundle.putString("TITLE", paramString1);
    }
    if (paramString2 != null) {
      localBundle.putString("DELIVERY_TEXT", paramString2);
    }
    if (paramString3 != null) {
      localBundle.putString("DURATION_TEXT", paramString3);
    }
    if (paramString2 != null) {
      localBundle.putString("FREE_DETAIL_TEXT", paramString4);
    }
    if (paramString5 != null) {
      localBundle.putString("GOT_IT", paramString5);
    }
    localFreeDeliveryFragment.setArguments(localBundle);
    return localFreeDeliveryFragment;
  }
  
  @OnClick({2131558711})
  void dismissDialog()
  {
    closeDialog();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903126, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    paramViewGroup = getArguments();
    this.mDialogTitle.setText(paramViewGroup.getString("TITLE"));
    this.mFreeDeliveryText.setText(paramViewGroup.getString("DELIVERY_TEXT"));
    this.mDurationText.setText(paramViewGroup.getString("DURATION_TEXT"));
    this.mFreeDetailText.setText(paramViewGroup.getString("FREE_DETAIL_TEXT"));
    this.mGotItButton.setText(paramViewGroup.getString("GOT_IT"));
    getDialog().getWindow().requestFeature(1);
    return paramLayoutInflater;
  }
  
  public void setOnDismissListener(DialogInterface.OnDismissListener paramOnDismissListener)
  {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  @OnClick({2131558712})
  void verifyPhoneNumber()
  {
    closeDialog();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/FreeDeliveryFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
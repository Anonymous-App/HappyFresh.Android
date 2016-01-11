package com.afollestad.materialdialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.afollestad.materialdialogs.internal.MDRootLayout;

class DialogBase
  extends Dialog
  implements DialogInterface.OnShowListener
{
  private DialogInterface.OnShowListener mShowListener;
  protected MDRootLayout view;
  
  protected DialogBase(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }
  
  public View findViewById(int paramInt)
  {
    return this.view.findViewById(paramInt);
  }
  
  public void onShow(DialogInterface paramDialogInterface)
  {
    if (this.mShowListener != null) {
      this.mShowListener.onShow(paramDialogInterface);
    }
  }
  
  @Deprecated
  public void setContentView(int paramInt)
    throws IllegalAccessError
  {
    throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
  }
  
  @Deprecated
  public void setContentView(View paramView)
    throws IllegalAccessError
  {
    throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
  }
  
  @Deprecated
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
    throws IllegalAccessError
  {
    throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
  }
  
  public final void setOnShowListener(DialogInterface.OnShowListener paramOnShowListener)
  {
    this.mShowListener = paramOnShowListener;
  }
  
  protected final void setOnShowListenerInternal()
  {
    super.setOnShowListener(this);
  }
  
  protected final void setViewInternal(View paramView)
  {
    super.setContentView(paramView);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/afollestad/materialdialogs/DialogBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
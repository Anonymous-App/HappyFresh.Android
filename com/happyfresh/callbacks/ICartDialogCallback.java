package com.happyfresh.callbacks;

import com.afollestad.materialdialogs.MaterialDialog;

public abstract interface ICartDialogCallback
{
  public abstract void onCancel();
  
  public abstract void onNegative(MaterialDialog paramMaterialDialog);
  
  public abstract void onNeutral(MaterialDialog paramMaterialDialog);
  
  public abstract void onPositive(MaterialDialog paramMaterialDialog);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/callbacks/ICartDialogCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
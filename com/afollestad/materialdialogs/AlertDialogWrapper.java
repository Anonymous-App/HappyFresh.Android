package com.afollestad.materialdialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlertDialogWrapper
{
  public static class Builder
  {
    private final MaterialDialog.Builder builder;
    private DialogInterface.OnClickListener negativeDialogListener;
    private DialogInterface.OnClickListener neutralDialogListener;
    private DialogInterface.OnClickListener onClickListener;
    private DialogInterface.OnClickListener positiveDialogListener;
    
    public Builder(@NonNull Context paramContext)
    {
      this.builder = new MaterialDialog.Builder(paramContext);
    }
    
    private void addButtonsCallback()
    {
      if ((this.positiveDialogListener != null) || (this.negativeDialogListener != null)) {
        this.builder.callback(new MaterialDialog.ButtonCallback()
        {
          public void onNegative(MaterialDialog paramAnonymousMaterialDialog)
          {
            if (AlertDialogWrapper.Builder.this.negativeDialogListener != null) {
              AlertDialogWrapper.Builder.this.negativeDialogListener.onClick(paramAnonymousMaterialDialog, -2);
            }
          }
          
          public void onNeutral(MaterialDialog paramAnonymousMaterialDialog)
          {
            if (AlertDialogWrapper.Builder.this.neutralDialogListener != null) {
              AlertDialogWrapper.Builder.this.neutralDialogListener.onClick(paramAnonymousMaterialDialog, -3);
            }
          }
          
          public void onPositive(MaterialDialog paramAnonymousMaterialDialog)
          {
            if (AlertDialogWrapper.Builder.this.positiveDialogListener != null) {
              AlertDialogWrapper.Builder.this.positiveDialogListener.onClick(paramAnonymousMaterialDialog, -1);
            }
          }
        });
      }
    }
    
    private void addListCallbacks()
    {
      if (this.onClickListener != null) {
        this.builder.itemsCallback(new MaterialDialog.ListCallback()
        {
          public void onSelection(MaterialDialog paramAnonymousMaterialDialog, View paramAnonymousView, int paramAnonymousInt, CharSequence paramAnonymousCharSequence)
          {
            AlertDialogWrapper.Builder.this.onClickListener.onClick(paramAnonymousMaterialDialog, paramAnonymousInt);
          }
        });
      }
    }
    
    private void setUpMultiChoiceCallback(@Nullable final boolean[] paramArrayOfBoolean, final DialogInterface.OnMultiChoiceClickListener paramOnMultiChoiceClickListener)
    {
      Object localObject = null;
      if (paramArrayOfBoolean != null)
      {
        localObject = new ArrayList();
        int i = 0;
        while (i < paramArrayOfBoolean.length)
        {
          if (paramArrayOfBoolean[i] != 0) {
            ((ArrayList)localObject).add(Integer.valueOf(i));
          }
          i += 1;
        }
        localObject = (Integer[])((ArrayList)localObject).toArray(new Integer[((ArrayList)localObject).size()]);
      }
      this.builder.itemsCallbackMultiChoice((Integer[])localObject, new MaterialDialog.ListCallbackMultiChoice()
      {
        public boolean onSelection(MaterialDialog paramAnonymousMaterialDialog, Integer[] paramAnonymousArrayOfInteger, CharSequence[] paramAnonymousArrayOfCharSequence)
        {
          paramAnonymousArrayOfInteger = Arrays.asList(paramAnonymousArrayOfInteger);
          if (paramArrayOfBoolean != null)
          {
            int i = 0;
            while (i < paramArrayOfBoolean.length)
            {
              int j = paramArrayOfBoolean[i];
              paramArrayOfBoolean[i] = paramAnonymousArrayOfInteger.contains(Integer.valueOf(i));
              if (j != paramArrayOfBoolean[i]) {
                paramOnMultiChoiceClickListener.onClick(paramAnonymousMaterialDialog, i, paramArrayOfBoolean[i]);
              }
              i += 1;
            }
          }
          return true;
        }
      });
    }
    
    public Builder alwaysCallMultiChoiceCallback()
    {
      this.builder.alwaysCallMultiChoiceCallback();
      return this;
    }
    
    public Builder alwaysCallSingleChoiceCallback()
    {
      this.builder.alwaysCallSingleChoiceCallback();
      return this;
    }
    
    public Builder autoDismiss(boolean paramBoolean)
    {
      this.builder.autoDismiss(paramBoolean);
      return this;
    }
    
    @UiThread
    public Dialog create()
    {
      addButtonsCallback();
      addListCallbacks();
      return this.builder.build();
    }
    
    @Deprecated
    public Builder setAdapter(ListAdapter paramListAdapter)
    {
      return setAdapter(paramListAdapter, null);
    }
    
    public Builder setAdapter(ListAdapter paramListAdapter, final DialogInterface.OnClickListener paramOnClickListener)
    {
      this.builder.adapter = paramListAdapter;
      this.builder.listCallbackCustom = new MaterialDialog.ListCallback()
      {
        public void onSelection(MaterialDialog paramAnonymousMaterialDialog, View paramAnonymousView, int paramAnonymousInt, CharSequence paramAnonymousCharSequence)
        {
          paramOnClickListener.onClick(paramAnonymousMaterialDialog, paramAnonymousInt);
        }
      };
      return this;
    }
    
    public Builder setCancelable(boolean paramBoolean)
    {
      this.builder.cancelable(paramBoolean);
      return this;
    }
    
    public Builder setIcon(@DrawableRes int paramInt)
    {
      this.builder.iconRes(paramInt);
      return this;
    }
    
    public Builder setIcon(Drawable paramDrawable)
    {
      this.builder.icon(paramDrawable);
      return this;
    }
    
    public Builder setIconAttribute(@AttrRes int paramInt)
    {
      this.builder.iconAttr(paramInt);
      return this;
    }
    
    public Builder setItems(@ArrayRes int paramInt, DialogInterface.OnClickListener paramOnClickListener)
    {
      this.builder.items(paramInt);
      this.onClickListener = paramOnClickListener;
      return this;
    }
    
    public Builder setItems(CharSequence[] paramArrayOfCharSequence, DialogInterface.OnClickListener paramOnClickListener)
    {
      this.builder.items(paramArrayOfCharSequence);
      this.onClickListener = paramOnClickListener;
      return this;
    }
    
    public Builder setMessage(@StringRes int paramInt)
    {
      this.builder.content(paramInt);
      return this;
    }
    
    public Builder setMessage(@NonNull CharSequence paramCharSequence)
    {
      this.builder.content(paramCharSequence);
      return this;
    }
    
    public Builder setMultiChoiceItems(@ArrayRes int paramInt, @Nullable boolean[] paramArrayOfBoolean, DialogInterface.OnMultiChoiceClickListener paramOnMultiChoiceClickListener)
    {
      this.builder.items(paramInt);
      setUpMultiChoiceCallback(paramArrayOfBoolean, paramOnMultiChoiceClickListener);
      return this;
    }
    
    public Builder setMultiChoiceItems(@NonNull String[] paramArrayOfString, @Nullable boolean[] paramArrayOfBoolean, DialogInterface.OnMultiChoiceClickListener paramOnMultiChoiceClickListener)
    {
      this.builder.items(paramArrayOfString);
      setUpMultiChoiceCallback(paramArrayOfBoolean, paramOnMultiChoiceClickListener);
      return this;
    }
    
    public Builder setNegativeButton(@StringRes int paramInt, DialogInterface.OnClickListener paramOnClickListener)
    {
      this.builder.negativeText(paramInt);
      this.negativeDialogListener = paramOnClickListener;
      return this;
    }
    
    public Builder setNegativeButton(@NonNull CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
    {
      this.builder.negativeText(paramCharSequence);
      this.negativeDialogListener = paramOnClickListener;
      return this;
    }
    
    public Builder setNeutralButton(@StringRes int paramInt, DialogInterface.OnClickListener paramOnClickListener)
    {
      this.builder.neutralText(paramInt);
      this.neutralDialogListener = paramOnClickListener;
      return this;
    }
    
    public Builder setNeutralButton(@NonNull CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
    {
      this.builder.neutralText(paramCharSequence);
      this.neutralDialogListener = paramOnClickListener;
      return this;
    }
    
    public Builder setOnCancelListener(@NonNull DialogInterface.OnCancelListener paramOnCancelListener)
    {
      this.builder.cancelListener(paramOnCancelListener);
      return this;
    }
    
    public Builder setOnDismissListener(@NonNull DialogInterface.OnDismissListener paramOnDismissListener)
    {
      this.builder.dismissListener(paramOnDismissListener);
      return this;
    }
    
    public Builder setOnKeyListener(@NonNull DialogInterface.OnKeyListener paramOnKeyListener)
    {
      this.builder.keyListener(paramOnKeyListener);
      return this;
    }
    
    public Builder setOnShowListener(@NonNull DialogInterface.OnShowListener paramOnShowListener)
    {
      this.builder.showListener(paramOnShowListener);
      return this;
    }
    
    public Builder setPositiveButton(@StringRes int paramInt, DialogInterface.OnClickListener paramOnClickListener)
    {
      this.builder.positiveText(paramInt);
      this.positiveDialogListener = paramOnClickListener;
      return this;
    }
    
    public Builder setPositiveButton(@NonNull CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
    {
      this.builder.positiveText(paramCharSequence);
      this.positiveDialogListener = paramOnClickListener;
      return this;
    }
    
    public Builder setSingleChoiceItems(@ArrayRes int paramInt1, int paramInt2, final DialogInterface.OnClickListener paramOnClickListener)
    {
      this.builder.items(paramInt1);
      this.builder.itemsCallbackSingleChoice(paramInt2, new MaterialDialog.ListCallbackSingleChoice()
      {
        public boolean onSelection(MaterialDialog paramAnonymousMaterialDialog, View paramAnonymousView, int paramAnonymousInt, CharSequence paramAnonymousCharSequence)
        {
          paramOnClickListener.onClick(paramAnonymousMaterialDialog, paramAnonymousInt);
          return true;
        }
      });
      return this;
    }
    
    public Builder setSingleChoiceItems(@NonNull String[] paramArrayOfString, int paramInt, final DialogInterface.OnClickListener paramOnClickListener)
    {
      this.builder.items(paramArrayOfString);
      this.builder.itemsCallbackSingleChoice(paramInt, new MaterialDialog.ListCallbackSingleChoice()
      {
        public boolean onSelection(MaterialDialog paramAnonymousMaterialDialog, View paramAnonymousView, int paramAnonymousInt, CharSequence paramAnonymousCharSequence)
        {
          paramOnClickListener.onClick(paramAnonymousMaterialDialog, paramAnonymousInt);
          return true;
        }
      });
      return this;
    }
    
    public Builder setTitle(@StringRes int paramInt)
    {
      this.builder.title(paramInt);
      return this;
    }
    
    public Builder setTitle(@NonNull CharSequence paramCharSequence)
    {
      this.builder.title(paramCharSequence);
      return this;
    }
    
    public Builder setView(@NonNull View paramView)
    {
      this.builder.customView(paramView, false);
      return this;
    }
    
    @UiThread
    public Dialog show()
    {
      Dialog localDialog = create();
      localDialog.show();
      return localDialog;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/afollestad/materialdialogs/AlertDialogWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
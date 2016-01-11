package com.happyfresh.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.support.annotation.NonNull;
import android.view.View;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.happyfresh.callbacks.ICartDialogCallback;

public class DialogUtils
{
  private static MaterialDialog buildDialog(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, ICartDialogCallback paramICartDialogCallback, View paramView, DialogColor paramDialogColor)
  {
    paramContext = new MaterialDialog.Builder(paramContext).onPositive(new MaterialDialog.SingleButtonCallback()
    {
      public void onClick(@NonNull MaterialDialog paramAnonymousMaterialDialog, @NonNull DialogAction paramAnonymousDialogAction)
      {
        if (this.val$iCartDialogCallback != null) {
          this.val$iCartDialogCallback.onPositive(paramAnonymousMaterialDialog);
        }
      }
    }).onNeutral(new MaterialDialog.SingleButtonCallback()
    {
      public void onClick(@NonNull MaterialDialog paramAnonymousMaterialDialog, @NonNull DialogAction paramAnonymousDialogAction)
      {
        if (this.val$iCartDialogCallback != null) {
          this.val$iCartDialogCallback.onNeutral(paramAnonymousMaterialDialog);
        }
      }
    }).onNegative(new MaterialDialog.SingleButtonCallback()
    {
      public void onClick(@NonNull MaterialDialog paramAnonymousMaterialDialog, @NonNull DialogAction paramAnonymousDialogAction)
      {
        if (this.val$iCartDialogCallback != null) {
          this.val$iCartDialogCallback.onNegative(paramAnonymousMaterialDialog);
        }
      }
    }).cancelListener(new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramAnonymousDialogInterface)
      {
        if (this.val$iCartDialogCallback != null) {
          this.val$iCartDialogCallback.onCancel();
        }
      }
    }).title(paramString1).content(paramString2);
    if (paramString3 != null) {
      paramContext.positiveText(paramString3);
    }
    if (paramString4 != null) {
      paramContext.negativeText(paramString4);
    }
    if (paramString5 != null) {
      paramContext.neutralText(paramString5);
    }
    if (paramView != null) {
      paramContext.customView(paramView, false);
    }
    if ((paramDialogColor != null) && (paramDialogColor.positiveButtonColor != null)) {
      paramContext.positiveColor(paramDialogColor.positiveButtonColor.intValue());
    }
    if ((paramDialogColor != null) && (paramDialogColor.negativeButtonColor != null)) {
      paramContext.negativeColor(paramDialogColor.negativeButtonColor.intValue());
    }
    if ((paramDialogColor != null) && (paramDialogColor.neutralButtonColor != null)) {
      paramContext.neutralColor(paramDialogColor.neutralButtonColor.intValue());
    }
    return paramContext.build();
  }
  
  public static MaterialDialog showDialog(Context paramContext, View paramView)
  {
    paramContext = buildDialog(paramContext, null, null, null, null, null, null, paramView, null);
    paramContext.show();
    return paramContext;
  }
  
  public static MaterialDialog showDialog(Context paramContext, String paramString1, String paramString2, String paramString3, ICartDialogCallback paramICartDialogCallback)
  {
    paramContext = buildDialog(paramContext, paramString1, paramString2, paramString3, null, null, paramICartDialogCallback, null, null);
    paramContext.show();
    return paramContext;
  }
  
  public static MaterialDialog showDialog(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, ICartDialogCallback paramICartDialogCallback, View paramView)
  {
    paramContext = buildDialog(paramContext, paramString1, paramString2, paramString3, paramString4, paramString5, paramICartDialogCallback, paramView, null);
    paramContext.show();
    return paramContext;
  }
  
  public static MaterialDialog showDialog(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, ICartDialogCallback paramICartDialogCallback, View paramView, DialogColor paramDialogColor)
  {
    paramContext = buildDialog(paramContext, paramString1, paramString2, paramString3, paramString4, paramString5, paramICartDialogCallback, paramView, paramDialogColor);
    paramContext.show();
    return paramContext;
  }
  
  public static void showDialog(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, ICartDialogCallback paramICartDialogCallback)
  {
    buildDialog(paramContext, paramString1, paramString2, paramString3, paramString4, paramString5, paramICartDialogCallback, null, null).show();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/utils/DialogUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
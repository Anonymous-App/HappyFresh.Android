package com.afollestad.materialdialogs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.annotation.UiThread;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.afollestad.materialdialogs.internal.MDAdapter;
import com.afollestad.materialdialogs.internal.MDButton;
import com.afollestad.materialdialogs.internal.MDRootLayout;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.util.DialogUtils;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import me.zhanghai.android.materialprogressbar.HorizontalProgressDrawable;
import me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable;
import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;

class DialogInit
{
  @LayoutRes
  public static int getInflateLayout(MaterialDialog.Builder paramBuilder)
  {
    if (paramBuilder.customView != null) {
      return R.layout.md_dialog_custom;
    }
    if (((paramBuilder.items != null) && (paramBuilder.items.length > 0)) || (paramBuilder.adapter != null)) {
      return R.layout.md_dialog_list;
    }
    if (paramBuilder.progress > -2) {
      return R.layout.md_dialog_progress;
    }
    if (paramBuilder.indeterminateProgress)
    {
      if (paramBuilder.indeterminateIsHorizontalProgress) {
        return R.layout.md_dialog_progress_indeterminate_horizontal;
      }
      return R.layout.md_dialog_progress_indeterminate;
    }
    if (paramBuilder.inputCallback != null) {
      return R.layout.md_dialog_input;
    }
    return R.layout.md_dialog_basic;
  }
  
  @StyleRes
  public static int getTheme(@NonNull MaterialDialog.Builder paramBuilder)
  {
    Object localObject = paramBuilder.context;
    int i = R.attr.md_dark_theme;
    boolean bool;
    if (paramBuilder.theme == Theme.DARK)
    {
      bool = true;
      bool = DialogUtils.resolveBoolean((Context)localObject, i, bool);
      if (!bool) {
        break label54;
      }
    }
    label54:
    for (localObject = Theme.DARK;; localObject = Theme.LIGHT)
    {
      paramBuilder.theme = ((Theme)localObject);
      if (!bool) {
        break label61;
      }
      return R.style.MD_Dark;
      bool = false;
      break;
    }
    label61:
    return R.style.MD_Light;
  }
  
  @UiThread
  public static void init(MaterialDialog paramMaterialDialog)
  {
    MaterialDialog.Builder localBuilder = paramMaterialDialog.mBuilder;
    paramMaterialDialog.setCancelable(localBuilder.cancelable);
    paramMaterialDialog.setCanceledOnTouchOutside(localBuilder.cancelable);
    if (localBuilder.backgroundColor == 0) {
      localBuilder.backgroundColor = DialogUtils.resolveColor(localBuilder.context, R.attr.md_background_color);
    }
    if (localBuilder.backgroundColor != 0)
    {
      localObject = new GradientDrawable();
      ((GradientDrawable)localObject).setCornerRadius(localBuilder.context.getResources().getDimension(R.dimen.md_bg_corner_radius));
      ((GradientDrawable)localObject).setColor(localBuilder.backgroundColor);
      DialogUtils.setBackgroundCompat(paramMaterialDialog.view, (Drawable)localObject);
    }
    if (!localBuilder.positiveColorSet) {
      localBuilder.positiveColor = DialogUtils.resolveActionTextColorStateList(localBuilder.context, R.attr.md_positive_color, localBuilder.positiveColor);
    }
    if (!localBuilder.neutralColorSet) {
      localBuilder.neutralColor = DialogUtils.resolveActionTextColorStateList(localBuilder.context, R.attr.md_neutral_color, localBuilder.neutralColor);
    }
    if (!localBuilder.negativeColorSet) {
      localBuilder.negativeColor = DialogUtils.resolveActionTextColorStateList(localBuilder.context, R.attr.md_negative_color, localBuilder.negativeColor);
    }
    if (!localBuilder.widgetColorSet) {
      localBuilder.widgetColor = DialogUtils.resolveColor(localBuilder.context, R.attr.md_widget_color, localBuilder.widgetColor);
    }
    int i;
    if (!localBuilder.titleColorSet)
    {
      i = DialogUtils.resolveColor(paramMaterialDialog.getContext(), 16842806);
      localBuilder.titleColor = DialogUtils.resolveColor(localBuilder.context, R.attr.md_title_color, i);
    }
    if (!localBuilder.contentColorSet)
    {
      i = DialogUtils.resolveColor(paramMaterialDialog.getContext(), 16842808);
      localBuilder.contentColor = DialogUtils.resolveColor(localBuilder.context, R.attr.md_content_color, i);
    }
    if (!localBuilder.itemColorSet) {
      localBuilder.itemColor = DialogUtils.resolveColor(localBuilder.context, R.attr.md_item_color, localBuilder.contentColor);
    }
    paramMaterialDialog.title = ((TextView)paramMaterialDialog.view.findViewById(R.id.title));
    paramMaterialDialog.icon = ((ImageView)paramMaterialDialog.view.findViewById(R.id.icon));
    paramMaterialDialog.titleFrame = paramMaterialDialog.view.findViewById(R.id.titleFrame);
    paramMaterialDialog.content = ((TextView)paramMaterialDialog.view.findViewById(R.id.content));
    paramMaterialDialog.listView = ((ListView)paramMaterialDialog.view.findViewById(R.id.contentListView));
    paramMaterialDialog.positiveButton = ((MDButton)paramMaterialDialog.view.findViewById(R.id.buttonDefaultPositive));
    paramMaterialDialog.neutralButton = ((MDButton)paramMaterialDialog.view.findViewById(R.id.buttonDefaultNeutral));
    paramMaterialDialog.negativeButton = ((MDButton)paramMaterialDialog.view.findViewById(R.id.buttonDefaultNegative));
    if ((localBuilder.inputCallback != null) && (localBuilder.positiveText == null)) {
      localBuilder.positiveText = localBuilder.context.getText(17039370);
    }
    Object localObject = paramMaterialDialog.positiveButton;
    label521:
    label543:
    label577:
    int j;
    label810:
    label881:
    label959:
    boolean bool1;
    label1040:
    label1425:
    label1445:
    FrameLayout localFrameLayout;
    View localView;
    int k;
    if (localBuilder.positiveText != null)
    {
      i = 0;
      ((MDButton)localObject).setVisibility(i);
      localObject = paramMaterialDialog.neutralButton;
      if (localBuilder.neutralText == null) {
        break label1739;
      }
      i = 0;
      ((MDButton)localObject).setVisibility(i);
      localObject = paramMaterialDialog.negativeButton;
      if (localBuilder.negativeText == null) {
        break label1745;
      }
      i = 0;
      ((MDButton)localObject).setVisibility(i);
      if (localBuilder.icon == null) {
        break label1751;
      }
      paramMaterialDialog.icon.setVisibility(0);
      paramMaterialDialog.icon.setImageDrawable(localBuilder.icon);
      j = localBuilder.maxIconSize;
      i = j;
      if (j == -1) {
        i = DialogUtils.resolveDimension(localBuilder.context, R.attr.md_icon_max_size);
      }
      if ((localBuilder.limitIconToDefaultSize) || (DialogUtils.resolveBoolean(localBuilder.context, R.attr.md_icon_limit_icon_to_default_size))) {
        i = localBuilder.context.getResources().getDimensionPixelSize(R.dimen.md_icon_max_size);
      }
      if (i > -1)
      {
        paramMaterialDialog.icon.setAdjustViewBounds(true);
        paramMaterialDialog.icon.setMaxHeight(i);
        paramMaterialDialog.icon.setMaxWidth(i);
        paramMaterialDialog.icon.requestLayout();
      }
      if (!localBuilder.dividerColorSet)
      {
        i = DialogUtils.resolveColor(paramMaterialDialog.getContext(), R.attr.md_divider);
        localBuilder.dividerColor = DialogUtils.resolveColor(localBuilder.context, R.attr.md_divider_color, i);
      }
      paramMaterialDialog.view.setDividerColor(localBuilder.dividerColor);
      if (paramMaterialDialog.title != null)
      {
        paramMaterialDialog.setTypeface(paramMaterialDialog.title, localBuilder.mediumFont);
        paramMaterialDialog.title.setTextColor(localBuilder.titleColor);
        paramMaterialDialog.title.setGravity(localBuilder.titleGravity.getGravityInt());
        if (Build.VERSION.SDK_INT >= 17) {
          paramMaterialDialog.title.setTextAlignment(localBuilder.titleGravity.getTextAlignment());
        }
        if (localBuilder.title != null) {
          break label1801;
        }
        paramMaterialDialog.titleFrame.setVisibility(8);
      }
      if (paramMaterialDialog.content != null)
      {
        paramMaterialDialog.content.setMovementMethod(new LinkMovementMethod());
        paramMaterialDialog.setTypeface(paramMaterialDialog.content, localBuilder.regularFont);
        paramMaterialDialog.content.setLineSpacing(0.0F, localBuilder.contentLineSpacingMultiplier);
        if (localBuilder.linkColor != null) {
          break label1824;
        }
        paramMaterialDialog.content.setLinkTextColor(DialogUtils.resolveColor(paramMaterialDialog.getContext(), 16842806));
        paramMaterialDialog.content.setTextColor(localBuilder.contentColor);
        paramMaterialDialog.content.setGravity(localBuilder.contentGravity.getGravityInt());
        if (Build.VERSION.SDK_INT >= 17) {
          paramMaterialDialog.content.setTextAlignment(localBuilder.contentGravity.getTextAlignment());
        }
        if (localBuilder.content == null) {
          break label1839;
        }
        paramMaterialDialog.content.setText(localBuilder.content);
        paramMaterialDialog.content.setVisibility(0);
      }
      paramMaterialDialog.view.setButtonGravity(localBuilder.buttonsGravity);
      paramMaterialDialog.view.setButtonStackedGravity(localBuilder.btnStackedGravity);
      paramMaterialDialog.view.setForceStack(localBuilder.forceStacking);
      if (Build.VERSION.SDK_INT < 14) {
        break label1851;
      }
      boolean bool2 = DialogUtils.resolveBoolean(localBuilder.context, 16843660, true);
      bool1 = bool2;
      if (bool2) {
        bool1 = DialogUtils.resolveBoolean(localBuilder.context, R.attr.textAllCaps, true);
      }
      localObject = paramMaterialDialog.positiveButton;
      paramMaterialDialog.setTypeface((TextView)localObject, localBuilder.mediumFont);
      ((MDButton)localObject).setAllCapsCompat(bool1);
      ((MDButton)localObject).setText(localBuilder.positiveText);
      ((MDButton)localObject).setTextColor(localBuilder.positiveColor);
      paramMaterialDialog.positiveButton.setStackedSelector(paramMaterialDialog.getButtonSelector(DialogAction.POSITIVE, true));
      paramMaterialDialog.positiveButton.setDefaultSelector(paramMaterialDialog.getButtonSelector(DialogAction.POSITIVE, false));
      paramMaterialDialog.positiveButton.setTag(DialogAction.POSITIVE);
      paramMaterialDialog.positiveButton.setOnClickListener(paramMaterialDialog);
      paramMaterialDialog.positiveButton.setVisibility(0);
      localObject = paramMaterialDialog.negativeButton;
      paramMaterialDialog.setTypeface((TextView)localObject, localBuilder.mediumFont);
      ((MDButton)localObject).setAllCapsCompat(bool1);
      ((MDButton)localObject).setText(localBuilder.negativeText);
      ((MDButton)localObject).setTextColor(localBuilder.negativeColor);
      paramMaterialDialog.negativeButton.setStackedSelector(paramMaterialDialog.getButtonSelector(DialogAction.NEGATIVE, true));
      paramMaterialDialog.negativeButton.setDefaultSelector(paramMaterialDialog.getButtonSelector(DialogAction.NEGATIVE, false));
      paramMaterialDialog.negativeButton.setTag(DialogAction.NEGATIVE);
      paramMaterialDialog.negativeButton.setOnClickListener(paramMaterialDialog);
      paramMaterialDialog.negativeButton.setVisibility(0);
      localObject = paramMaterialDialog.neutralButton;
      paramMaterialDialog.setTypeface((TextView)localObject, localBuilder.mediumFont);
      ((MDButton)localObject).setAllCapsCompat(bool1);
      ((MDButton)localObject).setText(localBuilder.neutralText);
      ((MDButton)localObject).setTextColor(localBuilder.neutralColor);
      paramMaterialDialog.neutralButton.setStackedSelector(paramMaterialDialog.getButtonSelector(DialogAction.NEUTRAL, true));
      paramMaterialDialog.neutralButton.setDefaultSelector(paramMaterialDialog.getButtonSelector(DialogAction.NEUTRAL, false));
      paramMaterialDialog.neutralButton.setTag(DialogAction.NEUTRAL);
      paramMaterialDialog.neutralButton.setOnClickListener(paramMaterialDialog);
      paramMaterialDialog.neutralButton.setVisibility(0);
      if (localBuilder.listCallbackMultiChoice != null) {
        paramMaterialDialog.selectedIndicesList = new ArrayList();
      }
      if ((paramMaterialDialog.listView != null) && (((localBuilder.items != null) && (localBuilder.items.length > 0)) || (localBuilder.adapter != null)))
      {
        paramMaterialDialog.listView.setSelector(paramMaterialDialog.getListSelector());
        if (localBuilder.adapter != null) {
          break label1929;
        }
        if (localBuilder.listCallbackSingleChoice == null) {
          break label1868;
        }
        paramMaterialDialog.listType = MaterialDialog.ListType.SINGLE;
        localBuilder.adapter = new DefaultAdapter(paramMaterialDialog, MaterialDialog.ListType.getLayoutForType(paramMaterialDialog.listType));
      }
      setupProgressDialog(paramMaterialDialog);
      setupInputDialog(paramMaterialDialog);
      if (localBuilder.customView != null)
      {
        ((MDRootLayout)paramMaterialDialog.view.findViewById(R.id.root)).noTitleNoPadding();
        localFrameLayout = (FrameLayout)paramMaterialDialog.view.findViewById(R.id.customViewFrame);
        paramMaterialDialog.customViewFrame = localFrameLayout;
        localView = localBuilder.customView;
        if (localView.getParent() != null) {
          ((ViewGroup)localView.getParent()).removeView(localView);
        }
        localObject = localView;
        if (localBuilder.wrapCustomViewInScroll)
        {
          Resources localResources = paramMaterialDialog.getContext().getResources();
          i = localResources.getDimensionPixelSize(R.dimen.md_dialog_frame_margin);
          localObject = new ScrollView(paramMaterialDialog.getContext());
          j = localResources.getDimensionPixelSize(R.dimen.md_content_padding_top);
          k = localResources.getDimensionPixelSize(R.dimen.md_content_padding_bottom);
          ((ScrollView)localObject).setClipToPadding(false);
          if (!(localView instanceof EditText)) {
            break label1957;
          }
          ((ScrollView)localObject).setPadding(i, j, i, k);
        }
      }
    }
    for (;;)
    {
      ((ScrollView)localObject).addView(localView, new FrameLayout.LayoutParams(-1, -2));
      localFrameLayout.addView((View)localObject, new ViewGroup.LayoutParams(-1, -2));
      if (localBuilder.showListener != null) {
        paramMaterialDialog.setOnShowListener(localBuilder.showListener);
      }
      if (localBuilder.cancelListener != null) {
        paramMaterialDialog.setOnCancelListener(localBuilder.cancelListener);
      }
      if (localBuilder.dismissListener != null) {
        paramMaterialDialog.setOnDismissListener(localBuilder.dismissListener);
      }
      if (localBuilder.keyListener != null) {
        paramMaterialDialog.setOnKeyListener(localBuilder.keyListener);
      }
      paramMaterialDialog.setOnShowListenerInternal();
      paramMaterialDialog.invalidateList();
      paramMaterialDialog.setViewInternal(paramMaterialDialog.view);
      paramMaterialDialog.checkIfListInitScroll();
      return;
      i = 8;
      break;
      label1739:
      i = 8;
      break label521;
      label1745:
      i = 8;
      break label543;
      label1751:
      localObject = DialogUtils.resolveDrawable(localBuilder.context, R.attr.md_icon);
      if (localObject != null)
      {
        paramMaterialDialog.icon.setVisibility(0);
        paramMaterialDialog.icon.setImageDrawable((Drawable)localObject);
        break label577;
      }
      paramMaterialDialog.icon.setVisibility(8);
      break label577;
      label1801:
      paramMaterialDialog.title.setText(localBuilder.title);
      paramMaterialDialog.titleFrame.setVisibility(0);
      break label810;
      label1824:
      paramMaterialDialog.content.setLinkTextColor(localBuilder.linkColor);
      break label881;
      label1839:
      paramMaterialDialog.content.setVisibility(8);
      break label959;
      label1851:
      bool1 = DialogUtils.resolveBoolean(localBuilder.context, R.attr.textAllCaps, true);
      break label1040;
      label1868:
      if (localBuilder.listCallbackMultiChoice != null)
      {
        paramMaterialDialog.listType = MaterialDialog.ListType.MULTI;
        if (localBuilder.selectedIndices == null) {
          break label1425;
        }
        paramMaterialDialog.selectedIndicesList = new ArrayList(Arrays.asList(localBuilder.selectedIndices));
        localBuilder.selectedIndices = null;
        break label1425;
      }
      paramMaterialDialog.listType = MaterialDialog.ListType.REGULAR;
      break label1425;
      label1929:
      if (!(localBuilder.adapter instanceof MDAdapter)) {
        break label1445;
      }
      ((MDAdapter)localBuilder.adapter).setDialog(paramMaterialDialog);
      break label1445;
      label1957:
      ((ScrollView)localObject).setPadding(0, j, 0, k);
      localView.setPadding(i, 0, i, 0);
    }
  }
  
  private static void setupInputDialog(MaterialDialog paramMaterialDialog)
  {
    MaterialDialog.Builder localBuilder = paramMaterialDialog.mBuilder;
    paramMaterialDialog.input = ((EditText)paramMaterialDialog.view.findViewById(16908297));
    if (paramMaterialDialog.input == null) {
      return;
    }
    paramMaterialDialog.setTypeface(paramMaterialDialog.input, localBuilder.regularFont);
    if (localBuilder.inputPrefill != null) {
      paramMaterialDialog.input.setText(localBuilder.inputPrefill);
    }
    paramMaterialDialog.setInternalInputCallback();
    paramMaterialDialog.input.setHint(localBuilder.inputHint);
    paramMaterialDialog.input.setSingleLine();
    paramMaterialDialog.input.setTextColor(localBuilder.contentColor);
    paramMaterialDialog.input.setHintTextColor(DialogUtils.adjustAlpha(localBuilder.contentColor, 0.3F));
    MDTintHelper.setTint(paramMaterialDialog.input, paramMaterialDialog.mBuilder.widgetColor);
    if (localBuilder.inputType != -1)
    {
      paramMaterialDialog.input.setInputType(localBuilder.inputType);
      if ((localBuilder.inputType & 0x80) == 128) {
        paramMaterialDialog.input.setTransformationMethod(PasswordTransformationMethod.getInstance());
      }
    }
    paramMaterialDialog.inputMinMax = ((TextView)paramMaterialDialog.view.findViewById(R.id.minMax));
    if ((localBuilder.inputMinLength > 0) || (localBuilder.inputMaxLength > -1))
    {
      int i = paramMaterialDialog.input.getText().toString().length();
      if (!localBuilder.inputAllowEmpty) {}
      for (boolean bool = true;; bool = false)
      {
        paramMaterialDialog.invalidateInputMinMaxIndicator(i, bool);
        return;
      }
    }
    paramMaterialDialog.inputMinMax.setVisibility(8);
    paramMaterialDialog.inputMinMax = null;
  }
  
  private static void setupProgressDialog(MaterialDialog paramMaterialDialog)
  {
    MaterialDialog.Builder localBuilder = paramMaterialDialog.mBuilder;
    if ((localBuilder.indeterminateProgress) || (localBuilder.progress > -2))
    {
      paramMaterialDialog.mProgress = ((ProgressBar)paramMaterialDialog.view.findViewById(16908301));
      if (paramMaterialDialog.mProgress != null) {
        break label46;
      }
    }
    for (;;)
    {
      return;
      label46:
      Object localObject;
      if (Build.VERSION.SDK_INT >= 14) {
        if (localBuilder.indeterminateProgress) {
          if (localBuilder.indeterminateIsHorizontalProgress)
          {
            localObject = new IndeterminateHorizontalProgressDrawable(localBuilder.getContext());
            ((IndeterminateHorizontalProgressDrawable)localObject).setTint(localBuilder.widgetColor);
            paramMaterialDialog.mProgress.setProgressDrawable((Drawable)localObject);
            paramMaterialDialog.mProgress.setIndeterminateDrawable((Drawable)localObject);
          }
        }
      }
      while ((!localBuilder.indeterminateProgress) || (localBuilder.indeterminateIsHorizontalProgress))
      {
        paramMaterialDialog.mProgress.setIndeterminate(localBuilder.indeterminateIsHorizontalProgress);
        paramMaterialDialog.mProgress.setProgress(0);
        paramMaterialDialog.mProgress.setMax(localBuilder.progressMax);
        paramMaterialDialog.mProgressLabel = ((TextView)paramMaterialDialog.view.findViewById(R.id.label));
        if (paramMaterialDialog.mProgressLabel != null)
        {
          paramMaterialDialog.mProgressLabel.setTextColor(localBuilder.contentColor);
          paramMaterialDialog.setTypeface(paramMaterialDialog.mProgressLabel, localBuilder.mediumFont);
          paramMaterialDialog.mProgressLabel.setText(localBuilder.progressPercentFormat.format(0L));
        }
        paramMaterialDialog.mProgressMinMax = ((TextView)paramMaterialDialog.view.findViewById(R.id.minMax));
        if (paramMaterialDialog.mProgressMinMax == null) {
          break label431;
        }
        paramMaterialDialog.mProgressMinMax.setTextColor(localBuilder.contentColor);
        paramMaterialDialog.setTypeface(paramMaterialDialog.mProgressMinMax, localBuilder.regularFont);
        if (!localBuilder.showMinMax) {
          break label421;
        }
        paramMaterialDialog.mProgressMinMax.setVisibility(0);
        paramMaterialDialog.mProgressMinMax.setText(String.format(localBuilder.progressNumberFormat, new Object[] { Integer.valueOf(0), Integer.valueOf(localBuilder.progressMax) }));
        paramMaterialDialog = (ViewGroup.MarginLayoutParams)paramMaterialDialog.mProgress.getLayoutParams();
        paramMaterialDialog.leftMargin = 0;
        paramMaterialDialog.rightMargin = 0;
        return;
        localObject = new IndeterminateProgressDrawable(localBuilder.getContext());
        ((IndeterminateProgressDrawable)localObject).setTint(localBuilder.widgetColor);
        paramMaterialDialog.mProgress.setProgressDrawable((Drawable)localObject);
        paramMaterialDialog.mProgress.setIndeterminateDrawable((Drawable)localObject);
        continue;
        localObject = new HorizontalProgressDrawable(localBuilder.getContext());
        ((HorizontalProgressDrawable)localObject).setTint(localBuilder.widgetColor);
        paramMaterialDialog.mProgress.setProgressDrawable((Drawable)localObject);
        paramMaterialDialog.mProgress.setIndeterminateDrawable((Drawable)localObject);
        continue;
        MDTintHelper.setTint(paramMaterialDialog.mProgress, localBuilder.widgetColor);
      }
    }
    label421:
    paramMaterialDialog.mProgressMinMax.setVisibility(8);
    return;
    label431:
    localBuilder.showMinMax = false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/afollestad/materialdialogs/DialogInit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
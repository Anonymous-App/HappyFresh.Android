package com.afollestad.materialdialogs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import java.util.List;

class DefaultAdapter
  extends BaseAdapter
{
  private final MaterialDialog dialog;
  private final GravityEnum itemGravity;
  @LayoutRes
  private final int layout;
  
  public DefaultAdapter(MaterialDialog paramMaterialDialog, @LayoutRes int paramInt)
  {
    this.dialog = paramMaterialDialog;
    this.layout = paramInt;
    this.itemGravity = paramMaterialDialog.mBuilder.itemsGravity;
  }
  
  @TargetApi(17)
  private boolean isRTL()
  {
    boolean bool = true;
    if (Build.VERSION.SDK_INT < 17) {
      return false;
    }
    if (this.dialog.getBuilder().getContext().getResources().getConfiguration().getLayoutDirection() == 1) {}
    for (;;)
    {
      return bool;
      bool = false;
    }
  }
  
  @TargetApi(17)
  private void setupGravity(ViewGroup paramViewGroup)
  {
    ((LinearLayout)paramViewGroup).setGravity(this.itemGravity.getGravityInt() | 0x10);
    if (paramViewGroup.getChildCount() == 2)
    {
      if ((this.itemGravity != GravityEnum.END) || (isRTL()) || (!(paramViewGroup.getChildAt(0) instanceof CompoundButton))) {
        break label112;
      }
      localCompoundButton = (CompoundButton)paramViewGroup.getChildAt(0);
      paramViewGroup.removeView(localCompoundButton);
      localTextView = (TextView)paramViewGroup.getChildAt(0);
      paramViewGroup.removeView(localTextView);
      localTextView.setPadding(localTextView.getPaddingRight(), localTextView.getPaddingTop(), localTextView.getPaddingLeft(), localTextView.getPaddingBottom());
      paramViewGroup.addView(localTextView);
      paramViewGroup.addView(localCompoundButton);
    }
    label112:
    while ((this.itemGravity != GravityEnum.START) || (!isRTL()) || (!(paramViewGroup.getChildAt(1) instanceof CompoundButton))) {
      return;
    }
    CompoundButton localCompoundButton = (CompoundButton)paramViewGroup.getChildAt(1);
    paramViewGroup.removeView(localCompoundButton);
    TextView localTextView = (TextView)paramViewGroup.getChildAt(0);
    paramViewGroup.removeView(localTextView);
    localTextView.setPadding(localTextView.getPaddingRight(), localTextView.getPaddingTop(), localTextView.getPaddingRight(), localTextView.getPaddingBottom());
    paramViewGroup.addView(localCompoundButton);
    paramViewGroup.addView(localTextView);
  }
  
  public int getCount()
  {
    if (this.dialog.mBuilder.items != null) {
      return this.dialog.mBuilder.items.length;
    }
    return 0;
  }
  
  public Object getItem(int paramInt)
  {
    return this.dialog.mBuilder.items[paramInt];
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  @SuppressLint({"WrongViewCast"})
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramView;
    if (paramView == null) {
      localView = LayoutInflater.from(this.dialog.getContext()).inflate(this.layout, paramViewGroup, false);
    }
    paramView = (TextView)localView.findViewById(R.id.title);
    switch (this.dialog.listType)
    {
    default: 
      paramView.setText(this.dialog.mBuilder.items[paramInt]);
      paramView.setTextColor(this.dialog.mBuilder.itemColor);
      this.dialog.setTypeface(paramView, this.dialog.mBuilder.regularFont);
      localView.setTag(paramInt + ":" + this.dialog.mBuilder.items[paramInt]);
      setupGravity((ViewGroup)localView);
      if (this.dialog.mBuilder.itemIds != null)
      {
        if (paramInt < this.dialog.mBuilder.itemIds.length) {
          localView.setId(this.dialog.mBuilder.itemIds[paramInt]);
        }
      }
      else {
        label217:
        if (Build.VERSION.SDK_INT >= 21)
        {
          paramView = (ViewGroup)localView;
          if (paramView.getChildCount() == 2)
          {
            if (!(paramView.getChildAt(0) instanceof CompoundButton)) {
              break label382;
            }
            paramView.getChildAt(0).setBackground(null);
          }
        }
      }
      break;
    }
    label382:
    while (!(paramView.getChildAt(1) instanceof CompoundButton))
    {
      return localView;
      paramViewGroup = (RadioButton)localView.findViewById(R.id.control);
      if (this.dialog.mBuilder.selectedIndex == paramInt) {}
      for (boolean bool = true;; bool = false)
      {
        MDTintHelper.setTint(paramViewGroup, this.dialog.mBuilder.widgetColor);
        paramViewGroup.setChecked(bool);
        break;
      }
      paramViewGroup = (CheckBox)localView.findViewById(R.id.control);
      bool = this.dialog.selectedIndicesList.contains(Integer.valueOf(paramInt));
      MDTintHelper.setTint(paramViewGroup, this.dialog.mBuilder.widgetColor);
      paramViewGroup.setChecked(bool);
      break;
      localView.setId(-1);
      break label217;
    }
    paramView.getChildAt(1).setBackground(null);
    return localView;
  }
  
  public boolean hasStableIds()
  {
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/afollestad/materialdialogs/DefaultAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
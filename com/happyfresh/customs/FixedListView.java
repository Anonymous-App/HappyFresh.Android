package com.happyfresh.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ListView;

public class FixedListView
  extends ListView
{
  boolean expanded = false;
  
  public FixedListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public FixedListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public boolean isExpanded()
  {
    return this.expanded;
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    if (isEnabled())
    {
      super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
      return;
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void setExpanded(boolean paramBoolean)
  {
    this.expanded = paramBoolean;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/FixedListView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
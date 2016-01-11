package com.happyfresh.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class ReplacementItemAdapter$ReplacementItemViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ReplacementItemAdapter.ReplacementItemViewHolder paramReplacementItemViewHolder, Object paramObject)
  {
    paramReplacementItemViewHolder.cartCounterTextView = ((TextView)paramFinder.findRequiredView(paramObject, 2131559038, "field 'cartCounterTextView'"));
    paramReplacementItemViewHolder.productImageView = ((ImageView)paramFinder.findRequiredView(paramObject, 2131559026, "field 'productImageView'"));
    paramReplacementItemViewHolder.productNameTextView = ((TextView)paramFinder.findRequiredView(paramObject, 2131559027, "field 'productNameTextView'"));
    paramReplacementItemViewHolder.productPriceTextView = ((TextView)paramFinder.findRequiredView(paramObject, 2131559028, "field 'productPriceTextView'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131559088, "field 'mReplacementOption1' and method 'onReplacementOption1Click'");
    paramReplacementItemViewHolder.mReplacementOption1 = ((RadioButton)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onReplacementOption1Click();
      }
    });
    localView = paramFinder.findRequiredView(paramObject, 2131559089, "field 'mReplacementOption2' and method 'onReplacementOption2Click'");
    paramReplacementItemViewHolder.mReplacementOption2 = ((RadioButton)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onReplacementOption2Click();
      }
    });
    paramFinder = paramFinder.findRequiredView(paramObject, 2131559090, "field 'mReplacementOption3' and method 'onReplacementOption3Click'");
    paramReplacementItemViewHolder.mReplacementOption3 = ((RadioButton)paramFinder);
    paramFinder.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onReplacementOption3Click();
      }
    });
  }
  
  public static void reset(ReplacementItemAdapter.ReplacementItemViewHolder paramReplacementItemViewHolder)
  {
    paramReplacementItemViewHolder.cartCounterTextView = null;
    paramReplacementItemViewHolder.productImageView = null;
    paramReplacementItemViewHolder.productNameTextView = null;
    paramReplacementItemViewHolder.productPriceTextView = null;
    paramReplacementItemViewHolder.mReplacementOption1 = null;
    paramReplacementItemViewHolder.mReplacementOption2 = null;
    paramReplacementItemViewHolder.mReplacementOption3 = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/ReplacementItemAdapter$ReplacementItemViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.happyfresh.adapters;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class ReplacementResultAdapter$ReplacementProductViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ReplacementResultAdapter.ReplacementProductViewHolder paramReplacementProductViewHolder, Object paramObject)
  {
    paramReplacementProductViewHolder.productImage = ((ImageView)paramFinder.findRequiredView(paramObject, 2131559096, "field 'productImage'"));
    paramReplacementProductViewHolder.productName = ((TextView)paramFinder.findRequiredView(paramObject, 2131559098, "field 'productName'"));
    paramReplacementProductViewHolder.productPrice = ((TextView)paramFinder.findRequiredView(paramObject, 2131559099, "field 'productPrice'"));
    paramReplacementProductViewHolder.replaceButtonContainer = paramFinder.findRequiredView(paramObject, 2131559097, "field 'replaceButtonContainer'");
    paramReplacementProductViewHolder.replaceButton = ((ImageView)paramFinder.findRequiredView(paramObject, 2131559101, "field 'replaceButton'"));
    paramReplacementProductViewHolder.replaceButtonProgress = paramFinder.findRequiredView(paramObject, 2131559100, "field 'replaceButtonProgress'");
    paramReplacementProductViewHolder.replaceArea = paramFinder.findRequiredView(paramObject, 2131559095, "field 'replaceArea'");
  }
  
  public static void reset(ReplacementResultAdapter.ReplacementProductViewHolder paramReplacementProductViewHolder)
  {
    paramReplacementProductViewHolder.productImage = null;
    paramReplacementProductViewHolder.productName = null;
    paramReplacementProductViewHolder.productPrice = null;
    paramReplacementProductViewHolder.replaceButtonContainer = null;
    paramReplacementProductViewHolder.replaceButton = null;
    paramReplacementProductViewHolder.replaceButtonProgress = null;
    paramReplacementProductViewHolder.replaceArea = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/ReplacementResultAdapter$ReplacementProductViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
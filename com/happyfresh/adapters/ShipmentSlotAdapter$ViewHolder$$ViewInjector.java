package com.happyfresh.adapters;

import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class ShipmentSlotAdapter$ViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ShipmentSlotAdapter.ViewHolder paramViewHolder, Object paramObject)
  {
    paramViewHolder.slotTime = ((TextView)paramFinder.findRequiredView(paramObject, 2131559048, "field 'slotTime'"));
    paramViewHolder.slotCost = ((TextView)paramFinder.findRequiredView(paramObject, 2131559049, "field 'slotCost'"));
  }
  
  public static void reset(ShipmentSlotAdapter.ViewHolder paramViewHolder)
  {
    paramViewHolder.slotTime = null;
    paramViewHolder.slotCost = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/ShipmentSlotAdapter$ViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
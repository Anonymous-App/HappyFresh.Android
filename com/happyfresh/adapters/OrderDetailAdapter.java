package com.happyfresh.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.models.Address;
import com.happyfresh.models.Country;
import com.happyfresh.models.ItemPrice;
import com.happyfresh.models.Order;
import java.text.NumberFormat;
import java.util.List;

public class OrderDetailAdapter
  extends ArrayAdapter<ItemPrice>
{
  private static Typeface mediumBoldTypeFace = Typeface.create("sans-serif-medium", 1);
  private static Typeface mediumNormalTypeFace = Typeface.create("sans-serif-medium", 0);
  private final String TAG = OrderDetailAdapter.class.getSimpleName();
  private Context mContext;
  private String mDeliveryStr;
  private ICartApplication mICartApplication;
  private NumberFormat mNumberFormat;
  private String mTotalStr;
  
  public OrderDetailAdapter(Context paramContext, List<ItemPrice> paramList, Order paramOrder)
  {
    super(paramContext, 0, paramList);
    this.mContext = paramContext;
    this.mICartApplication = ((ICartApplication)this.mContext.getApplicationContext());
    this.mNumberFormat = this.mICartApplication.getNumberFormatter(paramOrder.shipAddress.country.isoName);
    this.mTotalStr = this.mContext.getString(2131165616);
    this.mDeliveryStr = this.mContext.getString(2131165353);
  }
  
  public int dpToPx(int paramInt)
  {
    DisplayMetrics localDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
    return Math.round(paramInt * (localDisplayMetrics.xdpi / 160.0F));
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ItemPrice localItemPrice;
    int i;
    int j;
    if (paramView == null)
    {
      paramView = LayoutInflater.from(this.mContext).inflate(2130903174, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
      localItemPrice = (ItemPrice)getItem(paramInt);
      paramInt = paramViewGroup.itemText.getPaddingRight();
      i = paramViewGroup.itemText.getPaddingTop();
      j = paramViewGroup.itemText.getPaddingBottom();
      if (!localItemPrice.subItem) {
        break label294;
      }
      paramViewGroup.itemText.setPadding(dpToPx(10), i, paramInt, j);
      paramViewGroup.itemText.setTextColor(this.mContext.getResources().getColor(2131493141));
      paramViewGroup.priceText.setTextColor(this.mContext.getResources().getColor(2131493141));
    }
    for (;;)
    {
      paramViewGroup.itemText.setTypeface(mediumNormalTypeFace);
      paramViewGroup.priceText.setTypeface(mediumNormalTypeFace);
      if (this.mTotalStr.equalsIgnoreCase(localItemPrice.item))
      {
        paramViewGroup.itemText.setTypeface(mediumBoldTypeFace);
        paramViewGroup.itemText.setTextColor(-16777216);
        paramViewGroup.priceText.setTypeface(mediumBoldTypeFace);
        paramViewGroup.priceText.setTextColor(-16777216);
      }
      paramViewGroup.itemText.setText(String.valueOf(localItemPrice.item));
      paramViewGroup.priceText.setText(this.mNumberFormat.format(localItemPrice.price));
      if ((this.mDeliveryStr.equalsIgnoreCase(localItemPrice.item)) && (localItemPrice.price == 0.0D)) {
        paramViewGroup.priceText.setText(this.mContext.getString(2131165397));
      }
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
      break;
      label294:
      paramViewGroup.itemText.setPadding(0, i, paramInt, j);
      paramViewGroup.itemText.setTextColor(-16777216);
      paramViewGroup.priceText.setTextColor(-16777216);
    }
  }
  
  class ViewHolder
  {
    @InjectView(2131558951)
    TextView itemText;
    @InjectView(2131558952)
    TextView priceText;
    
    public ViewHolder(View paramView)
    {
      ButterKnife.inject(this, paramView);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/OrderDetailAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
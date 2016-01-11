package com.happyfresh.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.BaseActivity;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.Variant;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter
  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
  private static final int TYPE_CONTENT = 1;
  private static final int TYPE_HEADER = 0;
  private Context mContext;
  private CartHeaderViewHolder mHeader;
  private ICartApplication mICartApplication;
  private List<LineItem> mLineItems = new ArrayList();
  private OnItemCartActionListener mOnItemCartActionListener;
  
  public CartItemAdapter(Context paramContext)
  {
    this.mContext = paramContext;
    this.mICartApplication = ((ICartApplication)this.mContext.getApplicationContext());
    this.mHeader = new CartHeaderViewHolder(LayoutInflater.from(this.mContext).inflate(2130903165, null));
  }
  
  public void addAll(List<LineItem> paramList)
  {
    this.mLineItems.clear();
    this.mLineItems = paramList;
    this.mLineItems.add(0, null);
  }
  
  public LineItem getItem(int paramInt)
  {
    if ((paramInt > -1) && (paramInt < this.mLineItems.size())) {
      return (LineItem)this.mLineItems.get(paramInt);
    }
    return null;
  }
  
  public int getItemCount()
  {
    return this.mLineItems.size();
  }
  
  public int getItemViewType(int paramInt)
  {
    if (paramInt == 0) {
      return 0;
    }
    return 1;
  }
  
  public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    if (paramViewHolder.getClass().equals(CartHeaderViewHolder.class))
    {
      paramViewHolder = (CartHeaderViewHolder)paramViewHolder;
      localObject1 = this.mICartApplication.currentOrder.banner;
      if (!TextUtils.isEmpty((CharSequence)localObject1))
      {
        paramViewHolder.mFeeDeliveryReminderView.setVisibility(0);
        paramViewHolder.mFeeDeliveryReminder.setText(Html.fromHtml((String)localObject1));
        return;
      }
      paramViewHolder.mFeeDeliveryReminderView.setVisibility(8);
      return;
    }
    Object localObject1 = (LineItem)this.mLineItems.get(paramInt);
    paramViewHolder = (CartItemViewHolder)paramViewHolder;
    Object localObject2 = ((LineItem)localObject1).variant.getFirstImageSmallUrl();
    Picasso.with(this.mContext).cancelRequest(paramViewHolder.itemImage);
    Picasso.with(this.mContext).load((String)localObject2).error(2130837739).placeholder(2130837739).into(paramViewHolder.itemImage);
    paramViewHolder.itemName.setText(((LineItem)localObject1).variant.name);
    paramViewHolder.itemPriceEach.setText(((LineItem)localObject1).singleDisplayAmount + " / " + ((LineItem)localObject1).singleDisplayUnit);
    paramViewHolder.itemPrice.setText(((LineItem)localObject1).displayAmount);
    if (((LineItem)localObject1).isOutOfStock())
    {
      paramViewHolder.totalItemView.setVisibility(8);
      paramViewHolder.unavailable.setVisibility(0);
      paramViewHolder.container.setAlpha(0.3F);
      paramViewHolder.itemPriceEach.setTextColor(this.mICartApplication.getResources().getColor(2131493101));
      return;
    }
    paramViewHolder.totalItemView.setVisibility(0);
    paramViewHolder.unavailable.setVisibility(8);
    paramViewHolder.container.setAlpha(1.0F);
    paramViewHolder.itemPriceEach.setTextColor(this.mICartApplication.getResources().getColor(2131493086));
    paramInt = 0;
    if (this.mICartApplication.currentOrder != null) {
      paramInt = this.mICartApplication.currentOrder.getQuantityInCart(((LineItem)localObject1).variantId.longValue());
    }
    paramViewHolder.totalItemInTheCart.setText(String.valueOf(paramInt));
    localObject2 = ((LineItem)localObject1).maxOrderQuantity;
    if ((localObject2 != null) && (paramInt >= ((Integer)localObject2).intValue())) {
      paramViewHolder.btnAddItem.setEnabled(false);
    }
    for (;;)
    {
      paramViewHolder.btnSubItem.setEnabled(((LineItem)localObject1).btnSubItemEnabled);
      return;
      paramViewHolder.btnAddItem.setEnabled(true);
    }
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    if (paramInt == 0) {
      return this.mHeader;
    }
    return new CartItemViewHolder(LayoutInflater.from(this.mContext).inflate(2130903166, null));
  }
  
  public void setOnItemCartActionListener(OnItemCartActionListener paramOnItemCartActionListener)
  {
    this.mOnItemCartActionListener = paramOnItemCartActionListener;
  }
  
  public void setSubtotalPrice(String paramString)
  {
    if (this.mHeader != null) {
      this.mHeader.setSubtotalPrice(paramString);
    }
  }
  
  public class CartHeaderViewHolder
    extends RecyclerView.ViewHolder
  {
    @InjectView(2131558932)
    TextView mFeeDeliveryReminder;
    @InjectView(2131558931)
    View mFeeDeliveryReminderView;
    @InjectView(2131558933)
    TextView subtotalPrice;
    
    public CartHeaderViewHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
    }
    
    @OnClick({2131558934})
    void closeCart()
    {
      ((BaseActivity)CartItemAdapter.this.mContext).finish();
    }
    
    public void setSubtotalPrice(String paramString)
    {
      this.subtotalPrice.setText(paramString);
    }
  }
  
  public class CartItemViewHolder
    extends RecyclerView.ViewHolder
  {
    @InjectView(2131558909)
    Button btnAddItem;
    @InjectView(2131558907)
    Button btnSubItem;
    @InjectView(2131558513)
    RelativeLayout container;
    @InjectView(2131558935)
    ImageView itemImage;
    @InjectView(2131558936)
    TextView itemName;
    @InjectView(2131558938)
    TextView itemPrice;
    @InjectView(2131558937)
    TextView itemPriceEach;
    @InjectView(2131558908)
    TextView totalItemInTheCart;
    @InjectView(2131558939)
    View totalItemView;
    @InjectView(2131558940)
    TextView unavailable;
    
    public CartItemViewHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
    }
    
    @OnClick({2131558909})
    public void addItem(View paramView)
    {
      paramView = CartItemAdapter.this.getItem(getAdapterPosition());
      if (paramView == null) {
        return;
      }
      int i = Integer.parseInt(this.totalItemInTheCart.getText().toString()) + 1;
      paramView.btnSubItemEnabled = true;
      this.btnSubItem.setEnabled(paramView.btnSubItemEnabled);
      if ((paramView.maxOrderQuantity != null) && (i >= paramView.maxOrderQuantity.intValue()))
      {
        this.btnAddItem.setEnabled(false);
        Toast.makeText(CartItemAdapter.this.mContext, CartItemAdapter.this.mContext.getString(2131165424), 0).show();
      }
      for (;;)
      {
        this.totalItemInTheCart.setText("" + i);
        if (CartItemAdapter.this.mOnItemCartActionListener == null) {
          break;
        }
        CartItemAdapter.this.mOnItemCartActionListener.onItemUpdated(paramView, i);
        return;
        this.btnAddItem.setEnabled(true);
      }
    }
    
    @OnClick({2131558513})
    public void clickItem()
    {
      if (CartItemAdapter.this.mOnItemCartActionListener != null)
      {
        LineItem localLineItem = CartItemAdapter.this.getItem(getAdapterPosition());
        CartItemAdapter.this.mOnItemCartActionListener.onItemClick(localLineItem);
      }
    }
    
    @OnClick({2131558907})
    public void subItem(View paramView)
    {
      paramView = CartItemAdapter.this.getItem(getAdapterPosition());
      if (paramView == null) {
        return;
      }
      int i = Integer.parseInt(this.totalItemInTheCart.getText().toString()) - 1;
      if (i == 0) {}
      for (paramView.btnSubItemEnabled = false;; paramView.btnSubItemEnabled = true)
      {
        this.btnSubItem.setEnabled(paramView.btnSubItemEnabled);
        this.totalItemInTheCart.setText("" + i);
        if (CartItemAdapter.this.mOnItemCartActionListener == null) {
          break;
        }
        CartItemAdapter.this.mOnItemCartActionListener.onItemUpdated(paramView, i);
        return;
      }
    }
  }
  
  public static abstract interface OnItemCartActionListener
  {
    public abstract void onItemClick(LineItem paramLineItem);
    
    public abstract void onItemUpdated(LineItem paramLineItem, int paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/CartItemAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
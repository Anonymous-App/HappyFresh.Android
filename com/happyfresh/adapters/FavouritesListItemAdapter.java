package com.happyfresh.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.models.Order;
import com.happyfresh.models.Product;
import java.util.Iterator;
import java.util.List;

public class FavouritesListItemAdapter
  extends ShoppingListItemAdapter
{
  public FavouritesListItemAdapter(Context paramContext, ShoppingListItemAdapter.ShoppingListItemClickListener paramShoppingListItemClickListener)
  {
    super(paramContext, paramShoppingListItemClickListener);
  }
  
  public int getItemViewType(int paramInt)
  {
    return ((ShoppingListItemAdapter.BaseShopListItem)this.mList.get(paramInt)).getViewType();
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    if (paramInt == 1002) {
      return new HeaderListItemViewHolder(LayoutInflater.from(this.mContext).inflate(2130903171, null));
    }
    if (paramInt == 3001) {
      return new ShoppingListItemAdapter.BaseShoppingListItemViewHolder(this, LayoutInflater.from(this.mContext).inflate(2130903102, null));
    }
    if (paramInt == 3002) {
      return new ShoppingListItemAdapter.BaseShoppingListItemViewHolder(this, LayoutInflater.from(this.mContext).inflate(2130903103, null));
    }
    return new ShoppingListItemAdapter.ShoppingListItemViewHolder(this, LayoutInflater.from(this.mContext).inflate(2130903183, null));
  }
  
  public void setData(List<Product> paramList)
  {
    if (paramList == null) {}
    for (;;)
    {
      return;
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        Object localObject = (Product)paramList.next();
        String str = ((Product)localObject).description;
        if (String.valueOf(1002).equalsIgnoreCase(str))
        {
          this.mList.add(new HeaderListItem(((Product)localObject).name));
        }
        else if (String.valueOf(3001).equalsIgnoreCase(str))
        {
          this.mList.add(new EmptyFavouritesListItem());
        }
        else if (String.valueOf(3002).equalsIgnoreCase(str))
        {
          this.mList.add(new EmptyPrevPurchasedListItem());
        }
        else
        {
          int i = 0;
          if (this.mICartApplication.currentOrder != null) {
            i = this.mICartApplication.currentOrder.getQuantityInCart(((Product)localObject).remoteId.longValue());
          }
          localObject = new ShoppingListItemAdapter.ShopListItem(this, (Product)localObject, i);
          this.mList.add(localObject);
        }
      }
    }
  }
  
  protected class EmptyFavouritesListItem
    extends ShoppingListItemAdapter.BaseShopListItem
  {
    public EmptyFavouritesListItem()
    {
      super();
    }
    
    public int getViewType()
    {
      return 3001;
    }
  }
  
  protected class EmptyPrevPurchasedListItem
    extends ShoppingListItemAdapter.BaseShopListItem
  {
    public EmptyPrevPurchasedListItem()
    {
      super();
    }
    
    public int getViewType()
    {
      return 3002;
    }
  }
  
  protected class HeaderListItem
    extends ShoppingListItemAdapter.BaseShopListItem
  {
    protected String name;
    
    public HeaderListItem(String paramString)
    {
      super();
      this.name = paramString;
    }
    
    public int getViewType()
    {
      return 1002;
    }
  }
  
  public class HeaderListItemViewHolder
    extends ShoppingListItemAdapter.BaseShoppingListItemViewHolder
  {
    @InjectView(2131558569)
    TextView headerTitle;
    
    public HeaderListItemViewHolder(View paramView)
    {
      super(paramView);
      ButterKnife.inject(this, paramView);
    }
    
    public void bind(int paramInt)
    {
      FavouritesListItemAdapter.HeaderListItem localHeaderListItem = (FavouritesListItemAdapter.HeaderListItem)FavouritesListItemAdapter.this.mList.get(paramInt);
      this.headerTitle.setText(localHeaderListItem.name);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/FavouritesListItemAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
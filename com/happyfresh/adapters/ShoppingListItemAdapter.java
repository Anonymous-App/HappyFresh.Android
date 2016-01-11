package com.happyfresh.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.models.EditingCartInProgress;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.MultipleLineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.Product;
import com.happyfresh.models.Variant;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShoppingListItemAdapter
  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
  protected Context mContext;
  protected ICartApplication mICartApplication;
  protected List<BaseShopListItem> mList;
  protected ShoppingListItemClickListener mListener;
  
  public ShoppingListItemAdapter(Context paramContext, ShoppingListItemClickListener paramShoppingListItemClickListener)
  {
    this.mContext = paramContext;
    this.mICartApplication = ((ICartApplication)paramContext);
    this.mList = new ArrayList();
    this.mListener = paramShoppingListItemClickListener;
  }
  
  public void clearData()
  {
    if (this.mList != null) {
      this.mList.clear();
    }
  }
  
  public int getItemCount()
  {
    return this.mList.size();
  }
  
  public List<MultipleLineItem> getMultipleLineItem()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mList.iterator();
    while (localIterator.hasNext())
    {
      BaseShopListItem localBaseShopListItem = (BaseShopListItem)localIterator.next();
      if ((localBaseShopListItem instanceof ShopListItem))
      {
        MultipleLineItem localMultipleLineItem = new MultipleLineItem();
        localMultipleLineItem.variantId = ((Variant)localBaseShopListItem.getProduct().variants.get(0)).remoteId;
        localMultipleLineItem.quantity = localBaseShopListItem.getTotalAddToCart();
        localArrayList.add(localMultipleLineItem);
      }
    }
    return localArrayList;
  }
  
  public int isCartEdited()
  {
    int j = 1;
    int m = 1;
    int i = 0;
    Iterator localIterator = this.mList.iterator();
    while (localIterator.hasNext())
    {
      BaseShopListItem localBaseShopListItem = (BaseShopListItem)localIterator.next();
      if ((localBaseShopListItem instanceof ShopListItem))
      {
        int n = 0;
        int k = m;
        if (this.mICartApplication.currentOrder != null)
        {
          i1 = this.mICartApplication.currentOrder.getQuantityInCart(localBaseShopListItem.getProduct().remoteId.longValue());
          k = m;
          n = i1;
          if (i1 > 0)
          {
            k = 0;
            n = i1;
          }
        }
        int i1 = j;
        if (localBaseShopListItem.getTotalAddToCart() > 0) {
          i1 = 0;
        }
        m = k;
        j = i1;
        if (localBaseShopListItem.getTotalAddToCart() != n)
        {
          i = 1;
          m = k;
          j = i1;
        }
      }
    }
    if ((j != 0) && (m != 0)) {
      return 0;
    }
    if ((j == 0) && (m != 0)) {
      return 1;
    }
    if (i != 0) {
      return 2;
    }
    return 3;
  }
  
  public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    ((BaseShoppingListItemViewHolder)paramViewHolder).bind(paramInt);
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    return new ShoppingListItemViewHolder(LayoutInflater.from(this.mContext).inflate(2130903183, null));
  }
  
  public void resetTotalAddToCart()
  {
    Iterator localIterator = this.mList.iterator();
    while (localIterator.hasNext())
    {
      BaseShopListItem localBaseShopListItem = (BaseShopListItem)localIterator.next();
      if ((localBaseShopListItem instanceof ShopListItem))
      {
        LineItem localLineItem = this.mICartApplication.currentOrder.getLineItem(localBaseShopListItem.getProduct().remoteId.longValue());
        if (localLineItem != null) {
          localBaseShopListItem.setTotalAddToCart(localLineItem.quantity.intValue());
        }
      }
    }
  }
  
  public void setData(List<Product> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Object localObject = (Product)paramList.next();
      localObject = new ShopListItem((Product)localObject, this.mICartApplication.currentOrder.getQuantityInCart(((Product)localObject).remoteId.longValue()));
      this.mList.add(localObject);
    }
  }
  
  protected class BaseShopListItem
  {
    protected BaseShopListItem() {}
    
    public Product getProduct()
    {
      return null;
    }
    
    public int getTotalAddToCart()
    {
      return 0;
    }
    
    public int getViewType()
    {
      return 1002;
    }
    
    public void setTotalAddToCart(int paramInt) {}
  }
  
  public class BaseShoppingListItemViewHolder
    extends RecyclerView.ViewHolder
  {
    public BaseShoppingListItemViewHolder(View paramView)
    {
      super();
    }
    
    public void bind(int paramInt) {}
  }
  
  protected class ShopListItem
    extends ShoppingListItemAdapter.BaseShopListItem
  {
    protected Product product;
    protected int totalAddToCart;
    
    public ShopListItem(Product paramProduct, int paramInt)
    {
      super();
      this.product = paramProduct;
      this.totalAddToCart = paramInt;
    }
    
    public Product getProduct()
    {
      return this.product;
    }
    
    public int getTotalAddToCart()
    {
      return this.totalAddToCart;
    }
    
    public int getViewType()
    {
      return 2001;
    }
    
    public void setTotalAddToCart(int paramInt)
    {
      this.totalAddToCart = paramInt;
    }
  }
  
  public static abstract interface ShoppingListItemClickListener
  {
    public abstract void onCartQtyChanged();
    
    public abstract void onShoppingListItemClick(Product paramProduct);
  }
  
  public class ShoppingListItemViewHolder
    extends ShoppingListItemAdapter.BaseShoppingListItemViewHolder
  {
    @InjectView(2131558935)
    ImageView itemImage;
    @InjectView(2131558936)
    TextView itemName;
    @InjectView(2131558938)
    TextView itemPrice;
    @InjectView(2131559047)
    TextView totalAddToCart;
    @InjectView(2131559046)
    TextView totalInCart;
    @InjectView(2131558939)
    View totalItemView;
    
    public ShoppingListItemViewHolder(View paramView)
    {
      super(paramView);
      ButterKnife.inject(this, paramView);
    }
    
    @OnClick({2131558909})
    public void addItem(View paramView)
    {
      int i = Integer.parseInt(this.totalAddToCart.getText().toString()) + 1;
      ((ShoppingListItemAdapter.BaseShopListItem)ShoppingListItemAdapter.this.mList.get(getAdapterPosition())).setTotalAddToCart(i);
      this.totalAddToCart.setText("" + i);
      ShoppingListItemAdapter.this.mListener.onCartQtyChanged();
    }
    
    public void bind(int paramInt)
    {
      Object localObject = (ShoppingListItemAdapter.BaseShopListItem)ShoppingListItemAdapter.this.mList.get(paramInt);
      if (!(localObject instanceof ShoppingListItemAdapter.ShopListItem)) {
        return;
      }
      localObject = (ShoppingListItemAdapter.ShopListItem)localObject;
      Product localProduct = ((ShoppingListItemAdapter.ShopListItem)localObject).getProduct();
      Variant localVariant = (Variant)localProduct.variants.get(0);
      String str = localVariant.getFirstImageSmallUrl();
      Picasso.with(ShoppingListItemAdapter.this.mContext).cancelRequest(this.itemImage);
      Picasso.with(ShoppingListItemAdapter.this.mContext).load(str).error(2130837739).placeholder(2130837739).into(this.itemImage);
      this.itemName.setText(localVariant.name);
      this.itemPrice.setText(localProduct.displayPrice);
      paramInt = 0;
      if (ShoppingListItemAdapter.this.mICartApplication.currentOrder != null) {
        paramInt = ShoppingListItemAdapter.this.mICartApplication.currentOrder.getQuantityInCart(localVariant.remoteId);
      }
      int i = ShoppingListItemAdapter.this.mICartApplication.getEditingCartInProgress().getQuantityInProgressInCart(localVariant.remoteId);
      if (i != Integer.MIN_VALUE) {
        paramInt = i;
      }
      if (paramInt > 0)
      {
        this.totalInCart.setVisibility(0);
        this.totalInCart.setText("" + paramInt);
      }
      for (;;)
      {
        this.totalAddToCart.setText("" + ((ShoppingListItemAdapter.ShopListItem)localObject).getTotalAddToCart());
        return;
        this.totalInCart.setVisibility(8);
      }
    }
    
    @OnClick({2131559045})
    public void clickItem()
    {
      if (ShoppingListItemAdapter.this.mListener != null)
      {
        int i = getAdapterPosition();
        ShoppingListItemAdapter.this.mListener.onShoppingListItemClick(((ShoppingListItemAdapter.BaseShopListItem)ShoppingListItemAdapter.this.mList.get(i)).getProduct());
      }
    }
    
    @OnClick({2131558907})
    public void subItem(View paramView)
    {
      int i = Integer.parseInt(this.totalAddToCart.getText().toString()) - 1;
      if (i >= 0)
      {
        ((ShoppingListItemAdapter.BaseShopListItem)ShoppingListItemAdapter.this.mList.get(getAdapterPosition())).setTotalAddToCart(i);
        this.totalAddToCart.setText("" + i);
        ShoppingListItemAdapter.this.mListener.onCartQtyChanged();
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/ShoppingListItemAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
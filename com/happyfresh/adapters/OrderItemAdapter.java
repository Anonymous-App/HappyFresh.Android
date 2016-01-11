package com.happyfresh.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.activities.ViewProductActivity;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.listeners.OnAddToCartClickListener;
import com.happyfresh.models.Address;
import com.happyfresh.models.Country;
import com.happyfresh.models.ItemState;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.ManifestItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.Product;
import com.happyfresh.models.Replacement;
import com.happyfresh.models.Variant;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OrderItemAdapter
  extends ArrayAdapter<LineItem>
{
  private final String TAG = OrderItemAdapter.class.getSimpleName();
  private boolean mActiveOrder;
  private Context mContext;
  private Long mCurrentUserId;
  private boolean mHasReplacement;
  private ICartApplication mICartApplication;
  private List<ManifestItem> mManifest = new ArrayList();
  private NumberFormat mNumberFormat;
  private OnAddToCartClickListener mOnAddToCartClickListener;
  private Order mOrder;
  
  public OrderItemAdapter(Context paramContext, List<LineItem> paramList, Order paramOrder, boolean paramBoolean, List<ManifestItem> paramList1)
  {
    super(paramContext, 0, paramList);
    this.mContext = paramContext;
    this.mICartApplication = ((ICartApplication)this.mContext.getApplicationContext());
    this.mOrder = paramOrder;
    this.mCurrentUserId = paramOrder.userId;
    this.mActiveOrder = paramOrder.isActive();
    this.mHasReplacement = paramBoolean;
    this.mNumberFormat = this.mICartApplication.getNumberFormatter(paramOrder.shipAddress.country.isoName);
    this.mManifest = paramList1;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    final ViewHolder localViewHolder;
    final Object localObject2;
    Object localObject1;
    double d2;
    final Object localObject3;
    double d1;
    label443:
    double d3;
    Object localObject4;
    int i1;
    int i2;
    if (paramView == null)
    {
      paramViewGroup = LayoutInflater.from(this.mContext).inflate(2130903177, null);
      localViewHolder = new ViewHolder(paramViewGroup);
      paramViewGroup.setTag(localViewHolder);
      localObject2 = (LineItem)getItem(paramInt);
      localViewHolder.total.setText(String.valueOf(((LineItem)localObject2).quantity));
      paramView = ((LineItem)localObject2).variant.getFirstImageProductUrl();
      Picasso.with(this.mContext).load(paramView).error(2130837739).placeholder(2130837739).into(localViewHolder.img);
      localViewHolder.productName.setText(((LineItem)localObject2).variant.name);
      localViewHolder.productPrice.setText(((LineItem)localObject2).singleDisplayAmount);
      localViewHolder.orderItemContainer.setAlpha(1.0F);
      localViewHolder.totalPrice.setTextColor(this.mContext.getResources().getColor(2131493083));
      localViewHolder.totalPrice.setPaintFlags(0);
      localViewHolder.totalPrice.setText(((LineItem)localObject2).displayAmount);
      localViewHolder.orderItemRejectionView.setVisibility(8);
      j = 0;
      k = 0;
      localObject1 = null;
      d2 = ((LineItem)localObject2).total.doubleValue();
      localObject3 = this.mManifest.iterator();
      do
      {
        d1 = d2;
        i = j;
        paramInt = k;
        paramView = (View)localObject1;
        if (!((Iterator)localObject3).hasNext()) {
          break;
        }
        paramView = (ManifestItem)((Iterator)localObject3).next();
      } while (!paramView.variantId.equals(((LineItem)localObject2).variantId));
      paramInt = paramView.states.rejected;
      i = paramView.states.delivered;
      paramView = paramView.rejectedReason;
      d1 = ((LineItem)localObject2).price.doubleValue() * i;
      ((LineItem)localObject2).total.doubleValue();
      if ((!this.mActiveOrder) && (i != ((LineItem)localObject2).quantity.intValue()))
      {
        localViewHolder.totalPrice.setText(this.mNumberFormat.format((i + paramInt) * ((LineItem)localObject2).price.doubleValue()));
        if ((i != 0) || (paramInt != 0) || (this.mOrder.isCanceled())) {
          break label874;
        }
        localViewHolder.totalPrice.setTextColor(this.mContext.getResources().getColor(2131493057));
        localViewHolder.totalPrice.setText(this.mContext.getString(2131165496));
        localViewHolder.orderItemContainer.setAlpha(0.3F);
        localViewHolder.orderItemRejectionView.setAlpha(0.3F);
      }
      if (paramInt != 0)
      {
        localViewHolder.orderItemRejectionView.setVisibility(0);
        d2 = ((LineItem)localObject2).price.doubleValue();
        d3 = paramInt;
        localViewHolder.orderItemRejectionPrice.setText(this.mNumberFormat.format(Double.valueOf(d2 * d3)));
        localViewHolder.orderItemRejectionNumber.setText(this.mContext.getString(2131165615, new Object[] { Integer.valueOf(paramInt) }));
        localObject3 = localViewHolder.orderItemRejectionNotes;
        localObject4 = this.mContext;
        localObject1 = paramView;
        if (paramView == null) {
          localObject1 = "";
        }
        ((TextView)localObject3).setText(((Context)localObject4).getString(2131165477, new Object[] { localObject1 }));
        localViewHolder.totalPrice.setPaintFlags(16);
        localViewHolder.orderItemAcceptedPrice.setText(this.mNumberFormat.format(d1));
      }
      if (!this.mActiveOrder)
      {
        i1 = 0;
        n = 0;
        i2 = 0;
        k = i1;
        m = n;
        j = i2;
        if (this.mICartApplication.currentOrder != null)
        {
          paramView = this.mICartApplication.currentOrder.getLineItem(((LineItem)localObject2).variantId.longValue());
          k = i1;
          m = n;
          j = i2;
          if (paramView != null)
          {
            i1 = 1;
            i2 = paramView.quantity.intValue();
            k = i1;
            m = n;
            j = i2;
            if (paramView.isOutOfStock())
            {
              m = 1;
              j = i2;
              k = i1;
            }
          }
        }
        localViewHolder.orderItemAddView.setVisibility(0);
        if (k == 0) {
          break label948;
        }
        localViewHolder.orderItemAddProgress.setVisibility(8);
        if (m == 0) {
          break label913;
        }
        localViewHolder.orderItemAdd.setVisibility(0);
        label749:
        localViewHolder.itemContainer.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            paramAnonymousView = new Product();
            paramAnonymousView.remoteId = localObject2.variant.productId;
            paramAnonymousView.name = localObject2.variant.name;
            paramAnonymousView.variants.add(localObject2.variant);
            paramAnonymousView.availableOn = new Date();
            paramAnonymousView.description = localObject2.variant.description;
            Intent localIntent = new Intent(OrderItemAdapter.this.mContext, ViewProductActivity.class);
            localIntent.putExtra("ICartConstant.KEYS.EXTRAS.PRODUCT", paramAnonymousView);
            localIntent.putExtra("ICartConstant.KEYS.EXTRAS.FROM_ORDER_HISTORY", true);
            OrderItemAdapter.this.mContext.startActivity(localIntent);
          }
        });
        localViewHolder.orderItemAdd.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (OrderItemAdapter.this.mOnAddToCartClickListener != null) {
              OrderItemAdapter.this.mOnAddToCartClickListener.onClick(localViewHolder.orderItemAddView, localObject2);
            }
          }
        });
      }
      if (this.mHasReplacement)
      {
        if (((LineItem)localObject2).getCustomerReplacement(this.mCurrentUserId) != null) {
          break label980;
        }
        localViewHolder.replaceWith.setVisibility(8);
        localViewHolder.replaceItemContainer.setVisibility(8);
        label826:
        if (((LineItem)localObject2).getShopperReplacement(this.mCurrentUserId) != null) {
          break label2069;
        }
        localViewHolder.replaceShopperWith.setVisibility(8);
        localViewHolder.replaceShopperItemContainer.setVisibility(8);
      }
    }
    label874:
    label913:
    label948:
    label980:
    label1128:
    label1571:
    label1942:
    label2002:
    label2037:
    label2069:
    do
    {
      return paramViewGroup;
      localViewHolder = (ViewHolder)paramView.getTag();
      paramViewGroup = paramView;
      break;
      localViewHolder.total.setText(String.format("%d/%d", new Object[] { Integer.valueOf(i + paramInt), ((LineItem)localObject2).quantity }));
      break label443;
      localViewHolder.orderItemAdd.setVisibility(8);
      localViewHolder.orderItemAddInCart.setVisibility(0);
      localViewHolder.orderItemAddInCart.setText(String.valueOf(j));
      break label749;
      localViewHolder.orderItemAdd.setVisibility(0);
      localViewHolder.orderItemAddProgress.setVisibility(8);
      localViewHolder.orderItemAddInCart.setVisibility(8);
      break label749;
      localViewHolder.replaceWith.setVisibility(0);
      localViewHolder.replaceItemContainer.setVisibility(0);
      localObject3 = ((LineItem)localObject2).getCustomerReplacement(this.mCurrentUserId);
      paramView = ((Replacement)localObject3).variant.getFirstImageProductUrl();
      Picasso.with(this.mContext).load(paramView).error(2130837739).placeholder(2130837739).into(localViewHolder.imgReplacement);
      localViewHolder.productNameReplacement.setText(((Replacement)localObject3).variant.name);
      localViewHolder.productPriceReplacement.setText(this.mNumberFormat.format(((Replacement)localObject3).price));
      i1 = ((LineItem)localObject2).quantity.intValue();
      localViewHolder.orderReplaceItemRejectionView.setVisibility(8);
      if (this.mActiveOrder)
      {
        localViewHolder.replaceWith.setText(this.mContext.getString(2131165529));
        m = 0;
        n = 0;
        localObject1 = null;
        d3 = 0.0D;
        Object localObject5 = this.mManifest.iterator();
        do
        {
          j = n;
          k = m;
          d2 = d3;
          paramView = (View)localObject1;
          if (!((Iterator)localObject5).hasNext()) {
            break;
          }
          localObject4 = (ManifestItem)((Iterator)localObject5).next();
        } while (((ManifestItem)localObject4).variantId.longValue() != ((Replacement)localObject3).variant.remoteId);
        m = ((ManifestItem)localObject4).states.rejected;
        n = ((ManifestItem)localObject4).states.delivered;
        localObject1 = ((ManifestItem)localObject4).rejectedReason;
        d3 = ((Replacement)localObject3).price.doubleValue() * n;
        j = n;
        k = m;
        d2 = d3;
        paramView = (View)localObject1;
        if (((ManifestItem)localObject4).replacementId != null)
        {
          localViewHolder.replaceWith.setText(this.mContext.getString(2131165336));
          paramView = (View)localObject1;
          d2 = d3;
          k = m;
          j = n;
        }
        localViewHolder.replaceItemContainer.setAlpha(1.0F);
        d3 = ((Replacement)localObject3).price.doubleValue();
        d4 = j + k;
        localViewHolder.totalPriceReplacement.setTextColor(this.mContext.getResources().getColor(2131493083));
        localViewHolder.totalPriceReplacement.setPaintFlags(0);
        localViewHolder.totalPriceReplacement.setText(this.mNumberFormat.format(d3 * d4));
        if (!this.mActiveOrder)
        {
          localViewHolder.totalReplacement.setText(String.format("%d", new Object[] { Integer.valueOf(j + k) }));
          if ((j != i1 - i - paramInt) && (j == 0) && (k == 0))
          {
            localViewHolder.totalPriceReplacement.setTextColor(this.mContext.getResources().getColor(2131493057));
            localViewHolder.totalPriceReplacement.setText(this.mContext.getString(2131165496));
            localViewHolder.orderReplaceItemContainer.setAlpha(0.3F);
            localViewHolder.orderReplaceItemRejectionView.setAlpha(0.3F);
          }
        }
        d3 = d2 - d1;
        if (d3 <= 0.0D) {
          break label1942;
        }
        localViewHolder.orderReplaceItemPriceDifferential.setTextColor(this.mContext.getResources().getColor(2131493060));
        localViewHolder.orderReplaceItemPriceDifferential.setText(this.mNumberFormat.format(d3));
        localViewHolder.orderReplaceItemPriceDifferentialPlusSign.setVisibility(0);
        localViewHolder.orderReplaceItemPriceDifferentialMinusSign.setVisibility(8);
        if (k != 0)
        {
          localViewHolder.orderReplaceItemRejectionView.setVisibility(0);
          d3 = ((Replacement)localObject3).price.doubleValue();
          d4 = k;
          localViewHolder.orderReplaceItemRejectionPrice.setText(this.mNumberFormat.format(Double.valueOf(d3 * d4)));
          localViewHolder.orderReplaceItemRejectionNumber.setText(this.mContext.getString(2131165615, new Object[] { Integer.valueOf(k) }));
          localObject4 = localViewHolder.orderReplaceItemRejectionNotes;
          localObject5 = this.mContext;
          localObject1 = paramView;
          if (paramView == null) {
            localObject1 = "";
          }
          ((TextView)localObject4).setText(((Context)localObject5).getString(2131165477, new Object[] { localObject1 }));
          localViewHolder.totalPriceReplacement.setPaintFlags(16);
          localViewHolder.orderReplaceItemAcceptedPrice.setText(this.mNumberFormat.format(d2));
        }
        if (this.mActiveOrder) {
          break label826;
        }
        i1 = 0;
        n = 0;
        i2 = 0;
        k = i1;
        m = n;
        j = i2;
        if (this.mICartApplication.currentOrder != null)
        {
          paramView = this.mICartApplication.currentOrder.getLineItem(((Replacement)localObject3).variant.remoteId);
          k = i1;
          m = n;
          j = i2;
          if (paramView != null)
          {
            i1 = 1;
            i2 = paramView.quantity.intValue();
            k = i1;
            m = n;
            j = i2;
            if (paramView.isOutOfStock())
            {
              m = 1;
              j = i2;
              k = i1;
            }
          }
        }
        localViewHolder.orderReplaceItemAddView.setVisibility(0);
        if (k == 0) {
          break label2037;
        }
        localViewHolder.orderReplaceItemAddProgress.setVisibility(8);
        if (m == 0) {
          break label2002;
        }
        localViewHolder.orderReplaceItemAdd.setVisibility(0);
      }
      for (;;)
      {
        localViewHolder.replaceItemContainer.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            paramAnonymousView = new Product();
            paramAnonymousView.remoteId = localObject3.variant.productId;
            paramAnonymousView.name = localObject3.variant.name;
            paramAnonymousView.variants.add(localObject3.variant);
            paramAnonymousView.availableOn = new Date();
            paramAnonymousView.description = localObject3.variant.description;
            Intent localIntent = new Intent(OrderItemAdapter.this.mContext, ViewProductActivity.class);
            localIntent.putExtra("ICartConstant.KEYS.EXTRAS.PRODUCT", paramAnonymousView);
            OrderItemAdapter.this.mContext.startActivity(localIntent);
          }
        });
        localViewHolder.orderReplaceItemAdd.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (OrderItemAdapter.this.mOnAddToCartClickListener != null)
            {
              paramAnonymousView = new LineItem();
              paramAnonymousView.variantId = Long.valueOf(localObject3.variant.remoteId);
              paramAnonymousView.quantity = Integer.valueOf(Integer.parseInt(localViewHolder.totalReplacement.getText().toString()));
              OrderItemAdapter.this.mOnAddToCartClickListener.onReplacementClick(localViewHolder.orderReplaceItemAddView, paramAnonymousView);
            }
          }
        });
        break;
        localViewHolder.replaceWith.setText(this.mContext.getString(2131165336));
        break label1128;
        localViewHolder.orderReplaceItemPriceDifferential.setTextColor(this.mContext.getResources().getColor(2131493059));
        localViewHolder.orderReplaceItemPriceDifferential.setText(this.mNumberFormat.format(d3));
        localViewHolder.orderReplaceItemPriceDifferentialPlusSign.setVisibility(8);
        localViewHolder.orderReplaceItemPriceDifferentialMinusSign.setVisibility(0);
        break label1571;
        localViewHolder.orderReplaceItemAdd.setVisibility(8);
        localViewHolder.orderReplaceItemAddInCart.setVisibility(0);
        localViewHolder.orderReplaceItemAddInCart.setText(String.valueOf(j));
        continue;
        localViewHolder.orderReplaceItemAdd.setVisibility(0);
        localViewHolder.orderReplaceItemAddProgress.setVisibility(8);
        localViewHolder.orderReplaceItemAddInCart.setVisibility(8);
      }
      localViewHolder.replaceShopperWith.setVisibility(0);
      localViewHolder.replaceShopperItemContainer.setVisibility(0);
      localObject3 = ((LineItem)localObject2).getShopperReplacement(this.mCurrentUserId);
      paramView = ((Replacement)localObject3).variant.getFirstImageProductUrl();
      Picasso.with(this.mContext).load(paramView).error(2130837739).placeholder(2130837739).into(localViewHolder.imgReplacementShopper);
      localViewHolder.productNameReplacementShopper.setText(((Replacement)localObject3).variant.name);
      localViewHolder.productPriceReplacementShopper.setText(this.mNumberFormat.format(((Replacement)localObject3).price));
      i1 = ((LineItem)localObject2).quantity.intValue();
      localViewHolder.orderReplaceShopperItemRejectionView.setVisibility(8);
      localViewHolder.replaceShopperWith.setText(this.mContext.getString(2131165572));
      m = 0;
      n = 0;
      localObject1 = null;
      d3 = 0.0D;
      localObject2 = this.mManifest.iterator();
      do
      {
        k = n;
        j = m;
        d2 = d3;
        paramView = (View)localObject1;
        if (!((Iterator)localObject2).hasNext()) {
          break;
        }
        paramView = (ManifestItem)((Iterator)localObject2).next();
      } while (paramView.variantId.longValue() != ((Replacement)localObject3).variant.remoteId);
      j = paramView.states.rejected;
      k = paramView.states.delivered;
      paramView = paramView.rejectedReason;
      d2 = ((Replacement)localObject3).price.doubleValue() * k;
      localViewHolder.replaceShopperItemContainer.setAlpha(1.0F);
      d3 = ((Replacement)localObject3).price.doubleValue();
      double d4 = k + j;
      localViewHolder.totalPriceReplacementShopper.setTextColor(this.mContext.getResources().getColor(2131493083));
      localViewHolder.totalPriceReplacementShopper.setPaintFlags(0);
      localViewHolder.totalPriceReplacementShopper.setText(this.mNumberFormat.format(d3 * d4));
      if (!this.mActiveOrder)
      {
        localViewHolder.totalReplacementShopper.setText(String.format("%d", new Object[] { Integer.valueOf(k + j) }));
        if ((k != i1 - i - paramInt) && (k == 0) && (j == 0))
        {
          localViewHolder.totalPriceReplacementShopper.setTextColor(this.mContext.getResources().getColor(2131493057));
          localViewHolder.totalPriceReplacementShopper.setText(this.mContext.getString(2131165496));
          localViewHolder.orderReplaceShopperItemContainer.setAlpha(0.3F);
          localViewHolder.orderReplaceShopperItemRejectionView.setAlpha(0.3F);
        }
      }
      d1 = d2 - d1;
      if (d1 <= 0.0D) {
        break label2936;
      }
      localViewHolder.orderReplaceShopperItemPriceDifferential.setTextColor(this.mContext.getResources().getColor(2131493060));
      localViewHolder.orderReplaceShopperItemPriceDifferential.setText(this.mNumberFormat.format(d1));
      localViewHolder.orderReplaceShopperItemPriceDifferentialPlusSign.setVisibility(0);
      localViewHolder.orderReplaceShopperItemPriceDifferentialMinusSign.setVisibility(8);
      if (j != 0)
      {
        localViewHolder.orderReplaceShopperItemRejectionView.setVisibility(0);
        d1 = ((Replacement)localObject3).price.doubleValue();
        d3 = j;
        localViewHolder.orderReplaceShopperItemRejectionPrice.setText(this.mNumberFormat.format(Double.valueOf(d1 * d3)));
        localViewHolder.orderReplaceShopperItemRejectionNumber.setText(this.mContext.getString(2131165615, new Object[] { Integer.valueOf(j) }));
        localObject2 = localViewHolder.orderReplaceShopperItemRejectionNotes;
        localObject4 = this.mContext;
        localObject1 = paramView;
        if (paramView == null) {
          localObject1 = "";
        }
        ((TextView)localObject2).setText(((Context)localObject4).getString(2131165477, new Object[] { localObject1 }));
        localViewHolder.totalPriceReplacementShopper.setPaintFlags(16);
        localViewHolder.orderReplaceShopperItemAcceptedPrice.setText(this.mNumberFormat.format(d2));
      }
    } while (this.mActiveOrder);
    int m = 0;
    int k = 0;
    int n = 0;
    int i = m;
    int j = k;
    paramInt = n;
    if (this.mICartApplication.currentOrder != null)
    {
      paramView = this.mICartApplication.currentOrder.getLineItem(((Replacement)localObject3).variant.remoteId);
      i = m;
      j = k;
      paramInt = n;
      if (paramView != null)
      {
        m = 1;
        n = paramView.quantity.intValue();
        i = m;
        j = k;
        paramInt = n;
        if (paramView.isOutOfStock())
        {
          j = 1;
          paramInt = n;
          i = m;
        }
      }
    }
    localViewHolder.orderReplaceShopperItemAddView.setVisibility(0);
    if (i != 0)
    {
      localViewHolder.orderReplaceShopperItemAddProgress.setVisibility(8);
      if (j != 0) {
        localViewHolder.orderReplaceShopperItemAdd.setVisibility(0);
      }
    }
    for (;;)
    {
      localViewHolder.replaceShopperItemContainer.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          paramAnonymousView = new Product();
          paramAnonymousView.remoteId = localObject3.variant.productId;
          paramAnonymousView.name = localObject3.variant.name;
          paramAnonymousView.variants.add(localObject3.variant);
          paramAnonymousView.availableOn = new Date();
          paramAnonymousView.description = localObject3.variant.description;
          Intent localIntent = new Intent(OrderItemAdapter.this.mContext, ViewProductActivity.class);
          localIntent.putExtra("ICartConstant.KEYS.EXTRAS.PRODUCT", paramAnonymousView);
          OrderItemAdapter.this.mContext.startActivity(localIntent);
        }
      });
      localViewHolder.orderReplaceShopperItemAdd.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (OrderItemAdapter.this.mOnAddToCartClickListener != null)
          {
            paramAnonymousView = new LineItem();
            paramAnonymousView.variantId = Long.valueOf(localObject3.variant.remoteId);
            paramAnonymousView.quantity = Integer.valueOf(Integer.parseInt(localViewHolder.totalReplacementShopper.getText().toString()));
            OrderItemAdapter.this.mOnAddToCartClickListener.onShopperReplacementClick(localViewHolder.orderReplaceShopperItemAddView, paramAnonymousView);
          }
        }
      });
      return paramViewGroup;
      label2936:
      localViewHolder.orderReplaceShopperItemPriceDifferential.setTextColor(this.mContext.getResources().getColor(2131493059));
      localViewHolder.orderReplaceShopperItemPriceDifferential.setText(this.mNumberFormat.format(d1));
      localViewHolder.orderReplaceShopperItemPriceDifferentialPlusSign.setVisibility(8);
      localViewHolder.orderReplaceShopperItemPriceDifferentialMinusSign.setVisibility(0);
      break;
      localViewHolder.orderReplaceShopperItemAdd.setVisibility(8);
      localViewHolder.orderReplaceShopperItemAddInCart.setVisibility(0);
      localViewHolder.orderReplaceShopperItemAddInCart.setText(String.valueOf(paramInt));
      continue;
      localViewHolder.orderReplaceShopperItemAdd.setVisibility(0);
      localViewHolder.orderReplaceShopperItemAddProgress.setVisibility(8);
      localViewHolder.orderReplaceShopperItemAddInCart.setVisibility(8);
    }
  }
  
  public void setOnAddToCartClickListener(OnAddToCartClickListener paramOnAddToCartClickListener)
  {
    this.mOnAddToCartClickListener = paramOnAddToCartClickListener;
  }
  
  class MixpanelItem
  {
    String name;
    String sku;
    long supermarketId;
    
    MixpanelItem(String paramString1, String paramString2, long paramLong)
    {
      this.sku = paramString1;
      this.name = paramString2;
      this.supermarketId = paramLong;
    }
  }
  
  class ViewHolder
  {
    @InjectView(2131558961)
    ImageView img;
    @InjectView(2131558979)
    ImageView imgReplacement;
    @InjectView(2131559000)
    ImageView imgReplacementShopper;
    @InjectView(2131558958)
    View itemContainer;
    @InjectView(2131558974)
    TextView orderItemAcceptedPrice;
    @InjectView(2131558968)
    ImageView orderItemAdd;
    @InjectView(2131558969)
    TextView orderItemAddInCart;
    @InjectView(2131558967)
    CircularProgressBar orderItemAddProgress;
    @InjectView(2131558966)
    View orderItemAddView;
    @InjectView(2131558959)
    View orderItemContainer;
    @InjectView(2131558973)
    TextView orderItemRejectionNotes;
    @InjectView(2131558971)
    TextView orderItemRejectionNumber;
    @InjectView(2131558972)
    TextView orderItemRejectionPrice;
    @InjectView(2131558970)
    View orderItemRejectionView;
    @InjectView(2131558992)
    TextView orderReplaceItemAcceptedPrice;
    @InjectView(2131558986)
    ImageView orderReplaceItemAdd;
    @InjectView(2131558987)
    TextView orderReplaceItemAddInCart;
    @InjectView(2131558985)
    CircularProgressBar orderReplaceItemAddProgress;
    @InjectView(2131558984)
    View orderReplaceItemAddView;
    @InjectView(2131558977)
    View orderReplaceItemContainer;
    @InjectView(2131558995)
    TextView orderReplaceItemPriceDifferential;
    @InjectView(2131558993)
    TextView orderReplaceItemPriceDifferentialMinusSign;
    @InjectView(2131558994)
    TextView orderReplaceItemPriceDifferentialPlusSign;
    @InjectView(2131558991)
    TextView orderReplaceItemRejectionNotes;
    @InjectView(2131558989)
    TextView orderReplaceItemRejectionNumber;
    @InjectView(2131558990)
    TextView orderReplaceItemRejectionPrice;
    @InjectView(2131558988)
    View orderReplaceItemRejectionView;
    @InjectView(2131559013)
    TextView orderReplaceShopperItemAcceptedPrice;
    @InjectView(2131559007)
    ImageView orderReplaceShopperItemAdd;
    @InjectView(2131559008)
    TextView orderReplaceShopperItemAddInCart;
    @InjectView(2131559006)
    CircularProgressBar orderReplaceShopperItemAddProgress;
    @InjectView(2131559005)
    View orderReplaceShopperItemAddView;
    @InjectView(2131558998)
    View orderReplaceShopperItemContainer;
    @InjectView(2131559016)
    TextView orderReplaceShopperItemPriceDifferential;
    @InjectView(2131559014)
    TextView orderReplaceShopperItemPriceDifferentialMinusSign;
    @InjectView(2131559015)
    TextView orderReplaceShopperItemPriceDifferentialPlusSign;
    @InjectView(2131559012)
    TextView orderReplaceShopperItemRejectionNotes;
    @InjectView(2131559010)
    TextView orderReplaceShopperItemRejectionNumber;
    @InjectView(2131559011)
    TextView orderReplaceShopperItemRejectionPrice;
    @InjectView(2131559009)
    View orderReplaceShopperItemRejectionView;
    @InjectView(2131558963)
    TextView productName;
    @InjectView(2131558981)
    TextView productNameReplacement;
    @InjectView(2131559002)
    TextView productNameReplacementShopper;
    @InjectView(2131558964)
    TextView productPrice;
    @InjectView(2131558982)
    TextView productPriceReplacement;
    @InjectView(2131559003)
    TextView productPriceReplacementShopper;
    @InjectView(2131558976)
    View replaceItemContainer;
    @InjectView(2131558997)
    View replaceShopperItemContainer;
    @InjectView(2131558996)
    TextView replaceShopperWith;
    @InjectView(2131558975)
    TextView replaceWith;
    @InjectView(2131558960)
    TextView total;
    @InjectView(2131558965)
    TextView totalPrice;
    @InjectView(2131558983)
    TextView totalPriceReplacement;
    @InjectView(2131559004)
    TextView totalPriceReplacementShopper;
    @InjectView(2131558978)
    TextView totalReplacement;
    @InjectView(2131558999)
    TextView totalReplacementShopper;
    
    public ViewHolder(View paramView)
    {
      ButterKnife.inject(this, paramView);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/OrderItemAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
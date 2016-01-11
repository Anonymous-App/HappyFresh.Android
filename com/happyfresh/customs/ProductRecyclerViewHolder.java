package com.happyfresh.customs;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.andexert.library.RippleView;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.listeners.OnProductClickListener;
import com.happyfresh.models.DisplayPromoAction;
import com.happyfresh.models.EditingCartInProgress;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.Product;
import com.happyfresh.models.ProductGrid;
import com.happyfresh.models.Variant;
import com.happyfresh.utils.AutoSubmitCartEditedTimer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.Iterator;
import java.util.List;

public class ProductRecyclerViewHolder
  extends BaseRecyclerViewHolder
  implements View.OnClickListener
{
  public static int VIEW_GRID = 2;
  public static int VIEW_LINEAR_HORIZONTAL = 1;
  @InjectView(2131559033)
  View addToCartContainer;
  private AutoSubmitCartEditedTimer mAutoSubmitTimer;
  private Context mContext;
  private ICartApplication mICartApplication;
  private OnProductClickListener mItemClickListener;
  private List<ProductGrid> mProductGrids;
  private int mViewType;
  @InjectView(2131559036)
  View minusPlusCartContainer;
  @InjectView(2131559038)
  TextView productCartCounter;
  @InjectView(2131559022)
  CardView productContainer;
  @InjectView(2131559030)
  TextView productDiscount;
  @InjectView(2131559031)
  TextView productDiscountTotalOrder;
  @InjectView(2131559032)
  TextView productFreeDelivery;
  @InjectView(2131559026)
  ImageView productImageView;
  @InjectView(2131559027)
  TextView productNameTextView;
  @InjectView(2131559029)
  TextView productOriginalPriceTextView;
  @InjectView(2131559028)
  TextView productPriceTextView;
  @InjectView(2131559040)
  CircularProgressBar progressBar;
  @InjectView(2131559024)
  RippleView rippleView;
  
  public ProductRecyclerViewHolder(View paramView, ICartApplication paramICartApplication, Context paramContext, List<ProductGrid> paramList, OnProductClickListener paramOnProductClickListener, int paramInt)
  {
    super(paramView);
    ButterKnife.inject(this, paramView);
    this.mICartApplication = paramICartApplication;
    this.mContext = paramContext;
    this.mProductGrids = paramList;
    this.mItemClickListener = paramOnProductClickListener;
    this.mViewType = paramInt;
    this.rippleView.setOnClickListener(this);
    this.mAutoSubmitTimer = new AutoSubmitCartEditedTimer(this.mICartApplication, this.mContext, null);
  }
  
  private SpannableStringBuilder getPromoStringBuilder(String paramString)
  {
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(paramString);
    localSpannableStringBuilder.setSpan(new StrikethroughSpan(), 0, paramString.length(), 33);
    return localSpannableStringBuilder;
  }
  
  private Product getSelectedProduct()
  {
    int i = getAdapterPosition();
    if (i == -1) {
      return null;
    }
    return ((ProductGrid)this.mProductGrids.get(i)).getProduct();
  }
  
  private void setEvenMargin(int paramInt1, int paramInt2)
  {
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(this.productContainer.getLayoutParams());
    if (paramInt1 == 0) {
      localLayoutParams.leftMargin = paramInt2;
    }
    if (paramInt1 == this.mProductGrids.size() - 1) {}
    for (localLayoutParams.rightMargin = paramInt2;; localLayoutParams.rightMargin = (paramInt2 / 2))
    {
      localLayoutParams.topMargin = 0;
      localLayoutParams.bottomMargin = paramInt2;
      this.productContainer.setLayoutParams(localLayoutParams);
      return;
    }
  }
  
  private void setEvenMargin(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = (paramInt1 - 1) % paramInt2;
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(this.productContainer.getLayoutParams());
    if (i == 0)
    {
      localLayoutParams.leftMargin = paramInt3;
      if (i != paramInt2 - 1) {
        break label88;
      }
      localLayoutParams.rightMargin = paramInt3;
      label48:
      if (paramInt1 - 1 >= paramInt2) {
        break label99;
      }
    }
    label88:
    label99:
    for (localLayoutParams.topMargin = paramInt3;; localLayoutParams.topMargin = 0)
    {
      localLayoutParams.bottomMargin = paramInt3;
      this.productContainer.setLayoutParams(localLayoutParams);
      return;
      localLayoutParams.leftMargin = (paramInt3 / 2);
      break;
      localLayoutParams.rightMargin = (paramInt3 / 2);
      break label48;
    }
  }
  
  private void showMaxQtyReached()
  {
    Toast.makeText(this.mContext, this.mContext.getString(2131165424), 0).show();
  }
  
  public void bindViewHolder(int paramInt)
  {
    Object localObject2;
    Object localObject1;
    Object localObject3;
    Object localObject4;
    if (this.mViewType == VIEW_GRID)
    {
      setEvenMargin(paramInt, 2, dpToPx(16));
      localObject2 = ((ProductGrid)this.mProductGrids.get(paramInt)).getProduct();
      localObject1 = (Variant)((Product)localObject2).variants.get(0);
      this.productNameTextView.setText(((Variant)localObject1).name.trim());
      localObject3 = Html.fromHtml(this.mICartApplication.getString(2131165365, new Object[] { ((Product)localObject2).displayPrice, ((Product)localObject2).displayUnit }));
      this.productPriceTextView.setText("" + localObject3);
      if (!((Product)localObject2).hasPromo()) {
        break label418;
      }
      localObject3 = ((Product)localObject2).displayNormalPrice;
      localObject4 = ((Product)localObject2).displayPromoPercentage;
      if ((!TextUtils.isEmpty((CharSequence)localObject3)) || (!TextUtils.isEmpty((CharSequence)localObject4))) {
        break label326;
      }
      this.productOriginalPriceTextView.setVisibility(8);
      this.productDiscount.setVisibility(8);
      label190:
      this.productDiscountTotalOrder.setVisibility(8);
      this.productFreeDelivery.setVisibility(8);
      localObject2 = ((Product)localObject2).displayPromoActions;
      if (localObject2 == null) {
        break label454;
      }
      localObject3 = ((List)localObject2).iterator();
    }
    for (;;)
    {
      if (!((Iterator)localObject3).hasNext()) {
        break label454;
      }
      localObject4 = (DisplayPromoAction)((Iterator)localObject3).next();
      if (((DisplayPromoAction)localObject4).isPromoAdjustment())
      {
        this.productDiscountTotalOrder.setVisibility(0);
        this.productDiscountTotalOrder.setText(((DisplayPromoAction)localObject4).displayShortText);
        if (((List)localObject2).size() != 1) {
          continue;
        }
        this.productFreeDelivery.setVisibility(8);
        continue;
        if (this.mViewType != VIEW_LINEAR_HORIZONTAL) {
          break;
        }
        setEvenMargin(paramInt, dpToPx(16));
        break;
        label326:
        this.productOriginalPriceTextView.setVisibility(0);
        this.productOriginalPriceTextView.setText(getPromoStringBuilder((String)localObject3));
        this.productDiscount.setVisibility(0);
        this.productDiscount.setText((CharSequence)localObject4);
        break label190;
      }
      if (((DisplayPromoAction)localObject4).isPromoFreeDelivery())
      {
        this.productFreeDelivery.setVisibility(0);
        this.productFreeDelivery.setText(((DisplayPromoAction)localObject4).displayShortText);
        if (((List)localObject2).size() == 1) {
          this.productDiscountTotalOrder.setVisibility(8);
        }
      }
    }
    label418:
    this.productOriginalPriceTextView.setVisibility(8);
    this.productDiscount.setVisibility(8);
    this.productDiscountTotalOrder.setVisibility(8);
    this.productFreeDelivery.setVisibility(8);
    label454:
    paramInt = 0;
    if (this.mICartApplication.currentOrder != null) {
      paramInt = this.mICartApplication.currentOrder.getQuantityInCart(((Variant)localObject1).remoteId, this.mICartApplication.getStockLocationId());
    }
    int j = this.mICartApplication.getEditingCartInProgress().getQuantityInProgressInCart(((Variant)localObject1).remoteId);
    int i;
    int k;
    if (j != Integer.MIN_VALUE)
    {
      i = 1;
      if (i != 0) {
        paramInt = j;
      }
      k = this.mICartApplication.getEditingCartInProgress().getQuantityCountdownInCart(((Variant)localObject1).remoteId);
      if (k == Integer.MIN_VALUE) {
        break label659;
      }
    }
    label659:
    for (j = 1;; j = 0)
    {
      if (j != 0) {
        paramInt = k;
      }
      setProductCounter(paramInt);
      localObject1 = ((Variant)localObject1).getFirstImageProductUrl();
      Picasso.with(this.mContext).cancelRequest(this.productImageView);
      Picasso.with(this.mContext).load((String)localObject1).error(2130837739).placeholder(2130837739).into(this.productImageView);
      if (i == 0) {
        break label664;
      }
      this.progressBar.setVisibility(0);
      this.productCartCounter.setVisibility(4);
      this.addToCartContainer.setVisibility(8);
      if (paramInt == 0) {
        this.minusPlusCartContainer.setVisibility(8);
      }
      return;
      i = 0;
      break;
    }
    label664:
    this.progressBar.setVisibility(8);
    this.productCartCounter.setVisibility(0);
    if (paramInt == 0)
    {
      this.addToCartContainer.setVisibility(0);
      this.minusPlusCartContainer.setVisibility(8);
      return;
    }
    this.addToCartContainer.setVisibility(8);
    this.minusPlusCartContainer.setVisibility(0);
  }
  
  public int dpToPx(int paramInt)
  {
    DisplayMetrics localDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
    return Math.round(paramInt * (localDisplayMetrics.xdpi / 160.0F));
  }
  
  protected LineItem getLineItem(long paramLong)
  {
    if (this.mICartApplication.currentOrder != null) {
      return this.mICartApplication.currentOrder.getLineItem(paramLong, this.mICartApplication.getStockLocationId());
    }
    return null;
  }
  
  protected Variant getVariant(int paramInt)
  {
    if (paramInt == -1) {
      return null;
    }
    return (Variant)((ProductGrid)this.mProductGrids.get(paramInt)).getProduct().variants.get(0);
  }
  
  protected boolean isInProgress()
  {
    boolean bool = false;
    Variant localVariant = getVariant(getAdapterPosition());
    if (localVariant == null) {
      return false;
    }
    if (this.mICartApplication.getEditingCartInProgress().getQuantityInProgressInCart(localVariant.remoteId) != Integer.MIN_VALUE) {
      bool = true;
    }
    return bool;
  }
  
  @OnClick({2131559033})
  void onAddToCartContainerClick()
  {
    if (isInProgress()) {}
    Variant localVariant;
    do
    {
      return;
      localVariant = getVariant(getAdapterPosition());
    } while (localVariant == null);
    this.addToCartContainer.setVisibility(8);
    this.minusPlusCartContainer.setVisibility(0);
    setProductCounter(1);
    this.mAutoSubmitTimer.setAddAction(localVariant.remoteId, 1);
  }
  
  public void onClick(final View paramView)
  {
    final Product localProduct = getSelectedProduct();
    if ((this.mItemClickListener != null) && (localProduct != null)) {
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          ProductRecyclerViewHolder.this.mItemClickListener.onItemClick(paramView, localProduct);
        }
      }, 100L);
    }
  }
  
  @OnClick({2131559037})
  void onMinusCartClick()
  {
    if (isInProgress()) {}
    Variant localVariant;
    do
    {
      return;
      localVariant = getVariant(getAdapterPosition());
    } while (localVariant == null);
    int i = Integer.parseInt(this.productCartCounter.getText().toString()) - 1;
    setProductCounter(i);
    LineItem localLineItem = getLineItem(localVariant.remoteId);
    if (i == 0)
    {
      this.addToCartContainer.setVisibility(0);
      this.minusPlusCartContainer.setVisibility(8);
      this.mAutoSubmitTimer.setRemoveAction(localLineItem, localVariant.remoteId);
      return;
    }
    this.mAutoSubmitTimer.setUpdateAction(localLineItem, localVariant.remoteId, i);
  }
  
  @OnClick({2131559039})
  void onPlusCartClick()
  {
    if (isInProgress()) {
      return;
    }
    Object localObject = ((ProductGrid)this.mProductGrids.get(getAdapterPosition())).getProduct();
    Variant localVariant = (Variant)((Product)localObject).variants.get(0);
    int i = Integer.parseInt(this.productCartCounter.getText().toString());
    localObject = ((Product)localObject).maxOrderQuantity;
    if ((localObject != null) && (((Integer)localObject).intValue() == i))
    {
      showMaxQtyReached();
      return;
    }
    i += 1;
    if ((localObject != null) && (i >= ((Integer)localObject).intValue())) {
      showMaxQtyReached();
    }
    setProductCounter(i);
    localObject = getLineItem(localVariant.remoteId);
    this.mAutoSubmitTimer.setUpdateAction((LineItem)localObject, localVariant.remoteId, i);
  }
  
  public void setProductCounter(int paramInt)
  {
    this.productCartCounter.setText(String.valueOf(paramInt));
    if (paramInt == 0)
    {
      this.addToCartContainer.setVisibility(0);
      this.minusPlusCartContainer.setVisibility(8);
      return;
    }
    this.addToCartContainer.setVisibility(8);
    this.minusPlusCartContainer.setVisibility(0);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/ProductRecyclerViewHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
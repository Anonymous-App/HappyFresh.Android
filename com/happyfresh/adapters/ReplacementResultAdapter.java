package com.happyfresh.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.listeners.OnReplacementUpdated;
import com.happyfresh.managers.ReplacementManager;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.Product;
import com.happyfresh.models.Replacement;
import com.happyfresh.models.Variant;
import com.happyfresh.utils.GAUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;

public class ReplacementResultAdapter
  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
  private static final String TAG = ReplacementResultAdapter.class.getSimpleName();
  private ICartApplication mApplication;
  private Context mContext;
  private LineItem mLineItem;
  OnReplacementUpdated mOnReplacementUpdated;
  private List<Product> mResults = new ArrayList();
  
  public ReplacementResultAdapter(Context paramContext, ICartApplication paramICartApplication, LineItem paramLineItem)
  {
    this.mContext = paramContext;
    this.mApplication = paramICartApplication;
    this.mLineItem = paramLineItem;
  }
  
  private NumberFormat getNumberFormatter()
  {
    Object localObject = this.mApplication.getCountryCode();
    localObject = NumberFormat.getCurrencyInstance(this.mApplication.getLocaleByCountryCode((String)localObject));
    ((NumberFormat)localObject).setMinimumFractionDigits(2);
    return (NumberFormat)localObject;
  }
  
  public void addAll(List<Product> paramList)
  {
    this.mResults.clear();
    this.mResults = paramList;
  }
  
  void addReplacement(Replacement paramReplacement)
  {
    List localList = this.mApplication.currentOrder.lineItems;
    ((LineItem)localList.get(localList.indexOf(this.mLineItem))).setReplacement(paramReplacement);
    this.mApplication.currentOrder.lineItems = new ArrayList(localList);
  }
  
  public void clear()
  {
    this.mResults.clear();
  }
  
  void deleteReplacement()
  {
    List localList = this.mApplication.currentOrder.lineItems;
    ((LineItem)localList.get(localList.indexOf(this.mLineItem))).setReplacement(null);
    this.mApplication.currentOrder.lineItems = new ArrayList(localList);
  }
  
  public int getItemCount()
  {
    return this.mResults.size();
  }
  
  public void onBindViewHolder(final RecyclerView.ViewHolder paramViewHolder, final int paramInt)
  {
    Product localProduct = (Product)this.mResults.get(paramInt);
    Object localObject = (Variant)localProduct.variants.get(0);
    paramViewHolder = (ReplacementProductViewHolder)paramViewHolder;
    paramViewHolder.replaceButtonProgress.setVisibility(4);
    paramViewHolder.productName.setText(((Variant)localObject).name);
    localObject = ((Variant)localObject).getFirstImageProductUrl();
    Picasso.with(this.mContext).cancelRequest(paramViewHolder.productImage);
    Picasso.with(this.mContext).load((String)localObject).error(2130837739).placeholder(2130837739).into(paramViewHolder.productImage);
    localObject = Double.valueOf(localProduct.price.doubleValue() - this.mLineItem.price.doubleValue());
    String str = getNumberFormatter().format(localObject);
    int i;
    if (((Double)localObject).doubleValue() > 0.0D)
    {
      i = 2131165544;
      localObject = this.mContext.getString(i, new Object[] { localProduct.displayPrice, str });
      paramViewHolder.productPrice.setText(Html.fromHtml((String)localObject));
      if ((this.mLineItem.getReplacement() != null) && (this.mLineItem.getReplacement().variant.remoteId == ((Variant)localProduct.variants.get(0)).remoteId)) {
        localProduct.replaced = true;
      }
      if (!localProduct.replaced) {
        break label297;
      }
      paramViewHolder.showAsReplaced();
    }
    for (;;)
    {
      paramViewHolder.replaceButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          paramViewHolder.showProgress(true);
          ReplacementResultAdapter.this.updateReplacement((Product)ReplacementResultAdapter.this.mResults.get(paramInt), paramViewHolder, paramInt);
        }
      });
      paramViewHolder.replaceArea.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          paramViewHolder.showProgress(true);
          ReplacementResultAdapter.this.updateReplacement((Product)ReplacementResultAdapter.this.mResults.get(paramInt), paramViewHolder, paramInt);
        }
      });
      return;
      i = 2131165545;
      break;
      label297:
      paramViewHolder.showAsNormal();
    }
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    return new ReplacementProductViewHolder(LayoutInflater.from(this.mContext).inflate(2130903222, null));
  }
  
  void sendCreateReplacement(final Product paramProduct, final ReplacementProductViewHolder paramReplacementProductViewHolder, final int paramInt)
  {
    try
    {
      this.mApplication.getReplacementManager().createReplacement(this.mLineItem, paramProduct, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          if (ReplacementResultAdapter.this.mContext == null) {
            return;
          }
          paramReplacementProductViewHolder.showProgress(false);
          paramReplacementProductViewHolder.showAsNormal();
        }
        
        public void onSuccess(Replacement paramAnonymousReplacement)
        {
          if (ReplacementResultAdapter.this.mContext == null) {
            return;
          }
          String str = String.format("original: %s, replacement: %s", new Object[] { ReplacementResultAdapter.this.mLineItem.variant.name, paramProduct.name });
          GAUtils.trackReplacement(ReplacementResultAdapter.this.mContext, str);
          paramReplacementProductViewHolder.showProgress(false);
          ReplacementResultAdapter.this.addReplacement(paramAnonymousReplacement);
          paramReplacementProductViewHolder.showAsReplaced();
          ReplacementResultAdapter.this.setAsReplaced(paramInt, true);
          ReplacementResultAdapter.this.triggerReplacementUpdate(2131165369);
        }
      });
      return;
    }
    catch (JSONException paramProduct)
    {
      paramProduct.printStackTrace();
      return;
    }
    catch (UnsupportedEncodingException paramProduct)
    {
      paramProduct.printStackTrace();
    }
  }
  
  void sendDeleteReplacement(final ICartCallback<Object> paramICartCallback, final ReplacementProductViewHolder paramReplacementProductViewHolder, final int paramInt)
  {
    this.mApplication.getReplacementManager().deleteReplacement(this.mLineItem, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        if (ReplacementResultAdapter.this.mContext == null) {
          return;
        }
        paramReplacementProductViewHolder.showProgress(false);
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        if (ReplacementResultAdapter.this.mContext == null) {
          return;
        }
        ReplacementResultAdapter.this.deleteReplacement();
        if (paramICartCallback != null)
        {
          paramICartCallback.onSuccess(paramAnonymousObject);
          paramReplacementProductViewHolder.showAsReplaced();
        }
        for (;;)
        {
          ReplacementResultAdapter.this.triggerReplacementUpdate(2131165369);
          return;
          paramReplacementProductViewHolder.showProgress(false);
          paramReplacementProductViewHolder.showAsNormal();
          ReplacementResultAdapter.this.setAsReplaced(paramInt, false);
        }
      }
    });
  }
  
  void setAsReplaced(int paramInt, boolean paramBoolean)
  {
    int i = 0;
    Iterator localIterator = this.mResults.iterator();
    if (localIterator.hasNext())
    {
      Product localProduct = (Product)localIterator.next();
      if (i == paramInt) {}
      for (localProduct.replaced = paramBoolean;; localProduct.replaced = false)
      {
        i += 1;
        break;
      }
    }
    notifyDataSetChanged();
  }
  
  public void setOnReplaceListener(OnReplacementUpdated paramOnReplacementUpdated)
  {
    this.mOnReplacementUpdated = paramOnReplacementUpdated;
  }
  
  void triggerReplacementUpdate(int paramInt)
  {
    if (this.mOnReplacementUpdated != null) {
      this.mOnReplacementUpdated.onUpdated(paramInt);
    }
  }
  
  void updateReplacement(final Product paramProduct, final ReplacementProductViewHolder paramReplacementProductViewHolder, final int paramInt)
  {
    if (this.mLineItem.getReplacement() != null)
    {
      ICartCallback local3 = null;
      if (((Variant)paramProduct.variants.get(0)).remoteId != this.mLineItem.getReplacement().variant.remoteId) {
        local3 = new ICartCallback(TAG)
        {
          public void onSuccess(Object paramAnonymousObject)
          {
            ReplacementResultAdapter.this.sendCreateReplacement(paramProduct, paramReplacementProductViewHolder, paramInt);
          }
        };
      }
      sendDeleteReplacement(local3, paramReplacementProductViewHolder, paramInt);
      return;
    }
    sendCreateReplacement(paramProduct, paramReplacementProductViewHolder, paramInt);
  }
  
  class ReplacementProductViewHolder
    extends RecyclerView.ViewHolder
  {
    @InjectView(2131559096)
    ImageView productImage;
    @InjectView(2131559098)
    TextView productName;
    @InjectView(2131559099)
    TextView productPrice;
    @InjectView(2131559095)
    View replaceArea;
    @InjectView(2131559101)
    ImageView replaceButton;
    @InjectView(2131559097)
    View replaceButtonContainer;
    @InjectView(2131559100)
    View replaceButtonProgress;
    
    public ReplacementProductViewHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
    }
    
    public void showAsNormal()
    {
      this.replaceButton.setImageResource(2130837793);
    }
    
    public void showAsReplaced()
    {
      this.replaceButton.setImageResource(2130837800);
    }
    
    public void showProgress(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        this.replaceButton.setVisibility(8);
        this.replaceButtonProgress.setVisibility(0);
        return;
      }
      this.replaceButton.setVisibility(0);
      this.replaceButtonProgress.setVisibility(4);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/ReplacementResultAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
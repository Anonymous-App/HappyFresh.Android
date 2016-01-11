package com.happyfresh.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.listeners.OnItemClickListener;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.Photo;
import com.happyfresh.models.Slot;
import com.happyfresh.models.StockLocation;
import com.happyfresh.utils.DateUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StoreAdapter
  extends RecyclerView.Adapter<ViewHolder>
{
  private static final String TAG = StoreAdapter.class.getSimpleName();
  private Context mContext;
  private ICartApplication mICartApplication;
  OnItemClickListener mOnItemClickListener;
  private List<StockLocation> mStockLocation = new ArrayList();
  
  public StoreAdapter(Context paramContext)
  {
    this.mContext = paramContext;
    this.mICartApplication = ((ICartApplication)paramContext.getApplicationContext());
  }
  
  public int dpToPx(float paramFloat)
  {
    return Math.round(this.mContext.getResources().getDisplayMetrics().xdpi / 160.0F * paramFloat);
  }
  
  public int getItemCount()
  {
    return this.mStockLocation.size();
  }
  
  public int getItemViewType(int paramInt)
  {
    if (paramInt == 0) {
      return 1;
    }
    return 0;
  }
  
  public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt)
  {
    Object localObject = (StockLocation)this.mStockLocation.get(paramInt);
    if (localObject != null)
    {
      paramViewHolder.stockLocationName.setText(((StockLocation)localObject).name);
      Photo localPhoto = ((StockLocation)localObject).photo;
      if (localPhoto != null) {
        Picasso.with(this.mContext).load(localPhoto.url).placeholder(2130837740).error(2130837740).skipMemoryCache().into(paramViewHolder.locationImage);
      }
      for (;;)
      {
        if (((StockLocation)localObject).nextAvailableSlot != null)
        {
          localObject = ((StockLocation)localObject).nextAvailableSlot;
          paramViewHolder.nextDeliveryDate.setText(this.mContext.getString(2131165462, new Object[] { DateUtils.showUtcInLocalForDeliverySlot(((Slot)localObject).startTime, ((Slot)localObject).endTime, this.mContext.getString(2131165610).toLowerCase(), this.mContext.getString(2131165611).toLowerCase()) }));
          if (paramInt == getItemCount() - 1)
          {
            localObject = new FrameLayout.LayoutParams(paramViewHolder.storeContainer.getLayoutParams());
            ((FrameLayout.LayoutParams)localObject).setMargins(dpToPx(9.5F), dpToPx(10.0F), dpToPx(9.5F), dpToPx(10.0F));
            paramViewHolder.storeContainer.setLayoutParams((ViewGroup.LayoutParams)localObject);
          }
        }
        return;
        Picasso.with(this.mContext).load(2130837740).into(paramViewHolder.locationImage);
      }
    }
    paramInt = this.mStockLocation.size() - 1;
    if (paramInt == 1)
    {
      paramViewHolder.storeAvailable.setText(this.mContext.getString(2131165479, new Object[] { this.mICartApplication.getSharedPreferencesManager().getSubDistrictName() }));
      return;
    }
    paramViewHolder.storeAvailable.setText(this.mContext.getString(2131165288, new Object[] { Integer.valueOf(paramInt), this.mICartApplication.getSharedPreferencesManager().getSubDistrictName() }));
  }
  
  public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    if (paramInt == 0) {}
    for (paramViewGroup = LayoutInflater.from(this.mContext).inflate(2130903185, paramViewGroup, false);; paramViewGroup = LayoutInflater.from(this.mContext).inflate(2130903173, paramViewGroup, false)) {
      return new ViewHolder(paramViewGroup);
    }
  }
  
  public void setData(List<StockLocation> paramList)
  {
    this.mStockLocation.clear();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      StockLocation localStockLocation = (StockLocation)paramList.next();
      if (localStockLocation.nextAvailableSlot != null) {
        this.mStockLocation.add(localStockLocation);
      }
    }
    this.mStockLocation.add(0, null);
    notifyDataSetChanged();
  }
  
  public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
  {
    this.mOnItemClickListener = paramOnItemClickListener;
  }
  
  public class ViewHolder
    extends RecyclerView.ViewHolder
    implements View.OnClickListener
  {
    ImageView locationImage;
    TextView nextDeliveryDate;
    TextView stockLocationName;
    TextView storeAvailable;
    CardView storeContainer;
    
    public ViewHolder(View paramView)
    {
      super();
      this.storeContainer = ((CardView)paramView.findViewById(2131559050));
      this.storeAvailable = ((TextView)paramView.findViewById(2131558950));
      this.stockLocationName = ((TextView)paramView.findViewById(2131559052));
      this.nextDeliveryDate = ((TextView)paramView.findViewById(2131559053));
      this.locationImage = ((ImageView)paramView.findViewById(2131559051));
      paramView.setOnClickListener(this);
    }
    
    public void onClick(View paramView)
    {
      if (StoreAdapter.this.getItemViewType(getPosition()) == 0)
      {
        StockLocation localStockLocation = (StockLocation)StoreAdapter.this.mStockLocation.get(getPosition());
        if (StoreAdapter.this.mOnItemClickListener != null) {
          StoreAdapter.this.mOnItemClickListener.onItemClick(paramView, localStockLocation);
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/StoreAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
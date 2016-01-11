package com.happyfresh.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.listeners.OnItemClickListener;
import com.happyfresh.models.Order;
import com.happyfresh.models.OrderHistory;
import com.happyfresh.models.Shipment;
import com.happyfresh.models.Slot;
import com.happyfresh.utils.DateUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderAdapter
  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
  private Context mContext;
  OnItemClickListener mOnItemClickListener;
  private List<OrderHistory> mOrderHistory = new ArrayList();
  
  public OrderAdapter(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void addAll(List<Order> paramList)
  {
    this.mOrderHistory.clear();
    int i = 0;
    boolean bool1 = false;
    Iterator localIterator = paramList.iterator();
    if (localIterator.hasNext())
    {
      Order localOrder = (Order)localIterator.next();
      OrderHistory localOrderHistory;
      label74:
      boolean bool2;
      if (i == 0)
      {
        localOrderHistory = new OrderHistory();
        if (localOrder.isActive())
        {
          paramList = this.mContext.getString(2131165264);
          localOrderHistory.headerTitle = paramList;
          localOrderHistory.viewType = 1002;
          this.mOrderHistory.add(localOrderHistory);
          bool1 = localOrder.isActive();
        }
      }
      else
      {
        bool2 = bool1;
        if (bool1 != localOrder.isActive())
        {
          localOrderHistory = new OrderHistory();
          if (!localOrder.isActive()) {
            break label233;
          }
        }
      }
      label233:
      for (paramList = this.mContext.getString(2131165264);; paramList = this.mContext.getString(2131165393))
      {
        localOrderHistory.headerTitle = paramList;
        localOrderHistory.viewType = 1002;
        this.mOrderHistory.add(localOrderHistory);
        bool2 = localOrder.isActive();
        paramList = new OrderHistory();
        paramList.order = localOrder;
        paramList.viewType = 2001;
        this.mOrderHistory.add(paramList);
        i += 1;
        bool1 = bool2;
        break;
        paramList = this.mContext.getString(2131165393);
        break label74;
      }
    }
    notifyDataSetChanged();
  }
  
  public int getItemCount()
  {
    return this.mOrderHistory.size();
  }
  
  public int getItemViewType(int paramInt)
  {
    return ((OrderHistory)this.mOrderHistory.get(paramInt)).viewType;
  }
  
  public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    Object localObject = (OrderHistory)this.mOrderHistory.get(paramInt);
    if (((OrderHistory)localObject).viewType == 1002)
    {
      ((HeaderViewHolder)paramViewHolder).orderTitle.setText(((OrderHistory)localObject).headerTitle);
      return;
    }
    ItemViewHolder localItemViewHolder = (ItemViewHolder)paramViewHolder;
    Order localOrder = ((OrderHistory)localObject).order;
    if (localOrder.shipments.size() == 0)
    {
      localItemViewHolder.orderStockLocation.setText("");
      localItemViewHolder.orderDate.setText("");
    }
    for (;;)
    {
      localItemViewHolder.orderStatus.setText(localOrder.getStatus(this.mContext));
      return;
      localItemViewHolder.orderStockLocation.setText(((Shipment)localOrder.shipments.get(0)).stockLocationName);
      localObject = ((Shipment)localOrder.shipments.get(0)).slot;
      paramViewHolder = (RecyclerView.ViewHolder)localObject;
      if (localObject == null) {
        paramViewHolder = ((Shipment)localOrder.shipments.get(0)).canceledSlot;
      }
      if (paramViewHolder == null) {
        localItemViewHolder.orderDate.setText("");
      } else {
        localItemViewHolder.orderDate.setText(DateUtils.showUtcInLocal(paramViewHolder.startTime, paramViewHolder.endTime));
      }
    }
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    if (paramInt == 1002) {
      return new HeaderViewHolder(LayoutInflater.from(this.mContext).inflate(2130903179, null));
    }
    return new ItemViewHolder(LayoutInflater.from(this.mContext).inflate(2130903176, paramViewGroup, false));
  }
  
  public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
  {
    this.mOnItemClickListener = paramOnItemClickListener;
  }
  
  public class HeaderViewHolder
    extends RecyclerView.ViewHolder
  {
    @InjectView(2131559021)
    TextView orderTitle;
    
    public HeaderViewHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
    }
  }
  
  public class ItemViewHolder
    extends RecyclerView.ViewHolder
    implements View.OnClickListener
  {
    @InjectView(2131558955)
    TextView orderDate;
    @InjectView(2131558957)
    TextView orderStatus;
    @InjectView(2131558956)
    TextView orderStockLocation;
    
    public ItemViewHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
      paramView.setOnClickListener(this);
    }
    
    public void onClick(View paramView)
    {
      if (OrderAdapter.this.mOnItemClickListener != null) {
        OrderAdapter.this.mOnItemClickListener.onItemClick(paramView, ((OrderHistory)OrderAdapter.this.mOrderHistory.get(getPosition())).order);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/OrderAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
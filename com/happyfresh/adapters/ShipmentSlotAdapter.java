package com.happyfresh.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.listeners.OnItemClickListener;
import com.happyfresh.models.Order;
import com.happyfresh.models.Shipment;
import com.happyfresh.models.ShippingRate;
import com.happyfresh.models.Slot;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public class ShipmentSlotAdapter
  extends RecyclerView.Adapter<ViewHolder>
  implements StickyRecyclerHeadersAdapter
{
  private static SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
  private static SimpleDateFormat df2 = new SimpleDateFormat("cccc, dd MMM yyyy");
  private static SimpleDateFormat df3 = new SimpleDateFormat("hha");
  private List<Long> mAvailableShippingMethodId;
  private Context mContext;
  private ICartApplication mICartApplication;
  private HashMap<Integer, String> mMapper = new HashMap();
  OnItemClickListener mOnItemClickListener;
  private List<Slot> mSlots = new ArrayList();
  
  public ShipmentSlotAdapter(Context paramContext, List<Long> paramList)
  {
    this.mContext = paramContext;
    this.mICartApplication = ((ICartApplication)paramContext.getApplicationContext());
    this.mAvailableShippingMethodId = paramList;
    paramContext = Calendar.getInstance();
    int i = 0;
    while (i < 7)
    {
      this.mMapper.put(Integer.valueOf(i), df1.format(paramContext.getTime()));
      paramContext.add(6, 1);
      i += 1;
    }
  }
  
  private String buildShippingCost(Long paramLong)
  {
    Object localObject = (ICartApplication)this.mContext.getApplicationContext();
    if (((ICartApplication)localObject).currentOrder == null) {
      return "";
    }
    localObject = ((ICartApplication)localObject).currentOrder.shipments;
    if (((List)localObject).size() > 0)
    {
      localObject = ((Shipment)((List)localObject).get(0)).shippingRates.iterator();
      while (((Iterator)localObject).hasNext())
      {
        ShippingRate localShippingRate = (ShippingRate)((Iterator)localObject).next();
        if (paramLong == localShippingRate.shippingMethodId) {
          return this.mICartApplication.getNumberFormatter().format(localShippingRate.cost);
        }
      }
    }
    return "";
  }
  
  private boolean isShippingMethodAvailable(Slot paramSlot)
  {
    boolean bool2 = false;
    Iterator localIterator = this.mAvailableShippingMethodId.iterator();
    Long localLong;
    do
    {
      bool1 = bool2;
      if (!localIterator.hasNext()) {
        break;
      }
      localLong = (Long)localIterator.next();
    } while (!paramSlot.shippingMethodId.equals(localLong));
    boolean bool1 = true;
    return bool1;
  }
  
  private boolean isSlotAvailable(Slot paramSlot)
  {
    return (paramSlot.available) && (isShippingMethodAvailable(paramSlot));
  }
  
  public void addAll(List<Slot> paramList)
  {
    this.mSlots = paramList;
    notifyDataSetChanged();
  }
  
  public long getHeaderId(int paramInt)
  {
    Slot localSlot = (Slot)this.mSlots.get(paramInt);
    long l2 = 0L;
    paramInt = 0;
    for (;;)
    {
      long l1 = l2;
      if (paramInt < 7)
      {
        if (((String)this.mMapper.get(Integer.valueOf(paramInt))).equals(df1.format(localSlot.startTime))) {
          l1 = paramInt;
        }
      }
      else {
        return l1;
      }
      paramInt += 1;
    }
  }
  
  public int getItemCount()
  {
    return this.mSlots.size();
  }
  
  public void onBindHeaderViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    Slot localSlot = (Slot)this.mSlots.get(paramInt);
    String str = df1.format(localSlot.startTime);
    paramViewHolder = (HeaderViewHolder)paramViewHolder;
    Calendar localCalendar = Calendar.getInstance();
    if (df1.format(localCalendar.getTime()).equals(str))
    {
      paramViewHolder.headerSlotDate.setText(2131165610);
      return;
    }
    localCalendar.add(6, 1);
    if (df1.format(localCalendar.getTime()).equals(str))
    {
      paramViewHolder.headerSlotDate.setText(2131165611);
      return;
    }
    paramViewHolder.headerSlotDate.setText(df2.format(localSlot.startTime));
  }
  
  public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt)
  {
    Slot localSlot = (Slot)this.mSlots.get(paramInt);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeZone(TimeZone.getDefault());
    localCalendar.setTime(localSlot.startTime);
    StringBuilder localStringBuilder = new StringBuilder(df3.format(localCalendar.getTime()));
    localStringBuilder.append(" - ");
    localCalendar.setTime(localSlot.endTime);
    localStringBuilder.append(df3.format(localCalendar.getTime()));
    if (isSlotAvailable(localSlot))
    {
      paramViewHolder.slotTime.setTextColor(-16777216);
      paramViewHolder.slotTime.setText(localStringBuilder.toString());
      paramViewHolder.slotTime.setPaintFlags(paramViewHolder.slotTime.getPaintFlags() & 0xFFFFFFEF);
    }
    for (;;)
    {
      paramInt = Color.parseColor("#BBBBBB");
      paramViewHolder.slotCost.setTextColor(paramInt);
      paramViewHolder.slotCost.setText(buildShippingCost(localSlot.shippingMethodId));
      return;
      paramViewHolder.slotTime.setTextColor(Color.parseColor("#BBBBBB"));
      localStringBuilder.append(" ");
      localStringBuilder.append(this.mContext.getString(2131165398));
      paramViewHolder.slotTime.setText(localStringBuilder.toString());
      paramViewHolder.slotTime.setPaintFlags(16);
    }
  }
  
  public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup paramViewGroup)
  {
    return new HeaderViewHolder(LayoutInflater.from(this.mContext).inflate(2130903161, paramViewGroup, false));
  }
  
  public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    return new ViewHolder(LayoutInflater.from(this.mContext).inflate(2130903184, paramViewGroup, false));
  }
  
  public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
  {
    this.mOnItemClickListener = paramOnItemClickListener;
  }
  
  public class HeaderViewHolder
    extends RecyclerView.ViewHolder
  {
    @InjectView(2131558913)
    TextView headerSlotDate;
    
    public HeaderViewHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
    }
  }
  
  public class ViewHolder
    extends RecyclerView.ViewHolder
    implements View.OnClickListener
  {
    @InjectView(2131559049)
    TextView slotCost;
    @InjectView(2131559048)
    TextView slotTime;
    
    public ViewHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
      paramView.setOnClickListener(this);
    }
    
    public void onClick(View paramView)
    {
      if (ShipmentSlotAdapter.this.mOnItemClickListener != null)
      {
        Slot localSlot = (Slot)ShipmentSlotAdapter.this.mSlots.get(getPosition());
        if (ShipmentSlotAdapter.this.isSlotAvailable(localSlot)) {
          ShipmentSlotAdapter.this.mOnItemClickListener.onItemClick(paramView, localSlot);
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/ShipmentSlotAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
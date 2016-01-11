package com.happyfresh.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.adapters.ShipmentSlotAdapter;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.listeners.OnItemClickListener;
import com.happyfresh.models.Order;
import com.happyfresh.models.Shipment;
import com.happyfresh.models.ShippingRate;
import com.happyfresh.models.Slot;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SelectShipmentTimeFragment
  extends BaseDialogFragment
{
  private static final String TAG = SelectShipmentTimeFragment.class.getSimpleName();
  Listener mAvailableSlotsReceivedListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject1, ICartNotification.Type paramAnonymousType, Object paramAnonymousObject2)
    {
      SelectShipmentTimeFragment.this.mSlots = ((List)paramAnonymousObject2);
      SelectShipmentTimeFragment.this.addSlots(SelectShipmentTimeFragment.this.mSlots);
    }
  };
  private ICartApplication mICartApplication;
  private OnItemClickListener mOnItemClickListener;
  private LinearLayoutManager mSelectShipmentLinearLayoutManager;
  @InjectView(2131558715)
  RecyclerView mSelectShipmentList;
  private ShipmentSlotAdapter mShipmentSlotAdapter;
  List<Slot> mSlots = new ArrayList();
  
  public void addSlots(List<Slot> paramList)
  {
    this.mShipmentSlotAdapter.addAll(paramList);
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mSlots = getArguments().getParcelableArrayList("ICartConstant.KEYS.EXTRAS.SLOTS");
    paramBundle = new ArrayList();
    Object localObject = this.mICartApplication.currentOrder;
    if ((localObject != null) && (((Order)localObject).shipments.size() > 0))
    {
      localObject = ((Shipment)((Order)localObject).shipments.get(0)).shippingRates.iterator();
      while (((Iterator)localObject).hasNext()) {
        paramBundle.add(((ShippingRate)((Iterator)localObject).next()).shippingMethodId);
      }
    }
    this.mSelectShipmentLinearLayoutManager = new LinearLayoutManager(getActivity());
    this.mSelectShipmentList.setLayoutManager(this.mSelectShipmentLinearLayoutManager);
    this.mShipmentSlotAdapter = new ShipmentSlotAdapter(getActivity(), paramBundle);
    this.mSelectShipmentList.setAdapter(this.mShipmentSlotAdapter);
    this.mSelectShipmentList.addItemDecoration(new StickyRecyclerHeadersDecoration(this.mShipmentSlotAdapter));
    addSlots(this.mSlots);
    this.mShipmentSlotAdapter.setOnItemClickListener(new OnItemClickListener()
    {
      public void onItemClick(View paramAnonymousView, Object paramAnonymousObject)
      {
        if (SelectShipmentTimeFragment.this.mOnItemClickListener != null) {
          SelectShipmentTimeFragment.this.mOnItemClickListener.onItemClick(paramAnonymousView, paramAnonymousObject);
        }
      }
    });
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mICartApplication = ICartApplication.get(this);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903149, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    getDialog().getWindow().requestFeature(1);
    int i = getResources().getDimensionPixelSize(2131296417);
    int j = getResources().getDimensionPixelSize(2131296416);
    getDialog().getWindow().setLayout(i, j);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public void onDismiss(DialogInterface paramDialogInterface)
  {
    super.onDismiss(paramDialogInterface);
    sendTracker("Delivery");
  }
  
  public void onPause()
  {
    super.onPause();
    this.mICartApplication.getICartNotification().removeAvailableSlotReceivedListener(this.mAvailableSlotsReceivedListener);
  }
  
  public void onResume()
  {
    super.onResume();
    this.mICartApplication.getICartNotification().addAvailableSlotReceivedListener(this.mAvailableSlotsReceivedListener);
  }
  
  public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
  {
    this.mOnItemClickListener = paramOnItemClickListener;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/SelectShipmentTimeFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
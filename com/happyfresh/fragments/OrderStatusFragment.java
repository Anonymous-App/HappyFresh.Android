package com.happyfresh.fragments;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;
import com.happyfresh.activities.CancelOrderConfirmationActivity;
import com.happyfresh.adapters.OrderDetailAdapter;
import com.happyfresh.adapters.OrderItemAdapter;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartDialogCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartRestError;
import com.happyfresh.listeners.OnAddToCartClickListener;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.models.Address;
import com.happyfresh.models.Assignment;
import com.happyfresh.models.FailedItem;
import com.happyfresh.models.ItemPrice;
import com.happyfresh.models.ItemState;
import com.happyfresh.models.Job;
import com.happyfresh.models.JobState;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.ManifestItem;
import com.happyfresh.models.MultipleLineItem;
import com.happyfresh.models.MultipleLineItemResponse;
import com.happyfresh.models.Order;
import com.happyfresh.models.Replacement;
import com.happyfresh.models.Shipment;
import com.happyfresh.models.Slot;
import com.happyfresh.models.User;
import com.happyfresh.models.Variant;
import com.happyfresh.utils.AccengageTrackerUtils;
import com.happyfresh.utils.AdjustUtils;
import com.happyfresh.utils.DialogUtils;
import com.happyfresh.utils.GAUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

public class OrderStatusFragment
  extends BaseFragment
{
  private static final String TAG = OrderStatusFragment.class.getSimpleName();
  @InjectView(2131558811)
  TextView addAllItemToCart;
  @InjectView(2131558812)
  TextView addAllItemToCartDisable;
  @InjectView(2131558815)
  TextView addAllReplacementsToCart;
  @InjectView(2131558816)
  TextView addAllReplacementsToCartDisable;
  @InjectView(2131558807)
  TextView deliveryInfoAddress;
  @InjectView(2131558809)
  TextView deliveryInfoInstruction;
  @InjectView(2131558808)
  View deliveryInfoInstructionView;
  @InjectView(2131558806)
  TextView deliveryInfoName;
  @InjectView(2131558804)
  TextView deliveryInfoStockLocation;
  @InjectView(2131558800)
  Button editOrderButton;
  @InjectView(2131558799)
  View editOrderContainer;
  @InjectView(2131558813)
  ListView itemListView;
  @InjectView(2131558817)
  ListView itemReplacementListView;
  @InjectView(2131558818)
  View itemTotalContainer;
  @InjectView(2131558819)
  ListView itemTotalListView;
  @InjectView(2131558814)
  View itemsReplacementView;
  @InjectView(2131558810)
  View itemsView;
  private OrderStatusAdapter mAdapter;
  private String mContactStr = null;
  private Context mContext;
  private OrderItemAdapter mItemAdapter;
  private OrderItemAdapter mItemReplacementAdapter;
  private Order mOrder;
  private OrderDetailAdapter mOrderDetailAdapter;
  private String mPhoneNumber = null;
  private ProgressDialog mProgressDialog;
  private List<OrderStatusInfo> mStatuses = new ArrayList();
  private Map<Long, LineItem> mapItem = new HashMap();
  @InjectView(2131558796)
  TextView orderDetailDate;
  @InjectView(2131558797)
  TextView orderDetailText;
  @InjectView(2131558802)
  TextView orderNo;
  @InjectView(2131558798)
  ListView statusListView;
  
  private void addMultipleItemToCart(final TextView paramTextView1, final TextView paramTextView2)
  {
    showProgressDialog(this.mContext.getString(2131165274));
    final ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mOrder.lineItems.iterator();
    while (localIterator.hasNext())
    {
      LineItem localLineItem = (LineItem)localIterator.next();
      MultipleLineItem localMultipleLineItem = new MultipleLineItem();
      localMultipleLineItem.variantId = localLineItem.variantId.longValue();
      localMultipleLineItem.quantity = localLineItem.quantity.intValue();
      localArrayList.add(localMultipleLineItem);
    }
    try
    {
      getApplication().getOrderManager().addMultipleItemToCart(localArrayList, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          Toast.makeText(OrderStatusFragment.this.getActivity(), OrderStatusFragment.this.getActivity().getString(2131165422), 0).show();
          OrderStatusFragment.this.dismissProgressDialog();
        }
        
        public void onSuccess(MultipleLineItemResponse paramAnonymousMultipleLineItemResponse)
        {
          Object localObject1;
          Object localObject2;
          if ((paramAnonymousMultipleLineItemResponse != null) && (paramAnonymousMultipleLineItemResponse.order != null))
          {
            int i = 0;
            localObject1 = paramAnonymousMultipleLineItemResponse.order.lineItems.iterator();
            Object localObject3;
            for (;;)
            {
              if (!((Iterator)localObject1).hasNext()) {
                break label111;
              }
              localObject2 = (LineItem)((Iterator)localObject1).next();
              localObject3 = localArrayList.iterator();
              if (((Iterator)localObject3).hasNext())
              {
                if (((MultipleLineItem)((Iterator)localObject3).next()).variantId != ((LineItem)localObject2).variantId.longValue()) {
                  break;
                }
                OrderStatusFragment.this.trackAddToCart((LineItem)localObject2);
                i += 1;
              }
            }
            label111:
            localObject1 = paramAnonymousMultipleLineItemResponse.failedItems;
            localObject2 = ((List)localObject1).iterator();
            while (((Iterator)localObject2).hasNext())
            {
              localObject3 = (FailedItem)((Iterator)localObject2).next();
              localObject3 = (LineItem)OrderStatusFragment.this.mapItem.get(Long.valueOf(((FailedItem)localObject3).variantId));
              ((LineItem)localObject3).setOutOfStock();
              OrderStatusFragment.this.getApplication().outOfStockItems.add(localObject3);
            }
            OrderStatusFragment.this.getApplication().currentOrder = paramAnonymousMultipleLineItemResponse.order;
            OrderStatusFragment.this.getApplication().getICartNotification().raiseCurrentOrderSetEvent(this, OrderStatusFragment.this.getApplication().currentOrder);
            if (OrderStatusFragment.this.mItemAdapter != null) {
              OrderStatusFragment.this.mItemAdapter.notifyDataSetChanged();
            }
            if (OrderStatusFragment.this.mItemReplacementAdapter != null) {
              OrderStatusFragment.this.mItemReplacementAdapter.notifyDataSetChanged();
            }
            int j = localArrayList.size();
            paramAnonymousMultipleLineItemResponse = "";
            if (i <= 0) {
              break label461;
            }
            paramAnonymousMultipleLineItemResponse = OrderStatusFragment.this.mContext.getString(2131165597, new Object[] { Integer.valueOf(i), Integer.valueOf(j) });
          }
          for (;;)
          {
            localObject1 = OrderStatusFragment.this.mContext.getResources().getString(2131165410);
            localObject2 = OrderStatusFragment.this.mContext.getResources().getString(2131165478);
            DialogUtils.showDialog(OrderStatusFragment.this.getActivity(), (String)localObject1, paramAnonymousMultipleLineItemResponse, (String)localObject2, null, null, null);
            OrderStatusFragment.this.getApplication().addOrderIdAddedToCartList(Long.valueOf(OrderStatusFragment.this.mOrder.remoteId));
            paramTextView1.setVisibility(8);
            paramTextView2.setVisibility(0);
            OrderStatusFragment.this.dismissProgressDialog();
            paramAnonymousMultipleLineItemResponse = OrderStatusFragment.this.getApplication().currentOrder.number;
            MixpanelTrackerUtils.trackAddAllItemFromOrderHistory(OrderStatusFragment.this.getApplication(), paramAnonymousMultipleLineItemResponse);
            AccengageTrackerUtils.trackAddAllItemFromOrderHistory(OrderStatusFragment.this.getApplication(), paramAnonymousMultipleLineItemResponse);
            return;
            label461:
            if (((List)localObject1).size() > 0) {
              paramAnonymousMultipleLineItemResponse = OrderStatusFragment.this.mContext.getString(2131165278);
            }
          }
        }
      });
      return;
    }
    catch (JSONException paramTextView1)
    {
      Log.e(TAG, paramTextView1.getLocalizedMessage());
      return;
    }
    catch (UnsupportedEncodingException paramTextView1)
    {
      Log.e(TAG, paramTextView1.getLocalizedMessage());
    }
  }
  
  private void callOperator(String paramString1, final String paramString2)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
    localBuilder.setMessage(paramString1);
    localBuilder.setNegativeButton(2131165293, null);
    localBuilder.setPositiveButton(2131165292, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        OrderStatusFragment.this.callPhone(paramString2);
      }
    });
    localBuilder.show();
  }
  
  private void dismissProgressDialog()
  {
    if ((this.mProgressDialog != null) && (this.mProgressDialog.isShowing())) {
      this.mProgressDialog.dismiss();
    }
  }
  
  private void displayItemList()
  {
    Object localObject2 = new ArrayList();
    ArrayList localArrayList = new ArrayList();
    Object localObject1 = new ArrayList();
    if (this.mOrder.shipments.size() > 0) {
      localObject1 = ((Shipment)this.mOrder.shipments.get(0)).manifest;
    }
    Iterator localIterator1 = this.mOrder.lineItems.iterator();
    while (localIterator1.hasNext())
    {
      LineItem localLineItem = (LineItem)localIterator1.next();
      this.mapItem.put(localLineItem.variantId, localLineItem);
      if (!localLineItem.hasReplacement())
      {
        ((List)localObject2).add(localLineItem);
      }
      else
      {
        boolean bool1 = this.mOrder.isActive();
        Iterator localIterator2 = localLineItem.getReplacements().iterator();
        boolean bool2;
        do
        {
          bool2 = bool1;
          if (!localIterator2.hasNext()) {
            break;
          }
          Replacement localReplacement = (Replacement)localIterator2.next();
          Iterator localIterator3 = ((List)localObject1).iterator();
          ManifestItem localManifestItem;
          do
          {
            bool2 = bool1;
            if (!localIterator3.hasNext()) {
              break;
            }
            localManifestItem = (ManifestItem)localIterator3.next();
          } while (localReplacement.variant.remoteId != localManifestItem.variantId.longValue());
          if (localManifestItem.states.delivered <= 0)
          {
            bool2 = bool1;
            if (localManifestItem.states.rejected <= 0) {}
          }
          else
          {
            bool2 = true;
          }
          bool1 = bool2;
        } while (!bool2);
        if (bool2) {
          localArrayList.add(localLineItem);
        } else {
          ((List)localObject2).add(localLineItem);
        }
      }
    }
    if (!((List)localObject2).isEmpty())
    {
      this.itemsView.setVisibility(0);
      this.mItemAdapter = new OrderItemAdapter(getActivity(), (List)localObject2, this.mOrder, false, (List)localObject1);
      this.itemListView.setAdapter(this.mItemAdapter);
      this.mItemAdapter.setOnAddToCartClickListener(new OnAddToCartClickListener()
      {
        public void onClick(View paramAnonymousView, LineItem paramAnonymousLineItem)
        {
          OrderStatusFragment.this.addSelectedItemToCart(paramAnonymousLineItem);
        }
        
        public void onReplacementClick(View paramAnonymousView, LineItem paramAnonymousLineItem)
        {
          OrderStatusFragment.this.addSelectedItemToCart(paramAnonymousLineItem);
        }
        
        public void onShopperReplacementClick(View paramAnonymousView, LineItem paramAnonymousLineItem)
        {
          OrderStatusFragment.this.addSelectedItemToCart(paramAnonymousLineItem);
        }
      });
      if (!this.mOrder.isActive())
      {
        this.addAllItemToCart.setVisibility(0);
        if (getApplication().hasOrderIdBeenAddedToCart(Long.valueOf(this.mOrder.remoteId)))
        {
          this.addAllItemToCart.setVisibility(8);
          this.addAllItemToCartDisable.setVisibility(0);
        }
      }
    }
    for (;;)
    {
      if (!localArrayList.isEmpty())
      {
        this.itemsReplacementView.setVisibility(0);
        this.mItemReplacementAdapter = new OrderItemAdapter(getActivity(), localArrayList, this.mOrder, true, (List)localObject1);
        this.itemReplacementListView.setAdapter(this.mItemReplacementAdapter);
        this.mItemReplacementAdapter.setOnAddToCartClickListener(new OnAddToCartClickListener()
        {
          public void onClick(View paramAnonymousView, LineItem paramAnonymousLineItem)
          {
            OrderStatusFragment.this.addSelectedItemToCart(paramAnonymousLineItem);
          }
          
          public void onReplacementClick(View paramAnonymousView, LineItem paramAnonymousLineItem)
          {
            OrderStatusFragment.this.addSelectedItemToCart(paramAnonymousLineItem);
          }
          
          public void onShopperReplacementClick(View paramAnonymousView, LineItem paramAnonymousLineItem)
          {
            OrderStatusFragment.this.addSelectedItemToCart(paramAnonymousLineItem);
          }
        });
      }
      if (!this.mOrder.isActive())
      {
        localObject1 = new ArrayList();
        localObject2 = new ItemPrice();
        ((ItemPrice)localObject2).item = getString(2131165593);
        ((ItemPrice)localObject2).price = this.mOrder.itemTotal.doubleValue();
        ((List)localObject1).add(localObject2);
        localObject2 = new ItemPrice();
        ((ItemPrice)localObject2).item = getString(2131165353);
        ((ItemPrice)localObject2).price = this.mOrder.getDeliveryFee();
        ((List)localObject1).add(localObject2);
        double d1 = this.mOrder.getTotalDiscount();
        if (d1 != 0.0D)
        {
          localObject2 = new ItemPrice();
          ((ItemPrice)localObject2).item = getString(2131165364);
          ((ItemPrice)localObject2).price = d1;
          ((List)localObject1).add(localObject2);
        }
        d1 = this.mOrder.getTotalOutOfStock();
        double d2 = this.mOrder.getTotalReplacement();
        double d3 = this.mOrder.getTotalRejected();
        if ((d1 != 0.0D) || (d2 != 0.0D) || (d3 != 0.0D))
        {
          localObject2 = new ItemPrice();
          ((ItemPrice)localObject2).item = getString(2131165277);
          ((ItemPrice)localObject2).price = (d1 + d2 + d3);
          ((List)localObject1).add(localObject2);
          if (d1 != 0.0D)
          {
            localObject2 = new ItemPrice();
            ((ItemPrice)localObject2).item = getString(2131165497);
            ((ItemPrice)localObject2).price = d1;
            ((ItemPrice)localObject2).subItem = true;
            ((List)localObject1).add(localObject2);
          }
          if (d2 != 0.0D)
          {
            localObject2 = new ItemPrice();
            ((ItemPrice)localObject2).item = getString(2131165546);
            ((ItemPrice)localObject2).price = d2;
            ((ItemPrice)localObject2).subItem = true;
            ((List)localObject1).add(localObject2);
          }
          if (d3 != 0.0D)
          {
            localObject2 = new ItemPrice();
            ((ItemPrice)localObject2).item = getString(2131165523);
            ((ItemPrice)localObject2).price = d3;
            ((ItemPrice)localObject2).subItem = true;
            ((List)localObject1).add(localObject2);
          }
        }
        localObject2 = new ItemPrice();
        ((ItemPrice)localObject2).item = getString(2131165616);
        ((ItemPrice)localObject2).price = this.mOrder.total.doubleValue();
        ((List)localObject1).add(localObject2);
        this.mOrderDetailAdapter = new OrderDetailAdapter(getActivity(), (List)localObject1, this.mOrder);
        this.itemTotalListView.setAdapter(this.mOrderDetailAdapter);
        this.itemTotalContainer.setVisibility(0);
      }
      return;
      if (!this.mOrder.isActive())
      {
        this.addAllReplacementsToCart.setVisibility(0);
        if (getApplication().hasOrderIdBeenAddedToCart(Long.valueOf(this.mOrder.remoteId)))
        {
          this.addAllReplacementsToCart.setVisibility(8);
          this.addAllReplacementsToCartDisable.setVisibility(0);
        }
      }
    }
  }
  
  public static OrderStatusFragment newInstance(String paramString)
  {
    OrderStatusFragment localOrderStatusFragment = new OrderStatusFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("ICartConstant.KEYS.ORDER_NUMBER", paramString);
    localOrderStatusFragment.setArguments(localBundle);
    return localOrderStatusFragment;
  }
  
  private void showAddAllConfirmationDialog(final TextView paramTextView1, final TextView paramTextView2)
  {
    String str1 = this.mContext.getResources().getString(2131165266);
    String str2 = this.mContext.getResources().getString(2131165267);
    String str3 = this.mContext.getResources().getString(2131165478);
    String str4 = this.mContext.getResources().getString(2131165293);
    DialogUtils.showDialog(getActivity(), str1, str2, str3, str4, null, new ICartDialogCallback()
    {
      public void onCancel() {}
      
      public void onNegative(MaterialDialog paramAnonymousMaterialDialog) {}
      
      public void onNeutral(MaterialDialog paramAnonymousMaterialDialog) {}
      
      public void onPositive(MaterialDialog paramAnonymousMaterialDialog)
      {
        OrderStatusFragment.this.addMultipleItemToCart(paramTextView1, paramTextView2);
      }
    });
  }
  
  private void showProgressDialog(String paramString)
  {
    if (this.mProgressDialog == null) {
      this.mProgressDialog = new ProgressDialog(getActivity());
    }
    this.mProgressDialog.setMessage(paramString);
    this.mProgressDialog.show();
  }
  
  private void syncOrder(String paramString)
  {
    getApplication().getOrderManager().getOrder(paramString, new ICartCallback(TAG)
    {
      public void onSuccess(Object paramAnonymousObject)
      {
        OrderStatusFragment.access$002(OrderStatusFragment.this, (Order)paramAnonymousObject);
        if (OrderStatusFragment.this.getActivity() == null) {
          return;
        }
        OrderStatusFragment.this.updateList();
      }
    });
  }
  
  private void trackAddToCart(LineItem paramLineItem)
  {
    GAUtils.trackAddToCart(getApplication(), paramLineItem, getScreenName());
    AdjustUtils.trackAddToCart(paramLineItem);
  }
  
  private void updateList()
  {
    if (this.mOrder != null)
    {
      this.mStatuses = new ArrayList();
      Object localObject2;
      if (!this.mOrder.shipments.isEmpty())
      {
        localObject2 = ((Shipment)this.mOrder.shipments.get(0)).slot;
        localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = ((Shipment)this.mOrder.shipments.get(0)).canceledSlot;
        }
        if (localObject1 != null) {
          this.orderDetailDate.setText(com.happyfresh.utils.DateUtils.showUtcInLocal(((Slot)localObject1).startTime, ((Slot)localObject1).endTime));
        }
        this.deliveryInfoStockLocation.setText(((Shipment)this.mOrder.shipments.get(0)).stockLocationName);
      }
      this.orderNo.setText("" + this.mOrder.number);
      if (!this.mOrder.isActive())
      {
        this.orderDetailText.setVisibility(0);
        this.orderDetailText.setText(this.mOrder.getStatus(getActivity()));
        this.editOrderContainer.setVisibility(8);
      }
      Object localObject3;
      for (;;)
      {
        if (this.mOrder.shipAddress != null)
        {
          this.deliveryInfoName.setText(this.mOrder.shipAddress.fullName);
          this.deliveryInfoAddress.setText(this.mOrder.shipAddress.toStringTwoLines(this.mICartApplication));
          if (!TextUtils.isEmpty(this.mOrder.shipAddress.deliveryInstruction))
          {
            this.deliveryInfoInstructionView.setVisibility(0);
            this.deliveryInfoInstruction.setText(this.mOrder.shipAddress.deliveryInstruction);
          }
        }
        localObject1 = new OrderStatusInfo();
        ((OrderStatusInfo)localObject1).description = getString(2131165492);
        ((OrderStatusInfo)localObject1).time = this.mOrder.completedAt;
        this.mStatuses.add(localObject1);
        localObject1 = this.mOrder.shipments.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (Shipment)((Iterator)localObject1).next();
          if (((Shipment)localObject2).shoppingJob != null)
          {
            localObject3 = new OrderStatusInfo();
            ((OrderStatusInfo)localObject3).description = getString(2131165481);
            ((OrderStatusInfo)localObject3).time = ((Shipment)localObject2).shoppingJob.startTime;
            this.mStatuses.add(localObject3);
            this.mContactStr = getString(2131165325);
            if ((((Shipment)localObject2).shoppingJob.assignment != null) && (((Shipment)localObject2).shoppingJob.assignment.user != null))
            {
              localObject2 = ((Shipment)localObject2).shoppingJob.assignment.user.getPrimaryShippingAddress();
              if (localObject2 != null) {
                this.mPhoneNumber = ((Address)localObject2).phone;
              }
            }
          }
        }
        localObject1 = this.mOrder.getStatus(getActivity());
        if (getResources().getString(2131165492).equalsIgnoreCase((String)localObject1)) {
          this.editOrderButton.setEnabled(true);
        } else {
          this.editOrderButton.setEnabled(false);
        }
      }
      Object localObject1 = this.mOrder.shipments.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Shipment)((Iterator)localObject1).next();
        if (((Shipment)localObject2).deliveryJob != null)
        {
          this.mContactStr = getString(2131165324);
          if (((Shipment)localObject2).deliveryJob.assignment != null)
          {
            localObject3 = ((Shipment)localObject2).deliveryJob.assignment.user;
            if (localObject3 != null)
            {
              localObject3 = ((User)localObject3).getPrimaryShippingAddress();
              if (localObject3 != null) {
                this.mPhoneNumber = ((Address)localObject3).phone;
              }
            }
          }
          localObject3 = ((Shipment)localObject2).deliveryJob;
          Object localObject4 = ((Job)localObject3).getState("accepted");
          if (localObject4 != null)
          {
            OrderStatusInfo localOrderStatusInfo = new OrderStatusInfo();
            localOrderStatusInfo.description = getString(2131165489);
            localOrderStatusInfo.time = ((JobState)localObject4).startTime;
            this.mStatuses.add(localOrderStatusInfo);
          }
          localObject3 = ((Job)localObject3).getState("found_address");
          if (localObject3 != null)
          {
            localObject4 = new OrderStatusInfo();
            ((OrderStatusInfo)localObject4).description = getString(2131165480);
            ((OrderStatusInfo)localObject4).time = ((JobState)localObject3).startTime;
            this.mStatuses.add(localObject4);
          }
          if (((Shipment)localObject2).deliveryJob.isFinished())
          {
            localObject3 = new OrderStatusInfo();
            ((OrderStatusInfo)localObject3).description = getString(2131165487);
            ((OrderStatusInfo)localObject3).time = ((Shipment)localObject2).deliveryJob.endTime;
            this.mStatuses.add(localObject3);
          }
        }
      }
      if (this.mOrder.isActive())
      {
        this.statusListView.setVisibility(0);
        Collections.reverse(this.mStatuses);
        this.mAdapter = new OrderStatusAdapter(getActivity(), this.mStatuses);
        this.statusListView.setAdapter(this.mAdapter);
      }
      displayItemList();
    }
  }
  
  @OnClick({2131558811})
  void addItemsToCart()
  {
    showAddAllConfirmationDialog(this.addAllItemToCart, this.addAllItemToCartDisable);
  }
  
  @OnClick({2131558815})
  void addReplacementsToCart()
  {
    showAddAllConfirmationDialog(this.addAllReplacementsToCart, this.addAllReplacementsToCartDisable);
  }
  
  public void addSelectedItemToCart(LineItem paramLineItem)
  {
    showProgressDialog(this.mContext.getString(2131165275));
    final ArrayList localArrayList = new ArrayList();
    MultipleLineItem localMultipleLineItem = new MultipleLineItem();
    localMultipleLineItem.variantId = paramLineItem.variantId.longValue();
    localMultipleLineItem.quantity = paramLineItem.quantity.intValue();
    localArrayList.add(localMultipleLineItem);
    try
    {
      this.mICartApplication.getOrderManager().addMultipleItemToCart(localArrayList, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          ICartRestError.showMessage(OrderStatusFragment.this.getActivity(), paramAnonymousThrowable);
          OrderStatusFragment.this.dismissProgressDialog();
        }
        
        public void onSuccess(MultipleLineItemResponse paramAnonymousMultipleLineItemResponse)
        {
          Object localObject1;
          if ((paramAnonymousMultipleLineItemResponse != null) && (paramAnonymousMultipleLineItemResponse.order != null))
          {
            int i = 0;
            List localList = paramAnonymousMultipleLineItemResponse.order.lineItems;
            localObject1 = localList.iterator();
            Object localObject3;
            for (;;)
            {
              if (!((Iterator)localObject1).hasNext()) {
                break label115;
              }
              localObject2 = (LineItem)((Iterator)localObject1).next();
              localObject3 = localArrayList.iterator();
              if (((Iterator)localObject3).hasNext())
              {
                if (((MultipleLineItem)((Iterator)localObject3).next()).variantId != ((LineItem)localObject2).variantId.longValue()) {
                  break;
                }
                OrderStatusFragment.this.trackAddToCart((LineItem)localObject2);
                i += 1;
              }
            }
            label115:
            localObject1 = paramAnonymousMultipleLineItemResponse.failedItems;
            Object localObject2 = ((List)localObject1).iterator();
            while (((Iterator)localObject2).hasNext())
            {
              localObject3 = (FailedItem)((Iterator)localObject2).next();
              localObject3 = (LineItem)OrderStatusFragment.this.mapItem.get(Long.valueOf(((FailedItem)localObject3).variantId));
              if (localObject3 != null)
              {
                ((LineItem)localObject3).setOutOfStock();
                OrderStatusFragment.this.mICartApplication.outOfStockItems.add(localObject3);
              }
            }
            OrderStatusFragment.this.mICartApplication.currentOrder = paramAnonymousMultipleLineItemResponse.order;
            OrderStatusFragment.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, OrderStatusFragment.this.mICartApplication.currentOrder);
            if (OrderStatusFragment.this.mItemAdapter != null) {
              OrderStatusFragment.this.mItemAdapter.notifyDataSetChanged();
            }
            if (OrderStatusFragment.this.mItemReplacementAdapter != null) {
              OrderStatusFragment.this.mItemReplacementAdapter.notifyDataSetChanged();
            }
            int j = localArrayList.size();
            paramAnonymousMultipleLineItemResponse = "";
            if (i <= 0) {
              break label506;
            }
            paramAnonymousMultipleLineItemResponse = OrderStatusFragment.this.mContext.getString(2131165597, new Object[] { Integer.valueOf(i), Integer.valueOf(j) });
            localObject1 = OrderStatusFragment.this.mContext.getResources().getString(2131165410);
            localObject2 = OrderStatusFragment.this.mContext.getResources().getString(2131165478);
            DialogUtils.showDialog(OrderStatusFragment.this.getActivity(), (String)localObject1, paramAnonymousMultipleLineItemResponse, (String)localObject2, null, null, null);
            if (OrderStatusFragment.this.mOrder != null) {
              OrderStatusFragment.this.getApplication().addOrderIdAddedToCartList(Long.valueOf(OrderStatusFragment.this.mOrder.remoteId));
            }
            OrderStatusFragment.this.dismissProgressDialog();
            if (localList == null) {
              break label537;
            }
            if (localList.size() <= 0) {
              break label532;
            }
            paramAnonymousMultipleLineItemResponse = (LineItem)localList.get(0);
          }
          for (;;)
          {
            if (paramAnonymousMultipleLineItemResponse != null)
            {
              MixpanelTrackerUtils.trackAddToCartFromOrderHistory(OrderStatusFragment.this.mICartApplication, paramAnonymousMultipleLineItemResponse.variant.sku, paramAnonymousMultipleLineItemResponse.variant.name);
              AccengageTrackerUtils.trackAddToCartFromOrderHistory(OrderStatusFragment.this.mICartApplication, paramAnonymousMultipleLineItemResponse.variant.sku, paramAnonymousMultipleLineItemResponse.variant.name);
            }
            return;
            label506:
            if (((List)localObject1).size() <= 0) {
              break;
            }
            paramAnonymousMultipleLineItemResponse = OrderStatusFragment.this.mContext.getString(2131165278);
            break;
            label532:
            paramAnonymousMultipleLineItemResponse = null;
            continue;
            label537:
            paramAnonymousMultipleLineItemResponse = null;
          }
        }
      });
      return;
    }
    catch (JSONException paramLineItem)
    {
      Log.e(TAG, paramLineItem.getLocalizedMessage());
      return;
    }
    catch (UnsupportedEncodingException paramLineItem)
    {
      Log.e(TAG, paramLineItem.getLocalizedMessage());
    }
  }
  
  public void addToLineItems(LineItem paramLineItem)
  {
    int i = 0;
    LineItem localLineItem = getApplication().currentOrder.getLineItem(paramLineItem.variantId.longValue());
    if (localLineItem != null)
    {
      localLineItem.quantity = paramLineItem.quantity;
      i = 1;
    }
    if (i == 0) {
      getApplication().currentOrder.lineItems.add(paramLineItem);
    }
  }
  
  void callPhone(String paramString)
  {
    if (paramString == null) {
      return;
    }
    paramString = "tel:" + paramString;
    Intent localIntent = new Intent("android.intent.action.CALL");
    localIntent.setData(Uri.parse(paramString));
    startActivity(localIntent);
  }
  
  @OnClick({2131558800})
  void editOrder()
  {
    GAUtils.trackEditOrder(this.mContext);
    this.mICartApplication.setTemporaryActivity(getActivity());
    this.mICartApplication.setTemporaryLineItems(this.mOrder.lineItems);
    Intent localIntent = new Intent(getActivity(), CancelOrderConfirmationActivity.class);
    localIntent.putExtra("ICartConstant.KEYS.ORDER_NUMBER", getArguments().getString("ICartConstant.KEYS.ORDER_NUMBER"));
    startActivity(localIntent);
  }
  
  protected String getScreenName()
  {
    if (this.mOrder != null)
    {
      if (this.mOrder.isActive()) {
        return "Active Orders Details";
      }
      return "Past Orders Details";
    }
    return null;
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mContext = paramActivity.getApplicationContext();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getArguments().getString("ICartConstant.KEYS.ORDER_NUMBER");
    Object localObject = getApplication().getOrderList();
    if (!((List)localObject).isEmpty())
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        Order localOrder = (Order)((Iterator)localObject).next();
        if (localOrder.number.equals(paramBundle)) {
          this.mOrder = localOrder;
        }
      }
    }
    if (this.mOrder == null) {
      if ((getApplication().currentOrder != null) && (getApplication().currentOrder.number.equals(paramBundle))) {
        this.mOrder = getApplication().currentOrder;
      }
    }
    for (;;)
    {
      if ((this.mOrder != null) && (!this.mOrder.isActive()))
      {
        MixpanelTrackerUtils.trackViewOrderHistory(getApplication());
        AccengageTrackerUtils.trackViewOrderHistory(getApplication());
      }
      return;
      syncOrder(paramBundle);
      continue;
      if (this.mOrder.isMustSync) {
        syncOrder(paramBundle);
      }
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903141, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    ButterKnife.reset(this);
  }
  
  public void onResume()
  {
    super.onResume();
    if (getApplication().isOrderStatusNeedToBeSynced())
    {
      syncOrder(getArguments().getString("ICartConstant.KEYS.ORDER_NUMBER"));
      getApplication().setOrderStatusNeedToBeSynced(false);
      return;
    }
    updateList();
  }
  
  static class OrderStatusAdapter
    extends ArrayAdapter<OrderStatusFragment.OrderStatusInfo>
  {
    Context mContext;
    
    public OrderStatusAdapter(Context paramContext, List<OrderStatusFragment.OrderStatusInfo> paramList)
    {
      super(0, paramList);
      this.mContext = paramContext;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
      {
        paramView = LayoutInflater.from(this.mContext).inflate(2130903178, null);
        paramViewGroup = new ViewHolder(paramView);
        paramView.setTag(paramViewGroup);
        Object localObject = (OrderStatusFragment.OrderStatusInfo)getItem(paramInt);
        paramViewGroup.statusInfo.setText(((OrderStatusFragment.OrderStatusInfo)localObject).description);
        if ((!this.mContext.getString(2131165492).equalsIgnoreCase(((OrderStatusFragment.OrderStatusInfo)localObject).description)) || (paramInt <= 0)) {
          break label223;
        }
        paramViewGroup.statusLayout.setAlpha(0.3F);
        paramViewGroup.statusLayout.setBackgroundColor(this.mContext.getResources().getColor(2131492921));
        label106:
        if (((OrderStatusFragment.OrderStatusInfo)localObject).time == null) {
          break label253;
        }
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("hh:mma");
        paramViewGroup.statusInfoTime.setText(localSimpleDateFormat.format(((OrderStatusFragment.OrderStatusInfo)localObject).time));
        localObject = android.text.format.DateUtils.getRelativeDateTimeString(this.mContext, ((OrderStatusFragment.OrderStatusInfo)localObject).time.getTime(), 60000L, 604800000L, 0).toString().split(", ");
        paramViewGroup.statusInfoDay.setText(localObject[0]);
      }
      for (;;)
      {
        if (paramInt != 0) {
          break label274;
        }
        paramViewGroup.statusInfo.setTextColor(this.mContext.getResources().getColor(2131493051));
        return paramView;
        paramViewGroup = (ViewHolder)paramView.getTag();
        break;
        label223:
        paramViewGroup.statusLayout.setAlpha(1.0F);
        paramViewGroup.statusLayout.setBackgroundColor(this.mContext.getResources().getColor(2131493144));
        break label106;
        label253:
        paramViewGroup.statusInfoTime.setText("");
        paramViewGroup.statusInfoDay.setText("");
      }
      label274:
      paramViewGroup.statusInfo.setTextColor(this.mContext.getResources().getColor(2131493052));
      return paramView;
    }
    
    static class ViewHolder
    {
      @InjectView(2131559020)
      TextView statusInfo;
      @InjectView(2131559019)
      TextView statusInfoDay;
      @InjectView(2131559018)
      TextView statusInfoTime;
      @InjectView(2131558513)
      RelativeLayout statusLayout;
      
      public ViewHolder(View paramView)
      {
        ButterKnife.inject(this, paramView);
      }
    }
  }
  
  static class OrderStatusInfo
  {
    public String description;
    public Date time;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/OrderStatusFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
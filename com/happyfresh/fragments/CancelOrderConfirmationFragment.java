package com.happyfresh.fragments;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.ShoppingCartActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartRestError;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.FailedItem;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.MultipleLineItemResponse;
import com.happyfresh.utils.GAUtils;
import java.util.Iterator;
import java.util.List;

public class CancelOrderConfirmationFragment
  extends BaseFragment
  implements DialogInterface.OnDismissListener
{
  private static final String TAG = CancelOrderConfirmationFragment.class.getSimpleName();
  @InjectView(2131558628)
  Button mBackButton;
  @InjectView(2131558630)
  CircularProgressBar mCircularProgressBar;
  @InjectView(2131558627)
  Button mProceedButton;
  @InjectView(2131558629)
  View mProgressContainer;
  @InjectView(2131558618)
  ScrollView mScrollView;
  
  @OnClick({2131558627})
  void cancelOrder()
  {
    this.mProgressContainer.setVisibility(0);
    this.mProceedButton.setEnabled(false);
    this.mBackButton.setEnabled(false);
    String str = getArguments().getString("ICartConstant.KEYS.ORDER_NUMBER");
    getApplication().getOrderManager().recreateOrder(str, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        CancelOrderConfirmationFragment.this.mICartApplication.setOrderListNeedToBeSynced(true);
        String str = ICartRestError.getErrorMessage(CancelOrderConfirmationFragment.this.mContext, paramAnonymousThrowable);
        if (!"Spree::Api::InvalidStateError".equalsIgnoreCase(ICartRestError.getType(paramAnonymousThrowable))) {
          Toast.makeText(CancelOrderConfirmationFragment.this.mContext, str, 1).show();
        }
        while ((CancelOrderConfirmationFragment.this.getActivity() == null) || (!CancelOrderConfirmationFragment.this.isAdded()))
        {
          do
          {
            return;
            CancelOrderConfirmationFragment.this.mICartApplication.setOrderStatusNeedToBeSynced(true);
          } while (CancelOrderConfirmationFragment.this.getActivity() == null);
          new Handler().postDelayed(new Runnable()
          {
            public void run()
            {
              if ((CancelOrderConfirmationFragment.this.getActivity() == null) || (CancelOrderConfirmationFragment.this.getFragmentManager() == null)) {}
              CannotEditOrderDialogFragment localCannotEditOrderDialogFragment;
              do
              {
                return;
                localCannotEditOrderDialogFragment = CannotEditOrderDialogFragment.getInstance();
              } while ((CancelOrderConfirmationFragment.this.getFragmentManager().findFragmentByTag("cannotEditOrderDialog") != null) || (localCannotEditOrderDialogFragment.isAdded()));
              localCannotEditOrderDialogFragment.setOnDismissListener(CancelOrderConfirmationFragment.this);
              localCannotEditOrderDialogFragment.show(CancelOrderConfirmationFragment.this.getFragmentManager(), "cannotEditOrderDialog");
              CancelOrderConfirmationFragment.this.sendTracker("Edit Order Failed Popup");
            }
          }, 200L);
        }
        CancelOrderConfirmationFragment.this.mProgressContainer.setVisibility(8);
        CancelOrderConfirmationFragment.this.mProceedButton.setEnabled(true);
        CancelOrderConfirmationFragment.this.mBackButton.setEnabled(true);
      }
      
      public void onSuccess(MultipleLineItemResponse paramAnonymousMultipleLineItemResponse)
      {
        if (paramAnonymousMultipleLineItemResponse != null)
        {
          if (paramAnonymousMultipleLineItemResponse.order != null)
          {
            CancelOrderConfirmationFragment.this.getApplication().currentOrder = paramAnonymousMultipleLineItemResponse.order;
            CancelOrderConfirmationFragment.this.getApplication().getICartNotification().raiseCurrentOrderSetEvent(this, CancelOrderConfirmationFragment.this.getApplication().currentOrder);
          }
          if ((paramAnonymousMultipleLineItemResponse.failedItems != null) && (!paramAnonymousMultipleLineItemResponse.failedItems.isEmpty()))
          {
            paramAnonymousMultipleLineItemResponse = paramAnonymousMultipleLineItemResponse.failedItems.iterator();
            break label174;
          }
        }
        for (;;)
        {
          FailedItem localFailedItem;
          if (paramAnonymousMultipleLineItemResponse.hasNext())
          {
            localFailedItem = (FailedItem)paramAnonymousMultipleLineItemResponse.next();
            localObject = CancelOrderConfirmationFragment.this.mICartApplication.getTemporaryLineItems();
            if (localObject != null) {}
          }
          else
          {
            CancelOrderConfirmationFragment.this.mICartApplication.setTemporaryLineItems(null);
            GAUtils.trackCancelOrder(CancelOrderConfirmationFragment.this.mContext);
            CancelOrderConfirmationFragment.this.mICartApplication.getSharedPreferencesManager().saveLastCancelOrder();
            if ((CancelOrderConfirmationFragment.this.getActivity() != null) && (CancelOrderConfirmationFragment.this.isAdded())) {
              break label236;
            }
            return;
          }
          Object localObject = ((List)localObject).iterator();
          label174:
          if (((Iterator)localObject).hasNext())
          {
            LineItem localLineItem = (LineItem)((Iterator)localObject).next();
            if (localFailedItem.variantId != localLineItem.variantId.longValue()) {
              break;
            }
            localLineItem.setOutOfStock();
            CancelOrderConfirmationFragment.this.mICartApplication.outOfStockItems.add(localLineItem);
          }
        }
        label236:
        paramAnonymousMultipleLineItemResponse = new Intent(CancelOrderConfirmationFragment.this.getActivity(), ShoppingCartActivity.class);
        paramAnonymousMultipleLineItemResponse.putExtra("ICartConstant.KEYS.EXTRAS.PREVIOUS_SCREEN", CancelOrderConfirmationFragment.this.getScreenName());
        CancelOrderConfirmationFragment.this.startActivity(paramAnonymousMultipleLineItemResponse);
        CancelOrderConfirmationFragment.this.mICartApplication.finishTemporaryActivity();
        CancelOrderConfirmationFragment.this.mICartApplication.setOrderListNeedToBeSynced(true);
        CancelOrderConfirmationFragment.this.mProgressContainer.setVisibility(8);
        CancelOrderConfirmationFragment.this.mProceedButton.setEnabled(true);
        CancelOrderConfirmationFragment.this.mBackButton.setEnabled(true);
        CancelOrderConfirmationFragment.this.getActivity().finish();
      }
    });
  }
  
  protected String getScreenName()
  {
    return "Cancel Order Confirmation";
  }
  
  @OnClick({2131558628})
  void keepOrder()
  {
    if (getActivity() == null) {
      return;
    }
    getActivity().finish();
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903109, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public void onDismiss(DialogInterface paramDialogInterface)
  {
    keepOrder();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CancelOrderConfirmationFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.happyfresh.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.activities.CheckoutActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.models.Address;
import com.happyfresh.models.Order;
import com.happyfresh.models.User;
import com.happyfresh.utils.GAUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;

public class AddressManagementFragment
  extends BaseFragment
{
  private static final String TAG = AddressManagementFragment.class.getSimpleName();
  private CheckoutActivity mActivity;
  private AddressItemAdapter mAdapter;
  private List<Address> mAddresses;
  @InjectView(2131558607)
  Button mBackButton;
  @InjectView(2131558604)
  TextView mCreateNew;
  private LinearLayoutManager mLinearLayoutManager;
  @InjectView(2131558605)
  RecyclerView mRecyclerView;
  private boolean mSelectingAddress = false;
  
  private void createNewAddress()
  {
    CreateAddressFormFragment localCreateAddressFormFragment = new CreateAddressFormFragment();
    localCreateAddressFormFragment.setTargetFragment(this, 1000);
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("ICartConstant.KEYS.EXPRESS_CHECKOUT", true);
    localBundle.putString("ICartConstant.KEYS.ADDRESS_SCREEN_TITLE", "Add Address");
    localCreateAddressFormFragment.setArguments(localBundle);
    getFragmentManager().beginTransaction().add(2131558581, localCreateAddressFormFragment).addToBackStack(null).commit();
  }
  
  protected String getScreenName()
  {
    return "Choose Delivery Address";
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    paramBundle = getApplication().getCurrentUser();
    if (paramBundle == null) {}
    do
    {
      return;
      this.mAddresses = paramBundle.getAddresses();
    } while (this.mAddresses == null);
    this.mAdapter = new AddressItemAdapter(getActivity());
    this.mAdapter.addAll(this.mAddresses);
    this.mAdapter.setOnItemClickListener(new OnAddressClickListener()
    {
      public void onItemClick(int paramAnonymousInt)
      {
        Address localAddress = (Address)AddressManagementFragment.this.mAddresses.get(paramAnonymousInt);
        GAUtils.trackingECommerceCheckout(AddressManagementFragment.this.getApplication(), 2, localAddress.toString(AddressManagementFragment.this.mICartApplication), AddressManagementFragment.this.getScreenName());
        AddressManagementFragment.this.passBackAddress(localAddress);
      }
    });
    this.mLinearLayoutManager = new LinearLayoutManager(getActivity());
    this.mRecyclerView.setLayoutManager(this.mLinearLayoutManager);
    this.mRecyclerView.setAdapter(this.mAdapter);
    this.mBackButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AddressManagementFragment.this.getActivity().onBackPressed();
      }
    });
    this.mCreateNew.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AddressManagementFragment.this.createNewAddress();
      }
    });
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mActivity = ((CheckoutActivity)paramActivity);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    GAUtils.trackingECommerceCheckout(getApplication(), 2, null, getScreenName());
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903107, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void passBackAddress(final Address paramAddress)
  {
    if ((!this.mSelectingAddress) && (getApplication().currentOrder != null)) {}
    try
    {
      this.mSelectingAddress = true;
      getApplication().getOrderManager().updateAddressOnCheckout(getApplication().currentOrder.number, Long.valueOf(paramAddress.remoteId), null, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          Log.d(AddressManagementFragment.TAG, "Failure");
          AddressManagementFragment.access$202(AddressManagementFragment.this, false);
        }
        
        public void onSuccess(Order paramAnonymousOrder)
        {
          Iterator localIterator = AddressManagementFragment.this.getApplication().getCurrentUser().getAddresses().iterator();
          while (localIterator.hasNext())
          {
            Address localAddress = (Address)localIterator.next();
            if (localAddress.remoteId == paramAddress.remoteId) {
              localAddress.isPrimary = true;
            } else {
              localAddress.isPrimary = false;
            }
          }
          AddressManagementFragment.access$202(AddressManagementFragment.this, false);
          AddressManagementFragment.this.getApplication().currentOrder = paramAnonymousOrder;
          if (AddressManagementFragment.this.getActivity() == null) {
            return;
          }
          if ((AddressManagementFragment.this.getTargetFragment() != null) && ((AddressManagementFragment.this.getTargetFragment() instanceof CheckoutDeliveryFragment))) {
            ((CheckoutDeliveryFragment)AddressManagementFragment.this.getTargetFragment()).setDeliveryAddress(paramAddress);
          }
          new Handler().postDelayed(new Runnable()
          {
            public void run()
            {
              if ((AddressManagementFragment.this.getActivity() != null) && (AddressManagementFragment.this.getFragmentManager() != null)) {
                AddressManagementFragment.this.getFragmentManager().popBackStackImmediate();
              }
            }
          }, 200L);
        }
      });
      this.mActivity.setDeliveryScreenSelected("Delivery");
      sendTracker(this.mActivity.getDeliveryScreenSelected());
      return;
    }
    catch (JSONException paramAddress)
    {
      for (;;)
      {
        paramAddress.printStackTrace();
        this.mSelectingAddress = false;
      }
    }
    catch (UnsupportedEncodingException paramAddress)
    {
      for (;;)
      {
        paramAddress.printStackTrace();
        this.mSelectingAddress = false;
      }
    }
  }
  
  public void popPassBackAddress(Address paramAddress)
  {
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        if ((AddressManagementFragment.this.getActivity() == null) || (AddressManagementFragment.this.getFragmentManager() == null)) {
          return;
        }
        AddressManagementFragment.this.getFragmentManager().popBackStackImmediate();
      }
    }, 200L);
    passBackAddress(paramAddress);
  }
  
  protected class AddressItemAdapter
    extends RecyclerView.Adapter<RecyclerView.ViewHolder>
  {
    private List<Address> mAddresses = new ArrayList();
    private Context mContext;
    private AddressManagementFragment.OnAddressClickListener mItemClickListener;
    
    public AddressItemAdapter(Context paramContext)
    {
      this.mContext = paramContext;
    }
    
    public void addAll(List<Address> paramList)
    {
      this.mAddresses.clear();
      this.mAddresses = paramList;
    }
    
    public int getItemCount()
    {
      return this.mAddresses.size();
    }
    
    public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
    {
      Address localAddress = (Address)this.mAddresses.get(paramInt);
      ((AddressItemViewHolder)paramViewHolder).deliveryAddress.setText(localAddress.toString(AddressManagementFragment.this.mICartApplication));
    }
    
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
      return new AddressItemViewHolder(LayoutInflater.from(this.mContext).inflate(2130903070, null));
    }
    
    public void setOnItemClickListener(AddressManagementFragment.OnAddressClickListener paramOnAddressClickListener)
    {
      this.mItemClickListener = paramOnAddressClickListener;
    }
    
    class AddressItemViewHolder
      extends RecyclerView.ViewHolder
      implements View.OnClickListener
    {
      @InjectView(2131558516)
      TextView deliveryAddress;
      
      public AddressItemViewHolder(View paramView)
      {
        super();
        ButterKnife.inject(this, paramView);
        paramView.setOnClickListener(this);
      }
      
      public void onClick(View paramView)
      {
        if (AddressManagementFragment.AddressItemAdapter.this.mItemClickListener != null) {
          AddressManagementFragment.AddressItemAdapter.this.mItemClickListener.onItemClick(getPosition());
        }
      }
    }
  }
  
  static abstract interface OnAddressClickListener
  {
    public abstract void onItemClick(int paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/AddressManagementFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
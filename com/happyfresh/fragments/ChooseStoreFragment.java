package com.happyfresh.fragments;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;
import com.happyfresh.activities.ChooseLocationActivity;
import com.happyfresh.activities.ChooseStoreActivity;
import com.happyfresh.activities.MainActivity;
import com.happyfresh.adapters.StoreAdapter;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartDialogCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.listeners.OnItemClickListener;
import com.happyfresh.managers.LocationManager;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.Order;
import com.happyfresh.models.PromotionString;
import com.happyfresh.models.StockLocation;
import com.happyfresh.models.StockLocationResponse;
import com.happyfresh.utils.DialogColor;
import com.happyfresh.utils.DialogUtils;
import java.util.List;
import retrofit.RetrofitError;

public class ChooseStoreFragment
  extends BaseFragment
{
  private static final String TAG = ChooseStoreFragment.class.getSimpleName();
  @InjectView(2131558694)
  CircularProgressBar mProgressBar;
  private StoreAdapter mStoreAdapter;
  @InjectView(2131558692)
  RecyclerView mStoreRecyclerView;
  
  private void changeArea()
  {
    if (this.mICartApplication.currentOrder == null) {}
    for (boolean bool = true; bool; bool = this.mICartApplication.currentOrder.isEmptyCart())
    {
      goToChooseLocation();
      return;
    }
    showChangeAreaConfirmationDialog(new OnAreaChangedListener()
    {
      public void onAreaChanged()
      {
        ChooseStoreFragment.this.goToChooseLocation();
      }
    });
  }
  
  private void goToChooseLocation()
  {
    if (getActivity() == null) {
      return;
    }
    Intent localIntent = new Intent(getActivity(), ChooseLocationActivity.class);
    localIntent.setFlags(268468224);
    startActivity(localIntent);
    getActivity().finish();
  }
  
  private void saveStoreAndGotoMainActivity(StockLocation paramStockLocation)
  {
    final Object localObject1 = getApplication();
    SharedPreferencesManager localSharedPreferencesManager = ((ICartApplication)localObject1).getSharedPreferencesManager();
    ((ICartApplication)localObject1).sendEvent("ui_action", "Changed Store", paramStockLocation.name);
    localSharedPreferencesManager.saveStockLocation(paramStockLocation);
    ((ICartApplication)localObject1).outOfStockItems.clear();
    ((ICartApplication)localObject1).clearOrderIdAddedToCartList();
    final Object localObject2 = getArguments().getString("ICartConstant.KEYS.EXTRAS.CITY_NAME");
    final String str2 = getArguments().getString("ICartConstant.KEYS.EXTRAS.SUB_LOCALITY");
    String str1 = getArguments().getString("ICartConstant.KEYS.EXTRAS.ZIP_CODE");
    localObject1 = localObject2;
    if (TextUtils.isEmpty((CharSequence)localObject2)) {
      localObject1 = localSharedPreferencesManager.getStockLocationCity();
    }
    localObject2 = str1;
    if (TextUtils.isEmpty(str1)) {
      localObject2 = localSharedPreferencesManager.getStockLocationCity();
    }
    if ((!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty((CharSequence)localObject1)) && (!TextUtils.isEmpty((CharSequence)localObject2))) {
      this.mICartApplication.getAndroidAdsId(new ICartCallback(getClass().getSimpleName())
      {
        public void onSuccess(String paramAnonymousString)
        {
          ChooseStoreFragment.this.mICartApplication.sendUserData(paramAnonymousString, localObject1, str2, localObject2, true);
        }
      });
    }
    localObject1 = new Intent(getActivity(), MainActivity.class);
    ((Intent)localObject1).setFlags(268468224);
    ((Intent)localObject1).putExtra("ICartConstant.KEYS.EXTRAS.STOCK_LOCATION_ID", paramStockLocation.remoteId);
    startActivity((Intent)localObject1);
    getActivity().finish();
  }
  
  private void showChangeAreaConfirmationDialog(final OnAreaChangedListener paramOnAreaChangedListener)
  {
    View localView = LayoutInflater.from(getActivity()).inflate(2130903073, null);
    ((TextView)localView.findViewById(2131558525)).setText(getString(2131165308, new Object[] { this.mICartApplication.getSharedPreferencesManager().getStockLocationName() }));
    DialogColor localDialogColor = new DialogColor();
    localDialogColor.positiveButtonColor = Integer.valueOf(getResources().getColor(2131492920));
    DialogUtils.showDialog(getActivity(), getString(2131165307), null, getString(2131165320), getString(2131165321), null, new ICartDialogCallback()
    {
      public void onCancel() {}
      
      public void onNegative(MaterialDialog paramAnonymousMaterialDialog)
      {
        ChooseStoreFragment.this.sendTracker("Change Store");
      }
      
      public void onNeutral(MaterialDialog paramAnonymousMaterialDialog) {}
      
      public void onPositive(MaterialDialog paramAnonymousMaterialDialog)
      {
        paramAnonymousMaterialDialog = ChooseStoreFragment.this.mICartApplication.currentOrder.number;
        ChooseStoreFragment.this.mICartApplication.getOrderManager().emptyCart(paramAnonymousMaterialDialog, new ICartCallback(ChooseStoreFragment.TAG)
        {
          public void onSuccess(Object paramAnonymous2Object)
          {
            paramAnonymous2Object = (Order)paramAnonymous2Object;
            if (ChooseStoreFragment.this.mICartApplication.currentOrder != null) {
              ChooseStoreFragment.this.mICartApplication.currentOrder.clearCart();
            }
            ChooseStoreFragment.this.mICartApplication.resetCountryCode();
            ChooseStoreFragment.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, (Order)paramAnonymous2Object);
            if (ChooseStoreFragment.7.this.val$onAreaChangedListener != null) {
              ChooseStoreFragment.7.this.val$onAreaChangedListener.onAreaChanged();
            }
          }
        });
      }
    }, localView, localDialogColor).setOnCancelListener(new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramAnonymousDialogInterface)
      {
        ChooseStoreFragment.this.sendTracker("Change Store");
      }
    });
    sendTracker("Store Popup");
  }
  
  private void showFreeDelivery(PromotionString paramPromotionString)
  {
    paramPromotionString = FreeDeliveryFragment.newInstance(paramPromotionString.title, paramPromotionString.subject, paramPromotionString.line1, paramPromotionString.line2, paramPromotionString.buttonString);
    paramPromotionString.setOnDismissListener(new DialogInterface.OnDismissListener()
    {
      public void onDismiss(DialogInterface paramAnonymousDialogInterface)
      {
        ChooseStoreFragment.this.getApplication().getSharedPreferencesManager().markingNewUser(false);
      }
    });
    paramPromotionString.show(getFragmentManager(), null);
  }
  
  private void updateList(List<StockLocation> paramList)
  {
    this.mStoreAdapter.setData(paramList);
  }
  
  private void updateStockLocations()
  {
    this.mProgressBar.setVisibility(0);
    Object localObject1 = getArguments().getParcelableArrayList("ICartConstant.KEYS.EXTRAS.STOCK_LOCATIONS");
    Object localObject2 = Long.valueOf(getArguments().getLong("ICartConstant.KEYS.EXTRAS.SUB_DISTRICT_ID"));
    if (localObject1 != null)
    {
      updateList((List)localObject1);
      this.mProgressBar.setVisibility(8);
      return;
    }
    if ((localObject2 != null) && (((Long)localObject2).longValue() != 0L))
    {
      getApplication().getLocationManager().getStockLocations((Long)localObject2, null, null, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (ChooseStoreFragment.this.getActivity() == null) {}
          do
          {
            return;
            ChooseStoreFragment.this.mProgressBar.setVisibility(8);
            paramAnonymousThrowable = (RetrofitError)paramAnonymousThrowable;
          } while ((paramAnonymousThrowable == null) || (paramAnonymousThrowable.getBody() == null));
          DialogUtils.showDialog(ChooseStoreFragment.this.getActivity(), ChooseStoreFragment.this.getString(2131165378), paramAnonymousThrowable.getBody().toString(), null, null, null, null);
        }
        
        public void onSuccess(StockLocationResponse paramAnonymousStockLocationResponse)
        {
          ChooseStoreFragment.this.updateList(paramAnonymousStockLocationResponse.stockLocations);
          if (ChooseStoreFragment.this.getActivity() == null) {
            return;
          }
          ChooseStoreFragment.this.mProgressBar.setVisibility(8);
        }
      });
      return;
    }
    localObject1 = getArguments().getString("ICartConstant.KEYS.EXTRAS.ZIP_CODE");
    double d1 = getArguments().getDouble("ICartConstant.KEYS.EXTRAS.LONGITUDE");
    double d2 = getArguments().getDouble("ICartConstant.KEYS.EXTRAS.LATITUDE");
    localObject2 = getArguments().getString("ICartConstant.KEYS.EXTRAS.SUB_LOCALITY");
    getApplication().getLocationManager().getNearbyStockLocations(Double.valueOf(d1), Double.valueOf(d2), (String)localObject1, true, (String)localObject2, null, null, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (ChooseStoreFragment.this.getActivity() == null) {}
        do
        {
          return;
          ChooseStoreFragment.this.mProgressBar.setVisibility(8);
          paramAnonymousThrowable = (RetrofitError)paramAnonymousThrowable;
        } while ((paramAnonymousThrowable == null) || (paramAnonymousThrowable.getBody() == null));
        DialogUtils.showDialog(ChooseStoreFragment.this.getActivity(), ChooseStoreFragment.this.getString(2131165378), paramAnonymousThrowable.getBody().toString(), null, null, null, null);
      }
      
      public void onSuccess(StockLocationResponse paramAnonymousStockLocationResponse)
      {
        ChooseStoreFragment.this.updateList(paramAnonymousStockLocationResponse.stockLocations);
        if (ChooseStoreFragment.this.getActivity() == null) {
          return;
        }
        ChooseStoreFragment.this.mProgressBar.setVisibility(8);
      }
    });
  }
  
  protected String getScreenName()
  {
    ComponentName localComponentName = getActivity().getCallingActivity();
    if ((localComponentName != null) && (localComponentName.getClassName().equalsIgnoreCase(MainActivity.class.getName()))) {
      return "Change Store";
    }
    return "Choose Store";
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mStoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    this.mStoreRecyclerView.setItemAnimator(new DefaultItemAnimator());
    this.mStoreAdapter = new StoreAdapter(getActivity());
    this.mStoreRecyclerView.setAdapter(this.mStoreAdapter);
    this.mStoreAdapter.setOnItemClickListener(new OnItemClickListener()
    {
      public void onItemClick(View paramAnonymousView, final Object paramAnonymousObject)
      {
        paramAnonymousView = (ChooseStoreActivity)ChooseStoreFragment.this.getActivity();
        if (paramAnonymousView == null) {
          return;
        }
        paramAnonymousObject = (StockLocation)paramAnonymousObject;
        paramAnonymousView = paramAnonymousView.getCallingActivity();
        if (paramAnonymousView != null)
        {
          if ((paramAnonymousView.getClassName().equalsIgnoreCase(MainActivity.class.getName())) && (ChooseStoreFragment.this.getApplication().getStockLocationId() != ((StockLocation)paramAnonymousObject).remoteId) && (ChooseStoreFragment.this.getApplication().currentOrder != null) && (!ChooseStoreFragment.this.getApplication().currentOrder.isEmptyCart()))
          {
            ChooseStoreFragment.this.showChangeAreaConfirmationDialog(new ChooseStoreFragment.OnAreaChangedListener()
            {
              public void onAreaChanged()
              {
                if (ChooseStoreFragment.this.getActivity() == null) {
                  return;
                }
                ChooseStoreFragment.this.saveStoreAndGotoMainActivity(paramAnonymousObject);
              }
            });
            return;
          }
          ChooseStoreFragment.this.saveStoreAndGotoMainActivity((StockLocation)paramAnonymousObject);
          return;
        }
        ChooseStoreFragment.this.saveStoreAndGotoMainActivity((StockLocation)paramAnonymousObject);
      }
    });
  }
  
  @OnClick({2131558695})
  public void onClickChangeArea(View paramView)
  {
    changeArea();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903117, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    if (getApplication().getSharedPreferencesManager().isNewUser())
    {
      paramViewGroup = getApplication().getSharedPreferencesManager().getPromotionString();
      if (paramViewGroup != null) {
        showFreeDelivery(paramViewGroup);
      }
    }
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public void onResume()
  {
    super.onResume();
    updateStockLocations();
  }
  
  public static abstract interface OnAreaChangedListener
  {
    public abstract void onAreaChanged();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ChooseStoreFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
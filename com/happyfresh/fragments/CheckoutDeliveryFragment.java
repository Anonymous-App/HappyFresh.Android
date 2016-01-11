package com.happyfresh.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.CheckoutActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.common.ICartRestError;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.listeners.OnItemClickListener;
import com.happyfresh.listeners.OnVerifyCodeListener;
import com.happyfresh.listeners.OnVerifyNumberListener;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.models.Address;
import com.happyfresh.models.Order;
import com.happyfresh.models.Shipment;
import com.happyfresh.models.ShippingRate;
import com.happyfresh.models.Slot;
import com.happyfresh.models.SlotResponse;
import com.happyfresh.models.User;
import com.happyfresh.utils.AccengageTrackerUtils;
import com.happyfresh.utils.AdjustUtils;
import com.happyfresh.utils.DialogUtils;
import com.happyfresh.utils.GAUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.json.JSONException;
import retrofit.RetrofitError;

public class CheckoutDeliveryFragment
  extends BaseFragment
{
  private static final String CHECKOUT_STATE_KEY = String.format("%s%s", new Object[] { CheckoutDeliveryFragment.class.getSimpleName(), "CHECKOUT_STATE_KEY" });
  private static final String TAG = CheckoutDeliveryFragment.class.getSimpleName();
  @InjectView(2131558581)
  View checkoutContainer;
  @InjectView(2131558646)
  TextView deliveryInfoLabel;
  private boolean isVoucherCodeEntered = false;
  private ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener()
  {
    public void onGlobalLayout()
    {
      FragmentActivity localFragmentActivity = CheckoutDeliveryFragment.this.getActivity();
      if (localFragmentActivity == null) {}
      int i;
      int j;
      int k;
      int m;
      do
      {
        return;
        Rect localRect = new Rect();
        localFragmentActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        i = localRect.top;
        j = CheckoutDeliveryFragment.this.checkoutContainer.getRootView().getHeight();
        k = localRect.bottom;
        m = localRect.top;
      } while (!CheckoutDeliveryFragment.this.mDeliverySlotSelected);
      if (j - i - (k - m) > 400)
      {
        CheckoutDeliveryFragment.this.mPaymentDetail.setVisibility(8);
        return;
      }
      CheckoutDeliveryFragment.this.mPaymentDetail.setVisibility(0);
    }
  };
  private CheckoutActivity mActivity;
  Listener mAvailableSlotsReceivedListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject1, ICartNotification.Type paramAnonymousType, Object paramAnonymousObject2)
    {
      CheckoutDeliveryFragment.access$902(CheckoutDeliveryFragment.this, (List)paramAnonymousObject2);
      CheckoutDeliveryFragment.this.mDeliveryTime.setHintTextColor(CheckoutDeliveryFragment.this.getResources().getColor(2131492964));
      CheckoutDeliveryFragment.this.mDeliveryTime.setEnabled(true);
      CheckoutDeliveryFragment.this.mDeliverySlot.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymous2View)
        {
          CheckoutDeliveryFragment.this.showDialogSelectDeliveryTime();
        }
      });
      CheckoutDeliveryFragment.this.mSlotUpdateProgress.setVisibility(4);
    }
  };
  @InjectView(2131558645)
  LinearLayout mCheckoutDeliveryContainer;
  private int mCheckoutState = 0;
  Listener mCheckoutStateListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject1, ICartNotification.Type paramAnonymousType, Object paramAnonymousObject2)
    {
      CheckoutDeliveryFragment.access$1002(CheckoutDeliveryFragment.this, ((Integer)paramAnonymousObject2).intValue());
      CheckoutDeliveryFragment.this.initiateDelivery();
    }
  };
  Listener mCurrentOrderSetListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject1, ICartNotification.Type paramAnonymousType, Object paramAnonymousObject2)
    {
      CheckoutDeliveryFragment.this.showShipmentData();
      if (CheckoutDeliveryFragment.this.shouldUpdateAddress) {
        CheckoutDeliveryFragment.this.updateAddress();
      }
    }
  };
  Listener mCurrentUserAddressSetListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject1, ICartNotification.Type paramAnonymousType, Object paramAnonymousObject2)
    {
      CheckoutDeliveryFragment.this.updateView();
    }
  };
  @InjectView(2131558516)
  TextView mDeliveryAddressText;
  @InjectView(2131558658)
  TextView mDeliveryFee;
  @InjectView(2131558648)
  TextView mDeliveryNumber;
  @InjectView(2131558651)
  View mDeliverySlot;
  private boolean mDeliverySlotSelected = false;
  @InjectView(2131558652)
  TextView mDeliveryTime;
  @InjectView(2131558660)
  TextView mDiscount;
  @InjectView(2131558659)
  View mDiscountContainer;
  private boolean mExpressCheckout = false;
  @InjectView(2131558656)
  View mPaymentContent;
  @InjectView(2131558655)
  View mPaymentDetail;
  @InjectView(2131558662)
  CircularProgressBar mPaymentProgress;
  @InjectView(2131558664)
  CircularProgressBar mProgressIndicator;
  private Address mSelectedDeliveryAddress;
  private Slot mShipmentSlot;
  private ShippingRate mShippingRate;
  @InjectView(2131558653)
  CircularProgressBar mSlotUpdateProgress;
  private List<Slot> mSlots = new ArrayList();
  @InjectView(2131558663)
  Button mSubmitButton;
  @InjectView(2131558657)
  TextView mSubtotal;
  @InjectView(2131558661)
  TextView mTotalFee;
  Listener mUpdateAddressOnCheckoutListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject1, ICartNotification.Type paramAnonymousType, Object paramAnonymousObject2)
    {
      CheckoutDeliveryFragment.this.showShipmentData();
      CheckoutDeliveryFragment.this.updateSlot();
    }
  };
  @InjectView(2131558650)
  View mVerified;
  @InjectView(2131558647)
  View mVerifyArea;
  @InjectView(2131558649)
  TextView mVerifyNow;
  private String mVoucherCode;
  @InjectView(2131558654)
  EditText mVoucherCodeText;
  private boolean shouldUpdateAddress = false;
  
  private void applyCouponCode()
  {
    if (getApplication().currentOrder == null)
    {
      Toast.makeText(this.mContext, this.mContext.getString(2131165671), 0).show();
      return;
    }
    try
    {
      this.mPaymentContent.setVisibility(8);
      this.mPaymentProgress.setVisibility(0);
      toggleVoucherCodeEditText();
      String str1 = getApplication().currentOrder.number;
      final String str2 = this.mVoucherCodeText.getText().toString();
      getApplication().getOrderManager().applyCouponCode(str1, str2, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (CheckoutDeliveryFragment.this.getActivity() == null) {
            return;
          }
          CheckoutDeliveryFragment.this.mPaymentContent.setVisibility(0);
          CheckoutDeliveryFragment.this.mPaymentProgress.setVisibility(8);
          CheckoutDeliveryFragment.this.toggleVoucherCodeEditText();
          RetrofitError localRetrofitError = (RetrofitError)paramAnonymousThrowable;
          if ((localRetrofitError != null) && (localRetrofitError.getResponse() != null))
          {
            paramAnonymousThrowable = ICartRestError.getErrorMessage(CheckoutDeliveryFragment.this.getApplication(), paramAnonymousThrowable);
            DialogUtils.showDialog(CheckoutDeliveryFragment.this.getActivity(), CheckoutDeliveryFragment.this.getString(2131165378), paramAnonymousThrowable, CheckoutDeliveryFragment.this.getString(2131165478), null, null, null);
            return;
          }
          DialogUtils.showDialog(CheckoutDeliveryFragment.this.getActivity(), CheckoutDeliveryFragment.this.getString(2131165378), localRetrofitError.getLocalizedMessage(), CheckoutDeliveryFragment.this.getString(2131165478), null, null, null);
        }
        
        public void onSuccess(Order paramAnonymousOrder)
        {
          CheckoutDeliveryFragment.this.getApplication().currentOrder = paramAnonymousOrder;
          CheckoutDeliveryFragment.access$1902(CheckoutDeliveryFragment.this, str2);
          if (CheckoutDeliveryFragment.this.getActivity() == null) {
            return;
          }
          CheckoutDeliveryFragment.this.showOrderPrice();
          CheckoutDeliveryFragment.this.toggleVoucherCodeEditText();
          CheckoutDeliveryFragment.this.mSubmitButton.setText(2131165464);
          CheckoutDeliveryFragment.access$202(CheckoutDeliveryFragment.this, false);
        }
      });
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
  }
  
  private String buildSlotText(Slot paramSlot)
  {
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("ccc, dd MMM yyyy");
    SimpleDateFormat localSimpleDateFormat3 = new SimpleDateFormat("hha");
    String str = localSimpleDateFormat1.format(paramSlot.startTime);
    StringBuilder localStringBuilder = new StringBuilder();
    Calendar localCalendar = Calendar.getInstance();
    if (localSimpleDateFormat1.format(localCalendar.getTime()).equals(str)) {
      localStringBuilder.append(getString(2131165610));
    }
    for (;;)
    {
      localStringBuilder.append(", ");
      localStringBuilder.append(localSimpleDateFormat3.format(paramSlot.startTime));
      localStringBuilder.append(" - ");
      localStringBuilder.append(localSimpleDateFormat3.format(paramSlot.endTime));
      return localStringBuilder.toString();
      localCalendar.add(6, 1);
      if (localSimpleDateFormat1.format(localCalendar.getTime()).equals(str)) {
        localStringBuilder.append(getString(2131165611));
      } else {
        localStringBuilder.append(localSimpleDateFormat2.format(paramSlot.startTime));
      }
    }
  }
  
  private void deliveryAddressPicker()
  {
    AddressManagementFragment localAddressManagementFragment = new AddressManagementFragment();
    localAddressManagementFragment.setTargetFragment(this, 1000);
    getFragmentManager().beginTransaction().add(2131558581, localAddressManagementFragment).addToBackStack(null).commit();
    this.mActivity.setDeliveryScreenSelected("Choose Delivery Address");
  }
  
  private void displayVerified()
  {
    if (this.mVerifyArea == null) {
      return;
    }
    this.mVerifyArea.setVisibility(0);
    this.mVerifyNow.setVisibility(8);
    this.mVerified.setVisibility(0);
    this.mSubmitButton.setEnabled(true);
  }
  
  private void displayVerifyNow()
  {
    if (this.mVerifyArea == null) {
      return;
    }
    this.mVerifyArea.setVisibility(0);
    this.mVerifyNow.setVisibility(0);
    this.mVerified.setVisibility(8);
    this.mSubmitButton.setEnabled(false);
  }
  
  private String getAppliedVoucher()
  {
    if (getApplication().currentOrder != null)
    {
      String str = getApplication().currentOrder.appliedVoucher;
      if (str != null) {
        return str;
      }
    }
    return null;
  }
  
  private void goToVerifyCodeDialog(final String paramString)
  {
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        if ((CheckoutDeliveryFragment.this.getActivity() == null) || (CheckoutDeliveryFragment.this.getFragmentManager() == null)) {
          return;
        }
        VerifyCodeDialogFragment localVerifyCodeDialogFragment = VerifyCodeDialogFragment.newInstance(paramString);
        localVerifyCodeDialogFragment.show(CheckoutDeliveryFragment.this.getFragmentManager(), null);
        localVerifyCodeDialogFragment.setOnVerifyCodeListener(new OnVerifyCodeListener()
        {
          public void onBackEditNumber(String paramAnonymous2String)
          {
            paramAnonymous2String = VerifyNumberDialogFragment.newInstance(paramAnonymous2String);
            paramAnonymous2String.show(CheckoutDeliveryFragment.this.getFragmentManager(), null);
            paramAnonymous2String.setOnVerifyNumberListener(new OnVerifyNumberListener()
            {
              public void onSuccessVerifyNumber(String paramAnonymous3String)
              {
                CheckoutDeliveryFragment.this.goToVerifyCodeDialog(paramAnonymous3String);
              }
            });
            CheckoutDeliveryFragment.this.sendTracker("Verify Number 1");
          }
          
          public void onSuccessVerifyCode()
          {
            CheckoutDeliveryFragment.this.displayVerified();
          }
        });
        CheckoutDeliveryFragment.this.sendTracker("Verify Number 2");
      }
    }, 200L);
  }
  
  private void processExpressCheckout()
  {
    if ((CheckoutFragment.class.equals(getTargetFragment().getClass())) && (this.mCheckoutState != 1)) {}
    while (getApplication().currentOrder == null) {
      return;
    }
    String str = getApplication().currentOrder.number;
    if (getApplication().currentOrder.isCart())
    {
      showProgress();
      getApplication().getOrderManager().next(str, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (CheckoutDeliveryFragment.this.getActivity() == null) {
            return;
          }
          DialogUtils.showDialog(CheckoutDeliveryFragment.this.getActivity(), CheckoutDeliveryFragment.this.getString(2131165378), CheckoutDeliveryFragment.this.getString(2131165351), CheckoutDeliveryFragment.this.getString(2131165478), null, null, null);
          CheckoutDeliveryFragment.this.mDeliverySlot.setEnabled(false);
          CheckoutDeliveryFragment.this.updateView();
        }
        
        public void onSuccess(Order paramAnonymousOrder)
        {
          CheckoutDeliveryFragment.this.getApplication().currentOrder = paramAnonymousOrder;
          if (CheckoutDeliveryFragment.this.getActivity() == null) {
            return;
          }
          CheckoutDeliveryFragment.this.updateAddress();
        }
      });
      return;
    }
    updateAddress();
  }
  
  private void resetView()
  {
    if (getActivity() == null) {
      return;
    }
    this.mProgressIndicator.setVisibility(4);
    this.mSlotUpdateProgress.setVisibility(4);
    this.mSubmitButton.setClickable(true);
  }
  
  private void showDeliveryFee()
  {
    NumberFormat localNumberFormat = getApplication().getNumberFormatter();
    double d = getApplication().currentOrder.getDeliveryFee();
    if (d == 0.0D)
    {
      this.mDeliveryFee.setText(getString(2131165397));
      return;
    }
    this.mDeliveryFee.setText(localNumberFormat.format(d));
  }
  
  private void showDiscount()
  {
    NumberFormat localNumberFormat = getApplication().getNumberFormatter();
    double d = getApplication().currentOrder.getDiscount();
    if (d == 0.0D)
    {
      this.mDiscountContainer.setVisibility(8);
      return;
    }
    this.mDiscountContainer.setVisibility(0);
    this.mDiscount.setText(localNumberFormat.format(d));
  }
  
  private void showOrderPrice()
  {
    NumberFormat localNumberFormat = getApplication().getNumberFormatter();
    double d = getApplication().currentOrder.itemTotal.doubleValue();
    this.mSubtotal.setText(localNumberFormat.format(d));
    showDeliveryFee();
    showDiscount();
    d = getApplication().currentOrder.total.doubleValue();
    this.mTotalFee.setText(localNumberFormat.format(d));
    this.mPaymentContent.setVisibility(0);
    this.mPaymentProgress.setVisibility(8);
  }
  
  private void showPaymentDetail(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mPaymentDetail.setVisibility(0);
      this.mPaymentProgress.setVisibility(0);
      this.mPaymentContent.setVisibility(8);
      return;
    }
    this.mPaymentDetail.setVisibility(8);
  }
  
  private void showProgress()
  {
    this.mSlotUpdateProgress.setVisibility(0);
  }
  
  private void showShipmentData()
  {
    Object localObject = getApplication().currentOrder;
    if ((localObject != null) && (((Order)localObject).shipments.size() > 0))
    {
      localObject = (Shipment)((Order)localObject).shipments.get(0);
      if (((Shipment)localObject).slot != null)
      {
        this.mShipmentSlot = ((Shipment)localObject).slot;
        this.mDeliveryTime.setText(buildSlotText(this.mShipmentSlot));
        showPaymentDetail(true);
        showOrderPrice();
      }
    }
    else
    {
      return;
    }
    this.mShipmentSlot = ((Shipment)localObject).slot;
    this.mDeliveryTime.setText("");
    showPaymentDetail(false);
    this.mPaymentContent.setVisibility(8);
  }
  
  private void trackContinuePayment()
  {
    MixpanelTrackerUtils.trackContinuePayment(getApplication());
    AccengageTrackerUtils.trackContinuePayment(getApplication());
    AdjustUtils.trackContinuePayment(getApplication(), this.mVoucherCode);
  }
  
  private void updateAddress()
  {
    if (getApplication().currentOrder == null) {
      this.shouldUpdateAddress = true;
    }
    do
    {
      for (;;)
      {
        return;
        showProgress();
        try
        {
          getApplication().getOrderManager().updateAddressOnCheckout(getApplication().currentOrder.number, Long.valueOf(getApplication().getCurrentUser().getPrimaryShippingAddress().remoteId), null, new ICartCallback(TAG)
          {
            public void onFailure(Throwable paramAnonymousThrowable)
            {
              Log.d(CheckoutDeliveryFragment.TAG, "Failure");
              CheckoutDeliveryFragment.access$602(CheckoutDeliveryFragment.this, false);
              if (CheckoutDeliveryFragment.this.getActivity() == null) {
                return;
              }
              CheckoutDeliveryFragment.this.updateView();
            }
            
            public void onSuccess(Order paramAnonymousOrder)
            {
              CheckoutDeliveryFragment.this.getApplication().currentOrder = paramAnonymousOrder;
              CheckoutDeliveryFragment.access$602(CheckoutDeliveryFragment.this, false);
              if (CheckoutDeliveryFragment.this.getActivity() == null) {
                return;
              }
              if (CheckoutDeliveryFragment.this.getApplication().currentOrder.isAddress())
              {
                CheckoutDeliveryFragment.this.mDeliverySlot.setEnabled(false);
                DialogUtils.showDialog(CheckoutDeliveryFragment.this.getActivity(), CheckoutDeliveryFragment.this.getString(2131165378), CheckoutDeliveryFragment.this.getString(2131165351), CheckoutDeliveryFragment.this.getString(2131165478), null, null, null);
              }
              for (;;)
              {
                CheckoutDeliveryFragment.this.updateView();
                return;
                CheckoutDeliveryFragment.this.mDeliverySlot.setEnabled(true);
              }
            }
          });
          return;
        }
        catch (JSONException localJSONException)
        {
          this.shouldUpdateAddress = false;
          if (getActivity() != null)
          {
            updateView();
            return;
          }
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          this.shouldUpdateAddress = false;
        }
      }
    } while (getActivity() == null);
    updateView();
  }
  
  private void updateSlot()
  {
    this.mDeliveryTime.setHintTextColor(getResources().getColor(2131492963));
    this.mDeliveryTime.setEnabled(false);
    this.mSlotUpdateProgress.setVisibility(0);
    Date localDate = new Date();
    Object localObject = Calendar.getInstance();
    ((Calendar)localObject).setTime(localDate);
    ((Calendar)localObject).add(5, 6);
    localObject = new Date(((Calendar)localObject).getTime().getTime());
    getApplication().getOrderManager().getAvailableSlot(localDate, (Date)localObject, new ICartCallback(TAG)
    {
      public void onSuccess(SlotResponse paramAnonymousSlotResponse)
      {
        CheckoutDeliveryFragment.this.getApplication().getICartNotification().raiseAvailableSlotReceivedEvent(CheckoutDeliveryFragment.this, paramAnonymousSlotResponse.slots);
      }
    });
  }
  
  private void updateView()
  {
    this.deliveryInfoLabel.setVisibility(8);
    this.mCheckoutDeliveryContainer.setVisibility(0);
    this.mProgressIndicator.setVisibility(4);
    this.mSlotUpdateProgress.setVisibility(4);
    this.mPaymentProgress.setVisibility(4);
    User localUser = getApplication().getCurrentUser();
    Object localObject = null;
    if (localUser != null) {
      localObject = localUser.getPrimaryShippingAddress();
    }
    if (localObject != null)
    {
      this.mDeliveryAddressText.setText(((Address)localObject).toString(this.mICartApplication));
      this.mDeliveryNumber.setText(((Address)localObject).phone);
      this.mSelectedDeliveryAddress = ((Address)localObject);
    }
    this.mDeliveryAddressText.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        CheckoutDeliveryFragment.this.deliveryAddressPicker();
      }
    });
    if ((getApplication().currentOrder != null) && (!getApplication().currentOrder.isUserVerified)) {
      displayVerifyNow();
    }
    localObject = getAppliedVoucher();
    if (localObject != null)
    {
      this.mVoucherCodeText.setText((CharSequence)localObject);
      this.mSubmitButton.setText(2131165464);
      this.isVoucherCodeEntered = false;
    }
  }
  
  @OnClick({2131558663})
  void attemptOpenPayment()
  {
    if (this.isVoucherCodeEntered)
    {
      applyCouponCode();
      return;
    }
    this.mSubmitButton.post(new Runnable()
    {
      public void run()
      {
        ((InputMethodManager)CheckoutDeliveryFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(CheckoutDeliveryFragment.this.mDeliveryAddressText.getWindowToken(), 0);
      }
    });
    this.mDeliveryAddressText.clearFocus();
    this.mDeliveryAddressText.setError(null);
    this.mDeliveryTime.clearFocus();
    this.mDeliveryTime.setError(null);
    Object localObject2 = null;
    int j = 0;
    int i = j;
    Object localObject1 = localObject2;
    if (0 == 0)
    {
      i = j;
      localObject1 = localObject2;
      if (this.mSelectedDeliveryAddress == null)
      {
        this.mDeliveryAddressText.setError(getString(2131165379));
        localObject1 = this.mDeliveryAddressText;
        i = 1;
      }
    }
    j = i;
    localObject2 = localObject1;
    if (i == 0)
    {
      j = i;
      localObject2 = localObject1;
      if (this.mShipmentSlot == null)
      {
        this.mDeliveryTime.setError(getString(2131165379));
        localObject2 = this.mDeliveryTime;
        j = 1;
      }
    }
    if (j != 0)
    {
      ((View)localObject2).requestFocus();
      return;
    }
    goToPayment();
  }
  
  protected String getScreenName()
  {
    return "Delivery";
  }
  
  void goToPayment()
  {
    if (getApplication().currentOrder.isPayment())
    {
      trackContinuePayment();
      ((CheckoutActivity)getActivity()).getFragment().goToPayment();
      return;
    }
    processToPayment();
  }
  
  public void initiateDelivery()
  {
    if (getArguments() != null) {
      this.mExpressCheckout = getArguments().getBoolean("ICartConstant.KEYS.EXPRESS_CHECKOUT");
    }
    this.mVoucherCodeText.setEnabled(false);
    this.mVoucherCodeText.setHintTextColor(getResources().getColor(2131492963));
    if (this.mExpressCheckout) {
      processExpressCheckout();
    }
    for (;;)
    {
      this.mVerifyNow.setVisibility(8);
      this.mVerified.setVisibility(8);
      return;
      updateAddress();
    }
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    showShipmentData();
    resetView();
    this.checkoutContainer.getViewTreeObserver().addOnGlobalLayoutListener(this.layoutListener);
    this.mVoucherCodeText.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable) {}
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        String str = CheckoutDeliveryFragment.this.getAppliedVoucher();
        if ((str != null) && (str.equalsIgnoreCase(paramAnonymousCharSequence.toString())))
        {
          CheckoutDeliveryFragment.this.mSubmitButton.setText(2131165464);
          CheckoutDeliveryFragment.access$202(CheckoutDeliveryFragment.this, false);
          return;
        }
        if (TextUtils.isEmpty(paramAnonymousCharSequence))
        {
          CheckoutDeliveryFragment.this.mSubmitButton.setText(2131165464);
          CheckoutDeliveryFragment.access$202(CheckoutDeliveryFragment.this, false);
          return;
        }
        CheckoutDeliveryFragment.this.mSubmitButton.setText(2131165594);
        CheckoutDeliveryFragment.access$202(CheckoutDeliveryFragment.this, true);
      }
    });
    this.mVoucherCodeText.setOnEditorActionListener(new TextView.OnEditorActionListener()
    {
      public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousInt == 6) && (CheckoutDeliveryFragment.this.isVoucherCodeEntered)) {
          CheckoutDeliveryFragment.this.applyCouponCode();
        }
        return false;
      }
    });
    initiateDelivery();
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mActivity = ((CheckoutActivity)paramActivity);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if ((paramBundle != null) && (paramBundle.containsKey(CHECKOUT_STATE_KEY))) {
      this.mCheckoutState = paramBundle.getInt(CHECKOUT_STATE_KEY);
    }
    if (getArguments() != null) {
      this.mExpressCheckout = getArguments().getBoolean("ICartConstant.KEYS.EXPRESS_CHECKOUT");
    }
    if (this.mExpressCheckout)
    {
      GAUtils.trackingECommerceCheckout(getApplication(), 2, null, getScreenName());
      paramBundle = User.getCurrentUser().getPrimaryShippingAddress();
      if (paramBundle == null) {
        break label118;
      }
    }
    label118:
    for (paramBundle = paramBundle.toString(this.mICartApplication);; paramBundle = "")
    {
      GAUtils.trackingECommerceCheckout(getApplication(), 2, paramBundle, getScreenName());
      GAUtils.trackingECommerceCheckout(getApplication(), 3, null, getScreenName());
      return;
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903114, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public void onPause()
  {
    super.onPause();
    ICartNotification localICartNotification = getApplication().getICartNotification();
    localICartNotification.removeAvailableSlotReceivedListener(this.mAvailableSlotsReceivedListener);
    localICartNotification.removeOnCheckoutStateListener(this.mCheckoutStateListener);
    localICartNotification.removeCurrentOrderSetListener(this.mCurrentOrderSetListener);
    localICartNotification.removeCurrentUserAddressSetListener(this.mCurrentUserAddressSetListener);
    localICartNotification.removeUpdateAddressOnCheckoutListener(this.mUpdateAddressOnCheckoutListener);
  }
  
  public void onResume()
  {
    super.onResume();
    ICartNotification localICartNotification = getApplication().getICartNotification();
    localICartNotification.addAvailableSlotReceivedListener(this.mAvailableSlotsReceivedListener);
    localICartNotification.addOnCheckoutStateListener(this.mCheckoutStateListener);
    localICartNotification.addCurrentOrderSetListener(this.mCurrentOrderSetListener);
    localICartNotification.addCurrentUserAddressSetListener(this.mCurrentUserAddressSetListener);
    localICartNotification.addUpdateAddressOnCheckoutListener(this.mUpdateAddressOnCheckoutListener);
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt(CHECKOUT_STATE_KEY, this.mCheckoutState);
  }
  
  @OnClick({2131558649})
  public void onVerifyNowClick()
  {
    VerifyNumberDialogFragment localVerifyNumberDialogFragment = VerifyNumberDialogFragment.newInstance(null);
    localVerifyNumberDialogFragment.show(getFragmentManager(), null);
    localVerifyNumberDialogFragment.setOnVerifyNumberListener(new OnVerifyNumberListener()
    {
      public void onSuccessVerifyNumber(String paramAnonymousString)
      {
        CheckoutDeliveryFragment.this.goToVerifyCodeDialog(paramAnonymousString);
      }
    });
    sendTracker("Verify Number 1");
  }
  
  void processToPayment()
  {
    getApplication().getOrderManager().next(getApplication().currentOrder.number, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        CheckoutDeliveryFragment.this.resetView();
      }
      
      public void onSuccess(Order paramAnonymousOrder)
      {
        CheckoutDeliveryFragment.this.trackContinuePayment();
        paramAnonymousOrder = (CheckoutActivity)CheckoutDeliveryFragment.this.getActivity();
        if (paramAnonymousOrder == null) {
          return;
        }
        paramAnonymousOrder.getFragment().goToPayment();
      }
    });
  }
  
  protected void sendTracker() {}
  
  public void setDeliveryAddress(Address paramAddress)
  {
    this.mSelectedDeliveryAddress = paramAddress;
    this.mPaymentContent.setVisibility(8);
    this.mShipmentSlot = null;
    this.mDeliveryTime.setText(null);
    this.mDeliveryTime.clearFocus();
    this.mDeliveryTime.setError(null);
    this.mDeliveryAddressText.clearFocus();
    this.mDeliveryAddressText.setError(null);
    this.mDeliveryAddressText.setText(this.mSelectedDeliveryAddress.toString(this.mICartApplication));
    this.mDeliveryNumber.setText(this.mSelectedDeliveryAddress.phone);
    if (getApplication().currentOrder.isAddress())
    {
      this.mDeliverySlot.setEnabled(false);
      DialogUtils.showDialog(getActivity(), getString(2131165378), getString(2131165351), getString(2131165478), null, null, null);
      return;
    }
    this.mDeliverySlot.setEnabled(true);
  }
  
  void showDialogSelectDeliveryTime()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelableArrayList("ICartConstant.KEYS.EXTRAS.SLOTS", new ArrayList(this.mSlots));
    final SelectShipmentTimeFragment localSelectShipmentTimeFragment = new SelectShipmentTimeFragment();
    localSelectShipmentTimeFragment.setArguments(localBundle);
    localSelectShipmentTimeFragment.setOnItemClickListener(new OnItemClickListener()
    {
      public void onItemClick(final View paramAnonymousView, Object paramAnonymousObject)
      {
        paramAnonymousView = (Slot)paramAnonymousObject;
        CheckoutDeliveryFragment.this.mDeliveryTime.setText(CheckoutDeliveryFragment.this.buildSlotText(paramAnonymousView));
        CheckoutDeliveryFragment.this.mDeliveryTime.clearFocus();
        CheckoutDeliveryFragment.this.mDeliveryTime.setError(null);
        paramAnonymousObject = paramAnonymousView.shippingMethodId;
        CheckoutDeliveryFragment.access$1402(CheckoutDeliveryFragment.this, CheckoutDeliveryFragment.this.getApplication().currentOrder.getShippingRateByShippingMethodId((Long)paramAnonymousObject));
        if (CheckoutDeliveryFragment.this.mShippingRate == null)
        {
          CheckoutDeliveryFragment.this.showPaymentDetail(false);
          CheckoutDeliveryFragment.this.mPaymentContent.setVisibility(8);
          return;
        }
        try
        {
          CheckoutDeliveryFragment.this.getApplication().getOrderManager().updateSlotOnCheckout(CheckoutDeliveryFragment.this.getApplication().currentOrder.number, CheckoutDeliveryFragment.this.mShippingRate.remoteId, Long.valueOf(paramAnonymousView.remoteId), Long.valueOf(((Shipment)CheckoutDeliveryFragment.this.getApplication().currentOrder.shipments.get(0)).remoteId), CheckoutDeliveryFragment.this.getApplication().currentOrder.isPayment(), new ICartCallback(CheckoutDeliveryFragment.TAG)
          {
            public void onFailure(Throwable paramAnonymous2Throwable)
            {
              if (CheckoutDeliveryFragment.this.getActivity() == null) {
                return;
              }
              CheckoutDeliveryFragment.this.resetView();
              CheckoutDeliveryFragment.this.mPaymentDetail.setVisibility(8);
              CheckoutDeliveryFragment.access$002(CheckoutDeliveryFragment.this, false);
              CheckoutDeliveryFragment.this.mVoucherCodeText.setEnabled(false);
            }
            
            public void onSuccess(Order paramAnonymous2Order)
            {
              CheckoutDeliveryFragment.access$1602(CheckoutDeliveryFragment.this, paramAnonymousView);
              CheckoutDeliveryFragment.this.getApplication().currentOrder = paramAnonymous2Order;
              if (CheckoutDeliveryFragment.this.getActivity() == null) {
                return;
              }
              CheckoutDeliveryFragment.this.showOrderPrice();
              CheckoutDeliveryFragment.access$002(CheckoutDeliveryFragment.this, true);
              CheckoutDeliveryFragment.this.mVoucherCodeText.setEnabled(true);
              CheckoutDeliveryFragment.this.mVoucherCodeText.setHintTextColor(CheckoutDeliveryFragment.this.getResources().getColor(2131492964));
            }
          });
          new Handler().postDelayed(new Runnable()
          {
            public void run()
            {
              CheckoutDeliveryFragment.this.getFragmentManager().beginTransaction().remove(CheckoutDeliveryFragment.13.this.val$fragment).commit();
            }
          }, 50L);
          CheckoutDeliveryFragment.this.showPaymentDetail(true);
          return;
        }
        catch (JSONException paramAnonymousView)
        {
          paramAnonymousView.printStackTrace();
          CheckoutDeliveryFragment.this.resetView();
          return;
        }
        catch (UnsupportedEncodingException paramAnonymousView)
        {
          paramAnonymousView.printStackTrace();
          CheckoutDeliveryFragment.this.resetView();
        }
      }
    });
    localSelectShipmentTimeFragment.show(getFragmentManager(), null);
    sendTracker("Delivery Time");
  }
  
  void toggleVoucherCodeEditText()
  {
    if (this.mVoucherCodeText.isEnabled())
    {
      this.mCheckoutDeliveryContainer.requestFocus();
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.mVoucherCodeText.getWindowToken(), 0);
      this.mVoucherCodeText.setEnabled(false);
      return;
    }
    this.mVoucherCodeText.setEnabled(true);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CheckoutDeliveryFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
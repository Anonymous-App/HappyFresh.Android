package com.happyfresh.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.customs.SquareRelativeLayout;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.models.Order;
import com.happyfresh.models.PaymentMethod;
import com.happyfresh.models.PaymentMethodInformation;
import com.happyfresh.models.PaymentMethodResponse;
import com.happyfresh.models.PaymentService;
import com.happyfresh.models.payload.AdyenPayload;
import com.happyfresh.models.payload.PaymentPayload;
import com.happyfresh.utils.DialogUtils;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

public class CheckoutPaymentFragment
  extends BaseFragment
{
  private static final String CHECKOUT_PAYMENT_FRAGMENT_TAG = String.format("%s_TAG", new Object[] { CheckoutPaymentFragment.class.getSimpleName() });
  private static final String TAG = CheckoutPaymentFragment.class.getSimpleName();
  @InjectView(2131558670)
  CircularProgressBar buttonProgressBar;
  @InjectView(2131558678)
  ImageView circleButtonCc;
  @InjectView(2131558675)
  ImageView circleButtonCod;
  @InjectView(2131558677)
  ImageView iconCc;
  @InjectView(2131558674)
  ImageView iconCod;
  protected boolean mPayment;
  private PaymentMethodResponse mPaymentMethodResponse;
  private String mPaymentMethodSelected;
  private PaymentService mPaymentService;
  private WebPaymentFragment mWebPaymentFragment;
  @InjectView(2131558676)
  SquareRelativeLayout optionCc;
  @InjectView(2131558673)
  SquareRelativeLayout optionCod;
  @InjectView(2131558679)
  View paymentCcInfo;
  @InjectView(2131558684)
  ImageView paymentCcInfoArrow;
  @InjectView(2131558680)
  TextView paymentCcInfoText1;
  @InjectView(2131558681)
  TextView paymentCcInfoText2;
  @InjectView(2131558682)
  TextView paymentCcInfoText3;
  @InjectView(2131558683)
  TextView paymentCcInfoText4;
  @InjectView(2131558685)
  FrameLayout paymentContainer;
  @InjectView(2131558665)
  RelativeLayout paymentFooter;
  @InjectView(2131558671)
  View paymentOptionsContainer;
  @InjectView(2131558686)
  CircularProgressBar paymentProgressBar;
  @InjectView(2131558668)
  TextView totalFee;
  
  private WebPaymentFragment createPaymentFragment(PaymentService paramPaymentService)
  {
    Object localObject = null;
    switch (paramPaymentService)
    {
    default: 
      paramPaymentService = (PaymentService)localObject;
    }
    for (;;)
    {
      paramPaymentService.setTargetFragment(this, 1000);
      return paramPaymentService;
      paramPaymentService = new AsiaPayFragment();
      continue;
      paramPaymentService = new AdyenFragment();
    }
  }
  
  private void getAvailablePayments()
  {
    this.paymentOptionsContainer.setVisibility(8);
    this.paymentFooter.setVisibility(8);
    this.paymentProgressBar.setVisibility(0);
    if (!getApplication().getOrderManager().getAvailablePayments(new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (CheckoutPaymentFragment.this.getActivity() == null) {
          return;
        }
        CheckoutPaymentFragment.this.paymentProgressBar.setVisibility(8);
      }
      
      public void onSuccess(PaymentMethodResponse paramAnonymousPaymentMethodResponse)
      {
        CheckoutPaymentFragment.access$002(CheckoutPaymentFragment.this, paramAnonymousPaymentMethodResponse);
        if (CheckoutPaymentFragment.this.getActivity() == null) {
          return;
        }
        CheckoutPaymentFragment.this.updateView(paramAnonymousPaymentMethodResponse);
      }
    }))
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(getString(2131165618));
      DialogUtils.showDialog(getActivity(), getString(2131165501), localStringBuffer.toString(), getString(2131165250), null, null, null);
      this.paymentProgressBar.setVisibility(8);
    }
  }
  
  public static CheckoutPaymentFragment newInstance(PaymentService paramPaymentService)
  {
    CheckoutPaymentFragment localCheckoutPaymentFragment = new CheckoutPaymentFragment();
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("SERVICE", paramPaymentService);
    localCheckoutPaymentFragment.setArguments(localBundle);
    return localCheckoutPaymentFragment;
  }
  
  private void preparePaymentFragment()
  {
    this.mWebPaymentFragment = ((WebPaymentFragment)getFragmentManager().findFragmentByTag(CHECKOUT_PAYMENT_FRAGMENT_TAG));
    if (this.mWebPaymentFragment == null) {
      this.mWebPaymentFragment = createPaymentFragment(this.mPaymentService);
    }
    getFragmentManager().beginTransaction().replace(2131558685, this.mWebPaymentFragment, CHECKOUT_PAYMENT_FRAGMENT_TAG).commit();
  }
  
  private void setTotalPriceText()
  {
    if (getApplication().currentOrder == null) {
      return;
    }
    NumberFormat localNumberFormat = getApplication().getNumberFormatter();
    double d = getApplication().currentOrder.total.doubleValue();
    this.totalFee.setText(localNumberFormat.format(d));
  }
  
  private void setViewEnabled(SquareRelativeLayout paramSquareRelativeLayout, ImageView paramImageView, boolean paramBoolean)
  {
    paramImageView.setAlpha(1.0F);
    paramSquareRelativeLayout.setClickable(true);
    paramSquareRelativeLayout.setFocusable(true);
    if (!paramBoolean)
    {
      paramImageView.setAlpha(0.25F);
      paramSquareRelativeLayout.setClickable(false);
      paramSquareRelativeLayout.setFocusable(false);
    }
  }
  
  private void updateView(PaymentMethodResponse paramPaymentMethodResponse)
  {
    Object localObject2 = null;
    Object localObject1 = null;
    Iterator localIterator = paramPaymentMethodResponse.paymentMethods.iterator();
    Object localObject3;
    while (localIterator.hasNext())
    {
      localObject3 = (PaymentMethod)localIterator.next();
      if (((PaymentMethod)localObject3).isCreditCard()) {
        localObject2 = localObject3;
      } else if (((PaymentMethod)localObject3).isCod()) {
        localObject1 = localObject3;
      }
    }
    if (localObject2 != null)
    {
      localObject3 = ((PaymentMethod)localObject2).information;
      this.paymentCcInfoText1.setText(((PaymentMethodInformation)localObject3).title1);
      this.paymentCcInfoText2.setText(((PaymentMethodInformation)localObject3).content1);
      this.paymentCcInfoText3.setText(((PaymentMethodInformation)localObject3).title2);
      this.paymentCcInfoText4.setText(((PaymentMethodInformation)localObject3).content2);
    }
    if ((PaymentMethod.isCod(paramPaymentMethodResponse.defaultPaymentMethod)) && (localObject1 != null) && (((PaymentMethod)localObject1).enabled.booleanValue())) {
      onCodSelected();
    }
    for (;;)
    {
      setViewEnabled(this.optionCod, this.iconCod, ((PaymentMethod)localObject1).enabled.booleanValue());
      setViewEnabled(this.optionCc, this.iconCc, ((PaymentMethod)localObject2).enabled.booleanValue());
      this.paymentOptionsContainer.setVisibility(0);
      this.paymentFooter.setVisibility(0);
      this.paymentProgressBar.setVisibility(8);
      return;
      if ((PaymentMethod.isCreditCard(paramPaymentMethodResponse.defaultPaymentMethod)) && (localObject2 != null) && (((PaymentMethod)localObject2).enabled.booleanValue())) {
        onCreditCardSelected();
      }
    }
  }
  
  protected String getScreenName()
  {
    return null;
  }
  
  public boolean isPayment()
  {
    return this.mPayment;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mPaymentService = ((PaymentService)getArguments().getSerializable("SERVICE"));
    preparePaymentFragment();
  }
  
  @OnClick({2131558673})
  void onCodSelected()
  {
    if (this.mPaymentMethodSelected == "cod") {
      return;
    }
    this.mPaymentMethodSelected = "cod";
    this.circleButtonCod.setImageDrawable(getResources().getDrawable(2130837631));
    this.circleButtonCc.setImageDrawable(getResources().getDrawable(2130837630));
    this.optionCod.setBackground(getResources().getDrawable(2130837617));
    this.optionCc.setBackground(getResources().getDrawable(2130837616));
    this.paymentCcInfo.setVisibility(8);
    this.paymentCcInfoArrow.setVisibility(8);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903115, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.buttonProgressBar.setVisibility(8);
    this.paymentOptionsContainer.setVisibility(8);
    this.paymentFooter.setVisibility(8);
    return paramLayoutInflater;
  }
  
  @OnClick({2131558676})
  void onCreditCardSelected()
  {
    if (this.mPaymentMethodSelected == "credit_card") {
      return;
    }
    this.mPaymentMethodSelected = "credit_card";
    this.circleButtonCc.setImageDrawable(getResources().getDrawable(2130837631));
    this.circleButtonCod.setImageDrawable(getResources().getDrawable(2130837630));
    this.optionCc.setBackground(getResources().getDrawable(2130837617));
    this.optionCod.setBackground(getResources().getDrawable(2130837616));
    this.paymentCcInfo.setVisibility(0);
    this.paymentCcInfoArrow.setVisibility(0);
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  @OnClick({2131558669})
  void onSubmit()
  {
    Order localOrder = getApplication().currentOrder;
    AdyenPayload localAdyenPayload = new AdyenPayload();
    localAdyenPayload.orderToken = localOrder.token;
    localAdyenPayload.orderNumber = localOrder.number;
    localAdyenPayload.paymentMethod = this.mPaymentMethodSelected;
    processToPayment(this.mPaymentService, localAdyenPayload);
    setPaymentWebViewVisibility(true);
  }
  
  public void processToPayment(PaymentService paramPaymentService, PaymentPayload paramPaymentPayload)
  {
    switch (paramPaymentService)
    {
    default: 
      return;
    case ???: 
      this.mWebPaymentFragment.startPayment(paramPaymentPayload);
      return;
    }
    this.mWebPaymentFragment.startPayment(paramPaymentPayload);
  }
  
  public void setPaymentWebViewVisibility(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.paymentContainer.setVisibility(0);
      this.paymentFooter.setVisibility(8);
    }
    for (;;)
    {
      this.mPayment = paramBoolean;
      return;
      this.paymentContainer.setVisibility(8);
      this.paymentFooter.setVisibility(0);
    }
  }
  
  public void updateView()
  {
    setTotalPriceText();
    getAvailablePayments();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CheckoutPaymentFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
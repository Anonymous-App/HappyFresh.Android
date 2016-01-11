package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PaymentMethodResponse
{
  @SerializedName("default_payment_method")
  public String defaultPaymentMethod;
  @SerializedName("payment_methods")
  public List<PaymentMethod> paymentMethods;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/PaymentMethodResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
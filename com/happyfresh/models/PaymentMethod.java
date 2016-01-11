package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;

public class PaymentMethod
{
  public Boolean enabled;
  @SerializedName("information_strings")
  public PaymentMethodInformation information;
  public String label;
  public String type;
  
  public static boolean isCod(String paramString)
  {
    return "cod".equalsIgnoreCase(paramString);
  }
  
  public static boolean isCreditCard(String paramString)
  {
    return "credit_card".equalsIgnoreCase(paramString);
  }
  
  public boolean isCod()
  {
    return isCod(this.type);
  }
  
  public boolean isCreditCard()
  {
    return isCreditCard(this.type);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/PaymentMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
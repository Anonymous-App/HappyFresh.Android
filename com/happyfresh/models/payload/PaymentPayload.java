package com.happyfresh.models.payload;

import android.os.Parcelable;
import com.happyfresh.models.PaymentService;

public abstract class PaymentPayload
  implements Parcelable
{
  public abstract PaymentService getType();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/payload/PaymentPayload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
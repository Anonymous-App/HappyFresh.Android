package com.ad4screen.sdk.analytics;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.persistence.d;
import com.ad4screen.sdk.common.persistence.e;
import org.json.JSONException;
import org.json.JSONObject;

@API
public class Cart
  implements Parcelable, d, Cloneable
{
  public static final Parcelable.Creator<Cart> CREATOR = new Parcelable.Creator()
  {
    public Cart a(Parcel paramAnonymousParcel)
    {
      return new Cart(paramAnonymousParcel, null);
    }
    
    public Cart[] a(int paramAnonymousInt)
    {
      return new Cart[paramAnonymousInt];
    }
  };
  public static final String KEY_ID = "cartId";
  public static final String KEY_UNIT_PRICE = "unitPrice";
  private String a;
  private Item b;
  
  private Cart(Parcel paramParcel)
  {
    this.a = paramParcel.readString();
    this.b = ((Item)paramParcel.readParcelable(getClass().getClassLoader()));
  }
  
  public Cart(String paramString, Item paramItem)
  {
    setId(paramString);
    setItem(paramItem);
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    Cart localCart = (Cart)super.clone();
    localCart.b = ((Item)this.b.clone());
    return localCart;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getId()
  {
    return this.a;
  }
  
  public Item getItem()
  {
    return this.b;
  }
  
  public void setId(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Cart id cannot be null");
    }
    this.a = paramString;
  }
  
  public void setItem(Item paramItem)
  {
    if (paramItem == null) {
      throw new IllegalArgumentException("Cart Item cannot be null");
    }
    this.b = paramItem;
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = new e().a(this.b);
    localJSONObject.put("cartId", this.a);
    localJSONObject.remove("price");
    localJSONObject.put("unitPrice", this.b.getPrice());
    return localJSONObject;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.a);
    paramParcel.writeParcelable(this.b, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/analytics/Cart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
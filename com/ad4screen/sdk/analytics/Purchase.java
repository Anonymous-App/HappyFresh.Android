package com.ad4screen.sdk.analytics;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.persistence.d;
import com.ad4screen.sdk.common.persistence.e;
import java.util.Currency;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@API
public class Purchase
  implements Parcelable, d, Cloneable
{
  public static final Parcelable.Creator<Purchase> CREATOR = new Parcelable.Creator()
  {
    public Purchase a(Parcel paramAnonymousParcel)
    {
      return new Purchase(paramAnonymousParcel, null);
    }
    
    public Purchase[] a(int paramAnonymousInt)
    {
      return new Purchase[paramAnonymousInt];
    }
  };
  public static final String KEY_CURRENCY = "currency";
  public static final String KEY_ID = "purchaseId";
  public static final String KEY_ITEMS = "items";
  public static final String KEY_TOTAL_PRICE = "total";
  private String a;
  private String b;
  private double c;
  private Item[] d;
  
  private Purchase(Parcel paramParcel)
  {
    this.a = paramParcel.readString();
    this.b = paramParcel.readString();
    Object[] arrayOfObject = paramParcel.readArray(getClass().getClassLoader());
    if (arrayOfObject != null)
    {
      this.d = new Item[arrayOfObject.length];
      System.arraycopy(arrayOfObject, 0, this.d, 0, arrayOfObject.length);
    }
    this.c = paramParcel.readDouble();
  }
  
  public Purchase(String paramString1, String paramString2, double paramDouble)
    throws IllegalArgumentException
  {
    setId(paramString1);
    setCurrency(paramString2);
    setTotalPrice(paramDouble);
  }
  
  public Purchase(String paramString1, String paramString2, double paramDouble, Item[] paramArrayOfItem)
  {
    setId(paramString1);
    setCurrency(paramString2);
    setTotalPrice(paramDouble);
    setItems(paramArrayOfItem);
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    Purchase localPurchase = (Purchase)super.clone();
    if (this.d != null)
    {
      Item[] arrayOfItem = new Item[this.d.length];
      int i = 0;
      while (i < this.d.length)
      {
        arrayOfItem[i] = ((Item)this.d[i].clone());
        i += 1;
      }
      localPurchase.d = arrayOfItem;
    }
    return localPurchase;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getCurrency()
  {
    return this.b;
  }
  
  public String getId()
  {
    return this.a;
  }
  
  public Item[] getItems()
  {
    return this.d;
  }
  
  public double getTotalPrice()
  {
    return this.c;
  }
  
  public void setCurrency(String paramString)
    throws IllegalArgumentException
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Purchase currency cannot be null and should be in ISO4217 format (3 letters)");
    }
    if (paramString.length() < 3) {
      throw new IllegalArgumentException("Purchase currency should be in ISO4217 format (3 letters)");
    }
    paramString = Currency.getInstance(paramString.substring(0, 3).toUpperCase(Locale.US)).getCurrencyCode();
    if (paramString.equals("XXX")) {
      throw new IllegalArgumentException("Purchase currency can't be XXX (no currency)");
    }
    this.b = paramString;
  }
  
  public void setId(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Purchase id cannot be null");
    }
    this.a = paramString;
  }
  
  public void setItems(Item[] paramArrayOfItem)
  {
    this.d = paramArrayOfItem;
  }
  
  public void setTotalPrice(double paramDouble)
  {
    this.c = paramDouble;
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    localJSONObject1.put("currency", this.b);
    if (this.d != null)
    {
      JSONArray localJSONArray = new JSONArray();
      e locale = new e();
      int i = 0;
      while (i < this.d.length)
      {
        JSONObject localJSONObject2 = locale.a(this.d[i]);
        localJSONObject2.remove("articleId");
        localJSONObject2.put("id", this.d[i].getId());
        localJSONArray.put(localJSONObject2);
        i += 1;
      }
      localJSONObject1.put("items", localJSONArray);
    }
    localJSONObject1.put("total", this.c);
    localJSONObject1.put("purchaseId", this.a);
    return localJSONObject1;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.a);
    paramParcel.writeString(this.b);
    paramParcel.writeArray(this.d);
    paramParcel.writeDouble(this.c);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/analytics/Purchase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
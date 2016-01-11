package com.ad4screen.sdk.analytics;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.persistence.d;
import java.util.Currency;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

@API
public class Item
  implements Parcelable, d, Cloneable
{
  public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator()
  {
    public Item a(Parcel paramAnonymousParcel)
    {
      return new Item(paramAnonymousParcel, null);
    }
    
    public Item[] a(int paramAnonymousInt)
    {
      return new Item[paramAnonymousInt];
    }
  };
  public static final String KEY_CATEGORY = "category";
  public static final String KEY_CURRENCY = "currency";
  public static final String KEY_ID = "articleId";
  public static final String KEY_LABEL = "label";
  public static final String KEY_PRICE = "price";
  public static final String KEY_QUANTITY = "quantity";
  private String a;
  private String b;
  private String c;
  private String d;
  private double e;
  private int f;
  
  private Item(Parcel paramParcel)
  {
    this.a = paramParcel.readString();
    this.c = paramParcel.readString();
    this.d = paramParcel.readString();
    this.b = paramParcel.readString();
    this.e = paramParcel.readDouble();
    this.f = paramParcel.readInt();
  }
  
  public Item(String paramString1, String paramString2, String paramString3, String paramString4, double paramDouble, int paramInt)
    throws IllegalArgumentException
  {
    setId(paramString1);
    setLabel(paramString2);
    setCategory(paramString3);
    setCurrency(paramString4);
    setPrice(paramDouble);
    setQuantity(paramInt);
  }
  
  protected Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getCategory()
  {
    return this.d;
  }
  
  public String getCurrency()
  {
    return this.b;
  }
  
  public String getId()
  {
    return this.a;
  }
  
  public String getLabel()
  {
    return this.c;
  }
  
  public double getPrice()
  {
    return this.e;
  }
  
  public int getQuantity()
  {
    return this.f;
  }
  
  public void setCategory(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Item category cannot be null");
    }
    this.d = paramString;
  }
  
  public void setCurrency(String paramString)
    throws IllegalArgumentException
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Item currency cannot be null and should be in ISO4217 format (3 letters)");
    }
    if (paramString.length() < 3) {
      throw new IllegalArgumentException("Item currency should be in ISO4217 format (3 letters)");
    }
    paramString = Currency.getInstance(paramString.substring(0, 3).toUpperCase(Locale.US)).getCurrencyCode();
    if (paramString.equals("XXX")) {
      throw new IllegalArgumentException("Item currency can't be XXX (no currency)");
    }
    this.b = paramString;
  }
  
  public void setId(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Item id cannot be null");
    }
    this.a = paramString;
  }
  
  public void setLabel(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Item label cannot be null");
    }
    this.c = paramString;
  }
  
  public void setPrice(double paramDouble)
  {
    this.e = paramDouble;
  }
  
  public void setQuantity(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Item quantity must be positive");
    }
    this.f = paramInt;
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("articleId", this.a);
    localJSONObject.put("label", this.c);
    localJSONObject.put("category", this.d);
    localJSONObject.put("currency", this.b);
    localJSONObject.put("price", this.e);
    localJSONObject.put("quantity", this.f);
    return localJSONObject;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.a);
    paramParcel.writeString(this.c);
    paramParcel.writeString(this.d);
    paramParcel.writeString(this.b);
    paramParcel.writeDouble(this.e);
    paramParcel.writeInt(this.f);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/analytics/Item.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
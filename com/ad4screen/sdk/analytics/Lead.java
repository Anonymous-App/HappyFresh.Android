package com.ad4screen.sdk.analytics;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.persistence.d;
import org.json.JSONException;
import org.json.JSONObject;

@API
public class Lead
  implements Parcelable, d, Cloneable
{
  public static final Parcelable.Creator<Lead> CREATOR = new Parcelable.Creator()
  {
    public Lead a(Parcel paramAnonymousParcel)
    {
      return new Lead(paramAnonymousParcel, null);
    }
    
    public Lead[] a(int paramAnonymousInt)
    {
      return new Lead[paramAnonymousInt];
    }
  };
  public static final String KEY_LABEL = "label";
  public static final String KEY_VALUE = "value";
  private String a;
  private String b;
  
  private Lead(Parcel paramParcel)
  {
    this.a = paramParcel.readString();
    this.b = paramParcel.readString();
  }
  
  public Lead(String paramString1, String paramString2)
  {
    setValue(paramString2);
    setLabel(paramString1);
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getLabel()
  {
    return this.a;
  }
  
  public String getValue()
  {
    return this.b;
  }
  
  public void setLabel(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Lead label cannot be null");
    }
    this.a = paramString;
  }
  
  public void setValue(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Lead value cannot be null");
    }
    this.b = paramString;
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("label", this.a);
    localJSONObject.put("value", this.b);
    return localJSONObject;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.a);
    paramParcel.writeString(this.b);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/analytics/Lead.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
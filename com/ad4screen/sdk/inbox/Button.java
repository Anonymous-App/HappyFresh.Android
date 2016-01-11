package com.ad4screen.sdk.inbox;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Button
  implements Parcelable
{
  public static final Parcelable.Creator<Button> CREATOR = new Parcelable.Creator()
  {
    public Button a(Parcel paramAnonymousParcel)
    {
      return new Button(paramAnonymousParcel, null);
    }
    
    public Button[] a(int paramAnonymousInt)
    {
      return new Button[paramAnonymousInt];
    }
  };
  public String a;
  public String b;
  public Message.a c;
  public String d;
  public String e;
  public HashMap<String, String> f;
  
  public Button() {}
  
  private Button(Parcel paramParcel)
  {
    this.a = paramParcel.readString();
    this.b = paramParcel.readString();
    this.c = Message.a.valueOf(paramParcel.readString());
    this.d = paramParcel.readString();
    this.e = paramParcel.readString();
    this.f = new HashMap();
    int j = paramParcel.readInt();
    int i = 0;
    while (i < j)
    {
      this.f.put(paramParcel.readString(), paramParcel.readString());
      i += 1;
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.a);
    paramParcel.writeString(this.b);
    paramParcel.writeString(this.c.name());
    paramParcel.writeString(this.d);
    paramParcel.writeString(this.e);
    paramParcel.writeInt(this.f.size());
    Iterator localIterator = this.f.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      paramParcel.writeString(str);
      paramParcel.writeString((String)this.f.get(str));
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/inbox/Button.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
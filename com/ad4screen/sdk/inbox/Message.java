package com.ad4screen.sdk.inbox;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Message
  implements Parcelable
{
  public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator()
  {
    public Message a(Parcel paramAnonymousParcel)
    {
      return new Message(paramAnonymousParcel, null);
    }
    
    public Message[] a(int paramAnonymousInt)
    {
      return new Message[paramAnonymousInt];
    }
  };
  public String a;
  public String b;
  public Date c;
  public String d;
  public String e;
  public String f;
  public String g;
  public String h;
  public String i;
  public a j;
  public boolean k;
  public boolean l;
  public boolean m;
  public boolean n;
  public boolean o;
  public String p;
  public Button[] q = new Button[0];
  public HashMap<String, String> r;
  
  public Message() {}
  
  private Message(Parcel paramParcel)
  {
    this.a = paramParcel.readString();
    this.b = paramParcel.readString();
    this.c = new Date(Long.valueOf(paramParcel.readLong()).longValue());
    this.d = paramParcel.readString();
    this.e = paramParcel.readString();
    this.f = paramParcel.readString();
    this.g = paramParcel.readString();
    this.h = paramParcel.readString();
    this.j = a.valueOf(paramParcel.readString());
    this.i = paramParcel.readString();
    this.p = paramParcel.readString();
    Object localObject = new boolean[5];
    paramParcel.readBooleanArray((boolean[])localObject);
    this.n = localObject[0];
    this.k = localObject[1];
    this.l = localObject[2];
    this.m = localObject[3];
    this.o = localObject[4];
    localObject = paramParcel.readArray(getClass().getClassLoader());
    if (localObject != null)
    {
      this.q = new Button[localObject.length];
      System.arraycopy(localObject, 0, this.q, 0, localObject.length);
    }
    for (;;)
    {
      this.r = new HashMap();
      int i2 = paramParcel.readInt();
      while (i1 < i2)
      {
        this.r.put(paramParcel.readString(), paramParcel.readString());
        i1 += 1;
      }
      this.q = new Button[0];
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
    paramParcel.writeLong(this.c.getTime());
    paramParcel.writeString(this.d);
    paramParcel.writeString(this.e);
    paramParcel.writeString(this.f);
    paramParcel.writeString(this.g);
    paramParcel.writeString(this.h);
    paramParcel.writeString(this.j.name());
    paramParcel.writeString(this.i);
    paramParcel.writeString(this.p);
    paramParcel.writeBooleanArray(new boolean[] { this.n, this.k, this.l, this.m, this.o });
    paramParcel.writeArray(this.q);
    if (this.r != null)
    {
      paramParcel.writeInt(this.r.size());
      Iterator localIterator = this.r.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        paramParcel.writeString(str);
        paramParcel.writeString((String)this.r.get(str));
      }
    }
    paramParcel.writeInt(0);
  }
  
  public static enum a
  {
    private a() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/inbox/Message.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
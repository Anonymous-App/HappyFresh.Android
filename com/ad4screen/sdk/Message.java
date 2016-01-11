package com.ad4screen.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.i;
import java.util.Date;

@API
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
  protected String a;
  private String b;
  private Date c;
  private String d;
  private String e;
  private String f;
  private String g;
  private MessageContentType h;
  private boolean i;
  private boolean j;
  private boolean k;
  private boolean l;
  private String m;
  private Button[] n = new Button[0];
  
  private Message(Parcel paramParcel)
  {
    this.a = paramParcel.readString();
    this.b = paramParcel.readString();
    this.c = new Date(paramParcel.readLong());
    this.d = paramParcel.readString();
    this.e = paramParcel.readString();
    this.f = paramParcel.readString();
    this.g = paramParcel.readString();
    this.h = MessageContentType.valueOf(paramParcel.readString());
    this.m = paramParcel.readString();
    boolean[] arrayOfBoolean = new boolean[4];
    paramParcel.readBooleanArray(arrayOfBoolean);
    this.i = arrayOfBoolean[0];
    this.j = arrayOfBoolean[1];
    this.k = arrayOfBoolean[2];
    this.l = arrayOfBoolean[3];
    paramParcel = paramParcel.readArray(getClass().getClassLoader());
    if (paramParcel != null)
    {
      this.n = new Button[paramParcel.length];
      System.arraycopy(paramParcel, 0, this.n, 0, paramParcel.length);
    }
  }
  
  protected Message(String paramString1, String paramString2, Date paramDate, String paramString3, String paramString4, String paramString5, String paramString6, MessageContentType paramMessageContentType, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, String paramString7, Button[] paramArrayOfButton, boolean paramBoolean5)
  {
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramDate;
    this.d = paramString3;
    this.e = paramString4;
    this.f = paramString5;
    this.g = paramString6;
    this.h = paramMessageContentType;
    this.i = paramBoolean1;
    this.j = paramBoolean2;
    this.k = paramBoolean3;
    this.m = paramString7;
    this.n = paramArrayOfButton;
    this.l = paramBoolean5;
  }
  
  public int countButtons()
  {
    return this.n.length;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void display(Context paramContext, A4S.Callback<Message> paramCallback)
  {
    A4S.get(paramContext).a(this.a, paramCallback);
  }
  
  public String getBody()
  {
    return this.d;
  }
  
  public Button getButton(int paramInt)
  {
    return this.n[paramInt];
  }
  
  public String getCategory()
  {
    return this.f;
  }
  
  public MessageContentType getContentType()
  {
    return this.h;
  }
  
  public void getIcon(final onIconDownloadedListener paramonIconDownloadedListener)
  {
    i.a(this.m, new A4S.Callback()
    {
      public void a(Bitmap paramAnonymousBitmap)
      {
        if (paramonIconDownloadedListener != null) {
          paramonIconDownloadedListener.onIconDownloaded(paramAnonymousBitmap);
        }
      }
      
      public void onError(int paramAnonymousInt, String paramAnonymousString) {}
    }, false);
  }
  
  public Date getSendDate()
  {
    return this.c;
  }
  
  public String getSender()
  {
    return this.e;
  }
  
  public String getText()
  {
    return this.g;
  }
  
  public String getTitle()
  {
    return this.b;
  }
  
  public boolean isArchived()
  {
    return this.k;
  }
  
  public boolean isDownloaded()
  {
    return this.l;
  }
  
  public boolean isOutdated()
  {
    return this.i;
  }
  
  public boolean isRead()
  {
    return this.j;
  }
  
  public void setArchived(boolean paramBoolean)
  {
    this.k = paramBoolean;
  }
  
  public void setRead(boolean paramBoolean)
  {
    this.j = paramBoolean;
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
    paramParcel.writeString(this.h.name());
    paramParcel.writeString(this.m);
    paramParcel.writeBooleanArray(new boolean[] { this.i, this.j, this.k, this.l });
    paramParcel.writeArray(this.n);
  }
  
  @API
  public static class Button
    implements Parcelable
  {
    public static final Parcelable.Creator<Button> CREATOR = new Parcelable.Creator()
    {
      public Message.Button a(Parcel paramAnonymousParcel)
      {
        return new Message.Button(paramAnonymousParcel, null);
      }
      
      public Message.Button[] a(int paramAnonymousInt)
      {
        return new Message.Button[paramAnonymousInt];
      }
    };
    protected String a;
    private String b;
    
    private Button(Parcel paramParcel)
    {
      this.a = paramParcel.readString();
      this.b = paramParcel.readString();
    }
    
    protected Button(String paramString1, String paramString2)
    {
      this.a = paramString1;
      this.b = paramString2;
    }
    
    public void click(Context paramContext)
    {
      A4S.get(paramContext).a(this.a);
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public String getTitle()
    {
      return this.b;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeString(this.a);
      paramParcel.writeString(this.b);
    }
  }
  
  @API
  public static enum MessageContentType
  {
    static
    {
      System = new MessageContentType("System", 2);
      Event = new MessageContentType("Event", 3);
      Url = new MessageContentType("Url", 4);
    }
    
    private MessageContentType() {}
  }
  
  @API
  public static abstract interface onIconDownloadedListener
  {
    public abstract void onIconDownloaded(Bitmap paramBitmap);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/Message.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
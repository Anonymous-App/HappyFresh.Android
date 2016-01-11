package com.parse;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;

class NotificationCompat
{
  public static final int FLAG_HIGH_PRIORITY = 128;
  private static final NotificationCompatImpl IMPL = new NotificationCompatImplBase();
  public static final int PRIORITY_DEFAULT = 0;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      IMPL = new NotificationCompatPostJellyBean();
      return;
    }
  }
  
  public static class Builder
  {
    private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
    PendingIntent mContentIntent;
    CharSequence mContentText;
    CharSequence mContentTitle;
    Context mContext;
    Bitmap mLargeIcon;
    Notification mNotification = new Notification();
    int mPriority;
    Style mStyle;
    
    public Builder(Context paramContext)
    {
      this.mContext = paramContext;
      this.mNotification.when = System.currentTimeMillis();
      this.mNotification.audioStreamType = -1;
      this.mPriority = 0;
    }
    
    protected static CharSequence limitCharSequenceLength(CharSequence paramCharSequence)
    {
      if (paramCharSequence == null) {}
      while (paramCharSequence.length() <= 5120) {
        return paramCharSequence;
      }
      return paramCharSequence.subSequence(0, 5120);
    }
    
    private void setFlag(int paramInt, boolean paramBoolean)
    {
      if (paramBoolean)
      {
        localNotification = this.mNotification;
        localNotification.flags |= paramInt;
        return;
      }
      Notification localNotification = this.mNotification;
      localNotification.flags &= (paramInt ^ 0xFFFFFFFF);
    }
    
    public Notification build()
    {
      return NotificationCompat.IMPL.build(this);
    }
    
    @Deprecated
    public Notification getNotification()
    {
      return NotificationCompat.IMPL.build(this);
    }
    
    public Builder setAutoCancel(boolean paramBoolean)
    {
      setFlag(16, paramBoolean);
      return this;
    }
    
    public Builder setContentIntent(PendingIntent paramPendingIntent)
    {
      this.mContentIntent = paramPendingIntent;
      return this;
    }
    
    public Builder setContentText(CharSequence paramCharSequence)
    {
      this.mContentText = limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public Builder setContentTitle(CharSequence paramCharSequence)
    {
      this.mContentTitle = limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public Builder setDefaults(int paramInt)
    {
      this.mNotification.defaults = paramInt;
      if ((paramInt & 0x4) != 0)
      {
        Notification localNotification = this.mNotification;
        localNotification.flags |= 0x1;
      }
      return this;
    }
    
    public Builder setDeleteIntent(PendingIntent paramPendingIntent)
    {
      this.mNotification.deleteIntent = paramPendingIntent;
      return this;
    }
    
    public Builder setLargeIcon(Bitmap paramBitmap)
    {
      this.mLargeIcon = paramBitmap;
      return this;
    }
    
    public Builder setPriority(int paramInt)
    {
      this.mPriority = paramInt;
      return this;
    }
    
    public Builder setSmallIcon(int paramInt)
    {
      this.mNotification.icon = paramInt;
      return this;
    }
    
    public Builder setSmallIcon(int paramInt1, int paramInt2)
    {
      this.mNotification.icon = paramInt1;
      this.mNotification.iconLevel = paramInt2;
      return this;
    }
    
    public Builder setStyle(Style paramStyle)
    {
      if (this.mStyle != paramStyle)
      {
        this.mStyle = paramStyle;
        if (this.mStyle != null) {
          this.mStyle.setBuilder(this);
        }
      }
      return this;
    }
    
    public Builder setTicker(CharSequence paramCharSequence)
    {
      this.mNotification.tickerText = limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public Builder setWhen(long paramLong)
    {
      this.mNotification.when = paramLong;
      return this;
    }
    
    public static class BigTextStyle
      extends NotificationCompat.Builder.Style
    {
      CharSequence mBigText;
      
      public BigTextStyle() {}
      
      public BigTextStyle(NotificationCompat.Builder paramBuilder)
      {
        setBuilder(paramBuilder);
      }
      
      public BigTextStyle bigText(CharSequence paramCharSequence)
      {
        this.mBigText = paramCharSequence;
        return this;
      }
      
      public BigTextStyle setBigContentTitle(CharSequence paramCharSequence)
      {
        this.mBigContentTitle = paramCharSequence;
        return this;
      }
      
      public BigTextStyle setSummaryText(CharSequence paramCharSequence)
      {
        this.mSummaryText = paramCharSequence;
        this.mSummaryTextSet = true;
        return this;
      }
    }
    
    public static abstract class Style
    {
      CharSequence mBigContentTitle;
      NotificationCompat.Builder mBuilder;
      CharSequence mSummaryText;
      boolean mSummaryTextSet = false;
      
      public Notification build()
      {
        Notification localNotification = null;
        if (this.mBuilder != null) {
          localNotification = this.mBuilder.build();
        }
        return localNotification;
      }
      
      public void setBuilder(NotificationCompat.Builder paramBuilder)
      {
        if (this.mBuilder != paramBuilder)
        {
          this.mBuilder = paramBuilder;
          if (this.mBuilder != null) {
            this.mBuilder.setStyle(this);
          }
        }
      }
    }
  }
  
  static abstract interface NotificationCompatImpl
  {
    public abstract Notification build(NotificationCompat.Builder paramBuilder);
  }
  
  static class NotificationCompatImplBase
    implements NotificationCompat.NotificationCompatImpl
  {
    public Notification build(NotificationCompat.Builder paramBuilder)
    {
      Notification localNotification = paramBuilder.mNotification;
      localNotification.setLatestEventInfo(paramBuilder.mContext, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentIntent);
      if (paramBuilder.mPriority > 0) {
        localNotification.flags |= 0x80;
      }
      return localNotification;
    }
  }
  
  @TargetApi(16)
  static class NotificationCompatPostJellyBean
    implements NotificationCompat.NotificationCompatImpl
  {
    private Notification.Builder postJellyBeanBuilder;
    
    public Notification build(NotificationCompat.Builder paramBuilder)
    {
      this.postJellyBeanBuilder = new Notification.Builder(paramBuilder.mContext);
      Object localObject = this.postJellyBeanBuilder.setContentTitle(paramBuilder.mContentTitle).setContentText(paramBuilder.mContentText).setTicker(paramBuilder.mNotification.tickerText).setSmallIcon(paramBuilder.mNotification.icon, paramBuilder.mNotification.iconLevel).setContentIntent(paramBuilder.mContentIntent).setDeleteIntent(paramBuilder.mNotification.deleteIntent);
      if ((paramBuilder.mNotification.flags & 0x10) != 0) {}
      for (boolean bool = true;; bool = false)
      {
        ((Notification.Builder)localObject).setAutoCancel(bool).setLargeIcon(paramBuilder.mLargeIcon).setDefaults(paramBuilder.mNotification.defaults);
        if ((paramBuilder.mStyle != null) && ((paramBuilder.mStyle instanceof NotificationCompat.Builder.BigTextStyle)))
        {
          paramBuilder = (NotificationCompat.Builder.BigTextStyle)paramBuilder.mStyle;
          localObject = new Notification.BigTextStyle(this.postJellyBeanBuilder).setBigContentTitle(paramBuilder.mBigContentTitle).bigText(paramBuilder.mBigText);
          if (paramBuilder.mSummaryTextSet) {
            ((Notification.BigTextStyle)localObject).setSummaryText(paramBuilder.mSummaryText);
          }
        }
        return this.postJellyBeanBuilder.build();
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/NotificationCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
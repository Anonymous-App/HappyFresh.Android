package com.ad4screen.sdk.common.compatibility;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R.id;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.service.modules.push.f.a;
import com.ad4screen.sdk.service.modules.push.model.a;

@TargetApi(16)
public final class b
{
  public static Notification.Builder a(Notification.Builder paramBuilder, int paramInt, String paramString, PendingIntent paramPendingIntent)
  {
    paramBuilder.addAction(paramInt, paramString, paramPendingIntent);
    return paramBuilder;
  }
  
  public static Notification a(Notification.Builder paramBuilder, Bitmap paramBitmap, String paramString1, String paramString2)
  {
    if (paramBuilder != null)
    {
      Notification.BigPictureStyle localBigPictureStyle = new Notification.BigPictureStyle(paramBuilder);
      if (paramBitmap != null) {
        localBigPictureStyle.bigPicture(paramBitmap);
      }
      if (paramString2 != null) {
        localBigPictureStyle.setSummaryText(paramString2);
      }
      for (;;)
      {
        paramBuilder.setStyle(localBigPictureStyle);
        return paramBuilder.build();
        if (paramString1 != null) {
          localBigPictureStyle.setSummaryText(paramString1);
        }
      }
    }
    return null;
  }
  
  public static void a(Context paramContext, final String paramString1, final String paramString2, int paramInt1, boolean paramBoolean, int paramInt2, Bitmap paramBitmap, final a parama, Notification.Builder paramBuilder, final A4S.Callback<Notification> paramCallback)
  {
    if (!parama.g) {
      if (paramCallback != null) {
        paramCallback.onResult(paramBuilder.build());
      }
    }
    do
    {
      do
      {
        return;
        if ((parama.r != null) && (!parama.r.equals("BigTextStyle")) && (!parama.r.equals("InboxStyle"))) {
          break;
        }
        paramBuilder.setStyle(b(paramString2, parama.o, paramBuilder));
      } while (paramCallback == null);
      paramCallback.onResult(paramBuilder.build());
      return;
      if (!parama.r.equals("BigPictureStyle")) {
        break label180;
      }
      if (parama.p != null) {
        break;
      }
      paramBuilder.setStyle(b(paramString2, parama.o, paramBuilder));
    } while (paramCallback == null);
    paramCallback.onResult(paramBuilder.build());
    return;
    Log.debug("Push|Downloading picture...");
    i.a(parama.p, new A4S.Callback()
    {
      public void a(Bitmap paramAnonymousBitmap)
      {
        Log.debug("Push|Picture successfully downloaded");
        Log.debug("Push|Displaying notification with picture...");
        b.a(this.a, paramAnonymousBitmap, parama.n, parama.o);
        if (paramCallback != null) {
          paramCallback.onResult(this.a.build());
        }
      }
      
      public void onError(int paramAnonymousInt, String paramAnonymousString)
      {
        Log.warn("Push|Can't download provided picture.");
        Log.debug("Push|Displaying notification...");
        this.a.setStyle(b.a(paramString2, parama.o, this.a));
        if (paramCallback != null) {
          paramCallback.onResult(this.a.build());
        }
      }
    }, true);
    return;
    label180:
    String str = paramContext.getPackageName();
    f.a locala = com.ad4screen.sdk.service.modules.push.e.a(paramContext);
    int i = 0;
    if (locala == f.a.c) {
      i = paramContext.getResources().getIdentifier(parama.r + "_amazon", "layout", str);
    }
    if (i == 0) {
      i = paramContext.getResources().getIdentifier(parama.r, "layout", str);
    }
    for (;;)
    {
      if (i == 0)
      {
        Log.warn("Notification|Wrong big template provided : " + parama.r + " using default");
        paramBuilder.setStyle(b(paramString2, parama.o, paramBuilder));
        if (paramCallback == null) {
          break;
        }
        paramCallback.onResult(paramBuilder.build());
        return;
      }
      paramString2 = new RemoteViews(str, i);
      paramString2.setTextViewText(R.id.com_ad4screen_sdk_title, paramString1);
      paramString2.setTextViewText(R.id.com_ad4screen_sdk_body, parama.o);
      paramString2.setImageViewResource(R.id.com_ad4screen_sdk_logo, paramInt1);
      paramString2.setViewVisibility(R.id.com_ad4screen_sdk_picture, 8);
      if (paramBitmap != null) {
        paramString2.setImageViewBitmap(R.id.com_ad4screen_sdk_logo, paramBitmap);
      }
      for (;;)
      {
        paramString1 = paramBuilder.build();
        paramString1.bigContentView = paramString2;
        paramContext = ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(i, null);
        if ((paramContext == null) || (paramContext.findViewById(R.id.com_ad4screen_sdk_picture) == null)) {
          break label515;
        }
        if (parama.p != null) {
          break label489;
        }
        if (paramCallback == null) {
          break;
        }
        paramCallback.onResult(paramString1);
        return;
        if ((paramBoolean) && (Build.VERSION.SDK_INT >= 21)) {
          paramString2.setImageViewBitmap(R.id.com_ad4screen_sdk_logo, e.a(paramContext, paramInt1, paramInt2));
        }
      }
      label489:
      Log.debug("Push|Downloading picture...");
      i.a(parama.p, new A4S.Callback()
      {
        public void a(Bitmap paramAnonymousBitmap)
        {
          Log.debug("Push|Picture successfully downloaded");
          Log.debug("Push|Displaying notification with picture...");
          if (paramAnonymousBitmap != null)
          {
            this.a.setImageViewBitmap(R.id.com_ad4screen_sdk_picture, paramAnonymousBitmap);
            this.a.setViewVisibility(R.id.com_ad4screen_sdk_picture, 0);
            if (paramCallback != null) {
              paramCallback.onResult(paramString1);
            }
          }
        }
        
        public void onError(int paramAnonymousInt, String paramAnonymousString)
        {
          Log.warn("Push|Can't download provided picture.");
          Log.debug("Push|Displaying notification...");
          if (paramCallback != null) {
            paramCallback.onResult(paramString1);
          }
        }
      }, true);
      return;
      label515:
      if (paramCallback == null) {
        break;
      }
      paramCallback.onResult(paramString1);
      return;
    }
  }
  
  private static Notification.BigTextStyle b(String paramString1, String paramString2, Notification.Builder paramBuilder)
  {
    paramBuilder = new Notification.BigTextStyle(paramBuilder);
    if (paramString2 != null) {
      paramBuilder.bigText(paramString2);
    }
    if (paramString1 != null) {
      paramBuilder.setSummaryText(paramString1);
    }
    return paramBuilder;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/compatibility/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
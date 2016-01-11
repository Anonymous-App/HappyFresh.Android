package com.ad4screen.sdk.common.compatibility;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.HandlerThread;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R.id;
import com.ad4screen.sdk.model.displayformats.e.a;
import com.ad4screen.sdk.service.modules.push.d.b;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"InlinedApi"})
public final class k
{
  public static String a(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 7) {
      return h.a();
    }
    return f.c(paramContext);
  }
  
  public static void a(ViewGroup paramViewGroup, ArrayList<View> paramArrayList, int paramInt)
  {
    int i = 0;
    while (i < paramViewGroup.getChildCount())
    {
      View localView = paramViewGroup.getChildAt(i);
      if (localView.getId() == paramInt) {
        paramArrayList.add(localView);
      }
      if ((localView instanceof ViewGroup)) {
        a((ViewGroup)localView, paramArrayList, paramInt);
      }
      i += 1;
    }
  }
  
  public static final class a
  {
    public static void a(AlarmManager paramAlarmManager, int paramInt, long paramLong, PendingIntent paramPendingIntent)
    {
      if (Build.VERSION.SDK_INT >= 19)
      {
        d.a(paramAlarmManager, paramInt, paramLong, paramPendingIntent);
        return;
      }
      paramAlarmManager.set(paramInt, paramLong, paramPendingIntent);
    }
  }
  
  public static final class b
  {
    @SuppressLint({"NewApi"})
    public static boolean a(Context paramContext, View paramView, String paramString, a parama)
    {
      if ((Build.VERSION.SDK_INT >= 11) && (a.a(paramContext, paramView, paramString, new Animator.AnimatorListener()
      {
        public void onAnimationCancel(Animator paramAnonymousAnimator) {}
        
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          if (this.a != null) {
            this.a.b();
          }
        }
        
        public void onAnimationRepeat(Animator paramAnonymousAnimator) {}
        
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          if (this.a != null) {
            this.a.a();
          }
        }
      }))) {
        return true;
      }
      f.a(paramContext, paramView, paramString, new Animation.AnimationListener()
      {
        public void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          if (this.a != null) {
            this.a.b();
          }
        }
        
        public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
        
        public void onAnimationStart(Animation paramAnonymousAnimation)
        {
          if (this.a != null) {
            this.a.a();
          }
        }
      });
    }
    
    public static abstract interface a
    {
      public abstract void a();
      
      public abstract void b();
    }
  }
  
  public static final class c
  {
    public static String a(byte[] paramArrayOfByte, int paramInt)
    {
      if (Build.VERSION.SDK_INT >= 8) {
        return i.a(paramArrayOfByte, paramInt);
      }
      return f.a(paramArrayOfByte);
    }
    
    public static byte[] a(String paramString, int paramInt)
    {
      if (Build.VERSION.SDK_INT >= 8) {
        return i.a(paramString, paramInt);
      }
      return f.a(paramString, paramInt);
    }
  }
  
  public static final class d
  {
    public static Button a(Context paramContext)
    {
      if (Build.VERSION.SDK_INT >= 11) {}
      for (paramContext = new Button(paramContext, null, 16843567);; paramContext = new Button(paramContext))
      {
        paramContext.setMinLines(2);
        return paramContext;
      }
    }
  }
  
  public static final class e
  {
    public static File a(Context paramContext)
    {
      if (Build.VERSION.SDK_INT >= 21) {
        return b(paramContext);
      }
      if (Build.VERSION.SDK_INT >= 8) {
        return i.a(paramContext);
      }
      return f.b(paramContext);
    }
    
    public static File b(Context paramContext)
    {
      if (Build.VERSION.SDK_INT >= 21) {
        return e.a(paramContext);
      }
      return a(paramContext);
    }
  }
  
  public static final class f
  {
    public static void a(Activity paramActivity)
    {
      if (Build.VERSION.SDK_INT < 11) {
        f.a(paramActivity);
      }
    }
  }
  
  public static final class g
  {
    public static int a(Context paramContext)
    {
      return paramContext.getResources().getConfiguration().orientation;
    }
  }
  
  public static final class h
  {
    public static boolean a(HandlerThread paramHandlerThread)
    {
      if (Build.VERSION.SDK_INT >= 5) {
        return g.a(paramHandlerThread);
      }
      return f.a(paramHandlerThread);
    }
  }
  
  public static final class i
  {
    public static LinearLayout a(Context paramContext)
    {
      if (Build.VERSION.SDK_INT >= 11) {
        return a.a(paramContext, null, 16843566);
      }
      return f.a(paramContext);
    }
  }
  
  public static final class j
  {
    public static void a(Context paramContext, BroadcastReceiver paramBroadcastReceiver, IntentFilter paramIntentFilter)
    {
      try
      {
        Object localObject = Class.forName("android.support.v4.content.LocalBroadcastManager");
        paramContext = ((Class)localObject).getMethod("getInstance", new Class[] { Context.class }).invoke(localObject, new Object[] { paramContext });
        localObject = ((Class)localObject).getMethod("registerReceiver", new Class[] { BroadcastReceiver.class, IntentFilter.class });
        if (paramContext != null)
        {
          ((Method)localObject).invoke(paramContext, new Object[] { paramBroadcastReceiver, paramIntentFilter });
          Log.internal("LocalBroadcastManager|Receiver registered");
          return;
        }
        Log.internal("LocalBroadcastManager|Can't register receiver. Instance is null");
        return;
      }
      catch (Exception paramContext)
      {
        Log.internal("LocalBroadcastManager|Can't register receiver. Class not found?", paramContext);
      }
    }
  }
  
  public static final class k
  {
    public static void a(Context paramContext, final PendingIntent paramPendingIntent, final com.ad4screen.sdk.service.modules.push.model.a parama, final d.b paramb)
    {
      Log.debug("Push|Downloading large icon..");
      a(paramContext, parama.l, new A4S.Callback()
      {
        Notification.Builder a = null;
        Notification b = null;
        RemoteViews c;
        int d = com.ad4screen.sdk.common.i.d(this.g);
        String e = com.ad4screen.sdk.common.i.e(this.g);
        NotificationManager f = (NotificationManager)this.g.getSystemService("notification");
        
        private void a(Context paramAnonymousContext, com.ad4screen.sdk.service.modules.push.model.a paramAnonymousa)
        {
          Log.debug("Push|Adding action buttons to this notification...");
          try
          {
            localJSONArray = new JSONArray(paramAnonymousa.s);
            k = 0;
          }
          catch (JSONException paramAnonymousContext)
          {
            for (;;)
            {
              JSONArray localJSONArray;
              int k;
              com.ad4screen.sdk.service.modules.push.c localc;
              Intent localIntent;
              Bundle localBundle;
              Object localObject2;
              Log.error("Push|Can't parse notification buttons.", paramAnonymousContext);
              return;
              Object localObject1 = "true";
              continue;
              localIntent.putExtra("com.ad4screen.sdk.extra.GCM_PAYLOAD", localBundle);
              b.a(this.a, localc.a(paramAnonymousContext), localc.a(), PendingIntent.getActivity(paramAnonymousContext, paramAnonymousa.v + (k + 1), localIntent, 134217728));
              k += 1;
            }
          }
          catch (ClassNotFoundException paramAnonymousContext)
          {
            Log.error("Push|Can't find activity to launch.", paramAnonymousContext);
            return;
          }
          catch (URISyntaxException paramAnonymousContext)
          {
            label262:
            label269:
            Log.error("Push|Can't find activity to launch.", paramAnonymousContext);
          }
          if (k < localJSONArray.length())
          {
            localObject1 = localJSONArray.getJSONObject(k);
            localc = com.ad4screen.sdk.service.modules.push.c.a(paramAnonymousa.b, (JSONObject)localObject1);
            localIntent = com.ad4screen.sdk.service.modules.push.g.a(paramAnonymousContext);
            localBundle = new Bundle();
            localObject2 = (com.ad4screen.sdk.model.displayformats.e)localc.c();
            localBundle.putString("a4sid", ((com.ad4screen.sdk.model.displayformats.e)localObject2).i);
            localBundle.putString("a4ssysid", String.valueOf(paramAnonymousa.v));
            if (((com.ad4screen.sdk.model.displayformats.e)localObject2).b != e.a.a) {
              break label262;
            }
            localObject1 = "false";
            localBundle.putString("openWithSafari", (String)localObject1);
            localBundle.putString("a4surl", ((com.ad4screen.sdk.model.displayformats.e)localObject2).a.c);
            localBundle.putString("a4st", ((com.ad4screen.sdk.model.displayformats.e)localObject2).a.d);
            localBundle.putString("a4scontent", localc.a());
            localBundle.putBoolean("a4scancel", localc.d());
            localObject1 = localc.b();
            if (((HashMap)localObject1).isEmpty()) {
              break label269;
            }
            localObject1 = ((HashMap)localObject1).entrySet().iterator();
            while (((Iterator)localObject1).hasNext())
            {
              localObject2 = (Map.Entry)((Iterator)localObject1).next();
              localBundle.putString((String)((Map.Entry)localObject2).getKey(), (String)((Map.Entry)localObject2).getValue());
            }
          }
        }
        
        public void a(Bitmap paramAnonymousBitmap)
        {
          if ((Build.VERSION.SDK_INT >= 19) && (d.a(this.g, parama.k) > -1)) {
            this.d = d.a(this.g, parama.k);
          }
          for (boolean bool = true;; bool = false)
          {
            if ((parama.m != null) && (parama.m.length() > 0)) {
              this.e = parama.m;
            }
            this.c = k.k.a(this.g, this.d, parama.k, parama.q, this.e, parama.n, paramAnonymousBitmap);
            if (Build.VERSION.SDK_INT >= 11)
            {
              this.a = a.a(this.g, this.e, this.d, paramAnonymousBitmap, parama, paramPendingIntent);
              if (Build.VERSION.SDK_INT >= 21) {
                this.a = e.a(this.g, parama.h, parama.i, parama.j, bool, this.a);
              }
              if (this.c != null) {
                this.a = a.a(this.a, this.c);
              }
            }
            while (Build.VERSION.SDK_INT >= 16)
            {
              if (parama.s != null) {
                a(this.g, parama);
              }
              b.a(this.g, this.e, com.ad4screen.sdk.common.i.e(this.g), this.d, bool, parama.j, paramAnonymousBitmap, parama, this.a, new A4S.Callback()
              {
                public void a(Notification paramAnonymous2Notification)
                {
                  Log.debug("Push|Notification is ready, displaying...");
                  k.k.a(k.k.1.this.f, k.k.1.this.h.v, paramAnonymous2Notification, k.k.1.this.j);
                }
                
                public void onError(int paramAnonymous2Int, String paramAnonymous2String) {}
              });
              return;
              this.b = i.a(this.g, this.d, parama.n, paramPendingIntent);
              if (this.c != null) {
                this.b.contentView = this.c;
              }
            }
            Log.debug("Push|Notification is ready, displaying...");
            if (Build.VERSION.SDK_INT >= 11) {
              this.b = a.a(this.a);
            }
            k.k.a(this.f, parama.v, this.b, paramb);
            return;
          }
        }
        
        public void onError(int paramAnonymousInt, String paramAnonymousString) {}
      });
    }
    
    private static void a(Context paramContext, String paramString, A4S.Callback<Bitmap> paramCallback)
    {
      if (Build.VERSION.SDK_INT < 11)
      {
        Log.debug("Push|LargeIcon is not supported on API < 11.. No download needed");
        if (paramCallback != null) {
          paramCallback.onResult(null);
        }
      }
      do
      {
        return;
        if (paramString != null) {
          break;
        }
        Log.debug("Push|No icon to download..");
      } while (paramCallback == null);
      paramCallback.onResult(null);
      return;
      com.ad4screen.sdk.common.i.a(com.ad4screen.sdk.common.h.a(paramContext, paramString, new com.ad4screen.sdk.common.e[] { new com.ad4screen.sdk.common.e("iconsize", b(paramContext)) }), new A4S.Callback()
      {
        public void a(Bitmap paramAnonymousBitmap)
        {
          Log.debug("Push|Large Icon successfully downloaded");
          if (this.a != null) {
            this.a.onResult(paramAnonymousBitmap);
          }
        }
        
        public void onError(int paramAnonymousInt, String paramAnonymousString)
        {
          Log.warn("Push|Can't download provided large icon");
          if (this.a != null) {
            this.a.onResult(null);
          }
        }
      }, true);
    }
    
    public static boolean a(Context paramContext)
    {
      if (Build.VERSION.SDK_INT >= 19) {
        return d.a(paramContext);
      }
      if (Build.VERSION.SDK_INT >= 18) {
        return c.a(paramContext);
      }
      return false;
    }
    
    private static RemoteViews b(Context paramContext, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, Bitmap paramBitmap)
    {
      paramString1 = null;
      if (paramString2 == null) {
        paramContext = paramString1;
      }
      do
      {
        return paramContext;
        paramString1 = paramContext.getPackageName();
        int i = paramContext.getResources().getIdentifier(paramString2, "layout", paramString1);
        if (i == 0)
        {
          Log.warn("Notification|Wrong template provided : " + paramString2 + " using default");
          return null;
        }
        paramString1 = new RemoteViews(paramString1, i);
        paramString1.setTextViewText(R.id.com_ad4screen_sdk_title, paramString3);
        paramString1.setTextViewText(R.id.com_ad4screen_sdk_body, paramString4);
        if (Build.VERSION.SDK_INT < 16) {
          break;
        }
        paramString1.setImageViewResource(R.id.com_ad4screen_sdk_logo, paramInt);
        paramContext = paramString1;
      } while (paramBitmap == null);
      paramString1.setImageViewBitmap(R.id.com_ad4screen_sdk_logo, paramBitmap);
      return paramString1;
      paramString1.setViewVisibility(R.id.com_ad4screen_sdk_logo, 8);
      return paramString1;
    }
    
    private static String b(Context paramContext)
    {
      paramContext = com.ad4screen.sdk.systems.b.a(paramContext).v;
      switch (k.1.a[paramContext.ordinal()])
      {
      default: 
        return "32";
      case 1: 
        return "64";
      case 2: 
        return "96";
      case 3: 
        return "128";
      case 4: 
        return "192";
      }
      return "256";
    }
    
    private static void b(NotificationManager paramNotificationManager, int paramInt, Notification paramNotification, d.b paramb)
    {
      paramNotificationManager.notify(paramInt, paramNotification);
      if (paramb != null) {
        paramb.a();
      }
    }
  }
  
  public static final class l
  {
    public static long a(PackageInfo paramPackageInfo)
    {
      if (Build.VERSION.SDK_INT >= 9) {
        return j.a(paramPackageInfo);
      }
      return 0L;
    }
  }
  
  public static final class m
  {
    public static void a(Context paramContext)
    {
      if (Build.VERSION.SDK_INT >= 19) {
        d.a();
      }
    }
    
    public static void a(WebView paramWebView)
    {
      if (((paramWebView.getBackground() instanceof ColorDrawable)) && (Build.VERSION.SDK_INT >= 11)) {
        a.a(paramWebView);
      }
    }
    
    public static void a(WebView paramWebView, boolean paramBoolean)
    {
      if (Build.VERSION.SDK_INT >= 7) {
        h.a(paramWebView, paramBoolean);
      }
    }
    
    public static void b(WebView paramWebView, boolean paramBoolean)
    {
      if (Build.VERSION.SDK_INT >= 11) {
        a.a(paramWebView, paramBoolean);
      }
    }
    
    public static void c(WebView paramWebView, boolean paramBoolean)
    {
      if (Build.VERSION.SDK_INT >= 7)
      {
        h.b(paramWebView, paramBoolean);
        return;
      }
      paramWebView.setInitialScale(1);
    }
    
    public static void d(WebView paramWebView, boolean paramBoolean)
    {
      if (Build.VERSION.SDK_INT >= 18)
      {
        c.a(paramWebView, paramBoolean);
        return;
      }
      f.a(paramWebView, paramBoolean);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/compatibility/k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
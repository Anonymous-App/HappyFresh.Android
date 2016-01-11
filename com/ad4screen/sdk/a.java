package com.ad4screen.sdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.client.a.a;
import com.ad4screen.sdk.client.c.a;
import com.ad4screen.sdk.client.c.b;
import com.ad4screen.sdk.common.c;
import com.ad4screen.sdk.common.compatibility.k.c;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.inbox.Button;
import com.ad4screen.sdk.inbox.Message.a;
import com.ad4screen.sdk.plugins.model.Beacon;
import com.ad4screen.sdk.service.modules.inapp.c.c;
import com.ad4screen.sdk.service.modules.inapp.c.d;
import com.ad4screen.sdk.service.modules.inapp.c.f;
import com.ad4screen.sdk.service.modules.inapp.c.g;
import com.ad4screen.sdk.service.modules.inapp.c.j;
import com.ad4screen.sdk.service.modules.inapp.c.k;
import com.ad4screen.sdk.service.modules.inapp.c.m;
import com.ad4screen.sdk.service.modules.inapp.c.n;
import com.ad4screen.sdk.systems.k;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONException;
import org.json.JSONObject;

final class a
  extends A4S
{
  private static com.ad4screen.sdk.inbox.a p;
  private static boolean q = false;
  private static ArrayList<A4S.Callback<Inbox>> r;
  private static ArrayList<String> s;
  private static ArrayList<A4S.Callback<Inbox>> t;
  private static ArrayList<String> u;
  private static boolean v = false;
  private c.k A = new c.k()
  {
    public void a(String paramAnonymousString1, int paramAnonymousInt, String paramAnonymousString2, boolean paramAnonymousBoolean)
    {
      a.a(a.this, paramAnonymousString1, paramAnonymousBoolean, paramAnonymousInt, paramAnonymousString2);
    }
  };
  A4S.Callback<InApp> a;
  A4S.Callback<InApp> b;
  A4S.Callback<InApp> c;
  A4S.Callback<InApp> d;
  int[] e;
  int[] f;
  int[] g;
  int[] h;
  c.a i = new c.a()
  {
    public void a(final Bundle paramAnonymousBundle)
    {
      a.b(a.this).a(new a.a("openedPush")
      {
        public void a(IA4SService paramAnonymous2IA4SService)
          throws RemoteException
        {
          paramAnonymous2IA4SService.openedPush(paramAnonymousBundle);
        }
      });
    }
  };
  private final Context j;
  private final com.ad4screen.sdk.client.b k;
  private final com.ad4screen.sdk.client.a<IA4SService> l;
  private final Handler m;
  private String n;
  private String o;
  private String w;
  private c.n x = new c.n()
  {
    public void a(String paramAnonymousString1, int paramAnonymousInt, String paramAnonymousString2)
    {
      a.a(a.this, paramAnonymousString1, paramAnonymousInt, paramAnonymousString2);
    }
  };
  private c.m y = new c.m()
  {
    public void a(String paramAnonymousString1, int paramAnonymousInt, String paramAnonymousString2, HashMap<String, String> paramAnonymousHashMap)
    {
      a.a(a.this, paramAnonymousString1, paramAnonymousInt, paramAnonymousString2, paramAnonymousHashMap);
    }
  };
  private c.j z = new c.j()
  {
    public void a(String paramAnonymousString1, int paramAnonymousInt, String paramAnonymousString2, String paramAnonymousString3, HashMap<String, String> paramAnonymousHashMap)
    {
      a.a(a.this, paramAnonymousString1, paramAnonymousString3, paramAnonymousInt, paramAnonymousString2, paramAnonymousHashMap);
    }
  };
  
  protected a(Context paramContext, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.j = paramContext.getApplicationContext();
      this.m = new Handler(Looper.getMainLooper());
      paramContext = com.ad4screen.sdk.systems.b.a(this.j);
      Log.setEnabled(paramContext.y);
      if (paramContext.B)
      {
        Log.error("**************************************/!\\**************************************");
        Log.error("/!\\ Unsecure push is Enabled and must be DISABLED in production environment /!\\");
        Log.error("**************************************/!\\**************************************");
        Toast.makeText(this.j, "Unsecure Push is ENABLED and must be DISABLED in production", 1).show();
      }
      if (paramContext.y)
      {
        Log.info("A4S SDK VERSION : A3.2.1 (Build : 15b121ed0fdda3cad3b5e535be468e8c8daa05f8)");
        Log.error("***********************************/!\\***********************************");
        Log.error("/!\\ Logging is Enabled and must be DISABLED in production environment /!\\");
        Log.error("***********************************/!\\***********************************");
        Toast.makeText(this.j, "A4S Logging is ENABLED and must be DISABLED in production", 1).show();
      }
      com.ad4screen.sdk.common.f.a(this.j);
      com.ad4screen.sdk.systems.f.a().a(c.f.class, this.y);
      com.ad4screen.sdk.systems.f.a().a(c.c.class, this.z);
      com.ad4screen.sdk.systems.f.a().a(c.d.class, this.A);
      com.ad4screen.sdk.systems.f.a().a(c.g.class, this.x);
      com.ad4screen.sdk.systems.f.a().a(c.b.class, this.i);
      this.k = com.ad4screen.sdk.client.b.a(this.j);
      this.l = new com.ad4screen.sdk.client.a(this.j)
      {
        protected IA4SService a(IBinder paramAnonymousIBinder)
        {
          return IA4SService.Stub.asInterface(paramAnonymousIBinder);
        }
        
        protected void a(IA4SService paramAnonymousIA4SService)
          throws RemoteException
        {
          paramAnonymousIA4SService.setClientCallback(a.a(a.this).a());
        }
      };
      this.l.a(new a.a("clientStarted")
      {
        public void a(IA4SService paramAnonymousIA4SService)
          throws RemoteException
        {
          paramAnonymousIA4SService.clientStarted();
        }
      });
      return;
    }
    this.j = paramContext.getApplicationContext();
    Log.setEnabled(com.ad4screen.sdk.systems.b.a(this.j).y);
    Log.info("Initializing A4S light mode");
    this.m = new Handler(Looper.getMainLooper());
    this.k = com.ad4screen.sdk.client.b.a(this.j);
    this.l = new com.ad4screen.sdk.client.a(this.j)
    {
      protected IA4SService a(IBinder paramAnonymousIBinder)
      {
        return IA4SService.Stub.asInterface(paramAnonymousIBinder);
      }
      
      protected void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.setClientCallback(a.a(a.this).a());
      }
    };
    this.l.a(new a.a("setDoNotTrackEnabled")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.setDoNotTrack(true, true, false, new d.a()
        {
          public void a(boolean paramAnonymous2Boolean)
            throws RemoteException
          {
            a.this.c();
          }
        });
      }
    });
  }
  
  private void a(Intent paramIntent)
  {
    Uri localUri = paramIntent.getData();
    if (localUri != null) {}
    try
    {
      this.w = localUri.getQueryParameter("bma4ssrc");
      if ((paramIntent.getExtras() != null) && (paramIntent.getExtras().getBundle("com.ad4screen.sdk.extra.GCM_PAYLOAD") != null))
      {
        paramIntent = com.ad4screen.sdk.service.modules.push.model.a.a(paramIntent.getExtras().getBundle("com.ad4screen.sdk.extra.GCM_PAYLOAD"));
        if (paramIntent != null) {
          this.w = paramIntent.x;
        }
      }
      if (this.w != null) {
        this.l.a(new a.a("setSource")
        {
          public void a(IA4SService paramAnonymousIA4SService)
            throws RemoteException
          {
            paramAnonymousIA4SService.setSource(a.c(a.this));
          }
        });
      }
      return;
    }
    catch (UnsupportedOperationException localUnsupportedOperationException)
    {
      for (;;)
      {
        Log.internal("Can't get uri parameters", localUnsupportedOperationException);
      }
    }
  }
  
  private void a(final String paramString1, final int paramInt, String paramString2)
  {
    this.l.a(new a.a("InAppReady")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.onInAppReady(paramString1, paramInt);
      }
    });
    if (this.a != null)
    {
      if ((this.e != null) && (this.e.length != 0)) {
        break label63;
      }
      this.a.onResult(new InApp(paramString1, paramInt, paramString2, null));
    }
    for (;;)
    {
      return;
      label63:
      int i1 = 0;
      while (i1 < this.e.length)
      {
        if (paramInt == this.e[i1])
        {
          this.a.onResult(new InApp(paramString1, paramInt, paramString2, null));
          return;
        }
        i1 += 1;
      }
    }
  }
  
  private void a(final String paramString1, int paramInt, String paramString2, HashMap<String, String> paramHashMap)
  {
    this.l.a(new a.a("displayedInApp")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.onInAppDisplayed(paramString1);
      }
    });
    if (this.b != null)
    {
      if ((this.f != null) && (this.f.length != 0)) {
        break label63;
      }
      this.b.onResult(new InApp(paramString1, paramInt, paramString2, paramHashMap));
    }
    for (;;)
    {
      return;
      label63:
      int i1 = 0;
      while (i1 < this.f.length)
      {
        if (paramInt == this.f[i1])
        {
          this.b.onResult(new InApp(paramString1, paramInt, paramString2, paramHashMap));
          return;
        }
        i1 += 1;
      }
    }
  }
  
  private void a(final String paramString1, final String paramString2, int paramInt, String paramString3, HashMap<String, String> paramHashMap)
  {
    this.l.a(new a.a("clickedInApp")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.onInAppClicked(paramString1, paramString2);
      }
    });
    if (this.d != null)
    {
      if ((this.h != null) && (this.h.length != 0)) {
        break label65;
      }
      this.d.onResult(new InApp(paramString1, paramInt, paramString3, paramHashMap));
    }
    for (;;)
    {
      return;
      label65:
      int i1 = 0;
      while (i1 < this.h.length)
      {
        if (paramInt == this.h[i1])
        {
          this.b.onResult(new InApp(paramString1, paramInt, paramString3, paramHashMap));
          return;
        }
        i1 += 1;
      }
    }
  }
  
  private void a(final String paramString1, final boolean paramBoolean, int paramInt, String paramString2)
  {
    this.l.a(new a.a("closedInApp")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.onInAppClosed(paramString1, paramBoolean);
      }
    });
    if (this.c != null)
    {
      if ((this.g != null) && (this.g.length != 0)) {
        break label64;
      }
      this.c.onResult(new InApp(paramString1, paramInt, paramString2, null));
    }
    for (;;)
    {
      return;
      label64:
      int i1 = 0;
      while (i1 < this.g.length)
      {
        if (paramInt == this.g[i1])
        {
          this.c.onResult(new InApp(paramString1, paramInt, paramString2, null));
          return;
        }
        i1 += 1;
      }
    }
  }
  
  protected void a()
  {
    this.l.a(new a.a("interstitialDisplayed")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.interstitialDisplayed();
      }
    });
  }
  
  protected void a(int paramInt, A4S.Callback<Inbox> paramCallback)
  {
    if (u == null) {
      u = new ArrayList();
    }
    if (t == null) {
      t = new ArrayList();
    }
    if (!u.contains(p.a[paramInt].a)) {
      u.add(p.a[paramInt].a);
    }
    t.add(paramCallback);
    v = true;
  }
  
  protected void a(int paramInt, final A4S.Callback<Inbox> paramCallback, boolean paramBoolean)
  {
    int i4 = 0;
    if (p == null) {
      Log.debug("Please use method getInbox first in order to retrieve some messages.");
    }
    while ((paramCallback == null) && (!paramBoolean)) {
      return;
    }
    if ((q) || (v))
    {
      a(paramInt, paramCallback);
      return;
    }
    if (r == null)
    {
      r = new ArrayList();
      if (paramBoolean) {
        break label206;
      }
      r.add(paramCallback);
      label71:
      if (s != null) {
        break label232;
      }
      s = new ArrayList();
      label87:
      if (paramBoolean) {
        break label241;
      }
      s.add(p.a[paramInt].a);
    }
    int i2;
    for (;;)
    {
      i1 = paramInt + 1;
      int i3;
      for (i2 = 0; i1 < p.a.length; i2 = i3)
      {
        i3 = i2;
        if (!p.a[i1].n)
        {
          i3 = i2;
          if (i2 < 15)
          {
            s.add(p.a[i1].a);
            i3 = i2 + 1;
          }
        }
        i1 += 1;
      }
      r.clear();
      break;
      label206:
      r = new ArrayList(t);
      t.clear();
      t = null;
      break label71;
      label232:
      s.clear();
      break label87;
      label241:
      s = new ArrayList(u);
      u.clear();
      u = null;
    }
    paramInt -= 1;
    for (int i1 = 0; paramInt > 0; i1 = i2)
    {
      i2 = i1;
      if (!p.a[paramInt].n)
      {
        i2 = i1;
        if (i1 < 15)
        {
          s.add(p.a[paramInt].a);
          i2 = i1 + 1;
        }
      }
      paramInt -= 1;
    }
    q = true;
    i1 = s.size();
    final String[] arrayOfString = new String[i1];
    paramInt = i4;
    while (paramInt < i1)
    {
      arrayOfString[paramInt] = ((String)s.get(paramInt));
      paramInt += 1;
    }
    this.l.a(new a.a("getMessages")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.getMessagesDetails(arrayOfString, new g.a()
        {
          public void a()
            throws RemoteException
          {
            a.a(false);
            a.d(a.this).post(new Runnable()
            {
              public void run()
              {
                a.38.this.b.onError(0, "An error occured while downloading messages");
              }
            });
          }
          
          public void a(com.ad4screen.sdk.inbox.Message[] paramAnonymous2ArrayOfMessage)
            throws RemoteException
          {
            a.a(false);
            int i = 0;
            while (i < paramAnonymous2ArrayOfMessage.length)
            {
              int j = 0;
              while (j < a.e().a.length)
              {
                if (a.e().a[j].a.equals(paramAnonymous2ArrayOfMessage[i].a)) {
                  a.e().a[j] = paramAnonymous2ArrayOfMessage[i];
                }
                j += 1;
              }
              i += 1;
            }
            a.d(a.this).post(new Runnable()
            {
              public void run()
              {
                int k = 0;
                Inbox localInbox = h.a(a.e(a.this), a.e());
                int i = 0;
                while (i < a.f().size())
                {
                  ((A4S.Callback)a.f().get(i)).onResult(localInbox);
                  i += 1;
                }
                if (a.g())
                {
                  a.b(false);
                  i = 0;
                  int j = 0;
                  while (i < a.h().size())
                  {
                    if (!a.i().contains(a.h().get(i))) {
                      j = 1;
                    }
                    i += 1;
                  }
                  i = k;
                  if (j != 0) {
                    a.this.a(0, null, true);
                  }
                }
                else
                {
                  return;
                }
                while (i < a.j().size())
                {
                  ((A4S.Callback)a.j().get(i)).onResult(localInbox);
                  i += 1;
                }
                a.j().clear();
                a.h().clear();
              }
            });
          }
        });
      }
    });
  }
  
  protected void a(Context paramContext, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    this.l.a(new a.a("setDoNotTrackEnabled")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.setDoNotTrack(paramBoolean1, paramBoolean2, true, new d.a()
        {
          public void a(boolean paramAnonymous2Boolean)
            throws RemoteException
          {
            if (a.44.this.a) {
              a.this.c();
            }
          }
        });
      }
    });
  }
  
  protected void a(final String paramString)
  {
    this.l.a(new a.a("clickMessageButton")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        int i = 0;
        while (i < com.ad4screen.sdk.inbox.a.b.q.length)
        {
          if (paramString.equals(paramString))
          {
            Button localButton = com.ad4screen.sdk.inbox.a.b.q[i];
            h.a(a.e(a.this), "com.ad4screen.sdk.intent.action.CLICKED", com.ad4screen.sdk.inbox.a.b.q[i].f);
            paramAnonymousIA4SService.clickButtonMessage(localButton, paramString);
            if (localButton.c != Message.a.f) {
              h.a(a.d(a.this), a.e(a.this), a.a(a.this), localButton.c, localButton.d, localButton.b);
            }
          }
          i += 1;
        }
      }
    });
  }
  
  protected void a(final String paramString, final A4S.Callback<Message> paramCallback)
  {
    this.l.a(new a.a("displayMessage")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        int i = 0;
        while (i < a.e().a.length)
        {
          if (paramString.equals(paramString))
          {
            com.ad4screen.sdk.inbox.a.b = a.e().a[i];
            paramAnonymousIA4SService.displayMessage(com.ad4screen.sdk.inbox.a.b);
            a.d(a.this).post(new Runnable()
            {
              public void run()
              {
                if ((com.ad4screen.sdk.inbox.a.b.j == Message.a.b) || (com.ad4screen.sdk.inbox.a.b.j == Message.a.d))
                {
                  h.a(a.e(a.this), "com.ad4screen.sdk.intent.action.DISPLAYED", com.ad4screen.sdk.inbox.a.b.r);
                  a.39.this.b.onResult(h.a(com.ad4screen.sdk.inbox.a.b));
                }
                do
                {
                  return;
                  h.a(a.e(a.this), "com.ad4screen.sdk.intent.action.CLICKED", com.ad4screen.sdk.inbox.a.b.r);
                } while (com.ad4screen.sdk.inbox.a.b.j == Message.a.f);
                h.a(a.d(a.this), a.e(a.this), a.a(a.this), com.ad4screen.sdk.inbox.a.b.j, com.ad4screen.sdk.inbox.a.b.e, com.ad4screen.sdk.inbox.a.b.b);
              }
            });
          }
          i += 1;
        }
      }
    });
  }
  
  protected void a(final String paramString1, final String paramString2, final String[] paramArrayOfString)
  {
    this.l.a(new a.a("uploadFacebookProfile")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.uploadFacebookProfile(paramString1, paramString2, paramArrayOfString);
      }
    });
  }
  
  protected void b()
  {
    this.l.a(new a.a("interstitialClosed")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.interstitialClosed();
      }
    });
  }
  
  protected void b(String paramString)
  {
    if ((this.n == null) || (this.o == null)) {
      Log.error("A4S|No action to perform");
    }
    do
    {
      return;
      if (!this.o.equals(paramString))
      {
        Log.error("A4S|Wrong confirmation");
        return;
      }
      this.l.a(new a.a("doAction")
      {
        public void a(IA4SService paramAnonymousIA4SService)
          throws RemoteException
        {
          paramAnonymousIA4SService.doAction(a.f(a.this));
        }
      });
      paramString = com.ad4screen.sdk.systems.b.a(this.j);
      if ("activateInternalLogging".equals(this.n))
      {
        Log.debug("A4S|Internal Logging is now enabled on " + paramString.i + " (" + paramString.o + ")");
        Log.setInternalLoggingEnabled(true);
        return;
      }
      if ("disableInternalLogging".equals(this.n))
      {
        Log.debug("A4S|Internal Logging is now disabled on " + paramString.i + " (" + paramString.o + ")");
        Log.setInternalLoggingEnabled(false);
        return;
      }
      if ("activateLogging".equals(this.n))
      {
        Log.setEnabled(true);
        Log.debug("A4S|Logging is now enabled on " + paramString.i + " (" + paramString.o + ")");
        Log.debug("A4S|App Version : " + paramString.n);
        Log.debug("A4S|Partner Id : " + paramString.d);
        Log.debug("A4S|SDK Version : " + paramString.a);
        paramString.a(true);
        return;
      }
    } while (!"disableLogging".equals(this.n));
    Log.debug("A4S|Logging is now disabled on " + paramString.i + " (" + paramString.o + ")");
    Log.setEnabled(false);
    paramString.a(false);
  }
  
  protected void c()
  {
    this.l.a();
  }
  
  @SuppressLint({"TrulyRandom"})
  protected void c(String paramString)
  {
    if (paramString == null) {
      return;
    }
    try
    {
      Object localObject = new X509EncodedKeySpec(k.c.a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdvmLrVeu/wHpscTzjVh6Z61lUmvAGGHRKF+KRF9ZhfUvDrS/T4vxetFx4gRU2ofYVOoLFsFWPIzsZKL3G9bLQnsmGFsiqjAiOWUmm5TbozwGtISsB4OKMtM+lMoC44SIUWx1dpwh5N0F92gMRS4HJPmvhEAXEkvsAvH3cOUqsrwIDAQAB", 0));
      PublicKey localPublicKey = KeyFactory.getInstance("RSA").generatePublic((KeySpec)localObject);
      localObject = Cipher.getInstance("RSA");
      ((Cipher)localObject).init(1, localPublicKey);
      this.n = paramString;
      this.o = UUID.randomUUID().toString();
      paramString = new Intent("com.ad4screen.sdk.action.CONFIRM");
      paramString.addCategory("com.ad4screen.sdk.intent.category.ACTION");
      paramString.putExtra("confirmation", k.c.a(((Cipher)localObject).doFinal(this.o.getBytes()), 0));
      this.j.sendBroadcast(paramString);
      return;
    }
    catch (NoSuchAlgorithmException paramString)
    {
      Log.error("A4S|Can't send confirmation for this action", paramString);
      return;
    }
    catch (InvalidKeySpecException paramString)
    {
      Log.error("A4S|Can't send confirmation for this action", paramString);
      return;
    }
    catch (IllegalBlockSizeException paramString)
    {
      Log.error("A4S|Can't send confirmation for this action", paramString);
      return;
    }
    catch (BadPaddingException paramString)
    {
      Log.error("A4S|Can't send confirmation for this action", paramString);
      return;
    }
    catch (InvalidKeyException paramString)
    {
      Log.error("A4S|Can't send confirmation for this action", paramString);
      return;
    }
    catch (NoSuchPaddingException paramString)
    {
      Log.error("A4S|Can't send confirmation for this action", paramString);
    }
  }
  
  protected void d(final String paramString)
  {
    this.l.a(new a.a("trackReferrer")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.trackReferrer(paramString);
      }
    });
  }
  
  public void displayInApp(final String paramString)
  {
    if (this.a == null)
    {
      Log.error("A4S|You should call setInAppReadyCallback before displayInApp");
      return;
    }
    this.l.a(new a.a("displayInApp")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.displayInApp(paramString);
      }
    });
  }
  
  public void getA4SId(final A4S.Callback<String> paramCallback)
  {
    if (paramCallback == null) {
      return;
    }
    this.l.a(new a.a("getA4SId")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.getA4SId(new e.a()
        {
          public void a(final String paramAnonymous2String)
            throws RemoteException
          {
            a.d(a.this).post(new Runnable()
            {
              public void run()
              {
                a.22.this.a.onResult(paramAnonymous2String);
              }
            });
          }
        });
      }
    });
  }
  
  public String getAndroidId()
  {
    return i.b(this.j);
  }
  
  protected ArrayList<Beacon> getBeacons()
  {
    List localList = new com.ad4screen.sdk.service.modules.tracking.a(this.j).a();
    ArrayList localArrayList = new ArrayList();
    if (localList != null)
    {
      int i1 = 0;
      for (;;)
      {
        if (i1 < localList.size()) {
          try
          {
            localArrayList.add(new e().a(((JSONObject)localList.get(i1)).toString(), new Beacon()));
            i1 += 1;
          }
          catch (JSONException localJSONException)
          {
            for (;;)
            {
              Log.internal("A4SImpl|Can't retrieve beacon from archive", localJSONException);
            }
          }
        }
      }
    }
    return localArrayList;
  }
  
  public void getIDFV(final A4S.Callback<String> paramCallback)
  {
    if (paramCallback == null) {
      return;
    }
    this.l.a(new a.a("getIDFV")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.getIDFV(new e.a()
        {
          public void a(final String paramAnonymous2String)
            throws RemoteException
          {
            a.d(a.this).post(new Runnable()
            {
              public void run()
              {
                a.24.this.a.onResult(paramAnonymous2String);
              }
            });
          }
        });
      }
    });
  }
  
  public void getInbox(final A4S.Callback<Inbox> paramCallback)
  {
    if (paramCallback == null) {
      return;
    }
    if (q) {
      Log.debug("A call to Inbox Webservice is in progress. Please wait for it to complete before calling again");
    }
    q = true;
    getA4SId(new A4S.Callback()
    {
      public void a(String paramAnonymousString)
      {
        a.b(a.this).a(new a.a("getInbox")
        {
          public void a(IA4SService paramAnonymous2IA4SService)
            throws RemoteException
          {
            paramAnonymous2IA4SService.getMessagesList(new g.a()
            {
              public void a()
                throws RemoteException
              {
                a.a(false);
                a.d(a.this).post(new Runnable()
                {
                  public void run()
                  {
                    a.37.this.a.onError(0, "An error occured while downloading messages");
                  }
                });
              }
              
              public void a(final com.ad4screen.sdk.inbox.Message[] paramAnonymous3ArrayOfMessage)
                throws RemoteException
              {
                a.a(false);
                a.a(new com.ad4screen.sdk.inbox.a(paramAnonymous3ArrayOfMessage));
                paramAnonymous3ArrayOfMessage = h.a(a.e(a.this), a.e());
                a.d(a.this).post(new Runnable()
                {
                  public void run()
                  {
                    a.37.this.a.onResult(paramAnonymous3ArrayOfMessage);
                  }
                });
              }
            });
          }
        });
      }
      
      public void onError(int paramAnonymousInt, String paramAnonymousString) {}
    });
  }
  
  protected void handleGeofencingMessage(final Bundle paramBundle)
  {
    this.l.a(new a.a("handleGeofencingMessage")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.handleGeofencingMessage(paramBundle);
      }
    });
  }
  
  protected void handlePushMessage(final Bundle paramBundle)
  {
    this.l.a(new a.a("handlePushMessage")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.handlePushMessage(paramBundle);
      }
    });
  }
  
  public void isGCMEnabled(A4S.Callback<Boolean> paramCallback)
  {
    isPushEnabled(paramCallback);
  }
  
  public void isInAppDisplayLocked(final A4S.Callback<Boolean> paramCallback)
  {
    if (paramCallback == null) {
      return;
    }
    this.l.a(new a.a("isInAppDisplayLocked")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramCallback.onResult(Boolean.valueOf(paramAnonymousIA4SService.isInAppDisplayLocked()));
      }
    });
  }
  
  public void isPushEnabled(final A4S.Callback<Boolean> paramCallback)
  {
    if (paramCallback == null) {
      return;
    }
    this.l.a(new a.a("isPushEnabled")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramCallback.onResult(Boolean.valueOf(paramAnonymousIA4SService.isPushEnabled()));
      }
    });
  }
  
  public boolean isPushNotificationLocked()
  {
    return this.k.e();
  }
  
  public void isRestrictedConnection(final A4S.Callback<Boolean> paramCallback)
  {
    if (paramCallback == null) {
      return;
    }
    this.l.a(new a.a("isRestrictedConnection")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramCallback.onResult(Boolean.valueOf(paramAnonymousIA4SService.isRestrictedConnection()));
      }
    });
  }
  
  public void putState(final String paramString1, final String paramString2)
  {
    this.l.a(new a.a("putState")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.putState(paramString1, paramString2);
      }
    });
  }
  
  public void resetOverlayPosition()
  {
    k.a(this.j).a(new FrameLayout.LayoutParams(-1, -2, 80));
  }
  
  public void sendGCMToken(String paramString)
  {
    sendPushToken(paramString);
  }
  
  public void sendPushToken(String paramString)
  {
    if (paramString == null) {
      return;
    }
    Bundle localBundle = new Bundle();
    localBundle.putString("registration_id", paramString);
    updatePushRegistration(localBundle);
  }
  
  public void setGCMEnabled(boolean paramBoolean)
  {
    setPushEnabled(paramBoolean);
  }
  
  public void setInAppClickedCallback(A4S.Callback<InApp> paramCallback, int... paramVarArgs)
  {
    this.d = paramCallback;
    this.h = paramVarArgs;
  }
  
  public void setInAppClosedCallback(A4S.Callback<InApp> paramCallback, int... paramVarArgs)
  {
    this.c = paramCallback;
    this.g = paramVarArgs;
  }
  
  public void setInAppDisplayLocked(final boolean paramBoolean)
  {
    this.l.a(new a.a("setInAppDisplayLocked")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.setInAppDisplayLocked(paramBoolean);
      }
    });
  }
  
  public void setInAppDisplayedCallback(A4S.Callback<InApp> paramCallback, int... paramVarArgs)
  {
    this.b = paramCallback;
    this.f = paramVarArgs;
  }
  
  public void setInAppReadyCallback(final boolean paramBoolean, A4S.Callback<InApp> paramCallback, final int... paramVarArgs)
  {
    this.a = paramCallback;
    this.e = paramVarArgs;
    this.l.a(new a.a("setInAppReadyCallback")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.setInAppReadyCallback(paramBoolean, paramVarArgs);
      }
    });
  }
  
  public void setIntent(Intent paramIntent)
  {
    this.k.a(paramIntent);
  }
  
  public void setOverlayPosition(FrameLayout.LayoutParams paramLayoutParams)
  {
    k.a(this.j).a(paramLayoutParams);
  }
  
  public void setPushEnabled(final boolean paramBoolean)
  {
    this.l.a(new a.a("setPushEnabled")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.setPushEnabled(paramBoolean);
      }
    });
  }
  
  public void setPushNotificationLocked(boolean paramBoolean)
  {
    this.k.a(paramBoolean);
    StringBuilder localStringBuilder = new StringBuilder().append("Push|Push display is now ");
    if (paramBoolean) {}
    for (String str = "";; str = "un")
    {
      Log.debug(str + "locked");
      if (!paramBoolean) {
        this.k.b();
      }
      return;
    }
  }
  
  public void setRestrictedConnection(final boolean paramBoolean)
  {
    this.l.a(new a.a("setRestrictedConnection")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.setRestrictedConnection(paramBoolean);
      }
    });
  }
  
  public void setView(final String paramString)
  {
    this.l.a(new a.a("setView")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.setView(paramString);
      }
    });
  }
  
  public void startActivity(final Activity paramActivity)
  {
    if (paramActivity == null)
    {
      Log.error("A4S|Activity for startActivity can't be null");
      return;
    }
    this.k.a(paramActivity);
    Intent localIntent = paramActivity.getIntent();
    if (localIntent != null) {
      a(localIntent);
    }
    this.k.c();
    this.k.b();
    this.l.a(new a.a("startActivity")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.startActivity(k.b(paramActivity), k.a(paramActivity), k.c(paramActivity));
      }
    });
  }
  
  public void stopActivity(final Activity paramActivity)
  {
    if (paramActivity == null)
    {
      Log.error("A4S|Activity for stopActivity can't be null");
      return;
    }
    this.k.b(paramActivity);
    if (this.a != null) {
      setInAppReadyCallback(false, null, new int[0]);
    }
    if (this.b != null) {
      setInAppDisplayedCallback(null, new int[0]);
    }
    if (this.d != null) {
      setInAppClickedCallback(null, new int[0]);
    }
    if (this.c != null) {
      setInAppClosedCallback(null, new int[0]);
    }
    this.l.a(new a.a("stopActivity")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.stopActivity(k.a(paramActivity));
      }
    });
  }
  
  public void trackAddToCart(final Cart paramCart)
  {
    if (paramCart == null) {
      throw new IllegalArgumentException("Purchase cannot be null");
    }
    try
    {
      paramCart = (Cart)paramCart.clone();
      final Bundle localBundle = new Bundle();
      localBundle.putString("FBToken", c.c(this.j));
      this.l.a(new a.a("trackAddToCart")
      {
        public void a(IA4SService paramAnonymousIA4SService)
          throws RemoteException
        {
          paramAnonymousIA4SService.trackAddToCart(paramCart, localBundle);
        }
      });
      return;
    }
    catch (CloneNotSupportedException paramCart)
    {
      Log.error("Cannot properly clone Cart", paramCart);
    }
  }
  
  public void trackEvent(final long paramLong, String... paramVarArgs)
  {
    final Bundle localBundle = new Bundle();
    localBundle.putString("FBToken", c.c(this.j));
    this.l.a(new a.a("trackEvent")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.trackEvent(paramLong, localBundle, this.c);
      }
    });
  }
  
  public void trackLead(final Lead paramLead)
  {
    if (paramLead == null) {
      throw new IllegalArgumentException("Lead cannot be null");
    }
    try
    {
      paramLead = (Lead)paramLead.clone();
      final Bundle localBundle = new Bundle();
      localBundle.putString("FBToken", c.c(this.j));
      this.l.a(new a.a("trackLead")
      {
        public void a(IA4SService paramAnonymousIA4SService)
          throws RemoteException
        {
          paramAnonymousIA4SService.trackLead(paramLead, localBundle);
        }
      });
      return;
    }
    catch (CloneNotSupportedException paramLead)
    {
      Log.error("Cannot properly clone Lead", paramLead);
    }
  }
  
  public void trackPurchase(final Purchase paramPurchase)
  {
    if (paramPurchase == null) {
      throw new IllegalArgumentException("Purchase cannot be null");
    }
    try
    {
      paramPurchase = (Purchase)paramPurchase.clone();
      final Bundle localBundle = new Bundle();
      localBundle.putString("FBToken", c.c(this.j));
      this.l.a(new a.a("trackPurchase")
      {
        public void a(IA4SService paramAnonymousIA4SService)
          throws RemoteException
        {
          paramAnonymousIA4SService.trackPurchase(paramPurchase, localBundle);
        }
      });
      return;
    }
    catch (CloneNotSupportedException paramPurchase)
    {
      Log.error("Cannot properly clone Purchase", paramPurchase);
    }
  }
  
  protected void triggerBeacons(final Bundle paramBundle)
  {
    this.l.a(new a.a("triggerBeacons")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.triggerBeacons(paramBundle);
      }
    });
  }
  
  public void updateDeviceInfo(final Bundle paramBundle)
  {
    if (paramBundle == null) {
      return;
    }
    paramBundle = new Bundle(paramBundle);
    this.l.a(new a.a("updateDeviceInfo")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.updateUserPreferences(paramBundle, false);
      }
    });
  }
  
  public void updateGeolocation(final Location paramLocation)
  {
    this.l.a(new a.a("updateGeolocation")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.updateGeolocation(paramLocation);
      }
    });
  }
  
  public void updateMessages(final Inbox paramInbox)
  {
    if ((paramInbox == null) || (paramInbox.countMessages() == 0)) {
      return;
    }
    this.l.a(new a.a("updateMessages")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        com.ad4screen.sdk.inbox.a locala = a.e();
        int i = 0;
        while (i < locala.a.length)
        {
          if ((paramInbox.a[i].isRead() != locala.a[i].l) || (paramInbox.a[i].isArchived() != locala.a[i].m))
          {
            locala.a[i].l = paramInbox.a[i].isRead();
            locala.a[i].m = paramInbox.a[i].isArchived();
            locala.a[i].o = true;
          }
          i += 1;
        }
        paramAnonymousIA4SService.updateMessages(locala.a, new g.a()
        {
          public void a()
            throws RemoteException
          {
            Log.debug("Inbox|Update failed, we will try again as soon as updateMessages method will be called again");
          }
          
          public void a(com.ad4screen.sdk.inbox.Message[] paramAnonymous2ArrayOfMessage)
            throws RemoteException
          {
            a.a(new com.ad4screen.sdk.inbox.a(paramAnonymous2ArrayOfMessage));
          }
        });
      }
    });
  }
  
  protected void updatePushRegistration(final Bundle paramBundle)
  {
    this.l.a(new a.a("updatePushRegistration")
    {
      public void a(IA4SService paramAnonymousIA4SService)
        throws RemoteException
      {
        paramAnonymousIA4SService.updatePushRegistration(paramBundle);
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
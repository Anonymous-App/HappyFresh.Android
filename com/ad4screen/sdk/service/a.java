package com.ad4screen.sdk.service;

import android.location.Location;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ResultReceiver;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.IA4SService.Stub;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.d;
import com.ad4screen.sdk.g;
import com.ad4screen.sdk.inbox.Button;
import com.ad4screen.sdk.inbox.Message;
import com.ad4screen.sdk.inbox.Message.a;
import com.ad4screen.sdk.service.modules.common.e.a;
import com.ad4screen.sdk.service.modules.member.c;
import java.util.ArrayList;

public final class a
  extends IA4SService.Stub
{
  private final A4SService.a a;
  private final Object b = new Object();
  
  public a(A4SService.a parama)
  {
    this.a = parama;
  }
  
  public void clickButtonMessage(Button paramButton, String paramString)
    throws RemoteException
  {
    if (paramButton.c == Message.a.g) {
      com.ad4screen.sdk.service.modules.common.h.a(this.a, paramString, paramButton.a, e.a.c);
    }
    for (;;)
    {
      if (paramButton.c == Message.a.f) {
        com.ad4screen.sdk.service.modules.common.h.a(this.a.a(), paramButton.d, new com.ad4screen.sdk.common.e[] { new com.ad4screen.sdk.common.e("nid", paramString), new com.ad4screen.sdk.common.e("bid", paramButton.a) });
      }
      return;
      com.ad4screen.sdk.service.modules.common.h.a(this.a, paramString, paramButton.a, e.a.b);
    }
  }
  
  public void clientStarted()
    throws RemoteException
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        com.ad4screen.sdk.systems.h.a(a.a(a.this).a()).b();
      }
    });
  }
  
  public void closeCurrentInApp()
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).d().d();
      }
    });
  }
  
  public void displayInApp(final String paramString)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).d().f(paramString);
      }
    });
  }
  
  public void displayMessage(Message paramMessage)
    throws RemoteException
  {
    com.ad4screen.sdk.service.modules.common.h.a(this.a, paramMessage.a, e.a.a);
    if (paramMessage.j == Message.a.f) {
      com.ad4screen.sdk.service.modules.common.h.a(this.a.a(), paramMessage.e, new com.ad4screen.sdk.common.e[] { new com.ad4screen.sdk.common.e("nid", paramMessage.a) });
    }
  }
  
  public void doAction(String paramString)
  {
    if ("activateInternalLogging".equals(paramString)) {
      Log.setInternalLoggingEnabled(true);
    }
    do
    {
      return;
      if ("disableInternalLogging".equals(paramString))
      {
        Log.setInternalLoggingEnabled(false);
        return;
      }
      if ("activateLogging".equals(paramString))
      {
        Log.setEnabled(true);
        com.ad4screen.sdk.systems.b.a(this.a.a()).a(true);
        return;
      }
    } while (!"disableLogging".equals(paramString));
    Log.setEnabled(false);
    com.ad4screen.sdk.systems.b.a(this.a.a()).a(false);
  }
  
  public void getA4SId(final com.ad4screen.sdk.e parame)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        String str = com.ad4screen.sdk.systems.b.a(a.a(a.this).a()).f;
        if (str != null) {
          try
          {
            parame.a(str);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            Log.error("A4SService|Error while sending A4SId back to client", localRemoteException);
            return;
          }
        }
        a.a(a.this).a(this, 1000L);
      }
    });
  }
  
  public void getActiveMember(com.ad4screen.sdk.e parame)
  {
    try
    {
      parame.a(c.a(this.a.a()).b());
      return;
    }
    catch (RemoteException parame)
    {
      Log.error("MemberManager|Error while sending active member to client. Please check connection between service and client", parame);
    }
  }
  
  public void getIDFV(final com.ad4screen.sdk.e parame)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        com.ad4screen.sdk.service.modules.tracking.f.a(new A4S.Callback()
        {
          public void a(String paramAnonymous2String)
          {
            try
            {
              a.7.this.a.a(paramAnonymous2String);
              return;
            }
            catch (RemoteException paramAnonymous2String)
            {
              Log.error("A4SService|Error while sending IDFV back to client", paramAnonymous2String);
            }
          }
          
          public void onError(int paramAnonymous2Int, String paramAnonymous2String) {}
        }, a.a(a.this).a());
      }
    });
  }
  
  public void getMembers(final com.ad4screen.sdk.f paramf)
  {
    c.a(this.a.a()).a(new A4S.Callback()
    {
      public void a(com.ad4screen.sdk.service.modules.member.model.a paramAnonymousa)
      {
        String[] arrayOfString = new String[paramAnonymousa.a.size()];
        int i = 0;
        while (i < paramAnonymousa.a.size())
        {
          arrayOfString[i] = ((com.ad4screen.sdk.service.modules.member.model.b)paramAnonymousa.a.get(i)).a;
          i += 1;
        }
        try
        {
          paramf.a(arrayOfString);
          return;
        }
        catch (RemoteException paramAnonymousa)
        {
          Log.error("MemberManager|Error while sending retrieved members to client. Please check connection between service and client", paramAnonymousa);
        }
      }
      
      public void onError(int paramAnonymousInt, String paramAnonymousString) {}
    });
  }
  
  public void getMessagesDetails(String[] paramArrayOfString, g paramg)
    throws RemoteException
  {
    this.a.e().a(paramArrayOfString, paramg);
  }
  
  public void getMessagesList(g paramg)
    throws RemoteException
  {
    this.a.e().a(paramg);
  }
  
  public void handleGeofencingMessage(final Bundle paramBundle)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).g().a(paramBundle);
      }
    });
  }
  
  public void handlePushMessage(final Bundle paramBundle)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).c().handleMessage(paramBundle);
      }
    });
  }
  
  public void interstitialClosed()
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        com.ad4screen.sdk.systems.h.a(a.a(a.this).a()).d();
      }
    });
  }
  
  public void interstitialDisplayed()
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        com.ad4screen.sdk.systems.h.a(a.a(a.this).a()).c();
      }
    });
  }
  
  public boolean isInAppDisplayLocked()
  {
    return this.a.d().c();
  }
  
  public boolean isPushEnabled()
  {
    synchronized (this.b)
    {
      boolean bool = this.a.c().isEnabled();
      return bool;
    }
  }
  
  public boolean isRestrictedConnection()
  {
    return com.ad4screen.sdk.common.cache.a.a(this.a.a()).d();
  }
  
  public void logIn(String paramString)
    throws RemoteException
  {
    c.a(this.a.a()).a(paramString);
  }
  
  public void logOut()
    throws RemoteException
  {
    c.a(this.a.a()).a();
  }
  
  public void onInAppClicked(final String paramString1, final String paramString2)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).d().a(paramString1, paramString2);
      }
    });
  }
  
  public void onInAppClosed(final String paramString, final boolean paramBoolean)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        if (paramBoolean)
        {
          a.a(a.this).d().c(paramString);
          return;
        }
        a.a(a.this).d().b(paramString);
      }
    });
  }
  
  public void onInAppDisplayed(final String paramString)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).d().d(paramString);
      }
    });
  }
  
  public void onInAppReady(final String paramString, final int paramInt)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).d().a(paramString, paramInt);
      }
    });
  }
  
  public void openedPush(final Bundle paramBundle)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).c().openedPush(paramBundle);
      }
    });
  }
  
  public void putState(final String paramString1, final String paramString2)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).d().b(paramString1, paramString2);
      }
    });
  }
  
  public void removeMembers(String[] paramArrayOfString)
    throws RemoteException
  {
    c.a(this.a.a()).a(paramArrayOfString);
  }
  
  public void setClientCallback(ResultReceiver paramResultReceiver)
  {
    this.a.f().a(paramResultReceiver);
  }
  
  public void setDoNotTrack(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, final d paramd)
    throws RemoteException
  {
    com.ad4screen.sdk.common.cache.a locala = com.ad4screen.sdk.common.cache.a.a(this.a.a());
    if (!paramBoolean2) {
      locala.g();
    }
    if (paramBoolean3) {
      new com.ad4screen.sdk.service.modules.profile.a(this.a.a(), paramBoolean1).run();
    }
    locala.a(new A4S.Callback()
    {
      public void a(Boolean paramAnonymousBoolean)
      {
        if (paramd != null) {}
        try
        {
          paramd.a(paramAnonymousBoolean.booleanValue());
          return;
        }
        catch (RemoteException paramAnonymousBoolean)
        {
          Log.error("A4SService|Error while sending flush result back to client", paramAnonymousBoolean);
        }
      }
      
      public void onError(int paramAnonymousInt, String paramAnonymousString)
      {
        if (paramd != null) {}
        try
        {
          paramd.a(true);
          return;
        }
        catch (RemoteException paramAnonymousString)
        {
          Log.error("A4SService|Error while sending flush result back to client", paramAnonymousString);
        }
      }
    });
  }
  
  public void setInAppDisplayLocked(final boolean paramBoolean)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).d().a(paramBoolean);
      }
    });
  }
  
  public void setInAppReadyCallback(final boolean paramBoolean, final int[] paramArrayOfInt)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).d().a(paramBoolean, paramArrayOfInt);
      }
    });
  }
  
  public void setPushEnabled(boolean paramBoolean)
  {
    synchronized (this.b)
    {
      this.a.c().setEnabled(paramBoolean);
      return;
    }
  }
  
  public void setRestrictedConnection(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder().append("SDK Internet Connection is now : ");
    if (paramBoolean) {}
    for (String str = "restricted";; str = "unrestricted")
    {
      Log.debug(str);
      if (!paramBoolean) {
        break;
      }
      com.ad4screen.sdk.common.cache.a.a(this.a.a()).c();
      return;
    }
    com.ad4screen.sdk.common.cache.a.a(this.a.a()).b();
  }
  
  public void setSource(String paramString)
  {
    com.ad4screen.sdk.systems.b.a(this.a.a()).f(paramString);
    Log.info("A4S|New source : " + paramString);
  }
  
  public void setView(final String paramString)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).d().e(paramString);
      }
    });
  }
  
  public void startActivity(final String paramString1, final String paramString2, final String paramString3)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        com.ad4screen.sdk.systems.h.a(a.a(a.this).a()).a(paramString1, paramString2, paramString3);
      }
    });
  }
  
  public void stopActivity(String paramString)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        if (!com.ad4screen.sdk.systems.h.a(a.a(a.this).a()).f())
        {
          Log.info("A4S|Received StopActivity but no Activity started yet");
          return;
        }
        com.ad4screen.sdk.systems.h.a(a.a(a.this).a()).a();
      }
    });
  }
  
  public void trackAddToCart(final Cart paramCart, final Bundle paramBundle)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).b().a(paramCart, paramBundle);
      }
    });
  }
  
  public void trackEvent(final long paramLong, final String[] paramArrayOfString, Bundle paramBundle)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).b().a(paramLong, paramArrayOfString, this.c);
      }
    });
  }
  
  public void trackLead(final Lead paramLead, final Bundle paramBundle)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).b().a(paramLead, paramBundle);
      }
    });
  }
  
  public void trackPurchase(final Purchase paramPurchase, final Bundle paramBundle)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).b().a(paramPurchase, paramBundle);
      }
    });
  }
  
  public void trackReferrer(final String paramString)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).b().a(paramString);
      }
    });
  }
  
  public void triggerBeacons(final Bundle paramBundle)
    throws RemoteException
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).h().a(paramBundle);
      }
    });
  }
  
  public void updateGeolocation(final Location paramLocation)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).g().a(paramLocation);
      }
    });
  }
  
  public void updateMessages(Message[] paramArrayOfMessage, g paramg)
    throws RemoteException
  {
    this.a.e().a(paramArrayOfMessage, paramg);
  }
  
  public void updatePushRegistration(final Bundle paramBundle)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).c().updateRegistration(paramBundle);
      }
    });
  }
  
  public void updateUserPreferences(final Bundle paramBundle, final boolean paramBoolean)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).b().a(paramBundle, paramBoolean);
      }
    });
  }
  
  public void uploadFacebookProfile(final String paramString1, final String paramString2, final String[] paramArrayOfString)
  {
    this.a.a(new Runnable()
    {
      public void run()
      {
        a.a(a.this).b().a(paramString1, paramString2, paramArrayOfString);
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.ad4screen.sdk.service.modules.push.providers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.ADMPlugin;
import com.ad4screen.sdk.service.modules.push.PushProvider;
import com.ad4screen.sdk.service.modules.push.f;
import com.ad4screen.sdk.service.modules.push.f.a;

public class a
  extends PushProvider
{
  public a(A4SService.a parama)
  {
    super(parama);
  }
  
  private void a(Context paramContext, String paramString, boolean paramBoolean)
  {
    new f(paramContext, paramString, f.a.c, paramBoolean).run();
  }
  
  public void a()
  {
    a(this.a.a());
  }
  
  protected void a(Context paramContext)
  {
    if (com.ad4screen.sdk.systems.b.a(paramContext).f == null)
    {
      Log.debug("ADMPushProvider|Skipping ADM registration, sharedId was unavailable");
      return;
    }
    if (!isEnabled())
    {
      Log.debug("ADMPushProvider|Notifications were explicitely disabled, skipping ADM registration.");
      return;
    }
    Log.debug("ADMPushProvider|Registering application '" + paramContext.getPackageName() + "' with ADM...");
    ADMPlugin localADMPlugin = com.ad4screen.sdk.common.plugins.b.b();
    if (localADMPlugin != null)
    {
      paramContext = localADMPlugin.register(paramContext, null);
      if (paramContext != null)
      {
        Log.internal("ADMPushProvider|ADM Plugin returned registration id : " + paramContext);
        a(paramContext);
        return;
      }
      Log.internal("ADMPushProvider|No registration id returned from ADM Plugin");
      return;
    }
    Log.debug("ADMPushProvider|ADM Plugin encountered an error while registering !");
  }
  
  protected void a(String paramString)
  {
    Log.debug("ADMPushProvider|ADM registration ID found : " + paramString);
    this.c = 3000L;
    d(paramString);
    a(this.a.a(), paramString, true);
  }
  
  protected void b(Context paramContext)
  {
    if (b() == null)
    {
      Log.debug("ADMPushProvider|Device was already unregistered, skipping ADM unregistration.");
      return;
    }
    Log.debug("ADMPushProvider|Unregistering application from ADM...");
    ADMPlugin localADMPlugin = com.ad4screen.sdk.common.plugins.b.b();
    if (localADMPlugin != null)
    {
      localADMPlugin.unregister(paramContext);
      return;
    }
    Log.warn("ADMPushProvider|Skipping ADM unregistration, plugin unavailable");
  }
  
  protected void b(String paramString)
  {
    Log.debug("ADMPushProvider|ADM unregistered with id : " + paramString);
    this.c = 3000L;
    c();
  }
  
  protected void c(final String paramString)
  {
    Log.error("ADMPushProvider|ADM registration failed with error: " + paramString);
    if ("SERVICE_NOT_AVAILABLE".equals(paramString))
    {
      this.c *= 2L;
      if (this.c > 3600000L) {
        this.c = 3600000L;
      }
      paramString = b();
      new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
      {
        public void run()
        {
          if (paramString == null)
          {
            a.this.a(a.a(a.this).a());
            return;
          }
          a.this.b(a.b(a.this).a());
        }
      }, this.c);
      return;
    }
    Log.error("ADMPushProvider|ADM unrecoverable error for this session");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/push/providers/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.ad4screen.sdk.service.modules.push.providers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.GCMPlugin;
import com.ad4screen.sdk.plugins.PushPlugin;
import com.ad4screen.sdk.service.modules.push.PushProvider;
import com.ad4screen.sdk.service.modules.push.f;
import com.ad4screen.sdk.service.modules.push.f.a;

public class c
  extends PushProvider
{
  public c(A4SService.a parama)
  {
    super(parama);
  }
  
  private void a(Context paramContext, com.ad4screen.sdk.systems.b paramb)
  {
    Object localObject1 = paramContext.getPackageManager();
    try
    {
      ((PackageManager)localObject1).getPackageInfo("com.google.android.gsf", 0);
      Log.debug("GCMPushProvider|Registering to GCM using standard method");
      localObject1 = new Intent("com.google.android.c2dm.intent.REGISTER");
      Object localObject2 = paramContext.getPackageManager();
      if (localObject2 != null)
      {
        localObject2 = ((PackageManager)localObject2).resolveService((Intent)localObject1, 0);
        if (localObject2 != null)
        {
          localObject2 = ((ResolveInfo)localObject2).serviceInfo;
          if (localObject2 != null) {
            ((Intent)localObject1).setClassName(((ServiceInfo)localObject2).packageName, ((ServiceInfo)localObject2).name);
          }
        }
      }
      ((Intent)localObject1).putExtra("app", PendingIntent.getBroadcast(paramContext, 0, new Intent(), 0));
      ((Intent)localObject1).putExtra("sender", paramb.w);
      paramContext.startService((Intent)localObject1);
      return;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      Log.debug("GCMPushProvider|Device does not have package com.google.android.gsf, notification will not be enabled");
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      for (;;) {}
    }
  }
  
  private void a(Context paramContext, String paramString, boolean paramBoolean)
  {
    new f(paramContext, paramString, f.a.b, paramBoolean).run();
  }
  
  private boolean a(Context paramContext, com.ad4screen.sdk.systems.b paramb, PushPlugin paramPushPlugin)
  {
    paramContext = paramPushPlugin.register(paramContext, paramb.w);
    if (paramContext == null)
    {
      Log.internal("GCMPushProvider|No registration id returned from GCM Plugin");
      return false;
    }
    Log.internal("GCMPushProvider|GCM Plugin returned registration id : " + paramContext);
    paramb = new Bundle();
    paramb.putString("registration_id", paramContext);
    updateRegistration(paramb);
    return true;
  }
  
  private void c(Context paramContext)
  {
    Log.debug("GCMPushProvider|Unregistering to GCM using standard method...");
    Intent localIntent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
    Object localObject = paramContext.getPackageManager();
    if (localObject != null)
    {
      localObject = ((PackageManager)localObject).resolveService(localIntent, 0);
      if (localObject != null)
      {
        localObject = ((ResolveInfo)localObject).serviceInfo;
        if (localObject != null) {
          localIntent.setClassName(((ServiceInfo)localObject).packageName, ((ServiceInfo)localObject).name);
        }
      }
    }
    localIntent.putExtra("app", PendingIntent.getBroadcast(paramContext, 0, new Intent(), 0));
    paramContext.startService(localIntent);
  }
  
  private boolean e(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    if ((paramString.contains(".")) || (paramString.length() < 100))
    {
      Log.debug("GCMPushProvider|Registration Id found but invalid.");
      return false;
    }
    return true;
  }
  
  public void a()
  {
    com.ad4screen.sdk.systems.b localb = com.ad4screen.sdk.systems.b.a(this.a.a());
    String str = this.b.g();
    if (localb.w != null)
    {
      if ((str != null) && (!localb.w.equals(str)))
      {
        Log.debug("GCMPushProvider|SenderID is different from previous session, we will register again to GCM");
        c();
      }
      this.b.c(localb.w);
    }
    a(this.a.a());
  }
  
  protected void a(Context paramContext)
  {
    int j = 0;
    com.ad4screen.sdk.systems.b localb = com.ad4screen.sdk.systems.b.a(paramContext);
    if (localb.f == null)
    {
      Log.debug("GCMPushProvider|Skipping GCM registration, sharedId was unavailable");
      return;
    }
    if (!isEnabled())
    {
      Log.debug("GCMPushProvider|Notifications were explicitely disabled, skipping GCM registration.");
      return;
    }
    GCMPlugin localGCMPlugin;
    try
    {
      i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
      String str = b();
      if ((e(str)) && (i == this.b.h()))
      {
        Log.debug("GCMPushProvider|Device was already registered");
        a(paramContext, str, false);
        return;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        Log.internal("GCMPushProvider|Unable to retrieve current app version");
        i = 0;
      }
      if (localb.w == null)
      {
        Log.debug("GCMPushProvider|No senderID provided, notifications will not be enabled.");
        return;
      }
      Log.debug("GCMPushProvider|Registering application '" + paramContext.getPackageName() + "' with GCM with senderID : '" + localb.w + "'...");
      this.b.a(i);
      localGCMPlugin = com.ad4screen.sdk.common.plugins.b.a();
      if (localGCMPlugin != null) {}
    }
    for (int i = 1;; i = 0)
    {
      if (i == 0)
      {
        i = j;
        if (!a(paramContext, localb, localGCMPlugin)) {
          i = 1;
        }
      }
      while (i != 0)
      {
        Log.debug("GCMPushProvider|GCM Plugin encountered an error while registering !");
        a(paramContext, localb);
        return;
      }
      break;
    }
  }
  
  protected void a(String paramString)
  {
    Log.debug("GCMPushProvider|Handling GCM registration status update");
    if (e(paramString))
    {
      Log.debug("GCMPushProvider|GCM registration ID found");
      this.c = 3000L;
      d(paramString);
      a(this.a.a(), paramString, true);
    }
  }
  
  protected void b(Context paramContext)
  {
    int j = 1;
    if (b() == null) {
      Log.debug("GCMPushProvider|Device was already unregistered, skipping GCM unregistration.");
    }
    Log.debug("GCMPushProvider|Unregistering application from GCM...");
    GCMPlugin localGCMPlugin = com.ad4screen.sdk.common.plugins.b.a();
    if (localGCMPlugin == null) {}
    for (int i = 1;; i = 0)
    {
      if (i == 0)
      {
        Log.debug("GCMPushProvider|Unregistering to GCM using GCM Plugin...");
        if (!localGCMPlugin.unregister(paramContext)) {
          i = j;
        }
      }
      for (;;)
      {
        if (i != 0)
        {
          Log.debug("GCMPushProvider|GCM Plugin encountered an error while unregistering !");
          c(paramContext);
        }
        return;
        i = 0;
      }
    }
  }
  
  protected void b(String paramString)
  {
    if (paramString != null)
    {
      Log.debug("GCMPushProvider|GCM unregistered with message : " + paramString);
      this.c = 3000L;
      c();
    }
  }
  
  protected void c(final String paramString)
  {
    if (paramString != null)
    {
      Log.error("GCMPushProvider|GCM registration failed with error: " + paramString);
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
            if (!c.a(c.this, paramString))
            {
              c.this.a(c.a(c.this).a());
              return;
            }
            c.this.b(c.b(c.this).a());
          }
        }, this.c);
      }
    }
    else
    {
      return;
    }
    Log.error("GCMPushProvider|GCM unrecoverable error for this session");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/push/providers/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
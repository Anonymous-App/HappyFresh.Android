package com.ad4screen.sdk.service.modules.push;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.ad4screen.sdk.A4SPopup;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.service.modules.authentication.a.b;
import com.ad4screen.sdk.service.modules.authentication.a.c;
import java.util.Iterator;
import java.util.Set;

@API
public abstract class PushProvider
  implements a
{
  protected A4SService.a a;
  protected b b;
  protected long c = 3000L;
  
  public PushProvider(A4SService.a parama)
  {
    this.a = parama;
    this.b = new b(parama.a());
    com.ad4screen.sdk.systems.f.a().a(d.d.class, new c(null));
    com.ad4screen.sdk.systems.f.a().a(a.b.class, new a(null));
  }
  
  private void a(Bundle paramBundle)
  {
    if (paramBundle.getBoolean("a4scancel", true))
    {
      paramBundle = paramBundle.getString("a4ssysid");
      if (paramBundle == null) {}
    }
    try
    {
      Integer localInteger = Integer.valueOf(paramBundle);
      if (localInteger != null) {
        ((NotificationManager)this.a.a().getSystemService("notification")).cancel(localInteger.intValue());
      }
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      Log.internal("Could not parse a4ssysid parameter : " + paramBundle);
    }
  }
  
  private void a(com.ad4screen.sdk.service.modules.push.model.a parama)
  {
    if (parama != null)
    {
      if (parama.a == com.ad4screen.sdk.model.displayformats.d.a.b)
      {
        if (parama.y)
        {
          com.ad4screen.sdk.service.modules.common.h.a(this.a, parama.b, com.ad4screen.sdk.service.modules.common.d.a.b);
          return;
        }
        com.ad4screen.sdk.service.modules.common.h.a(this.a, parama.b);
        return;
      }
      Log.debug("Push|No Tracking on this notification");
      return;
    }
    Log.warn("Error while retrieving notification parameters when push was opened, push notification could not be tracked");
  }
  
  private void a(String paramString, Intent paramIntent)
  {
    paramIntent = paramIntent.getExtras();
    if (paramIntent != null)
    {
      Object localObject = paramIntent.keySet();
      if (localObject != null)
      {
        Log.debug("Send Custom Params for action " + paramString);
        paramString = ((Set)localObject).iterator();
        while (paramString.hasNext())
        {
          localObject = (String)paramString.next();
          String str = paramIntent.getString((String)localObject);
          Log.debug((String)localObject + " -> " + str);
        }
      }
    }
  }
  
  private void b(Bundle paramBundle)
  {
    Intent localIntent = new Intent("com.ad4screen.sdk.intent.action.CLICKED");
    localIntent.addCategory("com.ad4screen.sdk.intent.category.PUSH_NOTIFICATIONS");
    if (paramBundle != null) {
      localIntent.putExtras(paramBundle);
    }
    a("com.ad4screen.sdk.intent.action.CLICKED", localIntent);
    i.a(this.a.a(), localIntent);
  }
  
  private void c(Bundle paramBundle)
  {
    Intent localIntent = new Intent("com.ad4screen.sdk.intent.action.DISPLAYED");
    localIntent.addCategory("com.ad4screen.sdk.intent.category.PUSH_NOTIFICATIONS");
    if (paramBundle != null) {
      localIntent.putExtras(paramBundle);
    }
    a("com.ad4screen.sdk.intent.action.DISPLAYED", localIntent);
    i.a(this.a.a(), localIntent);
  }
  
  protected abstract void a(Context paramContext);
  
  protected abstract void a(String paramString);
  
  protected String b()
  {
    return this.b.e();
  }
  
  protected abstract void b(Context paramContext);
  
  protected abstract void b(String paramString);
  
  protected void c()
  {
    this.b.f();
  }
  
  protected abstract void c(String paramString);
  
  protected void d(String paramString)
  {
    this.b.b(paramString);
  }
  
  public void handleMessage(Bundle paramBundle)
  {
    if (!isEnabled())
    {
      Log.debug("Push|Received a Push message, but notifications were explicitely disabled, skipping...");
      return;
    }
    if (paramBundle == null)
    {
      Log.warn("Push|Received a Push message, but no content was provided.");
      return;
    }
    com.ad4screen.sdk.service.modules.push.model.a locala = com.ad4screen.sdk.service.modules.push.model.a.a(paramBundle);
    if (locala == null)
    {
      Log.debug("Push|Received Push message but no Accengage parameters were found, skipping...");
      return;
    }
    if ((!locala.w) && ((com.ad4screen.sdk.systems.h.a(this.a.a()).f()) || (com.ad4screen.sdk.systems.h.a(this.a.a()).h())))
    {
      Log.debug("Push|Received Push message but application was in foreground, skipping...");
      return;
    }
    Log.debug("Push|Push message received from Ad4Push, displaying notification...");
    try
    {
      Intent localIntent = g.a(this.a.a());
      localIntent.putExtra("com.ad4screen.sdk.extra.GCM_PAYLOAD", paramBundle);
      PendingIntent localPendingIntent = PendingIntent.getActivity(this.a.a(), locala.v, localIntent, 134217728);
      com.ad4screen.sdk.model.displayformats.f localf = new com.ad4screen.sdk.model.displayformats.f();
      localf.a = localIntent;
      g.a(this.a.a(), localPendingIntent, locala, new b(localf, locala, paramBundle, null));
      return;
    }
    catch (Exception paramBundle)
    {
      Log.error("Push|Error while displaying notification...", paramBundle);
    }
  }
  
  public boolean isEnabled()
  {
    return this.b.a();
  }
  
  public void openedPush(Bundle paramBundle)
  {
    Log.debug("Push|Notification was opened");
    Log.debug("Push|Sending notification click broadcast");
    b(paramBundle);
    Log.debug("Push|Tracking notification click");
    a(com.ad4screen.sdk.service.modules.push.model.a.a(paramBundle));
    a(paramBundle);
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    String str = b();
    this.b.a(paramBoolean);
    if (paramBoolean)
    {
      Log.debug("Push|Notification are now enabled");
      if (str == null) {
        this.a.a(new Runnable()
        {
          public void run()
          {
            PushProvider.this.a(PushProvider.this.a.a());
          }
        });
      }
    }
    do
    {
      return;
      Log.debug("Push|Notification are now disabled");
    } while (str == null);
    this.a.a(new Runnable()
    {
      public void run()
      {
        PushProvider.this.b(PushProvider.this.a.a());
      }
    });
  }
  
  public void updateRegistration(Bundle paramBundle)
  {
    if (paramBundle == null) {}
    do
    {
      return;
      String str = paramBundle.getString("registration_id");
      if (str != null) {
        a(str);
      }
      str = paramBundle.getString("unregistered");
      if (str != null) {
        b(str);
      }
      paramBundle = paramBundle.getString("error");
    } while (paramBundle == null);
    c(paramBundle);
  }
  
  private final class a
    implements a.c
  {
    private a() {}
    
    public void a() {}
    
    public void a(com.ad4screen.sdk.service.modules.authentication.model.a parama, boolean paramBoolean)
    {
      Log.debug("Push|Received sharedId, starting session");
      PushProvider.this.a();
    }
  }
  
  private final class b
    implements d.b
  {
    private final com.ad4screen.sdk.model.displayformats.f b;
    private final com.ad4screen.sdk.service.modules.push.model.a c;
    private final Bundle d;
    
    private b(com.ad4screen.sdk.model.displayformats.f paramf, com.ad4screen.sdk.service.modules.push.model.a parama, Bundle paramBundle)
    {
      this.b = paramf;
      this.c = parama;
      this.d = paramBundle;
    }
    
    private void b()
    {
      int i = 2;
      if (!com.ad4screen.sdk.systems.h.a(PushProvider.this.a.a()).f()) {
        i = 6;
      }
      Intent localIntent = A4SPopup.build(PushProvider.this.a.a(), i, g.a(PushProvider.this.a.a(), this.c, this.b), this.c.v);
      localIntent.addFlags(1484783616);
      PushProvider.this.a.a().startActivity(localIntent);
    }
    
    public void a()
    {
      if ((this.c.f) && (i.a(PushProvider.this.a.a()))) {
        b();
      }
      PushProvider.a(PushProvider.this, this.d);
    }
  }
  
  private final class c
    implements d.a
  {
    private c() {}
    
    public void a()
    {
      Log.debug("Push|Token was uploaded");
    }
    
    public void b() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/push/PushProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
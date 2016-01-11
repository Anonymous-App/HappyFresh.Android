package com.ad4screen.sdk.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.A4SInterstitial;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.c;
import com.ad4screen.sdk.common.compatibility.k.j;
import com.ad4screen.sdk.service.modules.push.g;
import com.ad4screen.sdk.service.modules.push.model.a;
import com.ad4screen.sdk.systems.f;
import com.ad4screen.sdk.systems.k;
import java.lang.ref.WeakReference;
import org.json.JSONException;

public final class b
{
  private static b h;
  private boolean a = false;
  private Intent b;
  private WeakReference<Activity> c = new WeakReference(null);
  private c d;
  private Intent e;
  private final ResultReceiver f;
  private final Context g;
  
  private b(Context paramContext)
  {
    this.g = paramContext.getApplicationContext();
    this.f = new Client.1(this, new Handler(Looper.getMainLooper()));
    this.d = new c();
    paramContext = new IntentFilter();
    paramContext.addAction("com.facebook.sdk.ACTIVE_SESSION_OPENED");
    k.j.a(this.g, this.d, paramContext);
  }
  
  public static b a(Context paramContext)
  {
    if (h == null) {
      h = new b(paramContext);
    }
    return h;
  }
  
  private void a(Uri paramUri)
  {
    com.ad4screen.sdk.service.modules.tracking.webview.e locale = new com.ad4screen.sdk.service.modules.tracking.webview.e(A4S.get(this.g));
    com.ad4screen.sdk.service.modules.tracking.webview.d.a(paramUri).a(locale);
  }
  
  private void a(String paramString)
  {
    Activity localActivity = (Activity)this.c.get();
    com.ad4screen.sdk.common.persistence.e locale = new com.ad4screen.sdk.common.persistence.e();
    try
    {
      paramString = (com.ad4screen.sdk.model.displayformats.d)locale.a(paramString, new com.ad4screen.sdk.model.displayformats.d());
      if (paramString != null)
      {
        k.a(this.g).b(localActivity, paramString);
        return;
      }
      Log.debug("Client|Could not deserialize JSON format");
      return;
    }
    catch (JSONException paramString)
    {
      Log.internal("Client|Could not deserialize Format from JSON", paramString);
    }
  }
  
  private void a(String paramString, int paramInt)
  {
    Activity localActivity = (Activity)this.c.get();
    com.ad4screen.sdk.common.persistence.e locale = new com.ad4screen.sdk.common.persistence.e();
    try
    {
      paramString = (com.ad4screen.sdk.model.displayformats.d)locale.a(paramString, new com.ad4screen.sdk.model.displayformats.d());
      if (paramString != null)
      {
        k.a(this.g).a(localActivity, paramString, paramInt);
        return;
      }
      Log.debug("Client|Could not deserialize JSON format");
      return;
    }
    catch (JSONException paramString)
    {
      Log.internal("Client|Could not deserialize Format from JSON", paramString);
    }
  }
  
  private void a(String paramString1, String paramString2)
  {
    Activity localActivity = (Activity)this.c.get();
    if ((paramString2 != null) && (localActivity != null) && (!paramString2.equals(k.c(localActivity)))) {
      return;
    }
    paramString2 = new com.ad4screen.sdk.common.persistence.e();
    try
    {
      paramString1 = (com.ad4screen.sdk.model.displayformats.d)paramString2.a(paramString1, new com.ad4screen.sdk.model.displayformats.d());
      if (paramString1 != null)
      {
        k.a(this.g).a(localActivity, paramString1);
        return;
      }
    }
    catch (JSONException paramString1)
    {
      Log.internal("Client|Could not deserialize Format from JSON", paramString1);
      return;
    }
    Log.debug("Client|Could not deserialize JSON format to be displayed");
  }
  
  private void b(String paramString)
  {
    k.a(this.g).a((Activity)this.c.get(), paramString);
  }
  
  private void b(String paramString1, String paramString2)
  {
    Activity localActivity = (Activity)this.c.get();
    if (localActivity == null) {
      Log.warn("Client|No activity provided for closing inapp");
    }
    while ((paramString1 != null) && (!paramString1.equals(k.c(localActivity)))) {
      return;
    }
    b(paramString2);
  }
  
  private boolean b(Intent paramIntent)
  {
    return (paramIntent != null) && (paramIntent.getExtras() != null) && (paramIntent.getExtras().getBundle("com.ad4screen.sdk.extra.GCM_PAYLOAD") != null);
  }
  
  private void f()
  {
    k.a(this.g).a();
    k.a(this.g).b();
  }
  
  public ResultReceiver a()
  {
    return this.f;
  }
  
  public void a(Activity paramActivity)
  {
    this.c = new WeakReference(paramActivity);
  }
  
  public void a(Intent paramIntent)
  {
    this.e = paramIntent;
  }
  
  public void a(Bundle paramBundle)
  {
    if (this.c != null)
    {
      Activity localActivity = (Activity)this.c.get();
      if (localActivity != null)
      {
        if (localActivity.getIntent() == null) {
          localActivity.setIntent(new Intent());
        }
        localActivity.getIntent().putExtra("com.ad4screen.sdk.extra.GCM_PAYLOAD", paramBundle);
        b();
      }
    }
  }
  
  public void a(boolean paramBoolean)
  {
    this.a = paramBoolean;
  }
  
  public void b()
  {
    Activity localActivity = (Activity)this.c.get();
    Object localObject2 = this.e;
    if (localActivity == null) {}
    Object localObject1;
    do
    {
      return;
      localObject1 = localObject2;
      if (!b((Intent)localObject2)) {
        localObject1 = localActivity.getIntent();
      }
      if (!this.a) {
        break;
      }
      Log.debug("Push|Push is currently locked. Landing Page will be displayed later");
    } while (!b((Intent)localObject1));
    this.b = ((Intent)localObject1);
    return;
    if (this.b != null) {
      localObject1 = this.b;
    }
    this.b = null;
    if ((localObject1 == null) || (((Intent)localObject1).getExtras() == null))
    {
      Log.debug("Push|Started LaunchActivity intent or extras are null, skipping richpush display");
      return;
    }
    localObject2 = ((Intent)localObject1).getExtras();
    Bundle localBundle = ((Bundle)localObject2).getBundle("com.ad4screen.sdk.extra.GCM_PAYLOAD");
    a locala = a.a(localBundle);
    if (locala == null)
    {
      Log.debug("Push|Could not find push payload in activity extras");
      return;
    }
    Log.debug("Push|Push payload found in activity extras");
    if (locala.z)
    {
      Log.debug("Push|But notification was already displayed, skipping...");
      return;
    }
    f.a().a(new c.b(localBundle));
    if (b(this.e)) {
      this.e = null;
    }
    for (;;)
    {
      localObject2 = g.a(localActivity, locala);
      if (localObject2 != null) {
        break;
      }
      Log.debug("Push|No RichPush was found in activity extras");
      return;
      localBundle.putBoolean("displayed", true);
      localActivity.getIntent().putExtras((Bundle)localObject2);
    }
    Log.debug("Push|RichPush was found in activity extras, starting RichPush");
    localActivity.startActivity(A4SInterstitial.build(localActivity, 0, (com.ad4screen.sdk.model.displayformats.e)localObject2, ((Intent)localObject1).getExtras()));
  }
  
  public void b(Activity paramActivity)
  {
    f();
  }
  
  public void c()
  {
    Intent localIntent = this.e;
    Object localObject;
    if (localIntent != null)
    {
      localObject = localIntent;
      if (localIntent.getData() != null) {}
    }
    else
    {
      localObject = (Activity)this.c.get();
      if (localObject != null) {}
    }
    do
    {
      do
      {
        return;
        localObject = ((Activity)localObject).getIntent();
      } while ((localObject == null) || (((Intent)localObject).getData() == null));
      localObject = ((Intent)localObject).getData();
    } while (localObject == null);
    a((Uri)localObject);
  }
  
  public void d()
  {
    if (this.c != null)
    {
      Activity localActivity = (Activity)this.c.get();
      if (localActivity != null) {
        localActivity.finish();
      }
    }
  }
  
  public boolean e()
  {
    return this.a;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/client/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
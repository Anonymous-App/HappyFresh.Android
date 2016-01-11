package android.support.v4.media.session;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.view.KeyEvent;
import java.util.List;

public class MediaButtonReceiver
  extends BroadcastReceiver
{
  public static KeyEvent handleIntent(MediaSessionCompat paramMediaSessionCompat, Intent paramIntent)
  {
    if ((paramMediaSessionCompat == null) || (paramIntent == null) || (!"android.intent.action.MEDIA_BUTTON".equals(paramIntent.getAction())) || (!paramIntent.hasExtra("android.intent.extra.KEY_EVENT"))) {
      return null;
    }
    paramIntent = (KeyEvent)paramIntent.getParcelableExtra("android.intent.extra.KEY_EVENT");
    paramMediaSessionCompat.getController().dispatchMediaButtonEvent(paramIntent);
    return paramIntent;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Object localObject = new Intent("android.intent.action.MEDIA_BUTTON");
    ((Intent)localObject).setPackage(paramContext.getPackageName());
    localObject = paramContext.getPackageManager().queryIntentServices((Intent)localObject, 0);
    if (((List)localObject).size() != 1) {
      throw new IllegalStateException("Expected 1 Service that handles android.intent.action.MEDIA_BUTTON, found " + ((List)localObject).size());
    }
    localObject = (ResolveInfo)((List)localObject).get(0);
    paramIntent.setComponent(new ComponentName(((ResolveInfo)localObject).serviceInfo.packageName, ((ResolveInfo)localObject).serviceInfo.name));
    paramContext.startService(paramIntent);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/android/support/v4/media/session/MediaButtonReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.optimizely.Core;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Process;
import android.support.annotation.NonNull;
import com.optimizely.Optimizely;

public class AppRestarter
{
  private final String component;
  private final Optimizely optimizely;
  
  public AppRestarter(@NonNull Optimizely paramOptimizely, String paramString)
  {
    this.optimizely = paramOptimizely;
    this.component = paramString;
  }
  
  public void restart()
  {
    Object localObject1 = this.optimizely.getCurrentContext().getApplicationContext();
    if (localObject1 == null)
    {
      this.optimizely.verboseLog(true, this.component, "Context is null, couldn't schedule app restart", new Object[0]);
      return;
    }
    Object localObject2 = ((Context)localObject1).getPackageName();
    localObject2 = PendingIntent.getActivity((Context)localObject1, 5197908, ((Context)localObject1).getPackageManager().getLaunchIntentForPackage((String)localObject2).addFlags(402653184), 268435456);
    localObject1 = (AlarmManager)((Context)localObject1).getSystemService("alarm");
    if (localObject1 == null)
    {
      this.optimizely.verboseLog(true, this.component, "AlarmManager is null, couldn't schedule app restart", new Object[0]);
      return;
    }
    this.optimizely.stopOptimizely();
    ((AlarmManager)localObject1).set(1, System.currentTimeMillis() + 200L, (PendingIntent)localObject2);
    Process.killProcess(Process.myPid());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Core/AppRestarter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
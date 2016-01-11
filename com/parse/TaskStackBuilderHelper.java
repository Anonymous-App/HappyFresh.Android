package com.parse;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

@TargetApi(16)
class TaskStackBuilderHelper
{
  public static void startActivities(Context paramContext, Class<? extends Activity> paramClass, Intent paramIntent)
  {
    paramContext = TaskStackBuilder.create(paramContext);
    paramContext.addParentStack(paramClass);
    paramContext.addNextIntent(paramIntent);
    paramContext.startActivities();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/TaskStackBuilderHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
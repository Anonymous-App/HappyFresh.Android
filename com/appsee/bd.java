package com.appsee;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

class bd
{
  public static Drawable l()
  {
    Application localApplication = jn.i();
    return localApplication.getApplicationInfo().loadIcon(localApplication.getPackageManager());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/bd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
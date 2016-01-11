package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;
import java.io.File;

class zzal
{
  public static int version()
  {
    try
    {
      int i = Integer.parseInt(Build.VERSION.SDK);
      return i;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      zzbg.zzaz("Invalid version number: " + Build.VERSION.SDK);
    }
    return 0;
  }
  
  static boolean zzbe(String paramString)
  {
    if (version() < 9) {
      return false;
    }
    paramString = new File(paramString);
    paramString.setReadable(false, false);
    paramString.setWritable(false, false);
    paramString.setReadable(true, true);
    paramString.setWritable(true, true);
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
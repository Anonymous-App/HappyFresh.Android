package com.happyfresh.utils;

import android.content.Context;
import android.content.res.Resources;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.TimeZone;

public class IANATimeZoneUtils
{
  private static String currentCountry;
  private static String currentTz;
  
  public static String getCountry(Context paramContext)
  {
    String str1 = TimeZone.getDefault().getID();
    if ((currentTz != null) && (currentTz.equals(str1)) && (currentCountry != null)) {
      return currentCountry;
    }
    currentTz = str1;
    paramContext = new Scanner(new InputStreamReader(paramContext.getResources().openRawResource(2131099650)));
    while (paramContext.hasNextLine())
    {
      String str2 = paramContext.nextLine();
      if (str2.contains(str1)) {
        currentCountry = str2.replaceAll("(\\w{2})\\t.+", "$1");
      }
    }
    LogUtils.LOG("IANA Country >> " + currentCountry);
    return currentCountry;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/utils/IANATimeZoneUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
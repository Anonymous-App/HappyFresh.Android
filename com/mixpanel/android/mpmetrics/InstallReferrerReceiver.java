package com.mixpanel.android.mpmetrics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstallReferrerReceiver
  extends BroadcastReceiver
{
  private static final String LOGTAG = "MixpanelAPI.InstRfrRcvr";
  private final Pattern UTM_CAMPAIGN_PATTERN = Pattern.compile("(^|&)utm_campaign=([^&#=]*)([#&]|$)");
  private final Pattern UTM_CONTENT_PATTERN = Pattern.compile("(^|&)utm_content=([^&#=]*)([#&]|$)");
  private final Pattern UTM_MEDIUM_PATTERN = Pattern.compile("(^|&)utm_medium=([^&#=]*)([#&]|$)");
  private final Pattern UTM_SOURCE_PATTERN = Pattern.compile("(^|&)utm_source=([^&#=]*)([#&]|$)");
  private final Pattern UTM_TERM_PATTERN = Pattern.compile("(^|&)utm_term=([^&#=]*)([#&]|$)");
  
  private String find(Matcher paramMatcher)
  {
    if (paramMatcher.find())
    {
      paramMatcher = paramMatcher.group(2);
      if (paramMatcher != null) {
        try
        {
          paramMatcher = URLDecoder.decode(paramMatcher, "UTF-8");
          return paramMatcher;
        }
        catch (UnsupportedEncodingException paramMatcher)
        {
          Log.e("MixpanelAPI.InstRfrRcvr", "Could not decode a parameter into UTF-8");
        }
      }
    }
    return null;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    paramIntent = paramIntent.getExtras();
    if (paramIntent == null) {}
    do
    {
      return;
      str1 = paramIntent.getString("referrer");
    } while (str1 == null);
    paramIntent = new HashMap();
    paramIntent.put("referrer", str1);
    String str2 = find(this.UTM_SOURCE_PATTERN.matcher(str1));
    if (str2 != null) {
      paramIntent.put("utm_source", str2);
    }
    str2 = find(this.UTM_MEDIUM_PATTERN.matcher(str1));
    if (str2 != null) {
      paramIntent.put("utm_medium", str2);
    }
    str2 = find(this.UTM_CAMPAIGN_PATTERN.matcher(str1));
    if (str2 != null) {
      paramIntent.put("utm_campaign", str2);
    }
    str2 = find(this.UTM_CONTENT_PATTERN.matcher(str1));
    if (str2 != null) {
      paramIntent.put("utm_content", str2);
    }
    String str1 = find(this.UTM_TERM_PATTERN.matcher(str1));
    if (str1 != null) {
      paramIntent.put("utm_term", str1);
    }
    PersistentIdentity.writeReferrerPrefs(paramContext, "com.mixpanel.android.mpmetrics.ReferralInfo", paramIntent);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/mpmetrics/InstallReferrerReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
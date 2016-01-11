package com.facebook.messenger;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import bolts.AppLinks;
import com.facebook.FacebookSdk;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessengerUtils
{
  public static final String EXTRA_APP_ID = "com.facebook.orca.extra.APPLICATION_ID";
  public static final String EXTRA_EXTERNAL_URI = "com.facebook.orca.extra.EXTERNAL_URI";
  public static final String EXTRA_IS_COMPOSE = "com.facebook.orca.extra.IS_COMPOSE";
  public static final String EXTRA_IS_REPLY = "com.facebook.orca.extra.IS_REPLY";
  public static final String EXTRA_METADATA = "com.facebook.orca.extra.METADATA";
  public static final String EXTRA_PARTICIPANTS = "com.facebook.orca.extra.PARTICIPANTS";
  public static final String EXTRA_PROTOCOL_VERSION = "com.facebook.orca.extra.PROTOCOL_VERSION";
  public static final String EXTRA_REPLY_TOKEN_KEY = "com.facebook.orca.extra.REPLY_TOKEN";
  public static final String EXTRA_THREAD_TOKEN_KEY = "com.facebook.orca.extra.THREAD_TOKEN";
  public static final String ORCA_THREAD_CATEGORY_20150314 = "com.facebook.orca.category.PLATFORM_THREAD_20150314";
  public static final String PACKAGE_NAME = "com.facebook.orca";
  public static final int PROTOCOL_VERSION_20150314 = 20150314;
  private static final String TAG = "MessengerUtils";
  
  public static void finishShareToMessenger(Activity paramActivity, ShareToMessengerParams paramShareToMessengerParams)
  {
    Object localObject = paramActivity.getIntent();
    Set localSet = ((Intent)localObject).getCategories();
    if (localSet == null)
    {
      paramActivity.setResult(0, null);
      paramActivity.finish();
      return;
    }
    if (localSet.contains("com.facebook.orca.category.PLATFORM_THREAD_20150314"))
    {
      localObject = AppLinks.getAppLinkExtras((Intent)localObject);
      Intent localIntent = new Intent();
      if (localSet.contains("com.facebook.orca.category.PLATFORM_THREAD_20150314"))
      {
        localIntent.putExtra("com.facebook.orca.extra.PROTOCOL_VERSION", 20150314);
        localIntent.putExtra("com.facebook.orca.extra.THREAD_TOKEN", ((Bundle)localObject).getString("com.facebook.orca.extra.THREAD_TOKEN"));
        localIntent.setDataAndType(paramShareToMessengerParams.uri, paramShareToMessengerParams.mimeType);
        localIntent.setFlags(1);
        localIntent.putExtra("com.facebook.orca.extra.APPLICATION_ID", FacebookSdk.getApplicationId());
        localIntent.putExtra("com.facebook.orca.extra.METADATA", paramShareToMessengerParams.metaData);
        localIntent.putExtra("com.facebook.orca.extra.EXTERNAL_URI", paramShareToMessengerParams.externalUri);
        paramActivity.setResult(-1, localIntent);
        paramActivity.finish();
        return;
      }
      throw new RuntimeException();
    }
    paramActivity.setResult(0, null);
    paramActivity.finish();
  }
  
  private static Set<Integer> getAllAvailableProtocolVersions(Context paramContext)
  {
    Object localObject = paramContext.getContentResolver();
    paramContext = new HashSet();
    localObject = ((ContentResolver)localObject).query(Uri.parse("content://com.facebook.orca.provider.MessengerPlatformProvider/versions"), new String[] { "version" }, null, null, null);
    if (localObject != null) {
      try
      {
        int i = ((Cursor)localObject).getColumnIndex("version");
        while (((Cursor)localObject).moveToNext()) {
          paramContext.add(Integer.valueOf(((Cursor)localObject).getInt(i)));
        }
      }
      finally
      {
        ((Cursor)localObject).close();
      }
    }
    return paramContext;
  }
  
  public static MessengerThreadParams getMessengerThreadParamsForIntent(Intent paramIntent)
  {
    Object localObject = paramIntent.getCategories();
    if (localObject == null) {}
    while (!((Set)localObject).contains("com.facebook.orca.category.PLATFORM_THREAD_20150314")) {
      return null;
    }
    paramIntent = AppLinks.getAppLinkExtras(paramIntent);
    localObject = paramIntent.getString("com.facebook.orca.extra.THREAD_TOKEN");
    String str1 = paramIntent.getString("com.facebook.orca.extra.METADATA");
    String str2 = paramIntent.getString("com.facebook.orca.extra.PARTICIPANTS");
    boolean bool1 = paramIntent.getBoolean("com.facebook.orca.extra.IS_REPLY");
    boolean bool2 = paramIntent.getBoolean("com.facebook.orca.extra.IS_COMPOSE");
    paramIntent = MessengerThreadParams.Origin.UNKNOWN;
    if (bool1) {
      paramIntent = MessengerThreadParams.Origin.REPLY_FLOW;
    }
    for (;;)
    {
      return new MessengerThreadParams(paramIntent, (String)localObject, str1, parseParticipants(str2));
      if (bool2) {
        paramIntent = MessengerThreadParams.Origin.COMPOSE_FLOW;
      }
    }
  }
  
  public static boolean hasMessengerInstalled(Context paramContext)
  {
    try
    {
      paramContext.getPackageManager().getPackageInfo("com.facebook.orca", 0);
      return true;
    }
    catch (PackageManager.NameNotFoundException paramContext) {}
    return false;
  }
  
  public static void openMessengerInPlayStore(Context paramContext)
  {
    try
    {
      startViewUri(paramContext, "market://details?id=com.facebook.orca");
      return;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      startViewUri(paramContext, "http://play.google.com/store/apps/details?id=com.facebook.orca");
    }
  }
  
  private static List<String> parseParticipants(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      paramString = Collections.emptyList();
      return paramString;
    }
    String[] arrayOfString = paramString.split(",");
    ArrayList localArrayList = new ArrayList();
    int j = arrayOfString.length;
    int i = 0;
    for (;;)
    {
      paramString = localArrayList;
      if (i >= j) {
        break;
      }
      localArrayList.add(arrayOfString[i].trim());
      i += 1;
    }
  }
  
  public static void shareToMessenger(Activity paramActivity, int paramInt, ShareToMessengerParams paramShareToMessengerParams)
  {
    if (!hasMessengerInstalled(paramActivity))
    {
      openMessengerInPlayStore(paramActivity);
      return;
    }
    if (getAllAvailableProtocolVersions(paramActivity).contains(Integer.valueOf(20150314)))
    {
      shareToMessenger20150314(paramActivity, paramInt, paramShareToMessengerParams);
      return;
    }
    openMessengerInPlayStore(paramActivity);
  }
  
  private static void shareToMessenger20150314(Activity paramActivity, int paramInt, ShareToMessengerParams paramShareToMessengerParams)
  {
    try
    {
      Intent localIntent = new Intent("android.intent.action.SEND");
      localIntent.setFlags(1);
      localIntent.setPackage("com.facebook.orca");
      localIntent.putExtra("android.intent.extra.STREAM", paramShareToMessengerParams.uri);
      localIntent.setType(paramShareToMessengerParams.mimeType);
      String str = FacebookSdk.getApplicationId();
      if (str != null)
      {
        localIntent.putExtra("com.facebook.orca.extra.PROTOCOL_VERSION", 20150314);
        localIntent.putExtra("com.facebook.orca.extra.APPLICATION_ID", str);
        localIntent.putExtra("com.facebook.orca.extra.METADATA", paramShareToMessengerParams.metaData);
        localIntent.putExtra("com.facebook.orca.extra.EXTERNAL_URI", paramShareToMessengerParams.externalUri);
      }
      paramActivity.startActivityForResult(localIntent, paramInt);
      return;
    }
    catch (ActivityNotFoundException paramShareToMessengerParams)
    {
      paramActivity.startActivity(paramActivity.getPackageManager().getLaunchIntentForPackage("com.facebook.orca"));
    }
  }
  
  private static void startViewUri(Context paramContext, String paramString)
  {
    paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/messenger/MessengerUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
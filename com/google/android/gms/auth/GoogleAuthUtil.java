package com.google.android.gms.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzjr;
import java.io.IOException;
import java.net.URISyntaxException;

public final class GoogleAuthUtil
{
  public static final int CHANGE_TYPE_ACCOUNT_ADDED = 1;
  public static final int CHANGE_TYPE_ACCOUNT_REMOVED = 2;
  public static final int CHANGE_TYPE_ACCOUNT_RENAMED_FROM = 3;
  public static final int CHANGE_TYPE_ACCOUNT_RENAMED_TO = 4;
  public static final String GOOGLE_ACCOUNT_TYPE = "com.google";
  public static final String KEY_ANDROID_PACKAGE_NAME;
  public static final String KEY_CALLER_UID;
  public static final String KEY_REQUEST_ACTIONS = "request_visible_actions";
  @Deprecated
  public static final String KEY_REQUEST_VISIBLE_ACTIVITIES = "request_visible_actions";
  public static final String KEY_SUPPRESS_PROGRESS_SCREEN = "suppressProgressScreen";
  private static final ComponentName zzOB;
  private static final ComponentName zzOC;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      KEY_CALLER_UID = "callerUid";
      if (Build.VERSION.SDK_INT < 14) {
        break label58;
      }
    }
    label58:
    for (;;)
    {
      KEY_ANDROID_PACKAGE_NAME = "androidPackageName";
      zzOB = new ComponentName("com.google.android.gms", "com.google.android.gms.auth.GetToken");
      zzOC = new ComponentName("com.google.android.gms", "com.google.android.gms.recovery.RecoveryService");
      return;
      break;
    }
  }
  
  /* Error */
  public static void clearToken(Context paramContext, String paramString)
    throws GooglePlayServicesAvailabilityException, GoogleAuthException, IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 82	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   4: astore_2
    //   5: ldc 84
    //   7: invokestatic 90	com/google/android/gms/common/internal/zzu:zzbZ	(Ljava/lang/String;)V
    //   10: aload_2
    //   11: invokestatic 94	com/google/android/gms/auth/GoogleAuthUtil:zzY	(Landroid/content/Context;)V
    //   14: new 96	android/os/Bundle
    //   17: dup
    //   18: invokespecial 97	android/os/Bundle:<init>	()V
    //   21: astore_3
    //   22: aload_0
    //   23: invokevirtual 101	android/content/Context:getApplicationInfo	()Landroid/content/pm/ApplicationInfo;
    //   26: getfield 106	android/content/pm/ApplicationInfo:packageName	Ljava/lang/String;
    //   29: astore_0
    //   30: aload_3
    //   31: ldc 108
    //   33: aload_0
    //   34: invokevirtual 111	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   37: aload_3
    //   38: getstatic 45	com/google/android/gms/auth/GoogleAuthUtil:KEY_ANDROID_PACKAGE_NAME	Ljava/lang/String;
    //   41: invokevirtual 115	android/os/Bundle:containsKey	(Ljava/lang/String;)Z
    //   44: ifne +11 -> 55
    //   47: aload_3
    //   48: getstatic 45	com/google/android/gms/auth/GoogleAuthUtil:KEY_ANDROID_PACKAGE_NAME	Ljava/lang/String;
    //   51: aload_0
    //   52: invokevirtual 111	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   55: new 117	com/google/android/gms/common/zza
    //   58: dup
    //   59: invokespecial 118	com/google/android/gms/common/zza:<init>	()V
    //   62: astore_0
    //   63: aload_2
    //   64: invokestatic 124	com/google/android/gms/common/internal/zzk:zzah	(Landroid/content/Context;)Lcom/google/android/gms/common/internal/zzk;
    //   67: astore_2
    //   68: aload_2
    //   69: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzOB	Landroid/content/ComponentName;
    //   72: aload_0
    //   73: ldc 126
    //   75: invokevirtual 130	com/google/android/gms/common/internal/zzk:zza	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)Z
    //   78: ifeq +98 -> 176
    //   81: aload_0
    //   82: invokevirtual 134	com/google/android/gms/common/zza:zzmh	()Landroid/os/IBinder;
    //   85: invokestatic 139	com/google/android/gms/internal/zzau$zza:zza	(Landroid/os/IBinder;)Lcom/google/android/gms/internal/zzau;
    //   88: aload_1
    //   89: aload_3
    //   90: invokeinterface 144 3 0
    //   95: astore_1
    //   96: aload_1
    //   97: ldc -110
    //   99: invokevirtual 150	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   102: astore_3
    //   103: aload_1
    //   104: ldc -104
    //   106: invokevirtual 155	android/os/Bundle:getBoolean	(Ljava/lang/String;)Z
    //   109: ifne +45 -> 154
    //   112: new 70	com/google/android/gms/auth/GoogleAuthException
    //   115: dup
    //   116: aload_3
    //   117: invokespecial 157	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   120: athrow
    //   121: astore_1
    //   122: ldc 126
    //   124: ldc -97
    //   126: aload_1
    //   127: invokestatic 165	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   130: pop
    //   131: new 72	java/io/IOException
    //   134: dup
    //   135: ldc -89
    //   137: invokespecial 168	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   140: athrow
    //   141: astore_1
    //   142: aload_2
    //   143: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzOB	Landroid/content/ComponentName;
    //   146: aload_0
    //   147: ldc 126
    //   149: invokevirtual 172	com/google/android/gms/common/internal/zzk:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   152: aload_1
    //   153: athrow
    //   154: aload_2
    //   155: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzOB	Landroid/content/ComponentName;
    //   158: aload_0
    //   159: ldc 126
    //   161: invokevirtual 172	com/google/android/gms/common/internal/zzk:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   164: return
    //   165: astore_1
    //   166: new 70	com/google/android/gms/auth/GoogleAuthException
    //   169: dup
    //   170: ldc -82
    //   172: invokespecial 157	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   175: athrow
    //   176: new 72	java/io/IOException
    //   179: dup
    //   180: ldc -80
    //   182: invokespecial 168	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   185: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	186	0	paramContext	Context
    //   0	186	1	paramString	String
    //   4	151	2	localObject1	Object
    //   21	96	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   81	121	121	android/os/RemoteException
    //   81	121	141	finally
    //   122	141	141	finally
    //   166	176	141	finally
    //   81	121	165	java/lang/InterruptedException
  }
  
  /* Error */
  public static java.util.List<AccountChangeEvent> getAccountChangeEvents(Context paramContext, int paramInt, String paramString)
    throws GoogleAuthException, IOException
  {
    // Byte code:
    //   0: aload_2
    //   1: ldc -75
    //   3: invokestatic 185	com/google/android/gms/common/internal/zzu:zzh	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;
    //   6: pop
    //   7: ldc 84
    //   9: invokestatic 90	com/google/android/gms/common/internal/zzu:zzbZ	(Ljava/lang/String;)V
    //   12: aload_0
    //   13: invokevirtual 82	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   16: astore_3
    //   17: aload_3
    //   18: invokestatic 94	com/google/android/gms/auth/GoogleAuthUtil:zzY	(Landroid/content/Context;)V
    //   21: new 117	com/google/android/gms/common/zza
    //   24: dup
    //   25: invokespecial 118	com/google/android/gms/common/zza:<init>	()V
    //   28: astore_0
    //   29: aload_3
    //   30: invokestatic 124	com/google/android/gms/common/internal/zzk:zzah	(Landroid/content/Context;)Lcom/google/android/gms/common/internal/zzk;
    //   33: astore_3
    //   34: aload_3
    //   35: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzOB	Landroid/content/ComponentName;
    //   38: aload_0
    //   39: ldc 126
    //   41: invokevirtual 130	com/google/android/gms/common/internal/zzk:zza	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)Z
    //   44: ifeq +90 -> 134
    //   47: aload_0
    //   48: invokevirtual 134	com/google/android/gms/common/zza:zzmh	()Landroid/os/IBinder;
    //   51: invokestatic 139	com/google/android/gms/internal/zzau$zza:zza	(Landroid/os/IBinder;)Lcom/google/android/gms/internal/zzau;
    //   54: new 187	com/google/android/gms/auth/AccountChangeEventsRequest
    //   57: dup
    //   58: invokespecial 188	com/google/android/gms/auth/AccountChangeEventsRequest:<init>	()V
    //   61: aload_2
    //   62: invokevirtual 192	com/google/android/gms/auth/AccountChangeEventsRequest:setAccountName	(Ljava/lang/String;)Lcom/google/android/gms/auth/AccountChangeEventsRequest;
    //   65: iload_1
    //   66: invokevirtual 196	com/google/android/gms/auth/AccountChangeEventsRequest:setEventIndex	(I)Lcom/google/android/gms/auth/AccountChangeEventsRequest;
    //   69: invokeinterface 199 2 0
    //   74: invokevirtual 205	com/google/android/gms/auth/AccountChangeEventsResponse:getEvents	()Ljava/util/List;
    //   77: astore_2
    //   78: aload_3
    //   79: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzOB	Landroid/content/ComponentName;
    //   82: aload_0
    //   83: ldc 126
    //   85: invokevirtual 172	com/google/android/gms/common/internal/zzk:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   88: aload_2
    //   89: areturn
    //   90: astore_2
    //   91: ldc 126
    //   93: ldc -97
    //   95: aload_2
    //   96: invokestatic 165	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   99: pop
    //   100: new 72	java/io/IOException
    //   103: dup
    //   104: ldc -89
    //   106: invokespecial 168	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   109: athrow
    //   110: astore_2
    //   111: aload_3
    //   112: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzOB	Landroid/content/ComponentName;
    //   115: aload_0
    //   116: ldc 126
    //   118: invokevirtual 172	com/google/android/gms/common/internal/zzk:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   121: aload_2
    //   122: athrow
    //   123: astore_2
    //   124: new 70	com/google/android/gms/auth/GoogleAuthException
    //   127: dup
    //   128: ldc -82
    //   130: invokespecial 157	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   133: athrow
    //   134: new 72	java/io/IOException
    //   137: dup
    //   138: ldc -80
    //   140: invokespecial 168	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   143: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	144	0	paramContext	Context
    //   0	144	1	paramInt	int
    //   0	144	2	paramString	String
    //   16	96	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   47	78	90	android/os/RemoteException
    //   47	78	110	finally
    //   91	110	110	finally
    //   124	134	110	finally
    //   47	78	123	java/lang/InterruptedException
  }
  
  public static String getAccountId(Context paramContext, String paramString)
    throws GoogleAuthException, IOException
  {
    zzu.zzh(paramString, "accountName must be provided");
    zzu.zzbZ("Calling this from your main thread can lead to deadlock");
    zzY(paramContext.getApplicationContext());
    return getToken(paramContext, paramString, "^^_account_id_^^", new Bundle());
  }
  
  public static String getToken(Context paramContext, Account paramAccount, String paramString)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    return getToken(paramContext, paramAccount, paramString, new Bundle());
  }
  
  public static String getToken(Context paramContext, Account paramAccount, String paramString, Bundle paramBundle)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    return zza(paramContext, paramAccount, paramString, paramBundle).getString("authtoken");
  }
  
  @Deprecated
  public static String getToken(Context paramContext, String paramString1, String paramString2)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    return getToken(paramContext, new Account(paramString1, "com.google"), paramString2);
  }
  
  @Deprecated
  public static String getToken(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    return getToken(paramContext, new Account(paramString1, "com.google"), paramString2, paramBundle);
  }
  
  public static String getTokenWithNotification(Context paramContext, Account paramAccount, String paramString, Bundle paramBundle)
    throws IOException, UserRecoverableNotifiedException, GoogleAuthException
  {
    Bundle localBundle = paramBundle;
    if (paramBundle == null) {
      localBundle = new Bundle();
    }
    localBundle.putBoolean("handle_notification", true);
    return zzb(paramContext, paramAccount, paramString, localBundle);
  }
  
  public static String getTokenWithNotification(Context paramContext, Account paramAccount, String paramString, Bundle paramBundle, Intent paramIntent)
    throws IOException, UserRecoverableNotifiedException, GoogleAuthException
  {
    zzi(paramIntent);
    Bundle localBundle = paramBundle;
    if (paramBundle == null) {
      localBundle = new Bundle();
    }
    localBundle.putParcelable("callback_intent", paramIntent);
    localBundle.putBoolean("handle_notification", true);
    return zzb(paramContext, paramAccount, paramString, localBundle);
  }
  
  public static String getTokenWithNotification(Context paramContext, Account paramAccount, String paramString1, Bundle paramBundle1, String paramString2, Bundle paramBundle2)
    throws IOException, UserRecoverableNotifiedException, GoogleAuthException
  {
    if (TextUtils.isEmpty(paramString2)) {
      throw new IllegalArgumentException("Authority cannot be empty or null.");
    }
    Bundle localBundle = paramBundle1;
    if (paramBundle1 == null) {
      localBundle = new Bundle();
    }
    paramBundle1 = paramBundle2;
    if (paramBundle2 == null) {
      paramBundle1 = new Bundle();
    }
    ContentResolver.validateSyncExtrasBundle(paramBundle1);
    localBundle.putString("authority", paramString2);
    localBundle.putBundle("sync_extras", paramBundle1);
    localBundle.putBoolean("handle_notification", true);
    return zzb(paramContext, paramAccount, paramString1, localBundle);
  }
  
  @Deprecated
  public static String getTokenWithNotification(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
    throws IOException, UserRecoverableNotifiedException, GoogleAuthException
  {
    return getTokenWithNotification(paramContext, new Account(paramString1, "com.google"), paramString2, paramBundle);
  }
  
  @Deprecated
  public static String getTokenWithNotification(Context paramContext, String paramString1, String paramString2, Bundle paramBundle, Intent paramIntent)
    throws IOException, UserRecoverableNotifiedException, GoogleAuthException
  {
    return getTokenWithNotification(paramContext, new Account(paramString1, "com.google"), paramString2, paramBundle, paramIntent);
  }
  
  @Deprecated
  public static String getTokenWithNotification(Context paramContext, String paramString1, String paramString2, Bundle paramBundle1, String paramString3, Bundle paramBundle2)
    throws IOException, UserRecoverableNotifiedException, GoogleAuthException
  {
    return getTokenWithNotification(paramContext, new Account(paramString1, "com.google"), paramString2, paramBundle1, paramString3, paramBundle2);
  }
  
  @Deprecated
  public static void invalidateToken(Context paramContext, String paramString)
  {
    AccountManager.get(paramContext).invalidateAuthToken("com.google", paramString);
  }
  
  private static void zzY(Context paramContext)
    throws GoogleAuthException
  {
    try
    {
      GooglePlayServicesUtil.zzY(paramContext);
      return;
    }
    catch (GooglePlayServicesRepairableException paramContext)
    {
      throw new GooglePlayServicesAvailabilityException(paramContext.getConnectionStatusCode(), paramContext.getMessage(), paramContext.getIntent());
    }
    catch (GooglePlayServicesNotAvailableException paramContext)
    {
      throw new GoogleAuthException(paramContext.getMessage());
    }
  }
  
  /* Error */
  public static Bundle zza(Context paramContext, Account paramAccount, String paramString, Bundle paramBundle)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 82	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   4: astore 5
    //   6: ldc 84
    //   8: invokestatic 90	com/google/android/gms/common/internal/zzu:zzbZ	(Ljava/lang/String;)V
    //   11: aload 5
    //   13: invokestatic 94	com/google/android/gms/auth/GoogleAuthUtil:zzY	(Landroid/content/Context;)V
    //   16: aload_3
    //   17: ifnonnull +150 -> 167
    //   20: new 96	android/os/Bundle
    //   23: dup
    //   24: invokespecial 97	android/os/Bundle:<init>	()V
    //   27: astore_3
    //   28: aload_0
    //   29: invokevirtual 101	android/content/Context:getApplicationInfo	()Landroid/content/pm/ApplicationInfo;
    //   32: getfield 106	android/content/pm/ApplicationInfo:packageName	Ljava/lang/String;
    //   35: astore_0
    //   36: aload_3
    //   37: ldc 108
    //   39: aload_0
    //   40: invokevirtual 111	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   43: aload_3
    //   44: getstatic 45	com/google/android/gms/auth/GoogleAuthUtil:KEY_ANDROID_PACKAGE_NAME	Ljava/lang/String;
    //   47: invokevirtual 150	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   50: invokestatic 262	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   53: ifeq +11 -> 64
    //   56: aload_3
    //   57: getstatic 45	com/google/android/gms/auth/GoogleAuthUtil:KEY_ANDROID_PACKAGE_NAME	Ljava/lang/String;
    //   60: aload_0
    //   61: invokevirtual 111	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   64: new 117	com/google/android/gms/common/zza
    //   67: dup
    //   68: invokespecial 118	com/google/android/gms/common/zza:<init>	()V
    //   71: astore_0
    //   72: aload 5
    //   74: invokestatic 124	com/google/android/gms/common/internal/zzk:zzah	(Landroid/content/Context;)Lcom/google/android/gms/common/internal/zzk;
    //   77: astore 5
    //   79: aload 5
    //   81: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzOB	Landroid/content/ComponentName;
    //   84: aload_0
    //   85: ldc 126
    //   87: invokevirtual 130	com/google/android/gms/common/internal/zzk:zza	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)Z
    //   90: ifeq +189 -> 279
    //   93: aload_0
    //   94: invokevirtual 134	com/google/android/gms/common/zza:zzmh	()Landroid/os/IBinder;
    //   97: invokestatic 139	com/google/android/gms/internal/zzau$zza:zza	(Landroid/os/IBinder;)Lcom/google/android/gms/internal/zzau;
    //   100: aload_1
    //   101: aload_2
    //   102: aload_3
    //   103: invokeinterface 325 4 0
    //   108: astore_2
    //   109: aload_2
    //   110: ifnonnull +69 -> 179
    //   113: ldc 126
    //   115: ldc_w 327
    //   118: invokestatic 331	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   121: pop
    //   122: new 70	com/google/android/gms/auth/GoogleAuthException
    //   125: dup
    //   126: ldc_w 333
    //   129: invokespecial 157	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   132: athrow
    //   133: astore_1
    //   134: ldc 126
    //   136: ldc -97
    //   138: aload_1
    //   139: invokestatic 165	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   142: pop
    //   143: new 72	java/io/IOException
    //   146: dup
    //   147: ldc -89
    //   149: invokespecial 168	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   152: athrow
    //   153: astore_1
    //   154: aload 5
    //   156: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzOB	Landroid/content/ComponentName;
    //   159: aload_0
    //   160: ldc 126
    //   162: invokevirtual 172	com/google/android/gms/common/internal/zzk:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   165: aload_1
    //   166: athrow
    //   167: new 96	android/os/Bundle
    //   170: dup
    //   171: aload_3
    //   172: invokespecial 335	android/os/Bundle:<init>	(Landroid/os/Bundle;)V
    //   175: astore_3
    //   176: goto -148 -> 28
    //   179: aload_2
    //   180: ldc -30
    //   182: invokevirtual 150	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   185: invokestatic 262	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   188: istore 4
    //   190: iload 4
    //   192: ifne +16 -> 208
    //   195: aload 5
    //   197: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzOB	Landroid/content/ComponentName;
    //   200: aload_0
    //   201: ldc 126
    //   203: invokevirtual 172	com/google/android/gms/common/internal/zzk:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   206: aload_2
    //   207: areturn
    //   208: aload_2
    //   209: ldc -110
    //   211: invokevirtual 150	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   214: astore_1
    //   215: aload_2
    //   216: ldc_w 337
    //   219: invokevirtual 341	android/os/Bundle:getParcelable	(Ljava/lang/String;)Landroid/os/Parcelable;
    //   222: checkcast 343	android/content/Intent
    //   225: astore_2
    //   226: aload_1
    //   227: invokestatic 346	com/google/android/gms/auth/GoogleAuthUtil:zzbv	(Ljava/lang/String;)Z
    //   230: ifeq +24 -> 254
    //   233: new 218	com/google/android/gms/auth/UserRecoverableAuthException
    //   236: dup
    //   237: aload_1
    //   238: aload_2
    //   239: invokespecial 349	com/google/android/gms/auth/UserRecoverableAuthException:<init>	(Ljava/lang/String;Landroid/content/Intent;)V
    //   242: athrow
    //   243: astore_1
    //   244: new 70	com/google/android/gms/auth/GoogleAuthException
    //   247: dup
    //   248: ldc -82
    //   250: invokespecial 157	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   253: athrow
    //   254: aload_1
    //   255: invokestatic 352	com/google/android/gms/auth/GoogleAuthUtil:zzbu	(Ljava/lang/String;)Z
    //   258: ifeq +12 -> 270
    //   261: new 72	java/io/IOException
    //   264: dup
    //   265: aload_1
    //   266: invokespecial 168	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   269: athrow
    //   270: new 70	com/google/android/gms/auth/GoogleAuthException
    //   273: dup
    //   274: aload_1
    //   275: invokespecial 157	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   278: athrow
    //   279: new 72	java/io/IOException
    //   282: dup
    //   283: ldc -80
    //   285: invokespecial 168	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   288: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	289	0	paramContext	Context
    //   0	289	1	paramAccount	Account
    //   0	289	2	paramString	String
    //   0	289	3	paramBundle	Bundle
    //   188	3	4	bool	boolean
    //   4	192	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   93	109	133	android/os/RemoteException
    //   113	133	133	android/os/RemoteException
    //   179	190	133	android/os/RemoteException
    //   208	243	133	android/os/RemoteException
    //   254	270	133	android/os/RemoteException
    //   270	279	133	android/os/RemoteException
    //   93	109	153	finally
    //   113	133	153	finally
    //   134	153	153	finally
    //   179	190	153	finally
    //   208	243	153	finally
    //   244	254	153	finally
    //   254	270	153	finally
    //   270	279	153	finally
    //   93	109	243	java/lang/InterruptedException
    //   113	133	243	java/lang/InterruptedException
    //   179	190	243	java/lang/InterruptedException
    //   208	243	243	java/lang/InterruptedException
    //   254	270	243	java/lang/InterruptedException
    //   270	279	243	java/lang/InterruptedException
  }
  
  private static String zzb(Context paramContext, Account paramAccount, String paramString, Bundle paramBundle)
    throws IOException, GoogleAuthException
  {
    Bundle localBundle = paramBundle;
    if (paramBundle == null) {
      localBundle = new Bundle();
    }
    try
    {
      paramAccount = getToken(paramContext, paramAccount, paramString, localBundle);
      GooglePlayServicesUtil.zzac(paramContext);
      return paramAccount;
    }
    catch (GooglePlayServicesAvailabilityException paramAccount)
    {
      GooglePlayServicesUtil.showErrorNotification(paramAccount.getConnectionStatusCode(), paramContext);
      throw new UserRecoverableNotifiedException("User intervention required. Notification has been pushed.");
    }
    catch (UserRecoverableAuthException paramAccount)
    {
      GooglePlayServicesUtil.zzac(paramContext);
      throw new UserRecoverableNotifiedException("User intervention required. Notification has been pushed.");
    }
  }
  
  private static boolean zzbu(String paramString)
  {
    return ("NetworkError".equals(paramString)) || ("ServiceUnavailable".equals(paramString)) || ("Timeout".equals(paramString));
  }
  
  private static boolean zzbv(String paramString)
  {
    return ("BadAuthentication".equals(paramString)) || ("CaptchaRequired".equals(paramString)) || ("DeviceManagementRequiredOrSyncDisabled".equals(paramString)) || ("NeedPermission".equals(paramString)) || ("NeedsBrowser".equals(paramString)) || ("UserCancel".equals(paramString)) || ("AppDownloadRequired".equals(paramString)) || (zzjr.zzPR.zzld().equals(paramString)) || (zzjr.zzPS.zzld().equals(paramString)) || (zzjr.zzPT.zzld().equals(paramString)) || (zzjr.zzPU.zzld().equals(paramString)) || (zzjr.zzPV.zzld().equals(paramString)) || (zzjr.zzPW.zzld().equals(paramString)) || (zzjr.zzPP.zzld().equals(paramString));
  }
  
  private static void zzi(Intent paramIntent)
  {
    if (paramIntent == null) {
      throw new IllegalArgumentException("Callback cannot be null.");
    }
    paramIntent = paramIntent.toUri(1);
    try
    {
      Intent.parseUri(paramIntent, 1);
      return;
    }
    catch (URISyntaxException paramIntent)
    {
      throw new IllegalArgumentException("Parameter callback contains invalid data. It must be serializable using toUri() and parseUri().");
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/GoogleAuthUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
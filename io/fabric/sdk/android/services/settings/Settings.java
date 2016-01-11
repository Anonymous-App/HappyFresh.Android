package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class Settings
{
  public static final String SETTINGS_CACHE_FILENAME = "com.crashlytics.settings.json";
  private static final String SETTINGS_URL_FORMAT = "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings";
  private boolean initialized = false;
  private SettingsController settingsController;
  private final AtomicReference<SettingsData> settingsData = new AtomicReference();
  private final CountDownLatch settingsDataLatch = new CountDownLatch(1);
  
  public static Settings getInstance()
  {
    return LazyHolder.INSTANCE;
  }
  
  private void setSettingsData(SettingsData paramSettingsData)
  {
    this.settingsData.set(paramSettingsData);
    this.settingsDataLatch.countDown();
  }
  
  public SettingsData awaitSettingsData()
  {
    try
    {
      this.settingsDataLatch.await();
      SettingsData localSettingsData = (SettingsData)this.settingsData.get();
      return localSettingsData;
    }
    catch (InterruptedException localInterruptedException)
    {
      Fabric.getLogger().e("Fabric", "Interrupted while waiting for settings data.");
    }
    return null;
  }
  
  public void clearSettings()
  {
    this.settingsData.set(null);
  }
  
  /* Error */
  public Settings initialize(io.fabric.sdk.android.Kit paramKit, io.fabric.sdk.android.services.common.IdManager paramIdManager, io.fabric.sdk.android.services.network.HttpRequestFactory paramHttpRequestFactory, String paramString1, String paramString2, String paramString3)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 46	io/fabric/sdk/android/services/settings/Settings:initialized	Z
    //   6: istore 7
    //   8: iload 7
    //   10: ifeq +7 -> 17
    //   13: aload_0
    //   14: monitorexit
    //   15: aload_0
    //   16: areturn
    //   17: aload_0
    //   18: getfield 97	io/fabric/sdk/android/services/settings/Settings:settingsController	Lio/fabric/sdk/android/services/settings/SettingsController;
    //   21: ifnonnull +164 -> 185
    //   24: aload_1
    //   25: invokevirtual 103	io/fabric/sdk/android/Kit:getContext	()Landroid/content/Context;
    //   28: astore 8
    //   30: aload_2
    //   31: invokevirtual 109	io/fabric/sdk/android/services/common/IdManager:getAppIdentifier	()Ljava/lang/String;
    //   34: astore 9
    //   36: new 111	io/fabric/sdk/android/services/common/ApiKey
    //   39: dup
    //   40: invokespecial 112	io/fabric/sdk/android/services/common/ApiKey:<init>	()V
    //   43: aload 8
    //   45: invokevirtual 116	io/fabric/sdk/android/services/common/ApiKey:getValue	(Landroid/content/Context;)Ljava/lang/String;
    //   48: astore 10
    //   50: aload_2
    //   51: invokevirtual 119	io/fabric/sdk/android/services/common/IdManager:getInstallerPackageName	()Ljava/lang/String;
    //   54: astore 11
    //   56: new 121	io/fabric/sdk/android/services/common/SystemCurrentTimeProvider
    //   59: dup
    //   60: invokespecial 122	io/fabric/sdk/android/services/common/SystemCurrentTimeProvider:<init>	()V
    //   63: astore 12
    //   65: new 124	io/fabric/sdk/android/services/settings/DefaultSettingsJsonTransform
    //   68: dup
    //   69: invokespecial 125	io/fabric/sdk/android/services/settings/DefaultSettingsJsonTransform:<init>	()V
    //   72: astore 13
    //   74: new 127	io/fabric/sdk/android/services/settings/DefaultCachedSettingsIo
    //   77: dup
    //   78: aload_1
    //   79: invokespecial 130	io/fabric/sdk/android/services/settings/DefaultCachedSettingsIo:<init>	(Lio/fabric/sdk/android/Kit;)V
    //   82: astore 14
    //   84: aload 8
    //   86: invokestatic 135	io/fabric/sdk/android/services/common/CommonUtils:getAppIconHashOrNull	(Landroid/content/Context;)Ljava/lang/String;
    //   89: astore 15
    //   91: new 137	io/fabric/sdk/android/services/settings/DefaultSettingsSpiCall
    //   94: dup
    //   95: aload_1
    //   96: aload 6
    //   98: getstatic 143	java/util/Locale:US	Ljava/util/Locale;
    //   101: ldc 19
    //   103: iconst_1
    //   104: anewarray 4	java/lang/Object
    //   107: dup
    //   108: iconst_0
    //   109: aload 9
    //   111: aastore
    //   112: invokestatic 149	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   115: aload_3
    //   116: invokespecial 152	io/fabric/sdk/android/services/settings/DefaultSettingsSpiCall:<init>	(Lio/fabric/sdk/android/Kit;Ljava/lang/String;Ljava/lang/String;Lio/fabric/sdk/android/services/network/HttpRequestFactory;)V
    //   119: astore_3
    //   120: aload_0
    //   121: new 154	io/fabric/sdk/android/services/settings/DefaultSettingsController
    //   124: dup
    //   125: aload_1
    //   126: new 156	io/fabric/sdk/android/services/settings/SettingsRequest
    //   129: dup
    //   130: aload 10
    //   132: aload_2
    //   133: aload 10
    //   135: aload 9
    //   137: invokevirtual 160	io/fabric/sdk/android/services/common/IdManager:createIdHeaderValue	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   140: iconst_1
    //   141: anewarray 145	java/lang/String
    //   144: dup
    //   145: iconst_0
    //   146: aload 8
    //   148: invokestatic 163	io/fabric/sdk/android/services/common/CommonUtils:resolveBuildId	(Landroid/content/Context;)Ljava/lang/String;
    //   151: aastore
    //   152: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:createInstanceIdFrom	([Ljava/lang/String;)Ljava/lang/String;
    //   155: aload 5
    //   157: aload 4
    //   159: aload 11
    //   161: invokestatic 173	io/fabric/sdk/android/services/common/DeliveryMechanism:determineFrom	(Ljava/lang/String;)Lio/fabric/sdk/android/services/common/DeliveryMechanism;
    //   164: invokevirtual 177	io/fabric/sdk/android/services/common/DeliveryMechanism:getId	()I
    //   167: aload 15
    //   169: invokespecial 180	io/fabric/sdk/android/services/settings/SettingsRequest:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V
    //   172: aload 12
    //   174: aload 13
    //   176: aload 14
    //   178: aload_3
    //   179: invokespecial 183	io/fabric/sdk/android/services/settings/DefaultSettingsController:<init>	(Lio/fabric/sdk/android/Kit;Lio/fabric/sdk/android/services/settings/SettingsRequest;Lio/fabric/sdk/android/services/common/CurrentTimeProvider;Lio/fabric/sdk/android/services/settings/SettingsJsonTransform;Lio/fabric/sdk/android/services/settings/CachedSettingsIo;Lio/fabric/sdk/android/services/settings/SettingsSpiCall;)V
    //   182: putfield 97	io/fabric/sdk/android/services/settings/Settings:settingsController	Lio/fabric/sdk/android/services/settings/SettingsController;
    //   185: aload_0
    //   186: iconst_1
    //   187: putfield 46	io/fabric/sdk/android/services/settings/Settings:initialized	Z
    //   190: goto -177 -> 13
    //   193: astore_1
    //   194: aload_0
    //   195: monitorexit
    //   196: aload_1
    //   197: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	198	0	this	Settings
    //   0	198	1	paramKit	io.fabric.sdk.android.Kit
    //   0	198	2	paramIdManager	io.fabric.sdk.android.services.common.IdManager
    //   0	198	3	paramHttpRequestFactory	io.fabric.sdk.android.services.network.HttpRequestFactory
    //   0	198	4	paramString1	String
    //   0	198	5	paramString2	String
    //   0	198	6	paramString3	String
    //   6	3	7	bool	boolean
    //   28	119	8	localContext	android.content.Context
    //   34	102	9	str1	String
    //   48	86	10	str2	String
    //   54	106	11	str3	String
    //   63	110	12	localSystemCurrentTimeProvider	io.fabric.sdk.android.services.common.SystemCurrentTimeProvider
    //   72	103	13	localDefaultSettingsJsonTransform	DefaultSettingsJsonTransform
    //   82	95	14	localDefaultCachedSettingsIo	DefaultCachedSettingsIo
    //   89	79	15	str4	String
    // Exception table:
    //   from	to	target	type
    //   2	8	193	finally
    //   17	185	193	finally
    //   185	190	193	finally
  }
  
  /* Error */
  public boolean loadSettingsData()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 97	io/fabric/sdk/android/services/settings/Settings:settingsController	Lio/fabric/sdk/android/services/settings/SettingsController;
    //   6: invokeinterface 189 1 0
    //   11: astore_2
    //   12: aload_0
    //   13: aload_2
    //   14: invokespecial 191	io/fabric/sdk/android/services/settings/Settings:setSettingsData	(Lio/fabric/sdk/android/services/settings/SettingsData;)V
    //   17: aload_2
    //   18: ifnull +9 -> 27
    //   21: iconst_1
    //   22: istore_1
    //   23: aload_0
    //   24: monitorexit
    //   25: iload_1
    //   26: ireturn
    //   27: iconst_0
    //   28: istore_1
    //   29: goto -6 -> 23
    //   32: astore_2
    //   33: aload_0
    //   34: monitorexit
    //   35: aload_2
    //   36: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	37	0	this	Settings
    //   22	7	1	bool	boolean
    //   11	7	2	localSettingsData	SettingsData
    //   32	4	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	17	32	finally
  }
  
  /* Error */
  public boolean loadSettingsSkippingCache()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 97	io/fabric/sdk/android/services/settings/Settings:settingsController	Lio/fabric/sdk/android/services/settings/SettingsController;
    //   6: getstatic 198	io/fabric/sdk/android/services/settings/SettingsCacheBehavior:SKIP_CACHE_LOOKUP	Lio/fabric/sdk/android/services/settings/SettingsCacheBehavior;
    //   9: invokeinterface 201 2 0
    //   14: astore_2
    //   15: aload_0
    //   16: aload_2
    //   17: invokespecial 191	io/fabric/sdk/android/services/settings/Settings:setSettingsData	(Lio/fabric/sdk/android/services/settings/SettingsData;)V
    //   20: aload_2
    //   21: ifnonnull +16 -> 37
    //   24: invokestatic 82	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   27: ldc 84
    //   29: ldc -53
    //   31: aconst_null
    //   32: invokeinterface 206 4 0
    //   37: aload_2
    //   38: ifnull +9 -> 47
    //   41: iconst_1
    //   42: istore_1
    //   43: aload_0
    //   44: monitorexit
    //   45: iload_1
    //   46: ireturn
    //   47: iconst_0
    //   48: istore_1
    //   49: goto -6 -> 43
    //   52: astore_2
    //   53: aload_0
    //   54: monitorexit
    //   55: aload_2
    //   56: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	57	0	this	Settings
    //   42	7	1	bool	boolean
    //   14	24	2	localSettingsData	SettingsData
    //   52	4	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	20	52	finally
    //   24	37	52	finally
  }
  
  public void setSettingsController(SettingsController paramSettingsController)
  {
    this.settingsController = paramSettingsController;
  }
  
  public <T> T withSettings(SettingsAccess<T> paramSettingsAccess, T paramT)
  {
    SettingsData localSettingsData = (SettingsData)this.settingsData.get();
    if (localSettingsData == null) {
      return paramT;
    }
    return (T)paramSettingsAccess.usingSettings(localSettingsData);
  }
  
  static class LazyHolder
  {
    private static final Settings INSTANCE = new Settings(null);
  }
  
  public static abstract interface SettingsAccess<T>
  {
    public abstract T usingSettings(SettingsData paramSettingsData);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/settings/Settings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
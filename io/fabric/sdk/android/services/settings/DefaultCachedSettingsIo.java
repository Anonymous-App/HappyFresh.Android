package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Kit;

class DefaultCachedSettingsIo
  implements CachedSettingsIo
{
  private final Kit kit;
  
  public DefaultCachedSettingsIo(Kit paramKit)
  {
    this.kit = paramKit;
  }
  
  /* Error */
  public org.json.JSONObject readCachedSettings()
  {
    // Byte code:
    //   0: invokestatic 26	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   3: ldc 28
    //   5: ldc 30
    //   7: invokeinterface 36 3 0
    //   12: aconst_null
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 5
    //   17: aconst_null
    //   18: astore_2
    //   19: aconst_null
    //   20: astore 4
    //   22: aload 5
    //   24: astore_1
    //   25: new 38	java/io/File
    //   28: dup
    //   29: new 40	io/fabric/sdk/android/services/persistence/FileStoreImpl
    //   32: dup
    //   33: aload_0
    //   34: getfield 15	io/fabric/sdk/android/services/settings/DefaultCachedSettingsIo:kit	Lio/fabric/sdk/android/Kit;
    //   37: invokespecial 42	io/fabric/sdk/android/services/persistence/FileStoreImpl:<init>	(Lio/fabric/sdk/android/Kit;)V
    //   40: invokevirtual 46	io/fabric/sdk/android/services/persistence/FileStoreImpl:getFilesDir	()Ljava/io/File;
    //   43: ldc 48
    //   45: invokespecial 51	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   48: astore 6
    //   50: aload 5
    //   52: astore_1
    //   53: aload 6
    //   55: invokevirtual 55	java/io/File:exists	()Z
    //   58: ifeq +36 -> 94
    //   61: aload 5
    //   63: astore_1
    //   64: new 57	java/io/FileInputStream
    //   67: dup
    //   68: aload 6
    //   70: invokespecial 60	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   73: astore_2
    //   74: new 62	org/json/JSONObject
    //   77: dup
    //   78: aload_2
    //   79: invokestatic 68	io/fabric/sdk/android/services/common/CommonUtils:streamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   82: invokespecial 71	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   85: astore_1
    //   86: aload_2
    //   87: ldc 73
    //   89: invokestatic 77	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   92: aload_1
    //   93: areturn
    //   94: aload 5
    //   96: astore_1
    //   97: invokestatic 26	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   100: ldc 28
    //   102: ldc 79
    //   104: invokeinterface 36 3 0
    //   109: aload 4
    //   111: astore_1
    //   112: goto -26 -> 86
    //   115: astore_1
    //   116: aload_3
    //   117: astore_2
    //   118: aload_1
    //   119: astore_3
    //   120: aload_2
    //   121: astore_1
    //   122: invokestatic 26	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   125: ldc 28
    //   127: ldc 81
    //   129: aload_3
    //   130: invokeinterface 85 4 0
    //   135: aload_2
    //   136: ldc 73
    //   138: invokestatic 77	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   141: aconst_null
    //   142: areturn
    //   143: astore_2
    //   144: aload_1
    //   145: ldc 73
    //   147: invokestatic 77	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   150: aload_2
    //   151: athrow
    //   152: astore_3
    //   153: aload_2
    //   154: astore_1
    //   155: aload_3
    //   156: astore_2
    //   157: goto -13 -> 144
    //   160: astore_3
    //   161: goto -41 -> 120
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	164	0	this	DefaultCachedSettingsIo
    //   24	88	1	localObject1	Object
    //   115	4	1	localException1	Exception
    //   121	34	1	localObject2	Object
    //   18	118	2	localObject3	Object
    //   143	11	2	localObject4	Object
    //   156	1	2	localObject5	Object
    //   13	117	3	localObject6	Object
    //   152	4	3	localObject7	Object
    //   160	1	3	localException2	Exception
    //   20	90	4	localObject8	Object
    //   15	80	5	localObject9	Object
    //   48	21	6	localFile	java.io.File
    // Exception table:
    //   from	to	target	type
    //   25	50	115	java/lang/Exception
    //   53	61	115	java/lang/Exception
    //   64	74	115	java/lang/Exception
    //   97	109	115	java/lang/Exception
    //   25	50	143	finally
    //   53	61	143	finally
    //   64	74	143	finally
    //   97	109	143	finally
    //   122	135	143	finally
    //   74	86	152	finally
    //   74	86	160	java/lang/Exception
  }
  
  /* Error */
  public void writeCachedSettings(long paramLong, org.json.JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: invokestatic 26	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   3: ldc 28
    //   5: ldc 89
    //   7: invokeinterface 36 3 0
    //   12: aload_3
    //   13: ifnull +78 -> 91
    //   16: aconst_null
    //   17: astore 5
    //   19: aconst_null
    //   20: astore 6
    //   22: aload 5
    //   24: astore 4
    //   26: aload_3
    //   27: ldc 91
    //   29: lload_1
    //   30: invokevirtual 95	org/json/JSONObject:put	(Ljava/lang/String;J)Lorg/json/JSONObject;
    //   33: pop
    //   34: aload 5
    //   36: astore 4
    //   38: new 97	java/io/FileWriter
    //   41: dup
    //   42: new 38	java/io/File
    //   45: dup
    //   46: new 40	io/fabric/sdk/android/services/persistence/FileStoreImpl
    //   49: dup
    //   50: aload_0
    //   51: getfield 15	io/fabric/sdk/android/services/settings/DefaultCachedSettingsIo:kit	Lio/fabric/sdk/android/Kit;
    //   54: invokespecial 42	io/fabric/sdk/android/services/persistence/FileStoreImpl:<init>	(Lio/fabric/sdk/android/Kit;)V
    //   57: invokevirtual 46	io/fabric/sdk/android/services/persistence/FileStoreImpl:getFilesDir	()Ljava/io/File;
    //   60: ldc 48
    //   62: invokespecial 51	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   65: invokespecial 98	java/io/FileWriter:<init>	(Ljava/io/File;)V
    //   68: astore 5
    //   70: aload 5
    //   72: aload_3
    //   73: invokevirtual 102	org/json/JSONObject:toString	()Ljava/lang/String;
    //   76: invokevirtual 105	java/io/FileWriter:write	(Ljava/lang/String;)V
    //   79: aload 5
    //   81: invokevirtual 108	java/io/FileWriter:flush	()V
    //   84: aload 5
    //   86: ldc 110
    //   88: invokestatic 77	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   91: return
    //   92: astore 5
    //   94: aload 6
    //   96: astore_3
    //   97: aload_3
    //   98: astore 4
    //   100: invokestatic 26	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   103: ldc 28
    //   105: ldc 112
    //   107: aload 5
    //   109: invokeinterface 85 4 0
    //   114: aload_3
    //   115: ldc 110
    //   117: invokestatic 77	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   120: return
    //   121: astore_3
    //   122: aload 4
    //   124: ldc 110
    //   126: invokestatic 77	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   129: aload_3
    //   130: athrow
    //   131: astore_3
    //   132: aload 5
    //   134: astore 4
    //   136: goto -14 -> 122
    //   139: astore 4
    //   141: aload 5
    //   143: astore_3
    //   144: aload 4
    //   146: astore 5
    //   148: goto -51 -> 97
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	DefaultCachedSettingsIo
    //   0	151	1	paramLong	long
    //   0	151	3	paramJSONObject	org.json.JSONObject
    //   24	111	4	localObject1	Object
    //   139	6	4	localException1	Exception
    //   17	68	5	localFileWriter	java.io.FileWriter
    //   92	50	5	localException2	Exception
    //   146	1	5	localObject2	Object
    //   20	75	6	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   26	34	92	java/lang/Exception
    //   38	70	92	java/lang/Exception
    //   26	34	121	finally
    //   38	70	121	finally
    //   100	114	121	finally
    //   70	84	131	finally
    //   70	84	139	java/lang/Exception
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/settings/DefaultCachedSettingsIo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
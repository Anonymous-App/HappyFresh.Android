package com.crashlytics.android.core;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class MetaDataStore
{
  private static final String KEYDATA_SUFFIX = "keys";
  private static final String KEY_USER_EMAIL = "userEmail";
  private static final String KEY_USER_ID = "userId";
  private static final String KEY_USER_NAME = "userName";
  private static final String METADATA_EXT = ".meta";
  private static final String USERDATA_SUFFIX = "user";
  private static final Charset UTF_8 = Charset.forName("UTF-8");
  private final File filesDir;
  
  public MetaDataStore(File paramFile)
  {
    this.filesDir = paramFile;
  }
  
  private File getKeysFileForSession(String paramString)
  {
    return new File(this.filesDir, paramString + "keys" + ".meta");
  }
  
  private File getUserDataFileForSession(String paramString)
  {
    return new File(this.filesDir, paramString + "user" + ".meta");
  }
  
  private static Map<String, String> jsonToKeysData(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramString.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localHashMap.put(str, valueOrNull(paramString, str));
    }
    return localHashMap;
  }
  
  private static UserMetaData jsonToUserData(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    return new UserMetaData(valueOrNull(paramString, "userId"), valueOrNull(paramString, "userName"), valueOrNull(paramString, "userEmail"));
  }
  
  private static String keysDataToJson(Map<String, String> paramMap)
    throws JSONException
  {
    return new JSONObject(paramMap).toString();
  }
  
  private static String userDataToJson(UserMetaData paramUserMetaData)
    throws JSONException
  {
    new JSONObject() {}.toString();
  }
  
  private static String valueOrNull(JSONObject paramJSONObject, String paramString)
  {
    String str = null;
    if (!paramJSONObject.isNull(paramString)) {
      str = paramJSONObject.optString(paramString, null);
    }
    return str;
  }
  
  /* Error */
  public Map<String, String> readKeyData(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial 140	com/crashlytics/android/core/MetaDataStore:getKeysFileForSession	(Ljava/lang/String;)Ljava/io/File;
    //   5: astore_2
    //   6: aload_2
    //   7: invokevirtual 143	java/io/File:exists	()Z
    //   10: ifne +7 -> 17
    //   13: invokestatic 149	java/util/Collections:emptyMap	()Ljava/util/Map;
    //   16: areturn
    //   17: aconst_null
    //   18: astore_1
    //   19: aconst_null
    //   20: astore_3
    //   21: new 151	java/io/FileInputStream
    //   24: dup
    //   25: aload_2
    //   26: invokespecial 153	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   29: astore_2
    //   30: aload_2
    //   31: invokestatic 159	io/fabric/sdk/android/services/common/CommonUtils:streamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   34: invokestatic 161	com/crashlytics/android/core/MetaDataStore:jsonToKeysData	(Ljava/lang/String;)Ljava/util/Map;
    //   37: astore_1
    //   38: aload_2
    //   39: ldc -93
    //   41: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   44: aload_1
    //   45: areturn
    //   46: astore_1
    //   47: aload_3
    //   48: astore_2
    //   49: aload_1
    //   50: astore_3
    //   51: aload_2
    //   52: astore_1
    //   53: invokestatic 173	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   56: ldc -81
    //   58: ldc -79
    //   60: aload_3
    //   61: invokeinterface 183 4 0
    //   66: aload_2
    //   67: ldc -93
    //   69: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   72: invokestatic 149	java/util/Collections:emptyMap	()Ljava/util/Map;
    //   75: areturn
    //   76: astore_2
    //   77: aload_1
    //   78: ldc -93
    //   80: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   83: aload_2
    //   84: athrow
    //   85: astore_3
    //   86: aload_2
    //   87: astore_1
    //   88: aload_3
    //   89: astore_2
    //   90: goto -13 -> 77
    //   93: astore_3
    //   94: goto -43 -> 51
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	97	0	this	MetaDataStore
    //   0	97	1	paramString	String
    //   5	62	2	localObject1	Object
    //   76	11	2	localObject2	Object
    //   89	1	2	localObject3	Object
    //   20	41	3	str	String
    //   85	4	3	localObject4	Object
    //   93	1	3	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   21	30	46	java/lang/Exception
    //   21	30	76	finally
    //   53	66	76	finally
    //   30	38	85	finally
    //   30	38	93	java/lang/Exception
  }
  
  /* Error */
  public UserMetaData readUserData(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial 186	com/crashlytics/android/core/MetaDataStore:getUserDataFileForSession	(Ljava/lang/String;)Ljava/io/File;
    //   5: astore_2
    //   6: aload_2
    //   7: invokevirtual 143	java/io/File:exists	()Z
    //   10: ifne +7 -> 17
    //   13: getstatic 190	com/crashlytics/android/core/UserMetaData:EMPTY	Lcom/crashlytics/android/core/UserMetaData;
    //   16: areturn
    //   17: aconst_null
    //   18: astore_1
    //   19: aconst_null
    //   20: astore_3
    //   21: new 151	java/io/FileInputStream
    //   24: dup
    //   25: aload_2
    //   26: invokespecial 153	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   29: astore_2
    //   30: aload_2
    //   31: invokestatic 159	io/fabric/sdk/android/services/common/CommonUtils:streamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   34: invokestatic 192	com/crashlytics/android/core/MetaDataStore:jsonToUserData	(Ljava/lang/String;)Lcom/crashlytics/android/core/UserMetaData;
    //   37: astore_1
    //   38: aload_2
    //   39: ldc -93
    //   41: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   44: aload_1
    //   45: areturn
    //   46: astore_1
    //   47: aload_3
    //   48: astore_2
    //   49: aload_1
    //   50: astore_3
    //   51: aload_2
    //   52: astore_1
    //   53: invokestatic 173	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   56: ldc -81
    //   58: ldc -79
    //   60: aload_3
    //   61: invokeinterface 183 4 0
    //   66: aload_2
    //   67: ldc -93
    //   69: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   72: getstatic 190	com/crashlytics/android/core/UserMetaData:EMPTY	Lcom/crashlytics/android/core/UserMetaData;
    //   75: areturn
    //   76: astore_2
    //   77: aload_1
    //   78: ldc -93
    //   80: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   83: aload_2
    //   84: athrow
    //   85: astore_3
    //   86: aload_2
    //   87: astore_1
    //   88: aload_3
    //   89: astore_2
    //   90: goto -13 -> 77
    //   93: astore_3
    //   94: goto -43 -> 51
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	97	0	this	MetaDataStore
    //   0	97	1	paramString	String
    //   5	62	2	localObject1	Object
    //   76	11	2	localObject2	Object
    //   89	1	2	localObject3	Object
    //   20	41	3	str	String
    //   85	4	3	localObject4	Object
    //   93	1	3	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   21	30	46	java/lang/Exception
    //   21	30	76	finally
    //   53	66	76	finally
    //   30	38	85	finally
    //   30	38	93	java/lang/Exception
  }
  
  /* Error */
  public void writeKeyData(String paramString, Map<String, String> paramMap)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial 140	com/crashlytics/android/core/MetaDataStore:getKeysFileForSession	(Ljava/lang/String;)Ljava/io/File;
    //   5: astore 5
    //   7: aconst_null
    //   8: astore 4
    //   10: aconst_null
    //   11: astore_3
    //   12: aload 4
    //   14: astore_1
    //   15: aload_2
    //   16: invokestatic 196	com/crashlytics/android/core/MetaDataStore:keysDataToJson	(Ljava/util/Map;)Ljava/lang/String;
    //   19: astore 6
    //   21: aload 4
    //   23: astore_1
    //   24: new 198	java/io/BufferedWriter
    //   27: dup
    //   28: new 200	java/io/OutputStreamWriter
    //   31: dup
    //   32: new 202	java/io/FileOutputStream
    //   35: dup
    //   36: aload 5
    //   38: invokespecial 203	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   41: getstatic 41	com/crashlytics/android/core/MetaDataStore:UTF_8	Ljava/nio/charset/Charset;
    //   44: invokespecial 206	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
    //   47: invokespecial 209	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   50: astore_2
    //   51: aload_2
    //   52: aload 6
    //   54: invokevirtual 214	java/io/Writer:write	(Ljava/lang/String;)V
    //   57: aload_2
    //   58: invokevirtual 217	java/io/Writer:flush	()V
    //   61: aload_2
    //   62: ldc -37
    //   64: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   67: return
    //   68: astore_1
    //   69: aload_3
    //   70: astore_2
    //   71: aload_1
    //   72: astore_3
    //   73: aload_2
    //   74: astore_1
    //   75: invokestatic 173	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   78: ldc -81
    //   80: ldc -35
    //   82: aload_3
    //   83: invokeinterface 183 4 0
    //   88: aload_2
    //   89: ldc -37
    //   91: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   94: return
    //   95: astore_2
    //   96: aload_1
    //   97: ldc -37
    //   99: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   102: aload_2
    //   103: athrow
    //   104: astore_3
    //   105: aload_2
    //   106: astore_1
    //   107: aload_3
    //   108: astore_2
    //   109: goto -13 -> 96
    //   112: astore_3
    //   113: goto -40 -> 73
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	116	0	this	MetaDataStore
    //   0	116	1	paramString	String
    //   0	116	2	paramMap	Map<String, String>
    //   11	72	3	str1	String
    //   104	4	3	localObject1	Object
    //   112	1	3	localException	Exception
    //   8	14	4	localObject2	Object
    //   5	32	5	localFile	File
    //   19	34	6	str2	String
    // Exception table:
    //   from	to	target	type
    //   15	21	68	java/lang/Exception
    //   24	51	68	java/lang/Exception
    //   15	21	95	finally
    //   24	51	95	finally
    //   75	88	95	finally
    //   51	61	104	finally
    //   51	61	112	java/lang/Exception
  }
  
  /* Error */
  public void writeUserData(String paramString, UserMetaData paramUserMetaData)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial 186	com/crashlytics/android/core/MetaDataStore:getUserDataFileForSession	(Ljava/lang/String;)Ljava/io/File;
    //   5: astore 5
    //   7: aconst_null
    //   8: astore 4
    //   10: aconst_null
    //   11: astore_3
    //   12: aload 4
    //   14: astore_1
    //   15: aload_2
    //   16: invokestatic 226	com/crashlytics/android/core/MetaDataStore:userDataToJson	(Lcom/crashlytics/android/core/UserMetaData;)Ljava/lang/String;
    //   19: astore 6
    //   21: aload 4
    //   23: astore_1
    //   24: new 198	java/io/BufferedWriter
    //   27: dup
    //   28: new 200	java/io/OutputStreamWriter
    //   31: dup
    //   32: new 202	java/io/FileOutputStream
    //   35: dup
    //   36: aload 5
    //   38: invokespecial 203	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   41: getstatic 41	com/crashlytics/android/core/MetaDataStore:UTF_8	Ljava/nio/charset/Charset;
    //   44: invokespecial 206	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
    //   47: invokespecial 209	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   50: astore_2
    //   51: aload_2
    //   52: aload 6
    //   54: invokevirtual 214	java/io/Writer:write	(Ljava/lang/String;)V
    //   57: aload_2
    //   58: invokevirtual 217	java/io/Writer:flush	()V
    //   61: aload_2
    //   62: ldc -93
    //   64: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   67: return
    //   68: astore_1
    //   69: aload_3
    //   70: astore_2
    //   71: aload_1
    //   72: astore_3
    //   73: aload_2
    //   74: astore_1
    //   75: invokestatic 173	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   78: ldc -81
    //   80: ldc -28
    //   82: aload_3
    //   83: invokeinterface 183 4 0
    //   88: aload_2
    //   89: ldc -93
    //   91: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   94: return
    //   95: astore_2
    //   96: aload_1
    //   97: ldc -93
    //   99: invokestatic 167	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   102: aload_2
    //   103: athrow
    //   104: astore_3
    //   105: aload_2
    //   106: astore_1
    //   107: aload_3
    //   108: astore_2
    //   109: goto -13 -> 96
    //   112: astore_3
    //   113: goto -40 -> 73
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	116	0	this	MetaDataStore
    //   0	116	1	paramString	String
    //   0	116	2	paramUserMetaData	UserMetaData
    //   11	72	3	str1	String
    //   104	4	3	localObject1	Object
    //   112	1	3	localException	Exception
    //   8	14	4	localObject2	Object
    //   5	32	5	localFile	File
    //   19	34	6	str2	String
    // Exception table:
    //   from	to	target	type
    //   15	21	68	java/lang/Exception
    //   24	51	68	java/lang/Exception
    //   15	21	95	finally
    //   24	51	95	finally
    //   75	88	95	finally
    //   51	61	104	finally
    //   51	61	112	java/lang/Exception
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/MetaDataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
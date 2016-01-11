package com.parse;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

class LocalIdManager
{
  private final File diskPath;
  private final Random random;
  
  LocalIdManager(File paramFile)
  {
    this.diskPath = new File(paramFile, "LocalId");
    this.random = new Random();
  }
  
  private MapEntry getMapEntry(String paramString)
  {
    try
    {
      if (!isLocalId(paramString)) {
        throw new IllegalStateException("Tried to get invalid local id: \"" + paramString + "\".");
      }
    }
    finally {}
    try
    {
      JSONObject localJSONObject = ParseFileUtils.readFileToJSONObject(new File(this.diskPath, paramString));
      paramString = new MapEntry(null);
      paramString.retainCount = localJSONObject.optInt("retainCount", 0);
      paramString.objectId = localJSONObject.optString("objectId", null);
      return paramString;
    }
    catch (IOException paramString)
    {
      for (;;)
      {
        paramString = new MapEntry(null);
      }
    }
    catch (JSONException paramString)
    {
      for (;;) {}
    }
  }
  
  private boolean isLocalId(String paramString)
  {
    if (!paramString.startsWith("local_")) {
      return false;
    }
    int i = 6;
    for (;;)
    {
      if (i >= paramString.length()) {
        break label59;
      }
      int j = paramString.charAt(i);
      if (((j < 48) || (j > 57)) && ((j < 97) || (j > 102))) {
        break;
      }
      i += 1;
    }
    label59:
    return true;
  }
  
  /* Error */
  private void putMapEntry(String paramString, MapEntry paramMapEntry)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokespecial 43	com/parse/LocalIdManager:isLocalId	(Ljava/lang/String;)Z
    //   7: ifne +40 -> 47
    //   10: new 45	java/lang/IllegalStateException
    //   13: dup
    //   14: new 47	java/lang/StringBuilder
    //   17: dup
    //   18: invokespecial 48	java/lang/StringBuilder:<init>	()V
    //   21: ldc 50
    //   23: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: aload_1
    //   27: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: ldc 56
    //   32: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: invokevirtual 60	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   38: invokespecial 63	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   41: athrow
    //   42: astore_1
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_1
    //   46: athrow
    //   47: new 76	org/json/JSONObject
    //   50: dup
    //   51: invokespecial 110	org/json/JSONObject:<init>	()V
    //   54: astore_3
    //   55: aload_3
    //   56: ldc 74
    //   58: aload_2
    //   59: getfield 83	com/parse/LocalIdManager$MapEntry:retainCount	I
    //   62: invokevirtual 114	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
    //   65: pop
    //   66: aload_2
    //   67: getfield 92	com/parse/LocalIdManager$MapEntry:objectId	Ljava/lang/String;
    //   70: ifnull +14 -> 84
    //   73: aload_3
    //   74: ldc 85
    //   76: aload_2
    //   77: getfield 92	com/parse/LocalIdManager$MapEntry:objectId	Ljava/lang/String;
    //   80: invokevirtual 117	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   83: pop
    //   84: new 20	java/io/File
    //   87: dup
    //   88: aload_0
    //   89: getfield 27	com/parse/LocalIdManager:diskPath	Ljava/io/File;
    //   92: aload_1
    //   93: invokespecial 25	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   96: astore_1
    //   97: aload_0
    //   98: getfield 27	com/parse/LocalIdManager:diskPath	Ljava/io/File;
    //   101: invokevirtual 121	java/io/File:exists	()Z
    //   104: ifne +11 -> 115
    //   107: aload_0
    //   108: getfield 27	com/parse/LocalIdManager:diskPath	Ljava/io/File;
    //   111: invokevirtual 124	java/io/File:mkdirs	()Z
    //   114: pop
    //   115: aload_1
    //   116: aload_3
    //   117: invokestatic 128	com/parse/ParseFileUtils:writeJSONObjectToFile	(Ljava/io/File;Lorg/json/JSONObject;)V
    //   120: aload_0
    //   121: monitorexit
    //   122: return
    //   123: astore_1
    //   124: new 45	java/lang/IllegalStateException
    //   127: dup
    //   128: ldc -126
    //   130: aload_1
    //   131: invokespecial 133	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   134: athrow
    //   135: astore_1
    //   136: goto -16 -> 120
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	139	0	this	LocalIdManager
    //   0	139	1	paramString	String
    //   0	139	2	paramMapEntry	MapEntry
    //   54	63	3	localJSONObject	JSONObject
    // Exception table:
    //   from	to	target	type
    //   2	42	42	finally
    //   47	55	42	finally
    //   55	84	42	finally
    //   84	115	42	finally
    //   115	120	42	finally
    //   124	135	42	finally
    //   55	84	123	org/json/JSONException
    //   115	120	135	java/io/IOException
  }
  
  private void removeMapEntry(String paramString)
  {
    try
    {
      if (!isLocalId(paramString)) {
        throw new IllegalStateException("Tried to get invalid local id: \"" + paramString + "\".");
      }
    }
    finally {}
    ParseFileUtils.deleteQuietly(new File(this.diskPath, paramString));
  }
  
  boolean clear()
    throws IOException
  {
    boolean bool = false;
    for (;;)
    {
      int i;
      try
      {
        String[] arrayOfString = this.diskPath.list();
        if (arrayOfString == null) {
          return bool;
        }
        if (arrayOfString.length == 0) {
          continue;
        }
        int j = arrayOfString.length;
        i = 0;
        if (i >= j) {
          break label111;
        }
        String str = arrayOfString[i];
        if (!new File(this.diskPath, str).delete()) {
          throw new IOException("Unable to delete file " + str + " in localId cache.");
        }
      }
      finally {}
      i += 1;
      continue;
      label111:
      bool = true;
    }
  }
  
  String createLocalId()
  {
    try
    {
      long l = this.random.nextLong();
      String str1 = "local_" + Long.toHexString(l);
      if (!isLocalId(str1)) {
        throw new IllegalStateException("Generated an invalid local id: \"" + str1 + "\". " + "This should never happen. Contact us at https://parse.com/help");
      }
    }
    finally {}
    return str2;
  }
  
  String getObjectId(String paramString)
  {
    try
    {
      paramString = getMapEntry(paramString).objectId;
      return paramString;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  /* Error */
  void releaseLocalIdOnDisk(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokespecial 173	com/parse/LocalIdManager:getMapEntry	(Ljava/lang/String;)Lcom/parse/LocalIdManager$MapEntry;
    //   7: astore_2
    //   8: aload_2
    //   9: aload_2
    //   10: getfield 83	com/parse/LocalIdManager$MapEntry:retainCount	I
    //   13: iconst_1
    //   14: isub
    //   15: putfield 83	com/parse/LocalIdManager$MapEntry:retainCount	I
    //   18: aload_2
    //   19: getfield 83	com/parse/LocalIdManager$MapEntry:retainCount	I
    //   22: ifle +12 -> 34
    //   25: aload_0
    //   26: aload_1
    //   27: aload_2
    //   28: invokespecial 176	com/parse/LocalIdManager:putMapEntry	(Ljava/lang/String;Lcom/parse/LocalIdManager$MapEntry;)V
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: aload_0
    //   35: aload_1
    //   36: invokespecial 178	com/parse/LocalIdManager:removeMapEntry	(Ljava/lang/String;)V
    //   39: goto -8 -> 31
    //   42: astore_1
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_1
    //   46: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	47	0	this	LocalIdManager
    //   0	47	1	paramString	String
    //   7	21	2	localMapEntry	MapEntry
    // Exception table:
    //   from	to	target	type
    //   2	31	42	finally
    //   34	39	42	finally
  }
  
  void retainLocalIdOnDisk(String paramString)
  {
    try
    {
      MapEntry localMapEntry = getMapEntry(paramString);
      localMapEntry.retainCount += 1;
      putMapEntry(paramString, localMapEntry);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  void setObjectId(String paramString1, String paramString2)
  {
    MapEntry localMapEntry;
    try
    {
      localMapEntry = getMapEntry(paramString1);
      if (localMapEntry.retainCount <= 0) {
        break label48;
      }
      if (localMapEntry.objectId != null) {
        throw new IllegalStateException("Tried to set an objectId for a localId that already has one.");
      }
    }
    finally {}
    localMapEntry.objectId = paramString2;
    putMapEntry(paramString1, localMapEntry);
    label48:
  }
  
  private static class MapEntry
  {
    String objectId;
    int retainCount;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/LocalIdManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.parse;

import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

class PushRouter
{
  private static final String LEGACY_STATE_LOCATION = "pushState";
  private static int MAX_HISTORY_LENGTH = 10;
  private static final String STATE_LOCATION = "push";
  private static final String TAG = "com.parse.ParsePushRouter";
  private static PushRouter instance;
  private final File diskState;
  private final PushHistory history;
  
  private PushRouter(File paramFile, PushHistory paramPushHistory)
  {
    this.diskState = paramFile;
    this.history = paramPushHistory;
  }
  
  public static PushRouter getInstance()
  {
    try
    {
      if (instance == null) {
        instance = pushRouterFromState(new File(ParsePlugins.get().getFilesDir(), "push"), new File(ParsePlugins.get().getParseDir(), "pushState"), MAX_HISTORY_LENGTH);
      }
      PushRouter localPushRouter = instance;
      return localPushRouter;
    }
    finally {}
  }
  
  static PushRouter pushRouterFromState(File paramFile1, File paramFile2, int paramInt)
  {
    Object localObject1 = readJSONFileQuietly(paramFile1);
    if (localObject1 != null) {}
    for (localObject1 = ((JSONObject)localObject1).optJSONObject("history");; localObject1 = null)
    {
      localObject1 = new PushHistory(paramInt, (JSONObject)localObject1);
      int i = 0;
      paramInt = i;
      if (((PushHistory)localObject1).getLastReceivedTimestamp() == null)
      {
        Object localObject2 = readJSONFileQuietly(paramFile2);
        paramInt = i;
        if (localObject2 != null)
        {
          localObject2 = ((JSONObject)localObject2).optString("lastTime", null);
          if (localObject2 != null) {
            ((PushHistory)localObject1).setLastReceivedTimestamp((String)localObject2);
          }
          paramInt = 1;
        }
      }
      paramFile1 = new PushRouter(paramFile1, (PushHistory)localObject1);
      if (paramInt != 0)
      {
        paramFile1.saveStateToDisk();
        ParseFileUtils.deleteQuietly(paramFile2);
      }
      return paramFile1;
    }
  }
  
  private static JSONObject readJSONFileQuietly(File paramFile)
  {
    JSONObject localJSONObject = null;
    if (paramFile != null) {}
    try
    {
      localJSONObject = ParseFileUtils.readFileToJSONObject(paramFile);
      return localJSONObject;
    }
    catch (IOException paramFile)
    {
      return null;
    }
    catch (JSONException paramFile) {}
    return null;
  }
  
  static void resetInstance()
  {
    try
    {
      ParseFileUtils.deleteQuietly(new File(ParsePlugins.get().getFilesDir(), "push"));
      instance = null;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  private void saveStateToDisk()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 34	com/parse/PushRouter:diskState	Ljava/io/File;
    //   6: aload_0
    //   7: invokevirtual 115	com/parse/PushRouter:toJSON	()Lorg/json/JSONObject;
    //   10: invokestatic 119	com/parse/ParseFileUtils:writeJSONObjectToFile	(Ljava/io/File;Lorg/json/JSONObject;)V
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: astore_1
    //   17: ldc 17
    //   19: new 121	java/lang/StringBuilder
    //   22: dup
    //   23: invokespecial 122	java/lang/StringBuilder:<init>	()V
    //   26: ldc 124
    //   28: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: aload_0
    //   32: getfield 34	com/parse/PushRouter:diskState	Ljava/io/File;
    //   35: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   38: invokevirtual 134	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   41: aload_1
    //   42: invokestatic 140	com/parse/PLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   45: goto -32 -> 13
    //   48: astore_1
    //   49: aload_0
    //   50: monitorexit
    //   51: aload_1
    //   52: athrow
    //   53: astore_1
    //   54: goto -37 -> 17
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	57	0	this	PushRouter
    //   16	26	1	localIOException	IOException
    //   48	4	1	localObject	Object
    //   53	1	1	localJSONException	JSONException
    // Exception table:
    //   from	to	target	type
    //   2	13	16	java/io/IOException
    //   2	13	48	finally
    //   17	45	48	finally
    //   2	13	53	org/json/JSONException
  }
  
  public String getLastReceivedTimestamp()
  {
    try
    {
      String str = this.history.getLastReceivedTimestamp();
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public boolean handlePush(String paramString1, String paramString2, String paramString3, JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 6
    //   3: aload_0
    //   4: monitorenter
    //   5: iload 6
    //   7: istore 5
    //   9: aload_1
    //   10: invokestatic 148	com/parse/ParseTextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   13: ifne +18 -> 31
    //   16: aload_2
    //   17: invokestatic 148	com/parse/ParseTextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   20: istore 5
    //   22: iload 5
    //   24: ifeq +12 -> 36
    //   27: iload 6
    //   29: istore 5
    //   31: aload_0
    //   32: monitorexit
    //   33: iload 5
    //   35: ireturn
    //   36: iload 6
    //   38: istore 5
    //   40: aload_0
    //   41: getfield 36	com/parse/PushRouter:history	Lcom/parse/PushHistory;
    //   44: aload_1
    //   45: aload_2
    //   46: invokevirtual 152	com/parse/PushHistory:tryInsertPush	(Ljava/lang/String;Ljava/lang/String;)Z
    //   49: ifeq -18 -> 31
    //   52: aload_0
    //   53: invokespecial 97	com/parse/PushRouter:saveStateToDisk	()V
    //   56: new 154	android/os/Bundle
    //   59: dup
    //   60: invokespecial 155	android/os/Bundle:<init>	()V
    //   63: astore_1
    //   64: aload_1
    //   65: ldc -99
    //   67: aload_3
    //   68: invokevirtual 161	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   71: aload 4
    //   73: ifnonnull +51 -> 124
    //   76: aload_1
    //   77: ldc -93
    //   79: ldc -91
    //   81: invokevirtual 161	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   84: new 167	android/content/Intent
    //   87: dup
    //   88: ldc -87
    //   90: invokespecial 171	android/content/Intent:<init>	(Ljava/lang/String;)V
    //   93: astore_2
    //   94: aload_2
    //   95: aload_1
    //   96: invokevirtual 175	android/content/Intent:putExtras	(Landroid/os/Bundle;)Landroid/content/Intent;
    //   99: pop
    //   100: invokestatic 181	com/parse/Parse:getApplicationContext	()Landroid/content/Context;
    //   103: astore_1
    //   104: aload_2
    //   105: aload_1
    //   106: invokevirtual 186	android/content/Context:getPackageName	()Ljava/lang/String;
    //   109: invokevirtual 190	android/content/Intent:setPackage	(Ljava/lang/String;)Landroid/content/Intent;
    //   112: pop
    //   113: aload_1
    //   114: aload_2
    //   115: invokevirtual 194	android/content/Context:sendBroadcast	(Landroid/content/Intent;)V
    //   118: iconst_1
    //   119: istore 5
    //   121: goto -90 -> 31
    //   124: aload_1
    //   125: ldc -93
    //   127: aload 4
    //   129: invokevirtual 195	org/json/JSONObject:toString	()Ljava/lang/String;
    //   132: invokevirtual 161	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   135: goto -51 -> 84
    //   138: astore_1
    //   139: aload_0
    //   140: monitorexit
    //   141: aload_1
    //   142: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	143	0	this	PushRouter
    //   0	143	1	paramString1	String
    //   0	143	2	paramString2	String
    //   0	143	3	paramString3	String
    //   0	143	4	paramJSONObject	JSONObject
    //   7	113	5	bool1	boolean
    //   1	36	6	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   9	22	138	finally
    //   40	71	138	finally
    //   76	84	138	finally
    //   84	118	138	finally
    //   124	135	138	finally
  }
  
  JSONObject toJSON()
    throws JSONException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("history", this.history.toJSON());
      return localJSONObject;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/PushRouter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
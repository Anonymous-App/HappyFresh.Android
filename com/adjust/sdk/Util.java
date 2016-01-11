package com.adjust.sdk;

import android.content.Context;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.NotSerializableException;
import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

public class Util
{
  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'Z";
  private static SimpleDateFormat dateFormat;
  private static final String fieldReadErrorMessage = "Unable to read '%s' field in migration device with message (%s)";
  
  public static boolean checkPermission(Context paramContext, String paramString)
  {
    return paramContext.checkCallingOrSelfPermission(paramString) == 0;
  }
  
  protected static String createUuid()
  {
    return UUID.randomUUID().toString();
  }
  
  public static String dateFormat(long paramLong)
  {
    if (dateFormat == null) {
      dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'Z", Locale.US);
    }
    return dateFormat.format(Long.valueOf(paramLong));
  }
  
  public static boolean equalBoolean(Boolean paramBoolean1, Boolean paramBoolean2)
  {
    return equalObject(paramBoolean1, paramBoolean2);
  }
  
  public static boolean equalEnum(Enum paramEnum1, Enum paramEnum2)
  {
    return equalObject(paramEnum1, paramEnum2);
  }
  
  public static boolean equalInt(Integer paramInteger1, Integer paramInteger2)
  {
    return equalObject(paramInteger1, paramInteger2);
  }
  
  public static boolean equalLong(Long paramLong1, Long paramLong2)
  {
    return equalObject(paramLong1, paramLong2);
  }
  
  public static boolean equalObject(Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 == null) || (paramObject2 == null)) {
      return (paramObject1 == null) && (paramObject2 == null);
    }
    return paramObject1.equals(paramObject2);
  }
  
  public static boolean equalString(String paramString1, String paramString2)
  {
    return equalObject(paramString1, paramString2);
  }
  
  public static boolean equalsDouble(Double paramDouble1, Double paramDouble2)
  {
    if ((paramDouble1 == null) || (paramDouble2 == null)) {
      if ((paramDouble1 != null) || (paramDouble2 != null)) {}
    }
    while (Double.doubleToLongBits(paramDouble1.doubleValue()) == Double.doubleToLongBits(paramDouble2.doubleValue()))
    {
      return true;
      return false;
    }
    return false;
  }
  
  public static boolean equalsMap(Map paramMap1, Map paramMap2)
  {
    if ((paramMap1 == null) || (paramMap2 == null)) {
      return (paramMap1 == null) && (paramMap2 == null);
    }
    return paramMap1.entrySet().equals(paramMap2.entrySet());
  }
  
  public static HttpClient getHttpClient()
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 60000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 60000);
    return AdjustFactory.getHttpClient(localBasicHttpParams);
  }
  
  private static ILogger getLogger()
  {
    return AdjustFactory.getLogger();
  }
  
  public static String getPlayAdId(Context paramContext)
  {
    return Reflection.getPlayAdId(paramContext);
  }
  
  public static int hashBoolean(Boolean paramBoolean)
  {
    if (paramBoolean == null) {
      return 0;
    }
    return paramBoolean.hashCode();
  }
  
  public static int hashEnum(Enum paramEnum)
  {
    if (paramEnum == null) {
      return 0;
    }
    return paramEnum.hashCode();
  }
  
  public static int hashLong(Long paramLong)
  {
    if (paramLong == null) {
      return 0;
    }
    return paramLong.hashCode();
  }
  
  public static int hashMap(Map paramMap)
  {
    if (paramMap == null) {
      return 0;
    }
    return paramMap.entrySet().hashCode();
  }
  
  public static int hashString(String paramString)
  {
    if (paramString == null) {
      return 0;
    }
    return paramString.hashCode();
  }
  
  public static Boolean isPlayTrackingEnabled(Context paramContext)
  {
    return Reflection.isPlayTrackingEnabled(paramContext);
  }
  
  public static JSONObject parseJsonResponse(HttpResponse paramHttpResponse)
  {
    if (paramHttpResponse == null) {
      return null;
    }
    Object localObject1 = null;
    Object localObject3;
    try
    {
      Object localObject2 = new ByteArrayOutputStream();
      paramHttpResponse.getEntity().writeTo((OutputStream)localObject2);
      ((ByteArrayOutputStream)localObject2).close();
      localObject2 = ((ByteArrayOutputStream)localObject2).toString().trim();
      localObject1 = localObject2;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        getLogger().error("Failed to parse response (%s)", new Object[] { localException.getMessage() });
      }
      localObject3 = null;
      try
      {
        JSONObject localJSONObject = new JSONObject((String)localObject1);
        localObject1 = localJSONObject;
      }
      catch (JSONException localJSONException)
      {
        for (;;)
        {
          getLogger().error("Failed to parse json response: %s (%s)", new Object[] { localObject1, localJSONException.getMessage() });
          localObject1 = localObject3;
        }
        String str = ((JSONObject)localObject1).optString("message", null);
        localObject3 = str;
        if (str != null) {
          break label158;
        }
        localObject3 = "No message found";
        if (paramHttpResponse.getStatusLine().getStatusCode() != 200) {
          break label195;
        }
        getLogger().info("%s", new Object[] { localObject3 });
        return (JSONObject)localObject1;
        getLogger().error("%s", new Object[] { localObject3 });
      }
      if (localObject1 != null) {
        break label141;
      }
      return null;
    }
    getLogger().verbose("Response: %s", new Object[] { localObject1 });
    if (localObject1 == null) {
      return null;
    }
    label141:
    label158:
    label195:
    return (JSONObject)localObject1;
  }
  
  public static String quote(String paramString)
  {
    String str;
    if (paramString == null) {
      str = null;
    }
    do
    {
      return str;
      str = paramString;
    } while (!Pattern.compile("\\s").matcher(paramString).find());
    return String.format(Locale.US, "'%s'", new Object[] { paramString });
  }
  
  public static boolean readBooleanField(ObjectInputStream.GetField paramGetField, String paramString, boolean paramBoolean)
  {
    try
    {
      boolean bool = paramGetField.get(paramString, paramBoolean);
      return bool;
    }
    catch (Exception paramGetField)
    {
      getLogger().debug("Unable to read '%s' field in migration device with message (%s)", new Object[] { paramString, paramGetField.getMessage() });
    }
    return paramBoolean;
  }
  
  public static int readIntField(ObjectInputStream.GetField paramGetField, String paramString, int paramInt)
  {
    try
    {
      int i = paramGetField.get(paramString, paramInt);
      return i;
    }
    catch (Exception paramGetField)
    {
      getLogger().debug("Unable to read '%s' field in migration device with message (%s)", new Object[] { paramString, paramGetField.getMessage() });
    }
    return paramInt;
  }
  
  public static long readLongField(ObjectInputStream.GetField paramGetField, String paramString, long paramLong)
  {
    try
    {
      long l = paramGetField.get(paramString, paramLong);
      return l;
    }
    catch (Exception paramGetField)
    {
      getLogger().debug("Unable to read '%s' field in migration device with message (%s)", new Object[] { paramString, paramGetField.getMessage() });
    }
    return paramLong;
  }
  
  /* Error */
  public static <T> T readObject(Context paramContext, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aconst_null
    //   4: astore 4
    //   6: aconst_null
    //   7: astore 9
    //   9: aconst_null
    //   10: astore 10
    //   12: aconst_null
    //   13: astore 8
    //   15: aconst_null
    //   16: astore 7
    //   18: aconst_null
    //   19: astore 11
    //   21: aload 9
    //   23: astore_3
    //   24: aload 8
    //   26: astore 5
    //   28: aload_0
    //   29: aload_1
    //   30: invokevirtual 292	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   33: astore_0
    //   34: aload_0
    //   35: astore 6
    //   37: aload 6
    //   39: astore 4
    //   41: aload 9
    //   43: astore_3
    //   44: aload 8
    //   46: astore 5
    //   48: new 294	java/io/BufferedInputStream
    //   51: dup
    //   52: aload_0
    //   53: invokespecial 297	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   56: astore_0
    //   57: aload_0
    //   58: astore 6
    //   60: aload 6
    //   62: astore 4
    //   64: aload 9
    //   66: astore_3
    //   67: aload 8
    //   69: astore 5
    //   71: new 299	java/io/ObjectInputStream
    //   74: dup
    //   75: aload_0
    //   76: invokespecial 300	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   79: astore 12
    //   81: aload 12
    //   83: astore 8
    //   85: aload 11
    //   87: astore_0
    //   88: aload 8
    //   90: astore 4
    //   92: aload 9
    //   94: astore_3
    //   95: aload 10
    //   97: astore_1
    //   98: aload 12
    //   100: invokevirtual 303	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
    //   103: astore 5
    //   105: aload 5
    //   107: astore_0
    //   108: aload 8
    //   110: astore 4
    //   112: aload 5
    //   114: astore_3
    //   115: aload 5
    //   117: astore_1
    //   118: aload 5
    //   120: astore 7
    //   122: invokestatic 188	com/adjust/sdk/Util:getLogger	()Lcom/adjust/sdk/ILogger;
    //   125: ldc_w 305
    //   128: iconst_2
    //   129: anewarray 4	java/lang/Object
    //   132: dup
    //   133: iconst_0
    //   134: aload_2
    //   135: aastore
    //   136: dup
    //   137: iconst_1
    //   138: aload 5
    //   140: aastore
    //   141: invokeinterface 270 3 0
    //   146: aload 5
    //   148: astore_3
    //   149: aload 8
    //   151: astore 4
    //   153: aload 4
    //   155: ifnull +10 -> 165
    //   158: aload 4
    //   160: invokeinterface 308 1 0
    //   165: aload_3
    //   166: areturn
    //   167: astore_1
    //   168: aload 8
    //   170: astore 4
    //   172: aload_0
    //   173: astore_3
    //   174: aload 8
    //   176: astore 6
    //   178: aload_0
    //   179: astore 5
    //   181: invokestatic 188	com/adjust/sdk/Util:getLogger	()Lcom/adjust/sdk/ILogger;
    //   184: ldc_w 310
    //   187: iconst_2
    //   188: anewarray 4	java/lang/Object
    //   191: dup
    //   192: iconst_0
    //   193: aload_2
    //   194: aastore
    //   195: dup
    //   196: iconst_1
    //   197: aload_1
    //   198: invokevirtual 311	java/lang/ClassNotFoundException:getMessage	()Ljava/lang/String;
    //   201: aastore
    //   202: invokeinterface 204 3 0
    //   207: aload 8
    //   209: astore 4
    //   211: aload_0
    //   212: astore_3
    //   213: goto -60 -> 153
    //   216: astore_0
    //   217: invokestatic 188	com/adjust/sdk/Util:getLogger	()Lcom/adjust/sdk/ILogger;
    //   220: ldc_w 313
    //   223: iconst_1
    //   224: anewarray 4	java/lang/Object
    //   227: dup
    //   228: iconst_0
    //   229: aload_2
    //   230: aastore
    //   231: invokeinterface 196 3 0
    //   236: goto -83 -> 153
    //   239: astore_0
    //   240: aload 8
    //   242: astore 4
    //   244: aload_1
    //   245: astore_3
    //   246: aload 8
    //   248: astore 6
    //   250: aload_1
    //   251: astore 5
    //   253: invokestatic 188	com/adjust/sdk/Util:getLogger	()Lcom/adjust/sdk/ILogger;
    //   256: ldc_w 315
    //   259: iconst_2
    //   260: anewarray 4	java/lang/Object
    //   263: dup
    //   264: iconst_0
    //   265: aload_2
    //   266: aastore
    //   267: dup
    //   268: iconst_1
    //   269: aload_0
    //   270: invokevirtual 316	java/lang/ClassCastException:getMessage	()Ljava/lang/String;
    //   273: aastore
    //   274: invokeinterface 204 3 0
    //   279: aload 8
    //   281: astore 4
    //   283: aload_1
    //   284: astore_3
    //   285: goto -132 -> 153
    //   288: astore_0
    //   289: invokestatic 188	com/adjust/sdk/Util:getLogger	()Lcom/adjust/sdk/ILogger;
    //   292: ldc_w 318
    //   295: iconst_2
    //   296: anewarray 4	java/lang/Object
    //   299: dup
    //   300: iconst_0
    //   301: aload_2
    //   302: aastore
    //   303: dup
    //   304: iconst_1
    //   305: aload_0
    //   306: aastore
    //   307: invokeinterface 204 3 0
    //   312: aload 6
    //   314: astore 4
    //   316: aload 5
    //   318: astore_3
    //   319: goto -166 -> 153
    //   322: astore_0
    //   323: aload 8
    //   325: astore 4
    //   327: aload 7
    //   329: astore_3
    //   330: aload 8
    //   332: astore 6
    //   334: aload 7
    //   336: astore 5
    //   338: invokestatic 188	com/adjust/sdk/Util:getLogger	()Lcom/adjust/sdk/ILogger;
    //   341: ldc_w 320
    //   344: iconst_2
    //   345: anewarray 4	java/lang/Object
    //   348: dup
    //   349: iconst_0
    //   350: aload_2
    //   351: aastore
    //   352: dup
    //   353: iconst_1
    //   354: aload_0
    //   355: invokevirtual 201	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   358: aastore
    //   359: invokeinterface 204 3 0
    //   364: aload 8
    //   366: astore 4
    //   368: aload 7
    //   370: astore_3
    //   371: goto -218 -> 153
    //   374: astore_0
    //   375: invokestatic 188	com/adjust/sdk/Util:getLogger	()Lcom/adjust/sdk/ILogger;
    //   378: ldc_w 322
    //   381: iconst_2
    //   382: anewarray 4	java/lang/Object
    //   385: dup
    //   386: iconst_0
    //   387: aload_2
    //   388: aastore
    //   389: dup
    //   390: iconst_1
    //   391: aload_0
    //   392: aastore
    //   393: invokeinterface 204 3 0
    //   398: aload_3
    //   399: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	400	0	paramContext	Context
    //   0	400	1	paramString1	String
    //   0	400	2	paramString2	String
    //   23	376	3	localObject1	Object
    //   4	363	4	localObject2	Object
    //   26	311	5	localObject3	Object
    //   1	332	6	localObject4	Object
    //   16	353	7	localObject5	Object
    //   13	352	8	localObject6	Object
    //   7	86	9	localObject7	Object
    //   10	86	10	localObject8	Object
    //   19	67	11	localObject9	Object
    //   79	20	12	localObjectInputStream	java.io.ObjectInputStream
    // Exception table:
    //   from	to	target	type
    //   98	105	167	java/lang/ClassNotFoundException
    //   122	146	167	java/lang/ClassNotFoundException
    //   28	34	216	java/io/FileNotFoundException
    //   48	57	216	java/io/FileNotFoundException
    //   71	81	216	java/io/FileNotFoundException
    //   98	105	216	java/io/FileNotFoundException
    //   122	146	216	java/io/FileNotFoundException
    //   181	207	216	java/io/FileNotFoundException
    //   253	279	216	java/io/FileNotFoundException
    //   338	364	216	java/io/FileNotFoundException
    //   98	105	239	java/lang/ClassCastException
    //   122	146	239	java/lang/ClassCastException
    //   28	34	288	java/lang/Exception
    //   48	57	288	java/lang/Exception
    //   71	81	288	java/lang/Exception
    //   181	207	288	java/lang/Exception
    //   253	279	288	java/lang/Exception
    //   338	364	288	java/lang/Exception
    //   98	105	322	java/lang/Exception
    //   122	146	322	java/lang/Exception
    //   158	165	374	java/lang/Exception
  }
  
  public static <T> T readObjectField(ObjectInputStream.GetField paramGetField, String paramString, T paramT)
  {
    try
    {
      paramGetField = paramGetField.get(paramString, paramT);
      return paramGetField;
    }
    catch (Exception paramGetField)
    {
      getLogger().debug("Unable to read '%s' field in migration device with message (%s)", new Object[] { paramString, paramGetField.getMessage() });
    }
    return paramT;
  }
  
  public static String readStringField(ObjectInputStream.GetField paramGetField, String paramString1, String paramString2)
  {
    return (String)readObjectField(paramGetField, paramString1, paramString2);
  }
  
  public static <T> void writeObject(T paramT, Context paramContext, String paramString1, String paramString2)
  {
    Context localContext = null;
    try
    {
      paramContext = paramContext.openFileOutput(paramString1, 0);
      localContext = paramContext;
      paramContext = new BufferedOutputStream(paramContext);
      localContext = paramContext;
      paramString1 = new ObjectOutputStream(paramContext);
      paramContext = paramString1;
      localContext = paramContext;
    }
    catch (Exception paramT)
    {
      for (;;)
      {
        label70:
        getLogger().error("Failed to open %s for writing (%s)", new Object[] { paramString2, paramT });
        paramContext = localContext;
      }
    }
    try
    {
      paramString1.writeObject(paramT);
      localContext = paramContext;
      getLogger().debug("Wrote %s: %s", new Object[] { paramString2, paramT });
    }
    catch (NotSerializableException paramT)
    {
      try
      {
        paramContext.close();
        return;
      }
      catch (Exception paramT)
      {
        getLogger().error("Failed to close %s file for writing (%s)", new Object[] { paramString2, paramT });
      }
      paramT = paramT;
      localContext = paramContext;
      getLogger().error("Failed to serialize %s", new Object[] { paramString2 });
      break label70;
    }
    if (paramContext != null) {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
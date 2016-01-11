package com.mixpanel.android.mpmetrics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.mixpanel.android.util.RemoteService;
import com.mixpanel.android.util.RemoteService.ServiceUnavailableException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

class DecideChecker
{
  private static final JSONArray EMPTY_JSON_ARRAY = new JSONArray();
  private static final String LOGTAG = "MixpanelAPI.DChecker";
  private final List<DecideMessages> mChecks;
  private final MPConfig mConfig;
  private final Context mContext;
  
  public DecideChecker(Context paramContext, MPConfig paramMPConfig)
  {
    this.mContext = paramContext;
    this.mConfig = paramMPConfig;
    this.mChecks = new LinkedList();
  }
  
  private String getDecideResponseFromServer(String paramString1, String paramString2, RemoteService paramRemoteService)
    throws RemoteService.ServiceUnavailableException
  {
    for (;;)
    {
      try
      {
        String str = URLEncoder.encode(paramString1, "utf-8");
        if (paramString2 != null)
        {
          paramString1 = URLEncoder.encode(paramString2, "utf-8");
          paramString2 = new StringBuilder().append("?version=1&lib=android&token=").append(str);
          if (paramString1 != null) {
            paramString2.append("&distinct_id=").append(paramString1);
          }
          paramString2 = paramString2.toString();
          if (this.mConfig.getDisableFallback())
          {
            paramString1 = new String[1];
            paramString1[0] = (this.mConfig.getDecideEndpoint() + paramString2);
            if (!MPConfig.DEBUG) {
              break;
            }
            Log.v("MixpanelAPI.DChecker", "Querying decide server, urls:");
            int i = 0;
            if (i >= paramString1.length) {
              break;
            }
            Log.v("MixpanelAPI.DChecker", "    >> " + paramString1[i]);
            i += 1;
            continue;
          }
        }
        else
        {
          paramString1 = null;
          continue;
        }
        paramString1 = new String[2];
      }
      catch (UnsupportedEncodingException paramString1)
      {
        throw new RuntimeException("Mixpanel library requires utf-8 string encoding to be available", paramString1);
      }
      paramString1[0] = (this.mConfig.getDecideEndpoint() + paramString2);
      paramString1[1] = (this.mConfig.getDecideFallbackEndpoint() + paramString2);
    }
    paramString1 = getUrls(paramRemoteService, this.mContext, paramString1);
    if (paramString1 == null) {
      return null;
    }
    try
    {
      paramString1 = new String(paramString1, "UTF-8");
      return paramString1;
    }
    catch (UnsupportedEncodingException paramString1)
    {
      throw new RuntimeException("UTF not supported on this platform?", paramString1);
    }
  }
  
  @SuppressLint({"NewApi"})
  private static int getDisplayWidth(Display paramDisplay)
  {
    if (Build.VERSION.SDK_INT < 13) {
      return paramDisplay.getWidth();
    }
    Point localPoint = new Point();
    paramDisplay.getSize(localPoint);
    return localPoint.x;
  }
  
  private static Bitmap getNotificationImage(InAppNotification paramInAppNotification, Context paramContext, RemoteService paramRemoteService)
    throws RemoteService.ServiceUnavailableException
  {
    String[] arrayOfString2 = new String[2];
    arrayOfString2[0] = paramInAppNotification.getImage2xUrl();
    arrayOfString2[1] = paramInAppNotification.getImageUrl();
    int i = getDisplayWidth(((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay());
    String[] arrayOfString1 = arrayOfString2;
    if (paramInAppNotification.getType() == InAppNotification.Type.TAKEOVER)
    {
      arrayOfString1 = arrayOfString2;
      if (i >= 720)
      {
        arrayOfString1 = new String[3];
        arrayOfString1[0] = paramInAppNotification.getImage4xUrl();
        arrayOfString1[1] = paramInAppNotification.getImage2xUrl();
        arrayOfString1[2] = paramInAppNotification.getImageUrl();
      }
    }
    paramInAppNotification = getUrls(paramRemoteService, paramContext, arrayOfString1);
    if (paramInAppNotification != null) {
      return BitmapFactory.decodeByteArray(paramInAppNotification, 0, paramInAppNotification.length);
    }
    Log.i("MixpanelAPI.DChecker", "Failed to download images from " + Arrays.toString(arrayOfString1));
    return null;
  }
  
  private static byte[] getUrls(RemoteService paramRemoteService, Context paramContext, String[] paramArrayOfString)
    throws RemoteService.ServiceUnavailableException
  {
    if (!paramRemoteService.isOnline(paramContext)) {
      return null;
    }
    int j = paramArrayOfString.length;
    int i = 0;
    while (i < j)
    {
      String str = paramArrayOfString[i];
      try
      {
        byte[] arrayOfByte = paramRemoteService.performRequest(str, null, MPConfig.getInstance(paramContext).getSSLSocketFactory());
        return arrayOfByte;
      }
      catch (MalformedURLException localMalformedURLException)
      {
        Log.e("MixpanelAPI.DChecker", "Cannot interpret " + str + " as a URL.", localMalformedURLException);
        i += 1;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        for (;;)
        {
          if (MPConfig.DEBUG) {
            Log.v("MixpanelAPI.DChecker", "Cannot get " + str + ", file not found.", localFileNotFoundException);
          }
        }
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          if (MPConfig.DEBUG) {
            Log.v("MixpanelAPI.DChecker", "Cannot get " + str + ".", localIOException);
          }
        }
      }
      catch (OutOfMemoryError paramRemoteService)
      {
        Log.e("MixpanelAPI.DChecker", "Out of memory when getting to " + str + ".", paramRemoteService);
      }
    }
    return null;
  }
  
  /* Error */
  static Result parseDecideResponse(String paramString)
    throws DecideChecker.UnintelligibleMessageException
  {
    // Byte code:
    //   0: new 6	com/mixpanel/android/mpmetrics/DecideChecker$Result
    //   3: dup
    //   4: invokespecial 256	com/mixpanel/android/mpmetrics/DecideChecker$Result:<init>	()V
    //   7: astore 4
    //   9: new 258	org/json/JSONObject
    //   12: dup
    //   13: aload_0
    //   14: invokespecial 261	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   17: astore 5
    //   19: aconst_null
    //   20: astore_3
    //   21: aload_3
    //   22: astore_0
    //   23: aload 5
    //   25: ldc_w 263
    //   28: invokevirtual 267	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   31: ifeq +12 -> 43
    //   34: aload 5
    //   36: ldc_w 263
    //   39: invokevirtual 271	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   42: astore_0
    //   43: aload_0
    //   44: ifnull +174 -> 218
    //   47: iconst_0
    //   48: istore_1
    //   49: iload_1
    //   50: aload_0
    //   51: invokevirtual 274	org/json/JSONArray:length	()I
    //   54: if_icmpge +164 -> 218
    //   57: new 276	com/mixpanel/android/mpmetrics/Survey
    //   60: dup
    //   61: aload_0
    //   62: iload_1
    //   63: invokevirtual 280	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   66: invokespecial 283	com/mixpanel/android/mpmetrics/Survey:<init>	(Lorg/json/JSONObject;)V
    //   69: astore_3
    //   70: aload 4
    //   72: getfield 285	com/mixpanel/android/mpmetrics/DecideChecker$Result:surveys	Ljava/util/List;
    //   75: aload_3
    //   76: invokeinterface 291 2 0
    //   81: pop
    //   82: iload_1
    //   83: iconst_1
    //   84: iadd
    //   85: istore_1
    //   86: goto -37 -> 49
    //   89: astore_3
    //   90: new 9	com/mixpanel/android/mpmetrics/DecideChecker$UnintelligibleMessageException
    //   93: dup
    //   94: new 62	java/lang/StringBuilder
    //   97: dup
    //   98: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   101: ldc_w 293
    //   104: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: aload_0
    //   108: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   114: aload_3
    //   115: invokespecial 296	com/mixpanel/android/mpmetrics/DecideChecker$UnintelligibleMessageException:<init>	(Ljava/lang/String;Lorg/json/JSONException;)V
    //   118: athrow
    //   119: astore_0
    //   120: ldc 16
    //   122: new 62	java/lang/StringBuilder
    //   125: dup
    //   126: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   129: ldc_w 298
    //   132: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: aload 5
    //   137: invokevirtual 301	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   140: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   143: invokestatic 303	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   146: pop
    //   147: aload_3
    //   148: astore_0
    //   149: goto -106 -> 43
    //   152: astore_3
    //   153: ldc 16
    //   155: new 62	java/lang/StringBuilder
    //   158: dup
    //   159: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   162: ldc_w 305
    //   165: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   168: aload_0
    //   169: invokevirtual 306	org/json/JSONArray:toString	()Ljava/lang/String;
    //   172: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   175: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   178: invokestatic 303	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   181: pop
    //   182: goto -100 -> 82
    //   185: astore_3
    //   186: ldc 16
    //   188: new 62	java/lang/StringBuilder
    //   191: dup
    //   192: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   195: ldc_w 305
    //   198: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   201: aload_0
    //   202: invokevirtual 306	org/json/JSONArray:toString	()Ljava/lang/String;
    //   205: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   211: invokestatic 303	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   214: pop
    //   215: goto -133 -> 82
    //   218: aconst_null
    //   219: astore_3
    //   220: aload_3
    //   221: astore_0
    //   222: aload 5
    //   224: ldc_w 308
    //   227: invokevirtual 267	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   230: ifeq +12 -> 242
    //   233: aload 5
    //   235: ldc_w 308
    //   238: invokevirtual 271	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   241: astore_0
    //   242: aload_0
    //   243: ifnull +186 -> 429
    //   246: aload_0
    //   247: invokevirtual 274	org/json/JSONArray:length	()I
    //   250: iconst_2
    //   251: invokestatic 314	java/lang/Math:min	(II)I
    //   254: istore_2
    //   255: iconst_0
    //   256: istore_1
    //   257: iload_1
    //   258: iload_2
    //   259: if_icmpge +170 -> 429
    //   262: new 154	com/mixpanel/android/mpmetrics/InAppNotification
    //   265: dup
    //   266: aload_0
    //   267: iload_1
    //   268: invokevirtual 280	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   271: invokespecial 315	com/mixpanel/android/mpmetrics/InAppNotification:<init>	(Lorg/json/JSONObject;)V
    //   274: astore_3
    //   275: aload 4
    //   277: getfield 317	com/mixpanel/android/mpmetrics/DecideChecker$Result:notifications	Ljava/util/List;
    //   280: aload_3
    //   281: invokeinterface 291 2 0
    //   286: pop
    //   287: iload_1
    //   288: iconst_1
    //   289: iadd
    //   290: istore_1
    //   291: goto -34 -> 257
    //   294: astore_0
    //   295: ldc 16
    //   297: new 62	java/lang/StringBuilder
    //   300: dup
    //   301: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   304: ldc_w 319
    //   307: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   310: aload 5
    //   312: invokevirtual 301	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   315: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   318: invokestatic 303	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   321: pop
    //   322: aload_3
    //   323: astore_0
    //   324: goto -82 -> 242
    //   327: astore_3
    //   328: ldc 16
    //   330: new 62	java/lang/StringBuilder
    //   333: dup
    //   334: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   337: ldc_w 321
    //   340: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   343: aload_0
    //   344: invokevirtual 306	org/json/JSONArray:toString	()Ljava/lang/String;
    //   347: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   350: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   353: aload_3
    //   354: invokestatic 239	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   357: pop
    //   358: goto -71 -> 287
    //   361: astore_3
    //   362: ldc 16
    //   364: new 62	java/lang/StringBuilder
    //   367: dup
    //   368: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   371: ldc_w 321
    //   374: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   377: aload_0
    //   378: invokevirtual 306	org/json/JSONArray:toString	()Ljava/lang/String;
    //   381: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   384: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   387: aload_3
    //   388: invokestatic 239	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   391: pop
    //   392: goto -105 -> 287
    //   395: astore_3
    //   396: ldc 16
    //   398: new 62	java/lang/StringBuilder
    //   401: dup
    //   402: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   405: ldc_w 323
    //   408: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   411: aload_0
    //   412: invokevirtual 306	org/json/JSONArray:toString	()Ljava/lang/String;
    //   415: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   418: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   421: aload_3
    //   422: invokestatic 239	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   425: pop
    //   426: goto -139 -> 287
    //   429: aload 5
    //   431: ldc_w 325
    //   434: invokevirtual 267	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   437: ifeq +16 -> 453
    //   440: aload 4
    //   442: aload 5
    //   444: ldc_w 325
    //   447: invokevirtual 271	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   450: putfield 328	com/mixpanel/android/mpmetrics/DecideChecker$Result:eventBindings	Lorg/json/JSONArray;
    //   453: aload 5
    //   455: ldc_w 330
    //   458: invokevirtual 267	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   461: ifeq +16 -> 477
    //   464: aload 4
    //   466: aload 5
    //   468: ldc_w 330
    //   471: invokevirtual 271	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   474: putfield 332	com/mixpanel/android/mpmetrics/DecideChecker$Result:variants	Lorg/json/JSONArray;
    //   477: aload 4
    //   479: areturn
    //   480: astore_0
    //   481: ldc 16
    //   483: new 62	java/lang/StringBuilder
    //   486: dup
    //   487: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   490: ldc_w 334
    //   493: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   496: aload 5
    //   498: invokevirtual 301	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   501: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   504: invokestatic 303	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   507: pop
    //   508: goto -55 -> 453
    //   511: astore_0
    //   512: ldc 16
    //   514: new 62	java/lang/StringBuilder
    //   517: dup
    //   518: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   521: ldc_w 336
    //   524: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   527: aload 5
    //   529: invokevirtual 301	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   532: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   535: invokestatic 303	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   538: pop
    //   539: aload 4
    //   541: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	542	0	paramString	String
    //   48	243	1	i	int
    //   254	6	2	j	int
    //   20	56	3	localSurvey	Survey
    //   89	59	3	localJSONException1	JSONException
    //   152	1	3	localJSONException2	JSONException
    //   185	1	3	localBadDecideObjectException1	BadDecideObjectException
    //   219	104	3	localInAppNotification	InAppNotification
    //   327	27	3	localJSONException3	JSONException
    //   361	27	3	localBadDecideObjectException2	BadDecideObjectException
    //   395	27	3	localOutOfMemoryError	OutOfMemoryError
    //   7	533	4	localResult	Result
    //   17	511	5	localJSONObject	org.json.JSONObject
    // Exception table:
    //   from	to	target	type
    //   9	19	89	org/json/JSONException
    //   34	43	119	org/json/JSONException
    //   57	82	152	org/json/JSONException
    //   57	82	185	com/mixpanel/android/mpmetrics/BadDecideObjectException
    //   233	242	294	org/json/JSONException
    //   262	287	327	org/json/JSONException
    //   262	287	361	com/mixpanel/android/mpmetrics/BadDecideObjectException
    //   262	287	395	java/lang/OutOfMemoryError
    //   440	453	480	org/json/JSONException
    //   464	477	511	org/json/JSONException
  }
  
  private Result runDecideCheck(String paramString1, String paramString2, RemoteService paramRemoteService)
    throws RemoteService.ServiceUnavailableException, DecideChecker.UnintelligibleMessageException
  {
    paramString2 = getDecideResponseFromServer(paramString1, paramString2, paramRemoteService);
    if (MPConfig.DEBUG) {
      Log.v("MixpanelAPI.DChecker", "Mixpanel decide server response was:\n" + paramString2);
    }
    paramString1 = new Result();
    if (paramString2 != null) {
      paramString1 = parseDecideResponse(paramString2);
    }
    paramString2 = paramString1.notifications.iterator();
    while (paramString2.hasNext())
    {
      InAppNotification localInAppNotification = (InAppNotification)paramString2.next();
      Bitmap localBitmap = getNotificationImage(localInAppNotification, this.mContext, paramRemoteService);
      if (localBitmap == null)
      {
        Log.i("MixpanelAPI.DChecker", "Could not retrieve image for notification " + localInAppNotification.getId() + ", will not show the notification.");
        paramString2.remove();
      }
      else
      {
        localInAppNotification.setImage(localBitmap);
      }
    }
    return paramString1;
  }
  
  public void addDecideCheck(DecideMessages paramDecideMessages)
  {
    this.mChecks.add(paramDecideMessages);
  }
  
  public void runDecideChecks(RemoteService paramRemoteService)
    throws RemoteService.ServiceUnavailableException
  {
    Iterator localIterator = this.mChecks.iterator();
    while (localIterator.hasNext())
    {
      DecideMessages localDecideMessages = (DecideMessages)localIterator.next();
      Object localObject = localDecideMessages.getDistinctId();
      try
      {
        localObject = runDecideCheck(localDecideMessages.getToken(), (String)localObject, paramRemoteService);
        localDecideMessages.reportResults(((Result)localObject).surveys, ((Result)localObject).notifications, ((Result)localObject).eventBindings, ((Result)localObject).variants);
      }
      catch (UnintelligibleMessageException localUnintelligibleMessageException)
      {
        Log.e("MixpanelAPI.DChecker", localUnintelligibleMessageException.getMessage(), localUnintelligibleMessageException);
      }
    }
  }
  
  static class Result
  {
    public JSONArray eventBindings = DecideChecker.EMPTY_JSON_ARRAY;
    public final List<InAppNotification> notifications = new ArrayList();
    public final List<Survey> surveys = new ArrayList();
    public JSONArray variants = DecideChecker.EMPTY_JSON_ARRAY;
  }
  
  static class UnintelligibleMessageException
    extends Exception
  {
    private static final long serialVersionUID = -6501269367559104957L;
    
    public UnintelligibleMessageException(String paramString, JSONException paramJSONException)
    {
      super(paramJSONException);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/mpmetrics/DecideChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.ad4screen.sdk.common.persistence;

import android.content.Context;
import com.ad4screen.sdk.Log;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class b
  implements a
{
  public JSONObject a = new JSONObject();
  private final String b;
  private final Context c;
  private boolean d;
  
  public b(Context paramContext, String paramString)
  {
    this.b = paramString;
    this.c = paramContext;
    this.a = a(paramContext, this.b);
  }
  
  /* Error */
  @android.annotation.SuppressLint({"NewApi"})
  private JSONObject a(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 4
    //   3: aload_1
    //   4: ldc 47
    //   6: ldc 49
    //   8: invokestatic 54	com/ad4screen/sdk/common/i:a	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/String;
    //   11: astore 5
    //   13: iload 4
    //   15: istore_3
    //   16: aload 5
    //   18: ifnull +18 -> 36
    //   21: iload 4
    //   23: istore_3
    //   24: aload 5
    //   26: ldc 56
    //   28: invokevirtual 62	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   31: ifeq +5 -> 36
    //   34: iconst_1
    //   35: istore_3
    //   36: iload_3
    //   37: ifeq +37 -> 74
    //   40: new 64	java/io/FileInputStream
    //   43: dup
    //   44: new 66	java/io/File
    //   47: dup
    //   48: aload_1
    //   49: invokestatic 71	com/ad4screen/sdk/common/compatibility/k$e:a	(Landroid/content/Context;)Ljava/io/File;
    //   52: aload_2
    //   53: invokespecial 74	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   56: invokespecial 77	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   59: astore 5
    //   61: aload 5
    //   63: ifnonnull +21 -> 84
    //   66: new 21	org/json/JSONObject
    //   69: dup
    //   70: invokespecial 22	org/json/JSONObject:<init>	()V
    //   73: areturn
    //   74: aload_1
    //   75: aload_2
    //   76: invokevirtual 83	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   79: astore 5
    //   81: goto -20 -> 61
    //   84: new 85	java/lang/StringBuffer
    //   87: dup
    //   88: ldc 87
    //   90: invokespecial 90	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
    //   93: astore 6
    //   95: sipush 1024
    //   98: newarray <illegal type>
    //   100: astore 7
    //   102: aload 5
    //   104: aload 7
    //   106: invokevirtual 94	java/io/FileInputStream:read	([B)I
    //   109: iconst_m1
    //   110: if_icmpeq +52 -> 162
    //   113: aload 6
    //   115: new 58	java/lang/String
    //   118: dup
    //   119: aload 7
    //   121: invokespecial 97	java/lang/String:<init>	([B)V
    //   124: invokevirtual 101	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   127: pop
    //   128: goto -26 -> 102
    //   131: astore_1
    //   132: new 103	java/lang/StringBuilder
    //   135: dup
    //   136: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   139: ldc 106
    //   141: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   144: aload_2
    //   145: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   148: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   151: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   154: new 21	org/json/JSONObject
    //   157: dup
    //   158: invokespecial 22	org/json/JSONObject:<init>	()V
    //   161: areturn
    //   162: aload 5
    //   164: invokevirtual 121	java/io/FileInputStream:close	()V
    //   167: new 21	org/json/JSONObject
    //   170: dup
    //   171: aload 6
    //   173: invokevirtual 122	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   176: invokespecial 123	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   179: astore 6
    //   181: aload 6
    //   183: ifnull -29 -> 154
    //   186: ldc 87
    //   188: astore 5
    //   190: aload 6
    //   192: ldc 125
    //   194: invokevirtual 128	org/json/JSONObject:isNull	(Ljava/lang/String;)Z
    //   197: ifne +12 -> 209
    //   200: aload 6
    //   202: ldc 125
    //   204: invokevirtual 132	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   207: astore 5
    //   209: aload_1
    //   210: invokevirtual 136	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   213: astore 7
    //   215: aload_1
    //   216: invokevirtual 140	android/content/Context:getApplicationInfo	()Landroid/content/pm/ApplicationInfo;
    //   219: astore_1
    //   220: aload 7
    //   222: aload_1
    //   223: getfield 145	android/content/pm/ApplicationInfo:packageName	Ljava/lang/String;
    //   226: iconst_0
    //   227: invokevirtual 151	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   230: astore 7
    //   232: aload 7
    //   234: aload_1
    //   235: invokestatic 154	com/ad4screen/sdk/common/i:a	(Landroid/content/pm/PackageInfo;Landroid/content/pm/ApplicationInfo;)Ljava/lang/String;
    //   238: astore_1
    //   239: getstatic 160	android/os/Build$VERSION:SDK_INT	I
    //   242: bipush 9
    //   244: if_icmplt +80 -> 324
    //   247: aload_1
    //   248: new 162	java/util/Date
    //   251: dup
    //   252: aload 7
    //   254: getfield 168	android/content/pm/PackageInfo:lastUpdateTime	J
    //   257: invokespecial 171	java/util/Date:<init>	(J)V
    //   260: getstatic 176	com/ad4screen/sdk/common/h$a:b	Lcom/ad4screen/sdk/common/h$a;
    //   263: invokestatic 181	com/ad4screen/sdk/common/h:a	(Ljava/util/Date;Lcom/ad4screen/sdk/common/h$a;)Ljava/lang/String;
    //   266: invokevirtual 62	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   269: ifeq +55 -> 324
    //   272: aload 5
    //   274: aload_1
    //   275: invokevirtual 62	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   278: ifne +46 -> 324
    //   281: new 103	java/lang/StringBuilder
    //   284: dup
    //   285: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   288: ldc -73
    //   290: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   293: aload_2
    //   294: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: ldc -71
    //   299: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   302: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   305: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   308: new 21	org/json/JSONObject
    //   311: dup
    //   312: invokespecial 22	org/json/JSONObject:<init>	()V
    //   315: astore_1
    //   316: aload_1
    //   317: areturn
    //   318: astore_1
    //   319: ldc -69
    //   321: invokestatic 190	com/ad4screen/sdk/Log:warn	(Ljava/lang/String;)V
    //   324: aload 6
    //   326: ldc -64
    //   328: invokevirtual 128	org/json/JSONObject:isNull	(Ljava/lang/String;)Z
    //   331: ifne +151 -> 482
    //   334: aload_0
    //   335: invokevirtual 195	com/ad4screen/sdk/common/persistence/b:b	()I
    //   338: istore_3
    //   339: aload 6
    //   341: ldc -64
    //   343: invokevirtual 199	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   346: istore 4
    //   348: iload 4
    //   350: iload_3
    //   351: if_icmpne +6 -> 357
    //   354: aload 6
    //   356: areturn
    //   357: new 103	java/lang/StringBuilder
    //   360: dup
    //   361: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   364: ldc -55
    //   366: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   369: aload_2
    //   370: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   373: ldc -53
    //   375: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   378: iload 4
    //   380: invokevirtual 206	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   383: ldc -48
    //   385: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   388: iload_3
    //   389: invokevirtual 206	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   392: ldc 87
    //   394: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   397: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   400: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   403: aload_0
    //   404: iload 4
    //   406: aload 6
    //   408: invokevirtual 211	com/ad4screen/sdk/common/persistence/b:a	(ILorg/json/JSONObject;)Z
    //   411: ifeq +6 -> 417
    //   414: aload 6
    //   416: areturn
    //   417: new 103	java/lang/StringBuilder
    //   420: dup
    //   421: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   424: ldc -55
    //   426: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   429: aload_2
    //   430: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   433: ldc -43
    //   435: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   438: iload_3
    //   439: invokevirtual 206	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   442: ldc -41
    //   444: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   447: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   450: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   453: goto -299 -> 154
    //   456: astore_1
    //   457: new 103	java/lang/StringBuilder
    //   460: dup
    //   461: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   464: ldc -39
    //   466: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   469: aload_2
    //   470: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   473: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   476: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   479: goto -325 -> 154
    //   482: new 103	java/lang/StringBuilder
    //   485: dup
    //   486: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   489: ldc -55
    //   491: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   494: aload_2
    //   495: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   498: ldc -37
    //   500: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   503: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   506: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   509: goto -355 -> 154
    //   512: astore_1
    //   513: new 103	java/lang/StringBuilder
    //   516: dup
    //   517: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   520: ldc -35
    //   522: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   525: aload_2
    //   526: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   529: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   532: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   535: goto -381 -> 154
    //   538: astore_1
    //   539: goto -215 -> 324
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	542	0	this	b
    //   0	542	1	paramContext	Context
    //   0	542	2	paramString	String
    //   15	424	3	i	int
    //   1	404	4	j	int
    //   11	262	5	localObject1	Object
    //   93	322	6	localObject2	Object
    //   100	153	7	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   3	13	131	java/io/FileNotFoundException
    //   24	34	131	java/io/FileNotFoundException
    //   40	61	131	java/io/FileNotFoundException
    //   66	74	131	java/io/FileNotFoundException
    //   74	81	131	java/io/FileNotFoundException
    //   84	102	131	java/io/FileNotFoundException
    //   102	128	131	java/io/FileNotFoundException
    //   162	181	131	java/io/FileNotFoundException
    //   190	209	131	java/io/FileNotFoundException
    //   209	316	131	java/io/FileNotFoundException
    //   319	324	131	java/io/FileNotFoundException
    //   324	348	131	java/io/FileNotFoundException
    //   357	414	131	java/io/FileNotFoundException
    //   417	453	131	java/io/FileNotFoundException
    //   482	509	131	java/io/FileNotFoundException
    //   209	316	318	android/content/pm/PackageManager$NameNotFoundException
    //   3	13	456	java/io/IOException
    //   24	34	456	java/io/IOException
    //   40	61	456	java/io/IOException
    //   66	74	456	java/io/IOException
    //   74	81	456	java/io/IOException
    //   84	102	456	java/io/IOException
    //   102	128	456	java/io/IOException
    //   162	181	456	java/io/IOException
    //   190	209	456	java/io/IOException
    //   209	316	456	java/io/IOException
    //   319	324	456	java/io/IOException
    //   324	348	456	java/io/IOException
    //   357	414	456	java/io/IOException
    //   417	453	456	java/io/IOException
    //   482	509	456	java/io/IOException
    //   3	13	512	org/json/JSONException
    //   24	34	512	org/json/JSONException
    //   40	61	512	org/json/JSONException
    //   66	74	512	org/json/JSONException
    //   74	81	512	org/json/JSONException
    //   84	102	512	org/json/JSONException
    //   102	128	512	org/json/JSONException
    //   162	181	512	org/json/JSONException
    //   190	209	512	org/json/JSONException
    //   209	316	512	org/json/JSONException
    //   319	324	512	org/json/JSONException
    //   324	348	512	org/json/JSONException
    //   357	414	512	org/json/JSONException
    //   417	453	512	org/json/JSONException
    //   482	509	512	org/json/JSONException
    //   209	316	538	java/lang/RuntimeException
  }
  
  /* Error */
  private void a(Context paramContext, String paramString, JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 10
    //   3: aconst_null
    //   4: astore 11
    //   6: aconst_null
    //   7: astore 12
    //   9: aconst_null
    //   10: astore 13
    //   12: aconst_null
    //   13: astore 4
    //   15: aload 4
    //   17: astore 6
    //   19: aload 10
    //   21: astore 7
    //   23: aload 11
    //   25: astore 8
    //   27: aload 12
    //   29: astore 9
    //   31: aload 13
    //   33: astore 5
    //   35: aload_1
    //   36: invokestatic 230	com/ad4screen/sdk/systems/b:a	(Landroid/content/Context;)Lcom/ad4screen/sdk/systems/b;
    //   39: getfield 233	com/ad4screen/sdk/systems/b:A	Z
    //   42: ifeq +274 -> 316
    //   45: aload 4
    //   47: astore 6
    //   49: aload 10
    //   51: astore 7
    //   53: aload 11
    //   55: astore 8
    //   57: aload 12
    //   59: astore 9
    //   61: aload 13
    //   63: astore 5
    //   65: new 235	java/io/FileOutputStream
    //   68: dup
    //   69: new 66	java/io/File
    //   72: dup
    //   73: aload_1
    //   74: invokestatic 71	com/ad4screen/sdk/common/compatibility/k$e:a	(Landroid/content/Context;)Ljava/io/File;
    //   77: aload_2
    //   78: invokespecial 74	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   81: invokespecial 236	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   84: astore 4
    //   86: aload 4
    //   88: astore 6
    //   90: aload 4
    //   92: astore 7
    //   94: aload 4
    //   96: astore 8
    //   98: aload 4
    //   100: astore 9
    //   102: aload 4
    //   104: astore 5
    //   106: aload_3
    //   107: ldc -64
    //   109: aload_0
    //   110: invokevirtual 195	com/ad4screen/sdk/common/persistence/b:b	()I
    //   113: invokevirtual 240	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
    //   116: pop
    //   117: aload 4
    //   119: astore 6
    //   121: aload 4
    //   123: astore 7
    //   125: aload 4
    //   127: astore 8
    //   129: aload 4
    //   131: astore 5
    //   133: aload_1
    //   134: invokevirtual 136	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   137: astore 9
    //   139: aload 4
    //   141: astore 6
    //   143: aload 4
    //   145: astore 7
    //   147: aload 4
    //   149: astore 8
    //   151: aload 4
    //   153: astore 5
    //   155: aload_1
    //   156: invokevirtual 140	android/content/Context:getApplicationInfo	()Landroid/content/pm/ApplicationInfo;
    //   159: astore_1
    //   160: aload 4
    //   162: astore 6
    //   164: aload 4
    //   166: astore 7
    //   168: aload 4
    //   170: astore 8
    //   172: aload 4
    //   174: astore 5
    //   176: aload_3
    //   177: ldc 125
    //   179: aload 9
    //   181: aload_1
    //   182: getfield 145	android/content/pm/ApplicationInfo:packageName	Ljava/lang/String;
    //   185: iconst_0
    //   186: invokevirtual 151	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   189: aload_1
    //   190: invokestatic 154	com/ad4screen/sdk/common/i:a	(Landroid/content/pm/PackageInfo;Landroid/content/pm/ApplicationInfo;)Ljava/lang/String;
    //   193: invokevirtual 243	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   196: pop
    //   197: aload 4
    //   199: astore 6
    //   201: aload 4
    //   203: astore 7
    //   205: aload 4
    //   207: astore 8
    //   209: aload 4
    //   211: astore 9
    //   213: aload 4
    //   215: astore 5
    //   217: aload_3
    //   218: invokevirtual 244	org/json/JSONObject:toString	()Ljava/lang/String;
    //   221: astore_1
    //   222: aload_1
    //   223: ifnull +32 -> 255
    //   226: aload 4
    //   228: astore 6
    //   230: aload 4
    //   232: astore 7
    //   234: aload 4
    //   236: astore 8
    //   238: aload 4
    //   240: astore 9
    //   242: aload 4
    //   244: astore 5
    //   246: aload 4
    //   248: aload_1
    //   249: invokevirtual 248	java/lang/String:getBytes	()[B
    //   252: invokevirtual 251	java/io/FileOutputStream:write	([B)V
    //   255: aload 4
    //   257: astore 6
    //   259: aload 4
    //   261: astore 7
    //   263: aload 4
    //   265: astore 8
    //   267: aload 4
    //   269: astore 9
    //   271: aload 4
    //   273: astore 5
    //   275: aload 4
    //   277: invokevirtual 254	java/io/FileOutputStream:flush	()V
    //   280: aload 4
    //   282: astore 6
    //   284: aload 4
    //   286: astore 7
    //   288: aload 4
    //   290: astore 8
    //   292: aload 4
    //   294: astore 9
    //   296: aload 4
    //   298: astore 5
    //   300: aload 4
    //   302: invokevirtual 255	java/io/FileOutputStream:close	()V
    //   305: aload 4
    //   307: ifnull +8 -> 315
    //   310: aload 4
    //   312: invokevirtual 255	java/io/FileOutputStream:close	()V
    //   315: return
    //   316: aload 4
    //   318: astore 6
    //   320: aload 10
    //   322: astore 7
    //   324: aload 11
    //   326: astore 8
    //   328: aload 12
    //   330: astore 9
    //   332: aload 13
    //   334: astore 5
    //   336: aload_1
    //   337: aload_2
    //   338: iconst_0
    //   339: invokevirtual 259	android/content/Context:openFileOutput	(Ljava/lang/String;I)Ljava/io/FileOutputStream;
    //   342: astore 4
    //   344: goto -258 -> 86
    //   347: astore_1
    //   348: aload 4
    //   350: astore 6
    //   352: aload 4
    //   354: astore 7
    //   356: aload 4
    //   358: astore 8
    //   360: aload 4
    //   362: astore 9
    //   364: aload 4
    //   366: astore 5
    //   368: ldc -69
    //   370: invokestatic 190	com/ad4screen/sdk/Log:warn	(Ljava/lang/String;)V
    //   373: goto -176 -> 197
    //   376: astore_1
    //   377: aload 6
    //   379: astore 5
    //   381: ldc_w 261
    //   384: aload_1
    //   385: invokestatic 264	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   388: aload 6
    //   390: ifnull -75 -> 315
    //   393: aload 6
    //   395: invokevirtual 255	java/io/FileOutputStream:close	()V
    //   398: return
    //   399: astore_1
    //   400: new 103	java/lang/StringBuilder
    //   403: dup
    //   404: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   407: ldc_w 266
    //   410: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   413: aload_2
    //   414: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   417: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   420: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   423: return
    //   424: astore_1
    //   425: aload 4
    //   427: astore 6
    //   429: aload 4
    //   431: astore 7
    //   433: aload 4
    //   435: astore 8
    //   437: aload 4
    //   439: astore 9
    //   441: aload 4
    //   443: astore 5
    //   445: ldc_w 268
    //   448: aload_1
    //   449: invokestatic 264	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   452: goto -255 -> 197
    //   455: astore_1
    //   456: aload 7
    //   458: astore 5
    //   460: new 103	java/lang/StringBuilder
    //   463: dup
    //   464: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   467: ldc_w 270
    //   470: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   473: aload_2
    //   474: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   477: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   480: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   483: aload 7
    //   485: ifnull -170 -> 315
    //   488: aload 7
    //   490: invokevirtual 255	java/io/FileOutputStream:close	()V
    //   493: return
    //   494: astore_1
    //   495: new 103	java/lang/StringBuilder
    //   498: dup
    //   499: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   502: ldc_w 266
    //   505: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   508: aload_2
    //   509: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   512: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   515: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   518: return
    //   519: astore_1
    //   520: new 103	java/lang/StringBuilder
    //   523: dup
    //   524: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   527: ldc_w 266
    //   530: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   533: aload_2
    //   534: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   537: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   540: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   543: return
    //   544: astore_1
    //   545: aload 8
    //   547: astore 5
    //   549: new 103	java/lang/StringBuilder
    //   552: dup
    //   553: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   556: ldc_w 272
    //   559: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   562: aload_2
    //   563: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   566: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   569: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   572: aload 8
    //   574: ifnull -259 -> 315
    //   577: aload 8
    //   579: invokevirtual 255	java/io/FileOutputStream:close	()V
    //   582: return
    //   583: astore_1
    //   584: new 103	java/lang/StringBuilder
    //   587: dup
    //   588: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   591: ldc_w 266
    //   594: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   597: aload_2
    //   598: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   601: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   604: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   607: return
    //   608: astore_1
    //   609: aload 9
    //   611: astore 5
    //   613: ldc_w 274
    //   616: aload_1
    //   617: invokestatic 264	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   620: aload 9
    //   622: ifnull -307 -> 315
    //   625: aload 9
    //   627: invokevirtual 255	java/io/FileOutputStream:close	()V
    //   630: return
    //   631: astore_1
    //   632: new 103	java/lang/StringBuilder
    //   635: dup
    //   636: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   639: ldc_w 266
    //   642: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   645: aload_2
    //   646: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   649: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   652: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   655: return
    //   656: astore_1
    //   657: aload 5
    //   659: ifnull +8 -> 667
    //   662: aload 5
    //   664: invokevirtual 255	java/io/FileOutputStream:close	()V
    //   667: aload_1
    //   668: athrow
    //   669: astore_3
    //   670: new 103	java/lang/StringBuilder
    //   673: dup
    //   674: invokespecial 104	java/lang/StringBuilder:<init>	()V
    //   677: ldc_w 266
    //   680: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   683: aload_2
    //   684: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   687: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   690: invokestatic 118	com/ad4screen/sdk/Log:internal	(Ljava/lang/String;)V
    //   693: goto -26 -> 667
    //   696: astore_1
    //   697: goto -500 -> 197
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	700	0	this	b
    //   0	700	1	paramContext	Context
    //   0	700	2	paramString	String
    //   0	700	3	paramJSONObject	JSONObject
    //   13	429	4	localFileOutputStream1	java.io.FileOutputStream
    //   33	630	5	localObject1	Object
    //   17	411	6	localFileOutputStream2	java.io.FileOutputStream
    //   21	468	7	localObject2	Object
    //   25	553	8	localObject3	Object
    //   29	597	9	localObject4	Object
    //   1	320	10	localObject5	Object
    //   4	321	11	localObject6	Object
    //   7	322	12	localObject7	Object
    //   10	323	13	localObject8	Object
    // Exception table:
    //   from	to	target	type
    //   133	139	347	android/content/pm/PackageManager$NameNotFoundException
    //   155	160	347	android/content/pm/PackageManager$NameNotFoundException
    //   176	197	347	android/content/pm/PackageManager$NameNotFoundException
    //   35	45	376	java/util/ConcurrentModificationException
    //   65	86	376	java/util/ConcurrentModificationException
    //   106	117	376	java/util/ConcurrentModificationException
    //   133	139	376	java/util/ConcurrentModificationException
    //   155	160	376	java/util/ConcurrentModificationException
    //   176	197	376	java/util/ConcurrentModificationException
    //   217	222	376	java/util/ConcurrentModificationException
    //   246	255	376	java/util/ConcurrentModificationException
    //   275	280	376	java/util/ConcurrentModificationException
    //   300	305	376	java/util/ConcurrentModificationException
    //   336	344	376	java/util/ConcurrentModificationException
    //   368	373	376	java/util/ConcurrentModificationException
    //   445	452	376	java/util/ConcurrentModificationException
    //   393	398	399	java/io/IOException
    //   133	139	424	org/json/JSONException
    //   155	160	424	org/json/JSONException
    //   176	197	424	org/json/JSONException
    //   35	45	455	java/io/FileNotFoundException
    //   65	86	455	java/io/FileNotFoundException
    //   106	117	455	java/io/FileNotFoundException
    //   133	139	455	java/io/FileNotFoundException
    //   155	160	455	java/io/FileNotFoundException
    //   176	197	455	java/io/FileNotFoundException
    //   217	222	455	java/io/FileNotFoundException
    //   246	255	455	java/io/FileNotFoundException
    //   275	280	455	java/io/FileNotFoundException
    //   300	305	455	java/io/FileNotFoundException
    //   336	344	455	java/io/FileNotFoundException
    //   368	373	455	java/io/FileNotFoundException
    //   445	452	455	java/io/FileNotFoundException
    //   488	493	494	java/io/IOException
    //   310	315	519	java/io/IOException
    //   35	45	544	java/io/IOException
    //   65	86	544	java/io/IOException
    //   106	117	544	java/io/IOException
    //   133	139	544	java/io/IOException
    //   155	160	544	java/io/IOException
    //   176	197	544	java/io/IOException
    //   217	222	544	java/io/IOException
    //   246	255	544	java/io/IOException
    //   275	280	544	java/io/IOException
    //   300	305	544	java/io/IOException
    //   336	344	544	java/io/IOException
    //   368	373	544	java/io/IOException
    //   445	452	544	java/io/IOException
    //   577	582	583	java/io/IOException
    //   35	45	608	org/json/JSONException
    //   65	86	608	org/json/JSONException
    //   106	117	608	org/json/JSONException
    //   217	222	608	org/json/JSONException
    //   246	255	608	org/json/JSONException
    //   275	280	608	org/json/JSONException
    //   300	305	608	org/json/JSONException
    //   336	344	608	org/json/JSONException
    //   368	373	608	org/json/JSONException
    //   445	452	608	org/json/JSONException
    //   625	630	631	java/io/IOException
    //   35	45	656	finally
    //   65	86	656	finally
    //   106	117	656	finally
    //   133	139	656	finally
    //   155	160	656	finally
    //   176	197	656	finally
    //   217	222	656	finally
    //   246	255	656	finally
    //   275	280	656	finally
    //   300	305	656	finally
    //   336	344	656	finally
    //   368	373	656	finally
    //   381	388	656	finally
    //   445	452	656	finally
    //   460	483	656	finally
    //   549	572	656	finally
    //   613	620	656	finally
    //   662	667	669	java/io/IOException
    //   133	139	696	java/lang/RuntimeException
    //   155	160	696	java/lang/RuntimeException
    //   176	197	696	java/lang/RuntimeException
  }
  
  public int a(String paramString, int paramInt)
  {
    try
    {
      paramString = this.a.get(paramString);
      if (!(paramString instanceof Integer)) {
        return paramInt;
      }
      int i = ((Integer)paramString).intValue();
      return i;
    }
    catch (JSONException paramString) {}
    return paramInt;
  }
  
  public long a(String paramString, long paramLong)
  {
    try
    {
      paramString = this.a.get(paramString);
      if (!(paramString instanceof Long)) {
        return paramLong;
      }
      long l = ((Long)paramString).longValue();
      return l;
    }
    catch (JSONException paramString) {}
    return paramLong;
  }
  
  public a a(String paramString, Object paramObject)
  {
    try
    {
      JSONObject localJSONObject = new e().a(paramObject);
      if (localJSONObject != null) {
        this.a.put(paramString, localJSONObject);
      }
      while (!this.d)
      {
        d();
        return this;
        this.a.put(paramString, paramObject);
      }
      return this;
    }
    catch (JSONException paramString)
    {
      Log.debug("JSONArchive|Error while putting data", paramString);
    }
  }
  
  public String a(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = this.a.get(paramString1);
      if (!(paramString1 instanceof String)) {
        return paramString2;
      }
      paramString1 = (String)paramString1;
      return paramString1;
    }
    catch (JSONException paramString1) {}
    return paramString2;
  }
  
  public void a(String paramString)
  {
    this.a.remove(paramString);
    if (!this.d) {
      d();
    }
  }
  
  public boolean a(String paramString, boolean paramBoolean)
  {
    try
    {
      paramString = this.a.get(paramString);
      if (!(paramString instanceof Boolean)) {
        return paramBoolean;
      }
      boolean bool = ((Boolean)paramString).booleanValue();
      return bool;
    }
    catch (JSONException paramString) {}
    return paramBoolean;
  }
  
  public <T> T b(String paramString, T paramT)
  {
    try
    {
      paramString = this.a.getString(paramString);
      paramString = new e().a(paramString, paramT);
      return paramString;
    }
    catch (JSONException paramString) {}
    return paramT;
  }
  
  public void c()
  {
    this.a = new JSONObject();
    if (!this.d) {
      d();
    }
  }
  
  public void d()
  {
    this.d = false;
    a(this.c, this.b, this.a);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
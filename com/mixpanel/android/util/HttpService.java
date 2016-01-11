package com.mixpanel.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.mixpanel.android.mpmetrics.MPConfig;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpService
  implements RemoteService
{
  private static final String LOGTAG = "MixpanelAPI.Message";
  
  private static byte[] slurp(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[' '];
    for (;;)
    {
      int i = paramInputStream.read(arrayOfByte, 0, arrayOfByte.length);
      if (i == -1) {
        break;
      }
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
    localByteArrayOutputStream.flush();
    return localByteArrayOutputStream.toByteArray();
  }
  
  public boolean isOnline(Context paramContext)
  {
    for (;;)
    {
      try
      {
        paramContext = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if ((paramContext != null) && (paramContext.isConnectedOrConnecting()))
        {
          bool1 = true;
          boolean bool2 = bool1;
          if (MPConfig.DEBUG)
          {
            StringBuilder localStringBuilder = new StringBuilder().append("ConnectivityManager says we ");
            if (bool1)
            {
              paramContext = "are";
              Log.v("MixpanelAPI.Message", paramContext + " online");
              return bool1;
            }
            paramContext = "are not";
            continue;
          }
          return bool2;
        }
      }
      catch (SecurityException paramContext)
      {
        bool1 = true;
        bool2 = bool1;
        if (MPConfig.DEBUG)
        {
          Log.v("MixpanelAPI.Message", "Don't have permission to check connectivity, will assume we are online");
          bool2 = bool1;
        }
      }
      boolean bool1 = false;
    }
  }
  
  /* Error */
  public byte[] performRequest(String paramString, java.util.List<org.apache.http.NameValuePair> paramList, javax.net.ssl.SSLSocketFactory paramSSLSocketFactory)
    throws RemoteService.ServiceUnavailableException, IOException
  {
    // Byte code:
    //   0: getstatic 70	com/mixpanel/android/mpmetrics/MPConfig:DEBUG	Z
    //   3: ifeq +28 -> 31
    //   6: ldc 10
    //   8: new 72	java/lang/StringBuilder
    //   11: dup
    //   12: invokespecial 73	java/lang/StringBuilder:<init>	()V
    //   15: ldc 105
    //   17: invokevirtual 79	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: aload_1
    //   21: invokevirtual 79	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   27: invokestatic 93	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   30: pop
    //   31: aconst_null
    //   32: astore 13
    //   34: iconst_0
    //   35: istore 5
    //   37: iconst_0
    //   38: istore 4
    //   40: iload 5
    //   42: iconst_3
    //   43: if_icmpge +1182 -> 1225
    //   46: iload 4
    //   48: ifne +1177 -> 1225
    //   51: aconst_null
    //   52: astore 27
    //   54: aconst_null
    //   55: astore 22
    //   57: aconst_null
    //   58: astore 23
    //   60: aconst_null
    //   61: astore 24
    //   63: aconst_null
    //   64: astore 26
    //   66: aconst_null
    //   67: astore 25
    //   69: aconst_null
    //   70: astore 29
    //   72: aconst_null
    //   73: astore 28
    //   75: aconst_null
    //   76: astore 10
    //   78: aconst_null
    //   79: astore 21
    //   81: aconst_null
    //   82: astore 17
    //   84: aconst_null
    //   85: astore 11
    //   87: aconst_null
    //   88: astore 16
    //   90: aload 25
    //   92: astore 18
    //   94: aload 10
    //   96: astore 14
    //   98: aload 13
    //   100: astore 19
    //   102: aload 22
    //   104: astore 20
    //   106: aload 29
    //   108: astore 15
    //   110: aload 21
    //   112: astore 7
    //   114: aload 24
    //   116: astore 12
    //   118: aload 28
    //   120: astore 9
    //   122: new 107	java/net/URL
    //   125: dup
    //   126: aload_1
    //   127: invokespecial 110	java/net/URL:<init>	(Ljava/lang/String;)V
    //   130: invokevirtual 114	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   133: checkcast 116	java/net/HttpURLConnection
    //   136: astore 8
    //   138: aload_3
    //   139: ifnull +108 -> 247
    //   142: aload 8
    //   144: astore 16
    //   146: aload 25
    //   148: astore 18
    //   150: aload 10
    //   152: astore 14
    //   154: aload 13
    //   156: astore 19
    //   158: aload 8
    //   160: astore 17
    //   162: aload 22
    //   164: astore 20
    //   166: aload 29
    //   168: astore 15
    //   170: aload 21
    //   172: astore 7
    //   174: aload 8
    //   176: astore 11
    //   178: aload 24
    //   180: astore 12
    //   182: aload 28
    //   184: astore 9
    //   186: aload 8
    //   188: instanceof 118
    //   191: ifeq +56 -> 247
    //   194: aload 8
    //   196: astore 16
    //   198: aload 25
    //   200: astore 18
    //   202: aload 10
    //   204: astore 14
    //   206: aload 13
    //   208: astore 19
    //   210: aload 8
    //   212: astore 17
    //   214: aload 22
    //   216: astore 20
    //   218: aload 29
    //   220: astore 15
    //   222: aload 21
    //   224: astore 7
    //   226: aload 8
    //   228: astore 11
    //   230: aload 24
    //   232: astore 12
    //   234: aload 28
    //   236: astore 9
    //   238: aload 8
    //   240: checkcast 118	javax/net/ssl/HttpsURLConnection
    //   243: aload_3
    //   244: invokevirtual 122	javax/net/ssl/HttpsURLConnection:setSSLSocketFactory	(Ljavax/net/ssl/SSLSocketFactory;)V
    //   247: aload 8
    //   249: astore 16
    //   251: aload 25
    //   253: astore 18
    //   255: aload 10
    //   257: astore 14
    //   259: aload 13
    //   261: astore 19
    //   263: aload 8
    //   265: astore 17
    //   267: aload 22
    //   269: astore 20
    //   271: aload 29
    //   273: astore 15
    //   275: aload 21
    //   277: astore 7
    //   279: aload 8
    //   281: astore 11
    //   283: aload 24
    //   285: astore 12
    //   287: aload 28
    //   289: astore 9
    //   291: aload 8
    //   293: sipush 2000
    //   296: invokevirtual 126	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   299: aload 8
    //   301: astore 16
    //   303: aload 25
    //   305: astore 18
    //   307: aload 10
    //   309: astore 14
    //   311: aload 13
    //   313: astore 19
    //   315: aload 8
    //   317: astore 17
    //   319: aload 22
    //   321: astore 20
    //   323: aload 29
    //   325: astore 15
    //   327: aload 21
    //   329: astore 7
    //   331: aload 8
    //   333: astore 11
    //   335: aload 24
    //   337: astore 12
    //   339: aload 28
    //   341: astore 9
    //   343: aload 8
    //   345: sipush 10000
    //   348: invokevirtual 129	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   351: aload_2
    //   352: ifnull +381 -> 733
    //   355: aload 8
    //   357: astore 16
    //   359: aload 25
    //   361: astore 18
    //   363: aload 10
    //   365: astore 14
    //   367: aload 13
    //   369: astore 19
    //   371: aload 8
    //   373: astore 17
    //   375: aload 22
    //   377: astore 20
    //   379: aload 29
    //   381: astore 15
    //   383: aload 21
    //   385: astore 7
    //   387: aload 8
    //   389: astore 11
    //   391: aload 24
    //   393: astore 12
    //   395: aload 28
    //   397: astore 9
    //   399: aload 8
    //   401: iconst_1
    //   402: invokevirtual 133	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   405: aload 8
    //   407: astore 16
    //   409: aload 25
    //   411: astore 18
    //   413: aload 10
    //   415: astore 14
    //   417: aload 13
    //   419: astore 19
    //   421: aload 8
    //   423: astore 17
    //   425: aload 22
    //   427: astore 20
    //   429: aload 29
    //   431: astore 15
    //   433: aload 21
    //   435: astore 7
    //   437: aload 8
    //   439: astore 11
    //   441: aload 24
    //   443: astore 12
    //   445: aload 28
    //   447: astore 9
    //   449: new 135	org/apache/http/client/entity/UrlEncodedFormEntity
    //   452: dup
    //   453: aload_2
    //   454: ldc -119
    //   456: invokespecial 140	org/apache/http/client/entity/UrlEncodedFormEntity:<init>	(Ljava/util/List;Ljava/lang/String;)V
    //   459: astore 30
    //   461: aload 8
    //   463: astore 16
    //   465: aload 25
    //   467: astore 18
    //   469: aload 10
    //   471: astore 14
    //   473: aload 13
    //   475: astore 19
    //   477: aload 8
    //   479: astore 17
    //   481: aload 22
    //   483: astore 20
    //   485: aload 29
    //   487: astore 15
    //   489: aload 21
    //   491: astore 7
    //   493: aload 8
    //   495: astore 11
    //   497: aload 24
    //   499: astore 12
    //   501: aload 28
    //   503: astore 9
    //   505: aload 8
    //   507: ldc -114
    //   509: invokevirtual 145	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   512: aload 8
    //   514: astore 16
    //   516: aload 25
    //   518: astore 18
    //   520: aload 10
    //   522: astore 14
    //   524: aload 13
    //   526: astore 19
    //   528: aload 8
    //   530: astore 17
    //   532: aload 22
    //   534: astore 20
    //   536: aload 29
    //   538: astore 15
    //   540: aload 21
    //   542: astore 7
    //   544: aload 8
    //   546: astore 11
    //   548: aload 24
    //   550: astore 12
    //   552: aload 28
    //   554: astore 9
    //   556: aload 8
    //   558: aload 30
    //   560: invokevirtual 149	org/apache/http/client/entity/UrlEncodedFormEntity:getContentLength	()J
    //   563: l2i
    //   564: invokevirtual 152	java/net/HttpURLConnection:setFixedLengthStreamingMode	(I)V
    //   567: aload 8
    //   569: astore 16
    //   571: aload 25
    //   573: astore 18
    //   575: aload 10
    //   577: astore 14
    //   579: aload 13
    //   581: astore 19
    //   583: aload 8
    //   585: astore 17
    //   587: aload 22
    //   589: astore 20
    //   591: aload 29
    //   593: astore 15
    //   595: aload 21
    //   597: astore 7
    //   599: aload 8
    //   601: astore 11
    //   603: aload 24
    //   605: astore 12
    //   607: aload 28
    //   609: astore 9
    //   611: aload 8
    //   613: invokevirtual 156	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   616: astore 10
    //   618: aload 8
    //   620: astore 16
    //   622: aload 25
    //   624: astore 18
    //   626: aload 10
    //   628: astore 14
    //   630: aload 13
    //   632: astore 19
    //   634: aload 8
    //   636: astore 17
    //   638: aload 22
    //   640: astore 20
    //   642: aload 10
    //   644: astore 15
    //   646: aload 21
    //   648: astore 7
    //   650: aload 8
    //   652: astore 11
    //   654: aload 24
    //   656: astore 12
    //   658: aload 10
    //   660: astore 9
    //   662: new 158	java/io/BufferedOutputStream
    //   665: dup
    //   666: aload 10
    //   668: invokespecial 161	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   671: astore 21
    //   673: aload 30
    //   675: aload 21
    //   677: invokevirtual 164	org/apache/http/client/entity/UrlEncodedFormEntity:writeTo	(Ljava/io/OutputStream;)V
    //   680: aload 21
    //   682: invokevirtual 167	java/io/BufferedOutputStream:close	()V
    //   685: aconst_null
    //   686: astore 7
    //   688: aload 8
    //   690: astore 16
    //   692: aload 25
    //   694: astore 18
    //   696: aload 10
    //   698: astore 14
    //   700: aload 13
    //   702: astore 19
    //   704: aload 8
    //   706: astore 17
    //   708: aload 22
    //   710: astore 20
    //   712: aload 10
    //   714: astore 15
    //   716: aload 8
    //   718: astore 11
    //   720: aload 24
    //   722: astore 12
    //   724: aload 10
    //   726: astore 9
    //   728: aload 10
    //   730: invokevirtual 170	java/io/OutputStream:close	()V
    //   733: aconst_null
    //   734: astore 26
    //   736: aconst_null
    //   737: astore 27
    //   739: aconst_null
    //   740: astore 23
    //   742: aconst_null
    //   743: astore 28
    //   745: aload 8
    //   747: astore 16
    //   749: aload 25
    //   751: astore 18
    //   753: aload 23
    //   755: astore 14
    //   757: aload 13
    //   759: astore 19
    //   761: aload 8
    //   763: astore 17
    //   765: aload 22
    //   767: astore 20
    //   769: aload 26
    //   771: astore 15
    //   773: aload 28
    //   775: astore 7
    //   777: aload 8
    //   779: astore 11
    //   781: aload 24
    //   783: astore 12
    //   785: aload 27
    //   787: astore 9
    //   789: aload 8
    //   791: invokevirtual 174	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   794: astore 10
    //   796: aload 8
    //   798: astore 16
    //   800: aload 10
    //   802: astore 18
    //   804: aload 23
    //   806: astore 14
    //   808: aload 13
    //   810: astore 19
    //   812: aload 8
    //   814: astore 17
    //   816: aload 10
    //   818: astore 20
    //   820: aload 26
    //   822: astore 15
    //   824: aload 28
    //   826: astore 7
    //   828: aload 8
    //   830: astore 11
    //   832: aload 10
    //   834: astore 12
    //   836: aload 27
    //   838: astore 9
    //   840: aload 10
    //   842: invokestatic 176	com/mixpanel/android/util/HttpService:slurp	(Ljava/io/InputStream;)[B
    //   845: astore 21
    //   847: aload 8
    //   849: astore 16
    //   851: aload 10
    //   853: astore 18
    //   855: aload 23
    //   857: astore 14
    //   859: aload 21
    //   861: astore 19
    //   863: aload 8
    //   865: astore 17
    //   867: aload 10
    //   869: astore 20
    //   871: aload 26
    //   873: astore 15
    //   875: aload 28
    //   877: astore 7
    //   879: aload 8
    //   881: astore 11
    //   883: aload 10
    //   885: astore 12
    //   887: aload 27
    //   889: astore 9
    //   891: aload 10
    //   893: invokevirtual 177	java/io/InputStream:close	()V
    //   896: iconst_1
    //   897: istore 6
    //   899: iconst_0
    //   900: ifeq +11 -> 911
    //   903: new 179	java/lang/NullPointerException
    //   906: dup
    //   907: invokespecial 180	java/lang/NullPointerException:<init>	()V
    //   910: athrow
    //   911: iconst_0
    //   912: ifeq +11 -> 923
    //   915: new 179	java/lang/NullPointerException
    //   918: dup
    //   919: invokespecial 180	java/lang/NullPointerException:<init>	()V
    //   922: athrow
    //   923: iconst_0
    //   924: ifeq +11 -> 935
    //   927: new 179	java/lang/NullPointerException
    //   930: dup
    //   931: invokespecial 180	java/lang/NullPointerException:<init>	()V
    //   934: athrow
    //   935: aload 21
    //   937: astore 13
    //   939: iload 6
    //   941: istore 4
    //   943: aload 8
    //   945: ifnull -905 -> 40
    //   948: aload 8
    //   950: invokevirtual 183	java/net/HttpURLConnection:disconnect	()V
    //   953: aload 21
    //   955: astore 13
    //   957: iload 6
    //   959: istore 4
    //   961: goto -921 -> 40
    //   964: aconst_null
    //   965: astore 7
    //   967: astore 8
    //   969: aload 14
    //   971: astore 10
    //   973: aload 16
    //   975: astore 8
    //   977: aload 7
    //   979: astore 14
    //   981: aload 14
    //   983: astore 7
    //   985: aload 8
    //   987: astore 11
    //   989: aload 18
    //   991: astore 12
    //   993: aload 10
    //   995: astore 9
    //   997: getstatic 70	com/mixpanel/android/mpmetrics/MPConfig:DEBUG	Z
    //   1000: ifeq +27 -> 1027
    //   1003: aload 14
    //   1005: astore 7
    //   1007: aload 8
    //   1009: astore 11
    //   1011: aload 18
    //   1013: astore 12
    //   1015: aload 10
    //   1017: astore 9
    //   1019: ldc 10
    //   1021: ldc -71
    //   1023: invokestatic 188	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1026: pop
    //   1027: iload 5
    //   1029: iconst_1
    //   1030: iadd
    //   1031: istore 6
    //   1033: aload 14
    //   1035: ifnull +8 -> 1043
    //   1038: aload 14
    //   1040: invokevirtual 167	java/io/BufferedOutputStream:close	()V
    //   1043: aload 10
    //   1045: ifnull +8 -> 1053
    //   1048: aload 10
    //   1050: invokevirtual 170	java/io/OutputStream:close	()V
    //   1053: aload 18
    //   1055: ifnull +8 -> 1063
    //   1058: aload 18
    //   1060: invokevirtual 177	java/io/InputStream:close	()V
    //   1063: aload 19
    //   1065: astore 13
    //   1067: iload 6
    //   1069: istore 5
    //   1071: aload 8
    //   1073: ifnull -1033 -> 40
    //   1076: aload 8
    //   1078: invokevirtual 183	java/net/HttpURLConnection:disconnect	()V
    //   1081: aload 19
    //   1083: astore 13
    //   1085: iload 6
    //   1087: istore 5
    //   1089: goto -1049 -> 40
    //   1092: aconst_null
    //   1093: astore_1
    //   1094: astore_2
    //   1095: aload 15
    //   1097: astore 10
    //   1099: aload 17
    //   1101: astore 8
    //   1103: aload_1
    //   1104: astore 7
    //   1106: aload 8
    //   1108: astore 11
    //   1110: aload 20
    //   1112: astore 12
    //   1114: aload 10
    //   1116: astore 9
    //   1118: sipush 503
    //   1121: aload 8
    //   1123: invokevirtual 192	java/net/HttpURLConnection:getResponseCode	()I
    //   1126: if_icmpne +82 -> 1208
    //   1129: aload_1
    //   1130: astore 7
    //   1132: aload 8
    //   1134: astore 11
    //   1136: aload 20
    //   1138: astore 12
    //   1140: aload 10
    //   1142: astore 9
    //   1144: new 101	com/mixpanel/android/util/RemoteService$ServiceUnavailableException
    //   1147: dup
    //   1148: ldc -62
    //   1150: aload 8
    //   1152: ldc -60
    //   1154: invokevirtual 200	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
    //   1157: invokespecial 203	com/mixpanel/android/util/RemoteService$ServiceUnavailableException:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1160: athrow
    //   1161: astore_1
    //   1162: aload 11
    //   1164: astore 8
    //   1166: aload 7
    //   1168: ifnull +8 -> 1176
    //   1171: aload 7
    //   1173: invokevirtual 167	java/io/BufferedOutputStream:close	()V
    //   1176: aload 9
    //   1178: ifnull +8 -> 1186
    //   1181: aload 9
    //   1183: invokevirtual 170	java/io/OutputStream:close	()V
    //   1186: aload 12
    //   1188: ifnull +8 -> 1196
    //   1191: aload 12
    //   1193: invokevirtual 177	java/io/InputStream:close	()V
    //   1196: aload 8
    //   1198: ifnull +8 -> 1206
    //   1201: aload 8
    //   1203: invokevirtual 183	java/net/HttpURLConnection:disconnect	()V
    //   1206: aload_1
    //   1207: athrow
    //   1208: aload_1
    //   1209: astore 7
    //   1211: aload 8
    //   1213: astore 11
    //   1215: aload 20
    //   1217: astore 12
    //   1219: aload 10
    //   1221: astore 9
    //   1223: aload_2
    //   1224: athrow
    //   1225: getstatic 70	com/mixpanel/android/mpmetrics/MPConfig:DEBUG	Z
    //   1228: ifeq +17 -> 1245
    //   1231: iload 5
    //   1233: iconst_3
    //   1234: if_icmplt +11 -> 1245
    //   1237: ldc 10
    //   1239: ldc -51
    //   1241: invokestatic 93	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   1244: pop
    //   1245: aload 13
    //   1247: areturn
    //   1248: astore 7
    //   1250: goto -339 -> 911
    //   1253: astore 7
    //   1255: goto -332 -> 923
    //   1258: astore 7
    //   1260: goto -325 -> 935
    //   1263: astore 7
    //   1265: goto -222 -> 1043
    //   1268: astore 7
    //   1270: goto -217 -> 1053
    //   1273: astore 7
    //   1275: goto -212 -> 1063
    //   1278: astore_2
    //   1279: goto -103 -> 1176
    //   1282: astore_2
    //   1283: goto -97 -> 1186
    //   1286: astore_2
    //   1287: goto -91 -> 1196
    //   1290: astore_1
    //   1291: aload 21
    //   1293: astore 7
    //   1295: aload 26
    //   1297: astore 12
    //   1299: aload 10
    //   1301: astore 9
    //   1303: goto -137 -> 1166
    //   1306: astore_2
    //   1307: aload 21
    //   1309: astore_1
    //   1310: aload 23
    //   1312: astore 20
    //   1314: goto -211 -> 1103
    //   1317: astore 7
    //   1319: aload 21
    //   1321: astore 14
    //   1323: aload 27
    //   1325: astore 18
    //   1327: aload 13
    //   1329: astore 19
    //   1331: goto -350 -> 981
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1334	0	this	HttpService
    //   0	1334	1	paramString	String
    //   0	1334	2	paramList	java.util.List<org.apache.http.NameValuePair>
    //   0	1334	3	paramSSLSocketFactory	javax.net.ssl.SSLSocketFactory
    //   38	922	4	i	int
    //   35	1200	5	j	int
    //   897	189	6	k	int
    //   112	1098	7	localObject1	Object
    //   1248	1	7	localIOException1	IOException
    //   1253	1	7	localIOException2	IOException
    //   1258	1	7	localIOException3	IOException
    //   1263	1	7	localIOException4	IOException
    //   1268	1	7	localIOException5	IOException
    //   1273	1	7	localIOException6	IOException
    //   1293	1	7	localObject2	Object
    //   1317	1	7	localEOFException1	java.io.EOFException
    //   136	813	8	localHttpURLConnection	java.net.HttpURLConnection
    //   967	1	8	localEOFException2	java.io.EOFException
    //   975	237	8	localObject3	Object
    //   120	1182	9	localObject4	Object
    //   76	1224	10	localObject5	Object
    //   85	1129	11	localObject6	Object
    //   116	1182	12	localObject7	Object
    //   32	1296	13	localObject8	Object
    //   96	1226	14	localObject9	Object
    //   108	988	15	localObject10	Object
    //   88	886	16	localObject11	Object
    //   82	1018	17	localObject12	Object
    //   92	1234	18	localObject13	Object
    //   100	1230	19	localObject14	Object
    //   104	1209	20	localObject15	Object
    //   79	1241	21	localObject16	Object
    //   55	711	22	localObject17	Object
    //   58	1253	23	localObject18	Object
    //   61	721	24	localObject19	Object
    //   67	683	25	localObject20	Object
    //   64	1232	26	localObject21	Object
    //   52	1272	27	localObject22	Object
    //   73	803	28	localObject23	Object
    //   70	522	29	localObject24	Object
    //   459	215	30	localUrlEncodedFormEntity	org.apache.http.client.entity.UrlEncodedFormEntity
    // Exception table:
    //   from	to	target	type
    //   122	138	964	java/io/EOFException
    //   186	194	964	java/io/EOFException
    //   238	247	964	java/io/EOFException
    //   291	299	964	java/io/EOFException
    //   343	351	964	java/io/EOFException
    //   399	405	964	java/io/EOFException
    //   449	461	964	java/io/EOFException
    //   505	512	964	java/io/EOFException
    //   556	567	964	java/io/EOFException
    //   611	618	964	java/io/EOFException
    //   662	673	964	java/io/EOFException
    //   728	733	964	java/io/EOFException
    //   789	796	964	java/io/EOFException
    //   840	847	964	java/io/EOFException
    //   891	896	964	java/io/EOFException
    //   122	138	1092	java/io/IOException
    //   186	194	1092	java/io/IOException
    //   238	247	1092	java/io/IOException
    //   291	299	1092	java/io/IOException
    //   343	351	1092	java/io/IOException
    //   399	405	1092	java/io/IOException
    //   449	461	1092	java/io/IOException
    //   505	512	1092	java/io/IOException
    //   556	567	1092	java/io/IOException
    //   611	618	1092	java/io/IOException
    //   662	673	1092	java/io/IOException
    //   728	733	1092	java/io/IOException
    //   789	796	1092	java/io/IOException
    //   840	847	1092	java/io/IOException
    //   891	896	1092	java/io/IOException
    //   122	138	1161	finally
    //   186	194	1161	finally
    //   238	247	1161	finally
    //   291	299	1161	finally
    //   343	351	1161	finally
    //   399	405	1161	finally
    //   449	461	1161	finally
    //   505	512	1161	finally
    //   556	567	1161	finally
    //   611	618	1161	finally
    //   662	673	1161	finally
    //   728	733	1161	finally
    //   789	796	1161	finally
    //   840	847	1161	finally
    //   891	896	1161	finally
    //   997	1003	1161	finally
    //   1019	1027	1161	finally
    //   1118	1129	1161	finally
    //   1144	1161	1161	finally
    //   1223	1225	1161	finally
    //   903	911	1248	java/io/IOException
    //   915	923	1253	java/io/IOException
    //   927	935	1258	java/io/IOException
    //   1038	1043	1263	java/io/IOException
    //   1048	1053	1268	java/io/IOException
    //   1058	1063	1273	java/io/IOException
    //   1171	1176	1278	java/io/IOException
    //   1181	1186	1282	java/io/IOException
    //   1191	1196	1286	java/io/IOException
    //   673	685	1290	finally
    //   673	685	1306	java/io/IOException
    //   673	685	1317	java/io/EOFException
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/util/HttpService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
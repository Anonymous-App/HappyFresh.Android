package com.mixpanel.android.java_websocket.util;

import android.annotation.SuppressLint;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

@SuppressLint({"Assert"})
public class Base64
{
  public static final int DECODE = 0;
  public static final int DONT_GUNZIP = 4;
  public static final int DO_BREAK_LINES = 8;
  public static final int ENCODE = 1;
  private static final byte EQUALS_SIGN = 61;
  private static final byte EQUALS_SIGN_ENC = -1;
  public static final int GZIP = 2;
  private static final int MAX_LINE_LENGTH = 76;
  private static final byte NEW_LINE = 10;
  public static final int NO_OPTIONS = 0;
  public static final int ORDERED = 32;
  private static final String PREFERRED_ENCODING = "US-ASCII";
  public static final int URL_SAFE = 16;
  private static final byte WHITE_SPACE_ENC = -5;
  private static final byte[] _ORDERED_ALPHABET;
  private static final byte[] _ORDERED_DECODABET;
  private static final byte[] _STANDARD_ALPHABET;
  private static final byte[] _STANDARD_DECODABET;
  private static final byte[] _URL_SAFE_ALPHABET;
  private static final byte[] _URL_SAFE_DECODABET;
  
  static
  {
    if (!Base64.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      _STANDARD_ALPHABET = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
      _STANDARD_DECODABET = new byte[] { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 };
      _URL_SAFE_ALPHABET = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
      _URL_SAFE_DECODABET = new byte[] { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 };
      _ORDERED_ALPHABET = new byte[] { 45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };
      _ORDERED_DECODABET = new byte[] { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 };
      return;
    }
  }
  
  public static byte[] decode(String paramString)
    throws IOException
  {
    return decode(paramString, 0);
  }
  
  /* Error */
  public static byte[] decode(String paramString, int paramInt)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +13 -> 14
    //   4: new 222	java/lang/NullPointerException
    //   7: dup
    //   8: ldc -32
    //   10: invokespecial 227	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   13: athrow
    //   14: aload_0
    //   15: ldc 44
    //   17: invokevirtual 232	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   20: astore_2
    //   21: aload_2
    //   22: astore_0
    //   23: aload_0
    //   24: iconst_0
    //   25: aload_0
    //   26: arraylength
    //   27: iload_1
    //   28: invokestatic 235	com/mixpanel/android/java_websocket/util/Base64:decode	([BIII)[B
    //   31: astore 11
    //   33: iload_1
    //   34: iconst_4
    //   35: iand
    //   36: ifeq +171 -> 207
    //   39: iconst_1
    //   40: istore_1
    //   41: aload 11
    //   43: ifnull +152 -> 195
    //   46: aload 11
    //   48: arraylength
    //   49: iconst_4
    //   50: if_icmplt +145 -> 195
    //   53: iload_1
    //   54: ifne +141 -> 195
    //   57: ldc -20
    //   59: aload 11
    //   61: iconst_0
    //   62: baload
    //   63: sipush 255
    //   66: iand
    //   67: aload 11
    //   69: iconst_1
    //   70: baload
    //   71: bipush 8
    //   73: ishl
    //   74: ldc -19
    //   76: iand
    //   77: ior
    //   78: if_icmpne +117 -> 195
    //   81: aconst_null
    //   82: astore 4
    //   84: aconst_null
    //   85: astore 9
    //   87: aconst_null
    //   88: astore_3
    //   89: aconst_null
    //   90: astore 6
    //   92: aconst_null
    //   93: astore 8
    //   95: aconst_null
    //   96: astore 7
    //   98: aconst_null
    //   99: astore 5
    //   101: aconst_null
    //   102: astore 10
    //   104: sipush 2048
    //   107: newarray <illegal type>
    //   109: astore 12
    //   111: new 239	java/io/ByteArrayOutputStream
    //   114: dup
    //   115: invokespecial 240	java/io/ByteArrayOutputStream:<init>	()V
    //   118: astore_0
    //   119: new 242	java/io/ByteArrayInputStream
    //   122: dup
    //   123: aload 11
    //   125: invokespecial 245	java/io/ByteArrayInputStream:<init>	([B)V
    //   128: astore_2
    //   129: new 247	java/util/zip/GZIPInputStream
    //   132: dup
    //   133: aload_2
    //   134: invokespecial 250	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   137: astore_3
    //   138: aload_3
    //   139: aload 12
    //   141: invokevirtual 254	java/util/zip/GZIPInputStream:read	([B)I
    //   144: istore_1
    //   145: iload_1
    //   146: iflt +66 -> 212
    //   149: aload_0
    //   150: aload 12
    //   152: iconst_0
    //   153: iload_1
    //   154: invokevirtual 258	java/io/ByteArrayOutputStream:write	([BII)V
    //   157: goto -19 -> 138
    //   160: astore 4
    //   162: aload_3
    //   163: astore 7
    //   165: aload 4
    //   167: astore_3
    //   168: aload_2
    //   169: astore 4
    //   171: aload_0
    //   172: astore 5
    //   174: aload 7
    //   176: astore 6
    //   178: aload_3
    //   179: invokevirtual 261	java/io/IOException:printStackTrace	()V
    //   182: aload_0
    //   183: invokevirtual 264	java/io/ByteArrayOutputStream:close	()V
    //   186: aload 7
    //   188: invokevirtual 265	java/util/zip/GZIPInputStream:close	()V
    //   191: aload_2
    //   192: invokevirtual 266	java/io/ByteArrayInputStream:close	()V
    //   195: aload 11
    //   197: areturn
    //   198: astore_2
    //   199: aload_0
    //   200: invokevirtual 269	java/lang/String:getBytes	()[B
    //   203: astore_0
    //   204: goto -181 -> 23
    //   207: iconst_0
    //   208: istore_1
    //   209: goto -168 -> 41
    //   212: aload_0
    //   213: invokevirtual 272	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   216: astore 4
    //   218: aload_0
    //   219: invokevirtual 264	java/io/ByteArrayOutputStream:close	()V
    //   222: aload_3
    //   223: invokevirtual 265	java/util/zip/GZIPInputStream:close	()V
    //   226: aload_2
    //   227: invokevirtual 266	java/io/ByteArrayInputStream:close	()V
    //   230: aload 4
    //   232: areturn
    //   233: astore_0
    //   234: aload 4
    //   236: areturn
    //   237: astore_3
    //   238: aload 5
    //   240: astore_2
    //   241: aload 4
    //   243: astore_0
    //   244: aload_2
    //   245: invokevirtual 264	java/io/ByteArrayOutputStream:close	()V
    //   248: aload 6
    //   250: invokevirtual 265	java/util/zip/GZIPInputStream:close	()V
    //   253: aload_0
    //   254: invokevirtual 266	java/io/ByteArrayInputStream:close	()V
    //   257: aload_3
    //   258: athrow
    //   259: astore_0
    //   260: goto -38 -> 222
    //   263: astore_0
    //   264: goto -38 -> 226
    //   267: astore_0
    //   268: goto -82 -> 186
    //   271: astore_0
    //   272: goto -81 -> 191
    //   275: astore_0
    //   276: aload 11
    //   278: areturn
    //   279: astore_2
    //   280: goto -32 -> 248
    //   283: astore_2
    //   284: goto -31 -> 253
    //   287: astore_0
    //   288: goto -31 -> 257
    //   291: astore_3
    //   292: aload_0
    //   293: astore_2
    //   294: aload 9
    //   296: astore_0
    //   297: aload 8
    //   299: astore 6
    //   301: goto -57 -> 244
    //   304: astore 4
    //   306: aload_0
    //   307: astore_3
    //   308: aload_2
    //   309: astore_0
    //   310: aload_3
    //   311: astore_2
    //   312: aload 8
    //   314: astore 6
    //   316: aload 4
    //   318: astore_3
    //   319: goto -75 -> 244
    //   322: astore 5
    //   324: aload_0
    //   325: astore 4
    //   327: aload_2
    //   328: astore_0
    //   329: aload 4
    //   331: astore_2
    //   332: aload_3
    //   333: astore 6
    //   335: aload 5
    //   337: astore_3
    //   338: goto -94 -> 244
    //   341: astore 4
    //   343: aload_3
    //   344: astore_2
    //   345: aload 10
    //   347: astore_0
    //   348: aload 4
    //   350: astore_3
    //   351: goto -183 -> 168
    //   354: astore 4
    //   356: aload_3
    //   357: astore_2
    //   358: aload 4
    //   360: astore_3
    //   361: goto -193 -> 168
    //   364: astore_3
    //   365: goto -197 -> 168
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	368	0	paramString	String
    //   0	368	1	paramInt	int
    //   20	172	2	localObject1	Object
    //   198	29	2	localUnsupportedEncodingException	UnsupportedEncodingException
    //   240	5	2	localObject2	Object
    //   279	1	2	localException1	Exception
    //   283	1	2	localException2	Exception
    //   293	65	2	localObject3	Object
    //   88	135	3	localObject4	Object
    //   237	21	3	localObject5	Object
    //   291	1	3	localObject6	Object
    //   307	54	3	localObject7	Object
    //   364	1	3	localIOException1	IOException
    //   82	1	4	localObject8	Object
    //   160	6	4	localIOException2	IOException
    //   169	73	4	localObject9	Object
    //   304	13	4	localObject10	Object
    //   325	5	4	str1	String
    //   341	8	4	localIOException3	IOException
    //   354	5	4	localIOException4	IOException
    //   99	140	5	str2	String
    //   322	14	5	localObject11	Object
    //   90	244	6	localObject12	Object
    //   96	91	7	localObject13	Object
    //   93	220	8	localObject14	Object
    //   85	210	9	localObject15	Object
    //   102	244	10	localObject16	Object
    //   31	246	11	arrayOfByte1	byte[]
    //   109	42	12	arrayOfByte2	byte[]
    // Exception table:
    //   from	to	target	type
    //   138	145	160	java/io/IOException
    //   149	157	160	java/io/IOException
    //   212	218	160	java/io/IOException
    //   14	21	198	java/io/UnsupportedEncodingException
    //   226	230	233	java/lang/Exception
    //   111	119	237	finally
    //   178	182	237	finally
    //   218	222	259	java/lang/Exception
    //   222	226	263	java/lang/Exception
    //   182	186	267	java/lang/Exception
    //   186	191	271	java/lang/Exception
    //   191	195	275	java/lang/Exception
    //   244	248	279	java/lang/Exception
    //   248	253	283	java/lang/Exception
    //   253	257	287	java/lang/Exception
    //   119	129	291	finally
    //   129	138	304	finally
    //   138	145	322	finally
    //   149	157	322	finally
    //   212	218	322	finally
    //   111	119	341	java/io/IOException
    //   119	129	354	java/io/IOException
    //   129	138	364	java/io/IOException
  }
  
  public static byte[] decode(byte[] paramArrayOfByte)
    throws IOException
  {
    return decode(paramArrayOfByte, 0, paramArrayOfByte.length, 0);
  }
  
  public static byte[] decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
    throws IOException
  {
    if (paramArrayOfByte == null) {
      throw new NullPointerException("Cannot decode null source array.");
    }
    if ((paramInt1 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length)) {
      throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", new Object[] { Integer.valueOf(paramArrayOfByte.length), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) }));
    }
    if (paramInt2 == 0) {
      return new byte[0];
    }
    if (paramInt2 < 4) {
      throw new IllegalArgumentException("Base64-encoded string must have at least four characters, but length specified was " + paramInt2);
    }
    byte[] arrayOfByte2 = getDecodabet(paramInt3);
    byte[] arrayOfByte1 = new byte[paramInt2 * 3 / 4];
    int j = 0;
    byte[] arrayOfByte3 = new byte[4];
    int k = paramInt1;
    int i = 0;
    int m;
    if (k < paramInt1 + paramInt2)
    {
      m = arrayOfByte2[(paramArrayOfByte[k] & 0xFF)];
      if (m >= -5)
      {
        if (m < -1) {
          break label289;
        }
        int n = i + 1;
        arrayOfByte3[i] = paramArrayOfByte[k];
        i = n;
        m = j;
        if (n <= 3) {
          break label293;
        }
        j += decode4to3(arrayOfByte3, 0, arrayOfByte1, j, paramInt3);
        i = 0;
        m = j;
        if (paramArrayOfByte[k] != 61) {
          break label293;
        }
      }
    }
    for (;;)
    {
      paramArrayOfByte = new byte[j];
      System.arraycopy(arrayOfByte1, 0, paramArrayOfByte, 0, j);
      return paramArrayOfByte;
      throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", new Object[] { Integer.valueOf(paramArrayOfByte[k] & 0xFF), Integer.valueOf(k) }));
      label289:
      m = j;
      label293:
      k += 1;
      j = m;
      break;
    }
  }
  
  private static int decode4to3(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3)
  {
    if (paramArrayOfByte1 == null) {
      throw new NullPointerException("Source array was null.");
    }
    if (paramArrayOfByte2 == null) {
      throw new NullPointerException("Destination array was null.");
    }
    if ((paramInt1 < 0) || (paramInt1 + 3 >= paramArrayOfByte1.length)) {
      throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", new Object[] { Integer.valueOf(paramArrayOfByte1.length), Integer.valueOf(paramInt1) }));
    }
    if ((paramInt2 < 0) || (paramInt2 + 2 >= paramArrayOfByte2.length)) {
      throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", new Object[] { Integer.valueOf(paramArrayOfByte2.length), Integer.valueOf(paramInt2) }));
    }
    byte[] arrayOfByte = getDecodabet(paramInt3);
    if (paramArrayOfByte1[(paramInt1 + 2)] == 61)
    {
      paramArrayOfByte2[paramInt2] = ((byte)(((arrayOfByte[paramArrayOfByte1[paramInt1]] & 0xFF) << 18 | (arrayOfByte[paramArrayOfByte1[(paramInt1 + 1)]] & 0xFF) << 12) >>> 16));
      return 1;
    }
    if (paramArrayOfByte1[(paramInt1 + 3)] == 61)
    {
      paramInt1 = (arrayOfByte[paramArrayOfByte1[paramInt1]] & 0xFF) << 18 | (arrayOfByte[paramArrayOfByte1[(paramInt1 + 1)]] & 0xFF) << 12 | (arrayOfByte[paramArrayOfByte1[(paramInt1 + 2)]] & 0xFF) << 6;
      paramArrayOfByte2[paramInt2] = ((byte)(paramInt1 >>> 16));
      paramArrayOfByte2[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
      return 2;
    }
    paramInt1 = (arrayOfByte[paramArrayOfByte1[paramInt1]] & 0xFF) << 18 | (arrayOfByte[paramArrayOfByte1[(paramInt1 + 1)]] & 0xFF) << 12 | (arrayOfByte[paramArrayOfByte1[(paramInt1 + 2)]] & 0xFF) << 6 | arrayOfByte[paramArrayOfByte1[(paramInt1 + 3)]] & 0xFF;
    paramArrayOfByte2[paramInt2] = ((byte)(paramInt1 >> 16));
    paramArrayOfByte2[(paramInt2 + 1)] = ((byte)(paramInt1 >> 8));
    paramArrayOfByte2[(paramInt2 + 2)] = ((byte)paramInt1);
    return 3;
  }
  
  /* Error */
  public static void decodeFileToFile(String paramString1, String paramString2)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 328	com/mixpanel/android/java_websocket/util/Base64:decodeFromFile	(Ljava/lang/String;)[B
    //   4: astore_3
    //   5: aconst_null
    //   6: astore_0
    //   7: aconst_null
    //   8: astore_2
    //   9: new 330	java/io/BufferedOutputStream
    //   12: dup
    //   13: new 332	java/io/FileOutputStream
    //   16: dup
    //   17: aload_1
    //   18: invokespecial 333	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   21: invokespecial 336	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   24: astore_1
    //   25: aload_1
    //   26: aload_3
    //   27: invokevirtual 340	java/io/OutputStream:write	([B)V
    //   30: aload_1
    //   31: invokevirtual 341	java/io/OutputStream:close	()V
    //   34: return
    //   35: astore_1
    //   36: aload_2
    //   37: astore_0
    //   38: aload_1
    //   39: athrow
    //   40: astore_1
    //   41: aload_0
    //   42: invokevirtual 341	java/io/OutputStream:close	()V
    //   45: aload_1
    //   46: athrow
    //   47: astore_0
    //   48: return
    //   49: astore_0
    //   50: goto -5 -> 45
    //   53: astore_2
    //   54: aload_1
    //   55: astore_0
    //   56: aload_2
    //   57: astore_1
    //   58: goto -17 -> 41
    //   61: astore_2
    //   62: aload_1
    //   63: astore_0
    //   64: aload_2
    //   65: astore_1
    //   66: goto -28 -> 38
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	69	0	paramString1	String
    //   0	69	1	paramString2	String
    //   8	29	2	localObject1	Object
    //   53	4	2	localObject2	Object
    //   61	4	2	localIOException	IOException
    //   4	23	3	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   9	25	35	java/io/IOException
    //   9	25	40	finally
    //   38	40	40	finally
    //   30	34	47	java/lang/Exception
    //   41	45	49	java/lang/Exception
    //   25	30	53	finally
    //   25	30	61	java/io/IOException
  }
  
  /* Error */
  public static byte[] decodeFromFile(String paramString)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 4
    //   6: aload 5
    //   8: astore_3
    //   9: new 343	java/io/File
    //   12: dup
    //   13: aload_0
    //   14: invokespecial 344	java/io/File:<init>	(Ljava/lang/String;)V
    //   17: astore_0
    //   18: iconst_0
    //   19: istore_1
    //   20: aload 5
    //   22: astore_3
    //   23: aload_0
    //   24: invokevirtual 348	java/io/File:length	()J
    //   27: ldc2_w 349
    //   30: lcmp
    //   31: ifle +56 -> 87
    //   34: aload 5
    //   36: astore_3
    //   37: new 212	java/io/IOException
    //   40: dup
    //   41: new 292	java/lang/StringBuilder
    //   44: dup
    //   45: invokespecial 293	java/lang/StringBuilder:<init>	()V
    //   48: ldc_w 352
    //   51: invokevirtual 299	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: aload_0
    //   55: invokevirtual 348	java/io/File:length	()J
    //   58: invokevirtual 355	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   61: ldc_w 357
    //   64: invokevirtual 299	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: invokevirtual 306	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   70: invokespecial 315	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   73: athrow
    //   74: astore_0
    //   75: aload 4
    //   77: astore_3
    //   78: aload_0
    //   79: athrow
    //   80: astore_0
    //   81: aload_3
    //   82: invokevirtual 358	com/mixpanel/android/java_websocket/util/Base64$InputStream:close	()V
    //   85: aload_0
    //   86: athrow
    //   87: aload 5
    //   89: astore_3
    //   90: aload_0
    //   91: invokevirtual 348	java/io/File:length	()J
    //   94: l2i
    //   95: newarray <illegal type>
    //   97: astore 6
    //   99: aload 5
    //   101: astore_3
    //   102: new 8	com/mixpanel/android/java_websocket/util/Base64$InputStream
    //   105: dup
    //   106: new 360	java/io/BufferedInputStream
    //   109: dup
    //   110: new 362	java/io/FileInputStream
    //   113: dup
    //   114: aload_0
    //   115: invokespecial 365	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   118: invokespecial 366	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   121: iconst_0
    //   122: invokespecial 369	com/mixpanel/android/java_websocket/util/Base64$InputStream:<init>	(Ljava/io/InputStream;I)V
    //   125: astore_0
    //   126: aload_0
    //   127: aload 6
    //   129: iload_1
    //   130: sipush 4096
    //   133: invokevirtual 372	com/mixpanel/android/java_websocket/util/Base64$InputStream:read	([BII)I
    //   136: istore_2
    //   137: iload_2
    //   138: iflt +10 -> 148
    //   141: iload_1
    //   142: iload_2
    //   143: iadd
    //   144: istore_1
    //   145: goto -19 -> 126
    //   148: iload_1
    //   149: newarray <illegal type>
    //   151: astore_3
    //   152: aload 6
    //   154: iconst_0
    //   155: aload_3
    //   156: iconst_0
    //   157: iload_1
    //   158: invokestatic 312	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   161: aload_0
    //   162: invokevirtual 358	com/mixpanel/android/java_websocket/util/Base64$InputStream:close	()V
    //   165: aload_3
    //   166: areturn
    //   167: astore_0
    //   168: aload_3
    //   169: areturn
    //   170: astore_3
    //   171: goto -86 -> 85
    //   174: astore 4
    //   176: aload_0
    //   177: astore_3
    //   178: aload 4
    //   180: astore_0
    //   181: goto -100 -> 81
    //   184: astore 4
    //   186: aload_0
    //   187: astore_3
    //   188: aload 4
    //   190: astore_0
    //   191: goto -113 -> 78
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	194	0	paramString	String
    //   19	139	1	i	int
    //   136	8	2	j	int
    //   8	161	3	localObject1	Object
    //   170	1	3	localException	Exception
    //   177	11	3	str	String
    //   4	72	4	localObject2	Object
    //   174	5	4	localObject3	Object
    //   184	5	4	localIOException	IOException
    //   1	99	5	localObject4	Object
    //   97	56	6	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   9	18	74	java/io/IOException
    //   23	34	74	java/io/IOException
    //   37	74	74	java/io/IOException
    //   90	99	74	java/io/IOException
    //   102	126	74	java/io/IOException
    //   9	18	80	finally
    //   23	34	80	finally
    //   37	74	80	finally
    //   78	80	80	finally
    //   90	99	80	finally
    //   102	126	80	finally
    //   161	165	167	java/lang/Exception
    //   81	85	170	java/lang/Exception
    //   126	137	174	finally
    //   148	161	174	finally
    //   126	137	184	java/io/IOException
    //   148	161	184	java/io/IOException
  }
  
  /* Error */
  public static void decodeToFile(String paramString1, String paramString2)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: new 11	com/mixpanel/android/java_websocket/util/Base64$OutputStream
    //   7: dup
    //   8: new 332	java/io/FileOutputStream
    //   11: dup
    //   12: aload_1
    //   13: invokespecial 333	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   16: iconst_0
    //   17: invokespecial 376	com/mixpanel/android/java_websocket/util/Base64$OutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   20: astore_1
    //   21: aload_1
    //   22: aload_0
    //   23: ldc 44
    //   25: invokevirtual 232	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   28: invokevirtual 377	com/mixpanel/android/java_websocket/util/Base64$OutputStream:write	([B)V
    //   31: aload_1
    //   32: invokevirtual 378	com/mixpanel/android/java_websocket/util/Base64$OutputStream:close	()V
    //   35: return
    //   36: astore_0
    //   37: aload_3
    //   38: astore_2
    //   39: aload_0
    //   40: athrow
    //   41: astore_0
    //   42: aload_2
    //   43: invokevirtual 378	com/mixpanel/android/java_websocket/util/Base64$OutputStream:close	()V
    //   46: aload_0
    //   47: athrow
    //   48: astore_0
    //   49: return
    //   50: astore_1
    //   51: goto -5 -> 46
    //   54: astore_0
    //   55: aload_1
    //   56: astore_2
    //   57: goto -15 -> 42
    //   60: astore_0
    //   61: aload_1
    //   62: astore_2
    //   63: goto -24 -> 39
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	66	0	paramString1	String
    //   0	66	1	paramString2	String
    //   1	62	2	localObject1	Object
    //   3	35	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   4	21	36	java/io/IOException
    //   4	21	41	finally
    //   39	41	41	finally
    //   31	35	48	java/lang/Exception
    //   42	46	50	java/lang/Exception
    //   21	31	54	finally
    //   21	31	60	java/io/IOException
  }
  
  public static Object decodeToObject(String paramString)
    throws IOException, ClassNotFoundException
  {
    return decodeToObject(paramString, 0, null);
  }
  
  /* Error */
  public static Object decodeToObject(String paramString, int paramInt, final ClassLoader paramClassLoader)
    throws IOException, ClassNotFoundException
  {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: invokestatic 215	com/mixpanel/android/java_websocket/util/Base64:decode	(Ljava/lang/String;I)[B
    //   5: astore 4
    //   7: aconst_null
    //   8: astore_0
    //   9: aconst_null
    //   10: astore 6
    //   12: aconst_null
    //   13: astore 5
    //   15: aconst_null
    //   16: astore_3
    //   17: aconst_null
    //   18: astore 9
    //   20: aconst_null
    //   21: astore 11
    //   23: aconst_null
    //   24: astore 10
    //   26: aconst_null
    //   27: astore 7
    //   29: aconst_null
    //   30: astore 8
    //   32: new 242	java/io/ByteArrayInputStream
    //   35: dup
    //   36: aload 4
    //   38: invokespecial 245	java/io/ByteArrayInputStream:<init>	([B)V
    //   41: astore 4
    //   43: aload_2
    //   44: ifnonnull +48 -> 92
    //   47: aload 11
    //   49: astore_3
    //   50: aload 10
    //   52: astore 5
    //   54: aload 7
    //   56: astore 6
    //   58: new 387	java/io/ObjectInputStream
    //   61: dup
    //   62: aload 4
    //   64: invokespecial 388	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   67: astore_0
    //   68: aload_0
    //   69: astore_3
    //   70: aload_0
    //   71: astore 5
    //   73: aload_0
    //   74: astore 6
    //   76: aload_0
    //   77: invokevirtual 392	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
    //   80: astore_2
    //   81: aload 4
    //   83: invokevirtual 266	java/io/ByteArrayInputStream:close	()V
    //   86: aload_0
    //   87: invokevirtual 393	java/io/ObjectInputStream:close	()V
    //   90: aload_2
    //   91: areturn
    //   92: aload 11
    //   94: astore_3
    //   95: aload 10
    //   97: astore 5
    //   99: aload 7
    //   101: astore 6
    //   103: new 6	com/mixpanel/android/java_websocket/util/Base64$1
    //   106: dup
    //   107: aload 4
    //   109: aload_2
    //   110: invokespecial 396	com/mixpanel/android/java_websocket/util/Base64$1:<init>	(Ljava/io/InputStream;Ljava/lang/ClassLoader;)V
    //   113: astore_0
    //   114: goto -46 -> 68
    //   117: astore_2
    //   118: aload 8
    //   120: astore_3
    //   121: aload 5
    //   123: astore_0
    //   124: aload_2
    //   125: athrow
    //   126: astore_2
    //   127: aload_0
    //   128: invokevirtual 266	java/io/ByteArrayInputStream:close	()V
    //   131: aload_3
    //   132: invokevirtual 393	java/io/ObjectInputStream:close	()V
    //   135: aload_2
    //   136: athrow
    //   137: astore_2
    //   138: aload 9
    //   140: astore_3
    //   141: aload 6
    //   143: astore_0
    //   144: aload_2
    //   145: athrow
    //   146: astore_3
    //   147: goto -61 -> 86
    //   150: astore_0
    //   151: aload_2
    //   152: areturn
    //   153: astore_0
    //   154: goto -23 -> 131
    //   157: astore_0
    //   158: goto -23 -> 135
    //   161: astore_2
    //   162: aload 4
    //   164: astore_0
    //   165: goto -38 -> 127
    //   168: astore_2
    //   169: aload 4
    //   171: astore_0
    //   172: aload 5
    //   174: astore_3
    //   175: goto -31 -> 144
    //   178: astore_2
    //   179: aload 4
    //   181: astore_0
    //   182: aload 6
    //   184: astore_3
    //   185: goto -61 -> 124
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	188	0	paramString	String
    //   0	188	1	paramInt	int
    //   0	188	2	paramClassLoader	ClassLoader
    //   16	125	3	localObject1	Object
    //   146	1	3	localException	Exception
    //   174	11	3	localObject2	Object
    //   5	175	4	localObject3	Object
    //   13	160	5	localObject4	Object
    //   10	173	6	localObject5	Object
    //   27	73	7	localObject6	Object
    //   30	89	8	localObject7	Object
    //   18	121	9	localObject8	Object
    //   24	72	10	localObject9	Object
    //   21	72	11	localObject10	Object
    // Exception table:
    //   from	to	target	type
    //   32	43	117	java/io/IOException
    //   32	43	126	finally
    //   124	126	126	finally
    //   144	146	126	finally
    //   32	43	137	java/lang/ClassNotFoundException
    //   81	86	146	java/lang/Exception
    //   86	90	150	java/lang/Exception
    //   127	131	153	java/lang/Exception
    //   131	135	157	java/lang/Exception
    //   58	68	161	finally
    //   76	81	161	finally
    //   103	114	161	finally
    //   58	68	168	java/lang/ClassNotFoundException
    //   76	81	168	java/lang/ClassNotFoundException
    //   103	114	168	java/lang/ClassNotFoundException
    //   58	68	178	java/io/IOException
    //   76	81	178	java/io/IOException
    //   103	114	178	java/io/IOException
  }
  
  public static void encode(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
  {
    byte[] arrayOfByte1 = new byte[3];
    byte[] arrayOfByte2 = new byte[4];
    while (paramByteBuffer1.hasRemaining())
    {
      int i = Math.min(3, paramByteBuffer1.remaining());
      paramByteBuffer1.get(arrayOfByte1, 0, i);
      encode3to4(arrayOfByte2, arrayOfByte1, i, 0);
      paramByteBuffer2.put(arrayOfByte2);
    }
  }
  
  public static void encode(ByteBuffer paramByteBuffer, CharBuffer paramCharBuffer)
  {
    byte[] arrayOfByte1 = new byte[3];
    byte[] arrayOfByte2 = new byte[4];
    while (paramByteBuffer.hasRemaining())
    {
      int i = Math.min(3, paramByteBuffer.remaining());
      paramByteBuffer.get(arrayOfByte1, 0, i);
      encode3to4(arrayOfByte2, arrayOfByte1, i, 0);
      i = 0;
      while (i < 4)
      {
        paramCharBuffer.put((char)(arrayOfByte2[i] & 0xFF));
        i += 1;
      }
    }
  }
  
  private static byte[] encode3to4(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4)
  {
    int j = 0;
    byte[] arrayOfByte = getAlphabet(paramInt4);
    if (paramInt2 > 0)
    {
      paramInt4 = paramArrayOfByte1[paramInt1] << 24 >>> 8;
      label25:
      if (paramInt2 <= 1) {
        break label104;
      }
    }
    label104:
    for (int i = paramArrayOfByte1[(paramInt1 + 1)] << 24 >>> 16;; i = 0)
    {
      if (paramInt2 > 2) {
        j = paramArrayOfByte1[(paramInt1 + 2)] << 24 >>> 24;
      }
      paramInt1 = i | paramInt4 | j;
      switch (paramInt2)
      {
      default: 
        return paramArrayOfByte2;
        paramInt4 = 0;
        break label25;
      }
    }
    paramArrayOfByte2[paramInt3] = arrayOfByte[(paramInt1 >>> 18)];
    paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(paramInt1 >>> 12 & 0x3F)];
    paramArrayOfByte2[(paramInt3 + 2)] = arrayOfByte[(paramInt1 >>> 6 & 0x3F)];
    paramArrayOfByte2[(paramInt3 + 3)] = arrayOfByte[(paramInt1 & 0x3F)];
    return paramArrayOfByte2;
    paramArrayOfByte2[paramInt3] = arrayOfByte[(paramInt1 >>> 18)];
    paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(paramInt1 >>> 12 & 0x3F)];
    paramArrayOfByte2[(paramInt3 + 2)] = arrayOfByte[(paramInt1 >>> 6 & 0x3F)];
    paramArrayOfByte2[(paramInt3 + 3)] = 61;
    return paramArrayOfByte2;
    paramArrayOfByte2[paramInt3] = arrayOfByte[(paramInt1 >>> 18)];
    paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(paramInt1 >>> 12 & 0x3F)];
    paramArrayOfByte2[(paramInt3 + 2)] = 61;
    paramArrayOfByte2[(paramInt3 + 3)] = 61;
    return paramArrayOfByte2;
  }
  
  private static byte[] encode3to4(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
  {
    encode3to4(paramArrayOfByte2, 0, paramInt1, paramArrayOfByte1, 0, paramInt2);
    return paramArrayOfByte1;
  }
  
  public static String encodeBytes(byte[] paramArrayOfByte)
  {
    Object localObject = null;
    try
    {
      paramArrayOfByte = encodeBytes(paramArrayOfByte, 0, paramArrayOfByte.length, 0);
      if ((!$assertionsDisabled) && (paramArrayOfByte == null)) {
        throw new AssertionError();
      }
    }
    catch (IOException localIOException)
    {
      do
      {
        paramArrayOfByte = (byte[])localObject;
      } while ($assertionsDisabled);
      throw new AssertionError(localIOException.getMessage());
    }
    return paramArrayOfByte;
  }
  
  public static String encodeBytes(byte[] paramArrayOfByte, int paramInt)
    throws IOException
  {
    return encodeBytes(paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
  }
  
  public static String encodeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Object localObject = null;
    try
    {
      paramArrayOfByte = encodeBytes(paramArrayOfByte, paramInt1, paramInt2, 0);
      if ((!$assertionsDisabled) && (paramArrayOfByte == null)) {
        throw new AssertionError();
      }
    }
    catch (IOException localIOException)
    {
      do
      {
        paramArrayOfByte = (byte[])localObject;
      } while ($assertionsDisabled);
      throw new AssertionError(localIOException.getMessage());
    }
    return paramArrayOfByte;
  }
  
  public static String encodeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
    throws IOException
  {
    paramArrayOfByte = encodeBytesToBytes(paramArrayOfByte, paramInt1, paramInt2, paramInt3);
    try
    {
      String str = new String(paramArrayOfByte, "US-ASCII");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
    return new String(paramArrayOfByte);
  }
  
  public static byte[] encodeBytesToBytes(byte[] paramArrayOfByte)
  {
    Object localObject = null;
    try
    {
      paramArrayOfByte = encodeBytesToBytes(paramArrayOfByte, 0, paramArrayOfByte.length, 0);
      return paramArrayOfByte;
    }
    catch (IOException localIOException)
    {
      do
      {
        paramArrayOfByte = (byte[])localObject;
      } while ($assertionsDisabled);
      throw new AssertionError("IOExceptions only come from GZipping, which is turned off: " + localIOException.getMessage());
    }
  }
  
  /* Error */
  public static byte[] encodeBytesToBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +14 -> 15
    //   4: new 222	java/lang/NullPointerException
    //   7: dup
    //   8: ldc_w 457
    //   11: invokespecial 227	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   14: athrow
    //   15: iload_1
    //   16: ifge +31 -> 47
    //   19: new 277	java/lang/IllegalArgumentException
    //   22: dup
    //   23: new 292	java/lang/StringBuilder
    //   26: dup
    //   27: invokespecial 293	java/lang/StringBuilder:<init>	()V
    //   30: ldc_w 459
    //   33: invokevirtual 299	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: iload_1
    //   37: invokevirtual 302	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   40: invokevirtual 306	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   43: invokespecial 290	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   46: athrow
    //   47: iload_2
    //   48: ifge +31 -> 79
    //   51: new 277	java/lang/IllegalArgumentException
    //   54: dup
    //   55: new 292	java/lang/StringBuilder
    //   58: dup
    //   59: invokespecial 293	java/lang/StringBuilder:<init>	()V
    //   62: ldc_w 461
    //   65: invokevirtual 299	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   68: iload_2
    //   69: invokevirtual 302	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   72: invokevirtual 306	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   75: invokespecial 290	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   78: athrow
    //   79: iload_1
    //   80: iload_2
    //   81: iadd
    //   82: aload_0
    //   83: arraylength
    //   84: if_icmple +43 -> 127
    //   87: new 277	java/lang/IllegalArgumentException
    //   90: dup
    //   91: ldc_w 463
    //   94: iconst_3
    //   95: anewarray 4	java/lang/Object
    //   98: dup
    //   99: iconst_0
    //   100: iload_1
    //   101: invokestatic 285	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   104: aastore
    //   105: dup
    //   106: iconst_1
    //   107: iload_2
    //   108: invokestatic 285	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   111: aastore
    //   112: dup
    //   113: iconst_2
    //   114: aload_0
    //   115: arraylength
    //   116: invokestatic 285	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   119: aastore
    //   120: invokestatic 289	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   123: invokespecial 290	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   126: athrow
    //   127: iload_3
    //   128: iconst_2
    //   129: iand
    //   130: ifeq +140 -> 270
    //   133: aconst_null
    //   134: astore 11
    //   136: aconst_null
    //   137: astore 18
    //   139: aconst_null
    //   140: astore 13
    //   142: aconst_null
    //   143: astore 15
    //   145: aconst_null
    //   146: astore 14
    //   148: aconst_null
    //   149: astore 12
    //   151: aconst_null
    //   152: astore 17
    //   154: aconst_null
    //   155: astore 16
    //   157: new 239	java/io/ByteArrayOutputStream
    //   160: dup
    //   161: invokespecial 240	java/io/ByteArrayOutputStream:<init>	()V
    //   164: astore 10
    //   166: new 11	com/mixpanel/android/java_websocket/util/Base64$OutputStream
    //   169: dup
    //   170: aload 10
    //   172: iload_3
    //   173: iconst_1
    //   174: ior
    //   175: invokespecial 376	com/mixpanel/android/java_websocket/util/Base64$OutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   178: astore 11
    //   180: new 465	java/util/zip/GZIPOutputStream
    //   183: dup
    //   184: aload 11
    //   186: invokespecial 466	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   189: astore 12
    //   191: aload 12
    //   193: aload_0
    //   194: iload_1
    //   195: iload_2
    //   196: invokevirtual 467	java/util/zip/GZIPOutputStream:write	([BII)V
    //   199: aload 12
    //   201: invokevirtual 468	java/util/zip/GZIPOutputStream:close	()V
    //   204: aload 12
    //   206: invokevirtual 468	java/util/zip/GZIPOutputStream:close	()V
    //   209: aload 11
    //   211: invokevirtual 378	com/mixpanel/android/java_websocket/util/Base64$OutputStream:close	()V
    //   214: aload 10
    //   216: invokevirtual 264	java/io/ByteArrayOutputStream:close	()V
    //   219: aload 10
    //   221: invokevirtual 272	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   224: areturn
    //   225: astore 10
    //   227: aload 14
    //   229: astore 13
    //   231: aload 18
    //   233: astore_0
    //   234: aload 16
    //   236: astore 11
    //   238: aload 11
    //   240: astore 12
    //   242: aload_0
    //   243: astore 11
    //   245: aload 10
    //   247: athrow
    //   248: astore_0
    //   249: aload 12
    //   251: astore 10
    //   253: aload 13
    //   255: invokevirtual 468	java/util/zip/GZIPOutputStream:close	()V
    //   258: aload 10
    //   260: invokevirtual 378	com/mixpanel/android/java_websocket/util/Base64$OutputStream:close	()V
    //   263: aload 11
    //   265: invokevirtual 264	java/io/ByteArrayOutputStream:close	()V
    //   268: aload_0
    //   269: athrow
    //   270: iload_3
    //   271: bipush 8
    //   273: iand
    //   274: ifeq +153 -> 427
    //   277: iconst_1
    //   278: istore 6
    //   280: iload_2
    //   281: iconst_3
    //   282: idiv
    //   283: istore 5
    //   285: iload_2
    //   286: iconst_3
    //   287: irem
    //   288: ifle +145 -> 433
    //   291: iconst_4
    //   292: istore 4
    //   294: iload 5
    //   296: iconst_4
    //   297: imul
    //   298: iload 4
    //   300: iadd
    //   301: istore 5
    //   303: iload 5
    //   305: istore 4
    //   307: iload 6
    //   309: ifeq +13 -> 322
    //   312: iload 5
    //   314: iload 5
    //   316: bipush 76
    //   318: idiv
    //   319: iadd
    //   320: istore 4
    //   322: iload 4
    //   324: newarray <illegal type>
    //   326: astore 10
    //   328: iconst_0
    //   329: istore 7
    //   331: iconst_0
    //   332: istore 4
    //   334: iconst_0
    //   335: istore 5
    //   337: iload 7
    //   339: iload_2
    //   340: iconst_2
    //   341: isub
    //   342: if_icmpge +97 -> 439
    //   345: aload_0
    //   346: iload 7
    //   348: iload_1
    //   349: iadd
    //   350: iconst_3
    //   351: aload 10
    //   353: iload 4
    //   355: iload_3
    //   356: invokestatic 199	com/mixpanel/android/java_websocket/util/Base64:encode3to4	([BII[BII)[B
    //   359: pop
    //   360: iload 5
    //   362: iconst_4
    //   363: iadd
    //   364: istore 9
    //   366: iload 4
    //   368: istore 8
    //   370: iload 9
    //   372: istore 5
    //   374: iload 6
    //   376: ifeq +36 -> 412
    //   379: iload 4
    //   381: istore 8
    //   383: iload 9
    //   385: istore 5
    //   387: iload 9
    //   389: bipush 76
    //   391: if_icmplt +21 -> 412
    //   394: aload 10
    //   396: iload 4
    //   398: iconst_4
    //   399: iadd
    //   400: bipush 10
    //   402: bastore
    //   403: iload 4
    //   405: iconst_1
    //   406: iadd
    //   407: istore 8
    //   409: iconst_0
    //   410: istore 5
    //   412: iload 7
    //   414: iconst_3
    //   415: iadd
    //   416: istore 7
    //   418: iload 8
    //   420: iconst_4
    //   421: iadd
    //   422: istore 4
    //   424: goto -87 -> 337
    //   427: iconst_0
    //   428: istore 6
    //   430: goto -150 -> 280
    //   433: iconst_0
    //   434: istore 4
    //   436: goto -142 -> 294
    //   439: iload 4
    //   441: istore 5
    //   443: iload 7
    //   445: iload_2
    //   446: if_icmpge +27 -> 473
    //   449: aload_0
    //   450: iload 7
    //   452: iload_1
    //   453: iadd
    //   454: iload_2
    //   455: iload 7
    //   457: isub
    //   458: aload 10
    //   460: iload 4
    //   462: iload_3
    //   463: invokestatic 199	com/mixpanel/android/java_websocket/util/Base64:encode3to4	([BII[BII)[B
    //   466: pop
    //   467: iload 4
    //   469: iconst_4
    //   470: iadd
    //   471: istore 5
    //   473: iload 5
    //   475: aload 10
    //   477: arraylength
    //   478: iconst_1
    //   479: isub
    //   480: if_icmpgt +20 -> 500
    //   483: iload 5
    //   485: newarray <illegal type>
    //   487: astore_0
    //   488: aload 10
    //   490: iconst_0
    //   491: aload_0
    //   492: iconst_0
    //   493: iload 5
    //   495: invokestatic 312	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   498: aload_0
    //   499: areturn
    //   500: aload 10
    //   502: areturn
    //   503: astore_0
    //   504: goto -295 -> 209
    //   507: astore_0
    //   508: goto -294 -> 214
    //   511: astore_0
    //   512: goto -293 -> 219
    //   515: astore 12
    //   517: goto -259 -> 258
    //   520: astore 10
    //   522: goto -259 -> 263
    //   525: astore 10
    //   527: goto -259 -> 268
    //   530: astore_0
    //   531: aload 10
    //   533: astore 11
    //   535: aload 17
    //   537: astore 10
    //   539: aload 15
    //   541: astore 13
    //   543: goto -290 -> 253
    //   546: astore_0
    //   547: aload 10
    //   549: astore 12
    //   551: aload 11
    //   553: astore 10
    //   555: aload 12
    //   557: astore 11
    //   559: aload 15
    //   561: astore 13
    //   563: goto -310 -> 253
    //   566: astore_0
    //   567: aload 10
    //   569: astore 13
    //   571: aload 11
    //   573: astore 10
    //   575: aload 13
    //   577: astore 11
    //   579: aload 12
    //   581: astore 13
    //   583: goto -330 -> 253
    //   586: astore 11
    //   588: aload 10
    //   590: astore_0
    //   591: aload 11
    //   593: astore 10
    //   595: aload 16
    //   597: astore 11
    //   599: aload 14
    //   601: astore 13
    //   603: goto -365 -> 238
    //   606: astore 12
    //   608: aload 10
    //   610: astore_0
    //   611: aload 12
    //   613: astore 10
    //   615: aload 14
    //   617: astore 13
    //   619: goto -381 -> 238
    //   622: astore 13
    //   624: aload 10
    //   626: astore_0
    //   627: aload 13
    //   629: astore 10
    //   631: aload 12
    //   633: astore 13
    //   635: goto -397 -> 238
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	638	0	paramArrayOfByte	byte[]
    //   0	638	1	paramInt1	int
    //   0	638	2	paramInt2	int
    //   0	638	3	paramInt3	int
    //   292	179	4	i	int
    //   283	211	5	j	int
    //   278	151	6	k	int
    //   329	129	7	m	int
    //   368	54	8	n	int
    //   364	28	9	i1	int
    //   164	56	10	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    //   225	21	10	localIOException1	IOException
    //   251	250	10	localObject1	Object
    //   520	1	10	localException1	Exception
    //   525	7	10	localException2	Exception
    //   537	93	10	localObject2	Object
    //   134	444	11	localObject3	Object
    //   586	6	11	localIOException2	IOException
    //   597	1	11	localObject4	Object
    //   149	101	12	localObject5	Object
    //   515	1	12	localException3	Exception
    //   549	31	12	localObject6	Object
    //   606	26	12	localIOException3	IOException
    //   140	478	13	localObject7	Object
    //   622	6	13	localIOException4	IOException
    //   633	1	13	localObject8	Object
    //   146	470	14	localObject9	Object
    //   143	417	15	localObject10	Object
    //   155	441	16	localObject11	Object
    //   152	384	17	localObject12	Object
    //   137	95	18	localObject13	Object
    // Exception table:
    //   from	to	target	type
    //   157	166	225	java/io/IOException
    //   157	166	248	finally
    //   245	248	248	finally
    //   204	209	503	java/lang/Exception
    //   209	214	507	java/lang/Exception
    //   214	219	511	java/lang/Exception
    //   253	258	515	java/lang/Exception
    //   258	263	520	java/lang/Exception
    //   263	268	525	java/lang/Exception
    //   166	180	530	finally
    //   180	191	546	finally
    //   191	204	566	finally
    //   166	180	586	java/io/IOException
    //   180	191	606	java/io/IOException
    //   191	204	622	java/io/IOException
  }
  
  /* Error */
  public static void encodeFileToFile(String paramString1, String paramString2)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 473	com/mixpanel/android/java_websocket/util/Base64:encodeFromFile	(Ljava/lang/String;)Ljava/lang/String;
    //   4: astore_3
    //   5: aconst_null
    //   6: astore_0
    //   7: aconst_null
    //   8: astore_2
    //   9: new 330	java/io/BufferedOutputStream
    //   12: dup
    //   13: new 332	java/io/FileOutputStream
    //   16: dup
    //   17: aload_1
    //   18: invokespecial 333	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   21: invokespecial 336	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   24: astore_1
    //   25: aload_1
    //   26: aload_3
    //   27: ldc 44
    //   29: invokevirtual 232	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   32: invokevirtual 340	java/io/OutputStream:write	([B)V
    //   35: aload_1
    //   36: invokevirtual 341	java/io/OutputStream:close	()V
    //   39: return
    //   40: astore_1
    //   41: aload_2
    //   42: astore_0
    //   43: aload_1
    //   44: athrow
    //   45: astore_1
    //   46: aload_0
    //   47: invokevirtual 341	java/io/OutputStream:close	()V
    //   50: aload_1
    //   51: athrow
    //   52: astore_0
    //   53: return
    //   54: astore_0
    //   55: goto -5 -> 50
    //   58: astore_2
    //   59: aload_1
    //   60: astore_0
    //   61: aload_2
    //   62: astore_1
    //   63: goto -17 -> 46
    //   66: astore_2
    //   67: aload_1
    //   68: astore_0
    //   69: aload_2
    //   70: astore_1
    //   71: goto -28 -> 43
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	74	0	paramString1	String
    //   0	74	1	paramString2	String
    //   8	34	2	localObject1	Object
    //   58	4	2	localObject2	Object
    //   66	4	2	localIOException	IOException
    //   4	23	3	str	String
    // Exception table:
    //   from	to	target	type
    //   9	25	40	java/io/IOException
    //   9	25	45	finally
    //   43	45	45	finally
    //   35	39	52	java/lang/Exception
    //   46	50	54	java/lang/Exception
    //   25	35	58	finally
    //   25	35	66	java/io/IOException
  }
  
  /* Error */
  public static String encodeFromFile(String paramString)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 4
    //   6: aload 5
    //   8: astore_3
    //   9: new 343	java/io/File
    //   12: dup
    //   13: aload_0
    //   14: invokespecial 344	java/io/File:<init>	(Ljava/lang/String;)V
    //   17: astore_0
    //   18: aload 5
    //   20: astore_3
    //   21: aload_0
    //   22: invokevirtual 348	java/io/File:length	()J
    //   25: l2d
    //   26: ldc2_w 474
    //   29: dmul
    //   30: dconst_1
    //   31: dadd
    //   32: d2i
    //   33: bipush 40
    //   35: invokestatic 478	java/lang/Math:max	(II)I
    //   38: newarray <illegal type>
    //   40: astore 6
    //   42: iconst_0
    //   43: istore_1
    //   44: aload 5
    //   46: astore_3
    //   47: new 8	com/mixpanel/android/java_websocket/util/Base64$InputStream
    //   50: dup
    //   51: new 360	java/io/BufferedInputStream
    //   54: dup
    //   55: new 362	java/io/FileInputStream
    //   58: dup
    //   59: aload_0
    //   60: invokespecial 365	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   63: invokespecial 366	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   66: iconst_1
    //   67: invokespecial 369	com/mixpanel/android/java_websocket/util/Base64$InputStream:<init>	(Ljava/io/InputStream;I)V
    //   70: astore_0
    //   71: aload_0
    //   72: aload 6
    //   74: iload_1
    //   75: sipush 4096
    //   78: invokevirtual 372	com/mixpanel/android/java_websocket/util/Base64$InputStream:read	([BII)I
    //   81: istore_2
    //   82: iload_2
    //   83: iflt +10 -> 93
    //   86: iload_1
    //   87: iload_2
    //   88: iadd
    //   89: istore_1
    //   90: goto -19 -> 71
    //   93: new 229	java/lang/String
    //   96: dup
    //   97: aload 6
    //   99: iconst_0
    //   100: iload_1
    //   101: ldc 44
    //   103: invokespecial 481	java/lang/String:<init>	([BIILjava/lang/String;)V
    //   106: astore_3
    //   107: aload_0
    //   108: invokevirtual 358	com/mixpanel/android/java_websocket/util/Base64$InputStream:close	()V
    //   111: aload_3
    //   112: areturn
    //   113: astore_0
    //   114: aload 4
    //   116: astore_3
    //   117: aload_0
    //   118: athrow
    //   119: astore_0
    //   120: aload_3
    //   121: invokevirtual 358	com/mixpanel/android/java_websocket/util/Base64$InputStream:close	()V
    //   124: aload_0
    //   125: athrow
    //   126: astore_0
    //   127: aload_3
    //   128: areturn
    //   129: astore_3
    //   130: goto -6 -> 124
    //   133: astore 4
    //   135: aload_0
    //   136: astore_3
    //   137: aload 4
    //   139: astore_0
    //   140: goto -20 -> 120
    //   143: astore 4
    //   145: aload_0
    //   146: astore_3
    //   147: aload 4
    //   149: astore_0
    //   150: goto -33 -> 117
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	paramString	String
    //   43	58	1	i	int
    //   81	8	2	j	int
    //   8	120	3	localObject1	Object
    //   129	1	3	localException	Exception
    //   136	11	3	str	String
    //   4	111	4	localObject2	Object
    //   133	5	4	localObject3	Object
    //   143	5	4	localIOException	IOException
    //   1	44	5	localObject4	Object
    //   40	58	6	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   9	18	113	java/io/IOException
    //   21	42	113	java/io/IOException
    //   47	71	113	java/io/IOException
    //   9	18	119	finally
    //   21	42	119	finally
    //   47	71	119	finally
    //   117	119	119	finally
    //   107	111	126	java/lang/Exception
    //   120	124	129	java/lang/Exception
    //   71	82	133	finally
    //   93	107	133	finally
    //   71	82	143	java/io/IOException
    //   93	107	143	java/io/IOException
  }
  
  public static String encodeObject(Serializable paramSerializable)
    throws IOException
  {
    return encodeObject(paramSerializable, 0);
  }
  
  /* Error */
  public static String encodeObject(Serializable paramSerializable, int paramInt)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +14 -> 15
    //   4: new 222	java/lang/NullPointerException
    //   7: dup
    //   8: ldc_w 488
    //   11: invokespecial 227	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   14: athrow
    //   15: aconst_null
    //   16: astore 6
    //   18: aconst_null
    //   19: astore 14
    //   21: aconst_null
    //   22: astore 5
    //   24: aconst_null
    //   25: astore 16
    //   27: aconst_null
    //   28: astore 9
    //   30: aconst_null
    //   31: astore 4
    //   33: aconst_null
    //   34: astore 7
    //   36: aconst_null
    //   37: astore 13
    //   39: aconst_null
    //   40: astore 19
    //   42: aconst_null
    //   43: astore 17
    //   45: aconst_null
    //   46: astore 12
    //   48: aconst_null
    //   49: astore 8
    //   51: aconst_null
    //   52: astore 11
    //   54: aconst_null
    //   55: astore 18
    //   57: aconst_null
    //   58: astore 15
    //   60: aconst_null
    //   61: astore 10
    //   63: new 239	java/io/ByteArrayOutputStream
    //   66: dup
    //   67: invokespecial 240	java/io/ByteArrayOutputStream:<init>	()V
    //   70: astore_2
    //   71: new 11	com/mixpanel/android/java_websocket/util/Base64$OutputStream
    //   74: dup
    //   75: aload_2
    //   76: iload_1
    //   77: iconst_1
    //   78: ior
    //   79: invokespecial 376	com/mixpanel/android/java_websocket/util/Base64$OutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   82: astore_3
    //   83: iload_1
    //   84: iconst_2
    //   85: iand
    //   86: ifeq +96 -> 182
    //   89: aload 19
    //   91: astore 6
    //   93: aload 18
    //   95: astore 7
    //   97: aload 17
    //   99: astore 8
    //   101: aload 15
    //   103: astore 9
    //   105: new 465	java/util/zip/GZIPOutputStream
    //   108: dup
    //   109: aload_3
    //   110: invokespecial 466	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   113: astore 4
    //   115: new 490	java/io/ObjectOutputStream
    //   118: dup
    //   119: aload 4
    //   121: invokespecial 491	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   124: astore 5
    //   126: aload 4
    //   128: astore 6
    //   130: aload 5
    //   132: astore 7
    //   134: aload 4
    //   136: astore 8
    //   138: aload 5
    //   140: astore 9
    //   142: aload 5
    //   144: aload_0
    //   145: invokevirtual 494	java/io/ObjectOutputStream:writeObject	(Ljava/lang/Object;)V
    //   148: aload 5
    //   150: invokevirtual 495	java/io/ObjectOutputStream:close	()V
    //   153: aload 4
    //   155: invokevirtual 468	java/util/zip/GZIPOutputStream:close	()V
    //   158: aload_3
    //   159: invokevirtual 341	java/io/OutputStream:close	()V
    //   162: aload_2
    //   163: invokevirtual 264	java/io/ByteArrayOutputStream:close	()V
    //   166: new 229	java/lang/String
    //   169: dup
    //   170: aload_2
    //   171: invokevirtual 272	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   174: ldc 44
    //   176: invokespecial 452	java/lang/String:<init>	([BLjava/lang/String;)V
    //   179: astore_0
    //   180: aload_0
    //   181: areturn
    //   182: aload 19
    //   184: astore 6
    //   186: aload 18
    //   188: astore 7
    //   190: aload 17
    //   192: astore 8
    //   194: aload 15
    //   196: astore 9
    //   198: new 490	java/io/ObjectOutputStream
    //   201: dup
    //   202: aload_3
    //   203: invokespecial 491	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   206: astore 5
    //   208: aload 12
    //   210: astore 4
    //   212: goto -86 -> 126
    //   215: astore_3
    //   216: aload 10
    //   218: astore 8
    //   220: aload 14
    //   222: astore_0
    //   223: aload 9
    //   225: astore_2
    //   226: aload_2
    //   227: astore 5
    //   229: aload_0
    //   230: astore 6
    //   232: aload 4
    //   234: astore 7
    //   236: aload_3
    //   237: athrow
    //   238: astore 4
    //   240: aload 7
    //   242: astore_3
    //   243: aload 6
    //   245: astore_2
    //   246: aload 5
    //   248: astore_0
    //   249: aload 8
    //   251: invokevirtual 495	java/io/ObjectOutputStream:close	()V
    //   254: aload_3
    //   255: invokevirtual 468	java/util/zip/GZIPOutputStream:close	()V
    //   258: aload_0
    //   259: invokevirtual 341	java/io/OutputStream:close	()V
    //   262: aload_2
    //   263: invokevirtual 264	java/io/ByteArrayOutputStream:close	()V
    //   266: aload 4
    //   268: athrow
    //   269: astore_0
    //   270: new 229	java/lang/String
    //   273: dup
    //   274: aload_2
    //   275: invokevirtual 272	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   278: invokespecial 453	java/lang/String:<init>	([B)V
    //   281: areturn
    //   282: astore_0
    //   283: goto -130 -> 153
    //   286: astore_0
    //   287: goto -129 -> 158
    //   290: astore_0
    //   291: goto -129 -> 162
    //   294: astore_0
    //   295: goto -129 -> 166
    //   298: astore 5
    //   300: goto -46 -> 254
    //   303: astore_3
    //   304: goto -46 -> 258
    //   307: astore_0
    //   308: goto -46 -> 262
    //   311: astore_0
    //   312: goto -46 -> 266
    //   315: astore 4
    //   317: aload 16
    //   319: astore_0
    //   320: aload 13
    //   322: astore_3
    //   323: aload 11
    //   325: astore 8
    //   327: goto -78 -> 249
    //   330: astore 4
    //   332: aload_3
    //   333: astore_0
    //   334: aload 6
    //   336: astore_3
    //   337: aload 7
    //   339: astore 8
    //   341: goto -92 -> 249
    //   344: astore 5
    //   346: aload_3
    //   347: astore_0
    //   348: aload 4
    //   350: astore_3
    //   351: aload 11
    //   353: astore 8
    //   355: aload 5
    //   357: astore 4
    //   359: goto -110 -> 249
    //   362: astore_3
    //   363: aload_2
    //   364: astore_0
    //   365: aload 9
    //   367: astore_2
    //   368: aload 10
    //   370: astore 8
    //   372: goto -146 -> 226
    //   375: astore 4
    //   377: aload_2
    //   378: astore_0
    //   379: aload_3
    //   380: astore_2
    //   381: aload 4
    //   383: astore_3
    //   384: aload 8
    //   386: astore 4
    //   388: aload 9
    //   390: astore 8
    //   392: goto -166 -> 226
    //   395: astore 5
    //   397: aload_2
    //   398: astore_0
    //   399: aload_3
    //   400: astore_2
    //   401: aload 5
    //   403: astore_3
    //   404: aload 10
    //   406: astore 8
    //   408: goto -182 -> 226
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	411	0	paramSerializable	Serializable
    //   0	411	1	paramInt	int
    //   70	331	2	localObject1	Object
    //   82	121	3	localOutputStream	OutputStream
    //   215	22	3	localIOException1	IOException
    //   242	13	3	localObject2	Object
    //   303	1	3	localException1	Exception
    //   322	29	3	localObject3	Object
    //   362	18	3	localIOException2	IOException
    //   383	21	3	localIOException3	IOException
    //   31	202	4	localObject4	Object
    //   238	29	4	localObject5	Object
    //   315	1	4	localObject6	Object
    //   330	19	4	localObject7	Object
    //   357	1	4	localObject8	Object
    //   375	7	4	localIOException4	IOException
    //   386	1	4	localObject9	Object
    //   22	225	5	localObject10	Object
    //   298	1	5	localException2	Exception
    //   344	12	5	localObject11	Object
    //   395	7	5	localIOException5	IOException
    //   16	319	6	localObject12	Object
    //   34	304	7	localObject13	Object
    //   49	358	8	localObject14	Object
    //   28	361	9	localObject15	Object
    //   61	344	10	localObject16	Object
    //   52	300	11	localObject17	Object
    //   46	163	12	localObject18	Object
    //   37	284	13	localObject19	Object
    //   19	202	14	localObject20	Object
    //   58	137	15	localObject21	Object
    //   25	293	16	localObject22	Object
    //   43	148	17	localObject23	Object
    //   55	132	18	localObject24	Object
    //   40	143	19	localObject25	Object
    // Exception table:
    //   from	to	target	type
    //   63	71	215	java/io/IOException
    //   63	71	238	finally
    //   236	238	238	finally
    //   166	180	269	java/io/UnsupportedEncodingException
    //   148	153	282	java/lang/Exception
    //   153	158	286	java/lang/Exception
    //   158	162	290	java/lang/Exception
    //   162	166	294	java/lang/Exception
    //   249	254	298	java/lang/Exception
    //   254	258	303	java/lang/Exception
    //   258	262	307	java/lang/Exception
    //   262	266	311	java/lang/Exception
    //   71	83	315	finally
    //   105	115	330	finally
    //   142	148	330	finally
    //   198	208	330	finally
    //   115	126	344	finally
    //   71	83	362	java/io/IOException
    //   105	115	375	java/io/IOException
    //   142	148	375	java/io/IOException
    //   198	208	375	java/io/IOException
    //   115	126	395	java/io/IOException
  }
  
  /* Error */
  public static void encodeToFile(byte[] paramArrayOfByte, String paramString)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +14 -> 15
    //   4: new 222	java/lang/NullPointerException
    //   7: dup
    //   8: ldc_w 498
    //   11: invokespecial 227	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   14: athrow
    //   15: aconst_null
    //   16: astore_2
    //   17: aconst_null
    //   18: astore_3
    //   19: new 11	com/mixpanel/android/java_websocket/util/Base64$OutputStream
    //   22: dup
    //   23: new 332	java/io/FileOutputStream
    //   26: dup
    //   27: aload_1
    //   28: invokespecial 333	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   31: iconst_1
    //   32: invokespecial 376	com/mixpanel/android/java_websocket/util/Base64$OutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   35: astore_1
    //   36: aload_1
    //   37: aload_0
    //   38: invokevirtual 377	com/mixpanel/android/java_websocket/util/Base64$OutputStream:write	([B)V
    //   41: aload_1
    //   42: invokevirtual 378	com/mixpanel/android/java_websocket/util/Base64$OutputStream:close	()V
    //   45: return
    //   46: astore_0
    //   47: aload_3
    //   48: astore_2
    //   49: aload_0
    //   50: athrow
    //   51: astore_0
    //   52: aload_2
    //   53: invokevirtual 378	com/mixpanel/android/java_websocket/util/Base64$OutputStream:close	()V
    //   56: aload_0
    //   57: athrow
    //   58: astore_0
    //   59: return
    //   60: astore_1
    //   61: goto -5 -> 56
    //   64: astore_0
    //   65: aload_1
    //   66: astore_2
    //   67: goto -15 -> 52
    //   70: astore_0
    //   71: aload_1
    //   72: astore_2
    //   73: goto -24 -> 49
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	76	0	paramArrayOfByte	byte[]
    //   0	76	1	paramString	String
    //   16	57	2	localObject1	Object
    //   18	30	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   19	36	46	java/io/IOException
    //   19	36	51	finally
    //   49	51	51	finally
    //   41	45	58	java/lang/Exception
    //   52	56	60	java/lang/Exception
    //   36	41	64	finally
    //   36	41	70	java/io/IOException
  }
  
  private static final byte[] getAlphabet(int paramInt)
  {
    if ((paramInt & 0x10) == 16) {
      return _URL_SAFE_ALPHABET;
    }
    if ((paramInt & 0x20) == 32) {
      return _ORDERED_ALPHABET;
    }
    return _STANDARD_ALPHABET;
  }
  
  private static final byte[] getDecodabet(int paramInt)
  {
    if ((paramInt & 0x10) == 16) {
      return _URL_SAFE_DECODABET;
    }
    if ((paramInt & 0x20) == 32) {
      return _ORDERED_DECODABET;
    }
    return _STANDARD_DECODABET;
  }
  
  public static class InputStream
    extends FilterInputStream
  {
    private boolean breakLines;
    private byte[] buffer;
    private int bufferLength;
    private byte[] decodabet;
    private boolean encode;
    private int lineLength;
    private int numSigBytes;
    private int options;
    private int position;
    
    public InputStream(InputStream paramInputStream)
    {
      this(paramInputStream, 0);
    }
    
    public InputStream(InputStream paramInputStream, int paramInt)
    {
      super();
      this.options = paramInt;
      boolean bool1;
      if ((paramInt & 0x8) > 0)
      {
        bool1 = true;
        this.breakLines = bool1;
        if ((paramInt & 0x1) <= 0) {
          break label94;
        }
        bool1 = bool2;
        label39:
        this.encode = bool1;
        if (!this.encode) {
          break label100;
        }
      }
      label94:
      label100:
      for (int i = 4;; i = 3)
      {
        this.bufferLength = i;
        this.buffer = new byte[this.bufferLength];
        this.position = -1;
        this.lineLength = 0;
        this.decodabet = Base64.getDecodabet(paramInt);
        return;
        bool1 = false;
        break;
        bool1 = false;
        break label39;
      }
    }
    
    public int read()
      throws IOException
    {
      int j;
      if (this.position < 0)
      {
        if (!this.encode) {
          break label110;
        }
        arrayOfByte = new byte[3];
        j = 0;
        i = 0;
        while (i < 3)
        {
          int k = this.in.read();
          if (k < 0) {
            break;
          }
          arrayOfByte[i] = ((byte)k);
          j += 1;
          i += 1;
        }
        if (j > 0)
        {
          Base64.encode3to4(arrayOfByte, 0, j, this.buffer, 0, this.options);
          this.position = 0;
          this.numSigBytes = 4;
        }
      }
      else
      {
        if (this.position < 0) {
          break label299;
        }
        if (this.position < this.numSigBytes) {
          break label213;
        }
        return -1;
      }
      return -1;
      label110:
      byte[] arrayOfByte = new byte[4];
      int i = 0;
      for (;;)
      {
        if (i < 4)
        {
          do
          {
            j = this.in.read();
          } while ((j >= 0) && (this.decodabet[(j & 0x7F)] <= -5));
          if (j >= 0) {}
        }
        else
        {
          if (i != 4) {
            break label197;
          }
          this.numSigBytes = Base64.decode4to3(arrayOfByte, 0, this.buffer, 0, this.options);
          this.position = 0;
          break;
        }
        arrayOfByte[i] = ((byte)j);
        i += 1;
      }
      label197:
      if (i == 0) {
        return -1;
      }
      throw new IOException("Improperly padded Base64 input.");
      label213:
      if ((this.encode) && (this.breakLines) && (this.lineLength >= 76))
      {
        this.lineLength = 0;
        return 10;
      }
      this.lineLength += 1;
      arrayOfByte = this.buffer;
      i = this.position;
      this.position = (i + 1);
      i = arrayOfByte[i];
      if (this.position >= this.bufferLength) {
        this.position = -1;
      }
      return i & 0xFF;
      label299:
      throw new IOException("Error in Base64 code reading stream.");
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = 0;
      for (;;)
      {
        j = i;
        if (i >= paramInt2) {
          break label54;
        }
        j = read();
        if (j < 0) {
          break;
        }
        paramArrayOfByte[(paramInt1 + i)] = ((byte)j);
        i += 1;
      }
      int j = i;
      if (i == 0) {
        j = -1;
      }
      label54:
      return j;
    }
  }
  
  public static class OutputStream
    extends FilterOutputStream
  {
    private byte[] b4;
    private boolean breakLines;
    private byte[] buffer;
    private int bufferLength;
    private byte[] decodabet;
    private boolean encode;
    private int lineLength;
    private int options;
    private int position;
    private boolean suspendEncoding;
    
    public OutputStream(OutputStream paramOutputStream)
    {
      this(paramOutputStream, 1);
    }
    
    public OutputStream(OutputStream paramOutputStream, int paramInt)
    {
      super();
      boolean bool1;
      if ((paramInt & 0x8) != 0)
      {
        bool1 = true;
        this.breakLines = bool1;
        if ((paramInt & 0x1) == 0) {
          break label106;
        }
        bool1 = bool2;
        label34:
        this.encode = bool1;
        if (!this.encode) {
          break label112;
        }
      }
      label106:
      label112:
      for (int i = 3;; i = 4)
      {
        this.bufferLength = i;
        this.buffer = new byte[this.bufferLength];
        this.position = 0;
        this.lineLength = 0;
        this.suspendEncoding = false;
        this.b4 = new byte[4];
        this.options = paramInt;
        this.decodabet = Base64.getDecodabet(paramInt);
        return;
        bool1 = false;
        break;
        bool1 = false;
        break label34;
      }
    }
    
    public void close()
      throws IOException
    {
      flushBase64();
      super.close();
      this.buffer = null;
      this.out = null;
    }
    
    public void flushBase64()
      throws IOException
    {
      if (this.position > 0)
      {
        if (this.encode)
        {
          this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position, this.options));
          this.position = 0;
        }
      }
      else {
        return;
      }
      throw new IOException("Base64 input not properly padded.");
    }
    
    public void resumeEncoding()
    {
      this.suspendEncoding = false;
    }
    
    public void suspendEncoding()
      throws IOException
    {
      flushBase64();
      this.suspendEncoding = true;
    }
    
    public void write(int paramInt)
      throws IOException
    {
      if (this.suspendEncoding) {
        this.out.write(paramInt);
      }
      do
      {
        do
        {
          do
          {
            return;
            if (!this.encode) {
              break;
            }
            arrayOfByte = this.buffer;
            i = this.position;
            this.position = (i + 1);
            arrayOfByte[i] = ((byte)paramInt);
          } while (this.position < this.bufferLength);
          this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
          this.lineLength += 4;
          if ((this.breakLines) && (this.lineLength >= 76))
          {
            this.out.write(10);
            this.lineLength = 0;
          }
          this.position = 0;
          return;
          if (this.decodabet[(paramInt & 0x7F)] <= -5) {
            break;
          }
          byte[] arrayOfByte = this.buffer;
          int i = this.position;
          this.position = (i + 1);
          arrayOfByte[i] = ((byte)paramInt);
        } while (this.position < this.bufferLength);
        paramInt = Base64.decode4to3(this.buffer, 0, this.b4, 0, this.options);
        this.out.write(this.b4, 0, paramInt);
        this.position = 0;
        return;
      } while (this.decodabet[(paramInt & 0x7F)] == -5);
      throw new IOException("Invalid character in Base64 data.");
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      if (this.suspendEncoding) {
        this.out.write(paramArrayOfByte, paramInt1, paramInt2);
      }
      for (;;)
      {
        return;
        int i = 0;
        while (i < paramInt2)
        {
          write(paramArrayOfByte[(paramInt1 + i)]);
          i += 1;
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/util/Base64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
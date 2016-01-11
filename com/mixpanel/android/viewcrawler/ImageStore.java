package com.mixpanel.android.viewcrawler;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.util.HttpService;
import com.mixpanel.android.util.RemoteService;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class ImageStore
{
  private static final String DEFAULT_DIRECTORY_NAME = "MixpanelAPI.Images";
  private static final String FILE_PREFIX = "MP_IMG_";
  private static final String LOGTAG = "MixpanelAPI.ImageStore";
  private static final int MAX_BITMAP_SIZE = 10000000;
  private final MPConfig mConfig;
  private final MessageDigest mDigest;
  private final File mDirectory;
  private final RemoteService mPoster;
  
  public ImageStore(Context paramContext)
  {
    this(paramContext, "MixpanelAPI.Images", new HttpService());
  }
  
  public ImageStore(Context paramContext, String paramString, RemoteService paramRemoteService)
  {
    this.mDirectory = paramContext.getDir(paramString, 0);
    this.mPoster = paramRemoteService;
    this.mConfig = MPConfig.getInstance(paramContext);
    try
    {
      paramContext = MessageDigest.getInstance("SHA1");
      this.mDigest = paramContext;
      return;
    }
    catch (NoSuchAlgorithmException paramContext)
    {
      for (;;)
      {
        Log.w("MixpanelAPI.ImageStore", "Images won't be stored because this platform doesn't supply a SHA1 hash function");
        paramContext = null;
      }
    }
  }
  
  private File storedFile(String paramString)
  {
    if (this.mDigest == null) {
      return null;
    }
    paramString = this.mDigest.digest(paramString.getBytes());
    paramString = "MP_IMG_" + Base64.encodeToString(paramString, 10);
    return new File(this.mDirectory, paramString);
  }
  
  public void clearStorage()
  {
    File[] arrayOfFile = this.mDirectory.listFiles();
    int j = arrayOfFile.length;
    int i = 0;
    while (i < j)
    {
      File localFile = arrayOfFile[i];
      if (localFile.getName().startsWith("MP_IMG_")) {
        localFile.delete();
      }
      i += 1;
    }
  }
  
  public void deleteStorage(String paramString)
  {
    paramString = storedFile(paramString);
    if (paramString != null) {
      paramString.delete();
    }
  }
  
  /* Error */
  public android.graphics.Bitmap getImage(String paramString)
    throws ImageStore.CantGetImageException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial 131	com/mixpanel/android/viewcrawler/ImageStore:storedFile	(Ljava/lang/String;)Ljava/io/File;
    //   5: astore 6
    //   7: aconst_null
    //   8: astore_3
    //   9: aload 6
    //   11: ifnull +11 -> 22
    //   14: aload 6
    //   16: invokevirtual 142	java/io/File:exists	()Z
    //   19: ifne +24 -> 43
    //   22: aload_0
    //   23: getfield 60	com/mixpanel/android/viewcrawler/ImageStore:mConfig	Lcom/mixpanel/android/mpmetrics/MPConfig;
    //   26: invokevirtual 146	com/mixpanel/android/mpmetrics/MPConfig:getSSLSocketFactory	()Ljavax/net/ssl/SSLSocketFactory;
    //   29: astore_2
    //   30: aload_0
    //   31: getfield 52	com/mixpanel/android/viewcrawler/ImageStore:mPoster	Lcom/mixpanel/android/util/RemoteService;
    //   34: aload_1
    //   35: aconst_null
    //   36: aload_2
    //   37: invokeinterface 152 4 0
    //   42: astore_3
    //   43: aload_3
    //   44: ifnull +161 -> 205
    //   47: aload 6
    //   49: ifnull +41 -> 90
    //   52: aload_3
    //   53: arraylength
    //   54: ldc 20
    //   56: if_icmpge +34 -> 90
    //   59: aconst_null
    //   60: astore_1
    //   61: aconst_null
    //   62: astore 5
    //   64: aconst_null
    //   65: astore 4
    //   67: new 154	java/io/FileOutputStream
    //   70: dup
    //   71: aload 6
    //   73: invokespecial 157	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   76: astore_2
    //   77: aload_2
    //   78: aload_3
    //   79: invokevirtual 163	java/io/OutputStream:write	([B)V
    //   82: aload_2
    //   83: ifnull +7 -> 90
    //   86: aload_2
    //   87: invokevirtual 166	java/io/OutputStream:close	()V
    //   90: aload_3
    //   91: iconst_0
    //   92: aload_3
    //   93: arraylength
    //   94: invokestatic 172	android/graphics/BitmapFactory:decodeByteArray	([BII)Landroid/graphics/Bitmap;
    //   97: astore_2
    //   98: aload_2
    //   99: astore_1
    //   100: aload_2
    //   101: ifnonnull +135 -> 236
    //   104: new 6	com/mixpanel/android/viewcrawler/ImageStore$CantGetImageException
    //   107: dup
    //   108: ldc -82
    //   110: invokespecial 176	com/mixpanel/android/viewcrawler/ImageStore$CantGetImageException:<init>	(Ljava/lang/String;)V
    //   113: athrow
    //   114: astore_1
    //   115: new 6	com/mixpanel/android/viewcrawler/ImageStore$CantGetImageException
    //   118: dup
    //   119: ldc -78
    //   121: aload_1
    //   122: invokespecial 181	com/mixpanel/android/viewcrawler/ImageStore$CantGetImageException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   125: athrow
    //   126: astore_1
    //   127: new 6	com/mixpanel/android/viewcrawler/ImageStore$CantGetImageException
    //   130: dup
    //   131: ldc -73
    //   133: aload_1
    //   134: invokespecial 181	com/mixpanel/android/viewcrawler/ImageStore$CantGetImageException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   137: athrow
    //   138: astore_1
    //   139: ldc 17
    //   141: ldc -71
    //   143: aload_1
    //   144: invokestatic 188	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   147: pop
    //   148: goto -58 -> 90
    //   151: astore_2
    //   152: aload 4
    //   154: astore_1
    //   155: new 6	com/mixpanel/android/viewcrawler/ImageStore$CantGetImageException
    //   158: dup
    //   159: ldc -66
    //   161: aload_2
    //   162: invokespecial 181	com/mixpanel/android/viewcrawler/ImageStore$CantGetImageException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   165: athrow
    //   166: astore_2
    //   167: aload_1
    //   168: ifnull +7 -> 175
    //   171: aload_1
    //   172: invokevirtual 166	java/io/OutputStream:close	()V
    //   175: aload_2
    //   176: athrow
    //   177: astore_2
    //   178: aload 5
    //   180: astore_1
    //   181: new 6	com/mixpanel/android/viewcrawler/ImageStore$CantGetImageException
    //   184: dup
    //   185: ldc -64
    //   187: aload_2
    //   188: invokespecial 181	com/mixpanel/android/viewcrawler/ImageStore$CantGetImageException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   191: athrow
    //   192: astore_1
    //   193: ldc 17
    //   195: ldc -71
    //   197: aload_1
    //   198: invokestatic 188	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   201: pop
    //   202: goto -27 -> 175
    //   205: aload 6
    //   207: invokevirtual 195	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   210: invokestatic 198	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   213: astore_2
    //   214: aload_2
    //   215: astore_1
    //   216: aload_2
    //   217: ifnonnull +19 -> 236
    //   220: aload 6
    //   222: invokevirtual 127	java/io/File:delete	()Z
    //   225: pop
    //   226: new 6	com/mixpanel/android/viewcrawler/ImageStore$CantGetImageException
    //   229: dup
    //   230: ldc -56
    //   232: invokespecial 176	com/mixpanel/android/viewcrawler/ImageStore$CantGetImageException:<init>	(Ljava/lang/String;)V
    //   235: athrow
    //   236: aload_1
    //   237: areturn
    //   238: astore_3
    //   239: aload_2
    //   240: astore_1
    //   241: aload_3
    //   242: astore_2
    //   243: goto -76 -> 167
    //   246: astore_3
    //   247: aload_2
    //   248: astore_1
    //   249: aload_3
    //   250: astore_2
    //   251: goto -70 -> 181
    //   254: astore_3
    //   255: aload_2
    //   256: astore_1
    //   257: aload_3
    //   258: astore_2
    //   259: goto -104 -> 155
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	262	0	this	ImageStore
    //   0	262	1	paramString	String
    //   29	72	2	localObject1	Object
    //   151	11	2	localFileNotFoundException1	java.io.FileNotFoundException
    //   166	10	2	localObject2	Object
    //   177	11	2	localIOException1	java.io.IOException
    //   213	46	2	localObject3	Object
    //   8	85	3	arrayOfByte	byte[]
    //   238	4	3	localObject4	Object
    //   246	4	3	localIOException2	java.io.IOException
    //   254	4	3	localFileNotFoundException2	java.io.FileNotFoundException
    //   65	88	4	localObject5	Object
    //   62	117	5	localObject6	Object
    //   5	216	6	localFile	File
    // Exception table:
    //   from	to	target	type
    //   22	43	114	java/io/IOException
    //   22	43	126	com/mixpanel/android/util/RemoteService$ServiceUnavailableException
    //   86	90	138	java/io/IOException
    //   67	77	151	java/io/FileNotFoundException
    //   67	77	166	finally
    //   155	166	166	finally
    //   181	192	166	finally
    //   67	77	177	java/io/IOException
    //   171	175	192	java/io/IOException
    //   77	82	238	finally
    //   77	82	246	java/io/IOException
    //   77	82	254	java/io/FileNotFoundException
  }
  
  public static class CantGetImageException
    extends Exception
  {
    public CantGetImageException(String paramString)
    {
      super();
    }
    
    public CantGetImageException(String paramString, Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/viewcrawler/ImageStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
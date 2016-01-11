package com.facebook.internal;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class FileLruCache
{
  private static final String HEADER_CACHEKEY_KEY = "key";
  private static final String HEADER_CACHE_CONTENT_TAG_KEY = "tag";
  static final String TAG = FileLruCache.class.getSimpleName();
  private static final AtomicLong bufferIndex = new AtomicLong();
  private final File directory;
  private boolean isTrimInProgress;
  private boolean isTrimPending;
  private AtomicLong lastClearCacheTime = new AtomicLong(0L);
  private final Limits limits;
  private final Object lock;
  private final String tag;
  
  public FileLruCache(String paramString, Limits paramLimits)
  {
    this.tag = paramString;
    this.limits = paramLimits;
    this.directory = new File(FacebookSdk.getCacheDir(), paramString);
    this.lock = new Object();
    if ((this.directory.mkdirs()) || (this.directory.isDirectory())) {
      BufferFile.deleteAll(this.directory);
    }
  }
  
  private void postTrim()
  {
    synchronized (this.lock)
    {
      if (!this.isTrimPending)
      {
        this.isTrimPending = true;
        FacebookSdk.getExecutor().execute(new Runnable()
        {
          public void run()
          {
            FileLruCache.this.trim();
          }
        });
      }
      return;
    }
  }
  
  private void renameToTargetAndTrim(String paramString, File paramFile)
  {
    if (!paramFile.renameTo(new File(this.directory, Utility.md5hash(paramString)))) {
      paramFile.delete();
    }
    postTrim();
  }
  
  /* Error */
  private void trim()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 99	com/facebook/internal/FileLruCache:lock	Ljava/lang/Object;
    //   4: astore 11
    //   6: aload 11
    //   8: monitorenter
    //   9: aload_0
    //   10: iconst_0
    //   11: putfield 128	com/facebook/internal/FileLruCache:isTrimPending	Z
    //   14: aload_0
    //   15: iconst_1
    //   16: putfield 157	com/facebook/internal/FileLruCache:isTrimInProgress	Z
    //   19: aload 11
    //   21: monitorexit
    //   22: getstatic 163	com/facebook/LoggingBehavior:CACHE	Lcom/facebook/LoggingBehavior;
    //   25: getstatic 65	com/facebook/internal/FileLruCache:TAG	Ljava/lang/String;
    //   28: ldc -91
    //   30: invokestatic 171	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;)V
    //   33: new 173	java/util/PriorityQueue
    //   36: dup
    //   37: invokespecial 174	java/util/PriorityQueue:<init>	()V
    //   40: astore 11
    //   42: lconst_0
    //   43: lstore 7
    //   45: lconst_0
    //   46: lstore 5
    //   48: aload_0
    //   49: getfield 97	com/facebook/internal/FileLruCache:directory	Ljava/io/File;
    //   52: invokestatic 178	com/facebook/internal/FileLruCache$BufferFile:excludeBufferFiles	()Ljava/io/FilenameFilter;
    //   55: invokevirtual 182	java/io/File:listFiles	(Ljava/io/FilenameFilter;)[Ljava/io/File;
    //   58: astore 12
    //   60: lload 5
    //   62: lstore_3
    //   63: lload 7
    //   65: lstore 9
    //   67: aload 12
    //   69: ifnull +130 -> 199
    //   72: aload 12
    //   74: arraylength
    //   75: istore_2
    //   76: iconst_0
    //   77: istore_1
    //   78: lload 5
    //   80: lstore_3
    //   81: lload 7
    //   83: lstore 9
    //   85: iload_1
    //   86: iload_2
    //   87: if_icmpge +112 -> 199
    //   90: aload 12
    //   92: iload_1
    //   93: aaload
    //   94: astore 13
    //   96: new 28	com/facebook/internal/FileLruCache$ModifiedFile
    //   99: dup
    //   100: aload 13
    //   102: invokespecial 184	com/facebook/internal/FileLruCache$ModifiedFile:<init>	(Ljava/io/File;)V
    //   105: astore 14
    //   107: aload 11
    //   109: aload 14
    //   111: invokevirtual 188	java/util/PriorityQueue:add	(Ljava/lang/Object;)Z
    //   114: pop
    //   115: getstatic 163	com/facebook/LoggingBehavior:CACHE	Lcom/facebook/LoggingBehavior;
    //   118: getstatic 65	com/facebook/internal/FileLruCache:TAG	Ljava/lang/String;
    //   121: new 190	java/lang/StringBuilder
    //   124: dup
    //   125: invokespecial 191	java/lang/StringBuilder:<init>	()V
    //   128: ldc -63
    //   130: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: aload 14
    //   135: invokevirtual 201	com/facebook/internal/FileLruCache$ModifiedFile:getModified	()J
    //   138: invokestatic 207	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   141: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   144: ldc -44
    //   146: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: aload 14
    //   151: invokevirtual 215	com/facebook/internal/FileLruCache$ModifiedFile:getFile	()Ljava/io/File;
    //   154: invokevirtual 218	java/io/File:getName	()Ljava/lang/String;
    //   157: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: invokevirtual 221	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   163: invokestatic 171	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;)V
    //   166: aload 13
    //   168: invokevirtual 224	java/io/File:length	()J
    //   171: lstore_3
    //   172: lload 7
    //   174: lload_3
    //   175: ladd
    //   176: lstore 7
    //   178: lload 5
    //   180: lconst_1
    //   181: ladd
    //   182: lstore 5
    //   184: iload_1
    //   185: iconst_1
    //   186: iadd
    //   187: istore_1
    //   188: goto -110 -> 78
    //   191: astore 12
    //   193: aload 11
    //   195: monitorexit
    //   196: aload 12
    //   198: athrow
    //   199: lload 9
    //   201: aload_0
    //   202: getfield 84	com/facebook/internal/FileLruCache:limits	Lcom/facebook/internal/FileLruCache$Limits;
    //   205: invokevirtual 228	com/facebook/internal/FileLruCache$Limits:getByteCount	()I
    //   208: i2l
    //   209: lcmp
    //   210: ifgt +16 -> 226
    //   213: lload_3
    //   214: aload_0
    //   215: getfield 84	com/facebook/internal/FileLruCache:limits	Lcom/facebook/internal/FileLruCache$Limits;
    //   218: invokevirtual 231	com/facebook/internal/FileLruCache$Limits:getFileCount	()I
    //   221: i2l
    //   222: lcmp
    //   223: ifle +100 -> 323
    //   226: aload 11
    //   228: invokevirtual 235	java/util/PriorityQueue:remove	()Ljava/lang/Object;
    //   231: checkcast 28	com/facebook/internal/FileLruCache$ModifiedFile
    //   234: invokevirtual 215	com/facebook/internal/FileLruCache$ModifiedFile:getFile	()Ljava/io/File;
    //   237: astore 12
    //   239: getstatic 163	com/facebook/LoggingBehavior:CACHE	Lcom/facebook/LoggingBehavior;
    //   242: getstatic 65	com/facebook/internal/FileLruCache:TAG	Ljava/lang/String;
    //   245: new 190	java/lang/StringBuilder
    //   248: dup
    //   249: invokespecial 191	java/lang/StringBuilder:<init>	()V
    //   252: ldc -19
    //   254: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   257: aload 12
    //   259: invokevirtual 218	java/io/File:getName	()Ljava/lang/String;
    //   262: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   265: invokevirtual 221	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   268: invokestatic 171	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;)V
    //   271: lload 9
    //   273: aload 12
    //   275: invokevirtual 224	java/io/File:length	()J
    //   278: lsub
    //   279: lstore 9
    //   281: lload_3
    //   282: lconst_1
    //   283: lsub
    //   284: lstore_3
    //   285: aload 12
    //   287: invokevirtual 153	java/io/File:delete	()Z
    //   290: pop
    //   291: goto -92 -> 199
    //   294: astore 12
    //   296: aload_0
    //   297: getfield 99	com/facebook/internal/FileLruCache:lock	Ljava/lang/Object;
    //   300: astore 11
    //   302: aload 11
    //   304: monitorenter
    //   305: aload_0
    //   306: iconst_0
    //   307: putfield 157	com/facebook/internal/FileLruCache:isTrimInProgress	Z
    //   310: aload_0
    //   311: getfield 99	com/facebook/internal/FileLruCache:lock	Ljava/lang/Object;
    //   314: invokevirtual 240	java/lang/Object:notifyAll	()V
    //   317: aload 11
    //   319: monitorexit
    //   320: aload 12
    //   322: athrow
    //   323: aload_0
    //   324: getfield 99	com/facebook/internal/FileLruCache:lock	Ljava/lang/Object;
    //   327: astore 11
    //   329: aload 11
    //   331: monitorenter
    //   332: aload_0
    //   333: iconst_0
    //   334: putfield 157	com/facebook/internal/FileLruCache:isTrimInProgress	Z
    //   337: aload_0
    //   338: getfield 99	com/facebook/internal/FileLruCache:lock	Ljava/lang/Object;
    //   341: invokevirtual 240	java/lang/Object:notifyAll	()V
    //   344: aload 11
    //   346: monitorexit
    //   347: return
    //   348: astore 12
    //   350: aload 11
    //   352: monitorexit
    //   353: aload 12
    //   355: athrow
    //   356: astore 12
    //   358: aload 11
    //   360: monitorexit
    //   361: aload 12
    //   363: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	364	0	this	FileLruCache
    //   77	111	1	i	int
    //   75	13	2	j	int
    //   62	223	3	l1	long
    //   46	137	5	l2	long
    //   43	134	7	l3	long
    //   65	215	9	l4	long
    //   58	33	12	arrayOfFile	File[]
    //   191	6	12	localObject2	Object
    //   237	49	12	localFile1	File
    //   294	27	12	localObject3	Object
    //   348	6	12	localObject4	Object
    //   356	6	12	localObject5	Object
    //   94	73	13	localFile2	File
    //   105	45	14	localModifiedFile	ModifiedFile
    // Exception table:
    //   from	to	target	type
    //   9	22	191	finally
    //   193	196	191	finally
    //   22	42	294	finally
    //   48	60	294	finally
    //   72	76	294	finally
    //   96	172	294	finally
    //   199	226	294	finally
    //   226	281	294	finally
    //   285	291	294	finally
    //   332	347	348	finally
    //   350	353	348	finally
    //   305	320	356	finally
    //   358	361	356	finally
  }
  
  public void clearCache()
  {
    final File[] arrayOfFile = this.directory.listFiles(BufferFile.excludeBufferFiles());
    this.lastClearCacheTime.set(System.currentTimeMillis());
    if (arrayOfFile != null) {
      FacebookSdk.getExecutor().execute(new Runnable()
      {
        public void run()
        {
          File[] arrayOfFile = arrayOfFile;
          int j = arrayOfFile.length;
          int i = 0;
          while (i < j)
          {
            arrayOfFile[i].delete();
            i += 1;
          }
        }
      });
    }
  }
  
  public InputStream get(String paramString)
    throws IOException
  {
    return get(paramString, null);
  }
  
  /* Error */
  public InputStream get(String paramString1, String paramString2)
    throws IOException
  {
    // Byte code:
    //   0: new 86	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: getfield 97	com/facebook/internal/FileLruCache:directory	Ljava/io/File;
    //   8: aload_1
    //   9: invokestatic 146	com/facebook/internal/Utility:md5hash	(Ljava/lang/String;)Ljava/lang/String;
    //   12: invokespecial 95	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   15: astore 6
    //   17: new 262	java/io/FileInputStream
    //   20: dup
    //   21: aload 6
    //   23: invokespecial 263	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   26: astore 7
    //   28: new 265	java/io/BufferedInputStream
    //   31: dup
    //   32: aload 7
    //   34: sipush 8192
    //   37: invokespecial 268	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;I)V
    //   40: astore 7
    //   42: aload 7
    //   44: invokestatic 272	com/facebook/internal/FileLruCache$StreamHeader:readHeader	(Ljava/io/InputStream;)Lorg/json/JSONObject;
    //   47: astore 8
    //   49: aload 8
    //   51: ifnonnull +17 -> 68
    //   54: iconst_0
    //   55: ifne +8 -> 63
    //   58: aload 7
    //   60: invokevirtual 275	java/io/BufferedInputStream:close	()V
    //   63: aconst_null
    //   64: areturn
    //   65: astore_1
    //   66: aconst_null
    //   67: areturn
    //   68: aload 8
    //   70: ldc 39
    //   72: invokevirtual 280	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   75: astore 9
    //   77: aload 9
    //   79: ifnull +14 -> 93
    //   82: aload 9
    //   84: aload_1
    //   85: invokevirtual 285	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   88: istore_3
    //   89: iload_3
    //   90: ifne +14 -> 104
    //   93: iconst_0
    //   94: ifne +8 -> 102
    //   97: aload 7
    //   99: invokevirtual 275	java/io/BufferedInputStream:close	()V
    //   102: aconst_null
    //   103: areturn
    //   104: aload 8
    //   106: ldc 42
    //   108: aconst_null
    //   109: invokevirtual 288	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   112: astore_1
    //   113: aload_2
    //   114: ifnonnull +7 -> 121
    //   117: aload_1
    //   118: ifnonnull +17 -> 135
    //   121: aload_2
    //   122: ifnull +24 -> 146
    //   125: aload_2
    //   126: aload_1
    //   127: invokevirtual 285	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   130: istore_3
    //   131: iload_3
    //   132: ifne +14 -> 146
    //   135: iconst_0
    //   136: ifne +8 -> 144
    //   139: aload 7
    //   141: invokevirtual 275	java/io/BufferedInputStream:close	()V
    //   144: aconst_null
    //   145: areturn
    //   146: new 290	java/util/Date
    //   149: dup
    //   150: invokespecial 291	java/util/Date:<init>	()V
    //   153: invokevirtual 294	java/util/Date:getTime	()J
    //   156: lstore 4
    //   158: getstatic 163	com/facebook/LoggingBehavior:CACHE	Lcom/facebook/LoggingBehavior;
    //   161: getstatic 65	com/facebook/internal/FileLruCache:TAG	Ljava/lang/String;
    //   164: new 190	java/lang/StringBuilder
    //   167: dup
    //   168: invokespecial 191	java/lang/StringBuilder:<init>	()V
    //   171: ldc_w 296
    //   174: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   177: lload 4
    //   179: invokestatic 207	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   182: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   185: ldc_w 298
    //   188: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   191: aload 6
    //   193: invokevirtual 218	java/io/File:getName	()Ljava/lang/String;
    //   196: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   199: invokevirtual 221	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   202: invokestatic 171	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;)V
    //   205: aload 6
    //   207: lload 4
    //   209: invokevirtual 302	java/io/File:setLastModified	(J)Z
    //   212: pop
    //   213: iconst_1
    //   214: ifne +8 -> 222
    //   217: aload 7
    //   219: invokevirtual 275	java/io/BufferedInputStream:close	()V
    //   222: aload 7
    //   224: areturn
    //   225: astore_1
    //   226: iconst_0
    //   227: ifne +8 -> 235
    //   230: aload 7
    //   232: invokevirtual 275	java/io/BufferedInputStream:close	()V
    //   235: aload_1
    //   236: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	237	0	this	FileLruCache
    //   0	237	1	paramString1	String
    //   0	237	2	paramString2	String
    //   88	44	3	bool	boolean
    //   156	52	4	l	long
    //   15	191	6	localFile	File
    //   26	205	7	localObject	Object
    //   47	58	8	localJSONObject	JSONObject
    //   75	8	9	str	String
    // Exception table:
    //   from	to	target	type
    //   17	28	65	java/io/IOException
    //   42	49	225	finally
    //   68	77	225	finally
    //   82	89	225	finally
    //   104	113	225	finally
    //   125	131	225	finally
    //   146	213	225	finally
  }
  
  public String getLocation()
  {
    return this.directory.getPath();
  }
  
  public InputStream interceptAndPut(String paramString, InputStream paramInputStream)
    throws IOException
  {
    return new CopyingInputStream(paramInputStream, openPutStream(paramString));
  }
  
  public OutputStream openPutStream(String paramString)
    throws IOException
  {
    return openPutStream(paramString, null);
  }
  
  /* Error */
  public OutputStream openPutStream(final String paramString1, String paramString2)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 97	com/facebook/internal/FileLruCache:directory	Ljava/io/File;
    //   4: invokestatic 326	com/facebook/internal/FileLruCache$BufferFile:newFile	(Ljava/io/File;)Ljava/io/File;
    //   7: astore_3
    //   8: aload_3
    //   9: invokevirtual 153	java/io/File:delete	()Z
    //   12: pop
    //   13: aload_3
    //   14: invokevirtual 329	java/io/File:createNewFile	()Z
    //   17: ifne +34 -> 51
    //   20: new 256	java/io/IOException
    //   23: dup
    //   24: new 190	java/lang/StringBuilder
    //   27: dup
    //   28: invokespecial 191	java/lang/StringBuilder:<init>	()V
    //   31: ldc_w 331
    //   34: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: aload_3
    //   38: invokevirtual 334	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   41: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: invokevirtual 221	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   47: invokespecial 337	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   50: athrow
    //   51: new 339	java/io/FileOutputStream
    //   54: dup
    //   55: aload_3
    //   56: invokespecial 340	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   59: astore 4
    //   61: new 342	java/io/BufferedOutputStream
    //   64: dup
    //   65: new 19	com/facebook/internal/FileLruCache$CloseCallbackOutputStream
    //   68: dup
    //   69: aload 4
    //   71: new 6	com/facebook/internal/FileLruCache$1
    //   74: dup
    //   75: aload_0
    //   76: invokestatic 246	java/lang/System:currentTimeMillis	()J
    //   79: aload_3
    //   80: aload_1
    //   81: invokespecial 345	com/facebook/internal/FileLruCache$1:<init>	(Lcom/facebook/internal/FileLruCache;JLjava/io/File;Ljava/lang/String;)V
    //   84: invokespecial 348	com/facebook/internal/FileLruCache$CloseCallbackOutputStream:<init>	(Ljava/io/OutputStream;Lcom/facebook/internal/FileLruCache$StreamCloseCallback;)V
    //   87: sipush 8192
    //   90: invokespecial 351	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   93: astore_3
    //   94: new 277	org/json/JSONObject
    //   97: dup
    //   98: invokespecial 352	org/json/JSONObject:<init>	()V
    //   101: astore 4
    //   103: aload 4
    //   105: ldc 39
    //   107: aload_1
    //   108: invokevirtual 356	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   111: pop
    //   112: aload_2
    //   113: invokestatic 360	com/facebook/internal/Utility:isNullOrEmpty	(Ljava/lang/String;)Z
    //   116: ifne +12 -> 128
    //   119: aload 4
    //   121: ldc 42
    //   123: aload_2
    //   124: invokevirtual 356	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   127: pop
    //   128: aload_3
    //   129: aload 4
    //   131: invokestatic 364	com/facebook/internal/FileLruCache$StreamHeader:writeHeader	(Ljava/io/OutputStream;Lorg/json/JSONObject;)V
    //   134: iconst_1
    //   135: ifne +7 -> 142
    //   138: aload_3
    //   139: invokevirtual 365	java/io/BufferedOutputStream:close	()V
    //   142: aload_3
    //   143: areturn
    //   144: astore_1
    //   145: getstatic 163	com/facebook/LoggingBehavior:CACHE	Lcom/facebook/LoggingBehavior;
    //   148: iconst_5
    //   149: getstatic 65	com/facebook/internal/FileLruCache:TAG	Ljava/lang/String;
    //   152: new 190	java/lang/StringBuilder
    //   155: dup
    //   156: invokespecial 191	java/lang/StringBuilder:<init>	()V
    //   159: ldc_w 367
    //   162: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: aload_1
    //   166: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   169: invokevirtual 221	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   172: invokestatic 370	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;ILjava/lang/String;Ljava/lang/String;)V
    //   175: new 256	java/io/IOException
    //   178: dup
    //   179: aload_1
    //   180: invokevirtual 373	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
    //   183: invokespecial 337	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   186: athrow
    //   187: astore_1
    //   188: getstatic 163	com/facebook/LoggingBehavior:CACHE	Lcom/facebook/LoggingBehavior;
    //   191: iconst_5
    //   192: getstatic 65	com/facebook/internal/FileLruCache:TAG	Ljava/lang/String;
    //   195: new 190	java/lang/StringBuilder
    //   198: dup
    //   199: invokespecial 191	java/lang/StringBuilder:<init>	()V
    //   202: ldc_w 375
    //   205: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: aload_1
    //   209: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   212: invokevirtual 221	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   215: invokestatic 370	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;ILjava/lang/String;Ljava/lang/String;)V
    //   218: new 256	java/io/IOException
    //   221: dup
    //   222: aload_1
    //   223: invokevirtual 376	org/json/JSONException:getMessage	()Ljava/lang/String;
    //   226: invokespecial 337	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   229: athrow
    //   230: astore_1
    //   231: iconst_0
    //   232: ifne +7 -> 239
    //   235: aload_3
    //   236: invokevirtual 365	java/io/BufferedOutputStream:close	()V
    //   239: aload_1
    //   240: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	241	0	this	FileLruCache
    //   0	241	1	paramString1	String
    //   0	241	2	paramString2	String
    //   7	229	3	localObject1	Object
    //   59	71	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   51	61	144	java/io/FileNotFoundException
    //   94	128	187	org/json/JSONException
    //   128	134	187	org/json/JSONException
    //   94	128	230	finally
    //   128	134	230	finally
    //   188	230	230	finally
  }
  
  long sizeInBytesForTest()
  {
    long l2;
    synchronized (this.lock)
    {
      for (;;)
      {
        if (!this.isTrimPending)
        {
          boolean bool = this.isTrimInProgress;
          if (!bool) {
            break;
          }
        }
        try
        {
          this.lock.wait();
        }
        catch (InterruptedException localInterruptedException) {}
      }
      ??? = this.directory.listFiles();
      long l1 = 0L;
      l2 = l1;
      if (??? != null)
      {
        int j = ???.length;
        int i = 0;
        l2 = l1;
        if (i < j)
        {
          l1 += ???[i].length();
          i += 1;
        }
      }
    }
    return l2;
  }
  
  public String toString()
  {
    return "{FileLruCache: tag:" + this.tag + " file:" + this.directory.getName() + "}";
  }
  
  private static class BufferFile
  {
    private static final String FILE_NAME_PREFIX = "buffer";
    private static final FilenameFilter filterExcludeBufferFiles = new FilenameFilter()
    {
      public boolean accept(File paramAnonymousFile, String paramAnonymousString)
      {
        return !paramAnonymousString.startsWith("buffer");
      }
    };
    private static final FilenameFilter filterExcludeNonBufferFiles = new FilenameFilter()
    {
      public boolean accept(File paramAnonymousFile, String paramAnonymousString)
      {
        return paramAnonymousString.startsWith("buffer");
      }
    };
    
    static void deleteAll(File paramFile)
    {
      paramFile = paramFile.listFiles(excludeNonBufferFiles());
      if (paramFile != null)
      {
        int j = paramFile.length;
        int i = 0;
        while (i < j)
        {
          paramFile[i].delete();
          i += 1;
        }
      }
    }
    
    static FilenameFilter excludeBufferFiles()
    {
      return filterExcludeBufferFiles;
    }
    
    static FilenameFilter excludeNonBufferFiles()
    {
      return filterExcludeNonBufferFiles;
    }
    
    static File newFile(File paramFile)
    {
      return new File(paramFile, "buffer" + Long.valueOf(FileLruCache.bufferIndex.incrementAndGet()).toString());
    }
  }
  
  private static class CloseCallbackOutputStream
    extends OutputStream
  {
    final FileLruCache.StreamCloseCallback callback;
    final OutputStream innerStream;
    
    CloseCallbackOutputStream(OutputStream paramOutputStream, FileLruCache.StreamCloseCallback paramStreamCloseCallback)
    {
      this.innerStream = paramOutputStream;
      this.callback = paramStreamCloseCallback;
    }
    
    public void close()
      throws IOException
    {
      try
      {
        this.innerStream.close();
        return;
      }
      finally
      {
        this.callback.onClose();
      }
    }
    
    public void flush()
      throws IOException
    {
      this.innerStream.flush();
    }
    
    public void write(int paramInt)
      throws IOException
    {
      this.innerStream.write(paramInt);
    }
    
    public void write(byte[] paramArrayOfByte)
      throws IOException
    {
      this.innerStream.write(paramArrayOfByte);
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      this.innerStream.write(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
  
  private static final class CopyingInputStream
    extends InputStream
  {
    final InputStream input;
    final OutputStream output;
    
    CopyingInputStream(InputStream paramInputStream, OutputStream paramOutputStream)
    {
      this.input = paramInputStream;
      this.output = paramOutputStream;
    }
    
    public int available()
      throws IOException
    {
      return this.input.available();
    }
    
    public void close()
      throws IOException
    {
      try
      {
        this.input.close();
        return;
      }
      finally
      {
        this.output.close();
      }
    }
    
    public void mark(int paramInt)
    {
      throw new UnsupportedOperationException();
    }
    
    public boolean markSupported()
    {
      return false;
    }
    
    public int read()
      throws IOException
    {
      int i = this.input.read();
      if (i >= 0) {
        this.output.write(i);
      }
      return i;
    }
    
    public int read(byte[] paramArrayOfByte)
      throws IOException
    {
      int i = this.input.read(paramArrayOfByte);
      if (i > 0) {
        this.output.write(paramArrayOfByte, 0, i);
      }
      return i;
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      paramInt2 = this.input.read(paramArrayOfByte, paramInt1, paramInt2);
      if (paramInt2 > 0) {
        this.output.write(paramArrayOfByte, paramInt1, paramInt2);
      }
      return paramInt2;
    }
    
    public void reset()
    {
      try
      {
        throw new UnsupportedOperationException();
      }
      finally {}
    }
    
    public long skip(long paramLong)
      throws IOException
    {
      byte[] arrayOfByte = new byte['Ѐ'];
      int i;
      for (long l = 0L;; l += i) {
        if (l < paramLong)
        {
          i = read(arrayOfByte, 0, (int)Math.min(paramLong - l, arrayOfByte.length));
          if (i >= 0) {}
        }
        else
        {
          return l;
        }
      }
    }
  }
  
  public static final class Limits
  {
    private int byteCount = 1048576;
    private int fileCount = 1024;
    
    int getByteCount()
    {
      return this.byteCount;
    }
    
    int getFileCount()
    {
      return this.fileCount;
    }
    
    void setByteCount(int paramInt)
    {
      if (paramInt < 0) {
        throw new InvalidParameterException("Cache byte-count limit must be >= 0");
      }
      this.byteCount = paramInt;
    }
    
    void setFileCount(int paramInt)
    {
      if (paramInt < 0) {
        throw new InvalidParameterException("Cache file count limit must be >= 0");
      }
      this.fileCount = paramInt;
    }
  }
  
  private static final class ModifiedFile
    implements Comparable<ModifiedFile>
  {
    private static final int HASH_MULTIPLIER = 37;
    private static final int HASH_SEED = 29;
    private final File file;
    private final long modified;
    
    ModifiedFile(File paramFile)
    {
      this.file = paramFile;
      this.modified = paramFile.lastModified();
    }
    
    public int compareTo(ModifiedFile paramModifiedFile)
    {
      if (getModified() < paramModifiedFile.getModified()) {
        return -1;
      }
      if (getModified() > paramModifiedFile.getModified()) {
        return 1;
      }
      return getFile().compareTo(paramModifiedFile.getFile());
    }
    
    public boolean equals(Object paramObject)
    {
      return ((paramObject instanceof ModifiedFile)) && (compareTo((ModifiedFile)paramObject) == 0);
    }
    
    File getFile()
    {
      return this.file;
    }
    
    long getModified()
    {
      return this.modified;
    }
    
    public int hashCode()
    {
      return (this.file.hashCode() + 1073) * 37 + (int)(this.modified % 2147483647L);
    }
  }
  
  private static abstract interface StreamCloseCallback
  {
    public abstract void onClose();
  }
  
  private static final class StreamHeader
  {
    private static final int HEADER_VERSION = 0;
    
    static JSONObject readHeader(InputStream paramInputStream)
      throws IOException
    {
      if (paramInputStream.read() != 0) {
        return null;
      }
      int j = 0;
      int i = 0;
      while (i < 3)
      {
        int k = paramInputStream.read();
        if (k == -1)
        {
          Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read returned -1 while reading header size");
          return null;
        }
        j = (j << 8) + (k & 0xFF);
        i += 1;
      }
      byte[] arrayOfByte = new byte[j];
      i = 0;
      while (i < arrayOfByte.length)
      {
        j = paramInputStream.read(arrayOfByte, i, arrayOfByte.length - i);
        if (j < 1)
        {
          Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read stopped at " + Integer.valueOf(i) + " when expected " + arrayOfByte.length);
          return null;
        }
        i += j;
      }
      paramInputStream = new JSONTokener(new String(arrayOfByte));
      try
      {
        paramInputStream = paramInputStream.nextValue();
        if (!(paramInputStream instanceof JSONObject))
        {
          Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: expected JSONObject, got " + paramInputStream.getClass().getCanonicalName());
          return null;
        }
        paramInputStream = (JSONObject)paramInputStream;
        return paramInputStream;
      }
      catch (JSONException paramInputStream)
      {
        throw new IOException(paramInputStream.getMessage());
      }
    }
    
    static void writeHeader(OutputStream paramOutputStream, JSONObject paramJSONObject)
      throws IOException
    {
      paramJSONObject = paramJSONObject.toString().getBytes();
      paramOutputStream.write(0);
      paramOutputStream.write(paramJSONObject.length >> 16 & 0xFF);
      paramOutputStream.write(paramJSONObject.length >> 8 & 0xFF);
      paramOutputStream.write(paramJSONObject.length >> 0 & 0xFF);
      paramOutputStream.write(paramJSONObject);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/FileLruCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.activeandroid;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.activeandroid.util.Log;
import com.activeandroid.util.SQLiteUtils;
import com.activeandroid.util.SqlParser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class DatabaseHelper
  extends SQLiteOpenHelper
{
  public static final String MIGRATION_PATH = "migrations";
  private final String mSqlParser;
  
  public DatabaseHelper(Configuration paramConfiguration)
  {
    super(paramConfiguration.getContext(), paramConfiguration.getDatabaseName(), null, paramConfiguration.getDatabaseVersion());
    copyAttachedDatabase(paramConfiguration.getContext(), paramConfiguration.getDatabaseName());
    this.mSqlParser = paramConfiguration.getSqlParser();
  }
  
  private void executeCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.beginTransaction();
    try
    {
      Iterator localIterator = Cache.getTableInfos().iterator();
      while (localIterator.hasNext()) {
        paramSQLiteDatabase.execSQL(SQLiteUtils.createTableDefinition((TableInfo)localIterator.next()));
      }
    }
    finally
    {
      paramSQLiteDatabase.endTransaction();
    }
    paramSQLiteDatabase.endTransaction();
  }
  
  private void executeCreateIndex(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.beginTransaction();
    try
    {
      Iterator localIterator = Cache.getTableInfos().iterator();
      while (localIterator.hasNext())
      {
        String[] arrayOfString = SQLiteUtils.createIndexDefinition((TableInfo)localIterator.next());
        int j = arrayOfString.length;
        int i = 0;
        while (i < j)
        {
          paramSQLiteDatabase.execSQL(arrayOfString[i]);
          i += 1;
        }
      }
      paramSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      paramSQLiteDatabase.endTransaction();
    }
  }
  
  private void executeDelimitedSqlScript(SQLiteDatabase paramSQLiteDatabase, InputStream paramInputStream)
    throws IOException
  {
    paramInputStream = SqlParser.parse(paramInputStream).iterator();
    while (paramInputStream.hasNext()) {
      paramSQLiteDatabase.execSQL((String)paramInputStream.next());
    }
  }
  
  /* Error */
  private void executeLegacySqlScript(SQLiteDatabase paramSQLiteDatabase, InputStream paramInputStream)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore_3
    //   5: new 110	java/io/InputStreamReader
    //   8: dup
    //   9: aload_2
    //   10: invokespecial 113	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   13: astore_2
    //   14: new 115	java/io/BufferedReader
    //   17: dup
    //   18: aload_2
    //   19: invokespecial 118	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   22: astore 4
    //   24: aload 4
    //   26: invokevirtual 121	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   29: astore_3
    //   30: aload_3
    //   31: ifnull +50 -> 81
    //   34: aload_3
    //   35: ldc 123
    //   37: ldc 125
    //   39: invokevirtual 129	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   42: invokevirtual 132	java/lang/String:trim	()Ljava/lang/String;
    //   45: astore_3
    //   46: aload_3
    //   47: invokestatic 138	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   50: ifne -26 -> 24
    //   53: aload_1
    //   54: aload_3
    //   55: invokevirtual 80	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   58: goto -34 -> 24
    //   61: astore 5
    //   63: aload 4
    //   65: astore_3
    //   66: aload_2
    //   67: astore_1
    //   68: aload 5
    //   70: astore_2
    //   71: aload_3
    //   72: invokestatic 144	com/activeandroid/util/IOUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   75: aload_1
    //   76: invokestatic 144	com/activeandroid/util/IOUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   79: aload_2
    //   80: athrow
    //   81: aload 4
    //   83: invokestatic 144	com/activeandroid/util/IOUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   86: aload_2
    //   87: invokestatic 144	com/activeandroid/util/IOUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   90: return
    //   91: astore_2
    //   92: aload 4
    //   94: astore_1
    //   95: goto -24 -> 71
    //   98: astore 4
    //   100: aload_2
    //   101: astore_1
    //   102: aload 4
    //   104: astore_2
    //   105: goto -34 -> 71
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	108	0	this	DatabaseHelper
    //   0	108	1	paramSQLiteDatabase	SQLiteDatabase
    //   0	108	2	paramInputStream	InputStream
    //   4	68	3	localObject1	Object
    //   1	92	4	localBufferedReader	java.io.BufferedReader
    //   98	5	4	localObject2	Object
    //   61	8	5	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   24	30	61	finally
    //   34	58	61	finally
    //   5	14	91	finally
    //   14	24	98	finally
  }
  
  /* Error */
  private boolean executeMigrations(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 5
    //   3: iconst_0
    //   4: istore 8
    //   6: iconst_0
    //   7: istore 6
    //   9: iload 8
    //   11: istore 7
    //   13: invokestatic 149	com/activeandroid/Cache:getContext	()Landroid/content/Context;
    //   16: invokevirtual 155	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   19: ldc 8
    //   21: invokevirtual 161	android/content/res/AssetManager:list	(Ljava/lang/String;)[Ljava/lang/String;
    //   24: invokestatic 167	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
    //   27: astore 9
    //   29: iload 8
    //   31: istore 7
    //   33: aload 9
    //   35: new 169	com/activeandroid/util/NaturalOrderComparator
    //   38: dup
    //   39: invokespecial 171	com/activeandroid/util/NaturalOrderComparator:<init>	()V
    //   42: invokestatic 177	java/util/Collections:sort	(Ljava/util/List;Ljava/util/Comparator;)V
    //   45: iload 8
    //   47: istore 7
    //   49: aload_1
    //   50: invokevirtual 46	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   53: aload 9
    //   55: invokeinterface 104 1 0
    //   60: astore 9
    //   62: iload 6
    //   64: istore 5
    //   66: aload 9
    //   68: invokeinterface 64 1 0
    //   73: ifeq +174 -> 247
    //   76: iload 6
    //   78: istore 5
    //   80: aload 9
    //   82: invokeinterface 68 1 0
    //   87: checkcast 106	java/lang/String
    //   90: astore 10
    //   92: iload 6
    //   94: istore 7
    //   96: iload 6
    //   98: istore 5
    //   100: aload 10
    //   102: ldc -77
    //   104: ldc 125
    //   106: invokevirtual 129	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   109: invokestatic 185	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   112: invokevirtual 188	java/lang/Integer:intValue	()I
    //   115: istore 4
    //   117: iload 4
    //   119: iload_2
    //   120: if_icmple -58 -> 62
    //   123: iload 4
    //   125: iload_3
    //   126: if_icmpgt -64 -> 62
    //   129: iload 6
    //   131: istore 7
    //   133: iload 6
    //   135: istore 5
    //   137: aload_0
    //   138: aload_1
    //   139: aload 10
    //   141: invokespecial 192	com/activeandroid/DatabaseHelper:executeSqlScript	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
    //   144: iconst_1
    //   145: istore 7
    //   147: iconst_1
    //   148: istore 5
    //   150: iconst_1
    //   151: istore 6
    //   153: new 194	java/lang/StringBuilder
    //   156: dup
    //   157: invokespecial 195	java/lang/StringBuilder:<init>	()V
    //   160: aload 10
    //   162: invokevirtual 199	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: ldc -55
    //   167: invokevirtual 199	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   170: invokevirtual 204	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   173: invokestatic 210	com/activeandroid/util/Log:i	(Ljava/lang/String;)I
    //   176: pop
    //   177: goto -115 -> 62
    //   180: astore 11
    //   182: iload 7
    //   184: istore 5
    //   186: new 194	java/lang/StringBuilder
    //   189: dup
    //   190: invokespecial 195	java/lang/StringBuilder:<init>	()V
    //   193: ldc -44
    //   195: invokevirtual 199	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: aload 10
    //   200: invokevirtual 199	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: invokevirtual 204	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   206: aload 11
    //   208: invokestatic 216	com/activeandroid/util/Log:w	(Ljava/lang/String;Ljava/lang/Throwable;)I
    //   211: pop
    //   212: iload 7
    //   214: istore 6
    //   216: goto -154 -> 62
    //   219: astore 9
    //   221: iload 5
    //   223: istore 7
    //   225: aload_1
    //   226: invokevirtual 83	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   229: iload 5
    //   231: istore 7
    //   233: aload 9
    //   235: athrow
    //   236: astore_1
    //   237: ldc -38
    //   239: aload_1
    //   240: invokestatic 221	com/activeandroid/util/Log:e	(Ljava/lang/String;Ljava/lang/Throwable;)I
    //   243: pop
    //   244: iload 7
    //   246: ireturn
    //   247: iload 6
    //   249: istore 5
    //   251: aload_1
    //   252: invokevirtual 86	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   255: iload 6
    //   257: istore 7
    //   259: aload_1
    //   260: invokevirtual 83	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   263: iload 6
    //   265: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	266	0	this	DatabaseHelper
    //   0	266	1	paramSQLiteDatabase	SQLiteDatabase
    //   0	266	2	paramInt1	int
    //   0	266	3	paramInt2	int
    //   115	12	4	i	int
    //   1	249	5	bool1	boolean
    //   7	257	6	bool2	boolean
    //   11	247	7	bool3	boolean
    //   4	42	8	bool4	boolean
    //   27	54	9	localObject1	Object
    //   219	15	9	localObject2	Object
    //   90	109	10	str	String
    //   180	27	11	localNumberFormatException	NumberFormatException
    // Exception table:
    //   from	to	target	type
    //   100	117	180	java/lang/NumberFormatException
    //   137	144	180	java/lang/NumberFormatException
    //   153	177	180	java/lang/NumberFormatException
    //   53	62	219	finally
    //   66	76	219	finally
    //   80	92	219	finally
    //   100	117	219	finally
    //   137	144	219	finally
    //   153	177	219	finally
    //   186	212	219	finally
    //   251	255	219	finally
    //   13	29	236	java/io/IOException
    //   33	45	236	java/io/IOException
    //   49	53	236	java/io/IOException
    //   225	229	236	java/io/IOException
    //   233	236	236	java/io/IOException
    //   259	263	236	java/io/IOException
  }
  
  private void executePragmas(SQLiteDatabase paramSQLiteDatabase)
  {
    if (SQLiteUtils.FOREIGN_KEYS_SUPPORTED)
    {
      paramSQLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
      Log.i("Foreign Keys supported. Enabling foreign key features.");
    }
  }
  
  /* Error */
  private void executeSqlScript(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore_3
    //   5: invokestatic 149	com/activeandroid/Cache:getContext	()Landroid/content/Context;
    //   8: invokevirtual 155	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   11: new 194	java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial 195	java/lang/StringBuilder:<init>	()V
    //   18: ldc -24
    //   20: invokevirtual 199	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: aload_2
    //   24: invokevirtual 199	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: invokevirtual 204	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   30: invokevirtual 236	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   33: astore 5
    //   35: aload 5
    //   37: astore_3
    //   38: aload 5
    //   40: astore 4
    //   42: ldc -18
    //   44: aload_0
    //   45: getfield 37	com/activeandroid/DatabaseHelper:mSqlParser	Ljava/lang/String;
    //   48: invokevirtual 242	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   51: ifeq +23 -> 74
    //   54: aload 5
    //   56: astore_3
    //   57: aload 5
    //   59: astore 4
    //   61: aload_0
    //   62: aload_1
    //   63: aload 5
    //   65: invokespecial 244	com/activeandroid/DatabaseHelper:executeDelimitedSqlScript	(Landroid/database/sqlite/SQLiteDatabase;Ljava/io/InputStream;)V
    //   68: aload 5
    //   70: invokestatic 144	com/activeandroid/util/IOUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   73: return
    //   74: aload 5
    //   76: astore_3
    //   77: aload 5
    //   79: astore 4
    //   81: aload_0
    //   82: aload_1
    //   83: aload 5
    //   85: invokespecial 246	com/activeandroid/DatabaseHelper:executeLegacySqlScript	(Landroid/database/sqlite/SQLiteDatabase;Ljava/io/InputStream;)V
    //   88: goto -20 -> 68
    //   91: astore_1
    //   92: aload_3
    //   93: astore 4
    //   95: new 194	java/lang/StringBuilder
    //   98: dup
    //   99: invokespecial 195	java/lang/StringBuilder:<init>	()V
    //   102: ldc -8
    //   104: invokevirtual 199	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: aload_2
    //   108: invokevirtual 199	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: invokevirtual 204	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   114: aload_1
    //   115: invokestatic 221	com/activeandroid/util/Log:e	(Ljava/lang/String;Ljava/lang/Throwable;)I
    //   118: pop
    //   119: aload_3
    //   120: invokestatic 144	com/activeandroid/util/IOUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   123: return
    //   124: astore_1
    //   125: aload 4
    //   127: invokestatic 144	com/activeandroid/util/IOUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   130: aload_1
    //   131: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	132	0	this	DatabaseHelper
    //   0	132	1	paramSQLiteDatabase	SQLiteDatabase
    //   0	132	2	paramString	String
    //   4	116	3	localObject1	Object
    //   1	125	4	localObject2	Object
    //   33	51	5	localInputStream	InputStream
    // Exception table:
    //   from	to	target	type
    //   5	35	91	java/io/IOException
    //   42	54	91	java/io/IOException
    //   61	68	91	java/io/IOException
    //   81	88	91	java/io/IOException
    //   5	35	124	finally
    //   42	54	124	finally
    //   61	68	124	finally
    //   81	88	124	finally
    //   95	119	124	finally
  }
  
  public void copyAttachedDatabase(Context paramContext, String paramString)
  {
    Object localObject = paramContext.getDatabasePath(paramString);
    if (((File)localObject).exists()) {
      return;
    }
    ((File)localObject).getParentFile().mkdirs();
    try
    {
      paramContext = paramContext.getAssets().open(paramString);
      paramString = new FileOutputStream((File)localObject);
      localObject = new byte[' '];
      for (;;)
      {
        int i = paramContext.read((byte[])localObject, 0, 8192);
        if (i <= 0) {
          break;
        }
        paramString.write((byte[])localObject, 0, i);
      }
      paramString.flush();
    }
    catch (IOException paramContext)
    {
      Log.e("Failed to open file", paramContext);
      return;
    }
    paramString.close();
    paramContext.close();
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    executePragmas(paramSQLiteDatabase);
    executeCreate(paramSQLiteDatabase);
    executeMigrations(paramSQLiteDatabase, -1, paramSQLiteDatabase.getVersion());
    executeCreateIndex(paramSQLiteDatabase);
  }
  
  public void onOpen(SQLiteDatabase paramSQLiteDatabase)
  {
    executePragmas(paramSQLiteDatabase);
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    executePragmas(paramSQLiteDatabase);
    executeCreate(paramSQLiteDatabase);
    executeMigrations(paramSQLiteDatabase, paramInt1, paramInt2);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/DatabaseHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
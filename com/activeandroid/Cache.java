package com.activeandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.LruCache;
import com.activeandroid.serializer.TypeSerializer;
import com.activeandroid.util.Log;
import java.util.Collection;

public final class Cache
{
  public static final int DEFAULT_CACHE_SIZE = 1024;
  private static Context sContext;
  private static DatabaseHelper sDatabaseHelper;
  private static LruCache<String, Model> sEntities;
  private static boolean sIsInitialized = false;
  private static ModelInfo sModelInfo;
  
  public static void addEntity(Model paramModel)
  {
    try
    {
      sEntities.put(getIdentifier(paramModel), paramModel);
      return;
    }
    finally
    {
      paramModel = finally;
      throw paramModel;
    }
  }
  
  public static void clear()
  {
    try
    {
      sEntities.evictAll();
      Log.v("Cache cleared.");
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static void closeDatabase()
  {
    try
    {
      sDatabaseHelper.close();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static void dispose()
  {
    try
    {
      closeDatabase();
      sEntities = null;
      sModelInfo = null;
      sDatabaseHelper = null;
      sIsInitialized = false;
      Log.v("ActiveAndroid disposed. Call initialize to use library.");
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static Context getContext()
  {
    return sContext;
  }
  
  public static Model getEntity(Class<? extends Model> paramClass, long paramLong)
  {
    try
    {
      paramClass = (Model)sEntities.get(getIdentifier(paramClass, Long.valueOf(paramLong)));
      return paramClass;
    }
    finally
    {
      paramClass = finally;
      throw paramClass;
    }
  }
  
  public static String getIdentifier(Model paramModel)
  {
    return getIdentifier(paramModel.getClass(), paramModel.getId());
  }
  
  public static String getIdentifier(Class<? extends Model> paramClass, Long paramLong)
  {
    return getTableName(paramClass) + "@" + paramLong;
  }
  
  public static TypeSerializer getParserForType(Class<?> paramClass)
  {
    try
    {
      paramClass = sModelInfo.getTypeSerializer(paramClass);
      return paramClass;
    }
    finally
    {
      paramClass = finally;
      throw paramClass;
    }
  }
  
  public static TableInfo getTableInfo(Class<? extends Model> paramClass)
  {
    try
    {
      paramClass = sModelInfo.getTableInfo(paramClass);
      return paramClass;
    }
    finally
    {
      paramClass = finally;
      throw paramClass;
    }
  }
  
  public static Collection<TableInfo> getTableInfos()
  {
    try
    {
      Collection localCollection = sModelInfo.getTableInfos();
      return localCollection;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static String getTableName(Class<? extends Model> paramClass)
  {
    try
    {
      paramClass = sModelInfo.getTableInfo(paramClass).getTableName();
      return paramClass;
    }
    finally
    {
      paramClass = finally;
      throw paramClass;
    }
  }
  
  /* Error */
  public static void initialize(Configuration paramConfiguration)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 22	com/activeandroid/Cache:sIsInitialized	Z
    //   6: ifeq +13 -> 19
    //   9: ldc -110
    //   11: invokestatic 52	com/activeandroid/util/Log:v	(Ljava/lang/String;)I
    //   14: pop
    //   15: ldc 2
    //   17: monitorexit
    //   18: return
    //   19: aload_0
    //   20: invokevirtual 150	com/activeandroid/Configuration:getContext	()Landroid/content/Context;
    //   23: putstatic 71	com/activeandroid/Cache:sContext	Landroid/content/Context;
    //   26: new 123	com/activeandroid/ModelInfo
    //   29: dup
    //   30: aload_0
    //   31: invokespecial 152	com/activeandroid/ModelInfo:<init>	(Lcom/activeandroid/Configuration;)V
    //   34: putstatic 65	com/activeandroid/Cache:sModelInfo	Lcom/activeandroid/ModelInfo;
    //   37: new 57	com/activeandroid/DatabaseHelper
    //   40: dup
    //   41: aload_0
    //   42: invokespecial 153	com/activeandroid/DatabaseHelper:<init>	(Lcom/activeandroid/Configuration;)V
    //   45: putstatic 55	com/activeandroid/Cache:sDatabaseHelper	Lcom/activeandroid/DatabaseHelper;
    //   48: new 36	android/support/v4/util/LruCache
    //   51: dup
    //   52: aload_0
    //   53: invokevirtual 157	com/activeandroid/Configuration:getCacheSize	()I
    //   56: invokespecial 160	android/support/v4/util/LruCache:<init>	(I)V
    //   59: putstatic 30	com/activeandroid/Cache:sEntities	Landroid/support/v4/util/LruCache;
    //   62: invokestatic 164	com/activeandroid/Cache:openDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   65: pop
    //   66: iconst_1
    //   67: putstatic 22	com/activeandroid/Cache:sIsInitialized	Z
    //   70: ldc -90
    //   72: invokestatic 52	com/activeandroid/util/Log:v	(Ljava/lang/String;)I
    //   75: pop
    //   76: goto -61 -> 15
    //   79: astore_0
    //   80: ldc 2
    //   82: monitorexit
    //   83: aload_0
    //   84: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	85	0	paramConfiguration	Configuration
    // Exception table:
    //   from	to	target	type
    //   3	15	79	finally
    //   19	76	79	finally
  }
  
  public static boolean isInitialized()
  {
    return sIsInitialized;
  }
  
  public static SQLiteDatabase openDatabase()
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = sDatabaseHelper.getWritableDatabase();
      return localSQLiteDatabase;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static void removeEntity(Model paramModel)
  {
    try
    {
      sEntities.remove(getIdentifier(paramModel));
      return;
    }
    finally
    {
      paramModel = finally;
      throw paramModel;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/Cache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
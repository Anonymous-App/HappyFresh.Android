package com.activeandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.activeandroid.util.Log;

public final class ActiveAndroid
{
  public static void beginTransaction()
  {
    Cache.openDatabase().beginTransaction();
  }
  
  public static void clearCache() {}
  
  public static void dispose() {}
  
  public static void endTransaction()
  {
    Cache.openDatabase().endTransaction();
  }
  
  public static void execSQL(String paramString)
  {
    Cache.openDatabase().execSQL(paramString);
  }
  
  public static void execSQL(String paramString, Object[] paramArrayOfObject)
  {
    Cache.openDatabase().execSQL(paramString, paramArrayOfObject);
  }
  
  public static SQLiteDatabase getDatabase()
  {
    return Cache.openDatabase();
  }
  
  public static boolean inTransaction()
  {
    return Cache.openDatabase().inTransaction();
  }
  
  public static void initialize(Context paramContext)
  {
    initialize(new Configuration.Builder(paramContext).create());
  }
  
  public static void initialize(Context paramContext, boolean paramBoolean)
  {
    initialize(new Configuration.Builder(paramContext).create(), paramBoolean);
  }
  
  public static void initialize(Configuration paramConfiguration)
  {
    initialize(paramConfiguration, false);
  }
  
  public static void initialize(Configuration paramConfiguration, boolean paramBoolean)
  {
    setLoggingEnabled(paramBoolean);
    Cache.initialize(paramConfiguration);
  }
  
  public static void setLoggingEnabled(boolean paramBoolean)
  {
    Log.setEnabled(paramBoolean);
  }
  
  public static void setTransactionSuccessful()
  {
    Cache.openDatabase().setTransactionSuccessful();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/ActiveAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
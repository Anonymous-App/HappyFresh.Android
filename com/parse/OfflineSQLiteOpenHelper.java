package com.parse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

class OfflineSQLiteOpenHelper
  extends ParseSQLiteOpenHelper
{
  private static final String DATABASE_NAME = "ParseOfflineStore";
  private static final int DATABASE_VERSION = 4;
  static final String KEY_CLASS_NAME = "className";
  static final String KEY_IS_DELETING_EVENTUALLY = "isDeletingEventually";
  static final String KEY_JSON = "json";
  static final String KEY_KEY = "key";
  static final String KEY_OBJECT_ID = "objectId";
  static final String KEY_UUID = "uuid";
  static final String TABLE_DEPENDENCIES = "Dependencies";
  static final String TABLE_OBJECTS = "ParseObjects";
  
  public OfflineSQLiteOpenHelper(Context paramContext)
  {
    super(paramContext, "ParseOfflineStore", null, 4);
  }
  
  private void createSchema(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("CREATE TABLE ParseObjects (uuid TEXT PRIMARY KEY, className TEXT NOT NULL, objectId TEXT, json TEXT, isDeletingEventually INTEGER DEFAULT 0, UNIQUE(className, objectId));");
    paramSQLiteDatabase.execSQL("CREATE TABLE Dependencies (key TEXT NOT NULL, uuid TEXT NOT NULL, PRIMARY KEY(key, uuid));");
  }
  
  public void clearDatabase(Context paramContext)
  {
    paramContext.deleteDatabase("ParseOfflineStore");
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    createSchema(paramSQLiteDatabase);
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/OfflineSQLiteOpenHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
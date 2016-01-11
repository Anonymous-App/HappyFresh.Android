package com.mixpanel.android.mpmetrics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class MPDbAdapter
{
  private static final String CREATE_EVENTS_TABLE = "CREATE TABLE " + Table.EVENTS.getName() + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "data" + " STRING NOT NULL, " + "created_at" + " INTEGER NOT NULL);";
  private static final String CREATE_PEOPLE_TABLE = "CREATE TABLE " + Table.PEOPLE.getName() + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "data" + " STRING NOT NULL, " + "created_at" + " INTEGER NOT NULL);";
  private static final String DATABASE_NAME = "mixpanel";
  private static final int DATABASE_VERSION = 4;
  public static final int DB_OUT_OF_MEMORY_ERROR = -2;
  public static final int DB_UNDEFINED_CODE = -3;
  public static final int DB_UPDATE_ERROR = -1;
  private static final String EVENTS_TIME_INDEX = "CREATE INDEX IF NOT EXISTS time_idx ON " + Table.EVENTS.getName() + " (" + "created_at" + ");";
  public static final String KEY_CREATED_AT = "created_at";
  public static final String KEY_DATA = "data";
  private static final String LOGTAG = "MixpanelAPI.Database";
  private static final String PEOPLE_TIME_INDEX = "CREATE INDEX IF NOT EXISTS time_idx ON " + Table.PEOPLE.getName() + " (" + "created_at" + ");";
  private final MPDatabaseHelper mDb;
  
  public MPDbAdapter(Context paramContext)
  {
    this(paramContext, "mixpanel");
  }
  
  public MPDbAdapter(Context paramContext, String paramString)
  {
    this.mDb = new MPDatabaseHelper(paramContext, paramString);
  }
  
  public int addJSON(JSONObject paramJSONObject, Table paramTable)
  {
    if (!belowMemThreshold())
    {
      Log.e("MixpanelAPI.Database", "There is not enough space left on the device to store Mixpanel data, so data was discarded");
      return -2;
    }
    String str = paramTable.getName();
    Object localObject3 = null;
    Object localObject2 = null;
    Object localObject1 = localObject2;
    paramTable = (Table)localObject3;
    try
    {
      SQLiteDatabase localSQLiteDatabase = this.mDb.getWritableDatabase();
      localObject1 = localObject2;
      paramTable = (Table)localObject3;
      ContentValues localContentValues = new ContentValues();
      localObject1 = localObject2;
      paramTable = (Table)localObject3;
      localContentValues.put("data", paramJSONObject.toString());
      localObject1 = localObject2;
      paramTable = (Table)localObject3;
      localContentValues.put("created_at", Long.valueOf(System.currentTimeMillis()));
      localObject1 = localObject2;
      paramTable = (Table)localObject3;
      localSQLiteDatabase.insert(str, null, localContentValues);
      localObject1 = localObject2;
      paramTable = (Table)localObject3;
      paramJSONObject = localSQLiteDatabase.rawQuery("SELECT COUNT(*) FROM " + str, null);
      localObject1 = paramJSONObject;
      paramTable = paramJSONObject;
      paramJSONObject.moveToFirst();
      localObject1 = paramJSONObject;
      paramTable = paramJSONObject;
      int i = paramJSONObject.getInt(0);
      return i;
    }
    catch (SQLiteException paramJSONObject)
    {
      paramTable = (Table)localObject1;
      Log.e("MixpanelAPI.Database", "Could not add Mixpanel data to table " + str + ". Re-initializing database.", paramJSONObject);
      paramJSONObject = (JSONObject)localObject1;
      if (localObject1 != null)
      {
        paramTable = (Table)localObject1;
        ((Cursor)localObject1).close();
        paramJSONObject = null;
      }
      paramTable = paramJSONObject;
      this.mDb.deleteDatabase();
      return -1;
    }
    finally
    {
      if (paramTable != null) {
        paramTable.close();
      }
      this.mDb.close();
    }
  }
  
  protected boolean belowMemThreshold()
  {
    return this.mDb.belowMemThreshold();
  }
  
  public void cleanupEvents(long paramLong, Table paramTable)
  {
    paramTable = paramTable.getName();
    try
    {
      this.mDb.getWritableDatabase().delete(paramTable, "created_at <= " + paramLong, null);
      return;
    }
    catch (SQLiteException localSQLiteException)
    {
      Log.e("MixpanelAPI.Database", "Could not clean timed-out Mixpanel records from " + paramTable + ". Re-initializing database.", localSQLiteException);
      this.mDb.deleteDatabase();
      return;
    }
    finally
    {
      this.mDb.close();
    }
  }
  
  public void cleanupEvents(String paramString, Table paramTable)
  {
    paramTable = paramTable.getName();
    try
    {
      this.mDb.getWritableDatabase().delete(paramTable, "_id <= " + paramString, null);
      return;
    }
    catch (SQLiteException paramString)
    {
      Log.e("MixpanelAPI.Database", "Could not clean sent Mixpanel records from " + paramTable + ". Re-initializing database.", paramString);
      this.mDb.deleteDatabase();
      return;
    }
    finally
    {
      this.mDb.close();
    }
  }
  
  public void deleteDB()
  {
    this.mDb.deleteDatabase();
  }
  
  public String[] generateDataString(Table paramTable)
  {
    Object localObject4 = null;
    localObject1 = null;
    Cursor localCursor = null;
    str2 = null;
    String str1 = null;
    localObject3 = paramTable.getName();
    paramTable = localCursor;
    try
    {
      localCursor = this.mDb.getReadableDatabase().rawQuery("SELECT * FROM " + (String)localObject3 + " ORDER BY " + "created_at" + " ASC LIMIT 50", null);
      paramTable = localCursor;
      localObject1 = localCursor;
      JSONArray localJSONArray = new JSONArray();
      for (;;)
      {
        paramTable = localCursor;
        localObject1 = localCursor;
        if (!localCursor.moveToNext()) {
          break;
        }
        paramTable = localCursor;
        localObject1 = localCursor;
        if (localCursor.isLast())
        {
          paramTable = localCursor;
          localObject1 = localCursor;
          str1 = localCursor.getString(localCursor.getColumnIndex("_id"));
        }
        paramTable = localCursor;
        localObject1 = localCursor;
        try
        {
          localJSONArray.put(new JSONObject(localCursor.getString(localCursor.getColumnIndex("data"))));
        }
        catch (JSONException paramTable) {}
      }
      paramTable = localCursor;
      localObject1 = localCursor;
      if (localJSONArray.length() > 0)
      {
        paramTable = localCursor;
        localObject1 = localCursor;
        str2 = localJSONArray.toString();
      }
      this.mDb.close();
      localObject1 = str2;
      localObject3 = str1;
      if (localCursor != null)
      {
        localCursor.close();
        localObject3 = str1;
        localObject1 = str2;
      }
    }
    catch (SQLiteException localSQLiteException)
    {
      for (;;)
      {
        localObject1 = paramTable;
        Log.e("MixpanelAPI.Database", "Could not pull records for Mixpanel out of database " + (String)localObject3 + ". Waiting to send.", localSQLiteException);
        Object localObject2 = null;
        str2 = null;
        this.mDb.close();
        localObject1 = str2;
        localObject3 = localObject2;
        if (paramTable != null)
        {
          paramTable.close();
          localObject1 = str2;
          localObject3 = localObject2;
        }
      }
    }
    finally
    {
      this.mDb.close();
      if (localObject1 == null) {
        break label361;
      }
      ((Cursor)localObject1).close();
    }
    paramTable = (Table)localObject4;
    if (localObject3 != null)
    {
      paramTable = (Table)localObject4;
      if (localObject1 != null)
      {
        paramTable = new String[2];
        paramTable[0] = localObject3;
        paramTable[1] = localObject1;
      }
    }
    return paramTable;
  }
  
  private static class MPDatabaseHelper
    extends SQLiteOpenHelper
  {
    private final MPConfig mConfig;
    private final File mDatabaseFile;
    
    MPDatabaseHelper(Context paramContext, String paramString)
    {
      super(paramString, null, 4);
      this.mDatabaseFile = paramContext.getDatabasePath(paramString);
      this.mConfig = MPConfig.getInstance(paramContext);
    }
    
    public boolean belowMemThreshold()
    {
      return (!this.mDatabaseFile.exists()) || (Math.max(this.mDatabaseFile.getUsableSpace(), this.mConfig.getMinimumDatabaseLimit()) >= this.mDatabaseFile.length());
    }
    
    public void deleteDatabase()
    {
      close();
      this.mDatabaseFile.delete();
    }
    
    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      if (MPConfig.DEBUG) {
        Log.v("MixpanelAPI.Database", "Creating a new Mixpanel events DB");
      }
      paramSQLiteDatabase.execSQL(MPDbAdapter.CREATE_EVENTS_TABLE);
      paramSQLiteDatabase.execSQL(MPDbAdapter.CREATE_PEOPLE_TABLE);
      paramSQLiteDatabase.execSQL(MPDbAdapter.EVENTS_TIME_INDEX);
      paramSQLiteDatabase.execSQL(MPDbAdapter.PEOPLE_TIME_INDEX);
    }
    
    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      if (MPConfig.DEBUG) {
        Log.v("MixpanelAPI.Database", "Upgrading app, replacing Mixpanel events DB");
      }
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MPDbAdapter.Table.EVENTS.getName());
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MPDbAdapter.Table.PEOPLE.getName());
      paramSQLiteDatabase.execSQL(MPDbAdapter.CREATE_EVENTS_TABLE);
      paramSQLiteDatabase.execSQL(MPDbAdapter.CREATE_PEOPLE_TABLE);
      paramSQLiteDatabase.execSQL(MPDbAdapter.EVENTS_TIME_INDEX);
      paramSQLiteDatabase.execSQL(MPDbAdapter.PEOPLE_TIME_INDEX);
    }
  }
  
  public static enum Table
  {
    EVENTS("events"),  PEOPLE("people");
    
    private final String mTableName;
    
    private Table(String paramString)
    {
      this.mTableName = paramString;
    }
    
    public String getName()
    {
      return this.mTableName;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/mpmetrics/MPDbAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
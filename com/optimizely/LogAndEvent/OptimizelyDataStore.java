package com.optimizely.LogAndEvent;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import com.optimizely.Optimizely;
import java.util.ArrayList;
import java.util.List;

public class OptimizelyDataStore
  extends SQLiteOpenHelper
{
  private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS %s (id INTEGER PRIMARY KEY AUTOINCREMENT, json TEXT,timestamp INTEGER)";
  public static final String DATABASE_NAME = "OptimizelyDB";
  private static final int DATABASE_VERSION = 1;
  private static final String DATA_COMPONENT_NAME = "OptimizelyDataStore";
  public static final String DB_THREAD_NAME = "OptimizelyDbThread";
  private static final String EVENT_TABLE_NAME = "optimizely_events";
  private static final String LOG_TABLE_NAME = "optimizely_logs";
  private static final String TIME_SERIES_EVENTS_TABLE_NAME = "optimizely_time_series_events";
  @NonNull
  private DatabaseRunner databaseRunner = new DatabaseRunner(paramOptimizely, this);
  
  public OptimizelyDataStore(@NonNull Optimizely paramOptimizely)
  {
    this(paramOptimizely, true);
  }
  
  public OptimizelyDataStore(@NonNull Optimizely paramOptimizely, boolean paramBoolean)
  {
    super(paramOptimizely.getCurrentContext(), "OptimizelyDB", null, 1);
    if (paramBoolean) {
      this.databaseRunner.start();
    }
  }
  
  @NonNull
  private String createTableQuery(String paramString)
  {
    return String.format("CREATE TABLE IF NOT EXISTS %s (id INTEGER PRIMARY KEY AUTOINCREMENT, json TEXT,timestamp INTEGER)", new Object[] { paramString });
  }
  
  @NonNull
  private Result<Integer> delete(final String paramString, @Nullable final List<Long> paramList)
  {
    final Result localResult = new Result();
    final String str = null;
    if (paramList != null) {
      if (paramList.size() != 1) {
        break label84;
      }
    }
    label84:
    for (str = "id = " + paramList.get(0);; str = String.format("id IN(%s)", new Object[] { TextUtils.join(", ", paramList) }))
    {
      if (!this.databaseRunner.post(new Runnable()
      {
        public void run()
        {
          SQLiteDatabase localSQLiteDatabase = OptimizelyDataStore.this.getWritableDatabase();
          int i = localSQLiteDatabase.delete(paramString, str, null);
          Result localResult = localResult;
          if ((paramList == null) || (i == paramList.size())) {}
          for (boolean bool = true;; bool = false)
          {
            localResult.resolve(bool, Integer.valueOf(i));
            localSQLiteDatabase.close();
            return;
          }
        }
      })) {
        localResult.cancel(true);
      }
      return localResult;
    }
  }
  
  @NonNull
  private Result<ArrayList<Pair<Long, String>>> getPending(@NonNull final String paramString)
  {
    final Result localResult = new Result();
    if (!this.databaseRunner.post(new Runnable()
    {
      public void run()
      {
        ArrayList localArrayList = new ArrayList();
        SQLiteDatabase localSQLiteDatabase = OptimizelyDataStore.this.getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.query(paramString, null, null, null, null, null, null);
        while ((localCursor != null) && (localCursor.moveToNext())) {
          localArrayList.add(new Pair(Long.valueOf(localCursor.getLong(localCursor.getColumnIndex("id"))), localCursor.getString(localCursor.getColumnIndex("json"))));
        }
        if (localCursor != null) {
          localCursor.close();
        }
        localSQLiteDatabase.close();
        localResult.resolve(true, localArrayList);
      }
    })) {
      localResult.cancel(true);
    }
    return localResult;
  }
  
  @NonNull
  private Result<Long> store(@Nullable final String paramString1, final String paramString2)
  {
    final Result localResult = new Result();
    if (paramString1 != null)
    {
      if (!this.databaseRunner.post(new Runnable()
      {
        public void run()
        {
          Object localObject = new ContentValues();
          ((ContentValues)localObject).put("json", paramString1);
          ((ContentValues)localObject).put("timestamp", Long.valueOf(System.currentTimeMillis()));
          SQLiteDatabase localSQLiteDatabase = OptimizelyDataStore.this.getWritableDatabase();
          long l = localSQLiteDatabase.insert(paramString2, null, (ContentValues)localObject);
          localSQLiteDatabase.close();
          localObject = localResult;
          if (l != -1L) {}
          for (boolean bool = true;; bool = false)
          {
            ((Result)localObject).resolve(bool, Long.valueOf(l));
            return;
          }
        }
      })) {
        localResult.cancel(true);
      }
      return localResult;
    }
    localResult.cancel(true);
    return localResult;
  }
  
  @NonNull
  public Result<Integer> deleteEvents(List<Long> paramList)
  {
    return delete("optimizely_events", paramList);
  }
  
  @NonNull
  public Result<Integer> deleteLogs(List<Long> paramList)
  {
    return delete("optimizely_logs", paramList);
  }
  
  @NonNull
  public Result<Integer> deleteTimeSeriesEvents(List<Long> paramList)
  {
    return delete("optimizely_time_series_events", paramList);
  }
  
  @NonNull
  public DatabaseRunner getDatabaseRunner()
  {
    return this.databaseRunner;
  }
  
  @NonNull
  public Result<ArrayList<Pair<Long, String>>> getPendingEvents()
  {
    return getPending("optimizely_events");
  }
  
  @NonNull
  public Result<ArrayList<Pair<Long, String>>> getPendingLogs()
  {
    return getPending("optimizely_logs");
  }
  
  @NonNull
  public Result<ArrayList<Pair<Long, String>>> getPendingTimeSeriesEvents()
  {
    return getPending("optimizely_time_series_events");
  }
  
  public void onCreate(@NonNull SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL(createTableQuery("optimizely_events"));
    paramSQLiteDatabase.execSQL(createTableQuery("optimizely_logs"));
    paramSQLiteDatabase.execSQL(createTableQuery("optimizely_time_series_events"));
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}
  
  public void setDatabaseRunner(@NonNull DatabaseRunner paramDatabaseRunner)
  {
    setDatabaseRunner(paramDatabaseRunner, true);
  }
  
  public void setDatabaseRunner(@NonNull DatabaseRunner paramDatabaseRunner, boolean paramBoolean)
  {
    this.databaseRunner = paramDatabaseRunner;
    if (paramBoolean) {
      this.databaseRunner.start();
    }
  }
  
  @NonNull
  public Result<Long> storeEvent(@Nullable String paramString)
  {
    return store(paramString, "optimizely_events");
  }
  
  @NonNull
  public Result<Long> storeLog(@Nullable String paramString)
  {
    return store(paramString, "optimizely_logs");
  }
  
  @NonNull
  public Result<Long> storeTimeSeriesEvent(@Nullable String paramString)
  {
    return store(paramString, "optimizely_time_series_events");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/LogAndEvent/OptimizelyDataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.parse;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import bolts.Continuation;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ParseSQLiteDatabase
{
  private static final ExecutorService dbExecutor = ;
  private static final TaskQueue taskQueue = new TaskQueue();
  private Task<Void> current = null;
  private final Object currentLock = new Object();
  private SQLiteDatabase db;
  private int openFlags;
  private final Task<Void>.TaskCompletionSource tcs = Task.create();
  
  private ParseSQLiteDatabase(int paramInt)
  {
    this.openFlags = paramInt;
    taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        synchronized (ParseSQLiteDatabase.this.currentLock)
        {
          ParseSQLiteDatabase.access$102(ParseSQLiteDatabase.this, paramAnonymousTask);
          return ParseSQLiteDatabase.this.tcs.getTask();
        }
      }
    });
  }
  
  static Task<ParseSQLiteDatabase> openDatabaseAsync(SQLiteOpenHelper paramSQLiteOpenHelper, int paramInt)
  {
    ParseSQLiteDatabase localParseSQLiteDatabase = new ParseSQLiteDatabase(paramInt);
    localParseSQLiteDatabase.open(paramSQLiteOpenHelper).continueWithTask(new Continuation()
    {
      public Task<ParseSQLiteDatabase> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return Task.forResult(this.val$db);
      }
    });
  }
  
  public Task<Void> beginTransactionAsync()
  {
    synchronized (this.currentLock)
    {
      this.current = this.current.continueWithTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          ParseSQLiteDatabase.this.db.beginTransaction();
          return paramAnonymousTask;
        }
      }, dbExecutor);
      Task localTask = this.current.continueWithTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return paramAnonymousTask;
        }
      }, Task.BACKGROUND_EXECUTOR);
      return localTask;
    }
  }
  
  public Task<Void> closeAsync()
  {
    synchronized (this.currentLock)
    {
      this.current = this.current.continueWithTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          try
          {
            ParseSQLiteDatabase.this.db.close();
            return ParseSQLiteDatabase.this.tcs.getTask();
          }
          finally
          {
            ParseSQLiteDatabase.this.tcs.setResult(null);
          }
        }
      }, dbExecutor);
      Task localTask = this.current.continueWithTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return paramAnonymousTask;
        }
      }, Task.BACKGROUND_EXECUTOR);
      return localTask;
    }
  }
  
  public Task<Void> deleteAsync(final String paramString1, final String paramString2, final String[] paramArrayOfString)
  {
    synchronized (this.currentLock)
    {
      paramString1 = this.current.onSuccess(new Continuation()
      {
        public Integer then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return Integer.valueOf(ParseSQLiteDatabase.this.db.delete(paramString1, paramString2, paramArrayOfString));
        }
      }, dbExecutor);
      this.current = paramString1.makeVoid();
      paramString1 = paramString1.continueWithTask(new Continuation()
      {
        public Task<Integer> then(Task<Integer> paramAnonymousTask)
          throws Exception
        {
          return paramAnonymousTask;
        }
      }, Task.BACKGROUND_EXECUTOR).makeVoid();
      return paramString1;
    }
  }
  
  public Task<Void> endTransactionAsync()
  {
    synchronized (this.currentLock)
    {
      this.current = this.current.continueWith(new Continuation()
      {
        public Void then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          ParseSQLiteDatabase.this.db.endTransaction();
          return null;
        }
      }, dbExecutor);
      Task localTask = this.current.continueWithTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return paramAnonymousTask;
        }
      }, Task.BACKGROUND_EXECUTOR);
      return localTask;
    }
  }
  
  public boolean inTransaction()
  {
    return this.db.inTransaction();
  }
  
  public Task<Void> insertOrThrowAsync(final String paramString, final ContentValues paramContentValues)
  {
    synchronized (this.currentLock)
    {
      paramString = this.current.onSuccess(new Continuation()
      {
        public Long then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return Long.valueOf(ParseSQLiteDatabase.this.db.insertOrThrow(paramString, null, paramContentValues));
        }
      }, dbExecutor);
      this.current = paramString.makeVoid();
      paramString = paramString.continueWithTask(new Continuation()
      {
        public Task<Long> then(Task<Long> paramAnonymousTask)
          throws Exception
        {
          return paramAnonymousTask;
        }
      }, Task.BACKGROUND_EXECUTOR).makeVoid();
      return paramString;
    }
  }
  
  public Task<Void> insertWithOnConflict(final String paramString, final ContentValues paramContentValues, final int paramInt)
  {
    synchronized (this.currentLock)
    {
      paramString = this.current.onSuccess(new Continuation()
      {
        public Long then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return Long.valueOf(ParseSQLiteDatabase.this.db.insertWithOnConflict(paramString, null, paramContentValues, paramInt));
        }
      }, dbExecutor);
      this.current = paramString.makeVoid();
      paramString = paramString.continueWithTask(new Continuation()
      {
        public Task<Long> then(Task<Long> paramAnonymousTask)
          throws Exception
        {
          return paramAnonymousTask;
        }
      }, Task.BACKGROUND_EXECUTOR).makeVoid();
      return paramString;
    }
  }
  
  public Task<Boolean> isOpenAsync()
  {
    synchronized (this.currentLock)
    {
      Task localTask = this.current.continueWith(new Continuation()
      {
        public Boolean then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return Boolean.valueOf(ParseSQLiteDatabase.this.db.isOpen());
        }
      });
      this.current = localTask.makeVoid();
      return localTask;
    }
  }
  
  public Task<Boolean> isReadOnlyAsync()
  {
    synchronized (this.currentLock)
    {
      Task localTask = this.current.continueWith(new Continuation()
      {
        public Boolean then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return Boolean.valueOf(ParseSQLiteDatabase.this.db.isReadOnly());
        }
      });
      this.current = localTask.makeVoid();
      return localTask;
    }
  }
  
  Task<Void> open(final SQLiteOpenHelper paramSQLiteOpenHelper)
  {
    synchronized (this.currentLock)
    {
      this.current = this.current.continueWith(new Continuation()
      {
        public SQLiteDatabase then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          if ((ParseSQLiteDatabase.this.openFlags & 0x1) == 1) {
            return paramSQLiteOpenHelper.getReadableDatabase();
          }
          return paramSQLiteOpenHelper.getWritableDatabase();
        }
      }, dbExecutor).continueWithTask(new Continuation()
      {
        public Task<Void> then(Task<SQLiteDatabase> paramAnonymousTask)
          throws Exception
        {
          ParseSQLiteDatabase.access$302(ParseSQLiteDatabase.this, (SQLiteDatabase)paramAnonymousTask.getResult());
          return paramAnonymousTask.makeVoid();
        }
      }, Task.BACKGROUND_EXECUTOR);
      paramSQLiteOpenHelper = this.current;
      return paramSQLiteOpenHelper;
    }
  }
  
  public Task<Cursor> queryAsync(final String paramString1, final String[] paramArrayOfString1, final String paramString2, final String[] paramArrayOfString2)
  {
    synchronized (this.currentLock)
    {
      paramString1 = this.current.onSuccess(new Continuation()
      {
        public Cursor then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return ParseSQLiteDatabase.this.db.query(paramString1, paramArrayOfString1, paramString2, paramArrayOfString2, null, null, null);
        }
      }, dbExecutor).onSuccess(new Continuation()
      {
        public Cursor then(Task<Cursor> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = ParseSQLiteCursor.create((Cursor)paramAnonymousTask.getResult(), ParseSQLiteDatabase.dbExecutor);
          paramAnonymousTask.getCount();
          return paramAnonymousTask;
        }
      }, dbExecutor);
      this.current = paramString1.makeVoid();
      paramString1 = paramString1.continueWithTask(new Continuation()
      {
        public Task<Cursor> then(Task<Cursor> paramAnonymousTask)
          throws Exception
        {
          return paramAnonymousTask;
        }
      }, Task.BACKGROUND_EXECUTOR);
      return paramString1;
    }
  }
  
  public Task<Cursor> rawQueryAsync(final String paramString, final String[] paramArrayOfString)
  {
    synchronized (this.currentLock)
    {
      paramString = this.current.onSuccess(new Continuation()
      {
        public Cursor then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return ParseSQLiteDatabase.this.db.rawQuery(paramString, paramArrayOfString);
        }
      }, dbExecutor).onSuccess(new Continuation()
      {
        public Cursor then(Task<Cursor> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = ParseSQLiteCursor.create((Cursor)paramAnonymousTask.getResult(), ParseSQLiteDatabase.dbExecutor);
          paramAnonymousTask.getCount();
          return paramAnonymousTask;
        }
      }, dbExecutor);
      this.current = paramString.makeVoid();
      paramString = paramString.continueWithTask(new Continuation()
      {
        public Task<Cursor> then(Task<Cursor> paramAnonymousTask)
          throws Exception
        {
          return paramAnonymousTask;
        }
      }, Task.BACKGROUND_EXECUTOR);
      return paramString;
    }
  }
  
  public Task<Void> setTransactionSuccessfulAsync()
  {
    synchronized (this.currentLock)
    {
      this.current = this.current.onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          ParseSQLiteDatabase.this.db.setTransactionSuccessful();
          return paramAnonymousTask;
        }
      }, dbExecutor);
      Task localTask = this.current.continueWithTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return paramAnonymousTask;
        }
      }, Task.BACKGROUND_EXECUTOR);
      return localTask;
    }
  }
  
  public Task<Integer> updateAsync(final String paramString1, final ContentValues paramContentValues, final String paramString2, final String[] paramArrayOfString)
  {
    synchronized (this.currentLock)
    {
      paramString1 = this.current.onSuccess(new Continuation()
      {
        public Integer then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return Integer.valueOf(ParseSQLiteDatabase.this.db.update(paramString1, paramContentValues, paramString2, paramArrayOfString));
        }
      }, dbExecutor);
      this.current = paramString1.makeVoid();
      paramString1 = paramString1.continueWithTask(new Continuation()
      {
        public Task<Integer> then(Task<Integer> paramAnonymousTask)
          throws Exception
        {
          return paramAnonymousTask;
        }
      }, Task.BACKGROUND_EXECUTOR);
      return paramString1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseSQLiteDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.parse;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import bolts.Task;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

class ParseSQLiteCursor
  implements Cursor
{
  private Cursor cursor;
  private Executor executor;
  
  private ParseSQLiteCursor(Cursor paramCursor, Executor paramExecutor)
  {
    this.cursor = paramCursor;
    this.executor = paramExecutor;
  }
  
  public static Cursor create(Cursor paramCursor, Executor paramExecutor)
  {
    if (Build.VERSION.SDK_INT >= 14) {
      return paramCursor;
    }
    return new ParseSQLiteCursor(paramCursor, paramExecutor);
  }
  
  public void close()
  {
    Task.call(new Callable()
    {
      public Void call()
        throws Exception
      {
        ParseSQLiteCursor.this.cursor.close();
        return null;
      }
    }, this.executor);
  }
  
  public void copyStringToBuffer(int paramInt, CharArrayBuffer paramCharArrayBuffer)
  {
    this.cursor.copyStringToBuffer(paramInt, paramCharArrayBuffer);
  }
  
  @Deprecated
  public void deactivate()
  {
    this.cursor.deactivate();
  }
  
  public byte[] getBlob(int paramInt)
  {
    return this.cursor.getBlob(paramInt);
  }
  
  public int getColumnCount()
  {
    return this.cursor.getColumnCount();
  }
  
  public int getColumnIndex(String paramString)
  {
    return this.cursor.getColumnIndex(paramString);
  }
  
  public int getColumnIndexOrThrow(String paramString)
    throws IllegalArgumentException
  {
    return this.cursor.getColumnIndexOrThrow(paramString);
  }
  
  public String getColumnName(int paramInt)
  {
    return this.cursor.getColumnName(paramInt);
  }
  
  public String[] getColumnNames()
  {
    return this.cursor.getColumnNames();
  }
  
  public int getCount()
  {
    return this.cursor.getCount();
  }
  
  public double getDouble(int paramInt)
  {
    return this.cursor.getDouble(paramInt);
  }
  
  public Bundle getExtras()
  {
    return this.cursor.getExtras();
  }
  
  public float getFloat(int paramInt)
  {
    return this.cursor.getFloat(paramInt);
  }
  
  public int getInt(int paramInt)
  {
    return this.cursor.getInt(paramInt);
  }
  
  public long getLong(int paramInt)
  {
    return this.cursor.getLong(paramInt);
  }
  
  @TargetApi(19)
  public Uri getNotificationUri()
  {
    return this.cursor.getNotificationUri();
  }
  
  public int getPosition()
  {
    return this.cursor.getPosition();
  }
  
  public short getShort(int paramInt)
  {
    return this.cursor.getShort(paramInt);
  }
  
  public String getString(int paramInt)
  {
    return this.cursor.getString(paramInt);
  }
  
  @TargetApi(11)
  public int getType(int paramInt)
  {
    return this.cursor.getType(paramInt);
  }
  
  public boolean getWantsAllOnMoveCalls()
  {
    return this.cursor.getWantsAllOnMoveCalls();
  }
  
  public boolean isAfterLast()
  {
    return this.cursor.isAfterLast();
  }
  
  public boolean isBeforeFirst()
  {
    return this.cursor.isBeforeFirst();
  }
  
  public boolean isClosed()
  {
    return this.cursor.isClosed();
  }
  
  public boolean isFirst()
  {
    return this.cursor.isFirst();
  }
  
  public boolean isLast()
  {
    return this.cursor.isLast();
  }
  
  public boolean isNull(int paramInt)
  {
    return this.cursor.isNull(paramInt);
  }
  
  public boolean move(int paramInt)
  {
    return this.cursor.move(paramInt);
  }
  
  public boolean moveToFirst()
  {
    return this.cursor.moveToFirst();
  }
  
  public boolean moveToLast()
  {
    return this.cursor.moveToLast();
  }
  
  public boolean moveToNext()
  {
    return this.cursor.moveToNext();
  }
  
  public boolean moveToPosition(int paramInt)
  {
    return this.cursor.moveToPosition(paramInt);
  }
  
  public boolean moveToPrevious()
  {
    return this.cursor.moveToPrevious();
  }
  
  public void registerContentObserver(ContentObserver paramContentObserver)
  {
    this.cursor.registerContentObserver(paramContentObserver);
  }
  
  public void registerDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    this.cursor.registerDataSetObserver(paramDataSetObserver);
  }
  
  @Deprecated
  public boolean requery()
  {
    return this.cursor.requery();
  }
  
  public Bundle respond(Bundle paramBundle)
  {
    return this.cursor.respond(paramBundle);
  }
  
  public void setNotificationUri(ContentResolver paramContentResolver, Uri paramUri)
  {
    this.cursor.setNotificationUri(paramContentResolver, paramUri);
  }
  
  public void unregisterContentObserver(ContentObserver paramContentObserver)
  {
    this.cursor.unregisterContentObserver(paramContentObserver);
  }
  
  public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    this.cursor.unregisterDataSetObserver(paramDataSetObserver);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseSQLiteCursor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
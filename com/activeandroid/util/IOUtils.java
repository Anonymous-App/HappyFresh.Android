package com.activeandroid.util;

import android.database.Cursor;
import java.io.Closeable;
import java.io.IOException;

public class IOUtils
{
  public static void closeQuietly(Cursor paramCursor)
  {
    if (paramCursor == null) {
      return;
    }
    try
    {
      paramCursor.close();
      return;
    }
    catch (Exception paramCursor)
    {
      Log.e("Couldn't close cursor.", paramCursor);
    }
  }
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable == null) {
      return;
    }
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException paramCloseable)
    {
      Log.e("Couldn't close closeable.", paramCloseable);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/util/IOUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
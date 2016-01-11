package com.parse;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class ParseIOUtils
{
  private static final int DEFAULT_BUFFER_SIZE = 4096;
  private static final int EOF = -1;
  private static final int SKIP_BUFFER_SIZE = 2048;
  private static byte[] SKIP_BYTE_BUFFER;
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException paramCloseable) {}
  }
  
  public static void closeQuietly(InputStream paramInputStream)
  {
    if (paramInputStream != null) {}
    try
    {
      paramInputStream.close();
      return;
    }
    catch (IOException paramInputStream) {}
  }
  
  public static void closeQuietly(OutputStream paramOutputStream)
  {
    if (paramOutputStream != null) {}
    try
    {
      paramOutputStream.close();
      return;
    }
    catch (IOException paramOutputStream) {}
  }
  
  public static int copy(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    long l = copyLarge(paramInputStream, paramOutputStream);
    if (l > 2147483647L) {
      return -1;
    }
    return (int)l;
  }
  
  public static long copyLarge(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    return copyLarge(paramInputStream, paramOutputStream, new byte['က']);
  }
  
  public static long copyLarge(InputStream paramInputStream, OutputStream paramOutputStream, long paramLong1, long paramLong2)
    throws IOException
  {
    return copyLarge(paramInputStream, paramOutputStream, paramLong1, paramLong2, new byte['က']);
  }
  
  public static long copyLarge(InputStream paramInputStream, OutputStream paramOutputStream, long paramLong1, long paramLong2, byte[] paramArrayOfByte)
    throws IOException
  {
    if (paramLong1 > 0L) {
      skipFully(paramInputStream, paramLong1);
    }
    long l;
    if (paramLong2 == 0L)
    {
      l = 0L;
      return l;
    }
    int k = paramArrayOfByte.length;
    int j = k;
    int i = j;
    if (paramLong2 > 0L)
    {
      i = j;
      if (paramLong2 < k) {
        i = (int)paramLong2;
      }
    }
    paramLong1 = 0L;
    for (;;)
    {
      l = paramLong1;
      if (i <= 0) {
        break;
      }
      j = paramInputStream.read(paramArrayOfByte, 0, i);
      l = paramLong1;
      if (-1 == j) {
        break;
      }
      paramOutputStream.write(paramArrayOfByte, 0, j);
      l = paramLong1 + j;
      paramLong1 = l;
      if (paramLong2 > 0L)
      {
        i = (int)Math.min(paramLong2 - l, k);
        paramLong1 = l;
      }
    }
  }
  
  public static long copyLarge(InputStream paramInputStream, OutputStream paramOutputStream, byte[] paramArrayOfByte)
    throws IOException
  {
    int i;
    for (long l = 0L;; l += i)
    {
      i = paramInputStream.read(paramArrayOfByte);
      if (-1 == i) {
        break;
      }
      paramOutputStream.write(paramArrayOfByte, 0, i);
    }
    return l;
  }
  
  public static long skip(InputStream paramInputStream, long paramLong)
    throws IOException
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("Skip count must be non-negative, actual: " + paramLong);
    }
    if (SKIP_BYTE_BUFFER == null) {
      SKIP_BYTE_BUFFER = new byte['ࠀ'];
    }
    long l2;
    for (long l1 = paramLong;; l1 -= l2) {
      if (l1 > 0L)
      {
        l2 = paramInputStream.read(SKIP_BYTE_BUFFER, 0, (int)Math.min(l1, 2048L));
        if (l2 >= 0L) {}
      }
      else
      {
        return paramLong - l1;
      }
    }
  }
  
  public static void skipFully(InputStream paramInputStream, long paramLong)
    throws IOException
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("Bytes to skip must not be negative: " + paramLong);
    }
    long l = skip(paramInputStream, paramLong);
    if (l != paramLong) {
      throw new EOFException("Bytes to skip: " + paramLong + " actual: " + l);
    }
  }
  
  public static byte[] toByteArray(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    copy(paramInputStream, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseIOUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
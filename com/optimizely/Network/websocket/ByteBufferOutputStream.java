package com.optimizely.Network.websocket;

import android.support.annotation.NonNull;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

class ByteBufferOutputStream
  extends OutputStream
{
  @NonNull
  private ByteBuffer mBuffer;
  private final int mGrowSize;
  
  public ByteBufferOutputStream()
  {
    this(131072, 65536);
  }
  
  private ByteBufferOutputStream(int paramInt1, int paramInt2)
  {
    this.mGrowSize = paramInt2;
    this.mBuffer = ByteBuffer.allocateDirect(paramInt1);
    this.mBuffer.clear();
  }
  
  private void expand(int paramInt)
  {
    try
    {
      if (paramInt > this.mBuffer.capacity())
      {
        ByteBuffer localByteBuffer = this.mBuffer;
        int i = this.mBuffer.position();
        this.mBuffer = ByteBuffer.allocateDirect((paramInt / this.mGrowSize + 1) * this.mGrowSize);
        localByteBuffer.clear();
        this.mBuffer.clear();
        this.mBuffer.put(localByteBuffer);
        this.mBuffer.position(i);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @NonNull
  public Buffer clear()
  {
    return this.mBuffer.clear();
  }
  
  public void crlf()
    throws IOException
  {
    try
    {
      write(13);
      write(10);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @NonNull
  public Buffer flip()
  {
    return this.mBuffer.flip();
  }
  
  public ByteBuffer getBuffer()
  {
    return this.mBuffer;
  }
  
  public int remaining()
  {
    return this.mBuffer.remaining();
  }
  
  public void write(int paramInt)
    throws IOException
  {
    try
    {
      if (this.mBuffer.position() + 1 > this.mBuffer.capacity()) {
        expand(this.mBuffer.capacity() + 1);
      }
      this.mBuffer.put((byte)paramInt);
      return;
    }
    finally {}
  }
  
  public void write(@NonNull String paramString)
    throws IOException
  {
    try
    {
      write(paramString.getBytes("UTF-8"));
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void write(@NonNull byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      write(paramArrayOfByte, 0, paramArrayOfByte.length);
      return;
    }
    finally
    {
      paramArrayOfByte = finally;
      throw paramArrayOfByte;
    }
  }
  
  public void write(@NonNull byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    try
    {
      if (this.mBuffer.position() + paramInt2 > this.mBuffer.capacity()) {
        expand(this.mBuffer.capacity() + paramInt2);
      }
      this.mBuffer.put(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    finally {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/websocket/ByteBufferOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
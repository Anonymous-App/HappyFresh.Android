package com.optimizely.Network.websocket;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

class ByteBufferInputStream
  extends InputStream
{
  @NonNull
  private final ByteBuffer mBuffer;
  
  public ByteBufferInputStream(@NonNull ByteBuffer paramByteBuffer)
  {
    this.mBuffer = paramByteBuffer;
  }
  
  /* Error */
  public int read()
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 14	com/optimizely/Network/websocket/ByteBufferInputStream:mBuffer	Ljava/nio/ByteBuffer;
    //   6: invokevirtual 26	java/nio/ByteBuffer:hasRemaining	()Z
    //   9: istore_2
    //   10: iload_2
    //   11: ifne +9 -> 20
    //   14: iconst_m1
    //   15: istore_1
    //   16: aload_0
    //   17: monitorexit
    //   18: iload_1
    //   19: ireturn
    //   20: aload_0
    //   21: getfield 14	com/optimizely/Network/websocket/ByteBufferInputStream:mBuffer	Ljava/nio/ByteBuffer;
    //   24: invokevirtual 30	java/nio/ByteBuffer:get	()B
    //   27: istore_1
    //   28: iload_1
    //   29: sipush 255
    //   32: iand
    //   33: istore_1
    //   34: goto -18 -> 16
    //   37: astore_3
    //   38: aload_0
    //   39: monitorexit
    //   40: aload_3
    //   41: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	42	0	this	ByteBufferInputStream
    //   15	19	1	i	int
    //   9	2	2	bool	boolean
    //   37	4	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	10	37	finally
    //   20	28	37	finally
  }
  
  public int read(@Nullable byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramArrayOfByte == null) {
      try
      {
        throw new NullPointerException();
      }
      finally {}
    }
    if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt2 > paramArrayOfByte.length - paramInt1)) {
      throw new IndexOutOfBoundsException();
    }
    if (paramInt2 == 0) {
      paramInt1 = 0;
    }
    for (;;)
    {
      return paramInt1;
      paramInt2 = Math.min(this.mBuffer.remaining(), paramInt2);
      if (paramInt2 == 0)
      {
        paramInt1 = -1;
      }
      else
      {
        this.mBuffer.get(paramArrayOfByte, paramInt1, paramInt2);
        paramInt1 = paramInt2;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/websocket/ByteBufferInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
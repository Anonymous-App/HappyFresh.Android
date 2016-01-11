package org.jcodec;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public abstract interface SeekableByteChannel
  extends ByteChannel, Channel, Closeable, ReadableByteChannel, WritableByteChannel
{
  public abstract String getFileName();
  
  public abstract long getWantedSize();
  
  public abstract long position()
    throws IOException;
  
  public abstract SeekableByteChannel position(long paramLong)
    throws IOException;
  
  public abstract void setChannel(FileChannel paramFileChannel);
  
  public abstract long size()
    throws IOException;
  
  public abstract SeekableByteChannel truncate(long paramLong)
    throws IOException;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/SeekableByteChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
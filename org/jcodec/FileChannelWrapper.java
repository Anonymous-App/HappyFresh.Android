package org.jcodec;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelWrapper
  implements SeekableByteChannel
{
  private FileChannel ch;
  private String file;
  private long wantedSize;
  
  public FileChannelWrapper(FileChannel paramFileChannel, String paramString, long paramLong)
    throws FileNotFoundException
  {
    this.ch = paramFileChannel;
    this.file = paramString;
    this.wantedSize = paramLong;
  }
  
  public void close()
    throws IOException
  {
    this.ch.close();
  }
  
  public String getFileName()
  {
    return this.file;
  }
  
  public long getWantedSize()
  {
    return this.wantedSize;
  }
  
  public boolean isOpen()
  {
    return this.ch.isOpen();
  }
  
  public long position()
    throws IOException
  {
    return this.ch.position();
  }
  
  public SeekableByteChannel position(long paramLong)
    throws IOException
  {
    this.ch.position(paramLong);
    return this;
  }
  
  public int read(ByteBuffer paramByteBuffer)
    throws IOException
  {
    return this.ch.read(paramByteBuffer);
  }
  
  public void setChannel(FileChannel paramFileChannel)
  {
    this.ch = paramFileChannel;
  }
  
  public long size()
    throws IOException
  {
    return this.ch.size();
  }
  
  public SeekableByteChannel truncate(long paramLong)
    throws IOException
  {
    this.ch.truncate(paramLong);
    return this;
  }
  
  public int write(ByteBuffer paramByteBuffer)
    throws IOException
  {
    return this.ch.write(paramByteBuffer);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/FileChannelWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
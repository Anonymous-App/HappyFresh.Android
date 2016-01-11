package com.mixpanel.android.java_websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;
import javax.net.ssl.SSLException;

public class AbstractWrappedByteChannel
  implements WrappedByteChannel
{
  private final ByteChannel channel;
  
  public AbstractWrappedByteChannel(WrappedByteChannel paramWrappedByteChannel)
  {
    this.channel = paramWrappedByteChannel;
  }
  
  public AbstractWrappedByteChannel(ByteChannel paramByteChannel)
  {
    this.channel = paramByteChannel;
  }
  
  public void close()
    throws IOException
  {
    this.channel.close();
  }
  
  public boolean isBlocking()
  {
    if ((this.channel instanceof SocketChannel)) {
      return ((SocketChannel)this.channel).isBlocking();
    }
    if ((this.channel instanceof WrappedByteChannel)) {
      return ((WrappedByteChannel)this.channel).isBlocking();
    }
    return false;
  }
  
  public boolean isNeedRead()
  {
    if ((this.channel instanceof WrappedByteChannel)) {
      return ((WrappedByteChannel)this.channel).isNeedRead();
    }
    return false;
  }
  
  public boolean isNeedWrite()
  {
    if ((this.channel instanceof WrappedByteChannel)) {
      return ((WrappedByteChannel)this.channel).isNeedWrite();
    }
    return false;
  }
  
  public boolean isOpen()
  {
    return this.channel.isOpen();
  }
  
  public int read(ByteBuffer paramByteBuffer)
    throws IOException
  {
    return this.channel.read(paramByteBuffer);
  }
  
  public int readMore(ByteBuffer paramByteBuffer)
    throws SSLException
  {
    if ((this.channel instanceof WrappedByteChannel)) {
      return ((WrappedByteChannel)this.channel).readMore(paramByteBuffer);
    }
    return 0;
  }
  
  public int write(ByteBuffer paramByteBuffer)
    throws IOException
  {
    return this.channel.write(paramByteBuffer);
  }
  
  public void writeMore()
    throws IOException
  {
    if ((this.channel instanceof WrappedByteChannel)) {
      ((WrappedByteChannel)this.channel).writeMore();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/AbstractWrappedByteChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
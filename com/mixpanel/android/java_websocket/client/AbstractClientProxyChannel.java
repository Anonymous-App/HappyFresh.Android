package com.mixpanel.android.java_websocket.client;

import com.mixpanel.android.java_websocket.AbstractWrappedByteChannel;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public abstract class AbstractClientProxyChannel
  extends AbstractWrappedByteChannel
{
  protected final ByteBuffer proxyHandshake;
  
  public AbstractClientProxyChannel(ByteChannel paramByteChannel)
  {
    super(paramByteChannel);
    try
    {
      this.proxyHandshake = ByteBuffer.wrap(buildHandShake().getBytes("ASCII"));
      return;
    }
    catch (UnsupportedEncodingException paramByteChannel)
    {
      throw new RuntimeException(paramByteChannel);
    }
  }
  
  public abstract String buildHandShake();
  
  public int write(ByteBuffer paramByteBuffer)
    throws IOException
  {
    if (!this.proxyHandshake.hasRemaining()) {
      return super.write(paramByteBuffer);
    }
    return super.write(this.proxyHandshake);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/client/AbstractClientProxyChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
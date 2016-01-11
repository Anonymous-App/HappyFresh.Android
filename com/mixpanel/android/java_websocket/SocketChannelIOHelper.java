package com.mixpanel.android.java_websocket;

import com.mixpanel.android.java_websocket.drafts.Draft;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.util.concurrent.BlockingQueue;

public class SocketChannelIOHelper
{
  public static boolean batch(WebSocketImpl paramWebSocketImpl, ByteChannel paramByteChannel)
    throws IOException
  {
    ByteBuffer localByteBuffer = (ByteBuffer)paramWebSocketImpl.outQueue.peek();
    WrappedByteChannel localWrappedByteChannel = null;
    Object localObject = localByteBuffer;
    if (localByteBuffer == null)
    {
      localObject = localWrappedByteChannel;
      if ((paramByteChannel instanceof WrappedByteChannel))
      {
        localWrappedByteChannel = (WrappedByteChannel)paramByteChannel;
        localObject = localWrappedByteChannel;
        if (localWrappedByteChannel.isNeedWrite()) {
          localWrappedByteChannel.writeMore();
        }
      }
    }
    for (localObject = localWrappedByteChannel;; localObject = localWrappedByteChannel)
    {
      if ((paramWebSocketImpl.outQueue.isEmpty()) && (paramWebSocketImpl.isFlushAndClose()) && (paramWebSocketImpl.getDraft().getRole() == WebSocket.Role.SERVER)) {}
      do
      {
        try
        {
          paramWebSocketImpl.closeConnection();
          if ((localObject != null) && (((WrappedByteChannel)paramByteChannel).isNeedWrite())) {
            break;
          }
          return true;
        }
        finally {}
        paramByteChannel.write((ByteBuffer)localObject);
        if (((ByteBuffer)localObject).remaining() > 0) {
          return false;
        }
        paramWebSocketImpl.outQueue.poll();
        localByteBuffer = (ByteBuffer)paramWebSocketImpl.outQueue.peek();
        localObject = localByteBuffer;
      } while (localByteBuffer != null);
    }
    return false;
  }
  
  public static boolean read(ByteBuffer paramByteBuffer, WebSocketImpl paramWebSocketImpl, ByteChannel paramByteChannel)
    throws IOException
  {
    paramByteBuffer.clear();
    int i = paramByteChannel.read(paramByteBuffer);
    paramByteBuffer.flip();
    if (i == -1) {
      paramWebSocketImpl.eot();
    }
    while (i == 0) {
      return false;
    }
    return true;
  }
  
  public static boolean readMore(ByteBuffer paramByteBuffer, WebSocketImpl paramWebSocketImpl, WrappedByteChannel paramWrappedByteChannel)
    throws IOException
  {
    paramByteBuffer.clear();
    int i = paramWrappedByteChannel.readMore(paramByteBuffer);
    paramByteBuffer.flip();
    if (i == -1)
    {
      paramWebSocketImpl.eot();
      return false;
    }
    return paramWrappedByteChannel.isNeedRead();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/SocketChannelIOHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
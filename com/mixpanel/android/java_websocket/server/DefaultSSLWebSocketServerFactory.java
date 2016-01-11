package com.mixpanel.android.java_websocket.server;

import com.mixpanel.android.java_websocket.SSLSocketChannel2;
import com.mixpanel.android.java_websocket.WebSocketAdapter;
import com.mixpanel.android.java_websocket.WebSocketImpl;
import com.mixpanel.android.java_websocket.drafts.Draft;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public class DefaultSSLWebSocketServerFactory
  implements WebSocketServer.WebSocketServerFactory
{
  protected ExecutorService exec;
  protected SSLContext sslcontext;
  
  public DefaultSSLWebSocketServerFactory(SSLContext paramSSLContext)
  {
    this(paramSSLContext, Executors.newSingleThreadScheduledExecutor());
  }
  
  public DefaultSSLWebSocketServerFactory(SSLContext paramSSLContext, ExecutorService paramExecutorService)
  {
    if ((paramSSLContext == null) || (paramExecutorService == null)) {
      throw new IllegalArgumentException();
    }
    this.sslcontext = paramSSLContext;
    this.exec = paramExecutorService;
  }
  
  public WebSocketImpl createWebSocket(WebSocketAdapter paramWebSocketAdapter, Draft paramDraft, Socket paramSocket)
  {
    return new WebSocketImpl(paramWebSocketAdapter, paramDraft);
  }
  
  public WebSocketImpl createWebSocket(WebSocketAdapter paramWebSocketAdapter, List<Draft> paramList, Socket paramSocket)
  {
    return new WebSocketImpl(paramWebSocketAdapter, paramList);
  }
  
  public ByteChannel wrapChannel(SocketChannel paramSocketChannel, SelectionKey paramSelectionKey)
    throws IOException
  {
    SSLEngine localSSLEngine = this.sslcontext.createSSLEngine();
    localSSLEngine.setUseClientMode(false);
    return new SSLSocketChannel2(paramSocketChannel, localSSLEngine, this.exec, paramSelectionKey);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/server/DefaultSSLWebSocketServerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
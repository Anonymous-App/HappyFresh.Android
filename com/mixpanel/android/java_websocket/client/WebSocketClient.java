package com.mixpanel.android.java_websocket.client;

import android.annotation.SuppressLint;
import com.mixpanel.android.java_websocket.WebSocket;
import com.mixpanel.android.java_websocket.WebSocket.READYSTATE;
import com.mixpanel.android.java_websocket.WebSocketAdapter;
import com.mixpanel.android.java_websocket.WebSocketImpl;
import com.mixpanel.android.java_websocket.drafts.Draft;
import com.mixpanel.android.java_websocket.drafts.Draft_17;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.framing.Framedata.Opcode;
import com.mixpanel.android.java_websocket.handshake.ClientHandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.HandshakeImpl1Client;
import com.mixpanel.android.java_websocket.handshake.Handshakedata;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

@SuppressLint({"Assert"})
public abstract class WebSocketClient
  extends WebSocketAdapter
  implements Runnable, WebSocket
{
  private CountDownLatch closeLatch = new CountDownLatch(1);
  private CountDownLatch connectLatch = new CountDownLatch(1);
  private int connectTimeout = 0;
  private Draft draft;
  private WebSocketImpl engine = null;
  private Map<String, String> headers;
  private InputStream istream;
  private OutputStream ostream;
  private Proxy proxy = Proxy.NO_PROXY;
  private Socket socket = null;
  protected URI uri = null;
  private Thread writeThread;
  
  static
  {
    if (!WebSocketClient.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public WebSocketClient(URI paramURI)
  {
    this(paramURI, new Draft_17());
  }
  
  public WebSocketClient(URI paramURI, Draft paramDraft)
  {
    this(paramURI, paramDraft, null, 0);
  }
  
  public WebSocketClient(URI paramURI, Draft paramDraft, Map<String, String> paramMap, int paramInt)
  {
    if (paramURI == null) {
      throw new IllegalArgumentException();
    }
    if (paramDraft == null) {
      throw new IllegalArgumentException("null as draft is permitted for `WebSocketServer` only!");
    }
    this.uri = paramURI;
    this.draft = paramDraft;
    this.headers = paramMap;
    this.connectTimeout = paramInt;
    this.engine = new WebSocketImpl(this, paramDraft);
  }
  
  private int getPort()
  {
    int j = this.uri.getPort();
    int i = j;
    String str;
    if (j == -1)
    {
      str = this.uri.getScheme();
      if (str.equals("wss")) {
        i = 443;
      }
    }
    else
    {
      return i;
    }
    if (str.equals("ws")) {
      return 80;
    }
    throw new RuntimeException("unkonow scheme" + str);
  }
  
  private void sendHandshake()
    throws InvalidHandshakeException
  {
    Object localObject1 = this.uri.getPath();
    Object localObject3 = this.uri.getQuery();
    Object localObject2;
    int i;
    if ((localObject1 == null) || (((String)localObject1).length() == 0))
    {
      localObject1 = "/";
      localObject2 = localObject1;
      if (localObject3 != null) {
        localObject2 = (String)localObject1 + "?" + (String)localObject3;
      }
      i = getPort();
      localObject3 = new StringBuilder().append(this.uri.getHost());
      if (i == 80) {
        break label217;
      }
    }
    label217:
    for (localObject1 = ":" + i;; localObject1 = "")
    {
      localObject3 = (String)localObject1;
      localObject1 = new HandshakeImpl1Client();
      ((HandshakeImpl1Client)localObject1).setResourceDescriptor((String)localObject2);
      ((HandshakeImpl1Client)localObject1).put("Host", (String)localObject3);
      if (this.headers == null) {
        break label223;
      }
      localObject2 = this.headers.entrySet().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (Map.Entry)((Iterator)localObject2).next();
        ((HandshakeImpl1Client)localObject1).put((String)((Map.Entry)localObject3).getKey(), (String)((Map.Entry)localObject3).getValue());
      }
      break;
    }
    label223:
    this.engine.startHandshake((ClientHandshakeBuilder)localObject1);
  }
  
  public void close()
  {
    if (this.writeThread != null) {
      this.engine.close(1000);
    }
  }
  
  public void close(int paramInt)
  {
    this.engine.close();
  }
  
  public void close(int paramInt, String paramString)
  {
    this.engine.close(paramInt, paramString);
  }
  
  public void closeBlocking()
    throws InterruptedException
  {
    close();
    this.closeLatch.await();
  }
  
  public void closeConnection(int paramInt, String paramString)
  {
    this.engine.closeConnection(paramInt, paramString);
  }
  
  public void connect()
  {
    if (this.writeThread != null) {
      throw new IllegalStateException("WebSocketClient objects are not reuseable");
    }
    this.writeThread = new Thread(this);
    this.writeThread.start();
  }
  
  public boolean connectBlocking()
    throws InterruptedException
  {
    connect();
    this.connectLatch.await();
    return this.engine.isOpen();
  }
  
  public WebSocket getConnection()
  {
    return this.engine;
  }
  
  public Draft getDraft()
  {
    return this.draft;
  }
  
  public InetSocketAddress getLocalSocketAddress()
  {
    return this.engine.getLocalSocketAddress();
  }
  
  public InetSocketAddress getLocalSocketAddress(WebSocket paramWebSocket)
  {
    if (this.socket != null) {
      return (InetSocketAddress)this.socket.getLocalSocketAddress();
    }
    return null;
  }
  
  public WebSocket.READYSTATE getReadyState()
  {
    return this.engine.getReadyState();
  }
  
  public InetSocketAddress getRemoteSocketAddress()
  {
    return this.engine.getRemoteSocketAddress();
  }
  
  public InetSocketAddress getRemoteSocketAddress(WebSocket paramWebSocket)
  {
    if (this.socket != null) {
      return (InetSocketAddress)this.socket.getRemoteSocketAddress();
    }
    return null;
  }
  
  public String getResourceDescriptor()
  {
    return this.uri.getPath();
  }
  
  public URI getURI()
  {
    return this.uri;
  }
  
  public boolean hasBufferedData()
  {
    return this.engine.hasBufferedData();
  }
  
  public boolean isClosed()
  {
    return this.engine.isClosed();
  }
  
  public boolean isClosing()
  {
    return this.engine.isClosing();
  }
  
  public boolean isConnecting()
  {
    return this.engine.isConnecting();
  }
  
  public boolean isFlushAndClose()
  {
    return this.engine.isFlushAndClose();
  }
  
  public boolean isOpen()
  {
    return this.engine.isOpen();
  }
  
  public abstract void onClose(int paramInt, String paramString, boolean paramBoolean);
  
  public void onCloseInitiated(int paramInt, String paramString) {}
  
  public void onClosing(int paramInt, String paramString, boolean paramBoolean) {}
  
  public abstract void onError(Exception paramException);
  
  public void onFragment(Framedata paramFramedata) {}
  
  public abstract void onMessage(String paramString);
  
  public void onMessage(ByteBuffer paramByteBuffer) {}
  
  public abstract void onOpen(ServerHandshake paramServerHandshake);
  
  public final void onWebsocketClose(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean)
  {
    this.connectLatch.countDown();
    this.closeLatch.countDown();
    if (this.writeThread != null) {
      this.writeThread.interrupt();
    }
    try
    {
      if (this.socket != null) {
        this.socket.close();
      }
      onClose(paramInt, paramString, paramBoolean);
      return;
    }
    catch (IOException paramWebSocket)
    {
      for (;;)
      {
        onWebsocketError(this, paramWebSocket);
      }
    }
  }
  
  public void onWebsocketCloseInitiated(WebSocket paramWebSocket, int paramInt, String paramString)
  {
    onCloseInitiated(paramInt, paramString);
  }
  
  public void onWebsocketClosing(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean)
  {
    onClosing(paramInt, paramString, paramBoolean);
  }
  
  public final void onWebsocketError(WebSocket paramWebSocket, Exception paramException)
  {
    onError(paramException);
  }
  
  public final void onWebsocketMessage(WebSocket paramWebSocket, String paramString)
  {
    onMessage(paramString);
  }
  
  public final void onWebsocketMessage(WebSocket paramWebSocket, ByteBuffer paramByteBuffer)
  {
    onMessage(paramByteBuffer);
  }
  
  public void onWebsocketMessageFragment(WebSocket paramWebSocket, Framedata paramFramedata)
  {
    onFragment(paramFramedata);
  }
  
  public final void onWebsocketOpen(WebSocket paramWebSocket, Handshakedata paramHandshakedata)
  {
    this.connectLatch.countDown();
    onOpen((ServerHandshake)paramHandshakedata);
  }
  
  public final void onWriteDemand(WebSocket paramWebSocket) {}
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 72	com/mixpanel/android/java_websocket/client/WebSocketClient:socket	Ljava/net/Socket;
    //   4: ifnonnull +185 -> 189
    //   7: aload_0
    //   8: new 274	java/net/Socket
    //   11: dup
    //   12: aload_0
    //   13: getfield 79	com/mixpanel/android/java_websocket/client/WebSocketClient:proxy	Ljava/net/Proxy;
    //   16: invokespecial 369	java/net/Socket:<init>	(Ljava/net/Proxy;)V
    //   19: putfield 72	com/mixpanel/android/java_websocket/client/WebSocketClient:socket	Ljava/net/Socket;
    //   22: aload_0
    //   23: getfield 72	com/mixpanel/android/java_websocket/client/WebSocketClient:socket	Ljava/net/Socket;
    //   26: invokevirtual 372	java/net/Socket:isBound	()Z
    //   29: ifne +32 -> 61
    //   32: aload_0
    //   33: getfield 72	com/mixpanel/android/java_websocket/client/WebSocketClient:socket	Ljava/net/Socket;
    //   36: new 279	java/net/InetSocketAddress
    //   39: dup
    //   40: aload_0
    //   41: getfield 68	com/mixpanel/android/java_websocket/client/WebSocketClient:uri	Ljava/net/URI;
    //   44: invokevirtual 170	java/net/URI:getHost	()Ljava/lang/String;
    //   47: aload_0
    //   48: invokespecial 167	com/mixpanel/android/java_websocket/client/WebSocketClient:getPort	()I
    //   51: invokespecial 375	java/net/InetSocketAddress:<init>	(Ljava/lang/String;I)V
    //   54: aload_0
    //   55: getfield 90	com/mixpanel/android/java_websocket/client/WebSocketClient:connectTimeout	I
    //   58: invokevirtual 378	java/net/Socket:connect	(Ljava/net/SocketAddress;I)V
    //   61: aload_0
    //   62: aload_0
    //   63: getfield 72	com/mixpanel/android/java_websocket/client/WebSocketClient:socket	Ljava/net/Socket;
    //   66: invokevirtual 382	java/net/Socket:getInputStream	()Ljava/io/InputStream;
    //   69: putfield 384	com/mixpanel/android/java_websocket/client/WebSocketClient:istream	Ljava/io/InputStream;
    //   72: aload_0
    //   73: aload_0
    //   74: getfield 72	com/mixpanel/android/java_websocket/client/WebSocketClient:socket	Ljava/net/Socket;
    //   77: invokevirtual 388	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
    //   80: putfield 115	com/mixpanel/android/java_websocket/client/WebSocketClient:ostream	Ljava/io/OutputStream;
    //   83: aload_0
    //   84: invokespecial 390	com/mixpanel/android/java_websocket/client/WebSocketClient:sendHandshake	()V
    //   87: aload_0
    //   88: new 251	java/lang/Thread
    //   91: dup
    //   92: new 12	com/mixpanel/android/java_websocket/client/WebSocketClient$WebsocketWriteThread
    //   95: dup
    //   96: aload_0
    //   97: aconst_null
    //   98: invokespecial 393	com/mixpanel/android/java_websocket/client/WebSocketClient$WebsocketWriteThread:<init>	(Lcom/mixpanel/android/java_websocket/client/WebSocketClient;Lcom/mixpanel/android/java_websocket/client/WebSocketClient$1;)V
    //   101: invokespecial 254	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   104: putfield 226	com/mixpanel/android/java_websocket/client/WebSocketClient:writeThread	Ljava/lang/Thread;
    //   107: aload_0
    //   108: getfield 226	com/mixpanel/android/java_websocket/client/WebSocketClient:writeThread	Ljava/lang/Thread;
    //   111: invokevirtual 257	java/lang/Thread:start	()V
    //   114: getstatic 396	com/mixpanel/android/java_websocket/WebSocketImpl:RCVBUF	I
    //   117: newarray <illegal type>
    //   119: astore_2
    //   120: aload_0
    //   121: invokevirtual 397	com/mixpanel/android/java_websocket/client/WebSocketClient:isClosed	()Z
    //   124: ifne +106 -> 230
    //   127: aload_0
    //   128: getfield 384	com/mixpanel/android/java_websocket/client/WebSocketClient:istream	Ljava/io/InputStream;
    //   131: aload_2
    //   132: invokevirtual 403	java/io/InputStream:read	([B)I
    //   135: istore_1
    //   136: iload_1
    //   137: iconst_m1
    //   138: if_icmpeq +92 -> 230
    //   141: aload_0
    //   142: getfield 70	com/mixpanel/android/java_websocket/client/WebSocketClient:engine	Lcom/mixpanel/android/java_websocket/WebSocketImpl;
    //   145: aload_2
    //   146: iconst_0
    //   147: iload_1
    //   148: invokestatic 409	java/nio/ByteBuffer:wrap	([BII)Ljava/nio/ByteBuffer;
    //   151: invokevirtual 412	com/mixpanel/android/java_websocket/WebSocketImpl:decode	(Ljava/nio/ByteBuffer;)V
    //   154: goto -34 -> 120
    //   157: astore_2
    //   158: aload_0
    //   159: getfield 70	com/mixpanel/android/java_websocket/client/WebSocketClient:engine	Lcom/mixpanel/android/java_websocket/WebSocketImpl;
    //   162: invokevirtual 415	com/mixpanel/android/java_websocket/WebSocketImpl:eot	()V
    //   165: getstatic 52	com/mixpanel/android/java_websocket/client/WebSocketClient:$assertionsDisabled	Z
    //   168: ifne +61 -> 229
    //   171: aload_0
    //   172: getfield 72	com/mixpanel/android/java_websocket/client/WebSocketClient:socket	Ljava/net/Socket;
    //   175: invokevirtual 416	java/net/Socket:isClosed	()Z
    //   178: ifne +51 -> 229
    //   181: new 418	java/lang/AssertionError
    //   184: dup
    //   185: invokespecial 419	java/lang/AssertionError:<init>	()V
    //   188: athrow
    //   189: aload_0
    //   190: getfield 72	com/mixpanel/android/java_websocket/client/WebSocketClient:socket	Ljava/net/Socket;
    //   193: invokevirtual 416	java/net/Socket:isClosed	()Z
    //   196: ifeq -174 -> 22
    //   199: new 322	java/io/IOException
    //   202: dup
    //   203: invokespecial 420	java/io/IOException:<init>	()V
    //   206: athrow
    //   207: astore_2
    //   208: aload_0
    //   209: aload_0
    //   210: getfield 70	com/mixpanel/android/java_websocket/client/WebSocketClient:engine	Lcom/mixpanel/android/java_websocket/WebSocketImpl;
    //   213: aload_2
    //   214: invokevirtual 335	com/mixpanel/android/java_websocket/client/WebSocketClient:onWebsocketError	(Lcom/mixpanel/android/java_websocket/WebSocket;Ljava/lang/Exception;)V
    //   217: aload_0
    //   218: getfield 70	com/mixpanel/android/java_websocket/client/WebSocketClient:engine	Lcom/mixpanel/android/java_websocket/WebSocketImpl;
    //   221: iconst_m1
    //   222: aload_2
    //   223: invokevirtual 423	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   226: invokevirtual 243	com/mixpanel/android/java_websocket/WebSocketImpl:closeConnection	(ILjava/lang/String;)V
    //   229: return
    //   230: aload_0
    //   231: getfield 70	com/mixpanel/android/java_websocket/client/WebSocketClient:engine	Lcom/mixpanel/android/java_websocket/WebSocketImpl;
    //   234: invokevirtual 415	com/mixpanel/android/java_websocket/WebSocketImpl:eot	()V
    //   237: goto -72 -> 165
    //   240: astore_2
    //   241: aload_0
    //   242: aload_2
    //   243: invokevirtual 344	com/mixpanel/android/java_websocket/client/WebSocketClient:onError	(Ljava/lang/Exception;)V
    //   246: aload_0
    //   247: getfield 70	com/mixpanel/android/java_websocket/client/WebSocketClient:engine	Lcom/mixpanel/android/java_websocket/WebSocketImpl;
    //   250: sipush 1006
    //   253: aload_2
    //   254: invokevirtual 424	java/lang/RuntimeException:getMessage	()Ljava/lang/String;
    //   257: invokevirtual 243	com/mixpanel/android/java_websocket/WebSocketImpl:closeConnection	(ILjava/lang/String;)V
    //   260: goto -95 -> 165
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	263	0	this	WebSocketClient
    //   135	13	1	i	int
    //   119	27	2	arrayOfByte	byte[]
    //   157	1	2	localIOException	IOException
    //   207	16	2	localException	Exception
    //   240	14	2	localRuntimeException	RuntimeException
    // Exception table:
    //   from	to	target	type
    //   120	136	157	java/io/IOException
    //   141	154	157	java/io/IOException
    //   230	237	157	java/io/IOException
    //   0	22	207	java/lang/Exception
    //   22	61	207	java/lang/Exception
    //   61	87	207	java/lang/Exception
    //   189	207	207	java/lang/Exception
    //   120	136	240	java/lang/RuntimeException
    //   141	154	240	java/lang/RuntimeException
    //   230	237	240	java/lang/RuntimeException
  }
  
  public void send(String paramString)
    throws NotYetConnectedException
  {
    this.engine.send(paramString);
  }
  
  public void send(ByteBuffer paramByteBuffer)
    throws IllegalArgumentException, NotYetConnectedException
  {
    this.engine.send(paramByteBuffer);
  }
  
  public void send(byte[] paramArrayOfByte)
    throws NotYetConnectedException
  {
    this.engine.send(paramArrayOfByte);
  }
  
  public void sendFragmentedFrame(Framedata.Opcode paramOpcode, ByteBuffer paramByteBuffer, boolean paramBoolean)
  {
    this.engine.sendFragmentedFrame(paramOpcode, paramByteBuffer, paramBoolean);
  }
  
  public void sendFrame(Framedata paramFramedata)
  {
    this.engine.sendFrame(paramFramedata);
  }
  
  public void setProxy(Proxy paramProxy)
  {
    if (paramProxy == null) {
      throw new IllegalArgumentException();
    }
    this.proxy = paramProxy;
  }
  
  public void setSocket(Socket paramSocket)
  {
    if (this.socket != null) {
      throw new IllegalStateException("socket has already been set");
    }
    this.socket = paramSocket;
  }
  
  private class WebsocketWriteThread
    implements Runnable
  {
    private WebsocketWriteThread() {}
    
    public void run()
    {
      Thread.currentThread().setName("WebsocketWriteThread");
      try
      {
        while (!Thread.interrupted())
        {
          ByteBuffer localByteBuffer = (ByteBuffer)WebSocketClient.this.engine.outQueue.take();
          WebSocketClient.this.ostream.write(localByteBuffer.array(), 0, localByteBuffer.limit());
          WebSocketClient.this.ostream.flush();
        }
        return;
      }
      catch (IOException localIOException)
      {
        WebSocketClient.this.engine.eot();
        return;
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/client/WebSocketClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.mixpanel.android.java_websocket.server;

import android.annotation.SuppressLint;
import com.mixpanel.android.java_websocket.WebSocket;
import com.mixpanel.android.java_websocket.WebSocketAdapter;
import com.mixpanel.android.java_websocket.WebSocketFactory;
import com.mixpanel.android.java_websocket.WebSocketImpl;
import com.mixpanel.android.java_websocket.drafts.Draft;
import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.Handshakedata;
import com.mixpanel.android.java_websocket.handshake.ServerHandshakeBuilder;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressLint({"Assert"})
public abstract class WebSocketServer
  extends WebSocketAdapter
  implements Runnable
{
  public static int DECODERS;
  private final InetSocketAddress address;
  private BlockingQueue<ByteBuffer> buffers;
  private final Collection<WebSocket> connections;
  private List<WebSocketWorker> decoders;
  private List<Draft> drafts;
  private List<WebSocketImpl> iqueue;
  private volatile AtomicBoolean isclosed = new AtomicBoolean(false);
  private int queueinvokes = 0;
  private AtomicInteger queuesize = new AtomicInteger(0);
  private Selector selector;
  private Thread selectorthread;
  private ServerSocketChannel server;
  private WebSocketServerFactory wsf = new DefaultWebSocketServerFactory();
  
  static
  {
    if (!WebSocketServer.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      DECODERS = Runtime.getRuntime().availableProcessors();
      return;
    }
  }
  
  public WebSocketServer()
    throws UnknownHostException
  {
    this(new InetSocketAddress(80), DECODERS, null);
  }
  
  public WebSocketServer(InetSocketAddress paramInetSocketAddress)
  {
    this(paramInetSocketAddress, DECODERS, null);
  }
  
  public WebSocketServer(InetSocketAddress paramInetSocketAddress, int paramInt)
  {
    this(paramInetSocketAddress, paramInt, null);
  }
  
  public WebSocketServer(InetSocketAddress paramInetSocketAddress, int paramInt, List<Draft> paramList)
  {
    this(paramInetSocketAddress, paramInt, paramList, new HashSet());
  }
  
  public WebSocketServer(InetSocketAddress paramInetSocketAddress, int paramInt, List<Draft> paramList, Collection<WebSocket> paramCollection)
  {
    if ((paramInetSocketAddress == null) || (paramInt < 1) || (paramCollection == null)) {
      throw new IllegalArgumentException("address and connectionscontainer must not be null and you need at least 1 decoder");
    }
    if (paramList == null) {}
    for (this.drafts = Collections.emptyList();; this.drafts = paramList)
    {
      this.address = paramInetSocketAddress;
      this.connections = paramCollection;
      this.iqueue = new LinkedList();
      this.decoders = new ArrayList(paramInt);
      this.buffers = new LinkedBlockingQueue();
      int i = 0;
      while (i < paramInt)
      {
        paramInetSocketAddress = new WebSocketWorker();
        this.decoders.add(paramInetSocketAddress);
        paramInetSocketAddress.start();
        i += 1;
      }
    }
  }
  
  public WebSocketServer(InetSocketAddress paramInetSocketAddress, List<Draft> paramList)
  {
    this(paramInetSocketAddress, DECODERS, paramList);
  }
  
  private Socket getSocket(WebSocket paramWebSocket)
  {
    return ((SocketChannel)((WebSocketImpl)paramWebSocket).key.channel()).socket();
  }
  
  private void handleFatal(WebSocket paramWebSocket, Exception paramException)
  {
    onError(paramWebSocket, paramException);
    try
    {
      stop();
      return;
    }
    catch (IOException paramWebSocket)
    {
      onError(null, paramWebSocket);
      return;
    }
    catch (InterruptedException paramWebSocket)
    {
      Thread.currentThread().interrupt();
      onError(null, paramWebSocket);
    }
  }
  
  private void handleIOException(SelectionKey paramSelectionKey, WebSocket paramWebSocket, IOException paramIOException)
  {
    if (paramWebSocket != null) {
      paramWebSocket.closeConnection(1006, paramIOException.getMessage());
    }
    for (;;)
    {
      return;
      if (paramSelectionKey == null) {
        continue;
      }
      paramSelectionKey = paramSelectionKey.channel();
      if ((paramSelectionKey == null) || (!paramSelectionKey.isOpen())) {
        break;
      }
      try
      {
        paramSelectionKey.close();
        if (!WebSocketImpl.DEBUG) {
          continue;
        }
        System.out.println("Connection closed because of" + paramIOException);
        return;
      }
      catch (IOException paramSelectionKey)
      {
        for (;;) {}
      }
    }
  }
  
  private void pushBuffer(ByteBuffer paramByteBuffer)
    throws InterruptedException
  {
    if (this.buffers.size() > this.queuesize.intValue()) {
      return;
    }
    this.buffers.put(paramByteBuffer);
  }
  
  private void queue(WebSocketImpl paramWebSocketImpl)
    throws InterruptedException
  {
    if (paramWebSocketImpl.workerThread == null)
    {
      paramWebSocketImpl.workerThread = ((WebSocketWorker)this.decoders.get(this.queueinvokes % this.decoders.size()));
      this.queueinvokes += 1;
    }
    paramWebSocketImpl.workerThread.put(paramWebSocketImpl);
  }
  
  private ByteBuffer takeBuffer()
    throws InterruptedException
  {
    return (ByteBuffer)this.buffers.take();
  }
  
  protected boolean addConnection(WebSocket paramWebSocket)
  {
    if (!this.isclosed.get())
    {
      boolean bool;
      synchronized (this.connections)
      {
        bool = this.connections.add(paramWebSocket);
        if ((!$assertionsDisabled) && (!bool)) {
          throw new AssertionError();
        }
      }
      return bool;
    }
    paramWebSocket.close(1001);
    return true;
  }
  
  protected void allocateBuffers(WebSocket paramWebSocket)
    throws InterruptedException
  {
    if (this.queuesize.get() >= this.decoders.size() * 2 + 1) {
      return;
    }
    this.queuesize.incrementAndGet();
    this.buffers.put(createBuffer());
  }
  
  public Collection<WebSocket> connections()
  {
    return this.connections;
  }
  
  public ByteBuffer createBuffer()
  {
    return ByteBuffer.allocate(WebSocketImpl.RCVBUF);
  }
  
  public InetSocketAddress getAddress()
  {
    return this.address;
  }
  
  public List<Draft> getDraft()
  {
    return Collections.unmodifiableList(this.drafts);
  }
  
  protected String getFlashSecurityPolicy()
  {
    return "<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"" + getPort() + "\" /></cross-domain-policy>";
  }
  
  public InetSocketAddress getLocalSocketAddress(WebSocket paramWebSocket)
  {
    return (InetSocketAddress)getSocket(paramWebSocket).getLocalSocketAddress();
  }
  
  public int getPort()
  {
    int j = getAddress().getPort();
    int i = j;
    if (j == 0)
    {
      i = j;
      if (this.server != null) {
        i = this.server.socket().getLocalPort();
      }
    }
    return i;
  }
  
  public InetSocketAddress getRemoteSocketAddress(WebSocket paramWebSocket)
  {
    return (InetSocketAddress)getSocket(paramWebSocket).getRemoteSocketAddress();
  }
  
  public final WebSocketFactory getWebSocketFactory()
  {
    return this.wsf;
  }
  
  public abstract void onClose(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean);
  
  public void onCloseInitiated(WebSocket paramWebSocket, int paramInt, String paramString) {}
  
  public void onClosing(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean) {}
  
  protected boolean onConnect(SelectionKey paramSelectionKey)
  {
    return true;
  }
  
  public abstract void onError(WebSocket paramWebSocket, Exception paramException);
  
  public void onFragment(WebSocket paramWebSocket, Framedata paramFramedata) {}
  
  public abstract void onMessage(WebSocket paramWebSocket, String paramString);
  
  public void onMessage(WebSocket paramWebSocket, ByteBuffer paramByteBuffer) {}
  
  public abstract void onOpen(WebSocket paramWebSocket, ClientHandshake paramClientHandshake);
  
  public final void onWebsocketClose(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean)
  {
    this.selector.wakeup();
    try
    {
      if (removeConnection(paramWebSocket)) {
        onClose(paramWebSocket, paramInt, paramString, paramBoolean);
      }
      try
      {
        releaseBuffers(paramWebSocket);
        return;
      }
      catch (InterruptedException paramWebSocket)
      {
        Thread.currentThread().interrupt();
        return;
      }
      try
      {
        releaseBuffers(paramWebSocket);
        throw paramString;
      }
      catch (InterruptedException paramWebSocket)
      {
        for (;;)
        {
          Thread.currentThread().interrupt();
        }
      }
    }
    finally {}
  }
  
  public void onWebsocketCloseInitiated(WebSocket paramWebSocket, int paramInt, String paramString)
  {
    onCloseInitiated(paramWebSocket, paramInt, paramString);
  }
  
  public void onWebsocketClosing(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean)
  {
    onClosing(paramWebSocket, paramInt, paramString, paramBoolean);
  }
  
  public final void onWebsocketError(WebSocket paramWebSocket, Exception paramException)
  {
    onError(paramWebSocket, paramException);
  }
  
  public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket paramWebSocket, Draft paramDraft, ClientHandshake paramClientHandshake)
    throws InvalidDataException
  {
    return super.onWebsocketHandshakeReceivedAsServer(paramWebSocket, paramDraft, paramClientHandshake);
  }
  
  public final void onWebsocketMessage(WebSocket paramWebSocket, String paramString)
  {
    onMessage(paramWebSocket, paramString);
  }
  
  public final void onWebsocketMessage(WebSocket paramWebSocket, ByteBuffer paramByteBuffer)
  {
    onMessage(paramWebSocket, paramByteBuffer);
  }
  
  @Deprecated
  public void onWebsocketMessageFragment(WebSocket paramWebSocket, Framedata paramFramedata)
  {
    onFragment(paramWebSocket, paramFramedata);
  }
  
  public final void onWebsocketOpen(WebSocket paramWebSocket, Handshakedata paramHandshakedata)
  {
    if (addConnection(paramWebSocket)) {
      onOpen(paramWebSocket, (ClientHandshake)paramHandshakedata);
    }
  }
  
  public final void onWriteDemand(WebSocket paramWebSocket)
  {
    paramWebSocket = (WebSocketImpl)paramWebSocket;
    try
    {
      paramWebSocket.key.interestOps(5);
      this.selector.wakeup();
      return;
    }
    catch (CancelledKeyException localCancelledKeyException)
    {
      for (;;)
      {
        paramWebSocket.outQueue.clear();
      }
    }
  }
  
  protected void releaseBuffers(WebSocket paramWebSocket)
    throws InterruptedException
  {}
  
  protected boolean removeConnection(WebSocket paramWebSocket)
  {
    boolean bool;
    synchronized (this.connections)
    {
      bool = this.connections.remove(paramWebSocket);
      if ((!$assertionsDisabled) && (!bool)) {
        throw new AssertionError();
      }
    }
    if ((this.isclosed.get()) && (this.connections.size() == 0)) {
      this.selectorthread.interrupt();
    }
    return bool;
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 457	com/mixpanel/android/java_websocket/server/WebSocketServer:selectorthread	Ljava/lang/Thread;
    //   6: ifnull +42 -> 48
    //   9: new 464	java/lang/IllegalStateException
    //   12: dup
    //   13: new 246	java/lang/StringBuilder
    //   16: dup
    //   17: invokespecial 247	java/lang/StringBuilder:<init>	()V
    //   20: aload_0
    //   21: invokevirtual 470	java/lang/Object:getClass	()Ljava/lang/Class;
    //   24: invokevirtual 473	java/lang/Class:getName	()Ljava/lang/String;
    //   27: invokevirtual 253	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: ldc_w 475
    //   33: invokevirtual 253	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: invokevirtual 259	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   39: invokespecial 476	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   42: athrow
    //   43: astore_2
    //   44: aload_0
    //   45: monitorexit
    //   46: aload_2
    //   47: athrow
    //   48: aload_0
    //   49: invokestatic 212	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   52: putfield 457	com/mixpanel/android/java_websocket/server/WebSocketServer:selectorthread	Ljava/lang/Thread;
    //   55: aload_0
    //   56: getfield 103	com/mixpanel/android/java_websocket/server/WebSocketServer:isclosed	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   59: invokevirtual 301	java/util/concurrent/atomic/AtomicBoolean:get	()Z
    //   62: ifeq +6 -> 68
    //   65: aload_0
    //   66: monitorexit
    //   67: return
    //   68: aload_0
    //   69: monitorexit
    //   70: aload_0
    //   71: getfield 457	com/mixpanel/android/java_websocket/server/WebSocketServer:selectorthread	Ljava/lang/Thread;
    //   74: new 246	java/lang/StringBuilder
    //   77: dup
    //   78: invokespecial 247	java/lang/StringBuilder:<init>	()V
    //   81: ldc_w 478
    //   84: invokevirtual 253	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: aload_0
    //   88: getfield 457	com/mixpanel/android/java_websocket/server/WebSocketServer:selectorthread	Ljava/lang/Thread;
    //   91: invokevirtual 482	java/lang/Thread:getId	()J
    //   94: invokevirtual 485	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   97: invokevirtual 259	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   100: invokevirtual 488	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   103: aload_0
    //   104: invokestatic 492	java/nio/channels/ServerSocketChannel:open	()Ljava/nio/channels/ServerSocketChannel;
    //   107: putfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   110: aload_0
    //   111: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   114: iconst_0
    //   115: invokevirtual 496	java/nio/channels/ServerSocketChannel:configureBlocking	(Z)Ljava/nio/channels/SelectableChannel;
    //   118: pop
    //   119: aload_0
    //   120: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   123: invokevirtual 366	java/nio/channels/ServerSocketChannel:socket	()Ljava/net/ServerSocket;
    //   126: astore_2
    //   127: aload_2
    //   128: getstatic 324	com/mixpanel/android/java_websocket/WebSocketImpl:RCVBUF	I
    //   131: invokevirtual 499	java/net/ServerSocket:setReceiveBufferSize	(I)V
    //   134: aload_2
    //   135: aload_0
    //   136: getfield 132	com/mixpanel/android/java_websocket/server/WebSocketServer:address	Ljava/net/InetSocketAddress;
    //   139: invokevirtual 503	java/net/ServerSocket:bind	(Ljava/net/SocketAddress;)V
    //   142: aload_0
    //   143: invokestatic 505	java/nio/channels/Selector:open	()Ljava/nio/channels/Selector;
    //   146: putfield 393	com/mixpanel/android/java_websocket/server/WebSocketServer:selector	Ljava/nio/channels/Selector;
    //   149: aload_0
    //   150: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   153: aload_0
    //   154: getfield 393	com/mixpanel/android/java_websocket/server/WebSocketServer:selector	Ljava/nio/channels/Selector;
    //   157: aload_0
    //   158: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   161: invokevirtual 508	java/nio/channels/ServerSocketChannel:validOps	()I
    //   164: invokevirtual 512	java/nio/channels/ServerSocketChannel:register	(Ljava/nio/channels/Selector;I)Ljava/nio/channels/SelectionKey;
    //   167: pop
    //   168: aload_0
    //   169: getfield 457	com/mixpanel/android/java_websocket/server/WebSocketServer:selectorthread	Ljava/lang/Thread;
    //   172: invokevirtual 515	java/lang/Thread:isInterrupted	()Z
    //   175: istore_1
    //   176: iload_1
    //   177: ifne +987 -> 1164
    //   180: aconst_null
    //   181: astore 6
    //   183: aconst_null
    //   184: astore 5
    //   186: aconst_null
    //   187: astore 7
    //   189: aconst_null
    //   190: astore_3
    //   191: aload 7
    //   193: astore_2
    //   194: aload 6
    //   196: astore 4
    //   198: aload_0
    //   199: getfield 393	com/mixpanel/android/java_websocket/server/WebSocketServer:selector	Ljava/nio/channels/Selector;
    //   202: invokevirtual 518	java/nio/channels/Selector:select	()I
    //   205: pop
    //   206: aload 7
    //   208: astore_2
    //   209: aload 6
    //   211: astore 4
    //   213: aload_0
    //   214: getfield 393	com/mixpanel/android/java_websocket/server/WebSocketServer:selector	Ljava/nio/channels/Selector;
    //   217: invokevirtual 522	java/nio/channels/Selector:selectedKeys	()Ljava/util/Set;
    //   220: invokeinterface 528 1 0
    //   225: astore 8
    //   227: aload_3
    //   228: astore_2
    //   229: aload 5
    //   231: astore 4
    //   233: aload_3
    //   234: astore 6
    //   236: aload 8
    //   238: invokeinterface 533 1 0
    //   243: ifeq +723 -> 966
    //   246: aload_3
    //   247: astore_2
    //   248: aload 5
    //   250: astore 4
    //   252: aload 8
    //   254: invokeinterface 536 1 0
    //   259: checkcast 188	java/nio/channels/SelectionKey
    //   262: astore 7
    //   264: aload 7
    //   266: astore 5
    //   268: aload_3
    //   269: astore_2
    //   270: aload 7
    //   272: astore 4
    //   274: aload 7
    //   276: invokevirtual 539	java/nio/channels/SelectionKey:isValid	()Z
    //   279: ifeq -52 -> 227
    //   282: aload_3
    //   283: astore_2
    //   284: aload 7
    //   286: astore 4
    //   288: aload 7
    //   290: invokevirtual 542	java/nio/channels/SelectionKey:isAcceptable	()Z
    //   293: ifeq +227 -> 520
    //   296: aload_3
    //   297: astore_2
    //   298: aload 7
    //   300: astore 4
    //   302: aload_0
    //   303: aload 7
    //   305: invokevirtual 544	com/mixpanel/android/java_websocket/server/WebSocketServer:onConnect	(Ljava/nio/channels/SelectionKey;)Z
    //   308: ifne +33 -> 341
    //   311: aload_3
    //   312: astore_2
    //   313: aload 7
    //   315: astore 4
    //   317: aload 7
    //   319: invokevirtual 547	java/nio/channels/SelectionKey:cancel	()V
    //   322: aload 7
    //   324: astore 5
    //   326: goto -99 -> 227
    //   329: astore_2
    //   330: goto -162 -> 168
    //   333: astore_2
    //   334: aload_0
    //   335: aconst_null
    //   336: aload_2
    //   337: invokespecial 178	com/mixpanel/android/java_websocket/server/WebSocketServer:handleFatal	(Lcom/mixpanel/android/java_websocket/WebSocket;Ljava/lang/Exception;)V
    //   340: return
    //   341: aload_3
    //   342: astore_2
    //   343: aload 7
    //   345: astore 4
    //   347: aload_0
    //   348: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   351: invokevirtual 551	java/nio/channels/ServerSocketChannel:accept	()Ljava/nio/channels/SocketChannel;
    //   354: astore 5
    //   356: aload_3
    //   357: astore_2
    //   358: aload 7
    //   360: astore 4
    //   362: aload 5
    //   364: iconst_0
    //   365: invokevirtual 552	java/nio/channels/SocketChannel:configureBlocking	(Z)Ljava/nio/channels/SelectableChannel;
    //   368: pop
    //   369: aload_3
    //   370: astore_2
    //   371: aload 7
    //   373: astore 4
    //   375: aload_0
    //   376: getfield 115	com/mixpanel/android/java_websocket/server/WebSocketServer:wsf	Lcom/mixpanel/android/java_websocket/server/WebSocketServer$WebSocketServerFactory;
    //   379: aload_0
    //   380: aload_0
    //   381: getfield 130	com/mixpanel/android/java_websocket/server/WebSocketServer:drafts	Ljava/util/List;
    //   384: aload 5
    //   386: invokevirtual 198	java/nio/channels/SocketChannel:socket	()Ljava/net/Socket;
    //   389: invokeinterface 556 4 0
    //   394: astore 6
    //   396: aload_3
    //   397: astore_2
    //   398: aload 7
    //   400: astore 4
    //   402: aload 6
    //   404: aload 5
    //   406: aload_0
    //   407: getfield 393	com/mixpanel/android/java_websocket/server/WebSocketServer:selector	Ljava/nio/channels/Selector;
    //   410: iconst_1
    //   411: aload 6
    //   413: invokevirtual 559	java/nio/channels/SocketChannel:register	(Ljava/nio/channels/Selector;ILjava/lang/Object;)Ljava/nio/channels/SelectionKey;
    //   416: putfield 186	com/mixpanel/android/java_websocket/WebSocketImpl:key	Ljava/nio/channels/SelectionKey;
    //   419: aload_3
    //   420: astore_2
    //   421: aload 7
    //   423: astore 4
    //   425: aload 6
    //   427: aload_0
    //   428: getfield 115	com/mixpanel/android/java_websocket/server/WebSocketServer:wsf	Lcom/mixpanel/android/java_websocket/server/WebSocketServer$WebSocketServerFactory;
    //   431: aload 5
    //   433: aload 6
    //   435: getfield 186	com/mixpanel/android/java_websocket/WebSocketImpl:key	Ljava/nio/channels/SelectionKey;
    //   438: invokeinterface 563 3 0
    //   443: putfield 566	com/mixpanel/android/java_websocket/WebSocketImpl:channel	Ljava/nio/channels/ByteChannel;
    //   446: aload_3
    //   447: astore_2
    //   448: aload 7
    //   450: astore 4
    //   452: aload 8
    //   454: invokeinterface 568 1 0
    //   459: aload_3
    //   460: astore_2
    //   461: aload 7
    //   463: astore 4
    //   465: aload_0
    //   466: aload 6
    //   468: invokevirtual 570	com/mixpanel/android/java_websocket/server/WebSocketServer:allocateBuffers	(Lcom/mixpanel/android/java_websocket/WebSocket;)V
    //   471: aload 7
    //   473: astore 5
    //   475: goto -248 -> 227
    //   478: astore_2
    //   479: aload_0
    //   480: getfield 144	com/mixpanel/android/java_websocket/server/WebSocketServer:decoders	Ljava/util/List;
    //   483: ifnull +635 -> 1118
    //   486: aload_0
    //   487: getfield 144	com/mixpanel/android/java_websocket/server/WebSocketServer:decoders	Ljava/util/List;
    //   490: invokeinterface 571 1 0
    //   495: astore_2
    //   496: aload_2
    //   497: invokeinterface 533 1 0
    //   502: ifeq +616 -> 1118
    //   505: aload_2
    //   506: invokeinterface 536 1 0
    //   511: checkcast 11	com/mixpanel/android/java_websocket/server/WebSocketServer$WebSocketWorker
    //   514: invokevirtual 572	com/mixpanel/android/java_websocket/server/WebSocketServer$WebSocketWorker:interrupt	()V
    //   517: goto -21 -> 496
    //   520: aload_3
    //   521: astore 6
    //   523: aload_3
    //   524: astore_2
    //   525: aload 7
    //   527: astore 4
    //   529: aload 7
    //   531: invokevirtual 575	java/nio/channels/SelectionKey:isReadable	()Z
    //   534: ifeq +119 -> 653
    //   537: aload_3
    //   538: astore_2
    //   539: aload 7
    //   541: astore 4
    //   543: aload 7
    //   545: invokevirtual 578	java/nio/channels/SelectionKey:attachment	()Ljava/lang/Object;
    //   548: checkcast 182	com/mixpanel/android/java_websocket/WebSocketImpl
    //   551: astore_3
    //   552: aload_3
    //   553: astore_2
    //   554: aload 7
    //   556: astore 4
    //   558: aload_0
    //   559: invokespecial 580	com/mixpanel/android/java_websocket/server/WebSocketServer:takeBuffer	()Ljava/nio/ByteBuffer;
    //   562: astore 5
    //   564: aload 5
    //   566: aload_3
    //   567: aload_3
    //   568: getfield 566	com/mixpanel/android/java_websocket/WebSocketImpl:channel	Ljava/nio/channels/ByteChannel;
    //   571: invokestatic 586	com/mixpanel/android/java_websocket/SocketChannelIOHelper:read	(Ljava/nio/ByteBuffer;Lcom/mixpanel/android/java_websocket/WebSocketImpl;Ljava/nio/channels/ByteChannel;)Z
    //   574: ifeq +338 -> 912
    //   577: aload 5
    //   579: invokevirtual 589	java/nio/ByteBuffer:hasRemaining	()Z
    //   582: ifeq +253 -> 835
    //   585: aload_3
    //   586: getfield 592	com/mixpanel/android/java_websocket/WebSocketImpl:inQueue	Ljava/util/concurrent/BlockingQueue;
    //   589: aload 5
    //   591: invokeinterface 276 2 0
    //   596: aload_0
    //   597: aload_3
    //   598: invokespecial 594	com/mixpanel/android/java_websocket/server/WebSocketServer:queue	(Lcom/mixpanel/android/java_websocket/WebSocketImpl;)V
    //   601: aload 8
    //   603: invokeinterface 568 1 0
    //   608: aload_3
    //   609: astore 6
    //   611: aload_3
    //   612: getfield 566	com/mixpanel/android/java_websocket/WebSocketImpl:channel	Ljava/nio/channels/ByteChannel;
    //   615: instanceof 596
    //   618: ifeq +35 -> 653
    //   621: aload_3
    //   622: astore 6
    //   624: aload_3
    //   625: getfield 566	com/mixpanel/android/java_websocket/WebSocketImpl:channel	Ljava/nio/channels/ByteChannel;
    //   628: checkcast 596	com/mixpanel/android/java_websocket/WrappedByteChannel
    //   631: invokeinterface 599 1 0
    //   636: ifeq +17 -> 653
    //   639: aload_0
    //   640: getfield 139	com/mixpanel/android/java_websocket/server/WebSocketServer:iqueue	Ljava/util/List;
    //   643: aload_3
    //   644: invokeinterface 158 2 0
    //   649: pop
    //   650: aload_3
    //   651: astore 6
    //   653: aload 6
    //   655: astore_3
    //   656: aload 7
    //   658: astore 5
    //   660: aload 6
    //   662: astore_2
    //   663: aload 7
    //   665: astore 4
    //   667: aload 7
    //   669: invokevirtual 602	java/nio/channels/SelectionKey:isWritable	()Z
    //   672: ifeq -445 -> 227
    //   675: aload 6
    //   677: astore_2
    //   678: aload 7
    //   680: astore 4
    //   682: aload 7
    //   684: invokevirtual 578	java/nio/channels/SelectionKey:attachment	()Ljava/lang/Object;
    //   687: checkcast 182	com/mixpanel/android/java_websocket/WebSocketImpl
    //   690: astore 6
    //   692: aload 6
    //   694: astore_3
    //   695: aload 7
    //   697: astore 5
    //   699: aload 6
    //   701: astore_2
    //   702: aload 7
    //   704: astore 4
    //   706: aload 6
    //   708: aload 6
    //   710: getfield 566	com/mixpanel/android/java_websocket/WebSocketImpl:channel	Ljava/nio/channels/ByteChannel;
    //   713: invokestatic 606	com/mixpanel/android/java_websocket/SocketChannelIOHelper:batch	(Lcom/mixpanel/android/java_websocket/WebSocketImpl;Ljava/nio/channels/ByteChannel;)Z
    //   716: ifeq -489 -> 227
    //   719: aload 6
    //   721: astore_3
    //   722: aload 7
    //   724: astore 5
    //   726: aload 6
    //   728: astore_2
    //   729: aload 7
    //   731: astore 4
    //   733: aload 7
    //   735: invokevirtual 539	java/nio/channels/SelectionKey:isValid	()Z
    //   738: ifeq -511 -> 227
    //   741: aload 6
    //   743: astore_2
    //   744: aload 7
    //   746: astore 4
    //   748: aload 7
    //   750: iconst_1
    //   751: invokevirtual 445	java/nio/channels/SelectionKey:interestOps	(I)Ljava/nio/channels/SelectionKey;
    //   754: pop
    //   755: aload 6
    //   757: astore_3
    //   758: aload 7
    //   760: astore 5
    //   762: goto -535 -> 227
    //   765: astore_3
    //   766: aload 4
    //   768: ifnull +8 -> 776
    //   771: aload 4
    //   773: invokevirtual 547	java/nio/channels/SelectionKey:cancel	()V
    //   776: aload_0
    //   777: aload 4
    //   779: aload_2
    //   780: aload_3
    //   781: invokespecial 608	com/mixpanel/android/java_websocket/server/WebSocketServer:handleIOException	(Ljava/nio/channels/SelectionKey;Lcom/mixpanel/android/java_websocket/WebSocket;Ljava/io/IOException;)V
    //   784: goto -616 -> 168
    //   787: astore_2
    //   788: aload_0
    //   789: aconst_null
    //   790: aload_2
    //   791: invokespecial 178	com/mixpanel/android/java_websocket/server/WebSocketServer:handleFatal	(Lcom/mixpanel/android/java_websocket/WebSocket;Ljava/lang/Exception;)V
    //   794: aload_0
    //   795: getfield 144	com/mixpanel/android/java_websocket/server/WebSocketServer:decoders	Ljava/util/List;
    //   798: ifnull +430 -> 1228
    //   801: aload_0
    //   802: getfield 144	com/mixpanel/android/java_websocket/server/WebSocketServer:decoders	Ljava/util/List;
    //   805: invokeinterface 571 1 0
    //   810: astore_2
    //   811: aload_2
    //   812: invokeinterface 533 1 0
    //   817: ifeq +411 -> 1228
    //   820: aload_2
    //   821: invokeinterface 536 1 0
    //   826: checkcast 11	com/mixpanel/android/java_websocket/server/WebSocketServer$WebSocketWorker
    //   829: invokevirtual 572	com/mixpanel/android/java_websocket/server/WebSocketServer$WebSocketWorker:interrupt	()V
    //   832: goto -21 -> 811
    //   835: aload_0
    //   836: aload 5
    //   838: invokespecial 172	com/mixpanel/android/java_websocket/server/WebSocketServer:pushBuffer	(Ljava/nio/ByteBuffer;)V
    //   841: aload_3
    //   842: astore 6
    //   844: goto -191 -> 653
    //   847: astore 6
    //   849: aload_3
    //   850: astore_2
    //   851: aload 7
    //   853: astore 4
    //   855: aload_0
    //   856: aload 5
    //   858: invokespecial 172	com/mixpanel/android/java_websocket/server/WebSocketServer:pushBuffer	(Ljava/nio/ByteBuffer;)V
    //   861: aload_3
    //   862: astore_2
    //   863: aload 7
    //   865: astore 4
    //   867: aload 6
    //   869: athrow
    //   870: astore_2
    //   871: aload_0
    //   872: getfield 144	com/mixpanel/android/java_websocket/server/WebSocketServer:decoders	Ljava/util/List;
    //   875: ifnull +266 -> 1141
    //   878: aload_0
    //   879: getfield 144	com/mixpanel/android/java_websocket/server/WebSocketServer:decoders	Ljava/util/List;
    //   882: invokeinterface 571 1 0
    //   887: astore_2
    //   888: aload_2
    //   889: invokeinterface 533 1 0
    //   894: ifeq +247 -> 1141
    //   897: aload_2
    //   898: invokeinterface 536 1 0
    //   903: checkcast 11	com/mixpanel/android/java_websocket/server/WebSocketServer$WebSocketWorker
    //   906: invokevirtual 572	com/mixpanel/android/java_websocket/server/WebSocketServer$WebSocketWorker:interrupt	()V
    //   909: goto -21 -> 888
    //   912: aload_0
    //   913: aload 5
    //   915: invokespecial 172	com/mixpanel/android/java_websocket/server/WebSocketServer:pushBuffer	(Ljava/nio/ByteBuffer;)V
    //   918: aload_3
    //   919: astore 6
    //   921: goto -268 -> 653
    //   924: astore_2
    //   925: aload_0
    //   926: getfield 144	com/mixpanel/android/java_websocket/server/WebSocketServer:decoders	Ljava/util/List;
    //   929: ifnull +322 -> 1251
    //   932: aload_0
    //   933: getfield 144	com/mixpanel/android/java_websocket/server/WebSocketServer:decoders	Ljava/util/List;
    //   936: invokeinterface 571 1 0
    //   941: astore_3
    //   942: aload_3
    //   943: invokeinterface 533 1 0
    //   948: ifeq +303 -> 1251
    //   951: aload_3
    //   952: invokeinterface 536 1 0
    //   957: checkcast 11	com/mixpanel/android/java_websocket/server/WebSocketServer$WebSocketWorker
    //   960: invokevirtual 572	com/mixpanel/android/java_websocket/server/WebSocketServer$WebSocketWorker:interrupt	()V
    //   963: goto -21 -> 942
    //   966: aload 6
    //   968: astore_2
    //   969: aload 5
    //   971: astore 4
    //   973: aload_0
    //   974: getfield 139	com/mixpanel/android/java_websocket/server/WebSocketServer:iqueue	Ljava/util/List;
    //   977: invokeinterface 611 1 0
    //   982: ifne -814 -> 168
    //   985: aload 6
    //   987: astore_2
    //   988: aload 5
    //   990: astore 4
    //   992: aload_0
    //   993: getfield 139	com/mixpanel/android/java_websocket/server/WebSocketServer:iqueue	Ljava/util/List;
    //   996: iconst_0
    //   997: invokeinterface 613 2 0
    //   1002: checkcast 182	com/mixpanel/android/java_websocket/WebSocketImpl
    //   1005: astore 6
    //   1007: aload 6
    //   1009: astore_2
    //   1010: aload 5
    //   1012: astore 4
    //   1014: aload 6
    //   1016: getfield 566	com/mixpanel/android/java_websocket/WebSocketImpl:channel	Ljava/nio/channels/ByteChannel;
    //   1019: checkcast 596	com/mixpanel/android/java_websocket/WrappedByteChannel
    //   1022: astore 7
    //   1024: aload 6
    //   1026: astore_2
    //   1027: aload 5
    //   1029: astore 4
    //   1031: aload_0
    //   1032: invokespecial 580	com/mixpanel/android/java_websocket/server/WebSocketServer:takeBuffer	()Ljava/nio/ByteBuffer;
    //   1035: astore_3
    //   1036: aload_3
    //   1037: aload 6
    //   1039: aload 7
    //   1041: invokestatic 617	com/mixpanel/android/java_websocket/SocketChannelIOHelper:readMore	(Ljava/nio/ByteBuffer;Lcom/mixpanel/android/java_websocket/WebSocketImpl;Lcom/mixpanel/android/java_websocket/WrappedByteChannel;)Z
    //   1044: ifeq +15 -> 1059
    //   1047: aload_0
    //   1048: getfield 139	com/mixpanel/android/java_websocket/server/WebSocketServer:iqueue	Ljava/util/List;
    //   1051: aload 6
    //   1053: invokeinterface 158 2 0
    //   1058: pop
    //   1059: aload_3
    //   1060: invokevirtual 589	java/nio/ByteBuffer:hasRemaining	()Z
    //   1063: ifeq +47 -> 1110
    //   1066: aload 6
    //   1068: getfield 592	com/mixpanel/android/java_websocket/WebSocketImpl:inQueue	Ljava/util/concurrent/BlockingQueue;
    //   1071: aload_3
    //   1072: invokeinterface 276 2 0
    //   1077: aload_0
    //   1078: aload 6
    //   1080: invokespecial 594	com/mixpanel/android/java_websocket/server/WebSocketServer:queue	(Lcom/mixpanel/android/java_websocket/WebSocketImpl;)V
    //   1083: goto -117 -> 966
    //   1086: astore 7
    //   1088: aload 6
    //   1090: astore_2
    //   1091: aload 5
    //   1093: astore 4
    //   1095: aload_0
    //   1096: aload_3
    //   1097: invokespecial 172	com/mixpanel/android/java_websocket/server/WebSocketServer:pushBuffer	(Ljava/nio/ByteBuffer;)V
    //   1100: aload 6
    //   1102: astore_2
    //   1103: aload 5
    //   1105: astore 4
    //   1107: aload 7
    //   1109: athrow
    //   1110: aload_0
    //   1111: aload_3
    //   1112: invokespecial 172	com/mixpanel/android/java_websocket/server/WebSocketServer:pushBuffer	(Ljava/nio/ByteBuffer;)V
    //   1115: goto -149 -> 966
    //   1118: aload_0
    //   1119: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   1122: ifnull +155 -> 1277
    //   1125: aload_0
    //   1126: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   1129: invokevirtual 618	java/nio/channels/ServerSocketChannel:close	()V
    //   1132: return
    //   1133: astore_2
    //   1134: aload_0
    //   1135: aconst_null
    //   1136: aload_2
    //   1137: invokevirtual 203	com/mixpanel/android/java_websocket/server/WebSocketServer:onError	(Lcom/mixpanel/android/java_websocket/WebSocket;Ljava/lang/Exception;)V
    //   1140: return
    //   1141: aload_0
    //   1142: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   1145: ifnull +132 -> 1277
    //   1148: aload_0
    //   1149: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   1152: invokevirtual 618	java/nio/channels/ServerSocketChannel:close	()V
    //   1155: return
    //   1156: astore_2
    //   1157: aload_0
    //   1158: aconst_null
    //   1159: aload_2
    //   1160: invokevirtual 203	com/mixpanel/android/java_websocket/server/WebSocketServer:onError	(Lcom/mixpanel/android/java_websocket/WebSocket;Ljava/lang/Exception;)V
    //   1163: return
    //   1164: aload_0
    //   1165: getfield 144	com/mixpanel/android/java_websocket/server/WebSocketServer:decoders	Ljava/util/List;
    //   1168: ifnull +37 -> 1205
    //   1171: aload_0
    //   1172: getfield 144	com/mixpanel/android/java_websocket/server/WebSocketServer:decoders	Ljava/util/List;
    //   1175: invokeinterface 571 1 0
    //   1180: astore_2
    //   1181: aload_2
    //   1182: invokeinterface 533 1 0
    //   1187: ifeq +18 -> 1205
    //   1190: aload_2
    //   1191: invokeinterface 536 1 0
    //   1196: checkcast 11	com/mixpanel/android/java_websocket/server/WebSocketServer$WebSocketWorker
    //   1199: invokevirtual 572	com/mixpanel/android/java_websocket/server/WebSocketServer$WebSocketWorker:interrupt	()V
    //   1202: goto -21 -> 1181
    //   1205: aload_0
    //   1206: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   1209: ifnull +68 -> 1277
    //   1212: aload_0
    //   1213: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   1216: invokevirtual 618	java/nio/channels/ServerSocketChannel:close	()V
    //   1219: return
    //   1220: astore_2
    //   1221: aload_0
    //   1222: aconst_null
    //   1223: aload_2
    //   1224: invokevirtual 203	com/mixpanel/android/java_websocket/server/WebSocketServer:onError	(Lcom/mixpanel/android/java_websocket/WebSocket;Ljava/lang/Exception;)V
    //   1227: return
    //   1228: aload_0
    //   1229: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   1232: ifnull +45 -> 1277
    //   1235: aload_0
    //   1236: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   1239: invokevirtual 618	java/nio/channels/ServerSocketChannel:close	()V
    //   1242: return
    //   1243: astore_2
    //   1244: aload_0
    //   1245: aconst_null
    //   1246: aload_2
    //   1247: invokevirtual 203	com/mixpanel/android/java_websocket/server/WebSocketServer:onError	(Lcom/mixpanel/android/java_websocket/WebSocket;Ljava/lang/Exception;)V
    //   1250: return
    //   1251: aload_0
    //   1252: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   1255: ifnull +10 -> 1265
    //   1258: aload_0
    //   1259: getfield 361	com/mixpanel/android/java_websocket/server/WebSocketServer:server	Ljava/nio/channels/ServerSocketChannel;
    //   1262: invokevirtual 618	java/nio/channels/ServerSocketChannel:close	()V
    //   1265: aload_2
    //   1266: athrow
    //   1267: astore_3
    //   1268: aload_0
    //   1269: aconst_null
    //   1270: aload_3
    //   1271: invokevirtual 203	com/mixpanel/android/java_websocket/server/WebSocketServer:onError	(Lcom/mixpanel/android/java_websocket/WebSocket;Ljava/lang/Exception;)V
    //   1274: goto -9 -> 1265
    //   1277: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1278	0	this	WebSocketServer
    //   175	2	1	bool	boolean
    //   43	4	2	localObject1	Object
    //   126	187	2	localObject2	Object
    //   329	1	2	localCancelledKeyException	CancelledKeyException
    //   333	4	2	localIOException1	IOException
    //   342	119	2	localObject3	Object
    //   478	1	2	localClosedByInterruptException	java.nio.channels.ClosedByInterruptException
    //   495	285	2	localObject4	Object
    //   787	4	2	localRuntimeException	RuntimeException
    //   810	53	2	localObject5	Object
    //   870	1	2	localInterruptedException	InterruptedException
    //   887	11	2	localIterator1	java.util.Iterator
    //   924	1	2	localObject6	Object
    //   968	135	2	localObject7	Object
    //   1133	4	2	localIOException2	IOException
    //   1156	4	2	localIOException3	IOException
    //   1180	11	2	localIterator2	java.util.Iterator
    //   1220	4	2	localIOException4	IOException
    //   1243	23	2	localIOException5	IOException
    //   190	568	3	localObject8	Object
    //   765	154	3	localIOException6	IOException
    //   941	171	3	localObject9	Object
    //   1267	4	3	localIOException7	IOException
    //   196	910	4	localObject10	Object
    //   184	920	5	localObject11	Object
    //   181	662	6	localObject12	Object
    //   847	21	6	localIOException8	IOException
    //   919	182	6	localObject13	Object
    //   187	853	7	localObject14	Object
    //   1086	22	7	localIOException9	IOException
    //   225	377	8	localIterator3	java.util.Iterator
    // Exception table:
    //   from	to	target	type
    //   2	43	43	finally
    //   44	46	43	finally
    //   48	67	43	finally
    //   68	70	43	finally
    //   198	206	329	java/nio/channels/CancelledKeyException
    //   213	227	329	java/nio/channels/CancelledKeyException
    //   236	246	329	java/nio/channels/CancelledKeyException
    //   252	264	329	java/nio/channels/CancelledKeyException
    //   274	282	329	java/nio/channels/CancelledKeyException
    //   288	296	329	java/nio/channels/CancelledKeyException
    //   302	311	329	java/nio/channels/CancelledKeyException
    //   317	322	329	java/nio/channels/CancelledKeyException
    //   347	356	329	java/nio/channels/CancelledKeyException
    //   362	369	329	java/nio/channels/CancelledKeyException
    //   375	396	329	java/nio/channels/CancelledKeyException
    //   402	419	329	java/nio/channels/CancelledKeyException
    //   425	446	329	java/nio/channels/CancelledKeyException
    //   452	459	329	java/nio/channels/CancelledKeyException
    //   465	471	329	java/nio/channels/CancelledKeyException
    //   529	537	329	java/nio/channels/CancelledKeyException
    //   543	552	329	java/nio/channels/CancelledKeyException
    //   558	564	329	java/nio/channels/CancelledKeyException
    //   564	608	329	java/nio/channels/CancelledKeyException
    //   611	621	329	java/nio/channels/CancelledKeyException
    //   624	650	329	java/nio/channels/CancelledKeyException
    //   667	675	329	java/nio/channels/CancelledKeyException
    //   682	692	329	java/nio/channels/CancelledKeyException
    //   706	719	329	java/nio/channels/CancelledKeyException
    //   733	741	329	java/nio/channels/CancelledKeyException
    //   748	755	329	java/nio/channels/CancelledKeyException
    //   835	841	329	java/nio/channels/CancelledKeyException
    //   855	861	329	java/nio/channels/CancelledKeyException
    //   867	870	329	java/nio/channels/CancelledKeyException
    //   912	918	329	java/nio/channels/CancelledKeyException
    //   973	985	329	java/nio/channels/CancelledKeyException
    //   992	1007	329	java/nio/channels/CancelledKeyException
    //   1014	1024	329	java/nio/channels/CancelledKeyException
    //   1031	1036	329	java/nio/channels/CancelledKeyException
    //   1036	1059	329	java/nio/channels/CancelledKeyException
    //   1059	1083	329	java/nio/channels/CancelledKeyException
    //   1095	1100	329	java/nio/channels/CancelledKeyException
    //   1107	1110	329	java/nio/channels/CancelledKeyException
    //   1110	1115	329	java/nio/channels/CancelledKeyException
    //   103	168	333	java/io/IOException
    //   198	206	478	java/nio/channels/ClosedByInterruptException
    //   213	227	478	java/nio/channels/ClosedByInterruptException
    //   236	246	478	java/nio/channels/ClosedByInterruptException
    //   252	264	478	java/nio/channels/ClosedByInterruptException
    //   274	282	478	java/nio/channels/ClosedByInterruptException
    //   288	296	478	java/nio/channels/ClosedByInterruptException
    //   302	311	478	java/nio/channels/ClosedByInterruptException
    //   317	322	478	java/nio/channels/ClosedByInterruptException
    //   347	356	478	java/nio/channels/ClosedByInterruptException
    //   362	369	478	java/nio/channels/ClosedByInterruptException
    //   375	396	478	java/nio/channels/ClosedByInterruptException
    //   402	419	478	java/nio/channels/ClosedByInterruptException
    //   425	446	478	java/nio/channels/ClosedByInterruptException
    //   452	459	478	java/nio/channels/ClosedByInterruptException
    //   465	471	478	java/nio/channels/ClosedByInterruptException
    //   529	537	478	java/nio/channels/ClosedByInterruptException
    //   543	552	478	java/nio/channels/ClosedByInterruptException
    //   558	564	478	java/nio/channels/ClosedByInterruptException
    //   564	608	478	java/nio/channels/ClosedByInterruptException
    //   611	621	478	java/nio/channels/ClosedByInterruptException
    //   624	650	478	java/nio/channels/ClosedByInterruptException
    //   667	675	478	java/nio/channels/ClosedByInterruptException
    //   682	692	478	java/nio/channels/ClosedByInterruptException
    //   706	719	478	java/nio/channels/ClosedByInterruptException
    //   733	741	478	java/nio/channels/ClosedByInterruptException
    //   748	755	478	java/nio/channels/ClosedByInterruptException
    //   835	841	478	java/nio/channels/ClosedByInterruptException
    //   855	861	478	java/nio/channels/ClosedByInterruptException
    //   867	870	478	java/nio/channels/ClosedByInterruptException
    //   912	918	478	java/nio/channels/ClosedByInterruptException
    //   973	985	478	java/nio/channels/ClosedByInterruptException
    //   992	1007	478	java/nio/channels/ClosedByInterruptException
    //   1014	1024	478	java/nio/channels/ClosedByInterruptException
    //   1031	1036	478	java/nio/channels/ClosedByInterruptException
    //   1036	1059	478	java/nio/channels/ClosedByInterruptException
    //   1059	1083	478	java/nio/channels/ClosedByInterruptException
    //   1095	1100	478	java/nio/channels/ClosedByInterruptException
    //   1107	1110	478	java/nio/channels/ClosedByInterruptException
    //   1110	1115	478	java/nio/channels/ClosedByInterruptException
    //   198	206	765	java/io/IOException
    //   213	227	765	java/io/IOException
    //   236	246	765	java/io/IOException
    //   252	264	765	java/io/IOException
    //   274	282	765	java/io/IOException
    //   288	296	765	java/io/IOException
    //   302	311	765	java/io/IOException
    //   317	322	765	java/io/IOException
    //   347	356	765	java/io/IOException
    //   362	369	765	java/io/IOException
    //   375	396	765	java/io/IOException
    //   402	419	765	java/io/IOException
    //   425	446	765	java/io/IOException
    //   452	459	765	java/io/IOException
    //   465	471	765	java/io/IOException
    //   529	537	765	java/io/IOException
    //   543	552	765	java/io/IOException
    //   558	564	765	java/io/IOException
    //   667	675	765	java/io/IOException
    //   682	692	765	java/io/IOException
    //   706	719	765	java/io/IOException
    //   733	741	765	java/io/IOException
    //   748	755	765	java/io/IOException
    //   855	861	765	java/io/IOException
    //   867	870	765	java/io/IOException
    //   973	985	765	java/io/IOException
    //   992	1007	765	java/io/IOException
    //   1014	1024	765	java/io/IOException
    //   1031	1036	765	java/io/IOException
    //   1095	1100	765	java/io/IOException
    //   1107	1110	765	java/io/IOException
    //   168	176	787	java/lang/RuntimeException
    //   198	206	787	java/lang/RuntimeException
    //   213	227	787	java/lang/RuntimeException
    //   236	246	787	java/lang/RuntimeException
    //   252	264	787	java/lang/RuntimeException
    //   274	282	787	java/lang/RuntimeException
    //   288	296	787	java/lang/RuntimeException
    //   302	311	787	java/lang/RuntimeException
    //   317	322	787	java/lang/RuntimeException
    //   347	356	787	java/lang/RuntimeException
    //   362	369	787	java/lang/RuntimeException
    //   375	396	787	java/lang/RuntimeException
    //   402	419	787	java/lang/RuntimeException
    //   425	446	787	java/lang/RuntimeException
    //   452	459	787	java/lang/RuntimeException
    //   465	471	787	java/lang/RuntimeException
    //   529	537	787	java/lang/RuntimeException
    //   543	552	787	java/lang/RuntimeException
    //   558	564	787	java/lang/RuntimeException
    //   564	608	787	java/lang/RuntimeException
    //   611	621	787	java/lang/RuntimeException
    //   624	650	787	java/lang/RuntimeException
    //   667	675	787	java/lang/RuntimeException
    //   682	692	787	java/lang/RuntimeException
    //   706	719	787	java/lang/RuntimeException
    //   733	741	787	java/lang/RuntimeException
    //   748	755	787	java/lang/RuntimeException
    //   771	776	787	java/lang/RuntimeException
    //   776	784	787	java/lang/RuntimeException
    //   835	841	787	java/lang/RuntimeException
    //   855	861	787	java/lang/RuntimeException
    //   867	870	787	java/lang/RuntimeException
    //   912	918	787	java/lang/RuntimeException
    //   973	985	787	java/lang/RuntimeException
    //   992	1007	787	java/lang/RuntimeException
    //   1014	1024	787	java/lang/RuntimeException
    //   1031	1036	787	java/lang/RuntimeException
    //   1036	1059	787	java/lang/RuntimeException
    //   1059	1083	787	java/lang/RuntimeException
    //   1095	1100	787	java/lang/RuntimeException
    //   1107	1110	787	java/lang/RuntimeException
    //   1110	1115	787	java/lang/RuntimeException
    //   564	608	847	java/io/IOException
    //   611	621	847	java/io/IOException
    //   624	650	847	java/io/IOException
    //   835	841	847	java/io/IOException
    //   912	918	847	java/io/IOException
    //   198	206	870	java/lang/InterruptedException
    //   213	227	870	java/lang/InterruptedException
    //   236	246	870	java/lang/InterruptedException
    //   252	264	870	java/lang/InterruptedException
    //   274	282	870	java/lang/InterruptedException
    //   288	296	870	java/lang/InterruptedException
    //   302	311	870	java/lang/InterruptedException
    //   317	322	870	java/lang/InterruptedException
    //   347	356	870	java/lang/InterruptedException
    //   362	369	870	java/lang/InterruptedException
    //   375	396	870	java/lang/InterruptedException
    //   402	419	870	java/lang/InterruptedException
    //   425	446	870	java/lang/InterruptedException
    //   452	459	870	java/lang/InterruptedException
    //   465	471	870	java/lang/InterruptedException
    //   529	537	870	java/lang/InterruptedException
    //   543	552	870	java/lang/InterruptedException
    //   558	564	870	java/lang/InterruptedException
    //   564	608	870	java/lang/InterruptedException
    //   611	621	870	java/lang/InterruptedException
    //   624	650	870	java/lang/InterruptedException
    //   667	675	870	java/lang/InterruptedException
    //   682	692	870	java/lang/InterruptedException
    //   706	719	870	java/lang/InterruptedException
    //   733	741	870	java/lang/InterruptedException
    //   748	755	870	java/lang/InterruptedException
    //   835	841	870	java/lang/InterruptedException
    //   855	861	870	java/lang/InterruptedException
    //   867	870	870	java/lang/InterruptedException
    //   912	918	870	java/lang/InterruptedException
    //   973	985	870	java/lang/InterruptedException
    //   992	1007	870	java/lang/InterruptedException
    //   1014	1024	870	java/lang/InterruptedException
    //   1031	1036	870	java/lang/InterruptedException
    //   1036	1059	870	java/lang/InterruptedException
    //   1059	1083	870	java/lang/InterruptedException
    //   1095	1100	870	java/lang/InterruptedException
    //   1107	1110	870	java/lang/InterruptedException
    //   1110	1115	870	java/lang/InterruptedException
    //   168	176	924	finally
    //   198	206	924	finally
    //   213	227	924	finally
    //   236	246	924	finally
    //   252	264	924	finally
    //   274	282	924	finally
    //   288	296	924	finally
    //   302	311	924	finally
    //   317	322	924	finally
    //   347	356	924	finally
    //   362	369	924	finally
    //   375	396	924	finally
    //   402	419	924	finally
    //   425	446	924	finally
    //   452	459	924	finally
    //   465	471	924	finally
    //   529	537	924	finally
    //   543	552	924	finally
    //   558	564	924	finally
    //   564	608	924	finally
    //   611	621	924	finally
    //   624	650	924	finally
    //   667	675	924	finally
    //   682	692	924	finally
    //   706	719	924	finally
    //   733	741	924	finally
    //   748	755	924	finally
    //   771	776	924	finally
    //   776	784	924	finally
    //   788	794	924	finally
    //   835	841	924	finally
    //   855	861	924	finally
    //   867	870	924	finally
    //   912	918	924	finally
    //   973	985	924	finally
    //   992	1007	924	finally
    //   1014	1024	924	finally
    //   1031	1036	924	finally
    //   1036	1059	924	finally
    //   1059	1083	924	finally
    //   1095	1100	924	finally
    //   1107	1110	924	finally
    //   1110	1115	924	finally
    //   1036	1059	1086	java/io/IOException
    //   1059	1083	1086	java/io/IOException
    //   1110	1115	1086	java/io/IOException
    //   1125	1132	1133	java/io/IOException
    //   1148	1155	1156	java/io/IOException
    //   1212	1219	1220	java/io/IOException
    //   1235	1242	1243	java/io/IOException
    //   1258	1265	1267	java/io/IOException
  }
  
  public final void setWebSocketFactory(WebSocketServerFactory paramWebSocketServerFactory)
  {
    this.wsf = paramWebSocketServerFactory;
  }
  
  public void start()
  {
    if (this.selectorthread != null) {
      throw new IllegalStateException(getClass().getName() + " can only be started once.");
    }
    new Thread(this).start();
  }
  
  public void stop()
    throws IOException, InterruptedException
  {
    stop(0);
  }
  
  /* Error */
  public void stop(int paramInt)
    throws InterruptedException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 103	com/mixpanel/android/java_websocket/server/WebSocketServer:isclosed	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   4: iconst_0
    //   5: iconst_1
    //   6: invokevirtual 630	java/util/concurrent/atomic/AtomicBoolean:compareAndSet	(ZZ)Z
    //   9: ifne +4 -> 13
    //   12: return
    //   13: aload_0
    //   14: getfield 134	com/mixpanel/android/java_websocket/server/WebSocketServer:connections	Ljava/util/Collection;
    //   17: astore_3
    //   18: aload_3
    //   19: monitorenter
    //   20: new 141	java/util/ArrayList
    //   23: dup
    //   24: aload_0
    //   25: getfield 134	com/mixpanel/android/java_websocket/server/WebSocketServer:connections	Ljava/util/Collection;
    //   28: invokespecial 633	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
    //   31: astore_2
    //   32: aload_3
    //   33: monitorexit
    //   34: aload_2
    //   35: invokeinterface 571 1 0
    //   40: astore_3
    //   41: aload_3
    //   42: invokeinterface 533 1 0
    //   47: ifeq +28 -> 75
    //   50: aload_3
    //   51: invokeinterface 536 1 0
    //   56: checkcast 223	com/mixpanel/android/java_websocket/WebSocket
    //   59: sipush 1001
    //   62: invokeinterface 309 2 0
    //   67: goto -26 -> 41
    //   70: astore_2
    //   71: aload_3
    //   72: monitorexit
    //   73: aload_2
    //   74: athrow
    //   75: aload_0
    //   76: monitorenter
    //   77: aload_0
    //   78: getfield 457	com/mixpanel/android/java_websocket/server/WebSocketServer:selectorthread	Ljava/lang/Thread;
    //   81: ifnull +55 -> 136
    //   84: invokestatic 212	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   87: aload_0
    //   88: getfield 457	com/mixpanel/android/java_websocket/server/WebSocketServer:selectorthread	Ljava/lang/Thread;
    //   91: if_acmpeq +3 -> 94
    //   94: aload_0
    //   95: getfield 457	com/mixpanel/android/java_websocket/server/WebSocketServer:selectorthread	Ljava/lang/Thread;
    //   98: invokestatic 212	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   101: if_acmpeq +35 -> 136
    //   104: aload_2
    //   105: invokeinterface 283 1 0
    //   110: ifle +12 -> 122
    //   113: aload_0
    //   114: getfield 457	com/mixpanel/android/java_websocket/server/WebSocketServer:selectorthread	Ljava/lang/Thread;
    //   117: iload_1
    //   118: i2l
    //   119: invokevirtual 637	java/lang/Thread:join	(J)V
    //   122: aload_0
    //   123: getfield 457	com/mixpanel/android/java_websocket/server/WebSocketServer:selectorthread	Ljava/lang/Thread;
    //   126: invokevirtual 215	java/lang/Thread:interrupt	()V
    //   129: aload_0
    //   130: getfield 457	com/mixpanel/android/java_websocket/server/WebSocketServer:selectorthread	Ljava/lang/Thread;
    //   133: invokevirtual 639	java/lang/Thread:join	()V
    //   136: aload_0
    //   137: monitorexit
    //   138: return
    //   139: astore_2
    //   140: aload_0
    //   141: monitorexit
    //   142: aload_2
    //   143: athrow
    //   144: astore_2
    //   145: goto -74 -> 71
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	148	0	this	WebSocketServer
    //   0	148	1	paramInt	int
    //   31	4	2	localArrayList	ArrayList
    //   70	35	2	localObject1	Object
    //   139	4	2	localObject2	Object
    //   144	1	2	localObject3	Object
    //   17	55	3	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   20	32	70	finally
    //   71	73	70	finally
    //   77	94	139	finally
    //   94	122	139	finally
    //   122	136	139	finally
    //   136	138	139	finally
    //   140	142	139	finally
    //   32	34	144	finally
  }
  
  public static abstract interface WebSocketServerFactory
    extends WebSocketFactory
  {
    public abstract WebSocketImpl createWebSocket(WebSocketAdapter paramWebSocketAdapter, Draft paramDraft, Socket paramSocket);
    
    public abstract WebSocketImpl createWebSocket(WebSocketAdapter paramWebSocketAdapter, List<Draft> paramList, Socket paramSocket);
    
    public abstract ByteChannel wrapChannel(SocketChannel paramSocketChannel, SelectionKey paramSelectionKey)
      throws IOException;
  }
  
  public class WebSocketWorker
    extends Thread
  {
    private BlockingQueue<WebSocketImpl> iqueue = new LinkedBlockingQueue();
    
    static
    {
      if (!WebSocketServer.class.desiredAssertionStatus()) {}
      for (boolean bool = true;; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }
    
    public WebSocketWorker()
    {
      setName("WebSocketWorker-" + getId());
      setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
      {
        public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable)
        {
          Thread.getDefaultUncaughtExceptionHandler().uncaughtException(paramAnonymousThread, paramAnonymousThrowable);
        }
      });
    }
    
    public void put(WebSocketImpl paramWebSocketImpl)
      throws InterruptedException
    {
      this.iqueue.put(paramWebSocketImpl);
    }
    
    public void run()
    {
      Object localObject1 = null;
      try
      {
        localWebSocketImpl = (WebSocketImpl)this.iqueue.take();
        localObject1 = localWebSocketImpl;
        localByteBuffer = (ByteBuffer)localWebSocketImpl.inQueue.poll();
        localObject1 = localWebSocketImpl;
        if ((!$assertionsDisabled) && (localByteBuffer == null))
        {
          localObject1 = localWebSocketImpl;
          throw new AssertionError();
        }
      }
      catch (InterruptedException localInterruptedException) {}catch (RuntimeException localRuntimeException)
      {
        try
        {
          for (;;)
          {
            WebSocketImpl localWebSocketImpl;
            localWebSocketImpl.decode(localByteBuffer);
            localObject2 = localWebSocketImpl;
            WebSocketServer.this.pushBuffer(localByteBuffer);
            localObject2 = localWebSocketImpl;
          }
        }
        finally
        {
          ByteBuffer localByteBuffer;
          Object localObject2 = localRuntimeException;
          WebSocketServer.this.pushBuffer(localByteBuffer);
          localObject2 = localRuntimeException;
        }
        localRuntimeException = localRuntimeException;
        WebSocketServer.this.handleFatal((WebSocket)localObject2, localRuntimeException);
        return;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/server/WebSocketServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
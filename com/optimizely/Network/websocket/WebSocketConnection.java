package com.optimizely.Network.websocket;

import android.net.SSLCertificateSocketFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.optimizely.Optimizely;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.URI;
import java.util.Map;
import javax.net.SocketFactory;

public class WebSocketConnection
  implements WebSocket
{
  private static final String TAG = WebSocketConnection.class.getName();
  private static final String[] WSS_URI_SCHEMES = { "https", "wss" };
  private static final String WS_READER = "WebSocketReader";
  private static final String[] WS_URI_SCHEMES = { "http", "ws" };
  private static final String WS_WRITER = "WebSocketWriter";
  @NonNull
  private final Handler mHandler;
  private boolean mPreviousConnection = false;
  @Nullable
  private Socket mSocket;
  @Nullable
  SocketThread mSocketThread;
  private WeakReference<WebSocket.WebSocketConnectionObserver> mWebSocketConnectionObserver;
  private WebSocketOptions mWebSocketOptions;
  @Nullable
  WebSocketReader mWebSocketReader;
  private String[] mWebSocketSubprotocols;
  @Nullable
  private URI mWebSocketURI;
  @Nullable
  WebSocketWriter mWebSocketWriter;
  private final Optimizely optimizely;
  
  public WebSocketConnection(Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
    Log.d(TAG, "WebSocket connection created.");
    this.mHandler = new ThreadHandler(this);
  }
  
  private void connect()
  {
    this.mSocketThread = new SocketThread(this.mWebSocketURI, this.mWebSocketOptions);
    this.mSocketThread.start();
    try
    {
      synchronized (this.mSocketThread)
      {
        this.mSocketThread.wait();
        this.mSocketThread.getHandler().post(new Runnable()
        {
          public void run()
          {
            WebSocketConnection.this.mSocketThread.startConnection();
          }
        });
      }
      try
      {
        synchronized (this.mSocketThread)
        {
          this.mSocketThread.wait();
          this.mSocket = this.mSocketThread.getSocket();
          if (this.mSocket == null)
          {
            onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification.CANNOT_CONNECT, this.mSocketThread.getFailureMessage());
            return;
            localObject2 = finally;
            throw ((Throwable)localObject2);
          }
        }
        if (this.mSocket.isConnected()) {
          try
          {
            createReader();
            createWriter();
            ??? = new URI("https://www.optimizelysockets.com/");
            ??? = new WebSocketMessage.ClientHandshake(this.mWebSocketURI, (URI)???, this.mWebSocketSubprotocols);
            this.mWebSocketWriter.forward(???);
            return;
          }
          catch (Exception localException)
          {
            onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification.INTERNAL_ERROR, localException.getLocalizedMessage());
            return;
          }
        }
        onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification.CANNOT_CONNECT, "could not connect to WebSockets server");
        return;
      }
      catch (InterruptedException localInterruptedException1)
      {
        for (;;) {}
      }
    }
    catch (InterruptedException localInterruptedException2)
    {
      for (;;) {}
    }
  }
  
  private void connect(@Nullable URI paramURI, String[] paramArrayOfString, WebSocket.WebSocketConnectionObserver paramWebSocketConnectionObserver, @NonNull WebSocketOptions paramWebSocketOptions)
    throws WebSocketException
  {
    if ((this.mSocket != null) && (this.mSocket.isConnected())) {
      throw new WebSocketException("already connected");
    }
    if (paramURI == null) {
      throw new WebSocketException("WebSockets URI null.");
    }
    this.mWebSocketURI = paramURI;
    if ((!isSecureScheme(this.mWebSocketURI.getScheme())) && (!isNonSecureScheme(this.mWebSocketURI.getScheme()))) {
      throw new WebSocketException("unsupported scheme for WebSockets URI");
    }
    this.mWebSocketSubprotocols = paramArrayOfString;
    this.mWebSocketConnectionObserver = new WeakReference(paramWebSocketConnectionObserver);
    this.mWebSocketOptions = new WebSocketOptions(paramWebSocketOptions);
    connect();
  }
  
  private void createReader()
  {
    this.mWebSocketReader = new WebSocketReader(this.mHandler, this.mSocket, this.mWebSocketOptions, "WebSocketReader", this.optimizely);
    this.mWebSocketReader.start();
    try
    {
      synchronized (this.mWebSocketReader)
      {
        this.mWebSocketReader.wait();
        Log.d(TAG, "WebSocket reader created and started.");
        return;
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;) {}
    }
  }
  
  private void createWriter()
  {
    this.mWebSocketWriter = new WebSocketWriter(this.mHandler, this.mSocket, this.mWebSocketOptions, "WebSocketWriter", this.optimizely);
    this.mWebSocketWriter.start();
    try
    {
      synchronized (this.mWebSocketWriter)
      {
        this.mWebSocketWriter.wait();
        Log.d(TAG, "WebSocket writer created and started.");
        return;
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;) {}
    }
  }
  
  /* Error */
  private void failConnection(@NonNull WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString)
  {
    // Byte code:
    //   0: getstatic 65	com/optimizely/Network/websocket/WebSocketConnection:TAG	Ljava/lang/String;
    //   3: ldc_w 258
    //   6: iconst_2
    //   7: anewarray 4	java/lang/Object
    //   10: dup
    //   11: iconst_0
    //   12: aload_1
    //   13: invokevirtual 261	com/optimizely/Network/websocket/WebSocket$WebSocketConnectionObserver$WebSocketCloseNotification:toString	()Ljava/lang/String;
    //   16: aastore
    //   17: dup
    //   18: iconst_1
    //   19: aload_2
    //   20: aastore
    //   21: invokestatic 265	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   24: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   27: pop
    //   28: aload_0
    //   29: getfield 248	com/optimizely/Network/websocket/WebSocketConnection:mWebSocketReader	Lcom/optimizely/Network/websocket/WebSocketReader;
    //   32: ifnull +155 -> 187
    //   35: aload_0
    //   36: getfield 248	com/optimizely/Network/websocket/WebSocketConnection:mWebSocketReader	Lcom/optimizely/Network/websocket/WebSocketReader;
    //   39: invokevirtual 268	com/optimizely/Network/websocket/WebSocketReader:isAlive	()Z
    //   42: ifeq +145 -> 187
    //   45: aload_0
    //   46: getfield 248	com/optimizely/Network/websocket/WebSocketConnection:mWebSocketReader	Lcom/optimizely/Network/websocket/WebSocketReader;
    //   49: invokevirtual 271	com/optimizely/Network/websocket/WebSocketReader:quit	()V
    //   52: aload_0
    //   53: getfield 248	com/optimizely/Network/websocket/WebSocketConnection:mWebSocketReader	Lcom/optimizely/Network/websocket/WebSocketReader;
    //   56: invokevirtual 274	com/optimizely/Network/websocket/WebSocketReader:join	()V
    //   59: aload_0
    //   60: getfield 196	com/optimizely/Network/websocket/WebSocketConnection:mWebSocketWriter	Lcom/optimizely/Network/websocket/WebSocketWriter;
    //   63: ifnull +145 -> 208
    //   66: aload_0
    //   67: getfield 196	com/optimizely/Network/websocket/WebSocketConnection:mWebSocketWriter	Lcom/optimizely/Network/websocket/WebSocketWriter;
    //   70: invokevirtual 275	com/optimizely/Network/websocket/WebSocketWriter:isAlive	()Z
    //   73: ifeq +135 -> 208
    //   76: aload_0
    //   77: getfield 196	com/optimizely/Network/websocket/WebSocketConnection:mWebSocketWriter	Lcom/optimizely/Network/websocket/WebSocketWriter;
    //   80: new 277	com/optimizely/Network/websocket/WebSocketMessage$Quit
    //   83: dup
    //   84: invokespecial 278	com/optimizely/Network/websocket/WebSocketMessage$Quit:<init>	()V
    //   87: invokevirtual 202	com/optimizely/Network/websocket/WebSocketWriter:forward	(Ljava/lang/Object;)V
    //   90: aload_0
    //   91: getfield 196	com/optimizely/Network/websocket/WebSocketConnection:mWebSocketWriter	Lcom/optimizely/Network/websocket/WebSocketWriter;
    //   94: invokevirtual 279	com/optimizely/Network/websocket/WebSocketWriter:join	()V
    //   97: aload_0
    //   98: getfield 156	com/optimizely/Network/websocket/WebSocketConnection:mSocket	Ljava/net/Socket;
    //   101: ifnull +120 -> 221
    //   104: aload_0
    //   105: getfield 133	com/optimizely/Network/websocket/WebSocketConnection:mSocketThread	Lcom/optimizely/Network/websocket/WebSocketConnection$SocketThread;
    //   108: invokevirtual 280	com/optimizely/Network/websocket/WebSocketConnection$SocketThread:isAlive	()Z
    //   111: ifeq +110 -> 221
    //   114: aload_0
    //   115: getfield 133	com/optimizely/Network/websocket/WebSocketConnection:mSocketThread	Lcom/optimizely/Network/websocket/WebSocketConnection$SocketThread;
    //   118: invokevirtual 143	com/optimizely/Network/websocket/WebSocketConnection$SocketThread:getHandler	()Landroid/os/Handler;
    //   121: new 8	com/optimizely/Network/websocket/WebSocketConnection$1
    //   124: dup
    //   125: aload_0
    //   126: invokespecial 281	com/optimizely/Network/websocket/WebSocketConnection$1:<init>	(Lcom/optimizely/Network/websocket/WebSocketConnection;)V
    //   129: invokevirtual 150	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   132: pop
    //   133: aload_0
    //   134: getfield 133	com/optimizely/Network/websocket/WebSocketConnection:mSocketThread	Lcom/optimizely/Network/websocket/WebSocketConnection$SocketThread;
    //   137: invokevirtual 280	com/optimizely/Network/websocket/WebSocketConnection$SocketThread:isAlive	()Z
    //   140: ifeq +22 -> 162
    //   143: aload_0
    //   144: getfield 133	com/optimizely/Network/websocket/WebSocketConnection:mSocketThread	Lcom/optimizely/Network/websocket/WebSocketConnection$SocketThread;
    //   147: invokevirtual 143	com/optimizely/Network/websocket/WebSocketConnection$SocketThread:getHandler	()Landroid/os/Handler;
    //   150: new 10	com/optimizely/Network/websocket/WebSocketConnection$2
    //   153: dup
    //   154: aload_0
    //   155: invokespecial 282	com/optimizely/Network/websocket/WebSocketConnection$2:<init>	(Lcom/optimizely/Network/websocket/WebSocketConnection;)V
    //   158: invokevirtual 150	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   161: pop
    //   162: aload_0
    //   163: aload_1
    //   164: aload_2
    //   165: invokespecial 169	com/optimizely/Network/websocket/WebSocketConnection:onClose	(Lcom/optimizely/Network/websocket/WebSocket$WebSocketConnectionObserver$WebSocketCloseNotification;Ljava/lang/String;)V
    //   168: getstatic 65	com/optimizely/Network/websocket/WebSocketConnection:TAG	Ljava/lang/String;
    //   171: ldc_w 284
    //   174: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   177: pop
    //   178: return
    //   179: astore_3
    //   180: aload_3
    //   181: invokevirtual 287	java/lang/InterruptedException:printStackTrace	()V
    //   184: goto -125 -> 59
    //   187: getstatic 65	com/optimizely/Network/websocket/WebSocketConnection:TAG	Ljava/lang/String;
    //   190: ldc_w 289
    //   193: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   196: pop
    //   197: goto -138 -> 59
    //   200: astore_3
    //   201: aload_3
    //   202: invokevirtual 287	java/lang/InterruptedException:printStackTrace	()V
    //   205: goto -108 -> 97
    //   208: getstatic 65	com/optimizely/Network/websocket/WebSocketConnection:TAG	Ljava/lang/String;
    //   211: ldc_w 291
    //   214: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   217: pop
    //   218: goto -121 -> 97
    //   221: getstatic 65	com/optimizely/Network/websocket/WebSocketConnection:TAG	Ljava/lang/String;
    //   224: ldc_w 293
    //   227: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   230: pop
    //   231: goto -98 -> 133
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	234	0	this	WebSocketConnection
    //   0	234	1	paramWebSocketCloseNotification	WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification
    //   0	234	2	paramString	String
    //   179	2	3	localInterruptedException1	InterruptedException
    //   200	2	3	localInterruptedException2	InterruptedException
    // Exception table:
    //   from	to	target	type
    //   52	59	179	java/lang/InterruptedException
    //   90	97	200	java/lang/InterruptedException
  }
  
  private void handleMessage(@NonNull Message paramMessage)
  {
    Object localObject = (WebSocket.WebSocketConnectionObserver)this.mWebSocketConnectionObserver.get();
    if ((paramMessage.obj instanceof WebSocketMessage.TextMessage))
    {
      paramMessage = (WebSocketMessage.TextMessage)paramMessage.obj;
      if (localObject != null) {
        ((WebSocket.WebSocketConnectionObserver)localObject).onTextMessage(paramMessage.mPayload);
      }
    }
    do
    {
      return;
      this.optimizely.verboseLog(true, TAG, "Could not call onTextMessage() .. handler already NULL", new Object[0]);
      return;
      if ((paramMessage.obj instanceof WebSocketMessage.RawTextMessage))
      {
        paramMessage = (WebSocketMessage.RawTextMessage)paramMessage.obj;
        if (localObject != null)
        {
          ((WebSocket.WebSocketConnectionObserver)localObject).onRawTextMessage(paramMessage.mPayload);
          return;
        }
        this.optimizely.verboseLog(true, TAG, "Could not call onRawTextMessage() .. handler already NULL", new Object[0]);
        return;
      }
      if ((paramMessage.obj instanceof WebSocketMessage.BinaryMessage))
      {
        paramMessage = (WebSocketMessage.BinaryMessage)paramMessage.obj;
        if (localObject != null)
        {
          ((WebSocket.WebSocketConnectionObserver)localObject).onBinaryMessage(paramMessage.mPayload);
          return;
        }
        this.optimizely.verboseLog(true, TAG, "Could not call onBinaryMessage() .. handler already NULL", new Object[0]);
        return;
      }
      if ((paramMessage.obj instanceof WebSocketMessage.Ping))
      {
        paramMessage = (WebSocketMessage.Ping)paramMessage.obj;
        Log.d(TAG, "WebSockets Ping received");
        localObject = new WebSocketMessage.Pong();
        ((WebSocketMessage.Pong)localObject).mPayload = paramMessage.mPayload;
        this.mWebSocketWriter.forward(localObject);
        return;
      }
      if ((paramMessage.obj instanceof WebSocketMessage.Pong))
      {
        paramMessage = (WebSocketMessage.Pong)paramMessage.obj;
        Log.d(TAG, "WebSockets Pong received " + new String(paramMessage.mPayload));
        return;
      }
      if ((paramMessage.obj instanceof WebSocketMessage.Close))
      {
        paramMessage = (WebSocketMessage.Close)paramMessage.obj;
        Log.d(TAG, String.format("WebSockets Close received (%1$d - %2$s)", new Object[] { Integer.valueOf(paramMessage.getCode()), paramMessage.getReason() }));
        this.mWebSocketWriter.forward(new WebSocketMessage.Close(1000));
        return;
      }
      if (!(paramMessage.obj instanceof WebSocketMessage.ServerHandshake)) {
        break;
      }
      paramMessage = (WebSocketMessage.ServerHandshake)paramMessage.obj;
      Log.d(TAG, "WebSocket opening handshake received");
    } while (!paramMessage.mSuccess);
    if (localObject != null) {
      ((WebSocket.WebSocketConnectionObserver)localObject).onOpen();
    }
    for (;;)
    {
      this.mPreviousConnection = true;
      return;
      this.optimizely.verboseLog(true, TAG, "Could not call onOpen() .. handler already NULL", new Object[0]);
    }
    if ((paramMessage.obj instanceof WebSocketMessage.ConnectionLost))
    {
      failConnection(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification.CONNECTION_LOST, "WebSockets connection lost");
      return;
    }
    if ((paramMessage.obj instanceof WebSocketMessage.ProtocolViolation))
    {
      failConnection(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification.PROTOCOL_ERROR, "WebSockets protocol violation");
      return;
    }
    if ((paramMessage.obj instanceof WebSocketMessage.Error))
    {
      paramMessage = (WebSocketMessage.Error)paramMessage.obj;
      failConnection(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification.INTERNAL_ERROR, "WebSockets internal error (" + paramMessage.mException.toString() + ")");
      return;
    }
    if ((paramMessage.obj instanceof WebSocketMessage.ServerError))
    {
      paramMessage = (WebSocketMessage.ServerError)paramMessage.obj;
      failConnection(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification.SERVER_ERROR, "Server error " + paramMessage.mStatusCode + " (" + paramMessage.mStatusMessage + ")");
      return;
    }
    processAppMessage(paramMessage.obj);
  }
  
  private static boolean isNonSecureScheme(@NonNull String paramString)
  {
    boolean bool2 = false;
    String[] arrayOfString = WS_URI_SCHEMES;
    int j = arrayOfString.length;
    int i = 0;
    for (;;)
    {
      boolean bool1 = bool2;
      if (i < j)
      {
        if (paramString.equalsIgnoreCase(arrayOfString[i])) {
          bool1 = true;
        }
      }
      else {
        return bool1;
      }
      i += 1;
    }
  }
  
  private static boolean isSecureScheme(@NonNull String paramString)
  {
    boolean bool2 = false;
    String[] arrayOfString = WSS_URI_SCHEMES;
    int j = arrayOfString.length;
    int i = 0;
    for (;;)
    {
      boolean bool1 = bool2;
      if (i < j)
      {
        if (paramString.equalsIgnoreCase(arrayOfString[i])) {
          bool1 = true;
        }
      }
      else {
        return bool1;
      }
      i += 1;
    }
  }
  
  private void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString)
  {
    boolean bool = false;
    if ((paramWebSocketCloseNotification == WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification.CANNOT_CONNECT) || (paramWebSocketCloseNotification == WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification.CONNECTION_LOST)) {
      bool = scheduleReconnect();
    }
    WebSocket.WebSocketConnectionObserver localWebSocketConnectionObserver = (WebSocket.WebSocketConnectionObserver)this.mWebSocketConnectionObserver.get();
    if (localWebSocketConnectionObserver != null)
    {
      if (bool) {}
      try
      {
        localWebSocketConnectionObserver.onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification.RECONNECT, paramString);
        return;
      }
      catch (Exception paramWebSocketCloseNotification)
      {
        paramWebSocketCloseNotification.printStackTrace();
        return;
      }
      localWebSocketConnectionObserver.onClose(paramWebSocketCloseNotification, paramString);
      return;
    }
    Log.d(TAG, "WebSocket WebSocketObserver null");
  }
  
  private void processAppMessage(Object paramObject) {}
  
  private boolean reconnect()
  {
    if ((!isConnected()) && (this.mWebSocketURI != null))
    {
      connect();
      return true;
    }
    return false;
  }
  
  private boolean scheduleReconnect()
  {
    int i = this.mWebSocketOptions.getReconnectInterval();
    if ((!isConnected()) && (this.mPreviousConnection) && (i > 0)) {}
    for (boolean bool = true;; bool = false)
    {
      if (bool)
      {
        Log.d(TAG, "WebSocket reconnection scheduled");
        this.mHandler.postDelayed(new Runnable()
        {
          public void run()
          {
            Log.d(WebSocketConnection.TAG, "WebSocket reconnecting...");
            WebSocketConnection.this.reconnect();
          }
        }, i);
      }
      return bool;
    }
  }
  
  public void connect(URI paramURI, WebSocket.WebSocketConnectionObserver paramWebSocketConnectionObserver)
    throws WebSocketException
  {
    connect(paramURI, paramWebSocketConnectionObserver, new WebSocketOptions());
  }
  
  public void connect(URI paramURI, WebSocket.WebSocketConnectionObserver paramWebSocketConnectionObserver, @NonNull WebSocketOptions paramWebSocketOptions)
    throws WebSocketException
  {
    connect(paramURI, null, paramWebSocketConnectionObserver, paramWebSocketOptions);
  }
  
  public void disconnect()
  {
    if ((this.mWebSocketWriter != null) && (this.mWebSocketWriter.isAlive())) {
      this.mWebSocketWriter.forward(new WebSocketMessage.Close());
    }
    for (;;)
    {
      this.mPreviousConnection = false;
      return;
      this.optimizely.verboseLog(true, TAG, "Could not send WebSocket Close .. writer already null", new Object[0]);
    }
  }
  
  public boolean isConnected()
  {
    return (this.mSocket != null) && (this.mSocket.isConnected()) && (!this.mSocket.isClosed());
  }
  
  public void sendBinaryMessage(byte[] paramArrayOfByte)
  {
    this.mWebSocketWriter.forward(new WebSocketMessage.BinaryMessage(paramArrayOfByte));
  }
  
  public void sendMapMessage(Map<String, Object> paramMap)
  {
    this.mWebSocketWriter.forward(new WebSocketMessage.UnencodedMapMessage(paramMap));
  }
  
  public void sendRawTextMessage(byte[] paramArrayOfByte)
  {
    this.mWebSocketWriter.forward(new WebSocketMessage.RawTextMessage(paramArrayOfByte));
  }
  
  public void sendTextMessage(String paramString)
  {
    this.mWebSocketWriter.forward(new WebSocketMessage.TextMessage(paramString));
  }
  
  public static class SocketThread
    extends Thread
  {
    private static final String WS_CONNECTOR = "WebSocketConnector";
    @Nullable
    private String mFailureMessage = null;
    private Handler mHandler;
    @Nullable
    private Socket mSocket = null;
    private final URI mWebSocketURI;
    
    public SocketThread(URI paramURI, WebSocketOptions paramWebSocketOptions)
    {
      setName("WebSocketConnector");
      this.mWebSocketURI = paramURI;
    }
    
    @Nullable
    public String getFailureMessage()
    {
      return this.mFailureMessage;
    }
    
    public Handler getHandler()
    {
      return this.mHandler;
    }
    
    @Nullable
    public Socket getSocket()
    {
      return this.mSocket;
    }
    
    public void quit()
    {
      this.mHandler.getLooper().quit();
    }
    
    public void run()
    {
      Looper.prepare();
      this.mHandler = new Handler();
      try
      {
        notifyAll();
        Looper.loop();
        Log.d(WebSocketConnection.TAG, "SocketThread exited.");
        return;
      }
      finally {}
    }
    
    public void startConnection()
    {
      for (;;)
      {
        try
        {
          String str = this.mWebSocketURI.getHost();
          int j = this.mWebSocketURI.getPort();
          i = j;
          if (j == -1)
          {
            if (!WebSocketConnection.isSecureScheme(this.mWebSocketURI.getScheme())) {
              continue;
            }
            i = 443;
          }
          if (!WebSocketConnection.isSecureScheme(this.mWebSocketURI.getScheme())) {
            continue;
          }
          localSocketFactory = SSLCertificateSocketFactory.getDefault();
          this.mSocket = localSocketFactory.createSocket(str, i);
        }
        catch (IOException localIOException)
        {
          int i;
          SocketFactory localSocketFactory;
          this.mFailureMessage = localIOException.getLocalizedMessage();
          continue;
        }
        try
        {
          notifyAll();
          return;
        }
        finally {}
        i = 80;
        continue;
        localSocketFactory = SocketFactory.getDefault();
      }
    }
    
    public void stopConnection()
    {
      try
      {
        this.mSocket.close();
        this.mSocket = null;
        return;
      }
      catch (IOException localIOException)
      {
        this.mFailureMessage = localIOException.getLocalizedMessage();
      }
    }
  }
  
  private static class ThreadHandler
    extends Handler
  {
    @NonNull
    private final WeakReference<WebSocketConnection> mWebSocketConnection;
    
    public ThreadHandler(WebSocketConnection paramWebSocketConnection)
    {
      this.mWebSocketConnection = new WeakReference(paramWebSocketConnection);
    }
    
    public void handleMessage(@NonNull Message paramMessage)
    {
      WebSocketConnection localWebSocketConnection = (WebSocketConnection)this.mWebSocketConnection.get();
      if (localWebSocketConnection != null) {
        localWebSocketConnection.handleMessage(paramMessage);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/websocket/WebSocketConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
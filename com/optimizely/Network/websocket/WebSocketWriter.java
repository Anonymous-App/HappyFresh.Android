package com.optimizely.Network.websocket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.optimizely.Optimizely;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Random;

class WebSocketWriter
  extends Thread
{
  private static final String CRLF = "\r\n";
  private static final int WEB_SOCKETS_VERSION = 13;
  private static final String WEB_SOCKET_WRITER_COMPONENT = WebSocketWriter.class.getCanonicalName();
  @NonNull
  private final ByteBuffer mApplicationBuffer;
  private final Gson mGson;
  private Handler mHandler;
  @Nullable
  private OutputStream mOutputStream;
  private final Random mRandom = new Random();
  private final Socket mSocket;
  private final Handler mWebSocketConnectionHandler;
  @NonNull
  private final WebSocketOptions mWebSocketOptions;
  private final Optimizely optimizely;
  
  public WebSocketWriter(Handler paramHandler, Socket paramSocket, @NonNull WebSocketOptions paramWebSocketOptions, String paramString, Optimizely paramOptimizely)
  {
    super(paramString);
    this.mWebSocketConnectionHandler = paramHandler;
    this.mWebSocketOptions = paramWebSocketOptions;
    this.mSocket = paramSocket;
    this.optimizely = paramOptimizely;
    this.mApplicationBuffer = ByteBuffer.allocate(paramWebSocketOptions.getMaxFramePayloadSize() + 14);
    this.mGson = new Gson();
    Log.d(WEB_SOCKET_WRITER_COMPONENT, "WebSocket writer created.");
  }
  
  private void encodeAndSendMapMessage(@NonNull WebSocketMessage.UnencodedMapMessage paramUnencodedMapMessage)
    throws IOException, WebSocketException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    JsonWriter localJsonWriter = new JsonWriter(new OutputStreamWriter(localByteArrayOutputStream, "UTF-8"));
    this.mGson.toJson(paramUnencodedMapMessage.mPayload, Map.class, localJsonWriter);
    localJsonWriter.flush();
    localJsonWriter.close();
    paramUnencodedMapMessage = localByteArrayOutputStream.toByteArray();
    localByteArrayOutputStream.close();
    if (paramUnencodedMapMessage.length > this.mWebSocketOptions.getMaxMessagePayloadSize()) {
      throw new WebSocketException("message payload exceeds payload limit");
    }
    sendFrame(1, true, paramUnencodedMapMessage);
  }
  
  @NonNull
  private byte[] newFrameMask()
  {
    byte[] arrayOfByte = new byte[4];
    this.mRandom.nextBytes(arrayOfByte);
    return arrayOfByte;
  }
  
  private String newHandshakeKey()
  {
    byte[] arrayOfByte = new byte[16];
    this.mRandom.nextBytes(arrayOfByte);
    return Base64.encodeToString(arrayOfByte, 2);
  }
  
  private void notify(Object paramObject)
  {
    Message localMessage = this.mWebSocketConnectionHandler.obtainMessage();
    localMessage.obj = paramObject;
    this.mWebSocketConnectionHandler.sendMessage(localMessage);
  }
  
  private void processAppMessage(Object paramObject)
    throws WebSocketException, IOException
  {
    throw new WebSocketException("unknown message received by WebSocketWriter");
  }
  
  private void processMessage(Object paramObject)
    throws IOException, WebSocketException
  {
    if ((paramObject instanceof WebSocketMessage.TextMessage))
    {
      sendTextMessage((WebSocketMessage.TextMessage)paramObject);
      return;
    }
    if ((paramObject instanceof WebSocketMessage.UnencodedMapMessage))
    {
      encodeAndSendMapMessage((WebSocketMessage.UnencodedMapMessage)paramObject);
      return;
    }
    if ((paramObject instanceof WebSocketMessage.RawTextMessage))
    {
      sendRawTextMessage((WebSocketMessage.RawTextMessage)paramObject);
      return;
    }
    if ((paramObject instanceof WebSocketMessage.BinaryMessage))
    {
      sendBinaryMessage((WebSocketMessage.BinaryMessage)paramObject);
      return;
    }
    if ((paramObject instanceof WebSocketMessage.Ping))
    {
      sendPing((WebSocketMessage.Ping)paramObject);
      return;
    }
    if ((paramObject instanceof WebSocketMessage.Pong))
    {
      sendPong((WebSocketMessage.Pong)paramObject);
      return;
    }
    if ((paramObject instanceof WebSocketMessage.Close))
    {
      sendClose((WebSocketMessage.Close)paramObject);
      return;
    }
    if ((paramObject instanceof WebSocketMessage.ClientHandshake))
    {
      sendClientHandshake((WebSocketMessage.ClientHandshake)paramObject);
      return;
    }
    if ((paramObject instanceof WebSocketMessage.Quit))
    {
      Looper.myLooper().quit();
      Log.d(WEB_SOCKET_WRITER_COMPONENT, "WebSocket writer ended.");
      return;
    }
    processAppMessage(paramObject);
  }
  
  private void sendBinaryMessage(@NonNull WebSocketMessage.BinaryMessage paramBinaryMessage)
    throws IOException, WebSocketException
  {
    if (paramBinaryMessage.mPayload.length > this.mWebSocketOptions.getMaxMessagePayloadSize()) {
      throw new WebSocketException("message payload exceeds payload limit");
    }
    sendFrame(2, true, paramBinaryMessage.mPayload);
  }
  
  private void sendClientHandshake(@NonNull WebSocketMessage.ClientHandshake paramClientHandshake)
    throws IOException
  {
    Object localObject2 = paramClientHandshake.getURI().getPath();
    Object localObject1 = localObject2;
    if (localObject2 != null) {
      localObject1 = String.format("%s?%s", new Object[] { localObject2, paramClientHandshake.getURI().getQuery() });
    }
    if (localObject1 != null)
    {
      localObject2 = localObject1;
      if (((String)localObject1).length() != 0) {}
    }
    else
    {
      localObject2 = "/";
    }
    this.mApplicationBuffer.put(("GET " + (String)localObject2 + " HTTP/1.1" + "\r\n").getBytes());
    this.mApplicationBuffer.put(("Host: " + paramClientHandshake.getURI().getHost() + "\r\n").getBytes());
    this.mApplicationBuffer.put("Upgrade: WebSocket\r\n".getBytes());
    this.mApplicationBuffer.put("Connection: Upgrade\r\n".getBytes());
    this.mApplicationBuffer.put(("Sec-WebSocket-Key: " + newHandshakeKey() + "\r\n").getBytes());
    if (paramClientHandshake.getOrigin() != null) {
      this.mApplicationBuffer.put(("Origin: " + paramClientHandshake.getOrigin().toString() + "\r\n").getBytes());
    }
    if ((paramClientHandshake.getSubprotocols() != null) && (paramClientHandshake.getSubprotocols().length > 0))
    {
      this.mApplicationBuffer.put("Sec-WebSocket-Protocol: ".getBytes());
      int i = 0;
      while (i < paramClientHandshake.getSubprotocols().length)
      {
        this.mApplicationBuffer.put(paramClientHandshake.getSubprotocols()[i].getBytes());
        this.mApplicationBuffer.put(", ".getBytes());
        i += 1;
      }
      this.mApplicationBuffer.put("\r\n".getBytes());
    }
    this.mApplicationBuffer.put("Sec-WebSocket-Version: 13\r\n".getBytes());
    this.mApplicationBuffer.put("\r\n".getBytes());
  }
  
  private void sendClose(@NonNull WebSocketMessage.Close paramClose)
    throws IOException, WebSocketException
  {
    if (paramClose.getCode() > 0)
    {
      if ((paramClose.getReason() != null) && (paramClose.getReason().length() <= 0))
      {
        byte[] arrayOfByte3 = paramClose.getReason().getBytes("UTF-8");
        byte[] arrayOfByte2 = new byte[arrayOfByte3.length + 2];
        int i = 0;
        for (;;)
        {
          arrayOfByte1 = arrayOfByte2;
          if (i >= arrayOfByte3.length) {
            break;
          }
          arrayOfByte2[(i + 2)] = arrayOfByte3[i];
          i += 1;
        }
      }
      byte[] arrayOfByte1 = new byte[2];
      if (arrayOfByte1.length > 125) {
        throw new WebSocketException("close payload exceeds 125 octets");
      }
      arrayOfByte1[0] = ((byte)(paramClose.getCode() >> 8 & 0xFF));
      arrayOfByte1[1] = ((byte)(paramClose.getCode() & 0xFF));
      sendFrame(8, true, arrayOfByte1);
      return;
    }
    sendFrame(8, true, null);
  }
  
  private void sendFrame(int paramInt, boolean paramBoolean, @Nullable byte[] paramArrayOfByte)
    throws IOException
  {
    if (paramArrayOfByte != null)
    {
      sendFrame(paramInt, paramBoolean, paramArrayOfByte, 0, paramArrayOfByte.length);
      return;
    }
    sendFrame(paramInt, paramBoolean, null, 0, 0);
  }
  
  private void sendFrame(int paramInt1, boolean paramBoolean, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
    throws IOException
  {
    int i = 0;
    if (paramBoolean) {
      i = (byte)Byte.MIN_VALUE;
    }
    byte b = (byte)((byte)paramInt1 | i);
    this.mApplicationBuffer.put(b);
    paramInt1 = 0;
    if (this.mWebSocketOptions.getMaskClientFrames()) {
      paramInt1 = -128;
    }
    long l = paramInt3;
    if (l <= 125L)
    {
      b = (byte)((byte)(int)l | paramInt1);
      this.mApplicationBuffer.put(b);
    }
    for (;;)
    {
      byte[] arrayOfByte = null;
      if (this.mWebSocketOptions.getMaskClientFrames())
      {
        arrayOfByte = newFrameMask();
        this.mApplicationBuffer.put(arrayOfByte[0]);
        this.mApplicationBuffer.put(arrayOfByte[1]);
        this.mApplicationBuffer.put(arrayOfByte[2]);
        this.mApplicationBuffer.put(arrayOfByte[3]);
      }
      if (l <= 0L) {
        return;
      }
      if (!this.mWebSocketOptions.getMaskClientFrames()) {
        break;
      }
      paramInt1 = 0;
      while (paramInt1 < l)
      {
        i = paramInt1 + paramInt2;
        paramArrayOfByte[i] = ((byte)(paramArrayOfByte[i] ^ arrayOfByte[(paramInt1 % 4)]));
        paramInt1 += 1;
      }
      if (l <= 65535L)
      {
        b = (byte)(paramInt1 | 0x7E);
        this.mApplicationBuffer.put(b);
        this.mApplicationBuffer.put(new byte[] { (byte)(int)(l >> 8 & 0xFF), (byte)(int)(0xFF & l) });
      }
      else
      {
        b = (byte)(paramInt1 | 0x7F);
        this.mApplicationBuffer.put(b);
        this.mApplicationBuffer.put(new byte[] { (byte)(int)(l >> 56 & 0xFF), (byte)(int)(l >> 48 & 0xFF), (byte)(int)(l >> 40 & 0xFF), (byte)(int)(l >> 32 & 0xFF), (byte)(int)(l >> 24 & 0xFF), (byte)(int)(l >> 16 & 0xFF), (byte)(int)(l >> 8 & 0xFF), (byte)(int)(0xFF & l) });
      }
    }
    this.mApplicationBuffer.put(paramArrayOfByte, paramInt2, paramInt3);
  }
  
  private void sendPing(@NonNull WebSocketMessage.Ping paramPing)
    throws IOException, WebSocketException
  {
    if ((paramPing.mPayload != null) && (paramPing.mPayload.length > 125)) {
      throw new WebSocketException("ping payload exceeds 125 octets");
    }
    sendFrame(9, true, paramPing.mPayload);
  }
  
  private void sendPong(@NonNull WebSocketMessage.Pong paramPong)
    throws IOException, WebSocketException
  {
    if ((paramPong.mPayload != null) && (paramPong.mPayload.length > 125)) {
      throw new WebSocketException("pong payload exceeds 125 octets");
    }
    sendFrame(10, true, paramPong.mPayload);
  }
  
  private void sendRawTextMessage(@NonNull WebSocketMessage.RawTextMessage paramRawTextMessage)
    throws IOException, WebSocketException
  {
    if (paramRawTextMessage.mPayload.length > this.mWebSocketOptions.getMaxMessagePayloadSize()) {
      throw new WebSocketException("message payload exceeds payload limit");
    }
    sendFrame(1, true, paramRawTextMessage.mPayload);
  }
  
  private void sendTextMessage(@NonNull WebSocketMessage.TextMessage paramTextMessage)
    throws IOException, WebSocketException
  {
    paramTextMessage = paramTextMessage.mPayload.getBytes("UTF-8");
    if (paramTextMessage.length > this.mWebSocketOptions.getMaxMessagePayloadSize()) {
      throw new WebSocketException("message payload exceeds payload limit");
    }
    sendFrame(1, true, paramTextMessage);
  }
  
  private void writeMessageToBuffer(@NonNull Message paramMessage)
  {
    try
    {
      this.mApplicationBuffer.clear();
      processMessage(paramMessage.obj);
      this.mApplicationBuffer.flip();
      this.mOutputStream.write(this.mApplicationBuffer.array(), this.mApplicationBuffer.position(), this.mApplicationBuffer.limit());
      return;
    }
    catch (SocketException paramMessage)
    {
      this.optimizely.verboseLog(true, WEB_SOCKET_WRITER_COMPONENT, "run() : SocketException %1$s", new Object[] { paramMessage.getLocalizedMessage() });
      notify(new WebSocketMessage.ConnectionLost());
      return;
    }
    catch (IOException paramMessage)
    {
      this.optimizely.verboseLog(true, WEB_SOCKET_WRITER_COMPONENT, "run() : IOException %1$s", new Object[] { paramMessage.getLocalizedMessage() });
      return;
    }
    catch (Exception paramMessage)
    {
      notify(new WebSocketMessage.Error(paramMessage));
    }
  }
  
  public void forward(Object paramObject)
  {
    if (isAlive())
    {
      Message localMessage = this.mHandler.obtainMessage();
      localMessage.obj = paramObject;
      this.mHandler.sendMessage(localMessage);
    }
  }
  
  public void quit()
  {
    this.mHandler.getLooper().quit();
  }
  
  public void run()
  {
    Object localObject1 = null;
    try
    {
      OutputStream localOutputStream = this.mSocket.getOutputStream();
      localObject1 = localOutputStream;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        try
        {
          Log.d(WEB_SOCKET_WRITER_COMPONENT, "WebSocker writer running.");
          notifyAll();
          Looper.loop();
          return;
        }
        finally {}
        localIOException = localIOException;
        this.optimizely.verboseLog(true, WEB_SOCKET_WRITER_COMPONENT, localIOException.getLocalizedMessage(), new Object[0]);
      }
    }
    this.mOutputStream = ((OutputStream)localObject1);
    Looper.prepare();
    this.mHandler = new ThreadHandler(this);
  }
  
  private static class ThreadHandler
    extends Handler
  {
    @NonNull
    private final WeakReference<WebSocketWriter> mWebSocketWriterReference;
    
    public ThreadHandler(WebSocketWriter paramWebSocketWriter)
    {
      this.mWebSocketWriterReference = new WeakReference(paramWebSocketWriter);
    }
    
    public void handleMessage(@NonNull Message paramMessage)
    {
      WebSocketWriter localWebSocketWriter = (WebSocketWriter)this.mWebSocketWriterReference.get();
      if (localWebSocketWriter != null) {
        localWebSocketWriter.writeMessageToBuffer(paramMessage);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/websocket/WebSocketWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.optimizely.Network.websocket;

import android.support.annotation.Nullable;
import java.net.URI;
import java.util.Map;

class WebSocketMessage
{
  public static class BinaryMessage
    extends WebSocketMessage.Message
  {
    public final byte[] mPayload;
    
    BinaryMessage(byte[] paramArrayOfByte)
    {
      this.mPayload = paramArrayOfByte;
    }
  }
  
  public static class ClientHandshake
    extends WebSocketMessage.Message
  {
    @Nullable
    private final URI mOrigin;
    @Nullable
    private final String[] mSubprotocols;
    private final URI mURI;
    
    ClientHandshake(URI paramURI)
    {
      this.mURI = paramURI;
      this.mOrigin = null;
      this.mSubprotocols = null;
    }
    
    ClientHandshake(URI paramURI1, URI paramURI2, String[] paramArrayOfString)
    {
      this.mURI = paramURI1;
      this.mOrigin = paramURI2;
      this.mSubprotocols = paramArrayOfString;
    }
    
    @Nullable
    public URI getOrigin()
    {
      return this.mOrigin;
    }
    
    @Nullable
    public String[] getSubprotocols()
    {
      return this.mSubprotocols;
    }
    
    public URI getURI()
    {
      return this.mURI;
    }
  }
  
  public static class Close
    extends WebSocketMessage.Message
  {
    private final int mCode;
    @Nullable
    private final String mReason;
    
    Close()
    {
      this.mCode = 1011;
      this.mReason = null;
    }
    
    Close(int paramInt)
    {
      this.mCode = paramInt;
      this.mReason = null;
    }
    
    Close(int paramInt, String paramString)
    {
      this.mCode = paramInt;
      this.mReason = paramString;
    }
    
    public int getCode()
    {
      return this.mCode;
    }
    
    @Nullable
    public String getReason()
    {
      return this.mReason;
    }
  }
  
  public static class ConnectionLost
    extends WebSocketMessage.Message
  {}
  
  public static class Error
    extends WebSocketMessage.Message
  {
    public final Exception mException;
    
    public Error(Exception paramException)
    {
      this.mException = paramException;
    }
  }
  
  public static class Message {}
  
  public static class Ping
    extends WebSocketMessage.Message
  {
    @Nullable
    public final byte[] mPayload;
    
    Ping()
    {
      this.mPayload = null;
    }
    
    Ping(byte[] paramArrayOfByte)
    {
      this.mPayload = paramArrayOfByte;
    }
  }
  
  public static class Pong
    extends WebSocketMessage.Message
  {
    @Nullable
    public byte[] mPayload;
    
    Pong()
    {
      this.mPayload = null;
    }
    
    Pong(byte[] paramArrayOfByte)
    {
      this.mPayload = paramArrayOfByte;
    }
  }
  
  public static class ProtocolViolation
    extends WebSocketMessage.Message
  {
    public final WebSocketException mException;
    
    public ProtocolViolation(WebSocketException paramWebSocketException)
    {
      this.mException = paramWebSocketException;
    }
  }
  
  public static class Quit
    extends WebSocketMessage.Message
  {}
  
  public static class RawTextMessage
    extends WebSocketMessage.Message
  {
    public final byte[] mPayload;
    
    RawTextMessage(byte[] paramArrayOfByte)
    {
      this.mPayload = paramArrayOfByte;
    }
  }
  
  public static class ServerError
    extends WebSocketMessage.Message
  {
    public final int mStatusCode;
    public final String mStatusMessage;
    
    public ServerError(int paramInt, String paramString)
    {
      this.mStatusCode = paramInt;
      this.mStatusMessage = paramString;
    }
  }
  
  public static class ServerHandshake
    extends WebSocketMessage.Message
  {
    public final boolean mSuccess;
    
    public ServerHandshake(boolean paramBoolean)
    {
      this.mSuccess = paramBoolean;
    }
  }
  
  public static class TextMessage
    extends WebSocketMessage.Message
  {
    public final String mPayload;
    
    TextMessage(String paramString)
    {
      this.mPayload = paramString;
    }
  }
  
  public static class UnencodedMapMessage
    extends WebSocketMessage.Message
  {
    public final Map<String, Object> mPayload;
    
    UnencodedMapMessage(Map<String, Object> paramMap)
    {
      this.mPayload = paramMap;
    }
  }
  
  public static class WebSocketCloseCode
  {
    public static final int ENDPOINT_BAD_DATA = 1007;
    public static final int ENDPOINT_GOING_AWAY = 1001;
    public static final int ENDPOINT_NEEDS_EXTENSION = 1010;
    public static final int ENDPOINT_PROTOCOL_ERROR = 1002;
    public static final int ENDPOINT_UNSUPPORTED_DATA_TYPE = 1003;
    public static final int MESSAGE_TOO_BIG = 1009;
    public static final int NORMAL = 1000;
    public static final int POLICY_VIOLATION = 1008;
    public static final int RESERVED = 1004;
    public static final int RESERVED_NO_CLOSING_HANDSHAKE = 1006;
    public static final int RESERVED_NO_STATUS = 1005;
    public static final int RESERVED_TLS_REQUIRED = 1015;
    public static final int UNEXPECTED_CONDITION = 1011;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/websocket/WebSocketMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
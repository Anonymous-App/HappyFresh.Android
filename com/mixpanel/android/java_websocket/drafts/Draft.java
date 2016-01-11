package com.mixpanel.android.java_websocket.drafts;

import com.mixpanel.android.java_websocket.WebSocket.Role;
import com.mixpanel.android.java_websocket.exceptions.IncompleteHandshakeException;
import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.exceptions.LimitExedeedException;
import com.mixpanel.android.java_websocket.framing.FrameBuilder;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.framing.Framedata.Opcode;
import com.mixpanel.android.java_websocket.framing.FramedataImpl1;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.ClientHandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.HandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.HandshakeImpl1Client;
import com.mixpanel.android.java_websocket.handshake.HandshakeImpl1Server;
import com.mixpanel.android.java_websocket.handshake.Handshakedata;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import com.mixpanel.android.java_websocket.handshake.ServerHandshakeBuilder;
import com.mixpanel.android.java_websocket.util.Charsetfunctions;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public abstract class Draft
{
  public static final byte[] FLASH_POLICY_REQUEST = Charsetfunctions.utf8Bytes("<policy-file-request/>\000");
  public static int INITIAL_FAMESIZE;
  public static int MAX_FAME_SIZE = 1000;
  protected Framedata.Opcode continuousFrameType = null;
  protected WebSocket.Role role = null;
  
  static
  {
    INITIAL_FAMESIZE = 64;
  }
  
  public static ByteBuffer readLine(ByteBuffer paramByteBuffer)
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(paramByteBuffer.remaining());
    int i = 48;
    byte b;
    do
    {
      int j;
      do
      {
        j = i;
        if (!paramByteBuffer.hasRemaining()) {
          break;
        }
        b = paramByteBuffer.get();
        localByteBuffer.put(b);
        i = b;
      } while (j != 13);
      i = b;
    } while (b != 10);
    localByteBuffer.limit(localByteBuffer.position() - 2);
    localByteBuffer.position(0);
    return localByteBuffer;
    paramByteBuffer.position(paramByteBuffer.position() - localByteBuffer.position());
    return null;
  }
  
  public static String readStringLine(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer = readLine(paramByteBuffer);
    if (paramByteBuffer == null) {
      return null;
    }
    return Charsetfunctions.stringAscii(paramByteBuffer.array(), 0, paramByteBuffer.limit());
  }
  
  public static HandshakeBuilder translateHandshakeHttp(ByteBuffer paramByteBuffer, WebSocket.Role paramRole)
    throws InvalidHandshakeException, IncompleteHandshakeException
  {
    Object localObject = readStringLine(paramByteBuffer);
    if (localObject == null) {
      throw new IncompleteHandshakeException(paramByteBuffer.capacity() + 128);
    }
    localObject = ((String)localObject).split(" ", 3);
    if (localObject.length != 3) {
      throw new InvalidHandshakeException();
    }
    if (paramRole == WebSocket.Role.CLIENT)
    {
      paramRole = new HandshakeImpl1Server();
      ServerHandshakeBuilder localServerHandshakeBuilder = (ServerHandshakeBuilder)paramRole;
      localServerHandshakeBuilder.setHttpStatus(Short.parseShort(localObject[1]));
      localServerHandshakeBuilder.setHttpStatusMessage(localObject[2]);
    }
    for (localObject = readStringLine(paramByteBuffer);; localObject = readStringLine(paramByteBuffer))
    {
      if ((localObject == null) || (((String)localObject).length() <= 0)) {
        break label175;
      }
      localObject = ((String)localObject).split(":", 2);
      if (localObject.length != 2)
      {
        throw new InvalidHandshakeException("not an http header");
        paramRole = new HandshakeImpl1Client();
        paramRole.setResourceDescriptor(localObject[1]);
        break;
      }
      paramRole.put(localObject[0], localObject[1].replaceFirst("^ +", ""));
    }
    label175:
    if (localObject == null) {
      throw new IncompleteHandshakeException();
    }
    return paramRole;
  }
  
  public abstract HandshakeState acceptHandshakeAsClient(ClientHandshake paramClientHandshake, ServerHandshake paramServerHandshake)
    throws InvalidHandshakeException;
  
  public abstract HandshakeState acceptHandshakeAsServer(ClientHandshake paramClientHandshake)
    throws InvalidHandshakeException;
  
  protected boolean basicAccept(Handshakedata paramHandshakedata)
  {
    return (paramHandshakedata.getFieldValue("Upgrade").equalsIgnoreCase("websocket")) && (paramHandshakedata.getFieldValue("Connection").toLowerCase(Locale.ENGLISH).contains("upgrade"));
  }
  
  public int checkAlloc(int paramInt)
    throws LimitExedeedException, InvalidDataException
  {
    if (paramInt < 0) {
      throw new InvalidDataException(1002, "Negative count");
    }
    return paramInt;
  }
  
  public List<Framedata> continuousFrame(Framedata.Opcode paramOpcode, ByteBuffer paramByteBuffer, boolean paramBoolean)
  {
    if ((paramOpcode != Framedata.Opcode.BINARY) && (paramOpcode != Framedata.Opcode.TEXT) && (paramOpcode != Framedata.Opcode.TEXT)) {
      throw new IllegalArgumentException("Only Opcode.BINARY or  Opcode.TEXT are allowed");
    }
    FramedataImpl1 localFramedataImpl1;
    if (this.continuousFrameType != null)
    {
      this.continuousFrameType = Framedata.Opcode.CONTINUOUS;
      localFramedataImpl1 = new FramedataImpl1(this.continuousFrameType);
    }
    for (;;)
    {
      try
      {
        localFramedataImpl1.setPayload(paramByteBuffer);
        localFramedataImpl1.setFin(paramBoolean);
        if (!paramBoolean) {
          break label107;
        }
        this.continuousFrameType = null;
        return Collections.singletonList(localFramedataImpl1);
      }
      catch (InvalidDataException paramOpcode)
      {
        throw new RuntimeException(paramOpcode);
      }
      this.continuousFrameType = paramOpcode;
      break;
      label107:
      this.continuousFrameType = paramOpcode;
    }
  }
  
  public abstract Draft copyInstance();
  
  public abstract ByteBuffer createBinaryFrame(Framedata paramFramedata);
  
  public abstract List<Framedata> createFrames(String paramString, boolean paramBoolean);
  
  public abstract List<Framedata> createFrames(ByteBuffer paramByteBuffer, boolean paramBoolean);
  
  public List<ByteBuffer> createHandshake(Handshakedata paramHandshakedata, WebSocket.Role paramRole)
  {
    return createHandshake(paramHandshakedata, paramRole, true);
  }
  
  public List<ByteBuffer> createHandshake(Handshakedata paramHandshakedata, WebSocket.Role paramRole, boolean paramBoolean)
  {
    paramRole = new StringBuilder(100);
    if ((paramHandshakedata instanceof ClientHandshake))
    {
      paramRole.append("GET ");
      paramRole.append(((ClientHandshake)paramHandshakedata).getResourceDescriptor());
      paramRole.append(" HTTP/1.1");
    }
    Object localObject;
    for (;;)
    {
      paramRole.append("\r\n");
      localObject = paramHandshakedata.iterateHttpFields();
      while (((Iterator)localObject).hasNext())
      {
        String str1 = (String)((Iterator)localObject).next();
        String str2 = paramHandshakedata.getFieldValue(str1);
        paramRole.append(str1);
        paramRole.append(": ");
        paramRole.append(str2);
        paramRole.append("\r\n");
      }
      if (!(paramHandshakedata instanceof ServerHandshake)) {
        break;
      }
      paramRole.append("HTTP/1.1 101 " + ((ServerHandshake)paramHandshakedata).getHttpStatusMessage());
    }
    throw new RuntimeException("unknow role");
    paramRole.append("\r\n");
    paramRole = Charsetfunctions.asciiBytes(paramRole.toString());
    if (paramBoolean)
    {
      paramHandshakedata = paramHandshakedata.getContent();
      if (paramHandshakedata != null) {
        break label261;
      }
    }
    label261:
    for (int i = 0;; i = paramHandshakedata.length)
    {
      localObject = ByteBuffer.allocate(i + paramRole.length);
      ((ByteBuffer)localObject).put(paramRole);
      if (paramHandshakedata != null) {
        ((ByteBuffer)localObject).put(paramHandshakedata);
      }
      ((ByteBuffer)localObject).flip();
      return Collections.singletonList(localObject);
      paramHandshakedata = null;
      break;
    }
  }
  
  public abstract CloseHandshakeType getCloseHandshakeType();
  
  public WebSocket.Role getRole()
  {
    return this.role;
  }
  
  public abstract ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder paramClientHandshakeBuilder)
    throws InvalidHandshakeException;
  
  public abstract HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake paramClientHandshake, ServerHandshakeBuilder paramServerHandshakeBuilder)
    throws InvalidHandshakeException;
  
  public abstract void reset();
  
  public void setParseMode(WebSocket.Role paramRole)
  {
    this.role = paramRole;
  }
  
  public abstract List<Framedata> translateFrame(ByteBuffer paramByteBuffer)
    throws InvalidDataException;
  
  public Handshakedata translateHandshake(ByteBuffer paramByteBuffer)
    throws InvalidHandshakeException
  {
    return translateHandshakeHttp(paramByteBuffer, this.role);
  }
  
  public static enum CloseHandshakeType
  {
    NONE,  ONEWAY,  TWOWAY;
    
    private CloseHandshakeType() {}
  }
  
  public static enum HandshakeState
  {
    MATCHED,  NOT_MATCHED;
    
    private HandshakeState() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/drafts/Draft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
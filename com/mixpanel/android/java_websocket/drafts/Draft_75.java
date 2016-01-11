package com.mixpanel.android.java_websocket.drafts;

import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidFrameException;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.exceptions.LimitExedeedException;
import com.mixpanel.android.java_websocket.exceptions.NotSendableException;
import com.mixpanel.android.java_websocket.framing.FrameBuilder;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.framing.Framedata.Opcode;
import com.mixpanel.android.java_websocket.framing.FramedataImpl1;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.ClientHandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.HandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import com.mixpanel.android.java_websocket.handshake.ServerHandshakeBuilder;
import com.mixpanel.android.java_websocket.util.Charsetfunctions;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Draft_75
  extends Draft
{
  public static final byte CR = 13;
  public static final byte END_OF_FRAME = -1;
  public static final byte LF = 10;
  public static final byte START_OF_FRAME = 0;
  protected ByteBuffer currentFrame;
  protected boolean readingState = false;
  protected List<Framedata> readyframes = new LinkedList();
  private final Random reuseableRandom = new Random();
  
  public Draft.HandshakeState acceptHandshakeAsClient(ClientHandshake paramClientHandshake, ServerHandshake paramServerHandshake)
  {
    if ((paramClientHandshake.getFieldValue("WebSocket-Origin").equals(paramServerHandshake.getFieldValue("Origin"))) && (basicAccept(paramServerHandshake))) {
      return Draft.HandshakeState.MATCHED;
    }
    return Draft.HandshakeState.NOT_MATCHED;
  }
  
  public Draft.HandshakeState acceptHandshakeAsServer(ClientHandshake paramClientHandshake)
  {
    if ((paramClientHandshake.hasFieldValue("Origin")) && (basicAccept(paramClientHandshake))) {
      return Draft.HandshakeState.MATCHED;
    }
    return Draft.HandshakeState.NOT_MATCHED;
  }
  
  public Draft copyInstance()
  {
    return new Draft_75();
  }
  
  public ByteBuffer createBinaryFrame(Framedata paramFramedata)
  {
    if (paramFramedata.getOpcode() != Framedata.Opcode.TEXT) {
      throw new RuntimeException("only text frames supported");
    }
    paramFramedata = paramFramedata.getPayloadData();
    ByteBuffer localByteBuffer = ByteBuffer.allocate(paramFramedata.remaining() + 2);
    localByteBuffer.put((byte)0);
    paramFramedata.mark();
    localByteBuffer.put(paramFramedata);
    paramFramedata.reset();
    localByteBuffer.put((byte)-1);
    localByteBuffer.flip();
    return localByteBuffer;
  }
  
  public ByteBuffer createBuffer()
  {
    return ByteBuffer.allocate(INITIAL_FAMESIZE);
  }
  
  public List<Framedata> createFrames(String paramString, boolean paramBoolean)
  {
    FramedataImpl1 localFramedataImpl1 = new FramedataImpl1();
    try
    {
      localFramedataImpl1.setPayload(ByteBuffer.wrap(Charsetfunctions.utf8Bytes(paramString)));
      localFramedataImpl1.setFin(true);
      localFramedataImpl1.setOptcode(Framedata.Opcode.TEXT);
      localFramedataImpl1.setTransferemasked(paramBoolean);
      return Collections.singletonList(localFramedataImpl1);
    }
    catch (InvalidDataException paramString)
    {
      throw new NotSendableException(paramString);
    }
  }
  
  public List<Framedata> createFrames(ByteBuffer paramByteBuffer, boolean paramBoolean)
  {
    throw new RuntimeException("not yet implemented");
  }
  
  public Draft.CloseHandshakeType getCloseHandshakeType()
  {
    return Draft.CloseHandshakeType.NONE;
  }
  
  public ByteBuffer increaseBuffer(ByteBuffer paramByteBuffer)
    throws LimitExedeedException, InvalidDataException
  {
    paramByteBuffer.flip();
    ByteBuffer localByteBuffer = ByteBuffer.allocate(checkAlloc(paramByteBuffer.capacity() * 2));
    localByteBuffer.put(paramByteBuffer);
    return localByteBuffer;
  }
  
  public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder paramClientHandshakeBuilder)
    throws InvalidHandshakeException
  {
    paramClientHandshakeBuilder.put("Upgrade", "WebSocket");
    paramClientHandshakeBuilder.put("Connection", "Upgrade");
    if (!paramClientHandshakeBuilder.hasFieldValue("Origin")) {
      paramClientHandshakeBuilder.put("Origin", "random" + this.reuseableRandom.nextInt());
    }
    return paramClientHandshakeBuilder;
  }
  
  public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake paramClientHandshake, ServerHandshakeBuilder paramServerHandshakeBuilder)
    throws InvalidHandshakeException
  {
    paramServerHandshakeBuilder.setHttpStatusMessage("Web Socket Protocol Handshake");
    paramServerHandshakeBuilder.put("Upgrade", "WebSocket");
    paramServerHandshakeBuilder.put("Connection", paramClientHandshake.getFieldValue("Connection"));
    paramServerHandshakeBuilder.put("WebSocket-Origin", paramClientHandshake.getFieldValue("Origin"));
    paramServerHandshakeBuilder.put("WebSocket-Location", "ws://" + paramClientHandshake.getFieldValue("Host") + paramClientHandshake.getResourceDescriptor());
    return paramServerHandshakeBuilder;
  }
  
  public void reset()
  {
    this.readingState = false;
    this.currentFrame = null;
  }
  
  public List<Framedata> translateFrame(ByteBuffer paramByteBuffer)
    throws InvalidDataException
  {
    paramByteBuffer = translateRegularFrame(paramByteBuffer);
    if (paramByteBuffer == null) {
      throw new InvalidDataException(1002);
    }
    return paramByteBuffer;
  }
  
  protected List<Framedata> translateRegularFrame(ByteBuffer paramByteBuffer)
    throws InvalidDataException
  {
    Object localObject2 = null;
    while (paramByteBuffer.hasRemaining())
    {
      byte b = paramByteBuffer.get();
      if (b == 0)
      {
        if (this.readingState) {
          throw new InvalidFrameException("unexpected START_OF_FRAME");
        }
        this.readingState = true;
      }
      else if (b == -1)
      {
        if (!this.readingState) {
          throw new InvalidFrameException("unexpected END_OF_FRAME");
        }
        if (this.currentFrame != null)
        {
          this.currentFrame.flip();
          localObject1 = new FramedataImpl1();
          ((FramedataImpl1)localObject1).setPayload(this.currentFrame);
          ((FramedataImpl1)localObject1).setFin(true);
          ((FramedataImpl1)localObject1).setOptcode(Framedata.Opcode.TEXT);
          this.readyframes.add(localObject1);
          this.currentFrame = null;
          paramByteBuffer.mark();
        }
        this.readingState = false;
      }
      else
      {
        localObject1 = localObject2;
        if (!this.readingState) {
          return localObject1;
        }
        if (this.currentFrame == null) {
          this.currentFrame = createBuffer();
        }
        for (;;)
        {
          this.currentFrame.put(b);
          break;
          if (!this.currentFrame.hasRemaining()) {
            this.currentFrame = increaseBuffer(this.currentFrame);
          }
        }
      }
    }
    Object localObject1 = this.readyframes;
    this.readyframes = new LinkedList();
    return (List<Framedata>)localObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/drafts/Draft_75.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
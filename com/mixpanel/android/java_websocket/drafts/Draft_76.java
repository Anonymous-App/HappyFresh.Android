package com.mixpanel.android.java_websocket.drafts;

import android.annotation.SuppressLint;
import com.mixpanel.android.java_websocket.WebSocket.Role;
import com.mixpanel.android.java_websocket.exceptions.IncompleteHandshakeException;
import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidFrameException;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.framing.CloseFrameBuilder;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.framing.Framedata.Opcode;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.ClientHandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.HandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.Handshakedata;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import com.mixpanel.android.java_websocket.handshake.ServerHandshakeBuilder;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@SuppressLint({"UseValueOf"})
public class Draft_76
  extends Draft_75
{
  private static final byte[] closehandshake = { -1, 0 };
  private boolean failed = false;
  private final Random reuseableRandom = new Random();
  
  public static byte[] createChallenge(String paramString1, String paramString2, byte[] paramArrayOfByte)
    throws InvalidHandshakeException
  {
    paramString1 = getPart(paramString1);
    paramString2 = getPart(paramString2);
    int i = paramString1[0];
    int j = paramString1[1];
    int k = paramString1[2];
    int m = paramString1[3];
    int n = paramString2[0];
    int i1 = paramString2[1];
    int i2 = paramString2[2];
    int i3 = paramString2[3];
    int i4 = paramArrayOfByte[0];
    int i5 = paramArrayOfByte[1];
    int i6 = paramArrayOfByte[2];
    int i7 = paramArrayOfByte[3];
    int i8 = paramArrayOfByte[4];
    int i9 = paramArrayOfByte[5];
    int i10 = paramArrayOfByte[6];
    int i11 = paramArrayOfByte[7];
    try
    {
      paramString1 = MessageDigest.getInstance("MD5");
      return paramString1.digest(new byte[] { i, j, k, m, n, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11 });
    }
    catch (NoSuchAlgorithmException paramString1)
    {
      throw new RuntimeException(paramString1);
    }
  }
  
  private static String generateKey()
  {
    Random localRandom = new Random();
    long l = localRandom.nextInt(12) + 1;
    String str = Long.toString((localRandom.nextInt(Math.abs(new Long(4294967295L / l).intValue())) + 1) * l);
    int j = localRandom.nextInt(12);
    int i = 0;
    while (i < j + 1)
    {
      int k = Math.abs(localRandom.nextInt(str.length()));
      char c2 = (char)(localRandom.nextInt(95) + 33);
      char c1 = c2;
      if (c2 >= '0')
      {
        c1 = c2;
        if (c2 <= '9') {
          c1 = (char)(c2 - '\017');
        }
      }
      str = new StringBuilder(str).insert(k, c1).toString();
      i += 1;
    }
    i = 0;
    while (i < l)
    {
      j = Math.abs(localRandom.nextInt(str.length() - 1) + 1);
      str = new StringBuilder(str).insert(j, " ").toString();
      i += 1;
    }
    return str;
  }
  
  private static byte[] getPart(String paramString)
    throws InvalidHandshakeException
  {
    long l2;
    try
    {
      l1 = Long.parseLong(paramString.replaceAll("[^0-9]", ""));
      l2 = paramString.split(" ").length - 1;
      if (l2 == 0L) {
        throw new InvalidHandshakeException("invalid Sec-WebSocket-Key (/key2/)");
      }
    }
    catch (NumberFormatException paramString)
    {
      throw new InvalidHandshakeException("invalid Sec-WebSocket-Key (/key1/ or /key2/)");
    }
    long l1 = new Long(l1 / l2).longValue();
    int i = (byte)(int)(l1 >> 24);
    int j = (byte)(int)(l1 << 8 >> 24);
    int k = (byte)(int)(l1 << 16 >> 24);
    int m = (byte)(int)(l1 << 24 >> 24);
    return new byte[] { i, j, k, m };
  }
  
  public Draft.HandshakeState acceptHandshakeAsClient(ClientHandshake paramClientHandshake, ServerHandshake paramServerHandshake)
  {
    if (this.failed) {
      return Draft.HandshakeState.NOT_MATCHED;
    }
    try
    {
      if ((!paramServerHandshake.getFieldValue("Sec-WebSocket-Origin").equals(paramClientHandshake.getFieldValue("Origin"))) || (!basicAccept(paramServerHandshake))) {
        return Draft.HandshakeState.NOT_MATCHED;
      }
      paramServerHandshake = paramServerHandshake.getContent();
      if ((paramServerHandshake == null) || (paramServerHandshake.length == 0)) {
        throw new IncompleteHandshakeException();
      }
    }
    catch (InvalidHandshakeException paramClientHandshake)
    {
      throw new RuntimeException("bad handshakerequest", paramClientHandshake);
    }
    if (Arrays.equals(paramServerHandshake, createChallenge(paramClientHandshake.getFieldValue("Sec-WebSocket-Key1"), paramClientHandshake.getFieldValue("Sec-WebSocket-Key2"), paramClientHandshake.getContent()))) {
      return Draft.HandshakeState.MATCHED;
    }
    paramClientHandshake = Draft.HandshakeState.NOT_MATCHED;
    return paramClientHandshake;
  }
  
  public Draft.HandshakeState acceptHandshakeAsServer(ClientHandshake paramClientHandshake)
  {
    if ((paramClientHandshake.getFieldValue("Upgrade").equals("WebSocket")) && (paramClientHandshake.getFieldValue("Connection").contains("Upgrade")) && (paramClientHandshake.getFieldValue("Sec-WebSocket-Key1").length() > 0) && (!paramClientHandshake.getFieldValue("Sec-WebSocket-Key2").isEmpty()) && (paramClientHandshake.hasFieldValue("Origin"))) {
      return Draft.HandshakeState.MATCHED;
    }
    return Draft.HandshakeState.NOT_MATCHED;
  }
  
  public Draft copyInstance()
  {
    return new Draft_76();
  }
  
  public ByteBuffer createBinaryFrame(Framedata paramFramedata)
  {
    if (paramFramedata.getOpcode() == Framedata.Opcode.CLOSING) {
      return ByteBuffer.wrap(closehandshake);
    }
    return super.createBinaryFrame(paramFramedata);
  }
  
  public Draft.CloseHandshakeType getCloseHandshakeType()
  {
    return Draft.CloseHandshakeType.ONEWAY;
  }
  
  public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder paramClientHandshakeBuilder)
  {
    paramClientHandshakeBuilder.put("Upgrade", "WebSocket");
    paramClientHandshakeBuilder.put("Connection", "Upgrade");
    paramClientHandshakeBuilder.put("Sec-WebSocket-Key1", generateKey());
    paramClientHandshakeBuilder.put("Sec-WebSocket-Key2", generateKey());
    if (!paramClientHandshakeBuilder.hasFieldValue("Origin")) {
      paramClientHandshakeBuilder.put("Origin", "random" + this.reuseableRandom.nextInt());
    }
    byte[] arrayOfByte = new byte[8];
    this.reuseableRandom.nextBytes(arrayOfByte);
    paramClientHandshakeBuilder.setContent(arrayOfByte);
    return paramClientHandshakeBuilder;
  }
  
  public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake paramClientHandshake, ServerHandshakeBuilder paramServerHandshakeBuilder)
    throws InvalidHandshakeException
  {
    paramServerHandshakeBuilder.setHttpStatusMessage("WebSocket Protocol Handshake");
    paramServerHandshakeBuilder.put("Upgrade", "WebSocket");
    paramServerHandshakeBuilder.put("Connection", paramClientHandshake.getFieldValue("Connection"));
    paramServerHandshakeBuilder.put("Sec-WebSocket-Origin", paramClientHandshake.getFieldValue("Origin"));
    paramServerHandshakeBuilder.put("Sec-WebSocket-Location", "ws://" + paramClientHandshake.getFieldValue("Host") + paramClientHandshake.getResourceDescriptor());
    String str1 = paramClientHandshake.getFieldValue("Sec-WebSocket-Key1");
    String str2 = paramClientHandshake.getFieldValue("Sec-WebSocket-Key2");
    paramClientHandshake = paramClientHandshake.getContent();
    if ((str1 == null) || (str2 == null) || (paramClientHandshake == null) || (paramClientHandshake.length != 8)) {
      throw new InvalidHandshakeException("Bad keys");
    }
    paramServerHandshakeBuilder.setContent(createChallenge(str1, str2, paramClientHandshake));
    return paramServerHandshakeBuilder;
  }
  
  public List<Framedata> translateFrame(ByteBuffer paramByteBuffer)
    throws InvalidDataException
  {
    paramByteBuffer.mark();
    List localList = super.translateRegularFrame(paramByteBuffer);
    if (localList == null)
    {
      paramByteBuffer.reset();
      localList = this.readyframes;
      this.readingState = true;
      if (this.currentFrame == null)
      {
        this.currentFrame = ByteBuffer.allocate(2);
        if (paramByteBuffer.remaining() > this.currentFrame.remaining()) {
          throw new InvalidFrameException();
        }
      }
      else
      {
        throw new InvalidFrameException();
      }
      this.currentFrame.put(paramByteBuffer);
      if (!this.currentFrame.hasRemaining())
      {
        if (Arrays.equals(this.currentFrame.array(), closehandshake))
        {
          localList.add(new CloseFrameBuilder(1000));
          return localList;
        }
        throw new InvalidFrameException();
      }
      this.readyframes = new LinkedList();
      return localList;
    }
    return localList;
  }
  
  public Handshakedata translateHandshake(ByteBuffer paramByteBuffer)
    throws InvalidHandshakeException
  {
    HandshakeBuilder localHandshakeBuilder = translateHandshakeHttp(paramByteBuffer, this.role);
    if (((localHandshakeBuilder.hasFieldValue("Sec-WebSocket-Key1")) || (this.role == WebSocket.Role.CLIENT)) && (!localHandshakeBuilder.hasFieldValue("Sec-WebSocket-Version"))) {
      if (this.role != WebSocket.Role.SERVER) {
        break label77;
      }
    }
    for (int i = 8;; i = 16)
    {
      byte[] arrayOfByte = new byte[i];
      try
      {
        paramByteBuffer.get(arrayOfByte);
        localHandshakeBuilder.setContent(arrayOfByte);
        return localHandshakeBuilder;
      }
      catch (BufferUnderflowException localBufferUnderflowException)
      {
        label77:
        throw new IncompleteHandshakeException(paramByteBuffer.capacity() + 16);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/drafts/Draft_76.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
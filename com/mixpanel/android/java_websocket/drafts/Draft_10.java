package com.mixpanel.android.java_websocket.drafts;

import android.annotation.SuppressLint;
import com.mixpanel.android.java_websocket.WebSocket.Role;
import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidFrameException;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.exceptions.LimitExedeedException;
import com.mixpanel.android.java_websocket.exceptions.NotSendableException;
import com.mixpanel.android.java_websocket.framing.CloseFrameBuilder;
import com.mixpanel.android.java_websocket.framing.FrameBuilder;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.framing.Framedata.Opcode;
import com.mixpanel.android.java_websocket.framing.FramedataImpl1;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.ClientHandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.HandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.Handshakedata;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import com.mixpanel.android.java_websocket.handshake.ServerHandshakeBuilder;
import com.mixpanel.android.java_websocket.util.Base64;
import com.mixpanel.android.java_websocket.util.Charsetfunctions;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressLint({"Assert", "UseValueOf"})
public class Draft_10
  extends Draft
{
  private Framedata fragmentedframe = null;
  private ByteBuffer incompleteframe;
  private final Random reuseableRandom = new Random();
  
  static
  {
    if (!Draft_10.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  private byte fromOpcode(Framedata.Opcode paramOpcode)
  {
    if (paramOpcode == Framedata.Opcode.CONTINUOUS) {
      return 0;
    }
    if (paramOpcode == Framedata.Opcode.TEXT) {
      return 1;
    }
    if (paramOpcode == Framedata.Opcode.BINARY) {
      return 2;
    }
    if (paramOpcode == Framedata.Opcode.CLOSING) {
      return 8;
    }
    if (paramOpcode == Framedata.Opcode.PING) {
      return 9;
    }
    if (paramOpcode == Framedata.Opcode.PONG) {
      return 10;
    }
    throw new RuntimeException("Don't know how to handle " + paramOpcode.toString());
  }
  
  private String generateFinalKey(String paramString)
  {
    paramString = paramString.trim();
    paramString = paramString + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA1");
      return Base64.encodeBytes(localMessageDigest.digest(paramString.getBytes()));
    }
    catch (NoSuchAlgorithmException paramString)
    {
      throw new RuntimeException(paramString);
    }
  }
  
  public static int readVersion(Handshakedata paramHandshakedata)
  {
    int i = -1;
    paramHandshakedata = paramHandshakedata.getFieldValue("Sec-WebSocket-Version");
    if (paramHandshakedata.length() > 0) {}
    try
    {
      i = new Integer(paramHandshakedata.trim()).intValue();
      return i;
    }
    catch (NumberFormatException paramHandshakedata) {}
    return -1;
  }
  
  private byte[] toByteArray(long paramLong, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    int i = 0;
    while (i < paramInt)
    {
      arrayOfByte[i] = ((byte)(int)(paramLong >>> paramInt * 8 - 8 - i * 8));
      i += 1;
    }
    return arrayOfByte;
  }
  
  private Framedata.Opcode toOpcode(byte paramByte)
    throws InvalidFrameException
  {
    switch (paramByte)
    {
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 7: 
    default: 
      throw new InvalidFrameException("unknow optcode " + (short)paramByte);
    case 0: 
      return Framedata.Opcode.CONTINUOUS;
    case 1: 
      return Framedata.Opcode.TEXT;
    case 2: 
      return Framedata.Opcode.BINARY;
    case 8: 
      return Framedata.Opcode.CLOSING;
    case 9: 
      return Framedata.Opcode.PING;
    }
    return Framedata.Opcode.PONG;
  }
  
  public Draft.HandshakeState acceptHandshakeAsClient(ClientHandshake paramClientHandshake, ServerHandshake paramServerHandshake)
    throws InvalidHandshakeException
  {
    if ((!paramClientHandshake.hasFieldValue("Sec-WebSocket-Key")) || (!paramServerHandshake.hasFieldValue("Sec-WebSocket-Accept"))) {
      return Draft.HandshakeState.NOT_MATCHED;
    }
    paramServerHandshake = paramServerHandshake.getFieldValue("Sec-WebSocket-Accept");
    if (generateFinalKey(paramClientHandshake.getFieldValue("Sec-WebSocket-Key")).equals(paramServerHandshake)) {
      return Draft.HandshakeState.MATCHED;
    }
    return Draft.HandshakeState.NOT_MATCHED;
  }
  
  public Draft.HandshakeState acceptHandshakeAsServer(ClientHandshake paramClientHandshake)
    throws InvalidHandshakeException
  {
    int i = readVersion(paramClientHandshake);
    if ((i == 7) || (i == 8))
    {
      if (basicAccept(paramClientHandshake)) {
        return Draft.HandshakeState.MATCHED;
      }
      return Draft.HandshakeState.NOT_MATCHED;
    }
    return Draft.HandshakeState.NOT_MATCHED;
  }
  
  public Draft copyInstance()
  {
    return new Draft_10();
  }
  
  public ByteBuffer createBinaryFrame(Framedata paramFramedata)
  {
    ByteBuffer localByteBuffer1 = paramFramedata.getPayloadData();
    int j;
    int i;
    label32:
    label42:
    int m;
    label49:
    ByteBuffer localByteBuffer2;
    if (this.role == WebSocket.Role.CLIENT)
    {
      j = 1;
      if (localByteBuffer1.remaining() > 125) {
        break label142;
      }
      i = 1;
      if (i <= 1) {
        break label163;
      }
      k = i + 1;
      if (j == 0) {
        break label169;
      }
      m = 4;
      localByteBuffer2 = ByteBuffer.allocate(m + (k + 1) + localByteBuffer1.remaining());
      m = fromOpcode(paramFramedata.getOpcode());
      if (!paramFramedata.isFin()) {
        break label175;
      }
    }
    label142:
    label163:
    label169:
    label175:
    for (int k = -128;; k = 0)
    {
      localByteBuffer2.put((byte)((byte)k | m));
      paramFramedata = toByteArray(localByteBuffer1.remaining(), i);
      if (($assertionsDisabled) || (paramFramedata.length == i)) {
        break label181;
      }
      throw new AssertionError();
      j = 0;
      break;
      if (localByteBuffer1.remaining() <= 65535)
      {
        i = 2;
        break label32;
      }
      i = 8;
      break label32;
      k = i;
      break label42;
      m = 0;
      break label49;
    }
    label181:
    if (i == 1)
    {
      k = paramFramedata[0];
      if (j != 0) {}
      for (i = -128;; i = 0)
      {
        localByteBuffer2.put((byte)(i | k));
        if (j == 0) {
          break;
        }
        paramFramedata = ByteBuffer.allocate(4);
        paramFramedata.putInt(this.reuseableRandom.nextInt());
        localByteBuffer2.put(paramFramedata.array());
        i = 0;
        while (localByteBuffer1.hasRemaining())
        {
          localByteBuffer2.put((byte)(localByteBuffer1.get() ^ paramFramedata.get(i % 4)));
          i += 1;
        }
      }
    }
    if (i == 2)
    {
      if (j != 0) {}
      for (i = -128;; i = 0)
      {
        localByteBuffer2.put((byte)(i | 0x7E));
        localByteBuffer2.put(paramFramedata);
        break;
      }
    }
    if (i == 8)
    {
      if (j != 0) {}
      for (i = -128;; i = 0)
      {
        localByteBuffer2.put((byte)(i | 0x7F));
        localByteBuffer2.put(paramFramedata);
        break;
      }
    }
    throw new RuntimeException("Size representation not supported/specified");
    localByteBuffer2.put(localByteBuffer1);
    assert (localByteBuffer2.remaining() == 0) : localByteBuffer2.remaining();
    localByteBuffer2.flip();
    return localByteBuffer2;
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
    FramedataImpl1 localFramedataImpl1 = new FramedataImpl1();
    try
    {
      localFramedataImpl1.setPayload(paramByteBuffer);
      localFramedataImpl1.setFin(true);
      localFramedataImpl1.setOptcode(Framedata.Opcode.BINARY);
      localFramedataImpl1.setTransferemasked(paramBoolean);
      return Collections.singletonList(localFramedataImpl1);
    }
    catch (InvalidDataException paramByteBuffer)
    {
      throw new NotSendableException(paramByteBuffer);
    }
  }
  
  public Draft.CloseHandshakeType getCloseHandshakeType()
  {
    return Draft.CloseHandshakeType.TWOWAY;
  }
  
  public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder paramClientHandshakeBuilder)
  {
    paramClientHandshakeBuilder.put("Upgrade", "websocket");
    paramClientHandshakeBuilder.put("Connection", "Upgrade");
    paramClientHandshakeBuilder.put("Sec-WebSocket-Version", "8");
    byte[] arrayOfByte = new byte[16];
    this.reuseableRandom.nextBytes(arrayOfByte);
    paramClientHandshakeBuilder.put("Sec-WebSocket-Key", Base64.encodeBytes(arrayOfByte));
    return paramClientHandshakeBuilder;
  }
  
  public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake paramClientHandshake, ServerHandshakeBuilder paramServerHandshakeBuilder)
    throws InvalidHandshakeException
  {
    paramServerHandshakeBuilder.put("Upgrade", "websocket");
    paramServerHandshakeBuilder.put("Connection", paramClientHandshake.getFieldValue("Connection"));
    paramServerHandshakeBuilder.setHttpStatusMessage("Switching Protocols");
    paramClientHandshake = paramClientHandshake.getFieldValue("Sec-WebSocket-Key");
    if (paramClientHandshake == null) {
      throw new InvalidHandshakeException("missing Sec-WebSocket-Key");
    }
    paramServerHandshakeBuilder.put("Sec-WebSocket-Accept", generateFinalKey(paramClientHandshake));
    return paramServerHandshakeBuilder;
  }
  
  public void reset()
  {
    this.incompleteframe = null;
  }
  
  /* Error */
  public List<Framedata> translateFrame(ByteBuffer paramByteBuffer)
    throws LimitExedeedException, InvalidDataException
  {
    // Byte code:
    //   0: new 371	java/util/LinkedList
    //   3: dup
    //   4: invokespecial 372	java/util/LinkedList:<init>	()V
    //   7: astore 5
    //   9: aload_0
    //   10: getfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   13: ifnull +117 -> 130
    //   16: aload_1
    //   17: invokevirtual 375	java/nio/ByteBuffer:mark	()Ljava/nio/Buffer;
    //   20: pop
    //   21: aload_1
    //   22: invokevirtual 219	java/nio/ByteBuffer:remaining	()I
    //   25: istore_2
    //   26: aload_0
    //   27: getfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   30: invokevirtual 219	java/nio/ByteBuffer:remaining	()I
    //   33: istore_3
    //   34: iload_3
    //   35: iload_2
    //   36: if_icmple +35 -> 71
    //   39: aload_0
    //   40: getfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   43: aload_1
    //   44: invokevirtual 251	java/nio/ByteBuffer:array	()[B
    //   47: aload_1
    //   48: invokevirtual 378	java/nio/ByteBuffer:position	()I
    //   51: iload_2
    //   52: invokevirtual 381	java/nio/ByteBuffer:put	([BII)Ljava/nio/ByteBuffer;
    //   55: pop
    //   56: aload_1
    //   57: aload_1
    //   58: invokevirtual 378	java/nio/ByteBuffer:position	()I
    //   61: iload_2
    //   62: iadd
    //   63: invokevirtual 384	java/nio/ByteBuffer:position	(I)Ljava/nio/Buffer;
    //   66: pop
    //   67: invokestatic 388	java/util/Collections:emptyList	()Ljava/util/List;
    //   70: areturn
    //   71: aload_0
    //   72: getfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   75: aload_1
    //   76: invokevirtual 251	java/nio/ByteBuffer:array	()[B
    //   79: aload_1
    //   80: invokevirtual 378	java/nio/ByteBuffer:position	()I
    //   83: iload_3
    //   84: invokevirtual 381	java/nio/ByteBuffer:put	([BII)Ljava/nio/ByteBuffer;
    //   87: pop
    //   88: aload_1
    //   89: aload_1
    //   90: invokevirtual 378	java/nio/ByteBuffer:position	()I
    //   93: iload_3
    //   94: iadd
    //   95: invokevirtual 384	java/nio/ByteBuffer:position	(I)Ljava/nio/Buffer;
    //   98: pop
    //   99: aload 5
    //   101: aload_0
    //   102: aload_0
    //   103: getfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   106: invokevirtual 391	java/nio/ByteBuffer:duplicate	()Ljava/nio/ByteBuffer;
    //   109: iconst_0
    //   110: invokevirtual 384	java/nio/ByteBuffer:position	(I)Ljava/nio/Buffer;
    //   113: checkcast 216	java/nio/ByteBuffer
    //   116: invokevirtual 395	com/mixpanel/android/java_websocket/drafts/Draft_10:translateSingleFrame	(Ljava/nio/ByteBuffer;)Lcom/mixpanel/android/java_websocket/framing/Framedata;
    //   119: invokeinterface 400 2 0
    //   124: pop
    //   125: aload_0
    //   126: aconst_null
    //   127: putfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   130: aload 5
    //   132: astore 4
    //   134: aload_1
    //   135: invokevirtual 257	java/nio/ByteBuffer:hasRemaining	()Z
    //   138: ifeq +143 -> 281
    //   141: aload_1
    //   142: invokevirtual 375	java/nio/ByteBuffer:mark	()Ljava/nio/Buffer;
    //   145: pop
    //   146: aload 5
    //   148: aload_0
    //   149: aload_1
    //   150: invokevirtual 395	com/mixpanel/android/java_websocket/drafts/Draft_10:translateSingleFrame	(Ljava/nio/ByteBuffer;)Lcom/mixpanel/android/java_websocket/framing/Framedata;
    //   153: invokeinterface 400 2 0
    //   158: pop
    //   159: goto -29 -> 130
    //   162: astore 4
    //   164: aload_1
    //   165: invokevirtual 402	java/nio/ByteBuffer:reset	()Ljava/nio/Buffer;
    //   168: pop
    //   169: aload_0
    //   170: aload_0
    //   171: aload 4
    //   173: invokevirtual 405	com/mixpanel/android/java_websocket/drafts/Draft_10$IncompleteException:getPreferedSize	()I
    //   176: invokevirtual 409	com/mixpanel/android/java_websocket/drafts/Draft_10:checkAlloc	(I)I
    //   179: invokestatic 223	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   182: putfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   185: aload_0
    //   186: getfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   189: aload_1
    //   190: invokevirtual 269	java/nio/ByteBuffer:put	(Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;
    //   193: pop
    //   194: aload 5
    //   196: areturn
    //   197: astore 4
    //   199: aload_0
    //   200: getfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   203: invokevirtual 412	java/nio/ByteBuffer:limit	()I
    //   206: pop
    //   207: aload_0
    //   208: aload 4
    //   210: invokevirtual 405	com/mixpanel/android/java_websocket/drafts/Draft_10$IncompleteException:getPreferedSize	()I
    //   213: invokevirtual 409	com/mixpanel/android/java_websocket/drafts/Draft_10:checkAlloc	(I)I
    //   216: invokestatic 223	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   219: astore 4
    //   221: getstatic 29	com/mixpanel/android/java_websocket/drafts/Draft_10:$assertionsDisabled	Z
    //   224: ifne +26 -> 250
    //   227: aload 4
    //   229: invokevirtual 412	java/nio/ByteBuffer:limit	()I
    //   232: aload_0
    //   233: getfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   236: invokevirtual 412	java/nio/ByteBuffer:limit	()I
    //   239: if_icmpgt +11 -> 250
    //   242: new 240	java/lang/AssertionError
    //   245: dup
    //   246: invokespecial 241	java/lang/AssertionError:<init>	()V
    //   249: athrow
    //   250: aload_0
    //   251: getfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   254: invokevirtual 415	java/nio/ByteBuffer:rewind	()Ljava/nio/Buffer;
    //   257: pop
    //   258: aload 4
    //   260: aload_0
    //   261: getfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   264: invokevirtual 269	java/nio/ByteBuffer:put	(Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;
    //   267: pop
    //   268: aload_0
    //   269: aload 4
    //   271: putfield 365	com/mixpanel/android/java_websocket/drafts/Draft_10:incompleteframe	Ljava/nio/ByteBuffer;
    //   274: aload_0
    //   275: aload_1
    //   276: invokevirtual 417	com/mixpanel/android/java_websocket/drafts/Draft_10:translateFrame	(Ljava/nio/ByteBuffer;)Ljava/util/List;
    //   279: astore 4
    //   281: aload 4
    //   283: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	284	0	this	Draft_10
    //   0	284	1	paramByteBuffer	ByteBuffer
    //   25	38	2	i	int
    //   33	62	3	j	int
    //   132	1	4	localLinkedList1	java.util.LinkedList
    //   162	10	4	localIncompleteException1	IncompleteException
    //   197	12	4	localIncompleteException2	IncompleteException
    //   219	63	4	localObject	Object
    //   7	188	5	localLinkedList2	java.util.LinkedList
    // Exception table:
    //   from	to	target	type
    //   146	159	162	com/mixpanel/android/java_websocket/drafts/Draft_10$IncompleteException
    //   16	34	197	com/mixpanel/android/java_websocket/drafts/Draft_10$IncompleteException
    //   39	71	197	com/mixpanel/android/java_websocket/drafts/Draft_10$IncompleteException
    //   71	130	197	com/mixpanel/android/java_websocket/drafts/Draft_10$IncompleteException
  }
  
  public Framedata translateSingleFrame(ByteBuffer paramByteBuffer)
    throws Draft_10.IncompleteException, InvalidDataException
  {
    int n = paramByteBuffer.remaining();
    int j = 2;
    if (n < 2) {
      throw new IncompleteException(2);
    }
    int m = paramByteBuffer.get();
    if (m >> 8 != 0) {}
    for (boolean bool = true;; bool = false)
    {
      i = (byte)((m & 0x7F) >> 4);
      if (i == 0) {
        break;
      }
      throw new InvalidFrameException("bad rsv " + i);
    }
    int i = paramByteBuffer.get();
    if ((i & 0xFFFFFF80) != 0) {}
    Framedata.Opcode localOpcode;
    for (int k = 1;; k = 0)
    {
      i = (byte)(i & 0x7F);
      localOpcode = toOpcode((byte)(m & 0xF));
      if ((bool) || ((localOpcode != Framedata.Opcode.PING) && (localOpcode != Framedata.Opcode.PONG) && (localOpcode != Framedata.Opcode.CLOSING))) {
        break;
      }
      throw new InvalidFrameException("control frames may no be fragmented");
    }
    if ((i >= 0) && (i <= 125)) {
      if (k == 0) {
        break label397;
      }
    }
    label397:
    for (m = 4;; m = 0)
    {
      j = j + m + i;
      if (n >= j) {
        break label403;
      }
      throw new IncompleteException(j);
      if ((localOpcode == Framedata.Opcode.PING) || (localOpcode == Framedata.Opcode.PONG) || (localOpcode == Framedata.Opcode.CLOSING)) {
        throw new InvalidFrameException("more than 125 octets");
      }
      if (i == 126)
      {
        j = 2 + 2;
        if (n < j) {
          throw new IncompleteException(j);
        }
        localObject = new byte[3];
        localObject[1] = paramByteBuffer.get();
        localObject[2] = paramByteBuffer.get();
        i = new BigInteger((byte[])localObject).intValue();
        break;
      }
      j = 2 + 8;
      if (n < j) {
        throw new IncompleteException(j);
      }
      localObject = new byte[8];
      i = 0;
      while (i < 8)
      {
        localObject[i] = paramByteBuffer.get();
        i += 1;
      }
      long l = new BigInteger((byte[])localObject).longValue();
      if (l > 2147483647L) {
        throw new LimitExedeedException("Payloadsize is to big...");
      }
      i = (int)l;
      break;
    }
    label403:
    Object localObject = ByteBuffer.allocate(checkAlloc(i));
    if (k != 0)
    {
      byte[] arrayOfByte = new byte[4];
      paramByteBuffer.get(arrayOfByte);
      j = 0;
      while (j < i)
      {
        ((ByteBuffer)localObject).put((byte)(paramByteBuffer.get() ^ arrayOfByte[(j % 4)]));
        j += 1;
      }
    }
    ((ByteBuffer)localObject).put(paramByteBuffer.array(), paramByteBuffer.position(), ((ByteBuffer)localObject).limit());
    paramByteBuffer.position(paramByteBuffer.position() + ((ByteBuffer)localObject).limit());
    if (localOpcode == Framedata.Opcode.CLOSING) {
      paramByteBuffer = new CloseFrameBuilder();
    }
    for (;;)
    {
      ((ByteBuffer)localObject).flip();
      paramByteBuffer.setPayload((ByteBuffer)localObject);
      return paramByteBuffer;
      paramByteBuffer = new FramedataImpl1();
      paramByteBuffer.setFin(bool);
      paramByteBuffer.setOptcode(localOpcode);
    }
  }
  
  private class IncompleteException
    extends Throwable
  {
    private static final long serialVersionUID = 7330519489840500997L;
    private int preferedsize;
    
    public IncompleteException(int paramInt)
    {
      this.preferedsize = paramInt;
    }
    
    public int getPreferedSize()
    {
      return this.preferedsize;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/drafts/Draft_10.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
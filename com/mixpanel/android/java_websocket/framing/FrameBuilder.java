package com.mixpanel.android.java_websocket.framing;

import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import java.nio.ByteBuffer;

public abstract interface FrameBuilder
  extends Framedata
{
  public abstract void setFin(boolean paramBoolean);
  
  public abstract void setOptcode(Framedata.Opcode paramOpcode);
  
  public abstract void setPayload(ByteBuffer paramByteBuffer)
    throws InvalidDataException;
  
  public abstract void setTransferemasked(boolean paramBoolean);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/framing/FrameBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
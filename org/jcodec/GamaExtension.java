package org.jcodec;

import java.nio.ByteBuffer;

public class GamaExtension
  extends Box
{
  private float gamma;
  
  public GamaExtension(float paramFloat)
  {
    super(new Header(fourcc(), 0L));
    this.gamma = paramFloat;
  }
  
  public GamaExtension(Box paramBox)
  {
    super(paramBox);
  }
  
  public GamaExtension(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "gama";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putInt((int)(this.gamma * 65536.0F));
  }
  
  public float getGamma()
  {
    return this.gamma;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    this.gamma = (paramByteBuffer.getInt() / 65536.0F);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/GamaExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.optimizely.Network.websocket;

class WebSocketFrameHeader
{
  private boolean mFin;
  private int mHeaderLen;
  private byte[] mMask;
  private int mOpcode;
  private int mPayloadLen;
  private int mReserved;
  private int mTotalLen;
  
  public int getHeaderLength()
  {
    return this.mHeaderLen;
  }
  
  public byte[] getMask()
  {
    return this.mMask;
  }
  
  public int getOpcode()
  {
    return this.mOpcode;
  }
  
  public int getPayloadLength()
  {
    return this.mPayloadLen;
  }
  
  public int getReserved()
  {
    return this.mReserved;
  }
  
  public int getTotalLength()
  {
    return this.mTotalLen;
  }
  
  public boolean isFin()
  {
    return this.mFin;
  }
  
  public void setFin(boolean paramBoolean)
  {
    this.mFin = paramBoolean;
  }
  
  public void setHeaderLength(int paramInt)
  {
    this.mHeaderLen = paramInt;
  }
  
  public void setMask(byte[] paramArrayOfByte)
  {
    this.mMask = paramArrayOfByte;
  }
  
  public void setOpcode(int paramInt)
  {
    this.mOpcode = paramInt;
  }
  
  public void setPayloadLength(int paramInt)
  {
    this.mPayloadLen = paramInt;
  }
  
  public void setReserved(int paramInt)
  {
    this.mReserved = paramInt;
  }
  
  public void setTotalLen(int paramInt)
  {
    this.mTotalLen = paramInt;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/websocket/WebSocketFrameHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
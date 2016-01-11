package com.optimizely.Network.websocket;

import android.support.annotation.NonNull;

public class WebSocketOptions
{
  private boolean mMaskClientFrames;
  private int mMaxFramePayloadSize;
  private int mMaxMessagePayloadSize;
  private boolean mReceiveTextMessagesRaw;
  private int mReconnectInterval;
  private int mSocketConnectTimeout;
  private int mSocketReceiveTimeout;
  private boolean mTcpNoDelay;
  private boolean mValidateIncomingUtf8;
  
  public WebSocketOptions()
  {
    this.mMaxFramePayloadSize = 1048576;
    this.mMaxMessagePayloadSize = 1048576;
    this.mReceiveTextMessagesRaw = false;
    this.mTcpNoDelay = true;
    this.mSocketReceiveTimeout = 200;
    this.mSocketConnectTimeout = 6000;
    this.mValidateIncomingUtf8 = true;
    this.mMaskClientFrames = true;
    this.mReconnectInterval = 0;
  }
  
  public WebSocketOptions(@NonNull WebSocketOptions paramWebSocketOptions)
  {
    this.mMaxFramePayloadSize = paramWebSocketOptions.mMaxFramePayloadSize;
    this.mMaxMessagePayloadSize = paramWebSocketOptions.mMaxMessagePayloadSize;
    this.mReceiveTextMessagesRaw = paramWebSocketOptions.mReceiveTextMessagesRaw;
    this.mTcpNoDelay = paramWebSocketOptions.mTcpNoDelay;
    this.mSocketReceiveTimeout = paramWebSocketOptions.mSocketReceiveTimeout;
    this.mSocketConnectTimeout = paramWebSocketOptions.mSocketConnectTimeout;
    this.mValidateIncomingUtf8 = paramWebSocketOptions.mValidateIncomingUtf8;
    this.mMaskClientFrames = paramWebSocketOptions.mMaskClientFrames;
    this.mReconnectInterval = paramWebSocketOptions.mReconnectInterval;
  }
  
  public boolean getMaskClientFrames()
  {
    return this.mMaskClientFrames;
  }
  
  public int getMaxFramePayloadSize()
  {
    return this.mMaxFramePayloadSize;
  }
  
  public int getMaxMessagePayloadSize()
  {
    return this.mMaxMessagePayloadSize;
  }
  
  public boolean getReceiveTextMessagesRaw()
  {
    return this.mReceiveTextMessagesRaw;
  }
  
  public int getReconnectInterval()
  {
    return this.mReconnectInterval;
  }
  
  public int getSocketConnectTimeout()
  {
    return this.mSocketConnectTimeout;
  }
  
  public int getSocketReceiveTimeout()
  {
    return this.mSocketReceiveTimeout;
  }
  
  public boolean getTcpNoDelay()
  {
    return this.mTcpNoDelay;
  }
  
  public boolean getValidateIncomingUtf8()
  {
    return this.mValidateIncomingUtf8;
  }
  
  public void setMaskClientFrames(boolean paramBoolean)
  {
    this.mMaskClientFrames = paramBoolean;
  }
  
  public void setMaxFramePayloadSize(int paramInt)
  {
    if (paramInt > 0)
    {
      this.mMaxFramePayloadSize = paramInt;
      if (this.mMaxMessagePayloadSize < this.mMaxFramePayloadSize) {
        this.mMaxMessagePayloadSize = this.mMaxFramePayloadSize;
      }
    }
  }
  
  public void setMaxMessagePayloadSize(int paramInt)
  {
    if (paramInt > 0)
    {
      this.mMaxMessagePayloadSize = paramInt;
      if (this.mMaxMessagePayloadSize < this.mMaxFramePayloadSize) {
        this.mMaxFramePayloadSize = this.mMaxMessagePayloadSize;
      }
    }
  }
  
  public void setReceiveTextMessagesRaw(boolean paramBoolean)
  {
    this.mReceiveTextMessagesRaw = paramBoolean;
  }
  
  public void setReconnectInterval(int paramInt)
  {
    this.mReconnectInterval = paramInt;
  }
  
  public void setSocketConnectTimeout(int paramInt)
  {
    if (paramInt >= 0) {
      this.mSocketConnectTimeout = paramInt;
    }
  }
  
  public void setSocketReceiveTimeout(int paramInt)
  {
    if (paramInt >= 0) {
      this.mSocketReceiveTimeout = paramInt;
    }
  }
  
  public void setTcpNoDelay(boolean paramBoolean)
  {
    this.mTcpNoDelay = paramBoolean;
  }
  
  public void setValidateIncomingUtf8(boolean paramBoolean)
  {
    this.mValidateIncomingUtf8 = paramBoolean;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/websocket/WebSocketOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
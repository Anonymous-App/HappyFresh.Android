package com.optimizely.Network.websocket;

import android.support.annotation.NonNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

class NoCopyByteArrayOutputStream
  extends ByteArrayOutputStream
{
  public NoCopyByteArrayOutputStream() {}
  
  public NoCopyByteArrayOutputStream(int paramInt)
  {
    super(paramInt);
  }
  
  @NonNull
  public byte[] getByteArray()
  {
    return this.buf;
  }
  
  @NonNull
  public InputStream getInputStream()
  {
    return new ByteArrayInputStream(this.buf, 0, this.count);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/websocket/NoCopyByteArrayOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.optimizely.Network.Socket.Listeners;

import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;

public class NoOpListener
  implements WebSocket.WebSocketConnectionObserver
{
  public void onBinaryMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {}
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen() {}
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {}
  
  public void onTextMessage(String paramString) {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/NoOpListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
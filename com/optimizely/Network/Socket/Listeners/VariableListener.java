package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Variable.OptimizelyVariables;

public class VariableListener
  implements WebSocket.WebSocketConnectionObserver
{
  private final OptimizelyVariables optimizelyVariables;
  
  public VariableListener(@NonNull OptimizelyVariables paramOptimizelyVariables)
  {
    this.optimizelyVariables = paramOptimizelyVariables;
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte)
  {
    throw new UnknownError("VariableListener doesn't support onBinaryMessage");
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen() {}
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
  {
    throw new UnknownError("VariableListener doesn't support onRawTextMessage");
  }
  
  public void onTextMessage(String paramString)
  {
    this.optimizelyVariables.sendAllVariablesToEditor();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/VariableListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
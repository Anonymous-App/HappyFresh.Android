package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.CodeBlocks.OptimizelyCodeBlocks;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;

public class CodeBlockListener
  implements WebSocket.WebSocketConnectionObserver
{
  @NonNull
  private final OptimizelyCodeBlocks codeBlocks;
  
  public CodeBlockListener(@NonNull OptimizelyCodeBlocks paramOptimizelyCodeBlocks)
  {
    this.codeBlocks = paramOptimizelyCodeBlocks;
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte)
  {
    throw new UnknownError("CodeBlockListener doesn't support onBinaryMessage");
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen() {}
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
  {
    throw new UnknownError("CodeBlockListener doesn't support onRawTextMessage");
  }
  
  public void onTextMessage(String paramString)
  {
    this.codeBlocks.sendCodeBlocks();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/CodeBlockListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
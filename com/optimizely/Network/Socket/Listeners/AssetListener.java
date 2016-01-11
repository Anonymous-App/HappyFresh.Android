package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.Assets.OptimizelyAssets;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;

public class AssetListener
  implements WebSocket.WebSocketConnectionObserver
{
  @NonNull
  private final OptimizelyAssets assets;
  
  public AssetListener(@NonNull OptimizelyAssets paramOptimizelyAssets)
  {
    this.assets = paramOptimizelyAssets;
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte) {}
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen() {}
  
  public void onRawTextMessage(byte[] paramArrayOfByte) {}
  
  public void onTextMessage(String paramString)
  {
    this.assets.sendToEditor();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/AssetListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
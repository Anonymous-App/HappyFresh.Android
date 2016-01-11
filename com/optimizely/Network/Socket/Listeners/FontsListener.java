package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Optimizely;
import com.optimizely.fonts.OptimizelyFontsManager;

public class FontsListener
  implements WebSocket.WebSocketConnectionObserver
{
  @NonNull
  private final Optimizely optimizely;
  
  public FontsListener(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("DeviceInfoListener doesn't support onBinaryMessage");
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen()
  {
    this.optimizely.getFontsManager().sendFonts();
  }
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("DeviceInfoListener doesn't support onRawTextMessage");
  }
  
  public void onTextMessage(String paramString)
  {
    this.optimizely.getFontsManager().sendFonts();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/FontsListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.Network.OptimizelyScreenshot;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;

public class ScreenshotListener
  implements WebSocket.WebSocketConnectionObserver
{
  @NonNull
  private final OptimizelyScreenshot screenshot;
  
  public ScreenshotListener(@NonNull OptimizelyScreenshot paramOptimizelyScreenshot)
  {
    this.screenshot = paramOptimizelyScreenshot;
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte)
  {
    throw new UnknownError("RegisterSetVariableListener doesn't support onBinaryMessage");
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen() {}
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
  {
    throw new UnknownError("RegisterSetVariableListener doesn't support onRawTextMessage");
  }
  
  public void onTextMessage(@NonNull String paramString)
  {
    this.screenshot.sendOrientationToEditorIfNecessary(true);
    this.screenshot.sendScreenShotToEditor();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/ScreenshotListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Optimizely;
import com.optimizely.View.OptimizelyViews;
import com.optimizely.View.ViewUtils;

public class ViewsHierarchyListener
  implements WebSocket.WebSocketConnectionObserver
{
  private final Optimizely optimizely;
  private final OptimizelyViews views;
  
  public ViewsHierarchyListener(@NonNull OptimizelyViews paramOptimizelyViews, @NonNull Optimizely paramOptimizely)
  {
    this.views = paramOptimizelyViews;
    this.optimizely = paramOptimizely;
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("ViewsHierarchyListener doesn't support onBinaryMessage");
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen() {}
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("ViewsHierarchyListener doesn't support onRawTextMessage");
  }
  
  public void onTextMessage(String paramString)
  {
    ViewUtils.sendViewHierarchy(this.views.getCurrentRootView(), this.optimizely);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/ViewsHierarchyListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
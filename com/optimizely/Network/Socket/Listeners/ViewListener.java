package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Optimizely;
import com.optimizely.View.OptimizelyViews;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewListener
  implements WebSocket.WebSocketConnectionObserver
{
  private static final String GET_VIEW_LISTENER_COMPONENT = "GetViewListener";
  private final Optimizely optimizely;
  private final OptimizelyViews views;
  
  public ViewListener(@NonNull OptimizelyViews paramOptimizelyViews, @NonNull Optimizely paramOptimizely)
  {
    this.views = paramOptimizelyViews;
    this.optimizely = paramOptimizely;
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte)
  {
    throw new UnknownError("GetViewListener doesn't support onBinaryMessage");
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen() {}
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
  {
    throw new UnknownError("GetViewListener doesn't support onRawTextMessage");
  }
  
  public void onTextMessage(String paramString)
  {
    try
    {
      String str = new JSONObject(paramString).getString("viewId");
      if (str != null)
      {
        this.views.sendViewToSocket(str, true);
        return;
      }
      this.optimizely.verboseLog(true, "GetViewListener", "Malformed socket request for view: %s", new Object[] { paramString });
      return;
    }
    catch (JSONException localJSONException)
    {
      this.optimizely.verboseLog(true, "GetViewListener", "Failed to convert payload {%1$s} to jsonObject with exception %2$s", new Object[] { paramString, localJSONException.getLocalizedMessage() });
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/ViewListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
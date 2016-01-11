package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Optimizely;
import com.optimizely.View.OptimizelyViews;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewPropertyListener
  implements WebSocket.WebSocketConnectionObserver
{
  private static final String SET_VIEW_PROPERTY_LISTENER_COMPONENT = "SetViewPropertyListener";
  private final Optimizely optimizely;
  private final OptimizelyViews views;
  
  public ViewPropertyListener(@NonNull OptimizelyViews paramOptimizelyViews, @NonNull Optimizely paramOptimizely)
  {
    this.views = paramOptimizelyViews;
    this.optimizely = paramOptimizely;
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("SetViewPropertyListener doesn't support onBinaryMessage");
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen() {}
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("SetViewPropertyListener doesn't support onRawTextMessage");
  }
  
  public void onTextMessage(String paramString)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      String str1 = localJSONObject.getString("viewId");
      String str2 = localJSONObject.getString("key");
      localJSONObject = localJSONObject.getJSONObject("value");
      if ((str2 == null) || (localJSONObject == null))
      {
        this.optimizely.verboseLog(true, "SetViewPropertyListener", "Malformed view property message: %s", new Object[] { paramString });
        return;
      }
      this.views.setViewProperty(str1, str2, localJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      this.optimizely.verboseLog(true, "SetViewPropertyListener", "Failed to convert payload {%1$s} to jsonObject with exception %2$s", new Object[] { paramString, localJSONException.getLocalizedMessage() });
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/ViewPropertyListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
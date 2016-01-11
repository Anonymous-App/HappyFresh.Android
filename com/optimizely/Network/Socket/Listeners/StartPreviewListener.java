package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Optimizely;
import com.optimizely.Preview.OptimizelyPreview;
import org.json.JSONException;
import org.json.JSONObject;

public class StartPreviewListener
  implements WebSocket.WebSocketConnectionObserver
{
  private static final String START_PREVIEW_LISTENER = "StartPreviewListener";
  private final Optimizely optimizely;
  
  public StartPreviewListener(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("StartPreviewListener doesn't support onBinaryMessage");
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen() {}
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("StartPreviewListener doesn't support onRawTextMessage");
  }
  
  public void onTextMessage(String paramString)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString).getJSONObject("data");
      if (localJSONObject == null)
      {
        this.optimizely.verboseLog(true, "StartPreviewListener", "Malformed preview data. Refusing to restart into preview mode", new Object[0]);
        return;
      }
      if (this.optimizely.getPreviewManager().savePreviewData(localJSONObject.toString()))
      {
        this.optimizely.getPreviewManager().restartInPreviewMode();
        return;
      }
    }
    catch (JSONException localJSONException)
    {
      this.optimizely.verboseLog(true, "StartPreviewListener", "Failed to convert payload {%1$s} to jsonObject with exception %2$s", new Object[] { paramString, localJSONException.getLocalizedMessage() });
      return;
    }
    this.optimizely.verboseLog(true, "StartPreviewListener", "Couldn't save preview data. Refusing to restart into preview mode", new Object[0]);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/StartPreviewListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
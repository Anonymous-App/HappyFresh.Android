package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Optimizely;
import org.json.JSONArray;

public class GetDebugInfoListener
  implements WebSocket.WebSocketConnectionObserver
{
  private static final String GET_DEBUG_INFO_LISTENER = "GetDebugInfoListener";
  @NonNull
  private final Gson gson;
  @NonNull
  private final Optimizely optimizely;
  
  public GetDebugInfoListener(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
    this.gson = new Gson();
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("GetDebugInfoListener doesn't support onBinaryMessage");
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen() {}
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("GetDebugInfoListener doesn't support onRawTextMessage");
  }
  
  public void onTextMessage(String paramString)
  {
    paramString = this.optimizely.getOptimizelyData();
    Object localObject = paramString.getCodeRevision();
    if (localObject != null) {}
    for (double d = ((Double)localObject).doubleValue();; d = -1.0D)
    {
      localObject = Double.toString(d);
      String str = this.gson.toJson(paramString.getGoals());
      this.optimizely.verboseLog("GetDebugInfoListener", "Running with code revision %1$s.\n\nActive experiments are: %2$s,\n\nGoals for all experiments are %3$s", new Object[] { localObject, paramString.getExperimentLogInformation().toString(), str });
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/GetDebugInfoListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
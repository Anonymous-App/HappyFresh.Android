package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Optimizely;
import com.optimizely.Variable.OptimizelyVariables;
import com.optimizely.View.OptimizelyViews;
import java.util.HashMap;
import java.util.Map;

public class EditorClearChangesListener
  implements WebSocket.WebSocketConnectionObserver
{
  private static final String EDITOR_CLEAR_CHANGES_LISTENER_COMPONENT = "EditorClearChangesListenerComponent";
  @NonNull
  private final Optimizely optimizely;
  
  public EditorClearChangesListener(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("EditorClearChangesListenerComponent doesn't support onBinaryMessage");
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen()
  {
    this.optimizely.verboseLog(false, "EditorClearChangesListenerComponent", "Socket connection opened, resetting visual changes to receive edits", new Object[0]);
    this.optimizely.getViews().resetViewChangeHistory();
    this.optimizely.getOptimizelyVariables().resetVariableValues();
  }
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("EditorClearChangesListenerComponent doesn't support onRawTextMessage");
  }
  
  public void onTextMessage(String paramString)
  {
    paramString = new HashMap(2);
    paramString.put("action", "clear");
    this.optimizely.sendMap(paramString);
    this.optimizely.verboseLog(false, "EditorClearChangesListenerComponent", "Resetting visual changes and live variable values", new Object[0]);
    this.optimizely.getViews().resetViewChangeHistory();
    paramString = this.optimizely.getForegroundActivity();
    if (paramString != null) {
      this.optimizely.getViews().updateCurrentRootView(paramString);
    }
    this.optimizely.getOptimizelyVariables().resetVariableValues();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/EditorClearChangesListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
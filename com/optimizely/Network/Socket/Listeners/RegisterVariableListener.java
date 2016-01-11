package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.Core.OptimizelyCodec;
import com.optimizely.JSON.OptimizelyVariable;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Optimizely;
import com.optimizely.Variable.OptimizelyVariables;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterVariableListener
  implements WebSocket.WebSocketConnectionObserver
{
  private static final String REGISTER_VARIABLE_LISTENER_COMPONENT = "RegisterVariableListener";
  private final Optimizely optimizely;
  private final OptimizelyVariables optimizelyVariables;
  
  public RegisterVariableListener(@NonNull OptimizelyVariables paramOptimizelyVariables, @NonNull Optimizely paramOptimizely)
  {
    this.optimizelyVariables = paramOptimizelyVariables;
    this.optimizely = paramOptimizely;
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
    this.optimizely.verboseLog("RegisterVariableListener", "Received payload {%1$s} from socket server", new Object[] { paramString });
    try
    {
      Object localObject2 = new JSONObject(paramString).getJSONObject("variable");
      if (localObject2 != null)
      {
        Object localObject1 = ((JSONObject)localObject2).getString("key");
        String str = ((JSONObject)localObject2).getString("type");
        localObject2 = ((JSONObject)localObject2).get("value");
        if ((localObject1 != null) && (str != null))
        {
          localObject1 = OptimizelyCodec.decode((String)localObject1, str, localObject2);
          this.optimizelyVariables.setVariableHandler((OptimizelyVariable)localObject1);
        }
      }
      else
      {
        this.optimizely.verboseLog(true, "RegisterVariableListener", "Malformed socket request for variable: %s", new Object[] { paramString });
        return;
      }
    }
    catch (JSONException localJSONException)
    {
      this.optimizely.verboseLog(true, "RegisterVariableListener", "Failed to convert payload to jsonObject %s with exception %s", new Object[] { paramString, localJSONException.getLocalizedMessage() });
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/RegisterVariableListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
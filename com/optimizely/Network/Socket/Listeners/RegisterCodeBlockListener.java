package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.CodeBlocks.OptimizelyCodeBlocks;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Optimizely;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterCodeBlockListener
  implements WebSocket.WebSocketConnectionObserver
{
  private static final String REGISTER_CODE_BLOCK_LISTENER_COMPONENT = "RegisterCodeBlockListener";
  private final OptimizelyCodeBlocks codeBlocks;
  private final Optimizely optimizely;
  
  public RegisterCodeBlockListener(@NonNull OptimizelyCodeBlocks paramOptimizelyCodeBlocks, @NonNull Optimizely paramOptimizely)
  {
    this.codeBlocks = paramOptimizelyCodeBlocks;
    this.optimizely = paramOptimizely;
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte) {}
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen() {}
  
  public void onRawTextMessage(byte[] paramArrayOfByte) {}
  
  public void onTextMessage(@NonNull String paramString)
  {
    this.optimizely.verboseLog("RegisterCodeBlockListener", "Received payload {%1$s} from socket server", new Object[] { paramString });
    try
    {
      Object localObject = new JSONObject(paramString).getJSONObject("codeTest");
      String str = ((JSONObject)localObject).getString("key");
      localObject = ((JSONObject)localObject).getJSONObject("value").getString("blockKey");
      this.codeBlocks.forceBranchForBlock(str, (String)localObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      this.optimizely.verboseLog(true, "RegisterCodeBlockListener", "Failed to convert payload {%1$s} to jsonObject with exception %2$s", new Object[] { paramString, localJSONException.getLocalizedMessage() });
      return;
    }
    catch (NullPointerException paramString)
    {
      this.optimizely.verboseLog(true, "RegisterCodeBlockListener", "Malformed code block request from socket", new Object[0]);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/RegisterCodeBlockListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
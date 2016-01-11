package com.optimizely.Network.Socket.Listeners;

import android.support.annotation.NonNull;
import com.optimizely.CodeBlocks.OptimizelyCodeBlocks;
import com.optimizely.Core.OptimizelyCodec;
import com.optimizely.JSON.OptimizelyVariable;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Optimizely;
import com.optimizely.Variable.OptimizelyVariables;
import com.optimizely.View.OptimizelyViews;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangeVariationListener
  implements WebSocket.WebSocketConnectionObserver
{
  private final OptimizelyCodeBlocks codeBlocks;
  private final Optimizely optimizely;
  private final OptimizelyVariables optimizelyVariables;
  private final OptimizelyViews views;
  
  public ChangeVariationListener(@NonNull OptimizelyViews paramOptimizelyViews, @NonNull OptimizelyCodeBlocks paramOptimizelyCodeBlocks, @NonNull OptimizelyVariables paramOptimizelyVariables, @NonNull Optimizely paramOptimizely)
  {
    this.views = paramOptimizelyViews;
    this.codeBlocks = paramOptimizelyCodeBlocks;
    this.optimizelyVariables = paramOptimizelyVariables;
    this.optimizely = paramOptimizely;
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte)
  {
    throw new UnknownError("ChangeVariationListener doesn't support onBinaryMessage");
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen() {}
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
  {
    throw new UnknownError("ChangeVariationListener doesn't support onRawTextMessage");
  }
  
  public void onTextMessage(String paramString)
  {
    for (;;)
    {
      int i;
      try
      {
        localObject1 = new JSONObject(paramString);
        localObject3 = ((JSONObject)localObject1).getJSONArray("viewChanges");
        localObject2 = ((JSONObject)localObject1).getJSONArray("variableChanges");
        localObject1 = ((JSONObject)localObject1).getJSONArray("codeBlockChanges");
        if (!this.optimizely.isVisualExperimentsEnabled()) {
          break label268;
        }
        i = 0;
        if (i >= ((JSONArray)localObject3).length()) {
          break label268;
        }
        Object localObject4 = ((JSONArray)localObject3).getJSONObject(i);
        this.views.setViewProperty(((JSONObject)localObject4).getString("viewId"), ((JSONObject)localObject4).getString("key"), ((JSONObject)localObject4).getJSONObject("value"));
        i += 1;
        continue;
        if (i >= ((JSONArray)localObject2).length()) {
          break label280;
        }
        Object localObject5 = ((JSONArray)localObject2).getJSONObject(i).getJSONObject("variable");
        localObject3 = ((JSONObject)localObject5).getString("key");
        localObject4 = ((JSONObject)localObject5).getString("type");
        localObject5 = ((JSONObject)localObject5).getString("value");
        if ((localObject3 == null) || (localObject4 == null)) {
          break label273;
        }
        localObject3 = OptimizelyCodec.decode((String)localObject3, (String)localObject4, localObject5);
        this.optimizelyVariables.setVariableHandler((OptimizelyVariable)localObject3);
      }
      catch (JSONException localJSONException)
      {
        Object localObject1;
        Object localObject3;
        Object localObject2;
        this.optimizely.verboseLog(true, "ChangeVariationListener", "Failed to convert payload {%1$s} to JSONObject with exception: %2$s", new Object[] { paramString, localJSONException.getLocalizedMessage() });
      }
      if (i < ((JSONArray)localObject1).length())
      {
        localObject3 = ((JSONArray)localObject1).getJSONObject(i).getJSONObject("codeTest");
        localObject2 = ((JSONObject)localObject3).getString("key");
        localObject3 = ((JSONObject)localObject3).getJSONObject("value").getString("blockKey");
        this.codeBlocks.forceBranchForBlock((String)localObject3, (String)localObject2);
        i += 1;
      }
      else
      {
        return;
        label268:
        i = 0;
        continue;
        label273:
        i += 1;
        continue;
        label280:
        i = 0;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/ChangeVariationListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
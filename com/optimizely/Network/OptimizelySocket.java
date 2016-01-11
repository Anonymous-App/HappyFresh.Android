package com.optimizely.Network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.JsonObject;
import com.optimizely.Network.websocket.WebSocket;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Network.websocket.WebSocketConnection;
import com.optimizely.Network.websocket.WebSocketException;
import com.optimizely.Network.websocket.WebSocketOptions;
import com.optimizely.Optimizely;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class OptimizelySocket
  implements WebSocket.WebSocketConnectionObserver
{
  private static final String OPTIMIZELY_SOCKET_COMPONENT = "OptimizelySocket";
  @NonNull
  public static String SOCKET_HOSTNAME;
  @Nullable
  private List<Map<String, Object>> currentBatch;
  private final String deviceId;
  private boolean isReady;
  @NonNull
  private final Map<String, ArrayList<WebSocket.WebSocketConnectionObserver>> message_listeners;
  @NonNull
  private final List<MessageMonitor> monitors = new ArrayList();
  @NonNull
  private final Optimizely optimizely;
  @Nullable
  WebSocket socket;
  private final String token;
  
  static
  {
    if (!OptimizelySocket.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      SOCKET_HOSTNAME = "www.optimizelysockets.com";
      return;
    }
  }
  
  public OptimizelySocket(String paramString1, String paramString2, @NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
    this.socket = null;
    this.token = paramString1;
    this.deviceId = paramString2;
    this.message_listeners = new HashMap();
  }
  
  @NonNull
  private WebSocketOptions createReconnectableSocketOptions()
  {
    WebSocketOptions localWebSocketOptions = new WebSocketOptions();
    localWebSocketOptions.setReconnectInterval(5000);
    return localWebSocketOptions;
  }
  
  @Nullable
  private String getAction(@NonNull String paramString)
  {
    try
    {
      String str = new JSONObject(paramString).getString("action");
      return str;
    }
    catch (JSONException localJSONException)
    {
      this.optimizely.verboseLog(true, "OptimizelySocket", "Failed to get action from payload %1$s with exception %2$s ", new Object[] { paramString, localJSONException.getLocalizedMessage() });
    }
    return null;
  }
  
  @Nullable
  private String getAction(@NonNull byte[] paramArrayOfByte)
  {
    try
    {
      String str = getAction(new String(paramArrayOfByte, "UTF-8"));
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      this.optimizely.verboseLog(true, "OptimizelySocket", "Failed to convert payload to string with exception %1$s ", new Object[] { paramArrayOfByte, localUnsupportedEncodingException.getLocalizedMessage() });
    }
    return null;
  }
  
  private void notifyMonitors(Object paramObject, Class paramClass, boolean paramBoolean)
  {
    Iterator localIterator = this.monitors.iterator();
    while (localIterator.hasNext())
    {
      MessageMonitor localMessageMonitor = (MessageMonitor)localIterator.next();
      if (paramBoolean) {
        localMessageMonitor.onIncomingMessage(paramObject, paramClass);
      } else {
        localMessageMonitor.onOutgoingMessage(paramObject, paramClass);
      }
    }
  }
  
  private void sendBatch(@NonNull List<Map<String, Object>> paramList)
    throws IOException
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("action", "batch");
    localHashMap.put("messages", paramList);
    localHashMap.put("_source", "device");
    notifyMonitors(localHashMap, Map.class, false);
    if (this.socket != null)
    {
      this.socket.sendMapMessage(localHashMap);
      return;
    }
    this.optimizely.verboseLog(true, "OptimizelySocket", "Socket not yet opened, message not sent", new Object[0]);
  }
  
  public void addListener(String paramString, WebSocket.WebSocketConnectionObserver paramWebSocketConnectionObserver)
  {
    if (this.message_listeners.get(paramString) == null) {
      this.message_listeners.put(paramString, new ArrayList());
    }
    ((ArrayList)this.message_listeners.get(paramString)).add(paramWebSocketConnectionObserver);
  }
  
  public void addMonitor(MessageMonitor paramMessageMonitor)
  {
    this.monitors.add(paramMessageMonitor);
  }
  
  /* Error */
  public void batchBegin()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 193	com/optimizely/Network/OptimizelySocket:currentBatch	Ljava/util/List;
    //   6: ifnull +21 -> 27
    //   9: aload_0
    //   10: getfield 59	com/optimizely/Network/OptimizelySocket:optimizely	Lcom/optimizely/Optimizely;
    //   13: ldc 15
    //   15: ldc -61
    //   17: iconst_0
    //   18: anewarray 4	java/lang/Object
    //   21: invokevirtual 198	com/optimizely/Optimizely:verboseLog	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   24: aload_0
    //   25: monitorexit
    //   26: return
    //   27: aload_0
    //   28: new 54	java/util/ArrayList
    //   31: dup
    //   32: invokespecial 55	java/util/ArrayList:<init>	()V
    //   35: putfield 193	com/optimizely/Network/OptimizelySocket:currentBatch	Ljava/util/List;
    //   38: goto -14 -> 24
    //   41: astore_1
    //   42: aload_0
    //   43: monitorexit
    //   44: aload_1
    //   45: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	46	0	this	OptimizelySocket
    //   41	4	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	24	41	finally
    //   27	38	41	finally
  }
  
  /* Error */
  public void batchEnd()
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 193	com/optimizely/Network/OptimizelySocket:currentBatch	Ljava/util/List;
    //   6: astore_1
    //   7: aload_0
    //   8: aconst_null
    //   9: putfield 193	com/optimizely/Network/OptimizelySocket:currentBatch	Ljava/util/List;
    //   12: aload_1
    //   13: ifnull +11 -> 24
    //   16: aload_0
    //   17: aload_1
    //   18: invokespecial 201	com/optimizely/Network/OptimizelySocket:sendBatch	(Ljava/util/List;)V
    //   21: aload_0
    //   22: monitorexit
    //   23: return
    //   24: aload_0
    //   25: getfield 59	com/optimizely/Network/OptimizelySocket:optimizely	Lcom/optimizely/Optimizely;
    //   28: ldc 15
    //   30: ldc -53
    //   32: iconst_0
    //   33: anewarray 4	java/lang/Object
    //   36: invokevirtual 198	com/optimizely/Optimizely:verboseLog	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   39: goto -18 -> 21
    //   42: astore_1
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_1
    //   46: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	47	0	this	OptimizelySocket
    //   6	12	1	localList	List
    //   42	4	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	12	42	finally
    //   16	21	42	finally
    //   24	39	42	finally
  }
  
  public boolean isReady()
  {
    return this.isReady;
  }
  
  public void onBinaryMessage(@NonNull byte[] paramArrayOfByte)
  {
    notifyMonitors(paramArrayOfByte, byte[].class, true);
    Object localObject = getAction(paramArrayOfByte);
    if (localObject != null)
    {
      localObject = (List)this.message_listeners.get(localObject);
      if (localObject != null)
      {
        localObject = ((List)localObject).iterator();
        while (((Iterator)localObject).hasNext()) {
          ((WebSocket.WebSocketConnectionObserver)((Iterator)localObject).next()).onBinaryMessage(paramArrayOfByte);
        }
      }
    }
    else
    {
      this.optimizely.verboseLog(true, "OptimizelySocket", "No action found in message %1$s", new Object[] { new String(paramArrayOfByte) });
    }
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString)
  {
    if (this.isReady) {
      this.optimizely.verboseLog("OptimizelySocket", "Socket Closed " + paramString, new Object[0]);
    }
    this.isReady = false;
    Iterator localIterator = this.message_listeners.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (Map.Entry)localIterator.next();
      if (((Map.Entry)localObject).getValue() != null)
      {
        localObject = ((ArrayList)((Map.Entry)localObject).getValue()).iterator();
        while (((Iterator)localObject).hasNext()) {
          ((WebSocket.WebSocketConnectionObserver)((Iterator)localObject).next()).onClose(paramWebSocketCloseNotification, paramString);
        }
      }
    }
  }
  
  public void onOpen()
  {
    this.isReady = true;
    this.optimizely.verboseLog("OptimizelySocket", "Socket Opened", new Object[0]);
    Iterator localIterator1 = this.message_listeners.entrySet().iterator();
    while (localIterator1.hasNext())
    {
      Iterator localIterator2 = ((ArrayList)((Map.Entry)localIterator1.next()).getValue()).iterator();
      while (localIterator2.hasNext()) {
        ((WebSocket.WebSocketConnectionObserver)localIterator2.next()).onOpen();
      }
    }
  }
  
  public void onRawTextMessage(@NonNull byte[] paramArrayOfByte)
  {
    notifyMonitors(paramArrayOfByte, byte[].class, true);
    String str = getAction(paramArrayOfByte);
    if (str != null)
    {
      List localList = (List)this.message_listeners.get(str);
      if (localList != null)
      {
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext()) {
          ((WebSocket.WebSocketConnectionObserver)localIterator.next()).onRawTextMessage(paramArrayOfByte);
        }
      }
      if ((localList == null) || (localList.isEmpty())) {
        this.optimizely.verboseLog(true, "OptimizelySocket", "No message listener for action %1$s", new Object[] { str });
      }
      return;
    }
    this.optimizely.verboseLog(true, "OptimizelySocket", "No action found in message %1$s", new Object[] { new String(paramArrayOfByte) });
  }
  
  public void onTextMessage(@NonNull String paramString)
  {
    notifyMonitors(paramString, String.class, true);
    String str = getAction(paramString);
    if (str != null)
    {
      List localList = (List)this.message_listeners.get(str);
      if (localList != null)
      {
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext()) {
          ((WebSocket.WebSocketConnectionObserver)localIterator.next()).onTextMessage(paramString);
        }
      }
      if ((localList == null) || (localList.isEmpty())) {
        this.optimizely.verboseLog(true, "OptimizelySocket", "No message listener for action %1$s", new Object[] { str });
      }
      return;
    }
    this.optimizely.verboseLog(true, "OptimizelySocket", "No action found in message %1$s", new Object[] { paramString });
  }
  
  public void removeListener(String paramString, WebSocket.WebSocketConnectionObserver paramWebSocketConnectionObserver)
  {
    paramString = (ArrayList)this.message_listeners.get(paramString);
    if (paramString != null) {
      paramString.remove(paramWebSocketConnectionObserver);
    }
  }
  
  public void removeMonitor(MessageMonitor paramMessageMonitor)
  {
    this.monitors.remove(paramMessageMonitor);
  }
  
  public void sendMap(@NonNull Map<String, Object> paramMap)
  {
    for (;;)
    {
      try
      {
        if (this.currentBatch != null)
        {
          this.currentBatch.add(paramMap);
          return;
        }
        paramMap.put("_source", "device");
        notifyMonitors(paramMap, Map.class, false);
        if (this.socket != null) {
          this.socket.sendMapMessage(paramMap);
        } else {
          this.optimizely.verboseLog(true, "OptimizelySocket", "Socket not yet opened, message not sent", new Object[0]);
        }
      }
      finally {}
    }
  }
  
  public void sendObjectImmediate(@NonNull JsonObject paramJsonObject)
  {
    try
    {
      paramJsonObject.addProperty("_source", "device");
      paramJsonObject = paramJsonObject.toString();
      if ((!$assertionsDisabled) && (paramJsonObject == null)) {
        throw new AssertionError();
      }
    }
    finally {}
    notifyMonitors(paramJsonObject, String.class, false);
    if (this.socket != null) {
      this.socket.sendTextMessage(paramJsonObject);
    }
    for (;;)
    {
      return;
      this.optimizely.verboseLog(true, "OptimizelySocket", "Socket not yet opened, message not sent", new Object[0]);
    }
  }
  
  @NonNull
  URI socketURI()
    throws URISyntaxException
  {
    String str = String.format("socket/device?token=%s&device_id=%s", new Object[] { this.token, this.deviceId });
    return new URI(String.format("https://%s/%s", new Object[] { SOCKET_HOSTNAME, str }));
  }
  
  public void startSocket()
  {
    if (this.socket == null) {
      try
      {
        this.socket = new WebSocketConnection(this.optimizely);
        WebSocketOptions localWebSocketOptions = createReconnectableSocketOptions();
        this.socket.connect(socketURI(), this, localWebSocketOptions);
        return;
      }
      catch (WebSocketException localWebSocketException)
      {
        this.optimizely.verboseLog("OptimizelySocket", "Failed to connect to socket server with error %1$s", new Object[] { localWebSocketException.getLocalizedMessage() });
        this.socket = null;
        return;
      }
      catch (URISyntaxException localURISyntaxException)
      {
        this.optimizely.verboseLog("OptimizelySocket", "Invalid URI format: %1$s", new Object[] { localURISyntaxException.getLocalizedMessage() });
        this.socket = null;
        return;
      }
    }
    this.optimizely.verboseLog("OptimizelySocket", "Socket is already connected", new Object[0]);
  }
  
  public static abstract interface MessageMonitor
  {
    public abstract void onIncomingMessage(@Nullable Object paramObject, @Nullable Class paramClass);
    
    public abstract void onOutgoingMessage(@Nullable Object paramObject, @Nullable Class paramClass);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/OptimizelySocket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.optimizely.Network.Socket.Listeners;

import android.content.Context;
import android.support.annotation.NonNull;
import com.optimizely.Build;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.Network.OptimizelyScreenshot;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver;
import com.optimizely.Network.websocket.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;
import com.optimizely.Optimizely;
import com.optimizely.Optimizely.OptimizelyRunningMode;
import java.util.HashMap;
import java.util.Map;

public class DeviceInfoListener
  implements WebSocket.WebSocketConnectionObserver
{
  @NonNull
  private final Optimizely optimizely;
  
  public DeviceInfoListener(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  @NonNull
  private Map<String, Object> deviceMap()
  {
    boolean bool = true;
    HashMap localHashMap = new HashMap();
    localHashMap.put("id", OptimizelyUtils.deviceId());
    Context localContext = this.optimizely.getCurrentContext();
    localHashMap.put("bundleIdentifier", OptimizelyUtils.applicationName(localContext));
    localHashMap.put("projectId", this.optimizely.getProjectId());
    localHashMap.put("token", this.optimizely.getApiToken());
    localHashMap.put("sdkVersion", Build.sdkVersion());
    localHashMap.put("appVersion", OptimizelyUtils.applicationVersion(this.optimizely));
    localHashMap.put("name", OptimizelyUtils.deviceName());
    localHashMap.put("deviceModel", OptimizelyUtils.deviceModel());
    localHashMap.put("screenSize", OptimizelyUtils.getScaledScreenSizeMap(localContext));
    localHashMap.put("userInterfaceIdiom", Integer.valueOf(1));
    localHashMap.put("appStore", Boolean.valueOf(OptimizelyUtils.isAppStoreApp(localContext)));
    if (Optimizely.getRunningMode() == Optimizely.OptimizelyRunningMode.PREVIEW) {}
    for (;;)
    {
      localHashMap.put("isPreviewMode", Boolean.valueOf(bool));
      localHashMap.put("platform", "android");
      localHashMap.put("action", "registerDevice");
      return localHashMap;
      bool = false;
    }
  }
  
  public void onBinaryMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("DeviceInfoListener doesn't support onBinaryMessage");
  }
  
  public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification paramWebSocketCloseNotification, String paramString) {}
  
  public void onOpen()
  {
    this.optimizely.sendMap(deviceMap());
    this.optimizely.getScreenshot().sendOrientationToEditorIfNecessary(true);
  }
  
  public void onRawTextMessage(byte[] paramArrayOfByte)
    throws UnknownError
  {
    throw new UnknownError("DeviceInfoListener doesn't support onRawTextMessage");
  }
  
  public void onTextMessage(String paramString)
  {
    this.optimizely.sendMap(deviceMap());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/Socket/Listeners/DeviceInfoListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
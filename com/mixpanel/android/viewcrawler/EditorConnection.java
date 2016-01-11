package com.mixpanel.android.viewcrawler;

import android.util.Log;
import com.mixpanel.android.java_websocket.client.WebSocketClient;
import com.mixpanel.android.java_websocket.drafts.Draft_17;
import com.mixpanel.android.java_websocket.exceptions.NotSendableException;
import com.mixpanel.android.java_websocket.exceptions.WebsocketNotConnectedException;
import com.mixpanel.android.java_websocket.framing.Framedata.Opcode;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import com.mixpanel.android.mpmetrics.MPConfig;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.nio.ByteBuffer;
import org.json.JSONException;
import org.json.JSONObject;

class EditorConnection
{
  private static final int CONNECT_TIMEOUT = 5000;
  private static final ByteBuffer EMPTY_BYTE_BUFFER = ByteBuffer.allocate(0);
  private static final String LOGTAG = "MixpanelAPI.EditorCnctn";
  private final EditorClient mClient;
  private final Editor mService;
  private final URI mURI;
  
  public EditorConnection(URI paramURI, Editor paramEditor, Socket paramSocket)
    throws EditorConnection.EditorConnectionException
  {
    this.mService = paramEditor;
    this.mURI = paramURI;
    try
    {
      this.mClient = new EditorClient(paramURI, 5000, paramSocket);
      this.mClient.connectBlocking();
      return;
    }
    catch (InterruptedException paramURI)
    {
      throw new EditorConnectionException(paramURI);
    }
  }
  
  public BufferedOutputStream getBufferedOutputStream()
  {
    return new BufferedOutputStream(new WebSocketOutputStream(null));
  }
  
  public boolean isValid()
  {
    return (!this.mClient.isClosed()) && (!this.mClient.isClosing()) && (!this.mClient.isFlushAndClose());
  }
  
  public static abstract interface Editor
  {
    public abstract void bindEvents(JSONObject paramJSONObject);
    
    public abstract void cleanup();
    
    public abstract void clearEdits(JSONObject paramJSONObject);
    
    public abstract void performEdit(JSONObject paramJSONObject);
    
    public abstract void sendDeviceInfo();
    
    public abstract void sendSnapshot(JSONObject paramJSONObject);
    
    public abstract void setTweaks(JSONObject paramJSONObject);
  }
  
  private class EditorClient
    extends WebSocketClient
  {
    public EditorClient(URI paramURI, int paramInt, Socket paramSocket)
      throws InterruptedException
    {
      super(new Draft_17(), null, paramInt);
      setSocket(paramSocket);
    }
    
    public void onClose(int paramInt, String paramString, boolean paramBoolean)
    {
      if (MPConfig.DEBUG) {
        Log.v("MixpanelAPI.EditorCnctn", "WebSocket closed. Code: " + paramInt + ", reason: " + paramString + "\nURI: " + EditorConnection.this.mURI);
      }
      EditorConnection.this.mService.cleanup();
    }
    
    public void onError(Exception paramException)
    {
      if ((paramException != null) && (paramException.getMessage() != null))
      {
        Log.e("MixpanelAPI.EditorCnctn", "Websocket Error: " + paramException.getMessage());
        return;
      }
      Log.e("MixpanelAPI.EditorCnctn", "Unknown websocket error occurred");
    }
    
    public void onMessage(String paramString)
    {
      if (MPConfig.DEBUG) {
        Log.v("MixpanelAPI.EditorCnctn", "Received message from editor:\n" + paramString);
      }
      String str;
      try
      {
        JSONObject localJSONObject = new JSONObject(paramString);
        str = localJSONObject.getString("type");
        if (str.equals("device_info_request"))
        {
          EditorConnection.this.mService.sendDeviceInfo();
          return;
        }
        if (str.equals("snapshot_request"))
        {
          EditorConnection.this.mService.sendSnapshot(localJSONObject);
          return;
        }
      }
      catch (JSONException localJSONException)
      {
        Log.e("MixpanelAPI.EditorCnctn", "Bad JSON received:" + paramString, localJSONException);
        return;
      }
      if (str.equals("change_request"))
      {
        EditorConnection.this.mService.performEdit(localJSONException);
        return;
      }
      if (str.equals("event_binding_request"))
      {
        EditorConnection.this.mService.bindEvents(localJSONException);
        return;
      }
      if (str.equals("clear_request"))
      {
        EditorConnection.this.mService.clearEdits(localJSONException);
        return;
      }
      if (str.equals("tweak_request")) {
        EditorConnection.this.mService.setTweaks(localJSONException);
      }
    }
    
    public void onOpen(ServerHandshake paramServerHandshake)
    {
      if (MPConfig.DEBUG) {
        Log.v("MixpanelAPI.EditorCnctn", "Websocket connected");
      }
    }
  }
  
  public class EditorConnectionException
    extends IOException
  {
    private static final long serialVersionUID = -1884953175346045636L;
    
    public EditorConnectionException(Throwable paramThrowable)
    {
      super();
    }
  }
  
  private class WebSocketOutputStream
    extends OutputStream
  {
    private WebSocketOutputStream() {}
    
    public void close()
      throws EditorConnection.EditorConnectionException
    {
      try
      {
        EditorConnection.this.mClient.sendFragmentedFrame(Framedata.Opcode.TEXT, EditorConnection.EMPTY_BYTE_BUFFER, true);
        return;
      }
      catch (WebsocketNotConnectedException localWebsocketNotConnectedException)
      {
        throw new EditorConnection.EditorConnectionException(EditorConnection.this, localWebsocketNotConnectedException);
      }
      catch (NotSendableException localNotSendableException)
      {
        throw new EditorConnection.EditorConnectionException(EditorConnection.this, localNotSendableException);
      }
    }
    
    public void write(int paramInt)
      throws EditorConnection.EditorConnectionException
    {
      write(new byte[] { (byte)paramInt }, 0, 1);
    }
    
    public void write(byte[] paramArrayOfByte)
      throws EditorConnection.EditorConnectionException
    {
      write(paramArrayOfByte, 0, paramArrayOfByte.length);
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws EditorConnection.EditorConnectionException
    {
      paramArrayOfByte = ByteBuffer.wrap(paramArrayOfByte, paramInt1, paramInt2);
      try
      {
        EditorConnection.this.mClient.sendFragmentedFrame(Framedata.Opcode.TEXT, paramArrayOfByte, false);
        return;
      }
      catch (WebsocketNotConnectedException paramArrayOfByte)
      {
        throw new EditorConnection.EditorConnectionException(EditorConnection.this, paramArrayOfByte);
      }
      catch (NotSendableException paramArrayOfByte)
      {
        throw new EditorConnection.EditorConnectionException(EditorConnection.this, paramArrayOfByte);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/viewcrawler/EditorConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.mixpanel.android.java_websocket;

import com.mixpanel.android.java_websocket.drafts.Draft;
import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.framing.Framedata.Opcode;
import com.mixpanel.android.java_websocket.framing.FramedataImpl1;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.HandshakeImpl1Server;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import com.mixpanel.android.java_websocket.handshake.ServerHandshakeBuilder;
import java.net.InetSocketAddress;

public abstract class WebSocketAdapter
  implements WebSocketListener
{
  public String getFlashPolicy(WebSocket paramWebSocket)
    throws InvalidDataException
  {
    paramWebSocket = paramWebSocket.getLocalSocketAddress();
    if (paramWebSocket == null) {
      throw new InvalidHandshakeException("socket not bound");
    }
    StringBuffer localStringBuffer = new StringBuffer(90);
    localStringBuffer.append("<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"");
    localStringBuffer.append(paramWebSocket.getPort());
    localStringBuffer.append("\" /></cross-domain-policy>\000");
    return localStringBuffer.toString();
  }
  
  public void onWebsocketHandshakeReceivedAsClient(WebSocket paramWebSocket, ClientHandshake paramClientHandshake, ServerHandshake paramServerHandshake)
    throws InvalidDataException
  {}
  
  public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket paramWebSocket, Draft paramDraft, ClientHandshake paramClientHandshake)
    throws InvalidDataException
  {
    return new HandshakeImpl1Server();
  }
  
  public void onWebsocketHandshakeSentAsClient(WebSocket paramWebSocket, ClientHandshake paramClientHandshake)
    throws InvalidDataException
  {}
  
  public void onWebsocketMessageFragment(WebSocket paramWebSocket, Framedata paramFramedata) {}
  
  public void onWebsocketPing(WebSocket paramWebSocket, Framedata paramFramedata)
  {
    paramFramedata = new FramedataImpl1(paramFramedata);
    paramFramedata.setOptcode(Framedata.Opcode.PONG);
    paramWebSocket.sendFrame(paramFramedata);
  }
  
  public void onWebsocketPong(WebSocket paramWebSocket, Framedata paramFramedata) {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/WebSocketAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
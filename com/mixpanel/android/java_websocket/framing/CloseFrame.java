package com.mixpanel.android.java_websocket.framing;

import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidFrameException;

public abstract interface CloseFrame
  extends Framedata
{
  public static final int ABNORMAL_CLOSE = 1006;
  public static final int BUGGYCLOSE = -2;
  public static final int EXTENSION = 1010;
  public static final int FLASHPOLICY = -3;
  public static final int GOING_AWAY = 1001;
  public static final int NEVER_CONNECTED = -1;
  public static final int NOCODE = 1005;
  public static final int NORMAL = 1000;
  public static final int NO_UTF8 = 1007;
  public static final int POLICY_VALIDATION = 1008;
  public static final int PROTOCOL_ERROR = 1002;
  public static final int REFUSE = 1003;
  public static final int TLS_ERROR = 1015;
  public static final int TOOBIG = 1009;
  public static final int UNEXPECTED_CONDITION = 1011;
  
  public abstract int getCloseCode()
    throws InvalidFrameException;
  
  public abstract String getMessage()
    throws InvalidDataException;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/framing/CloseFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
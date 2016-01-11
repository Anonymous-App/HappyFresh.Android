package com.facebook.internal;

import android.content.Intent;
import java.util.UUID;

public class AppCall
{
  private static AppCall currentPendingCall;
  private UUID callId;
  private int requestCode;
  private Intent requestIntent;
  
  public AppCall(int paramInt)
  {
    this(paramInt, UUID.randomUUID());
  }
  
  public AppCall(int paramInt, UUID paramUUID)
  {
    this.callId = paramUUID;
    this.requestCode = paramInt;
  }
  
  /* Error */
  public static AppCall finishPendingCall(UUID paramUUID, int paramInt)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: invokestatic 37	com/facebook/internal/AppCall:getCurrentPendingCall	()Lcom/facebook/internal/AppCall;
    //   6: astore_3
    //   7: aload_3
    //   8: ifnull +24 -> 32
    //   11: aload_3
    //   12: invokevirtual 40	com/facebook/internal/AppCall:getCallId	()Ljava/util/UUID;
    //   15: aload_0
    //   16: invokevirtual 44	java/util/UUID:equals	(Ljava/lang/Object;)Z
    //   19: ifeq +13 -> 32
    //   22: aload_3
    //   23: invokevirtual 48	com/facebook/internal/AppCall:getRequestCode	()I
    //   26: istore_2
    //   27: iload_2
    //   28: iload_1
    //   29: if_icmpeq +10 -> 39
    //   32: aconst_null
    //   33: astore_0
    //   34: ldc 2
    //   36: monitorexit
    //   37: aload_0
    //   38: areturn
    //   39: aconst_null
    //   40: invokestatic 52	com/facebook/internal/AppCall:setCurrentPendingCall	(Lcom/facebook/internal/AppCall;)Z
    //   43: pop
    //   44: aload_3
    //   45: astore_0
    //   46: goto -12 -> 34
    //   49: astore_0
    //   50: ldc 2
    //   52: monitorexit
    //   53: aload_0
    //   54: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	55	0	paramUUID	UUID
    //   0	55	1	paramInt	int
    //   26	4	2	i	int
    //   6	39	3	localAppCall	AppCall
    // Exception table:
    //   from	to	target	type
    //   3	7	49	finally
    //   11	27	49	finally
    //   39	44	49	finally
  }
  
  public static AppCall getCurrentPendingCall()
  {
    return currentPendingCall;
  }
  
  /* Error */
  private static boolean setCurrentPendingCall(AppCall paramAppCall)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: invokestatic 37	com/facebook/internal/AppCall:getCurrentPendingCall	()Lcom/facebook/internal/AppCall;
    //   6: astore_2
    //   7: aload_0
    //   8: putstatic 54	com/facebook/internal/AppCall:currentPendingCall	Lcom/facebook/internal/AppCall;
    //   11: aload_2
    //   12: ifnull +10 -> 22
    //   15: iconst_1
    //   16: istore_1
    //   17: ldc 2
    //   19: monitorexit
    //   20: iload_1
    //   21: ireturn
    //   22: iconst_0
    //   23: istore_1
    //   24: goto -7 -> 17
    //   27: astore_0
    //   28: ldc 2
    //   30: monitorexit
    //   31: aload_0
    //   32: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	33	0	paramAppCall	AppCall
    //   16	8	1	bool	boolean
    //   6	6	2	localAppCall	AppCall
    // Exception table:
    //   from	to	target	type
    //   3	11	27	finally
  }
  
  public UUID getCallId()
  {
    return this.callId;
  }
  
  public int getRequestCode()
  {
    return this.requestCode;
  }
  
  public Intent getRequestIntent()
  {
    return this.requestIntent;
  }
  
  public boolean setPending()
  {
    return setCurrentPendingCall(this);
  }
  
  public void setRequestCode(int paramInt)
  {
    this.requestCode = paramInt;
  }
  
  public void setRequestIntent(Intent paramIntent)
  {
    this.requestIntent = paramIntent;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/AppCall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
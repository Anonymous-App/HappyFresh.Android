package com.parse;

import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class ParseUserCurrentCoder
  extends ParseObjectCurrentCoder
{
  private static final ParseUserCurrentCoder INSTANCE = new ParseUserCurrentCoder();
  private static final String KEY_AUTH_DATA = "auth_data";
  private static final String KEY_SESSION_TOKEN = "session_token";
  
  public static ParseUserCurrentCoder get()
  {
    return INSTANCE;
  }
  
  public <T extends ParseObject.State.Init<?>> T decode(T paramT, JSONObject paramJSONObject, ParseDecoder paramParseDecoder)
  {
    ParseUser.State.Builder localBuilder = (ParseUser.State.Builder)paramT;
    Object localObject = paramJSONObject.optString("session_token", null);
    if (localObject != null)
    {
      localBuilder.sessionToken((String)localObject);
      paramJSONObject.remove("session_token");
    }
    localObject = paramJSONObject.optJSONObject("auth_data");
    if (localObject != null) {
      try
      {
        Iterator localIterator = ((JSONObject)localObject).keys();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          if (!((JSONObject)localObject).isNull(str)) {
            localBuilder.putAuthData(str, (Map)ParseDecoder.get().decode(((JSONObject)localObject).getJSONObject(str)));
          }
        }
        paramJSONObject.remove("auth_data");
      }
      catch (JSONException paramT)
      {
        throw new RuntimeException(paramT);
      }
    }
    return super.decode(paramT, paramJSONObject, paramParseDecoder);
  }
  
  /* Error */
  public <T extends ParseObject.State> JSONObject encode(T paramT, ParseOperationSet paramParseOperationSet, ParseEncoder paramParseEncoder)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: aload_3
    //   4: invokespecial 98	com/parse/ParseObjectCurrentCoder:encode	(Lcom/parse/ParseObject$State;Lcom/parse/ParseOperationSet;Lcom/parse/ParseEncoder;)Lorg/json/JSONObject;
    //   7: astore_2
    //   8: aload_1
    //   9: checkcast 100	com/parse/ParseUser$State
    //   12: invokevirtual 103	com/parse/ParseUser$State:sessionToken	()Ljava/lang/String;
    //   15: astore 4
    //   17: aload 4
    //   19: ifnull +12 -> 31
    //   22: aload_2
    //   23: ldc 13
    //   25: aload 4
    //   27: invokevirtual 107	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   30: pop
    //   31: aload_1
    //   32: checkcast 100	com/parse/ParseUser$State
    //   35: invokevirtual 111	com/parse/ParseUser$State:authData	()Ljava/util/Map;
    //   38: astore_1
    //   39: aload_1
    //   40: invokeinterface 115 1 0
    //   45: ifle +15 -> 60
    //   48: aload_2
    //   49: ldc 10
    //   51: aload_3
    //   52: aload_1
    //   53: invokevirtual 119	com/parse/ParseEncoder:encode	(Ljava/lang/Object;)Ljava/lang/Object;
    //   56: invokevirtual 107	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   59: pop
    //   60: aload_2
    //   61: areturn
    //   62: astore_1
    //   63: new 87	java/lang/RuntimeException
    //   66: dup
    //   67: ldc 121
    //   69: invokespecial 124	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   72: athrow
    //   73: astore_1
    //   74: new 87	java/lang/RuntimeException
    //   77: dup
    //   78: ldc 126
    //   80: invokespecial 124	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   83: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	84	0	this	ParseUserCurrentCoder
    //   0	84	1	paramT	T
    //   0	84	2	paramParseOperationSet	ParseOperationSet
    //   0	84	3	paramParseEncoder	ParseEncoder
    //   15	11	4	str	String
    // Exception table:
    //   from	to	target	type
    //   22	31	62	org/json/JSONException
    //   48	60	73	org/json/JSONException
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseUserCurrentCoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.facebook.internal;

import com.facebook.FacebookRequestError.Category;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FacebookRequestErrorClassification
{
  public static final int EC_APP_TOO_MANY_CALLS = 4;
  public static final int EC_INVALID_SESSION = 102;
  public static final int EC_INVALID_TOKEN = 190;
  public static final int EC_RATE = 9;
  public static final int EC_SERVICE_UNAVAILABLE = 2;
  public static final int EC_TOO_MANY_USER_ACTION_CALLS = 341;
  public static final int EC_USER_TOO_MANY_CALLS = 17;
  public static final String KEY_LOGIN_RECOVERABLE = "login_recoverable";
  public static final String KEY_NAME = "name";
  public static final String KEY_OTHER = "other";
  public static final String KEY_RECOVERY_MESSAGE = "recovery_message";
  public static final String KEY_TRANSIENT = "transient";
  private static FacebookRequestErrorClassification defaultInstance;
  private final Map<Integer, Set<Integer>> loginRecoverableErrors;
  private final String loginRecoverableRecoveryMessage;
  private final Map<Integer, Set<Integer>> otherErrors;
  private final String otherRecoveryMessage;
  private final Map<Integer, Set<Integer>> transientErrors;
  private final String transientRecoveryMessage;
  
  FacebookRequestErrorClassification(Map<Integer, Set<Integer>> paramMap1, Map<Integer, Set<Integer>> paramMap2, Map<Integer, Set<Integer>> paramMap3, String paramString1, String paramString2, String paramString3)
  {
    this.otherErrors = paramMap1;
    this.transientErrors = paramMap2;
    this.loginRecoverableErrors = paramMap3;
    this.otherRecoveryMessage = paramString1;
    this.transientRecoveryMessage = paramString2;
    this.loginRecoverableRecoveryMessage = paramString3;
  }
  
  public static FacebookRequestErrorClassification createFromJSON(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null) {
      return null;
    }
    Object localObject7 = null;
    Object localObject5 = null;
    Object localObject4 = null;
    Object localObject3 = null;
    Object localObject2 = null;
    Object localObject1 = null;
    int i = 0;
    if (i < paramJSONArray.length())
    {
      JSONObject localJSONObject = paramJSONArray.optJSONObject(i);
      Object localObject12;
      Object localObject11;
      Object localObject10;
      Object localObject9;
      Object localObject8;
      Object localObject6;
      if (localJSONObject == null)
      {
        localObject12 = localObject1;
        localObject11 = localObject2;
        localObject10 = localObject3;
        localObject9 = localObject4;
        localObject8 = localObject5;
        localObject6 = localObject7;
      }
      for (;;)
      {
        i += 1;
        localObject7 = localObject6;
        localObject5 = localObject8;
        localObject4 = localObject9;
        localObject3 = localObject10;
        localObject2 = localObject11;
        localObject1 = localObject12;
        break;
        String str = localJSONObject.optString("name");
        localObject6 = localObject7;
        localObject8 = localObject5;
        localObject9 = localObject4;
        localObject10 = localObject3;
        localObject11 = localObject2;
        localObject12 = localObject1;
        if (str != null) {
          if (str.equalsIgnoreCase("other"))
          {
            localObject10 = localJSONObject.optString("recovery_message", null);
            localObject6 = parseJSONDefinition(localJSONObject);
            localObject8 = localObject5;
            localObject9 = localObject4;
            localObject11 = localObject2;
            localObject12 = localObject1;
          }
          else if (str.equalsIgnoreCase("transient"))
          {
            localObject11 = localJSONObject.optString("recovery_message", null);
            localObject8 = parseJSONDefinition(localJSONObject);
            localObject6 = localObject7;
            localObject9 = localObject4;
            localObject10 = localObject3;
            localObject12 = localObject1;
          }
          else
          {
            localObject6 = localObject7;
            localObject8 = localObject5;
            localObject9 = localObject4;
            localObject10 = localObject3;
            localObject11 = localObject2;
            localObject12 = localObject1;
            if (str.equalsIgnoreCase("login_recoverable"))
            {
              localObject12 = localJSONObject.optString("recovery_message", null);
              localObject9 = parseJSONDefinition(localJSONObject);
              localObject6 = localObject7;
              localObject8 = localObject5;
              localObject10 = localObject3;
              localObject11 = localObject2;
            }
          }
        }
      }
    }
    return new FacebookRequestErrorClassification((Map)localObject7, (Map)localObject5, (Map)localObject4, (String)localObject3, (String)localObject2, (String)localObject1);
  }
  
  public static FacebookRequestErrorClassification getDefaultErrorClassification()
  {
    try
    {
      if (defaultInstance == null) {
        defaultInstance = getDefaultErrorClassificationImpl();
      }
      FacebookRequestErrorClassification localFacebookRequestErrorClassification = defaultInstance;
      return localFacebookRequestErrorClassification;
    }
    finally {}
  }
  
  private static FacebookRequestErrorClassification getDefaultErrorClassificationImpl()
  {
    new FacebookRequestErrorClassification(null, new HashMap()new HashMap {}, new HashMap() {}, null, null, null);
  }
  
  private static Map<Integer, Set<Integer>> parseJSONDefinition(JSONObject paramJSONObject)
  {
    JSONArray localJSONArray1 = paramJSONObject.optJSONArray("items");
    if (localJSONArray1.length() == 0) {
      paramJSONObject = null;
    }
    HashMap localHashMap;
    int i;
    do
    {
      return paramJSONObject;
      localHashMap = new HashMap();
      i = 0;
      paramJSONObject = localHashMap;
    } while (i >= localJSONArray1.length());
    paramJSONObject = localJSONArray1.optJSONObject(i);
    if (paramJSONObject == null) {}
    for (;;)
    {
      i += 1;
      break;
      int k = paramJSONObject.optInt("code");
      if (k != 0)
      {
        HashSet localHashSet = null;
        JSONArray localJSONArray2 = paramJSONObject.optJSONArray("subcodes");
        paramJSONObject = localHashSet;
        if (localJSONArray2 != null)
        {
          paramJSONObject = localHashSet;
          if (localJSONArray2.length() > 0)
          {
            localHashSet = new HashSet();
            int j = 0;
            for (;;)
            {
              paramJSONObject = localHashSet;
              if (j >= localJSONArray2.length()) {
                break;
              }
              int m = localJSONArray2.optInt(j);
              if (m != 0) {
                localHashSet.add(Integer.valueOf(m));
              }
              j += 1;
            }
          }
        }
        localHashMap.put(Integer.valueOf(k), paramJSONObject);
      }
    }
  }
  
  public FacebookRequestError.Category classify(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramBoolean) {
      return FacebookRequestError.Category.TRANSIENT;
    }
    Set localSet;
    if ((this.otherErrors != null) && (this.otherErrors.containsKey(Integer.valueOf(paramInt1))))
    {
      localSet = (Set)this.otherErrors.get(Integer.valueOf(paramInt1));
      if ((localSet == null) || (localSet.contains(Integer.valueOf(paramInt2)))) {
        return FacebookRequestError.Category.OTHER;
      }
    }
    if ((this.loginRecoverableErrors != null) && (this.loginRecoverableErrors.containsKey(Integer.valueOf(paramInt1))))
    {
      localSet = (Set)this.loginRecoverableErrors.get(Integer.valueOf(paramInt1));
      if ((localSet == null) || (localSet.contains(Integer.valueOf(paramInt2)))) {
        return FacebookRequestError.Category.LOGIN_RECOVERABLE;
      }
    }
    if ((this.transientErrors != null) && (this.transientErrors.containsKey(Integer.valueOf(paramInt1))))
    {
      localSet = (Set)this.transientErrors.get(Integer.valueOf(paramInt1));
      if ((localSet == null) || (localSet.contains(Integer.valueOf(paramInt2)))) {
        return FacebookRequestError.Category.TRANSIENT;
      }
    }
    return FacebookRequestError.Category.OTHER;
  }
  
  public Map<Integer, Set<Integer>> getLoginRecoverableErrors()
  {
    return this.loginRecoverableErrors;
  }
  
  public Map<Integer, Set<Integer>> getOtherErrors()
  {
    return this.otherErrors;
  }
  
  public String getRecoveryMessage(FacebookRequestError.Category paramCategory)
  {
    switch (paramCategory)
    {
    default: 
      return null;
    case ???: 
      return this.otherRecoveryMessage;
    case ???: 
      return this.loginRecoverableRecoveryMessage;
    }
    return this.transientRecoveryMessage;
  }
  
  public Map<Integer, Set<Integer>> getTransientErrors()
  {
    return this.transientErrors;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/FacebookRequestErrorClassification.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
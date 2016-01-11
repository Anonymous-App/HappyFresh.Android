package com.facebook;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.Utility;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestUserManager
{
  private static final String LOG_TAG = "TestUserManager";
  private Map<String, JSONObject> appTestAccounts;
  private String testApplicationId;
  private String testApplicationSecret;
  
  static
  {
    if (!TestUserManager.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public TestUserManager(String paramString1, String paramString2)
  {
    if ((Utility.isNullOrEmpty(paramString2)) || (Utility.isNullOrEmpty(paramString1))) {
      throw new FacebookException("Must provide app ID and secret");
    }
    this.testApplicationSecret = paramString1;
    this.testApplicationId = paramString2;
  }
  
  private JSONObject createTestAccount(List<String> paramList, Mode paramMode, String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("installed", "true");
    localBundle.putString("permissions", getPermissionsString(paramList));
    localBundle.putString("access_token", getAppAccessToken());
    if (paramMode == Mode.SHARED) {
      localBundle.putString("name", String.format("Shared %s Testuser", new Object[] { getSharedTestAccountIdentifier(paramList, paramString) }));
    }
    paramString = new GraphRequest(null, String.format("%s/accounts/test-users", new Object[] { this.testApplicationId }), localBundle, HttpMethod.POST).executeAndWait();
    paramList = paramString.getError();
    paramString = paramString.getJSONObject();
    if (paramList != null) {
      paramList = null;
    }
    do
    {
      return paramList;
      assert (paramString != null);
      paramList = paramString;
    } while (paramMode != Mode.SHARED);
    try
    {
      paramString.put("name", localBundle.getString("name"));
      storeTestAccount(paramString);
      return paramString;
    }
    catch (JSONException paramList)
    {
      for (;;)
      {
        Log.e("TestUserManager", "Could not set name", paramList);
      }
    }
  }
  
  private JSONObject findOrCreateSharedTestAccount(List<String> paramList, Mode paramMode, String paramString)
  {
    JSONObject localJSONObject = findTestAccountMatchingIdentifier(getSharedTestAccountIdentifier(paramList, paramString));
    if (localJSONObject != null) {
      return localJSONObject;
    }
    return createTestAccount(paramList, paramMode, paramString);
  }
  
  /* Error */
  private JSONObject findTestAccountMatchingIdentifier(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 157	com/facebook/TestUserManager:appTestAccounts	Ljava/util/Map;
    //   6: invokeinterface 163 1 0
    //   11: invokeinterface 169 1 0
    //   16: astore 4
    //   18: aload 4
    //   20: invokeinterface 174 1 0
    //   25: ifeq +35 -> 60
    //   28: aload 4
    //   30: invokeinterface 178 1 0
    //   35: checkcast 130	org/json/JSONObject
    //   38: astore_3
    //   39: aload_3
    //   40: ldc 82
    //   42: invokevirtual 181	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   45: aload_1
    //   46: invokevirtual 185	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   49: istore_2
    //   50: iload_2
    //   51: ifeq -33 -> 18
    //   54: aload_3
    //   55: astore_1
    //   56: aload_0
    //   57: monitorexit
    //   58: aload_1
    //   59: areturn
    //   60: aconst_null
    //   61: astore_1
    //   62: goto -6 -> 56
    //   65: astore_1
    //   66: aload_0
    //   67: monitorexit
    //   68: aload_1
    //   69: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	70	0	this	TestUserManager
    //   0	70	1	paramString	String
    //   49	2	2	bool	boolean
    //   38	17	3	localJSONObject	JSONObject
    //   16	13	4	localIterator	java.util.Iterator
    // Exception table:
    //   from	to	target	type
    //   2	18	65	finally
    //   18	50	65	finally
  }
  
  private AccessToken getAccessTokenForUser(List<String> paramList, Mode paramMode, String paramString)
  {
    retrieveTestAccountsForAppIfNeeded();
    Object localObject = paramList;
    if (Utility.isNullOrEmpty(paramList)) {
      localObject = Arrays.asList(new String[] { "email", "publish_actions" });
    }
    if (paramMode == Mode.PRIVATE) {}
    for (paramList = createTestAccount((List)localObject, paramMode, paramString);; paramList = findOrCreateSharedTestAccount((List)localObject, paramMode, paramString)) {
      return new AccessToken(paramList.optString("access_token"), this.testApplicationId, paramList.optString("id"), (Collection)localObject, null, AccessTokenSource.TEST_USER, null, null);
    }
  }
  
  private String getPermissionsString(List<String> paramList)
  {
    return TextUtils.join(",", paramList);
  }
  
  private String getSharedTestAccountIdentifier(List<String> paramList, String paramString)
  {
    long l2 = getPermissionsString(paramList).hashCode();
    if (paramString != null) {}
    for (long l1 = paramString.hashCode() & 0xFFFFFFFF;; l1 = 0L) {
      return validNameStringFromInteger(l2 & 0xFFFFFFFF ^ l1);
    }
  }
  
  private void populateTestAccounts(JSONArray paramJSONArray, JSONObject paramJSONObject)
  {
    int i = 0;
    try
    {
      for (;;)
      {
        if (i < paramJSONArray.length())
        {
          JSONObject localJSONObject1 = paramJSONArray.optJSONObject(i);
          JSONObject localJSONObject2 = paramJSONObject.optJSONObject(localJSONObject1.optString("id"));
          try
          {
            localJSONObject1.put("name", localJSONObject2.optString("name"));
            storeTestAccount(localJSONObject1);
            i += 1;
          }
          catch (JSONException localJSONException)
          {
            for (;;)
            {
              Log.e("TestUserManager", "Could not set name", localJSONException);
            }
          }
        }
      }
    }
    finally {}
  }
  
  private void retrieveTestAccountsForAppIfNeeded()
  {
    for (;;)
    {
      try
      {
        Object localObject1 = this.appTestAccounts;
        if (localObject1 != null) {
          return;
        }
        this.appTestAccounts = new HashMap();
        GraphRequest.setDefaultBatchApplicationId(this.testApplicationId);
        localObject1 = new Bundle();
        ((Bundle)localObject1).putString("access_token", getAppAccessToken());
        localObject1 = new GraphRequest(null, "app/accounts/test-users", (Bundle)localObject1, null);
        ((GraphRequest)localObject1).setBatchEntryName("testUsers");
        ((GraphRequest)localObject1).setBatchEntryOmitResultOnSuccess(false);
        Object localObject3 = new Bundle();
        ((Bundle)localObject3).putString("access_token", getAppAccessToken());
        ((Bundle)localObject3).putString("ids", "{result=testUsers:$.data.*.id}");
        ((Bundle)localObject3).putString("fields", "name");
        localObject3 = new GraphRequest(null, "", (Bundle)localObject3, null);
        ((GraphRequest)localObject3).setBatchEntryDependsOn("testUsers");
        localObject1 = GraphRequest.executeBatchAndWait(new GraphRequest[] { localObject1, localObject3 });
        if ((localObject1 == null) || (((List)localObject1).size() != 2)) {
          throw new FacebookException("Unexpected number of results from TestUsers batch query");
        }
      }
      finally {}
      populateTestAccounts(((GraphResponse)((List)localObject2).get(0)).getJSONObject().optJSONArray("data"), ((GraphResponse)((List)localObject2).get(1)).getJSONObject());
    }
  }
  
  private void storeTestAccount(JSONObject paramJSONObject)
  {
    try
    {
      this.appTestAccounts.put(paramJSONObject.optString("id"), paramJSONObject);
      return;
    }
    finally
    {
      paramJSONObject = finally;
      throw paramJSONObject;
    }
  }
  
  private String validNameStringFromInteger(long paramLong)
  {
    Object localObject = Long.toString(paramLong);
    StringBuilder localStringBuilder = new StringBuilder("Perm");
    int k = 0;
    localObject = ((String)localObject).toCharArray();
    int n = localObject.length;
    int j = 0;
    while (j < n)
    {
      int m = localObject[j];
      int i = m;
      if (m == k) {
        i = (char)(m + 10);
      }
      localStringBuilder.append((char)(i + 97 - 48));
      j += 1;
      k = i;
    }
    return localStringBuilder.toString();
  }
  
  public AccessToken getAccessTokenForPrivateUser(List<String> paramList)
  {
    return getAccessTokenForUser(paramList, Mode.PRIVATE, null);
  }
  
  public AccessToken getAccessTokenForSharedUser(List<String> paramList)
  {
    return getAccessTokenForSharedUser(paramList, null);
  }
  
  public AccessToken getAccessTokenForSharedUser(List<String> paramList, String paramString)
  {
    return getAccessTokenForUser(paramList, Mode.SHARED, paramString);
  }
  
  final String getAppAccessToken()
  {
    return this.testApplicationId + "|" + this.testApplicationSecret;
  }
  
  public String getTestApplicationId()
  {
    try
    {
      String str = this.testApplicationId;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getTestApplicationSecret()
  {
    try
    {
      String str = this.testApplicationSecret;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private static enum Mode
  {
    PRIVATE,  SHARED;
    
    private Mode() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/TestUserManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
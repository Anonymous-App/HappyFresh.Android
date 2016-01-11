package com.facebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.GraphMeRequestWithCacheCallback;
import com.facebook.internal.Validate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class AccessToken
  implements Parcelable
{
  public static final String ACCESS_TOKEN_KEY = "access_token";
  private static final String APPLICATION_ID_KEY = "application_id";
  public static final Parcelable.Creator<AccessToken> CREATOR = new Parcelable.Creator()
  {
    public AccessToken createFromParcel(Parcel paramAnonymousParcel)
    {
      return new AccessToken(paramAnonymousParcel);
    }
    
    public AccessToken[] newArray(int paramAnonymousInt)
    {
      return new AccessToken[paramAnonymousInt];
    }
  };
  private static final int CURRENT_JSON_FORMAT = 1;
  private static final String DECLINED_PERMISSIONS_KEY = "declined_permissions";
  private static final AccessTokenSource DEFAULT_ACCESS_TOKEN_SOURCE;
  private static final Date DEFAULT_EXPIRATION_TIME;
  private static final Date DEFAULT_LAST_REFRESH_TIME;
  private static final String EXPIRES_AT_KEY = "expires_at";
  public static final String EXPIRES_IN_KEY = "expires_in";
  private static final String LAST_REFRESH_KEY = "last_refresh";
  private static final Date MAX_DATE = new Date(Long.MAX_VALUE);
  private static final String PERMISSIONS_KEY = "permissions";
  private static final String SOURCE_KEY = "source";
  private static final String TOKEN_KEY = "token";
  public static final String USER_ID_KEY = "user_id";
  private static final String VERSION_KEY = "version";
  private final String applicationId;
  private final Set<String> declinedPermissions;
  private final Date expires;
  private final Date lastRefresh;
  private final Set<String> permissions;
  private final AccessTokenSource source;
  private final String token;
  private final String userId;
  
  static
  {
    DEFAULT_EXPIRATION_TIME = MAX_DATE;
    DEFAULT_LAST_REFRESH_TIME = new Date();
    DEFAULT_ACCESS_TOKEN_SOURCE = AccessTokenSource.FACEBOOK_APPLICATION_WEB;
  }
  
  AccessToken(Parcel paramParcel)
  {
    this.expires = new Date(paramParcel.readLong());
    ArrayList localArrayList = new ArrayList();
    paramParcel.readStringList(localArrayList);
    this.permissions = Collections.unmodifiableSet(new HashSet(localArrayList));
    localArrayList.clear();
    paramParcel.readStringList(localArrayList);
    this.declinedPermissions = Collections.unmodifiableSet(new HashSet(localArrayList));
    this.token = paramParcel.readString();
    this.source = AccessTokenSource.valueOf(paramParcel.readString());
    this.lastRefresh = new Date(paramParcel.readLong());
    this.applicationId = paramParcel.readString();
    this.userId = paramParcel.readString();
  }
  
  public AccessToken(String paramString1, String paramString2, String paramString3, @Nullable Collection<String> paramCollection1, @Nullable Collection<String> paramCollection2, @Nullable AccessTokenSource paramAccessTokenSource, @Nullable Date paramDate1, @Nullable Date paramDate2)
  {
    Validate.notNullOrEmpty(paramString1, "accessToken");
    Validate.notNullOrEmpty(paramString2, "applicationId");
    Validate.notNullOrEmpty(paramString3, "userId");
    if (paramDate1 != null)
    {
      this.expires = paramDate1;
      if (paramCollection1 == null) {
        break label129;
      }
      paramCollection1 = new HashSet(paramCollection1);
      label49:
      this.permissions = Collections.unmodifiableSet(paramCollection1);
      if (paramCollection2 == null) {
        break label141;
      }
      paramCollection1 = new HashSet(paramCollection2);
      label74:
      this.declinedPermissions = Collections.unmodifiableSet(paramCollection1);
      this.token = paramString1;
      if (paramAccessTokenSource == null) {
        break label153;
      }
      label93:
      this.source = paramAccessTokenSource;
      if (paramDate2 == null) {
        break label161;
      }
    }
    for (;;)
    {
      this.lastRefresh = paramDate2;
      this.applicationId = paramString2;
      this.userId = paramString3;
      return;
      paramDate1 = DEFAULT_EXPIRATION_TIME;
      break;
      label129:
      paramCollection1 = new HashSet();
      break label49;
      label141:
      paramCollection1 = new HashSet();
      break label74;
      label153:
      paramAccessTokenSource = DEFAULT_ACCESS_TOKEN_SOURCE;
      break label93;
      label161:
      paramDate2 = DEFAULT_LAST_REFRESH_TIME;
    }
  }
  
  private void appendPermissions(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append(" permissions:");
    if (this.permissions == null)
    {
      paramStringBuilder.append("null");
      return;
    }
    paramStringBuilder.append("[");
    paramStringBuilder.append(TextUtils.join(", ", this.permissions));
    paramStringBuilder.append("]");
  }
  
  private static AccessToken createFromBundle(List<String> paramList, Bundle paramBundle, AccessTokenSource paramAccessTokenSource, Date paramDate, String paramString)
  {
    String str = paramBundle.getString("access_token");
    paramDate = Utility.getBundleLongAsDate(paramBundle, "expires_in", paramDate);
    paramBundle = paramBundle.getString("user_id");
    if ((Utility.isNullOrEmpty(str)) || (paramDate == null)) {
      return null;
    }
    return new AccessToken(str, paramString, paramBundle, paramList, null, paramAccessTokenSource, paramDate, new Date());
  }
  
  static AccessToken createFromJSONObject(JSONObject paramJSONObject)
    throws JSONException
  {
    if (paramJSONObject.getInt("version") > 1) {
      throw new FacebookException("Unknown AccessToken serialization format.");
    }
    String str = paramJSONObject.getString("token");
    Date localDate1 = new Date(paramJSONObject.getLong("expires_at"));
    JSONArray localJSONArray1 = paramJSONObject.getJSONArray("permissions");
    JSONArray localJSONArray2 = paramJSONObject.getJSONArray("declined_permissions");
    Date localDate2 = new Date(paramJSONObject.getLong("last_refresh"));
    AccessTokenSource localAccessTokenSource = AccessTokenSource.valueOf(paramJSONObject.getString("source"));
    return new AccessToken(str, paramJSONObject.getString("application_id"), paramJSONObject.getString("user_id"), Utility.jsonArrayToStringList(localJSONArray1), Utility.jsonArrayToStringList(localJSONArray2), localAccessTokenSource, localDate1, localDate2);
  }
  
  static AccessToken createFromLegacyCache(Bundle paramBundle)
  {
    List localList1 = getPermissionsFromBundle(paramBundle, "com.facebook.TokenCachingStrategy.Permissions");
    List localList2 = getPermissionsFromBundle(paramBundle, "com.facebook.TokenCachingStrategy.DeclinedPermissions");
    String str2 = LegacyTokenHelper.getApplicationId(paramBundle);
    String str1 = str2;
    if (Utility.isNullOrEmpty(str2)) {
      str1 = FacebookSdk.getApplicationId();
    }
    str2 = LegacyTokenHelper.getToken(paramBundle);
    Object localObject = Utility.awaitGetGraphMeRequestWithCache(str2);
    try
    {
      localObject = ((JSONObject)localObject).getString("id");
      return new AccessToken(str2, str1, (String)localObject, localList1, localList2, LegacyTokenHelper.getSource(paramBundle), LegacyTokenHelper.getDate(paramBundle, "com.facebook.TokenCachingStrategy.ExpirationDate"), LegacyTokenHelper.getDate(paramBundle, "com.facebook.TokenCachingStrategy.LastRefreshDate"));
    }
    catch (JSONException paramBundle) {}
    return null;
  }
  
  public static void createFromNativeLinkingIntent(Intent paramIntent, final String paramString, final AccessTokenCreationCallback paramAccessTokenCreationCallback)
  {
    Validate.notNull(paramIntent, "intent");
    if (paramIntent.getExtras() == null)
    {
      paramAccessTokenCreationCallback.onError(new FacebookException("No extras found on intent"));
      return;
    }
    paramIntent = new Bundle(paramIntent.getExtras());
    String str1 = paramIntent.getString("access_token");
    if ((str1 == null) || (str1.isEmpty()))
    {
      paramAccessTokenCreationCallback.onError(new FacebookException("No access token found on intent"));
      return;
    }
    String str2 = paramIntent.getString("user_id");
    if ((str2 == null) || (str2.isEmpty()))
    {
      Utility.getGraphMeRequestWithCacheAsync(str1, new Utility.GraphMeRequestWithCacheCallback()
      {
        public void onFailure(FacebookException paramAnonymousFacebookException)
        {
          paramAccessTokenCreationCallback.onError(paramAnonymousFacebookException);
        }
        
        public void onSuccess(JSONObject paramAnonymousJSONObject)
        {
          try
          {
            paramAnonymousJSONObject = paramAnonymousJSONObject.getString("id");
            this.val$extras.putString("user_id", paramAnonymousJSONObject);
            paramAccessTokenCreationCallback.onSuccess(AccessToken.createFromBundle(null, this.val$extras, AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date(), paramString));
            return;
          }
          catch (JSONException paramAnonymousJSONObject)
          {
            paramAccessTokenCreationCallback.onError(new FacebookException("Unable to generate access token due to missing user id"));
          }
        }
      });
      return;
    }
    paramAccessTokenCreationCallback.onSuccess(createFromBundle(null, paramIntent, AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date(), paramString));
  }
  
  @SuppressLint({"FieldGetter"})
  static AccessToken createFromRefresh(AccessToken paramAccessToken, Bundle paramBundle)
  {
    if ((paramAccessToken.source != AccessTokenSource.FACEBOOK_APPLICATION_WEB) && (paramAccessToken.source != AccessTokenSource.FACEBOOK_APPLICATION_NATIVE) && (paramAccessToken.source != AccessTokenSource.FACEBOOK_APPLICATION_SERVICE)) {
      throw new FacebookException("Invalid token source: " + paramAccessToken.source);
    }
    Date localDate = Utility.getBundleLongAsDate(paramBundle, "expires_in", new Date(0L));
    paramBundle = paramBundle.getString("access_token");
    if (Utility.isNullOrEmpty(paramBundle)) {
      return null;
    }
    return new AccessToken(paramBundle, paramAccessToken.applicationId, paramAccessToken.getUserId(), paramAccessToken.getPermissions(), paramAccessToken.getDeclinedPermissions(), paramAccessToken.source, localDate, new Date());
  }
  
  public static AccessToken getCurrentAccessToken()
  {
    return AccessTokenManager.getInstance().getCurrentAccessToken();
  }
  
  static List<String> getPermissionsFromBundle(Bundle paramBundle, String paramString)
  {
    paramBundle = paramBundle.getStringArrayList(paramString);
    if (paramBundle == null) {
      return Collections.emptyList();
    }
    return Collections.unmodifiableList(new ArrayList(paramBundle));
  }
  
  public static void refreshCurrentAccessTokenAsync()
  {
    AccessTokenManager.getInstance().refreshCurrentAccessToken();
  }
  
  public static void setCurrentAccessToken(AccessToken paramAccessToken)
  {
    AccessTokenManager.getInstance().setCurrentAccessToken(paramAccessToken);
  }
  
  private String tokenToString()
  {
    if (this.token == null) {
      return "null";
    }
    if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)) {
      return this.token;
    }
    return "ACCESS_TOKEN_REMOVED";
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    for (;;)
    {
      return true;
      if (!(paramObject instanceof AccessToken)) {
        return false;
      }
      paramObject = (AccessToken)paramObject;
      if ((this.expires.equals(((AccessToken)paramObject).expires)) && (this.permissions.equals(((AccessToken)paramObject).permissions)) && (this.declinedPermissions.equals(((AccessToken)paramObject).declinedPermissions)) && (this.token.equals(((AccessToken)paramObject).token)) && (this.source == ((AccessToken)paramObject).source) && (this.lastRefresh.equals(((AccessToken)paramObject).lastRefresh)))
      {
        if (this.applicationId != null) {
          break label136;
        }
        if (((AccessToken)paramObject).applicationId != null) {}
      }
      while (!this.userId.equals(((AccessToken)paramObject).userId)) {
        label136:
        do
        {
          return false;
        } while (!this.applicationId.equals(((AccessToken)paramObject).applicationId));
      }
    }
  }
  
  public String getApplicationId()
  {
    return this.applicationId;
  }
  
  public Set<String> getDeclinedPermissions()
  {
    return this.declinedPermissions;
  }
  
  public Date getExpires()
  {
    return this.expires;
  }
  
  public Date getLastRefresh()
  {
    return this.lastRefresh;
  }
  
  public Set<String> getPermissions()
  {
    return this.permissions;
  }
  
  public AccessTokenSource getSource()
  {
    return this.source;
  }
  
  public String getToken()
  {
    return this.token;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public int hashCode()
  {
    int j = this.expires.hashCode();
    int k = this.permissions.hashCode();
    int m = this.declinedPermissions.hashCode();
    int n = this.token.hashCode();
    int i1 = this.source.hashCode();
    int i2 = this.lastRefresh.hashCode();
    if (this.applicationId == null) {}
    for (int i = 0;; i = this.applicationId.hashCode()) {
      return (((((((j + 527) * 31 + k) * 31 + m) * 31 + n) * 31 + i1) * 31 + i2) * 31 + i) * 31 + this.userId.hashCode();
    }
  }
  
  public boolean isExpired()
  {
    return new Date().after(this.expires);
  }
  
  JSONObject toJSONObject()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("version", 1);
    localJSONObject.put("token", this.token);
    localJSONObject.put("expires_at", this.expires.getTime());
    localJSONObject.put("permissions", new JSONArray(this.permissions));
    localJSONObject.put("declined_permissions", new JSONArray(this.declinedPermissions));
    localJSONObject.put("last_refresh", this.lastRefresh.getTime());
    localJSONObject.put("source", this.source.name());
    localJSONObject.put("application_id", this.applicationId);
    localJSONObject.put("user_id", this.userId);
    return localJSONObject;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("{AccessToken");
    localStringBuilder.append(" token:").append(tokenToString());
    appendPermissions(localStringBuilder);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.expires.getTime());
    paramParcel.writeStringList(new ArrayList(this.permissions));
    paramParcel.writeStringList(new ArrayList(this.declinedPermissions));
    paramParcel.writeString(this.token);
    paramParcel.writeString(this.source.name());
    paramParcel.writeLong(this.lastRefresh.getTime());
    paramParcel.writeString(this.applicationId);
    paramParcel.writeString(this.userId);
  }
  
  public static abstract interface AccessTokenCreationCallback
  {
    public abstract void onError(FacebookException paramFacebookException);
    
    public abstract void onSuccess(AccessToken paramAccessToken);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/AccessToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
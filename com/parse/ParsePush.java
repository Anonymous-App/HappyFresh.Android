package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class ParsePush
{
  static String KEY_DATA_MESSAGE = "alert";
  private static final String TAG = "com.parse.ParsePush";
  final ParsePush.State.Builder builder;
  
  public ParsePush()
  {
    this(new ParsePush.State.Builder());
  }
  
  private ParsePush(ParsePush.State.Builder paramBuilder)
  {
    this.builder = paramBuilder;
  }
  
  private static void checkArgument(boolean paramBoolean, Object paramObject)
  {
    if (!paramBoolean) {
      throw new IllegalArgumentException(String.valueOf(paramObject));
    }
  }
  
  static ParsePushChannelsController getPushChannelsController()
  {
    return ParseCorePlugins.getInstance().getPushChannelsController();
  }
  
  static ParsePushController getPushController()
  {
    return ParseCorePlugins.getInstance().getPushController();
  }
  
  public static Task<Void> sendDataInBackground(JSONObject paramJSONObject, ParseQuery<ParseInstallation> paramParseQuery)
  {
    ParsePush localParsePush = new ParsePush();
    localParsePush.setQuery(paramParseQuery);
    localParsePush.setData(paramJSONObject);
    return localParsePush.sendInBackground();
  }
  
  public static void sendDataInBackground(JSONObject paramJSONObject, ParseQuery<ParseInstallation> paramParseQuery, SendCallback paramSendCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(sendDataInBackground(paramJSONObject, paramParseQuery), paramSendCallback);
  }
  
  public static Task<Void> sendMessageInBackground(String paramString, ParseQuery<ParseInstallation> paramParseQuery)
  {
    ParsePush localParsePush = new ParsePush();
    localParsePush.setQuery(paramParseQuery);
    localParsePush.setMessage(paramString);
    return localParsePush.sendInBackground();
  }
  
  public static void sendMessageInBackground(String paramString, ParseQuery<ParseInstallation> paramParseQuery, SendCallback paramSendCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(sendMessageInBackground(paramString, paramParseQuery), paramSendCallback);
  }
  
  public static Task<Void> subscribeInBackground(String paramString)
  {
    return getPushChannelsController().subscribeInBackground(paramString);
  }
  
  public static void subscribeInBackground(String paramString, SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(subscribeInBackground(paramString), paramSaveCallback);
  }
  
  public static Task<Void> unsubscribeInBackground(String paramString)
  {
    return getPushChannelsController().unsubscribeInBackground(paramString);
  }
  
  public static void unsubscribeInBackground(String paramString, SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(unsubscribeInBackground(paramString), paramSaveCallback);
  }
  
  public void clearExpiration()
  {
    this.builder.expirationTime(null);
    this.builder.expirationTimeInterval(null);
  }
  
  public void send()
    throws ParseException
  {
    ParseTaskUtils.wait(sendInBackground());
  }
  
  public Task<Void> sendInBackground()
  {
    final State localState = this.builder.build();
    ParseUser.getCurrentSessionTokenAsync().onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        return ParsePush.getPushController().sendInBackground(localState, paramAnonymousTask);
      }
    });
  }
  
  public void sendInBackground(SendCallback paramSendCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(sendInBackground(), paramSendCallback);
  }
  
  public void setChannel(String paramString)
  {
    this.builder.channelSet(Collections.singletonList(paramString));
  }
  
  public void setChannels(Collection<String> paramCollection)
  {
    this.builder.channelSet(paramCollection);
  }
  
  public void setData(JSONObject paramJSONObject)
  {
    this.builder.data(paramJSONObject);
  }
  
  public void setExpirationTime(long paramLong)
  {
    this.builder.expirationTime(Long.valueOf(paramLong));
  }
  
  public void setExpirationTimeInterval(long paramLong)
  {
    this.builder.expirationTimeInterval(Long.valueOf(paramLong));
  }
  
  public void setMessage(String paramString)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put(KEY_DATA_MESSAGE, paramString);
      setData(localJSONObject);
      return;
    }
    catch (JSONException paramString)
    {
      for (;;)
      {
        PLog.e("com.parse.ParsePush", "JSONException in setMessage", paramString);
      }
    }
  }
  
  @Deprecated
  public void setPushToAndroid(boolean paramBoolean)
  {
    this.builder.pushToAndroid(Boolean.valueOf(paramBoolean));
  }
  
  @Deprecated
  public void setPushToIOS(boolean paramBoolean)
  {
    this.builder.pushToIOS(Boolean.valueOf(paramBoolean));
  }
  
  public void setQuery(ParseQuery<ParseInstallation> paramParseQuery)
  {
    this.builder.query(paramParseQuery);
  }
  
  static class State
  {
    private final Set<String> channelSet;
    private final JSONObject data;
    private final Long expirationTime;
    private final Long expirationTimeInterval;
    private final Boolean pushToAndroid;
    private final Boolean pushToIOS;
    private final ParseQuery.State<ParseInstallation> queryState;
    
    private State(Builder paramBuilder)
    {
      Object localObject1;
      if (paramBuilder.channelSet == null) {
        localObject1 = null;
      }
      for (;;)
      {
        this.channelSet = ((Set)localObject1);
        if (paramBuilder.query == null)
        {
          localObject1 = localObject2;
          this.queryState = ((ParseQuery.State)localObject1);
          this.expirationTime = paramBuilder.expirationTime;
          this.expirationTimeInterval = paramBuilder.expirationTimeInterval;
          this.pushToIOS = paramBuilder.pushToIOS;
          this.pushToAndroid = paramBuilder.pushToAndroid;
          localObject1 = null;
        }
        try
        {
          paramBuilder = new JSONObject(paramBuilder.data.toString());
          this.data = paramBuilder;
          return;
          localObject1 = Collections.unmodifiableSet(new HashSet(paramBuilder.channelSet));
          continue;
          localObject1 = paramBuilder.query.getBuilder().build();
        }
        catch (JSONException paramBuilder)
        {
          for (;;)
          {
            paramBuilder = (Builder)localObject1;
          }
        }
      }
    }
    
    public Set<String> channelSet()
    {
      return this.channelSet;
    }
    
    public JSONObject data()
    {
      try
      {
        JSONObject localJSONObject = new JSONObject(this.data.toString());
        return localJSONObject;
      }
      catch (JSONException localJSONException) {}
      return null;
    }
    
    public Long expirationTime()
    {
      return this.expirationTime;
    }
    
    public Long expirationTimeInterval()
    {
      return this.expirationTimeInterval;
    }
    
    public Boolean pushToAndroid()
    {
      return this.pushToAndroid;
    }
    
    public Boolean pushToIOS()
    {
      return this.pushToIOS;
    }
    
    public ParseQuery.State<ParseInstallation> queryState()
    {
      return this.queryState;
    }
    
    static class Builder
    {
      private Set<String> channelSet;
      private JSONObject data;
      private Long expirationTime;
      private Long expirationTimeInterval;
      private Boolean pushToAndroid;
      private Boolean pushToIOS;
      private ParseQuery<ParseInstallation> query;
      
      public ParsePush.State build()
      {
        if (this.data == null) {
          throw new IllegalArgumentException("Cannot send a push without calling either setMessage or setData");
        }
        return new ParsePush.State(this, null);
      }
      
      public Builder channelSet(Collection<String> paramCollection)
      {
        if (paramCollection != null)
        {
          bool = true;
          ParsePush.checkArgument(bool, "channels collection cannot be null");
          Iterator localIterator = paramCollection.iterator();
          label19:
          if (!localIterator.hasNext()) {
            break label61;
          }
          if ((String)localIterator.next() == null) {
            break label56;
          }
        }
        label56:
        for (boolean bool = true;; bool = false)
        {
          ParsePush.checkArgument(bool, "channel cannot be null");
          break label19;
          bool = false;
          break;
        }
        label61:
        this.channelSet = new HashSet(paramCollection);
        this.query = null;
        return this;
      }
      
      public Builder data(JSONObject paramJSONObject)
      {
        this.data = paramJSONObject;
        return this;
      }
      
      public Builder expirationTime(Long paramLong)
      {
        this.expirationTime = paramLong;
        this.expirationTimeInterval = null;
        return this;
      }
      
      public Builder expirationTimeInterval(Long paramLong)
      {
        this.expirationTimeInterval = paramLong;
        this.expirationTime = null;
        return this;
      }
      
      public Builder pushToAndroid(Boolean paramBoolean)
      {
        if (this.query == null) {}
        for (boolean bool = true;; bool = false)
        {
          ParsePush.checkArgument(bool, "Cannot set push targets (i.e. setPushToAndroid or setPushToIOS) when pushing to a query");
          this.pushToAndroid = paramBoolean;
          return this;
        }
      }
      
      public Builder pushToIOS(Boolean paramBoolean)
      {
        if (this.query == null) {}
        for (boolean bool = true;; bool = false)
        {
          ParsePush.checkArgument(bool, "Cannot set push targets (i.e. setPushToAndroid or setPushToIOS) when pushing to a query");
          this.pushToIOS = paramBoolean;
          return this;
        }
      }
      
      public Builder query(ParseQuery<ParseInstallation> paramParseQuery)
      {
        boolean bool2 = true;
        if (paramParseQuery != null)
        {
          bool1 = true;
          ParsePush.checkArgument(bool1, "Cannot target a null query");
          if ((this.pushToIOS != null) || (this.pushToAndroid != null)) {
            break label70;
          }
        }
        label70:
        for (boolean bool1 = bool2;; bool1 = false)
        {
          ParsePush.checkArgument(bool1, "Cannot set push targets (i.e. setPushToAndroid or setPushToIOS) when pushing to a query");
          ParsePush.checkArgument(paramParseQuery.getClassName().equals(ParseObject.getClassName(ParseInstallation.class)), "Can only push to a query for Installations");
          this.channelSet = null;
          this.query = paramParseQuery;
          return this;
          bool1 = false;
          break;
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParsePush.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
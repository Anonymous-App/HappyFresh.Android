package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class ParseConfig
{
  static final TaskQueue taskQueue = new TaskQueue();
  final Map<String, Object> params;
  
  ParseConfig()
  {
    this.params = Collections.unmodifiableMap(new HashMap());
  }
  
  ParseConfig(JSONObject paramJSONObject, ParseDecoder paramParseDecoder)
  {
    paramJSONObject = (Map)((Map)paramParseDecoder.decode(paramJSONObject)).get("params");
    if (paramJSONObject == null) {
      throw new RuntimeException("Object did not contain the 'params' key.");
    }
    this.params = Collections.unmodifiableMap(paramJSONObject);
  }
  
  public static ParseConfig get()
    throws ParseException
  {
    return (ParseConfig)ParseTaskUtils.wait(getInBackground());
  }
  
  private static Task<ParseConfig> getAsync(Task<Void> paramTask)
  {
    ParseUser.getCurrentSessionTokenAsync().onSuccessTask(new Continuation()
    {
      public Task<ParseConfig> then(final Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        this.val$toAwait.continueWithTask(new Continuation()
        {
          public Task<ParseConfig> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            return ParseConfig.getConfigController().getAsync(paramAnonymousTask);
          }
        });
      }
    });
  }
  
  static ParseConfigController getConfigController()
  {
    return ParseCorePlugins.getInstance().getConfigController();
  }
  
  public static ParseConfig getCurrentConfig()
  {
    try
    {
      ParseConfig localParseConfig = (ParseConfig)ParseTaskUtils.wait(getConfigController().getCurrentConfigController().getCurrentConfigAsync());
      return localParseConfig;
    }
    catch (ParseException localParseException) {}
    return new ParseConfig();
  }
  
  public static Task<ParseConfig> getInBackground()
  {
    taskQueue.enqueue(new Continuation()
    {
      public Task<ParseConfig> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseConfig.getAsync(paramAnonymousTask);
      }
    });
  }
  
  public static void getInBackground(ConfigCallback paramConfigCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(getInBackground(), paramConfigCallback);
  }
  
  public Object get(String paramString)
  {
    return get(paramString, null);
  }
  
  public Object get(String paramString, Object paramObject)
  {
    if (!this.params.containsKey(paramString)) {
      return paramObject;
    }
    if (this.params.get(paramString) == JSONObject.NULL) {
      return null;
    }
    return this.params.get(paramString);
  }
  
  public boolean getBoolean(String paramString)
  {
    return getBoolean(paramString, false);
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    if (!this.params.containsKey(paramString)) {}
    do
    {
      return paramBoolean;
      paramString = this.params.get(paramString);
    } while (!(paramString instanceof Boolean));
    return ((Boolean)paramString).booleanValue();
  }
  
  public Date getDate(String paramString)
  {
    return getDate(paramString, null);
  }
  
  public Date getDate(String paramString, Date paramDate)
  {
    if (!this.params.containsKey(paramString)) {
      return paramDate;
    }
    paramString = this.params.get(paramString);
    if ((paramString == null) || (paramString == JSONObject.NULL)) {
      return null;
    }
    if ((paramString instanceof Date)) {}
    for (paramString = (Date)paramString;; paramString = paramDate) {
      return paramString;
    }
  }
  
  public double getDouble(String paramString)
  {
    return getDouble(paramString, 0.0D);
  }
  
  public double getDouble(String paramString, double paramDouble)
  {
    paramString = getNumber(paramString);
    if (paramString != null) {
      paramDouble = paramString.doubleValue();
    }
    return paramDouble;
  }
  
  public int getInt(String paramString)
  {
    return getInt(paramString, 0);
  }
  
  public int getInt(String paramString, int paramInt)
  {
    paramString = getNumber(paramString);
    if (paramString != null) {
      paramInt = paramString.intValue();
    }
    return paramInt;
  }
  
  public JSONArray getJSONArray(String paramString)
  {
    return getJSONArray(paramString, null);
  }
  
  public JSONArray getJSONArray(String paramString, JSONArray paramJSONArray)
  {
    paramString = getList(paramString);
    if (paramString != null) {}
    for (paramString = PointerEncoder.get().encode(paramString);; paramString = null)
    {
      if ((paramString == null) || ((paramString instanceof JSONArray))) {
        paramJSONArray = (JSONArray)paramString;
      }
      return paramJSONArray;
    }
  }
  
  public JSONObject getJSONObject(String paramString)
  {
    return getJSONObject(paramString, null);
  }
  
  public JSONObject getJSONObject(String paramString, JSONObject paramJSONObject)
  {
    paramString = getMap(paramString);
    if (paramString != null) {}
    for (paramString = PointerEncoder.get().encode(paramString);; paramString = null)
    {
      if ((paramString == null) || ((paramString instanceof JSONObject))) {
        paramJSONObject = (JSONObject)paramString;
      }
      return paramJSONObject;
    }
  }
  
  public <T> List<T> getList(String paramString)
  {
    return getList(paramString, null);
  }
  
  public <T> List<T> getList(String paramString, List<T> paramList)
  {
    if (!this.params.containsKey(paramString)) {
      return paramList;
    }
    paramString = this.params.get(paramString);
    if ((paramString == null) || (paramString == JSONObject.NULL)) {
      return null;
    }
    if ((paramString instanceof List)) {}
    for (paramString = (List)paramString;; paramString = paramList) {
      return paramString;
    }
  }
  
  public long getLong(String paramString)
  {
    return getLong(paramString, 0L);
  }
  
  public long getLong(String paramString, long paramLong)
  {
    paramString = getNumber(paramString);
    if (paramString != null) {
      paramLong = paramString.longValue();
    }
    return paramLong;
  }
  
  public <V> Map<String, V> getMap(String paramString)
  {
    return getMap(paramString, null);
  }
  
  public <V> Map<String, V> getMap(String paramString, Map<String, V> paramMap)
  {
    if (!this.params.containsKey(paramString)) {
      return paramMap;
    }
    paramString = this.params.get(paramString);
    if ((paramString == null) || (paramString == JSONObject.NULL)) {
      return null;
    }
    if ((paramString instanceof Map)) {}
    for (paramString = (Map)paramString;; paramString = paramMap) {
      return paramString;
    }
  }
  
  public Number getNumber(String paramString)
  {
    return getNumber(paramString, null);
  }
  
  public Number getNumber(String paramString, Number paramNumber)
  {
    if (!this.params.containsKey(paramString)) {
      return paramNumber;
    }
    paramString = this.params.get(paramString);
    if ((paramString == null) || (paramString == JSONObject.NULL)) {
      return null;
    }
    if ((paramString instanceof Number)) {}
    for (paramString = (Number)paramString;; paramString = paramNumber) {
      return paramString;
    }
  }
  
  Map<String, Object> getParams()
  {
    return Collections.unmodifiableMap(new HashMap(this.params));
  }
  
  public ParseFile getParseFile(String paramString)
  {
    return getParseFile(paramString, null);
  }
  
  public ParseFile getParseFile(String paramString, ParseFile paramParseFile)
  {
    if (!this.params.containsKey(paramString)) {
      return paramParseFile;
    }
    paramString = this.params.get(paramString);
    if ((paramString == null) || (paramString == JSONObject.NULL)) {
      return null;
    }
    if ((paramString instanceof ParseFile)) {}
    for (paramString = (ParseFile)paramString;; paramString = paramParseFile) {
      return paramString;
    }
  }
  
  public ParseGeoPoint getParseGeoPoint(String paramString)
  {
    return getParseGeoPoint(paramString, null);
  }
  
  public ParseGeoPoint getParseGeoPoint(String paramString, ParseGeoPoint paramParseGeoPoint)
  {
    if (!this.params.containsKey(paramString)) {
      return paramParseGeoPoint;
    }
    paramString = this.params.get(paramString);
    if ((paramString == null) || (paramString == JSONObject.NULL)) {
      return null;
    }
    if ((paramString instanceof ParseGeoPoint)) {}
    for (paramString = (ParseGeoPoint)paramString;; paramString = paramParseGeoPoint) {
      return paramString;
    }
  }
  
  public String getString(String paramString)
  {
    return getString(paramString, null);
  }
  
  public String getString(String paramString1, String paramString2)
  {
    if (!this.params.containsKey(paramString1)) {
      return paramString2;
    }
    paramString1 = this.params.get(paramString1);
    if ((paramString1 == null) || (paramString1 == JSONObject.NULL)) {
      return null;
    }
    if ((paramString1 instanceof String)) {}
    for (paramString1 = (String)paramString1;; paramString1 = paramString2) {
      return paramString1;
    }
  }
  
  public String toString()
  {
    return "ParseConfig[" + this.params.toString() + "]";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
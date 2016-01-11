package com.parse;

import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import org.json.JSONException;
import org.json.JSONObject;

class PushHistory
{
  private static final String TAG = "com.parse.PushHistory";
  private final PriorityQueue<Entry> entries;
  private String lastTime;
  private final int maxHistoryLength;
  private final HashSet<String> pushIds;
  
  public PushHistory(int paramInt, JSONObject paramJSONObject)
  {
    this.maxHistoryLength = paramInt;
    this.entries = new PriorityQueue(paramInt + 1);
    this.pushIds = new HashSet(paramInt + 1);
    this.lastTime = null;
    if (paramJSONObject != null)
    {
      JSONObject localJSONObject = paramJSONObject.optJSONObject("seen");
      if (localJSONObject != null)
      {
        Iterator localIterator = localJSONObject.keys();
        while (localIterator.hasNext())
        {
          String str1 = (String)localIterator.next();
          String str2 = localJSONObject.optString(str1, null);
          if ((str1 != null) && (str2 != null)) {
            tryInsertPush(str1, str2);
          }
        }
      }
      setLastReceivedTimestamp(paramJSONObject.optString("lastTime", null));
    }
  }
  
  public String getLastReceivedTimestamp()
  {
    return this.lastTime;
  }
  
  public void setLastReceivedTimestamp(String paramString)
  {
    this.lastTime = paramString;
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    if (this.entries.size() > 0)
    {
      JSONObject localJSONObject2 = new JSONObject();
      Iterator localIterator = this.entries.iterator();
      while (localIterator.hasNext())
      {
        Entry localEntry = (Entry)localIterator.next();
        localJSONObject2.put(localEntry.pushId, localEntry.timestamp);
      }
      localJSONObject1.put("seen", localJSONObject2);
    }
    localJSONObject1.putOpt("lastTime", this.lastTime);
    return localJSONObject1;
  }
  
  public boolean tryInsertPush(String paramString1, String paramString2)
  {
    if (paramString2 == null) {
      throw new IllegalArgumentException("Can't insert null pushId or timestamp into history");
    }
    if ((this.lastTime == null) || (paramString2.compareTo(this.lastTime) > 0)) {
      this.lastTime = paramString2;
    }
    if (this.pushIds.contains(paramString1))
    {
      PLog.e("com.parse.PushHistory", "Ignored duplicate push " + paramString1);
      return false;
    }
    this.entries.add(new Entry(paramString1, paramString2));
    this.pushIds.add(paramString1);
    while (this.entries.size() > this.maxHistoryLength)
    {
      paramString1 = (Entry)this.entries.remove();
      this.pushIds.remove(paramString1.pushId);
    }
    return true;
  }
  
  private static class Entry
    implements Comparable<Entry>
  {
    public String pushId;
    public String timestamp;
    
    public Entry(String paramString1, String paramString2)
    {
      this.pushId = paramString1;
      this.timestamp = paramString2;
    }
    
    public int compareTo(Entry paramEntry)
    {
      return this.timestamp.compareTo(paramEntry.timestamp);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/PushHistory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
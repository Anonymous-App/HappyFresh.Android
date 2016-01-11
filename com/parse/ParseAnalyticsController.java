package com.parse;

import bolts.Task;
import org.json.JSONObject;

class ParseAnalyticsController
{
  ParseEventuallyQueue eventuallyQueue;
  
  public ParseAnalyticsController(ParseEventuallyQueue paramParseEventuallyQueue)
  {
    this.eventuallyQueue = paramParseEventuallyQueue;
  }
  
  public Task<Void> trackAppOpenedInBackground(String paramString1, String paramString2)
  {
    paramString1 = ParseRESTAnalyticsCommand.trackAppOpenedCommand(paramString1, paramString2);
    return this.eventuallyQueue.enqueueEventuallyAsync(paramString1, null).makeVoid();
  }
  
  public Task<Void> trackEventInBackground(String paramString1, JSONObject paramJSONObject, String paramString2)
  {
    paramString1 = ParseRESTAnalyticsCommand.trackEventCommand(paramString1, paramJSONObject, paramString2);
    return this.eventuallyQueue.enqueueEventuallyAsync(paramString1, null).makeVoid();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseAnalyticsController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
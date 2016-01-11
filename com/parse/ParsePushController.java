package com.parse;

import bolts.Task;

class ParsePushController
{
  static final String DEVICE_TYPE_ANDROID = "android";
  static final String DEVICE_TYPE_IOS = "ios";
  private final ParseHttpClient restClient;
  
  public ParsePushController(ParseHttpClient paramParseHttpClient)
  {
    this.restClient = paramParseHttpClient;
  }
  
  ParseRESTCommand buildRESTSendPushCommand(ParsePush.State paramState, String paramString)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    int i;
    int j;
    if (paramState.queryState() == null)
    {
      if ((paramState.pushToAndroid() == null) || (!paramState.pushToAndroid().booleanValue())) {
        break label93;
      }
      i = 1;
      if ((paramState.pushToIOS() == null) || (!paramState.pushToIOS().booleanValue())) {
        break label98;
      }
      j = 1;
      label53:
      if ((j == 0) || (i == 0)) {
        break label104;
      }
      localObject1 = localObject2;
    }
    for (;;)
    {
      return ParseRESTPushCommand.sendPushCommand(paramState.queryState(), paramState.channelSet(), (String)localObject1, paramState.expirationTime(), paramState.expirationTimeInterval(), paramState.data(), paramString);
      label93:
      i = 0;
      break;
      label98:
      j = 0;
      break label53;
      label104:
      if (j != 0)
      {
        localObject1 = "ios";
      }
      else
      {
        localObject1 = localObject2;
        if (i != 0) {
          localObject1 = "android";
        }
      }
    }
  }
  
  public Task<Void> sendInBackground(ParsePush.State paramState, String paramString)
  {
    return buildRESTSendPushCommand(paramState, paramString).executeAsync(this.restClient).makeVoid();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParsePushController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
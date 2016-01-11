package com.parse;

import bolts.Task;
import java.util.List;

class OfflineQueryController
  extends AbstractQueryController
{
  private final ParseQueryController networkController;
  private final OfflineStore offlineStore;
  
  public OfflineQueryController(OfflineStore paramOfflineStore, ParseQueryController paramParseQueryController)
  {
    this.offlineStore = paramOfflineStore;
    this.networkController = paramParseQueryController;
  }
  
  public <T extends ParseObject> Task<Integer> countAsync(ParseQuery.State<T> paramState, ParseUser paramParseUser, Task<Void> paramTask)
  {
    if (paramState.isFromLocalDatastore()) {
      return this.offlineStore.countFromPinAsync(paramState.pinName(), paramState, paramParseUser);
    }
    return this.networkController.countAsync(paramState, paramParseUser, paramTask);
  }
  
  public <T extends ParseObject> Task<List<T>> findAsync(ParseQuery.State<T> paramState, ParseUser paramParseUser, Task<Void> paramTask)
  {
    if (paramState.isFromLocalDatastore()) {
      return this.offlineStore.findFromPinAsync(paramState.pinName(), paramState, paramParseUser);
    }
    return this.networkController.findAsync(paramState, paramParseUser, paramTask);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/OfflineQueryController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.List;

abstract class AbstractQueryController
  implements ParseQueryController
{
  public <T extends ParseObject> Task<T> getFirstAsync(ParseQuery.State<T> paramState, ParseUser paramParseUser, Task<Void> paramTask)
  {
    findAsync(paramState, paramParseUser, paramTask).continueWith(new Continuation()
    {
      public T then(Task<List<T>> paramAnonymousTask)
        throws Exception
      {
        if (paramAnonymousTask.isFaulted()) {
          throw paramAnonymousTask.getError();
        }
        if ((paramAnonymousTask.getResult() != null) && (((List)paramAnonymousTask.getResult()).size() > 0)) {
          return (ParseObject)((List)paramAnonymousTask.getResult()).get(0);
        }
        throw new ParseException(101, "no results found for query");
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/AbstractQueryController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
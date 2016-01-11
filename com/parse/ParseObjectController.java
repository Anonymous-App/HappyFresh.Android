package com.parse;

import bolts.Task;
import java.util.List;

abstract interface ParseObjectController
{
  public abstract List<Task<Void>> deleteAllAsync(List<ParseObject.State> paramList, String paramString);
  
  public abstract Task<Void> deleteAsync(ParseObject.State paramState, String paramString);
  
  public abstract Task<ParseObject.State> fetchAsync(ParseObject.State paramState, String paramString, ParseDecoder paramParseDecoder);
  
  public abstract List<Task<ParseObject.State>> saveAllAsync(List<ParseObject.State> paramList, List<ParseOperationSet> paramList1, String paramString, List<ParseDecoder> paramList2);
  
  public abstract Task<ParseObject.State> saveAsync(ParseObject.State paramState, ParseOperationSet paramParseOperationSet, String paramString, ParseDecoder paramParseDecoder);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseObjectController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
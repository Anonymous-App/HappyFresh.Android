package com.parse;

import org.json.JSONException;
import org.json.JSONObject;

class ParseIncrementOperation
  implements ParseFieldOperation
{
  private final Number amount;
  
  public ParseIncrementOperation(Number paramNumber)
  {
    this.amount = paramNumber;
  }
  
  public Object apply(Object paramObject, String paramString)
  {
    if (paramObject == null) {
      return this.amount;
    }
    if ((paramObject instanceof Number)) {
      return Numbers.add((Number)paramObject, this.amount);
    }
    throw new IllegalArgumentException("You cannot increment a non-number.");
  }
  
  public JSONObject encode(ParseEncoder paramParseEncoder)
    throws JSONException
  {
    paramParseEncoder = new JSONObject();
    paramParseEncoder.put("__op", "Increment");
    paramParseEncoder.put("amount", this.amount);
    return paramParseEncoder;
  }
  
  public ParseFieldOperation mergeWithPrevious(ParseFieldOperation paramParseFieldOperation)
  {
    if (paramParseFieldOperation == null) {
      return this;
    }
    if ((paramParseFieldOperation instanceof ParseDeleteOperation)) {
      return new ParseSetOperation(this.amount);
    }
    if ((paramParseFieldOperation instanceof ParseSetOperation))
    {
      paramParseFieldOperation = ((ParseSetOperation)paramParseFieldOperation).getValue();
      if ((paramParseFieldOperation instanceof Number)) {
        return new ParseSetOperation(Numbers.add((Number)paramParseFieldOperation, this.amount));
      }
      throw new IllegalArgumentException("You cannot increment a non-number.");
    }
    if ((paramParseFieldOperation instanceof ParseIncrementOperation)) {
      return new ParseIncrementOperation(Numbers.add(((ParseIncrementOperation)paramParseFieldOperation).amount, this.amount));
    }
    throw new IllegalArgumentException("Operation is invalid after previous operation.");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseIncrementOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.parse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class ParseMulticastDelegate<T>
{
  private final List<ParseCallback2<T, ParseException>> callbacks = new LinkedList();
  
  public void clear()
  {
    this.callbacks.clear();
  }
  
  public void invoke(T paramT, ParseException paramParseException)
  {
    Iterator localIterator = new ArrayList(this.callbacks).iterator();
    while (localIterator.hasNext()) {
      ((ParseCallback2)localIterator.next()).done(paramT, paramParseException);
    }
  }
  
  public void subscribe(ParseCallback2<T, ParseException> paramParseCallback2)
  {
    this.callbacks.add(paramParseCallback2);
  }
  
  public void unsubscribe(ParseCallback2<T, ParseException> paramParseCallback2)
  {
    this.callbacks.remove(paramParseCallback2);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseMulticastDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
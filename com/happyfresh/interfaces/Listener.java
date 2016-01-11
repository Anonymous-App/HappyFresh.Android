package com.happyfresh.interfaces;

import com.happyfresh.common.ICartNotification.Type;

public abstract interface Listener<T>
{
  public abstract void onHappened(Object paramObject, ICartNotification.Type paramType, T paramT);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/interfaces/Listener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
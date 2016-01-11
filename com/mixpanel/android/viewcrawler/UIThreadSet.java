package com.mixpanel.android.viewcrawler;

import android.os.Looper;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class UIThreadSet<T>
{
  private Set<T> mSet = new HashSet();
  
  public void add(T paramT)
  {
    if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
      throw new RuntimeException("Can't add an activity when not on the UI thread");
    }
    this.mSet.add(paramT);
  }
  
  public Set<T> getAll()
  {
    if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
      throw new RuntimeException("Can't remove an activity when not on the UI thread");
    }
    return Collections.unmodifiableSet(this.mSet);
  }
  
  public boolean isEmpty()
  {
    if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
      throw new RuntimeException("Can't check isEmpty() when not on the UI thread");
    }
    return this.mSet.isEmpty();
  }
  
  public void remove(T paramT)
  {
    if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
      throw new RuntimeException("Can't remove an activity when not on the UI thread");
    }
    this.mSet.remove(paramT);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/viewcrawler/UIThreadSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
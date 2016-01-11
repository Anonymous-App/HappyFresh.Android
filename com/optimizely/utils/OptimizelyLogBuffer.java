package com.optimizely.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class OptimizelyLogBuffer
{
  private static final int sLOG_SIZE = 100;
  @NonNull
  private ReadWriteLock errorsLock = new ReentrantReadWriteLock();
  @NonNull
  private ReadWriteLock goalsLock = new ReentrantReadWriteLock();
  private int mErrorsHead = 0;
  @NonNull
  private final String[] mErrorsLog = new String[100];
  private int mGoalsHead = 0;
  @NonNull
  private final String[] mGoalsLog = new String[100];
  
  public void error(@Nullable String paramString)
  {
    this.errorsLock.writeLock().lock();
    this.mErrorsLog[this.mErrorsHead] = paramString;
    this.mErrorsHead = ((this.mErrorsHead + 1) % 100);
    this.errorsLock.writeLock().unlock();
  }
  
  @NonNull
  public List<String> getErrorSnapshot()
  {
    ArrayList localArrayList = new ArrayList(100);
    this.errorsLock.readLock().lock();
    for (int i = (this.mErrorsHead + 1) % 100; i != this.mErrorsHead; i = (i + 1) % 100) {
      if (this.mErrorsLog[i] != null) {
        localArrayList.add(this.mErrorsLog[i]);
      }
    }
    this.errorsLock.readLock().unlock();
    Collections.reverse(localArrayList);
    return localArrayList;
  }
  
  @NonNull
  public List<String> getGoalsSnapshot()
  {
    ArrayList localArrayList = new ArrayList(100);
    this.goalsLock.readLock().lock();
    for (int i = (this.mGoalsHead + 1) % 100; i != this.mGoalsHead; i = (i + 1) % 100) {
      if (this.mGoalsLog[i] != null) {
        localArrayList.add(this.mGoalsLog[i]);
      }
    }
    this.goalsLock.readLock().unlock();
    Collections.reverse(localArrayList);
    return localArrayList;
  }
  
  public void goal(@Nullable String paramString)
  {
    this.goalsLock.writeLock().lock();
    this.mGoalsLog[this.mGoalsHead] = paramString;
    this.mGoalsHead = ((this.mGoalsHead + 1) % 100);
    this.goalsLock.writeLock().unlock();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/utils/OptimizelyLogBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
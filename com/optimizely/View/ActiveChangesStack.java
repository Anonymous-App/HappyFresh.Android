package com.optimizely.View;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.optimizely.Optimizely;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class ActiveChangesStack
{
  private static int currentChangeId;
  private final long debounceIntervalMs;
  @NonNull
  private final Map<String, Map<String, HistoryDequeue>> mChangeStack = new HashMap();
  private final int maxHistorySize;
  @NonNull
  private final Optimizely optimizely;
  
  static
  {
    if (!ActiveChangesStack.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      currentChangeId = 0;
      return;
    }
  }
  
  public ActiveChangesStack(int paramInt, long paramLong, @NonNull Optimizely paramOptimizely)
  {
    this.maxHistorySize = (paramInt + 1);
    this.debounceIntervalMs = paramLong;
    this.optimizely = paramOptimizely;
  }
  
  public ActiveChangesStack(@NonNull Optimizely paramOptimizely)
  {
    this(1, 200L, paramOptimizely);
  }
  
  public void addChange(@NonNull String paramString1, @NonNull String paramString2, @Nullable Object paramObject)
  {
    if (this.mChangeStack.get(paramString1) == null) {
      this.mChangeStack.put(paramString1, new HashMap());
    }
    Object localObject = (Map)this.mChangeStack.get(paramString1);
    assert (localObject != null);
    if (!((Map)localObject).containsKey(paramString2)) {
      ((Map)localObject).put(paramString2, new HistoryDequeue(new OptimizelyVisualChange(paramString1, paramString2, this.optimizely.getViews().getViewProperty(paramString1, paramString2))));
    }
    localObject = (HistoryDequeue)((Map)localObject).get(paramString2);
    assert (localObject != null);
    if ((!((HistoryDequeue)localObject).isEmpty()) && (((OptimizelyVisualChange)((HistoryDequeue)localObject).peek()).timestamp - System.currentTimeMillis() < this.debounceIntervalMs))
    {
      paramString1 = (OptimizelyVisualChange)((HistoryDequeue)localObject).peek();
      assert (paramString1 != null);
      paramString1.value = paramObject;
      OptimizelyVisualChange.access$002(paramString1, System.currentTimeMillis());
    }
    while (this.maxHistorySize <= 0) {
      return;
    }
    while (((HistoryDequeue)localObject).size() >= this.maxHistorySize) {
      ((HistoryDequeue)localObject).removeLast();
    }
    ((HistoryDequeue)localObject).push(new OptimizelyVisualChange(paramString1, paramString2, paramObject));
  }
  
  @NonNull
  public List<Pair<String, String>> getCurrentChangeKeys()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = this.mChangeStack.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str = (String)localIterator1.next();
      Iterator localIterator2 = ((Map)this.mChangeStack.get(str)).keySet().iterator();
      while (localIterator2.hasNext()) {
        localArrayList.add(new Pair(str, (String)localIterator2.next()));
      }
    }
    return localArrayList;
  }
  
  @NonNull
  public Map<String, Object> getCurrentChangesForId(String paramString)
  {
    HashMap localHashMap = new HashMap();
    Map localMap = (Map)this.mChangeStack.get(paramString);
    if (localMap != null)
    {
      Iterator localIterator = localMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        paramString = (HistoryDequeue)localMap.get(str);
        if (paramString != null)
        {
          assert (paramString.baseState != null);
          if (paramString.isEmpty()) {}
          for (paramString = paramString.baseState.value;; paramString = ((OptimizelyVisualChange)paramString.peek()).value)
          {
            localHashMap.put(str, paramString);
            break;
          }
        }
      }
    }
    return localHashMap;
  }
  
  @Nullable
  public HistoryDequeue getHistory(String paramString1, String paramString2)
  {
    if (this.mChangeStack.get(paramString1) == null) {
      this.mChangeStack.put(paramString1, new HashMap());
    }
    paramString1 = (Map)this.mChangeStack.get(paramString1);
    assert (paramString1 != null);
    return (HistoryDequeue)paramString1.get(paramString2);
  }
  
  public boolean hasUndo(String paramString1, String paramString2)
  {
    boolean bool2 = false;
    paramString1 = (Map)this.mChangeStack.get(paramString1);
    boolean bool1 = bool2;
    if (paramString1 != null)
    {
      paramString1 = (Queue)paramString1.get(paramString2);
      bool1 = bool2;
      if (paramString1 != null)
      {
        bool1 = bool2;
        if (!paramString1.isEmpty()) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  @Nullable
  public OptimizelyVisualChange revertToBase(String paramString1, String paramString2)
  {
    paramString1 = (Map)this.mChangeStack.get(paramString1);
    if (paramString1 != null)
    {
      paramString1 = (HistoryDequeue)paramString1.get(paramString2);
      if (paramString1 != null)
      {
        paramString1.clear();
        return paramString1.baseState;
      }
    }
    return null;
  }
  
  @Nullable
  public OptimizelyVisualChange undoChange(String paramString1, String paramString2)
  {
    paramString1 = (Map)this.mChangeStack.get(paramString1);
    if (paramString1 != null)
    {
      paramString1 = (HistoryDequeue)paramString1.get(paramString2);
      if (paramString1 != null)
      {
        if (!paramString1.isEmpty()) {
          paramString1.pop();
        }
        if (paramString1.isEmpty()) {
          return paramString1.baseState;
        }
        return (OptimizelyVisualChange)paramString1.peek();
      }
    }
    return null;
  }
  
  @Nullable
  public OptimizelyVisualChange undoChangeById(String paramString1, String paramString2, int paramInt)
  {
    paramString1 = (Map)this.mChangeStack.get(paramString1);
    if (paramString1 != null)
    {
      paramString1 = (HistoryDequeue)paramString1.get(paramString2);
      if ((paramString1 != null) && (!paramString1.isEmpty()))
      {
        paramString2 = paramString1.iterator();
        while (paramString2.hasNext())
        {
          OptimizelyVisualChange localOptimizelyVisualChange = (OptimizelyVisualChange)paramString2.next();
          if (localOptimizelyVisualChange.changeId == paramInt) {
            paramString1.remove(localOptimizelyVisualChange);
          }
        }
        if (paramString1.isEmpty()) {
          return paramString1.baseState;
        }
        return (OptimizelyVisualChange)paramString1.peek();
      }
    }
    return null;
  }
  
  public static class HistoryDequeue
    extends LinkedList<ActiveChangesStack.OptimizelyVisualChange>
  {
    private final ActiveChangesStack.OptimizelyVisualChange baseState;
    
    public HistoryDequeue(ActiveChangesStack.OptimizelyVisualChange paramOptimizelyVisualChange)
    {
      this.baseState = paramOptimizelyVisualChange;
    }
  }
  
  public static class OptimizelyVisualChange
  {
    public final int changeId;
    public final String optimizelyId;
    public final String propertyKey;
    private long timestamp;
    public Object value;
    
    public OptimizelyVisualChange(String paramString1, String paramString2, @Nullable Object paramObject)
    {
      this.optimizelyId = paramString1;
      this.propertyKey = paramString2;
      this.value = paramObject;
      this.changeId = ActiveChangesStack.access$208();
      this.timestamp = System.currentTimeMillis();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/View/ActiveChangesStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
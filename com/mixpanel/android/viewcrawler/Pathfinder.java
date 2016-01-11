package com.mixpanel.android.viewcrawler;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

class Pathfinder
{
  private static final String LOGTAG = "MixpanelAPI.PathFinder";
  private final IntStack mIndexStack = new IntStack();
  
  private View findPrefixedMatch(PathElement paramPathElement, View paramView, int paramInt)
  {
    int i = this.mIndexStack.read(paramInt);
    if (matches(paramPathElement, paramView))
    {
      this.mIndexStack.increment(paramInt);
      if ((paramPathElement.index == -1) || (paramPathElement.index == i)) {
        return paramView;
      }
    }
    if ((paramPathElement.prefix == 1) && ((paramView instanceof ViewGroup)))
    {
      paramView = (ViewGroup)paramView;
      int j = paramView.getChildCount();
      i = 0;
      while (i < j)
      {
        View localView = findPrefixedMatch(paramPathElement, paramView.getChildAt(i), paramInt);
        if (localView != null) {
          return localView;
        }
        i += 1;
      }
    }
    return null;
  }
  
  private void findTargetsInMatchedView(View paramView, List<PathElement> paramList, Accumulator paramAccumulator)
  {
    if (paramList.isEmpty()) {
      paramAccumulator.accumulate(paramView);
    }
    while (!(paramView instanceof ViewGroup)) {
      return;
    }
    if (this.mIndexStack.full())
    {
      Log.v("MixpanelAPI.PathFinder", "Path is too deep, will not match");
      return;
    }
    paramView = (ViewGroup)paramView;
    PathElement localPathElement = (PathElement)paramList.get(0);
    paramList = paramList.subList(1, paramList.size());
    int j = paramView.getChildCount();
    int k = this.mIndexStack.alloc();
    int i = 0;
    for (;;)
    {
      if (i < j)
      {
        View localView = findPrefixedMatch(localPathElement, paramView.getChildAt(i), k);
        if (localView != null) {
          findTargetsInMatchedView(localView, paramList, paramAccumulator);
        }
        if ((localPathElement.index < 0) || (this.mIndexStack.read(k) <= localPathElement.index)) {}
      }
      else
      {
        this.mIndexStack.free();
        return;
      }
      i += 1;
    }
  }
  
  private static boolean hasClassName(Object paramObject, String paramString)
  {
    for (paramObject = paramObject.getClass();; paramObject = ((Class)paramObject).getSuperclass())
    {
      if (((Class)paramObject).getCanonicalName().equals(paramString)) {
        return true;
      }
      if (paramObject == Object.class) {
        return false;
      }
    }
  }
  
  private boolean matches(PathElement paramPathElement, View paramView)
  {
    if ((paramPathElement.viewClassName != null) && (!hasClassName(paramView, paramPathElement.viewClassName))) {}
    String str;
    do
    {
      do
      {
        return false;
      } while (((-1 != paramPathElement.viewId) && (paramView.getId() != paramPathElement.viewId)) || ((paramPathElement.contentDescription != null) && (!paramPathElement.contentDescription.equals(paramView.getContentDescription()))));
      str = paramPathElement.tag;
    } while ((paramPathElement.tag != null) && ((paramView.getTag() == null) || (!str.equals(paramView.getTag().toString()))));
    return true;
  }
  
  public void findTargetsInRoot(View paramView, List<PathElement> paramList, Accumulator paramAccumulator)
  {
    if (paramList.isEmpty()) {}
    do
    {
      return;
      if (this.mIndexStack.full())
      {
        Log.w("MixpanelAPI.PathFinder", "There appears to be a concurrency issue in the pathfinding code. Path will not be matched.");
        return;
      }
      PathElement localPathElement = (PathElement)paramList.get(0);
      paramList = paramList.subList(1, paramList.size());
      paramView = findPrefixedMatch(localPathElement, paramView, this.mIndexStack.alloc());
      this.mIndexStack.free();
    } while (paramView == null);
    findTargetsInMatchedView(paramView, paramList, paramAccumulator);
  }
  
  public static abstract interface Accumulator
  {
    public abstract void accumulate(View paramView);
  }
  
  private static class IntStack
  {
    private static final int MAX_INDEX_STACK_SIZE = 256;
    private final int[] mStack = new int['Ā'];
    private int mStackSize = 0;
    
    public int alloc()
    {
      int i = this.mStackSize;
      this.mStackSize += 1;
      this.mStack[i] = 0;
      return i;
    }
    
    public void free()
    {
      this.mStackSize -= 1;
      if (this.mStackSize < 0) {
        throw new ArrayIndexOutOfBoundsException(this.mStackSize);
      }
    }
    
    public boolean full()
    {
      return this.mStack.length == this.mStackSize;
    }
    
    public void increment(int paramInt)
    {
      int[] arrayOfInt = this.mStack;
      arrayOfInt[paramInt] += 1;
    }
    
    public int read(int paramInt)
    {
      return this.mStack[paramInt];
    }
  }
  
  public static class PathElement
  {
    public static final int SHORTEST_PREFIX = 1;
    public static final int ZERO_LENGTH_PREFIX = 0;
    public final String contentDescription;
    public final int index;
    public final int prefix;
    public final String tag;
    public final String viewClassName;
    public final int viewId;
    
    public PathElement(int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2, String paramString3)
    {
      this.prefix = paramInt1;
      this.viewClassName = paramString1;
      this.index = paramInt2;
      this.viewId = paramInt3;
      this.contentDescription = paramString2;
      this.tag = paramString3;
    }
    
    public String toString()
    {
      try
      {
        Object localObject = new JSONObject();
        if (this.prefix == 1) {
          ((JSONObject)localObject).put("prefix", "shortest");
        }
        if (this.viewClassName != null) {
          ((JSONObject)localObject).put("view_class", this.viewClassName);
        }
        if (this.index > -1) {
          ((JSONObject)localObject).put("index", this.index);
        }
        if (this.viewId > -1) {
          ((JSONObject)localObject).put("id", this.viewId);
        }
        if (this.contentDescription != null) {
          ((JSONObject)localObject).put("contentDescription", this.contentDescription);
        }
        if (this.tag != null) {
          ((JSONObject)localObject).put("tag", this.tag);
        }
        localObject = ((JSONObject)localObject).toString();
        return (String)localObject;
      }
      catch (JSONException localJSONException)
      {
        throw new RuntimeException("Can't serialize PathElement to String", localJSONException);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/viewcrawler/Pathfinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
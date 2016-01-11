package com.mixpanel.android.viewcrawler;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;

@TargetApi(16)
abstract class ViewVisitor
  implements Pathfinder.Accumulator
{
  private static final String LOGTAG = "MixpanelAPI.ViewVisitor";
  private final List<Pathfinder.PathElement> mPath;
  private final Pathfinder mPathfinder;
  
  protected ViewVisitor(List<Pathfinder.PathElement> paramList)
  {
    this.mPath = paramList;
    this.mPathfinder = new Pathfinder();
  }
  
  public abstract void cleanup();
  
  protected List<Pathfinder.PathElement> getPath()
  {
    return this.mPath;
  }
  
  protected Pathfinder getPathfinder()
  {
    return this.mPathfinder;
  }
  
  protected abstract String name();
  
  public void visit(View paramView)
  {
    this.mPathfinder.findTargetsInRoot(paramView, this.mPath, this);
  }
  
  public static class AddAccessibilityEventVisitor
    extends ViewVisitor.EventTriggeringVisitor
  {
    private final int mEventType;
    private final WeakHashMap<View, TrackingAccessibilityDelegate> mWatching;
    
    public AddAccessibilityEventVisitor(List<Pathfinder.PathElement> paramList, int paramInt, String paramString, ViewVisitor.OnEventListener paramOnEventListener)
    {
      super(paramString, paramOnEventListener, false);
      this.mEventType = paramInt;
      this.mWatching = new WeakHashMap();
    }
    
    private View.AccessibilityDelegate getOldDelegate(View paramView)
    {
      try
      {
        paramView = (View.AccessibilityDelegate)paramView.getClass().getMethod("getAccessibilityDelegate", new Class[0]).invoke(paramView, new Object[0]);
        return paramView;
      }
      catch (InvocationTargetException paramView)
      {
        Log.w("MixpanelAPI.ViewVisitor", "getAccessibilityDelegate threw an exception when called.", paramView);
        return null;
      }
      catch (IllegalAccessException paramView)
      {
        return null;
      }
      catch (NoSuchMethodException paramView) {}
      return null;
    }
    
    public void accumulate(View paramView)
    {
      Object localObject = getOldDelegate(paramView);
      if (((localObject instanceof TrackingAccessibilityDelegate)) && (((TrackingAccessibilityDelegate)localObject).willFireEvent(getEventName()))) {
        return;
      }
      localObject = new TrackingAccessibilityDelegate((View.AccessibilityDelegate)localObject);
      paramView.setAccessibilityDelegate((View.AccessibilityDelegate)localObject);
      this.mWatching.put(paramView, localObject);
    }
    
    public void cleanup()
    {
      Iterator localIterator = this.mWatching.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Object localObject = (Map.Entry)localIterator.next();
        View localView = (View)((Map.Entry)localObject).getKey();
        localObject = (TrackingAccessibilityDelegate)((Map.Entry)localObject).getValue();
        View.AccessibilityDelegate localAccessibilityDelegate = getOldDelegate(localView);
        if (localAccessibilityDelegate == localObject) {
          localView.setAccessibilityDelegate(((TrackingAccessibilityDelegate)localObject).getRealDelegate());
        } else if ((localAccessibilityDelegate instanceof TrackingAccessibilityDelegate)) {
          ((TrackingAccessibilityDelegate)localAccessibilityDelegate).removeFromDelegateChain((TrackingAccessibilityDelegate)localObject);
        }
      }
      this.mWatching.clear();
    }
    
    protected String name()
    {
      return getEventName() + " event when (" + this.mEventType + ")";
    }
    
    private class TrackingAccessibilityDelegate
      extends View.AccessibilityDelegate
    {
      private View.AccessibilityDelegate mRealDelegate;
      
      public TrackingAccessibilityDelegate(View.AccessibilityDelegate paramAccessibilityDelegate)
      {
        this.mRealDelegate = paramAccessibilityDelegate;
      }
      
      public View.AccessibilityDelegate getRealDelegate()
      {
        return this.mRealDelegate;
      }
      
      public void removeFromDelegateChain(TrackingAccessibilityDelegate paramTrackingAccessibilityDelegate)
      {
        if (this.mRealDelegate == paramTrackingAccessibilityDelegate) {
          this.mRealDelegate = paramTrackingAccessibilityDelegate.getRealDelegate();
        }
        while (!(this.mRealDelegate instanceof TrackingAccessibilityDelegate)) {
          return;
        }
        ((TrackingAccessibilityDelegate)this.mRealDelegate).removeFromDelegateChain(paramTrackingAccessibilityDelegate);
      }
      
      public void sendAccessibilityEvent(View paramView, int paramInt)
      {
        if (paramInt == ViewVisitor.AddAccessibilityEventVisitor.this.mEventType) {
          ViewVisitor.AddAccessibilityEventVisitor.this.fireEvent(paramView);
        }
        if (this.mRealDelegate != null) {
          this.mRealDelegate.sendAccessibilityEvent(paramView, paramInt);
        }
      }
      
      public boolean willFireEvent(String paramString)
      {
        if (ViewVisitor.AddAccessibilityEventVisitor.this.getEventName() == paramString) {
          return true;
        }
        if ((this.mRealDelegate instanceof TrackingAccessibilityDelegate)) {
          return ((TrackingAccessibilityDelegate)this.mRealDelegate).willFireEvent(paramString);
        }
        return false;
      }
    }
  }
  
  public static class AddTextChangeListener
    extends ViewVisitor.EventTriggeringVisitor
  {
    private final Map<TextView, TextWatcher> mWatching = new HashMap();
    
    public AddTextChangeListener(List<Pathfinder.PathElement> paramList, String paramString, ViewVisitor.OnEventListener paramOnEventListener)
    {
      super(paramString, paramOnEventListener, true);
    }
    
    public void accumulate(View paramView)
    {
      if ((paramView instanceof TextView))
      {
        paramView = (TextView)paramView;
        TrackingTextWatcher localTrackingTextWatcher = new TrackingTextWatcher(paramView);
        TextWatcher localTextWatcher = (TextWatcher)this.mWatching.get(paramView);
        if (localTextWatcher != null) {
          paramView.removeTextChangedListener(localTextWatcher);
        }
        paramView.addTextChangedListener(localTrackingTextWatcher);
        this.mWatching.put(paramView, localTrackingTextWatcher);
      }
    }
    
    public void cleanup()
    {
      Iterator localIterator = this.mWatching.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        ((TextView)localEntry.getKey()).removeTextChangedListener((TextWatcher)localEntry.getValue());
      }
      this.mWatching.clear();
    }
    
    protected String name()
    {
      return getEventName() + " on Text Change";
    }
    
    private class TrackingTextWatcher
      implements TextWatcher
    {
      private final View mBoundTo;
      
      public TrackingTextWatcher(View paramView)
      {
        this.mBoundTo = paramView;
      }
      
      public void afterTextChanged(Editable paramEditable)
      {
        ViewVisitor.AddTextChangeListener.this.fireEvent(this.mBoundTo);
      }
      
      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
      
      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
    }
  }
  
  private static class CycleDetector
  {
    private boolean detectSubgraphCycle(TreeMap<View, List<View>> paramTreeMap, View paramView, List<View> paramList)
    {
      if (paramList.contains(paramView)) {
        return false;
      }
      if (paramTreeMap.containsKey(paramView))
      {
        List localList = (List)paramTreeMap.remove(paramView);
        paramList.add(paramView);
        int j = localList.size();
        int i = 0;
        while (i < j)
        {
          if (!detectSubgraphCycle(paramTreeMap, (View)localList.get(i), paramList)) {
            return false;
          }
          i += 1;
        }
        paramList.remove(paramView);
      }
      return true;
    }
    
    public boolean hasCycle(TreeMap<View, List<View>> paramTreeMap)
    {
      ArrayList localArrayList = new ArrayList();
      while (!paramTreeMap.isEmpty()) {
        if (!detectSubgraphCycle(paramTreeMap, (View)paramTreeMap.firstKey(), localArrayList)) {
          return false;
        }
      }
      return true;
    }
  }
  
  private static abstract class EventTriggeringVisitor
    extends ViewVisitor
  {
    private final boolean mDebounce;
    private final String mEventName;
    private final ViewVisitor.OnEventListener mListener;
    
    public EventTriggeringVisitor(List<Pathfinder.PathElement> paramList, String paramString, ViewVisitor.OnEventListener paramOnEventListener, boolean paramBoolean)
    {
      super();
      this.mListener = paramOnEventListener;
      this.mEventName = paramString;
      this.mDebounce = paramBoolean;
    }
    
    protected void fireEvent(View paramView)
    {
      this.mListener.OnEvent(paramView, this.mEventName, this.mDebounce);
    }
    
    protected String getEventName()
    {
      return this.mEventName;
    }
  }
  
  public static class LayoutErrorMessage
  {
    private final String mErrorType;
    private final String mName;
    
    public LayoutErrorMessage(String paramString1, String paramString2)
    {
      this.mErrorType = paramString1;
      this.mName = paramString2;
    }
    
    public String getErrorType()
    {
      return this.mErrorType;
    }
    
    public String getName()
    {
      return this.mName;
    }
  }
  
  public static class LayoutRule
  {
    public final int anchor;
    public final int verb;
    public final int viewId;
    
    public LayoutRule(int paramInt1, int paramInt2, int paramInt3)
    {
      this.viewId = paramInt1;
      this.verb = paramInt2;
      this.anchor = paramInt3;
    }
  }
  
  public static class LayoutUpdateVisitor
    extends ViewVisitor
  {
    private static final Set<Integer> mHorizontalRules = new HashSet(Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(5), Integer.valueOf(7) }));
    private static final Set<Integer> mVerticalRules = new HashSet(Arrays.asList(new Integer[] { Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(6), Integer.valueOf(8) }));
    private boolean mAlive;
    private final List<ViewVisitor.LayoutRule> mArgs;
    private final ViewVisitor.CycleDetector mCycleDetector;
    private final String mName;
    private final ViewVisitor.OnLayoutErrorListener mOnLayoutErrorListener;
    private final WeakHashMap<View, int[]> mOriginalValues = new WeakHashMap();
    
    public LayoutUpdateVisitor(List<Pathfinder.PathElement> paramList, List<ViewVisitor.LayoutRule> paramList1, String paramString, ViewVisitor.OnLayoutErrorListener paramOnLayoutErrorListener)
    {
      super();
      this.mArgs = paramList1;
      this.mName = paramString;
      this.mAlive = true;
      this.mOnLayoutErrorListener = paramOnLayoutErrorListener;
      this.mCycleDetector = new ViewVisitor.CycleDetector(null);
    }
    
    private boolean verifyLayout(Set<Integer> paramSet, SparseArray<View> paramSparseArray)
    {
      TreeMap localTreeMap = new TreeMap(new Comparator()
      {
        public int compare(View paramAnonymousView1, View paramAnonymousView2)
        {
          if (paramAnonymousView1 == paramAnonymousView2) {
            return 0;
          }
          if (paramAnonymousView1 == null) {
            return -1;
          }
          if (paramAnonymousView2 == null) {
            return 1;
          }
          return paramAnonymousView2.hashCode() - paramAnonymousView1.hashCode();
        }
      });
      int j = paramSparseArray.size();
      int i = 0;
      while (i < j)
      {
        View localView = (View)paramSparseArray.valueAt(i);
        int[] arrayOfInt = ((RelativeLayout.LayoutParams)localView.getLayoutParams()).getRules();
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = paramSet.iterator();
        while (localIterator.hasNext())
        {
          int k = arrayOfInt[((Integer)localIterator.next()).intValue()];
          if ((k > 0) && (k != localView.getId())) {
            localArrayList.add(paramSparseArray.get(k));
          }
        }
        localTreeMap.put(localView, localArrayList);
        i += 1;
      }
      return this.mCycleDetector.hasCycle(localTreeMap);
    }
    
    public void accumulate(View paramView)
    {
      paramView = (ViewGroup)paramView;
      SparseArray localSparseArray = new SparseArray();
      int j = paramView.getChildCount();
      int i = 0;
      View localView;
      while (i < j)
      {
        localView = paramView.getChildAt(i);
        int k = localView.getId();
        if (k > 0) {
          localSparseArray.put(k, localView);
        }
        i += 1;
      }
      j = this.mArgs.size();
      i = 0;
      if (i < j)
      {
        paramView = (ViewVisitor.LayoutRule)this.mArgs.get(i);
        localView = (View)localSparseArray.get(paramView.viewId);
        if (localView != null) {}
      }
      for (;;)
      {
        i += 1;
        break;
        RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)localView.getLayoutParams();
        int[] arrayOfInt = (int[])localLayoutParams.getRules().clone();
        if (arrayOfInt[paramView.verb] != paramView.anchor)
        {
          if (this.mOriginalValues.containsKey(localView))
          {
            localLayoutParams.addRule(paramView.verb, paramView.anchor);
            if (!mHorizontalRules.contains(Integer.valueOf(paramView.verb))) {
              break label258;
            }
            paramView = mHorizontalRules;
          }
          for (;;)
          {
            if ((paramView == null) || (verifyLayout(paramView, localSparseArray))) {
              break label288;
            }
            cleanup();
            this.mOnLayoutErrorListener.onLayoutError(new ViewVisitor.LayoutErrorMessage("circular_dependency", this.mName));
            return;
            this.mOriginalValues.put(localView, arrayOfInt);
            break;
            label258:
            if (mVerticalRules.contains(Integer.valueOf(paramView.verb))) {
              paramView = mVerticalRules;
            } else {
              paramView = null;
            }
          }
          label288:
          localView.setLayoutParams(localLayoutParams);
        }
      }
    }
    
    public void cleanup()
    {
      Iterator localIterator = this.mOriginalValues.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Object localObject = (Map.Entry)localIterator.next();
        View localView = (View)((Map.Entry)localObject).getKey();
        localObject = (int[])((Map.Entry)localObject).getValue();
        RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)localView.getLayoutParams();
        int i = 0;
        while (i < localObject.length)
        {
          localLayoutParams.addRule(i, localObject[i]);
          i += 1;
        }
        localView.setLayoutParams(localLayoutParams);
      }
      this.mAlive = false;
    }
    
    protected String name()
    {
      return "Layout Update";
    }
    
    public void visit(View paramView)
    {
      if (this.mAlive) {
        getPathfinder().findTargetsInRoot(paramView, getPath(), this);
      }
    }
  }
  
  public static abstract interface OnEventListener
  {
    public abstract void OnEvent(View paramView, String paramString, boolean paramBoolean);
  }
  
  public static abstract interface OnLayoutErrorListener
  {
    public abstract void onLayoutError(ViewVisitor.LayoutErrorMessage paramLayoutErrorMessage);
  }
  
  public static class PropertySetVisitor
    extends ViewVisitor
  {
    private final Caller mAccessor;
    private final Caller mMutator;
    private final Object[] mOriginalValueHolder;
    private final WeakHashMap<View, Object> mOriginalValues;
    
    public PropertySetVisitor(List<Pathfinder.PathElement> paramList, Caller paramCaller1, Caller paramCaller2)
    {
      super();
      this.mMutator = paramCaller1;
      this.mAccessor = paramCaller2;
      this.mOriginalValueHolder = new Object[1];
      this.mOriginalValues = new WeakHashMap();
    }
    
    public void accumulate(View paramView)
    {
      Object localObject;
      Bitmap localBitmap1;
      if (this.mAccessor != null)
      {
        localObject = this.mMutator.getArgs();
        if (1 == localObject.length)
        {
          localBitmap1 = localObject[0];
          localObject = this.mAccessor.applyMethod(paramView);
          if (localBitmap1 == localObject) {}
          do
          {
            return;
            if (localBitmap1 == null) {
              break;
            }
            if ((!(localBitmap1 instanceof Bitmap)) || (!(localObject instanceof Bitmap))) {
              break label107;
            }
          } while (((Bitmap)localBitmap1).sameAs((Bitmap)localObject));
          if ((!(localObject instanceof Bitmap)) && (!(localObject instanceof BitmapDrawable)) && (!this.mOriginalValues.containsKey(paramView))) {
            break label161;
          }
        }
      }
      for (;;)
      {
        this.mMutator.applyMethod(paramView);
        return;
        label107:
        if (((localBitmap1 instanceof BitmapDrawable)) && ((localObject instanceof BitmapDrawable)))
        {
          localBitmap1 = ((BitmapDrawable)localBitmap1).getBitmap();
          Bitmap localBitmap2 = ((BitmapDrawable)localObject).getBitmap();
          if ((localBitmap1 == null) || (!localBitmap1.sameAs(localBitmap2))) {
            break;
          }
          return;
        }
        if (!localBitmap1.equals(localObject)) {
          break;
        }
        return;
        label161:
        this.mOriginalValueHolder[0] = localObject;
        if (this.mMutator.argsAreApplicable(this.mOriginalValueHolder)) {
          this.mOriginalValues.put(paramView, localObject);
        } else {
          this.mOriginalValues.put(paramView, null);
        }
      }
    }
    
    public void cleanup()
    {
      Iterator localIterator = this.mOriginalValues.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Object localObject = (Map.Entry)localIterator.next();
        View localView = (View)((Map.Entry)localObject).getKey();
        localObject = ((Map.Entry)localObject).getValue();
        if (localObject != null)
        {
          this.mOriginalValueHolder[0] = localObject;
          this.mMutator.applyMethodWithArguments(localView, this.mOriginalValueHolder);
        }
      }
    }
    
    protected String name()
    {
      return "Property Mutator";
    }
  }
  
  public static class ViewDetectorVisitor
    extends ViewVisitor.EventTriggeringVisitor
  {
    private boolean mSeen = false;
    
    public ViewDetectorVisitor(List<Pathfinder.PathElement> paramList, String paramString, ViewVisitor.OnEventListener paramOnEventListener)
    {
      super(paramString, paramOnEventListener, false);
    }
    
    public void accumulate(View paramView)
    {
      if ((paramView != null) && (!this.mSeen)) {
        fireEvent(paramView);
      }
      if (paramView != null) {}
      for (boolean bool = true;; bool = false)
      {
        this.mSeen = bool;
        return;
      }
    }
    
    public void cleanup() {}
    
    protected String name()
    {
      return getEventName() + " when Detected";
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/viewcrawler/ViewVisitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
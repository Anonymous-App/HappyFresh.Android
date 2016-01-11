package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.Arrays;

public class GridLayoutManager
  extends LinearLayoutManager
{
  private static final boolean DEBUG = false;
  public static final int DEFAULT_SPAN_COUNT = -1;
  static final int MAIN_DIR_SPEC = View.MeasureSpec.makeMeasureSpec(0, 0);
  private static final String TAG = "GridLayoutManager";
  int[] mCachedBorders;
  final Rect mDecorInsets = new Rect();
  boolean mPendingSpanCountChange = false;
  final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
  final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
  View[] mSet;
  int mSpanCount = -1;
  SpanSizeLookup mSpanSizeLookup = new DefaultSpanSizeLookup();
  
  public GridLayoutManager(Context paramContext, int paramInt)
  {
    super(paramContext);
    setSpanCount(paramInt);
  }
  
  public GridLayoutManager(Context paramContext, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    super(paramContext, paramInt2, paramBoolean);
    setSpanCount(paramInt1);
  }
  
  public GridLayoutManager(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setSpanCount(getProperties(paramContext, paramAttributeSet, paramInt1, paramInt2).spanCount);
  }
  
  private void assignSpans(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int j;
    int i;
    int k;
    label43:
    LayoutParams localLayoutParams;
    if (paramBoolean)
    {
      paramInt2 = 0;
      j = paramInt1;
      i = 1;
      paramInt1 = paramInt2;
      if ((this.mOrientation != 1) || (!isLayoutRTL())) {
        break label150;
      }
      paramInt2 = this.mSpanCount - 1;
      k = -1;
      if (paramInt1 == j) {
        return;
      }
      View localView = this.mSet[paramInt1];
      localLayoutParams = (LayoutParams)localView.getLayoutParams();
      LayoutParams.access$102(localLayoutParams, getSpanSize(paramRecycler, paramState, getPosition(localView)));
      if ((k != -1) || (localLayoutParams.mSpanSize <= 1)) {
        break label159;
      }
      LayoutParams.access$002(localLayoutParams, paramInt2 - (localLayoutParams.mSpanSize - 1));
    }
    for (;;)
    {
      paramInt2 += localLayoutParams.mSpanSize * k;
      paramInt1 += i;
      break label43;
      paramInt1 -= 1;
      j = -1;
      i = -1;
      break;
      label150:
      paramInt2 = 0;
      k = 1;
      break label43;
      label159:
      LayoutParams.access$002(localLayoutParams, paramInt2);
    }
  }
  
  private void cachePreLayoutSpanMapping()
  {
    int j = getChildCount();
    int i = 0;
    while (i < j)
    {
      LayoutParams localLayoutParams = (LayoutParams)getChildAt(i).getLayoutParams();
      int k = localLayoutParams.getViewLayoutPosition();
      this.mPreLayoutSpanSizeCache.put(k, localLayoutParams.getSpanSize());
      this.mPreLayoutSpanIndexCache.put(k, localLayoutParams.getSpanIndex());
      i += 1;
    }
  }
  
  private void calculateItemBorders(int paramInt)
  {
    if ((this.mCachedBorders == null) || (this.mCachedBorders.length != this.mSpanCount + 1) || (this.mCachedBorders[(this.mCachedBorders.length - 1)] != paramInt)) {
      this.mCachedBorders = new int[this.mSpanCount + 1];
    }
    this.mCachedBorders[0] = 0;
    int n = paramInt / this.mSpanCount;
    int i2 = paramInt % this.mSpanCount;
    int j = 0;
    paramInt = 0;
    int i = 1;
    while (i <= this.mSpanCount)
    {
      int k = n;
      int i1 = paramInt + i2;
      paramInt = i1;
      int m = k;
      if (i1 > 0)
      {
        paramInt = i1;
        m = k;
        if (this.mSpanCount - i1 < i2)
        {
          m = k + 1;
          paramInt = i1 - this.mSpanCount;
        }
      }
      j += m;
      this.mCachedBorders[i] = j;
      i += 1;
    }
  }
  
  private void clearPreLayoutSpanMappingCache()
  {
    this.mPreLayoutSpanSizeCache.clear();
    this.mPreLayoutSpanIndexCache.clear();
  }
  
  private void ensureAnchorIsInFirstSpan(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.AnchorInfo paramAnchorInfo)
  {
    for (int i = getSpanIndex(paramRecycler, paramState, paramAnchorInfo.mPosition); (i > 0) && (paramAnchorInfo.mPosition > 0); i = getSpanIndex(paramRecycler, paramState, paramAnchorInfo.mPosition)) {
      paramAnchorInfo.mPosition -= 1;
    }
  }
  
  private void ensureViewSet()
  {
    if ((this.mSet == null) || (this.mSet.length != this.mSpanCount)) {
      this.mSet = new View[this.mSpanCount];
    }
  }
  
  private int getMainDirSpec(int paramInt)
  {
    if (paramInt < 0) {
      return MAIN_DIR_SPEC;
    }
    return View.MeasureSpec.makeMeasureSpec(paramInt, 1073741824);
  }
  
  private int getSpanGroupIndex(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    if (!paramState.isPreLayout()) {
      return this.mSpanSizeLookup.getSpanGroupIndex(paramInt, this.mSpanCount);
    }
    int i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1)
    {
      Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + paramInt);
      return 0;
    }
    return this.mSpanSizeLookup.getSpanGroupIndex(i, this.mSpanCount);
  }
  
  private int getSpanIndex(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    if (!paramState.isPreLayout()) {
      i = this.mSpanSizeLookup.getCachedSpanIndex(paramInt, this.mSpanCount);
    }
    int j;
    do
    {
      return i;
      j = this.mPreLayoutSpanIndexCache.get(paramInt, -1);
      i = j;
    } while (j != -1);
    int i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1)
    {
      Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + paramInt);
      return 0;
    }
    return this.mSpanSizeLookup.getCachedSpanIndex(i, this.mSpanCount);
  }
  
  private int getSpanSize(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    if (!paramState.isPreLayout()) {
      i = this.mSpanSizeLookup.getSpanSize(paramInt);
    }
    int j;
    do
    {
      return i;
      j = this.mPreLayoutSpanSizeCache.get(paramInt, -1);
      i = j;
    } while (j != -1);
    int i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1)
    {
      Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + paramInt);
      return 1;
    }
    return this.mSpanSizeLookup.getSpanSize(i);
  }
  
  private void measureChildWithDecorationsAndMargin(View paramView, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    calculateItemDecorationsForChild(paramView, this.mDecorInsets);
    RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
    int i;
    if (!paramBoolean)
    {
      i = paramInt1;
      if (this.mOrientation != 1) {}
    }
    else
    {
      i = updateSpecWithExtra(paramInt1, localLayoutParams.leftMargin + this.mDecorInsets.left, localLayoutParams.rightMargin + this.mDecorInsets.right);
    }
    if (!paramBoolean)
    {
      paramInt1 = paramInt2;
      if (this.mOrientation != 0) {}
    }
    else
    {
      paramInt1 = updateSpecWithExtra(paramInt2, localLayoutParams.topMargin + this.mDecorInsets.top, localLayoutParams.bottomMargin + this.mDecorInsets.bottom);
    }
    paramView.measure(i, paramInt1);
  }
  
  private void updateMeasurements()
  {
    if (getOrientation() == 1) {}
    for (int i = getWidth() - getPaddingRight() - getPaddingLeft();; i = getHeight() - getPaddingBottom() - getPaddingTop())
    {
      calculateItemBorders(i);
      return;
    }
  }
  
  private int updateSpecWithExtra(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 == 0) && (paramInt3 == 0)) {}
    int i;
    do
    {
      return paramInt1;
      i = View.MeasureSpec.getMode(paramInt1);
    } while ((i != Integer.MIN_VALUE) && (i != 1073741824));
    return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt1) - paramInt2 - paramInt3, i);
  }
  
  public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  View findReferenceChild(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2, int paramInt3)
  {
    ensureLayoutState();
    Object localObject2 = null;
    Object localObject1 = null;
    int j = this.mOrientationHelper.getStartAfterPadding();
    int k = this.mOrientationHelper.getEndAfterPadding();
    int i;
    View localView;
    Object localObject3;
    Object localObject4;
    if (paramInt2 > paramInt1)
    {
      i = 1;
      if (paramInt1 == paramInt2) {
        break label221;
      }
      localView = getChildAt(paramInt1);
      int m = getPosition(localView);
      localObject3 = localObject2;
      localObject4 = localObject1;
      if (m >= 0)
      {
        localObject3 = localObject2;
        localObject4 = localObject1;
        if (m < paramInt3)
        {
          if (getSpanIndex(paramRecycler, paramState, m) == 0) {
            break label127;
          }
          localObject4 = localObject1;
          localObject3 = localObject2;
        }
      }
    }
    for (;;)
    {
      paramInt1 += i;
      localObject2 = localObject3;
      localObject1 = localObject4;
      break;
      i = -1;
      break;
      label127:
      if (((RecyclerView.LayoutParams)localView.getLayoutParams()).isItemRemoved())
      {
        localObject3 = localObject2;
        localObject4 = localObject1;
        if (localObject2 == null)
        {
          localObject3 = localView;
          localObject4 = localObject1;
        }
      }
      else
      {
        if (this.mOrientationHelper.getDecoratedStart(localView) < k)
        {
          localObject3 = localView;
          if (this.mOrientationHelper.getDecoratedEnd(localView) >= j) {
            break label230;
          }
        }
        localObject3 = localObject2;
        localObject4 = localObject1;
        if (localObject1 == null)
        {
          localObject3 = localObject2;
          localObject4 = localView;
        }
      }
    }
    label221:
    if (localObject1 != null) {}
    for (;;)
    {
      localObject3 = localObject1;
      label230:
      return (View)localObject3;
      localObject1 = localObject2;
    }
  }
  
  public RecyclerView.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2);
  }
  
  public RecyclerView.LayoutParams generateLayoutParams(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new LayoutParams(paramContext, paramAttributeSet);
  }
  
  public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new LayoutParams(paramLayoutParams);
  }
  
  public int getColumnCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 1) {
      return this.mSpanCount;
    }
    if (paramState.getItemCount() < 1) {
      return 0;
    }
    return getSpanGroupIndex(paramRecycler, paramState, paramState.getItemCount() - 1);
  }
  
  public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 0) {
      return this.mSpanCount;
    }
    if (paramState.getItemCount() < 1) {
      return 0;
    }
    return getSpanGroupIndex(paramRecycler, paramState, paramState.getItemCount() - 1);
  }
  
  public int getSpanCount()
  {
    return this.mSpanCount;
  }
  
  public SpanSizeLookup getSpanSizeLookup()
  {
    return this.mSpanSizeLookup;
  }
  
  void layoutChunk(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.LayoutState paramLayoutState, LinearLayoutManager.LayoutChunkResult paramLayoutChunkResult)
  {
    boolean bool;
    int n;
    if (paramLayoutState.mItemDirection == 1)
    {
      bool = true;
      k = 0;
      m = 0;
      i = this.mSpanCount;
      n = k;
      j = m;
      if (!bool)
      {
        i = getSpanIndex(paramRecycler, paramState, paramLayoutState.mCurrentPosition) + getSpanSize(paramRecycler, paramState, paramLayoutState.mCurrentPosition);
        j = m;
        n = k;
      }
    }
    for (;;)
    {
      if ((n < this.mSpanCount) && (paramLayoutState.hasMore(paramState)) && (i > 0))
      {
        m = paramLayoutState.mCurrentPosition;
        k = getSpanSize(paramRecycler, paramState, m);
        if (k > this.mSpanCount)
        {
          throw new IllegalArgumentException("Item at position " + m + " requires " + k + " spans but GridLayoutManager has only " + this.mSpanCount + " spans.");
          bool = false;
          break;
        }
        i -= k;
        if (i >= 0) {
          break label203;
        }
      }
      label203:
      View localView;
      do
      {
        if (n != 0) {
          break;
        }
        paramLayoutChunkResult.mFinished = true;
        return;
        localView = paramLayoutState.next(paramRecycler);
      } while (localView == null);
      j += k;
      this.mSet[n] = localView;
      n += 1;
    }
    int i = 0;
    assignSpans(paramRecycler, paramState, n, j, bool);
    int j = 0;
    if (j < n)
    {
      paramRecycler = this.mSet[j];
      if (paramLayoutState.mScrapList == null) {
        if (bool)
        {
          addView(paramRecycler);
          label290:
          paramState = (LayoutParams)paramRecycler.getLayoutParams();
          k = View.MeasureSpec.makeMeasureSpec(this.mCachedBorders[(paramState.mSpanIndex + paramState.mSpanSize)] - this.mCachedBorders[paramState.mSpanIndex], 1073741824);
          if (this.mOrientation != 1) {
            break label422;
          }
          measureChildWithDecorationsAndMargin(paramRecycler, k, getMainDirSpec(paramState.height), false);
        }
      }
      for (;;)
      {
        m = this.mOrientationHelper.getDecoratedMeasurement(paramRecycler);
        k = i;
        if (m > i) {
          k = m;
        }
        j += 1;
        i = k;
        break;
        addView(paramRecycler, 0);
        break label290;
        if (bool)
        {
          addDisappearingView(paramRecycler);
          break label290;
        }
        addDisappearingView(paramRecycler, 0);
        break label290;
        label422:
        measureChildWithDecorationsAndMargin(paramRecycler, getMainDirSpec(paramState.width), k, false);
      }
    }
    int k = getMainDirSpec(i);
    j = 0;
    if (j < n)
    {
      paramRecycler = this.mSet[j];
      if (this.mOrientationHelper.getDecoratedMeasurement(paramRecycler) != i)
      {
        paramState = (LayoutParams)paramRecycler.getLayoutParams();
        m = View.MeasureSpec.makeMeasureSpec(this.mCachedBorders[(paramState.mSpanIndex + paramState.mSpanSize)] - this.mCachedBorders[paramState.mSpanIndex], 1073741824);
        if (this.mOrientation != 1) {
          break label546;
        }
        measureChildWithDecorationsAndMargin(paramRecycler, m, k, true);
      }
      for (;;)
      {
        j += 1;
        break;
        label546:
        measureChildWithDecorationsAndMargin(paramRecycler, k, m, true);
      }
    }
    paramLayoutChunkResult.mConsumed = i;
    j = 0;
    k = 0;
    int m = 0;
    int i1 = 0;
    if (this.mOrientation == 1) {
      if (paramLayoutState.mLayoutDirection == -1)
      {
        i1 = paramLayoutState.mOffset;
        m = i1 - i;
        i = i1;
        int i2 = 0;
        i1 = m;
        m = j;
        j = i2;
        label626:
        if (j >= n) {
          break label859;
        }
        paramRecycler = this.mSet[j];
        paramState = (LayoutParams)paramRecycler.getLayoutParams();
        if (this.mOrientation != 1) {
          break label827;
        }
        m = getPaddingLeft() + this.mCachedBorders[paramState.mSpanIndex];
        k = m + this.mOrientationHelper.getDecoratedMeasurementInOther(paramRecycler);
      }
    }
    for (;;)
    {
      layoutDecorated(paramRecycler, m + paramState.leftMargin, i1 + paramState.topMargin, k - paramState.rightMargin, i - paramState.bottomMargin);
      if ((paramState.isItemRemoved()) || (paramState.isItemChanged())) {
        paramLayoutChunkResult.mIgnoreConsumed = true;
      }
      paramLayoutChunkResult.mFocusable |= paramRecycler.isFocusable();
      j += 1;
      break label626;
      m = paramLayoutState.mOffset;
      i = m + i;
      break;
      if (paramLayoutState.mLayoutDirection == -1)
      {
        k = paramLayoutState.mOffset;
        j = k - i;
        i = i1;
        break;
      }
      j = paramLayoutState.mOffset;
      k = j + i;
      i = i1;
      break;
      label827:
      i1 = getPaddingTop() + this.mCachedBorders[paramState.mSpanIndex];
      i = i1 + this.mOrientationHelper.getDecoratedMeasurementInOther(paramRecycler);
    }
    label859:
    Arrays.fill(this.mSet, null);
  }
  
  void onAnchorReady(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.AnchorInfo paramAnchorInfo)
  {
    super.onAnchorReady(paramRecycler, paramState, paramAnchorInfo);
    updateMeasurements();
    if ((paramState.getItemCount() > 0) && (!paramState.isPreLayout())) {
      ensureAnchorIsInFirstSpan(paramRecycler, paramState, paramAnchorInfo);
    }
    ensureViewSet();
  }
  
  public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    if (!(localLayoutParams instanceof LayoutParams))
    {
      super.onInitializeAccessibilityNodeInfoForItem(paramView, paramAccessibilityNodeInfoCompat);
      return;
    }
    paramView = (LayoutParams)localLayoutParams;
    int i = getSpanGroupIndex(paramRecycler, paramState, paramView.getViewLayoutPosition());
    if (this.mOrientation == 0)
    {
      j = paramView.getSpanIndex();
      k = paramView.getSpanSize();
      if ((this.mSpanCount > 1) && (paramView.getSpanSize() == this.mSpanCount)) {}
      for (bool = true;; bool = false)
      {
        paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(j, k, i, 1, bool, false));
        return;
      }
    }
    int j = paramView.getSpanIndex();
    int k = paramView.getSpanSize();
    if ((this.mSpanCount > 1) && (paramView.getSpanSize() == this.mSpanCount)) {}
    for (boolean bool = true;; bool = false)
    {
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i, 1, j, k, bool, false));
      return;
    }
  }
  
  public void onItemsAdded(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onItemsChanged(RecyclerView paramRecyclerView)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onItemsMoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onItemsRemoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, Object paramObject)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (paramState.isPreLayout()) {
      cachePreLayoutSpanMapping();
    }
    super.onLayoutChildren(paramRecycler, paramState);
    clearPreLayoutSpanMappingCache();
    if (!paramState.isPreLayout()) {
      this.mPendingSpanCountChange = false;
    }
  }
  
  public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    updateMeasurements();
    ensureViewSet();
    return super.scrollHorizontallyBy(paramInt, paramRecycler, paramState);
  }
  
  public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    updateMeasurements();
    ensureViewSet();
    return super.scrollVerticallyBy(paramInt, paramRecycler, paramState);
  }
  
  public void setSpanCount(int paramInt)
  {
    if (paramInt == this.mSpanCount) {
      return;
    }
    this.mPendingSpanCountChange = true;
    if (paramInt < 1) {
      throw new IllegalArgumentException("Span count should be at least 1. Provided " + paramInt);
    }
    this.mSpanCount = paramInt;
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void setSpanSizeLookup(SpanSizeLookup paramSpanSizeLookup)
  {
    this.mSpanSizeLookup = paramSpanSizeLookup;
  }
  
  public void setStackFromEnd(boolean paramBoolean)
  {
    if (paramBoolean) {
      throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
    }
    super.setStackFromEnd(false);
  }
  
  public boolean supportsPredictiveItemAnimations()
  {
    return (this.mPendingSavedState == null) && (!this.mPendingSpanCountChange);
  }
  
  public static final class DefaultSpanSizeLookup
    extends GridLayoutManager.SpanSizeLookup
  {
    public int getSpanIndex(int paramInt1, int paramInt2)
    {
      return paramInt1 % paramInt2;
    }
    
    public int getSpanSize(int paramInt)
    {
      return 1;
    }
  }
  
  public static class LayoutParams
    extends RecyclerView.LayoutParams
  {
    public static final int INVALID_SPAN_ID = -1;
    private int mSpanIndex = -1;
    private int mSpanSize = 0;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(RecyclerView.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
    
    public int getSpanIndex()
    {
      return this.mSpanIndex;
    }
    
    public int getSpanSize()
    {
      return this.mSpanSize;
    }
  }
  
  public static abstract class SpanSizeLookup
  {
    private boolean mCacheSpanIndices = false;
    final SparseIntArray mSpanIndexCache = new SparseIntArray();
    
    int findReferenceIndexFromCache(int paramInt)
    {
      int i = 0;
      int j = this.mSpanIndexCache.size() - 1;
      while (i <= j)
      {
        int k = i + j >>> 1;
        if (this.mSpanIndexCache.keyAt(k) < paramInt) {
          i = k + 1;
        } else {
          j = k - 1;
        }
      }
      paramInt = i - 1;
      if ((paramInt >= 0) && (paramInt < this.mSpanIndexCache.size())) {
        return this.mSpanIndexCache.keyAt(paramInt);
      }
      return -1;
    }
    
    int getCachedSpanIndex(int paramInt1, int paramInt2)
    {
      int i;
      if (!this.mCacheSpanIndices) {
        i = getSpanIndex(paramInt1, paramInt2);
      }
      int j;
      do
      {
        return i;
        j = this.mSpanIndexCache.get(paramInt1, -1);
        i = j;
      } while (j != -1);
      paramInt2 = getSpanIndex(paramInt1, paramInt2);
      this.mSpanIndexCache.put(paramInt1, paramInt2);
      return paramInt2;
    }
    
    public int getSpanGroupIndex(int paramInt1, int paramInt2)
    {
      int i = 0;
      int j = 0;
      int i2 = getSpanSize(paramInt1);
      int m = 0;
      if (m < paramInt1)
      {
        int n = getSpanSize(m);
        int i1 = i + n;
        int k;
        if (i1 == paramInt2)
        {
          i = 0;
          k = j + 1;
        }
        for (;;)
        {
          m += 1;
          j = k;
          break;
          k = j;
          i = i1;
          if (i1 > paramInt2)
          {
            i = n;
            k = j + 1;
          }
        }
      }
      paramInt1 = j;
      if (i + i2 > paramInt2) {
        paramInt1 = j + 1;
      }
      return paramInt1;
    }
    
    public int getSpanIndex(int paramInt1, int paramInt2)
    {
      int n = getSpanSize(paramInt1);
      if (n == paramInt2) {
        paramInt1 = 0;
      }
      int i;
      do
      {
        return paramInt1;
        int k = 0;
        int m = 0;
        i = k;
        int j = m;
        if (this.mCacheSpanIndices)
        {
          i = k;
          j = m;
          if (this.mSpanIndexCache.size() > 0)
          {
            int i1 = findReferenceIndexFromCache(paramInt1);
            i = k;
            j = m;
            if (i1 >= 0)
            {
              i = this.mSpanIndexCache.get(i1) + getSpanSize(i1);
              j = i1 + 1;
            }
          }
        }
        if (j < paramInt1)
        {
          k = getSpanSize(j);
          m = i + k;
          if (m == paramInt2) {
            i = 0;
          }
          for (;;)
          {
            j += 1;
            break;
            i = m;
            if (m > paramInt2) {
              i = k;
            }
          }
        }
        paramInt1 = i;
      } while (i + n <= paramInt2);
      return 0;
    }
    
    public abstract int getSpanSize(int paramInt);
    
    public void invalidateSpanIndexCache()
    {
      this.mSpanIndexCache.clear();
    }
    
    public boolean isSpanIndexCacheEnabled()
    {
      return this.mCacheSpanIndices;
    }
    
    public void setSpanIndexCacheEnabled(boolean paramBoolean)
    {
      this.mCacheSpanIndices = paramBoolean;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/android/support/v7/widget/GridLayoutManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
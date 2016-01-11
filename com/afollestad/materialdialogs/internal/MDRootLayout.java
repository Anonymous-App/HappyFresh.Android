package com.afollestad.materialdialogs.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ScrollView;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog.NotImplementedException;
import com.afollestad.materialdialogs.R.attr;
import com.afollestad.materialdialogs.R.dimen;
import com.afollestad.materialdialogs.R.id;
import com.afollestad.materialdialogs.R.styleable;
import com.afollestad.materialdialogs.util.DialogUtils;

public class MDRootLayout
  extends ViewGroup
{
  private static final int INDEX_NEGATIVE = 1;
  private static final int INDEX_NEUTRAL = 0;
  private static final int INDEX_POSITIVE = 2;
  private ViewTreeObserver.OnScrollChangedListener mBottomOnScrollChangedListener;
  private int mButtonBarHeight;
  private GravityEnum mButtonGravity = GravityEnum.START;
  private int mButtonHorizontalEdgeMargin;
  private int mButtonPaddingFull;
  private final MDButton[] mButtons = new MDButton[3];
  private View mContent;
  private Paint mDividerPaint;
  private int mDividerWidth;
  private boolean mDrawBottomDivider = false;
  private boolean mDrawTopDivider = false;
  private boolean mForceStack = false;
  private boolean mIsStacked = false;
  private boolean mNoTitleNoPadding;
  private int mNoTitlePaddingFull;
  private boolean mReducePaddingNoTitleNoButtons;
  private View mTitleBar;
  private ViewTreeObserver.OnScrollChangedListener mTopOnScrollChangedListener;
  private boolean mUseFullPadding = true;
  
  public MDRootLayout(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }
  
  public MDRootLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }
  
  @TargetApi(11)
  public MDRootLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }
  
  @TargetApi(21)
  public MDRootLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    init(paramContext, paramAttributeSet, paramInt1);
  }
  
  private void addScrollListener(final ViewGroup paramViewGroup, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    ViewTreeObserver.OnScrollChangedListener local2;
    if (((!paramBoolean2) && (this.mTopOnScrollChangedListener == null)) || ((paramBoolean2) && (this.mBottomOnScrollChangedListener == null)))
    {
      local2 = new ViewTreeObserver.OnScrollChangedListener()
      {
        public void onScrollChanged()
        {
          boolean bool2 = false;
          MDButton[] arrayOfMDButton = MDRootLayout.this.mButtons;
          int j = arrayOfMDButton.length;
          int i = 0;
          boolean bool1 = bool2;
          if (i < j)
          {
            MDButton localMDButton = arrayOfMDButton[i];
            if ((localMDButton != null) && (localMDButton.getVisibility() != 8)) {
              bool1 = true;
            }
          }
          else
          {
            if (!(paramViewGroup instanceof WebView)) {
              break label97;
            }
            MDRootLayout.this.invalidateDividersForWebView((WebView)paramViewGroup, paramBoolean1, paramBoolean2, bool1);
          }
          for (;;)
          {
            MDRootLayout.this.invalidate();
            return;
            i += 1;
            break;
            label97:
            MDRootLayout.this.invalidateDividersForScrollingView(paramViewGroup, paramBoolean1, paramBoolean2, bool1);
          }
        }
      };
      if (paramBoolean2) {
        break label64;
      }
      this.mTopOnScrollChangedListener = local2;
      paramViewGroup.getViewTreeObserver().addOnScrollChangedListener(this.mTopOnScrollChangedListener);
    }
    for (;;)
    {
      local2.onScrollChanged();
      return;
      label64:
      this.mBottomOnScrollChangedListener = local2;
      paramViewGroup.getViewTreeObserver().addOnScrollChangedListener(this.mBottomOnScrollChangedListener);
    }
  }
  
  private static boolean canAdapterViewScroll(AdapterView paramAdapterView)
  {
    boolean bool2 = true;
    boolean bool1;
    if (paramAdapterView.getLastVisiblePosition() == -1)
    {
      bool1 = false;
      return bool1;
    }
    int i;
    if (paramAdapterView.getFirstVisiblePosition() == 0)
    {
      i = 1;
      label24:
      if (paramAdapterView.getLastVisiblePosition() != paramAdapterView.getCount() - 1) {
        break label116;
      }
    }
    label116:
    for (int j = 1;; j = 0)
    {
      bool1 = bool2;
      if (i == 0) {
        break;
      }
      bool1 = bool2;
      if (j == 0) {
        break;
      }
      bool1 = bool2;
      if (paramAdapterView.getChildCount() <= 0) {
        break;
      }
      bool1 = bool2;
      if (paramAdapterView.getChildAt(0).getTop() < paramAdapterView.getPaddingTop()) {
        break;
      }
      bool1 = bool2;
      if (paramAdapterView.getChildAt(paramAdapterView.getChildCount() - 1).getBottom() > paramAdapterView.getHeight() - paramAdapterView.getPaddingBottom()) {
        break;
      }
      return false;
      i = 0;
      break label24;
    }
  }
  
  public static boolean canRecyclerViewScroll(RecyclerView paramRecyclerView)
  {
    if ((paramRecyclerView == null) || (paramRecyclerView.getAdapter() == null) || (paramRecyclerView.getLayoutManager() == null)) {}
    for (;;)
    {
      return false;
      RecyclerView.LayoutManager localLayoutManager = paramRecyclerView.getLayoutManager();
      int i = paramRecyclerView.getAdapter().getItemCount();
      if ((localLayoutManager instanceof LinearLayoutManager))
      {
        int j = ((LinearLayoutManager)localLayoutManager).findLastVisibleItemPosition();
        if (j == -1) {
          continue;
        }
        if (j != i - 1) {
          break label110;
        }
      }
      label110:
      for (i = 1; (i == 0) || ((paramRecyclerView.getChildCount() > 0) && (paramRecyclerView.getChildAt(paramRecyclerView.getChildCount() - 1).getBottom() > paramRecyclerView.getHeight() - paramRecyclerView.getPaddingBottom())); i = 0)
      {
        return true;
        throw new MaterialDialog.NotImplementedException("Material Dialogs currently only supports LinearLayoutManager. Please report any new layout managers.");
      }
    }
  }
  
  private static boolean canScrollViewScroll(ScrollView paramScrollView)
  {
    if (paramScrollView.getChildCount() == 0) {}
    int i;
    do
    {
      return false;
      i = paramScrollView.getChildAt(0).getMeasuredHeight();
    } while (paramScrollView.getMeasuredHeight() - paramScrollView.getPaddingTop() - paramScrollView.getPaddingBottom() >= i);
    return true;
  }
  
  private static boolean canWebViewScroll(WebView paramWebView)
  {
    return paramWebView.getMeasuredHeight() < paramWebView.getContentHeight() * paramWebView.getScale();
  }
  
  @Nullable
  private static View getBottomView(ViewGroup paramViewGroup)
  {
    if ((paramViewGroup == null) || (paramViewGroup.getChildCount() == 0)) {}
    for (;;)
    {
      return null;
      int i = paramViewGroup.getChildCount() - 1;
      while (i >= 0)
      {
        View localView = paramViewGroup.getChildAt(i);
        if ((localView.getVisibility() == 0) && (localView.getBottom() == paramViewGroup.getMeasuredHeight())) {
          return localView;
        }
        i -= 1;
      }
    }
  }
  
  @Nullable
  private static View getTopView(ViewGroup paramViewGroup)
  {
    if ((paramViewGroup == null) || (paramViewGroup.getChildCount() == 0)) {}
    for (;;)
    {
      return null;
      int i = paramViewGroup.getChildCount() - 1;
      while (i >= 0)
      {
        View localView = paramViewGroup.getChildAt(i);
        if ((localView.getVisibility() == 0) && (localView.getTop() == 0)) {
          return localView;
        }
        i -= 1;
      }
    }
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MDRootLayout, paramInt, 0);
    this.mReducePaddingNoTitleNoButtons = paramAttributeSet.getBoolean(R.styleable.MDRootLayout_md_reduce_padding_no_title_no_buttons, true);
    paramAttributeSet.recycle();
    this.mNoTitlePaddingFull = localResources.getDimensionPixelSize(R.dimen.md_notitle_vertical_padding);
    this.mButtonPaddingFull = localResources.getDimensionPixelSize(R.dimen.md_button_frame_vertical_padding);
    this.mButtonHorizontalEdgeMargin = localResources.getDimensionPixelSize(R.dimen.md_button_padding_frame_side);
    this.mButtonBarHeight = localResources.getDimensionPixelSize(R.dimen.md_button_height);
    this.mDividerPaint = new Paint();
    this.mDividerWidth = localResources.getDimensionPixelSize(R.dimen.md_divider_height);
    this.mDividerPaint.setColor(DialogUtils.resolveColor(paramContext, R.attr.md_divider_color));
    setWillNotDraw(false);
  }
  
  private void invalidateDividersForScrollingView(ViewGroup paramViewGroup, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    boolean bool = true;
    if ((paramBoolean1) && (paramViewGroup.getChildCount() > 0))
    {
      if ((this.mTitleBar != null) && (this.mTitleBar.getVisibility() != 8) && (paramViewGroup.getScrollY() + paramViewGroup.getPaddingTop() > paramViewGroup.getChildAt(0).getTop()))
      {
        paramBoolean1 = true;
        this.mDrawTopDivider = paramBoolean1;
      }
    }
    else if ((paramBoolean2) && (paramViewGroup.getChildCount() > 0)) {
      if ((!paramBoolean3) || (paramViewGroup.getScrollY() + paramViewGroup.getHeight() - paramViewGroup.getPaddingBottom() >= paramViewGroup.getChildAt(paramViewGroup.getChildCount() - 1).getBottom())) {
        break label120;
      }
    }
    label120:
    for (paramBoolean1 = bool;; paramBoolean1 = false)
    {
      this.mDrawBottomDivider = paramBoolean1;
      return;
      paramBoolean1 = false;
      break;
    }
  }
  
  private void invalidateDividersForWebView(WebView paramWebView, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    boolean bool = true;
    if (paramBoolean1)
    {
      if ((this.mTitleBar != null) && (this.mTitleBar.getVisibility() != 8) && (paramWebView.getScrollY() + paramWebView.getPaddingTop() > 0))
      {
        paramBoolean1 = true;
        this.mDrawTopDivider = paramBoolean1;
      }
    }
    else if (paramBoolean2) {
      if ((!paramBoolean3) || (paramWebView.getScrollY() + paramWebView.getMeasuredHeight() - paramWebView.getPaddingBottom() >= paramWebView.getContentHeight() * paramWebView.getScale())) {
        break label97;
      }
    }
    label97:
    for (paramBoolean1 = bool;; paramBoolean1 = false)
    {
      this.mDrawBottomDivider = paramBoolean1;
      return;
      paramBoolean1 = false;
      break;
    }
  }
  
  private void invertGravityIfNecessary()
  {
    if (Build.VERSION.SDK_INT < 17) {}
    while (getResources().getConfiguration().getLayoutDirection() != 1) {
      return;
    }
    switch (this.mButtonGravity)
    {
    default: 
      return;
    case ???: 
      this.mButtonGravity = GravityEnum.END;
      return;
    }
    this.mButtonGravity = GravityEnum.START;
  }
  
  private static boolean isVisible(View paramView)
  {
    if ((paramView != null) && (paramView.getVisibility() != 8)) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      boolean bool2 = bool1;
      if (bool1)
      {
        bool2 = bool1;
        if ((paramView instanceof MDButton))
        {
          if (((MDButton)paramView).getText().toString().trim().length() <= 0) {
            break;
          }
          bool2 = true;
        }
      }
      return bool2;
    }
    return false;
  }
  
  private void setUpDividersVisibility(final View paramView, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    if (paramView == null) {}
    View localView;
    do
    {
      do
      {
        boolean bool;
        do
        {
          do
          {
            do
            {
              return;
              if (!(paramView instanceof ScrollView)) {
                break;
              }
              paramView = (ScrollView)paramView;
              if (canScrollViewScroll(paramView))
              {
                addScrollListener(paramView, paramBoolean1, paramBoolean2);
                return;
              }
              if (paramBoolean1) {
                this.mDrawTopDivider = false;
              }
            } while (!paramBoolean2);
            this.mDrawBottomDivider = false;
            return;
            if (!(paramView instanceof AdapterView)) {
              break;
            }
            paramView = (AdapterView)paramView;
            if (canAdapterViewScroll(paramView))
            {
              addScrollListener(paramView, paramBoolean1, paramBoolean2);
              return;
            }
            if (paramBoolean1) {
              this.mDrawTopDivider = false;
            }
          } while (!paramBoolean2);
          this.mDrawBottomDivider = false;
          return;
          if ((paramView instanceof WebView))
          {
            paramView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
            {
              public boolean onPreDraw()
              {
                if (paramView.getMeasuredHeight() != 0)
                {
                  if (MDRootLayout.canWebViewScroll((WebView)paramView)) {
                    break label68;
                  }
                  if (paramBoolean1) {
                    MDRootLayout.access$102(MDRootLayout.this, false);
                  }
                  if (paramBoolean2) {
                    MDRootLayout.access$202(MDRootLayout.this, false);
                  }
                }
                for (;;)
                {
                  paramView.getViewTreeObserver().removeOnPreDrawListener(this);
                  return true;
                  label68:
                  MDRootLayout.this.addScrollListener((ViewGroup)paramView, paramBoolean1, paramBoolean2);
                }
              }
            });
            return;
          }
          if (!(paramView instanceof RecyclerView)) {
            break;
          }
          bool = canRecyclerViewScroll((RecyclerView)paramView);
          if (paramBoolean1) {
            this.mDrawTopDivider = bool;
          }
        } while (!paramBoolean2);
        this.mDrawBottomDivider = bool;
        return;
      } while (!(paramView instanceof ViewGroup));
      localView = getTopView((ViewGroup)paramView);
      setUpDividersVisibility(localView, paramBoolean1, paramBoolean2);
      paramView = getBottomView((ViewGroup)paramView);
    } while (paramView == localView);
    setUpDividersVisibility(paramView, false, true);
  }
  
  public void noTitleNoPadding()
  {
    this.mNoTitleNoPadding = true;
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mContent != null)
    {
      int i;
      if (this.mDrawTopDivider)
      {
        i = this.mContent.getTop();
        paramCanvas.drawRect(0.0F, i - this.mDividerWidth, getMeasuredWidth(), i, this.mDividerPaint);
      }
      if (this.mDrawBottomDivider)
      {
        i = this.mContent.getBottom();
        paramCanvas.drawRect(0.0F, i, getMeasuredWidth(), this.mDividerWidth + i, this.mDividerPaint);
      }
    }
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    int i = 0;
    if (i < getChildCount())
    {
      View localView = getChildAt(i);
      if (localView.getId() == R.id.titleFrame) {
        this.mTitleBar = localView;
      }
      for (;;)
      {
        i += 1;
        break;
        if (localView.getId() == R.id.buttonDefaultNeutral) {
          this.mButtons[0] = ((MDButton)localView);
        } else if (localView.getId() == R.id.buttonDefaultNegative) {
          this.mButtons[1] = ((MDButton)localView);
        } else if (localView.getId() == R.id.buttonDefaultPositive) {
          this.mButtons[2] = ((MDButton)localView);
        } else {
          this.mContent = localView;
        }
      }
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (isVisible(this.mTitleBar))
    {
      i = this.mTitleBar.getMeasuredHeight();
      this.mTitleBar.layout(paramInt1, paramInt2, paramInt3, paramInt2 + i);
      i = paramInt2 + i;
    }
    for (;;)
    {
      if (isVisible(this.mContent)) {
        this.mContent.layout(paramInt1, i, paramInt3, this.mContent.getMeasuredHeight() + i);
      }
      if (!this.mIsStacked) {
        break;
      }
      paramInt4 -= this.mButtonPaddingFull;
      MDButton[] arrayOfMDButton = this.mButtons;
      j = arrayOfMDButton.length;
      paramInt2 = 0;
      while (paramInt2 < j)
      {
        MDButton localMDButton = arrayOfMDButton[paramInt2];
        i = paramInt4;
        if (isVisible(localMDButton))
        {
          localMDButton.layout(paramInt1, paramInt4 - localMDButton.getMeasuredHeight(), paramInt3, paramInt4);
          i = paramInt4 - localMDButton.getMeasuredHeight();
        }
        paramInt2 += 1;
        paramInt4 = i;
      }
      i = paramInt2;
      if (!this.mNoTitleNoPadding)
      {
        i = paramInt2;
        if (this.mUseFullPadding) {
          i = paramInt2 + this.mNoTitlePaddingFull;
        }
      }
    }
    int k = paramInt4;
    if (this.mUseFullPadding) {
      k = paramInt4 - this.mButtonPaddingFull;
    }
    int i1 = k - this.mButtonBarHeight;
    int m = this.mButtonHorizontalEdgeMargin;
    int j = -1;
    paramInt2 = -1;
    int i = paramInt2;
    paramInt4 = m;
    if (isVisible(this.mButtons[2]))
    {
      if (this.mButtonGravity == GravityEnum.END)
      {
        int n = paramInt1 + m;
        paramInt4 = n + this.mButtons[2].getMeasuredWidth();
        i = paramInt2;
        paramInt2 = n;
        this.mButtons[2].layout(paramInt2, i1, paramInt4, k);
        paramInt4 = m + this.mButtons[2].getMeasuredWidth();
      }
    }
    else
    {
      paramInt2 = j;
      if (isVisible(this.mButtons[1]))
      {
        if (this.mButtonGravity != GravityEnum.END) {
          break label476;
        }
        paramInt2 = paramInt1 + paramInt4;
        paramInt4 = paramInt2 + this.mButtons[1].getMeasuredWidth();
        label363:
        this.mButtons[1].layout(paramInt2, i1, paramInt4, k);
        paramInt2 = j;
      }
      if (isVisible(this.mButtons[0]))
      {
        if (this.mButtonGravity != GravityEnum.END) {
          break label536;
        }
        paramInt2 = paramInt3 - this.mButtonHorizontalEdgeMargin;
        paramInt1 = paramInt2 - this.mButtons[0].getMeasuredWidth();
      }
    }
    for (;;)
    {
      this.mButtons[0].layout(paramInt1, i1, paramInt2, k);
      setUpDividersVisibility(this.mContent, true, true);
      return;
      paramInt4 = paramInt3 - m;
      paramInt2 = paramInt4 - this.mButtons[2].getMeasuredWidth();
      i = paramInt2;
      break;
      label476:
      if (this.mButtonGravity == GravityEnum.START)
      {
        paramInt4 = paramInt3 - paramInt4;
        paramInt2 = paramInt4 - this.mButtons[1].getMeasuredWidth();
        break label363;
      }
      paramInt2 = paramInt1 + this.mButtonHorizontalEdgeMargin;
      paramInt4 = paramInt2 + this.mButtons[1].getMeasuredWidth();
      j = paramInt4;
      break label363;
      label536:
      if (this.mButtonGravity != GravityEnum.START) {
        break label568;
      }
      paramInt1 += this.mButtonHorizontalEdgeMargin;
      paramInt2 = paramInt1 + this.mButtons[0].getMeasuredWidth();
    }
    label568:
    if ((paramInt2 == -1) && (i != -1))
    {
      paramInt2 = i - this.mButtons[0].getMeasuredWidth();
      paramInt4 = i;
    }
    for (;;)
    {
      paramInt1 = paramInt2;
      paramInt2 = paramInt4;
      break;
      if ((i == -1) && (paramInt2 != -1))
      {
        paramInt4 = paramInt2 + this.mButtons[0].getMeasuredWidth();
      }
      else
      {
        paramInt4 = i;
        if (i == -1)
        {
          paramInt2 = (paramInt3 - paramInt1) / 2 - this.mButtons[0].getMeasuredWidth() / 2;
          paramInt4 = paramInt2 + this.mButtons[0].getMeasuredWidth();
        }
      }
    }
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i2 = View.MeasureSpec.getSize(paramInt1);
    int i1 = View.MeasureSpec.getSize(paramInt2);
    this.mUseFullPadding = true;
    int j = 0;
    int i = 0;
    int m;
    MDButton[] arrayOfMDButton;
    int i3;
    MDButton localMDButton;
    int n;
    int k;
    boolean bool;
    if (!this.mForceStack)
    {
      m = 0;
      arrayOfMDButton = this.mButtons;
      i3 = arrayOfMDButton.length;
      j = 0;
      while (j < i3)
      {
        localMDButton = arrayOfMDButton[j];
        n = m;
        k = i;
        if (localMDButton != null)
        {
          n = m;
          k = i;
          if (isVisible(localMDButton))
          {
            localMDButton.setStacked(false, false);
            measureChild(localMDButton, paramInt1, paramInt2);
            n = m + localMDButton.getMeasuredWidth();
            k = 1;
          }
        }
        j += 1;
        m = n;
        i = k;
      }
      if (m > i2 - getContext().getResources().getDimensionPixelSize(R.dimen.md_neutral_button_margin) * 2) {
        bool = true;
      }
    }
    for (;;)
    {
      n = 0;
      j = 0;
      this.mIsStacked = bool;
      k = i;
      if (!bool) {
        break;
      }
      arrayOfMDButton = this.mButtons;
      i3 = arrayOfMDButton.length;
      m = 0;
      for (;;)
      {
        k = i;
        n = j;
        if (m >= i3) {
          break;
        }
        localMDButton = arrayOfMDButton[m];
        n = i;
        k = j;
        if (localMDButton != null)
        {
          n = i;
          k = j;
          if (isVisible(localMDButton))
          {
            localMDButton.setStacked(true, false);
            measureChild(localMDButton, paramInt1, paramInt2);
            k = j + localMDButton.getMeasuredHeight();
            n = 1;
          }
        }
        m += 1;
        i = n;
        j = k;
      }
      bool = false;
      continue;
      bool = true;
      i = j;
    }
    paramInt1 = i1;
    j = 0;
    if (k != 0) {
      if (this.mIsStacked)
      {
        paramInt1 -= n;
        paramInt2 = 0 + this.mButtonPaddingFull * 2;
        j = 0 + this.mButtonPaddingFull * 2;
        if (!isVisible(this.mTitleBar)) {
          break label512;
        }
        this.mTitleBar.measure(View.MeasureSpec.makeMeasureSpec(i2, 1073741824), 0);
        i = paramInt1 - this.mTitleBar.getMeasuredHeight();
        m = paramInt2;
        label379:
        paramInt1 = i;
        if (isVisible(this.mContent))
        {
          this.mContent.measure(View.MeasureSpec.makeMeasureSpec(i2, 1073741824), View.MeasureSpec.makeMeasureSpec(i - j, Integer.MIN_VALUE));
          if (this.mContent.getMeasuredHeight() > i - m) {
            break label558;
          }
          if ((this.mReducePaddingNoTitleNoButtons) && (!isVisible(this.mTitleBar)) && (k == 0)) {
            break label537;
          }
          this.mUseFullPadding = true;
          paramInt1 = i - (this.mContent.getMeasuredHeight() + m);
        }
      }
    }
    for (;;)
    {
      setMeasuredDimension(i2, i1 - paramInt1);
      return;
      paramInt1 -= this.mButtonBarHeight;
      paramInt2 = 0 + this.mButtonPaddingFull * 2;
      break;
      paramInt2 = 0 + this.mButtonPaddingFull * 2;
      break;
      label512:
      i = paramInt1;
      m = paramInt2;
      if (this.mNoTitleNoPadding) {
        break label379;
      }
      m = paramInt2 + this.mNoTitlePaddingFull;
      i = paramInt1;
      break label379;
      label537:
      this.mUseFullPadding = false;
      paramInt1 = i - (this.mContent.getMeasuredHeight() + j);
      continue;
      label558:
      this.mUseFullPadding = false;
      paramInt1 = 0;
    }
  }
  
  public void setButtonGravity(GravityEnum paramGravityEnum)
  {
    this.mButtonGravity = paramGravityEnum;
    invertGravityIfNecessary();
  }
  
  public void setButtonStackedGravity(GravityEnum paramGravityEnum)
  {
    MDButton[] arrayOfMDButton = this.mButtons;
    int j = arrayOfMDButton.length;
    int i = 0;
    while (i < j)
    {
      MDButton localMDButton = arrayOfMDButton[i];
      if (localMDButton != null) {
        localMDButton.setStackedGravity(paramGravityEnum);
      }
      i += 1;
    }
  }
  
  public void setDividerColor(int paramInt)
  {
    this.mDividerPaint.setColor(paramInt);
    invalidate();
  }
  
  public void setForceStack(boolean paramBoolean)
  {
    this.mForceStack = paramBoolean;
    invalidate();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/afollestad/materialdialogs/internal/MDRootLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
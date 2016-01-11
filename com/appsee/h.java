package com.appsee;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;

class h
  implements Window.Callback
{
  h(n paramn) {}
  
  @TargetApi(12)
  public boolean dispatchGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    Window.Callback localCallback;
    if (Build.VERSION.SDK_INT >= 12)
    {
      localCallback = this.G.l();
      if (localCallback != null) {}
    }
    else
    {
      return false;
    }
    return localCallback.dispatchGenericMotionEvent(paramMotionEvent);
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    ol.l(new c(this, paramKeyEvent));
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return false;
    }
    return localCallback.dispatchKeyEvent(paramKeyEvent);
  }
  
  @TargetApi(11)
  public boolean dispatchKeyShortcutEvent(KeyEvent paramKeyEvent)
  {
    Window.Callback localCallback;
    if (Build.VERSION.SDK_INT >= 11)
    {
      localCallback = this.G.l();
      if (localCallback != null) {}
    }
    else
    {
      return false;
    }
    return localCallback.dispatchKeyShortcutEvent(paramKeyEvent);
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return false;
    }
    return localCallback.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    ol.l(new g(this, paramMotionEvent));
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      if (this.G.i() == null) {}
    }
    for (boolean bool = this.G.i().superDispatchTouchEvent(paramMotionEvent);; bool = localCallback.dispatchTouchEvent(paramMotionEvent))
    {
      ol.l(new w(this, bool, paramMotionEvent));
      return bool;
      return false;
    }
  }
  
  public boolean dispatchTrackballEvent(MotionEvent paramMotionEvent)
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return false;
    }
    return localCallback.dispatchTrackballEvent(paramMotionEvent);
  }
  
  public n i()
  {
    return this.G;
  }
  
  @TargetApi(11)
  public void onActionModeFinished(ActionMode paramActionMode)
  {
    Window.Callback localCallback;
    if (Build.VERSION.SDK_INT >= 11)
    {
      localCallback = this.G.l();
      if (localCallback != null) {}
    }
    else
    {
      return;
    }
    localCallback.onActionModeStarted(paramActionMode);
  }
  
  @TargetApi(11)
  public void onActionModeStarted(ActionMode paramActionMode)
  {
    Window.Callback localCallback;
    if (Build.VERSION.SDK_INT >= 11)
    {
      localCallback = this.G.l();
      if (localCallback != null) {}
    }
    else
    {
      return;
    }
    localCallback.onActionModeStarted(paramActionMode);
  }
  
  public void onAttachedToWindow()
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return;
    }
    localCallback.onAttachedToWindow();
  }
  
  public void onContentChanged()
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return;
    }
    localCallback.onContentChanged();
  }
  
  public boolean onCreatePanelMenu(int paramInt, Menu paramMenu)
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return false;
    }
    return localCallback.onCreatePanelMenu(paramInt, paramMenu);
  }
  
  public View onCreatePanelView(int paramInt)
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return null;
    }
    return localCallback.onCreatePanelView(paramInt);
  }
  
  public void onDetachedFromWindow()
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return;
    }
    localCallback.onDetachedFromWindow();
  }
  
  public boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem)
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return false;
    }
    return localCallback.onMenuItemSelected(paramInt, paramMenuItem);
  }
  
  public boolean onMenuOpened(int paramInt, Menu paramMenu)
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return false;
    }
    boolean bool = localCallback.onMenuOpened(paramInt, paramMenu);
    n.i(this.G, paramMenu);
    return bool;
  }
  
  public void onPanelClosed(int paramInt, Menu paramMenu)
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return;
    }
    localCallback.onPanelClosed(paramInt, paramMenu);
    n.i(this.G, null);
  }
  
  public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu)
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return false;
    }
    return localCallback.onPreparePanel(paramInt, paramView, paramMenu);
  }
  
  public boolean onSearchRequested()
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return false;
    }
    return localCallback.onSearchRequested();
  }
  
  @TargetApi(23)
  public boolean onSearchRequested(SearchEvent paramSearchEvent)
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return false;
    }
    return localCallback.onSearchRequested(paramSearchEvent);
  }
  
  public void onWindowAttributesChanged(WindowManager.LayoutParams paramLayoutParams)
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return;
    }
    localCallback.onWindowAttributesChanged(paramLayoutParams);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return;
    }
    localCallback.onWindowFocusChanged(paramBoolean);
  }
  
  @TargetApi(11)
  public ActionMode onWindowStartingActionMode(ActionMode.Callback paramCallback)
  {
    Window.Callback localCallback;
    if (Build.VERSION.SDK_INT >= 11)
    {
      localCallback = this.G.l();
      if (localCallback != null) {}
    }
    else
    {
      return null;
    }
    return localCallback.onWindowStartingActionMode(paramCallback);
  }
  
  @TargetApi(23)
  public ActionMode onWindowStartingActionMode(ActionMode.Callback paramCallback, int paramInt)
  {
    Window.Callback localCallback = this.G.l();
    if (localCallback == null) {
      return null;
    }
    return localCallback.onWindowStartingActionMode(paramCallback, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
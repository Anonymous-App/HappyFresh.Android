package com.optimizely.View;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.optimizely.Core.OptimizelyCodec;
import com.optimizely.Optimizely;
import com.optimizely.View.idmanager.OptimizelyIdManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class ViewUtils
{
  private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
  
  public static void clearViewHierarchy(@Nullable View paramView, @NonNull Optimizely paramOptimizely)
  {
    if (paramView == null) {
      return;
    }
    paramOptimizely.socketBatchBegin();
    paramView = findAllChildViews(paramView).iterator();
    while (paramView.hasNext())
    {
      Object localObject = (View)paramView.next();
      localObject = paramOptimizely.getIdManager().getOptimizelyId((View)localObject);
      if (localObject != null)
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("action", "unregisterView");
        localHashMap.put("id", localObject);
        paramOptimizely.sendMap(localHashMap);
      }
    }
    paramOptimizely.socketBatchEnd();
  }
  
  @NonNull
  public static List<View> findAllChildViews(@Nullable View paramView)
  {
    ArrayList localArrayList = new ArrayList(2);
    LinkedList localLinkedList = new LinkedList();
    if (paramView == null)
    {
      break label31;
      label21:
      return localArrayList;
    }
    else
    {
      localLinkedList.add(paramView);
    }
    for (;;)
    {
      label31:
      if (localLinkedList.isEmpty()) {
        break label21;
      }
      paramView = (View)localLinkedList.remove();
      localArrayList.add(paramView);
      if (!(paramView instanceof ViewGroup)) {
        break;
      }
      paramView = (ViewGroup)paramView;
      int i = 0;
      while (i < paramView.getChildCount())
      {
        localLinkedList.add(paramView.getChildAt(i));
        i += 1;
      }
    }
  }
  
  public static int generateViewId()
  {
    int k;
    int i;
    do
    {
      k = sNextGeneratedId.get();
      int j = k + 1;
      i = j;
      if (j > 16777215) {
        i = 1;
      }
    } while (!sNextGeneratedId.compareAndSet(k, i));
    return k;
  }
  
  @Nullable
  private static View getNearestViewParent(@NonNull View paramView, @NonNull Optimizely paramOptimizely)
  {
    for (paramView = paramView.getParent(); (paramView != null) && ((paramView instanceof View)); paramView = paramView.getParent())
    {
      paramView = (View)paramView;
      if (paramOptimizely.getIdManager().getOptimizelyId(paramView) != null) {
        return paramView;
      }
    }
    return null;
  }
  
  @NonNull
  static Map<String, Object> getOptimizelyInfo(@NonNull View paramView, @NonNull Optimizely paramOptimizely, boolean paramBoolean)
  {
    Hashtable localHashtable = new Hashtable();
    Map localMap = OptimizelyCodec.encodeDictionary(paramOptimizely, OptimizelyViewPropertyHandler.getViewProperties(paramView, paramOptimizely));
    View localView2 = getNearestViewParent(paramView, paramOptimizely);
    View localView1 = localView2;
    if (!(localView2 instanceof ViewGroup)) {
      localView1 = null;
    }
    paramOptimizely = paramOptimizely.getIdManager();
    localHashtable.put("properties", localMap);
    localHashtable.put("id", paramOptimizely.getOptimizelyId(paramView));
    localHashtable.put("class", paramView.getClass().getSimpleName());
    if (paramView.getContentDescription() != null)
    {
      paramView = paramView.getContentDescription();
      localHashtable.put("accessibilityLabel", paramView);
      if (localView1 != null) {
        localHashtable.put("superview", paramOptimizely.getOptimizelyId(localView1));
      }
      if (!paramBoolean) {
        break label158;
      }
    }
    label158:
    for (paramView = "updateView";; paramView = "registerView")
    {
      localHashtable.put("action", paramView);
      return localHashtable;
      paramView = "";
      break;
    }
  }
  
  @NonNull
  public static Rect getViewBoundsOnScreen(@NonNull View paramView, boolean paramBoolean, @NonNull Optimizely paramOptimizely)
  {
    int[] arrayOfInt = new int[2];
    paramView.getLocationOnScreen(arrayOfInt);
    paramView = new Rect(arrayOfInt[0], arrayOfInt[1], arrayOfInt[0] + paramView.getWidth(), arrayOfInt[1] + paramView.getHeight());
    if (paramBoolean)
    {
      paramOptimizely = paramOptimizely.getCurrentContext().getResources().getDisplayMetrics();
      paramOptimizely = new Rect(0, 0, paramOptimizely.widthPixels, paramOptimizely.heightPixels);
      if (paramView.intersect(paramOptimizely)) {
        paramView.intersect(paramOptimizely);
      }
    }
    else
    {
      return paramView;
    }
    return new Rect();
  }
  
  public static boolean isViewOnScreen(@NonNull View paramView, @NonNull Optimizely paramOptimizely)
  {
    paramView = getViewBoundsOnScreen(paramView, true, paramOptimizely);
    return (paramView.width() > 0) && (paramView.height() > 0);
  }
  
  @Nullable
  public static View rootView(@NonNull Activity paramActivity)
  {
    Object localObject = null;
    if (paramActivity == null) {}
    do
    {
      return (View)localObject;
      paramActivity = paramActivity.getWindow();
    } while (paramActivity == null);
    for (paramActivity = paramActivity.peekDecorView();; paramActivity = (View)paramActivity.getParent())
    {
      localObject = paramActivity;
      if (paramActivity == null) {
        break;
      }
      localObject = paramActivity;
      if (!(paramActivity.getParent() instanceof View)) {
        break;
      }
    }
  }
  
  public static void sendViewHierarchy(@Nullable View paramView, @NonNull Optimizely paramOptimizely)
  {
    if ((paramView == null) || (!paramOptimizely.isActive())) {
      return;
    }
    paramOptimizely.socketBatchBegin();
    paramView = transformViewsIntoOptimizelyInfo(findAllChildViews(paramView), paramOptimizely, false).iterator();
    while (paramView.hasNext()) {
      paramOptimizely.sendMap((Map)paramView.next());
    }
    paramOptimizely.socketBatchEnd();
  }
  
  @NonNull
  public static String stripPackage(@NonNull String paramString)
  {
    String str = paramString;
    if (paramString.indexOf('.') != -1) {
      str = paramString.substring(paramString.lastIndexOf('.') + 1);
    }
    return str;
  }
  
  @NonNull
  private static List<Map<String, Object>> transformViewsIntoOptimizelyInfo(@NonNull List<View> paramList, @NonNull Optimizely paramOptimizely, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      View localView = (View)paramList.next();
      if (paramOptimizely.getIdManager().getOptimizelyId(localView) != null) {
        localArrayList.add(getOptimizelyInfo(localView, paramOptimizely, paramBoolean));
      }
    }
    return localArrayList;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/View/ViewUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
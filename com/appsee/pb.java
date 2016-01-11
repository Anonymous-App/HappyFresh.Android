package com.appsee;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TabWidget;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class pb
{
  static
  {
    try
    {
      m = new ArrayList();
      G = new ArrayList();
      i = new ArrayList();
      k = new HashMap();
      K = new ArrayList();
      G.add(AbsListView.class);
      G.add(ScrollView.class);
      m.add(AbsListView.class);
      m.add(SeekBar.class);
      m.add(Button.class);
      m.add(ImageButton.class);
      m.add(EditText.class);
      m.add(TabWidget.class);
      i();
      m.addAll((Collection)k.get(zb.b));
      m.addAll((Collection)k.get(zb.d));
      m.addAll((Collection)k.get(zb.k));
      m.addAll((Collection)k.get(zb.i));
      m.addAll((Collection)k.get(zb.m));
      m.addAll((Collection)k.get(zb.C));
      m.addAll((Collection)k.get(zb.G));
      i.add(Button.class);
      i.add(ImageButton.class);
      i.addAll((Collection)k.get(zb.b));
      i.addAll((Collection)k.get(zb.m));
      i.addAll((Collection)k.get(zb.C));
      i.addAll((Collection)k.get(zb.i));
      i.addAll((Collection)k.get(zb.G));
      K.addAll((Collection)k.get(zb.b));
      K.addAll((Collection)k.get(zb.d));
      return;
    }
    catch (Exception localException)
    {
      vd.l(localException, AppseeBackgroundUploader.i("|\"G=E1\0301Z+C!Ml|\023~4^}\0256P8J"));
    }
  }
  
  public static String F(View paramView)
  {
    return i(paramView, true, i);
  }
  
  public static boolean F(View paramView)
  {
    return i(K, paramView);
  }
  
  public static View a(View paramView)
  {
    if (paramView == null) {
      return null;
    }
    return i(paramView, m);
  }
  
  public static String a(View paramView)
  {
    if (paramView == null) {
      return "";
    }
    for (;;)
    {
      try
      {
        if ((paramView instanceof TextView))
        {
          str = (((TextView)paramView).getText().toString() + AppseeBackgroundUploader.i("\032")).replace(AppseeBackgroundUploader.i("2"), AppseeBackgroundUploader.i("\001V")).replace(AppseeBackgroundUploader.i("5"), AppseeBackgroundUploader.i("\001J"));
          StringBuilder localStringBuilder = new StringBuilder().insert(0, paramView.getClass().getName()).append(AppseeBackgroundUploader.i("}\020")).append(paramView.getId()).append(AppseeBackgroundUploader.i("t\030")).append(i(i(paramView))).append(AppseeBackgroundUploader.i("\002")).append(paramView.isShown()).append(AppseeBackgroundUploader.i("\002")).append(paramView.isClickable()).append(AppseeBackgroundUploader.i("\002")).append(paramView.isEnabled()).append(AppseeBackgroundUploader.i("\002")).append(l(paramView));
          if (str == null)
          {
            paramView = "";
            return paramView;
          }
          paramView = str;
          continue;
        }
        String str = null;
      }
      catch (Exception paramView)
      {
        return null;
      }
    }
  }
  
  public static boolean a(View paramView)
  {
    return i(i, paramView);
  }
  
  public static boolean a(View paramView, Class<?> paramClass)
  {
    return i(paramView, i(new Class[] { paramClass }));
  }
  
  public static boolean a(AbsListView paramAbsListView)
  {
    boolean bool1 = l(paramAbsListView);
    boolean bool2 = i(paramAbsListView);
    return (!bool1) || (!bool2);
  }
  
  public static int i(View paramView, Class<?> paramClass)
  {
    int n = 0;
    paramView = i(paramView, paramClass);
    int j = n;
    if (paramView != null)
    {
      j = n;
      if (!paramView.isEmpty())
      {
        paramView = paramView.iterator();
        j = 0;
        while (paramView.hasNext()) {
          if (((View)paramView.next()).isShown()) {
            j += 1;
          }
        }
      }
    }
    return j;
  }
  
  public static int i(ViewGroup paramViewGroup, View paramView)
  {
    paramViewGroup = i(paramViewGroup);
    if ((paramView != null) && (!paramViewGroup.isEmpty()))
    {
      if (paramViewGroup.size() > 1) {
        return paramViewGroup.indexOf(paramView.getClass());
      }
      return 0;
    }
    return -1;
  }
  
  public static int i(AbsListView paramAbsListView, int paramInt)
  {
    try
    {
      paramInt = ((Integer)bc.i(paramAbsListView, AppseeBackgroundUploader.i("L%G>r5Kx\033!x2O"), 1, new Object[] { Integer.valueOf(paramInt - l(paramAbsListView).top) })).intValue();
      return paramInt;
    }
    catch (Exception localException)
    {
      vd.l(localException, paramAbsListView.getClass().getName());
    }
    return -1;
  }
  
  public static Bitmap i(Drawable paramDrawable)
  {
    if (paramDrawable == null) {}
    Drawable localDrawable;
    do
    {
      return null;
      localDrawable = paramDrawable;
      if (!(paramDrawable instanceof BitmapDrawable)) {
        localDrawable = paramDrawable.getCurrent();
      }
    } while (localDrawable == null);
    paramDrawable = localDrawable.getBounds();
    if ((!(localDrawable instanceof BitmapDrawable)) && (paramDrawable.width() > 0) && (paramDrawable.height() > 0))
    {
      int j = paramDrawable.width();
      int n = paramDrawable.height();
      Bitmap localBitmap = Bitmap.createBitmap(j, n, Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      if ((j != paramDrawable.width()) || (n != paramDrawable.height())) {
        localCanvas.scale(j / paramDrawable.width(), n / paramDrawable.height());
      }
      localDrawable.draw(localCanvas);
      return localBitmap;
    }
    return ((BitmapDrawable)localDrawable).getBitmap();
  }
  
  public static Point i(Point paramPoint)
  {
    ki localki = rj.i();
    Dimension localDimension = rj.i();
    if (localki == ki.k) {}
    do
    {
      return paramPoint;
      if (localki == ki.i) {
        return new Point(localDimension.getWidth() - paramPoint.x, localDimension.getHeight() - paramPoint.y);
      }
      if (localki == ki.b) {
        return new Point(localDimension.getWidth() - paramPoint.y, paramPoint.x);
      }
    } while (localki != ki.G);
    return new Point(paramPoint.y, localDimension.getHeight() - paramPoint.x);
  }
  
  public static Rect i()
  {
    return i(rj.i());
  }
  
  public static Rect i(Rect paramRect)
  {
    Rect localRect;
    if (paramRect == null) {
      localRect = null;
    }
    ki localki;
    Dimension localDimension;
    do
    {
      do
      {
        return localRect;
        if ((paramRect.width() < 1) || (paramRect.height() < 1)) {
          return null;
        }
        localki = rj.i();
        localDimension = rj.i();
        localRect = paramRect;
      } while (localki == ki.k);
      if (localki == ki.i) {
        return new Rect(localDimension.getWidth() - paramRect.right, localDimension.getHeight() - paramRect.bottom, localDimension.getWidth() - paramRect.left, localDimension.getHeight() - paramRect.top);
      }
      if (localki == ki.b) {
        return new Rect(localDimension.getWidth() - paramRect.bottom, paramRect.left, localDimension.getWidth() - paramRect.top, paramRect.right);
      }
      localRect = paramRect;
    } while (localki != ki.G);
    return new Rect(paramRect.top, localDimension.getHeight() - paramRect.right, paramRect.bottom, localDimension.getHeight() - paramRect.left);
  }
  
  public static Rect i(View paramView)
  {
    Rect localRect = new Rect();
    if (paramView == null) {
      return localRect;
    }
    paramView.getGlobalVisibleRect(localRect);
    int[] arrayOfInt = new int[2];
    paramView.getRootView().getLocationOnScreen(arrayOfInt);
    localRect.offset(arrayOfInt[0], arrayOfInt[1]);
    return localRect;
  }
  
  @TargetApi(16)
  public static Rect i(n paramn)
  {
    Window localWindow = paramn.i();
    paramn = paramn.i();
    int j;
    if ((localWindow != null) && (localWindow.getAttributes() != null) && ((localWindow.getAttributes().flags & 0x200) != 0))
    {
      j = 1;
      if ((Build.VERSION.SDK_INT < 16) || (paramn == null) || ((paramn.getSystemUiVisibility() & 0x2) == 0)) {
        break label99;
      }
    }
    label99:
    for (int n = 1;; n = 0)
    {
      paramn = rj.i();
      localWindow = paramn[1];
      if ((j == 0) && (n == 0)) {
        break label104;
      }
      return new Rect(0, 0, localWindow.getWidth(), localWindow.getHeight());
      j = 0;
      break;
    }
    label104:
    return i(paramn);
  }
  
  public static MenuItem i(View paramView)
    throws Exception
  {
    if (paramView == null) {
      return null;
    }
    return (MenuItem)bc.i(paramView, AppseeBackgroundUploader.i("N?K\023Kt\031\013K)Y"), null, new Object[0]);
  }
  
  public static View i(MotionEvent paramMotionEvent, View paramView)
  {
    int j = (short)(int)paramMotionEvent.getX(paramMotionEvent.getActionIndex());
    int n = (short)(int)paramMotionEvent.getY(paramMotionEvent.getActionIndex());
    paramMotionEvent = i(paramView);
    return i(paramView, (short)(j + paramMotionEvent.left), (short)(n + paramMotionEvent.top));
  }
  
  public static View i(View paramView)
  {
    if (paramView == null) {
      localObject = null;
    }
    do
    {
      return (View)localObject;
      if ((!paramView.isShown()) || (!paramView.isEnabled())) {
        break;
      }
      localObject = paramView;
    } while (paramView.isClickable());
    Object localObject = paramView.getParent();
    if ((!(localObject instanceof View)) || (paramView == null)) {
      return null;
    }
    return i((View)localObject);
  }
  
  public static View i(View paramView, Class<?> paramClass)
  {
    ArrayList localArrayList = new ArrayList(1);
    localArrayList.add(paramClass);
    return i(paramView, localArrayList);
  }
  
  public static View i(ViewGroup paramViewGroup, View paramView)
  {
    Object localObject;
    if ((paramViewGroup == null) || (paramView == null))
    {
      localObject = null;
      return (View)localObject;
    }
    int j = 0;
    for (int n = 0;; n = j)
    {
      if (j >= paramViewGroup.getChildCount()) {
        break label55;
      }
      View localView = paramViewGroup.getChildAt(n);
      localObject = localView;
      if (l(paramView, localView)) {
        break;
      }
      j = n + 1;
    }
    label55:
    return null;
  }
  
  public static <T extends View> T i(List<T> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        View localView = (View)paramList.next();
        if ((localView.isShown()) && (localView.getWidth() > 0) && (localView.getHeight() > 0)) {
          return localView;
        }
      }
    }
    return null;
  }
  
  public static View i(Object[] paramArrayOfObject)
  {
    int i1 = paramArrayOfObject.length;
    int j = 0;
    for (int n = 0; j < i1; n = j)
    {
      View localView = k((View)paramArrayOfObject[n]);
      if (localView != null) {
        return localView;
      }
      j = n + 1;
    }
    return null;
  }
  
  public static ImageView i(View paramView)
    throws Exception
  {
    paramView = i(paramView, ImageView.class);
    if (!paramView.isEmpty())
    {
      paramView = paramView.iterator();
      while (paramView.hasNext())
      {
        ImageView localImageView = (ImageView)paramView.next();
        if ((localImageView.isShown()) && (localImageView.getWidth() > 0) && (localImageView.getHeight() > 0)) {
          return localImageView;
        }
      }
    }
    return null;
  }
  
  public static String i(Rect paramRect)
  {
    return String.format(AppseeBackgroundUploader.i("\002iMv\032>\0234\020c\0179\021"), new Object[] { Integer.valueOf(paramRect.left), Integer.valueOf(paramRect.top), Integer.valueOf(paramRect.right), Integer.valueOf(paramRect.bottom) });
  }
  
  public static String i(View paramView)
  {
    if ((paramView != null) && ((paramView instanceof LinearLayout)))
    {
      paramView = (TextView)((LinearLayout)paramView).findViewById(16908299);
      if ((paramView != null) && (!lb.i(paramView.getText()))) {
        return paramView.getText().toString();
      }
    }
    return null;
  }
  
  public static String i(View paramView, boolean paramBoolean, List<Class<?>> paramList)
  {
    Object localObject1;
    if (!paramBoolean) {
      localObject1 = String.valueOf(i(paramView, paramView.getRootView(), paramList));
    }
    Object localObject2;
    do
    {
      return (String)localObject1;
      localObject1 = new ArrayList(2);
      ((List)localObject1).addAll((Collection)k.get(zb.K));
      ((List)localObject1).addAll((Collection)k.get(zb.k));
      ((List)localObject1).addAll(G);
      localObject2 = new ArrayList(paramList);
      ((List)localObject2).addAll((Collection)localObject1);
      localObject2 = String.valueOf(i(paramView, paramView.getRootView(), (List)localObject2));
      localObject3 = (AbsListView)i(paramView, AbsListView.class);
      if (localObject3 == null) {
        break;
      }
      mc.l(AppseeBackgroundUploader.i("g=R9[5\027]\035-O\031C*]l@4Q?M1\035!N8@"), 1);
      localObject3 = i((ViewGroup)localObject3, paramView);
      localObject1 = localObject2;
    } while (localObject3 == null);
    int j = i(paramView, (View)localObject3, paramList);
    return AppseeBackgroundUploader.i("\026") + String.valueOf(j);
    Object localObject3 = ((List)localObject1).iterator();
    for (;;)
    {
      localObject1 = localObject2;
      if (!((Iterator)localObject3).hasNext()) {
        break;
      }
      localObject1 = (Class)((Iterator)localObject3).next();
      View localView = i(paramView, (Class)localObject1);
      if (localView != null)
      {
        mc.l(((Class)localObject1).getName(), 1);
        j = i(paramView, localView, paramList);
        localObject2 = AppseeBackgroundUploader.i("\026") + String.valueOf(j);
      }
    }
  }
  
  public static List<Class<?>> i()
  {
    return Collections.unmodifiableList(i);
  }
  
  public static List<View> i(View paramView)
  {
    return i(paramView, K);
  }
  
  public static <T extends View> List<T> i(View paramView, Class<?> paramClass)
  {
    ArrayList localArrayList = new ArrayList(1);
    localArrayList.add(paramClass);
    return i(paramView, localArrayList, new ArrayList());
  }
  
  public static <T extends View> List<T> i(View paramView, List<Class<?>> paramList)
  {
    return i(paramView, paramList, new ArrayList());
  }
  
  public static List<Class<?>> i(Class<?>... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList(paramVarArgs.length);
    int i1 = paramVarArgs.length;
    int j = 0;
    for (int n = 0; j < i1; n = j)
    {
      Class<?> localClass = paramVarArgs[n];
      if (!localArrayList.contains(localClass)) {
        localArrayList.add(localClass);
      }
      j = n + 1;
    }
    return localArrayList;
  }
  
  public static List<View> i(Object[] paramArrayOfObject)
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    paramArrayOfObject = i(paramArrayOfObject);
    Object[] arrayOfObject = paramArrayOfObject.keySet().toArray();
    Arrays.sort(arrayOfObject);
    int i1 = arrayOfObject.length;
    int j = 0;
    for (int n = 0; j < i1; n = j)
    {
      Object localObject = arrayOfObject[n];
      Iterator localIterator = ((List)paramArrayOfObject.get(localObject)).iterator();
      while (localIterator.hasNext())
      {
        View localView = (View)localIterator.next();
        if ((localView.hasWindowFocus()) || (localObject.equals(Integer.valueOf(b)))) {
          localArrayList2.add(localView);
        } else {
          localArrayList1.add(localView);
        }
      }
      j = n + 1;
    }
    localArrayList1.addAll(localArrayList2);
    return localArrayList1;
  }
  
  public static void i(View paramView, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramView = i(paramView, "", paramBoolean2);
    if (paramBoolean1) {
      hd.i(paramView);
    }
  }
  
  public static boolean i(Drawable paramDrawable)
  {
    return (paramDrawable != null) && (paramDrawable.isVisible()) && (paramDrawable.getBounds().width() > 0) && (paramDrawable.getBounds().height() > 0);
  }
  
  public static boolean i(View paramView)
  {
    Rect localRect = i(paramView);
    if ((paramView.getWidth() < 2) || (paramView.getHeight() < 2)) {}
    while ((!i(paramView, localRect.left + 1, localRect.top + 1)) && (!i(paramView, localRect.right - 1, localRect.top + 1)) && (!i(paramView, localRect.left + 1, localRect.bottom - 1)) && (!i(paramView, localRect.right - 1, localRect.bottom - 1))) {
      return false;
    }
    return true;
  }
  
  public static boolean i(View paramView1, View paramView2)
  {
    if (paramView1 == null) {}
    for (;;)
    {
      return false;
      if (paramView1.equals(paramView2)) {
        return true;
      }
      if ((paramView1 instanceof ViewGroup))
      {
        paramView1 = (ViewGroup)paramView1;
        int j = 0;
        for (int n = 0; j < paramView1.getChildCount(); n = j)
        {
          if (i(paramView1.getChildAt(n), paramView2)) {
            return true;
          }
          j = n + 1;
        }
      }
    }
  }
  
  public static boolean i(View paramView, Class<?> paramClass)
  {
    if (paramView == null) {}
    for (;;)
    {
      return false;
      if (paramClass.isInstance(paramView)) {
        return true;
      }
      if ((paramView instanceof ViewGroup))
      {
        paramView = (ViewGroup)paramView;
        int j = 0;
        for (int n = 0; j < paramView.getChildCount(); n = j)
        {
          if (i(paramView.getChildAt(n), paramClass)) {
            return true;
          }
          j = n + 1;
        }
      }
    }
  }
  
  public static boolean i(View paramView, List<Class<?>> paramList)
  {
    if (paramView == null) {
      return false;
    }
    paramList = i(paramView.getRootView(), paramList).iterator();
    label83:
    label86:
    while (paramList.hasNext())
    {
      View localView = (View)paramList.next();
      if (!paramView.equals(localView))
      {
        int j;
        if (localView != null)
        {
          j = 1;
          if (paramView.getId() != localView.getId()) {
            break label83;
          }
        }
        for (int n = 1;; n = 0)
        {
          if ((n & j) == 0) {
            break label86;
          }
          return false;
          j = 0;
          break;
        }
      }
    }
    return true;
  }
  
  public static boolean i(AbsListView paramAbsListView)
  {
    if ((paramAbsListView == null) || (paramAbsListView.getCount() == 0)) {}
    while ((paramAbsListView.getLastVisiblePosition() == paramAbsListView.getCount() - 1) && (paramAbsListView.getChildAt(paramAbsListView.getChildCount() - 1).getBottom() <= paramAbsListView.getBottom())) {
      return true;
    }
    return false;
  }
  
  public static boolean i(zb paramzb, View paramView)
  {
    return i((List)k.get(paramzb), paramView);
  }
  
  public static boolean i(List<Class<?>> paramList, View paramView)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      if (((Class)paramList.next()).isInstance(paramView)) {
        return true;
      }
    }
    return false;
  }
  
  public static int l(ViewGroup paramViewGroup, View paramView)
  {
    if ((paramViewGroup == null) || (paramView == null))
    {
      n = -1;
      return n;
    }
    int n = 0;
    for (int j = 0;; j = n)
    {
      if (n >= paramViewGroup.getChildCount()) {
        break label44;
      }
      n = j;
      if (paramViewGroup.getChildAt(j) == paramView) {
        break;
      }
      n = j + 1;
    }
    label44:
    return -1;
  }
  
  public static Rect l(View paramView)
  {
    Rect localRect = new Rect();
    int[] arrayOfInt = new int[2];
    if (paramView == null) {
      return localRect;
    }
    paramView.getGlobalVisibleRect(localRect);
    paramView.getLocationInWindow(arrayOfInt);
    int j = arrayOfInt[0];
    int n = arrayOfInt[1];
    int i1 = arrayOfInt[0];
    int i2 = localRect.width();
    int i3 = arrayOfInt[1];
    return new Rect(j, n, i1 + i2, localRect.height() + i3);
  }
  
  public static View l(View paramView)
  {
    return i(paramView, G, null);
  }
  
  public static String l(View paramView)
  {
    paramView = i(paramView, TextView.class);
    if (!paramView.isEmpty())
    {
      paramView = paramView.iterator();
      while (paramView.hasNext())
      {
        TextView localTextView = (TextView)paramView.next();
        if ((!lb.i(localTextView.getText())) && (localTextView.isShown()) && (localTextView.getWidth() > 0) && (localTextView.getHeight() > 0)) {
          return localTextView.getText().toString();
        }
      }
    }
    return null;
  }
  
  public static boolean l(View paramView, Class<?> paramClass)
  {
    paramView = i(paramView, paramClass);
    if ((paramView == null) || (paramView.isEmpty())) {
      return false;
    }
    paramView = paramView.iterator();
    while (paramView.hasNext())
    {
      paramClass = (View)paramView.next();
      if ((paramClass.isShown()) && (paramClass.getWidth() > 0) && (paramClass.getHeight() > 0)) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean l(AbsListView paramAbsListView)
  {
    if ((paramAbsListView == null) || (paramAbsListView.getCount() == 0)) {}
    while ((paramAbsListView.getFirstVisiblePosition() == 0) && (paramAbsListView.getChildAt(0).getTop() >= paramAbsListView.getTop())) {
      return true;
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/pb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
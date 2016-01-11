package com.mixpanel.android.viewcrawler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.JsonWriter;
import android.util.Log;
import android.util.LruCache;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout.LayoutParams;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.mpmetrics.ResourceIds;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONObject;

@TargetApi(16)
class ViewSnapshot
{
  private static final String LOGTAG = "MixpanelAPI.Snapshot";
  private static final int MAX_CLASS_NAME_CACHE_SIZE = 255;
  private final ClassNameCache mClassnameCache;
  private final Handler mMainThreadHandler;
  private final List<PropertyDescription> mProperties;
  private final ResourceIds mResourceIds;
  private final RootViewFinder mRootViewFinder;
  
  public ViewSnapshot(List<PropertyDescription> paramList, ResourceIds paramResourceIds)
  {
    this.mProperties = paramList;
    this.mResourceIds = paramResourceIds;
    this.mMainThreadHandler = new Handler(Looper.getMainLooper());
    this.mRootViewFinder = new RootViewFinder();
    this.mClassnameCache = new ClassNameCache(255);
  }
  
  private void addProperties(JsonWriter paramJsonWriter, View paramView)
    throws IOException
  {
    Class localClass = paramView.getClass();
    Iterator localIterator = this.mProperties.iterator();
    while (localIterator.hasNext())
    {
      Object localObject1 = (PropertyDescription)localIterator.next();
      if ((((PropertyDescription)localObject1).targetClass.isAssignableFrom(localClass)) && (((PropertyDescription)localObject1).accessor != null))
      {
        Object localObject2 = ((PropertyDescription)localObject1).accessor.applyMethod(paramView);
        if (localObject2 != null) {
          if ((localObject2 instanceof Number))
          {
            paramJsonWriter.name(((PropertyDescription)localObject1).name).value((Number)localObject2);
          }
          else if ((localObject2 instanceof Boolean))
          {
            paramJsonWriter.name(((PropertyDescription)localObject1).name).value(((Boolean)localObject2).booleanValue());
          }
          else if ((localObject2 instanceof ColorStateList))
          {
            paramJsonWriter.name(((PropertyDescription)localObject1).name).value(Integer.valueOf(((ColorStateList)localObject2).getDefaultColor()));
          }
          else if ((localObject2 instanceof Drawable))
          {
            localObject2 = (Drawable)localObject2;
            Rect localRect = ((Drawable)localObject2).getBounds();
            paramJsonWriter.name(((PropertyDescription)localObject1).name);
            paramJsonWriter.beginObject();
            paramJsonWriter.name("classes");
            paramJsonWriter.beginArray();
            for (localObject1 = localObject2.getClass(); localObject1 != Object.class; localObject1 = ((Class)localObject1).getSuperclass()) {
              paramJsonWriter.value(((Class)localObject1).getCanonicalName());
            }
            paramJsonWriter.endArray();
            paramJsonWriter.name("dimensions");
            paramJsonWriter.beginObject();
            paramJsonWriter.name("left").value(localRect.left);
            paramJsonWriter.name("right").value(localRect.right);
            paramJsonWriter.name("top").value(localRect.top);
            paramJsonWriter.name("bottom").value(localRect.bottom);
            paramJsonWriter.endObject();
            if ((localObject2 instanceof ColorDrawable))
            {
              localObject1 = (ColorDrawable)localObject2;
              paramJsonWriter.name("color").value(((ColorDrawable)localObject1).getColor());
            }
            paramJsonWriter.endObject();
          }
          else
          {
            paramJsonWriter.name(((PropertyDescription)localObject1).name).value(localObject2.toString());
          }
        }
      }
    }
  }
  
  private void snapshotView(JsonWriter paramJsonWriter, View paramView)
    throws IOException
  {
    int i = paramView.getId();
    Object localObject1;
    if (-1 == i)
    {
      localObject1 = null;
      paramJsonWriter.beginObject();
      paramJsonWriter.name("hashCode").value(paramView.hashCode());
      paramJsonWriter.name("id").value(i);
      paramJsonWriter.name("mp_id_name").value((String)localObject1);
      localObject1 = paramView.getContentDescription();
      if (localObject1 != null) {
        break label418;
      }
      paramJsonWriter.name("contentDescription").nullValue();
      label81:
      localObject1 = paramView.getTag();
      if (localObject1 != null) {
        break label438;
      }
      paramJsonWriter.name("tag").nullValue();
    }
    Object localObject2;
    int j;
    for (;;)
    {
      paramJsonWriter.name("top").value(paramView.getTop());
      paramJsonWriter.name("left").value(paramView.getLeft());
      paramJsonWriter.name("width").value(paramView.getWidth());
      paramJsonWriter.name("height").value(paramView.getHeight());
      paramJsonWriter.name("scrollX").value(paramView.getScrollX());
      paramJsonWriter.name("scrollY").value(paramView.getScrollY());
      paramJsonWriter.name("visibility").value(paramView.getVisibility());
      float f1 = 0.0F;
      float f2 = 0.0F;
      if (Build.VERSION.SDK_INT >= 11)
      {
        f1 = paramView.getTranslationX();
        f2 = paramView.getTranslationY();
      }
      paramJsonWriter.name("translationX").value(f1);
      paramJsonWriter.name("translationY").value(f2);
      paramJsonWriter.name("classes");
      paramJsonWriter.beginArray();
      localObject1 = paramView.getClass();
      do
      {
        paramJsonWriter.value((String)this.mClassnameCache.get(localObject1));
        localObject2 = ((Class)localObject1).getSuperclass();
        if (localObject2 == Object.class) {
          break;
        }
        localObject1 = localObject2;
      } while (localObject2 != null);
      paramJsonWriter.endArray();
      addProperties(paramJsonWriter, paramView);
      localObject1 = paramView.getLayoutParams();
      if (!(localObject1 instanceof RelativeLayout.LayoutParams)) {
        break label469;
      }
      localObject1 = ((RelativeLayout.LayoutParams)localObject1).getRules();
      paramJsonWriter.name("layoutRules");
      paramJsonWriter.beginArray();
      j = localObject1.length;
      i = 0;
      while (i < j)
      {
        paramJsonWriter.value(localObject1[i]);
        i += 1;
      }
      localObject1 = this.mResourceIds.nameForId(i);
      break;
      label418:
      paramJsonWriter.name("contentDescription").value(((CharSequence)localObject1).toString());
      break label81;
      label438:
      if ((localObject1 instanceof CharSequence)) {
        paramJsonWriter.name("tag").value(localObject1.toString());
      }
    }
    paramJsonWriter.endArray();
    label469:
    paramJsonWriter.name("subviews");
    paramJsonWriter.beginArray();
    if ((paramView instanceof ViewGroup))
    {
      localObject1 = (ViewGroup)paramView;
      j = ((ViewGroup)localObject1).getChildCount();
      i = 0;
      while (i < j)
      {
        localObject2 = ((ViewGroup)localObject1).getChildAt(i);
        if (localObject2 != null) {
          paramJsonWriter.value(localObject2.hashCode());
        }
        i += 1;
      }
    }
    paramJsonWriter.endArray();
    paramJsonWriter.endObject();
    if ((paramView instanceof ViewGroup))
    {
      paramView = (ViewGroup)paramView;
      j = paramView.getChildCount();
      i = 0;
      while (i < j)
      {
        localObject1 = paramView.getChildAt(i);
        if (localObject1 != null) {
          snapshotView(paramJsonWriter, (View)localObject1);
        }
        i += 1;
      }
    }
  }
  
  List<PropertyDescription> getProperties()
  {
    return this.mProperties;
  }
  
  void snapshotViewHierarchy(JsonWriter paramJsonWriter, View paramView)
    throws IOException
  {
    paramJsonWriter.beginArray();
    snapshotView(paramJsonWriter, paramView);
    paramJsonWriter.endArray();
  }
  
  public void snapshots(UIThreadSet<Activity> paramUIThreadSet, OutputStream paramOutputStream)
    throws IOException
  {
    this.mRootViewFinder.findInActivities(paramUIThreadSet);
    paramUIThreadSet = new FutureTask(this.mRootViewFinder);
    this.mMainThreadHandler.post(paramUIThreadSet);
    OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(paramOutputStream);
    Object localObject = Collections.emptyList();
    localOutputStreamWriter.write("[");
    try
    {
      paramUIThreadSet = (List)paramUIThreadSet.get(1L, TimeUnit.SECONDS);
      int j = paramUIThreadSet.size();
      int i = 0;
      while (i < j)
      {
        if (i > 0) {
          localOutputStreamWriter.write(",");
        }
        localObject = (RootViewInfo)paramUIThreadSet.get(i);
        localOutputStreamWriter.write("{");
        localOutputStreamWriter.write("\"activity\":");
        localOutputStreamWriter.write(JSONObject.quote(((RootViewInfo)localObject).activityName));
        localOutputStreamWriter.write(",");
        localOutputStreamWriter.write("\"scale\":");
        localOutputStreamWriter.write(String.format("%s", new Object[] { Float.valueOf(((RootViewInfo)localObject).scale) }));
        localOutputStreamWriter.write(",");
        localOutputStreamWriter.write("\"serialized_objects\":");
        JsonWriter localJsonWriter = new JsonWriter(localOutputStreamWriter);
        localJsonWriter.beginObject();
        localJsonWriter.name("rootObject").value(((RootViewInfo)localObject).rootView.hashCode());
        localJsonWriter.name("objects");
        snapshotViewHierarchy(localJsonWriter, ((RootViewInfo)localObject).rootView);
        localJsonWriter.endObject();
        localJsonWriter.flush();
        localOutputStreamWriter.write(",");
        localOutputStreamWriter.write("\"screenshot\":");
        localOutputStreamWriter.flush();
        ((RootViewInfo)localObject).screenshot.writeBitmapJSON(Bitmap.CompressFormat.PNG, 100, paramOutputStream);
        localOutputStreamWriter.write("}");
        i += 1;
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        paramUIThreadSet = (UIThreadSet<Activity>)localObject;
        if (MPConfig.DEBUG)
        {
          Log.d("MixpanelAPI.Snapshot", "Screenshot interrupted, no screenshot will be sent.", localInterruptedException);
          paramUIThreadSet = (UIThreadSet<Activity>)localObject;
        }
      }
    }
    catch (TimeoutException localTimeoutException)
    {
      for (;;)
      {
        paramUIThreadSet = (UIThreadSet<Activity>)localObject;
        if (MPConfig.DEBUG)
        {
          Log.i("MixpanelAPI.Snapshot", "Screenshot took more than 1 second to be scheduled and executed. No screenshot will be sent.", localTimeoutException);
          paramUIThreadSet = (UIThreadSet<Activity>)localObject;
        }
      }
    }
    catch (ExecutionException localExecutionException)
    {
      for (;;)
      {
        paramUIThreadSet = (UIThreadSet<Activity>)localObject;
        if (MPConfig.DEBUG)
        {
          Log.e("MixpanelAPI.Snapshot", "Exception thrown during screenshot attempt", localExecutionException);
          paramUIThreadSet = (UIThreadSet<Activity>)localObject;
        }
      }
      localOutputStreamWriter.write("]");
      localOutputStreamWriter.flush();
    }
  }
  
  private static class CachedBitmap
  {
    private Bitmap mCached = null;
    private final Paint mPaint = new Paint(2);
    
    /* Error */
    public void recreate(int paramInt1, int paramInt2, int paramInt3, Bitmap paramBitmap)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   6: ifnull +29 -> 35
      //   9: aload_0
      //   10: getfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   13: invokevirtual 35	android/graphics/Bitmap:getWidth	()I
      //   16: iload_1
      //   17: if_icmpne +18 -> 35
      //   20: aload_0
      //   21: getfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   24: invokevirtual 38	android/graphics/Bitmap:getHeight	()I
      //   27: istore 5
      //   29: iload 5
      //   31: iload_2
      //   32: if_icmpeq +30 -> 62
      //   35: aload_0
      //   36: iload_1
      //   37: iload_2
      //   38: getstatic 44	android/graphics/Bitmap$Config:RGB_565	Landroid/graphics/Bitmap$Config;
      //   41: invokestatic 48	android/graphics/Bitmap:createBitmap	(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;
      //   44: putfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   47: aload_0
      //   48: getfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   51: ifnull +11 -> 62
      //   54: aload_0
      //   55: getfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   58: iload_3
      //   59: invokevirtual 51	android/graphics/Bitmap:setDensity	(I)V
      //   62: aload_0
      //   63: getfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   66: ifnull +25 -> 91
      //   69: new 53	android/graphics/Canvas
      //   72: dup
      //   73: aload_0
      //   74: getfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   77: invokespecial 56	android/graphics/Canvas:<init>	(Landroid/graphics/Bitmap;)V
      //   80: aload 4
      //   82: fconst_0
      //   83: fconst_0
      //   84: aload_0
      //   85: getfield 22	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mPaint	Landroid/graphics/Paint;
      //   88: invokevirtual 60	android/graphics/Canvas:drawBitmap	(Landroid/graphics/Bitmap;FFLandroid/graphics/Paint;)V
      //   91: aload_0
      //   92: monitorexit
      //   93: return
      //   94: astore 6
      //   96: aload_0
      //   97: aconst_null
      //   98: putfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   101: goto -54 -> 47
      //   104: astore 4
      //   106: aload_0
      //   107: monitorexit
      //   108: aload 4
      //   110: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	111	0	this	CachedBitmap
      //   0	111	1	paramInt1	int
      //   0	111	2	paramInt2	int
      //   0	111	3	paramInt3	int
      //   0	111	4	paramBitmap	Bitmap
      //   27	6	5	i	int
      //   94	1	6	localOutOfMemoryError	OutOfMemoryError
      // Exception table:
      //   from	to	target	type
      //   35	47	94	java/lang/OutOfMemoryError
      //   2	29	104	finally
      //   35	47	104	finally
      //   47	62	104	finally
      //   62	91	104	finally
      //   96	101	104	finally
    }
    
    /* Error */
    public void writeBitmapJSON(Bitmap.CompressFormat paramCompressFormat, int paramInt, OutputStream paramOutputStream)
      throws IOException
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   6: ifnull +23 -> 29
      //   9: aload_0
      //   10: getfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   13: invokevirtual 35	android/graphics/Bitmap:getWidth	()I
      //   16: ifeq +13 -> 29
      //   19: aload_0
      //   20: getfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   23: invokevirtual 38	android/graphics/Bitmap:getHeight	()I
      //   26: ifne +15 -> 41
      //   29: aload_3
      //   30: ldc 66
      //   32: invokevirtual 72	java/lang/String:getBytes	()[B
      //   35: invokevirtual 78	java/io/OutputStream:write	([B)V
      //   38: aload_0
      //   39: monitorexit
      //   40: return
      //   41: aload_3
      //   42: bipush 34
      //   44: invokevirtual 80	java/io/OutputStream:write	(I)V
      //   47: new 82	android/util/Base64OutputStream
      //   50: dup
      //   51: aload_3
      //   52: iconst_2
      //   53: invokespecial 85	android/util/Base64OutputStream:<init>	(Ljava/io/OutputStream;I)V
      //   56: astore_1
      //   57: aload_0
      //   58: getfield 24	com/mixpanel/android/viewcrawler/ViewSnapshot$CachedBitmap:mCached	Landroid/graphics/Bitmap;
      //   61: getstatic 91	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
      //   64: bipush 100
      //   66: aload_1
      //   67: invokevirtual 95	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
      //   70: pop
      //   71: aload_1
      //   72: invokevirtual 98	android/util/Base64OutputStream:flush	()V
      //   75: aload_3
      //   76: bipush 34
      //   78: invokevirtual 80	java/io/OutputStream:write	(I)V
      //   81: goto -43 -> 38
      //   84: astore_1
      //   85: aload_0
      //   86: monitorexit
      //   87: aload_1
      //   88: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	89	0	this	CachedBitmap
      //   0	89	1	paramCompressFormat	Bitmap.CompressFormat
      //   0	89	2	paramInt	int
      //   0	89	3	paramOutputStream	OutputStream
      // Exception table:
      //   from	to	target	type
      //   2	29	84	finally
      //   29	38	84	finally
      //   41	81	84	finally
    }
  }
  
  private static class ClassNameCache
    extends LruCache<Class<?>, String>
  {
    public ClassNameCache(int paramInt)
    {
      super();
    }
    
    protected String create(Class<?> paramClass)
    {
      return paramClass.getCanonicalName();
    }
  }
  
  private static class RootViewFinder
    implements Callable<List<ViewSnapshot.RootViewInfo>>
  {
    private final ViewSnapshot.CachedBitmap mCachedBitmap = new ViewSnapshot.CachedBitmap();
    private final int mClientDensity = 160;
    private final DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    private UIThreadSet<Activity> mLiveActivities;
    private final List<ViewSnapshot.RootViewInfo> mRootViews = new ArrayList();
    
    private void takeScreenshot(ViewSnapshot.RootViewInfo paramRootViewInfo)
    {
      localView = paramRootViewInfo.rootView;
      localObject6 = null;
      try
      {
        localObject1 = View.class.getDeclaredMethod("createSnapshot", new Class[] { Bitmap.Config.class, Integer.TYPE, Boolean.TYPE });
        ((Method)localObject1).setAccessible(true);
        localObject1 = (Bitmap)((Method)localObject1).invoke(localView, new Object[] { Bitmap.Config.RGB_565, Integer.valueOf(-1), Boolean.valueOf(false) });
        localObject6 = null;
        Boolean localBoolean = null;
        localObject8 = localObject1;
        if (localObject1 == null) {}
        try
        {
          localBoolean = Boolean.valueOf(localView.isDrawingCacheEnabled());
          localObject6 = localBoolean;
          localView.setDrawingCacheEnabled(true);
          localObject6 = localBoolean;
          localView.buildDrawingCache(true);
          localObject6 = localBoolean;
          localObject8 = localView.getDrawingCache();
        }
        catch (RuntimeException localRuntimeException)
        {
          for (;;)
          {
            float f1;
            float f2;
            int i;
            int j;
            int k;
            int m;
            Object localObject2;
            Object localObject3;
            Object localObject4;
            Object localObject5;
            Object localObject7 = localObject6;
            localObject8 = localObject5;
            if (MPConfig.DEBUG)
            {
              Log.v("MixpanelAPI.Snapshot", "Can't take a bitmap snapshot of view " + localView + ", skipping for now.", localRuntimeException);
              localObject7 = localObject6;
              localObject8 = localObject5;
            }
          }
        }
        f1 = 1.0F;
        f2 = f1;
        if (localObject8 != null)
        {
          i = ((Bitmap)localObject8).getDensity();
          if (i != 0) {
            f1 = 160.0F / i;
          }
          i = ((Bitmap)localObject8).getWidth();
          j = ((Bitmap)localObject8).getHeight();
          k = (int)(((Bitmap)localObject8).getWidth() * f1 + 0.5D);
          m = (int)(((Bitmap)localObject8).getHeight() * f1 + 0.5D);
          f2 = f1;
          if (i > 0)
          {
            f2 = f1;
            if (j > 0)
            {
              f2 = f1;
              if (k > 0)
              {
                f2 = f1;
                if (m > 0)
                {
                  this.mCachedBitmap.recreate(k, m, 160, (Bitmap)localObject8);
                  f2 = f1;
                }
              }
            }
          }
        }
        if ((localBoolean != null) && (!localBoolean.booleanValue())) {
          localView.setDrawingCacheEnabled(false);
        }
        paramRootViewInfo.scale = f2;
        paramRootViewInfo.screenshot = this.mCachedBitmap;
        return;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        for (;;)
        {
          Object localObject1 = localObject6;
          if (MPConfig.DEBUG)
          {
            Log.v("MixpanelAPI.Snapshot", "Can't call createSnapshot, will use drawCache", localNoSuchMethodException);
            localObject1 = localObject6;
          }
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        for (;;)
        {
          Log.d("MixpanelAPI.Snapshot", "Can't call createSnapshot with arguments", localIllegalArgumentException);
          localObject2 = localObject6;
        }
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        for (;;)
        {
          Log.e("MixpanelAPI.Snapshot", "Exception when calling createSnapshot", localInvocationTargetException);
          localObject3 = localObject6;
        }
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        for (;;)
        {
          Log.e("MixpanelAPI.Snapshot", "Can't access createSnapshot, using drawCache", localIllegalAccessException);
          localObject4 = localObject6;
        }
      }
      catch (ClassCastException localClassCastException)
      {
        for (;;)
        {
          Log.e("MixpanelAPI.Snapshot", "createSnapshot didn't return a bitmap?", localClassCastException);
          localObject5 = localObject6;
        }
      }
    }
    
    public List<ViewSnapshot.RootViewInfo> call()
      throws Exception
    {
      this.mRootViews.clear();
      Iterator localIterator = this.mLiveActivities.getAll().iterator();
      while (localIterator.hasNext())
      {
        Object localObject = (Activity)localIterator.next();
        String str = localObject.getClass().getCanonicalName();
        View localView = ((Activity)localObject).getWindow().getDecorView().getRootView();
        ((Activity)localObject).getWindowManager().getDefaultDisplay().getMetrics(this.mDisplayMetrics);
        localObject = new ViewSnapshot.RootViewInfo(str, localView);
        this.mRootViews.add(localObject);
      }
      int j = this.mRootViews.size();
      int i = 0;
      while (i < j)
      {
        takeScreenshot((ViewSnapshot.RootViewInfo)this.mRootViews.get(i));
        i += 1;
      }
      return this.mRootViews;
    }
    
    public void findInActivities(UIThreadSet<Activity> paramUIThreadSet)
    {
      this.mLiveActivities = paramUIThreadSet;
    }
  }
  
  private static class RootViewInfo
  {
    public final String activityName;
    public final View rootView;
    public float scale;
    public ViewSnapshot.CachedBitmap screenshot;
    
    public RootViewInfo(String paramString, View paramView)
    {
      this.activityName = paramString;
      this.rootView = paramView;
      this.screenshot = null;
      this.scale = 1.0F;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/viewcrawler/ViewSnapshot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
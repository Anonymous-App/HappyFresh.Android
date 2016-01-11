package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.NetworkInfo;
import android.os.Handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

class BitmapHunter
  implements Runnable
{
  private static final Object DECODE_LOCK = new Object();
  private static final RequestHandler ERRORING_HANDLER = new RequestHandler()
  {
    public boolean canHandleRequest(Request paramAnonymousRequest)
    {
      return true;
    }
    
    public RequestHandler.Result load(Request paramAnonymousRequest)
      throws IOException
    {
      throw new IllegalStateException("Unrecognized type of request: " + paramAnonymousRequest);
    }
  };
  private static final ThreadLocal<StringBuilder> NAME_BUILDER = new ThreadLocal()
  {
    protected StringBuilder initialValue()
    {
      return new StringBuilder("Picasso-");
    }
  };
  private static final AtomicInteger SEQUENCE_GENERATOR = new AtomicInteger();
  Action action;
  List<Action> actions;
  final Cache cache;
  final Request data;
  final Dispatcher dispatcher;
  Exception exception;
  int exifRotation;
  Future<?> future;
  final String key;
  Picasso.LoadedFrom loadedFrom;
  final Picasso picasso;
  Picasso.Priority priority;
  final RequestHandler requestHandler;
  Bitmap result;
  int retryCount;
  final int sequence = SEQUENCE_GENERATOR.incrementAndGet();
  final boolean skipMemoryCache;
  final Stats stats;
  
  BitmapHunter(Picasso paramPicasso, Dispatcher paramDispatcher, Cache paramCache, Stats paramStats, Action paramAction, RequestHandler paramRequestHandler)
  {
    this.picasso = paramPicasso;
    this.dispatcher = paramDispatcher;
    this.cache = paramCache;
    this.stats = paramStats;
    this.action = paramAction;
    this.key = paramAction.getKey();
    this.data = paramAction.getRequest();
    this.priority = paramAction.getPriority();
    this.skipMemoryCache = paramAction.skipCache;
    this.requestHandler = paramRequestHandler;
    this.retryCount = paramRequestHandler.getRetryCount();
  }
  
  static Bitmap applyCustomTransformations(final List<Transformation> paramList, Bitmap paramBitmap)
  {
    int i = 0;
    int j = paramList.size();
    for (;;)
    {
      Bitmap localBitmap = paramBitmap;
      Transformation localTransformation;
      if (i < j)
      {
        localTransformation = (Transformation)paramList.get(i);
        try
        {
          localBitmap = localTransformation.transform(paramBitmap);
          if (localBitmap != null) {
            break label165;
          }
          paramBitmap = new StringBuilder().append("Transformation ").append(localTransformation.key()).append(" returned null after ").append(i).append(" previous transformation(s).\n\nTransformation list:\n");
          paramList = paramList.iterator();
          while (paramList.hasNext())
          {
            paramBitmap.append(((Transformation)paramList.next()).key()).append('\n');
            continue;
            return localBitmap;
          }
        }
        catch (RuntimeException paramList)
        {
          Picasso.HANDLER.post(new Runnable()
          {
            public void run()
            {
              throw new RuntimeException("Transformation " + this.val$transformation.key() + " crashed with exception.", paramList);
            }
          });
          localBitmap = null;
        }
      }
      Picasso.HANDLER.post(new Runnable()
      {
        public void run()
        {
          throw new NullPointerException(this.val$builder.toString());
        }
      });
      return null;
      label165:
      if ((localBitmap == paramBitmap) && (paramBitmap.isRecycled()))
      {
        Picasso.HANDLER.post(new Runnable()
        {
          public void run()
          {
            throw new IllegalStateException("Transformation " + this.val$transformation.key() + " returned input Bitmap but recycled it.");
          }
        });
        return null;
      }
      if ((localBitmap != paramBitmap) && (!paramBitmap.isRecycled()))
      {
        Picasso.HANDLER.post(new Runnable()
        {
          public void run()
          {
            throw new IllegalStateException("Transformation " + this.val$transformation.key() + " mutated input Bitmap but failed to recycle the original.");
          }
        });
        return null;
      }
      paramBitmap = localBitmap;
      i += 1;
    }
  }
  
  private Picasso.Priority computeNewPriority()
  {
    Object localObject1 = Picasso.Priority.LOW;
    int i;
    if ((this.actions != null) && (!this.actions.isEmpty()))
    {
      i = 1;
      if ((this.action == null) && (i == 0)) {
        break label49;
      }
    }
    label49:
    for (int j = 1;; j = 0)
    {
      if (j != 0) {
        break label54;
      }
      return (Picasso.Priority)localObject1;
      i = 0;
      break;
    }
    label54:
    if (this.action != null) {
      localObject1 = this.action.getPriority();
    }
    Object localObject2 = localObject1;
    if (i != 0)
    {
      i = 0;
      j = this.actions.size();
      for (;;)
      {
        localObject2 = localObject1;
        if (i >= j) {
          break;
        }
        Picasso.Priority localPriority = ((Action)this.actions.get(i)).getPriority();
        localObject2 = localObject1;
        if (localPriority.ordinal() > ((Picasso.Priority)localObject1).ordinal()) {
          localObject2 = localPriority;
        }
        i += 1;
        localObject1 = localObject2;
      }
    }
    return (Picasso.Priority)localObject2;
  }
  
  static BitmapHunter forRequest(Picasso paramPicasso, Dispatcher paramDispatcher, Cache paramCache, Stats paramStats, Action paramAction)
  {
    Request localRequest = paramAction.getRequest();
    List localList = paramPicasso.getRequestHandlers();
    int i = 0;
    int j = localList.size();
    while (i < j)
    {
      RequestHandler localRequestHandler = (RequestHandler)localList.get(i);
      if (localRequestHandler.canHandleRequest(localRequest)) {
        return new BitmapHunter(paramPicasso, paramDispatcher, paramCache, paramStats, paramAction, localRequestHandler);
      }
      i += 1;
    }
    return new BitmapHunter(paramPicasso, paramDispatcher, paramCache, paramStats, paramAction, ERRORING_HANDLER);
  }
  
  static Bitmap transformResult(Request paramRequest, Bitmap paramBitmap, int paramInt)
  {
    int i4 = paramBitmap.getWidth();
    int i5 = paramBitmap.getHeight();
    int i6 = 0;
    int i2 = 0;
    int i7 = 0;
    int i3 = 0;
    int j = i4;
    int i = i5;
    Object localObject = new Matrix();
    int i1 = i6;
    int k = i7;
    int m = j;
    int n = i;
    int i8;
    int i9;
    float f1;
    float f3;
    if (paramRequest.needsMatrixTransform())
    {
      i8 = paramRequest.targetWidth;
      i9 = paramRequest.targetHeight;
      f1 = paramRequest.rotationDegrees;
      if (f1 != 0.0F)
      {
        if (!paramRequest.hasRotationPivot) {
          break label239;
        }
        ((Matrix)localObject).setRotate(f1, paramRequest.rotationPivotX, paramRequest.rotationPivotY);
      }
      if (!paramRequest.centerCrop) {
        break label283;
      }
      f2 = i8 / i4;
      f3 = i9 / i5;
      if (f2 <= f3) {
        break label248;
      }
      f1 = f2;
      i = (int)Math.ceil(i5 * (f3 / f2));
      k = (i5 - i) / 2;
      m = j;
      j = i2;
      label177:
      ((Matrix)localObject).preScale(f1, f1);
      n = i;
      i1 = j;
    }
    label239:
    label248:
    label283:
    label373:
    do
    {
      do
      {
        if (paramInt != 0) {
          ((Matrix)localObject).preRotate(paramInt);
        }
        localObject = Bitmap.createBitmap(paramBitmap, i1, k, m, n, (Matrix)localObject, true);
        paramRequest = paramBitmap;
        if (localObject != paramBitmap)
        {
          paramBitmap.recycle();
          paramRequest = (Request)localObject;
        }
        return paramRequest;
        ((Matrix)localObject).setRotate(f1);
        break;
        f1 = f3;
        m = (int)Math.ceil(i4 * (f2 / f3));
        j = (i4 - m) / 2;
        k = i3;
        break label177;
        if (paramRequest.centerInside)
        {
          f1 = i8 / i4;
          f2 = i9 / i5;
          if (f1 < f2) {}
          for (;;)
          {
            ((Matrix)localObject).preScale(f1, f1);
            i1 = i6;
            k = i7;
            m = j;
            n = i;
            break;
            f1 = f2;
          }
        }
        if (i8 != 0) {
          break label373;
        }
        i1 = i6;
        k = i7;
        m = j;
        n = i;
      } while (i9 == 0);
      if (i8 != i4) {
        break label403;
      }
      i1 = i6;
      k = i7;
      m = j;
      n = i;
    } while (i9 == i5);
    label403:
    if (i8 != 0)
    {
      f1 = i8 / i4;
      label416:
      if (i9 == 0) {
        break label469;
      }
    }
    label469:
    for (float f2 = i9 / i5;; f2 = i8 / i4)
    {
      ((Matrix)localObject).preScale(f1, f2);
      i1 = i6;
      k = i7;
      m = j;
      n = i;
      break;
      f1 = i9 / i5;
      break label416;
    }
  }
  
  static void updateThreadName(Request paramRequest)
  {
    paramRequest = paramRequest.getName();
    StringBuilder localStringBuilder = (StringBuilder)NAME_BUILDER.get();
    localStringBuilder.ensureCapacity("Picasso-".length() + paramRequest.length());
    localStringBuilder.replace("Picasso-".length(), localStringBuilder.length(), paramRequest);
    Thread.currentThread().setName(localStringBuilder.toString());
  }
  
  void attach(Action paramAction)
  {
    boolean bool = this.picasso.loggingEnabled;
    Request localRequest = paramAction.request;
    if (this.action == null)
    {
      this.action = paramAction;
      if (bool)
      {
        if ((this.actions != null) && (!this.actions.isEmpty())) {
          break label65;
        }
        Utils.log("Hunter", "joined", localRequest.logId(), "to empty hunter");
      }
    }
    label65:
    do
    {
      return;
      Utils.log("Hunter", "joined", localRequest.logId(), Utils.getLogIdsForHunter(this, "to "));
      return;
      if (this.actions == null) {
        this.actions = new ArrayList(3);
      }
      this.actions.add(paramAction);
      if (bool) {
        Utils.log("Hunter", "joined", localRequest.logId(), Utils.getLogIdsForHunter(this, "to "));
      }
      paramAction = paramAction.getPriority();
    } while (paramAction.ordinal() <= this.priority.ordinal());
    this.priority = paramAction;
  }
  
  boolean cancel()
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (this.action == null) {
      if (this.actions != null)
      {
        bool1 = bool2;
        if (!this.actions.isEmpty()) {}
      }
      else
      {
        bool1 = bool2;
        if (this.future != null)
        {
          bool1 = bool2;
          if (this.future.cancel(false)) {
            bool1 = true;
          }
        }
      }
    }
    return bool1;
  }
  
  void detach(Action paramAction)
  {
    boolean bool = false;
    if (this.action == paramAction)
    {
      this.action = null;
      bool = true;
    }
    for (;;)
    {
      if ((bool) && (paramAction.getPriority() == this.priority)) {
        this.priority = computeNewPriority();
      }
      if (this.picasso.loggingEnabled) {
        Utils.log("Hunter", "removed", paramAction.request.logId(), Utils.getLogIdsForHunter(this, "from "));
      }
      return;
      if (this.actions != null) {
        bool = this.actions.remove(paramAction);
      }
    }
  }
  
  Action getAction()
  {
    return this.action;
  }
  
  List<Action> getActions()
  {
    return this.actions;
  }
  
  Request getData()
  {
    return this.data;
  }
  
  Exception getException()
  {
    return this.exception;
  }
  
  String getKey()
  {
    return this.key;
  }
  
  Picasso.LoadedFrom getLoadedFrom()
  {
    return this.loadedFrom;
  }
  
  Picasso getPicasso()
  {
    return this.picasso;
  }
  
  Picasso.Priority getPriority()
  {
    return this.priority;
  }
  
  Bitmap getResult()
  {
    return this.result;
  }
  
  Bitmap hunt()
    throws IOException
  {
    Object localObject1 = null;
    if (!this.skipMemoryCache)
    {
      localObject3 = this.cache.get(this.key);
      localObject1 = localObject3;
      if (localObject3 != null)
      {
        this.stats.dispatchCacheHit();
        this.loadedFrom = Picasso.LoadedFrom.MEMORY;
        if (this.picasso.loggingEnabled) {
          Utils.log("Hunter", "decoded", this.data.logId(), "from cache");
        }
        return (Bitmap)localObject3;
      }
    }
    Object localObject3 = this.data;
    boolean bool;
    if (this.retryCount == 0) {
      bool = true;
    }
    for (;;)
    {
      ((Request)localObject3).loadFromLocalCacheOnly = bool;
      localObject3 = this.requestHandler.load(this.data);
      if (localObject3 != null)
      {
        localObject1 = ((RequestHandler.Result)localObject3).getBitmap();
        this.loadedFrom = ((RequestHandler.Result)localObject3).getLoadedFrom();
        this.exifRotation = ((RequestHandler.Result)localObject3).getExifOrientation();
      }
      localObject3 = localObject1;
      if (localObject1 != null)
      {
        if (this.picasso.loggingEnabled) {
          Utils.log("Hunter", "decoded", this.data.logId());
        }
        this.stats.dispatchBitmapDecoded((Bitmap)localObject1);
        if (!this.data.needsTransformation())
        {
          localObject3 = localObject1;
          if (this.exifRotation == 0) {
            break label335;
          }
        }
      }
      synchronized (DECODE_LOCK)
      {
        if (!this.data.needsMatrixTransform())
        {
          localObject3 = localObject1;
          if (this.exifRotation == 0) {}
        }
        else
        {
          localObject1 = transformResult(this.data, (Bitmap)localObject1, this.exifRotation);
          localObject3 = localObject1;
          if (this.picasso.loggingEnabled)
          {
            Utils.log("Hunter", "transformed", this.data.logId());
            localObject3 = localObject1;
          }
        }
        localObject1 = localObject3;
        if (this.data.hasCustomTransformations())
        {
          localObject3 = applyCustomTransformations(this.data.transformations, (Bitmap)localObject3);
          localObject1 = localObject3;
          if (this.picasso.loggingEnabled)
          {
            Utils.log("Hunter", "transformed", this.data.logId(), "from custom transformations");
            localObject1 = localObject3;
          }
        }
        localObject3 = localObject1;
        if (localObject1 != null)
        {
          this.stats.dispatchBitmapTransformed((Bitmap)localObject1);
          localObject3 = localObject1;
        }
        label335:
        return (Bitmap)localObject3;
        bool = false;
      }
    }
  }
  
  boolean isCancelled()
  {
    return (this.future != null) && (this.future.isCancelled());
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 112	com/squareup/picasso/BitmapHunter:data	Lcom/squareup/picasso/Request;
    //   4: invokestatic 503	com/squareup/picasso/BitmapHunter:updateThreadName	(Lcom/squareup/picasso/Request;)V
    //   7: aload_0
    //   8: getfield 90	com/squareup/picasso/BitmapHunter:picasso	Lcom/squareup/picasso/Picasso;
    //   11: getfield 353	com/squareup/picasso/Picasso:loggingEnabled	Z
    //   14: ifeq +16 -> 30
    //   17: ldc_w 358
    //   20: ldc_w 505
    //   23: aload_0
    //   24: invokestatic 508	com/squareup/picasso/Utils:getLogIdsForHunter	(Lcom/squareup/picasso/BitmapHunter;)Ljava/lang/String;
    //   27: invokestatic 466	com/squareup/picasso/Utils:log	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   30: aload_0
    //   31: aload_0
    //   32: invokevirtual 510	com/squareup/picasso/BitmapHunter:hunt	()Landroid/graphics/Bitmap;
    //   35: putfield 422	com/squareup/picasso/BitmapHunter:result	Landroid/graphics/Bitmap;
    //   38: aload_0
    //   39: getfield 422	com/squareup/picasso/BitmapHunter:result	Landroid/graphics/Bitmap;
    //   42: ifnonnull +21 -> 63
    //   45: aload_0
    //   46: getfield 92	com/squareup/picasso/BitmapHunter:dispatcher	Lcom/squareup/picasso/Dispatcher;
    //   49: aload_0
    //   50: invokevirtual 516	com/squareup/picasso/Dispatcher:dispatchFailed	(Lcom/squareup/picasso/BitmapHunter;)V
    //   53: invokestatic 341	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   56: ldc_w 518
    //   59: invokevirtual 348	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   62: return
    //   63: aload_0
    //   64: getfield 92	com/squareup/picasso/BitmapHunter:dispatcher	Lcom/squareup/picasso/Dispatcher;
    //   67: aload_0
    //   68: invokevirtual 521	com/squareup/picasso/Dispatcher:dispatchComplete	(Lcom/squareup/picasso/BitmapHunter;)V
    //   71: goto -18 -> 53
    //   74: astore_1
    //   75: aload_0
    //   76: aload_1
    //   77: putfield 412	com/squareup/picasso/BitmapHunter:exception	Ljava/lang/Exception;
    //   80: aload_0
    //   81: getfield 92	com/squareup/picasso/BitmapHunter:dispatcher	Lcom/squareup/picasso/Dispatcher;
    //   84: aload_0
    //   85: invokevirtual 516	com/squareup/picasso/Dispatcher:dispatchFailed	(Lcom/squareup/picasso/BitmapHunter;)V
    //   88: invokestatic 341	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   91: ldc_w 518
    //   94: invokevirtual 348	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   97: return
    //   98: astore_1
    //   99: aload_0
    //   100: aload_1
    //   101: putfield 412	com/squareup/picasso/BitmapHunter:exception	Ljava/lang/Exception;
    //   104: aload_0
    //   105: getfield 92	com/squareup/picasso/BitmapHunter:dispatcher	Lcom/squareup/picasso/Dispatcher;
    //   108: aload_0
    //   109: invokevirtual 524	com/squareup/picasso/Dispatcher:dispatchRetry	(Lcom/squareup/picasso/BitmapHunter;)V
    //   112: invokestatic 341	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   115: ldc_w 518
    //   118: invokevirtual 348	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   121: return
    //   122: astore_1
    //   123: new 526	java/io/StringWriter
    //   126: dup
    //   127: invokespecial 527	java/io/StringWriter:<init>	()V
    //   130: astore_2
    //   131: aload_0
    //   132: getfield 96	com/squareup/picasso/BitmapHunter:stats	Lcom/squareup/picasso/Stats;
    //   135: invokevirtual 531	com/squareup/picasso/Stats:createSnapshot	()Lcom/squareup/picasso/StatsSnapshot;
    //   138: new 533	java/io/PrintWriter
    //   141: dup
    //   142: aload_2
    //   143: invokespecial 536	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   146: invokevirtual 542	com/squareup/picasso/StatsSnapshot:dump	(Ljava/io/PrintWriter;)V
    //   149: aload_0
    //   150: new 136	java/lang/RuntimeException
    //   153: dup
    //   154: aload_2
    //   155: invokevirtual 543	java/io/StringWriter:toString	()Ljava/lang/String;
    //   158: aload_1
    //   159: invokespecial 546	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   162: putfield 412	com/squareup/picasso/BitmapHunter:exception	Ljava/lang/Exception;
    //   165: aload_0
    //   166: getfield 92	com/squareup/picasso/BitmapHunter:dispatcher	Lcom/squareup/picasso/Dispatcher;
    //   169: aload_0
    //   170: invokevirtual 516	com/squareup/picasso/Dispatcher:dispatchFailed	(Lcom/squareup/picasso/BitmapHunter;)V
    //   173: invokestatic 341	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   176: ldc_w 518
    //   179: invokevirtual 348	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   182: return
    //   183: astore_1
    //   184: aload_0
    //   185: aload_1
    //   186: putfield 412	com/squareup/picasso/BitmapHunter:exception	Ljava/lang/Exception;
    //   189: aload_0
    //   190: getfield 92	com/squareup/picasso/BitmapHunter:dispatcher	Lcom/squareup/picasso/Dispatcher;
    //   193: aload_0
    //   194: invokevirtual 516	com/squareup/picasso/Dispatcher:dispatchFailed	(Lcom/squareup/picasso/BitmapHunter;)V
    //   197: invokestatic 341	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   200: ldc_w 518
    //   203: invokevirtual 348	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   206: return
    //   207: astore_1
    //   208: invokestatic 341	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   211: ldc_w 518
    //   214: invokevirtual 348	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   217: aload_1
    //   218: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	219	0	this	BitmapHunter
    //   74	3	1	localResponseException	Downloader.ResponseException
    //   98	3	1	localIOException	IOException
    //   122	37	1	localOutOfMemoryError	OutOfMemoryError
    //   183	3	1	localException	Exception
    //   207	11	1	localObject	Object
    //   130	25	2	localStringWriter	java.io.StringWriter
    // Exception table:
    //   from	to	target	type
    //   0	30	74	com/squareup/picasso/Downloader$ResponseException
    //   30	53	74	com/squareup/picasso/Downloader$ResponseException
    //   63	71	74	com/squareup/picasso/Downloader$ResponseException
    //   0	30	98	java/io/IOException
    //   30	53	98	java/io/IOException
    //   63	71	98	java/io/IOException
    //   0	30	122	java/lang/OutOfMemoryError
    //   30	53	122	java/lang/OutOfMemoryError
    //   63	71	122	java/lang/OutOfMemoryError
    //   0	30	183	java/lang/Exception
    //   30	53	183	java/lang/Exception
    //   63	71	183	java/lang/Exception
    //   0	30	207	finally
    //   30	53	207	finally
    //   63	71	207	finally
    //   75	88	207	finally
    //   99	112	207	finally
    //   123	173	207	finally
    //   184	197	207	finally
  }
  
  boolean shouldRetry(boolean paramBoolean, NetworkInfo paramNetworkInfo)
  {
    if (this.retryCount > 0) {}
    for (int i = 1; i == 0; i = 0) {
      return false;
    }
    this.retryCount -= 1;
    return this.requestHandler.shouldRetry(paramBoolean, paramNetworkInfo);
  }
  
  boolean shouldSkipMemoryCache()
  {
    return this.skipMemoryCache;
  }
  
  boolean supportsReplay()
  {
    return this.requestHandler.supportsReplay();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/picasso/BitmapHunter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
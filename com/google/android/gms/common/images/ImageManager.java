package com.google.android.gms.common.images;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.internal.zzkj;
import com.google.android.gms.internal.zzku;
import com.google.android.gms.internal.zzlk;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager
{
  private static final Object zzYN = new Object();
  private static HashSet<Uri> zzYO = new HashSet();
  private static ImageManager zzYP;
  private static ImageManager zzYQ;
  private final Context mContext;
  private final Handler mHandler;
  private final ExecutorService zzYR;
  private final zzb zzYS;
  private final zzkj zzYT;
  private final Map<zza, ImageReceiver> zzYU;
  private final Map<Uri, ImageReceiver> zzYV;
  private final Map<Uri, Long> zzYW;
  
  private ImageManager(Context paramContext, boolean paramBoolean)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mHandler = new Handler(Looper.getMainLooper());
    this.zzYR = Executors.newFixedThreadPool(4);
    if (paramBoolean)
    {
      this.zzYS = new zzb(this.mContext);
      if (zzlk.zzoU()) {
        zznl();
      }
    }
    for (;;)
    {
      this.zzYT = new zzkj();
      this.zzYU = new HashMap();
      this.zzYV = new HashMap();
      this.zzYW = new HashMap();
      return;
      this.zzYS = null;
    }
  }
  
  public static ImageManager create(Context paramContext)
  {
    return zzb(paramContext, false);
  }
  
  private Bitmap zza(zza.zza paramzza)
  {
    if (this.zzYS == null) {
      return null;
    }
    return (Bitmap)this.zzYS.get(paramzza);
  }
  
  public static ImageManager zzb(Context paramContext, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (zzYQ == null) {
        zzYQ = new ImageManager(paramContext, true);
      }
      return zzYQ;
    }
    if (zzYP == null) {
      zzYP = new ImageManager(paramContext, false);
    }
    return zzYP;
  }
  
  private void zznl()
  {
    this.mContext.registerComponentCallbacks(new zze(this.zzYS));
  }
  
  public void loadImage(ImageView paramImageView, int paramInt)
  {
    zza(new zza.zzb(paramImageView, paramInt));
  }
  
  public void loadImage(ImageView paramImageView, Uri paramUri)
  {
    zza(new zza.zzb(paramImageView, paramUri));
  }
  
  public void loadImage(ImageView paramImageView, Uri paramUri, int paramInt)
  {
    paramImageView = new zza.zzb(paramImageView, paramUri);
    paramImageView.zzbm(paramInt);
    zza(paramImageView);
  }
  
  public void loadImage(OnImageLoadedListener paramOnImageLoadedListener, Uri paramUri)
  {
    zza(new zza.zzc(paramOnImageLoadedListener, paramUri));
  }
  
  public void loadImage(OnImageLoadedListener paramOnImageLoadedListener, Uri paramUri, int paramInt)
  {
    paramOnImageLoadedListener = new zza.zzc(paramOnImageLoadedListener, paramUri);
    paramOnImageLoadedListener.zzbm(paramInt);
    zza(paramOnImageLoadedListener);
  }
  
  public void zza(zza paramzza)
  {
    zzb.zzbY("ImageManager.loadImage() must be called in the main thread");
    new zzd(paramzza).run();
  }
  
  private final class ImageReceiver
    extends ResultReceiver
  {
    private final Uri mUri;
    private final ArrayList<zza> zzYX;
    
    ImageReceiver(Uri paramUri)
    {
      super();
      this.mUri = paramUri;
      this.zzYX = new ArrayList();
    }
    
    public void onReceiveResult(int paramInt, Bundle paramBundle)
    {
      paramBundle = (ParcelFileDescriptor)paramBundle.getParcelable("com.google.android.gms.extra.fileDescriptor");
      ImageManager.zzf(ImageManager.this).execute(new ImageManager.zzc(ImageManager.this, this.mUri, paramBundle));
    }
    
    public void zzb(zza paramzza)
    {
      zzb.zzbY("ImageReceiver.addImageRequest() must be called in the main thread");
      this.zzYX.add(paramzza);
    }
    
    public void zzc(zza paramzza)
    {
      zzb.zzbY("ImageReceiver.removeImageRequest() must be called in the main thread");
      this.zzYX.remove(paramzza);
    }
    
    public void zzno()
    {
      Intent localIntent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
      localIntent.putExtra("com.google.android.gms.extras.uri", this.mUri);
      localIntent.putExtra("com.google.android.gms.extras.resultReceiver", this);
      localIntent.putExtra("com.google.android.gms.extras.priority", 3);
      ImageManager.zzb(ImageManager.this).sendBroadcast(localIntent);
    }
  }
  
  public static abstract interface OnImageLoadedListener
  {
    public abstract void onImageLoaded(Uri paramUri, Drawable paramDrawable, boolean paramBoolean);
  }
  
  private static final class zza
  {
    static int zza(ActivityManager paramActivityManager)
    {
      return paramActivityManager.getLargeMemoryClass();
    }
  }
  
  private static final class zzb
    extends zzku<zza.zza, Bitmap>
  {
    public zzb(Context paramContext)
    {
      super();
    }
    
    private static int zzag(Context paramContext)
    {
      ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
      if ((paramContext.getApplicationInfo().flags & 0x100000) != 0)
      {
        i = 1;
        if ((i == 0) || (!zzlk.zzoR())) {
          break label55;
        }
      }
      label55:
      for (int i = ImageManager.zza.zza(localActivityManager);; i = localActivityManager.getMemoryClass())
      {
        return (int)(i * 1048576 * 0.33F);
        i = 0;
        break;
      }
    }
    
    protected int zza(zza.zza paramzza, Bitmap paramBitmap)
    {
      return paramBitmap.getHeight() * paramBitmap.getRowBytes();
    }
    
    protected void zza(boolean paramBoolean, zza.zza paramzza, Bitmap paramBitmap1, Bitmap paramBitmap2)
    {
      super.entryRemoved(paramBoolean, paramzza, paramBitmap1, paramBitmap2);
    }
  }
  
  private final class zzc
    implements Runnable
  {
    private final Uri mUri;
    private final ParcelFileDescriptor zzYZ;
    
    public zzc(Uri paramUri, ParcelFileDescriptor paramParcelFileDescriptor)
    {
      this.mUri = paramUri;
      this.zzYZ = paramParcelFileDescriptor;
    }
    
    public void run()
    {
      zzb.zzbZ("LoadBitmapFromDiskRunnable can't be executed in the main thread");
      boolean bool1 = false;
      boolean bool2 = false;
      Bitmap localBitmap = null;
      CountDownLatch localCountDownLatch = null;
      if (this.zzYZ != null) {}
      try
      {
        localBitmap = BitmapFactory.decodeFileDescriptor(this.zzYZ.getFileDescriptor());
        bool1 = bool2;
        Object localObject;
        return;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        try
        {
          for (;;)
          {
            this.zzYZ.close();
            localCountDownLatch = new CountDownLatch(1);
            ImageManager.zzg(ImageManager.this).post(new ImageManager.zzf(ImageManager.this, this.mUri, localBitmap, bool1, localCountDownLatch));
            try
            {
              localCountDownLatch.await();
              return;
            }
            catch (InterruptedException localInterruptedException)
            {
              Log.w("ImageManager", "Latch interrupted while posting " + this.mUri);
            }
            localOutOfMemoryError = localOutOfMemoryError;
            Log.e("ImageManager", "OOM while loading bitmap for uri: " + this.mUri, localOutOfMemoryError);
            bool1 = true;
            localObject = localCountDownLatch;
          }
        }
        catch (IOException localIOException)
        {
          for (;;)
          {
            Log.e("ImageManager", "closed failed", localIOException);
          }
        }
      }
    }
  }
  
  private final class zzd
    implements Runnable
  {
    private final zza zzZa;
    
    public zzd(zza paramzza)
    {
      this.zzZa = paramzza;
    }
    
    public void run()
    {
      zzb.zzbY("LoadImageRunnable must be executed on the main thread");
      Object localObject1 = (ImageManager.ImageReceiver)ImageManager.zza(ImageManager.this).get(this.zzZa);
      if (localObject1 != null)
      {
        ImageManager.zza(ImageManager.this).remove(this.zzZa);
        ((ImageManager.ImageReceiver)localObject1).zzc(this.zzZa);
      }
      zza.zza localzza = this.zzZa.zzZc;
      if (localzza.uri == null)
      {
        this.zzZa.zza(ImageManager.zzb(ImageManager.this), ImageManager.zzc(ImageManager.this), true);
        return;
      }
      localObject1 = ImageManager.zza(ImageManager.this, localzza);
      if (localObject1 != null)
      {
        this.zzZa.zza(ImageManager.zzb(ImageManager.this), (Bitmap)localObject1, true);
        return;
      }
      localObject1 = (Long)ImageManager.zzd(ImageManager.this).get(localzza.uri);
      if (localObject1 != null)
      {
        if (SystemClock.elapsedRealtime() - ((Long)localObject1).longValue() < 3600000L)
        {
          this.zzZa.zza(ImageManager.zzb(ImageManager.this), ImageManager.zzc(ImageManager.this), true);
          return;
        }
        ImageManager.zzd(ImageManager.this).remove(localzza.uri);
      }
      this.zzZa.zza(ImageManager.zzb(ImageManager.this), ImageManager.zzc(ImageManager.this));
      ??? = (ImageManager.ImageReceiver)ImageManager.zze(ImageManager.this).get(localzza.uri);
      localObject1 = ???;
      if (??? == null)
      {
        localObject1 = new ImageManager.ImageReceiver(ImageManager.this, localzza.uri);
        ImageManager.zze(ImageManager.this).put(localzza.uri, localObject1);
      }
      ((ImageManager.ImageReceiver)localObject1).zzb(this.zzZa);
      if (!(this.zzZa instanceof zza.zzc)) {
        ImageManager.zza(ImageManager.this).put(this.zzZa, localObject1);
      }
      synchronized (ImageManager.zznm())
      {
        if (!ImageManager.zznn().contains(localzza.uri))
        {
          ImageManager.zznn().add(localzza.uri);
          ((ImageManager.ImageReceiver)localObject1).zzno();
        }
        return;
      }
    }
  }
  
  private static final class zze
    implements ComponentCallbacks2
  {
    private final ImageManager.zzb zzYS;
    
    public zze(ImageManager.zzb paramzzb)
    {
      this.zzYS = paramzzb;
    }
    
    public void onConfigurationChanged(Configuration paramConfiguration) {}
    
    public void onLowMemory()
    {
      this.zzYS.evictAll();
    }
    
    public void onTrimMemory(int paramInt)
    {
      if (paramInt >= 60) {
        this.zzYS.evictAll();
      }
      while (paramInt < 20) {
        return;
      }
      this.zzYS.trimToSize(this.zzYS.size() / 2);
    }
  }
  
  private final class zzf
    implements Runnable
  {
    private final Bitmap mBitmap;
    private final Uri mUri;
    private boolean zzZb;
    private final CountDownLatch zzoD;
    
    public zzf(Uri paramUri, Bitmap paramBitmap, boolean paramBoolean, CountDownLatch paramCountDownLatch)
    {
      this.mUri = paramUri;
      this.mBitmap = paramBitmap;
      this.zzZb = paramBoolean;
      this.zzoD = paramCountDownLatch;
    }
    
    private void zza(ImageManager.ImageReceiver paramImageReceiver, boolean paramBoolean)
    {
      paramImageReceiver = ImageManager.ImageReceiver.zza(paramImageReceiver);
      int j = paramImageReceiver.size();
      int i = 0;
      if (i < j)
      {
        zza localzza = (zza)paramImageReceiver.get(i);
        if (paramBoolean) {
          localzza.zza(ImageManager.zzb(ImageManager.this), this.mBitmap, false);
        }
        for (;;)
        {
          if (!(localzza instanceof zza.zzc)) {
            ImageManager.zza(ImageManager.this).remove(localzza);
          }
          i += 1;
          break;
          ImageManager.zzd(ImageManager.this).put(this.mUri, Long.valueOf(SystemClock.elapsedRealtime()));
          localzza.zza(ImageManager.zzb(ImageManager.this), ImageManager.zzc(ImageManager.this), false);
        }
      }
    }
    
    public void run()
    {
      zzb.zzbY("OnBitmapLoadedRunnable must be executed in the main thread");
      boolean bool;
      if (this.mBitmap != null) {
        bool = true;
      }
      while (ImageManager.zzh(ImageManager.this) != null) {
        if (this.zzZb)
        {
          ImageManager.zzh(ImageManager.this).evictAll();
          System.gc();
          this.zzZb = false;
          ImageManager.zzg(ImageManager.this).post(this);
          return;
          bool = false;
        }
        else if (bool)
        {
          ImageManager.zzh(ImageManager.this).put(new zza.zza(this.mUri), this.mBitmap);
        }
      }
      ??? = (ImageManager.ImageReceiver)ImageManager.zze(ImageManager.this).remove(this.mUri);
      if (??? != null) {
        zza((ImageManager.ImageReceiver)???, bool);
      }
      this.zzoD.countDown();
      synchronized (ImageManager.zznm())
      {
        ImageManager.zznn().remove(this.mUri);
        return;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/images/ImageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
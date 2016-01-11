package com.google.android.gms.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.internal.zzkg;
import com.google.android.gms.internal.zzkh;
import com.google.android.gms.internal.zzki;
import com.google.android.gms.internal.zzkj;
import com.google.android.gms.internal.zzkj.zza;
import java.lang.ref.WeakReference;

public abstract class zza
{
  final zza zzZc;
  protected int zzZd = 0;
  protected int zzZe = 0;
  protected boolean zzZf = false;
  protected ImageManager.OnImageLoadedListener zzZg;
  private boolean zzZh = true;
  private boolean zzZi = false;
  private boolean zzZj = true;
  protected int zzZk;
  
  public zza(Uri paramUri, int paramInt)
  {
    this.zzZc = new zza(paramUri);
    this.zzZe = paramInt;
  }
  
  private Drawable zza(Context paramContext, zzkj paramzzkj, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    if (this.zzZk > 0)
    {
      zzkj.zza localzza = new zzkj.zza(paramInt, this.zzZk);
      Drawable localDrawable = (Drawable)paramzzkj.get(localzza);
      paramContext = localDrawable;
      if (localDrawable == null)
      {
        localDrawable = localResources.getDrawable(paramInt);
        paramContext = localDrawable;
        if ((this.zzZk & 0x1) != 0) {
          paramContext = zza(localResources, localDrawable);
        }
        paramzzkj.put(localzza, paramContext);
      }
      return paramContext;
    }
    return localResources.getDrawable(paramInt);
  }
  
  protected Drawable zza(Resources paramResources, Drawable paramDrawable)
  {
    return zzkh.zza(paramResources, paramDrawable);
  }
  
  protected zzkg zza(Drawable paramDrawable1, Drawable paramDrawable2)
  {
    if (paramDrawable1 != null)
    {
      localDrawable = paramDrawable1;
      if (!(paramDrawable1 instanceof zzkg)) {}
    }
    for (Drawable localDrawable = ((zzkg)paramDrawable1).zznp();; localDrawable = null) {
      return new zzkg(localDrawable, paramDrawable2);
    }
  }
  
  void zza(Context paramContext, Bitmap paramBitmap, boolean paramBoolean)
  {
    zzb.zzq(paramBitmap);
    Bitmap localBitmap = paramBitmap;
    if ((this.zzZk & 0x1) != 0) {
      localBitmap = zzkh.zza(paramBitmap);
    }
    paramContext = new BitmapDrawable(paramContext.getResources(), localBitmap);
    if (this.zzZg != null) {
      this.zzZg.onImageLoaded(this.zzZc.uri, paramContext, true);
    }
    zza(paramContext, paramBoolean, false, true);
  }
  
  void zza(Context paramContext, zzkj paramzzkj)
  {
    if (this.zzZj)
    {
      Drawable localDrawable = null;
      if (this.zzZd != 0) {
        localDrawable = zza(paramContext, paramzzkj, this.zzZd);
      }
      zza(localDrawable, false, true, false);
    }
  }
  
  void zza(Context paramContext, zzkj paramzzkj, boolean paramBoolean)
  {
    Drawable localDrawable = null;
    if (this.zzZe != 0) {
      localDrawable = zza(paramContext, paramzzkj, this.zzZe);
    }
    if (this.zzZg != null) {
      this.zzZg.onImageLoaded(this.zzZc.uri, localDrawable, false);
    }
    zza(localDrawable, paramBoolean, false, false);
  }
  
  protected abstract void zza(Drawable paramDrawable, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  protected boolean zzb(boolean paramBoolean1, boolean paramBoolean2)
  {
    return (this.zzZh) && (!paramBoolean2) && ((!paramBoolean1) || (this.zzZi));
  }
  
  public void zzbm(int paramInt)
  {
    this.zzZe = paramInt;
  }
  
  static final class zza
  {
    public final Uri uri;
    
    public zza(Uri paramUri)
    {
      this.uri = paramUri;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof zza)) {
        return false;
      }
      if (this == paramObject) {
        return true;
      }
      return zzt.equal(((zza)paramObject).uri, this.uri);
    }
    
    public int hashCode()
    {
      return zzt.hashCode(new Object[] { this.uri });
    }
  }
  
  public static final class zzb
    extends zza
  {
    private WeakReference<ImageView> zzZl;
    
    public zzb(ImageView paramImageView, int paramInt)
    {
      super(paramInt);
      zzb.zzq(paramImageView);
      this.zzZl = new WeakReference(paramImageView);
    }
    
    public zzb(ImageView paramImageView, Uri paramUri)
    {
      super(0);
      zzb.zzq(paramImageView);
      this.zzZl = new WeakReference(paramImageView);
    }
    
    private void zza(ImageView paramImageView, Drawable paramDrawable, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      if ((!paramBoolean2) && (!paramBoolean3)) {}
      for (int i = 1; (i != 0) && ((paramImageView instanceof zzki)); i = 0)
      {
        int j = ((zzki)paramImageView).zznr();
        if ((this.zzZe == 0) || (j != this.zzZe)) {
          break;
        }
        return;
      }
      paramBoolean1 = zzb(paramBoolean1, paramBoolean2);
      if ((this.zzZf) && (paramDrawable != null)) {
        paramDrawable = paramDrawable.getConstantState().newDrawable();
      }
      for (;;)
      {
        Object localObject = paramDrawable;
        if (paramBoolean1) {
          localObject = zza(paramImageView.getDrawable(), paramDrawable);
        }
        paramImageView.setImageDrawable((Drawable)localObject);
        if ((paramImageView instanceof zzki))
        {
          paramDrawable = (zzki)paramImageView;
          if (!paramBoolean3) {
            break label171;
          }
          paramImageView = this.zzZc.uri;
          label133:
          paramDrawable.zzi(paramImageView);
          if (i == 0) {
            break label176;
          }
        }
        label171:
        label176:
        for (i = this.zzZe;; i = 0)
        {
          paramDrawable.zzbo(i);
          if (!paramBoolean1) {
            break;
          }
          ((zzkg)localObject).startTransition(250);
          return;
          paramImageView = null;
          break label133;
        }
      }
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof zzb)) {
        return false;
      }
      if (this == paramObject) {
        return true;
      }
      Object localObject = (zzb)paramObject;
      paramObject = (ImageView)this.zzZl.get();
      localObject = (ImageView)((zzb)localObject).zzZl.get();
      if ((localObject != null) && (paramObject != null) && (zzt.equal(localObject, paramObject))) {}
      for (boolean bool = true;; bool = false) {
        return bool;
      }
    }
    
    public int hashCode()
    {
      return 0;
    }
    
    protected void zza(Drawable paramDrawable, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      ImageView localImageView = (ImageView)this.zzZl.get();
      if (localImageView != null) {
        zza(localImageView, paramDrawable, paramBoolean1, paramBoolean2, paramBoolean3);
      }
    }
  }
  
  public static final class zzc
    extends zza
  {
    private WeakReference<ImageManager.OnImageLoadedListener> zzZm;
    
    public zzc(ImageManager.OnImageLoadedListener paramOnImageLoadedListener, Uri paramUri)
    {
      super(0);
      zzb.zzq(paramOnImageLoadedListener);
      this.zzZm = new WeakReference(paramOnImageLoadedListener);
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof zzc)) {
        return false;
      }
      if (this == paramObject) {
        return true;
      }
      paramObject = (zzc)paramObject;
      ImageManager.OnImageLoadedListener localOnImageLoadedListener1 = (ImageManager.OnImageLoadedListener)this.zzZm.get();
      ImageManager.OnImageLoadedListener localOnImageLoadedListener2 = (ImageManager.OnImageLoadedListener)((zzc)paramObject).zzZm.get();
      if ((localOnImageLoadedListener2 != null) && (localOnImageLoadedListener1 != null) && (zzt.equal(localOnImageLoadedListener2, localOnImageLoadedListener1)) && (zzt.equal(((zzc)paramObject).zzZc, this.zzZc))) {}
      for (boolean bool = true;; bool = false) {
        return bool;
      }
    }
    
    public int hashCode()
    {
      return zzt.hashCode(new Object[] { this.zzZc });
    }
    
    protected void zza(Drawable paramDrawable, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      if (!paramBoolean2)
      {
        ImageManager.OnImageLoadedListener localOnImageLoadedListener = (ImageManager.OnImageLoadedListener)this.zzZm.get();
        if (localOnImageLoadedListener != null) {
          localOnImageLoadedListener.onImageLoaded(this.zzZc.uri, paramDrawable, paramBoolean3);
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/images/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
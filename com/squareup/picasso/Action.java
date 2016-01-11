package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

abstract class Action<T>
{
  boolean cancelled;
  final Drawable errorDrawable;
  final int errorResId;
  final String key;
  final boolean noFade;
  final Picasso picasso;
  final Request request;
  final boolean skipCache;
  final Object tag;
  final WeakReference<T> target;
  boolean willReplay;
  
  Action(Picasso paramPicasso, T paramT, Request paramRequest, boolean paramBoolean1, boolean paramBoolean2, int paramInt, Drawable paramDrawable, String paramString, Object paramObject)
  {
    this.picasso = paramPicasso;
    this.request = paramRequest;
    if (paramT == null)
    {
      paramPicasso = null;
      this.target = paramPicasso;
      this.skipCache = paramBoolean1;
      this.noFade = paramBoolean2;
      this.errorResId = paramInt;
      this.errorDrawable = paramDrawable;
      this.key = paramString;
      if (paramObject == null) {
        break label84;
      }
    }
    for (;;)
    {
      this.tag = paramObject;
      return;
      paramPicasso = new RequestWeakReference(this, paramT, paramPicasso.referenceQueue);
      break;
      label84:
      paramObject = this;
    }
  }
  
  void cancel()
  {
    this.cancelled = true;
  }
  
  abstract void complete(Bitmap paramBitmap, Picasso.LoadedFrom paramLoadedFrom);
  
  abstract void error();
  
  String getKey()
  {
    return this.key;
  }
  
  Picasso getPicasso()
  {
    return this.picasso;
  }
  
  Picasso.Priority getPriority()
  {
    return this.request.priority;
  }
  
  Request getRequest()
  {
    return this.request;
  }
  
  Object getTag()
  {
    return this.tag;
  }
  
  T getTarget()
  {
    if (this.target == null) {
      return null;
    }
    return (T)this.target.get();
  }
  
  boolean isCancelled()
  {
    return this.cancelled;
  }
  
  boolean willReplay()
  {
    return this.willReplay;
  }
  
  static class RequestWeakReference<M>
    extends WeakReference<M>
  {
    final Action action;
    
    public RequestWeakReference(Action paramAction, M paramM, ReferenceQueue<? super M> paramReferenceQueue)
    {
      super(paramReferenceQueue);
      this.action = paramAction;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/picasso/Action.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
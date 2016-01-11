package com.facebook.internal;

import com.facebook.FacebookException;
import java.util.Iterator;

public class CollectionMapper
{
  public static <T> void iterate(Collection<T> paramCollection, ValueMapper paramValueMapper, final OnMapperCompleteListener paramOnMapperCompleteListener)
  {
    Object localObject1 = new Mutable(Boolean.valueOf(false));
    final Mutable localMutable = new Mutable(Integer.valueOf(1));
    paramOnMapperCompleteListener = new OnMapperCompleteListener()
    {
      public void onComplete()
      {
        if (((Boolean)this.val$didReturnError.value).booleanValue()) {}
        Integer localInteger;
        do
        {
          return;
          Mutable localMutable = localMutable;
          localInteger = Integer.valueOf(((Integer)localMutable.value).intValue() - 1);
          localMutable.value = localInteger;
        } while (localInteger.intValue() != 0);
        paramOnMapperCompleteListener.onComplete();
      }
      
      public void onError(FacebookException paramAnonymousFacebookException)
      {
        if (((Boolean)this.val$didReturnError.value).booleanValue()) {
          return;
        }
        this.val$didReturnError.value = Boolean.valueOf(true);
        paramOnMapperCompleteListener.onError(paramAnonymousFacebookException);
      }
    };
    localObject1 = paramCollection.keyIterator();
    while (((Iterator)localObject1).hasNext())
    {
      final Object localObject3 = ((Iterator)localObject1).next();
      Object localObject2 = paramCollection.get(localObject3);
      localObject3 = new OnMapValueCompleteListener()
      {
        public void onComplete(Object paramAnonymousObject)
        {
          this.val$collection.set(localObject3, paramAnonymousObject, paramOnMapperCompleteListener);
          paramOnMapperCompleteListener.onComplete();
        }
        
        public void onError(FacebookException paramAnonymousFacebookException)
        {
          paramOnMapperCompleteListener.onError(paramAnonymousFacebookException);
        }
      };
      Integer localInteger = (Integer)localMutable.value;
      localMutable.value = Integer.valueOf(((Integer)localMutable.value).intValue() + 1);
      paramValueMapper.mapValue(localObject2, (OnMapValueCompleteListener)localObject3);
    }
    paramOnMapperCompleteListener.onComplete();
  }
  
  public static abstract interface Collection<T>
  {
    public abstract Object get(T paramT);
    
    public abstract Iterator<T> keyIterator();
    
    public abstract void set(T paramT, Object paramObject, CollectionMapper.OnErrorListener paramOnErrorListener);
  }
  
  public static abstract interface OnErrorListener
  {
    public abstract void onError(FacebookException paramFacebookException);
  }
  
  public static abstract interface OnMapValueCompleteListener
    extends CollectionMapper.OnErrorListener
  {
    public abstract void onComplete(Object paramObject);
  }
  
  public static abstract interface OnMapperCompleteListener
    extends CollectionMapper.OnErrorListener
  {
    public abstract void onComplete();
  }
  
  public static abstract interface ValueMapper
  {
    public abstract void mapValue(Object paramObject, CollectionMapper.OnMapValueCompleteListener paramOnMapValueCompleteListener);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/CollectionMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
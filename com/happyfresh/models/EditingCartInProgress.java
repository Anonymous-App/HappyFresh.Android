package com.happyfresh.models;

import java.util.HashMap;
import java.util.Map;

public class EditingCartInProgress
{
  private Map<Long, Integer> mAddToCartCountdown = new HashMap();
  private Map<Long, Integer> mAddToCartInProgress = new HashMap();
  private Map<Long, Integer> mRemoveFromCartCountdown = new HashMap();
  private Map<Long, Integer> mRemoveFromCartInProgress = new HashMap();
  private Map<Long, Integer> mUpdateCartCountdown = new HashMap();
  private Map<Long, Integer> mUpdateCartInProgress = new HashMap();
  
  public void addItemToAddToCartInProgress(Long paramLong, Integer paramInteger)
  {
    this.mAddToCartInProgress.put(paramLong, paramInteger);
  }
  
  public void addItemToRemoveFromCartInProgress(Long paramLong, Integer paramInteger)
  {
    this.mRemoveFromCartInProgress.put(paramLong, paramInteger);
  }
  
  public void addItemToUpdateItemInCartInProgress(Long paramLong, Integer paramInteger)
  {
    this.mUpdateCartInProgress.put(paramLong, paramInteger);
  }
  
  public int getQuantityCountdownInCart(long paramLong)
  {
    if (this.mAddToCartCountdown.containsKey(Long.valueOf(paramLong))) {
      return ((Integer)this.mAddToCartCountdown.get(Long.valueOf(paramLong))).intValue();
    }
    if (this.mUpdateCartCountdown.containsKey(Long.valueOf(paramLong))) {
      return ((Integer)this.mUpdateCartCountdown.get(Long.valueOf(paramLong))).intValue();
    }
    if (this.mRemoveFromCartCountdown.containsKey(Long.valueOf(paramLong))) {
      return ((Integer)this.mRemoveFromCartCountdown.get(Long.valueOf(paramLong))).intValue();
    }
    return Integer.MIN_VALUE;
  }
  
  public int getQuantityInProgressInCart(long paramLong)
  {
    if (this.mAddToCartInProgress.containsKey(Long.valueOf(paramLong))) {
      return ((Integer)this.mAddToCartInProgress.get(Long.valueOf(paramLong))).intValue();
    }
    if (this.mUpdateCartInProgress.containsKey(Long.valueOf(paramLong))) {
      return ((Integer)this.mUpdateCartInProgress.get(Long.valueOf(paramLong))).intValue();
    }
    if (this.mRemoveFromCartInProgress.containsKey(Long.valueOf(paramLong))) {
      return ((Integer)this.mRemoveFromCartInProgress.get(Long.valueOf(paramLong))).intValue();
    }
    return Integer.MIN_VALUE;
  }
  
  public int getSizeAddToCartInProgress()
  {
    return this.mAddToCartInProgress.size();
  }
  
  public int getSizeRemoveFromCartInProgress()
  {
    return this.mRemoveFromCartInProgress.size();
  }
  
  public void removeItemFromAddToCartCountdown(Long paramLong)
  {
    this.mAddToCartCountdown.remove(paramLong);
  }
  
  public void removeItemFromAddToCartInProgress(Long paramLong)
  {
    this.mAddToCartInProgress.remove(paramLong);
  }
  
  public void removeItemFromRemoveFromCartCountdown(Long paramLong)
  {
    this.mRemoveFromCartCountdown.remove(paramLong);
  }
  
  public void removeItemFromRemoveFromCartInProgress(Long paramLong)
  {
    this.mRemoveFromCartInProgress.remove(paramLong);
  }
  
  public void removeItemFromUpdateItemInCartCountdown(Long paramLong)
  {
    this.mUpdateCartCountdown.remove(paramLong);
  }
  
  public void removeItemFromUpdateItemInCartInProgress(Long paramLong)
  {
    this.mUpdateCartInProgress.remove(paramLong);
  }
  
  public void updateItemInAddToCartCountdown(Long paramLong, Integer paramInteger)
  {
    this.mAddToCartCountdown.remove(paramLong);
    this.mAddToCartCountdown.put(paramLong, paramInteger);
  }
  
  public void updateItemInRemoveFromCartCountdown(Long paramLong, Integer paramInteger)
  {
    this.mRemoveFromCartCountdown.remove(paramLong);
    this.mRemoveFromCartCountdown.put(paramLong, paramInteger);
  }
  
  public void updateItemInUpdateItemInCartCountdown(Long paramLong, Integer paramInteger)
  {
    this.mUpdateCartCountdown.remove(paramLong);
    this.mUpdateCartCountdown.put(paramLong, paramInteger);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/EditingCartInProgress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
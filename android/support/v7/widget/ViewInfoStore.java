package android.support.v7.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;

class ViewInfoStore
{
  private static final boolean DEBUG = false;
  final ArrayMap<RecyclerView.ViewHolder, InfoRecord> mLayoutHolderMap = new ArrayMap();
  final LongSparseArray<RecyclerView.ViewHolder> mOldChangedHolders = new LongSparseArray();
  
  void addToAppearedInPreLayoutHolders(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo)
  {
    InfoRecord localInfoRecord2 = (InfoRecord)this.mLayoutHolderMap.get(paramViewHolder);
    InfoRecord localInfoRecord1 = localInfoRecord2;
    if (localInfoRecord2 == null)
    {
      localInfoRecord1 = InfoRecord.obtain();
      this.mLayoutHolderMap.put(paramViewHolder, localInfoRecord1);
    }
    localInfoRecord1.flags |= 0x2;
    localInfoRecord1.preInfo = paramItemHolderInfo;
  }
  
  void addToDisappearedInLayout(RecyclerView.ViewHolder paramViewHolder)
  {
    InfoRecord localInfoRecord2 = (InfoRecord)this.mLayoutHolderMap.get(paramViewHolder);
    InfoRecord localInfoRecord1 = localInfoRecord2;
    if (localInfoRecord2 == null)
    {
      localInfoRecord1 = InfoRecord.obtain();
      this.mLayoutHolderMap.put(paramViewHolder, localInfoRecord1);
    }
    localInfoRecord1.flags |= 0x1;
  }
  
  void addToOldChangeHolders(long paramLong, RecyclerView.ViewHolder paramViewHolder)
  {
    this.mOldChangedHolders.put(paramLong, paramViewHolder);
  }
  
  void addToPostLayout(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo)
  {
    InfoRecord localInfoRecord2 = (InfoRecord)this.mLayoutHolderMap.get(paramViewHolder);
    InfoRecord localInfoRecord1 = localInfoRecord2;
    if (localInfoRecord2 == null)
    {
      localInfoRecord1 = InfoRecord.obtain();
      this.mLayoutHolderMap.put(paramViewHolder, localInfoRecord1);
    }
    localInfoRecord1.postInfo = paramItemHolderInfo;
    localInfoRecord1.flags |= 0x8;
  }
  
  void addToPreLayout(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo)
  {
    InfoRecord localInfoRecord2 = (InfoRecord)this.mLayoutHolderMap.get(paramViewHolder);
    InfoRecord localInfoRecord1 = localInfoRecord2;
    if (localInfoRecord2 == null)
    {
      localInfoRecord1 = InfoRecord.obtain();
      this.mLayoutHolderMap.put(paramViewHolder, localInfoRecord1);
    }
    localInfoRecord1.preInfo = paramItemHolderInfo;
    localInfoRecord1.flags |= 0x4;
  }
  
  void clear()
  {
    this.mLayoutHolderMap.clear();
    this.mOldChangedHolders.clear();
  }
  
  RecyclerView.ViewHolder getFromOldChangeHolders(long paramLong)
  {
    return (RecyclerView.ViewHolder)this.mOldChangedHolders.get(paramLong);
  }
  
  boolean isInPreLayout(RecyclerView.ViewHolder paramViewHolder)
  {
    paramViewHolder = (InfoRecord)this.mLayoutHolderMap.get(paramViewHolder);
    return (paramViewHolder != null) && ((paramViewHolder.flags & 0x4) != 0);
  }
  
  void onDetach() {}
  
  @Nullable
  RecyclerView.ItemAnimator.ItemHolderInfo popFromPreLayout(RecyclerView.ViewHolder paramViewHolder)
  {
    RecyclerView.ItemAnimator.ItemHolderInfo localItemHolderInfo = null;
    int i = this.mLayoutHolderMap.indexOfKey(paramViewHolder);
    if (i < 0) {
      paramViewHolder = localItemHolderInfo;
    }
    InfoRecord localInfoRecord;
    do
    {
      do
      {
        do
        {
          return paramViewHolder;
          localInfoRecord = (InfoRecord)this.mLayoutHolderMap.valueAt(i);
          paramViewHolder = localItemHolderInfo;
        } while (localInfoRecord == null);
        paramViewHolder = localItemHolderInfo;
      } while ((localInfoRecord.flags & 0x4) == 0);
      localInfoRecord.flags &= 0xFFFFFFFB;
      localItemHolderInfo = localInfoRecord.preInfo;
      paramViewHolder = localItemHolderInfo;
    } while (localInfoRecord.flags != 0);
    this.mLayoutHolderMap.removeAt(i);
    InfoRecord.recycle(localInfoRecord);
    return localItemHolderInfo;
  }
  
  void process(ProcessCallback paramProcessCallback)
  {
    int i = this.mLayoutHolderMap.size() - 1;
    if (i >= 0)
    {
      RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mLayoutHolderMap.keyAt(i);
      InfoRecord localInfoRecord = (InfoRecord)this.mLayoutHolderMap.removeAt(i);
      if ((localInfoRecord.flags & 0x3) == 3) {
        paramProcessCallback.unused(localViewHolder);
      }
      for (;;)
      {
        InfoRecord.recycle(localInfoRecord);
        i -= 1;
        break;
        if ((localInfoRecord.flags & 0x1) != 0) {
          paramProcessCallback.processDisappeared(localViewHolder, localInfoRecord.preInfo, localInfoRecord.postInfo);
        } else if ((localInfoRecord.flags & 0xE) == 14) {
          paramProcessCallback.processAppeared(localViewHolder, localInfoRecord.preInfo, localInfoRecord.postInfo);
        } else if ((localInfoRecord.flags & 0xC) == 12) {
          paramProcessCallback.processPersistent(localViewHolder, localInfoRecord.preInfo, localInfoRecord.postInfo);
        } else if ((localInfoRecord.flags & 0x4) != 0) {
          paramProcessCallback.processDisappeared(localViewHolder, localInfoRecord.preInfo, null);
        } else if ((localInfoRecord.flags & 0x8) != 0) {
          paramProcessCallback.processAppeared(localViewHolder, localInfoRecord.preInfo, localInfoRecord.postInfo);
        } else if ((localInfoRecord.flags & 0x2) == 0) {}
      }
    }
  }
  
  void removeFromDisappearedInLayout(RecyclerView.ViewHolder paramViewHolder)
  {
    paramViewHolder = (InfoRecord)this.mLayoutHolderMap.get(paramViewHolder);
    if (paramViewHolder == null) {
      return;
    }
    paramViewHolder.flags &= 0xFFFFFFFE;
  }
  
  void removeViewHolder(RecyclerView.ViewHolder paramViewHolder)
  {
    int i = this.mOldChangedHolders.size() - 1;
    for (;;)
    {
      if (i >= 0)
      {
        if (paramViewHolder == this.mOldChangedHolders.valueAt(i)) {
          this.mOldChangedHolders.removeAt(i);
        }
      }
      else
      {
        paramViewHolder = (InfoRecord)this.mLayoutHolderMap.remove(paramViewHolder);
        if (paramViewHolder != null) {
          InfoRecord.recycle(paramViewHolder);
        }
        return;
      }
      i -= 1;
    }
  }
  
  static class InfoRecord
  {
    static final int FLAG_APPEAR = 2;
    static final int FLAG_APPEAR_AND_DISAPPEAR = 3;
    static final int FLAG_APPEAR_PRE_AND_POST = 14;
    static final int FLAG_DISAPPEARED = 1;
    static final int FLAG_POST = 8;
    static final int FLAG_PRE = 4;
    static final int FLAG_PRE_AND_POST = 12;
    static Pools.Pool<InfoRecord> sPool = new Pools.SimplePool(20);
    int flags;
    @Nullable
    RecyclerView.ItemAnimator.ItemHolderInfo postInfo;
    @Nullable
    RecyclerView.ItemAnimator.ItemHolderInfo preInfo;
    
    static void drainCache()
    {
      while (sPool.acquire() != null) {}
    }
    
    static InfoRecord obtain()
    {
      InfoRecord localInfoRecord2 = (InfoRecord)sPool.acquire();
      InfoRecord localInfoRecord1 = localInfoRecord2;
      if (localInfoRecord2 == null) {
        localInfoRecord1 = new InfoRecord();
      }
      return localInfoRecord1;
    }
    
    static void recycle(InfoRecord paramInfoRecord)
    {
      paramInfoRecord.flags = 0;
      paramInfoRecord.preInfo = null;
      paramInfoRecord.postInfo = null;
      sPool.release(paramInfoRecord);
    }
  }
  
  static abstract interface ProcessCallback
  {
    public abstract void processAppeared(RecyclerView.ViewHolder paramViewHolder, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2);
    
    public abstract void processDisappeared(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2);
    
    public abstract void processPersistent(RecyclerView.ViewHolder paramViewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2);
    
    public abstract void unused(RecyclerView.ViewHolder paramViewHolder);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/android/support/v7/widget/ViewInfoStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
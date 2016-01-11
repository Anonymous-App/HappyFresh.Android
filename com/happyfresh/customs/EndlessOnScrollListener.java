package com.happyfresh.customs;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

public abstract class EndlessOnScrollListener
  extends RecyclerView.OnScrollListener
{
  private int firstVisibleItem;
  private boolean loading = true;
  private LinearLayoutManager mLayoutManager;
  private int mPerPage;
  private int previousTotal = 0;
  private int totalItemCount;
  private int visibleItemCount;
  private int visibleThreshold = 5;
  
  public EndlessOnScrollListener(LinearLayoutManager paramLinearLayoutManager, int paramInt)
  {
    this.mLayoutManager = paramLinearLayoutManager;
    this.mPerPage = paramInt;
    if ((paramLinearLayoutManager instanceof GridLayoutManager)) {
      this.visibleThreshold = 0;
    }
  }
  
  public void onLoadMore(int paramInt)
  {
    this.loading = true;
  }
  
  public void onScrolled(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    super.onScrolled(paramRecyclerView, paramInt1, paramInt2);
    this.visibleItemCount = this.mLayoutManager.getChildCount();
    this.totalItemCount = this.mLayoutManager.getItemCount();
    this.firstVisibleItem = this.mLayoutManager.findFirstVisibleItemPosition();
    if ((this.loading) && (this.totalItemCount > this.previousTotal))
    {
      this.loading = false;
      this.previousTotal = this.totalItemCount;
    }
    if ((!this.loading) && (this.totalItemCount - this.visibleItemCount <= this.firstVisibleItem + this.visibleThreshold)) {
      onLoadMore(this.totalItemCount / this.mPerPage + 1);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/EndlessOnScrollListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
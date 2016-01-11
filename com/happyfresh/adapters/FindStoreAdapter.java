package com.happyfresh.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.listeners.OnFindStoreClickListener;
import com.happyfresh.models.FindStoreModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindStoreAdapter
  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
  private Context mContext;
  private List<FindStoreModel> mList;
  private OnFindStoreClickListener mOnFindStoreClickListener;
  OnSelectionChangedListener mOnSelectionChanged = new OnSelectionChangedListener()
  {
    public void onSelect(Long paramAnonymousLong)
    {
      Object localObject = null;
      Iterator localIterator = FindStoreAdapter.this.mOriginalData.iterator();
      while (localIterator.hasNext())
      {
        FindStoreModel localFindStoreModel = (FindStoreModel)localIterator.next();
        if (localFindStoreModel.getRemoteId().equals(paramAnonymousLong))
        {
          localFindStoreModel.setSelected(true);
          localObject = localFindStoreModel;
          ((FindStoreModel)localObject).setProgressing(true);
        }
        else
        {
          localFindStoreModel.setSelected(false);
          localFindStoreModel.setProgressing(false);
        }
      }
      FindStoreAdapter.this.notifyDataSetChanged();
      if (FindStoreAdapter.this.mOnFindStoreClickListener != null) {
        FindStoreAdapter.this.mOnFindStoreClickListener.onSelected((FindStoreModel)localObject);
      }
    }
  };
  private List<FindStoreModel> mOriginalData;
  
  public FindStoreAdapter(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void addAll(List<FindStoreModel> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      ((FindStoreModel)localIterator.next()).setProgressing(false);
    }
    this.mList = new ArrayList(paramList);
    this.mOriginalData = paramList;
  }
  
  public void clearAllProgressBar()
  {
    Iterator localIterator = this.mOriginalData.iterator();
    while (localIterator.hasNext()) {
      ((FindStoreModel)localIterator.next()).setProgressing(false);
    }
    notifyDataSetChanged();
  }
  
  public void flushFilter()
  {
    this.mList = new ArrayList();
    this.mList.addAll(this.mOriginalData);
    notifyDataSetChanged();
  }
  
  public int getItemCount()
  {
    return this.mList.size();
  }
  
  public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    FindStoreModel localFindStoreModel = (FindStoreModel)this.mList.get(paramInt);
    paramViewHolder = (StoreViewHolder)paramViewHolder;
    paramViewHolder.radioButton.setText(localFindStoreModel.getName());
    paramViewHolder.remoteId = localFindStoreModel.getRemoteId();
    paramViewHolder.setSelected(localFindStoreModel.isSelected());
    if (localFindStoreModel.isProgressing()) {
      paramViewHolder.showProgress(true);
    }
    for (;;)
    {
      paramViewHolder.radioButton.setEnabled(localFindStoreModel.isEnabled());
      return;
      paramViewHolder.showProgress(false);
    }
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    return new StoreViewHolder(LayoutInflater.from(this.mContext).inflate(2130903163, null), this.mOnSelectionChanged);
  }
  
  public void setEnabledAll(boolean paramBoolean)
  {
    Iterator localIterator = this.mOriginalData.iterator();
    while (localIterator.hasNext()) {
      ((FindStoreModel)localIterator.next()).setEnabled(paramBoolean);
    }
    notifyDataSetChanged();
  }
  
  public void setFilter(String paramString)
  {
    this.mList = new ArrayList();
    Iterator localIterator = this.mOriginalData.iterator();
    while (localIterator.hasNext())
    {
      FindStoreModel localFindStoreModel = (FindStoreModel)localIterator.next();
      if (localFindStoreModel.getName().toLowerCase().contains(paramString.toLowerCase())) {
        this.mList.add(localFindStoreModel);
      }
    }
    notifyDataSetChanged();
  }
  
  public void setOnFindStoreClickListener(OnFindStoreClickListener paramOnFindStoreClickListener)
  {
    this.mOnFindStoreClickListener = paramOnFindStoreClickListener;
  }
  
  static abstract interface OnSelectionChangedListener
  {
    public abstract void onSelect(Long paramLong);
  }
  
  public class StoreViewHolder
    extends RecyclerView.ViewHolder
  {
    @InjectView(2131558915)
    RadioButton radioButton;
    Long remoteId;
    @InjectView(2131558916)
    CircularProgressBar selectionProgress;
    
    public StoreViewHolder(View paramView, final FindStoreAdapter.OnSelectionChangedListener paramOnSelectionChangedListener)
    {
      super();
      ButterKnife.inject(this, paramView);
      showProgress(false);
      this.radioButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          paramOnSelectionChangedListener.onSelect(FindStoreAdapter.StoreViewHolder.this.remoteId);
        }
      });
      setPadding();
    }
    
    private void setPadding()
    {
      if (Build.VERSION.SDK_INT < 17)
      {
        float f = FindStoreAdapter.this.mContext.getResources().getDisplayMetrics().density;
        this.radioButton.setPadding(this.radioButton.getPaddingLeft() + (int)(25.0F * f + 0.5F), this.radioButton.getPaddingTop(), this.radioButton.getPaddingRight(), this.radioButton.getPaddingBottom());
      }
    }
    
    public void setSelected(boolean paramBoolean)
    {
      this.radioButton.setChecked(paramBoolean);
    }
    
    public void showProgress(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        this.selectionProgress.setVisibility(0);
        return;
      }
      this.selectionProgress.setVisibility(8);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/FindStoreAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
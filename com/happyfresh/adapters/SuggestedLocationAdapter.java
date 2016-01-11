package com.happyfresh.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.listeners.OnItemClickListener;
import com.happyfresh.models.District;
import com.happyfresh.models.SubDistrict;
import java.util.ArrayList;
import java.util.List;

public class SuggestedLocationAdapter
  extends RecyclerView.Adapter<ViewHolder>
{
  private Context mContext;
  private OnItemClickListener mOnItemClickListener;
  private List<SubDistrict> mSubDistricts = new ArrayList();
  
  public SuggestedLocationAdapter(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void addAll(List<SubDistrict> paramList)
  {
    this.mSubDistricts.clear();
    this.mSubDistricts.addAll(paramList);
  }
  
  public void clearAll()
  {
    this.mSubDistricts.clear();
    notifyDataSetChanged();
  }
  
  public int getItemCount()
  {
    return this.mSubDistricts.size();
  }
  
  public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt)
  {
    SubDistrict localSubDistrict = (SubDistrict)this.mSubDistricts.get(paramInt);
    StringBuilder localStringBuilder = new StringBuilder(localSubDistrict.name);
    localStringBuilder.append(", ");
    localStringBuilder.append(localSubDistrict.district.name);
    localStringBuilder.append(", ");
    localStringBuilder.append(localSubDistrict.zipcode);
    paramViewHolder.itemLocation.setText(localStringBuilder.toString());
  }
  
  public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    return new ViewHolder(LayoutInflater.from(this.mContext).inflate(2130903175, paramViewGroup, false));
  }
  
  public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
  {
    this.mOnItemClickListener = paramOnItemClickListener;
  }
  
  public class ViewHolder
    extends RecyclerView.ViewHolder
    implements View.OnClickListener
  {
    @InjectView(2131558954)
    TextView itemLocation;
    
    public ViewHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
      paramView.setOnClickListener(this);
    }
    
    public void onClick(View paramView)
    {
      if (SuggestedLocationAdapter.this.mOnItemClickListener != null)
      {
        SubDistrict localSubDistrict = (SubDistrict)SuggestedLocationAdapter.this.mSubDistricts.get(getPosition());
        SuggestedLocationAdapter.this.mOnItemClickListener.onItemClick(paramView, localSubDistrict);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/SuggestedLocationAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
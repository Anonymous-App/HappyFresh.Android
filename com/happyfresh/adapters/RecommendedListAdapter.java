package com.happyfresh.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.customs.RoundedTransformation;
import com.happyfresh.models.ShoppingList;
import com.happyfresh.models.ShoppingList.Photo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;
import java.util.ArrayList;
import java.util.List;

public class RecommendedListAdapter
  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
  private Context mContext;
  private List<ShoppingList> mList;
  private OnRecommendedItemClickListener mOnItemClickListener;
  
  public RecommendedListAdapter(Context paramContext)
  {
    this.mContext = paramContext;
    this.mList = new ArrayList();
  }
  
  public void clearData()
  {
    if (this.mList != null) {
      this.mList.clear();
    }
  }
  
  public int dpToPx(int paramInt)
  {
    DisplayMetrics localDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
    return Math.round(paramInt * (localDisplayMetrics.xdpi / 160.0F));
  }
  
  public int getItemCount()
  {
    return this.mList.size();
  }
  
  public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    Object localObject = (ShoppingList)this.mList.get(paramInt);
    RecommendedViewHolder localRecommendedViewHolder = (RecommendedViewHolder)paramViewHolder;
    localRecommendedViewHolder.txt.setText(((ShoppingList)localObject).name);
    paramViewHolder = null;
    if (((ShoppingList)localObject).photo != null) {
      paramViewHolder = ((ShoppingList)localObject).photo.mediumUrl;
    }
    paramInt = dpToPx(3);
    localObject = new RoundedTransformation(paramInt, paramInt);
    Picasso.with(this.mContext).load(paramViewHolder).transform((Transformation)localObject).fit().skipMemoryCache().into(localRecommendedViewHolder.img);
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    return new RecommendedViewHolder(LayoutInflater.from(this.mContext).inflate(2130903181, null));
  }
  
  public void setData(List<ShoppingList> paramList)
  {
    this.mList = paramList;
  }
  
  public void setOnRecommendedItemClickListener(OnRecommendedItemClickListener paramOnRecommendedItemClickListener)
  {
    this.mOnItemClickListener = paramOnRecommendedItemClickListener;
  }
  
  public static abstract interface OnRecommendedItemClickListener
  {
    public abstract void onItemClick(int paramInt);
  }
  
  public class RecommendedViewHolder
    extends RecyclerView.ViewHolder
    implements View.OnClickListener
  {
    @InjectView(2131559041)
    ImageView img;
    @InjectView(2131559042)
    TextView txt;
    
    public RecommendedViewHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
      paramView.setOnClickListener(this);
    }
    
    public void onClick(View paramView)
    {
      RecommendedListAdapter.this.mOnItemClickListener.onItemClick(getAdapterPosition());
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/RecommendedListAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
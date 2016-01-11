package com.happyfresh.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.common.ICartApplication;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SuggestedSearchAdapter
  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
  public static int TYPE_CATEGORY = 2;
  public static int TYPE_PRODUCT = 1;
  protected Context mContext;
  protected ICartApplication mICartApplication;
  protected List<SuggestedListItem> mList;
  protected SuggestedListItemClickListener mListener;
  protected String mSearchTerm;
  
  public SuggestedSearchAdapter(Context paramContext)
  {
    this.mContext = paramContext;
    this.mICartApplication = ((ICartApplication)paramContext);
    this.mList = new ArrayList();
  }
  
  public void clearData()
  {
    if (this.mList != null) {
      this.mList.clear();
    }
    this.mSearchTerm = null;
  }
  
  public int getItemCount()
  {
    return this.mList.size();
  }
  
  public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    ((BaseSuggestedListItemViewHolder)paramViewHolder).bind(paramInt);
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    return new SuggestedListItemViewHolder(LayoutInflater.from(this.mContext).inflate(2130903187, null));
  }
  
  public void setData(List<String> paramList, String paramString)
  {
    this.mList.clear();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      SuggestedListItem localSuggestedListItem = new SuggestedListItem((String)paramList.next());
      this.mList.add(localSuggestedListItem);
    }
    this.mSearchTerm = paramString;
  }
  
  public void setOnSuggestedItemClickListener(SuggestedListItemClickListener paramSuggestedListItemClickListener)
  {
    this.mListener = paramSuggestedListItemClickListener;
  }
  
  protected class BaseSuggestedListItem
  {
    protected BaseSuggestedListItem() {}
    
    public int getViewType()
    {
      return 1002;
    }
  }
  
  public class BaseSuggestedListItemViewHolder
    extends RecyclerView.ViewHolder
  {
    public BaseSuggestedListItemViewHolder(View paramView)
    {
      super();
    }
    
    public void bind(int paramInt) {}
  }
  
  public class SuggestedListItem
    extends SuggestedSearchAdapter.BaseSuggestedListItem
  {
    protected String keyword;
    protected int type;
    
    public SuggestedListItem(String paramString)
    {
      super();
      this.keyword = paramString.toLowerCase();
      this.type = SuggestedSearchAdapter.TYPE_PRODUCT;
    }
    
    public String getHighlightSearchTermInHtml()
    {
      if (!TextUtils.isEmpty(SuggestedSearchAdapter.this.mSearchTerm)) {
        return getSearchTerm().replace(SuggestedSearchAdapter.this.mSearchTerm, "<b>" + SuggestedSearchAdapter.this.mSearchTerm + "</b>");
      }
      return getSearchTerm();
    }
    
    public String getSearchTerm()
    {
      return this.keyword;
    }
    
    public int getType()
    {
      return this.type;
    }
    
    public int getViewType()
    {
      return 2001;
    }
  }
  
  public static abstract interface SuggestedListItemClickListener
  {
    public abstract void onSuggestedArrowIconClick(String paramString);
    
    public abstract void onSuggestedListItemClick(String paramString);
  }
  
  public class SuggestedListItemViewHolder
    extends SuggestedSearchAdapter.BaseSuggestedListItemViewHolder
  {
    @InjectView(2131559057)
    ImageView suggestedIcon;
    @InjectView(2131559056)
    ImageView suggestedIconArrow;
    @InjectView(2131559058)
    TextView suggestedName;
    
    public SuggestedListItemViewHolder(View paramView)
    {
      super(paramView);
      ButterKnife.inject(this, paramView);
    }
    
    public void bind(int paramInt)
    {
      Object localObject = (SuggestedSearchAdapter.BaseSuggestedListItem)SuggestedSearchAdapter.this.mList.get(paramInt);
      if (!(localObject instanceof SuggestedSearchAdapter.SuggestedListItem)) {
        return;
      }
      localObject = (SuggestedSearchAdapter.SuggestedListItem)localObject;
      String str = ((SuggestedSearchAdapter.SuggestedListItem)localObject).getHighlightSearchTermInHtml();
      this.suggestedName.setText(Html.fromHtml(str));
      if (((SuggestedSearchAdapter.SuggestedListItem)localObject).getType() == SuggestedSearchAdapter.TYPE_PRODUCT)
      {
        this.suggestedIcon.setImageDrawable(SuggestedSearchAdapter.this.mContext.getResources().getDrawable(2130837825));
        this.suggestedIconArrow.setVisibility(0);
        return;
      }
      this.suggestedIcon.setImageDrawable(SuggestedSearchAdapter.this.mContext.getResources().getDrawable(2130837804));
      this.suggestedIconArrow.setVisibility(8);
    }
    
    @OnClick({2131559056})
    public void clickArrowIcon()
    {
      if (SuggestedSearchAdapter.this.mListener != null)
      {
        int i = getAdapterPosition();
        if ((i >= 0) && (i < SuggestedSearchAdapter.this.mList.size()))
        {
          String str = ((SuggestedSearchAdapter.SuggestedListItem)SuggestedSearchAdapter.this.mList.get(i)).getSearchTerm();
          SuggestedSearchAdapter.this.mListener.onSuggestedArrowIconClick(str);
        }
      }
    }
    
    @OnClick({2131559055})
    public void clickItem()
    {
      if (SuggestedSearchAdapter.this.mListener != null)
      {
        int i = getAdapterPosition();
        if ((i >= 0) && (i < SuggestedSearchAdapter.this.mList.size()))
        {
          String str = ((SuggestedSearchAdapter.SuggestedListItem)SuggestedSearchAdapter.this.mList.get(i)).getSearchTerm();
          SuggestedSearchAdapter.this.mListener.onSuggestedListItemClick(str);
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/SuggestedSearchAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
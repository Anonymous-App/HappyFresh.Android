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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.EditingCartInProgress;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.Variant;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReplacementItemAdapter
  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
  private Context mContext;
  private ICartApplication mICartApplication;
  private TextInfoItemViewHolder mIndividualReplacementInfoItemViewHolder;
  private OverallReplacementItemViewHolder mOverallReplacementItemViewHolder;
  private List<ReplacementItem> mReplacement = new ArrayList();
  private boolean showingOverallReplacementOptions;
  
  public ReplacementItemAdapter(Context paramContext, ICartApplication paramICartApplication)
  {
    this.mContext = paramContext;
    this.mICartApplication = paramICartApplication;
  }
  
  private NumberFormat getNumberFormatter()
  {
    Object localObject = this.mICartApplication.getCountryCode();
    localObject = NumberFormat.getCurrencyInstance(this.mICartApplication.getLocaleByCountryCode((String)localObject));
    ((NumberFormat)localObject).setMinimumFractionDigits(2);
    return (NumberFormat)localObject;
  }
  
  public void addAll(List<LineItem> paramList)
  {
    this.mReplacement.clear();
    setData(paramList);
  }
  
  public int getItemCount()
  {
    return this.mReplacement.size();
  }
  
  public int getItemViewType(int paramInt)
  {
    return ((ReplacementItem)this.mReplacement.get(paramInt)).viewType;
  }
  
  public List<LineItem> getReplacements()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mReplacement.iterator();
    while (localIterator.hasNext())
    {
      ReplacementItem localReplacementItem = (ReplacementItem)localIterator.next();
      if (localReplacementItem.lineItem != null) {
        localArrayList.add(localReplacementItem.lineItem);
      }
    }
    return localArrayList;
  }
  
  public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    ReplacementItem localReplacementItem = (ReplacementItem)this.mReplacement.get(paramInt);
    ((BaseReplacementItemViewHolder)paramViewHolder).bind(localReplacementItem);
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    if (paramInt == 1002) {
      return new BaseReplacementItemViewHolder(LayoutInflater.from(this.mContext).inflate(2130903219, null));
    }
    if (paramInt == 1001)
    {
      this.mOverallReplacementItemViewHolder = new OverallReplacementItemViewHolder(LayoutInflater.from(this.mContext).inflate(2130903221, null));
      return this.mOverallReplacementItemViewHolder;
    }
    if (paramInt == 2002)
    {
      this.mIndividualReplacementInfoItemViewHolder = new TextInfoItemViewHolder(LayoutInflater.from(this.mContext).inflate(2130903223, null));
      return this.mIndividualReplacementInfoItemViewHolder;
    }
    return new ReplacementItemViewHolder(LayoutInflater.from(this.mContext).inflate(2130903220, null));
  }
  
  public void setData(List<LineItem> paramList)
  {
    Object localObject = paramList;
    if (paramList == null) {
      localObject = new ArrayList();
    }
    paramList = new ReplacementItem();
    paramList.viewType = 1002;
    this.mReplacement.add(paramList);
    paramList = new ReplacementItem();
    paramList.viewType = 1001;
    this.mReplacement.add(paramList);
    paramList = new ReplacementItem();
    paramList.viewType = 2002;
    this.mReplacement.add(paramList);
    paramList = ((List)localObject).iterator();
    while (paramList.hasNext())
    {
      localObject = (LineItem)paramList.next();
      ReplacementItem localReplacementItem = new ReplacementItem();
      localReplacementItem.lineItem = ((LineItem)localObject);
      localReplacementItem.viewType = 2001;
      this.mReplacement.add(localReplacementItem);
    }
  }
  
  public void setOverallReplacement1Checked()
  {
    if (this.mOverallReplacementItemViewHolder != null)
    {
      this.mOverallReplacementItemViewHolder.mReplacementOption1.setChecked(true);
      this.mOverallReplacementItemViewHolder.setOverallReplacementOption1Active();
    }
  }
  
  public void setOverallReplacement2Checked()
  {
    if (this.mOverallReplacementItemViewHolder != null)
    {
      this.mOverallReplacementItemViewHolder.mReplacementOption2.setChecked(true);
      this.mOverallReplacementItemViewHolder.setOverallReplacementOption2Active();
    }
  }
  
  public void setOverallReplacement3Checked()
  {
    if (this.mOverallReplacementItemViewHolder != null)
    {
      this.mOverallReplacementItemViewHolder.mReplacementOption3.setChecked(true);
      this.mOverallReplacementItemViewHolder.setOverallReplacementOption3Active();
    }
  }
  
  public void setOverallReplacementOptionsInactive()
  {
    if (this.mOverallReplacementItemViewHolder != null) {
      this.mOverallReplacementItemViewHolder.setOverallReplacementOptionsInactive();
    }
  }
  
  public void setReplacementOption(String paramString)
  {
    Iterator localIterator = this.mReplacement.iterator();
    while (localIterator.hasNext())
    {
      ReplacementItem localReplacementItem = (ReplacementItem)localIterator.next();
      if (localReplacementItem.lineItem != null) {
        localReplacementItem.lineItem.replacementOption = paramString;
      }
    }
    notifyDataSetChanged();
  }
  
  public void setShowingOverallReplacementOptions(boolean paramBoolean)
  {
    this.showingOverallReplacementOptions = paramBoolean;
  }
  
  class BaseReplacementItemViewHolder
    extends RecyclerView.ViewHolder
  {
    public BaseReplacementItemViewHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
    }
    
    public void bind(ReplacementItemAdapter.ReplacementItem paramReplacementItem) {}
  }
  
  class OverallReplacementItemViewHolder
    extends ReplacementItemAdapter.BaseReplacementItemViewHolder
  {
    @InjectView(2131559092)
    RadioButton mReplacementOption1;
    @InjectView(2131559093)
    RadioButton mReplacementOption2;
    @InjectView(2131559094)
    RadioButton mReplacementOption3;
    @InjectView(2131559091)
    RadioGroup mReplacementOptions;
    
    public OverallReplacementItemViewHolder(View paramView)
    {
      super(paramView);
    }
    
    public void bind(ReplacementItemAdapter.ReplacementItem paramReplacementItem)
    {
      int i = 8;
      int j;
      if (ReplacementItemAdapter.this.showingOverallReplacementOptions)
      {
        j = 0;
        paramReplacementItem = ReplacementItemAdapter.this.mICartApplication.getSharedPreferencesManager().getOverallReplacementOption();
        if (!"by_shopper".equalsIgnoreCase(paramReplacementItem)) {
          break label53;
        }
        setOverallReplacementOption1Active();
        i = j;
      }
      for (;;)
      {
        this.mReplacementOptions.setVisibility(i);
        return;
        label53:
        if ("by_call".equalsIgnoreCase(paramReplacementItem))
        {
          setOverallReplacementOption2Active();
          i = j;
        }
        else if ("dont_replace".equalsIgnoreCase(paramReplacementItem))
        {
          setOverallReplacementOption3Active();
          i = j;
        }
        else
        {
          i = j;
          if ("per_product".equalsIgnoreCase(paramReplacementItem))
          {
            setOverallReplacementOptionsInactive();
            i = j;
          }
        }
      }
    }
    
    @OnClick({2131559092})
    public void onOverallReplacementOption1Click()
    {
      if (!this.mReplacementOption1.isChecked()) {
        return;
      }
      setOverallReplacementOption1Active();
      ReplacementItemAdapter.this.setReplacementOption("by_shopper");
      ReplacementItemAdapter.this.mICartApplication.getSharedPreferencesManager().saveOverallReplacementOption("by_shopper");
    }
    
    @OnClick({2131559093})
    public void onOverallReplacementOption2Click()
    {
      if (!this.mReplacementOption2.isChecked()) {
        return;
      }
      setOverallReplacementOption2Active();
      ReplacementItemAdapter.this.setReplacementOption("by_call");
      ReplacementItemAdapter.this.mICartApplication.getSharedPreferencesManager().saveOverallReplacementOption("by_call");
    }
    
    @OnClick({2131559094})
    public void onOverallReplacementOption3Click()
    {
      if (!this.mReplacementOption3.isChecked()) {
        return;
      }
      setOverallReplacementOption3Active();
      ReplacementItemAdapter.this.setReplacementOption("dont_replace");
      ReplacementItemAdapter.this.mICartApplication.getSharedPreferencesManager().saveOverallReplacementOption("dont_replace");
    }
    
    public void setOverallReplacementOption1Active()
    {
      Resources localResources = ReplacementItemAdapter.this.mContext.getResources();
      this.mReplacementOption1.setChecked(true);
      this.mReplacementOption1.setTextColor(localResources.getColor(2131493101));
      this.mReplacementOption2.setTextColor(localResources.getColor(2131493102));
      this.mReplacementOption3.setTextColor(localResources.getColor(2131493102));
    }
    
    public void setOverallReplacementOption2Active()
    {
      Resources localResources = ReplacementItemAdapter.this.mContext.getResources();
      this.mReplacementOption2.setChecked(true);
      this.mReplacementOption1.setTextColor(localResources.getColor(2131493102));
      this.mReplacementOption2.setTextColor(localResources.getColor(2131493101));
      this.mReplacementOption3.setTextColor(localResources.getColor(2131493102));
    }
    
    public void setOverallReplacementOption3Active()
    {
      Resources localResources = ReplacementItemAdapter.this.mContext.getResources();
      this.mReplacementOption3.setChecked(true);
      this.mReplacementOption1.setTextColor(localResources.getColor(2131493102));
      this.mReplacementOption2.setTextColor(localResources.getColor(2131493102));
      this.mReplacementOption3.setTextColor(localResources.getColor(2131493101));
    }
    
    void setOverallReplacementOptionsInactive()
    {
      Resources localResources = ReplacementItemAdapter.this.mContext.getResources();
      this.mReplacementOption1.setTextColor(localResources.getColor(2131493102));
      this.mReplacementOption2.setTextColor(localResources.getColor(2131493102));
      this.mReplacementOption3.setTextColor(localResources.getColor(2131493102));
      this.mReplacementOptions.clearCheck();
    }
  }
  
  public class ReplacementItem
  {
    public LineItem lineItem;
    public int viewType;
    
    public ReplacementItem() {}
  }
  
  class ReplacementItemViewHolder
    extends ReplacementItemAdapter.BaseReplacementItemViewHolder
  {
    @InjectView(2131559038)
    TextView cartCounterTextView;
    @InjectView(2131559088)
    RadioButton mReplacementOption1;
    @InjectView(2131559089)
    RadioButton mReplacementOption2;
    @InjectView(2131559090)
    RadioButton mReplacementOption3;
    @InjectView(2131559026)
    ImageView productImageView;
    @InjectView(2131559027)
    TextView productNameTextView;
    @InjectView(2131559028)
    TextView productPriceTextView;
    
    public ReplacementItemViewHolder(View paramView)
    {
      super(paramView);
    }
    
    public void bind(ReplacementItemAdapter.ReplacementItem paramReplacementItem)
    {
      int j = 0;
      Object localObject = paramReplacementItem.lineItem;
      if (localObject == null) {
        return;
      }
      if (TextUtils.isEmpty(((LineItem)localObject).replacementOption)) {
        ((LineItem)localObject).replacementOption = "by_shopper";
      }
      paramReplacementItem = ((LineItem)localObject).replacementOption;
      int i = -1;
      switch (paramReplacementItem.hashCode())
      {
      default: 
        switch (i)
        {
        default: 
          label108:
          paramReplacementItem = ((LineItem)localObject).variant;
          this.productNameTextView.setText(paramReplacementItem.name);
          localObject = Html.fromHtml(ReplacementItemAdapter.this.mContext.getString(2131165365, new Object[] { ((LineItem)localObject).singleDisplayAmount, ((LineItem)localObject).singleDisplayUnit }));
          this.productPriceTextView.setText("" + localObject);
          i = 0;
          if (ReplacementItemAdapter.this.mICartApplication.currentOrder != null) {
            i = ReplacementItemAdapter.this.mICartApplication.currentOrder.getQuantityInCart(paramReplacementItem.remoteId);
          }
          int k = ReplacementItemAdapter.this.mICartApplication.getEditingCartInProgress().getQuantityInProgressInCart(paramReplacementItem.remoteId);
          if (k != Integer.MIN_VALUE) {
            i = k;
          }
          this.cartCounterTextView.setText(String.valueOf(i));
          localObject = this.cartCounterTextView;
          if (i <= 0) {}
          break;
        }
        break;
      }
      for (i = j;; i = 4)
      {
        ((TextView)localObject).setVisibility(i);
        paramReplacementItem = paramReplacementItem.getFirstImageProductUrl();
        Picasso.with(ReplacementItemAdapter.this.mContext).cancelRequest(this.productImageView);
        Picasso.with(ReplacementItemAdapter.this.mContext).load(paramReplacementItem).error(2130837739).placeholder(2130837739).into(this.productImageView);
        return;
        if (!paramReplacementItem.equals("by_shopper")) {
          break;
        }
        i = 0;
        break;
        if (!paramReplacementItem.equals("by_call")) {
          break;
        }
        i = 1;
        break;
        if (!paramReplacementItem.equals("dont_replace")) {
          break;
        }
        i = 2;
        break;
        setReplacementOption1Active();
        break label108;
        setReplacementOption2Active();
        break label108;
        setReplacementOption3Active();
        break label108;
      }
    }
    
    @OnClick({2131559088})
    public void onReplacementOption1Click()
    {
      if (!this.mReplacementOption1.isChecked()) {}
      int i;
      do
      {
        return;
        setReplacementOption1Active();
        i = getAdapterPosition();
      } while ((i >= ReplacementItemAdapter.this.getItemCount()) || (i == -1));
      ((ReplacementItemAdapter.ReplacementItem)ReplacementItemAdapter.this.mReplacement.get(i)).lineItem.replacementOption = "by_shopper";
      ReplacementItemAdapter.this.mOverallReplacementItemViewHolder.setOverallReplacementOptionsInactive();
      ReplacementItemAdapter.this.mICartApplication.getSharedPreferencesManager().saveOverallReplacementOption("per_product");
    }
    
    @OnClick({2131559089})
    public void onReplacementOption2Click()
    {
      if (!this.mReplacementOption2.isChecked()) {}
      int i;
      do
      {
        return;
        setReplacementOption2Active();
        i = getAdapterPosition();
      } while ((i >= ReplacementItemAdapter.this.getItemCount()) || (i == -1));
      ((ReplacementItemAdapter.ReplacementItem)ReplacementItemAdapter.this.mReplacement.get(i)).lineItem.replacementOption = "by_call";
      ReplacementItemAdapter.this.mOverallReplacementItemViewHolder.setOverallReplacementOptionsInactive();
      ReplacementItemAdapter.this.mICartApplication.getSharedPreferencesManager().saveOverallReplacementOption("per_product");
    }
    
    @OnClick({2131559090})
    public void onReplacementOption3Click()
    {
      if (!this.mReplacementOption3.isChecked()) {}
      int i;
      do
      {
        return;
        setReplacementOption3Active();
        i = getAdapterPosition();
      } while ((i >= ReplacementItemAdapter.this.getItemCount()) || (i == -1));
      ((ReplacementItemAdapter.ReplacementItem)ReplacementItemAdapter.this.mReplacement.get(i)).lineItem.replacementOption = "dont_replace";
      ReplacementItemAdapter.this.mOverallReplacementItemViewHolder.setOverallReplacementOptionsInactive();
      ReplacementItemAdapter.this.mICartApplication.getSharedPreferencesManager().saveOverallReplacementOption("per_product");
    }
    
    public void setReplacementOption1Active()
    {
      Resources localResources = ReplacementItemAdapter.this.mContext.getResources();
      this.mReplacementOption1.setChecked(true);
      this.mReplacementOption1.setTextColor(localResources.getColor(2131493101));
      this.mReplacementOption2.setTextColor(localResources.getColor(2131493102));
      this.mReplacementOption3.setTextColor(localResources.getColor(2131493102));
    }
    
    public void setReplacementOption2Active()
    {
      Resources localResources = ReplacementItemAdapter.this.mContext.getResources();
      this.mReplacementOption2.setChecked(true);
      this.mReplacementOption1.setTextColor(localResources.getColor(2131493102));
      this.mReplacementOption2.setTextColor(localResources.getColor(2131493101));
      this.mReplacementOption3.setTextColor(localResources.getColor(2131493102));
    }
    
    public void setReplacementOption3Active()
    {
      Resources localResources = ReplacementItemAdapter.this.mContext.getResources();
      this.mReplacementOption3.setChecked(true);
      this.mReplacementOption1.setTextColor(localResources.getColor(2131493102));
      this.mReplacementOption2.setTextColor(localResources.getColor(2131493102));
      this.mReplacementOption3.setTextColor(localResources.getColor(2131493101));
    }
  }
  
  class TextInfoItemViewHolder
    extends ReplacementItemAdapter.BaseReplacementItemViewHolder
  {
    @InjectView(2131559102)
    TextView mIndividualReplacementOptionsInfo;
    
    public TextInfoItemViewHolder(View paramView)
    {
      super(paramView);
    }
    
    public void bind(ReplacementItemAdapter.ReplacementItem paramReplacementItem)
    {
      int i = 8;
      if (ReplacementItemAdapter.this.showingOverallReplacementOptions) {
        i = 0;
      }
      this.mIndividualReplacementOptionsInfo.setVisibility(i);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/ReplacementItemAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
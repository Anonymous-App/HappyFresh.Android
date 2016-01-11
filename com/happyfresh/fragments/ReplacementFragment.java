package com.happyfresh.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.CheckoutActivity;
import com.happyfresh.adapters.ReplacementItemAdapter;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.Variant;
import com.happyfresh.utils.GAUtils;
import com.happyfresh.utils.LogUtils;
import com.optimizely.CodeBlocks.CodeBranch;
import com.optimizely.CodeBlocks.DefaultCodeBranch;
import com.optimizely.CodeBlocks.OptimizelyCodeBlock;
import com.optimizely.CodeBlocks.OptimizelyCodeBlocks.OptimizelyCodeBlockBuilder;
import com.optimizely.Optimizely;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;

public class ReplacementFragment
  extends BaseFragment
{
  private static final String TAG = ReplacementFragment.class.getSimpleName();
  private static OptimizelyCodeBlock replacementFlow = Optimizely.codeBlock("ReplacementFlow").withBranchNames(new String[] { "NoOverallReplacementOption" });
  @InjectView(2131558664)
  CircularProgressBar mButtonProgress;
  private Listener mCurrentOrderSetListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject, ICartNotification.Type paramAnonymousType, Order paramAnonymousOrder)
    {
      ReplacementFragment.this.getApplication().currentOrder = paramAnonymousOrder;
      ReplacementFragment.this.onActivityCreated(null);
    }
  };
  List<LineItem> mLineItems = new ArrayList();
  @InjectView(2131558831)
  View mOverlay;
  @InjectView(2131558832)
  View mOverlayProgress;
  @InjectView(2131558715)
  RecyclerView mRecyclerView;
  ReplacementItemAdapter mReplacementAdapter;
  @InjectView(2131558663)
  Button mSubmitButton;
  
  private void processToDelivery()
  {
    ((CheckoutActivity)getActivity()).getFragment().goToDelivery();
  }
  
  @OnClick({2131558663})
  void attemptOpenDelivery()
  {
    if (getApplication().currentOrder == null)
    {
      Toast.makeText(this.mContext, this.mContext.getString(2131165671), 0).show();
      return;
    }
    this.mSubmitButton.setClickable(false);
    updateReplacements();
    Object localObject = getApplication().getSharedPreferencesManager().getOverallReplacementOption();
    String str = Order.getReplacementOptionGAText((String)localObject);
    GAUtils.trackReplacementOption(this.mContext, str);
    str = getApplication().getSharedPreferencesManager().getLastOverallReplacementOption();
    if ("per_product".equalsIgnoreCase((String)localObject))
    {
      int i = 0;
      localObject = getApplication().currentOrder.lineItems.iterator();
      while (((Iterator)localObject).hasNext()) {
        if (!str.equalsIgnoreCase(((LineItem)((Iterator)localObject).next()).replacementOption)) {
          i += 1;
        }
      }
      GAUtils.trackReplacementPerProductOption(this.mContext, i);
    }
    processToDelivery();
    resetView();
  }
  
  protected String getScreenName()
  {
    return "Replacement";
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mButtonProgress.setVisibility(4);
    this.mOverlayProgress.setVisibility(4);
    this.mRecyclerView.setNestedScrollingEnabled(false);
    paramBundle = new LinearLayoutManager(getActivity());
    this.mRecyclerView.setLayoutManager(paramBundle);
    this.mReplacementAdapter = new ReplacementItemAdapter(getActivity(), getApplication());
    if (getApplication().currentOrder == null)
    {
      getApplication().setCurrentOrder();
      return;
    }
    this.mLineItems = new ArrayList(getApplication().currentOrder.lineItems);
    this.mReplacementAdapter.setData(this.mLineItems);
    this.mRecyclerView.setAdapter(this.mReplacementAdapter);
    updateView();
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    this.mLineItems = new ArrayList(getApplication().currentOrder.lineItems);
    this.mReplacementAdapter.addAll(this.mLineItems);
    this.mReplacementAdapter.notifyDataSetChanged();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    GAUtils.trackingECommerceCheckout(getApplication(), 1, null, getScreenName());
    getApplication().getICartNotification().addCurrentOrderSetListener(this.mCurrentOrderSetListener);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903145, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroy()
  {
    getApplication().getICartNotification().removeCurrentOrderSetListener(this.mCurrentOrderSetListener);
    ButterKnife.reset(this);
    super.onDestroy();
  }
  
  public void onPause()
  {
    super.onPause();
    updateReplacements();
  }
  
  public void onResume()
  {
    super.onResume();
    if (getApplication().currentOrder == null)
    {
      getApplication().setCurrentOrder();
      return;
    }
    updateView();
  }
  
  void resetView()
  {
    if (getActivity() == null) {
      return;
    }
    this.mButtonProgress.setVisibility(4);
    this.mSubmitButton.setClickable(true);
  }
  
  protected void sendTracker() {}
  
  void updateReplacements()
  {
    if (getApplication().currentOrder == null) {
      return;
    }
    List localList1 = getApplication().currentOrder.lineItems;
    List localList2 = this.mReplacementAdapter.getReplacements();
    int j = localList1.size();
    int k = localList2.size();
    int i = 0;
    if (i < j)
    {
      LineItem localLineItem1 = (LineItem)localList1.get(i);
      if (i >= k) {}
      for (;;)
      {
        i += 1;
        break;
        LineItem localLineItem2 = (LineItem)localList2.get(i);
        if (localLineItem1.variant.remoteId == localLineItem2.variant.remoteId) {
          localLineItem1.replacementOption = localLineItem2.replacementOption;
        }
      }
    }
    try
    {
      getApplication().getOrderManager().updateReplacements(getApplication().currentOrder.lineItems, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          LogUtils.LOG("Update Replacements Failed");
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          LogUtils.LOG("Update Replacements Success");
        }
      });
      return;
    }
    catch (JSONException localJSONException) {}catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
  }
  
  void updateView()
  {
    Object localObject1 = this.mICartApplication.currentOrder;
    if ((localObject1 != null) && (((Order)localObject1).lineItems.size() > 0))
    {
      localObject1 = ((LineItem)((Order)localObject1).lineItems.get(0)).replacementOption;
      Object localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = "by_shopper";
      }
      int j = 1;
      localObject1 = this.mICartApplication.currentOrder.lineItems.iterator();
      LineItem localLineItem;
      do
      {
        i = j;
        if (!((Iterator)localObject1).hasNext()) {
          break;
        }
        localLineItem = (LineItem)((Iterator)localObject1).next();
        if (TextUtils.isEmpty(localLineItem.replacementOption)) {
          localLineItem.replacementOption = "by_shopper";
        }
      } while (localLineItem.replacementOption.equalsIgnoreCase((String)localObject2));
      int i = 0;
      localObject1 = this.mICartApplication.getSharedPreferencesManager().getOverallReplacementOption();
      if ((i == 0) || ("per_product".equalsIgnoreCase((String)localObject1))) {
        break label243;
      }
      if (!"by_shopper".equalsIgnoreCase((String)localObject1)) {
        break label203;
      }
      this.mReplacementAdapter.setOverallReplacement1Checked();
    }
    for (;;)
    {
      replacementFlow.execute(new DefaultCodeBranch()new CodeBranch
      {
        public void execute()
        {
          ReplacementFragment.this.mReplacementAdapter.setShowingOverallReplacementOptions(true);
        }
      }, new CodeBranch[] { new CodeBranch()
      {
        public void execute()
        {
          ReplacementFragment.this.mReplacementAdapter.setShowingOverallReplacementOptions(false);
        }
      } });
      return;
      localObject1 = null;
      break;
      label203:
      if ("by_call".equalsIgnoreCase((String)localObject1))
      {
        this.mReplacementAdapter.setOverallReplacement2Checked();
      }
      else if ("dont_replace".equalsIgnoreCase((String)localObject1))
      {
        this.mReplacementAdapter.setOverallReplacement3Checked();
        continue;
        label243:
        this.mICartApplication.getSharedPreferencesManager().saveOverallReplacementOption("per_product");
        this.mReplacementAdapter.setOverallReplacementOptionsInactive();
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ReplacementFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
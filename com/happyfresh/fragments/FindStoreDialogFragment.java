package com.happyfresh.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.adapters.FindStoreAdapter;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.listeners.OnFindStoreBackListener;
import com.happyfresh.listeners.OnFindStoreClickListener;
import com.happyfresh.managers.ConfigManager;
import com.happyfresh.models.Country;
import com.happyfresh.models.FindStoreModel;
import com.happyfresh.models.State;
import java.util.ArrayList;
import java.util.List;

public class FindStoreDialogFragment
  extends BaseDialogFragment
{
  public static final String LIST = "LIST";
  public static final String SCREEN_NAME = "SCREEN_NAME";
  private static final String TAG;
  public static final String TITLE = "TITLE";
  private FindStoreAdapter mAdapter;
  private List<FindStoreModel> mData = new ArrayList();
  @InjectView(2131558617)
  TextView mDialogTitle;
  private boolean mDismiss = false;
  ICartApplication mICartApplication;
  private boolean mLoading = false;
  @InjectView(2131558711)
  ImageButton mNavigateImage;
  private OnFindStoreBackListener mOnFindStoreBackListener;
  private OnFindStoreClickListener mOnFindStoreClickListener;
  private boolean mOnSaveInstance = false;
  @InjectView(2131558715)
  RecyclerView mRecyclerList;
  private String mScreenName;
  @InjectView(2131558714)
  SearchView mSearchView;
  private FindStoreModel mSelectedModel;
  private String mTitle;
  private int mTitleResource;
  
  static
  {
    if (!FindStoreDialogFragment.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      TAG = FindStoreDialogFragment.class.getSimpleName();
      return;
    }
  }
  
  public static FindStoreDialogFragment newInstance(int paramInt, ArrayList<Parcelable> paramArrayList, String paramString)
  {
    FindStoreDialogFragment localFindStoreDialogFragment = new FindStoreDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putInt("TITLE", paramInt);
    localBundle.putParcelableArrayList("LIST", paramArrayList);
    localBundle.putString("SCREEN_NAME", paramString);
    localFindStoreDialogFragment.setArguments(localBundle);
    return localFindStoreDialogFragment;
  }
  
  public void clearAllProgressBar()
  {
    if (this.mAdapter != null) {
      this.mAdapter.clearAllProgressBar();
    }
  }
  
  @OnClick({2131558711})
  void close()
  {
    if (this.mOnFindStoreBackListener != null) {
      this.mOnFindStoreBackListener.onBack();
    }
    dismiss();
  }
  
  void commit(FindStoreModel paramFindStoreModel)
  {
    if (getActivity() == null) {
      return;
    }
    if (this.mOnFindStoreClickListener != null) {
      this.mOnFindStoreClickListener.onSelected(paramFindStoreModel);
    }
    this.mLoading = false;
    this.mSelectedModel = null;
    this.mAdapter.setEnabledAll(true);
  }
  
  void commitAndDismiss(FindStoreModel paramFindStoreModel)
  {
    commit(paramFindStoreModel);
    dismissDelayed();
  }
  
  public void dismissDelayed()
  {
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        if ((FindStoreDialogFragment.this.getActivity() == null) || (FindStoreDialogFragment.this.getFragmentManager() == null)) {}
        do
        {
          return;
          FindStoreDialogFragment.access$302(FindStoreDialogFragment.this, true);
        } while (FindStoreDialogFragment.this.mOnSaveInstance);
        FindStoreDialogFragment.access$102(FindStoreDialogFragment.this, false);
        FindStoreDialogFragment.access$002(FindStoreDialogFragment.this, null);
        FindStoreDialogFragment.this.mAdapter.setEnabledAll(true);
        FindStoreDialogFragment.this.dismiss();
      }
    }, 200L);
  }
  
  void fetchStateByCountry(final FindStoreModel paramFindStoreModel)
  {
    assert (paramFindStoreModel.getClass().equals(Country.class));
    ICartApplication.get(this).getConfigManager().getStates(paramFindStoreModel.getRemoteId(), new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        FindStoreDialogFragment.this.dismissDelayed();
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        FindStoreDialogFragment.this.commit(paramFindStoreModel);
      }
    });
  }
  
  void fetchSubDistrictByStateId(final FindStoreModel paramFindStoreModel)
  {
    ICartApplication.get(this).getConfigManager().getSubdistrictByStateId(paramFindStoreModel.getRemoteId(), new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        FindStoreDialogFragment.this.dismissDelayed();
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        FindStoreDialogFragment.this.commit(paramFindStoreModel);
      }
    });
  }
  
  public boolean isLoading()
  {
    return this.mLoading;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mTitleResource = getArguments().getInt("TITLE");
    this.mDialogTitle.setText(this.mTitleResource);
    if (this.mTitle != null) {
      this.mDialogTitle.setText(this.mTitle);
    }
    if (this.mTitleResource != 2131165442) {
      this.mNavigateImage.setImageResource(2130837526);
    }
    for (;;)
    {
      this.mRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
      this.mAdapter = new FindStoreAdapter(getActivity());
      this.mRecyclerList.setAdapter(this.mAdapter);
      this.mAdapter.setOnFindStoreClickListener(new OnFindStoreClickListener()
      {
        public void onSelected(FindStoreModel paramAnonymousFindStoreModel)
        {
          if (FindStoreDialogFragment.this.mSelectedModel == paramAnonymousFindStoreModel) {
            return;
          }
          if (FindStoreDialogFragment.this.isLoading())
          {
            paramAnonymousFindStoreModel.setSelected(false);
            paramAnonymousFindStoreModel.setProgressing(false);
            return;
          }
          FindStoreDialogFragment.access$102(FindStoreDialogFragment.this, true);
          FindStoreDialogFragment.access$002(FindStoreDialogFragment.this, paramAnonymousFindStoreModel);
          FindStoreDialogFragment.this.mAdapter.setEnabledAll(false);
          if (paramAnonymousFindStoreModel.getClass().equals(Country.class))
          {
            FindStoreDialogFragment.this.fetchStateByCountry(paramAnonymousFindStoreModel);
            return;
          }
          if (paramAnonymousFindStoreModel.getClass().equals(State.class))
          {
            FindStoreDialogFragment.this.fetchSubDistrictByStateId(paramAnonymousFindStoreModel);
            return;
          }
          FindStoreDialogFragment.this.commit(paramAnonymousFindStoreModel);
        }
      });
      paramBundle = getArguments().getParcelableArrayList("LIST");
      this.mData.clear();
      this.mData.addAll(paramBundle);
      this.mAdapter.addAll(this.mData);
      this.mAdapter.setEnabledAll(true);
      this.mAdapter.notifyDataSetChanged();
      this.mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
      {
        public boolean onQueryTextChange(String paramAnonymousString)
        {
          if (paramAnonymousString.length() == 0) {
            FindStoreDialogFragment.this.mAdapter.flushFilter();
          }
          for (;;)
          {
            return true;
            FindStoreDialogFragment.this.mAdapter.setFilter(paramAnonymousString);
          }
        }
        
        public boolean onQueryTextSubmit(String paramAnonymousString)
        {
          return false;
        }
      });
      this.mSearchView.setOnCloseListener(new SearchView.OnCloseListener()
      {
        public boolean onClose()
        {
          FindStoreDialogFragment.this.mAdapter.flushFilter();
          return false;
        }
      });
      return;
      this.mNavigateImage.setImageResource(2130837585);
    }
  }
  
  public void onCancel(DialogInterface paramDialogInterface)
  {
    super.onCancel(paramDialogInterface);
    if (this.mOnFindStoreBackListener != null) {
      this.mOnFindStoreBackListener.onBack();
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mICartApplication = getApplication();
    this.mScreenName = getArguments().getString("SCREEN_NAME");
    sendTracker(this.mScreenName);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903121, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    getDialog().getWindow().requestFeature(1);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public void onResume()
  {
    super.onResume();
    if (this.mOnSaveInstance)
    {
      this.mOnSaveInstance = false;
      if (this.mDismiss)
      {
        this.mDismiss = false;
        dismiss();
      }
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    this.mOnSaveInstance = true;
  }
  
  public void setOnFindStoreBackListener(OnFindStoreBackListener paramOnFindStoreBackListener)
  {
    this.mOnFindStoreBackListener = paramOnFindStoreBackListener;
  }
  
  public void setOnFindStoreClickListener(OnFindStoreClickListener paramOnFindStoreClickListener)
  {
    this.mOnFindStoreClickListener = paramOnFindStoreClickListener;
  }
  
  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/FindStoreDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.happyfresh.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;
import com.happyfresh.activities.LoginActivity;
import com.happyfresh.activities.MyAccountActivity;
import com.happyfresh.activities.SignUpActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartDialogCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.customs.CircularProgressDrawable;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.managers.UserManager;
import com.happyfresh.models.Address;
import com.happyfresh.models.District;
import com.happyfresh.models.State;
import com.happyfresh.models.StateTimestamp;
import com.happyfresh.models.SubDistrict;
import com.happyfresh.models.User;
import com.happyfresh.utils.DialogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAccountFragment
  extends BaseFragment
{
  private static final String TAG = MyAccountFragment.class.getSimpleName();
  @InjectView(2131558781)
  ViewGroup mAccountContainer;
  @InjectView(2131558779)
  ViewGroup mAccountNotLoggedInContainer;
  private AddressItemAdapter mAdapter;
  @InjectView(2131558785)
  TextView mAddNewAddress;
  private Listener<List<Address>> mCurrentUserAddressListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject, ICartNotification.Type paramAnonymousType, List<Address> paramAnonymousList)
    {
      MyAccountFragment.this.setupList();
    }
  };
  @InjectView(2131558605)
  ListView mListView;
  @InjectView(2131558784)
  View mLogoutButton;
  @InjectView(2131558788)
  RadioButton mRbEnglish;
  @InjectView(2131558789)
  RadioButton mRbLocalLanguage;
  @InjectView(2131558787)
  RadioGroup mRgLanguage;
  @InjectView(2131558783)
  TextView mUserFullName;
  
  private void createNewAddress()
  {
    CreateAddressFormFragment localCreateAddressFormFragment = new CreateAddressFormFragment();
    localCreateAddressFormFragment.setTargetFragment(this, 2000);
    Object localObject = new Bundle();
    ((Bundle)localObject).putBoolean("ICartConstant.KEYS.EXPRESS_CHECKOUT", true);
    ((Bundle)localObject).putString("ICartConstant.KEYS.ADDRESS_SCREEN_TITLE", "Add Address");
    localCreateAddressFormFragment.setArguments((Bundle)localObject);
    localObject = ((MyAccountActivity)getActivity()).getSupportActionBar();
    if (localObject != null)
    {
      ((ActionBar)localObject).setDefaultDisplayHomeAsUpEnabled(true);
      ((ActionBar)localObject).setDisplayHomeAsUpEnabled(true);
      ((ActionBar)localObject).setTitle(getString(2131165270));
    }
    getFragmentManager().beginTransaction().add(2131558581, localCreateAddressFormFragment).addToBackStack(null).commit();
  }
  
  private void editAddress(int paramInt)
  {
    CreateAddressFormFragment localCreateAddressFormFragment = new CreateAddressFormFragment();
    localCreateAddressFormFragment.setTargetFragment(this, 3000);
    Object localObject = new Bundle();
    ((Bundle)localObject).putBoolean("ICartConstant.KEYS.EXPRESS_CHECKOUT", true);
    ((Bundle)localObject).putInt("ICartConstant.KEYS.EDITED_ADDRESS", paramInt);
    ((Bundle)localObject).putString("ICartConstant.KEYS.ADDRESS_SCREEN_TITLE", "Edit Address");
    localCreateAddressFormFragment.setArguments((Bundle)localObject);
    localObject = ((MyAccountActivity)getActivity()).getSupportActionBar();
    if (localObject != null)
    {
      ((ActionBar)localObject).setDefaultDisplayHomeAsUpEnabled(true);
      ((ActionBar)localObject).setDisplayHomeAsUpEnabled(true);
      ((ActionBar)localObject).setTitle(getString(2131165371));
    }
    getFragmentManager().beginTransaction().add(2131558581, localCreateAddressFormFragment).addToBackStack(null).commit();
  }
  
  private void restartApp()
  {
    StateTimestamp.deleteAll();
    State.deleteAll();
    SubDistrict.deleteAll();
    District.deleteAll();
    Intent localIntent = getApplication().getBaseContext().getPackageManager().getLaunchIntentForPackage(getApplication().getBaseContext().getPackageName());
    localIntent.addFlags(67108864);
    startActivity(localIntent);
  }
  
  private void sendPreferredLanguage(final int paramInt)
  {
    ICartApplication localICartApplication = getApplication();
    Object localObject4 = localICartApplication.getLocaleByCountryCode(localICartApplication.getCountryCode());
    Object localObject1 = "en";
    final Object localObject2 = Locale.ENGLISH;
    final Object localObject3 = this.mRbEnglish;
    if (paramInt == 2131558789)
    {
      RadioButton localRadioButton = this.mRbLocalLanguage;
      String str = ((Locale)localObject4).getLanguage();
      localObject1 = str;
      localObject2 = localObject4;
      localObject3 = localRadioButton;
      if ("in".equalsIgnoreCase(str))
      {
        localObject1 = "id";
        localObject3 = localRadioButton;
        localObject2 = localObject4;
      }
    }
    localObject4 = new CircularProgressDrawable(2131493005, 4.0F);
    ((RadioButton)localObject3).setCompoundDrawablesWithIntrinsicBounds(null, null, (Drawable)localObject4, null);
    ((CircularProgressDrawable)localObject4).setBounds(-30, -30, 30, 30);
    ((CircularProgressDrawable)localObject4).setColorFilter(getResources().getColor(2131493005), PorterDuff.Mode.LIGHTEN);
    ((CircularProgressDrawable)localObject4).start();
    try
    {
      localICartApplication.getUserManager().sendPreferredLanguage((String)localObject1, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          localObject3.setCompoundDrawables(null, null, null, null);
          if (paramInt == 2131558788)
          {
            MyAccountFragment.this.mRbEnglish.setChecked(false);
            MyAccountFragment.this.mRbLocalLanguage.setChecked(true);
            return;
          }
          MyAccountFragment.this.mRbEnglish.setChecked(true);
          MyAccountFragment.this.mRbLocalLanguage.setChecked(false);
        }
        
        public void onSuccess(User paramAnonymousUser)
        {
          MyAccountFragment.this.getApplication().getSharedPreferencesManager().saveLocale(localObject2);
          if (MyAccountFragment.this.getActivity() != null)
          {
            localObject3.setCompoundDrawables(null, null, null, null);
            MyAccountFragment.this.restartApp();
          }
        }
      });
      return;
    }
    catch (Exception localException)
    {
      ((RadioButton)localObject3).setCompoundDrawables(null, null, null, null);
      if (paramInt == 2131558788)
      {
        this.mRbEnglish.setChecked(false);
        this.mRbLocalLanguage.setChecked(true);
        return;
      }
      this.mRbEnglish.setChecked(true);
      this.mRbLocalLanguage.setChecked(false);
    }
  }
  
  private void setLanguageOptions()
  {
    final ICartApplication localICartApplication = getApplication();
    final Locale localLocale1 = localICartApplication.getLocaleByCountryCode(localICartApplication.getCountryCode());
    this.mRbLocalLanguage.setText(localLocale1.getDisplayLanguage());
    final Locale localLocale2 = localICartApplication.getSharedPreferencesManager().getLocale();
    if (localLocale2.equals(Locale.ENGLISH)) {
      this.mRgLanguage.check(2131558788);
    }
    for (;;)
    {
      this.mRgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
      {
        public void onCheckedChanged(RadioGroup paramAnonymousRadioGroup, final int paramAnonymousInt)
        {
          paramAnonymousRadioGroup = localLocale1;
          if (paramAnonymousInt == 2131558788) {
            paramAnonymousRadioGroup = Locale.ENGLISH;
          }
          if (paramAnonymousRadioGroup.equals(localLocale2)) {}
          while (localICartApplication.getSharedPreferencesManager().getLocale().equals(paramAnonymousRadioGroup)) {
            return;
          }
          DialogUtils.showDialog(MyAccountFragment.this.getActivity(), MyAccountFragment.this.getString(2131165319), MyAccountFragment.this.getString(2131165285), MyAccountFragment.this.getString(17039379), MyAccountFragment.this.getString(17039369), null, new ICartDialogCallback()
          {
            public void onCancel() {}
            
            public void onNegative(MaterialDialog paramAnonymous2MaterialDialog) {}
            
            public void onNeutral(MaterialDialog paramAnonymous2MaterialDialog) {}
            
            public void onPositive(MaterialDialog paramAnonymous2MaterialDialog)
            {
              MyAccountFragment.this.sendPreferredLanguage(paramAnonymousInt);
            }
          });
        }
      });
      return;
      if (localLocale2.getDisplayLanguage().equalsIgnoreCase(localLocale1.getDisplayLanguage())) {
        this.mRgLanguage.check(2131558789);
      }
    }
  }
  
  private void setupList()
  {
    this.mAdapter = new AddressItemAdapter(getActivity());
    this.mAdapter.addAll(getApplication().getCurrentUser().getAddresses());
    this.mListView.setAdapter(this.mAdapter);
    this.mAdapter.setOnItemClickListener(new OnAddressClickListener()
    {
      public void onItemClick(int paramAnonymousInt)
      {
        MyAccountFragment.this.editAddress(paramAnonymousInt);
      }
    });
  }
  
  private void toggleView()
  {
    if (getApplication().isLoggedIn())
    {
      this.mAccountContainer.setVisibility(0);
      this.mAccountNotLoggedInContainer.setVisibility(8);
      updateView();
      return;
    }
    this.mAccountContainer.setVisibility(8);
    this.mAccountNotLoggedInContainer.setVisibility(0);
  }
  
  protected String getScreenName()
  {
    return "Account";
  }
  
  @OnClick({2131558765})
  void login()
  {
    startActivityForResult(new Intent(getActivity(), LoginActivity.class), 11);
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mAdapter = new AddressItemAdapter(getActivity());
    this.mListView.setAdapter(this.mAdapter);
    this.mAdapter.setOnItemClickListener(new OnAddressClickListener()
    {
      public void onItemClick(int paramAnonymousInt)
      {
        MyAccountFragment.this.editAddress(paramAnonymousInt);
      }
    });
    toggleView();
    setLanguageOptions();
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt2 == -1) && (paramInt1 == 11))
    {
      toggleView();
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903139, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onPause()
  {
    super.onPause();
    getApplication().getICartNotification().removeCurrentUserAddressSetListener(this.mCurrentUserAddressListener);
  }
  
  public void onResume()
  {
    super.onResume();
    getApplication().getICartNotification().addCurrentUserAddressSetListener(this.mCurrentUserAddressListener);
  }
  
  public void popBackToAccount()
  {
    setupList();
    getFragmentManager().popBackStackImmediate();
  }
  
  @OnClick({2131558780})
  void signup()
  {
    startActivityForResult(new Intent(getActivity(), SignUpActivity.class), 11);
  }
  
  void updateView()
  {
    String str = "";
    if (getApplication().getCurrentUser() != null)
    {
      str = getApplication().getCurrentUser().getFullName();
      this.mAddNewAddress.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          MyAccountFragment.this.createNewAddress();
        }
      });
      this.mLogoutButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          DialogUtils.showDialog(MyAccountFragment.this.getActivity(), null, MyAccountFragment.this.getString(2131165452), MyAccountFragment.this.getString(2131165478), MyAccountFragment.this.getString(2131165293), null, new ICartDialogCallback()
          {
            public void onCancel() {}
            
            public void onNegative(MaterialDialog paramAnonymous2MaterialDialog)
            {
              paramAnonymous2MaterialDialog.dismiss();
            }
            
            public void onNeutral(MaterialDialog paramAnonymous2MaterialDialog) {}
            
            public void onPositive(MaterialDialog paramAnonymous2MaterialDialog)
            {
              MyAccountFragment.this.getApplication().onLogout();
            }
          });
        }
      });
    }
    for (;;)
    {
      this.mUserFullName.setText(getString(2131165405, new Object[] { str }));
      setupList();
      return;
      this.mAddNewAddress.setVisibility(8);
    }
  }
  
  protected class AddressItemAdapter
    extends BaseAdapter
  {
    private List<Address> mAddressesList = new ArrayList();
    private Context mContext;
    private MyAccountFragment.OnAddressClickListener mItemClickListener;
    
    public AddressItemAdapter(Context paramContext)
    {
      this.mContext = paramContext;
    }
    
    public void addAll(List<Address> paramList)
    {
      this.mAddressesList.clear();
      this.mAddressesList = paramList;
    }
    
    public int getCount()
    {
      return this.mAddressesList.size();
    }
    
    public Address getItem(int paramInt)
    {
      return (Address)this.mAddressesList.get(paramInt);
    }
    
    public long getItemId(int paramInt)
    {
      return 0L;
    }
    
    public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
      {
        paramView = LayoutInflater.from(this.mContext).inflate(2130903070, null);
        paramViewGroup = new AddressItemViewHolder(paramView);
        paramView.setTag(paramViewGroup);
      }
      for (;;)
      {
        Address localAddress = getItem(paramInt);
        paramViewGroup.deliveryAddress.setText(localAddress.toString(MyAccountFragment.this.mICartApplication));
        paramViewGroup.deliveryAddressView.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            MyAccountFragment.AddressItemAdapter.this.mItemClickListener.onItemClick(paramInt);
          }
        });
        return paramView;
        paramViewGroup = (AddressItemViewHolder)paramView.getTag();
      }
    }
    
    public void setOnItemClickListener(MyAccountFragment.OnAddressClickListener paramOnAddressClickListener)
    {
      this.mItemClickListener = paramOnAddressClickListener;
    }
    
    class AddressItemViewHolder
    {
      @InjectView(2131558516)
      TextView deliveryAddress;
      @InjectView(2131558515)
      View deliveryAddressView;
      
      public AddressItemViewHolder(View paramView)
      {
        ButterKnife.inject(this, paramView);
      }
    }
  }
  
  static abstract interface OnAddressClickListener
  {
    public abstract void onItemClick(int paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/MyAccountFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
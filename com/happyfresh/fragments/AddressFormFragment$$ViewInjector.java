package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class AddressFormFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, AddressFormFragment paramAddressFormFragment, Object paramObject)
  {
    paramAddressFormFragment.mCreateAddressHint = ((TextView)paramFinder.findRequiredView(paramObject, 2131558583, "field 'mCreateAddressHint'"));
    paramAddressFormFragment.mFirstName = ((EditText)paramFinder.findRequiredView(paramObject, 2131558584, "field 'mFirstName'"));
    paramAddressFormFragment.mLastName = ((EditText)paramFinder.findRequiredView(paramObject, 2131558585, "field 'mLastName'"));
    paramAddressFormFragment.mAddressFormNormal = paramFinder.findRequiredView(paramObject, 2131558587, "field 'mAddressFormNormal'");
    paramAddressFormFragment.mStreetAddress = ((EditText)paramFinder.findRequiredView(paramObject, 2131558588, "field 'mStreetAddress'"));
    paramAddressFormFragment.mStreetAddress2 = ((EditText)paramFinder.findRequiredView(paramObject, 2131558589, "field 'mStreetAddress2'"));
    paramAddressFormFragment.mSubDistrictText = ((TextView)paramFinder.findRequiredView(paramObject, 2131558590, "field 'mSubDistrictText'"));
    paramAddressFormFragment.mPhoneNumber = ((EditText)paramFinder.findRequiredView(paramObject, 2131558599, "field 'mPhoneNumber'"));
    paramAddressFormFragment.mInstruction = ((EditText)paramFinder.findRequiredView(paramObject, 2131558600, "field 'mInstruction'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558601, "field 'mSaveAddress' and method 'attemptToSubmitAddress'");
    paramAddressFormFragment.mSaveAddress = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.attemptToSubmitAddress();
      }
    });
    paramAddressFormFragment.mAddressTitle = ((TextView)paramFinder.findRequiredView(paramObject, 2131558586, "field 'mAddressTitle'"));
    paramAddressFormFragment.mProgressIndicator = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558602, "field 'mProgressIndicator'"));
    paramAddressFormFragment.mAddressFormTaipei = paramFinder.findRequiredView(paramObject, 2131558591, "field 'mAddressFormTaipei'");
    paramAddressFormFragment.mSubDistrictTaipei = ((TextView)paramFinder.findRequiredView(paramObject, 2131558592, "field 'mSubDistrictTaipei'"));
    paramAddressFormFragment.mLaneTaipei = ((EditText)paramFinder.findRequiredView(paramObject, 2131558593, "field 'mLaneTaipei'"));
    paramAddressFormFragment.mAlleyTaipei = ((EditText)paramFinder.findRequiredView(paramObject, 2131558594, "field 'mAlleyTaipei'"));
    paramAddressFormFragment.mNoTaipei = ((EditText)paramFinder.findRequiredView(paramObject, 2131558595, "field 'mNoTaipei'"));
    paramAddressFormFragment.mUnitTaipei = ((EditText)paramFinder.findRequiredView(paramObject, 2131558596, "field 'mUnitTaipei'"));
    paramAddressFormFragment.mFloorTaipei = ((EditText)paramFinder.findRequiredView(paramObject, 2131558597, "field 'mFloorTaipei'"));
    paramAddressFormFragment.mCompanyTaxTaipei = ((EditText)paramFinder.findRequiredView(paramObject, 2131558598, "field 'mCompanyTaxTaipei'"));
  }
  
  public static void reset(AddressFormFragment paramAddressFormFragment)
  {
    paramAddressFormFragment.mCreateAddressHint = null;
    paramAddressFormFragment.mFirstName = null;
    paramAddressFormFragment.mLastName = null;
    paramAddressFormFragment.mAddressFormNormal = null;
    paramAddressFormFragment.mStreetAddress = null;
    paramAddressFormFragment.mStreetAddress2 = null;
    paramAddressFormFragment.mSubDistrictText = null;
    paramAddressFormFragment.mPhoneNumber = null;
    paramAddressFormFragment.mInstruction = null;
    paramAddressFormFragment.mSaveAddress = null;
    paramAddressFormFragment.mAddressTitle = null;
    paramAddressFormFragment.mProgressIndicator = null;
    paramAddressFormFragment.mAddressFormTaipei = null;
    paramAddressFormFragment.mSubDistrictTaipei = null;
    paramAddressFormFragment.mLaneTaipei = null;
    paramAddressFormFragment.mAlleyTaipei = null;
    paramAddressFormFragment.mNoTaipei = null;
    paramAddressFormFragment.mUnitTaipei = null;
    paramAddressFormFragment.mFloorTaipei = null;
    paramAddressFormFragment.mCompanyTaxTaipei = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/AddressFormFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.happyfresh.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartRestError;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.managers.ConfigManager;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.models.ContactUsConfig;
import com.happyfresh.models.ContactUsString;
import com.happyfresh.utils.DialogUtils;
import com.happyfresh.utils.GAUtils;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class ContactUsFragment
  extends BaseFragment
{
  private static final int MAX_ATTEMPT = 3;
  private static final String TAG = ContactUsFragment.class.getSimpleName();
  private int attempt = 0;
  @InjectView(2131558704)
  CircularProgressBar mCircularProgressBar;
  private ContactUsConfig mConfig;
  @InjectView(2131558701)
  EditText mContactUsMessage;
  @InjectView(2131558697)
  ImageView mCsIcon;
  @InjectView(2131558621)
  TextView mPart1Line1;
  @InjectView(2131558622)
  TextView mPart1Line2;
  @InjectView(2131558623)
  TextView mPart2Line1;
  @InjectView(2131558624)
  TextView mPart2Line2;
  @InjectView(2131558698)
  TextView mPart3Line1;
  @InjectView(2131558699)
  TextView mPart3Line2;
  @InjectView(2131558664)
  CircularProgressBar mProgressBar;
  @InjectView(2131558703)
  View mProgressContainer;
  @InjectView(2131558618)
  ScrollView mScrollView;
  @InjectView(2131558702)
  Button mSubmitButton;
  @InjectView(2131558700)
  TextView mTitleMessage;
  
  private void getContactUsConfiguration()
  {
    this.mProgressContainer.setVisibility(0);
    this.mCircularProgressBar.setVisibility(0);
    getApplication().getConfigManager().getContactUsConfiguration(new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        ContactUsFragment.access$208(ContactUsFragment.this);
        if ((ContactUsFragment.this.attempt > 3) || (ContactUsFragment.this.getActivity() == null)) {
          return;
        }
        ContactUsFragment.this.getContactUsConfiguration();
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        ContactUsFragment.access$002(ContactUsFragment.this, (ContactUsConfig)paramAnonymousObject);
        if (ContactUsFragment.this.getActivity() == null) {
          return;
        }
        ContactUsFragment.this.updateView();
      }
    });
  }
  
  private void updateView()
  {
    ContactUsString localContactUsString = this.mConfig.contactUsString;
    Object localObject;
    if (localContactUsString != null)
    {
      this.mCsIcon.setVisibility(0);
      localObject = this.mPart1Line1;
      if (localContactUsString.part1Line1 != null) {
        break label264;
      }
      str = "";
      ((TextView)localObject).setText(str);
      localObject = this.mPart1Line2;
      if (localContactUsString.part1Line2 != null) {
        break label272;
      }
      str = "";
      label55:
      ((TextView)localObject).setText(str);
      localObject = this.mPart2Line1;
      if (localContactUsString.part2Line1 != null) {
        break label280;
      }
      str = "";
      label75:
      ((TextView)localObject).setText(str);
      localObject = this.mPart2Line2;
      if (localContactUsString.part2Line2 != null) {
        break label288;
      }
      str = "";
      label95:
      ((TextView)localObject).setText(Html.fromHtml(str));
      if (localContactUsString.part3Line1 != null) {
        break label296;
      }
      str = "";
      label113:
      this.mPart3Line1.setText(Html.fromHtml(str));
      this.mPart3Line1.setClickable(true);
      this.mPart3Line1.setMovementMethod(LinkMovementMethod.getInstance());
      localObject = this.mPart3Line2;
      if (localContactUsString.part3Line2 != null) {
        break label377;
      }
      str = "";
      label157:
      ((TextView)localObject).setText(str);
      localObject = this.mSubmitButton;
      if (localContactUsString.submit != null) {
        break label385;
      }
      str = getResources().getString(2131165594);
      label184:
      ((Button)localObject).setText(str);
      this.mSubmitButton.setEnabled(true);
      localObject = this.mTitleMessage;
      if (localContactUsString.messageTitle != null) {
        break label393;
      }
      str = "";
      label212:
      ((TextView)localObject).setText(str);
      localObject = this.mContactUsMessage;
      if (localContactUsString.messagePlaceholder != null) {
        break label401;
      }
    }
    label264:
    label272:
    label280:
    label288:
    label296:
    label377:
    label385:
    label393:
    label401:
    for (String str = "";; str = localContactUsString.messagePlaceholder)
    {
      ((EditText)localObject).setHint(str);
      this.mProgressBar.setVisibility(8);
      this.mProgressContainer.setVisibility(8);
      this.mCircularProgressBar.setVisibility(4);
      return;
      str = localContactUsString.part1Line1;
      break;
      str = localContactUsString.part1Line2;
      break label55;
      str = localContactUsString.part2Line1;
      break label75;
      str = localContactUsString.part2Line2;
      break label95;
      if (this.mConfig.csPhone == null)
      {
        str = localContactUsString.part3Line1;
        break label113;
      }
      str = localContactUsString.part3Line1.replace(this.mConfig.csPhone, "<a href=\"tel:" + this.mConfig.csPhone + "\">" + this.mConfig.csPhone + "</a>");
      break label113;
      str = localContactUsString.part3Line2;
      break label157;
      str = localContactUsString.submit;
      break label184;
      str = localContactUsString.messageTitle;
      break label212;
    }
  }
  
  protected String getScreenName()
  {
    return "Contact Us";
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mSubmitButton.setEnabled(false);
    this.mProgressBar.setVisibility(0);
    getContactUsConfiguration();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903118, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.mContactUsMessage.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        new Handler().postDelayed(new Runnable()
        {
          public void run()
          {
            if ((ContactUsFragment.this.getActivity() == null) || (!ContactUsFragment.this.isAdded())) {}
            while (ContactUsFragment.this.mScrollView == null) {
              return;
            }
            ContactUsFragment.this.mScrollView.fullScroll(130);
          }
        }, 100L);
      }
    });
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  @OnClick({2131558702})
  void submit()
  {
    if (this.mContactUsMessage.getText().toString().isEmpty()) {
      return;
    }
    this.mProgressBar.setVisibility(0);
    String str = this.mContactUsMessage.getText().toString();
    try
    {
      getApplication().getOrderManager().createFeedback(str, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (ContactUsFragment.this.getActivity() == null) {
            return;
          }
          ContactUsFragment.this.mProgressBar.setVisibility(8);
          ICartRestError.showMessage(ContactUsFragment.this.getActivity(), paramAnonymousThrowable);
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          GAUtils.trackRating(ContactUsFragment.this.getApplication(), "Feedback");
          if (ContactUsFragment.this.getActivity() == null) {
            return;
          }
          ContactUsFragment.this.mProgressBar.setVisibility(8);
          ContactUsFragment.this.mContactUsMessage.setText("");
          paramAnonymousObject = ContactUsFragment.this.mConfig.contactUsString.successTitle;
          String str1 = ContactUsFragment.this.mConfig.contactUsString.successBody;
          String str2 = ContactUsFragment.this.mContext.getResources().getString(2131165478);
          DialogUtils.showDialog(ContactUsFragment.this.getActivity(), (String)paramAnonymousObject, str1, str2, null, null, null);
        }
      });
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ContactUsFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
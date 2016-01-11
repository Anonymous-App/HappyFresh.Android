package com.happyfresh.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.listeners.OnVerifyCodeListener;
import com.happyfresh.managers.UserManager;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class VerifyCodeDialogFragment
  extends BaseDialogFragment
{
  private final String TAG = getClass().getSimpleName();
  private ICartApplication mApplication;
  @InjectView(2131558896)
  Button mBtnSubmit;
  @InjectView(2131558895)
  TextView mEditPhoneNumber;
  private String[] mEtCode;
  @InjectView(2131558891)
  EditText mEtCode1;
  @InjectView(2131558892)
  EditText mEtCode2;
  @InjectView(2131558893)
  EditText mEtCode3;
  @InjectView(2131558894)
  EditText mEtCode4;
  private boolean[] mEtCodeFocus;
  private String mPhoneNumber;
  @InjectView(2131558694)
  CircularProgressBar mProgressBar;
  @InjectView(2131558888)
  TextView mSentSMSTo;
  private AutoSubmitTimer mSubmitTimer;
  private CountTimer mTimer;
  @InjectView(2131558887)
  TextView mTxtWarningStillNotReceived;
  private OnVerifyCodeListener mVerifyCodeListener;
  @InjectView(2131558885)
  View mWarningCodeSent;
  @InjectView(2131558886)
  View mWarningStillNotReceived;
  private TextWatcher textWatcher = new TextWatcher()
  {
    public void afterTextChanged(Editable paramAnonymousEditable)
    {
      VerifyCodeDialogFragment.this.toggleSubmitButton();
      VerifyCodeDialogFragment.this.changeEditTextFocusAfterBackspace();
    }
    
    public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      VerifyCodeDialogFragment.this.saveStateBeforeTextChanged();
    }
    
    public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
  };
  
  private void changeEditTextFocusAfterBackspace()
  {
    EditText[] arrayOfEditText = new EditText[4];
    arrayOfEditText[0] = this.mEtCode1;
    arrayOfEditText[1] = this.mEtCode2;
    arrayOfEditText[2] = this.mEtCode3;
    arrayOfEditText[3] = this.mEtCode4;
    int i = 0;
    while (i < 4)
    {
      if (this.mEtCodeFocus[i] != 0)
      {
        String str = arrayOfEditText[i].getText().toString();
        if (((this.mEtCode[i].length() == 1) || (this.mEtCode[i].length() == 0)) && (str.length() == 0) && (i > 0))
        {
          arrayOfEditText[(i - 1)].requestFocus();
          if (i == 3) {
            this.mSubmitTimer.cancel();
          }
        }
      }
      i += 1;
    }
    i = 0;
    while (i < 4)
    {
      arrayOfEditText[i] = null;
      i += 1;
    }
  }
  
  public static VerifyCodeDialogFragment newInstance(String paramString)
  {
    VerifyCodeDialogFragment localVerifyCodeDialogFragment = new VerifyCodeDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("ICartConstant.KEYS.PHONE_NUMBER", paramString);
    localVerifyCodeDialogFragment.setArguments(localBundle);
    return localVerifyCodeDialogFragment;
  }
  
  private void saveStateBeforeTextChanged()
  {
    EditText[] arrayOfEditText = new EditText[4];
    arrayOfEditText[0] = this.mEtCode1;
    arrayOfEditText[1] = this.mEtCode2;
    arrayOfEditText[2] = this.mEtCode3;
    arrayOfEditText[3] = this.mEtCode4;
    int i = 0;
    while (i < 4)
    {
      this.mEtCode[i] = arrayOfEditText[i].getText().toString();
      this.mEtCodeFocus[i] = arrayOfEditText[i].isFocused();
      i += 1;
    }
    i = 0;
    while (i < 4)
    {
      arrayOfEditText[i] = null;
      i += 1;
    }
  }
  
  private void toggleSubmitButton()
  {
    String str1 = this.mEtCode1.getText().toString();
    String str2 = this.mEtCode2.getText().toString();
    String str3 = this.mEtCode3.getText().toString();
    String str4 = this.mEtCode4.getText().toString();
    if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)) || (TextUtils.isEmpty(str3)) || (TextUtils.isEmpty(str4)))
    {
      this.mBtnSubmit.setEnabled(false);
      if ((!this.mEtCode1.isFocused()) || (str1.length() != 1)) {
        break label128;
      }
      this.mEtCode2.requestFocus();
    }
    label128:
    do
    {
      return;
      this.mBtnSubmit.setEnabled(true);
      this.mSubmitTimer.start();
      break;
      if ((this.mEtCode2.isFocused()) && (str2.length() == 1))
      {
        this.mEtCode3.requestFocus();
        return;
      }
    } while ((!this.mEtCode3.isFocused()) || (str3.length() != 1));
    this.mEtCode4.requestFocus();
  }
  
  @OnClick({2131558711})
  void close()
  {
    sendTracker("Delivery");
    dismiss();
  }
  
  @OnClick({2131558895})
  public void editPhoneNumber()
  {
    if (this.mVerifyCodeListener != null) {
      this.mVerifyCodeListener.onBackEditNumber(this.mPhoneNumber);
    }
    dismiss();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mApplication = ICartApplication.get(this);
    this.mPhoneNumber = getArguments().getString("ICartConstant.KEYS.PHONE_NUMBER");
    this.mTimer = new CountTimer(30000L, 1000L);
    this.mEtCode = new String[4];
    this.mEtCodeFocus = new boolean[4];
    this.mSubmitTimer = new AutoSubmitTimer(2000L, 2000L);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903157, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    getDialog().getWindow().requestFeature(1);
    setCancelable(false);
    return paramLayoutInflater;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.mTimer.cancel();
    this.mSubmitTimer.cancel();
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mEtCode1.addTextChangedListener(this.textWatcher);
    this.mEtCode2.addTextChangedListener(this.textWatcher);
    this.mEtCode3.addTextChangedListener(this.textWatcher);
    this.mEtCode4.addTextChangedListener(this.textWatcher);
    this.mSentSMSTo.setText(getString(2131165635, new Object[] { this.mPhoneNumber }));
    paramView = Html.fromHtml(getString(2131165373));
    this.mEditPhoneNumber.setText(paramView);
    paramView = Html.fromHtml(getString(2131165548));
    this.mTxtWarningStillNotReceived.setText(paramView);
    this.mProgressBar.setVisibility(8);
    toggleSubmitButton();
    this.mTimer.start();
    this.mEtCode1.requestFocus();
  }
  
  @OnClick({2131558887})
  void resendCode()
  {
    this.mWarningStillNotReceived.setVisibility(8);
    try
    {
      this.mApplication.getUserManager().verifyNumber(this.mPhoneNumber, new ICartCallback(this.TAG)
      {
        public void onSuccess(Object paramAnonymousObject)
        {
          VerifyCodeDialogFragment.this.mWarningCodeSent.setVisibility(0);
          VerifyCodeDialogFragment.this.mTimer.start();
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
  
  public void setOnVerifyCodeListener(OnVerifyCodeListener paramOnVerifyCodeListener)
  {
    this.mVerifyCodeListener = paramOnVerifyCodeListener;
  }
  
  @OnClick({2131558896})
  public void verifyNumberClicked()
  {
    this.mSubmitTimer.cancel();
    ((InputMethodManager)this.mApplication.getSystemService("input_method")).hideSoftInputFromWindow(this.mEtCode4.getWindowToken(), 0);
    this.mWarningStillNotReceived.setVisibility(8);
    this.mWarningCodeSent.setVisibility(8);
    String str = this.mEtCode1.getText().toString() + this.mEtCode2.getText().toString() + this.mEtCode3.getText().toString() + this.mEtCode4.getText().toString();
    this.mProgressBar.setVisibility(0);
    try
    {
      this.mApplication.getUserManager().checkVerificationCode(str, new ICartCallback(this.TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (VerifyCodeDialogFragment.this.getActivity() == null) {
            return;
          }
          VerifyCodeDialogFragment.this.mProgressBar.setVisibility(8);
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          if (VerifyCodeDialogFragment.this.getActivity() == null) {
            return;
          }
          VerifyCodeDialogFragment.this.mProgressBar.setVisibility(8);
          if (VerifyCodeDialogFragment.this.mVerifyCodeListener != null) {
            VerifyCodeDialogFragment.this.mVerifyCodeListener.onSuccessVerifyCode();
          }
          VerifyCodeDialogFragment.this.sendTracker("Delivery");
          new Handler().postDelayed(new Runnable()
          {
            public void run()
            {
              if (VerifyCodeDialogFragment.this.getActivity() == null) {
                return;
              }
              VerifyCodeDialogFragment.this.dismiss();
            }
          }, 200L);
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
  
  private class AutoSubmitTimer
    extends CountDownTimer
  {
    public AutoSubmitTimer(long paramLong1, long paramLong2)
    {
      super(paramLong2);
    }
    
    public void onFinish()
    {
      if (VerifyCodeDialogFragment.this.getActivity() == null) {
        return;
      }
      VerifyCodeDialogFragment.this.verifyNumberClicked();
    }
    
    public void onTick(long paramLong) {}
  }
  
  private class CountTimer
    extends CountDownTimer
  {
    public CountTimer(long paramLong1, long paramLong2)
    {
      super(paramLong2);
    }
    
    public void onFinish()
    {
      if (VerifyCodeDialogFragment.this.getActivity() == null) {
        return;
      }
      if (VerifyCodeDialogFragment.this.mWarningCodeSent.getVisibility() == 0) {
        VerifyCodeDialogFragment.this.mWarningCodeSent.setVisibility(8);
      }
      VerifyCodeDialogFragment.this.mWarningStillNotReceived.setVisibility(0);
    }
    
    public void onTick(long paramLong) {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/VerifyCodeDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
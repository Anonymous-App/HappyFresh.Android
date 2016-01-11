package com.appsee;

import android.text.Editable;
import android.text.TextWatcher;

class x
  implements TextWatcher
{
  x(n paramn) {}
  
  public void afterTextChanged(Editable paramEditable)
  {
    try
    {
      lk.i().i(xm.m, null, AppseeBackgroundUploader.i("\023"), null);
      return;
    }
    catch (Exception paramEditable)
    {
      vd.l(paramEditable, AppseeBackgroundUploader.i("\035O(P)\0364X>Q>^\023~o*R;l%E6\037;\\e\035(L"));
    }
  }
  
  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/x.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
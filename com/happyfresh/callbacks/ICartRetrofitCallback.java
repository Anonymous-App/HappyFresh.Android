package com.happyfresh.callbacks;

import android.util.Log;
import android.widget.Toast;
import com.happyfresh.common.ICartApplication;
import java.net.SocketTimeoutException;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.RetrofitError.Kind;
import retrofit.client.Response;

public class ICartRetrofitCallback<T>
  implements Callback<T>
{
  private static final String TAG = ICartRetrofitCallback.class.getSimpleName();
  private ICartApplication mICartApplication;
  
  public ICartRetrofitCallback(ICartApplication paramICartApplication)
  {
    this.mICartApplication = paramICartApplication;
  }
  
  public void failure(RetrofitError paramRetrofitError)
  {
    Log.e(TAG, "RetrofitError: " + paramRetrofitError.getLocalizedMessage());
    if (paramRetrofitError.getKind() == RetrofitError.Kind.NETWORK)
    {
      if (!(paramRetrofitError.getCause() instanceof SocketTimeoutException)) {
        break label106;
      }
      Toast.makeText(this.mICartApplication, this.mICartApplication.getString(2131165606), 1).show();
    }
    for (;;)
    {
      paramRetrofitError = paramRetrofitError.getResponse();
      if ((paramRetrofitError != null) && (paramRetrofitError.getStatus() == 401) && (this.mICartApplication.isLoggedIn())) {
        this.mICartApplication.onLogout();
      }
      return;
      label106:
      Toast.makeText(this.mICartApplication, this.mICartApplication.getString(2131165322), 1).show();
    }
  }
  
  public void success(T paramT, Response paramResponse) {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/callbacks/ICartRetrofitCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
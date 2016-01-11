package com.ad4screen.sdk.service.modules.tracking.webview;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.A4S.Callback;
import java.util.List;

public class a
  extends b
{
  private A4S.Callback<Void> b;
  
  public a(A4S paramA4S, A4S.Callback<Void> paramCallback)
  {
    super(paramA4S);
    this.b = paramCallback;
  }
  
  public void a(List<String> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty()) && (this.b != null)) {
      this.b.onResult(null);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/webview/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
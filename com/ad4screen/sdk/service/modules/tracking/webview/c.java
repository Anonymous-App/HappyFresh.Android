package com.ad4screen.sdk.service.modules.tracking.webview;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.service.modules.inapp.DisplayView;
import com.ad4screen.sdk.service.modules.inapp.DisplayView.b;
import java.util.List;

public class c
  extends b
{
  private DisplayView.b b;
  private DisplayView c;
  
  public c(A4S paramA4S, DisplayView.b paramb, DisplayView paramDisplayView)
  {
    super(paramA4S);
    this.b = paramb;
    this.c = paramDisplayView;
  }
  
  private void a()
  {
    if ((this.b != null) && (this.c != null)) {
      this.b.b(this.c);
    }
  }
  
  public void a(List<String> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty())) {
      a();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/webview/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
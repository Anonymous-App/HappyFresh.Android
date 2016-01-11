package com.appsee;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.Window.Callback;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class n
{
  static
  {
    C.clear();
    i(AppseeBackgroundUploader.i("R?P$\\\"J/\027)I Z C?\002i\025.Z?\004\rJ.V5QS\025#u`\0225O:B/w4X3T*Zd-\037jtb;Yu\033)x.F#H-J1h(^a\0044F"), AppseeBackgroundUploader.i("7h(^a\0044P"));
    i(AppseeBackgroundUploader.i("7]5F9Z/\000w\r9N~b;Yu\033)x.F#H-J1h(^a\0044F"), AppseeBackgroundUploader.i("7h(^a\0044P"));
    i(AppseeBackgroundUploader.i(".D+X#@>\021)Ja\004>FwH*\016}_8G4F8R'\000w\r9N~b;Yu\033)x.F#H-J1h(^a\0044F"), AppseeBackgroundUploader.i("7h(^a\0044P"));
    i(AppseeBackgroundUploader.i("~\0066\032`\016.V>_#^V7@.\000@\024,U9V3Cx\0330h;K;_?d;Q;Xt\006u\006"), AppseeBackgroundUploader.i("D\031^6Ss\0252_"));
  }
  
  public n(Object paramObject, View paramView, p paramp)
    throws Exception
  {
    this.h = new WeakReference(paramObject);
    this.G = new WeakReference(paramView);
    this.k = paramp;
    if (paramObject != null)
    {
      if (!(paramObject instanceof Window)) {
        break label77;
      }
      this.m = ((Window)paramObject).getCallback();
      if (this.m == null) {
        vd.l(null, AppseeBackgroundUploader.i("w7]%AuD:P>Qr`x\032:T8\n;E9J2\037.^c\0234@"));
      }
    }
    label77:
    while (!(paramObject instanceof PopupWindow)) {
      return;
    }
    this.b = ((View.OnTouchListener)bc.i(paramObject, AppseeBackgroundUploader.i("\031\nT:I'c\"]?M9Za\000>F")));
  }
  
  public void a()
    throws Exception
  {
    Window.Callback localCallback = i();
    if ((!sd.i()) && ((localCallback instanceof Activity)))
    {
      Object localObject = bc.i(localCallback, AppseeBackgroundUploader.i("G\001H3Q\016Wc\0210P"));
      wd localwd = sd.i().i();
      bc.i(localCallback, AppseeBackgroundUploader.i("3r!Y;X9D?Q.^e\035>Z"), localwd);
      bc.i(localObject, AppseeBackgroundUploader.i("3r!Y;X9D?Q.^e\035>Z"), localwd);
      sd.i().i((Activity)localCallback, true);
    }
  }
  
  public Menu i()
  {
    if (this.d != null) {
      return (Menu)this.d.get();
    }
    return null;
  }
  
  public View i()
  {
    return (View)this.G.get();
  }
  
  public Window.Callback i()
    throws Exception
  {
    Window.Callback localCallback1 = l();
    if (localCallback1 != null)
    {
      if (this.K == null)
      {
        localCallback1 = i(localCallback1);
        for (Window.Callback localCallback2 = localCallback1; localCallback1 != null; localCallback2 = localCallback1)
        {
          this.K = localCallback2;
          localCallback1 = i(localCallback2);
        }
      }
      if (this.K == null) {
        this.K = this.m;
      }
      return this.K;
    }
    return null;
  }
  
  public Window i()
  {
    Object localObject = this.h.get();
    if ((localObject instanceof Window)) {
      return (Window)localObject;
    }
    return null;
  }
  
  public Object i()
  {
    return this.h.get();
  }
  
  public void i()
    throws Exception
  {
    if (this.k == null) {}
    List localList;
    do
    {
      do
      {
        do
        {
          return;
        } while ((!jc.i().i()) || (!ge.i().C()) || (!ge.i().D()));
        localList = i();
      } while ((localList == null) || (localList.isEmpty()));
      i(localList);
    } while (i() == null);
    this.k.i(this, localList);
  }
  
  public boolean i()
    throws Exception
  {
    return ((this.h.get() instanceof Window)) && ((i() instanceof AlertDialog));
  }
  
  public Window.Callback l()
  {
    return this.m;
  }
  
  public List<View> l()
  {
    if (this.i != null) {
      return (List)this.i.get();
    }
    return null;
  }
  
  public void l()
    throws Exception
  {
    if ((!ge.i().q()) || (!jc.i().i())) {}
    Object localObject;
    do
    {
      do
      {
        return;
        localObject = this.h.get();
        if (!(localObject instanceof Window)) {
          break;
        }
      } while ((l() instanceof h));
      ((Window)localObject).setCallback(new h(this));
      i();
      return;
    } while ((!(localObject instanceof PopupWindow)) || ((this.b instanceof o)));
    ((PopupWindow)localObject).setTouchInterceptor(new o(this, null));
  }
  
  public void l(List<View> paramList)
  {
    paramList = paramList.iterator();
    for (;;)
    {
      View localView;
      if (paramList.hasNext())
      {
        localView = (View)paramList.next();
        if (!(localView.getTouchDelegate() instanceof f)) {}
      }
      else
      {
        return;
      }
      localView.setTouchDelegate(new f(this, localView));
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
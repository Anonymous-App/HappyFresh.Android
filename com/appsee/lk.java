package com.appsee;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TabWidget;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

class lk
{
  public static lk i()
  {
    try
    {
      if (G == null) {
        G = new lk();
      }
      lk locallk = G;
      return locallk;
    }
    finally {}
  }
  
  public static byte[] i(Drawable paramDrawable)
    throws Exception
  {
    if (paramDrawable == null) {
      return null;
    }
    Bitmap localBitmap1 = pb.i(paramDrawable);
    if ((localBitmap1 == null) || (localBitmap1.getWidth() == 0) || (localBitmap1.getHeight() == 0)) {
      throw new Exception(AppseeBackgroundUploader.i("\031^4\030eT0W8R#_=M5\025:Vb\034~] Xo\032lZ3E?[1\035>W3T"));
    }
    double d = localBitmap1.getWidth() / localBitmap1.getHeight();
    paramDrawable = new Dimension(8, 8);
    Object localObject = new Dimension(16, 4);
    Bitmap localBitmap2;
    if (Math.abs(d - paramDrawable.getRatio()) < Math.abs(d - ((Dimension)localObject).getRatio()))
    {
      localBitmap2 = i(localBitmap1, paramDrawable, true);
      localObject = new byte[72];
      n = 0;
    }
    int i1;
    int i2;
    for (int m = 0;; m = n)
    {
      if (n >= localBitmap2.getHeight()) {
        break label224;
      }
      i1 = 0;
      n = 0;
      for (;;)
      {
        if (i1 < localBitmap2.getWidth())
        {
          i1 = localBitmap2.getPixel(n, m);
          i2 = localBitmap2.getWidth();
          int j = (byte)Color.red(i1);
          i1 = n + 1;
          localObject[(i2 * m + n)] = j;
          n = i1;
          continue;
          paramDrawable = (Drawable)localObject;
          break;
        }
      }
      n = m + 1;
    }
    label224:
    paramDrawable = i(localBitmap1, paramDrawable, false);
    int n = 0;
    for (m = 0; n < paramDrawable.getHeight(); m = n)
    {
      i1 = 0;
      for (n = 0; i1 < paramDrawable.getWidth(); n = i1)
      {
        if (Color.alpha(paramDrawable.getPixel(n, m)) == 0)
        {
          i1 = paramDrawable.getWidth() * m + n;
          i2 = i1 / 8 + 64;
          localObject[i2] = ((byte)(1 << 7 - i1 % 8 | localObject[i2]));
        }
        i1 = n + 1;
      }
      n = m + 1;
    }
    return (byte[])localObject;
  }
  
  public ie i(xm paramxm)
  {
    for (;;)
    {
      synchronized (this.k)
      {
        j = this.k.size() - 1;
        m = j;
        if (j >= 0)
        {
          ie localie = (ie)this.k.get(m);
          if ((localie != null) && (localie.i() == paramxm)) {
            return localie;
          }
        }
        else
        {
          return null;
        }
      }
      int j = m - 1;
      int m = j;
    }
  }
  
  public JSONArray i()
    throws JSONException
  {
    synchronized (this.k)
    {
      JSONArray localJSONArray = new JSONArray();
      Iterator localIterator = this.k.iterator();
      while (localIterator.hasNext()) {
        localJSONArray.put(((ie)localIterator.next()).l());
      }
      return localJSONArray;
    }
  }
  
  public void i()
  {
    if ((!jc.i().i()) || (!ge.i().M())) {}
    ie localie;
    do
    {
      return;
      if (this.k.isEmpty()) {
        break;
      }
      localie = (ie)this.k.get(this.k.size() - 1);
    } while ((localie != null) && (localie.i() == xm.m));
    i(xm.m, null, AppseeBackgroundUploader.i("\000"), null);
  }
  
  public void i(View paramView)
  {
    if ((paramView instanceof SeekBar))
    {
      String str = pb.i(paramView, false, pb.i(new Class[] { SeekBar.class }));
      i(xm.G, Integer.toString(((SeekBar)paramView).getProgress()), str, null, paramView.getId(), pb.a(paramView, SeekBar.class), pb.i(paramView));
    }
  }
  
  public void i(View paramView1, View paramView2, td paramtd, MotionEvent paramMotionEvent)
    throws Exception
  {
    if (paramView1 == null) {}
    do
    {
      do
      {
        return;
      } while ((!jc.i().i()) || (!ge.i().M()));
      if ((paramView1 instanceof AbsListView))
      {
        i((AbsListView)paramView1, paramMotionEvent, paramtd);
        return;
      }
      if ((paramView1 instanceof TabWidget))
      {
        i((TabWidget)paramView1, paramView2, paramtd);
        return;
      }
      if (pb.i(zb.k, paramView1))
      {
        i((ViewGroup)paramView1, paramView2, paramtd);
        return;
      }
      if (pb.a(paramView1))
      {
        i(paramView1, paramtd);
        return;
      }
    } while (((paramView1 instanceof SeekBar)) || (!(paramView1 instanceof EditText)));
    paramMotionEvent = pb.i(paramView1, false, pb.i(new Class[] { EditText.class }));
    i(xm.d, null, paramMotionEvent, paramtd, paramView1.getId(), pb.a(paramView2, EditText.class), pb.i(paramView1));
  }
  
  public void i(ViewGroup paramViewGroup, View paramView, td paramtd)
    throws Exception
  {
    if ((paramViewGroup == null) || (paramView == null)) {}
    while ((!(paramView instanceof EditText)) && (!(paramView instanceof ImageView))) {
      return;
    }
    Object localObject = new ArrayList(pb.i());
    ((List)localObject).add(ImageView.class);
    String str2 = pb.i(paramView, true, (List)localObject);
    String str1 = l(paramView);
    localObject = str1;
    if (lb.i(str1))
    {
      paramViewGroup = pb.i(paramViewGroup, ImageView.class);
      localObject = str1;
      if (paramViewGroup.size() > 2) {
        localObject = i(((ImageView)paramViewGroup.get(1)).getDrawable());
      }
    }
    i(xm.C, (String)localObject, str2, paramtd, paramView.getId(), pb.a(paramView, paramView.getClass()), pb.i(paramView));
  }
  
  public void i(AbsListView paramAbsListView, MotionEvent paramMotionEvent, td paramtd)
  {
    int j = pb.i(paramAbsListView, (short)(int)paramMotionEvent.getY(paramMotionEvent.getActionIndex()));
    if (j < 0)
    {
      paramtd.i(false);
      return;
    }
    paramMotionEvent = Integer.toString(j);
    paramAbsListView = pb.i(paramAbsListView, false, pb.i(new Class[] { AbsListView.class }));
    i(xm.h, paramMotionEvent, paramAbsListView, paramtd);
  }
  
  public void i(TabWidget paramTabWidget, View paramView, td paramtd)
    throws Exception
  {
    View localView = pb.i(paramTabWidget, paramView);
    String str = l(localView);
    paramView = pb.i(paramTabWidget, false, pb.i(new Class[] { TabWidget.class }));
    int j = pb.l(paramTabWidget, localView);
    paramTabWidget = paramView;
    if (j != -1) {
      paramTabWidget = AppseeBackgroundUploader.i("\037") + j;
    }
    i(xm.C, str, paramTabWidget, paramtd, localView.getId(), pb.a(localView, localView.getClass()), pb.i(localView));
  }
  
  public void i(xm paramxm, String paramString1, String paramString2, td paramtd)
  {
    i(paramxm, paramString1, paramString2, paramtd, -1, false, null);
  }
  
  public void i(xm arg1, String paramString1, String paramString2, td paramtd, int paramInt, boolean paramBoolean, Rect paramRect)
  {
    if (!jc.i().i()) {}
    while ((jc.i().i() == -1L) || (((??? == xm.K) || (??? == xm.i)) && (!this.k.isEmpty()) && (((ie)this.k.get(this.k.size() - 1)).i() == ???))) {
      return;
    }
    paramRect = new ie(???, paramString1, paramString2, jc.i().i(), pb.i(paramRect));
    if (paramInt > 0)
    {
      paramRect.l(String.valueOf(paramInt));
      paramRect.i(Boolean.valueOf(paramBoolean));
    }
    if (paramtd != null) {
      paramRect.i(paramtd.i());
    }
    mc.l(??? + AppseeBackgroundUploader.i("\006oZ-[;R?Kt\006 \026i\021") + paramString1 + AppseeBackgroundUploader.i("\0373Qu\021+\026i\021") + paramString2 + AppseeBackgroundUploader.i("sW \021") + paramRect.i(), 1);
    synchronized (this.k)
    {
      this.k.add(paramRect);
      return;
    }
  }
  
  public void i(String paramString)
  {
    if ((!jc.i().i()) || (!ge.i().M())) {}
    ie localie;
    do
    {
      return;
      if (this.k.isEmpty()) {
        break;
      }
      localie = (ie)this.k.get(this.k.size() - 1);
    } while ((localie != null) && (localie.i() == xm.b) && (jc.i().i() - localie.i() <= 4000L));
    i(xm.b, paramString, null, null);
  }
  
  public void l()
  {
    synchronized (this.k)
    {
      this.k.clear();
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/lk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
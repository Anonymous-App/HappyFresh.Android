package com.ad4screen.sdk.systems;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.ad4screen.sdk.A4SInterstitial;
import com.ad4screen.sdk.A4SPopup;
import com.ad4screen.sdk.A4SPopup.b;
import com.ad4screen.sdk.A4SPopup.c;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R.id;
import com.ad4screen.sdk.Tag;
import com.ad4screen.sdk.model.displayformats.a;
import com.ad4screen.sdk.model.displayformats.b;
import com.ad4screen.sdk.model.displayformats.d;
import com.ad4screen.sdk.model.displayformats.e;
import com.ad4screen.sdk.model.displayformats.g;
import com.ad4screen.sdk.service.modules.inapp.DisplayView;
import com.ad4screen.sdk.service.modules.inapp.DisplayView.b;
import com.ad4screen.sdk.service.modules.inapp.c.c;
import com.ad4screen.sdk.service.modules.inapp.c.d;
import com.ad4screen.sdk.service.modules.inapp.c.f;
import com.ad4screen.sdk.service.modules.inapp.c.g;
import java.util.ArrayList;

public final class k
{
  private static k j;
  private ArrayList<DisplayView> a = new ArrayList();
  private A4SPopup b;
  private l c;
  private FrameLayout d;
  private ArrayList<View> e = new ArrayList();
  private ArrayList<View> f = new ArrayList();
  private boolean g = false;
  private boolean h = false;
  private boolean i = false;
  private A4SPopup.b k = new A4SPopup.b()
  {
    public void a(A4SPopup paramAnonymousA4SPopup)
    {
      k.a(k.this, paramAnonymousA4SPopup);
      k.a(k.this, false);
    }
  };
  
  private k(Context paramContext)
  {
    f.a().a(A4SPopup.c.class, this.k);
    this.c = new l(paramContext);
  }
  
  public static k a(Context paramContext)
  {
    if (j == null) {
      j = new k(paramContext);
    }
    return j;
  }
  
  public static String a(Activity paramActivity)
  {
    paramActivity = paramActivity.getClass();
    Tag localTag = (Tag)paramActivity.getAnnotation(Tag.class);
    if (localTag != null) {
      return localTag.name();
    }
    return paramActivity.getName();
  }
  
  private void a(final Activity paramActivity, final a parama)
  {
    if (paramActivity == null)
    {
      Log.debug("UI|Cannot display inapp without activity");
      return;
    }
    Object localObject1 = (ViewGroup)((ViewGroup)paramActivity.findViewById(16908290)).getChildAt(0);
    Object localObject3 = new ArrayList();
    int m;
    if ((!parama.b) && (parama.c != null) && (parama.c.d != null))
    {
      com.ad4screen.sdk.common.compatibility.k.a((ViewGroup)localObject1, (ArrayList)localObject3, paramActivity.getResources().getIdentifier(parama.c.d, "layout", paramActivity.getPackageName()));
      if (((ArrayList)localObject3).size() > 0)
      {
        m = 0;
        if (m < ((ArrayList)localObject3).size()) {
          if (this.f.contains(((ArrayList)localObject3).get(m))) {}
        }
      }
    }
    for (Object localObject2 = (View)((ArrayList)localObject3).get(m);; localObject2 = null)
    {
      if (localObject2 != null)
      {
        localObject1 = new DisplayView(paramActivity);
        localObject3 = new FrameLayout.LayoutParams(-1, -2, 80);
        ((ViewGroup)localObject2).addView((View)localObject1, (ViewGroup.LayoutParams)localObject3);
        this.f.add(localObject2);
        localObject3 = (DisplayView)localObject1;
        ((DisplayView)localObject3).setParentView((View)localObject2);
        ((DisplayView)localObject3).setLayout(paramActivity.getResources().getIdentifier(parama.c.d, "layout", paramActivity.getPackageName()));
        m = 0;
        if (m == 0) {
          break label513;
        }
        Log.debug("InApp|No spaces available to display this banner");
        return;
        m += 1;
        break;
      }
      ((ArrayList)localObject3).clear();
      com.ad4screen.sdk.common.compatibility.k.a((ViewGroup)localObject1, (ArrayList)localObject3, R.id.com_ad4screen_banner);
      if (((ArrayList)localObject3).size() > 0)
      {
        m = 0;
        label268:
        if (m < ((ArrayList)localObject3).size())
        {
          localObject1 = (View)((ArrayList)localObject3).get(m);
          if (this.f.contains(localObject1)) {}
        }
      }
      for (;;)
      {
        if (((localObject1 instanceof DisplayView)) && (!parama.b) && (!this.f.contains(localObject1)))
        {
          localObject2 = (DisplayView)localObject1;
          ((DisplayView)localObject2).setParentView((View)localObject1);
          ((DisplayView)localObject2).setLayout(paramActivity.getResources().getIdentifier("com_ad4screen_sdk_banner", "layout", paramActivity.getPackageName()));
          this.f.add(localObject1);
          m = 0;
          break;
          m += 1;
          break label268;
        }
        if ((!this.h) && (parama.g) && (((localObject2 == null) && (this.f.isEmpty())) || (parama.b)))
        {
          this.h = true;
          this.d = new FrameLayout(paramActivity);
          localObject1 = new DisplayView(paramActivity);
          localObject2 = c();
          ((DisplayView)localObject1).setLayout(paramActivity.getResources().getIdentifier("com_ad4screen_sdk_overlay", "layout", paramActivity.getPackageName()));
          this.d.addView((View)localObject1, (ViewGroup.LayoutParams)localObject2);
          paramActivity.addContentView(this.d, new ViewGroup.LayoutParams(-1, -1));
          m = 0;
          break;
        }
        m = 1;
        break;
        label513:
        localObject1 = (DisplayView)localObject1;
        this.a.add(localObject1);
        ((DisplayView)localObject1).setBanner(parama);
        ((DisplayView)localObject1).setDelegate(new DisplayView.b()
        {
          public void a(DisplayView paramAnonymousDisplayView)
          {
            f.a().a(new c.f(parama.i, paramAnonymousDisplayView.getLayout(), parama.c.d, parama.e));
          }
          
          public void b(DisplayView paramAnonymousDisplayView)
          {
            Log.debug("UI|User closed banner #" + parama.i);
            f.a().a(new c.d(parama.i, paramAnonymousDisplayView.getLayout(), parama.c.d, true));
            k.a(k.this, paramActivity, paramAnonymousDisplayView);
          }
          
          public void c(DisplayView paramAnonymousDisplayView)
          {
            Log.debug("UI|User clicked banner #" + parama.i);
            if (parama.d == null)
            {
              Log.debug("UI|InApp #" + parama.i + " click tracking will not be sent because target is null");
              return;
            }
            f.a().a(new c.c(parama.i, paramAnonymousDisplayView.getLayout(), parama.c.d, null, null));
            k.a(k.this, paramActivity, paramAnonymousDisplayView);
          }
          
          public void d(DisplayView paramAnonymousDisplayView) {}
          
          public void e(DisplayView paramAnonymousDisplayView)
          {
            Log.debug("UI|Failed to load banner # " + parama.i + " webview");
            f.a().a(new c.d(parama.i, paramAnonymousDisplayView.getLayout(), parama.c.d, false));
            k.a(k.this, paramActivity, paramAnonymousDisplayView);
          }
        });
        if (parama.d != null)
        {
          ((DisplayView)localObject1).a(parama.c, 1);
          return;
        }
        ((DisplayView)localObject1).a(parama.c, 0);
        return;
        localObject1 = null;
      }
    }
  }
  
  private void a(Activity paramActivity, DisplayView paramDisplayView)
  {
    if (paramDisplayView.getParentView() != null)
    {
      this.e.remove(paramDisplayView.getParentView());
      this.f.remove(paramDisplayView.getParentView());
    }
    for (;;)
    {
      paramDisplayView.d();
      this.a.remove(paramDisplayView);
      return;
      paramActivity = (ViewGroup)this.d.getParent();
      if (paramActivity != null) {
        paramActivity.removeView(this.d);
      }
      this.h = false;
      this.g = false;
    }
  }
  
  private void a(Context paramContext, e parame)
  {
    paramContext.startActivity(A4SInterstitial.build(paramContext, 1, parame));
  }
  
  private void a(Context paramContext, g paramg)
  {
    paramContext.startActivity(A4SPopup.build(paramContext, 1, paramg));
  }
  
  public static String b(Activity paramActivity)
  {
    return paramActivity.getClass().getCanonicalName();
  }
  
  private FrameLayout.LayoutParams c()
  {
    return this.c.a();
  }
  
  public static String c(Activity paramActivity)
  {
    return Integer.toHexString(System.identityHashCode(paramActivity));
  }
  
  public void a()
  {
    if (this.a.size() > 0)
    {
      int m = 0;
      while (m < this.a.size())
      {
        f.a().a(new c.d(((DisplayView)this.a.get(m)).getBanner().i, ((DisplayView)this.a.get(m)).getLayout(), ((DisplayView)this.a.get(m)).getBanner().c.d, false));
        ((DisplayView)this.a.get(m)).d();
        m += 1;
      }
    }
    this.e.clear();
    this.f.clear();
    this.a.clear();
    this.g = false;
    this.h = false;
  }
  
  public void a(Activity paramActivity, d paramd)
  {
    if (paramd == null) {
      Log.error("UI|Unable to display null format");
    }
    do
    {
      return;
      if (paramActivity == null) {
        Log.error("UI|Unable to display format with null context");
      }
      if ((paramd instanceof a))
      {
        Log.debug("UI|Client displaying banner #" + paramd.i);
        a(paramActivity, (a)paramd);
        return;
      }
      if ((paramd instanceof e))
      {
        Log.debug("UI|Client displaying intersticial #" + paramd.i);
        a(paramActivity, (e)paramd);
        return;
      }
    } while (!(paramd instanceof g));
    Log.debug("UI|Client displaying popup #" + paramd.i);
    a(paramActivity, (g)paramd);
  }
  
  public void a(Activity paramActivity, d paramd, int paramInt)
  {
    if (paramActivity == null) {
      Log.debug("UI|Cannot cancel inapp template reservation without activity");
    }
    for (;;)
    {
      return;
      if (paramActivity.getResources().getIdentifier("com_ad4screen_sdk_overlay", "layout", paramActivity.getPackageName()) == paramInt)
      {
        if (!this.h) {
          this.g = false;
        }
      }
      else
      {
        if ((paramd instanceof g))
        {
          this.i = false;
          return;
        }
        if ((paramd instanceof a))
        {
          paramd = (a)paramd;
          if (paramd.c.d != null)
          {
            ArrayList localArrayList = new ArrayList();
            com.ad4screen.sdk.common.compatibility.k.a((ViewGroup)((ViewGroup)paramActivity.findViewById(16908290)).getChildAt(0), localArrayList, paramActivity.getResources().getIdentifier(paramd.c.d, "layout", paramActivity.getPackageName()));
            if (localArrayList.size() > 0)
            {
              paramInt = 0;
              while (paramInt < localArrayList.size())
              {
                if ((this.e.contains(localArrayList.get(paramInt))) && (!this.f.contains(localArrayList.get(paramInt)))) {
                  this.e.remove(localArrayList.get(paramInt));
                }
                paramInt += 1;
              }
            }
          }
        }
      }
    }
  }
  
  public void a(Activity paramActivity, String paramString)
  {
    if (this.a.size() > 0)
    {
      int n = 0;
      int m = 0;
      for (;;)
      {
        i1 = m;
        if (n >= this.a.size()) {
          break;
        }
        if (((DisplayView)this.a.get(n)).getBanner().i.equals(paramString))
        {
          f.a().a(new c.d(((DisplayView)this.a.get(n)).getBanner().i, ((DisplayView)this.a.get(n)).getLayout(), ((DisplayView)this.a.get(n)).getBanner().c.d, false));
          a(paramActivity, (DisplayView)this.a.get(n));
          m = 1;
        }
        n += 1;
      }
    }
    int i1 = 0;
    if (i1 == 0) {
      b();
    }
  }
  
  public void a(FrameLayout.LayoutParams paramLayoutParams)
  {
    this.c.a(paramLayoutParams);
  }
  
  public void b()
  {
    if ((this.b != null) && (!this.b.isFinishing()))
    {
      this.b.finish();
      this.b = null;
    }
  }
  
  public void b(Activity paramActivity, d paramd)
  {
    Object localObject3 = null;
    Object localObject2 = null;
    int n = 0;
    if (paramActivity == null) {
      Log.debug("UI|Cannot display inapp without activity");
    }
    do
    {
      a locala;
      Object localObject1;
      do
      {
        return;
        if (!(paramd instanceof a)) {
          break;
        }
        locala = (a)paramd;
        localObject1 = localObject3;
        if (locala.c.d != null)
        {
          ArrayList localArrayList = new ArrayList();
          ViewGroup localViewGroup = (ViewGroup)((ViewGroup)paramActivity.findViewById(16908290)).getChildAt(0);
          com.ad4screen.sdk.common.compatibility.k.a(localViewGroup, localArrayList, paramActivity.getResources().getIdentifier(locala.c.d, "layout", paramActivity.getPackageName()));
          int m;
          if (localArrayList.size() > 0)
          {
            m = 0;
            localObject1 = null;
            while (m < localArrayList.size())
            {
              localObject1 = (View)localArrayList.get(m);
              if (!this.e.contains(localObject1))
              {
                this.e.add(localObject1);
                f.a().a(new c.g(paramd.i, ((View)localObject1).getId(), locala.c.d));
                return;
              }
              m += 1;
            }
          }
          localObject1 = null;
          if (!locala.b)
          {
            localArrayList.clear();
            com.ad4screen.sdk.common.compatibility.k.a(localViewGroup, localArrayList, R.id.com_ad4screen_banner);
            localObject1 = localObject3;
            if (localArrayList.size() > 0)
            {
              localObject1 = localObject2;
              m = n;
              while (m < localArrayList.size())
              {
                localObject1 = (View)localArrayList.get(m);
                if (!this.e.contains(localObject1))
                {
                  this.e.add(localObject1);
                  f.a().a(new c.g(paramd.i, paramActivity.getResources().getIdentifier("com_ad4screen_sdk_banner", "layout", paramActivity.getPackageName()), locala.c.d));
                  return;
                }
                m += 1;
              }
            }
          }
        }
      } while ((this.g) || (this.h) || (localObject1 != null) || (!locala.g));
      this.g = true;
      f.a().a(new c.g(paramd.i, paramActivity.getResources().getIdentifier("com_ad4screen_sdk_overlay", "layout", paramActivity.getPackageName()), locala.c.d));
      return;
      if (!(paramd instanceof g)) {
        break;
      }
    } while ((this.i) || ((this.b != null) && (!this.b.isFinishing())));
    this.i = true;
    f.a().a(new c.g(paramd.i, -1, "com_ad4screen_sdk_theme_popup"));
    return;
    f.a().a(new c.g(paramd.i, paramActivity.getResources().getIdentifier("com_ad4screen_sdk_overlay", "layout", paramActivity.getPackageName()), "com_ad4screen_sdk_overlay"));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/systems/k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
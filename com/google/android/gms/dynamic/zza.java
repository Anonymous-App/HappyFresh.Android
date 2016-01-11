package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class zza<T extends LifecycleDelegate>
{
  private T zzajh;
  private Bundle zzaji;
  private LinkedList<zza> zzajj;
  private final zzf<T> zzajk = new zzf()
  {
    public void zza(T paramAnonymousT)
    {
      zza.zza(zza.this, paramAnonymousT);
      paramAnonymousT = zza.zza(zza.this).iterator();
      while (paramAnonymousT.hasNext()) {
        ((zza.zza)paramAnonymousT.next()).zzb(zza.zzb(zza.this));
      }
      zza.zza(zza.this).clear();
      zza.zza(zza.this, null);
    }
  };
  
  private void zza(Bundle paramBundle, zza paramzza)
  {
    if (this.zzajh != null)
    {
      paramzza.zzb(this.zzajh);
      return;
    }
    if (this.zzajj == null) {
      this.zzajj = new LinkedList();
    }
    this.zzajj.add(paramzza);
    if (paramBundle != null)
    {
      if (this.zzaji != null) {
        break label76;
      }
      this.zzaji = ((Bundle)paramBundle.clone());
    }
    for (;;)
    {
      zza(this.zzajk);
      return;
      label76:
      this.zzaji.putAll(paramBundle);
    }
  }
  
  public static void zzb(FrameLayout paramFrameLayout)
  {
    Context localContext = paramFrameLayout.getContext();
    final int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(localContext);
    String str2 = com.google.android.gms.common.internal.zzf.zzb(localContext, i, GooglePlayServicesUtil.zzad(localContext));
    String str1 = com.google.android.gms.common.internal.zzf.zzh(localContext, i);
    LinearLayout localLinearLayout = new LinearLayout(paramFrameLayout.getContext());
    localLinearLayout.setOrientation(1);
    localLinearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
    paramFrameLayout.addView(localLinearLayout);
    paramFrameLayout = new TextView(paramFrameLayout.getContext());
    paramFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
    paramFrameLayout.setText(str2);
    localLinearLayout.addView(paramFrameLayout);
    if (str1 != null)
    {
      paramFrameLayout = new Button(localContext);
      paramFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
      paramFrameLayout.setText(str1);
      localLinearLayout.addView(paramFrameLayout);
      paramFrameLayout.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          this.zzqV.startActivity(GooglePlayServicesUtil.zzaT(i));
        }
      });
    }
  }
  
  private void zzdY(int paramInt)
  {
    while ((!this.zzajj.isEmpty()) && (((zza)this.zzajj.getLast()).getState() >= paramInt)) {
      this.zzajj.removeLast();
    }
  }
  
  public void onCreate(final Bundle paramBundle)
  {
    zza(paramBundle, new zza()
    {
      public int getState()
      {
        return 1;
      }
      
      public void zzb(LifecycleDelegate paramAnonymousLifecycleDelegate)
      {
        zza.zzb(zza.this).onCreate(paramBundle);
      }
    });
  }
  
  public View onCreateView(final LayoutInflater paramLayoutInflater, final ViewGroup paramViewGroup, final Bundle paramBundle)
  {
    final FrameLayout localFrameLayout = new FrameLayout(paramLayoutInflater.getContext());
    zza(paramBundle, new zza()
    {
      public int getState()
      {
        return 2;
      }
      
      public void zzb(LifecycleDelegate paramAnonymousLifecycleDelegate)
      {
        localFrameLayout.removeAllViews();
        localFrameLayout.addView(zza.zzb(zza.this).onCreateView(paramLayoutInflater, paramViewGroup, paramBundle));
      }
    });
    if (this.zzajh == null) {
      zza(localFrameLayout);
    }
    return localFrameLayout;
  }
  
  public void onDestroy()
  {
    if (this.zzajh != null)
    {
      this.zzajh.onDestroy();
      return;
    }
    zzdY(1);
  }
  
  public void onDestroyView()
  {
    if (this.zzajh != null)
    {
      this.zzajh.onDestroyView();
      return;
    }
    zzdY(2);
  }
  
  public void onInflate(final Activity paramActivity, final Bundle paramBundle1, final Bundle paramBundle2)
  {
    zza(paramBundle2, new zza()
    {
      public int getState()
      {
        return 0;
      }
      
      public void zzb(LifecycleDelegate paramAnonymousLifecycleDelegate)
      {
        zza.zzb(zza.this).onInflate(paramActivity, paramBundle1, paramBundle2);
      }
    });
  }
  
  public void onLowMemory()
  {
    if (this.zzajh != null) {
      this.zzajh.onLowMemory();
    }
  }
  
  public void onPause()
  {
    if (this.zzajh != null)
    {
      this.zzajh.onPause();
      return;
    }
    zzdY(5);
  }
  
  public void onResume()
  {
    zza(null, new zza()
    {
      public int getState()
      {
        return 5;
      }
      
      public void zzb(LifecycleDelegate paramAnonymousLifecycleDelegate)
      {
        zza.zzb(zza.this).onResume();
      }
    });
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    if (this.zzajh != null) {
      this.zzajh.onSaveInstanceState(paramBundle);
    }
    while (this.zzaji == null) {
      return;
    }
    paramBundle.putAll(this.zzaji);
  }
  
  public void onStart()
  {
    zza(null, new zza()
    {
      public int getState()
      {
        return 4;
      }
      
      public void zzb(LifecycleDelegate paramAnonymousLifecycleDelegate)
      {
        zza.zzb(zza.this).onStart();
      }
    });
  }
  
  public void onStop()
  {
    if (this.zzajh != null)
    {
      this.zzajh.onStop();
      return;
    }
    zzdY(4);
  }
  
  protected void zza(FrameLayout paramFrameLayout)
  {
    zzb(paramFrameLayout);
  }
  
  protected abstract void zza(zzf<T> paramzzf);
  
  public T zzqj()
  {
    return this.zzajh;
  }
  
  private static abstract interface zza
  {
    public abstract int getState();
    
    public abstract void zzb(LifecycleDelegate paramLifecycleDelegate);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/dynamic/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
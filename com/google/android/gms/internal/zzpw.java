package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class zzpw
{
  private static zzpw zzaOV;
  private Context mContext;
  private boolean mStarted;
  private final Set<zza> zzaOW = new HashSet();
  private TagManager zzaOX = null;
  private zzpv zzoY;
  
  zzpw(Context paramContext, TagManager paramTagManager)
  {
    this.mContext = paramContext;
    this.zzaOX = paramTagManager;
  }
  
  public static zzpw zzaK(Context paramContext)
  {
    zzu.zzu(paramContext);
    if (zzaOV == null) {}
    try
    {
      if (zzaOV == null) {
        zzaOV = new zzpw(paramContext, TagManager.getInstance(paramContext.getApplicationContext()));
      }
      return zzaOV;
    }
    finally {}
  }
  
  private void zzgy()
  {
    try
    {
      Iterator localIterator = this.zzaOW.iterator();
      while (localIterator.hasNext()) {
        ((zza)localIterator.next()).zzbm();
      }
    }
    finally {}
  }
  
  public void start()
    throws IllegalStateException
  {
    try
    {
      if (this.mStarted) {
        throw new IllegalStateException("Method start() has already been called");
      }
    }
    finally {}
    if (this.zzoY == null) {
      throw new IllegalStateException("No settings configured");
    }
    this.mStarted = true;
    this.zzaOX.zzc(this.zzoY.zzzT(), -1, "admob").setResultCallback(new ResultCallback()
    {
      public void zza(ContainerHolder paramAnonymousContainerHolder)
      {
        if (paramAnonymousContainerHolder.getStatus().isSuccess()) {}
        for (paramAnonymousContainerHolder = paramAnonymousContainerHolder.getContainer();; paramAnonymousContainerHolder = null)
        {
          paramAnonymousContainerHolder = new zzpu(zzpw.zza(zzpw.this), paramAnonymousContainerHolder, zzpw.this.zzzX());
          zzpw.zza(zzpw.this, paramAnonymousContainerHolder.zzzR());
          zzpw.zzb(zzpw.this);
          return;
        }
      }
    });
  }
  
  public void zza(zzpv paramzzpv)
  {
    try
    {
      if (this.mStarted) {
        throw new IllegalStateException("Settings can't be changed after TagManager has been started");
      }
    }
    finally {}
    this.zzoY = paramzzpv;
  }
  
  public void zza(zza paramzza)
  {
    try
    {
      this.zzaOW.add(paramzza);
      return;
    }
    finally {}
  }
  
  public zzpv zzzX()
  {
    try
    {
      zzpv localzzpv = this.zzoY;
      return localzzpv;
    }
    finally {}
  }
  
  public static abstract interface zza
  {
    public abstract void zzbm();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzpw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
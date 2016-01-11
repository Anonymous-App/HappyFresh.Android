package com.google.android.gms.tagmanager;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.api.PendingResult;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TagManager
{
  private static TagManager zzaOb;
  private final Context mContext;
  private final DataLayer zzaKz;
  private final zzs zzaMV;
  private final zza zzaNY;
  private final zzct zzaNZ;
  private final ConcurrentMap<zzo, Boolean> zzaOa;
  
  TagManager(Context paramContext, zza paramzza, DataLayer paramDataLayer, zzct paramzzct)
  {
    if (paramContext == null) {
      throw new NullPointerException("context cannot be null");
    }
    this.mContext = paramContext.getApplicationContext();
    this.zzaNZ = paramzzct;
    this.zzaNY = paramzza;
    this.zzaOa = new ConcurrentHashMap();
    this.zzaKz = paramDataLayer;
    this.zzaKz.zza(new DataLayer.zzb()
    {
      public void zzF(Map<String, Object> paramAnonymousMap)
      {
        paramAnonymousMap = paramAnonymousMap.get("event");
        if (paramAnonymousMap != null) {
          TagManager.zza(TagManager.this, paramAnonymousMap.toString());
        }
      }
    });
    this.zzaKz.zza(new zzd(this.mContext));
    this.zzaMV = new zzs();
    zzzE();
  }
  
  public static TagManager getInstance(Context paramContext)
  {
    try
    {
      if (zzaOb != null) {
        break label68;
      }
      if (paramContext == null)
      {
        zzbg.zzaz("TagManager.getInstance requires non-null context.");
        throw new NullPointerException();
      }
    }
    finally {}
    zzaOb = new TagManager(paramContext, new zza()new DataLayernew zzw
    {
      public zzp zza(Context paramAnonymousContext, TagManager paramAnonymousTagManager, Looper paramAnonymousLooper, String paramAnonymousString, int paramAnonymousInt, zzs paramAnonymouszzs)
      {
        return new zzp(paramAnonymousContext, paramAnonymousTagManager, paramAnonymousLooper, paramAnonymousString, paramAnonymousInt, paramAnonymouszzs);
      }
    }, new DataLayer(new zzw(paramContext)), zzcu.zzzz());
    label68:
    paramContext = zzaOb;
    return paramContext;
  }
  
  private void zzeF(String paramString)
  {
    Iterator localIterator = this.zzaOa.keySet().iterator();
    while (localIterator.hasNext()) {
      ((zzo)localIterator.next()).zzeh(paramString);
    }
  }
  
  private void zzzE()
  {
    if (Build.VERSION.SDK_INT >= 14) {
      this.mContext.registerComponentCallbacks(new ComponentCallbacks2()
      {
        public void onConfigurationChanged(Configuration paramAnonymousConfiguration) {}
        
        public void onLowMemory() {}
        
        public void onTrimMemory(int paramAnonymousInt)
        {
          if (paramAnonymousInt == 20) {
            TagManager.this.dispatch();
          }
        }
      });
    }
  }
  
  public void dispatch()
  {
    this.zzaNZ.dispatch();
  }
  
  public DataLayer getDataLayer()
  {
    return this.zzaKz;
  }
  
  public PendingResult<ContainerHolder> loadContainerDefaultOnly(String paramString, int paramInt)
  {
    paramString = this.zzaNY.zza(this.mContext, this, null, paramString, paramInt, this.zzaMV);
    paramString.zzyr();
    return paramString;
  }
  
  public PendingResult<ContainerHolder> loadContainerDefaultOnly(String paramString, int paramInt, Handler paramHandler)
  {
    paramString = this.zzaNY.zza(this.mContext, this, paramHandler.getLooper(), paramString, paramInt, this.zzaMV);
    paramString.zzyr();
    return paramString;
  }
  
  public PendingResult<ContainerHolder> loadContainerPreferFresh(String paramString, int paramInt)
  {
    paramString = this.zzaNY.zza(this.mContext, this, null, paramString, paramInt, this.zzaMV);
    paramString.zzyt();
    return paramString;
  }
  
  public PendingResult<ContainerHolder> loadContainerPreferFresh(String paramString, int paramInt, Handler paramHandler)
  {
    paramString = this.zzaNY.zza(this.mContext, this, paramHandler.getLooper(), paramString, paramInt, this.zzaMV);
    paramString.zzyt();
    return paramString;
  }
  
  public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String paramString, int paramInt)
  {
    paramString = this.zzaNY.zza(this.mContext, this, null, paramString, paramInt, this.zzaMV);
    paramString.zzys();
    return paramString;
  }
  
  public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String paramString, int paramInt, Handler paramHandler)
  {
    paramString = this.zzaNY.zza(this.mContext, this, paramHandler.getLooper(), paramString, paramInt, this.zzaMV);
    paramString.zzys();
    return paramString;
  }
  
  public void setVerboseLoggingEnabled(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int i = 2;; i = 5)
    {
      zzbg.setLogLevel(i);
      return;
    }
  }
  
  void zza(zzo paramzzo)
  {
    this.zzaOa.put(paramzzo, Boolean.valueOf(true));
  }
  
  boolean zzb(zzo paramzzo)
  {
    return this.zzaOa.remove(paramzzo) != null;
  }
  
  public PendingResult<ContainerHolder> zzc(String paramString1, int paramInt, String paramString2)
  {
    paramString1 = this.zzaNY.zza(this.mContext, this, null, paramString1, paramInt, this.zzaMV);
    paramString1.load(paramString2);
    return paramString1;
  }
  
  boolean zzl(Uri paramUri)
  {
    for (;;)
    {
      boolean bool;
      Object localObject2;
      try
      {
        localObject1 = zzcb.zzzf();
        if (!((zzcb)localObject1).zzl(paramUri)) {
          break label229;
        }
        paramUri = ((zzcb)localObject1).getContainerId();
        int i = 4.zzaOd[localObject1.zzzg().ordinal()];
        switch (i)
        {
        default: 
          bool = true;
          return bool;
        }
      }
      finally {}
      Object localObject1 = this.zzaOa.keySet().iterator();
      if (((Iterator)localObject1).hasNext())
      {
        localObject2 = (zzo)((Iterator)localObject1).next();
        if (((zzo)localObject2).getContainerId().equals(paramUri))
        {
          ((zzo)localObject2).zzej(null);
          ((zzo)localObject2).refresh();
        }
      }
      else
      {
        continue;
        localObject2 = this.zzaOa.keySet().iterator();
        while (((Iterator)localObject2).hasNext())
        {
          zzo localzzo = (zzo)((Iterator)localObject2).next();
          if (localzzo.getContainerId().equals(paramUri))
          {
            localzzo.zzej(((zzcb)localObject1).zzzh());
            localzzo.refresh();
          }
          else if (localzzo.zzyo() != null)
          {
            localzzo.zzej(null);
            localzzo.refresh();
          }
        }
        continue;
        label229:
        bool = false;
      }
    }
  }
  
  public static abstract interface zza
  {
    public abstract zzp zza(Context paramContext, TagManager paramTagManager, Looper paramLooper, String paramString, int paramInt, zzs paramzzs);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/TagManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
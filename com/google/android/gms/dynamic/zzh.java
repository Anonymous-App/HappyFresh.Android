package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public final class zzh
  extends zzc.zza
{
  private Fragment zzZX;
  
  private zzh(Fragment paramFragment)
  {
    this.zzZX = paramFragment;
  }
  
  public static zzh zza(Fragment paramFragment)
  {
    if (paramFragment != null) {
      return new zzh(paramFragment);
    }
    return null;
  }
  
  public Bundle getArguments()
  {
    return this.zzZX.getArguments();
  }
  
  public int getId()
  {
    return this.zzZX.getId();
  }
  
  public boolean getRetainInstance()
  {
    return this.zzZX.getRetainInstance();
  }
  
  public String getTag()
  {
    return this.zzZX.getTag();
  }
  
  public int getTargetRequestCode()
  {
    return this.zzZX.getTargetRequestCode();
  }
  
  public boolean getUserVisibleHint()
  {
    return this.zzZX.getUserVisibleHint();
  }
  
  public zzd getView()
  {
    return zze.zzw(this.zzZX.getView());
  }
  
  public boolean isAdded()
  {
    return this.zzZX.isAdded();
  }
  
  public boolean isDetached()
  {
    return this.zzZX.isDetached();
  }
  
  public boolean isHidden()
  {
    return this.zzZX.isHidden();
  }
  
  public boolean isInLayout()
  {
    return this.zzZX.isInLayout();
  }
  
  public boolean isRemoving()
  {
    return this.zzZX.isRemoving();
  }
  
  public boolean isResumed()
  {
    return this.zzZX.isResumed();
  }
  
  public boolean isVisible()
  {
    return this.zzZX.isVisible();
  }
  
  public void setHasOptionsMenu(boolean paramBoolean)
  {
    this.zzZX.setHasOptionsMenu(paramBoolean);
  }
  
  public void setMenuVisibility(boolean paramBoolean)
  {
    this.zzZX.setMenuVisibility(paramBoolean);
  }
  
  public void setRetainInstance(boolean paramBoolean)
  {
    this.zzZX.setRetainInstance(paramBoolean);
  }
  
  public void setUserVisibleHint(boolean paramBoolean)
  {
    this.zzZX.setUserVisibleHint(paramBoolean);
  }
  
  public void startActivity(Intent paramIntent)
  {
    this.zzZX.startActivity(paramIntent);
  }
  
  public void startActivityForResult(Intent paramIntent, int paramInt)
  {
    this.zzZX.startActivityForResult(paramIntent, paramInt);
  }
  
  public void zzl(zzd paramzzd)
  {
    paramzzd = (View)zze.zzn(paramzzd);
    this.zzZX.registerForContextMenu(paramzzd);
  }
  
  public void zzm(zzd paramzzd)
  {
    paramzzd = (View)zze.zzn(paramzzd);
    this.zzZX.unregisterForContextMenu(paramzzd);
  }
  
  public zzd zzqk()
  {
    return zze.zzw(this.zzZX.getActivity());
  }
  
  public zzc zzql()
  {
    return zza(this.zzZX.getParentFragment());
  }
  
  public zzd zzqm()
  {
    return zze.zzw(this.zzZX.getResources());
  }
  
  public zzc zzqn()
  {
    return zza(this.zzZX.getTargetFragment());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/dynamic/zzh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
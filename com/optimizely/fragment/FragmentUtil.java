package com.optimizely.fragment;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class FragmentUtil
{
  @SuppressLint({"NewApi"})
  @Nullable
  static Class getActivityClass(@NonNull OptimizelyTargetable paramOptimizelyTargetable, int paramInt)
  {
    if ((paramInt >= 11) && (android.app.Fragment.class.isInstance(paramOptimizelyTargetable))) {
      return ((android.app.Fragment)paramOptimizelyTargetable).getActivity().getClass();
    }
    if (android.support.v4.app.Fragment.class.isInstance(paramOptimizelyTargetable)) {
      return ((android.support.v4.app.Fragment)paramOptimizelyTargetable).getActivity().getClass();
    }
    return null;
  }
  
  @NonNull
  static String getDefaultViewId(@NonNull Class paramClass1, @NonNull Class paramClass2)
  {
    return paramClass1.getSimpleName() + ">" + paramClass2.getSimpleName();
  }
  
  @Nullable
  static String getViewId(@NonNull OptimizelyTargetable paramOptimizelyTargetable)
  {
    Class localClass = getActivityClass(paramOptimizelyTargetable, Build.VERSION.SDK_INT);
    Object localObject;
    if (localClass == null) {
      localObject = null;
    }
    String str;
    do
    {
      return (String)localObject;
      str = paramOptimizelyTargetable.getViewId();
      localObject = str;
    } while (str != null);
    return getDefaultViewId(localClass, paramOptimizelyTargetable.getClass());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/fragment/FragmentUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
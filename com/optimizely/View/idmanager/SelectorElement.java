package com.optimizely.View.idmanager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

public class SelectorElement
{
  public static final String ANCHOR_CONTENT = "content";
  public static final String ANCHOR_ROOT = "root";
  public static final int TYPE_ANCHOR = 9;
  public static final int TYPE_COMBINATOR = 2;
  public static final int TYPE_ELEMENT = 1;
  public static final int TYPE_ID = 13;
  public static final int TYPE_PSEUDO_CLASS = 128;
  public static final int TYPE_PSEUDO_CLASS_WITH_ARGS = 144;
  public int type;
  @NonNull
  public String value;
  
  public SelectorElement(int paramInt, @NonNull String paramString)
  {
    this.type = paramInt;
    this.value = paramString;
  }
  
  @Nullable
  private String getArgs()
  {
    if (this.type == 144) {
      return this.value.substring(this.value.indexOf('(') + 1, this.value.indexOf(')'));
    }
    return null;
  }
  
  @Nullable
  private String getPsuedoClassName()
  {
    if (this.type == 144) {
      return this.value.substring(0, this.value.indexOf('('));
    }
    if (this.type == 128) {
      return this.value;
    }
    return null;
  }
  
  private static int indexInParent(@Nullable View paramView1, @Nullable View paramView2)
  {
    if ((paramView1 instanceof ViewGroup))
    {
      paramView1 = (ViewGroup)paramView1;
      int i = 0;
      while (i < paramView1.getChildCount())
      {
        if (paramView1.getChildAt(i) == paramView2) {
          return i;
        }
        i += 1;
      }
    }
    return -1;
  }
  
  public static int indexOfTypeInParent(@Nullable View paramView1, @NonNull View paramView2)
  {
    int i = -1;
    String str = paramView2.getClass().getSimpleName();
    int k = i;
    if ((paramView1 instanceof ViewGroup))
    {
      paramView1 = (ViewGroup)paramView1;
      int j = 0;
      for (;;)
      {
        k = i;
        if (j >= paramView1.getChildCount()) {
          break;
        }
        View localView = paramView1.getChildAt(j);
        k = i;
        if (str.equals(localView.getClass().getSimpleName())) {
          k = i + 1;
        }
        if (localView == paramView2) {
          return k;
        }
        j += 1;
        i = k;
      }
    }
    return k;
  }
  
  public static boolean isUniqueInParent(@Nullable ViewGroup paramViewGroup, @NonNull View paramView)
  {
    String str = paramView.getClass().getSimpleName();
    if (paramViewGroup != null)
    {
      int i = 0;
      while (i < paramViewGroup.getChildCount())
      {
        View localView = paramViewGroup.getChildAt(i);
        if ((str.equals(localView.getClass().getSimpleName())) && (localView != paramView)) {
          return false;
        }
        i += 1;
      }
    }
    return true;
  }
  
  private boolean matchesAnchor(@NonNull View paramView, @NonNull OptimizelyIdManager paramOptimizelyIdManager)
  {
    boolean bool = false;
    if (13 == this.type)
    {
      String str = getId();
      if ((str == null) || (!str.equals(paramOptimizelyIdManager.getOptimizelyId(paramView)))) {}
    }
    do
    {
      return true;
      return false;
      if (!"content".equals(this.value)) {
        break;
      }
    } while (paramView.getId() == 16908290);
    return false;
    if ("root".equals(this.value))
    {
      if ((paramView.getParent() == null) || (!(paramView.getParent() instanceof ViewGroup))) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  private boolean matchesPsuedoClass(@Nullable View paramView1, @NonNull View paramView2)
  {
    boolean bool2 = true;
    boolean bool1 = true;
    String str = getPsuedoClassName();
    if (str == null) {}
    int i;
    do
    {
      do
      {
        return false;
      } while ((this.type != 144) || (!getPsuedoClassName().startsWith(":nth")));
      for (;;)
      {
        try
        {
          i = Integer.parseInt(getArgs());
          if (!(paramView1 instanceof AdapterView)) {
            break;
          }
          if (((AdapterView)paramView1).getPositionForView(paramView2) == i) {
            return bool1;
          }
        }
        catch (Exception paramView1)
        {
          return false;
        }
        bool1 = false;
      }
    } while (!":nth-of-type".equals(str));
    if (indexOfTypeInParent(paramView1, paramView2) == i) {}
    for (bool1 = bool2;; bool1 = false) {
      return bool1;
    }
  }
  
  @Nullable
  public String getId()
  {
    if (this.type == 13) {
      return this.value.substring(1);
    }
    return null;
  }
  
  public boolean isAnchor()
  {
    return (this.type & 0x9) == 9;
  }
  
  public boolean matches(@Nullable View paramView1, @Nullable View paramView2, @NonNull OptimizelyIdManager paramOptimizelyIdManager)
  {
    boolean bool = true;
    if (paramView2 == null) {}
    do
    {
      return false;
      if (this.type == 2)
      {
        switch (this.value.charAt(0))
        {
        default: 
          return false;
        }
        if (indexInParent(paramView1, paramView2) != -1) {}
        for (;;)
        {
          return bool;
          bool = false;
        }
      }
      if (this.type == 1) {
        return this.value.equals(paramView2.getClass().getSimpleName());
      }
      if ((this.type & 0x80) == 128) {
        return matchesPsuedoClass(paramView1, paramView2);
      }
    } while (!isAnchor());
    return matchesAnchor(paramView2, paramOptimizelyIdManager);
  }
  
  public String toString()
  {
    String str;
    switch (this.type)
    {
    default: 
      str = "Unknown";
    }
    for (;;)
    {
      return String.format("%s: %s", new Object[] { str, this.value });
      str = "Element";
      continue;
      str = "Combinator";
      continue;
      str = "Psuedo Class";
      continue;
      str = "Pseudo Class()";
      continue;
      str = "ID (Anchor)";
      continue;
      str = "Anchor";
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/View/idmanager/SelectorElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
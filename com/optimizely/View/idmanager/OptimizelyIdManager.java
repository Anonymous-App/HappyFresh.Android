package com.optimizely.View.idmanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import com.optimizely.Optimizely;
import com.optimizely.View.ViewUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OptimizelyIdManager
{
  private final char ACTIVITY_SEP = '@';
  @NonNull
  private final Map<String, List<SelectorElement>> decodedSelectors = new HashMap();
  @NonNull
  private final Map<Class, String> mClassNameCache = new HashMap();
  private final Optimizely optimizely;
  final int tagId;
  
  public OptimizelyIdManager(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
    this.tagId = (paramOptimizely.hashCode() | 0xFF000000);
  }
  
  @Nullable
  private View getAnchorView(@NonNull List<SelectorElement> paramList, @NonNull Activity paramActivity)
  {
    Object localObject = null;
    SelectorElement localSelectorElement = (SelectorElement)paramList.get(0);
    if ((localSelectorElement != null) && (localSelectorElement.isAnchor()))
    {
      if (13 == localSelectorElement.type)
      {
        paramActivity = findAllViewsWithUserDefinedId(localSelectorElement.getId());
        paramList = (List<SelectorElement>)localObject;
        if (paramActivity.size() == 1) {
          paramList = (View)paramActivity.get(0);
        }
      }
      do
      {
        return paramList;
        if ("content".equals(localSelectorElement.value)) {
          return paramActivity.findViewById(16908290);
        }
        paramList = (List<SelectorElement>)localObject;
      } while (!"root".equals(localSelectorElement.value));
      return ViewUtils.rootView(paramActivity);
    }
    return ViewUtils.rootView(paramActivity);
  }
  
  @Nullable
  private String getHierarchicalId(@Nullable View paramView)
  {
    if (paramView != null)
    {
      paramView = paramView.getTag(this.tagId);
      if (paramView != null) {
        return ((IdContainer)paramView).hierarchicalId;
      }
    }
    return null;
  }
  
  @Nullable
  private String getUserDefinedId(@Nullable View paramView)
  {
    if (paramView != null)
    {
      paramView = paramView.getTag(this.tagId);
      if (paramView != null) {
        return ((IdContainer)paramView).userDefinedId;
      }
    }
    return null;
  }
  
  private void resolveSelector(@NonNull List<SelectorElement> paramList, @Nullable ViewGroup paramViewGroup, @Nullable View paramView, @NonNull List<View> paramList1)
  {
    if ((paramList.isEmpty()) || (paramView == null)) {}
    SelectorElement localSelectorElement;
    List localList;
    do
    {
      for (;;)
      {
        return;
        localSelectorElement = (SelectorElement)paramList.get(0);
        localList = paramList.subList(1, paramList.size());
        if (localSelectorElement == null)
        {
          resolveSelector(localList, paramViewGroup, paramView, paramList1);
          return;
        }
        if ((localSelectorElement.type != 2) || (!(paramView instanceof ViewGroup))) {
          break;
        }
        paramList = (ViewGroup)paramView;
        int i = 0;
        while (i < paramList.getChildCount())
        {
          resolveSelector(localList, paramList, paramList.getChildAt(i), paramList1);
          i += 1;
        }
      }
    } while (!localSelectorElement.matches(paramViewGroup, paramView, this));
    if (paramList.size() == 1)
    {
      paramList1.add(paramView);
      return;
    }
    resolveSelector(localList, paramViewGroup, paramView, paramList1);
  }
  
  private boolean selectorMatchesTarget(@NonNull List<SelectorElement> paramList, @Nullable View paramView)
  {
    boolean bool1 = true;
    boolean bool2 = false;
    if (paramList.isEmpty()) {
      if (paramView != null) {}
    }
    List localList;
    do
    {
      SelectorElement localSelectorElement;
      View localView;
      do
      {
        do
        {
          for (;;)
          {
            return bool1;
            bool1 = false;
          }
          bool1 = bool2;
        } while (paramView == null);
        localSelectorElement = (SelectorElement)paramList.get(paramList.size() - 1);
        localList = paramList.subList(0, paramList.size() - 1);
        ViewParent localViewParent = paramView.getParent();
        localView = null;
        if ((localViewParent instanceof View)) {
          localView = (View)localViewParent;
        }
        if (localSelectorElement == null) {
          return selectorMatchesTarget(localList, paramView);
        }
        if ((localSelectorElement.type == 2) && ((localView instanceof ViewGroup))) {
          return selectorMatchesTarget(localList, localView);
        }
        bool1 = bool2;
      } while (!localSelectorElement.matches(localView, paramView, this));
      if (paramList.size() == 1) {
        break;
      }
      bool1 = bool2;
    } while (!selectorMatchesTarget(localList, paramView));
    return true;
  }
  
  private void setHierarchicalId(@Nullable View paramView, @Nullable String paramString)
  {
    if (paramView != null)
    {
      if (paramView.getTag(this.tagId) == null) {
        paramView.setTag(this.tagId, new IdContainer(null));
      }
      IdContainer.access$102((IdContainer)paramView.getTag(this.tagId), paramString);
    }
  }
  
  private void setUserDefinedId(@Nullable View paramView, String paramString)
  {
    if (paramView != null)
    {
      if (paramView.getTag(this.tagId) == null) {
        paramView.setTag(this.tagId, new IdContainer(null));
      }
      IdContainer.access$202((IdContainer)paramView.getTag(this.tagId), paramString);
    }
  }
  
  public boolean activityMatchesOptimizelyId(@NonNull Activity paramActivity, @NonNull String paramString)
  {
    paramString = split(paramString);
    return (paramString == null) || (activityMatchesOptimizelyId(paramActivity, paramString));
  }
  
  public boolean activityMatchesOptimizelyId(@NonNull Activity paramActivity, @NonNull String[] paramArrayOfString)
  {
    return ViewUtils.stripPackage(paramArrayOfString[0]).equals(getSimpleClassName(paramActivity));
  }
  
  public void clearHierarchicalIds(@NonNull List<View> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      View localView = (View)paramList.next();
      if (localView != null) {
        setHierarchicalId(localView, null);
      }
    }
  }
  
  @NonNull
  public List<View> findAllViewsWithUserDefinedId(@Nullable String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject = this.optimizely.getForegroundActivity();
    if ((paramString != null) && (localObject != null))
    {
      localObject = ViewUtils.rootView((Activity)localObject);
      if (localObject != null)
      {
        localObject = ViewUtils.findAllChildViews((View)localObject).iterator();
        while (((Iterator)localObject).hasNext())
        {
          View localView = (View)((Iterator)localObject).next();
          if (paramString.equals(getUserDefinedId(localView))) {
            localArrayList.add(localView);
          }
        }
      }
    }
    return localArrayList;
  }
  
  @NonNull
  public List<View> findViewsByLegacyId(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      paramString = this.optimizely.getForegroundActivity();
      if (paramString != null)
      {
        paramString = paramString.findViewById(i);
        if (paramString != null)
        {
          paramString = Collections.singletonList(paramString);
          return paramString;
        }
      }
    }
    catch (NumberFormatException paramString) {}
    return Collections.emptyList();
  }
  
  @NonNull
  public List<View> findViewsByOptimizelyId(@Nullable String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramString == null) {}
    Activity localActivity;
    do
    {
      do
      {
        do
        {
          return localArrayList;
          localActivity = this.optimizely.getForegroundActivity();
        } while (localActivity == null);
        if (paramString.indexOf('@') == -1)
        {
          localArrayList.addAll(findViewsByLegacyId(paramString));
          localArrayList.addAll(findAllViewsWithUserDefinedId(paramString));
          return localArrayList;
        }
        paramString = split(paramString);
      } while ((paramString == null) || (!activityMatchesOptimizelyId(localActivity, paramString)));
      paramString = paramString[1];
      if (!this.decodedSelectors.containsKey(paramString)) {
        this.decodedSelectors.put(paramString, OptimizelySelectorCodec.parse(paramString));
      }
      paramString = (List)this.decodedSelectors.get(paramString);
    } while (paramString == null);
    resolveSelector(paramString, null, getAnchorView(paramString, localActivity), localArrayList);
    return localArrayList;
  }
  
  @Nullable
  public String getOptimizelyId(@Nullable View paramView)
  {
    if (paramView == null)
    {
      localObject1 = null;
      return (String)localObject1;
    }
    Object localObject1 = getUserDefinedId(paramView);
    if (localObject1 != null) {
      return (String)localObject1;
    }
    localObject1 = getHierarchicalId(paramView);
    if (localObject1 != null) {
      return (String)localObject1;
    }
    localObject1 = this.optimizely.getForegroundActivity();
    if (localObject1 == null) {
      return null;
    }
    String str1 = getSimpleClassName(localObject1);
    View localView = ViewUtils.rootView((Activity)localObject1);
    if (localView == null) {
      return null;
    }
    if (localView.equals(paramView))
    {
      localObject1 = String.format("%s%s%s", new Object[] { str1, Character.valueOf('@'), "root" });
      setHierarchicalId(paramView, (String)localObject1);
      return (String)localObject1;
    }
    if (paramView.getId() == 16908290)
    {
      localObject1 = String.format("%s%s%s", new Object[] { str1, Character.valueOf('@'), "content" });
      setHierarchicalId(paramView, (String)localObject1);
      return (String)localObject1;
    }
    LinkedList localLinkedList = new LinkedList();
    Object localObject2 = paramView.getParent();
    if ((localObject2 == null) && (paramView != localView)) {
      return null;
    }
    localObject1 = paramView;
    int i = 1;
    for (;;)
    {
      int j = i;
      ViewGroup localViewGroup;
      if (localObject2 != null)
      {
        j = i;
        if ((localObject2 instanceof ViewGroup))
        {
          localViewGroup = (ViewGroup)localObject2;
          if (((localViewGroup instanceof AdapterView)) || ((localViewGroup instanceof ViewPager))) {
            j = 0;
          }
          for (;;)
          {
            localLinkedList.push(new SelectorElement(1, getSimpleClassName(localObject1)));
            localLinkedList.push(new SelectorElement(2, ">"));
            if (getHierarchicalId(localViewGroup) == null) {
              break label397;
            }
            localObject2 = getHierarchicalId(localViewGroup) + OptimizelySelectorCodec.toString(localLinkedList);
            localObject1 = localObject2;
            if (j == 0) {
              break;
            }
            setHierarchicalId(paramView, (String)localObject2);
            return (String)localObject2;
            j = i;
            if (!SelectorElement.isUniqueInParent(localViewGroup, (View)localObject1))
            {
              localLinkedList.push(new SelectorElement(144, String.format(":nth-of-type(%d)", new Object[] { Integer.valueOf(SelectorElement.indexOfTypeInParent(localViewGroup, (View)localObject1)) })));
              j = i;
            }
          }
          label397:
          String str2 = getUserDefinedId(localViewGroup);
          if (str2 == null) {
            break label512;
          }
          i = j;
          if (findAllViewsWithUserDefinedId(str2).size() != 1) {
            continue;
          }
          localLinkedList.push(new SelectorElement(13, String.format("%c%s", new Object[] { Character.valueOf('#'), str2 })));
        }
      }
      for (;;)
      {
        localObject2 = str1 + '@' + OptimizelySelectorCodec.toString(localLinkedList);
        localObject1 = localObject2;
        if (j == 0) {
          break;
        }
        setHierarchicalId(paramView, (String)localObject2);
        return (String)localObject2;
        label512:
        if (localViewGroup.getId() == 16908290)
        {
          localLinkedList.push(new SelectorElement(9, "content"));
        }
        else
        {
          if ((!localViewGroup.equals(localView)) && (localViewGroup.getParent() != null)) {
            break label578;
          }
          localLinkedList.push(new SelectorElement(9, "root"));
        }
      }
      label578:
      localObject2 = localViewGroup.getParent();
      localObject1 = localViewGroup;
      i = j;
    }
  }
  
  @NonNull
  public String getSimpleClassName(@NonNull Object paramObject)
  {
    paramObject = paramObject.getClass();
    if (paramObject == null) {
      return "null";
    }
    if (!this.mClassNameCache.containsKey(paramObject)) {
      this.mClassNameCache.put(paramObject, ((Class)paramObject).getSimpleName());
    }
    return (String)this.mClassNameCache.get(paramObject);
  }
  
  public void setOptimizelyId(@NonNull String paramString, @Nullable View paramView)
  {
    if (paramView == null) {
      return;
    }
    setUserDefinedId(paramView, paramString);
  }
  
  @Nullable
  String[] split(@NonNull String paramString)
  {
    int i = paramString.indexOf('@');
    if (i == -1) {
      return null;
    }
    return new String[] { paramString.substring(0, i), paramString.substring(i + 1) };
  }
  
  @Nullable
  public String stripPackageNameFromOptimizelyId(@NonNull String paramString)
  {
    String[] arrayOfString = split(paramString);
    if ((arrayOfString == null) || (arrayOfString[0] == null) || (arrayOfString[1] == null)) {
      return paramString;
    }
    return ViewUtils.stripPackage(arrayOfString[0]) + '@' + arrayOfString[1];
  }
  
  public boolean viewMatchesOptimizelyId(@Nullable View paramView, @Nullable String paramString)
  {
    boolean bool2 = true;
    boolean bool1;
    if ((paramView == null) || (paramString == null)) {
      bool1 = false;
    }
    do
    {
      Activity localActivity;
      for (;;)
      {
        return bool1;
        localActivity = this.optimizely.getForegroundActivity();
        if (paramString.indexOf('@') == -1)
        {
          bool1 = bool2;
          if (paramString.equals(getUserDefinedId(paramView))) {
            continue;
          }
          try
          {
            int i = Integer.parseInt(paramString);
            int j = paramView.getId();
            bool1 = bool2;
            if (j != i) {
              return false;
            }
          }
          catch (NumberFormatException paramView)
          {
            return false;
          }
        }
      }
      paramString = split(paramString);
      if ((localActivity == null) || (paramString == null) || (!activityMatchesOptimizelyId(localActivity, paramString))) {
        break label168;
      }
      paramString = paramString[1];
      if (!this.decodedSelectors.containsKey(paramString)) {
        this.decodedSelectors.put(paramString, OptimizelySelectorCodec.parse(paramString));
      }
      paramString = (List)this.decodedSelectors.get(paramString);
      if (paramString == null) {
        break;
      }
      bool1 = bool2;
    } while (selectorMatchesTarget(paramString, paramView));
    return false;
    label168:
    return false;
  }
  
  private static class IdContainer
  {
    private String hierarchicalId;
    private String userDefinedId;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/View/idmanager/OptimizelyIdManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
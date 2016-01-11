package butterknife.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class ViewInjection
{
  private final int id;
  private final LinkedHashMap<ListenerClass, Map<ListenerMethod, Set<ListenerBinding>>> listenerBindings = new LinkedHashMap();
  private final Set<ViewBinding> viewBindings = new LinkedHashSet();
  
  ViewInjection(int paramInt)
  {
    this.id = paramInt;
  }
  
  public void addListenerBinding(ListenerClass paramListenerClass, ListenerMethod paramListenerMethod, ListenerBinding paramListenerBinding)
  {
    Object localObject1 = (Map)this.listenerBindings.get(paramListenerClass);
    Object localObject2 = null;
    if (localObject1 == null)
    {
      localObject1 = new LinkedHashMap();
      this.listenerBindings.put(paramListenerClass, localObject1);
    }
    for (paramListenerClass = (ListenerClass)localObject2;; paramListenerClass = (Set)((Map)localObject1).get(paramListenerMethod))
    {
      localObject2 = paramListenerClass;
      if (paramListenerClass == null)
      {
        localObject2 = new LinkedHashSet();
        ((Map)localObject1).put(paramListenerMethod, localObject2);
      }
      ((Set)localObject2).add(paramListenerBinding);
      return;
    }
  }
  
  public void addViewBinding(ViewBinding paramViewBinding)
  {
    this.viewBindings.add(paramViewBinding);
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public Map<ListenerClass, Map<ListenerMethod, Set<ListenerBinding>>> getListenerBindings()
  {
    return this.listenerBindings;
  }
  
  public List<Binding> getRequiredBindings()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = this.viewBindings.iterator();
    Object localObject;
    while (localIterator1.hasNext())
    {
      localObject = (ViewBinding)localIterator1.next();
      if (((ViewBinding)localObject).isRequired()) {
        localArrayList.add(localObject);
      }
    }
    localIterator1 = this.listenerBindings.values().iterator();
    if (localIterator1.hasNext())
    {
      localObject = ((Map)localIterator1.next()).values().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Iterator localIterator2 = ((Set)((Iterator)localObject).next()).iterator();
        while (localIterator2.hasNext())
        {
          ListenerBinding localListenerBinding = (ListenerBinding)localIterator2.next();
          if (localListenerBinding.isRequired()) {
            localArrayList.add(localListenerBinding);
          }
        }
      }
    }
    return localArrayList;
  }
  
  public Collection<ViewBinding> getViewBindings()
  {
    return this.viewBindings;
  }
  
  public boolean hasListenerBinding(ListenerClass paramListenerClass, ListenerMethod paramListenerMethod)
  {
    paramListenerClass = (Map)this.listenerBindings.get(paramListenerClass);
    return (paramListenerClass != null) && (paramListenerClass.containsKey(paramListenerMethod));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/butterknife/internal/ViewInjection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package butterknife.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class ViewInjector
{
  private final String className;
  private final String classPackage;
  private final Map<CollectionBinding, int[]> collectionBindings = new LinkedHashMap();
  private String parentInjector;
  private final String targetClass;
  private final Map<Integer, ViewInjection> viewIdMap = new LinkedHashMap();
  
  ViewInjector(String paramString1, String paramString2, String paramString3)
  {
    this.classPackage = paramString1;
    this.className = paramString2;
    this.targetClass = paramString3;
  }
  
  static void emitCastIfNeeded(StringBuilder paramStringBuilder, String paramString)
  {
    emitCastIfNeeded(paramStringBuilder, "android.view.View", paramString);
  }
  
  static void emitCastIfNeeded(StringBuilder paramStringBuilder, String paramString1, String paramString2)
  {
    if (!paramString1.equals(paramString2)) {
      paramStringBuilder.append('(').append(paramString2).append(") ");
    }
  }
  
  private void emitCollectionBinding(StringBuilder paramStringBuilder, CollectionBinding paramCollectionBinding, int[] paramArrayOfInt)
  {
    paramStringBuilder.append("    target.").append(paramCollectionBinding.getName()).append(" = ");
    int i;
    switch (paramCollectionBinding.getKind())
    {
    default: 
      throw new IllegalStateException("Unknown kind: " + paramCollectionBinding.getKind());
    case ???: 
      paramStringBuilder.append("Finder.arrayOf(");
      i = 0;
      label92:
      if (i >= paramArrayOfInt.length) {
        break label205;
      }
      if (i > 0) {
        paramStringBuilder.append(',');
      }
      paramStringBuilder.append("\n        ");
      emitCastIfNeeded(paramStringBuilder, paramCollectionBinding.getType());
      if (paramCollectionBinding.isRequired()) {
        paramStringBuilder.append("finder.findRequiredView(source, ").append(paramArrayOfInt[i]).append(", \"").append(paramCollectionBinding.getName()).append("\")");
      }
      break;
    }
    for (;;)
    {
      i += 1;
      break label92;
      paramStringBuilder.append("Finder.listOf(");
      break;
      paramStringBuilder.append("finder.findOptionalView(source, ").append(paramArrayOfInt[i]).append(")");
    }
    label205:
    paramStringBuilder.append("\n    );");
  }
  
  static void emitHumanDescription(StringBuilder paramStringBuilder, List<Binding> paramList)
  {
    int i;
    int j;
    switch (paramList.size())
    {
    default: 
      i = 0;
      j = paramList.size();
    case 1: 
      while (i < j)
      {
        Binding localBinding = (Binding)paramList.get(i);
        if (i != 0) {
          paramStringBuilder.append(", ");
        }
        if (i == j - 1) {
          paramStringBuilder.append("and ");
        }
        paramStringBuilder.append(localBinding.getDescription());
        i += 1;
        continue;
        paramStringBuilder.append(((Binding)paramList.get(0)).getDescription());
      }
      return;
    }
    paramStringBuilder.append(((Binding)paramList.get(0)).getDescription()).append(" and ").append(((Binding)paramList.get(1)).getDescription());
  }
  
  private void emitInject(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append("  public static void inject(Finder finder, final ").append(this.targetClass).append(" target, Object source) {\n");
    if (this.parentInjector != null) {
      paramStringBuilder.append("    ").append(this.parentInjector).append(".inject(finder, target, source);\n\n");
    }
    paramStringBuilder.append("    View view;\n");
    Iterator localIterator = this.viewIdMap.values().iterator();
    while (localIterator.hasNext()) {
      emitViewInjection(paramStringBuilder, (ViewInjection)localIterator.next());
    }
    localIterator = this.collectionBindings.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      emitCollectionBinding(paramStringBuilder, (CollectionBinding)localEntry.getKey(), (int[])localEntry.getValue());
    }
    paramStringBuilder.append("  }\n");
  }
  
  private void emitListenerBindings(StringBuilder paramStringBuilder, ViewInjection paramViewInjection)
  {
    Object localObject2 = paramViewInjection.getListenerBindings();
    if (((Map)localObject2).isEmpty()) {}
    boolean bool;
    label231:
    label238:
    label751:
    do
    {
      return;
      Object localObject1 = "";
      bool = paramViewInjection.getRequiredBindings().isEmpty();
      paramViewInjection = (ViewInjection)localObject1;
      if (bool)
      {
        paramStringBuilder.append("    if (view != null) {\n");
        paramViewInjection = "  ";
      }
      localObject1 = ((Map)localObject2).entrySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Map.Entry)((Iterator)localObject1).next();
        Object localObject3 = (ListenerClass)((Map.Entry)localObject2).getKey();
        localObject2 = (Map)((Map.Entry)localObject2).getValue();
        if (!"android.view.View".equals(((ListenerClass)localObject3).targetType())) {}
        int j;
        for (int i = 1;; i = 0)
        {
          paramStringBuilder.append(paramViewInjection).append("    ");
          if (i == 0) {
            break label238;
          }
          paramStringBuilder.append("((").append(((ListenerClass)localObject3).targetType());
          if (((ListenerClass)localObject3).genericArguments() <= 0) {
            break label231;
          }
          paramStringBuilder.append('<');
          j = 0;
          while (j < ((ListenerClass)localObject3).genericArguments())
          {
            if (j > 0) {
              paramStringBuilder.append(", ");
            }
            paramStringBuilder.append('?');
            j += 1;
          }
        }
        paramStringBuilder.append('>');
        paramStringBuilder.append(") ");
        paramStringBuilder.append("view");
        if (i != 0) {
          paramStringBuilder.append(')');
        }
        paramStringBuilder.append('.').append(((ListenerClass)localObject3).setter()).append("(\n");
        paramStringBuilder.append(paramViewInjection).append("      new ").append(((ListenerClass)localObject3).type()).append("() {\n");
        localObject3 = getListenerMethods((ListenerClass)localObject3).iterator();
        while (((Iterator)localObject3).hasNext())
        {
          ListenerMethod localListenerMethod = (ListenerMethod)((Iterator)localObject3).next();
          paramStringBuilder.append(paramViewInjection).append("        @Override public ").append(localListenerMethod.returnType()).append(' ').append(localListenerMethod.name()).append("(\n");
          Object localObject4 = localListenerMethod.parameters();
          i = 0;
          j = localObject4.length;
          while (i < j)
          {
            paramStringBuilder.append(paramViewInjection).append("          ").append(localObject4[i]).append(" p").append(i);
            if (i < j - 1) {
              paramStringBuilder.append(',');
            }
            paramStringBuilder.append('\n');
            i += 1;
          }
          paramStringBuilder.append(paramViewInjection).append("        ) {\n");
          paramStringBuilder.append(paramViewInjection).append("          ");
          if (!"void".equals(localListenerMethod.returnType()))
          {
            i = 1;
            if (i != 0) {
              paramStringBuilder.append("return ");
            }
            if (((Map)localObject2).containsKey(localListenerMethod)) {
              localObject4 = ((Set)((Map)localObject2).get(localListenerMethod)).iterator();
            }
          }
          else
          {
            for (;;)
            {
              if (!((Iterator)localObject4).hasNext()) {
                break label751;
              }
              Object localObject5 = (ListenerBinding)((Iterator)localObject4).next();
              paramStringBuilder.append("target.").append(((ListenerBinding)localObject5).getName()).append('(');
              localObject5 = ((ListenerBinding)localObject5).getParameters();
              String[] arrayOfString = localListenerMethod.parameters();
              i = 0;
              j = ((List)localObject5).size();
              for (;;)
              {
                if (i < j)
                {
                  Parameter localParameter = (Parameter)((List)localObject5).get(i);
                  int k = localParameter.getListenerPosition();
                  emitCastIfNeeded(paramStringBuilder, arrayOfString[k], localParameter.getType());
                  paramStringBuilder.append('p').append(k);
                  if (i < j - 1) {
                    paramStringBuilder.append(", ");
                  }
                  i += 1;
                  continue;
                  i = 0;
                  break;
                }
              }
              paramStringBuilder.append(");");
              if (((Iterator)localObject4).hasNext()) {
                paramStringBuilder.append("\n").append("          ");
              }
            }
          }
          if (i != 0) {
            paramStringBuilder.append(localListenerMethod.defaultReturn()).append(';');
          }
          paramStringBuilder.append('\n');
          paramStringBuilder.append(paramViewInjection).append("        }\n");
        }
        paramStringBuilder.append(paramViewInjection).append("      });\n");
      }
    } while (!bool);
    paramStringBuilder.append("    }\n");
  }
  
  private void emitReset(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append("  public static void reset(").append(this.targetClass).append(" target) {\n");
    if (this.parentInjector != null) {
      paramStringBuilder.append("    ").append(this.parentInjector).append(".reset(target);\n\n");
    }
    Iterator localIterator = this.viewIdMap.values().iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = ((ViewInjection)localIterator.next()).getViewBindings().iterator();
      while (((Iterator)localObject).hasNext())
      {
        ViewBinding localViewBinding = (ViewBinding)((Iterator)localObject).next();
        paramStringBuilder.append("    target.").append(localViewBinding.getName()).append(" = null;\n");
      }
    }
    localIterator = this.collectionBindings.keySet().iterator();
    while (localIterator.hasNext())
    {
      localObject = (CollectionBinding)localIterator.next();
      paramStringBuilder.append("    target.").append(((CollectionBinding)localObject).getName()).append(" = null;\n");
    }
    paramStringBuilder.append("  }\n");
  }
  
  private void emitViewBindings(StringBuilder paramStringBuilder, ViewInjection paramViewInjection)
  {
    paramViewInjection = paramViewInjection.getViewBindings();
    if (paramViewInjection.isEmpty()) {}
    for (;;)
    {
      return;
      paramViewInjection = paramViewInjection.iterator();
      while (paramViewInjection.hasNext())
      {
        ViewBinding localViewBinding = (ViewBinding)paramViewInjection.next();
        paramStringBuilder.append("    target.").append(localViewBinding.getName()).append(" = ");
        emitCastIfNeeded(paramStringBuilder, localViewBinding.getType());
        paramStringBuilder.append("view;\n");
      }
    }
  }
  
  private void emitViewInjection(StringBuilder paramStringBuilder, ViewInjection paramViewInjection)
  {
    paramStringBuilder.append("    view = ");
    List localList = paramViewInjection.getRequiredBindings();
    if (localList.isEmpty()) {
      paramStringBuilder.append("finder.findOptionalView(source, ").append(paramViewInjection.getId()).append(");\n");
    }
    for (;;)
    {
      emitViewBindings(paramStringBuilder, paramViewInjection);
      emitListenerBindings(paramStringBuilder, paramViewInjection);
      return;
      if (paramViewInjection.getId() == -1)
      {
        paramStringBuilder.append("target;\n");
      }
      else
      {
        paramStringBuilder.append("finder.findRequiredView(source, ").append(paramViewInjection.getId()).append(", \"");
        emitHumanDescription(paramStringBuilder, localList);
        paramStringBuilder.append("\");\n");
      }
    }
  }
  
  static List<ListenerMethod> getListenerMethods(ListenerClass paramListenerClass)
  {
    int i = 0;
    if (paramListenerClass.method().length == 1)
    {
      paramListenerClass = Arrays.asList(paramListenerClass.method());
      return paramListenerClass;
    }
    for (;;)
    {
      ArrayList localArrayList;
      ListenerMethod localListenerMethod;
      try
      {
        localArrayList = new ArrayList();
        Class localClass = paramListenerClass.callbacks();
        Enum[] arrayOfEnum = (Enum[])localClass.getEnumConstants();
        int j = arrayOfEnum.length;
        paramListenerClass = localArrayList;
        if (i >= j) {
          break;
        }
        paramListenerClass = arrayOfEnum[i];
        localListenerMethod = (ListenerMethod)localClass.getField(paramListenerClass.name()).getAnnotation(ListenerMethod.class);
        if (localListenerMethod == null) {
          throw new IllegalStateException(String.format("@%s's %s.%s missing @%s annotation.", new Object[] { localClass.getEnclosingClass().getSimpleName(), localClass.getSimpleName(), paramListenerClass.name(), ListenerMethod.class.getSimpleName() }));
        }
      }
      catch (NoSuchFieldException paramListenerClass)
      {
        throw new AssertionError(paramListenerClass);
      }
      localArrayList.add(localListenerMethod);
      i += 1;
    }
  }
  
  private ViewInjection getOrCreateViewInjection(int paramInt)
  {
    ViewInjection localViewInjection2 = (ViewInjection)this.viewIdMap.get(Integer.valueOf(paramInt));
    ViewInjection localViewInjection1 = localViewInjection2;
    if (localViewInjection2 == null)
    {
      localViewInjection1 = new ViewInjection(paramInt);
      this.viewIdMap.put(Integer.valueOf(paramInt), localViewInjection1);
    }
    return localViewInjection1;
  }
  
  void addCollection(int[] paramArrayOfInt, CollectionBinding paramCollectionBinding)
  {
    this.collectionBindings.put(paramCollectionBinding, paramArrayOfInt);
  }
  
  boolean addListener(int paramInt, ListenerClass paramListenerClass, ListenerMethod paramListenerMethod, ListenerBinding paramListenerBinding)
  {
    ViewInjection localViewInjection = getOrCreateViewInjection(paramInt);
    if ((localViewInjection.hasListenerBinding(paramListenerClass, paramListenerMethod)) && (!"void".equals(paramListenerMethod.returnType()))) {
      return false;
    }
    localViewInjection.addListenerBinding(paramListenerClass, paramListenerMethod, paramListenerBinding);
    return true;
  }
  
  void addView(int paramInt, ViewBinding paramViewBinding)
  {
    getOrCreateViewInjection(paramInt).addViewBinding(paramViewBinding);
  }
  
  String brewJava()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("// Generated code from Butter Knife. Do not modify!\n");
    localStringBuilder.append("package ").append(this.classPackage).append(";\n\n");
    localStringBuilder.append("import android.view.View;\n");
    localStringBuilder.append("import butterknife.ButterKnife.Finder;\n\n");
    localStringBuilder.append("public class ").append(this.className).append(" {\n");
    emitInject(localStringBuilder);
    localStringBuilder.append('\n');
    emitReset(localStringBuilder);
    localStringBuilder.append("}\n");
    return localStringBuilder.toString();
  }
  
  String getFqcn()
  {
    return this.classPackage + "." + this.className;
  }
  
  ViewInjection getViewInjection(int paramInt)
  {
    return (ViewInjection)this.viewIdMap.get(Integer.valueOf(paramInt));
  }
  
  void setParentInjector(String paramString)
  {
    this.parentInjector = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/butterknife/internal/ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
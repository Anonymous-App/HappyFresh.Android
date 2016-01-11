package org.jcodec;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ToJSON
{
  static Set<Class> good = new HashSet();
  
  static
  {
    good.add(String.class);
    good.add(Byte.class);
    good.add(Short.class);
    good.add(Integer.class);
    good.add(Long.class);
    good.add(Float.class);
    good.add(Double.class);
    good.add(Character.class);
  }
  
  public static boolean isGetter(Method paramMethod)
  {
    if (!paramMethod.getName().startsWith("get")) {}
    while ((paramMethod.getParameterTypes().length != 0) || (Void.TYPE.equals(paramMethod.getReturnType()))) {
      return false;
    }
    return true;
  }
  
  public static void toJSON(Object paramObject, StringBuilder paramStringBuilder, String... paramVarArgs)
  {
    int i = 0;
    paramStringBuilder.append("{\n");
    HashSet localHashSet = new HashSet(Arrays.asList(paramVarArgs));
    Method[] arrayOfMethod = paramObject.getClass().getMethods();
    int j = arrayOfMethod.length;
    if (i < j)
    {
      Object localObject = arrayOfMethod[i];
      if (!isGetter((Method)localObject)) {}
      for (;;)
      {
        i += 1;
        break;
        try
        {
          String str = toName((Method)localObject);
          if ((paramVarArgs.length <= 0) || (localHashSet.contains(str)))
          {
            localObject = ((Method)localObject).invoke(paramObject, new Object[0]);
            if ((localObject.getClass().isPrimitive()) || (good.contains(localObject.getClass())) || ((localObject instanceof Iterable)))
            {
              paramStringBuilder.append(str + ": ");
              value(paramStringBuilder, localObject);
              paramStringBuilder.append(",\n");
            }
          }
        }
        catch (Exception localException) {}
      }
    }
    paramStringBuilder.append("}");
  }
  
  private static String toName(Method paramMethod)
  {
    paramMethod = paramMethod.getName().substring(3);
    return paramMethod.substring(0, 1).toLowerCase() + paramMethod.substring(1);
  }
  
  private static void value(StringBuilder paramStringBuilder, Object paramObject)
  {
    if (paramObject == null)
    {
      paramStringBuilder.append("null");
      return;
    }
    if (paramObject == String.class)
    {
      paramStringBuilder.append("'");
      paramStringBuilder.append((String)paramObject);
      paramStringBuilder.append("'");
      return;
    }
    if ((paramObject instanceof Iterable))
    {
      paramObject = ((Iterable)paramObject).iterator();
      paramStringBuilder.append("[");
      while (((Iterator)paramObject).hasNext())
      {
        toJSON(((Iterator)paramObject).next(), paramStringBuilder, new String[0]);
        if (((Iterator)paramObject).hasNext()) {
          paramStringBuilder.append(",");
        }
      }
      paramStringBuilder.append("]");
      return;
    }
    paramStringBuilder.append(String.valueOf(paramObject));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/ToJSON.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
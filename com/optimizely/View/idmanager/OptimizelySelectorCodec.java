package com.optimizely.View.idmanager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class OptimizelySelectorCodec
{
  private static final String COMBINATORS = ">";
  static final char ID_START = '#';
  static final char PSEUDO_ARGS_END = ')';
  static final char PSEUDO_ARGS_START = '(';
  private static final char PSEUDO_CLASS_START = ':';
  
  @NonNull
  public static List<SelectorElement> parse(@Nullable String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramString == null) {}
    SelectorElement localSelectorElement;
    StringBuilder localStringBuilder;
    do
    {
      return localArrayList;
      localSelectorElement = new SelectorElement(9, "");
      localStringBuilder = new StringBuilder();
      int i = 0;
      if (i < paramString.length())
      {
        char c = paramString.charAt(i);
        if (">".indexOf(c) != -1)
        {
          localSelectorElement.value = localStringBuilder.toString();
          localArrayList.add(localSelectorElement);
          localArrayList.add(new SelectorElement(2, Character.toString(c)));
          localSelectorElement = new SelectorElement(1, "");
          localStringBuilder.setLength(0);
        }
        for (;;)
        {
          i += 1;
          break;
          if (':' == c)
          {
            localSelectorElement.value = localStringBuilder.toString();
            localArrayList.add(localSelectorElement);
            localSelectorElement = new SelectorElement(128, "");
            localStringBuilder.setLength(0);
            localStringBuilder.append(c);
          }
          else if ((localSelectorElement.type == 128) && ('(' == c))
          {
            localSelectorElement.type = 144;
            localStringBuilder.append(c);
          }
          else if ('#' == c)
          {
            localSelectorElement.type = 13;
            localStringBuilder.append(c);
          }
          else
          {
            localStringBuilder.append(c);
          }
        }
      }
    } while (localStringBuilder.length() <= 0);
    localSelectorElement.value = localStringBuilder.toString();
    localArrayList.add(localSelectorElement);
    return localArrayList;
  }
  
  public static String toString(@NonNull Collection<SelectorElement> paramCollection)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      localStringBuilder.append(((SelectorElement)paramCollection.next()).value);
    }
    return localStringBuilder.toString();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/View/idmanager/OptimizelySelectorCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.activeandroid.widget;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.activeandroid.Model;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ModelAdapter<T extends Model>
  extends ArrayAdapter<T>
{
  public ModelAdapter(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }
  
  public ModelAdapter(Context paramContext, int paramInt1, int paramInt2)
  {
    super(paramContext, paramInt1, paramInt2);
  }
  
  public ModelAdapter(Context paramContext, int paramInt1, int paramInt2, List<T> paramList)
  {
    super(paramContext, paramInt1, paramInt2, paramList);
  }
  
  public ModelAdapter(Context paramContext, int paramInt, List<T> paramList)
  {
    super(paramContext, paramInt, paramList);
  }
  
  public long getItemId(int paramInt)
  {
    Model localModel = (Model)getItem(paramInt);
    if (localModel != null) {
      return localModel.getId().longValue();
    }
    return -1L;
  }
  
  public void setData(Collection<? extends T> paramCollection)
  {
    clear();
    if (paramCollection != null)
    {
      paramCollection = paramCollection.iterator();
      while (paramCollection.hasNext()) {
        add((Model)paramCollection.next());
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/widget/ModelAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
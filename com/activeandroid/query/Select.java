package com.activeandroid.query;

import android.text.TextUtils;
import com.activeandroid.Model;

public final class Select
  implements Sqlable
{
  private boolean mAll = false;
  private String[] mColumns;
  private boolean mDistinct = false;
  
  public Select() {}
  
  public Select(Column... paramVarArgs)
  {
    int j = paramVarArgs.length;
    this.mColumns = new String[j];
    int i = 0;
    while (i < j)
    {
      this.mColumns[i] = (paramVarArgs[i].name + " AS " + paramVarArgs[i].alias);
      i += 1;
    }
  }
  
  public Select(String... paramVarArgs)
  {
    this.mColumns = paramVarArgs;
  }
  
  public Select all()
  {
    this.mDistinct = false;
    this.mAll = true;
    return this;
  }
  
  public Select distinct()
  {
    this.mDistinct = true;
    this.mAll = false;
    return this;
  }
  
  public From from(Class<? extends Model> paramClass)
  {
    return new From(paramClass, this);
  }
  
  public String toSql()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SELECT ");
    if (this.mDistinct)
    {
      localStringBuilder.append("DISTINCT ");
      if ((this.mColumns == null) || (this.mColumns.length <= 0)) {
        break label98;
      }
      localStringBuilder.append(TextUtils.join(", ", this.mColumns) + " ");
    }
    for (;;)
    {
      return localStringBuilder.toString();
      if (!this.mAll) {
        break;
      }
      localStringBuilder.append("ALL ");
      break;
      label98:
      localStringBuilder.append("* ");
    }
  }
  
  public static class Column
  {
    String alias;
    String name;
    
    public Column(String paramString1, String paramString2)
    {
      this.name = paramString1;
      this.alias = paramString2;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/query/Select.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
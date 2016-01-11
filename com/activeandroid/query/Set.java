package com.activeandroid.query;

import com.activeandroid.util.SQLiteUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Set
  implements Sqlable
{
  private String mSet;
  private List<Object> mSetArguments;
  private Update mUpdate;
  private String mWhere;
  private List<Object> mWhereArguments;
  
  public Set(Update paramUpdate, String paramString)
  {
    this.mUpdate = paramUpdate;
    this.mSet = paramString;
    this.mSetArguments = new ArrayList();
    this.mWhereArguments = new ArrayList();
  }
  
  public Set(Update paramUpdate, String paramString, Object... paramVarArgs)
  {
    this.mUpdate = paramUpdate;
    this.mSet = paramString;
    this.mSetArguments = new ArrayList();
    this.mWhereArguments = new ArrayList();
    this.mSetArguments.addAll(Arrays.asList(paramVarArgs));
  }
  
  public void execute()
  {
    SQLiteUtils.execSql(toSql(), getArguments());
  }
  
  public String[] getArguments()
  {
    int j = this.mSetArguments.size();
    int k = this.mWhereArguments.size();
    String[] arrayOfString = new String[j + k];
    int i = 0;
    while (i < j)
    {
      arrayOfString[i] = this.mSetArguments.get(i).toString();
      i += 1;
    }
    i = 0;
    while (i < k)
    {
      arrayOfString[(i + j)] = this.mWhereArguments.get(i).toString();
      i += 1;
    }
    return arrayOfString;
  }
  
  public String toSql()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mUpdate.toSql());
    localStringBuilder.append("SET ");
    localStringBuilder.append(this.mSet);
    localStringBuilder.append(" ");
    if (this.mWhere != null)
    {
      localStringBuilder.append("WHERE ");
      localStringBuilder.append(this.mWhere);
      localStringBuilder.append(" ");
    }
    return localStringBuilder.toString();
  }
  
  public Set where(String paramString)
  {
    this.mWhere = paramString;
    this.mWhereArguments.clear();
    return this;
  }
  
  public Set where(String paramString, Object... paramVarArgs)
  {
    this.mWhere = paramString;
    this.mWhereArguments.clear();
    this.mWhereArguments.addAll(Arrays.asList(paramVarArgs));
    return this;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/query/Set.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
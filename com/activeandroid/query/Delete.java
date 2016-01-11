package com.activeandroid.query;

import com.activeandroid.Model;

public final class Delete
  implements Sqlable
{
  public From from(Class<? extends Model> paramClass)
  {
    return new From(paramClass, this);
  }
  
  public String toSql()
  {
    return "DELETE ";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/query/Delete.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
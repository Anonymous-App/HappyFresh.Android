package com.optimizely.Variable;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LiveVariable<T>
{
  private final T defaultValue;
  private final OptimizelyVariables optimizelyVariables;
  private final String variableKey;
  
  protected LiveVariable(@NonNull String paramString, @Nullable T paramT, @NonNull OptimizelyVariables paramOptimizelyVariables)
  {
    this.variableKey = paramString;
    this.defaultValue = paramT;
    this.optimizelyVariables = paramOptimizelyVariables;
  }
  
  @Nullable
  public T get()
  {
    try
    {
      Object localObject = this.optimizelyVariables.computeVariableValue(this.variableKey, this.defaultValue);
      return (T)localObject;
    }
    catch (ClassCastException localClassCastException) {}
    return (T)this.defaultValue;
  }
  
  public static abstract interface Callback<T>
  {
    public abstract void execute(String paramString, @Nullable T paramT);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Variable/LiveVariable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
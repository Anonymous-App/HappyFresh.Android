package com.optimizely.Variable;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.Core.OptimizelyCodec;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.JSON.OptimizelyVariable;
import com.optimizely.JSON.OptimizelyVariation;
import com.optimizely.Optimizely;
import com.optimizely.Optimizely.OptimizelyRunningMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class OptimizelyVariables
{
  @NonNull
  private static final String OPTIMIZELY_VARIABLES_COMPONENT = "OptimizelyVariables";
  @NonNull
  private final Map<String, LiveVariable.Callback> callbacksByVariables = new HashMap();
  private final Optimizely optimizely;
  @NonNull
  private final Map<String, OptimizelyVariable> registeredVariables = new HashMap();
  
  static
  {
    if (!OptimizelyVariables.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public OptimizelyVariables(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  @NonNull
  private <T> LiveVariable<T> createVariable(@NonNull String paramString, @Nullable T paramT, @NonNull Class paramClass, @Nullable LiveVariable.Callback<T> paramCallback)
  {
    if (!this.registeredVariables.containsKey(paramString))
    {
      paramClass = OptimizelyCodec.encode(paramString, paramT, paramClass);
      this.registeredVariables.put(paramString, paramClass);
      sendToEditor(paramClass);
      if (paramCallback != null) {
        this.callbacksByVariables.put(paramString, paramCallback);
      }
    }
    return new LiveVariable(paramString, paramT, this);
  }
  
  private void sendToEditor(@NonNull OptimizelyVariable paramOptimizelyVariable)
  {
    if ((this.optimizely.isActive()) && (this.optimizely.isEditorEnabled().booleanValue()))
    {
      paramOptimizelyVariable = variableToMap(paramOptimizelyVariable);
      this.optimizely.sendMap(paramOptimizelyVariable);
    }
  }
  
  @NonNull
  private Map<String, Object> variableToMap(@NonNull OptimizelyVariable paramOptimizelyVariable)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("action", "registerVariable");
    localHashMap.put("key", paramOptimizelyVariable.getVariableKey());
    localHashMap.put("type", paramOptimizelyVariable.getType());
    localHashMap.put("value", paramOptimizelyVariable.getValue());
    return localHashMap;
  }
  
  @NonNull
  public LiveVariable<Boolean> booleanForKey(@NonNull String paramString, @Nullable Boolean paramBoolean, @Nullable LiveVariable.Callback<Boolean> paramCallback)
  {
    if (paramBoolean != null) {}
    for (boolean bool = paramBoolean.booleanValue();; bool = false) {
      return createVariable(paramString, Boolean.valueOf(bool), Boolean.class, paramCallback);
    }
  }
  
  @NonNull
  public LiveVariable<Integer> colorForKey(@NonNull String paramString, int paramInt, @Nullable LiveVariable.Callback<Integer> paramCallback)
  {
    return createVariable(paramString, Integer.valueOf(paramInt), Color.class, paramCallback);
  }
  
  @Nullable
  public Object computeVariableValue(@NonNull String paramString, @Nullable Object paramObject)
  {
    if (!this.optimizely.isActive())
    {
      this.optimizely.verboseLog(true, "OptimizelyVariables", "Warning: variable %s evaluated before Optimizely was started. Default value returned.", new Object[] { paramString });
      return paramObject;
    }
    if (Optimizely.getRunningMode() == Optimizely.OptimizelyRunningMode.EDIT)
    {
      paramString = (OptimizelyVariable)this.registeredVariables.get(paramString);
      if (paramString != null) {}
      for (paramString = OptimizelyCodec.decode(this.optimizely, paramString);; paramString = null) {
        return paramString;
      }
    }
    this.optimizely.getOptimizelyData().addLockedVariable(paramString);
    Object localObject2;
    do
    {
      OptimizelyExperiment localOptimizelyExperiment;
      do
      {
        Iterator localIterator = this.optimizely.getOptimizelyData().getRunningExperimentsById().entrySet().iterator();
        Object localObject1;
        while (!((Iterator)localObject1).hasNext())
        {
          for (;;)
          {
            if (!localIterator.hasNext()) {
              break label296;
            }
            localOptimizelyExperiment = (OptimizelyExperiment)((Map.Entry)localIterator.next()).getValue();
            if (localOptimizelyExperiment != null)
            {
              localObject1 = localOptimizelyExperiment.getActiveVariation();
              if (localObject1 != null)
              {
                localObject2 = ((OptimizelyVariation)localObject1).getVariables();
                if (localObject2 != null) {
                  break;
                }
                this.optimizely.verboseLog("OptimizelyVariables", "Missing variables for experiment %s, variation %s", new Object[] { localOptimizelyExperiment.getExperimentId(), ((OptimizelyVariation)localObject1).getVariationId() });
              }
            }
          }
          localObject1 = ((List)localObject2).iterator();
        }
        localObject2 = (OptimizelyVariable)((Iterator)localObject1).next();
      } while (!paramString.equals(((OptimizelyVariable)localObject2).getVariableKey()));
      OptimizelyData.markExperimentAsViewedIfNecessary(localOptimizelyExperiment, this.optimizely);
    } while (((OptimizelyVariable)localObject2).getValue() == null);
    this.optimizely.verboseLog("OptimizelyVariables", "Returning value %1$s for variable key %2$s", new Object[] { ((OptimizelyVariable)localObject2).getValue(), paramString });
    return OptimizelyCodec.decode(this.optimizely, (OptimizelyVariable)localObject2);
    label296:
    this.optimizely.verboseLog("OptimizelyVariables", "Returning default value %1$s for variable key %2$s", new Object[] { paramObject, paramString });
    return paramObject;
  }
  
  @NonNull
  public LiveVariable<Float> floatForKey(@NonNull String paramString, float paramFloat, @Nullable LiveVariable.Callback<Float> paramCallback)
  {
    return createVariable(paramString, Float.valueOf(paramFloat), Float.class, paramCallback);
  }
  
  @NonNull
  public LiveVariable<Integer> integerForKey(@NonNull String paramString, int paramInt, @Nullable LiveVariable.Callback<Integer> paramCallback)
  {
    return createVariable(paramString, Integer.valueOf(paramInt), Integer.class, paramCallback);
  }
  
  @NonNull
  public LiveVariable<Point> pointForKey(@NonNull String paramString, @Nullable Point paramPoint, @Nullable LiveVariable.Callback<Point> paramCallback)
  {
    return createVariable(paramString, paramPoint, Point.class, paramCallback);
  }
  
  @NonNull
  public LiveVariable<Rect> rectForKey(@NonNull String paramString, @Nullable Rect paramRect, @Nullable LiveVariable.Callback<Rect> paramCallback)
  {
    return createVariable(paramString, paramRect, Rect.class, paramCallback);
  }
  
  public void resetVariableValues()
  {
    Iterator localIterator = this.registeredVariables.entrySet().iterator();
    while (localIterator.hasNext())
    {
      OptimizelyVariable localOptimizelyVariable = (OptimizelyVariable)((Map.Entry)localIterator.next()).getValue();
      assert (localOptimizelyVariable != null);
      if (localOptimizelyVariable.getDefaultValue() != null) {
        localOptimizelyVariable.setValue(localOptimizelyVariable.getDefaultValue());
      }
    }
    sendAllVariablesToEditor();
  }
  
  public void sendAllVariablesToEditor()
  {
    this.optimizely.verboseLog("OptimizelyVariables", "Sending %1$s", new Object[] { this.registeredVariables.toString() });
    if (!this.registeredVariables.isEmpty())
    {
      this.optimizely.socketBatchBegin();
      Iterator localIterator = this.registeredVariables.values().iterator();
      while (localIterator.hasNext())
      {
        Map localMap = variableToMap((OptimizelyVariable)localIterator.next());
        this.optimizely.sendMap(localMap);
      }
      this.optimizely.socketBatchEnd();
      return;
    }
    sendAppHasNoRegisteredVariables();
  }
  
  void sendAppHasNoRegisteredVariables()
  {
    if ((this.optimizely.isActive()) && (this.optimizely.isEditorEnabled().booleanValue()))
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("action", "noLiveVariables");
      this.optimizely.sendMap(localHashMap);
    }
  }
  
  public void setVariableHandler(@NonNull OptimizelyVariable paramOptimizelyVariable)
  {
    String str = paramOptimizelyVariable.getVariableKey();
    this.optimizely.verboseLog("OptimizelyVariables", "Setting value %1$s for variable key %2$s", new Object[] { paramOptimizelyVariable.getValue(), str });
    if (str != null)
    {
      Object localObject = (OptimizelyVariable)this.registeredVariables.get(str);
      if (localObject != null) {
        paramOptimizelyVariable.setDefaultValue(((OptimizelyVariable)localObject).getDefaultValue());
      }
      this.registeredVariables.put(str, paramOptimizelyVariable);
      if (this.callbacksByVariables.containsKey(str))
      {
        localObject = (LiveVariable.Callback)this.callbacksByVariables.get(str);
        if (localObject != null) {
          ((LiveVariable.Callback)localObject).execute(str, OptimizelyCodec.decode(this.optimizely, paramOptimizelyVariable));
        }
      }
    }
  }
  
  @NonNull
  public LiveVariable<String> stringKorKey(@NonNull String paramString1, @Nullable String paramString2, @Nullable LiveVariable.Callback<String> paramCallback)
  {
    return createVariable(paramString1, paramString2, String.class, paramCallback);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Variable/OptimizelyVariables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
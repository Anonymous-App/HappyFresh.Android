package com.mixpanel.android.mpmetrics;

import android.support.annotation.IntDef;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tweaks
{
  public static final int BOOLEAN_TYPE = 1;
  public static final int DOUBLE_TYPE = 2;
  private static final String LOGTAG = "MixpanelAPI.Tweaks";
  public static final int LONG_TYPE = 3;
  public static final int STRING_TYPE = 4;
  private final List<OnTweakDeclaredListener> mTweakDeclaredListeners = new ArrayList();
  private final Map<String, TweakValue> mTweakValues = new HashMap();
  
  private void declareTweak(String paramString, Object paramObject, int paramInt)
  {
    if (this.mTweakValues.containsKey(paramString)) {
      Log.w("MixpanelAPI.Tweaks", "Attempt to define a tweak \"" + paramString + "\" twice with the same name");
    }
    for (;;)
    {
      return;
      paramObject = new TweakValue(paramInt, paramObject, null, null, paramObject, null);
      this.mTweakValues.put(paramString, paramObject);
      int i = this.mTweakDeclaredListeners.size();
      paramInt = 0;
      while (paramInt < i)
      {
        ((OnTweakDeclaredListener)this.mTweakDeclaredListeners.get(paramInt)).onTweakDeclared();
        paramInt += 1;
      }
    }
  }
  
  private TweakValue getValue(String paramString)
  {
    try
    {
      paramString = (TweakValue)this.mTweakValues.get(paramString);
      return paramString;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void addOnTweakDeclaredListener(OnTweakDeclaredListener paramOnTweakDeclaredListener)
  {
    if (paramOnTweakDeclaredListener == null) {
      try
      {
        throw new NullPointerException("listener cannot be null");
      }
      finally {}
    }
    this.mTweakDeclaredListeners.add(paramOnTweakDeclaredListener);
  }
  
  Tweak<Boolean> booleanTweak(final String paramString, boolean paramBoolean)
  {
    declareTweak(paramString, Boolean.valueOf(paramBoolean), 1);
    new Tweak()
    {
      public Boolean get()
      {
        return Tweaks.this.getValue(paramString).getBooleanValue();
      }
    };
  }
  
  Tweak<Byte> byteTweak(final String paramString, byte paramByte)
  {
    declareTweak(paramString, Byte.valueOf(paramByte), 3);
    new Tweak()
    {
      public Byte get()
      {
        return Byte.valueOf(Tweaks.this.getValue(paramString).getNumberValue().byteValue());
      }
    };
  }
  
  Tweak<Double> doubleTweak(final String paramString, double paramDouble)
  {
    declareTweak(paramString, Double.valueOf(paramDouble), 2);
    new Tweak()
    {
      public Double get()
      {
        return Double.valueOf(Tweaks.this.getValue(paramString).getNumberValue().doubleValue());
      }
    };
  }
  
  Tweak<Float> floatTweak(final String paramString, float paramFloat)
  {
    declareTweak(paramString, Float.valueOf(paramFloat), 2);
    new Tweak()
    {
      public Float get()
      {
        return Float.valueOf(Tweaks.this.getValue(paramString).getNumberValue().floatValue());
      }
    };
  }
  
  public Map<String, TweakValue> getAllValues()
  {
    try
    {
      HashMap localHashMap = new HashMap(this.mTweakValues);
      return localHashMap;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  Tweak<Integer> intTweak(final String paramString, int paramInt)
  {
    declareTweak(paramString, Integer.valueOf(paramInt), 3);
    new Tweak()
    {
      public Integer get()
      {
        return Integer.valueOf(Tweaks.this.getValue(paramString).getNumberValue().intValue());
      }
    };
  }
  
  Tweak<Long> longTweak(final String paramString, long paramLong)
  {
    declareTweak(paramString, Long.valueOf(paramLong), 3);
    new Tweak()
    {
      public Long get()
      {
        return Long.valueOf(Tweaks.this.getValue(paramString).getNumberValue().longValue());
      }
    };
  }
  
  /* Error */
  public void set(String paramString, Object paramObject)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 57	com/mixpanel/android/mpmetrics/Tweaks:mTweakValues	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 77 2 0
    //   12: ifne +36 -> 48
    //   15: ldc 38
    //   17: new 79	java/lang/StringBuilder
    //   20: dup
    //   21: invokespecial 80	java/lang/StringBuilder:<init>	()V
    //   24: ldc -53
    //   26: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: aload_1
    //   30: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: ldc -51
    //   35: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   41: invokestatic 98	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   44: pop
    //   45: aload_0
    //   46: monitorexit
    //   47: return
    //   48: aload_0
    //   49: getfield 57	com/mixpanel/android/mpmetrics/Tweaks:mTweakValues	Ljava/util/Map;
    //   52: aload_1
    //   53: invokeinterface 121 2 0
    //   58: checkcast 28	com/mixpanel/android/mpmetrics/Tweaks$TweakValue
    //   61: aload_2
    //   62: invokevirtual 209	com/mixpanel/android/mpmetrics/Tweaks$TweakValue:updateValue	(Ljava/lang/Object;)Lcom/mixpanel/android/mpmetrics/Tweaks$TweakValue;
    //   65: astore_2
    //   66: aload_0
    //   67: getfield 57	com/mixpanel/android/mpmetrics/Tweaks:mTweakValues	Ljava/util/Map;
    //   70: aload_1
    //   71: aload_2
    //   72: invokeinterface 105 3 0
    //   77: pop
    //   78: goto -33 -> 45
    //   81: astore_1
    //   82: aload_0
    //   83: monitorexit
    //   84: aload_1
    //   85: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	86	0	this	Tweaks
    //   0	86	1	paramString	String
    //   0	86	2	paramObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	45	81	finally
    //   48	78	81	finally
  }
  
  Tweak<Short> shortTweak(final String paramString, short paramShort)
  {
    declareTweak(paramString, Short.valueOf(paramShort), 3);
    new Tweak()
    {
      public Short get()
      {
        return Short.valueOf(Tweaks.this.getValue(paramString).getNumberValue().shortValue());
      }
    };
  }
  
  Tweak<String> stringTweak(final String paramString1, String paramString2)
  {
    declareTweak(paramString1, paramString2, 4);
    new Tweak()
    {
      public String get()
      {
        return Tweaks.this.getValue(paramString1).getStringValue();
      }
    };
  }
  
  public static abstract interface OnTweakDeclaredListener
  {
    public abstract void onTweakDeclared();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  @IntDef({1L, 2L, 3L, 4L})
  private static @interface TweakType {}
  
  public static class TweakValue
  {
    private final Object defaultValue;
    private final Number maximum;
    private final Number minimum;
    public final int type;
    private final Object value;
    
    private TweakValue(int paramInt, Object paramObject1, Number paramNumber1, Number paramNumber2, Object paramObject2)
    {
      this.type = paramInt;
      this.defaultValue = paramObject1;
      this.minimum = paramNumber1;
      this.maximum = paramNumber2;
      this.value = paramObject2;
    }
    
    public Boolean getBooleanValue()
    {
      Object localObject2 = Boolean.valueOf(false);
      Object localObject1 = localObject2;
      if (this.defaultValue != null) {}
      try
      {
        localObject1 = (Boolean)this.defaultValue;
        localObject2 = localObject1;
        if (this.value != null) {}
        try
        {
          localObject2 = (Boolean)this.value;
          return (Boolean)localObject2;
        }
        catch (ClassCastException localClassCastException3)
        {
          return (Boolean)localObject1;
        }
      }
      catch (ClassCastException localClassCastException1)
      {
        for (;;)
        {
          ClassCastException localClassCastException2 = localClassCastException3;
        }
      }
    }
    
    public Number getNumberValue()
    {
      Object localObject2 = Integer.valueOf(0);
      Object localObject1 = localObject2;
      if (this.defaultValue != null) {}
      try
      {
        localObject1 = (Number)this.defaultValue;
        localObject2 = localObject1;
        if (this.value != null) {}
        try
        {
          localObject2 = (Number)this.value;
          return (Number)localObject2;
        }
        catch (ClassCastException localClassCastException3)
        {
          return (Number)localObject1;
        }
      }
      catch (ClassCastException localClassCastException1)
      {
        for (;;)
        {
          ClassCastException localClassCastException2 = localClassCastException3;
        }
      }
    }
    
    public String getStringValue()
    {
      Object localObject = null;
      for (;;)
      {
        try
        {
          str = (String)this.defaultValue;
          localObject = str;
        }
        catch (ClassCastException localClassCastException2)
        {
          String str;
          continue;
        }
        try
        {
          str = (String)this.value;
          return str;
        }
        catch (ClassCastException localClassCastException1)
        {
          return (String)localObject;
        }
      }
    }
    
    public TweakValue updateValue(Object paramObject)
    {
      return new TweakValue(this.type, this.defaultValue, this.minimum, this.maximum, paramObject);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/mpmetrics/Tweaks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
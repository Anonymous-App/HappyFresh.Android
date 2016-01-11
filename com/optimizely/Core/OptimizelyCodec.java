package com.optimizely.Core;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.JSON.OptimizelyVariable;
import com.optimizely.Optimizely;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OptimizelyCodec
{
  public static final String BOOL = "boolean";
  public static final String COLOR = "color";
  public static final String FLOAT = "float";
  public static final String FONT = "font";
  public static final String INTEGER = "integer";
  private static final String LOG_TAG = OptimizelyCodec.class.getSimpleName();
  @NonNull
  private static final List<String> MAP_TYPES = Arrays.asList(new String[] { "color", "point", "rectangle", "font", "NSDictionary" });
  private static final String POINT = "point";
  public static final String RECT = "rectangle";
  public static final String SIZE = "size";
  public static final String STRING = "string";
  @NonNull
  private static final Set<String> colorKeys = new HashSet(Arrays.asList(new String[] { "Alpha", "Red", "Green", "Blue" }));
  @NonNull
  private static final Set<String> fontKeys = new HashSet(Arrays.asList(new String[] { "pointSize", "fontName" }));
  @NonNull
  private static final Set<String> rectKeys = new HashSet(Arrays.asList(new String[] { "X", "Y", "Width", "Height" }));
  
  @NonNull
  public static OptimizelyVariable decode(@NonNull String paramString1, @NonNull String paramString2, @Nullable Object paramObject)
    throws JSONException
  {
    OptimizelyVariable localOptimizelyVariable = new OptimizelyVariable();
    localOptimizelyVariable.setVariableKey(paramString1);
    localOptimizelyVariable.setType(paramString2);
    if (("string".equalsIgnoreCase(paramString2)) || ("boolean".equalsIgnoreCase(paramString2))) {
      localOptimizelyVariable.setValue(paramObject);
    }
    do
    {
      do
      {
        return localOptimizelyVariable;
        if (("integer".equalsIgnoreCase(paramString2)) && ((paramObject instanceof Number)))
        {
          localOptimizelyVariable.setValue(Integer.valueOf(((Number)paramObject).intValue()));
          return localOptimizelyVariable;
        }
        if (("float".equalsIgnoreCase(paramString2)) && ((paramObject instanceof Number)))
        {
          localOptimizelyVariable.setValue(Float.valueOf(((Number)paramObject).floatValue()));
          return localOptimizelyVariable;
        }
      } while (!MAP_TYPES.contains(paramString2));
      if ((paramObject instanceof Map))
      {
        localOptimizelyVariable.setValue(paramObject);
        return localOptimizelyVariable;
      }
    } while (paramObject == null);
    localOptimizelyVariable.setValue(toMap((JSONObject)paramObject));
    return localOptimizelyVariable;
  }
  
  @Nullable
  public static Object decode(@NonNull Optimizely paramOptimizely, @NonNull OptimizelyVariable paramOptimizelyVariable)
  {
    Object localObject1 = paramOptimizelyVariable.getValue();
    if (localObject1 == null) {
      return null;
    }
    if ("color".equalsIgnoreCase(paramOptimizelyVariable.getType())) {
      return Integer.valueOf(toColor(paramOptimizely, (Map)localObject1));
    }
    if ("point".equalsIgnoreCase(paramOptimizelyVariable.getType()))
    {
      paramOptimizelyVariable = (Map)localObject1;
      paramOptimizely = (Number)paramOptimizelyVariable.get("X");
      paramOptimizelyVariable = (Number)paramOptimizelyVariable.get("Y");
      if ((paramOptimizely != null) && (paramOptimizelyVariable != null)) {
        return new Point(paramOptimizely.intValue(), paramOptimizelyVariable.intValue());
      }
      return null;
    }
    if ("rectangle".equalsIgnoreCase(paramOptimizelyVariable.getType()))
    {
      Object localObject2 = (Map)localObject1;
      paramOptimizely = (Number)((Map)localObject2).get("X");
      paramOptimizelyVariable = (Number)((Map)localObject2).get("Y");
      localObject1 = (Number)((Map)localObject2).get("Height");
      localObject2 = (Number)((Map)localObject2).get("Width");
      if ((paramOptimizelyVariable != null) && (paramOptimizely != null) && (localObject1 != null) && (localObject2 != null)) {
        return new Rect(paramOptimizely.intValue(), paramOptimizelyVariable.intValue(), paramOptimizely.intValue() + ((Number)localObject2).intValue(), paramOptimizelyVariable.intValue() + ((Number)localObject1).intValue());
      }
      return null;
    }
    if ("float".equalsIgnoreCase(paramOptimizelyVariable.getType())) {
      return Float.valueOf(((Number)localObject1).floatValue());
    }
    if ("integer".equalsIgnoreCase(paramOptimizelyVariable.getType())) {
      return Integer.valueOf(((Number)localObject1).intValue());
    }
    return localObject1;
  }
  
  public static int decodeGravity(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return -1;
    case 0: 
      return 8388611;
    case 1: 
      return 17;
    }
    return 8388613;
  }
  
  @NonNull
  public static OptimizelyVariable encode(@NonNull String paramString, @Nullable Object paramObject, Class paramClass)
  {
    String str = "";
    Object localObject = paramObject;
    if ((paramClass == Color.class) && ((paramObject instanceof Integer)))
    {
      paramClass = "color";
      localObject = toColorMap(((Integer)paramObject).intValue());
    }
    for (;;)
    {
      paramObject = new OptimizelyVariable();
      ((OptimizelyVariable)paramObject).setVariableKey(paramString);
      ((OptimizelyVariable)paramObject).setType(paramClass);
      ((OptimizelyVariable)paramObject).setValue(localObject);
      ((OptimizelyVariable)paramObject).setDefaultValue(localObject);
      return (OptimizelyVariable)paramObject;
      if ((paramObject instanceof String))
      {
        paramClass = "string";
      }
      else if ((paramObject instanceof Boolean))
      {
        paramClass = "boolean";
      }
      else if ((paramObject instanceof Integer))
      {
        paramClass = "integer";
      }
      else if ((paramObject instanceof Number))
      {
        paramClass = "float";
        localObject = Float.valueOf(((Number)paramObject).floatValue());
      }
      else if ((paramObject instanceof Rect))
      {
        paramClass = "rectangle";
        localObject = toRectMap((Rect)paramObject);
      }
      else
      {
        paramClass = str;
        if ((paramObject instanceof Point))
        {
          paramClass = "point";
          localObject = toPointMap((Point)paramObject);
        }
      }
    }
  }
  
  @NonNull
  public static Map<String, Object> encode(@NonNull Optimizely paramOptimizely, @NonNull Object paramObject)
  {
    Hashtable localHashtable = new Hashtable();
    Object localObject2 = "";
    Object localObject1 = "";
    if ((paramObject instanceof String))
    {
      localObject2 = "string";
      localObject1 = paramObject;
    }
    Object localObject3;
    Object localObject4;
    if ((paramObject instanceof Integer))
    {
      localObject2 = "integer";
      localObject1 = paramObject;
      localObject3 = localObject1;
      localObject4 = localObject2;
      if ((paramObject instanceof Boolean))
      {
        localObject4 = "boolean";
        localObject3 = paramObject;
      }
      localObject1 = localObject3;
      localObject2 = localObject4;
      if ((paramObject instanceof Map))
      {
        if (!isMapAColorObject((Map)paramObject)) {
          break label127;
        }
        localObject2 = "color";
        localObject1 = paramObject;
      }
    }
    for (;;)
    {
      localHashtable.put("type", localObject2);
      localHashtable.put("value", localObject1);
      return localHashtable;
      if (!(paramObject instanceof Number)) {
        break;
      }
      localObject2 = "float";
      localObject1 = paramObject;
      break;
      label127:
      if (isMapARectObject((Map)paramObject))
      {
        localObject2 = "rectangle";
        localObject1 = paramObject;
      }
      else if (isMapAFontObject((Map)paramObject))
      {
        localObject2 = "font";
        localObject1 = paramObject;
      }
      else
      {
        paramOptimizely.verboseLog(LOG_TAG, "Unable to encode unknown map type", new Object[0]);
        localObject1 = localObject3;
        localObject2 = localObject4;
      }
    }
  }
  
  @NonNull
  public static Map<String, Object> encodeDictionary(@NonNull Optimizely paramOptimizely, @NonNull Map<String, Object> paramMap)
  {
    Hashtable localHashtable = new Hashtable();
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (paramMap.containsKey(str)) {
        localHashtable.put(str, encode(paramOptimizely, paramMap.get(str)));
      }
    }
    return localHashtable;
  }
  
  public static int encodeGravity(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return -1;
    case 3: 
    case 7: 
    case 8388611: 
      return 0;
    case 1: 
    case 16: 
    case 17: 
      return 1;
    }
    return 2;
  }
  
  @Nullable
  public static Object fromJson(@Nullable Object paramObject)
    throws JSONException
  {
    Object localObject;
    if (paramObject == JSONObject.NULL) {
      localObject = null;
    }
    do
    {
      return localObject;
      if ((paramObject instanceof JSONObject)) {
        return toMap((JSONObject)paramObject);
      }
      localObject = paramObject;
    } while (!(paramObject instanceof JSONArray));
    return toList((JSONArray)paramObject);
  }
  
  @Nullable
  public static Map getImageValues(@Nullable Object paramObject)
    throws JSONException
  {
    Object localObject2 = null;
    Object localObject1;
    if ((paramObject instanceof JSONObject))
    {
      localObject1 = fromJson(paramObject);
      paramObject = new LinkedList();
      ((Queue)paramObject).add((Map)localObject1);
    }
    for (;;)
    {
      localObject1 = localObject2;
      if (!((Queue)paramObject).isEmpty())
      {
        localObject1 = (Map)((Queue)paramObject).remove();
        if (localObject1 == null) {
          continue;
        }
        if (((Map)localObject1).containsKey("all")) {
          localObject1 = (Map)((Map)localObject1).get("all");
        }
      }
      else
      {
        do
        {
          return (Map)localObject1;
          localObject1 = localObject2;
        } while ((paramObject instanceof String));
        localObject1 = paramObject;
        if (paramObject != null) {
          break;
        }
        return null;
      }
      localObject1 = ((Map)localObject1).values().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        Object localObject3 = ((Iterator)localObject1).next();
        if ((localObject3 instanceof Map)) {
          ((Queue)paramObject).add((Map)localObject3);
        }
      }
    }
  }
  
  private static int getValue(@Nullable Number paramNumber)
  {
    if (paramNumber != null) {
      return (int)(paramNumber.doubleValue() * 255.0D);
    }
    return 0;
  }
  
  private static boolean isMapAColorObject(@NonNull Map<String, ?> paramMap)
  {
    return (paramMap.size() == 4) && (paramMap.keySet().equals(colorKeys));
  }
  
  private static boolean isMapAFontObject(@NonNull Map<String, Integer> paramMap)
  {
    return (paramMap.size() == 2) && (paramMap.keySet().equals(fontKeys));
  }
  
  private static boolean isMapARectObject(@NonNull Map<String, Number> paramMap)
  {
    return (paramMap.size() == 4) && (paramMap.keySet().equals(rectKeys));
  }
  
  private static int newColorFromJSON(@NonNull JSONObject paramJSONObject)
    throws JSONException
  {
    double d1 = paramJSONObject.getDouble("Alpha");
    double d2 = paramJSONObject.getDouble("Red");
    double d3 = paramJSONObject.getDouble("Green");
    double d4 = paramJSONObject.getDouble("Blue");
    return Color.argb(Double.valueOf(d1 * 255.0D).intValue(), Double.valueOf(d2 * 255.0D).intValue(), Double.valueOf(d3 * 255.0D).intValue(), Double.valueOf(d4 * 255.0D).intValue());
  }
  
  public static int toColor(@NonNull Optimizely paramOptimizely, @Nullable Object paramObject)
  {
    int i = 0;
    if ((paramObject instanceof JSONObject)) {}
    while (!(paramObject instanceof Map)) {
      try
      {
        i = newColorFromJSON((JSONObject)paramObject);
        return i;
      }
      catch (JSONException paramObject)
      {
        paramOptimizely.verboseLog(LOG_TAG, "Unable to convert value to color", new Object[] { paramObject });
        return 0;
      }
    }
    return toColor((Map)paramObject);
  }
  
  public static int toColor(@NonNull Map<String, Number> paramMap)
  {
    return Color.argb(getValue((Number)paramMap.get("Alpha")), getValue((Number)paramMap.get("Red")), getValue((Number)paramMap.get("Green")), getValue((Number)paramMap.get("Blue")));
  }
  
  @NonNull
  public static Map<String, Double> toColorMap(int paramInt)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("Alpha", Double.valueOf(Color.alpha(paramInt) / 255.0D));
    localHashMap.put("Red", Double.valueOf(Color.red(paramInt) / 255.0D));
    localHashMap.put("Green", Double.valueOf(Color.green(paramInt) / 255.0D));
    localHashMap.put("Blue", Double.valueOf(Color.blue(paramInt) / 255.0D));
    return localHashMap;
  }
  
  @NonNull
  private static List toList(@NonNull JSONArray paramJSONArray)
    throws JSONException
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramJSONArray.length())
    {
      localArrayList.add(fromJson(paramJSONArray.get(i)));
      i += 1;
    }
    return localArrayList;
  }
  
  @NonNull
  private static Map<String, Object> toMap(@NonNull JSONObject paramJSONObject)
    throws JSONException
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramJSONObject.keys();
    if (localIterator != null) {
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localHashMap.put(str, fromJson(paramJSONObject.get(str)));
      }
    }
    return localHashMap;
  }
  
  @NonNull
  private static Map toPointMap(@NonNull Point paramPoint)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("X", Integer.valueOf(paramPoint.x));
    localHashMap.put("Y", Integer.valueOf(paramPoint.y));
    return localHashMap;
  }
  
  @Nullable
  public static Rect toRect(@NonNull Optimizely paramOptimizely, @Nullable Object paramObject)
  {
    Rect localRect = null;
    if ((paramObject instanceof JSONObject))
    {
      paramObject = (JSONObject)paramObject;
      localRect = new Rect();
    }
    while (!(paramObject instanceof Map)) {
      try
      {
        localRect.left = ((JSONObject)paramObject).getInt("X");
        localRect.top = ((JSONObject)paramObject).getInt("Y");
        localRect.right = (localRect.left + ((JSONObject)paramObject).getInt("Width"));
        localRect.bottom = (localRect.top + ((JSONObject)paramObject).getInt("Height"));
        return localRect;
      }
      catch (JSONException paramObject)
      {
        paramOptimizely.verboseLog(LOG_TAG, "Unable to convert object to rect", new Object[] { paramObject });
        return localRect;
      }
    }
    localRect = new Rect();
    try
    {
      paramObject = (Map)paramObject;
      localRect.left = ((Number)((Map)paramObject).get("X")).intValue();
      localRect.top = ((Number)((Map)paramObject).get("Y")).intValue();
      int i = localRect.left;
      localRect.right = (((Number)((Map)paramObject).get("Width")).intValue() + i);
      i = localRect.top;
      localRect.bottom = (((Number)((Map)paramObject).get("Height")).intValue() + i);
      return localRect;
    }
    catch (ClassCastException paramObject)
    {
      paramOptimizely.verboseLog(LOG_TAG, "Unable to convert object to rect", new Object[] { paramObject });
    }
    return localRect;
  }
  
  @NonNull
  public static Map<String, Number> toRectMap(@NonNull Rect paramRect)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("X", Integer.valueOf(paramRect.left));
    localHashMap.put("Y", Integer.valueOf(paramRect.top));
    localHashMap.put("Height", Integer.valueOf(paramRect.height()));
    localHashMap.put("Width", Integer.valueOf(paramRect.width()));
    return localHashMap;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Core/OptimizelyCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
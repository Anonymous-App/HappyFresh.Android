package com.optimizely.fonts;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.Optimizely;
import com.optimizely.utils.OptimizelyThreadPoolExecutor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class OptimizelyFontsManager
{
  private static final String FONTS_DIRECTORY = "fonts";
  private static final String OPTIMIZELY_FONTS_MANAGER = "OptimizelyFontsManager";
  private List<Map<String, String>> cachedFonts;
  @NonNull
  private final Map<TextView, Typeface> customFontsByViews = new HashMap();
  @NonNull
  private OptimizelyFontAnalyzer fontAnalyzer;
  @NonNull
  private final Map<Typeface, String> fontsByTypeface = new HashMap();
  @NonNull
  private final Optimizely optimizely;
  @NonNull
  private final Map<String, Typeface> typefacesByPath = new HashMap();
  
  public OptimizelyFontsManager(@NonNull Optimizely paramOptimizely, @NonNull OptimizelyFontAnalyzer paramOptimizelyFontAnalyzer)
  {
    this.optimizely = paramOptimizely;
    this.fontAnalyzer = paramOptimizelyFontAnalyzer;
  }
  
  @NonNull
  private List<Map<String, String>> findFonts()
    throws IOException
  {
    Object localObject = findSystemFonts();
    ((Map)localObject).putAll(findLocalFonts(this.optimizely.getCurrentContext().getAssets(), "fonts"));
    ArrayList localArrayList = new ArrayList();
    localObject = ((Map)localObject).entrySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
      if ((localEntry.getValue() != null) && (localEntry.getKey() != null)) {
        localArrayList.add(newFont((String)localEntry.getValue(), (String)localEntry.getKey()));
      }
    }
    return localArrayList;
  }
  
  @NonNull
  private Map<String, String> findLocalFonts(@NonNull AssetManager paramAssetManager, @NonNull String paramString)
    throws IOException
  {
    paramAssetManager = paramAssetManager.list(paramString);
    HashMap localHashMap = new HashMap();
    int i = 0;
    for (;;)
    {
      if (i < paramAssetManager.length) {
        try
        {
          findLocalFontDetails(localHashMap, getPath(paramString, paramAssetManager[i]));
          i += 1;
        }
        catch (Exception localException)
        {
          for (;;)
          {
            this.optimizely.verboseLog(true, "OptimizelyFontsManager", "Failed to load/parse font", new Object[] { localException });
          }
        }
      }
    }
    return localHashMap;
  }
  
  @NonNull
  private String getPath(@NonNull String paramString1, @NonNull String paramString2)
  {
    return paramString1 + "/" + paramString2;
  }
  
  @NonNull
  private Map<String, String> newFont(@NonNull String paramString1, @NonNull String paramString2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("fontLabel", paramString1);
    localHashMap.put("fontName", paramString2);
    return localHashMap;
  }
  
  private void systemFont(@NonNull Map<String, String> paramMap, String paramString1, String paramString2, int paramInt)
  {
    paramString2 = Typeface.create(paramString2, paramInt);
    if (this.optimizely.isEditorEnabled().booleanValue()) {
      this.fontsByTypeface.put(paramString2, paramString1);
    }
    this.typefacesByPath.put(paramString1, paramString2);
    paramMap.put(paramString1, paramString1);
  }
  
  void findLocalFontDetails(@NonNull Map<String, String> paramMap, @NonNull String paramString)
  {
    if ((paramString != null) && (paramString.toLowerCase().endsWith(".ttf")))
    {
      String str = this.fontAnalyzer.findFontName(paramString);
      Typeface localTypeface = Typeface.createFromAsset(this.optimizely.getCurrentContext().getAssets(), paramString);
      paramMap.put(paramString, str);
      this.fontsByTypeface.put(localTypeface, str);
      if (this.optimizely.isEditorEnabled().booleanValue()) {
        this.typefacesByPath.put(paramString, localTypeface);
      }
    }
  }
  
  @NonNull
  Map<String, String> findSystemFonts()
  {
    HashMap localHashMap = new HashMap();
    systemFont(localHashMap, "Sans", "sans", 0);
    systemFont(localHashMap, "Sans-Bold", "sans", 1);
    systemFont(localHashMap, "Sans-Italic", "sans", 2);
    systemFont(localHashMap, "Sans-Bold-Italic", "sans", 3);
    systemFont(localHashMap, "SanSerif", "sans-serif", 0);
    systemFont(localHashMap, "SanSerif-Bold", "sans-serif", 1);
    systemFont(localHashMap, "SanSerif-Italic", "sans-serif", 2);
    systemFont(localHashMap, "SanSerif-Bold-Italic", "sans-serif", 3);
    systemFont(localHashMap, "Serif", "serif", 0);
    systemFont(localHashMap, "Serif-Bold", "serif", 1);
    systemFont(localHashMap, "Serif-Italic", "serif", 2);
    systemFont(localHashMap, "Serif-Bold-Italic", "serif", 3);
    systemFont(localHashMap, "Monospace", "monospace", 0);
    localHashMap.put("Unsupported-Custom", "Unsupported-Custom");
    return localHashMap;
  }
  
  @NonNull
  public Map<String, Object> getFont(@Nullable TextView paramTextView)
  {
    if (paramTextView == null) {
      return Collections.emptyMap();
    }
    Typeface localTypeface = paramTextView.getTypeface();
    HashMap localHashMap = new HashMap();
    localHashMap.put("pointSize", Integer.valueOf((int)(paramTextView.getTextSize() / OptimizelyUtils.getDensity(this.optimizely.getCurrentContext()))));
    if ((localTypeface != null) && (this.fontsByTypeface.containsKey(localTypeface)))
    {
      localHashMap.put("fontName", this.fontsByTypeface.get(localTypeface));
      return localHashMap;
    }
    localHashMap.put("fontName", "Unsupported-Custom");
    this.customFontsByViews.put(paramTextView, localTypeface);
    return localHashMap;
  }
  
  public void sendFonts()
  {
    if (this.optimizely.isEditorEnabled().booleanValue())
    {
      if (this.cachedFonts == null) {
        new CacheFontsTask(null).executeOnExecutor(OptimizelyThreadPoolExecutor.instance(), new Void[0]);
      }
    }
    else {
      return;
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("action", "deviceFonts");
    localHashMap.put("fonts", this.cachedFonts);
    this.optimizely.sendMap(localHashMap);
  }
  
  public void setFont(@NonNull TextView paramTextView, @NonNull Map paramMap)
  {
    String str = (String)paramMap.get("fontName");
    if ((str != null) && (!str.equals("Unsupported-Custom")))
    {
      if (!this.typefacesByPath.containsKey(str))
      {
        Typeface localTypeface = Typeface.createFromAsset(this.optimizely.getCurrentContext().getAssets(), str);
        this.typefacesByPath.put(str, localTypeface);
      }
      paramTextView.setTypeface((Typeface)this.typefacesByPath.get(str), 0);
    }
    for (;;)
    {
      paramTextView.setTextSize(Float.valueOf((String)paramMap.get("pointSize")).floatValue());
      return;
      if (this.optimizely.isEditorEnabled().booleanValue()) {
        paramTextView.setTypeface((Typeface)this.customFontsByViews.get(paramTextView));
      }
    }
  }
  
  private class CacheFontsTask
    extends AsyncTask<Void, Void, Void>
  {
    private CacheFontsTask() {}
    
    @Nullable
    protected Void doInBackground(Void... paramVarArgs)
    {
      try
      {
        OptimizelyFontsManager.access$102(OptimizelyFontsManager.this, OptimizelyFontsManager.this.findFonts());
        OptimizelyFontsManager.this.sendFonts();
        return null;
      }
      catch (IOException paramVarArgs)
      {
        for (;;)
        {
          OptimizelyFontsManager.this.optimizely.verboseLog(true, "OptimizelyFontsManager", "Failed to load/parse fonts", new Object[] { paramVarArgs });
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/fonts/OptimizelyFontsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
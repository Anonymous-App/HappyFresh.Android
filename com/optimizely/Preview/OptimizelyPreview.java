package com.optimizely.Preview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.optimizely.Core.AppRestarter;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.JSON.OptimizelyVariation;
import com.optimizely.Optimizely;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OptimizelyPreview
{
  public static final String EDIT_FLAG_PREFERENCES = "OptimizelyEditMode";
  private static final String EXPERIMENT_DESCRIPTION = "experiment_description";
  private static final String EXPERIMENT_ID = "experiment_id";
  private static final String EXPERIMENT_TO_VARIATION_MAP = "experimentToVariationMap";
  private static final String PREVIEW_COMPONENT = "OptimizelyPreview";
  private static final String PREVIEW_DATA_FILE = "Optimizely_preview_config";
  public static final String PREVIEW_FLAG_PREFERENCES = "OptimizelyPreviewMode";
  private static final String VARIATION_DESCRIPTION = "variation_description";
  private static final String VARIATION_ID = "variation_id";
  @NonNull
  private final Map<String, String> mExperimentsToVariations = new HashMap();
  @NonNull
  private final HashMap<String, String> mHumanReadableExperimentsToVariations = new HashMap();
  private final Optimizely mOptimizely;
  private String previewJson;
  private final AppRestarter restarter;
  
  public OptimizelyPreview(@NonNull Optimizely paramOptimizely)
  {
    this.mOptimizely = paramOptimizely;
    this.restarter = new AppRestarter(paramOptimizely, "OptimizelyPreview");
  }
  
  private boolean clearPreviewDataFile()
  {
    File localFile = getPreviewDataFile();
    return (!localFile.exists()) || (localFile.delete());
  }
  
  @NonNull
  private File getPreviewDataFile()
  {
    return new File(this.mOptimizely.getCurrentContext().getFilesDir(), "Optimizely_preview_config");
  }
  
  private void saveOptimizelyModePreferences(String paramString)
  {
    Context localContext = this.mOptimizely.getCurrentContext().getApplicationContext();
    if (localContext != null)
    {
      this.mOptimizely.getUserDefaults(localContext).edit().putBoolean(paramString, true).commit();
      Log.v("OptimizelyPreview", String.format("Restarting in %s mode.", new Object[] { paramString }));
    }
  }
  
  public boolean canActivateExperiment(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    return this.mExperimentsToVariations.containsKey(paramOptimizelyExperiment.getExperimentId());
  }
  
  @Nullable
  public String createPreviewData(@NonNull Map<OptimizelyExperiment, OptimizelyVariation> paramMap)
  {
    Object localObject;
    JSONArray localJSONArray;
    for (;;)
    {
      try
      {
        if (this.previewJson != null)
        {
          localObject = new JSONObject(this.previewJson).getJSONObject("dataFile");
          localJSONArray = new JSONArray();
          paramMap = paramMap.entrySet().iterator();
          if (!paramMap.hasNext()) {
            break;
          }
          Map.Entry localEntry = (Map.Entry)paramMap.next();
          if ((localEntry.getKey() == null) || (localEntry.getValue() == null)) {
            continue;
          }
          JSONObject localJSONObject = new JSONObject();
          localJSONObject.put("experiment_id", ((OptimizelyExperiment)localEntry.getKey()).getExperimentId());
          localJSONObject.put("experiment_description", ((OptimizelyExperiment)localEntry.getKey()).getDescription());
          localJSONObject.put("variation_id", ((OptimizelyVariation)localEntry.getValue()).getVariationId());
          localJSONObject.put("variation_description", ((OptimizelyVariation)localEntry.getValue()).getDescription());
          localJSONArray.put(localJSONObject);
          continue;
        }
        localObject = OptimizelyUtils.readDataFile(this.mOptimizely.getOptimizelyData().getDataFile(), this.mOptimizely);
      }
      catch (JSONException paramMap)
      {
        this.mOptimizely.verboseLog(true, "OptimizelyPreview", "Error creating preview data file to handle URL launch: %s", new Object[] { paramMap.getLocalizedMessage() });
        return null;
      }
      if (localObject == null)
      {
        this.mOptimizely.verboseLog(true, "OptimizelyPreview", "Could not create preview data from existing data", new Object[0]);
        return null;
      }
      localObject = new JSONObject((String)localObject);
    }
    paramMap = new JSONObject();
    paramMap.put("dataFile", localObject);
    paramMap.put("experimentToVariationMap", localJSONArray);
    paramMap.put("debugLogging", true);
    paramMap = paramMap.toString();
    return paramMap;
  }
  
  protected boolean deleteOptimizelyDataFile()
  {
    return this.mOptimizely.getOptimizelyData().getDataFile().delete();
  }
  
  @NonNull
  public HashMap<String, String> getHumanReadableExperimentsToVariations()
  {
    return this.mHumanReadableExperimentsToVariations;
  }
  
  @Nullable
  public String getVariationForExperiment(@Nullable String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return (String)this.mExperimentsToVariations.get(paramString);
  }
  
  /* Error */
  public boolean loadPreviewData()
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_0
    //   2: invokespecial 73	com/optimizely/Preview/OptimizelyPreview:getPreviewDataFile	()Ljava/io/File;
    //   5: aload_0
    //   6: getfield 58	com/optimizely/Preview/OptimizelyPreview:mOptimizely	Lcom/optimizely/Optimizely;
    //   9: invokestatic 236	com/optimizely/Core/OptimizelyUtils:readDataFile	(Ljava/io/File;Lcom/optimizely/Optimizely;)Ljava/lang/String;
    //   12: putfield 155	com/optimizely/Preview/OptimizelyPreview:previewJson	Ljava/lang/String;
    //   15: aload_0
    //   16: getfield 155	com/optimizely/Preview/OptimizelyPreview:previewJson	Ljava/lang/String;
    //   19: ifnonnull +5 -> 24
    //   22: iconst_0
    //   23: ireturn
    //   24: aload_0
    //   25: invokespecial 261	com/optimizely/Preview/OptimizelyPreview:clearPreviewDataFile	()Z
    //   28: pop
    //   29: new 157	org/json/JSONObject
    //   32: dup
    //   33: aload_0
    //   34: getfield 155	com/optimizely/Preview/OptimizelyPreview:previewJson	Ljava/lang/String;
    //   37: invokespecial 159	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   40: astore 4
    //   42: aload 4
    //   44: ldc 17
    //   46: invokevirtual 265	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   49: astore 5
    //   51: aload 5
    //   53: ifnonnull +50 -> 103
    //   56: aload_0
    //   57: getfield 58	com/optimizely/Preview/OptimizelyPreview:mOptimizely	Lcom/optimizely/Optimizely;
    //   60: iconst_1
    //   61: ldc 20
    //   63: ldc_w 267
    //   66: iconst_0
    //   67: anewarray 4	java/lang/Object
    //   70: invokevirtual 221	com/optimizely/Optimizely:verboseLog	(ZLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   73: iconst_0
    //   74: ireturn
    //   75: astore 4
    //   77: aload_0
    //   78: getfield 58	com/optimizely/Preview/OptimizelyPreview:mOptimizely	Lcom/optimizely/Optimizely;
    //   81: iconst_1
    //   82: ldc 20
    //   84: ldc_w 269
    //   87: iconst_1
    //   88: anewarray 4	java/lang/Object
    //   91: dup
    //   92: iconst_0
    //   93: aload_0
    //   94: getfield 155	com/optimizely/Preview/OptimizelyPreview:previewJson	Ljava/lang/String;
    //   97: aastore
    //   98: invokevirtual 221	com/optimizely/Optimizely:verboseLog	(ZLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   101: iconst_0
    //   102: ireturn
    //   103: iconst_0
    //   104: istore_1
    //   105: iload_1
    //   106: aload 5
    //   108: invokevirtual 273	org/json/JSONArray:length	()I
    //   111: if_icmpge +105 -> 216
    //   114: aload 5
    //   116: iload_1
    //   117: invokevirtual 276	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   120: astore 9
    //   122: aload 9
    //   124: ifnonnull +6 -> 130
    //   127: goto +160 -> 287
    //   130: aload 9
    //   132: ldc 14
    //   134: invokevirtual 279	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   137: astore 6
    //   139: aload 9
    //   141: ldc 32
    //   143: invokevirtual 279	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   146: astore 7
    //   148: aload 9
    //   150: ldc 11
    //   152: invokevirtual 279	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   155: astore 8
    //   157: aload 9
    //   159: ldc 29
    //   161: invokevirtual 279	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   164: astore 9
    //   166: aload_0
    //   167: getfield 56	com/optimizely/Preview/OptimizelyPreview:mHumanReadableExperimentsToVariations	Ljava/util/HashMap;
    //   170: aload 8
    //   172: aload 9
    //   174: invokevirtual 282	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   177: pop
    //   178: aload_0
    //   179: getfield 54	com/optimizely/Preview/OptimizelyPreview:mExperimentsToVariations	Ljava/util/Map;
    //   182: aload 6
    //   184: aload 7
    //   186: invokeinterface 283 3 0
    //   191: pop
    //   192: goto +95 -> 287
    //   195: astore 4
    //   197: aload_0
    //   198: getfield 58	com/optimizely/Preview/OptimizelyPreview:mOptimizely	Lcom/optimizely/Optimizely;
    //   201: iconst_1
    //   202: ldc 20
    //   204: ldc_w 267
    //   207: iconst_0
    //   208: anewarray 4	java/lang/Object
    //   211: invokevirtual 221	com/optimizely/Optimizely:verboseLog	(ZLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   214: iconst_0
    //   215: ireturn
    //   216: iconst_0
    //   217: istore_2
    //   218: aload 4
    //   220: ldc -16
    //   222: invokevirtual 287	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
    //   225: istore_3
    //   226: iload_3
    //   227: istore_2
    //   228: iload_2
    //   229: invokestatic 291	com/optimizely/Optimizely:setVerboseLogging	(Z)V
    //   232: aload 4
    //   234: ldc -95
    //   236: invokevirtual 165	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   239: astore 4
    //   241: aload 4
    //   243: ifnonnull +5 -> 248
    //   246: iconst_0
    //   247: ireturn
    //   248: aload 4
    //   250: invokevirtual 246	org/json/JSONObject:toString	()Ljava/lang/String;
    //   253: astore 4
    //   255: aload 4
    //   257: ifnonnull +5 -> 262
    //   260: iconst_0
    //   261: ireturn
    //   262: aload_0
    //   263: getfield 58	com/optimizely/Preview/OptimizelyPreview:mOptimizely	Lcom/optimizely/Optimizely;
    //   266: invokevirtual 225	com/optimizely/Optimizely:getOptimizelyData	()Lcom/optimizely/Core/OptimizelyData;
    //   269: aload 4
    //   271: invokevirtual 294	com/optimizely/Core/OptimizelyData:writeDataFile	(Ljava/lang/String;)Z
    //   274: pop
    //   275: iconst_1
    //   276: ireturn
    //   277: astore 4
    //   279: goto -4 -> 275
    //   282: astore 5
    //   284: goto -56 -> 228
    //   287: iload_1
    //   288: iconst_1
    //   289: iadd
    //   290: istore_1
    //   291: goto -186 -> 105
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	294	0	this	OptimizelyPreview
    //   104	187	1	i	int
    //   217	12	2	bool1	boolean
    //   225	2	3	bool2	boolean
    //   40	3	4	localJSONObject	JSONObject
    //   75	1	4	localJSONException1	JSONException
    //   195	38	4	localJSONException2	JSONException
    //   239	31	4	localObject1	Object
    //   277	1	4	localJSONException3	JSONException
    //   49	66	5	localJSONArray	JSONArray
    //   282	1	5	localJSONException4	JSONException
    //   137	46	6	str1	String
    //   146	39	7	str2	String
    //   155	16	8	str3	String
    //   120	53	9	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   29	42	75	org/json/JSONException
    //   42	51	195	org/json/JSONException
    //   56	73	195	org/json/JSONException
    //   105	122	195	org/json/JSONException
    //   130	192	195	org/json/JSONException
    //   232	241	277	org/json/JSONException
    //   248	255	277	org/json/JSONException
    //   262	275	277	org/json/JSONException
    //   218	226	282	org/json/JSONException
  }
  
  public void restartInEditMode()
  {
    if ((!this.mOptimizely.isActive()) || (this.mOptimizely.isEditorEnabled().booleanValue())) {
      return;
    }
    saveOptimizelyModePreferences("OptimizelyEditMode");
    this.restarter.restart();
  }
  
  public void restartInNormalMode()
  {
    this.restarter.restart();
  }
  
  public void restartInPreviewMode()
  {
    saveOptimizelyModePreferences("OptimizelyPreviewMode");
    this.restarter.restart();
  }
  
  public boolean savePreviewData(@Nullable String paramString)
  {
    if (paramString == null) {
      return false;
    }
    File localFile = getPreviewDataFile();
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
      localFileOutputStream.write(paramString.getBytes());
      localFileOutputStream.close();
      return true;
    }
    catch (FileNotFoundException paramString)
    {
      this.mOptimizely.verboseLog(true, "OptimizelyPreview", "Writing data file to (%1$s) failed: %2$s", new Object[] { localFile.getPath(), paramString.getLocalizedMessage() });
      return false;
    }
    catch (IOException paramString)
    {
      this.mOptimizely.verboseLog(true, "OptimizelyPreview", "Writing data file to (%1$s) failed: %2$s", new Object[] { localFile.getPath(), paramString.getLocalizedMessage() });
    }
    return false;
  }
  
  public void stopPreviewMode()
  {
    deleteOptimizelyDataFile();
    this.restarter.restart();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Preview/OptimizelyPreview.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
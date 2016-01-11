package com.optimizely.Preview;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.JSON.OptimizelyVariation;
import com.optimizely.Optimizely;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ShortLinkHandler
{
  private static final String LINK_HANDLER_COMPONENT = "LinkHandler";
  
  @Nullable
  static OptimizelyVariation getVariationById(@NonNull OptimizelyExperiment paramOptimizelyExperiment, @NonNull String paramString)
  {
    paramOptimizelyExperiment = paramOptimizelyExperiment.getVariations();
    if (paramOptimizelyExperiment != null)
    {
      paramOptimizelyExperiment = paramOptimizelyExperiment.iterator();
      while (paramOptimizelyExperiment.hasNext())
      {
        OptimizelyVariation localOptimizelyVariation = (OptimizelyVariation)paramOptimizelyExperiment.next();
        if ((localOptimizelyVariation != null) && (paramString.equals(localOptimizelyVariation.getVariationId()))) {
          return localOptimizelyVariation;
        }
      }
    }
    return null;
  }
  
  public static void handleStartActivityIntent(@Nullable Intent paramIntent, @NonNull Optimizely paramOptimizely)
  {
    if (paramIntent == null) {}
    OptimizelyPreview localOptimizelyPreview;
    do
    {
      do
      {
        do
        {
          return;
          paramIntent = paramIntent.getData();
        } while (paramIntent == null);
      } while (!String.format("optly%s", new Object[] { paramOptimizely.getProjectId() }).equals(paramIntent.getScheme()));
      localOptimizelyPreview = paramOptimizely.getPreviewManager();
      if ("edit".equals(paramIntent.getHost()))
      {
        localOptimizelyPreview.restartInEditMode();
        return;
      }
    } while (!"preview".equals(paramIntent.getHost()));
    setupPreviewMode(paramIntent, paramOptimizely);
    localOptimizelyPreview.restartInPreviewMode();
  }
  
  static void setupPreviewMode(@NonNull Uri paramUri, @NonNull Optimizely paramOptimizely)
  {
    HashMap localHashMap = new HashMap();
    Map localMap = paramOptimizely.getOptimizelyData().getExperimentsById();
    String str = paramUri.getLastPathSegment();
    if (str == null) {
      paramOptimizely.verboseLog(true, "LinkHandler", "Malformed preview URI: %s", new Object[] { paramUri.toString() });
    }
    do
    {
      return;
      paramUri = str.split("&");
      int j = paramUri.length;
      int i = 0;
      if (i < j)
      {
        Object localObject = paramUri[i].split("=");
        str = localObject[0];
        localObject = localObject[1];
        OptimizelyExperiment localOptimizelyExperiment = (OptimizelyExperiment)localMap.get(str);
        if (localObject == null) {
          paramOptimizely.verboseLog(true, "LinkHandler", "No variation specified in URI for experiment with id %s", new Object[] { str });
        }
        for (;;)
        {
          i += 1;
          break;
          if (localOptimizelyExperiment != null)
          {
            OptimizelyVariation localOptimizelyVariation = getVariationById(localOptimizelyExperiment, (String)localObject);
            if (localOptimizelyVariation == null) {
              paramOptimizely.verboseLog(true, "LinkHandler", "No variation with id %s found in experiment with id %s", new Object[] { localObject, str });
            } else {
              localHashMap.put(localOptimizelyExperiment, localOptimizelyVariation);
            }
          }
          else
          {
            paramOptimizely.verboseLog(true, "LinkHandler", "Couldn't find an experiment for id %s", new Object[] { str });
          }
        }
      }
      paramUri = paramOptimizely.getPreviewManager();
      paramOptimizely = paramUri.createPreviewData(localHashMap);
    } while (paramOptimizely == null);
    paramUri.savePreviewData(paramOptimizely);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Preview/ShortLinkHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.optimizely.Assets;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import com.optimizely.Network.OptimizelyNetworkUtil;
import com.optimizely.Optimizely;
import com.optimizely.View.idmanager.OptimizelyIdManager;
import com.optimizely.utils.OptimizelyThreadPoolExecutor;
import com.squareup.okhttp.OkHttpClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OptimizelyAssets
{
  @NonNull
  private static final Set<String> ALLOWED_EXTENSIONS = new HashSet();
  private static final String BASE_PATH = "";
  private static final int NETWORK_TIMEOUT = 1000;
  private static final String OPTIMIZELY_ASSETS = "/optimizelyAssets/";
  private static final String OPTIMIZELY_ASSETS_COMPONENT = "OptimizelyAssets";
  private static final String OPTIMIZELY_ASSETS_DRAWABLE_KEY = "@drawable/";
  private static final String OPTIMIZELY_DRAWABLE_CLASS_NAME = "R$drawable";
  private static final String OPTIMIZELY_DRAWABLE_TYPE = "drawable";
  @NonNull
  private final Map<String, String> assetNamesByView = new HashMap();
  private List<String> assets;
  @NonNull
  final List<String> cachedRemoteAssets = new ArrayList();
  @NonNull
  private final OkHttpClient httpClient;
  @NonNull
  private final Optimizely optimizely;
  private File optimizelyFilesDir;
  @NonNull
  private final OptimizelyNetworkUtil<byte[]> optimizelyNetworkUtil;
  
  static
  {
    ALLOWED_EXTENSIONS.add("png");
    ALLOWED_EXTENSIONS.add("jpg");
    ALLOWED_EXTENSIONS.add("gif");
  }
  
  public OptimizelyAssets(@NonNull Optimizely paramOptimizely, @NonNull OkHttpClient paramOkHttpClient)
  {
    this.optimizely = paramOptimizely;
    this.httpClient = paramOkHttpClient;
    this.optimizelyNetworkUtil = new OptimizelyNetworkUtil(paramOptimizely, 1000, OptimizelyNetworkUtil.TRANSFORM_TO_BYTE_ARRAY);
    new CacheRemoteAssetsTask(null).executeOnExecutor(OptimizelyThreadPoolExecutor.instance(), new Void[0]);
  }
  
  private void applyLocalAsset(@NonNull ImageView paramImageView, @NonNull String paramString)
    throws IOException
  {
    if (paramString.contains("@drawable/"))
    {
      paramString = paramString.replace("@drawable/", "");
      int i = paramImageView.getResources().getIdentifier(paramString, "drawable", this.optimizely.getCurrentContext().getPackageName());
      paramImageView.setImageDrawable(paramImageView.getResources().getDrawable(i));
      return;
    }
    paramImageView.setImageBitmap(BitmapFactory.decodeStream(paramImageView.getResources().getAssets().open(paramString)));
  }
  
  private void applyRemoteAsset(@NonNull ImageView paramImageView, String paramString1, @NonNull String paramString2)
    throws IOException
  {
    if (!this.cachedRemoteAssets.contains(paramString2))
    {
      new DownloadAssetTask(new WeakReference(paramImageView)).execute(new String[] { paramString1, paramString2 });
      return;
    }
    paramImageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(getOptimizelyAssetsPath(paramString2))));
  }
  
  @NonNull
  private Map<String, Object> createAssetMessages()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("action", "registerAssets");
    localHashMap.put("assets", this.assets);
    return localHashMap;
  }
  
  @NonNull
  private List<String> findAllLocalAssets(@NonNull AssetManager paramAssetManager, @NonNull String paramString)
    throws IOException
  {
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString = paramAssetManager.list(paramString);
    Object localObject = this.optimizely.getCurrentContext();
    if ((arrayOfString.length == 0) && (ALLOWED_EXTENSIONS.contains(getExtension(paramString)))) {
      localArrayList.add(paramString);
    }
    for (;;)
    {
      return localArrayList;
      localObject = new File("/data/data/" + ((Context)localObject).getPackageCodePath() + "/" + paramString);
      if (!((File)localObject).exists()) {
        ((File)localObject).mkdir();
      }
      int j = arrayOfString.length;
      int i = 0;
      while (i < j)
      {
        localArrayList.addAll(findAllLocalAssets(paramAssetManager, getPath(paramString, arrayOfString[i])));
        i += 1;
      }
    }
  }
  
  @NonNull
  private List<String> getDrawableResources()
  {
    localArrayList = new ArrayList();
    if (this.optimizely.isActive())
    {
      Object localObject1 = this.optimizely.getCurrentContext().getPackageName();
      try
      {
        localObject1 = Class.forName((String)localObject1 + "." + "R$drawable");
        if (localObject1 != null)
        {
          localObject1 = ((Class)localObject1).getFields();
          if (localObject1 != null)
          {
            int j = localObject1.length;
            int i = 0;
            while (i < j)
            {
              Object localObject2 = localObject1[i];
              localArrayList.add("@drawable/" + ((Field)localObject2).getName());
              i += 1;
            }
          }
        }
        return localArrayList;
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        this.optimizely.verboseLog(true, "OptimizelyAssets", "Failed to retrieve drawable assets to send to editor: %1$s", new Object[] { localClassNotFoundException.getLocalizedMessage() });
      }
    }
  }
  
  static String getExtension(@Nullable String paramString)
  {
    if ((paramString != null) && (paramString.indexOf('.') != -1)) {
      return paramString.substring(paramString.lastIndexOf(".") + 1, paramString.length());
    }
    return "";
  }
  
  @NonNull
  private File getOptimizelyAssetsPath(@NonNull String paramString)
  {
    paramString = "/optimizelyAssets/".concat(paramString);
    if (this.optimizelyFilesDir == null) {
      this.optimizelyFilesDir = this.optimizely.getCurrentContext().getFilesDir();
    }
    return new File(this.optimizelyFilesDir, paramString);
  }
  
  @NonNull
  private String getPath(@NonNull String paramString1, @NonNull String paramString2)
  {
    if (paramString1.equals("")) {
      return paramString2;
    }
    return paramString1 + "/" + paramString2;
  }
  
  @NonNull
  public String getAssetName(View paramView)
  {
    paramView = this.optimizely.getIdManager().getOptimizelyId(paramView);
    if (this.assetNamesByView.containsKey(paramView)) {
      return (String)this.assetNamesByView.get(paramView);
    }
    return "";
  }
  
  public boolean isAssetCached(@Nullable Map<String, Object> paramMap)
  {
    if ((paramMap != null) && (paramMap.containsKey("url")) && (paramMap.containsKey("filename")))
    {
      paramMap = (String)paramMap.get("filename");
      if (!this.cachedRemoteAssets.contains(paramMap)) {
        return false;
      }
    }
    return true;
  }
  
  public void prepareAsset(@Nullable Map<String, Object> paramMap)
  {
    if ((paramMap != null) && (paramMap.containsKey("url")) && (paramMap.containsKey("filename")))
    {
      String str = (String)paramMap.get("url");
      paramMap = (String)paramMap.get("filename");
      if (!this.cachedRemoteAssets.contains(paramMap)) {
        new DownloadAssetTask(null).executeOnExecutor(OptimizelyThreadPoolExecutor.instance(), new String[] { str, paramMap });
      }
    }
  }
  
  @Nullable
  public AsyncTask sendToEditor()
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (this.optimizely.isEditorEnabled().booleanValue())
    {
      localObject1 = localObject2;
      if (this.optimizely.isActive()) {
        localObject1 = new SendLocalAssetsTask(null).executeOnExecutor(OptimizelyThreadPoolExecutor.instance(), new Void[0]);
      }
    }
    return (AsyncTask)localObject1;
  }
  
  public void setAssetProperty(@NonNull ImageView paramImageView, @NonNull Map paramMap)
  {
    OptimizelyIdManager localOptimizelyIdManager = this.optimizely.getIdManager();
    try
    {
      if (paramMap.containsKey("url"))
      {
        String str = (String)paramMap.get("url");
        paramMap = (String)paramMap.get("filename");
        if ((str == null) || (paramMap == null)) {
          return;
        }
        applyRemoteAsset(paramImageView, str, paramMap);
        this.assetNamesByView.put(localOptimizelyIdManager.getOptimizelyId(paramImageView), paramMap);
        return;
      }
      paramMap = (String)paramMap.get("filename");
      if (paramMap != null)
      {
        applyLocalAsset(paramImageView, paramMap);
        this.assetNamesByView.put(localOptimizelyIdManager.getOptimizelyId(paramImageView), paramMap);
        return;
      }
    }
    catch (Exception paramImageView)
    {
      this.optimizely.verboseLog(true, "OptimizelyAssets", "Failed to swap asset", new Object[] { paramImageView });
      return;
    }
    this.optimizely.verboseLog(true, "OptimizelyAssets", "No filename specified", new Object[0]);
  }
  
  private class CacheRemoteAssetsTask
    extends AsyncTask<Void, Void, Void>
  {
    private CacheRemoteAssetsTask() {}
    
    @Nullable
    protected Void doInBackground(Void... paramVarArgs)
    {
      paramVarArgs = OptimizelyAssets.this.getOptimizelyAssetsPath("");
      if (!paramVarArgs.exists()) {
        paramVarArgs.mkdir();
      }
      paramVarArgs = paramVarArgs.listFiles();
      if (paramVarArgs != null)
      {
        int j = paramVarArgs.length;
        int i = 0;
        while (i < j)
        {
          Void localVoid = paramVarArgs[i];
          OptimizelyAssets.this.cachedRemoteAssets.add(localVoid.getName());
          i += 1;
        }
      }
      OptimizelyAssets.this.sendToEditor();
      return null;
    }
  }
  
  private class DownloadAssetTask
    extends AsyncTask<String, Void, String>
  {
    @Nullable
    private final WeakReference<ImageView> imageViewReference;
    
    public DownloadAssetTask()
    {
      WeakReference localWeakReference;
      this.imageViewReference = localWeakReference;
    }
    
    private void saveFileInLocalDirectory(@NonNull String paramString1, @NonNull String paramString2)
      throws Exception
    {
      paramString1 = OptimizelyAssets.this.optimizelyNetworkUtil.downloadFromUrl(OptimizelyAssets.this.httpClient, paramString1);
      if (paramString1.first != null)
      {
        paramString2 = new FileOutputStream(OptimizelyAssets.this.getOptimizelyAssetsPath(paramString2));
        paramString2.write((byte[])paramString1.first, 0, ((byte[])paramString1.first).length);
        paramString2.flush();
        paramString2.close();
      }
    }
    
    @NonNull
    protected String doInBackground(@NonNull String... paramVarArgs)
    {
      String str = paramVarArgs[0];
      paramVarArgs = paramVarArgs[1];
      if ((str != null) && (paramVarArgs != null)) {}
      try
      {
        saveFileInLocalDirectory(str, paramVarArgs);
      }
      catch (Exception localException)
      {
        OptimizelyAssets.this.optimizely.verboseLog(true, "OptimizelyAssets", "Exception while trying to update image view with exception %s", new Object[] { localException.getLocalizedMessage() });
      }
      OptimizelyAssets.this.optimizely.verboseLog(true, "OptimizelyAssets", "Expected (urldisplay, filename), but got (%s, %s", new Object[] { str, paramVarArgs });
      while (paramVarArgs == null) {
        return "";
      }
      return paramVarArgs;
    }
    
    protected void onPostExecute(@NonNull String paramString)
    {
      File localFile;
      if (OptimizelyAssets.this.optimizely.isEditorEnabled().booleanValue()) {
        localFile = OptimizelyAssets.this.getOptimizelyAssetsPath(paramString);
      }
      for (;;)
      {
        try
        {
          if (this.imageViewReference == null) {
            continue;
          }
          localImageView = (ImageView)this.imageViewReference.get();
          if ((localImageView != null) && (localImageView.isAttachedToWindow())) {
            localImageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(localFile)));
          }
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          ImageView localImageView;
          OptimizelyAssets.this.optimizely.verboseLog(true, "OptimizelyAssets", "Exception while trying to update image view with exception %s", new Object[] { localFileNotFoundException.getLocalizedMessage() });
          continue;
        }
        OptimizelyAssets.this.cachedRemoteAssets.add(paramString);
        return;
        localImageView = null;
      }
    }
  }
  
  private class SendLocalAssetsTask
    extends AsyncTask<Void, Void, Void>
  {
    private SendLocalAssetsTask() {}
    
    @Nullable
    protected Void doInBackground(Void... paramVarArgs)
    {
      if (OptimizelyAssets.this.optimizely.isActive()) {
        paramVarArgs = OptimizelyAssets.this.optimizely.getCurrentContext();
      }
      try
      {
        paramVarArgs = paramVarArgs.getResources().getAssets();
        OptimizelyAssets.access$402(OptimizelyAssets.this, OptimizelyAssets.this.findAllLocalAssets(paramVarArgs, ""));
        OptimizelyAssets.this.assets.addAll(OptimizelyAssets.this.getDrawableResources());
        paramVarArgs = OptimizelyAssets.this.createAssetMessages();
        OptimizelyAssets.this.optimizely.sendMap(paramVarArgs);
        return null;
      }
      catch (Exception paramVarArgs)
      {
        for (;;)
        {
          OptimizelyAssets.this.optimizely.verboseLog(true, "OptimizelyAssets", "Failed to send assets batch to editor: %1$s", new Object[] { paramVarArgs.getLocalizedMessage() });
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Assets/OptimizelyAssets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
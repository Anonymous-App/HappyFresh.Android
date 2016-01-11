package com.optimizely.Editor;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.CodeBlocks.OptimizelyCodeBlock;
import com.optimizely.CodeBlocks.OptimizelyCodeBlocks;
import com.optimizely.Optimizely;
import com.optimizely.Optimizely.OptimizelyStartState;
import com.optimizely.Variable.LiveVariable;
import com.optimizely.Variable.OptimizelyVariables;
import com.optimizely.View.ViewUtils;
import com.optimizely.integration.OptimizelyPlugin;
import com.optimizely.integration.PluginRegistry;
import dalvik.system.DexFile;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

public class OptimizelyClassLoaderTask
  extends AsyncTask<Void, Void, Void>
{
  private static final String CLASS_LOADER_COMPONENT = "OptimizelyFieldsLoaderTask";
  @NonNull
  private static final String[] EXCLUDED_PREFIXES;
  private final Optimizely mOptimizely;
  private boolean mShouldLoadFields;
  private boolean mShouldLoadPlugins;
  
  static
  {
    if (!OptimizelyClassLoaderTask.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      EXCLUDED_PREFIXES = new String[] { "android", "java", "com.android", "com.google.gson", "com.squareup", "com.crashlytics", "io.fabric.sdk", "com.mixpanel", "com.actionbarsherlock", "com.nineoldandroids", "org.apache", "org.xml" };
      return;
    }
  }
  
  public OptimizelyClassLoaderTask(@NonNull Optimizely paramOptimizely)
  {
    this.mOptimizely = paramOptimizely;
  }
  
  private static boolean isExcluded(@NonNull String paramString)
  {
    boolean bool = false;
    String[] arrayOfString = EXCLUDED_PREFIXES;
    int j = arrayOfString.length;
    int i = 0;
    while (i < j)
    {
      if (paramString.startsWith(arrayOfString[i])) {
        return true;
      }
      i += 1;
    }
    paramString = ViewUtils.stripPackage(paramString);
    if ((paramString.contains("$")) || (paramString.equals("R"))) {
      bool = true;
    }
    return bool;
  }
  
  private void loadOptimizelyEntitiesFromClasses()
  {
    Object localObject = this.mOptimizely.getCurrentContext().getApplicationContext();
    if (localObject == null) {}
    ClassLoader localClassLoader;
    do
    {
      do
      {
        return;
        localClassLoader = ((Context)localObject).getClassLoader();
      } while (localClassLoader == null);
      localObject = ((Context)localObject).getApplicationInfo();
    } while (localObject == null);
    localObject = ((ApplicationInfo)localObject).sourceDir;
    try
    {
      DexFile localDexFile = new DexFile((String)localObject);
      Enumeration localEnumeration = localDexFile.entries();
      while ((localEnumeration != null) && (localEnumeration.hasMoreElements()))
      {
        String str = (String)localEnumeration.nextElement();
        if (str != null)
        {
          boolean bool = isExcluded(str);
          if (!bool) {
            try
            {
              if (this.mShouldLoadFields) {
                loadStaticOptimizelyFieldsFromClass(localClassLoader, str);
              }
              if (this.mShouldLoadPlugins) {
                loadOptimizelyPlugins(localClassLoader, str, this.mOptimizely);
              }
            }
            catch (Throwable localThrowable)
            {
              this.mOptimizely.verboseLog(false, "OptimizelyFieldsLoaderTask", "Optimizely skipped scanning %s class to discover CodeBlocks and LiveVariables. If this is not one of your components or does not contains any CodeBlocks and LiveVariables, you can ignore this warning.", new Object[] { str });
            }
          }
        }
      }
      localDexFile.close();
    }
    catch (IOException localIOException)
    {
      this.mOptimizely.verboseLog(true, "OptimizelyFieldsLoaderTask", "Aborting class load because we could not load source dir: " + (String)localObject, new Object[0]);
      return;
    }
  }
  
  static void loadOptimizelyPlugins(ClassLoader paramClassLoader, @NonNull String paramString, @NonNull Optimizely paramOptimizely)
    throws ClassNotFoundException, NoClassDefFoundError
  {
    paramClassLoader = paramClassLoader.loadClass(paramString);
    int k = 0;
    Object localObject = paramClassLoader.getInterfaces();
    int m = localObject.length;
    int i = 0;
    for (;;)
    {
      int j = k;
      if (i < m)
      {
        if (OptimizelyPlugin.class.equals(localObject[i])) {
          j = 1;
        }
      }
      else if (j == 0) {}
      try
      {
        paramClassLoader = (OptimizelyPlugin)paramClassLoader.newInstance();
        localObject = paramOptimizely.getPluginRegistry();
        if (Optimizely.getStartState() != Optimizely.OptimizelyStartState.STARTED) {}
        for (boolean bool = true;; bool = false)
        {
          ((PluginRegistry)localObject).registerPlugin(paramClassLoader, bool);
          return;
          i += 1;
          break;
        }
        return;
      }
      catch (InstantiationException paramClassLoader)
      {
        paramOptimizely.verboseLog(true, paramString, "Extension Class could not be instantiated! Make sure your extension classhas a public zero-argument constructor. Load failed with exception: " + paramClassLoader.getLocalizedMessage(), new Object[0]);
        return;
      }
      catch (IllegalAccessException paramClassLoader)
      {
        paramOptimizely.verboseLog(true, paramString, "Extension Class could not be instantiated! Make sure your extension classhas a public zero-argument constructor. Load failed with exception: " + paramClassLoader.getLocalizedMessage(), new Object[0]);
        return;
      }
      catch (Exception paramClassLoader)
      {
        paramOptimizely.verboseLog(true, paramString, "Extension Class could not be instantiated! Load failed with exception" + paramClassLoader.getLocalizedMessage(), new Object[0]);
      }
    }
  }
  
  static void loadStaticOptimizelyFieldsFromClass(@NonNull ClassLoader paramClassLoader, String paramString)
    throws ClassNotFoundException, NoClassDefFoundError
  {
    paramClassLoader = paramClassLoader.loadClass(paramString);
    assert (paramClassLoader != null);
    paramClassLoader = paramClassLoader.getDeclaredFields();
    if (paramClassLoader == null) {
      return;
    }
    int j = paramClassLoader.length;
    int i = 0;
    while (i < j)
    {
      paramString = paramClassLoader[i];
      if (Modifier.isStatic(paramString.getModifiers()))
      {
        Class localClass = paramString.getType();
        if ((LiveVariable.class.equals(localClass)) || (OptimizelyCodeBlock.class.equals(localClass))) {
          paramString.setAccessible(true);
        }
      }
      try
      {
        paramString.get(null);
        i += 1;
      }
      catch (IllegalAccessException paramString)
      {
        for (;;) {}
      }
    }
  }
  
  @Nullable
  protected Void doInBackground(Void... paramVarArgs)
  {
    loadOptimizelyEntitiesFromClasses();
    if (this.mShouldLoadFields)
    {
      this.mOptimizely.getOptimizelyCodeBlocks().sendCodeBlocks();
      this.mOptimizely.getOptimizelyVariables().sendAllVariablesToEditor();
    }
    return null;
  }
  
  @NonNull
  public OptimizelyClassLoaderTask loadFields(boolean paramBoolean)
  {
    this.mShouldLoadFields = paramBoolean;
    return this;
  }
  
  @NonNull
  public OptimizelyClassLoaderTask loadPlugins(boolean paramBoolean)
  {
    this.mShouldLoadPlugins = paramBoolean;
    return this;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Editor/OptimizelyClassLoaderTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
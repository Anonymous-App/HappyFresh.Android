package com.optimizely.crashreporting;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.LogAndEvent.DatabaseRunner;
import com.optimizely.LogAndEvent.OptimizelyDataStore;
import com.optimizely.Optimizely;
import com.optimizely.utils.VersionResolver;
import java.util.Queue;

public class OptimizelyUncaughtExceptionHandler
  implements Thread.UncaughtExceptionHandler
{
  public static final String DISABLE_OPTIMIZELY_APP_VERSION = "com.optimizely.disable.app_version";
  public static final String DISABLE_OPTIMIZELY_KEY = "com.optimizely.disable";
  public static final String DISABLE_OPTIMIZELY_SDK_VERSION = "com.optimizely.disable.sdk_version";
  public static final String EXCEPTION_HANDLER_COMPONENT = OptimizelyUncaughtExceptionHandler.class.getSimpleName();
  public static final Object monitor = new Object();
  @NonNull
  private final Optimizely optimizely;
  private final Thread.UncaughtExceptionHandler wrappedHandler;
  
  private OptimizelyUncaughtExceptionHandler(@NonNull Optimizely paramOptimizely, Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler)
  {
    this.optimizely = paramOptimizely;
    this.wrappedHandler = paramUncaughtExceptionHandler;
  }
  
  private void forwardThrowable(Thread paramThread, Throwable paramThrowable)
  {
    if ((this.wrappedHandler != null) && (!paramThrowable.getClass().getSimpleName().equals("Crash")))
    {
      this.optimizely.verboseLog(false, OptimizelyUncaughtExceptionHandler.class.getSimpleName(), "Forwarding throwable", new Object[0]);
      this.wrappedHandler.uncaughtException(paramThread, paramThrowable);
    }
  }
  
  @NonNull
  public static OptimizelyUncaughtExceptionHandler start(@NonNull Optimizely paramOptimizely)
  {
    Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    if (!(localUncaughtExceptionHandler instanceof OptimizelyUncaughtExceptionHandler))
    {
      paramOptimizely = new OptimizelyUncaughtExceptionHandler(paramOptimizely, localUncaughtExceptionHandler);
      Thread.setDefaultUncaughtExceptionHandler(paramOptimizely);
      return paramOptimizely;
    }
    return (OptimizelyUncaughtExceptionHandler)localUncaughtExceptionHandler;
  }
  
  private String transformStackTraceToString(@Nullable Throwable paramThrowable)
  {
    if (paramThrowable == null) {
      return "";
    }
    StringBuilder localStringBuilder = new StringBuilder(transformStackTraceToString(paramThrowable.getCause()));
    localStringBuilder.append(paramThrowable.getLocalizedMessage());
    paramThrowable = paramThrowable.getStackTrace();
    if (paramThrowable == null) {
      return "No Available Stack";
    }
    int j = paramThrowable.length;
    int i = 0;
    while (i < j)
    {
      String str = paramThrowable[i].toString();
      localStringBuilder.append("\n");
      localStringBuilder.append(str);
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  boolean isOptimizelyCrash(@NonNull String paramString)
  {
    return paramString.contains("com.optimizely");
  }
  
  boolean isOptimizelyDbThread(Thread paramThread)
  {
    return (paramThread.getName().contains("OptimizelyDbThread")) && ((paramThread instanceof DatabaseRunner));
  }
  
  boolean isOptimizelyThread(Thread paramThread)
  {
    return (paramThread.getName().contains("OptimizelyAsyncTask")) || (paramThread.getName().contains("OptimizelyDbThread"));
  }
  
  public void stop()
  {
    if ((Thread.getDefaultUncaughtExceptionHandler() instanceof OptimizelyUncaughtExceptionHandler)) {
      Thread.setDefaultUncaughtExceptionHandler(this.wrappedHandler);
    }
  }
  
  public void uncaughtException(@NonNull Thread paramThread, @NonNull Throwable paramThrowable)
  {
    String str = transformStackTraceToString(paramThrowable);
    Object localObject = paramThrowable.getClass();
    if (localObject != null)
    {
      localObject = ((Class)localObject).getSimpleName();
      this.optimizely.errorInComponent(paramThread.getName(), (String)localObject, str, new Object[0]);
      Optimizely.sendEvents();
      localObject = new VersionResolver(this.optimizely);
      if (!isOptimizelyCrash(str)) {
        break label197;
      }
      if ((isOptimizelyThread(paramThread)) || (!OptimizelyUtils.isAppStoreApp(this.optimizely.getCurrentContext()))) {
        break label105;
      }
      OptimizelyUtils.disableOptimizely(this.optimizely, (VersionResolver)localObject);
      forwardThrowable(paramThread, paramThrowable);
    }
    label105:
    while (!isOptimizelyDbThread(paramThread))
    {
      return;
      localObject = "Unknown";
      break;
    }
    paramThread = ((DatabaseRunner)paramThread).getQueue();
    paramThrowable = new DatabaseRunner(this.optimizely, this.optimizely.getSharedDataStore());
    while (!paramThread.isEmpty()) {
      paramThrowable.post((Runnable)paramThread.poll());
    }
    this.optimizely.getSharedDataStore().setDatabaseRunner(paramThrowable);
    this.optimizely.verboseLog(false, OptimizelyUncaughtExceptionHandler.class.getSimpleName(), "Set new db runner", new Object[0]);
    return;
    label197:
    forwardThrowable(paramThread, paramThrowable);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/crashreporting/OptimizelyUncaughtExceptionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
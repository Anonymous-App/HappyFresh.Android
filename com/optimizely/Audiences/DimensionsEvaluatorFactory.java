package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import com.optimizely.Optimizely;
import java.util.HashMap;
import java.util.Map;

public class DimensionsEvaluatorFactory
{
  public static final String AND = "and";
  public static final String ANDROID_APP_VERSION = "android_app_version";
  public static final String ANDROID_DEVICE_DPI = "android_device_dpi";
  public static final String ANDROID_DEVICE_MODEL = "android_device_model";
  public static final String ANDROID_DEVICE_SCREEN_SIZE_DP = "android_device_screen_size_dp";
  public static final String ANDROID_DEVICE_SCREEN_SIZE_INCHES = "android_device_screen_size_inches";
  public static final String ANDROID_OPTIMIZELY_SDK_VERSION = "android_optimizely_sdk_version";
  public static final String ANDROID_OS_VERSION = "android_os_version";
  public static final String CUSTOM_TAG = "custom_tag";
  public static final String HAS_PPID = "has_ppid";
  public static final String LANGUAGE = "language";
  private static final Map<String, DimensionsEvaluator> MAPPING = new HashMap();
  public static final String NOT = "not";
  public static final String OR = "or";
  private AudiencesEvaluator audiencesEvaluator;
  
  public DimensionsEvaluatorFactory(@NonNull Optimizely paramOptimizely)
  {
    initialize(paramOptimizely);
  }
  
  private void initialize(@NonNull Optimizely paramOptimizely)
  {
    this.audiencesEvaluator = new AudiencesEvaluator(paramOptimizely, this);
    MAPPING.put("and", new AndDimensionsEvaluator(this));
    MAPPING.put("or", new OrDimensionsEvaluator(this));
    MAPPING.put("not", new NotDimensionsEvaluator(this));
    MAPPING.put("custom_tag", new CustomTagEvaluator(paramOptimizely));
    MAPPING.put("has_ppid", new HasPPIDEvaluator(paramOptimizely));
    MAPPING.put("language", new LanguageEvaluator(paramOptimizely));
    MAPPING.put("android_app_version", new AndroidAppVersionEvaluator(paramOptimizely));
    MAPPING.put("android_device_dpi", new AndroidDeviceDPIEvaluator(paramOptimizely));
    MAPPING.put("android_device_model", new AndroidDeviceModelEvaluator(paramOptimizely));
    MAPPING.put("android_device_screen_size_dp", new AndroidDeviceScreenSizeDPEvaluator(paramOptimizely));
    MAPPING.put("android_device_screen_size_inches", new AndroidDeviceScreenSizeInchesEvaluator(paramOptimizely));
    MAPPING.put("android_optimizely_sdk_version", new AndroidOptimizelySDKVersionEvaluator(paramOptimizely));
    MAPPING.put("android_os_version", new AndroidOSVersionEvaluator(paramOptimizely));
  }
  
  public DimensionsEvaluator get(@NonNull String paramString)
  {
    return (DimensionsEvaluator)MAPPING.get(paramString.toLowerCase());
  }
  
  public DimensionsEvaluator getDefault()
  {
    return this.audiencesEvaluator;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/DimensionsEvaluatorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
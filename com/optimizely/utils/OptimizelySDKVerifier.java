package com.optimizely.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.optimizely.Optimizely;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;

public class OptimizelySDKVerifier
{
  private static final String OPTIMIZELY_APPENGINE_END_POINT = "https://app.optimizely.com/mobile/installed";
  private static final String SDK_VERIFIER = "OPTIMIZELY_SDK_VERIFIER";
  private static Optimizely optimizely;
  
  public static void verifySDKInstallation(String paramString1, String paramString2, @NonNull Optimizely paramOptimizely)
  {
    optimizely = paramOptimizely;
    new SDKVerifierTask(null).executeOnExecutor(OptimizelyThreadPoolExecutor.instance(), new String[] { paramString1, paramString2 });
  }
  
  private static class SDKVerifierTask
    extends AsyncTask<String, Void, Void>
  {
    @Nullable
    protected Void doInBackground(@NonNull String... paramVarArgs)
    {
      String str = paramVarArgs[0];
      Object localObject = paramVarArgs[1];
      try
      {
        paramVarArgs = OptimizelySDKVerifier.optimizely.getHttpClient();
        localObject = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), String.format("project_id=%s&sdk_version=%s", new Object[] { str, localObject }));
        paramVarArgs.newCall(new Request.Builder().url("https://app.optimizely.com/mobile/installed").post((RequestBody)localObject).build()).execute();
        return null;
      }
      catch (Exception paramVarArgs)
      {
        for (;;)
        {
          Log.e("OPTIMIZELY_SDK_VERIFIER", String.format("Failed to send SDK installation ping to Optimizely %s", new Object[] { str }), paramVarArgs);
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/utils/OptimizelySDKVerifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
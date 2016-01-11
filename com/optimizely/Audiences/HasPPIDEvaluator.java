package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import com.optimizely.Optimizely;
import java.util.Map;

public class HasPPIDEvaluator
  implements DimensionsEvaluator<Map<String, String>>
{
  private Optimizely optimizely;
  
  public HasPPIDEvaluator(Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  public boolean evaluate(@NonNull Map<String, String> paramMap)
  {
    return this.optimizely.getPPID(this.optimizely.getCurrentContext()) != null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/HasPPIDEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
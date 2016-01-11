package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import java.util.List;

public class OrDimensionsEvaluator
  implements DimensionsEvaluator<List>
{
  private DimensionsEvaluatorFactory dimensionsEvaluatorFactory;
  
  public OrDimensionsEvaluator(@NonNull DimensionsEvaluatorFactory paramDimensionsEvaluatorFactory)
  {
    this.dimensionsEvaluatorFactory = paramDimensionsEvaluatorFactory;
  }
  
  public boolean evaluate(@NonNull List paramList)
  {
    int i = 1;
    while (i < paramList.size())
    {
      Object localObject = paramList.get(i);
      String str = AudienceUtils.findOperator(localObject);
      if (this.dimensionsEvaluatorFactory.get(str).evaluate(localObject)) {
        return true;
      }
      i += 1;
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/OrDimensionsEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
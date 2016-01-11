package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import java.util.List;

public class AndDimensionsEvaluator
  implements DimensionsEvaluator<List<Object>>
{
  private DimensionsEvaluatorFactory dimensionsEvaluatorFactory;
  
  public AndDimensionsEvaluator(@NonNull DimensionsEvaluatorFactory paramDimensionsEvaluatorFactory)
  {
    this.dimensionsEvaluatorFactory = paramDimensionsEvaluatorFactory;
  }
  
  public boolean evaluate(@NonNull List<Object> paramList)
  {
    if (paramList.size() < 2) {
      return false;
    }
    int i = 1;
    for (;;)
    {
      if (i >= paramList.size()) {
        break label63;
      }
      Object localObject = paramList.get(i);
      String str = AudienceUtils.findOperator(localObject);
      if (!this.dimensionsEvaluatorFactory.get(str).evaluate(localObject)) {
        break;
      }
      i += 1;
    }
    label63:
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/AndDimensionsEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
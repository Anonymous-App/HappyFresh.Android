package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import java.util.List;

public class NotDimensionsEvaluator
  implements DimensionsEvaluator
{
  private DimensionsEvaluatorFactory dimensionsEvaluatorFactory;
  
  public NotDimensionsEvaluator(@NonNull DimensionsEvaluatorFactory paramDimensionsEvaluatorFactory)
  {
    this.dimensionsEvaluatorFactory = paramDimensionsEvaluatorFactory;
  }
  
  private boolean reverseOfCondition(@NonNull Object paramObject)
  {
    String str = AudienceUtils.findOperator(paramObject);
    return !this.dimensionsEvaluatorFactory.get(str).evaluate(paramObject);
  }
  
  public boolean evaluate(@NonNull Object paramObject)
  {
    if (!(paramObject instanceof List)) {}
    do
    {
      return false;
      paramObject = (List)paramObject;
    } while (((List)paramObject).size() != 2);
    return reverseOfCondition(((List)paramObject).get(1));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/NotDimensionsEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.facebook.share.internal;

import com.facebook.internal.Validate;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.model.GameRequestContent.ActionType;

public class GameRequestValidation
{
  public static void validate(GameRequestContent paramGameRequestContent)
  {
    int j = 0;
    Validate.notNull(paramGameRequestContent.getMessage(), "message");
    if (paramGameRequestContent.getObjectId() != null) {}
    for (int i = 1;; i = 0)
    {
      if ((paramGameRequestContent.getActionType() == GameRequestContent.ActionType.ASKFOR) || (paramGameRequestContent.getActionType() == GameRequestContent.ActionType.SEND)) {
        j = 1;
      }
      if ((i ^ j) == 0) {
        break;
      }
      throw new IllegalArgumentException("Object id should be provided if and only if action type is send or askfor");
    }
    j = 0;
    if (paramGameRequestContent.getTo() != null) {
      j = 0 + 1;
    }
    i = j;
    if (paramGameRequestContent.getSuggestions() != null) {
      i = j + 1;
    }
    j = i;
    if (paramGameRequestContent.getFilters() != null) {
      j = i + 1;
    }
    if (j > 1) {
      throw new IllegalArgumentException("Parameters to, filters and suggestions are mutually exclusive");
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/internal/GameRequestValidation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
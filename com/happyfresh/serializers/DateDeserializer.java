package com.happyfresh.serializers;

import android.util.Log;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateDeserializer
  implements JsonDeserializer<Date>
{
  private static final String TAG = DateDeserializer.class.getSimpleName();
  
  public Date deserialize(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext)
    throws JsonParseException
  {
    paramJsonElement = paramJsonElement.getAsString();
    paramType = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    paramType.setTimeZone(TimeZone.getTimeZone("UTC"));
    try
    {
      paramJsonElement = paramType.parse(paramJsonElement);
      return paramJsonElement;
    }
    catch (ParseException paramJsonElement)
    {
      Log.e(TAG, "Failed to parse Date due to:", paramJsonElement);
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/serializers/DateDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package retrofit.converter;

import java.lang.reflect.Type;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public abstract interface Converter
{
  public abstract Object fromBody(TypedInput paramTypedInput, Type paramType)
    throws ConversionException;
  
  public abstract TypedOutput toBody(Object paramObject);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/converter/Converter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
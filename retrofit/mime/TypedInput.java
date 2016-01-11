package retrofit.mime;

import java.io.IOException;
import java.io.InputStream;

public abstract interface TypedInput
{
  public abstract InputStream in()
    throws IOException;
  
  public abstract long length();
  
  public abstract String mimeType();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/mime/TypedInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
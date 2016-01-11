package retrofit.mime;

import java.io.IOException;
import java.io.OutputStream;

public abstract interface TypedOutput
{
  public abstract String fileName();
  
  public abstract long length();
  
  public abstract String mimeType();
  
  public abstract void writeTo(OutputStream paramOutputStream)
    throws IOException;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/mime/TypedOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
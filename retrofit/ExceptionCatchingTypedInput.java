package retrofit;

import java.io.IOException;
import java.io.InputStream;
import retrofit.mime.TypedInput;

class ExceptionCatchingTypedInput
  implements TypedInput
{
  private final TypedInput delegate;
  private final ExceptionCatchingInputStream delegateStream;
  
  ExceptionCatchingTypedInput(TypedInput paramTypedInput)
    throws IOException
  {
    this.delegate = paramTypedInput;
    this.delegateStream = new ExceptionCatchingInputStream(paramTypedInput.in());
  }
  
  IOException getThrownException()
  {
    return this.delegateStream.thrownException;
  }
  
  public InputStream in()
    throws IOException
  {
    return this.delegateStream;
  }
  
  public long length()
  {
    return this.delegate.length();
  }
  
  public String mimeType()
  {
    return this.delegate.mimeType();
  }
  
  boolean threwException()
  {
    return this.delegateStream.thrownException != null;
  }
  
  private static class ExceptionCatchingInputStream
    extends InputStream
  {
    private final InputStream delegate;
    private IOException thrownException;
    
    ExceptionCatchingInputStream(InputStream paramInputStream)
    {
      this.delegate = paramInputStream;
    }
    
    public int available()
      throws IOException
    {
      try
      {
        int i = this.delegate.available();
        return i;
      }
      catch (IOException localIOException)
      {
        this.thrownException = localIOException;
        throw localIOException;
      }
    }
    
    public void close()
      throws IOException
    {
      try
      {
        this.delegate.close();
        return;
      }
      catch (IOException localIOException)
      {
        this.thrownException = localIOException;
        throw localIOException;
      }
    }
    
    public void mark(int paramInt)
    {
      try
      {
        this.delegate.mark(paramInt);
        return;
      }
      finally
      {
        localObject = finally;
        throw ((Throwable)localObject);
      }
    }
    
    public boolean markSupported()
    {
      return this.delegate.markSupported();
    }
    
    public int read()
      throws IOException
    {
      try
      {
        int i = this.delegate.read();
        return i;
      }
      catch (IOException localIOException)
      {
        this.thrownException = localIOException;
        throw localIOException;
      }
    }
    
    public int read(byte[] paramArrayOfByte)
      throws IOException
    {
      try
      {
        int i = this.delegate.read(paramArrayOfByte);
        return i;
      }
      catch (IOException paramArrayOfByte)
      {
        this.thrownException = paramArrayOfByte;
        throw paramArrayOfByte;
      }
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      try
      {
        paramInt1 = this.delegate.read(paramArrayOfByte, paramInt1, paramInt2);
        return paramInt1;
      }
      catch (IOException paramArrayOfByte)
      {
        this.thrownException = paramArrayOfByte;
        throw paramArrayOfByte;
      }
    }
    
    public void reset()
      throws IOException
    {
      try
      {
        this.delegate.reset();
        return;
      }
      catch (IOException localIOException)
      {
        this.thrownException = localIOException;
        throw localIOException;
      }
      finally {}
    }
    
    public long skip(long paramLong)
      throws IOException
    {
      try
      {
        paramLong = this.delegate.skip(paramLong);
        return paramLong;
      }
      catch (IOException localIOException)
      {
        this.thrownException = localIOException;
        throw localIOException;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/ExceptionCatchingTypedInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
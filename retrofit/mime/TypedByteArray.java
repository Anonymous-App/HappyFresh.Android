package retrofit.mime;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class TypedByteArray
  implements TypedInput, TypedOutput
{
  private final byte[] bytes;
  private final String mimeType;
  
  public TypedByteArray(String paramString, byte[] paramArrayOfByte)
  {
    String str = paramString;
    if (paramString == null) {
      str = "application/unknown";
    }
    if (paramArrayOfByte == null) {
      throw new NullPointerException("bytes");
    }
    this.mimeType = str;
    this.bytes = paramArrayOfByte;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      paramObject = (TypedByteArray)paramObject;
      if (!Arrays.equals(this.bytes, ((TypedByteArray)paramObject).bytes)) {
        return false;
      }
    } while (this.mimeType.equals(((TypedByteArray)paramObject).mimeType));
    return false;
  }
  
  public String fileName()
  {
    return null;
  }
  
  public byte[] getBytes()
  {
    return this.bytes;
  }
  
  public int hashCode()
  {
    return this.mimeType.hashCode() * 31 + Arrays.hashCode(this.bytes);
  }
  
  public InputStream in()
    throws IOException
  {
    return new ByteArrayInputStream(this.bytes);
  }
  
  public long length()
  {
    return this.bytes.length;
  }
  
  public String mimeType()
  {
    return this.mimeType;
  }
  
  public String toString()
  {
    return "TypedByteArray[length=" + length() + "]";
  }
  
  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write(this.bytes);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/mime/TypedByteArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
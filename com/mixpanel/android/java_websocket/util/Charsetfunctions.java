package com.mixpanel.android.java_websocket.util;

import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

public class Charsetfunctions
{
  public static CodingErrorAction codingErrorAction = CodingErrorAction.REPORT;
  
  public static byte[] asciiBytes(String paramString)
  {
    try
    {
      paramString = paramString.getBytes("ASCII");
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new RuntimeException(paramString);
    }
  }
  
  public static void main(String[] paramArrayOfString)
    throws InvalidDataException
  {
    stringUtf8(utf8Bytes("\000"));
    stringAscii(asciiBytes("\000"));
  }
  
  public static String stringAscii(byte[] paramArrayOfByte)
  {
    return stringAscii(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static String stringAscii(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      paramArrayOfByte = new String(paramArrayOfByte, paramInt1, paramInt2, "ASCII");
      return paramArrayOfByte;
    }
    catch (UnsupportedEncodingException paramArrayOfByte)
    {
      throw new RuntimeException(paramArrayOfByte);
    }
  }
  
  public static String stringUtf8(ByteBuffer paramByteBuffer)
    throws InvalidDataException
  {
    Object localObject = Charset.forName("UTF8").newDecoder();
    ((CharsetDecoder)localObject).onMalformedInput(codingErrorAction);
    ((CharsetDecoder)localObject).onUnmappableCharacter(codingErrorAction);
    try
    {
      paramByteBuffer.mark();
      localObject = ((CharsetDecoder)localObject).decode(paramByteBuffer).toString();
      paramByteBuffer.reset();
      return (String)localObject;
    }
    catch (CharacterCodingException paramByteBuffer)
    {
      throw new InvalidDataException(1007, paramByteBuffer);
    }
  }
  
  public static String stringUtf8(byte[] paramArrayOfByte)
    throws InvalidDataException
  {
    return stringUtf8(ByteBuffer.wrap(paramArrayOfByte));
  }
  
  public static byte[] utf8Bytes(String paramString)
  {
    try
    {
      paramString = paramString.getBytes("UTF8");
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new RuntimeException(paramString);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/util/Charsetfunctions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
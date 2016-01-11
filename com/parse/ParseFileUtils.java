package com.parse;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

class ParseFileUtils
{
  private static final long FILE_COPY_BUFFER_SIZE = 31457280L;
  public static final long ONE_KB = 1024L;
  public static final long ONE_MB = 1048576L;
  
  public static void cleanDirectory(File paramFile)
    throws IOException
  {
    if (!paramFile.exists()) {
      throw new IllegalArgumentException(paramFile + " does not exist");
    }
    if (!paramFile.isDirectory()) {
      throw new IllegalArgumentException(paramFile + " is not a directory");
    }
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile == null) {
      throw new IOException("Failed to list contents of " + paramFile);
    }
    paramFile = null;
    int j = arrayOfFile.length;
    int i = 0;
    for (;;)
    {
      if (i < j)
      {
        File localFile = arrayOfFile[i];
        try
        {
          forceDelete(localFile);
          i += 1;
        }
        catch (IOException paramFile)
        {
          for (;;) {}
        }
      }
    }
    if (paramFile != null) {
      throw paramFile;
    }
  }
  
  public static void copyFile(File paramFile1, File paramFile2)
    throws IOException
  {
    copyFile(paramFile1, paramFile2, true);
  }
  
  public static void copyFile(File paramFile1, File paramFile2, boolean paramBoolean)
    throws IOException
  {
    if (paramFile1 == null) {
      throw new NullPointerException("Source must not be null");
    }
    if (paramFile2 == null) {
      throw new NullPointerException("Destination must not be null");
    }
    if (!paramFile1.exists()) {
      throw new FileNotFoundException("Source '" + paramFile1 + "' does not exist");
    }
    if (paramFile1.isDirectory()) {
      throw new IOException("Source '" + paramFile1 + "' exists but is a directory");
    }
    if (paramFile1.getCanonicalPath().equals(paramFile2.getCanonicalPath())) {
      throw new IOException("Source '" + paramFile1 + "' and destination '" + paramFile2 + "' are the same");
    }
    File localFile = paramFile2.getParentFile();
    if ((localFile != null) && (!localFile.mkdirs()) && (!localFile.isDirectory())) {
      throw new IOException("Destination '" + localFile + "' directory cannot be created");
    }
    if ((paramFile2.exists()) && (!paramFile2.canWrite())) {
      throw new IOException("Destination '" + paramFile2 + "' exists but is read-only");
    }
    doCopyFile(paramFile1, paramFile2, paramBoolean);
  }
  
  public static void deleteDirectory(File paramFile)
    throws IOException
  {
    if (!paramFile.exists()) {}
    do
    {
      return;
      if (!isSymlink(paramFile)) {
        cleanDirectory(paramFile);
      }
    } while (paramFile.delete());
    throw new IOException("Unable to delete directory " + paramFile + ".");
  }
  
  public static boolean deleteQuietly(File paramFile)
  {
    if (paramFile == null) {
      return false;
    }
    try
    {
      if (paramFile.isDirectory()) {
        cleanDirectory(paramFile);
      }
      try
      {
        boolean bool = paramFile.delete();
        return bool;
      }
      catch (Exception paramFile)
      {
        return false;
      }
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  private static void doCopyFile(File paramFile1, File paramFile2, boolean paramBoolean)
    throws IOException
  {
    if ((paramFile2.exists()) && (paramFile2.isDirectory())) {
      throw new IOException("Destination '" + paramFile2 + "' exists but is a directory");
    }
    localFileOutputStream = null;
    localFileChannel2 = null;
    Object localObject5 = null;
    localObject2 = null;
    FileChannel localFileChannel3 = null;
    localFileChannel1 = null;
    for (;;)
    {
      try
      {
        localObject3 = new FileInputStream(paramFile1);
      }
      finally
      {
        Object localObject1;
        localObject3 = localFileChannel2;
        paramFile1 = localFileOutputStream;
        ParseIOUtils.closeQuietly(localFileChannel1);
        ParseIOUtils.closeQuietly((OutputStream)localObject3);
        ParseIOUtils.closeQuietly((Closeable)localObject2);
        ParseIOUtils.closeQuietly(paramFile1);
      }
      try
      {
        localFileOutputStream = new FileOutputStream(paramFile2);
        localFileChannel1 = localFileChannel3;
        localObject2 = localObject5;
      }
      finally
      {
        paramFile1 = (File)localObject3;
        localObject3 = localFileChannel2;
        continue;
      }
      try
      {
        localFileChannel2 = ((FileInputStream)localObject3).getChannel();
        localFileChannel1 = localFileChannel3;
        localObject2 = localFileChannel2;
        localFileChannel3 = localFileOutputStream.getChannel();
        localFileChannel1 = localFileChannel3;
        localObject2 = localFileChannel2;
        l3 = localFileChannel2.size();
        l1 = 0L;
      }
      finally
      {
        paramFile2 = localFileOutputStream;
        paramFile1 = (File)localObject3;
        localObject3 = paramFile2;
        paramFile2 = (File)localObject4;
        continue;
        if (l1 >= l3) {
          continue;
        }
        l2 = l3 - l1;
        if (l2 <= 31457280L) {
          continue;
        }
        l2 = 31457280L;
        continue;
      }
      localFileChannel1 = localFileChannel3;
      localObject2 = localFileChannel2;
      l2 = localFileChannel3.transferFrom(localFileChannel2, l1, localObject1);
      if (l2 != 0L) {
        continue;
      }
      ParseIOUtils.closeQuietly(localFileChannel3);
      ParseIOUtils.closeQuietly(localFileOutputStream);
      ParseIOUtils.closeQuietly(localFileChannel2);
      ParseIOUtils.closeQuietly((InputStream)localObject3);
      l1 = paramFile1.length();
      l2 = paramFile2.length();
      if (l1 == l2) {
        continue;
      }
      throw new IOException("Failed to copy full contents from '" + paramFile1 + "' to '" + paramFile2 + "' Expected length: " + l1 + " Actual: " + l2);
    }
    l1 += l2;
    break label336;
  }
  
  public static void forceDelete(File paramFile)
    throws IOException
  {
    if (paramFile.isDirectory()) {
      deleteDirectory(paramFile);
    }
    boolean bool;
    do
    {
      return;
      bool = paramFile.exists();
    } while (paramFile.delete());
    if (!bool) {
      throw new FileNotFoundException("File does not exist: " + paramFile);
    }
    throw new IOException("Unable to delete file: " + paramFile);
  }
  
  public static boolean isSymlink(File paramFile)
    throws IOException
  {
    if (paramFile == null) {
      throw new NullPointerException("File must not be null");
    }
    if (paramFile.getParent() == null) {}
    while (paramFile.getCanonicalFile().equals(paramFile.getAbsoluteFile()))
    {
      return false;
      paramFile = new File(paramFile.getParentFile().getCanonicalFile(), paramFile.getName());
    }
    return true;
  }
  
  public static void moveFile(File paramFile1, File paramFile2)
    throws IOException
  {
    if (paramFile1 == null) {
      throw new NullPointerException("Source must not be null");
    }
    if (paramFile2 == null) {
      throw new NullPointerException("Destination must not be null");
    }
    if (!paramFile1.exists()) {
      throw new FileNotFoundException("Source '" + paramFile1 + "' does not exist");
    }
    if (paramFile1.isDirectory()) {
      throw new IOException("Source '" + paramFile1 + "' is a directory");
    }
    if (paramFile2.exists()) {
      throw new IOException("Destination '" + paramFile2 + "' already exists");
    }
    if (paramFile2.isDirectory()) {
      throw new IOException("Destination '" + paramFile2 + "' is a directory");
    }
    if (!paramFile1.renameTo(paramFile2))
    {
      copyFile(paramFile1, paramFile2);
      if (!paramFile1.delete())
      {
        deleteQuietly(paramFile2);
        throw new IOException("Failed to delete original file '" + paramFile1 + "' after copy to '" + paramFile2 + "'");
      }
    }
  }
  
  public static FileInputStream openInputStream(File paramFile)
    throws IOException
  {
    if (paramFile.exists())
    {
      if (paramFile.isDirectory()) {
        throw new IOException("File '" + paramFile + "' exists but is a directory");
      }
      if (!paramFile.canRead()) {
        throw new IOException("File '" + paramFile + "' cannot be read");
      }
    }
    else
    {
      throw new FileNotFoundException("File '" + paramFile + "' does not exist");
    }
    return new FileInputStream(paramFile);
  }
  
  public static FileOutputStream openOutputStream(File paramFile)
    throws IOException
  {
    if (paramFile.exists())
    {
      if (paramFile.isDirectory()) {
        throw new IOException("File '" + paramFile + "' exists but is a directory");
      }
      if (!paramFile.canWrite()) {
        throw new IOException("File '" + paramFile + "' cannot be written to");
      }
    }
    else
    {
      File localFile = paramFile.getParentFile();
      if ((localFile != null) && (!localFile.exists()) && (!localFile.mkdirs())) {
        throw new IOException("File '" + paramFile + "' could not be created");
      }
    }
    return new FileOutputStream(paramFile);
  }
  
  public static byte[] readFileToByteArray(File paramFile)
    throws IOException
  {
    File localFile = null;
    try
    {
      paramFile = openInputStream(paramFile);
      localFile = paramFile;
      byte[] arrayOfByte = ParseIOUtils.toByteArray(paramFile);
      return arrayOfByte;
    }
    finally
    {
      ParseIOUtils.closeQuietly(localFile);
    }
  }
  
  public static JSONObject readFileToJSONObject(File paramFile)
    throws IOException, JSONException
  {
    return new JSONObject(readFileToString(paramFile, "UTF-8"));
  }
  
  public static String readFileToString(File paramFile, String paramString)
    throws IOException
  {
    return readFileToString(paramFile, Charset.forName(paramString));
  }
  
  public static String readFileToString(File paramFile, Charset paramCharset)
    throws IOException
  {
    return new String(readFileToByteArray(paramFile), paramCharset);
  }
  
  public static void writeByteArrayToFile(File paramFile, byte[] paramArrayOfByte)
    throws IOException
  {
    File localFile = null;
    try
    {
      paramFile = openOutputStream(paramFile);
      localFile = paramFile;
      paramFile.write(paramArrayOfByte);
      return;
    }
    finally
    {
      ParseIOUtils.closeQuietly(localFile);
    }
  }
  
  public static void writeJSONObjectToFile(File paramFile, JSONObject paramJSONObject)
    throws IOException
  {
    writeByteArrayToFile(paramFile, paramJSONObject.toString().getBytes(Charset.forName("UTF-8")));
  }
  
  public static void writeStringToFile(File paramFile, String paramString1, String paramString2)
    throws IOException
  {
    writeStringToFile(paramFile, paramString1, Charset.forName(paramString2));
  }
  
  public static void writeStringToFile(File paramFile, String paramString, Charset paramCharset)
    throws IOException
  {
    writeByteArrayToFile(paramFile, paramString.getBytes(paramCharset));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseFileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
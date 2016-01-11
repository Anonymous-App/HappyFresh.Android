package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class QueueFileEventStorage
  implements EventsStorage
{
  private final Context context;
  private QueueFile queueFile;
  private File targetDirectory;
  private final String targetDirectoryName;
  private final File workingDirectory;
  private final File workingFile;
  
  public QueueFileEventStorage(Context paramContext, File paramFile, String paramString1, String paramString2)
    throws IOException
  {
    this.context = paramContext;
    this.workingDirectory = paramFile;
    this.targetDirectoryName = paramString2;
    this.workingFile = new File(this.workingDirectory, paramString1);
    this.queueFile = new QueueFile(this.workingFile);
    createTargetDirectory();
  }
  
  private void createTargetDirectory()
  {
    this.targetDirectory = new File(this.workingDirectory, this.targetDirectoryName);
    if (!this.targetDirectory.exists()) {
      this.targetDirectory.mkdirs();
    }
  }
  
  /* Error */
  private void move(File paramFile1, File paramFile2)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aconst_null
    //   4: astore_3
    //   5: aconst_null
    //   6: astore 5
    //   8: new 61	java/io/FileInputStream
    //   11: dup
    //   12: aload_1
    //   13: invokespecial 62	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   16: astore 4
    //   18: aload 6
    //   20: astore_3
    //   21: aload_0
    //   22: aload_2
    //   23: invokevirtual 66	io/fabric/sdk/android/services/events/QueueFileEventStorage:getMoveOutputStream	(Ljava/io/File;)Ljava/io/OutputStream;
    //   26: astore_2
    //   27: aload_2
    //   28: astore_3
    //   29: aload 4
    //   31: aload_2
    //   32: sipush 1024
    //   35: newarray <illegal type>
    //   37: invokestatic 72	io/fabric/sdk/android/services/common/CommonUtils:copyStream	(Ljava/io/InputStream;Ljava/io/OutputStream;[B)V
    //   40: aload 4
    //   42: ldc 74
    //   44: invokestatic 78	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   47: aload_2
    //   48: ldc 80
    //   50: invokestatic 78	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   53: aload_1
    //   54: invokevirtual 83	java/io/File:delete	()Z
    //   57: pop
    //   58: return
    //   59: astore 4
    //   61: aload 5
    //   63: astore_2
    //   64: aload_2
    //   65: ldc 74
    //   67: invokestatic 78	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   70: aload_3
    //   71: ldc 80
    //   73: invokestatic 78	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   76: aload_1
    //   77: invokevirtual 83	java/io/File:delete	()Z
    //   80: pop
    //   81: aload 4
    //   83: athrow
    //   84: astore 5
    //   86: aload 4
    //   88: astore_2
    //   89: aload 5
    //   91: astore 4
    //   93: goto -29 -> 64
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	96	0	this	QueueFileEventStorage
    //   0	96	1	paramFile1	File
    //   0	96	2	paramFile2	File
    //   4	67	3	localObject1	Object
    //   16	25	4	localFileInputStream	java.io.FileInputStream
    //   59	28	4	localObject2	Object
    //   91	1	4	localObject3	Object
    //   6	56	5	localObject4	Object
    //   84	6	5	localObject5	Object
    //   1	18	6	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   8	18	59	finally
    //   21	27	84	finally
    //   29	40	84	finally
  }
  
  public void add(byte[] paramArrayOfByte)
    throws IOException
  {
    this.queueFile.add(paramArrayOfByte);
  }
  
  public boolean canWorkingFileStore(int paramInt1, int paramInt2)
  {
    return this.queueFile.hasSpaceFor(paramInt1, paramInt2);
  }
  
  public void deleteFilesInRollOverDirectory(List<File> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      File localFile = (File)paramList.next();
      CommonUtils.logControlled(this.context, String.format("deleting sent analytics file %s", new Object[] { localFile.getName() }));
      localFile.delete();
    }
  }
  
  public void deleteWorkingFile()
  {
    try
    {
      this.queueFile.close();
      this.workingFile.delete();
      return;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
  
  public List<File> getAllFilesInRollOverDirectory()
  {
    return Arrays.asList(this.targetDirectory.listFiles());
  }
  
  public List<File> getBatchOfFilesToSend(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    File[] arrayOfFile = this.targetDirectory.listFiles();
    int j = arrayOfFile.length;
    int i = 0;
    for (;;)
    {
      if (i < j)
      {
        localArrayList.add(arrayOfFile[i]);
        if (localArrayList.size() < paramInt) {}
      }
      else
      {
        return localArrayList;
      }
      i += 1;
    }
  }
  
  public OutputStream getMoveOutputStream(File paramFile)
    throws IOException
  {
    return new FileOutputStream(paramFile);
  }
  
  public File getRollOverDirectory()
  {
    return this.targetDirectory;
  }
  
  public File getWorkingDirectory()
  {
    return this.workingDirectory;
  }
  
  public int getWorkingFileUsedSizeInBytes()
  {
    return this.queueFile.usedBytes();
  }
  
  public boolean isWorkingFileEmpty()
  {
    return this.queueFile.isEmpty();
  }
  
  public void rollOver(String paramString)
    throws IOException
  {
    this.queueFile.close();
    move(this.workingFile, new File(this.targetDirectory, paramString));
    this.queueFile = new QueueFile(this.workingFile);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/events/QueueFileEventStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
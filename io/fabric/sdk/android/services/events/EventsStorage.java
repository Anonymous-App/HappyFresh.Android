package io.fabric.sdk.android.services.events;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract interface EventsStorage
{
  public abstract void add(byte[] paramArrayOfByte)
    throws IOException;
  
  public abstract boolean canWorkingFileStore(int paramInt1, int paramInt2);
  
  public abstract void deleteFilesInRollOverDirectory(List<File> paramList);
  
  public abstract void deleteWorkingFile();
  
  public abstract List<File> getAllFilesInRollOverDirectory();
  
  public abstract List<File> getBatchOfFilesToSend(int paramInt);
  
  public abstract File getRollOverDirectory();
  
  public abstract File getWorkingDirectory();
  
  public abstract int getWorkingFileUsedSizeInBytes();
  
  public abstract boolean isWorkingFileEmpty();
  
  public abstract void rollOver(String paramString)
    throws IOException;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/events/EventsStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
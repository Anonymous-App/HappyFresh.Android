package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class EventsFilesManager<T>
{
  public static final int MAX_BYTE_SIZE_PER_FILE = 8000;
  public static final int MAX_FILES_IN_BATCH = 1;
  public static final int MAX_FILES_TO_KEEP = 100;
  public static final String ROLL_OVER_FILE_NAME_SEPARATOR = "_";
  protected final Context context;
  protected final CurrentTimeProvider currentTimeProvider;
  private final int defaultMaxFilesToKeep;
  protected final EventsStorage eventStorage;
  protected volatile long lastRollOverTime;
  protected final List<EventsStorageListener> rollOverListeners = new CopyOnWriteArrayList();
  protected final EventTransform<T> transform;
  
  public EventsFilesManager(Context paramContext, EventTransform<T> paramEventTransform, CurrentTimeProvider paramCurrentTimeProvider, EventsStorage paramEventsStorage, int paramInt)
    throws IOException
  {
    this.context = paramContext.getApplicationContext();
    this.transform = paramEventTransform;
    this.eventStorage = paramEventsStorage;
    this.currentTimeProvider = paramCurrentTimeProvider;
    this.lastRollOverTime = this.currentTimeProvider.getCurrentTimeMillis();
    this.defaultMaxFilesToKeep = paramInt;
  }
  
  private void rollFileOverIfNeeded(int paramInt)
    throws IOException
  {
    if (!this.eventStorage.canWorkingFileStore(paramInt, getMaxByteSizePerFile()))
    {
      String str = String.format(Locale.US, "session analytics events file is %d bytes, new event is %d bytes, this is over flush limit of %d, rolling it over", new Object[] { Integer.valueOf(this.eventStorage.getWorkingFileUsedSizeInBytes()), Integer.valueOf(paramInt), Integer.valueOf(getMaxByteSizePerFile()) });
      CommonUtils.logControlled(this.context, 4, "Fabric", str);
      rollFileOver();
    }
  }
  
  private void triggerRollOverOnListeners(String paramString)
  {
    Iterator localIterator = this.rollOverListeners.iterator();
    while (localIterator.hasNext())
    {
      EventsStorageListener localEventsStorageListener = (EventsStorageListener)localIterator.next();
      try
      {
        localEventsStorageListener.onRollOver(paramString);
      }
      catch (Exception localException)
      {
        CommonUtils.logControlledError(this.context, "One of the roll over listeners threw an exception", localException);
      }
    }
  }
  
  public void deleteAllEventsFiles()
  {
    this.eventStorage.deleteFilesInRollOverDirectory(this.eventStorage.getAllFilesInRollOverDirectory());
    this.eventStorage.deleteWorkingFile();
  }
  
  public void deleteOldestInRollOverIfOverMax()
  {
    Object localObject2 = this.eventStorage.getAllFilesInRollOverDirectory();
    int i = getMaxFilesToKeep();
    if (((List)localObject2).size() <= i) {
      return;
    }
    int j = ((List)localObject2).size() - i;
    CommonUtils.logControlled(this.context, String.format(Locale.US, "Found %d files in  roll over directory, this is greater than %d, deleting %d oldest files", new Object[] { Integer.valueOf(((List)localObject2).size()), Integer.valueOf(i), Integer.valueOf(j) }));
    Object localObject1 = new TreeSet(new Comparator()
    {
      public int compare(EventsFilesManager.FileWithTimestamp paramAnonymousFileWithTimestamp1, EventsFilesManager.FileWithTimestamp paramAnonymousFileWithTimestamp2)
      {
        return (int)(paramAnonymousFileWithTimestamp1.timestamp - paramAnonymousFileWithTimestamp2.timestamp);
      }
    });
    localObject2 = ((List)localObject2).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      File localFile = (File)((Iterator)localObject2).next();
      ((TreeSet)localObject1).add(new FileWithTimestamp(localFile, parseCreationTimestampFromFileName(localFile.getName())));
    }
    localObject2 = new ArrayList();
    localObject1 = ((TreeSet)localObject1).iterator();
    do
    {
      if (!((Iterator)localObject1).hasNext()) {
        break;
      }
      ((ArrayList)localObject2).add(((FileWithTimestamp)((Iterator)localObject1).next()).file);
    } while (((ArrayList)localObject2).size() != j);
    this.eventStorage.deleteFilesInRollOverDirectory((List)localObject2);
  }
  
  public void deleteSentFiles(List<File> paramList)
  {
    this.eventStorage.deleteFilesInRollOverDirectory(paramList);
  }
  
  protected abstract String generateUniqueRollOverFileName();
  
  public List<File> getBatchOfFilesToSend()
  {
    return this.eventStorage.getBatchOfFilesToSend(1);
  }
  
  public long getLastRollOverTime()
  {
    return this.lastRollOverTime;
  }
  
  protected int getMaxByteSizePerFile()
  {
    return 8000;
  }
  
  protected int getMaxFilesToKeep()
  {
    return this.defaultMaxFilesToKeep;
  }
  
  public long parseCreationTimestampFromFileName(String paramString)
  {
    paramString = paramString.split("_");
    if (paramString.length != 3) {
      return 0L;
    }
    try
    {
      long l = Long.valueOf(paramString[2]).longValue();
      return l;
    }
    catch (NumberFormatException paramString) {}
    return 0L;
  }
  
  public void registerRollOverListener(EventsStorageListener paramEventsStorageListener)
  {
    if (paramEventsStorageListener != null) {
      this.rollOverListeners.add(paramEventsStorageListener);
    }
  }
  
  public boolean rollFileOver()
    throws IOException
  {
    boolean bool = false;
    String str = null;
    if (!this.eventStorage.isWorkingFileEmpty())
    {
      str = generateUniqueRollOverFileName();
      this.eventStorage.rollOver(str);
      CommonUtils.logControlled(this.context, 4, "Fabric", String.format(Locale.US, "generated new file %s", new Object[] { str }));
      this.lastRollOverTime = this.currentTimeProvider.getCurrentTimeMillis();
      bool = true;
    }
    triggerRollOverOnListeners(str);
    return bool;
  }
  
  public void writeEvent(T paramT)
    throws IOException
  {
    paramT = this.transform.toBytes(paramT);
    rollFileOverIfNeeded(paramT.length);
    this.eventStorage.add(paramT);
  }
  
  static class FileWithTimestamp
  {
    final File file;
    final long timestamp;
    
    public FileWithTimestamp(File paramFile, long paramLong)
    {
      this.file = paramFile;
      this.timestamp = paramLong;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/events/EventsFilesManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package org.jcodec;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MovieBox
  extends NodeBox
{
  public MovieBox()
  {
    super(new Header(fourcc()));
  }
  
  public MovieBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public MovieBox(MovieBox paramMovieBox)
  {
    super(paramMovieBox);
  }
  
  private Size applyMatrix(TrakBox paramTrakBox, Size paramSize)
  {
    paramTrakBox = paramTrakBox.getTrackHeader().getMatrix();
    return new Size((int)(paramSize.getWidth() * paramTrakBox[0] / 65536.0D), (int)(paramSize.getHeight() * paramTrakBox[4] / 65536.0D));
  }
  
  public static String fourcc()
  {
    return "moov";
  }
  
  private MovieHeaderBox getMovieHeader()
  {
    return (MovieHeaderBox)findFirst(this, MovieHeaderBox.class, new String[] { "mvhd" });
  }
  
  private void setTimescale(int paramInt)
  {
    ((MovieHeaderBox)findFirst(this, MovieHeaderBox.class, new String[] { "mvhd" })).setTimescale(paramInt);
  }
  
  public void fixTimescale(int paramInt)
  {
    int i = getTimescale();
    setTimescale(paramInt);
    TrakBox[] arrayOfTrakBox = getTracks();
    int j = arrayOfTrakBox.length;
    paramInt = 0;
    if (paramInt < j)
    {
      Object localObject = arrayOfTrakBox[paramInt];
      ((TrakBox)localObject).setDuration(rescale(((TrakBox)localObject).getDuration(), i));
      localObject = ((TrakBox)localObject).getEdits();
      if (localObject == null) {}
      for (;;)
      {
        paramInt += 1;
        break;
        localObject = ((List)localObject).listIterator();
        while (((ListIterator)localObject).hasNext())
        {
          Edit localEdit = (Edit)((ListIterator)localObject).next();
          ((ListIterator)localObject).set(new Edit(rescale(localEdit.getDuration(), i), localEdit.getMediaTime(), localEdit.getRate()));
        }
      }
    }
    setDuration(rescale(getDuration(), i));
  }
  
  public List<TrakBox> getAudioTracks()
  {
    ArrayList localArrayList = new ArrayList();
    TrakBox[] arrayOfTrakBox = getTracks();
    int j = arrayOfTrakBox.length;
    int i = 0;
    while (i < j)
    {
      TrakBox localTrakBox = arrayOfTrakBox[i];
      if (localTrakBox.isAudio()) {
        localArrayList.add(localTrakBox);
      }
      i += 1;
    }
    return localArrayList;
  }
  
  public Size getDisplaySize()
  {
    TrakBox localTrakBox = getVideoTrack();
    if (localTrakBox == null) {
      return null;
    }
    Object localObject = (ClearApertureBox)NodeBox.findFirst(localTrakBox, ClearApertureBox.class, new String[] { "tapt", "clef" });
    if (localObject != null) {
      return applyMatrix(localTrakBox, new Size((int)((ClearApertureBox)localObject).getWidth(), (int)((ClearApertureBox)localObject).getHeight()));
    }
    localObject = (Box)((SampleDescriptionBox)NodeBox.findFirst(localTrakBox, SampleDescriptionBox.class, new String[] { "mdia", "minf", "stbl", "stsd" })).getBoxes().get(0);
    if ((localObject == null) || (!(localObject instanceof VideoSampleEntry))) {
      return null;
    }
    localObject = (VideoSampleEntry)localObject;
    Rational localRational = localTrakBox.getPAR();
    return applyMatrix(localTrakBox, new Size(((VideoSampleEntry)localObject).getWidth() * localRational.getNum() / localRational.getDen(), ((VideoSampleEntry)localObject).getHeight()));
  }
  
  public long getDuration()
  {
    return getMovieHeader().getDuration();
  }
  
  public Size getStoredSize()
  {
    Object localObject = getVideoTrack();
    if (localObject == null) {
      return null;
    }
    EncodedPixelBox localEncodedPixelBox = (EncodedPixelBox)NodeBox.findFirst((NodeBox)localObject, EncodedPixelBox.class, new String[] { "tapt", "enof" });
    if (localEncodedPixelBox != null) {
      return new Size((int)localEncodedPixelBox.getWidth(), (int)localEncodedPixelBox.getHeight());
    }
    localObject = (Box)((SampleDescriptionBox)NodeBox.findFirst((NodeBox)localObject, SampleDescriptionBox.class, new String[] { "mdia", "minf", "stbl", "stsd" })).getBoxes().get(0);
    if ((localObject == null) || (!(localObject instanceof VideoSampleEntry))) {
      return null;
    }
    localObject = (VideoSampleEntry)localObject;
    return new Size(((VideoSampleEntry)localObject).getWidth(), ((VideoSampleEntry)localObject).getHeight());
  }
  
  public TrakBox getTimecodeTrack()
  {
    TrakBox[] arrayOfTrakBox = getTracks();
    int j = arrayOfTrakBox.length;
    int i = 0;
    while (i < j)
    {
      TrakBox localTrakBox = arrayOfTrakBox[i];
      if (localTrakBox.isTimecode()) {
        return localTrakBox;
      }
      i += 1;
    }
    return null;
  }
  
  public int getTimescale()
  {
    return getMovieHeader().getTimescale();
  }
  
  public TrakBox[] getTracks()
  {
    return (TrakBox[])findAll(this, TrakBox.class, new String[] { "trak" });
  }
  
  public TrakBox getVideoTrack()
  {
    TrakBox[] arrayOfTrakBox = getTracks();
    int j = arrayOfTrakBox.length;
    int i = 0;
    while (i < j)
    {
      TrakBox localTrakBox = arrayOfTrakBox[i];
      if (localTrakBox.isVideo()) {
        return localTrakBox;
      }
      i += 1;
    }
    return null;
  }
  
  public boolean isPureRefMovie(MovieBox paramMovieBox)
  {
    boolean bool = true;
    paramMovieBox = paramMovieBox.getTracks();
    int j = paramMovieBox.length;
    int i = 0;
    while (i < j)
    {
      bool &= paramMovieBox[i].isPureRef();
      i += 1;
    }
    return bool;
  }
  
  public long rescale(long paramLong1, long paramLong2)
  {
    return getTimescale() * paramLong1 / paramLong2;
  }
  
  public void setDuration(long paramLong)
  {
    getMovieHeader().setDuration(paramLong);
  }
  
  public void updateDuration()
  {
    TrakBox[] arrayOfTrakBox = getTracks();
    long l1 = 2147483647L;
    int j = arrayOfTrakBox.length;
    int i = 0;
    while (i < j)
    {
      TrakBox localTrakBox = arrayOfTrakBox[i];
      long l2 = l1;
      if (localTrakBox.getDuration() < l1) {
        l2 = localTrakBox.getDuration();
      }
      i += 1;
      l1 = l2;
    }
    getMovieHeader().setDuration(l1);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/MovieBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
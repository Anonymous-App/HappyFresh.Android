package org.jcodec;

import java.util.Iterator;
import java.util.List;

public class TrakBox
  extends NodeBox
{
  public TrakBox()
  {
    super(new Header(fourcc()));
  }
  
  public TrakBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "trak";
  }
  
  public void fixMediaTimescale(int paramInt)
  {
    Object localObject = (MediaHeaderBox)Box.findFirst(this, MediaHeaderBox.class, new String[] { "mdia", "mdhd" });
    int j = ((MediaHeaderBox)localObject).getTimescale();
    ((MediaHeaderBox)localObject).setTimescale(paramInt);
    ((MediaHeaderBox)localObject).setDuration(paramInt * ((MediaHeaderBox)localObject).getDuration() / j);
    localObject = getEdits();
    Edit localEdit;
    if (localObject != null)
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        localEdit = (Edit)((Iterator)localObject).next();
        localEdit.setMediaTime(paramInt * localEdit.getMediaTime() / j);
      }
    }
    localObject = ((TimeToSampleBox)Box.findFirst(this, TimeToSampleBox.class, new String[] { "mdia", "minf", "stbl", "stts" })).getEntries();
    int k = localObject.length;
    int i = 0;
    while (i < k)
    {
      localEdit = localObject[i];
      localEdit.setSampleDuration(localEdit.getSampleDuration() * paramInt / j);
      i += 1;
    }
  }
  
  public Size getCodedSize()
  {
    Object localObject = getSampleEntries()[0];
    if (!(localObject instanceof VideoSampleEntry)) {
      throw new IllegalArgumentException("Not a video track");
    }
    localObject = (VideoSampleEntry)localObject;
    return new Size(((VideoSampleEntry)localObject).getWidth(), ((VideoSampleEntry)localObject).getHeight());
  }
  
  public long getDuration()
  {
    return getTrackHeader().getDuration();
  }
  
  public List<Edit> getEdits()
  {
    EditListBox localEditListBox = (EditListBox)findFirst(this, EditListBox.class, new String[] { "edts", "elst" });
    if (localEditListBox == null) {
      return null;
    }
    return localEditListBox.getEdits();
  }
  
  public int getFrameCount()
  {
    SampleSizesBox localSampleSizesBox = (SampleSizesBox)findFirst(this, SampleSizesBox.class, new String[] { "mdia", "minf", "stbl", "stsz" });
    if (localSampleSizesBox.getDefaultSize() != 0) {
      return localSampleSizesBox.getCount();
    }
    return localSampleSizesBox.getSizes().length;
  }
  
  public String getHandlerType()
  {
    HandlerBox localHandlerBox = (HandlerBox)findFirst(this, HandlerBox.class, new String[] { "mdia", "hdlr" });
    if (localHandlerBox == null) {
      return null;
    }
    return localHandlerBox.getComponentSubType();
  }
  
  public MediaBox getMdia()
  {
    return (MediaBox)findFirst(this, MediaBox.class, new String[] { "mdia" });
  }
  
  public long getMediaDuration()
  {
    return ((MediaHeaderBox)findFirst(this, MediaHeaderBox.class, new String[] { "mdia", "mdhd" })).getDuration();
  }
  
  public String getName()
  {
    NameBox localNameBox = (NameBox)Box.findFirst(this, NameBox.class, new String[] { "udta", "name" });
    if (localNameBox == null) {
      return null;
    }
    return localNameBox.getName();
  }
  
  public Rational getPAR()
  {
    PixelAspectExt localPixelAspectExt = (PixelAspectExt)NodeBox.findFirst(this, PixelAspectExt.class, new String[] { "mdia", "minf", "stbl", "stsd", null, "pasp" });
    if (localPixelAspectExt == null) {
      return new Rational(1, 1);
    }
    return localPixelAspectExt.getRational();
  }
  
  public long getSampleCount()
  {
    return ((SampleSizesBox)NodeBox.findFirst(this, SampleSizesBox.class, new String[] { "mdia", "minf", "stbl", "stsz" })).getCount();
  }
  
  public SampleEntry[] getSampleEntries()
  {
    return (SampleEntry[])NodeBox.findAll(this, SampleEntry.class, new String[] { "mdia", "minf", "stbl", "stsd", null });
  }
  
  public int getTimescale()
  {
    return ((MediaHeaderBox)findFirst(this, MediaHeaderBox.class, new String[] { "mdia", "mdhd" })).getTimescale();
  }
  
  public TrackHeaderBox getTrackHeader()
  {
    return (TrackHeaderBox)findFirst(this, TrackHeaderBox.class, new String[] { "tkhd" });
  }
  
  public boolean hasDataRef()
  {
    Object localObject = getMdia().getMinf().getDinf();
    if (localObject == null) {}
    do
    {
      return false;
      localObject = ((DataInfoBox)localObject).getDref();
    } while (localObject == null);
    boolean bool2 = false;
    localObject = ((DataRefBox)localObject).boxes.iterator();
    if (((Iterator)localObject).hasNext())
    {
      if ((((FullBox)((Iterator)localObject).next()).getFlags() & 0x1) != 1) {}
      for (boolean bool1 = true;; bool1 = false)
      {
        bool2 |= bool1;
        break;
      }
    }
    return bool2;
  }
  
  public boolean isAudio()
  {
    return "soun".equals(getHandlerType());
  }
  
  public boolean isPureRef()
  {
    Object localObject = getMdia().getMinf().getDinf();
    if (localObject == null) {}
    do
    {
      return false;
      localObject = ((DataInfoBox)localObject).getDref();
    } while (localObject == null);
    localObject = ((DataRefBox)localObject).boxes.iterator();
    while (((Iterator)localObject).hasNext()) {
      if ((((FullBox)((Iterator)localObject).next()).getFlags() & 0x1) != 0) {
        return false;
      }
    }
    return true;
  }
  
  public boolean isTimecode()
  {
    return "tmcd".equals(getHandlerType());
  }
  
  public boolean isVideo()
  {
    return "vide".equals(getHandlerType());
  }
  
  public long rescale(long paramLong1, long paramLong2)
  {
    return getTimescale() * paramLong1 / paramLong2;
  }
  
  public void setAperture(Size paramSize1, Size paramSize2)
  {
    removeChildren(new String[] { "tapt" });
    NodeBox localNodeBox = new NodeBox(new Header("tapt"));
    localNodeBox.add(new ClearApertureBox(paramSize2.getWidth(), paramSize2.getHeight()));
    localNodeBox.add(new ProductionApertureBox(paramSize2.getWidth(), paramSize2.getHeight()));
    localNodeBox.add(new EncodedPixelBox(paramSize1.getWidth(), paramSize1.getHeight()));
    add(localNodeBox);
  }
  
  public void setClipRect(short paramShort1, short paramShort2, short paramShort3, short paramShort4)
  {
    NodeBox localNodeBox2 = (NodeBox)NodeBox.findFirst(this, NodeBox.class, new String[] { "clip" });
    NodeBox localNodeBox1 = localNodeBox2;
    if (localNodeBox2 == null)
    {
      localNodeBox1 = new NodeBox(new Header("clip"));
      add(localNodeBox1);
    }
    localNodeBox1.replace("crgn", new ClipRegionBox(paramShort1, paramShort2, paramShort3, paramShort4));
  }
  
  public void setDimensions(Size paramSize)
  {
    getTrackHeader().setWidth(paramSize.getWidth());
    getTrackHeader().setHeight(paramSize.getHeight());
  }
  
  public void setDuration(long paramLong)
  {
    getTrackHeader().setDuration(paramLong);
  }
  
  public void setName(String paramString)
  {
    NodeBox localNodeBox2 = (NodeBox)findFirst(this, NodeBox.class, new String[] { "udta" });
    NodeBox localNodeBox1 = localNodeBox2;
    if (localNodeBox2 == null)
    {
      localNodeBox1 = new NodeBox(new Header("udta"));
      add(localNodeBox1);
    }
    localNodeBox1.removeChildren(new String[] { "name" });
    localNodeBox1.add(new NameBox(paramString));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/TrakBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
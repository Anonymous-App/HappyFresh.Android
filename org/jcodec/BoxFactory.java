package org.jcodec;

import java.util.HashMap;
import java.util.Map;

public class BoxFactory
{
  private static BoxFactory instance = new BoxFactory();
  private Map<String, Class<? extends Box>> mappings = new HashMap();
  
  public BoxFactory()
  {
    this.mappings.put(VideoMediaHeaderBox.fourcc(), VideoMediaHeaderBox.class);
    this.mappings.put(FileTypeBox.fourcc(), FileTypeBox.class);
    this.mappings.put(MovieBox.fourcc(), MovieBox.class);
    this.mappings.put(MovieHeaderBox.fourcc(), MovieHeaderBox.class);
    this.mappings.put(TrakBox.fourcc(), TrakBox.class);
    this.mappings.put(TrackHeaderBox.fourcc(), TrackHeaderBox.class);
    this.mappings.put("edts", NodeBox.class);
    this.mappings.put(EditListBox.fourcc(), EditListBox.class);
    this.mappings.put(MediaBox.fourcc(), MediaBox.class);
    this.mappings.put(MediaHeaderBox.fourcc(), MediaHeaderBox.class);
    this.mappings.put(MediaInfoBox.fourcc(), MediaInfoBox.class);
    this.mappings.put(HandlerBox.fourcc(), HandlerBox.class);
    this.mappings.put(DataInfoBox.fourcc(), DataInfoBox.class);
    this.mappings.put("stbl", NodeBox.class);
    this.mappings.put(SampleDescriptionBox.fourcc(), SampleDescriptionBox.class);
    this.mappings.put(TimeToSampleBox.fourcc(), TimeToSampleBox.class);
    this.mappings.put(SyncSamplesBox.fourcc(), SyncSamplesBox.class);
    this.mappings.put(SampleToChunkBox.fourcc(), SampleToChunkBox.class);
    this.mappings.put(SampleSizesBox.fourcc(), SampleSizesBox.class);
    this.mappings.put(ChunkOffsetsBox.fourcc(), ChunkOffsetsBox.class);
    this.mappings.put("mvex", NodeBox.class);
    this.mappings.put("moof", NodeBox.class);
    this.mappings.put("traf", NodeBox.class);
    this.mappings.put("mfra", NodeBox.class);
    this.mappings.put("skip", NodeBox.class);
    this.mappings.put("meta", LeafBox.class);
    this.mappings.put(DataRefBox.fourcc(), DataRefBox.class);
    this.mappings.put("ipro", NodeBox.class);
    this.mappings.put("sinf", NodeBox.class);
    this.mappings.put(ChunkOffsets64Box.fourcc(), ChunkOffsets64Box.class);
    this.mappings.put(SoundMediaHeaderBox.fourcc(), SoundMediaHeaderBox.class);
    this.mappings.put("clip", NodeBox.class);
    this.mappings.put(ClipRegionBox.fourcc(), ClipRegionBox.class);
    this.mappings.put(LoadSettingsBox.fourcc(), LoadSettingsBox.class);
    this.mappings.put("tapt", NodeBox.class);
    this.mappings.put("gmhd", NodeBox.class);
    this.mappings.put("tmcd", LeafBox.class);
    this.mappings.put("tref", NodeBox.class);
    this.mappings.put(ClearApertureBox.fourcc(), ClearApertureBox.class);
    this.mappings.put(ProductionApertureBox.fourcc(), ProductionApertureBox.class);
    this.mappings.put(EncodedPixelBox.fourcc(), EncodedPixelBox.class);
    this.mappings.put(GenericMediaInfoBox.fourcc(), GenericMediaInfoBox.class);
    this.mappings.put(TimecodeMediaInfoBox.fourcc(), TimecodeMediaInfoBox.class);
    this.mappings.put("udta", NodeBox.class);
    this.mappings.put(CompositionOffsetsBox.fourcc(), CompositionOffsetsBox.class);
    this.mappings.put(NameBox.fourcc(), NameBox.class);
  }
  
  public static BoxFactory getDefault()
  {
    return instance;
  }
  
  public Class<? extends Box> toClass(String paramString)
  {
    return (Class)this.mappings.get(paramString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/BoxFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
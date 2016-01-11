package org.jcodec;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleDescriptionBox
  extends NodeBox
{
  public static final MyFactory FACTORY = new MyFactory();
  
  public SampleDescriptionBox()
  {
    this(new Header(fourcc()));
  }
  
  public SampleDescriptionBox(Header paramHeader)
  {
    super(paramHeader);
    this.factory = FACTORY;
  }
  
  public SampleDescriptionBox(SampleEntry... paramVarArgs)
  {
    this();
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      SampleEntry localSampleEntry = paramVarArgs[i];
      this.boxes.add(localSampleEntry);
      i += 1;
    }
  }
  
  public static String fourcc()
  {
    return "stsd";
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putInt(0);
    paramByteBuffer.putInt(this.boxes.size());
    super.doWrite(paramByteBuffer);
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.getInt();
    paramByteBuffer.getInt();
    super.parse(paramByteBuffer);
  }
  
  public static class MyFactory
    extends BoxFactory
  {
    private Map<String, Class<? extends Box>> handlers = new HashMap();
    
    public MyFactory()
    {
      this.handlers.put("ap4h", VideoSampleEntry.class);
      this.handlers.put("apch", VideoSampleEntry.class);
      this.handlers.put("apcn", VideoSampleEntry.class);
      this.handlers.put("apcs", VideoSampleEntry.class);
      this.handlers.put("apco", VideoSampleEntry.class);
      this.handlers.put("avc1", VideoSampleEntry.class);
      this.handlers.put("cvid", VideoSampleEntry.class);
      this.handlers.put("jpeg", VideoSampleEntry.class);
      this.handlers.put("smc ", VideoSampleEntry.class);
      this.handlers.put("rle ", VideoSampleEntry.class);
      this.handlers.put("rpza", VideoSampleEntry.class);
      this.handlers.put("kpcd", VideoSampleEntry.class);
      this.handlers.put("png ", VideoSampleEntry.class);
      this.handlers.put("mjpa", VideoSampleEntry.class);
      this.handlers.put("mjpb", VideoSampleEntry.class);
      this.handlers.put("SVQ1", VideoSampleEntry.class);
      this.handlers.put("SVQ3", VideoSampleEntry.class);
      this.handlers.put("mp4v", VideoSampleEntry.class);
      this.handlers.put("dvc ", VideoSampleEntry.class);
      this.handlers.put("dvcp", VideoSampleEntry.class);
      this.handlers.put("gif ", VideoSampleEntry.class);
      this.handlers.put("h263", VideoSampleEntry.class);
      this.handlers.put("tiff", VideoSampleEntry.class);
      this.handlers.put("raw ", VideoSampleEntry.class);
      this.handlers.put("2vuY", VideoSampleEntry.class);
      this.handlers.put("yuv2", VideoSampleEntry.class);
      this.handlers.put("v308", VideoSampleEntry.class);
      this.handlers.put("v408", VideoSampleEntry.class);
      this.handlers.put("v216", VideoSampleEntry.class);
      this.handlers.put("v410", VideoSampleEntry.class);
      this.handlers.put("v210", VideoSampleEntry.class);
      this.handlers.put("m2v1", VideoSampleEntry.class);
      this.handlers.put("m1v1", VideoSampleEntry.class);
      this.handlers.put("xd5b", VideoSampleEntry.class);
      this.handlers.put("dv5n", VideoSampleEntry.class);
      this.handlers.put("jp2h", VideoSampleEntry.class);
      this.handlers.put("mjp2", VideoSampleEntry.class);
      this.handlers.put("ac-3", AudioSampleEntry.class);
      this.handlers.put("cac3", AudioSampleEntry.class);
      this.handlers.put("ima4", AudioSampleEntry.class);
      this.handlers.put("aac ", AudioSampleEntry.class);
      this.handlers.put("celp", AudioSampleEntry.class);
      this.handlers.put("hvxc", AudioSampleEntry.class);
      this.handlers.put("twvq", AudioSampleEntry.class);
      this.handlers.put(".mp1", AudioSampleEntry.class);
      this.handlers.put(".mp2", AudioSampleEntry.class);
      this.handlers.put("midi", AudioSampleEntry.class);
      this.handlers.put("apvs", AudioSampleEntry.class);
      this.handlers.put("alac", AudioSampleEntry.class);
      this.handlers.put("aach", AudioSampleEntry.class);
      this.handlers.put("aacl", AudioSampleEntry.class);
      this.handlers.put("aace", AudioSampleEntry.class);
      this.handlers.put("aacf", AudioSampleEntry.class);
      this.handlers.put("aacp", AudioSampleEntry.class);
      this.handlers.put("aacs", AudioSampleEntry.class);
      this.handlers.put("samr", AudioSampleEntry.class);
      this.handlers.put("AUDB", AudioSampleEntry.class);
      this.handlers.put("ilbc", AudioSampleEntry.class);
      this.handlers.put(new String(new byte[] { 109, 115, 0, 17 }), AudioSampleEntry.class);
      this.handlers.put(new String(new byte[] { 109, 115, 0, 49 }), AudioSampleEntry.class);
      this.handlers.put("aes3", AudioSampleEntry.class);
      this.handlers.put("NONE", AudioSampleEntry.class);
      this.handlers.put("raw ", AudioSampleEntry.class);
      this.handlers.put("twos", AudioSampleEntry.class);
      this.handlers.put("sowt", AudioSampleEntry.class);
      this.handlers.put("MAC3 ", AudioSampleEntry.class);
      this.handlers.put("MAC6 ", AudioSampleEntry.class);
      this.handlers.put("ima4", AudioSampleEntry.class);
      this.handlers.put("fl32", AudioSampleEntry.class);
      this.handlers.put("fl64", AudioSampleEntry.class);
      this.handlers.put("in24", AudioSampleEntry.class);
      this.handlers.put("in32", AudioSampleEntry.class);
      this.handlers.put("ulaw", AudioSampleEntry.class);
      this.handlers.put("alaw", AudioSampleEntry.class);
      this.handlers.put("dvca", AudioSampleEntry.class);
      this.handlers.put("QDMC", AudioSampleEntry.class);
      this.handlers.put("QDM2", AudioSampleEntry.class);
      this.handlers.put("Qclp", AudioSampleEntry.class);
      this.handlers.put(".mp3", AudioSampleEntry.class);
      this.handlers.put("mp4a", AudioSampleEntry.class);
      this.handlers.put("lpcm", AudioSampleEntry.class);
      this.handlers.put("tmcd", TimecodeSampleEntry.class);
      this.handlers.put("time", TimecodeSampleEntry.class);
      this.handlers.put("c608", SampleEntry.class);
      this.handlers.put("c708", SampleEntry.class);
      this.handlers.put("text", SampleEntry.class);
    }
    
    public Class<? extends Box> toClass(String paramString)
    {
      return (Class)this.handlers.get(paramString);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/SampleDescriptionBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
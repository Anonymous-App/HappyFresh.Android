package org.jcodec;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AliasBox
  extends FullBox
{
  public static final int AbsolutePath = 2;
  public static final int AppleRemoteAccessDialup = 10;
  public static final int AppleShareServerName = 4;
  public static final int AppleShareUserName = 5;
  public static final int AppleShareZoneName = 3;
  public static final int DirectoryIDs = 1;
  public static final int DirectoryName = 0;
  public static final int DriverName = 6;
  public static final int RevisedAppleShare = 9;
  public static final int UFT16VolumeName = 15;
  public static final int UNIXAbsolutePath = 18;
  public static final int UTF16AbsolutePath = 14;
  public static final int VolumeMountPoint = 19;
  private static Set<Integer> utf16 = new HashSet();
  private int createdLocalDate;
  private String creatorName;
  private List<ExtraField> extra;
  private String fileName;
  private int fileNumber;
  private String fileTypeName;
  private short fsId;
  private short kind;
  private short nlvlFrom;
  private short nlvlTo;
  private int parentDirId;
  private short recordSize;
  private String type;
  private short version;
  private int volumeAttributes;
  private int volumeCreateDate;
  private String volumeName;
  private short volumeSignature;
  private short volumeType;
  
  static
  {
    utf16.add(Integer.valueOf(14));
    utf16.add(Integer.valueOf(15));
  }
  
  public AliasBox()
  {
    super(new Header(fourcc(), 0L));
  }
  
  public AliasBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static AliasBox createSelfRef()
  {
    AliasBox localAliasBox = new AliasBox();
    localAliasBox.setFlags(1);
    return localAliasBox;
  }
  
  public static String fourcc()
  {
    return "alis";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    if ((this.flags & 0x1) != 0) {
      return;
    }
    paramByteBuffer.put(JCodecUtil.asciiString(this.type), 0, 4);
    paramByteBuffer.putShort(this.recordSize);
    paramByteBuffer.putShort(this.version);
    paramByteBuffer.putShort(this.kind);
    NIOUtils.writePascalString(paramByteBuffer, this.volumeName, 27);
    paramByteBuffer.putInt(this.volumeCreateDate);
    paramByteBuffer.putShort(this.volumeSignature);
    paramByteBuffer.putShort(this.volumeType);
    paramByteBuffer.putInt(this.parentDirId);
    NIOUtils.writePascalString(paramByteBuffer, this.fileName, 63);
    paramByteBuffer.putInt(this.fileNumber);
    paramByteBuffer.putInt(this.createdLocalDate);
    paramByteBuffer.put(JCodecUtil.asciiString(this.fileTypeName), 0, 4);
    paramByteBuffer.put(JCodecUtil.asciiString(this.creatorName), 0, 4);
    paramByteBuffer.putShort(this.nlvlFrom);
    paramByteBuffer.putShort(this.nlvlTo);
    paramByteBuffer.putInt(this.volumeAttributes);
    paramByteBuffer.putShort(this.fsId);
    paramByteBuffer.put(new byte[10]);
    Iterator localIterator = this.extra.iterator();
    while (localIterator.hasNext())
    {
      ExtraField localExtraField = (ExtraField)localIterator.next();
      paramByteBuffer.putShort(localExtraField.type);
      paramByteBuffer.putShort((short)localExtraField.len);
      paramByteBuffer.put(localExtraField.data);
    }
    paramByteBuffer.putShort((short)-1);
    paramByteBuffer.putShort((short)0);
  }
  
  protected void dump(StringBuilder paramStringBuilder)
  {
    super.dump(paramStringBuilder);
    paramStringBuilder.append(": ");
    if (isSelfRef())
    {
      paramStringBuilder.append("'self'");
      return;
    }
    paramStringBuilder.append("'" + getUnixPath() + "'");
  }
  
  public List<ExtraField> getExtra()
  {
    return this.extra;
  }
  
  public ExtraField getExtra(int paramInt)
  {
    Iterator localIterator = this.extra.iterator();
    while (localIterator.hasNext())
    {
      ExtraField localExtraField = (ExtraField)localIterator.next();
      if (localExtraField.type == paramInt) {
        return localExtraField;
      }
    }
    return null;
  }
  
  public String getFileName()
  {
    return this.fileName;
  }
  
  public int getRecordSize()
  {
    return this.recordSize;
  }
  
  public String getUnixPath()
  {
    ExtraField localExtraField = getExtra(18);
    if (localExtraField == null) {
      return null;
    }
    return "/" + localExtraField.toString();
  }
  
  public boolean isSelfRef()
  {
    return (this.flags & 0x1) != 0;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    if ((this.flags & 0x1) != 0) {
      return;
    }
    this.type = NIOUtils.readString(paramByteBuffer, 4);
    this.recordSize = paramByteBuffer.getShort();
    this.version = paramByteBuffer.getShort();
    this.kind = paramByteBuffer.getShort();
    this.volumeName = NIOUtils.readPascalString(paramByteBuffer, 27);
    this.volumeCreateDate = paramByteBuffer.getInt();
    this.volumeSignature = paramByteBuffer.getShort();
    this.volumeType = paramByteBuffer.getShort();
    this.parentDirId = paramByteBuffer.getInt();
    this.fileName = NIOUtils.readPascalString(paramByteBuffer, 63);
    this.fileNumber = paramByteBuffer.getInt();
    this.createdLocalDate = paramByteBuffer.getInt();
    this.fileTypeName = NIOUtils.readString(paramByteBuffer, 4);
    this.creatorName = NIOUtils.readString(paramByteBuffer, 4);
    this.nlvlFrom = paramByteBuffer.getShort();
    this.nlvlTo = paramByteBuffer.getShort();
    this.volumeAttributes = paramByteBuffer.getInt();
    this.fsId = paramByteBuffer.getShort();
    NIOUtils.skip(paramByteBuffer, 10);
    this.extra = new ArrayList();
    for (;;)
    {
      short s = paramByteBuffer.getShort();
      if (s == -1) {
        break;
      }
      int i = paramByteBuffer.getShort();
      byte[] arrayOfByte = NIOUtils.toArray(NIOUtils.read(paramByteBuffer, i + 1 & 0xFFFFFFFE));
      if (arrayOfByte == null) {
        break;
      }
      this.extra.add(new ExtraField(s, i, arrayOfByte));
    }
  }
  
  public static class ExtraField
  {
    byte[] data;
    int len;
    short type;
    
    public ExtraField(short paramShort, int paramInt, byte[] paramArrayOfByte)
    {
      this.type = paramShort;
      this.len = paramInt;
      this.data = paramArrayOfByte;
    }
    
    public String toString()
    {
      try
      {
        byte[] arrayOfByte = this.data;
        int i = this.len;
        if (AliasBox.utf16.contains(Short.valueOf(this.type))) {}
        for (String str = "UTF-16";; str = "UTF-8") {
          return new String(arrayOfByte, 0, i, str);
        }
        return null;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/AliasBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package org.jcodec;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EditListBox
  extends FullBox
{
  private List<Edit> edits;
  
  public EditListBox()
  {
    this(new Header(fourcc()));
  }
  
  public EditListBox(List<Edit> paramList)
  {
    this();
    this.edits = paramList;
  }
  
  public EditListBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "elst";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(this.edits.size());
    Iterator localIterator = this.edits.iterator();
    while (localIterator.hasNext())
    {
      Edit localEdit = (Edit)localIterator.next();
      paramByteBuffer.putInt((int)localEdit.getDuration());
      paramByteBuffer.putInt((int)localEdit.getMediaTime());
      paramByteBuffer.putInt((int)(localEdit.getRate() * 65536.0F));
    }
  }
  
  protected void dump(StringBuilder paramStringBuilder)
  {
    super.dump(paramStringBuilder);
    paramStringBuilder.append(": ");
    ToJSON.toJSON(this, paramStringBuilder, new String[] { "edits" });
  }
  
  public List<Edit> getEdits()
  {
    return this.edits;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    this.edits = new ArrayList();
    long l = paramByteBuffer.getInt();
    int i = 0;
    while (i < l)
    {
      int j = paramByteBuffer.getInt();
      int k = paramByteBuffer.getInt();
      float f = paramByteBuffer.getInt() / 65536.0F;
      this.edits.add(new Edit(j, k, f));
      i += 1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/EditListBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
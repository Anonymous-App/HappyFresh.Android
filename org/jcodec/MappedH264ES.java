package org.jcodec;

import java.nio.ByteBuffer;

public class MappedH264ES
{
  private ByteBuffer bb;
  private int frameNo;
  private IntObjectMap<PictureParameterSet> pps = new IntObjectMap();
  private int prevFrameNum;
  private int prevFrameNumOffset;
  private int prevPicOrderCntLsb;
  private int prevPicOrderCntMsb;
  private SliceHeaderReader shr;
  private IntObjectMap<SeqParameterSet> sps = new IntObjectMap();
  
  public MappedH264ES(ByteBuffer paramByteBuffer)
  {
    this.bb = paramByteBuffer;
    this.shr = new SliceHeaderReader();
    this.frameNo = 0;
  }
  
  private int calcPOC0(NALUnit paramNALUnit, SliceHeader paramSliceHeader)
  {
    int j = paramSliceHeader.pic_order_cnt_lsb;
    int i = 1 << paramSliceHeader.sps.log2_max_pic_order_cnt_lsb_minus4 + 4;
    if ((j < this.prevPicOrderCntLsb) && (this.prevPicOrderCntLsb - j >= i / 2)) {
      i = this.prevPicOrderCntMsb + i;
    }
    for (;;)
    {
      if (paramNALUnit.nal_ref_idc != 0)
      {
        this.prevPicOrderCntMsb = i;
        this.prevPicOrderCntLsb = j;
      }
      return i + j;
      if ((j > this.prevPicOrderCntLsb) && (j - this.prevPicOrderCntLsb > i / 2)) {
        i = this.prevPicOrderCntMsb - i;
      } else {
        i = this.prevPicOrderCntMsb;
      }
    }
  }
  
  private int calcPOC1(int paramInt, NALUnit paramNALUnit, SliceHeader paramSliceHeader)
  {
    if (paramSliceHeader.sps.num_ref_frames_in_pic_order_cnt_cycle == 0) {
      paramInt = 0;
    }
    int j = paramInt;
    if (paramNALUnit.nal_ref_idc == 0)
    {
      j = paramInt;
      if (paramInt > 0) {
        j = paramInt - 1;
      }
    }
    paramInt = 0;
    int i = 0;
    while (i < paramSliceHeader.sps.num_ref_frames_in_pic_order_cnt_cycle)
    {
      paramInt += paramSliceHeader.sps.offsetForRefFrame[i];
      i += 1;
    }
    if (j > 0)
    {
      i = (j - 1) / paramSliceHeader.sps.num_ref_frames_in_pic_order_cnt_cycle;
      int m = paramSliceHeader.sps.num_ref_frames_in_pic_order_cnt_cycle;
      paramInt = i * paramInt;
      int k = 0;
      for (;;)
      {
        i = paramInt;
        if (k > (j - 1) % m) {
          break;
        }
        paramInt += paramSliceHeader.sps.offsetForRefFrame[k];
        k += 1;
      }
    }
    i = 0;
    paramInt = i;
    if (paramNALUnit.nal_ref_idc == 0) {
      paramInt = i + paramSliceHeader.sps.offset_for_non_ref_pic;
    }
    return paramSliceHeader.delta_pic_order_cnt[0] + paramInt;
  }
  
  private int calcPOC2(int paramInt, NALUnit paramNALUnit, SliceHeader paramSliceHeader)
  {
    if (paramNALUnit.nal_ref_idc == 0) {
      return paramInt * 2 - 1;
    }
    return paramInt * 2;
  }
  
  private int calcPoc(int paramInt, NALUnit paramNALUnit, SliceHeader paramSliceHeader)
  {
    if (paramSliceHeader.sps.pic_order_cnt_type == 0) {
      return calcPOC0(paramNALUnit, paramSliceHeader);
    }
    if (paramSliceHeader.sps.pic_order_cnt_type == 1) {
      return calcPOC1(paramInt, paramNALUnit, paramSliceHeader);
    }
    return calcPOC2(paramInt, paramNALUnit, paramSliceHeader);
  }
  
  private boolean detectGap(SliceHeader paramSliceHeader, int paramInt)
  {
    return (paramSliceHeader.frame_num != this.prevFrameNum) && (paramSliceHeader.frame_num != (this.prevFrameNum + 1) % paramInt);
  }
  
  private boolean detectMMCO5(RefPicMarking paramRefPicMarking)
  {
    if (paramRefPicMarking == null) {}
    for (;;)
    {
      return false;
      paramRefPicMarking = paramRefPicMarking.getInstructions();
      int j = paramRefPicMarking.length;
      int i = 0;
      while (i < j)
      {
        if (paramRefPicMarking[i].getType() == RefPicMarking.InstrType.CLEAR) {
          return true;
        }
        i += 1;
      }
    }
  }
  
  private Packet detectPoc(ByteBuffer paramByteBuffer, NALUnit paramNALUnit, SliceHeader paramSliceHeader)
  {
    int i = 1 << paramSliceHeader.sps.log2_max_frame_num_minus4 + 4;
    if (detectGap(paramSliceHeader, i)) {
      issueNonExistingPic(paramSliceHeader, i);
    }
    int j = updateFrameNumber(paramSliceHeader.frame_num, i, detectMMCO5(paramSliceHeader.refPicMarkingNonIDR));
    i = 0;
    if (paramNALUnit.type == NALUnitType.NON_IDR_SLICE) {
      i = calcPoc(j, paramNALUnit, paramSliceHeader);
    }
    long l1 = j;
    j = this.frameNo;
    this.frameNo = (j + 1);
    long l2 = j;
    if (paramNALUnit.type == NALUnitType.IDR_SLICE) {}
    for (boolean bool = true;; bool = false) {
      return new Packet(paramByteBuffer, l1, 1L, 1L, l2, bool, null, i);
    }
  }
  
  private void issueNonExistingPic(SliceHeader paramSliceHeader, int paramInt)
  {
    this.prevFrameNum = ((this.prevFrameNum + 1) % paramInt);
  }
  
  private SliceHeader readSliceHeader(ByteBuffer paramByteBuffer, NALUnit paramNALUnit)
  {
    paramByteBuffer = new BitReader(paramByteBuffer);
    SliceHeader localSliceHeader = this.shr.readPart1(paramByteBuffer);
    PictureParameterSet localPictureParameterSet = (PictureParameterSet)this.pps.get(localSliceHeader.pic_parameter_set_id);
    this.shr.readPart2(localSliceHeader, paramNALUnit, (SeqParameterSet)this.sps.get(localPictureParameterSet.seq_parameter_set_id), localPictureParameterSet, paramByteBuffer);
    return localSliceHeader;
  }
  
  private boolean sameFrame(NALUnit paramNALUnit1, NALUnit paramNALUnit2, SliceHeader paramSliceHeader1, SliceHeader paramSliceHeader2)
  {
    if (paramSliceHeader1.pic_parameter_set_id != paramSliceHeader2.pic_parameter_set_id) {}
    label171:
    label175:
    for (;;)
    {
      return false;
      if (paramSliceHeader1.frame_num == paramSliceHeader2.frame_num)
      {
        SeqParameterSet localSeqParameterSet = paramSliceHeader1.sps;
        if (((localSeqParameterSet.pic_order_cnt_type != 0) || (paramSliceHeader1.pic_order_cnt_lsb == paramSliceHeader2.pic_order_cnt_lsb)) && ((localSeqParameterSet.pic_order_cnt_type != 1) || ((paramSliceHeader1.delta_pic_order_cnt[0] == paramSliceHeader2.delta_pic_order_cnt[0]) && (paramSliceHeader1.delta_pic_order_cnt[1] == paramSliceHeader2.delta_pic_order_cnt[1]))) && (((paramNALUnit1.nal_ref_idc != 0) && (paramNALUnit2.nal_ref_idc != 0)) || (paramNALUnit1.nal_ref_idc == paramNALUnit2.nal_ref_idc)))
        {
          int i;
          if (paramNALUnit1.type == NALUnitType.IDR_SLICE)
          {
            i = 1;
            if (paramNALUnit2.type != NALUnitType.IDR_SLICE) {
              break label171;
            }
          }
          for (int j = 1;; j = 0)
          {
            if ((i != j) || (paramSliceHeader1.idr_pic_id != paramSliceHeader2.idr_pic_id)) {
              break label175;
            }
            return true;
            i = 0;
            break;
          }
        }
      }
    }
  }
  
  private int updateFrameNumber(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (this.prevFrameNum > paramInt1) {}
    for (int i = this.prevFrameNumOffset + paramInt2;; i = this.prevFrameNumOffset)
    {
      paramInt2 = paramInt1;
      if (paramBoolean) {
        paramInt2 = 0;
      }
      this.prevFrameNum = paramInt2;
      this.prevFrameNumOffset = i;
      return i + paramInt1;
    }
  }
  
  public PictureParameterSet[] getPps()
  {
    return (PictureParameterSet[])this.pps.values(new PictureParameterSet[0]);
  }
  
  public SeqParameterSet[] getSps()
  {
    return (SeqParameterSet[])this.sps.values(new SeqParameterSet[0]);
  }
  
  public Packet nextFrame()
  {
    ByteBuffer localByteBuffer = this.bb.duplicate();
    Object localObject1 = null;
    Object localObject2 = null;
    for (;;)
    {
      this.bb.mark();
      Object localObject4 = H264Utils.nextNALUnit(this.bb);
      if (localObject4 == null) {}
      Object localObject3;
      for (;;)
      {
        localByteBuffer.limit(this.bb.position());
        if (localObject2 != null) {
          break label190;
        }
        return null;
        localObject3 = NALUnit.read((ByteBuffer)localObject4);
        if ((((NALUnit)localObject3).type != NALUnitType.IDR_SLICE) && (((NALUnit)localObject3).type != NALUnitType.NON_IDR_SLICE)) {
          break label128;
        }
        localObject4 = readSliceHeader((ByteBuffer)localObject4, (NALUnit)localObject3);
        if ((localObject1 == null) || (localObject2 == null) || (sameFrame((NALUnit)localObject1, (NALUnit)localObject3, (SliceHeader)localObject2, (SliceHeader)localObject4))) {
          break;
        }
        this.bb.reset();
      }
      localObject2 = localObject4;
      localObject1 = localObject3;
      continue;
      label128:
      if (((NALUnit)localObject3).type == NALUnitType.PPS)
      {
        localObject3 = PictureParameterSet.read((ByteBuffer)localObject4);
        this.pps.put(((PictureParameterSet)localObject3).pic_parameter_set_id, localObject3);
      }
      else if (((NALUnit)localObject3).type == NALUnitType.SPS)
      {
        localObject3 = SeqParameterSet.read((ByteBuffer)localObject4);
        this.sps.put(((SeqParameterSet)localObject3).seq_parameter_set_id, localObject3);
      }
    }
    label190:
    return detectPoc(localByteBuffer, (NALUnit)localObject1, (SliceHeader)localObject2);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/MappedH264ES.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
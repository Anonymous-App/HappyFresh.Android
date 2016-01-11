package org.jcodec;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class PictureParameterSet
{
  public int[] bottom_right;
  public int chroma_qp_index_offset;
  public boolean constrained_intra_pred_flag;
  public boolean deblocking_filter_control_present_flag;
  public boolean entropy_coding_mode_flag;
  public PPSExt extended;
  public int[] num_ref_idx_active_minus1 = new int[2];
  public int num_slice_groups_minus1;
  public int pic_init_qp_minus26;
  public int pic_init_qs_minus26;
  public boolean pic_order_present_flag;
  public int pic_parameter_set_id;
  public boolean redundant_pic_cnt_present_flag;
  public int[] run_length_minus1;
  public int seq_parameter_set_id;
  public boolean slice_group_change_direction_flag;
  public int slice_group_change_rate_minus1;
  public int[] slice_group_id;
  public int slice_group_map_type;
  public int[] top_left;
  public int weighted_bipred_idc;
  public boolean weighted_pred_flag;
  
  public static PictureParameterSet read(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer = new BitReader(paramByteBuffer);
    PictureParameterSet localPictureParameterSet = new PictureParameterSet();
    localPictureParameterSet.pic_parameter_set_id = CAVLCReader.readUE(paramByteBuffer, "PPS: pic_parameter_set_id");
    localPictureParameterSet.seq_parameter_set_id = CAVLCReader.readUE(paramByteBuffer, "PPS: seq_parameter_set_id");
    localPictureParameterSet.entropy_coding_mode_flag = CAVLCReader.readBool(paramByteBuffer, "PPS: entropy_coding_mode_flag");
    localPictureParameterSet.pic_order_present_flag = CAVLCReader.readBool(paramByteBuffer, "PPS: pic_order_present_flag");
    localPictureParameterSet.num_slice_groups_minus1 = CAVLCReader.readUE(paramByteBuffer, "PPS: num_slice_groups_minus1");
    int i;
    label434:
    int j;
    if (localPictureParameterSet.num_slice_groups_minus1 > 0)
    {
      localPictureParameterSet.slice_group_map_type = CAVLCReader.readUE(paramByteBuffer, "PPS: slice_group_map_type");
      localPictureParameterSet.top_left = new int[localPictureParameterSet.num_slice_groups_minus1 + 1];
      localPictureParameterSet.bottom_right = new int[localPictureParameterSet.num_slice_groups_minus1 + 1];
      localPictureParameterSet.run_length_minus1 = new int[localPictureParameterSet.num_slice_groups_minus1 + 1];
      if (localPictureParameterSet.slice_group_map_type == 0)
      {
        i = 0;
        while (i <= localPictureParameterSet.num_slice_groups_minus1)
        {
          localPictureParameterSet.run_length_minus1[i] = CAVLCReader.readUE(paramByteBuffer, "PPS: run_length_minus1");
          i += 1;
        }
      }
      if (localPictureParameterSet.slice_group_map_type == 2)
      {
        i = 0;
        while (i < localPictureParameterSet.num_slice_groups_minus1)
        {
          localPictureParameterSet.top_left[i] = CAVLCReader.readUE(paramByteBuffer, "PPS: top_left");
          localPictureParameterSet.bottom_right[i] = CAVLCReader.readUE(paramByteBuffer, "PPS: bottom_right");
          i += 1;
        }
      }
      if ((localPictureParameterSet.slice_group_map_type == 3) || (localPictureParameterSet.slice_group_map_type == 4) || (localPictureParameterSet.slice_group_map_type == 5))
      {
        localPictureParameterSet.slice_group_change_direction_flag = CAVLCReader.readBool(paramByteBuffer, "PPS: slice_group_change_direction_flag");
        localPictureParameterSet.slice_group_change_rate_minus1 = CAVLCReader.readUE(paramByteBuffer, "PPS: slice_group_change_rate_minus1");
      }
    }
    else
    {
      localPictureParameterSet.num_ref_idx_active_minus1 = new int[] { CAVLCReader.readUE(paramByteBuffer, "PPS: num_ref_idx_l0_active_minus1"), CAVLCReader.readUE(paramByteBuffer, "PPS: num_ref_idx_l1_active_minus1") };
      localPictureParameterSet.weighted_pred_flag = CAVLCReader.readBool(paramByteBuffer, "PPS: weighted_pred_flag");
      localPictureParameterSet.weighted_bipred_idc = CAVLCReader.readNBit(paramByteBuffer, 2, "PPS: weighted_bipred_idc");
      localPictureParameterSet.pic_init_qp_minus26 = CAVLCReader.readSE(paramByteBuffer, "PPS: pic_init_qp_minus26");
      localPictureParameterSet.pic_init_qs_minus26 = CAVLCReader.readSE(paramByteBuffer, "PPS: pic_init_qs_minus26");
      localPictureParameterSet.chroma_qp_index_offset = CAVLCReader.readSE(paramByteBuffer, "PPS: chroma_qp_index_offset");
      localPictureParameterSet.deblocking_filter_control_present_flag = CAVLCReader.readBool(paramByteBuffer, "PPS: deblocking_filter_control_present_flag");
      localPictureParameterSet.constrained_intra_pred_flag = CAVLCReader.readBool(paramByteBuffer, "PPS: constrained_intra_pred_flag");
      localPictureParameterSet.redundant_pic_cnt_present_flag = CAVLCReader.readBool(paramByteBuffer, "PPS: redundant_pic_cnt_present_flag");
      if (!CAVLCReader.moreRBSPData(paramByteBuffer)) {
        break label685;
      }
      localPictureParameterSet.extended = new PPSExt();
      localPictureParameterSet.extended.transform_8x8_mode_flag = CAVLCReader.readBool(paramByteBuffer, "PPS: transform_8x8_mode_flag");
      if (!CAVLCReader.readBool(paramByteBuffer, "PPS: pic_scaling_matrix_present_flag")) {
        break label671;
      }
      i = 0;
      if (!localPictureParameterSet.extended.transform_8x8_mode_flag) {
        break label641;
      }
      j = 1;
      label447:
      if (i >= j * 2 + 6) {
        break label671;
      }
      if (CAVLCReader.readBool(paramByteBuffer, "PPS: pic_scaling_list_present_flag"))
      {
        localPictureParameterSet.extended.scalindMatrix.ScalingList4x4 = new ScalingList[8];
        localPictureParameterSet.extended.scalindMatrix.ScalingList8x8 = new ScalingList[8];
        if (i >= 6) {
          break label646;
        }
        localPictureParameterSet.extended.scalindMatrix.ScalingList4x4[i] = ScalingList.read(paramByteBuffer, 16);
      }
    }
    for (;;)
    {
      i += 1;
      break label434;
      if (localPictureParameterSet.slice_group_map_type != 6) {
        break;
      }
      if (localPictureParameterSet.num_slice_groups_minus1 + 1 > 4) {
        i = 3;
      }
      for (;;)
      {
        int k = CAVLCReader.readUE(paramByteBuffer, "PPS: pic_size_in_map_units_minus1");
        localPictureParameterSet.slice_group_id = new int[k + 1];
        j = 0;
        while (j <= k)
        {
          localPictureParameterSet.slice_group_id[j] = CAVLCReader.readU(paramByteBuffer, i, "PPS: slice_group_id [" + j + "]f");
          j += 1;
        }
        break;
        if (localPictureParameterSet.num_slice_groups_minus1 + 1 > 2) {
          i = 2;
        } else {
          i = 1;
        }
      }
      label641:
      j = 0;
      break label447;
      label646:
      localPictureParameterSet.extended.scalindMatrix.ScalingList8x8[(i - 6)] = ScalingList.read(paramByteBuffer, 64);
    }
    label671:
    localPictureParameterSet.extended.second_chroma_qp_index_offset = CAVLCReader.readSE(paramByteBuffer, "PPS: second_chroma_qp_index_offset");
    label685:
    return localPictureParameterSet;
  }
  
  public PictureParameterSet copy()
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(2048);
    write(localByteBuffer);
    localByteBuffer.flip();
    return read(localByteBuffer);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (PictureParameterSet)paramObject;
      if (!Arrays.equals(this.bottom_right, ((PictureParameterSet)paramObject).bottom_right)) {
        return false;
      }
      if (this.chroma_qp_index_offset != ((PictureParameterSet)paramObject).chroma_qp_index_offset) {
        return false;
      }
      if (this.constrained_intra_pred_flag != ((PictureParameterSet)paramObject).constrained_intra_pred_flag) {
        return false;
      }
      if (this.deblocking_filter_control_present_flag != ((PictureParameterSet)paramObject).deblocking_filter_control_present_flag) {
        return false;
      }
      if (this.entropy_coding_mode_flag != ((PictureParameterSet)paramObject).entropy_coding_mode_flag) {
        return false;
      }
      if (this.extended == null)
      {
        if (((PictureParameterSet)paramObject).extended != null) {
          return false;
        }
      }
      else if (!this.extended.equals(((PictureParameterSet)paramObject).extended)) {
        return false;
      }
      if (this.num_ref_idx_active_minus1[0] != paramObject.num_ref_idx_active_minus1[0]) {
        return false;
      }
      if (this.num_ref_idx_active_minus1[1] != paramObject.num_ref_idx_active_minus1[1]) {
        return false;
      }
      if (this.num_slice_groups_minus1 != ((PictureParameterSet)paramObject).num_slice_groups_minus1) {
        return false;
      }
      if (this.pic_init_qp_minus26 != ((PictureParameterSet)paramObject).pic_init_qp_minus26) {
        return false;
      }
      if (this.pic_init_qs_minus26 != ((PictureParameterSet)paramObject).pic_init_qs_minus26) {
        return false;
      }
      if (this.pic_order_present_flag != ((PictureParameterSet)paramObject).pic_order_present_flag) {
        return false;
      }
      if (this.pic_parameter_set_id != ((PictureParameterSet)paramObject).pic_parameter_set_id) {
        return false;
      }
      if (this.redundant_pic_cnt_present_flag != ((PictureParameterSet)paramObject).redundant_pic_cnt_present_flag) {
        return false;
      }
      if (!Arrays.equals(this.run_length_minus1, ((PictureParameterSet)paramObject).run_length_minus1)) {
        return false;
      }
      if (this.seq_parameter_set_id != ((PictureParameterSet)paramObject).seq_parameter_set_id) {
        return false;
      }
      if (this.slice_group_change_direction_flag != ((PictureParameterSet)paramObject).slice_group_change_direction_flag) {
        return false;
      }
      if (this.slice_group_change_rate_minus1 != ((PictureParameterSet)paramObject).slice_group_change_rate_minus1) {
        return false;
      }
      if (!Arrays.equals(this.slice_group_id, ((PictureParameterSet)paramObject).slice_group_id)) {
        return false;
      }
      if (this.slice_group_map_type != ((PictureParameterSet)paramObject).slice_group_map_type) {
        return false;
      }
      if (!Arrays.equals(this.top_left, ((PictureParameterSet)paramObject).top_left)) {
        return false;
      }
      if (this.weighted_bipred_idc != ((PictureParameterSet)paramObject).weighted_bipred_idc) {
        return false;
      }
    } while (this.weighted_pred_flag == ((PictureParameterSet)paramObject).weighted_pred_flag);
    return false;
  }
  
  public int hashCode()
  {
    int i3 = 1231;
    int i4 = Arrays.hashCode(this.bottom_right);
    int i5 = this.chroma_qp_index_offset;
    int i;
    int j;
    label42:
    int k;
    label53:
    int m;
    label63:
    int i6;
    int i7;
    int i8;
    int i9;
    int i10;
    int n;
    label109:
    int i11;
    int i1;
    label127:
    int i12;
    int i13;
    int i2;
    label154:
    int i14;
    int i15;
    int i16;
    int i17;
    int i18;
    if (this.constrained_intra_pred_flag)
    {
      i = 1231;
      if (!this.deblocking_filter_control_present_flag) {
        break label339;
      }
      j = 1231;
      if (!this.entropy_coding_mode_flag) {
        break label346;
      }
      k = 1231;
      if (this.extended != null) {
        break label353;
      }
      m = 0;
      i6 = this.num_ref_idx_active_minus1[0];
      i7 = this.num_ref_idx_active_minus1[1];
      i8 = this.num_slice_groups_minus1;
      i9 = this.pic_init_qp_minus26;
      i10 = this.pic_init_qs_minus26;
      if (!this.pic_order_present_flag) {
        break label365;
      }
      n = 1231;
      i11 = this.pic_parameter_set_id;
      if (!this.redundant_pic_cnt_present_flag) {
        break label373;
      }
      i1 = 1231;
      i12 = Arrays.hashCode(this.run_length_minus1);
      i13 = this.seq_parameter_set_id;
      if (!this.slice_group_change_direction_flag) {
        break label381;
      }
      i2 = 1231;
      i14 = this.slice_group_change_rate_minus1;
      i15 = Arrays.hashCode(this.slice_group_id);
      i16 = this.slice_group_map_type;
      i17 = Arrays.hashCode(this.top_left);
      i18 = this.weighted_bipred_idc;
      if (!this.weighted_pred_flag) {
        break label389;
      }
    }
    for (;;)
    {
      return ((((((((((((((((((((((i4 + 31) * 31 + i5) * 31 + i) * 31 + j) * 31 + k) * 31 + m) * 31 + i6) * 31 + i7) * 31 + i8) * 31 + i9) * 31 + i10) * 31 + n) * 31 + i11) * 31 + i1) * 31 + i12) * 31 + i13) * 31 + i2) * 31 + i14) * 31 + i15) * 31 + i16) * 31 + i17) * 31 + i18) * 31 + i3;
      i = 1237;
      break;
      label339:
      j = 1237;
      break label42;
      label346:
      k = 1237;
      break label53;
      label353:
      m = this.extended.hashCode();
      break label63;
      label365:
      n = 1237;
      break label109;
      label373:
      i1 = 1237;
      break label127;
      label381:
      i2 = 1237;
      break label154;
      label389:
      i3 = 1237;
    }
  }
  
  public void write(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer = new BitWriter(paramByteBuffer);
    CAVLCWriter.writeUE(paramByteBuffer, this.pic_parameter_set_id, "PPS: pic_parameter_set_id");
    CAVLCWriter.writeUE(paramByteBuffer, this.seq_parameter_set_id, "PPS: seq_parameter_set_id");
    CAVLCWriter.writeBool(paramByteBuffer, this.entropy_coding_mode_flag, "PPS: entropy_coding_mode_flag");
    CAVLCWriter.writeBool(paramByteBuffer, this.pic_order_present_flag, "PPS: pic_order_present_flag");
    CAVLCWriter.writeUE(paramByteBuffer, this.num_slice_groups_minus1, "PPS: num_slice_groups_minus1");
    int i;
    label356:
    label377:
    int j;
    if (this.num_slice_groups_minus1 > 0)
    {
      CAVLCWriter.writeUE(paramByteBuffer, this.slice_group_map_type, "PPS: slice_group_map_type");
      int[] arrayOfInt1 = new int[1];
      int[] arrayOfInt2 = new int[1];
      int[] arrayOfInt3 = new int[1];
      if (this.slice_group_map_type == 0)
      {
        i = 0;
        while (i <= this.num_slice_groups_minus1)
        {
          CAVLCWriter.writeUE(paramByteBuffer, arrayOfInt3[i], "PPS: ");
          i += 1;
        }
      }
      if (this.slice_group_map_type == 2)
      {
        i = 0;
        while (i < this.num_slice_groups_minus1)
        {
          CAVLCWriter.writeUE(paramByteBuffer, arrayOfInt1[i], "PPS: ");
          CAVLCWriter.writeUE(paramByteBuffer, arrayOfInt2[i], "PPS: ");
          i += 1;
        }
      }
      if ((this.slice_group_map_type == 3) || (this.slice_group_map_type == 4) || (this.slice_group_map_type == 5))
      {
        CAVLCWriter.writeBool(paramByteBuffer, this.slice_group_change_direction_flag, "PPS: slice_group_change_direction_flag");
        CAVLCWriter.writeUE(paramByteBuffer, this.slice_group_change_rate_minus1, "PPS: slice_group_change_rate_minus1");
      }
    }
    else
    {
      CAVLCWriter.writeUE(paramByteBuffer, this.num_ref_idx_active_minus1[0], "PPS: num_ref_idx_l0_active_minus1");
      CAVLCWriter.writeUE(paramByteBuffer, this.num_ref_idx_active_minus1[1], "PPS: num_ref_idx_l1_active_minus1");
      CAVLCWriter.writeBool(paramByteBuffer, this.weighted_pred_flag, "PPS: weighted_pred_flag");
      CAVLCWriter.writeNBit(paramByteBuffer, this.weighted_bipred_idc, 2, "PPS: weighted_bipred_idc");
      CAVLCWriter.writeSE(paramByteBuffer, this.pic_init_qp_minus26, "PPS: pic_init_qp_minus26");
      CAVLCWriter.writeSE(paramByteBuffer, this.pic_init_qs_minus26, "PPS: pic_init_qs_minus26");
      CAVLCWriter.writeSE(paramByteBuffer, this.chroma_qp_index_offset, "PPS: chroma_qp_index_offset");
      CAVLCWriter.writeBool(paramByteBuffer, this.deblocking_filter_control_present_flag, "PPS: deblocking_filter_control_present_flag");
      CAVLCWriter.writeBool(paramByteBuffer, this.constrained_intra_pred_flag, "PPS: constrained_intra_pred_flag");
      CAVLCWriter.writeBool(paramByteBuffer, this.redundant_pic_cnt_present_flag, "PPS: redundant_pic_cnt_present_flag");
      if (this.extended == null) {
        break label659;
      }
      CAVLCWriter.writeBool(paramByteBuffer, this.extended.transform_8x8_mode_flag, "PPS: transform_8x8_mode_flag");
      if (this.extended.scalindMatrix == null) {
        break label552;
      }
      bool = true;
      CAVLCWriter.writeBool(paramByteBuffer, bool, "PPS: scalindMatrix");
      if (this.extended.scalindMatrix == null) {
        break label645;
      }
      i = 0;
      if (!this.extended.transform_8x8_mode_flag) {
        break label558;
      }
      j = 1;
      label389:
      if (i >= j * 2 + 6) {
        break label645;
      }
      if (i >= 6) {
        break label569;
      }
      if (this.extended.scalindMatrix.ScalingList4x4[i] == null) {
        break label563;
      }
    }
    label552:
    label558:
    label563:
    for (boolean bool = true;; bool = false)
    {
      CAVLCWriter.writeBool(paramByteBuffer, bool, "PPS: ");
      if (this.extended.scalindMatrix.ScalingList4x4[i] != null) {
        this.extended.scalindMatrix.ScalingList4x4[i].write(paramByteBuffer);
      }
      i += 1;
      break label377;
      if (this.slice_group_map_type != 6) {
        break;
      }
      if (this.num_slice_groups_minus1 + 1 > 4) {
        i = 3;
      }
      for (;;)
      {
        CAVLCWriter.writeUE(paramByteBuffer, this.slice_group_id.length, "PPS: ");
        j = 0;
        while (j <= this.slice_group_id.length)
        {
          CAVLCWriter.writeU(paramByteBuffer, this.slice_group_id[j], i);
          j += 1;
        }
        break;
        if (this.num_slice_groups_minus1 + 1 > 2) {
          i = 2;
        } else {
          i = 1;
        }
      }
      bool = false;
      break label356;
      j = 0;
      break label389;
    }
    label569:
    if (this.extended.scalindMatrix.ScalingList8x8[(i - 6)] != null) {}
    for (bool = true;; bool = false)
    {
      CAVLCWriter.writeBool(paramByteBuffer, bool, "PPS: ");
      if (this.extended.scalindMatrix.ScalingList8x8[(i - 6)] == null) {
        break;
      }
      this.extended.scalindMatrix.ScalingList8x8[(i - 6)].write(paramByteBuffer);
      break;
    }
    label645:
    CAVLCWriter.writeSE(paramByteBuffer, this.extended.second_chroma_qp_index_offset, "PPS: ");
    label659:
    CAVLCWriter.writeTrailingBits(paramByteBuffer);
  }
  
  public static class PPSExt
  {
    public boolean[] pic_scaling_list_present_flag;
    public ScalingMatrix scalindMatrix;
    public int second_chroma_qp_index_offset;
    public boolean transform_8x8_mode_flag;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/PictureParameterSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
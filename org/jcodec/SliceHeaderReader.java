package org.jcodec;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SliceHeaderReader
{
  private static int CeilLog2(int paramInt)
  {
    int i = paramInt - 1;
    paramInt = 0;
    while (i != 0)
    {
      i >>= 1;
      paramInt += 1;
    }
    return paramInt;
  }
  
  private static void readDecoderPicMarking(NALUnit paramNALUnit, SliceHeader paramSliceHeader, BitReader paramBitReader)
  {
    if (paramNALUnit.type == NALUnitType.IDR_SLICE) {
      paramSliceHeader.refPicMarkingIDR = new RefPicMarkingIDR(CAVLCReader.readBool(paramBitReader, "SH: no_output_of_prior_pics_flag"), CAVLCReader.readBool(paramBitReader, "SH: long_term_reference_flag"));
    }
    while (!CAVLCReader.readBool(paramBitReader, "SH: adaptive_ref_pic_marking_mode_flag")) {
      return;
    }
    ArrayList localArrayList = new ArrayList();
    int i = CAVLCReader.readUE(paramBitReader, "SH: memory_management_control_operation");
    paramNALUnit = null;
    switch (i)
    {
    }
    for (;;)
    {
      if (paramNALUnit != null) {
        localArrayList.add(paramNALUnit);
      }
      if (i != 0) {
        break;
      }
      paramSliceHeader.refPicMarkingNonIDR = new RefPicMarking((RefPicMarking.Instruction[])localArrayList.toArray(new RefPicMarking.Instruction[0]));
      return;
      paramNALUnit = new RefPicMarking.Instruction(RefPicMarking.InstrType.REMOVE_SHORT, CAVLCReader.readUE(paramBitReader, "SH: difference_of_pic_nums_minus1") + 1, 0);
      continue;
      paramNALUnit = new RefPicMarking.Instruction(RefPicMarking.InstrType.REMOVE_LONG, CAVLCReader.readUE(paramBitReader, "SH: long_term_pic_num"), 0);
      continue;
      paramNALUnit = new RefPicMarking.Instruction(RefPicMarking.InstrType.CONVERT_INTO_LONG, CAVLCReader.readUE(paramBitReader, "SH: difference_of_pic_nums_minus1") + 1, CAVLCReader.readUE(paramBitReader, "SH: long_term_frame_idx"));
      continue;
      paramNALUnit = new RefPicMarking.Instruction(RefPicMarking.InstrType.TRUNK_LONG, CAVLCReader.readUE(paramBitReader, "SH: max_long_term_frame_idx_plus1") - 1, 0);
      continue;
      paramNALUnit = new RefPicMarking.Instruction(RefPicMarking.InstrType.CLEAR, 0, 0);
      continue;
      paramNALUnit = new RefPicMarking.Instruction(RefPicMarking.InstrType.MARK_LONG, CAVLCReader.readUE(paramBitReader, "SH: long_term_frame_idx"), 0);
    }
  }
  
  private static void readPredWeightTable(SeqParameterSet paramSeqParameterSet, PictureParameterSet paramPictureParameterSet, SliceHeader paramSliceHeader, BitReader paramBitReader)
  {
    paramSliceHeader.pred_weight_table = new PredictionWeightTable();
    Object localObject;
    int[] arrayOfInt;
    int k;
    int m;
    int i;
    if (paramSliceHeader.num_ref_idx_active_override_flag)
    {
      localObject = paramSliceHeader.num_ref_idx_active_minus1;
      arrayOfInt = new int[2];
      localObject[0] += 1;
      localObject[1] += 1;
      paramSliceHeader.pred_weight_table.luma_log2_weight_denom = CAVLCReader.readUE(paramBitReader, "SH: luma_log2_weight_denom");
      if (paramSeqParameterSet.chroma_format_idc != ColorSpace.MONO) {
        paramSliceHeader.pred_weight_table.chroma_log2_weight_denom = CAVLCReader.readUE(paramBitReader, "SH: chroma_log2_weight_denom");
      }
      k = paramSliceHeader.pred_weight_table.luma_log2_weight_denom;
      m = 1 << paramSliceHeader.pred_weight_table.chroma_log2_weight_denom;
      i = 0;
    }
    for (;;)
    {
      if (i >= 2) {
        break label369;
      }
      paramSliceHeader.pred_weight_table.luma_weight[i] = new int[arrayOfInt[i]];
      paramSliceHeader.pred_weight_table.luma_offset[i] = new int[arrayOfInt[i]];
      localObject = paramSliceHeader.pred_weight_table.chroma_weight;
      int j = arrayOfInt[i];
      localObject[i] = ((int[][])Array.newInstance(Integer.TYPE, new int[] { 2, j }));
      localObject = paramSliceHeader.pred_weight_table.chroma_offset;
      j = arrayOfInt[i];
      localObject[i] = ((int[][])Array.newInstance(Integer.TYPE, new int[] { 2, j }));
      j = 0;
      for (;;)
      {
        if (j < arrayOfInt[i])
        {
          paramSliceHeader.pred_weight_table.luma_weight[i][j] = (1 << k);
          paramSliceHeader.pred_weight_table.luma_offset[i][j] = 0;
          paramSliceHeader.pred_weight_table.chroma_weight[i][0][j] = m;
          paramSliceHeader.pred_weight_table.chroma_offset[i][0][j] = 0;
          paramSliceHeader.pred_weight_table.chroma_weight[i][1][j] = m;
          paramSliceHeader.pred_weight_table.chroma_offset[i][1][j] = 0;
          j += 1;
          continue;
          localObject = paramPictureParameterSet.num_ref_idx_active_minus1;
          break;
        }
      }
      i += 1;
    }
    label369:
    readWeightOffset(paramSeqParameterSet, paramPictureParameterSet, paramSliceHeader, paramBitReader, arrayOfInt, 0);
    if (paramSliceHeader.slice_type == SliceType.B) {
      readWeightOffset(paramSeqParameterSet, paramPictureParameterSet, paramSliceHeader, paramBitReader, arrayOfInt, 1);
    }
  }
  
  private static void readRefPicListReordering(SliceHeader paramSliceHeader, BitReader paramBitReader)
  {
    paramSliceHeader.refPicReordering = new int[2][][];
    if ((paramSliceHeader.slice_type.isInter()) && (CAVLCReader.readBool(paramBitReader, "SH: ref_pic_list_reordering_flag_l0"))) {
      paramSliceHeader.refPicReordering[0] = readReorderingEntries(paramBitReader);
    }
    if ((paramSliceHeader.slice_type == SliceType.B) && (CAVLCReader.readBool(paramBitReader, "SH: ref_pic_list_reordering_flag_l1"))) {
      paramSliceHeader.refPicReordering[1] = readReorderingEntries(paramBitReader);
    }
  }
  
  private static int[][] readReorderingEntries(BitReader paramBitReader)
  {
    IntArrayList localIntArrayList1 = new IntArrayList();
    IntArrayList localIntArrayList2 = new IntArrayList();
    for (;;)
    {
      int i = CAVLCReader.readUE(paramBitReader, "SH: reordering_of_pic_nums_idc");
      if (i == 3) {
        return new int[][] { localIntArrayList1.toArray(), localIntArrayList2.toArray() };
      }
      localIntArrayList1.add(i);
      localIntArrayList2.add(CAVLCReader.readUE(paramBitReader, "SH: abs_diff_pic_num_minus1"));
    }
  }
  
  private static void readWeightOffset(SeqParameterSet paramSeqParameterSet, PictureParameterSet paramPictureParameterSet, SliceHeader paramSliceHeader, BitReader paramBitReader, int[] paramArrayOfInt, int paramInt)
  {
    int i = 0;
    while (i < paramArrayOfInt[paramInt])
    {
      if (CAVLCReader.readBool(paramBitReader, "SH: luma_weight_l0_flag"))
      {
        paramSliceHeader.pred_weight_table.luma_weight[paramInt][i] = CAVLCReader.readSE(paramBitReader, "SH: weight");
        paramSliceHeader.pred_weight_table.luma_offset[paramInt][i] = CAVLCReader.readSE(paramBitReader, "SH: offset");
      }
      if ((paramSeqParameterSet.chroma_format_idc != ColorSpace.MONO) && (CAVLCReader.readBool(paramBitReader, "SH: chroma_weight_l0_flag")))
      {
        paramSliceHeader.pred_weight_table.chroma_weight[paramInt][0][i] = CAVLCReader.readSE(paramBitReader, "SH: weight");
        paramSliceHeader.pred_weight_table.chroma_offset[paramInt][0][i] = CAVLCReader.readSE(paramBitReader, "SH: offset");
        paramSliceHeader.pred_weight_table.chroma_weight[paramInt][1][i] = CAVLCReader.readSE(paramBitReader, "SH: weight");
        paramSliceHeader.pred_weight_table.chroma_offset[paramInt][1][i] = CAVLCReader.readSE(paramBitReader, "SH: offset");
      }
      i += 1;
    }
  }
  
  public SliceHeader readPart1(BitReader paramBitReader)
  {
    SliceHeader localSliceHeader = new SliceHeader();
    localSliceHeader.first_mb_in_slice = CAVLCReader.readUE(paramBitReader, "SH: first_mb_in_slice");
    int i = CAVLCReader.readUE(paramBitReader, "SH: slice_type");
    localSliceHeader.slice_type = SliceType.fromValue(i % 5);
    if (i / 5 > 0) {}
    for (boolean bool = true;; bool = false)
    {
      localSliceHeader.slice_type_restr = bool;
      localSliceHeader.pic_parameter_set_id = CAVLCReader.readUE(paramBitReader, "SH: pic_parameter_set_id");
      return localSliceHeader;
    }
  }
  
  public SliceHeader readPart2(SliceHeader paramSliceHeader, NALUnit paramNALUnit, SeqParameterSet paramSeqParameterSet, PictureParameterSet paramPictureParameterSet, BitReader paramBitReader)
  {
    paramSliceHeader.pps = paramPictureParameterSet;
    paramSliceHeader.sps = paramSeqParameterSet;
    paramSliceHeader.frame_num = CAVLCReader.readU(paramBitReader, paramSeqParameterSet.log2_max_frame_num_minus4 + 4, "SH: frame_num");
    if (!paramSeqParameterSet.frame_mbs_only_flag)
    {
      paramSliceHeader.field_pic_flag = CAVLCReader.readBool(paramBitReader, "SH: field_pic_flag");
      if (paramSliceHeader.field_pic_flag) {
        paramSliceHeader.bottom_field_flag = CAVLCReader.readBool(paramBitReader, "SH: bottom_field_flag");
      }
    }
    if (paramNALUnit.type == NALUnitType.IDR_SLICE) {
      paramSliceHeader.idr_pic_id = CAVLCReader.readUE(paramBitReader, "SH: idr_pic_id");
    }
    if (paramSeqParameterSet.pic_order_cnt_type == 0)
    {
      paramSliceHeader.pic_order_cnt_lsb = CAVLCReader.readU(paramBitReader, paramSeqParameterSet.log2_max_pic_order_cnt_lsb_minus4 + 4, "SH: pic_order_cnt_lsb");
      if ((paramPictureParameterSet.pic_order_present_flag) && (!paramSeqParameterSet.field_pic_flag)) {
        paramSliceHeader.delta_pic_order_cnt_bottom = CAVLCReader.readSE(paramBitReader, "SH: delta_pic_order_cnt_bottom");
      }
    }
    paramSliceHeader.delta_pic_order_cnt = new int[2];
    if ((paramSeqParameterSet.pic_order_cnt_type == 1) && (!paramSeqParameterSet.delta_pic_order_always_zero_flag))
    {
      paramSliceHeader.delta_pic_order_cnt[0] = CAVLCReader.readSE(paramBitReader, "SH: delta_pic_order_cnt[0]");
      if ((paramPictureParameterSet.pic_order_present_flag) && (!paramSeqParameterSet.field_pic_flag)) {
        paramSliceHeader.delta_pic_order_cnt[1] = CAVLCReader.readSE(paramBitReader, "SH: delta_pic_order_cnt[1]");
      }
    }
    if (paramPictureParameterSet.redundant_pic_cnt_present_flag) {
      paramSliceHeader.redundant_pic_cnt = CAVLCReader.readUE(paramBitReader, "SH: redundant_pic_cnt");
    }
    if (paramSliceHeader.slice_type == SliceType.B) {
      paramSliceHeader.direct_spatial_mv_pred_flag = CAVLCReader.readBool(paramBitReader, "SH: direct_spatial_mv_pred_flag");
    }
    if ((paramSliceHeader.slice_type == SliceType.P) || (paramSliceHeader.slice_type == SliceType.SP) || (paramSliceHeader.slice_type == SliceType.B))
    {
      paramSliceHeader.num_ref_idx_active_override_flag = CAVLCReader.readBool(paramBitReader, "SH: num_ref_idx_active_override_flag");
      if (paramSliceHeader.num_ref_idx_active_override_flag)
      {
        paramSliceHeader.num_ref_idx_active_minus1[0] = CAVLCReader.readUE(paramBitReader, "SH: num_ref_idx_l0_active_minus1");
        if (paramSliceHeader.slice_type == SliceType.B) {
          paramSliceHeader.num_ref_idx_active_minus1[1] = CAVLCReader.readUE(paramBitReader, "SH: num_ref_idx_l1_active_minus1");
        }
      }
    }
    readRefPicListReordering(paramSliceHeader, paramBitReader);
    if (((paramPictureParameterSet.weighted_pred_flag) && ((paramSliceHeader.slice_type == SliceType.P) || (paramSliceHeader.slice_type == SliceType.SP))) || ((paramPictureParameterSet.weighted_bipred_idc == 1) && (paramSliceHeader.slice_type == SliceType.B))) {
      readPredWeightTable(paramSeqParameterSet, paramPictureParameterSet, paramSliceHeader, paramBitReader);
    }
    if (paramNALUnit.nal_ref_idc != 0) {
      readDecoderPicMarking(paramNALUnit, paramSliceHeader, paramBitReader);
    }
    if ((paramPictureParameterSet.entropy_coding_mode_flag) && (paramSliceHeader.slice_type.isInter())) {
      paramSliceHeader.cabac_init_idc = CAVLCReader.readUE(paramBitReader, "SH: cabac_init_idc");
    }
    paramSliceHeader.slice_qp_delta = CAVLCReader.readSE(paramBitReader, "SH: slice_qp_delta");
    if ((paramSliceHeader.slice_type == SliceType.SP) || (paramSliceHeader.slice_type == SliceType.SI))
    {
      if (paramSliceHeader.slice_type == SliceType.SP) {
        paramSliceHeader.sp_for_switch_flag = CAVLCReader.readBool(paramBitReader, "SH: sp_for_switch_flag");
      }
      paramSliceHeader.slice_qs_delta = CAVLCReader.readSE(paramBitReader, "SH: slice_qs_delta");
    }
    if (paramPictureParameterSet.deblocking_filter_control_present_flag)
    {
      paramSliceHeader.disable_deblocking_filter_idc = CAVLCReader.readUE(paramBitReader, "SH: disable_deblocking_filter_idc");
      if (paramSliceHeader.disable_deblocking_filter_idc != 1)
      {
        paramSliceHeader.slice_alpha_c0_offset_div2 = CAVLCReader.readSE(paramBitReader, "SH: slice_alpha_c0_offset_div2");
        paramSliceHeader.slice_beta_offset_div2 = CAVLCReader.readSE(paramBitReader, "SH: slice_beta_offset_div2");
      }
    }
    if ((paramPictureParameterSet.num_slice_groups_minus1 > 0) && (paramPictureParameterSet.slice_group_map_type >= 3) && (paramPictureParameterSet.slice_group_map_type <= 5))
    {
      int j = H264Utils.getPicHeightInMbs(paramSeqParameterSet) * (paramSeqParameterSet.pic_width_in_mbs_minus1 + 1) / (paramPictureParameterSet.slice_group_change_rate_minus1 + 1);
      int i = j;
      if (H264Utils.getPicHeightInMbs(paramSeqParameterSet) * (paramSeqParameterSet.pic_width_in_mbs_minus1 + 1) % (paramPictureParameterSet.slice_group_change_rate_minus1 + 1) > 0) {
        i = j + 1;
      }
      paramSliceHeader.slice_group_change_cycle = CAVLCReader.readU(paramBitReader, CeilLog2(i + 1), "SH: slice_group_change_cycle");
    }
    return paramSliceHeader;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/SliceHeaderReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
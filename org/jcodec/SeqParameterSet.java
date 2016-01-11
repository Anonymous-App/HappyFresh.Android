package org.jcodec;

import java.nio.ByteBuffer;

public class SeqParameterSet
{
  public int bit_depth_chroma_minus8;
  public int bit_depth_luma_minus8;
  public ColorSpace chroma_format_idc;
  public boolean constraint_set_0_flag;
  public boolean constraint_set_1_flag;
  public boolean constraint_set_2_flag;
  public boolean constraint_set_3_flag;
  public boolean delta_pic_order_always_zero_flag;
  public boolean direct_8x8_inference_flag;
  public boolean field_pic_flag;
  public int frame_crop_bottom_offset;
  public int frame_crop_left_offset;
  public int frame_crop_right_offset;
  public int frame_crop_top_offset;
  public boolean frame_cropping_flag;
  public boolean frame_mbs_only_flag;
  public boolean gaps_in_frame_num_value_allowed_flag;
  public int level_idc;
  public int log2_max_frame_num_minus4;
  public int log2_max_pic_order_cnt_lsb_minus4;
  public boolean mb_adaptive_frame_field_flag;
  public int num_ref_frames;
  public int num_ref_frames_in_pic_order_cnt_cycle;
  public int[] offsetForRefFrame;
  public int offset_for_non_ref_pic;
  public int offset_for_top_to_bottom_field;
  public int pic_height_in_map_units_minus1;
  public int pic_order_cnt_type;
  public int pic_width_in_mbs_minus1;
  public int profile_idc;
  public boolean qpprime_y_zero_transform_bypass_flag;
  public boolean residual_color_transform_flag;
  public ScalingMatrix scalingMatrix;
  public int seq_parameter_set_id;
  public VUIParameters vuiParams;
  
  public static int fromColor(ColorSpace paramColorSpace)
  {
    switch (paramColorSpace)
    {
    default: 
      throw new RuntimeException("Colorspace not supported");
    case ???: 
      return 0;
    case ???: 
      return 1;
    case ???: 
      return 2;
    }
    return 3;
  }
  
  public static ColorSpace getColor(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new RuntimeException("Colorspace not supported");
    case 0: 
      return ColorSpace.MONO;
    case 1: 
      return ColorSpace.YUV420;
    case 2: 
      return ColorSpace.YUV422;
    }
    return ColorSpace.YUV444;
  }
  
  public static SeqParameterSet read(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer = new BitReader(paramByteBuffer);
    SeqParameterSet localSeqParameterSet = new SeqParameterSet();
    localSeqParameterSet.profile_idc = CAVLCReader.readNBit(paramByteBuffer, 8, "SPS: profile_idc");
    localSeqParameterSet.constraint_set_0_flag = CAVLCReader.readBool(paramByteBuffer, "SPS: constraint_set_0_flag");
    localSeqParameterSet.constraint_set_1_flag = CAVLCReader.readBool(paramByteBuffer, "SPS: constraint_set_1_flag");
    localSeqParameterSet.constraint_set_2_flag = CAVLCReader.readBool(paramByteBuffer, "SPS: constraint_set_2_flag");
    localSeqParameterSet.constraint_set_3_flag = CAVLCReader.readBool(paramByteBuffer, "SPS: constraint_set_3_flag");
    CAVLCReader.readNBit(paramByteBuffer, 4, "SPS: reserved_zero_4bits");
    localSeqParameterSet.level_idc = CAVLCReader.readNBit(paramByteBuffer, 8, "SPS: level_idc");
    localSeqParameterSet.seq_parameter_set_id = CAVLCReader.readUE(paramByteBuffer, "SPS: seq_parameter_set_id");
    if ((localSeqParameterSet.profile_idc == 100) || (localSeqParameterSet.profile_idc == 110) || (localSeqParameterSet.profile_idc == 122) || (localSeqParameterSet.profile_idc == 144))
    {
      localSeqParameterSet.chroma_format_idc = getColor(CAVLCReader.readUE(paramByteBuffer, "SPS: chroma_format_idc"));
      if (localSeqParameterSet.chroma_format_idc == ColorSpace.YUV444) {
        localSeqParameterSet.residual_color_transform_flag = CAVLCReader.readBool(paramByteBuffer, "SPS: residual_color_transform_flag");
      }
      localSeqParameterSet.bit_depth_luma_minus8 = CAVLCReader.readUE(paramByteBuffer, "SPS: bit_depth_luma_minus8");
      localSeqParameterSet.bit_depth_chroma_minus8 = CAVLCReader.readUE(paramByteBuffer, "SPS: bit_depth_chroma_minus8");
      localSeqParameterSet.qpprime_y_zero_transform_bypass_flag = CAVLCReader.readBool(paramByteBuffer, "SPS: qpprime_y_zero_transform_bypass_flag");
      if (CAVLCReader.readBool(paramByteBuffer, "SPS: seq_scaling_matrix_present_lag")) {
        readScalingListMatrix(paramByteBuffer, localSeqParameterSet);
      }
      localSeqParameterSet.log2_max_frame_num_minus4 = CAVLCReader.readUE(paramByteBuffer, "SPS: log2_max_frame_num_minus4");
      localSeqParameterSet.pic_order_cnt_type = CAVLCReader.readUE(paramByteBuffer, "SPS: pic_order_cnt_type");
      if (localSeqParameterSet.pic_order_cnt_type != 0) {
        break label413;
      }
      localSeqParameterSet.log2_max_pic_order_cnt_lsb_minus4 = CAVLCReader.readUE(paramByteBuffer, "SPS: log2_max_pic_order_cnt_lsb_minus4");
    }
    for (;;)
    {
      localSeqParameterSet.num_ref_frames = CAVLCReader.readUE(paramByteBuffer, "SPS: num_ref_frames");
      localSeqParameterSet.gaps_in_frame_num_value_allowed_flag = CAVLCReader.readBool(paramByteBuffer, "SPS: gaps_in_frame_num_value_allowed_flag");
      localSeqParameterSet.pic_width_in_mbs_minus1 = CAVLCReader.readUE(paramByteBuffer, "SPS: pic_width_in_mbs_minus1");
      localSeqParameterSet.pic_height_in_map_units_minus1 = CAVLCReader.readUE(paramByteBuffer, "SPS: pic_height_in_map_units_minus1");
      localSeqParameterSet.frame_mbs_only_flag = CAVLCReader.readBool(paramByteBuffer, "SPS: frame_mbs_only_flag");
      if (!localSeqParameterSet.frame_mbs_only_flag) {
        localSeqParameterSet.mb_adaptive_frame_field_flag = CAVLCReader.readBool(paramByteBuffer, "SPS: mb_adaptive_frame_field_flag");
      }
      localSeqParameterSet.direct_8x8_inference_flag = CAVLCReader.readBool(paramByteBuffer, "SPS: direct_8x8_inference_flag");
      localSeqParameterSet.frame_cropping_flag = CAVLCReader.readBool(paramByteBuffer, "SPS: frame_cropping_flag");
      if (localSeqParameterSet.frame_cropping_flag)
      {
        localSeqParameterSet.frame_crop_left_offset = CAVLCReader.readUE(paramByteBuffer, "SPS: frame_crop_left_offset");
        localSeqParameterSet.frame_crop_right_offset = CAVLCReader.readUE(paramByteBuffer, "SPS: frame_crop_right_offset");
        localSeqParameterSet.frame_crop_top_offset = CAVLCReader.readUE(paramByteBuffer, "SPS: frame_crop_top_offset");
        localSeqParameterSet.frame_crop_bottom_offset = CAVLCReader.readUE(paramByteBuffer, "SPS: frame_crop_bottom_offset");
      }
      if (CAVLCReader.readBool(paramByteBuffer, "SPS: vui_parameters_present_flag")) {
        localSeqParameterSet.vuiParams = readVUIParameters(paramByteBuffer);
      }
      return localSeqParameterSet;
      localSeqParameterSet.chroma_format_idc = ColorSpace.YUV420;
      break;
      label413:
      if (localSeqParameterSet.pic_order_cnt_type == 1)
      {
        localSeqParameterSet.delta_pic_order_always_zero_flag = CAVLCReader.readBool(paramByteBuffer, "SPS: delta_pic_order_always_zero_flag");
        localSeqParameterSet.offset_for_non_ref_pic = CAVLCReader.readSE(paramByteBuffer, "SPS: offset_for_non_ref_pic");
        localSeqParameterSet.offset_for_top_to_bottom_field = CAVLCReader.readSE(paramByteBuffer, "SPS: offset_for_top_to_bottom_field");
        localSeqParameterSet.num_ref_frames_in_pic_order_cnt_cycle = CAVLCReader.readUE(paramByteBuffer, "SPS: num_ref_frames_in_pic_order_cnt_cycle");
        localSeqParameterSet.offsetForRefFrame = new int[localSeqParameterSet.num_ref_frames_in_pic_order_cnt_cycle];
        int i = 0;
        while (i < localSeqParameterSet.num_ref_frames_in_pic_order_cnt_cycle)
        {
          localSeqParameterSet.offsetForRefFrame[i] = CAVLCReader.readSE(paramByteBuffer, "SPS: offsetForRefFrame [" + i + "]");
          i += 1;
        }
      }
    }
  }
  
  private static HRDParameters readHRDParameters(BitReader paramBitReader)
  {
    HRDParameters localHRDParameters = new HRDParameters();
    localHRDParameters.cpb_cnt_minus1 = CAVLCReader.readUE(paramBitReader, "SPS: cpb_cnt_minus1");
    localHRDParameters.bit_rate_scale = CAVLCReader.readNBit(paramBitReader, 4, "HRD: bit_rate_scale");
    localHRDParameters.cpb_size_scale = CAVLCReader.readNBit(paramBitReader, 4, "HRD: cpb_size_scale");
    localHRDParameters.bit_rate_value_minus1 = new int[localHRDParameters.cpb_cnt_minus1 + 1];
    localHRDParameters.cpb_size_value_minus1 = new int[localHRDParameters.cpb_cnt_minus1 + 1];
    localHRDParameters.cbr_flag = new boolean[localHRDParameters.cpb_cnt_minus1 + 1];
    int i = 0;
    while (i <= localHRDParameters.cpb_cnt_minus1)
    {
      localHRDParameters.bit_rate_value_minus1[i] = CAVLCReader.readUE(paramBitReader, "HRD: bit_rate_value_minus1");
      localHRDParameters.cpb_size_value_minus1[i] = CAVLCReader.readUE(paramBitReader, "HRD: cpb_size_value_minus1");
      localHRDParameters.cbr_flag[i] = CAVLCReader.readBool(paramBitReader, "HRD: cbr_flag");
      i += 1;
    }
    localHRDParameters.initial_cpb_removal_delay_length_minus1 = CAVLCReader.readNBit(paramBitReader, 5, "HRD: initial_cpb_removal_delay_length_minus1");
    localHRDParameters.cpb_removal_delay_length_minus1 = CAVLCReader.readNBit(paramBitReader, 5, "HRD: cpb_removal_delay_length_minus1");
    localHRDParameters.dpb_output_delay_length_minus1 = CAVLCReader.readNBit(paramBitReader, 5, "HRD: dpb_output_delay_length_minus1");
    localHRDParameters.time_offset_length = CAVLCReader.readNBit(paramBitReader, 5, "HRD: time_offset_length");
    return localHRDParameters;
  }
  
  private static void readScalingListMatrix(BitReader paramBitReader, SeqParameterSet paramSeqParameterSet)
  {
    paramSeqParameterSet.scalingMatrix = new ScalingMatrix();
    int i = 0;
    if (i < 8)
    {
      if (CAVLCReader.readBool(paramBitReader, "SPS: seqScalingListPresentFlag"))
      {
        paramSeqParameterSet.scalingMatrix.ScalingList4x4 = new ScalingList[8];
        paramSeqParameterSet.scalingMatrix.ScalingList8x8 = new ScalingList[8];
        if (i >= 6) {
          break label81;
        }
        paramSeqParameterSet.scalingMatrix.ScalingList4x4[i] = ScalingList.read(paramBitReader, 16);
      }
      for (;;)
      {
        i += 1;
        break;
        label81:
        paramSeqParameterSet.scalingMatrix.ScalingList8x8[(i - 6)] = ScalingList.read(paramBitReader, 64);
      }
    }
  }
  
  private static VUIParameters readVUIParameters(BitReader paramBitReader)
  {
    VUIParameters localVUIParameters = new VUIParameters();
    localVUIParameters.aspect_ratio_info_present_flag = CAVLCReader.readBool(paramBitReader, "VUI: aspect_ratio_info_present_flag");
    if (localVUIParameters.aspect_ratio_info_present_flag)
    {
      localVUIParameters.aspect_ratio = AspectRatio.fromValue(CAVLCReader.readNBit(paramBitReader, 8, "VUI: aspect_ratio"));
      if (localVUIParameters.aspect_ratio == AspectRatio.Extended_SAR)
      {
        localVUIParameters.sar_width = CAVLCReader.readNBit(paramBitReader, 16, "VUI: sar_width");
        localVUIParameters.sar_height = CAVLCReader.readNBit(paramBitReader, 16, "VUI: sar_height");
      }
    }
    localVUIParameters.overscan_info_present_flag = CAVLCReader.readBool(paramBitReader, "VUI: overscan_info_present_flag");
    if (localVUIParameters.overscan_info_present_flag) {
      localVUIParameters.overscan_appropriate_flag = CAVLCReader.readBool(paramBitReader, "VUI: overscan_appropriate_flag");
    }
    localVUIParameters.video_signal_type_present_flag = CAVLCReader.readBool(paramBitReader, "VUI: video_signal_type_present_flag");
    if (localVUIParameters.video_signal_type_present_flag)
    {
      localVUIParameters.video_format = CAVLCReader.readNBit(paramBitReader, 3, "VUI: video_format");
      localVUIParameters.video_full_range_flag = CAVLCReader.readBool(paramBitReader, "VUI: video_full_range_flag");
      localVUIParameters.colour_description_present_flag = CAVLCReader.readBool(paramBitReader, "VUI: colour_description_present_flag");
      if (localVUIParameters.colour_description_present_flag)
      {
        localVUIParameters.colour_primaries = CAVLCReader.readNBit(paramBitReader, 8, "VUI: colour_primaries");
        localVUIParameters.transfer_characteristics = CAVLCReader.readNBit(paramBitReader, 8, "VUI: transfer_characteristics");
        localVUIParameters.matrix_coefficients = CAVLCReader.readNBit(paramBitReader, 8, "VUI: matrix_coefficients");
      }
    }
    localVUIParameters.chroma_loc_info_present_flag = CAVLCReader.readBool(paramBitReader, "VUI: chroma_loc_info_present_flag");
    if (localVUIParameters.chroma_loc_info_present_flag)
    {
      localVUIParameters.chroma_sample_loc_type_top_field = CAVLCReader.readUE(paramBitReader, "VUI chroma_sample_loc_type_top_field");
      localVUIParameters.chroma_sample_loc_type_bottom_field = CAVLCReader.readUE(paramBitReader, "VUI chroma_sample_loc_type_bottom_field");
    }
    localVUIParameters.timing_info_present_flag = CAVLCReader.readBool(paramBitReader, "VUI: timing_info_present_flag");
    if (localVUIParameters.timing_info_present_flag)
    {
      localVUIParameters.num_units_in_tick = CAVLCReader.readNBit(paramBitReader, 32, "VUI: num_units_in_tick");
      localVUIParameters.time_scale = CAVLCReader.readNBit(paramBitReader, 32, "VUI: time_scale");
      localVUIParameters.fixed_frame_rate_flag = CAVLCReader.readBool(paramBitReader, "VUI: fixed_frame_rate_flag");
    }
    boolean bool1 = CAVLCReader.readBool(paramBitReader, "VUI: nal_hrd_parameters_present_flag");
    if (bool1) {
      localVUIParameters.nalHRDParams = readHRDParameters(paramBitReader);
    }
    boolean bool2 = CAVLCReader.readBool(paramBitReader, "VUI: vcl_hrd_parameters_present_flag");
    if (bool2) {
      localVUIParameters.vclHRDParams = readHRDParameters(paramBitReader);
    }
    if ((bool1) || (bool2)) {
      localVUIParameters.low_delay_hrd_flag = CAVLCReader.readBool(paramBitReader, "VUI: low_delay_hrd_flag");
    }
    localVUIParameters.pic_struct_present_flag = CAVLCReader.readBool(paramBitReader, "VUI: pic_struct_present_flag");
    if (CAVLCReader.readBool(paramBitReader, "VUI: bitstream_restriction_flag"))
    {
      localVUIParameters.bitstreamRestriction = new VUIParameters.BitstreamRestriction();
      localVUIParameters.bitstreamRestriction.motion_vectors_over_pic_boundaries_flag = CAVLCReader.readBool(paramBitReader, "VUI: motion_vectors_over_pic_boundaries_flag");
      localVUIParameters.bitstreamRestriction.max_bytes_per_pic_denom = CAVLCReader.readUE(paramBitReader, "VUI max_bytes_per_pic_denom");
      localVUIParameters.bitstreamRestriction.max_bits_per_mb_denom = CAVLCReader.readUE(paramBitReader, "VUI max_bits_per_mb_denom");
      localVUIParameters.bitstreamRestriction.log2_max_mv_length_horizontal = CAVLCReader.readUE(paramBitReader, "VUI log2_max_mv_length_horizontal");
      localVUIParameters.bitstreamRestriction.log2_max_mv_length_vertical = CAVLCReader.readUE(paramBitReader, "VUI log2_max_mv_length_vertical");
      localVUIParameters.bitstreamRestriction.num_reorder_frames = CAVLCReader.readUE(paramBitReader, "VUI num_reorder_frames");
      localVUIParameters.bitstreamRestriction.max_dec_frame_buffering = CAVLCReader.readUE(paramBitReader, "VUI max_dec_frame_buffering");
    }
    return localVUIParameters;
  }
  
  private void writeHRDParameters(HRDParameters paramHRDParameters, BitWriter paramBitWriter)
  {
    CAVLCWriter.writeUE(paramBitWriter, paramHRDParameters.cpb_cnt_minus1, "HRD: cpb_cnt_minus1");
    CAVLCWriter.writeNBit(paramBitWriter, paramHRDParameters.bit_rate_scale, 4, "HRD: bit_rate_scale");
    CAVLCWriter.writeNBit(paramBitWriter, paramHRDParameters.cpb_size_scale, 4, "HRD: cpb_size_scale");
    int i = 0;
    while (i <= paramHRDParameters.cpb_cnt_minus1)
    {
      CAVLCWriter.writeUE(paramBitWriter, paramHRDParameters.bit_rate_value_minus1[i], "HRD: ");
      CAVLCWriter.writeUE(paramBitWriter, paramHRDParameters.cpb_size_value_minus1[i], "HRD: ");
      CAVLCWriter.writeBool(paramBitWriter, paramHRDParameters.cbr_flag[i], "HRD: ");
      i += 1;
    }
    CAVLCWriter.writeNBit(paramBitWriter, paramHRDParameters.initial_cpb_removal_delay_length_minus1, 5, "HRD: initial_cpb_removal_delay_length_minus1");
    CAVLCWriter.writeNBit(paramBitWriter, paramHRDParameters.cpb_removal_delay_length_minus1, 5, "HRD: cpb_removal_delay_length_minus1");
    CAVLCWriter.writeNBit(paramBitWriter, paramHRDParameters.dpb_output_delay_length_minus1, 5, "HRD: dpb_output_delay_length_minus1");
    CAVLCWriter.writeNBit(paramBitWriter, paramHRDParameters.time_offset_length, 5, "HRD: time_offset_length");
  }
  
  private void writeVUIParameters(VUIParameters paramVUIParameters, BitWriter paramBitWriter)
  {
    boolean bool2 = true;
    CAVLCWriter.writeBool(paramBitWriter, paramVUIParameters.aspect_ratio_info_present_flag, "VUI: aspect_ratio_info_present_flag");
    if (paramVUIParameters.aspect_ratio_info_present_flag)
    {
      CAVLCWriter.writeNBit(paramBitWriter, paramVUIParameters.aspect_ratio.getValue(), 8, "VUI: aspect_ratio");
      if (paramVUIParameters.aspect_ratio == AspectRatio.Extended_SAR)
      {
        CAVLCWriter.writeNBit(paramBitWriter, paramVUIParameters.sar_width, 16, "VUI: sar_width");
        CAVLCWriter.writeNBit(paramBitWriter, paramVUIParameters.sar_height, 16, "VUI: sar_height");
      }
    }
    CAVLCWriter.writeBool(paramBitWriter, paramVUIParameters.overscan_info_present_flag, "VUI: overscan_info_present_flag");
    if (paramVUIParameters.overscan_info_present_flag) {
      CAVLCWriter.writeBool(paramBitWriter, paramVUIParameters.overscan_appropriate_flag, "VUI: overscan_appropriate_flag");
    }
    CAVLCWriter.writeBool(paramBitWriter, paramVUIParameters.video_signal_type_present_flag, "VUI: video_signal_type_present_flag");
    if (paramVUIParameters.video_signal_type_present_flag)
    {
      CAVLCWriter.writeNBit(paramBitWriter, paramVUIParameters.video_format, 3, "VUI: video_format");
      CAVLCWriter.writeBool(paramBitWriter, paramVUIParameters.video_full_range_flag, "VUI: video_full_range_flag");
      CAVLCWriter.writeBool(paramBitWriter, paramVUIParameters.colour_description_present_flag, "VUI: colour_description_present_flag");
      if (paramVUIParameters.colour_description_present_flag)
      {
        CAVLCWriter.writeNBit(paramBitWriter, paramVUIParameters.colour_primaries, 8, "VUI: colour_primaries");
        CAVLCWriter.writeNBit(paramBitWriter, paramVUIParameters.transfer_characteristics, 8, "VUI: transfer_characteristics");
        CAVLCWriter.writeNBit(paramBitWriter, paramVUIParameters.matrix_coefficients, 8, "VUI: matrix_coefficients");
      }
    }
    CAVLCWriter.writeBool(paramBitWriter, paramVUIParameters.chroma_loc_info_present_flag, "VUI: chroma_loc_info_present_flag");
    if (paramVUIParameters.chroma_loc_info_present_flag)
    {
      CAVLCWriter.writeUE(paramBitWriter, paramVUIParameters.chroma_sample_loc_type_top_field, "VUI: chroma_sample_loc_type_top_field");
      CAVLCWriter.writeUE(paramBitWriter, paramVUIParameters.chroma_sample_loc_type_bottom_field, "VUI: chroma_sample_loc_type_bottom_field");
    }
    CAVLCWriter.writeBool(paramBitWriter, paramVUIParameters.timing_info_present_flag, "VUI: timing_info_present_flag");
    if (paramVUIParameters.timing_info_present_flag)
    {
      CAVLCWriter.writeNBit(paramBitWriter, paramVUIParameters.num_units_in_tick, 32, "VUI: num_units_in_tick");
      CAVLCWriter.writeNBit(paramBitWriter, paramVUIParameters.time_scale, 32, "VUI: time_scale");
      CAVLCWriter.writeBool(paramBitWriter, paramVUIParameters.fixed_frame_rate_flag, "VUI: fixed_frame_rate_flag");
    }
    if (paramVUIParameters.nalHRDParams != null)
    {
      bool1 = true;
      CAVLCWriter.writeBool(paramBitWriter, bool1, "VUI: ");
      if (paramVUIParameters.nalHRDParams != null) {
        writeHRDParameters(paramVUIParameters.nalHRDParams, paramBitWriter);
      }
      if (paramVUIParameters.vclHRDParams == null) {
        break label535;
      }
      bool1 = true;
      label346:
      CAVLCWriter.writeBool(paramBitWriter, bool1, "VUI: ");
      if (paramVUIParameters.vclHRDParams != null) {
        writeHRDParameters(paramVUIParameters.vclHRDParams, paramBitWriter);
      }
      if ((paramVUIParameters.nalHRDParams != null) || (paramVUIParameters.vclHRDParams != null)) {
        CAVLCWriter.writeBool(paramBitWriter, paramVUIParameters.low_delay_hrd_flag, "VUI: low_delay_hrd_flag");
      }
      CAVLCWriter.writeBool(paramBitWriter, paramVUIParameters.pic_struct_present_flag, "VUI: pic_struct_present_flag");
      if (paramVUIParameters.bitstreamRestriction == null) {
        break label540;
      }
    }
    label535:
    label540:
    for (boolean bool1 = bool2;; bool1 = false)
    {
      CAVLCWriter.writeBool(paramBitWriter, bool1, "VUI: ");
      if (paramVUIParameters.bitstreamRestriction != null)
      {
        CAVLCWriter.writeBool(paramBitWriter, paramVUIParameters.bitstreamRestriction.motion_vectors_over_pic_boundaries_flag, "VUI: motion_vectors_over_pic_boundaries_flag");
        CAVLCWriter.writeUE(paramBitWriter, paramVUIParameters.bitstreamRestriction.max_bytes_per_pic_denom, "VUI: max_bytes_per_pic_denom");
        CAVLCWriter.writeUE(paramBitWriter, paramVUIParameters.bitstreamRestriction.max_bits_per_mb_denom, "VUI: max_bits_per_mb_denom");
        CAVLCWriter.writeUE(paramBitWriter, paramVUIParameters.bitstreamRestriction.log2_max_mv_length_horizontal, "VUI: log2_max_mv_length_horizontal");
        CAVLCWriter.writeUE(paramBitWriter, paramVUIParameters.bitstreamRestriction.log2_max_mv_length_vertical, "VUI: log2_max_mv_length_vertical");
        CAVLCWriter.writeUE(paramBitWriter, paramVUIParameters.bitstreamRestriction.num_reorder_frames, "VUI: num_reorder_frames");
        CAVLCWriter.writeUE(paramBitWriter, paramVUIParameters.bitstreamRestriction.max_dec_frame_buffering, "VUI: max_dec_frame_buffering");
      }
      return;
      bool1 = false;
      break;
      bool1 = false;
      break label346;
    }
  }
  
  public SeqParameterSet copy()
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(2048);
    write(localByteBuffer);
    localByteBuffer.flip();
    return read(localByteBuffer);
  }
  
  public void write(ByteBuffer paramByteBuffer)
  {
    boolean bool2 = true;
    paramByteBuffer = new BitWriter(paramByteBuffer);
    CAVLCWriter.writeNBit(paramByteBuffer, this.profile_idc, 8, "SPS: profile_idc");
    CAVLCWriter.writeBool(paramByteBuffer, this.constraint_set_0_flag, "SPS: constraint_set_0_flag");
    CAVLCWriter.writeBool(paramByteBuffer, this.constraint_set_1_flag, "SPS: constraint_set_1_flag");
    CAVLCWriter.writeBool(paramByteBuffer, this.constraint_set_2_flag, "SPS: constraint_set_2_flag");
    CAVLCWriter.writeBool(paramByteBuffer, this.constraint_set_3_flag, "SPS: constraint_set_3_flag");
    CAVLCWriter.writeNBit(paramByteBuffer, 0L, 4, "SPS: reserved");
    CAVLCWriter.writeNBit(paramByteBuffer, this.level_idc, 8, "SPS: level_idc");
    CAVLCWriter.writeUE(paramByteBuffer, this.seq_parameter_set_id, "SPS: seq_parameter_set_id");
    int i;
    if ((this.profile_idc == 100) || (this.profile_idc == 110) || (this.profile_idc == 122) || (this.profile_idc == 144))
    {
      CAVLCWriter.writeUE(paramByteBuffer, fromColor(this.chroma_format_idc), "SPS: chroma_format_idc");
      if (this.chroma_format_idc == ColorSpace.YUV444) {
        CAVLCWriter.writeBool(paramByteBuffer, this.residual_color_transform_flag, "SPS: residual_color_transform_flag");
      }
      CAVLCWriter.writeUE(paramByteBuffer, this.bit_depth_luma_minus8, "SPS: ");
      CAVLCWriter.writeUE(paramByteBuffer, this.bit_depth_chroma_minus8, "SPS: ");
      CAVLCWriter.writeBool(paramByteBuffer, this.qpprime_y_zero_transform_bypass_flag, "SPS: qpprime_y_zero_transform_bypass_flag");
      if (this.scalingMatrix != null)
      {
        bool1 = true;
        CAVLCWriter.writeBool(paramByteBuffer, bool1, "SPS: ");
        if (this.scalingMatrix == null) {
          break label365;
        }
        i = 0;
        label225:
        if (i >= 8) {
          break label365;
        }
        if (i >= 6) {
          break label301;
        }
        if (this.scalingMatrix.ScalingList4x4[i] == null) {
          break label296;
        }
      }
      label296:
      for (bool1 = true;; bool1 = false)
      {
        CAVLCWriter.writeBool(paramByteBuffer, bool1, "SPS: ");
        if (this.scalingMatrix.ScalingList4x4[i] != null) {
          this.scalingMatrix.ScalingList4x4[i].write(paramByteBuffer);
        }
        i += 1;
        break label225;
        bool1 = false;
        break;
      }
      label301:
      if (this.scalingMatrix.ScalingList8x8[(i - 6)] != null) {}
      for (bool1 = true;; bool1 = false)
      {
        CAVLCWriter.writeBool(paramByteBuffer, bool1, "SPS: ");
        if (this.scalingMatrix.ScalingList8x8[(i - 6)] == null) {
          break;
        }
        this.scalingMatrix.ScalingList8x8[(i - 6)].write(paramByteBuffer);
        break;
      }
    }
    label365:
    CAVLCWriter.writeUE(paramByteBuffer, this.log2_max_frame_num_minus4, "SPS: log2_max_frame_num_minus4");
    CAVLCWriter.writeUE(paramByteBuffer, this.pic_order_cnt_type, "SPS: pic_order_cnt_type");
    if (this.pic_order_cnt_type == 0)
    {
      CAVLCWriter.writeUE(paramByteBuffer, this.log2_max_pic_order_cnt_lsb_minus4, "SPS: log2_max_pic_order_cnt_lsb_minus4");
      CAVLCWriter.writeUE(paramByteBuffer, this.num_ref_frames, "SPS: num_ref_frames");
      CAVLCWriter.writeBool(paramByteBuffer, this.gaps_in_frame_num_value_allowed_flag, "SPS: gaps_in_frame_num_value_allowed_flag");
      CAVLCWriter.writeUE(paramByteBuffer, this.pic_width_in_mbs_minus1, "SPS: pic_width_in_mbs_minus1");
      CAVLCWriter.writeUE(paramByteBuffer, this.pic_height_in_map_units_minus1, "SPS: pic_height_in_map_units_minus1");
      CAVLCWriter.writeBool(paramByteBuffer, this.frame_mbs_only_flag, "SPS: frame_mbs_only_flag");
      if (!this.frame_mbs_only_flag) {
        CAVLCWriter.writeBool(paramByteBuffer, this.mb_adaptive_frame_field_flag, "SPS: mb_adaptive_frame_field_flag");
      }
      CAVLCWriter.writeBool(paramByteBuffer, this.direct_8x8_inference_flag, "SPS: direct_8x8_inference_flag");
      CAVLCWriter.writeBool(paramByteBuffer, this.frame_cropping_flag, "SPS: frame_cropping_flag");
      if (this.frame_cropping_flag)
      {
        CAVLCWriter.writeUE(paramByteBuffer, this.frame_crop_left_offset, "SPS: frame_crop_left_offset");
        CAVLCWriter.writeUE(paramByteBuffer, this.frame_crop_right_offset, "SPS: frame_crop_right_offset");
        CAVLCWriter.writeUE(paramByteBuffer, this.frame_crop_top_offset, "SPS: frame_crop_top_offset");
        CAVLCWriter.writeUE(paramByteBuffer, this.frame_crop_bottom_offset, "SPS: frame_crop_bottom_offset");
      }
      if (this.vuiParams == null) {
        break label656;
      }
    }
    label656:
    for (boolean bool1 = bool2;; bool1 = false)
    {
      CAVLCWriter.writeBool(paramByteBuffer, bool1, "SPS: ");
      if (this.vuiParams != null) {
        writeVUIParameters(this.vuiParams, paramByteBuffer);
      }
      CAVLCWriter.writeTrailingBits(paramByteBuffer);
      return;
      if (this.pic_order_cnt_type != 1) {
        break;
      }
      CAVLCWriter.writeBool(paramByteBuffer, this.delta_pic_order_always_zero_flag, "SPS: delta_pic_order_always_zero_flag");
      CAVLCWriter.writeSE(paramByteBuffer, this.offset_for_non_ref_pic, "SPS: offset_for_non_ref_pic");
      CAVLCWriter.writeSE(paramByteBuffer, this.offset_for_top_to_bottom_field, "SPS: offset_for_top_to_bottom_field");
      CAVLCWriter.writeUE(paramByteBuffer, this.offsetForRefFrame.length, "SPS: ");
      i = 0;
      while (i < this.offsetForRefFrame.length)
      {
        CAVLCWriter.writeSE(paramByteBuffer, this.offsetForRefFrame[i], "SPS: ");
        i += 1;
      }
      break;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/SeqParameterSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package org.jcodec;

public class SliceHeader
{
  public boolean bottom_field_flag;
  public int cabac_init_idc;
  public int[] delta_pic_order_cnt;
  public int delta_pic_order_cnt_bottom;
  public boolean direct_spatial_mv_pred_flag;
  public int disable_deblocking_filter_idc;
  public boolean field_pic_flag;
  public int first_mb_in_slice;
  public int frame_num;
  public int idr_pic_id;
  public int[] num_ref_idx_active_minus1 = new int[2];
  public boolean num_ref_idx_active_override_flag;
  public int pic_order_cnt_lsb;
  public int pic_parameter_set_id;
  public PictureParameterSet pps;
  public PredictionWeightTable pred_weight_table;
  public int redundant_pic_cnt;
  public RefPicMarkingIDR refPicMarkingIDR;
  public RefPicMarking refPicMarkingNonIDR;
  public int[][][] refPicReordering;
  public int slice_alpha_c0_offset_div2;
  public int slice_beta_offset_div2;
  public int slice_group_change_cycle;
  public int slice_qp_delta;
  public int slice_qs_delta;
  public SliceType slice_type;
  public boolean slice_type_restr;
  public boolean sp_for_switch_flag;
  public SeqParameterSet sps;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/SliceHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
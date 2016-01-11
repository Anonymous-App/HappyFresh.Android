package org.jcodec;

public class PredictionWeightTable
{
  public int chroma_log2_weight_denom;
  public int[][][] chroma_offset = new int[2][][];
  public int[][][] chroma_weight = new int[2][][];
  public int luma_log2_weight_denom;
  public int[][] luma_offset = new int[2][];
  public int[][] luma_weight = new int[2][];
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/PredictionWeightTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
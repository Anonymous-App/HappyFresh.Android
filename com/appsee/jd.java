package com.appsee;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

@TargetApi(16)
class jd
  implements j
{
  private static final int D = 1000000;
  private static final String K = "video/avc";
  private int A = -1;
  private ByteBuffer B;
  private int C;
  private mo E = yc.i().i(AppseeBackgroundUploader.i("s\031\031P4It\006&Y4P"));
  private String G;
  private int H;
  private long I;
  private MediaFormat J;
  private MediaCodec L;
  private List<Long> M = new ArrayList();
  private boolean a;
  private MediaCodec.BufferInfo b;
  private ByteBuffer[] c;
  private mo d = yc.i().i(AppseeBackgroundUploader.i("l4\\5[t=8Q<["));
  private int e;
  private mo f = yc.i().i(AppseeBackgroundUploader.i("\006H,^\037Qr\0331Y5Y"));
  private boolean g = false;
  private boolean h;
  private int i;
  private FileChannel k;
  private String l;
  private ByteBuffer[] m;
  
  public static List<String> i()
  {
    int i2 = MediaCodecList.getCodecCount();
    ArrayList localArrayList = new ArrayList(i2);
    int n = 0;
    int j = 0;
    if (n < i2)
    {
      MediaCodecInfo localMediaCodecInfo = MediaCodecList.getCodecInfoAt(j);
      if ((!localMediaCodecInfo.isEncoder()) || (i(localMediaCodecInfo.getName()))) {}
      label108:
      for (;;)
      {
        n = j + 1;
        j = n;
        break;
        String[] arrayOfString = localMediaCodecInfo.getSupportedTypes();
        n = 0;
        for (int i1 = 0;; i1 = n)
        {
          if (n >= arrayOfString.length) {
            break label108;
          }
          if (arrayOfString[i1].equalsIgnoreCase("video/avc"))
          {
            localArrayList.add(localMediaCodecInfo.getName());
            break;
          }
          n = i1 + 1;
        }
      }
    }
    return localArrayList;
  }
  
  public void i()
    throws Exception
  {
    if (this.L == null) {
      throw new Exception(AppseeBackgroundUploader.i("\nD,E(L(\0373L1\032 \\7\037"));
    }
    l();
    this.E.l();
    this.f.l();
    this.d.l();
    mc.l(AppseeBackgroundUploader.i("\034^ Z<@4XzZ\027:T>L"), 1);
    this.L.stop();
    this.L.release();
    if (this.k != null)
    {
      mc.l(AppseeBackgroundUploader.i("\030W:Y'C\"NzWh\t%T3Y7["), 1);
      this.k.close();
    }
    try
    {
      this.h = true;
      mo localmo = yc.i().i(AppseeBackgroundUploader.i("\\\001-Y5Y"));
      localmo.a();
      a();
      localmo.F();
      localmo.l();
      return;
    }
    finally
    {
      this.h = false;
    }
  }
  
  public void i(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString, boolean paramBoolean)
    throws Exception
  {
    mc.l(AppseeBackgroundUploader.i("w8Z(P1Y;MtT4Z9Ko\\%M?PzZ\027:T>L"), 1);
    this.H = paramInt1;
    this.C = paramInt2;
    this.i = paramInt3;
    this.e = paramInt4;
    this.l = paramString;
    this.G = (AppseeBackgroundUploader.i("\020") + "h264");
    this.a = paramBoolean;
    this.b = new MediaCodec.BufferInfo();
    this.M.clear();
    F();
  }
  
  public void i(xd paramxd, long paramLong)
    throws Exception
  {
    this.d.a();
    if (this.a) {
      mc.l(AppseeBackgroundUploader.i("\nD/F>V4X1\022'Q6["), 1);
    }
    i(paramxd);
    this.f.a();
    i(paramLong);
    if (this.a) {
      mc.l(AppseeBackgroundUploader.i("\013X.C\"@4XzZ\027:T>L"), 1);
    }
    i(false);
    this.f.F();
    this.d.F();
  }
  
  public boolean i()
  {
    return this.h;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/jd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
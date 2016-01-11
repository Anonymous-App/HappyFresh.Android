package com.appsee;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build.VERSION;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.util.HashMap;

@TargetApi(18)
class fb
  implements j
{
  private MediaCodec A;
  private MediaFormat B;
  private Surface C;
  private mo D = yc.i().i(AppseeBackgroundUploader.i("l4\\5[t=4]:]"));
  private int G;
  private HashMap<Long, Long> I = new HashMap();
  private int J;
  private mo K = yc.i().i(AppseeBackgroundUploader.i("'+I)K,O\006H,^\037Qr\033=U3_"));
  private boolean M;
  private MediaCodec.BufferInfo a;
  private int b;
  private int c;
  private Rect d;
  private MediaMuxer e;
  private int g;
  private long h;
  private mo i = yc.i().i(AppseeBackgroundUploader.i("\fK!\\-Z\031P4It\006*U2V"));
  private long k;
  private ByteBuffer[] l;
  private String m;
  
  @TargetApi(21)
  public void i()
    throws Exception
  {
    this.i.l();
    this.K.l();
    this.D.l();
    if (this.A != null)
    {
      mc.l(AppseeBackgroundUploader.i("~(V.V\023yy\022k"), 1);
      this.A.signalEndOfInputStream();
      i(true);
      mc.l(this.I.size() + AppseeBackgroundUploader.i("\021"), 1);
      if ((Build.VERSION.SDK_INT >= 21) && (!ge.i().b())) {
        this.A.reset();
      }
      this.A.stop();
      this.A.release();
    }
    if (this.e != null)
    {
      this.e.stop();
      this.e.release();
    }
    if (this.C != null) {
      this.C.release();
    }
    this.l = null;
    this.A = null;
    this.B = null;
    this.C = null;
    this.e = null;
    this.a = null;
  }
  
  public void i(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString, boolean paramBoolean)
    throws Exception
  {
    mc.l(AppseeBackgroundUploader.i("\020R4L?R0P*PrDd\0068Z,Oo\\%M?PzZ\0276X8J"), 1);
    this.b = paramInt1;
    this.G = paramInt2;
    this.d = new Rect(0, 0, this.b, this.G);
    this.c = paramInt3;
    this.g = paramInt4;
    this.m = paramString;
    this.M = paramBoolean;
    this.a = new MediaCodec.BufferInfo();
    this.I.clear();
    this.k = 0L;
    this.h = 0L;
    l();
  }
  
  public void i(xd paramxd, long paramLong)
    throws Exception
  {
    Object localObject = null;
    this.D.a();
    if (this.M) {
      mc.l(AppseeBackgroundUploader.i("\025Re\0007U(\n*D/F>Z(\037r\0257J<K"), 1);
    }
    if (!this.C.isValid()) {
      return;
    }
    try
    {
      Canvas localCanvas = this.C.lockCanvas(this.d);
      localObject = localCanvas;
      if (this.M)
      {
        localObject = localCanvas;
        mc.l(AppseeBackgroundUploader.i("}\"T%^\023~T!\n*D/F>Z(\037r\0257J<K"), 1);
      }
      localObject = localCanvas;
      this.i.a();
      localObject = localCanvas;
      localCanvas.drawColor(-16777216);
      localObject = localCanvas;
      localCanvas.drawBitmap(paramxd.i(), 0.0F, 0.0F, null);
      localObject = localCanvas;
      this.i.F();
      this.C.unlockCanvasAndPost(localCanvas);
      paramxd = this.I;
      long l1 = this.h;
      this.h = (1L + l1);
      paramxd.put(Long.valueOf(l1), Long.valueOf(paramLong));
      if (this.M) {
        mc.l(AppseeBackgroundUploader.i("\013X.C\"@4XzZ\0276X8J"), 1);
      }
      this.K.a();
      i(false);
      this.K.F();
      this.D.F();
      return;
    }
    finally
    {
      this.C.unlockCanvasAndPost((Canvas)localObject);
    }
  }
  
  public boolean i()
  {
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/fb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.appsee;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.media.MediaMuxer;
import android.os.Build.VERSION;
import android.view.Surface;
import java.io.InvalidObjectException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@TargetApi(21)
class co
  implements j
{
  private mo A = yc.i().i(AppseeBackgroundUploader.i("'+I)K,O\006H,^\037Qr\0338P>R"));
  private mo B = yc.i().i(AppseeBackgroundUploader.i("l4\\5[t=1X7P"));
  private CountDownLatch C;
  private boolean D;
  private long G;
  private int I;
  private mo J = yc.i().i(AppseeBackgroundUploader.i("\fK!\\-Z\031P4It\006/P?["));
  private Surface K;
  private int M;
  private HashMap<Long, Long> a = new HashMap();
  private MediaCodec b;
  private Rect c;
  private int d;
  private we e;
  private int g;
  private int h;
  private String i;
  private MediaMuxer k;
  private long l;
  private io m;
  
  public void i()
    throws Exception
  {
    this.J.l();
    this.A.l();
    this.B.l();
    if (this.b != null)
    {
      mc.l(AppseeBackgroundUploader.i("~(V.V\023||\037f"), 1);
      this.b.signalEndOfInputStream();
      if (!this.C.await(10L, TimeUnit.SECONDS)) {
        mc.i(AppseeBackgroundUploader.i("\033C\"O(\t5J.\037w\033.\031'T\"Z2^pC;St\033~~\000yo^#\t8ZzHc\035(M5["), 2);
      }
      mc.l(this.a.size() + AppseeBackgroundUploader.i("\034"), 1);
      if (!ge.i().b())
      {
        this.m.i();
        this.b.reset();
      }
      this.b.stop();
      this.b.release();
      this.m = null;
    }
    if (this.k != null)
    {
      this.k.stop();
      this.k.release();
    }
    if (this.K != null) {
      this.K.release();
    }
    if (this.e != null)
    {
      this.e.a();
      this.e = null;
    }
    this.b = null;
    this.K = null;
    this.k = null;
  }
  
  public void i(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString, boolean paramBoolean)
    throws Exception
  {
    mc.l(AppseeBackgroundUploader.i("=0R;C.F%S?\037\026P}\0305I?EvR/@>VrDd\0068Z,Oo\\%M?PzZ\0273]5G"), 1);
    if (Build.VERSION.SDK_INT < 21) {
      throw new InvalidObjectException(AppseeBackgroundUploader.i("\000F6S3O~\004\nP4P9v2Z?Q7E1\027?U!E;\n.LzV4Le\0252Z9T\"V|V>\025>Xf\021,\033.D+X#@>\037,Zc\0075V>F"));
    }
    this.M = paramInt1;
    this.I = paramInt2;
    this.c = new Rect(0, 0, this.M, this.I);
    this.g = paramInt3;
    this.h = paramInt4;
    this.i = paramString;
    this.D = paramBoolean;
    this.a.clear();
    this.G = 0L;
    this.l = 0L;
    this.e = new we(this);
    this.e.start();
    this.e.i();
  }
  
  public void i(xd paramxd, long paramLong)
    throws Exception
  {
    Object localObject = null;
    this.B.a();
    if (this.D) {
      mc.l(AppseeBackgroundUploader.i("\025Re\0007U(\n*D/F>Z(\037r\0252O1F"), 1);
    }
    if (!this.K.isValid()) {
      return;
    }
    try
    {
      Canvas localCanvas = this.K.lockCanvas(this.c);
      localObject = localCanvas;
      if (this.D)
      {
        localObject = localCanvas;
        mc.l(AppseeBackgroundUploader.i("}\"T%^\023~T!\n*D/F>Z(\037r\0252O1F"), 1);
      }
      localObject = localCanvas;
      this.J.a();
      localObject = localCanvas;
      localCanvas.drawColor(-16777216);
      localObject = localCanvas;
      localCanvas.drawBitmap(paramxd.i(), 0.0F, 0.0F, null);
      localObject = localCanvas;
      this.J.F();
      this.K.unlockCanvasAndPost(localCanvas);
      paramxd = this.a;
      long l1 = this.l;
      this.l = (1L + l1);
      paramxd.put(Long.valueOf(l1), Long.valueOf(paramLong));
      this.B.F();
      return;
    }
    finally
    {
      this.K.unlockCanvasAndPost((Canvas)localObject);
    }
  }
  
  public boolean i()
  {
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/co.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.appsee;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodec.Callback;
import android.media.MediaCodec.CodecException;
import android.media.MediaMuxer;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

@TargetApi(21)
class io
  extends MediaCodec.Callback
{
  private boolean G;
  
  public void i()
  {
    try
    {
      this.G = true;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void onError(MediaCodec paramMediaCodec, MediaCodec.CodecException paramCodecException)
  {
    vd.l(paramCodecException, AppseeBackgroundUploader.i("p E~\006~R!\n9C(L5\037?Qr\0332Z>R"));
  }
  
  public void onInputBufferAvailable(MediaCodec paramMediaCodec, int paramInt) {}
  
  public void onOutputBufferAvailable(MediaCodec paramMediaCodec, int paramInt, MediaCodec.BufferInfo paramBufferInfo)
  {
    for (;;)
    {
      try
      {
        if (this.G)
        {
          vd.l(null, AppseeBackgroundUploader.i("@%a:^:Z?l\b~,J+}/]3S,J\022~a\005~L7@sU?W1]7\026;Y(\\\"\0251V}\030<Z,Ao]-Zz[3La\033%V4\024"));
          return;
        }
        if (co.i(this.k)) {
          mc.l(paramInt, 1);
        }
        co.i(this.k).a();
        ByteBuffer localByteBuffer = paramMediaCodec.getOutputBuffer(paramInt).duplicate();
        if ((paramBufferInfo.flags & 0x2) != 0)
        {
          if (co.i(this.k)) {
            mc.l(AppseeBackgroundUploader.i("\030T:D+\n/F4Y3X1\022$R=P"), 1);
          }
          paramBufferInfo.size = 0;
        }
        if ((paramBufferInfo.flags & 0x4) != 0)
        {
          if (co.i(this.k)) {
            mc.l(AppseeBackgroundUploader.i("\tE9G>\037\037pBT0_1R"), 1);
          }
          paramBufferInfo.size = 0;
        }
        if (paramBufferInfo.size != 0)
        {
          if (co.i(this.k) == null)
          {
            mc.l(AppseeBackgroundUploader.i("\002_7O>\t3LzV\035\"Z1Y9O4PPz$C2\016 DjI\"\\\016ljK8H8\034<M,E\033;aY-N7_?O~R)\030<Xzv\022\037j\035bE$\013o\020l\000x\001h\016`\031wP:\021v\024\034"), 1);
            co.i(this.k, paramMediaCodec.getOutputFormat(paramInt));
          }
          long l1 = co.i(this.k);
          if (co.i(this.k).containsKey(Long.valueOf(l1)))
          {
            long l2 = ((Long)co.i(this.k).get(Long.valueOf(l1))).longValue();
            if (co.i(this.k)) {
              mc.l(l2, 1);
            }
            paramBufferInfo.presentationTimeUs = l2;
            co.i(this.k).writeSampleData(co.i(this.k), localByteBuffer, paramBufferInfo);
            co.i(this.k).remove(Long.valueOf(l1));
            co.i(this.k).releaseOutputBuffer(paramInt, false);
            if ((paramBufferInfo.flags & 0x4) != 0) {
              co.i(this.k).countDown();
            }
            co.i(this.k).F();
          }
          else
          {
            mc.l(co.l(this.k) - 1L, 1);
          }
        }
      }
      finally {}
    }
  }
  
  /* Error */
  public void onOutputFormatChanged(MediaCodec paramMediaCodec, android.media.MediaFormat paramMediaFormat)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 22	com/appsee/io:G	Z
    //   6: ifeq +15 -> 21
    //   9: aconst_null
    //   10: ldc -65
    //   12: invokestatic 36	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   15: invokestatic 42	com/appsee/vd:l	(Ljava/lang/Throwable;Ljava/lang/String;)V
    //   18: aload_0
    //   19: monitorexit
    //   20: return
    //   21: aload_0
    //   22: getfield 17	com/appsee/io:k	Lcom/appsee/co;
    //   25: invokestatic 51	com/appsee/co:i	(Lcom/appsee/co;)Z
    //   28: ifeq +12 -> 40
    //   31: ldc -63
    //   33: invokestatic 36	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   36: iconst_1
    //   37: invokestatic 73	com/appsee/mc:l	(Ljava/lang/String;I)V
    //   40: aload_0
    //   41: getfield 17	com/appsee/io:k	Lcom/appsee/co;
    //   44: aload_2
    //   45: invokestatic 118	com/appsee/co:i	(Lcom/appsee/co;Landroid/media/MediaFormat;)V
    //   48: goto -30 -> 18
    //   51: astore_1
    //   52: aload_0
    //   53: monitorexit
    //   54: aload_1
    //   55: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	io
    //   0	56	1	paramMediaCodec	MediaCodec
    //   0	56	2	paramMediaFormat	android.media.MediaFormat
    // Exception table:
    //   from	to	target	type
    //   2	18	51	finally
    //   21	40	51	finally
    //   40	48	51	finally
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/io.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.mixpanel.android.java_websocket;

import android.annotation.SuppressLint;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

@SuppressLint({"Assert"})
public class SSLSocketChannel2
  implements ByteChannel, WrappedByteChannel
{
  protected static ByteBuffer emptybuffer;
  protected int bufferallocations = 0;
  protected ExecutorService exec;
  protected ByteBuffer inCrypt;
  protected ByteBuffer inData;
  protected ByteBuffer outCrypt;
  protected SSLEngineResult readEngineResult;
  protected SelectionKey selectionKey;
  protected SocketChannel socketChannel;
  protected SSLEngine sslEngine;
  protected List<Future<?>> tasks;
  protected SSLEngineResult writeEngineResult;
  
  static
  {
    if (!SSLSocketChannel2.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      emptybuffer = ByteBuffer.allocate(0);
      return;
    }
  }
  
  public SSLSocketChannel2(SocketChannel paramSocketChannel, SSLEngine paramSSLEngine, ExecutorService paramExecutorService, SelectionKey paramSelectionKey)
    throws IOException
  {
    if ((paramSocketChannel == null) || (paramSSLEngine == null) || (paramExecutorService == null)) {
      throw new IllegalArgumentException("parameter must not be null");
    }
    this.socketChannel = paramSocketChannel;
    this.sslEngine = paramSSLEngine;
    this.exec = paramExecutorService;
    paramSocketChannel = new SSLEngineResult(SSLEngineResult.Status.BUFFER_UNDERFLOW, paramSSLEngine.getHandshakeStatus(), 0, 0);
    this.writeEngineResult = paramSocketChannel;
    this.readEngineResult = paramSocketChannel;
    this.tasks = new ArrayList(3);
    if (paramSelectionKey != null)
    {
      paramSelectionKey.interestOps(paramSelectionKey.interestOps() | 0x4);
      this.selectionKey = paramSelectionKey;
    }
    createBuffers(paramSSLEngine.getSession());
    this.socketChannel.write(wrap(emptybuffer));
    processHandshake();
  }
  
  private void consumeFutureUninterruptible(Future<?> paramFuture)
  {
    int i = 0;
    try
    {
      paramFuture.get();
      if (i != 0) {
        Thread.currentThread().interrupt();
      }
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        i = 1;
      }
    }
    catch (ExecutionException paramFuture)
    {
      throw new RuntimeException(paramFuture);
    }
  }
  
  private boolean isHandShakeComplete()
  {
    SSLEngineResult.HandshakeStatus localHandshakeStatus = this.sslEngine.getHandshakeStatus();
    return (localHandshakeStatus == SSLEngineResult.HandshakeStatus.FINISHED) || (localHandshakeStatus == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING);
  }
  
  private void processHandshake()
    throws IOException
  {
    for (;;)
    {
      Object localObject3;
      try
      {
        Object localObject1 = this.sslEngine.getHandshakeStatus();
        localObject3 = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
        if (localObject1 == localObject3) {
          return;
        }
        if (this.tasks.isEmpty()) {
          break label101;
        }
        localObject1 = this.tasks.iterator();
        if (!((Iterator)localObject1).hasNext()) {
          break label101;
        }
        localObject3 = (Future)((Iterator)localObject1).next();
        if (((Future)localObject3).isDone())
        {
          ((Iterator)localObject1).remove();
          continue;
        }
        if (!isBlocking()) {
          continue;
        }
      }
      finally {}
      consumeFutureUninterruptible((Future)localObject3);
      continue;
      label101:
      if (this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP)
      {
        if ((!isBlocking()) || (this.readEngineResult.getStatus() == SSLEngineResult.Status.BUFFER_UNDERFLOW))
        {
          this.inCrypt.compact();
          if (this.socketChannel.read(this.inCrypt) == -1) {
            throw new IOException("connection closed unexpectedly by peer");
          }
          this.inCrypt.flip();
        }
        this.inData.compact();
        unwrap();
        if (this.readEngineResult.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED)
        {
          createBuffers(this.sslEngine.getSession());
          continue;
        }
      }
      consumeDelegatedTasks();
      if ((this.tasks.isEmpty()) || (this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_WRAP))
      {
        this.socketChannel.write(wrap(emptybuffer));
        if (this.writeEngineResult.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED)
        {
          createBuffers(this.sslEngine.getSession());
          continue;
        }
      }
      assert (this.sslEngine.getHandshakeStatus() != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING);
      this.bufferallocations = 1;
    }
  }
  
  private int readRemaining(ByteBuffer paramByteBuffer)
    throws SSLException
  {
    int i;
    if (this.inData.hasRemaining()) {
      i = transfereTo(this.inData, paramByteBuffer);
    }
    int j;
    do
    {
      return i;
      if (!this.inData.hasRemaining()) {
        this.inData.clear();
      }
      if (!this.inCrypt.hasRemaining()) {
        break;
      }
      unwrap();
      j = transfereTo(this.inData, paramByteBuffer);
      i = j;
    } while (j > 0);
    return 0;
  }
  
  private int transfereTo(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
  {
    int j = paramByteBuffer1.remaining();
    int i = paramByteBuffer2.remaining();
    if (j > i)
    {
      int k = Math.min(j, i);
      i = 0;
      for (;;)
      {
        j = k;
        if (i >= k) {
          break;
        }
        paramByteBuffer2.put(paramByteBuffer1.get());
        i += 1;
      }
    }
    paramByteBuffer2.put(paramByteBuffer1);
    return j;
  }
  
  private ByteBuffer unwrap()
    throws SSLException
  {
    try
    {
      int i;
      do
      {
        i = this.inData.remaining();
        this.readEngineResult = this.sslEngine.unwrap(this.inCrypt, this.inData);
      } while ((this.readEngineResult.getStatus() == SSLEngineResult.Status.OK) && ((i != this.inData.remaining()) || (this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP)));
      this.inData.flip();
      ByteBuffer localByteBuffer = this.inData;
      return localByteBuffer;
    }
    finally {}
  }
  
  private ByteBuffer wrap(ByteBuffer paramByteBuffer)
    throws SSLException
  {
    try
    {
      this.outCrypt.compact();
      this.writeEngineResult = this.sslEngine.wrap(paramByteBuffer, this.outCrypt);
      this.outCrypt.flip();
      paramByteBuffer = this.outCrypt;
      return paramByteBuffer;
    }
    finally
    {
      paramByteBuffer = finally;
      throw paramByteBuffer;
    }
  }
  
  public void close()
    throws IOException
  {
    this.sslEngine.closeOutbound();
    this.sslEngine.getSession().invalidate();
    if (this.socketChannel.isOpen()) {
      this.socketChannel.write(wrap(emptybuffer));
    }
    this.socketChannel.close();
    this.exec.shutdownNow();
  }
  
  public SelectableChannel configureBlocking(boolean paramBoolean)
    throws IOException
  {
    return this.socketChannel.configureBlocking(paramBoolean);
  }
  
  public boolean connect(SocketAddress paramSocketAddress)
    throws IOException
  {
    return this.socketChannel.connect(paramSocketAddress);
  }
  
  protected void consumeDelegatedTasks()
  {
    for (;;)
    {
      Runnable localRunnable = this.sslEngine.getDelegatedTask();
      if (localRunnable == null) {
        break;
      }
      this.tasks.add(this.exec.submit(localRunnable));
    }
  }
  
  protected void createBuffers(SSLSession paramSSLSession)
  {
    int i = paramSSLSession.getApplicationBufferSize();
    int j = paramSSLSession.getPacketBufferSize();
    if (this.inData == null)
    {
      this.inData = ByteBuffer.allocate(i);
      this.outCrypt = ByteBuffer.allocate(j);
      this.inCrypt = ByteBuffer.allocate(j);
    }
    for (;;)
    {
      this.inData.rewind();
      this.inData.flip();
      this.inCrypt.rewind();
      this.inCrypt.flip();
      this.outCrypt.rewind();
      this.outCrypt.flip();
      this.bufferallocations += 1;
      return;
      if (this.inData.capacity() != i) {
        this.inData = ByteBuffer.allocate(i);
      }
      if (this.outCrypt.capacity() != j) {
        this.outCrypt = ByteBuffer.allocate(j);
      }
      if (this.inCrypt.capacity() != j) {
        this.inCrypt = ByteBuffer.allocate(j);
      }
    }
  }
  
  public boolean finishConnect()
    throws IOException
  {
    return this.socketChannel.finishConnect();
  }
  
  public boolean isBlocking()
  {
    return this.socketChannel.isBlocking();
  }
  
  public boolean isConnected()
  {
    return this.socketChannel.isConnected();
  }
  
  public boolean isInboundDone()
  {
    return this.sslEngine.isInboundDone();
  }
  
  public boolean isNeedRead()
  {
    return (this.inData.hasRemaining()) || ((this.inCrypt.hasRemaining()) && (this.readEngineResult.getStatus() != SSLEngineResult.Status.BUFFER_UNDERFLOW) && (this.readEngineResult.getStatus() != SSLEngineResult.Status.CLOSED));
  }
  
  public boolean isNeedWrite()
  {
    return (this.outCrypt.hasRemaining()) || (!isHandShakeComplete());
  }
  
  public boolean isOpen()
  {
    return this.socketChannel.isOpen();
  }
  
  public int read(ByteBuffer paramByteBuffer)
    throws IOException
  {
    int i = 0;
    if (!paramByteBuffer.hasRemaining()) {}
    int j;
    do
    {
      do
      {
        return i;
        if (isHandShakeComplete()) {
          break;
        }
        if (isBlocking()) {
          while (!isHandShakeComplete()) {
            processHandshake();
          }
        }
        processHandshake();
      } while (!isHandShakeComplete());
      if (this.bufferallocations <= 1) {
        createBuffers(this.sslEngine.getSession());
      }
      j = readRemaining(paramByteBuffer);
      i = j;
    } while (j != 0);
    assert (this.inData.position() == 0);
    this.inData.clear();
    if (!this.inCrypt.hasRemaining()) {
      this.inCrypt.clear();
    }
    while (((isBlocking()) || (this.readEngineResult.getStatus() == SSLEngineResult.Status.BUFFER_UNDERFLOW)) && (this.socketChannel.read(this.inCrypt) == -1))
    {
      return -1;
      this.inCrypt.compact();
    }
    this.inCrypt.flip();
    unwrap();
    i = transfereTo(this.inData, paramByteBuffer);
    if ((i == 0) && (isBlocking())) {
      return read(paramByteBuffer);
    }
    return i;
  }
  
  public int readMore(ByteBuffer paramByteBuffer)
    throws SSLException
  {
    return readRemaining(paramByteBuffer);
  }
  
  public Socket socket()
  {
    return this.socketChannel.socket();
  }
  
  public int write(ByteBuffer paramByteBuffer)
    throws IOException
  {
    if (!isHandShakeComplete())
    {
      processHandshake();
      return 0;
    }
    if (this.bufferallocations <= 1) {
      createBuffers(this.sslEngine.getSession());
    }
    return this.socketChannel.write(wrap(paramByteBuffer));
  }
  
  public void writeMore()
    throws IOException
  {
    write(this.outCrypt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/SSLSocketChannel2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
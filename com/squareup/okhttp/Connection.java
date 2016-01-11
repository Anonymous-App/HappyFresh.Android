package com.squareup.okhttp;

import com.squareup.okhttp.internal.ConnectionSpecSelector;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.Version;
import com.squareup.okhttp.internal.framed.FramedConnection;
import com.squareup.okhttp.internal.framed.FramedConnection.Builder;
import com.squareup.okhttp.internal.http.FramedTransport;
import com.squareup.okhttp.internal.http.HttpConnection;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpTransport;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RouteException;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.UnknownServiceException;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Source;

public final class Connection
{
  private FramedConnection framedConnection;
  private Handshake handshake;
  private HttpConnection httpConnection;
  private long idleStartTimeNs;
  private Object owner;
  private final ConnectionPool pool;
  private Protocol protocol;
  private int recycleCount;
  private final Route route;
  private Socket socket;
  
  public Connection(ConnectionPool paramConnectionPool, Route paramRoute)
  {
    this.pool = paramConnectionPool;
    this.route = paramRoute;
  }
  
  private void connectSocket(int paramInt1, int paramInt2, int paramInt3, ConnectionSpecSelector paramConnectionSpecSelector)
    throws IOException
  {
    this.socket.setSoTimeout(paramInt2);
    Platform.get().connectSocket(this.socket, this.route.getSocketAddress(), paramInt1);
    if (this.route.address.getSslSocketFactory() != null) {
      connectTls(paramInt2, paramInt3, paramConnectionSpecSelector);
    }
    while ((this.protocol == Protocol.SPDY_3) || (this.protocol == Protocol.HTTP_2))
    {
      this.socket.setSoTimeout(0);
      this.framedConnection = new FramedConnection.Builder(this.route.address.uriHost, true, this.socket).protocol(this.protocol).build();
      this.framedConnection.sendConnectionPreface();
      return;
      this.protocol = Protocol.HTTP_1_1;
    }
    this.httpConnection = new HttpConnection(this.pool, this, this.socket);
  }
  
  private void connectTls(int paramInt1, int paramInt2, ConnectionSpecSelector paramConnectionSpecSelector)
    throws IOException
  {
    if (this.route.requiresTunnel()) {
      createTunnel(paramInt1, paramInt2);
    }
    Address localAddress = this.route.getAddress();
    Object localObject3 = localAddress.getSslSocketFactory();
    Object localObject2 = null;
    Object localObject1 = null;
    Handshake localHandshake;
    try
    {
      localObject3 = (SSLSocket)((SSLSocketFactory)localObject3).createSocket(this.socket, localAddress.getUriHost(), localAddress.getUriPort(), true);
      localObject1 = localObject3;
      localObject2 = localObject3;
      paramConnectionSpecSelector = paramConnectionSpecSelector.configureSecureSocket((SSLSocket)localObject3);
      localObject1 = localObject3;
      localObject2 = localObject3;
      if (paramConnectionSpecSelector.supportsTlsExtensions())
      {
        localObject1 = localObject3;
        localObject2 = localObject3;
        Platform.get().configureTlsExtensions((SSLSocket)localObject3, localAddress.getUriHost(), localAddress.getProtocols());
      }
      localObject1 = localObject3;
      localObject2 = localObject3;
      ((SSLSocket)localObject3).startHandshake();
      localObject1 = localObject3;
      localObject2 = localObject3;
      localHandshake = Handshake.get(((SSLSocket)localObject3).getSession());
      localObject1 = localObject3;
      localObject2 = localObject3;
      if (!localAddress.getHostnameVerifier().verify(localAddress.getUriHost(), ((SSLSocket)localObject3).getSession()))
      {
        localObject1 = localObject3;
        localObject2 = localObject3;
        paramConnectionSpecSelector = (X509Certificate)localHandshake.peerCertificates().get(0);
        localObject1 = localObject3;
        localObject2 = localObject3;
        throw new SSLPeerUnverifiedException("Hostname " + localAddress.getUriHost() + " not verified:" + "\n    certificate: " + CertificatePinner.pin(paramConnectionSpecSelector) + "\n    DN: " + paramConnectionSpecSelector.getSubjectDN().getName() + "\n    subjectAltNames: " + OkHostnameVerifier.allSubjectAltNames(paramConnectionSpecSelector));
      }
    }
    catch (AssertionError paramConnectionSpecSelector)
    {
      localObject2 = localObject1;
      if (!Util.isAndroidGetsocknameError(paramConnectionSpecSelector)) {
        break label498;
      }
      localObject2 = localObject1;
      throw new IOException(paramConnectionSpecSelector);
    }
    finally
    {
      if (localObject2 != null) {
        Platform.get().afterHandshake((SSLSocket)localObject2);
      }
      if (0 == 0) {
        Util.closeQuietly((Socket)localObject2);
      }
    }
    localObject1 = localObject3;
    localObject2 = localObject3;
    localAddress.getCertificatePinner().check(localAddress.getUriHost(), localHandshake.peerCertificates());
    localObject1 = localObject3;
    localObject2 = localObject3;
    if (paramConnectionSpecSelector.supportsTlsExtensions())
    {
      localObject1 = localObject3;
      localObject2 = localObject3;
      paramConnectionSpecSelector = Platform.get().getSelectedProtocol((SSLSocket)localObject3);
      localObject1 = localObject3;
      localObject2 = localObject3;
      this.socket = ((Socket)localObject3);
      localObject1 = localObject3;
      localObject2 = localObject3;
      this.handshake = localHandshake;
      if (paramConnectionSpecSelector == null) {
        break label483;
      }
      localObject1 = localObject3;
      localObject2 = localObject3;
    }
    for (paramConnectionSpecSelector = Protocol.get(paramConnectionSpecSelector);; paramConnectionSpecSelector = Protocol.HTTP_1_1)
    {
      localObject1 = localObject3;
      localObject2 = localObject3;
      this.protocol = paramConnectionSpecSelector;
      if (localObject3 != null) {
        Platform.get().afterHandshake((SSLSocket)localObject3);
      }
      if (1 == 0) {
        Util.closeQuietly((Socket)localObject3);
      }
      return;
      paramConnectionSpecSelector = null;
      break;
      label483:
      localObject1 = localObject3;
      localObject2 = localObject3;
    }
    label498:
    localObject2 = localObject1;
    throw paramConnectionSpecSelector;
  }
  
  private void createTunnel(int paramInt1, int paramInt2)
    throws IOException
  {
    Object localObject1 = createTunnelRequest();
    HttpConnection localHttpConnection = new HttpConnection(this.pool, this, this.socket);
    localHttpConnection.setTimeouts(paramInt1, paramInt2);
    Object localObject2 = ((Request)localObject1).httpUrl();
    String str = "CONNECT " + ((HttpUrl)localObject2).host() + ":" + ((HttpUrl)localObject2).port() + " HTTP/1.1";
    do
    {
      localHttpConnection.writeRequest(((Request)localObject1).headers(), str);
      localHttpConnection.flush();
      localObject1 = localHttpConnection.readResponse().request((Request)localObject1).build();
      long l2 = OkHeaders.contentLength((Response)localObject1);
      long l1 = l2;
      if (l2 == -1L) {
        l1 = 0L;
      }
      localObject2 = localHttpConnection.newFixedLengthSource(l1);
      Util.skipAll((Source)localObject2, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
      ((Source)localObject2).close();
      switch (((Response)localObject1).code())
      {
      default: 
        throw new IOException("Unexpected response code for CONNECT: " + ((Response)localObject1).code());
      case 200: 
        if (localHttpConnection.bufferSize() <= 0L) {
          break;
        }
        throw new IOException("TLS tunnel buffered too many bytes!");
      case 407: 
        localObject2 = OkHeaders.processAuthHeader(this.route.getAddress().getAuthenticator(), (Response)localObject1, this.route.getProxy());
        localObject1 = localObject2;
      }
    } while (localObject2 != null);
    throw new IOException("Failed to authenticate with proxy");
  }
  
  private Request createTunnelRequest()
    throws IOException
  {
    HttpUrl localHttpUrl = new HttpUrl.Builder().scheme("https").host(this.route.address.uriHost).port(this.route.address.uriPort).build();
    return new Request.Builder().url(localHttpUrl).header("Host", Util.hostHeader(localHttpUrl)).header("Proxy-Connection", "Keep-Alive").header("User-Agent", Version.userAgent()).build();
  }
  
  boolean clearOwner()
  {
    synchronized (this.pool)
    {
      if (this.owner == null) {
        return false;
      }
      this.owner = null;
      return true;
    }
  }
  
  void closeIfOwnedBy(Object paramObject)
    throws IOException
  {
    if (isFramed()) {
      throw new IllegalStateException();
    }
    synchronized (this.pool)
    {
      if (this.owner != paramObject) {
        return;
      }
      this.owner = null;
      if (this.socket != null)
      {
        this.socket.close();
        return;
      }
    }
  }
  
  void connect(int paramInt1, int paramInt2, int paramInt3, List<ConnectionSpec> paramList, boolean paramBoolean)
    throws RouteException
  {
    if (this.protocol != null) {
      throw new IllegalStateException("already connected");
    }
    Object localObject2 = null;
    ConnectionSpecSelector localConnectionSpecSelector = new ConnectionSpecSelector(paramList);
    Proxy localProxy = this.route.getProxy();
    Address localAddress = this.route.getAddress();
    Object localObject1 = localObject2;
    if (this.route.address.getSslSocketFactory() == null)
    {
      localObject1 = localObject2;
      if (!paramList.contains(ConnectionSpec.CLEARTEXT)) {
        throw new RouteException(new UnknownServiceException("CLEARTEXT communication not supported: " + paramList));
      }
    }
    for (;;)
    {
      try
      {
        paramList = new Socket(localProxy);
        this.socket = paramList;
        connectSocket(paramInt1, paramInt2, paramInt3, localConnectionSpecSelector);
      }
      catch (IOException localIOException)
      {
        Util.closeQuietly(this.socket);
        this.socket = null;
        this.handshake = null;
        this.protocol = null;
        this.httpConnection = null;
        this.framedConnection = null;
        if (localObject1 != null) {
          continue;
        }
        paramList = new RouteException(localIOException);
        if (!paramBoolean) {
          continue;
        }
        localObject1 = paramList;
        if (localConnectionSpecSelector.connectionFailed(localIOException)) {
          continue;
        }
        throw paramList;
        ((RouteException)localObject1).addConnectException(localIOException);
        paramList = (List<ConnectionSpec>)localObject1;
        continue;
      }
      if (this.protocol != null) {
        return;
      }
      if ((localProxy.type() == Proxy.Type.DIRECT) || (localProxy.type() == Proxy.Type.HTTP)) {
        paramList = localAddress.getSocketFactory().createSocket();
      }
    }
  }
  
  void connectAndSetOwner(OkHttpClient paramOkHttpClient, Object paramObject)
    throws RouteException
  {
    setOwner(paramObject);
    if (!isConnected())
    {
      paramObject = this.route.address.getConnectionSpecs();
      connect(paramOkHttpClient.getConnectTimeout(), paramOkHttpClient.getReadTimeout(), paramOkHttpClient.getWriteTimeout(), (List)paramObject, paramOkHttpClient.getRetryOnConnectionFailure());
      if (isFramed()) {
        paramOkHttpClient.getConnectionPool().share(this);
      }
      paramOkHttpClient.routeDatabase().connected(getRoute());
    }
    setTimeouts(paramOkHttpClient.getReadTimeout(), paramOkHttpClient.getWriteTimeout());
  }
  
  public Handshake getHandshake()
  {
    return this.handshake;
  }
  
  long getIdleStartTimeNs()
  {
    if (this.framedConnection == null) {
      return this.idleStartTimeNs;
    }
    return this.framedConnection.getIdleStartTimeNs();
  }
  
  Object getOwner()
  {
    synchronized (this.pool)
    {
      Object localObject1 = this.owner;
      return localObject1;
    }
  }
  
  public Protocol getProtocol()
  {
    if (this.protocol != null) {
      return this.protocol;
    }
    return Protocol.HTTP_1_1;
  }
  
  public Route getRoute()
  {
    return this.route;
  }
  
  public Socket getSocket()
  {
    return this.socket;
  }
  
  void incrementRecycleCount()
  {
    this.recycleCount += 1;
  }
  
  boolean isAlive()
  {
    return (!this.socket.isClosed()) && (!this.socket.isInputShutdown()) && (!this.socket.isOutputShutdown());
  }
  
  boolean isConnected()
  {
    return this.protocol != null;
  }
  
  boolean isFramed()
  {
    return this.framedConnection != null;
  }
  
  boolean isIdle()
  {
    return (this.framedConnection == null) || (this.framedConnection.isIdle());
  }
  
  boolean isReadable()
  {
    if (this.httpConnection != null) {
      return this.httpConnection.isReadable();
    }
    return true;
  }
  
  Transport newTransport(HttpEngine paramHttpEngine)
    throws IOException
  {
    if (this.framedConnection != null) {
      return new FramedTransport(paramHttpEngine, this.framedConnection);
    }
    return new HttpTransport(paramHttpEngine, this.httpConnection);
  }
  
  BufferedSink rawSink()
  {
    if (this.httpConnection == null) {
      throw new UnsupportedOperationException();
    }
    return this.httpConnection.rawSink();
  }
  
  BufferedSource rawSource()
  {
    if (this.httpConnection == null) {
      throw new UnsupportedOperationException();
    }
    return this.httpConnection.rawSource();
  }
  
  int recycleCount()
  {
    return this.recycleCount;
  }
  
  void resetIdleStartTime()
  {
    if (this.framedConnection != null) {
      throw new IllegalStateException("framedConnection != null");
    }
    this.idleStartTimeNs = System.nanoTime();
  }
  
  void setOwner(Object paramObject)
  {
    if (isFramed()) {
      return;
    }
    synchronized (this.pool)
    {
      if (this.owner != null) {
        throw new IllegalStateException("Connection already has an owner!");
      }
    }
    this.owner = paramObject;
  }
  
  void setTimeouts(int paramInt1, int paramInt2)
    throws RouteException
  {
    if (this.protocol == null) {
      throw new IllegalStateException("not connected");
    }
    if (this.httpConnection != null) {}
    try
    {
      this.socket.setSoTimeout(paramInt1);
      this.httpConnection.setTimeouts(paramInt1, paramInt2);
      return;
    }
    catch (IOException localIOException)
    {
      throw new RouteException(localIOException);
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("Connection{").append(this.route.address.uriHost).append(":").append(this.route.address.uriPort).append(", proxy=").append(this.route.proxy).append(" hostAddress=").append(this.route.inetSocketAddress.getAddress().getHostAddress()).append(" cipherSuite=");
    if (this.handshake != null) {}
    for (String str = this.handshake.cipherSuite();; str = "none") {
      return str + " protocol=" + this.protocol + '}';
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/okhttp/Connection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
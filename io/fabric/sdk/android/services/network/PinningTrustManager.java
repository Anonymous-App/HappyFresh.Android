package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

class PinningTrustManager
  implements X509TrustManager
{
  private static final long PIN_FRESHNESS_DURATION_MILLIS = 15552000000L;
  private final Set<X509Certificate> cache = Collections.synchronizedSet(new HashSet());
  private final long pinCreationTimeMillis;
  private final List<byte[]> pins = new LinkedList();
  private final SystemKeyStore systemKeyStore;
  private final TrustManager[] systemTrustManagers = initializeSystemTrustManagers(paramSystemKeyStore);
  
  public PinningTrustManager(SystemKeyStore paramSystemKeyStore, PinningInfoProvider paramPinningInfoProvider)
  {
    this.systemKeyStore = paramSystemKeyStore;
    this.pinCreationTimeMillis = paramPinningInfoProvider.getPinCreationTimeInMillis();
    paramSystemKeyStore = paramPinningInfoProvider.getPins();
    int j = paramSystemKeyStore.length;
    int i = 0;
    while (i < j)
    {
      paramPinningInfoProvider = paramSystemKeyStore[i];
      this.pins.add(hexStringToByteArray(paramPinningInfoProvider));
      i += 1;
    }
  }
  
  private void checkPinTrust(X509Certificate[] paramArrayOfX509Certificate)
    throws CertificateException
  {
    if ((this.pinCreationTimeMillis != -1L) && (System.currentTimeMillis() - this.pinCreationTimeMillis > 15552000000L))
    {
      Fabric.getLogger().w("Fabric", "Certificate pins are stale, (" + (System.currentTimeMillis() - this.pinCreationTimeMillis) + " millis vs " + 15552000000L + " millis) " + "falling back to system trust.");
      return;
    }
    paramArrayOfX509Certificate = CertificateChainCleaner.getCleanChain(paramArrayOfX509Certificate, this.systemKeyStore);
    int j = paramArrayOfX509Certificate.length;
    int i = 0;
    for (;;)
    {
      if (i >= j) {
        break label120;
      }
      if (isValidPin(paramArrayOfX509Certificate[i])) {
        break;
      }
      i += 1;
    }
    label120:
    throw new CertificateException("No valid pins found in chain!");
  }
  
  private void checkSystemTrust(X509Certificate[] paramArrayOfX509Certificate, String paramString)
    throws CertificateException
  {
    TrustManager[] arrayOfTrustManager = this.systemTrustManagers;
    int j = arrayOfTrustManager.length;
    int i = 0;
    while (i < j)
    {
      ((X509TrustManager)arrayOfTrustManager[i]).checkServerTrusted(paramArrayOfX509Certificate, paramString);
      i += 1;
    }
  }
  
  private byte[] hexStringToByteArray(String paramString)
  {
    int j = paramString.length();
    byte[] arrayOfByte = new byte[j / 2];
    int i = 0;
    while (i < j)
    {
      arrayOfByte[(i / 2)] = ((byte)((Character.digit(paramString.charAt(i), 16) << 4) + Character.digit(paramString.charAt(i + 1), 16)));
      i += 2;
    }
    return arrayOfByte;
  }
  
  private TrustManager[] initializeSystemTrustManagers(SystemKeyStore paramSystemKeyStore)
  {
    try
    {
      TrustManagerFactory localTrustManagerFactory = TrustManagerFactory.getInstance("X509");
      localTrustManagerFactory.init(paramSystemKeyStore.trustStore);
      paramSystemKeyStore = localTrustManagerFactory.getTrustManagers();
      return paramSystemKeyStore;
    }
    catch (NoSuchAlgorithmException paramSystemKeyStore)
    {
      throw new AssertionError(paramSystemKeyStore);
    }
    catch (KeyStoreException paramSystemKeyStore)
    {
      throw new AssertionError(paramSystemKeyStore);
    }
  }
  
  private boolean isValidPin(X509Certificate paramX509Certificate)
    throws CertificateException
  {
    try
    {
      paramX509Certificate = MessageDigest.getInstance("SHA1").digest(paramX509Certificate.getPublicKey().getEncoded());
      Iterator localIterator = this.pins.iterator();
      while (localIterator.hasNext())
      {
        boolean bool = Arrays.equals((byte[])localIterator.next(), paramX509Certificate);
        if (bool) {
          return true;
        }
      }
      return false;
    }
    catch (NoSuchAlgorithmException paramX509Certificate)
    {
      throw new CertificateException(paramX509Certificate);
    }
  }
  
  public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
    throws CertificateException
  {
    throw new CertificateException("Client certificates not supported!");
  }
  
  public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
    throws CertificateException
  {
    if (this.cache.contains(paramArrayOfX509Certificate[0])) {
      return;
    }
    checkSystemTrust(paramArrayOfX509Certificate, paramString);
    checkPinTrust(paramArrayOfX509Certificate);
    this.cache.add(paramArrayOfX509Certificate[0]);
  }
  
  public X509Certificate[] getAcceptedIssuers()
  {
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/network/PinningTrustManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
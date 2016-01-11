package com.ad4screen.sdk;

import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.os.ResultReceiver;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.inbox.Button;
import com.ad4screen.sdk.inbox.Message;

public abstract interface IA4SService
  extends IInterface
{
  public abstract void clickButtonMessage(Button paramButton, String paramString)
    throws RemoteException;
  
  public abstract void clientStarted()
    throws RemoteException;
  
  public abstract void closeCurrentInApp()
    throws RemoteException;
  
  public abstract void displayInApp(String paramString)
    throws RemoteException;
  
  public abstract void displayMessage(Message paramMessage)
    throws RemoteException;
  
  public abstract void doAction(String paramString)
    throws RemoteException;
  
  public abstract void getA4SId(e parame)
    throws RemoteException;
  
  public abstract void getActiveMember(e parame)
    throws RemoteException;
  
  public abstract void getIDFV(e parame)
    throws RemoteException;
  
  public abstract void getMembers(f paramf)
    throws RemoteException;
  
  public abstract void getMessagesDetails(String[] paramArrayOfString, g paramg)
    throws RemoteException;
  
  public abstract void getMessagesList(g paramg)
    throws RemoteException;
  
  public abstract void handleGeofencingMessage(Bundle paramBundle)
    throws RemoteException;
  
  public abstract void handlePushMessage(Bundle paramBundle)
    throws RemoteException;
  
  public abstract void interstitialClosed()
    throws RemoteException;
  
  public abstract void interstitialDisplayed()
    throws RemoteException;
  
  public abstract boolean isInAppDisplayLocked()
    throws RemoteException;
  
  public abstract boolean isPushEnabled()
    throws RemoteException;
  
  public abstract boolean isRestrictedConnection()
    throws RemoteException;
  
  public abstract void logIn(String paramString)
    throws RemoteException;
  
  public abstract void logOut()
    throws RemoteException;
  
  public abstract void onInAppClicked(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract void onInAppClosed(String paramString, boolean paramBoolean)
    throws RemoteException;
  
  public abstract void onInAppDisplayed(String paramString)
    throws RemoteException;
  
  public abstract void onInAppReady(String paramString, int paramInt)
    throws RemoteException;
  
  public abstract void openedPush(Bundle paramBundle)
    throws RemoteException;
  
  public abstract void putState(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract void removeMembers(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract void setClientCallback(ResultReceiver paramResultReceiver)
    throws RemoteException;
  
  public abstract void setDoNotTrack(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, d paramd)
    throws RemoteException;
  
  public abstract void setInAppDisplayLocked(boolean paramBoolean)
    throws RemoteException;
  
  public abstract void setInAppReadyCallback(boolean paramBoolean, int[] paramArrayOfInt)
    throws RemoteException;
  
  public abstract void setPushEnabled(boolean paramBoolean)
    throws RemoteException;
  
  public abstract void setRestrictedConnection(boolean paramBoolean)
    throws RemoteException;
  
  public abstract void setSource(String paramString)
    throws RemoteException;
  
  public abstract void setView(String paramString)
    throws RemoteException;
  
  public abstract void startActivity(String paramString1, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract void stopActivity(String paramString)
    throws RemoteException;
  
  public abstract void trackAddToCart(Cart paramCart, Bundle paramBundle)
    throws RemoteException;
  
  public abstract void trackEvent(long paramLong, String[] paramArrayOfString, Bundle paramBundle)
    throws RemoteException;
  
  public abstract void trackLead(Lead paramLead, Bundle paramBundle)
    throws RemoteException;
  
  public abstract void trackPurchase(Purchase paramPurchase, Bundle paramBundle)
    throws RemoteException;
  
  public abstract void trackReferrer(String paramString)
    throws RemoteException;
  
  public abstract void triggerBeacons(Bundle paramBundle)
    throws RemoteException;
  
  public abstract void updateGeolocation(Location paramLocation)
    throws RemoteException;
  
  public abstract void updateMessages(Message[] paramArrayOfMessage, g paramg)
    throws RemoteException;
  
  public abstract void updatePushRegistration(Bundle paramBundle)
    throws RemoteException;
  
  public abstract void updateUserPreferences(Bundle paramBundle, boolean paramBoolean)
    throws RemoteException;
  
  public abstract void uploadFacebookProfile(String paramString1, String paramString2, String[] paramArrayOfString)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IA4SService
  {
    public Stub()
    {
      attachInterface(this, "com.ad4screen.sdk.IA4SService");
    }
    
    public static IA4SService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.ad4screen.sdk.IA4SService");
      if ((localIInterface != null) && ((localIInterface instanceof IA4SService))) {
        return (IA4SService)localIInterface;
      }
      return new a(paramIBinder);
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      int i = 0;
      boolean bool2 = false;
      boolean bool4 = false;
      int j = 0;
      boolean bool5 = false;
      boolean bool6 = false;
      boolean bool7 = false;
      boolean bool3 = false;
      boolean bool1 = false;
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.ad4screen.sdk.IA4SService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        startActivity(paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readString());
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        stopActivity(paramParcel1.readString());
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        setView(paramParcel1.readString());
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        long l = paramParcel1.readLong();
        paramParcel2 = paramParcel1.createStringArray();
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          trackEvent(l, paramParcel2, paramParcel1);
          return true;
        }
      case 5: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = (Purchase)Purchase.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label625;
          }
        }
        for (paramParcel1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          trackPurchase(paramParcel2, paramParcel1);
          return true;
          paramParcel2 = null;
          break;
        }
      case 6: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = (Cart)Cart.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label689;
          }
        }
        for (paramParcel1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          trackAddToCart(paramParcel2, paramParcel1);
          return true;
          paramParcel2 = null;
          break;
        }
      case 7: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = (Lead)Lead.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label753;
          }
        }
        for (paramParcel1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          trackLead(paramParcel2, paramParcel1);
          return true;
          paramParcel2 = null;
          break;
        }
      case 8: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        getMessagesList(g.a.a(paramParcel1.readStrongBinder()));
        return true;
      case 9: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        getMessagesDetails(paramParcel1.createStringArray(), g.a.a(paramParcel1.readStrongBinder()));
        return true;
      case 10: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        updateMessages((Message[])paramParcel1.createTypedArray(Message.CREATOR), g.a.a(paramParcel1.readStrongBinder()));
        return true;
      case 11: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        logIn(paramParcel1.readString());
        return true;
      case 12: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        logOut();
        return true;
      case 13: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        removeMembers(paramParcel1.createStringArray());
        return true;
      case 14: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel1 = (Message)Message.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          displayMessage(paramParcel1);
          return true;
        }
      case 15: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel2 = (Button)Button.CREATOR.createFromParcel(paramParcel1);; paramParcel2 = null)
        {
          clickButtonMessage(paramParcel2, paramParcel1.readString());
          return true;
        }
      case 16: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        bool1 = isPushEnabled();
        paramParcel2.writeNoException();
        if (bool1) {}
        for (paramInt1 = 1;; paramInt1 = 0)
        {
          paramParcel2.writeInt(paramInt1);
          return true;
        }
      case 17: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0) {
          bool1 = true;
        }
        setPushEnabled(bool1);
        paramParcel2.writeNoException();
        return true;
      case 18: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        bool1 = isRestrictedConnection();
        paramParcel2.writeNoException();
        paramInt1 = i;
        if (bool1) {
          paramInt1 = 1;
        }
        paramParcel2.writeInt(paramInt1);
        return true;
      case 19: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        bool1 = bool2;
        if (paramParcel1.readInt() != 0) {
          bool1 = true;
        }
        setRestrictedConnection(bool1);
        paramParcel2.writeNoException();
        return true;
      case 20: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        bool1 = bool4;
        if (paramParcel1.readInt() != 0) {
          bool1 = true;
        }
        setInAppDisplayLocked(bool1);
        paramParcel2.writeNoException();
        return true;
      case 21: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        bool1 = isInAppDisplayLocked();
        paramParcel2.writeNoException();
        paramInt1 = j;
        if (bool1) {
          paramInt1 = 1;
        }
        paramParcel2.writeInt(paramInt1);
        return true;
      case 22: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        closeCurrentInApp();
        return true;
      case 23: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel2 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; paramParcel2 = null)
        {
          bool1 = bool5;
          if (paramParcel1.readInt() != 0) {
            bool1 = true;
          }
          updateUserPreferences(paramParcel2, bool1);
          return true;
        }
      case 24: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel1 = (Location)Location.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          updateGeolocation(paramParcel1);
          return true;
        }
      case 25: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        getA4SId(e.a.a(paramParcel1.readStrongBinder()));
        return true;
      case 26: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        getIDFV(e.a.a(paramParcel1.readStrongBinder()));
        return true;
      case 27: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        getMembers(f.a.a(paramParcel1.readStrongBinder()));
        return true;
      case 28: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        getActiveMember(e.a.a(paramParcel1.readStrongBinder()));
        return true;
      case 29: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        clientStarted();
        return true;
      case 30: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        interstitialDisplayed();
        return true;
      case 31: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        interstitialClosed();
        return true;
      case 32: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel1 = (ResultReceiver)ResultReceiver.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          setClientCallback(paramParcel1);
          return true;
        }
      case 33: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        doAction(paramParcel1.readString());
        return true;
      case 34: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          updatePushRegistration(paramParcel1);
          return true;
        }
      case 35: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          handlePushMessage(paramParcel1);
          return true;
        }
      case 36: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          handleGeofencingMessage(paramParcel1);
          return true;
        }
      case 37: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          triggerBeacons(paramParcel1);
          return true;
        }
      case 38: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        trackReferrer(paramParcel1.readString());
        return true;
      case 39: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        uploadFacebookProfile(paramParcel1.readString(), paramParcel1.readString(), paramParcel1.createStringArray());
        return true;
      case 40: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        onInAppDisplayed(paramParcel1.readString());
        return true;
      case 41: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        onInAppClicked(paramParcel1.readString(), paramParcel1.readString());
        return true;
      case 42: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        paramParcel2 = paramParcel1.readString();
        bool1 = bool6;
        if (paramParcel1.readInt() != 0) {
          bool1 = true;
        }
        onInAppClosed(paramParcel2, bool1);
        return true;
      case 43: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        onInAppReady(paramParcel1.readString(), paramParcel1.readInt());
        return true;
      case 44: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        bool1 = bool7;
        if (paramParcel1.readInt() != 0) {
          bool1 = true;
        }
        setInAppReadyCallback(bool1, paramParcel1.createIntArray());
        return true;
      case 45: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        displayInApp(paramParcel1.readString());
        return true;
      case 46: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          openedPush(paramParcel1);
          return true;
        }
      case 47: 
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        putState(paramParcel1.readString(), paramParcel1.readString());
        return true;
      case 48: 
        label625:
        label689:
        label753:
        paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
        setSource(paramParcel1.readString());
        return true;
      }
      paramParcel1.enforceInterface("com.ad4screen.sdk.IA4SService");
      if (paramParcel1.readInt() != 0)
      {
        bool1 = true;
        if (paramParcel1.readInt() == 0) {
          break label1881;
        }
      }
      label1881:
      for (bool2 = true;; bool2 = false)
      {
        if (paramParcel1.readInt() != 0) {
          bool3 = true;
        }
        setDoNotTrack(bool1, bool2, bool3, d.a.a(paramParcel1.readStrongBinder()));
        return true;
        bool1 = false;
        break;
      }
    }
    
    private static class a
      implements IA4SService
    {
      private IBinder a;
      
      a(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.a;
      }
      
      /* Error */
      public void clickButtonMessage(Button paramButton, String paramString)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: aload_3
        //   5: ldc 33
        //   7: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +39 -> 50
        //   14: aload_3
        //   15: iconst_1
        //   16: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_3
        //   21: iconst_0
        //   22: invokevirtual 47	com/ad4screen/sdk/inbox/Button:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_3
        //   26: aload_2
        //   27: invokevirtual 50	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   30: aload_0
        //   31: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   34: bipush 15
        //   36: aload_3
        //   37: aconst_null
        //   38: iconst_1
        //   39: invokeinterface 56 5 0
        //   44: pop
        //   45: aload_3
        //   46: invokevirtual 59	android/os/Parcel:recycle	()V
        //   49: return
        //   50: aload_3
        //   51: iconst_0
        //   52: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   55: goto -30 -> 25
        //   58: astore_1
        //   59: aload_3
        //   60: invokevirtual 59	android/os/Parcel:recycle	()V
        //   63: aload_1
        //   64: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	65	0	this	a
        //   0	65	1	paramButton	Button
        //   0	65	2	paramString	String
        //   3	57	3	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   4	10	58	finally
        //   14	25	58	finally
        //   25	45	58	finally
        //   50	55	58	finally
      }
      
      public void clientStarted()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          this.a.transact(29, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void closeCurrentInApp()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          this.a.transact(22, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void displayInApp(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString);
          this.a.transact(45, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      /* Error */
      public void displayMessage(Message paramMessage)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 33
        //   7: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 68	com/ad4screen/sdk/inbox/Message:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   29: bipush 14
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 56 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 59	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_1
        //   54: aload_2
        //   55: invokevirtual 59	android/os/Parcel:recycle	()V
        //   58: aload_1
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	a
        //   0	60	1	paramMessage	Message
        //   3	52	2	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      public void doAction(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString);
          this.a.transact(33, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void getA4SId(e parame)
        throws RemoteException
      {
        IBinder localIBinder = null;
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          if (parame != null) {
            localIBinder = parame.asBinder();
          }
          localParcel.writeStrongBinder(localIBinder);
          this.a.transact(25, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void getActiveMember(e parame)
        throws RemoteException
      {
        IBinder localIBinder = null;
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          if (parame != null) {
            localIBinder = parame.asBinder();
          }
          localParcel.writeStrongBinder(localIBinder);
          this.a.transact(28, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void getIDFV(e parame)
        throws RemoteException
      {
        IBinder localIBinder = null;
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          if (parame != null) {
            localIBinder = parame.asBinder();
          }
          localParcel.writeStrongBinder(localIBinder);
          this.a.transact(26, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void getMembers(f paramf)
        throws RemoteException
      {
        IBinder localIBinder = null;
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          if (paramf != null) {
            localIBinder = paramf.asBinder();
          }
          localParcel.writeStrongBinder(localIBinder);
          this.a.transact(27, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void getMessagesDetails(String[] paramArrayOfString, g paramg)
        throws RemoteException
      {
        Object localObject = null;
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeStringArray(paramArrayOfString);
          paramArrayOfString = (String[])localObject;
          if (paramg != null) {
            paramArrayOfString = paramg.asBinder();
          }
          localParcel.writeStrongBinder(paramArrayOfString);
          this.a.transact(9, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void getMessagesList(g paramg)
        throws RemoteException
      {
        IBinder localIBinder = null;
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          if (paramg != null) {
            localIBinder = paramg.asBinder();
          }
          localParcel.writeStrongBinder(localIBinder);
          this.a.transact(8, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      /* Error */
      public void handleGeofencingMessage(Bundle paramBundle)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 33
        //   7: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 101	android/os/Bundle:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   29: bipush 36
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 56 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 59	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_1
        //   54: aload_2
        //   55: invokevirtual 59	android/os/Parcel:recycle	()V
        //   58: aload_1
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	a
        //   0	60	1	paramBundle	Bundle
        //   3	52	2	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      /* Error */
      public void handlePushMessage(Bundle paramBundle)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 33
        //   7: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 101	android/os/Bundle:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   29: bipush 35
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 56 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 59	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_1
        //   54: aload_2
        //   55: invokevirtual 59	android/os/Parcel:recycle	()V
        //   58: aload_1
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	a
        //   0	60	1	paramBundle	Bundle
        //   3	52	2	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      public void interstitialClosed()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          this.a.transact(31, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void interstitialDisplayed()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          this.a.transact(30, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public boolean isInAppDisplayLocked()
        throws RemoteException
      {
        boolean bool = false;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          this.a.transact(21, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean isPushEnabled()
        throws RemoteException
      {
        boolean bool = false;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          this.a.transact(16, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean isRestrictedConnection()
        throws RemoteException
      {
        boolean bool = false;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          this.a.transact(18, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void logIn(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString);
          this.a.transact(11, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void logOut()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          this.a.transact(12, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void onInAppClicked(String paramString1, String paramString2)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString1);
          localParcel.writeString(paramString2);
          this.a.transact(41, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      /* Error */
      public void onInAppClosed(String paramString, boolean paramBoolean)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore_3
        //   2: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   5: astore 4
        //   7: aload 4
        //   9: ldc 33
        //   11: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload 4
        //   16: aload_1
        //   17: invokevirtual 50	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   20: iload_2
        //   21: ifeq +31 -> 52
        //   24: aload 4
        //   26: iload_3
        //   27: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   30: aload_0
        //   31: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   34: bipush 42
        //   36: aload 4
        //   38: aconst_null
        //   39: iconst_1
        //   40: invokeinterface 56 5 0
        //   45: pop
        //   46: aload 4
        //   48: invokevirtual 59	android/os/Parcel:recycle	()V
        //   51: return
        //   52: iconst_0
        //   53: istore_3
        //   54: goto -30 -> 24
        //   57: astore_1
        //   58: aload 4
        //   60: invokevirtual 59	android/os/Parcel:recycle	()V
        //   63: aload_1
        //   64: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	65	0	this	a
        //   0	65	1	paramString	String
        //   0	65	2	paramBoolean	boolean
        //   1	53	3	i	int
        //   5	54	4	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   7	20	57	finally
        //   24	46	57	finally
      }
      
      public void onInAppDisplayed(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString);
          this.a.transact(40, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void onInAppReady(String paramString, int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString);
          localParcel.writeInt(paramInt);
          this.a.transact(43, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      /* Error */
      public void openedPush(Bundle paramBundle)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 33
        //   7: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 101	android/os/Bundle:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   29: bipush 46
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 56 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 59	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_1
        //   54: aload_2
        //   55: invokevirtual 59	android/os/Parcel:recycle	()V
        //   58: aload_1
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	a
        //   0	60	1	paramBundle	Bundle
        //   3	52	2	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      public void putState(String paramString1, String paramString2)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString1);
          localParcel.writeString(paramString2);
          this.a.transact(47, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void removeMembers(String[] paramArrayOfString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeStringArray(paramArrayOfString);
          this.a.transact(13, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      /* Error */
      public void setClientCallback(ResultReceiver paramResultReceiver)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 33
        //   7: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 132	android/os/ResultReceiver:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   29: bipush 32
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 56 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 59	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_1
        //   54: aload_2
        //   55: invokevirtual 59	android/os/Parcel:recycle	()V
        //   58: aload_1
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	a
        //   0	60	1	paramResultReceiver	ResultReceiver
        //   3	52	2	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      /* Error */
      public void setDoNotTrack(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, d paramd)
        throws RemoteException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore 7
        //   3: iconst_1
        //   4: istore 6
        //   6: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   9: astore 8
        //   11: aload 8
        //   13: ldc 33
        //   15: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   18: iload_1
        //   19: ifeq +85 -> 104
        //   22: iconst_1
        //   23: istore 5
        //   25: aload 8
        //   27: iload 5
        //   29: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   32: iload_2
        //   33: ifeq +77 -> 110
        //   36: iconst_1
        //   37: istore 5
        //   39: aload 8
        //   41: iload 5
        //   43: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   46: iload_3
        //   47: ifeq +69 -> 116
        //   50: iload 6
        //   52: istore 5
        //   54: aload 8
        //   56: iload 5
        //   58: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   61: aload 4
        //   63: ifnull +12 -> 75
        //   66: aload 4
        //   68: invokeinterface 137 1 0
        //   73: astore 7
        //   75: aload 8
        //   77: aload 7
        //   79: invokevirtual 78	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   82: aload_0
        //   83: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   86: bipush 49
        //   88: aload 8
        //   90: aconst_null
        //   91: iconst_1
        //   92: invokeinterface 56 5 0
        //   97: pop
        //   98: aload 8
        //   100: invokevirtual 59	android/os/Parcel:recycle	()V
        //   103: return
        //   104: iconst_0
        //   105: istore 5
        //   107: goto -82 -> 25
        //   110: iconst_0
        //   111: istore 5
        //   113: goto -74 -> 39
        //   116: iconst_0
        //   117: istore 5
        //   119: goto -65 -> 54
        //   122: astore 4
        //   124: aload 8
        //   126: invokevirtual 59	android/os/Parcel:recycle	()V
        //   129: aload 4
        //   131: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	132	0	this	a
        //   0	132	1	paramBoolean1	boolean
        //   0	132	2	paramBoolean2	boolean
        //   0	132	3	paramBoolean3	boolean
        //   0	132	4	paramd	d
        //   23	95	5	i	int
        //   4	47	6	j	int
        //   1	77	7	localIBinder	IBinder
        //   9	116	8	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   11	18	122	finally
        //   25	32	122	finally
        //   39	46	122	finally
        //   54	61	122	finally
        //   66	75	122	finally
        //   75	98	122	finally
      }
      
      public void setInAppDisplayLocked(boolean paramBoolean)
        throws RemoteException
      {
        int i = 0;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          if (paramBoolean) {
            i = 1;
          }
          localParcel1.writeInt(i);
          this.a.transact(20, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      /* Error */
      public void setInAppReadyCallback(boolean paramBoolean, int[] paramArrayOfInt)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore_3
        //   2: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   5: astore 4
        //   7: aload 4
        //   9: ldc 33
        //   11: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: iload_1
        //   15: ifeq +37 -> 52
        //   18: aload 4
        //   20: iload_3
        //   21: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   24: aload 4
        //   26: aload_2
        //   27: invokevirtual 145	android/os/Parcel:writeIntArray	([I)V
        //   30: aload_0
        //   31: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   34: bipush 44
        //   36: aload 4
        //   38: aconst_null
        //   39: iconst_1
        //   40: invokeinterface 56 5 0
        //   45: pop
        //   46: aload 4
        //   48: invokevirtual 59	android/os/Parcel:recycle	()V
        //   51: return
        //   52: iconst_0
        //   53: istore_3
        //   54: goto -36 -> 18
        //   57: astore_2
        //   58: aload 4
        //   60: invokevirtual 59	android/os/Parcel:recycle	()V
        //   63: aload_2
        //   64: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	65	0	this	a
        //   0	65	1	paramBoolean	boolean
        //   0	65	2	paramArrayOfInt	int[]
        //   1	53	3	i	int
        //   5	54	4	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   7	14	57	finally
        //   18	46	57	finally
      }
      
      public void setPushEnabled(boolean paramBoolean)
        throws RemoteException
      {
        int i = 0;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          if (paramBoolean) {
            i = 1;
          }
          localParcel1.writeInt(i);
          this.a.transact(17, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setRestrictedConnection(boolean paramBoolean)
        throws RemoteException
      {
        int i = 0;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          if (paramBoolean) {
            i = 1;
          }
          localParcel1.writeInt(i);
          this.a.transact(19, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setSource(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString);
          this.a.transact(48, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void setView(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString);
          this.a.transact(3, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void startActivity(String paramString1, String paramString2, String paramString3)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString1);
          localParcel.writeString(paramString2);
          localParcel.writeString(paramString3);
          this.a.transact(1, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void stopActivity(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString);
          this.a.transact(2, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void trackAddToCart(Cart paramCart, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
            if (paramCart != null)
            {
              localParcel.writeInt(1);
              paramCart.writeToParcel(localParcel, 0);
              if (paramBundle != null)
              {
                localParcel.writeInt(1);
                paramBundle.writeToParcel(localParcel, 0);
                this.a.transact(6, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
      
      /* Error */
      public void trackEvent(long paramLong, String[] paramArrayOfString, Bundle paramBundle)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore 5
        //   5: aload 5
        //   7: ldc 33
        //   9: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   12: aload 5
        //   14: lload_1
        //   15: invokevirtual 163	android/os/Parcel:writeLong	(J)V
        //   18: aload 5
        //   20: aload_3
        //   21: invokevirtual 91	android/os/Parcel:writeStringArray	([Ljava/lang/String;)V
        //   24: aload 4
        //   26: ifnull +38 -> 64
        //   29: aload 5
        //   31: iconst_1
        //   32: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   35: aload 4
        //   37: aload 5
        //   39: iconst_0
        //   40: invokevirtual 101	android/os/Bundle:writeToParcel	(Landroid/os/Parcel;I)V
        //   43: aload_0
        //   44: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   47: iconst_4
        //   48: aload 5
        //   50: aconst_null
        //   51: iconst_1
        //   52: invokeinterface 56 5 0
        //   57: pop
        //   58: aload 5
        //   60: invokevirtual 59	android/os/Parcel:recycle	()V
        //   63: return
        //   64: aload 5
        //   66: iconst_0
        //   67: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   70: goto -27 -> 43
        //   73: astore_3
        //   74: aload 5
        //   76: invokevirtual 59	android/os/Parcel:recycle	()V
        //   79: aload_3
        //   80: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	81	0	this	a
        //   0	81	1	paramLong	long
        //   0	81	3	paramArrayOfString	String[]
        //   0	81	4	paramBundle	Bundle
        //   3	72	5	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   5	24	73	finally
        //   29	43	73	finally
        //   43	58	73	finally
        //   64	70	73	finally
      }
      
      public void trackLead(Lead paramLead, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
            if (paramLead != null)
            {
              localParcel.writeInt(1);
              paramLead.writeToParcel(localParcel, 0);
              if (paramBundle != null)
              {
                localParcel.writeInt(1);
                paramBundle.writeToParcel(localParcel, 0);
                this.a.transact(7, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
      
      public void trackPurchase(Purchase paramPurchase, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
            if (paramPurchase != null)
            {
              localParcel.writeInt(1);
              paramPurchase.writeToParcel(localParcel, 0);
              if (paramBundle != null)
              {
                localParcel.writeInt(1);
                paramBundle.writeToParcel(localParcel, 0);
                this.a.transact(5, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
      
      public void trackReferrer(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString);
          this.a.transact(38, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      /* Error */
      public void triggerBeacons(Bundle paramBundle)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 33
        //   7: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 101	android/os/Bundle:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   29: bipush 37
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 56 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 59	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_1
        //   54: aload_2
        //   55: invokevirtual 59	android/os/Parcel:recycle	()V
        //   58: aload_1
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	a
        //   0	60	1	paramBundle	Bundle
        //   3	52	2	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      /* Error */
      public void updateGeolocation(Location paramLocation)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 33
        //   7: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 180	android/location/Location:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   29: bipush 24
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 56 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 59	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_1
        //   54: aload_2
        //   55: invokevirtual 59	android/os/Parcel:recycle	()V
        //   58: aload_1
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	a
        //   0	60	1	paramLocation	Location
        //   3	52	2	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      public void updateMessages(Message[] paramArrayOfMessage, g paramg)
        throws RemoteException
      {
        Object localObject = null;
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeTypedArray(paramArrayOfMessage, 0);
          paramArrayOfMessage = (Message[])localObject;
          if (paramg != null) {
            paramArrayOfMessage = paramg.asBinder();
          }
          localParcel.writeStrongBinder(paramArrayOfMessage);
          this.a.transact(10, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      /* Error */
      public void updatePushRegistration(Bundle paramBundle)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 31	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 33
        //   7: invokevirtual 37	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 101	android/os/Bundle:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 18	com/ad4screen/sdk/IA4SService$Stub$a:a	Landroid/os/IBinder;
        //   29: bipush 34
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 56 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 59	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 41	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_1
        //   54: aload_2
        //   55: invokevirtual 59	android/os/Parcel:recycle	()V
        //   58: aload_1
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	a
        //   0	60	1	paramBundle	Bundle
        //   3	52	2	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      public void updateUserPreferences(Bundle paramBundle, boolean paramBoolean)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
            if (paramBundle != null)
            {
              localParcel.writeInt(1);
              paramBundle.writeToParcel(localParcel, 0);
              break label84;
              localParcel.writeInt(i);
              this.a.transact(23, localParcel, null, 1);
            }
            else
            {
              localParcel.writeInt(0);
            }
          }
          finally
          {
            localParcel.recycle();
          }
          label84:
          do
          {
            i = 0;
            break;
          } while (!paramBoolean);
        }
      }
      
      public void uploadFacebookProfile(String paramString1, String paramString2, String[] paramArrayOfString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
          localParcel.writeString(paramString1);
          localParcel.writeString(paramString2);
          localParcel.writeStringArray(paramArrayOfString);
          this.a.transact(39, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/IA4SService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
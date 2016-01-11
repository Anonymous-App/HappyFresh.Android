package com.facebook.share.model;

import android.os.Parcel;
import com.facebook.share.ShareBuilder;

public abstract interface ShareModelBuilder<P extends ShareModel, E extends ShareModelBuilder>
  extends ShareBuilder<P, E>
{
  public abstract E readFrom(Parcel paramParcel);
  
  public abstract E readFrom(P paramP);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/model/ShareModelBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
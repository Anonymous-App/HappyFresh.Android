package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class JobState
{
  @SerializedName("id")
  public String remoteId;
  @SerializedName("start_time")
  public Date startTime;
  public String state;
  
  public boolean isAccepted()
  {
    return this.state.equalsIgnoreCase("accepted");
  }
  
  public boolean isFoundAddress()
  {
    return this.state.equalsIgnoreCase("found_address");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/JobState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
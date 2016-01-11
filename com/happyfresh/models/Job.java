package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Job
{
  public Assignment assignment;
  @SerializedName("end_time")
  public Date endTime;
  public String note;
  public String receiver;
  @SerializedName("id")
  public Long remoteId;
  @SerializedName("shipment_id")
  public Long shipmentId;
  @SerializedName("start_time")
  public Date startTime;
  public String state;
  @SerializedName("states")
  public List<JobState> states = new ArrayList();
  public User user;
  
  public JobState getState(String paramString)
  {
    Iterator localIterator = this.states.iterator();
    while (localIterator.hasNext())
    {
      JobState localJobState = (JobState)localIterator.next();
      if (paramString.equalsIgnoreCase(localJobState.state)) {
        return localJobState;
      }
    }
    return null;
  }
  
  public boolean isAccepted()
  {
    return this.state.equalsIgnoreCase("accepted");
  }
  
  public boolean isFinished()
  {
    return this.state.equalsIgnoreCase("finished");
  }
  
  public boolean isFoundAddress()
  {
    return this.state.equalsIgnoreCase("found_address");
  }
  
  public boolean isStarted()
  {
    return this.state.equalsIgnoreCase("started");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Job.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
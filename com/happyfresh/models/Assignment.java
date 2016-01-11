package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;

public class Assignment
{
  @SerializedName("assignment_type")
  public String assignmentType;
  @SerializedName("id")
  public Long remoteId;
  public User user;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Assignment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
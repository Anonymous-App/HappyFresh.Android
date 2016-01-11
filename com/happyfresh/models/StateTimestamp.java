package com.happyfresh.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import java.util.Date;

@Table(name="state_timestamp")
public class StateTimestamp
  extends Model
{
  @Column(name="state_id", onUniqueConflict=Column.ConflictAction.IGNORE, unique=true)
  public Long stateId;
  @Column(name="timestamps")
  public Date timestamps;
  
  public static void deleteAll()
  {
    new Delete().from(StateTimestamp.class).execute();
  }
  
  public static StateTimestamp findByStateId(Long paramLong)
  {
    return (StateTimestamp)new Select().from(StateTimestamp.class).where("state_id = ?", new Object[] { paramLong }).executeSingle();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/StateTimestamp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
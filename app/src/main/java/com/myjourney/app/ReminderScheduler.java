package com.myjourney.app;
import android.app.*; import android.content.*; import android.os.Build; import java.util.*;
public class ReminderScheduler {
 static void schedule(Context c, String time) {
  try {
   String[] a=(time==null?"20:00":time).split(":"); Calendar cal=Calendar.getInstance();
   cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(a[0])); cal.set(Calendar.MINUTE,Integer.parseInt(a[1])); cal.set(Calendar.SECOND,0); cal.set(Calendar.MILLISECOND,0);
   if(cal.getTimeInMillis()<=System.currentTimeMillis()) cal.add(Calendar.DAY_OF_YEAR,1);
   Intent i=new Intent(c,ReminderReceiver.class); PendingIntent pi=PendingIntent.getBroadcast(c,1001,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
   AlarmManager am=(AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
   if(am!=null) am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pi);
  } catch(Exception ignored) {}
 }
}

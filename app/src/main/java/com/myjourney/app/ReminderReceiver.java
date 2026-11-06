package com.myjourney.app;
import android.app.*; import android.content.*; import android.os.Build; import android.content.SharedPreferences; import org.json.*;
public class ReminderReceiver extends BroadcastReceiver {
 public void onReceive(Context c, Intent i) {
  SharedPreferences p=c.getSharedPreferences(MainActivity.PREFS,Context.MODE_PRIVATE);
  try {
   JSONObject root=new JSONObject(p.getString("data","{}")); JSONArray goals=root.optJSONArray("goals"); StringBuilder body=new StringBuilder(); int n=0;
   if(goals!=null) for(int x=0;x<goals.length();x++){JSONObject g=goals.optJSONObject(x); if(g!=null && "daily".equals(g.optString("type")) && !g.optBoolean("done")){if(n++>0)body.append(" • ");body.append(g.optString("name"));}}
   post(c,"أهدافك لبكرة",n==0?"افتح MY JOURNEY وجهّز أهدافك الجديدة":body.toString());
  } catch(Exception e){post(c,"MY JOURNEY","افتح التطبيق لمراجعة أهدافك");}
  ReminderScheduler.schedule(c,"20:00");
 }
 static void post(Context c,String title,String body){
  String ch="my_journey_goals"; NotificationManager nm=(NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
  if(Build.VERSION.SDK_INT>=26) nm.createNotificationChannel(new NotificationChannel(ch,"تذكيرات MY JOURNEY",NotificationManager.IMPORTANCE_DEFAULT));
  Intent open=new Intent(c,MainActivity.class); PendingIntent pi=PendingIntent.getActivity(c,2,open,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
  Notification.Builder b=Build.VERSION.SDK_INT>=26?new Notification.Builder(c,ch):new Notification.Builder(c);
  nm.notify(2001,b.setSmallIcon(android.R.drawable.ic_menu_my_calendar).setContentTitle(title).setContentText(body).setStyle(new Notification.BigTextStyle().bigText(body)).setContentIntent(pi).setAutoCancel(true).build());
 }
}

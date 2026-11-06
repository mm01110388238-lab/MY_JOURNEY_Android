package com.myjourney.app;
import android.app.*; import android.appwidget.*; import android.content.*; import android.widget.*; import org.json.*;
public class GoalsWidgetProvider extends AppWidgetProvider {
 public void onUpdate(Context c,AppWidgetManager m,int[] ids){ for(int id:ids) update(c,m,id); }
 static void updateAll(Context c){ AppWidgetManager m=AppWidgetManager.getInstance(c); ComponentName n=new ComponentName(c,GoalsWidgetProvider.class); for(int id:m.getAppWidgetIds(n)) update(c,m,id); }
 static void update(Context c,AppWidgetManager m,int id){
  RemoteViews v=new RemoteViews(c.getPackageName(),R.layout.goals_widget); int total=0,done=0; StringBuilder s=new StringBuilder();
  try { JSONObject root=new JSONObject(c.getSharedPreferences(MainActivity.PREFS,Context.MODE_PRIVATE).getString("data","{}")); JSONArray a=root.optJSONArray("goals"); if(a!=null) for(int x=0;x<a.length();x++){JSONObject g=a.optJSONObject(x); if(g!=null && "daily".equals(g.optString("type"))){total++; if(g.optBoolean("done"))done++; if(s.length()<160){if(s.length()>0)s.append("\n");s.append(g.optBoolean("done")?"✓ ":"• ").append(g.optString("name"));}}} }
  catch(Exception ignored){} v.setTextViewText(R.id.widget_count,done+"/"+total+" مكتمل"); v.setTextViewText(R.id.widget_goals,s.length()==0?"افتح التطبيق وأضف أهدافك اليومية":s.toString());
  Intent i=new Intent(c,MainActivity.class); PendingIntent pi=PendingIntent.getActivity(c,3,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE); v.setOnClickPendingIntent(R.id.widget_root,pi); m.updateAppWidget(id,v);
 }
}

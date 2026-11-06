package com.myjourney.app;
import android.content.*; import android.content.SharedPreferences;
public class BootReceiver extends BroadcastReceiver { public void onReceive(Context c,Intent i){ SharedPreferences p=c.getSharedPreferences(MainActivity.PREFS,Context.MODE_PRIVATE); ReminderScheduler.schedule(c,"20:00"); GoalsWidgetProvider.updateAll(c); } }

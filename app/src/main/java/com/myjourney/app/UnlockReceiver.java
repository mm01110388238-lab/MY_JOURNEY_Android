package com.myjourney.app;
import android.content.*; import android.content.SharedPreferences; import org.json.*;
public class UnlockReceiver extends BroadcastReceiver {
 public void onReceive(Context c,Intent i){ SharedPreferences p=c.getSharedPreferences(MainActivity.PREFS,Context.MODE_PRIVATE); if(!p.getBoolean("show_on_unlock",false)) return;
  try { JSONObject root=new JSONObject(p.getString("data","{}")); JSONArray goals=root.optJSONArray("goals"); StringBuilder body=new StringBuilder(); int n=0;
   if(goals!=null) for(int x=0;x<goals.length();x++){JSONObject g=goals.optJSONObject(x); if(g!=null && "daily".equals(g.optString("type")) && !g.optBoolean("done")){if(n++>0)body.append(" • ");body.append(g.optString("name"));}}
   ReminderReceiver.post(c,"أهدافك النهارده 🎯",n==0?"مفيش أهداف يومية متبقية حالياً":body.toString());
  }catch(Exception ignored){}
 }
}

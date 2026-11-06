package com.myjourney.app;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity {
  private WebView web;
  static final String PREFS = "my_journey_native";
  @Override public void onCreate(Bundle b) {
    super.onCreate(b);
    getWindow().setStatusBarColor(0xFF071321);
    web = new WebView(this);
    setContentView(web);
    web.getSettings().setJavaScriptEnabled(true);
    web.getSettings().setDomStorageEnabled(true);
    web.setWebViewClient(new WebViewClient());
    web.setWebChromeClient(new WebChromeClient());
    web.addJavascriptInterface(new NativeBridge(this), "MyJourneyNative");
    web.loadUrl("file:///android_asset/index.html");
    if (Build.VERSION.SDK_INT >= 33 && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
      requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 41);
  }
  @Override public void onBackPressed() { if (web != null && web.canGoBack()) web.goBack(); else super.onBackPressed(); }

  public static class NativeBridge {
    private final Context c;
    NativeBridge(Context c) { this.c = c.getApplicationContext(); }
    @JavascriptInterface public void syncData(String json, String reminder, boolean showOnUnlock) {
      SharedPreferences p = c.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
      p.edit().putString("data", json).putBoolean("show_on_unlock", showOnUnlock).apply();
      GoalsWidgetProvider.updateAll(c);
      ReminderScheduler.schedule(c, reminder);
    }
    @JavascriptInterface public boolean hasNotificationPermission() {
      return Build.VERSION.SDK_INT < 33 || c.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
    }
  }
}

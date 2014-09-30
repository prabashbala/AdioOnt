package uk.org.audioont;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class PowerChecker extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("MyApp","I am here");
		PowerBroadcastReceiver receiver = new PowerBroadcastReceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
		registerReceiver(receiver, filter);
		return START_STICKY;
	}

}

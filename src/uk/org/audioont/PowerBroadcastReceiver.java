package uk.org.audioont;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class PowerBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ctx, Intent intent) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx).setSmallIcon(R.drawable.ic_launcher)
				.setContentText("Power cable has been plugged in").setContentTitle("Attention");
		NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		System.out.println("HHI*************");
		manager.notify(0, builder.build());

	}
	
	
	public void SetAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 10, pi); // Millisec * Second * Minute
    }
	
	 public void CancelAlarm(Context context)
     {
         Intent intent = new Intent(context, Alarm.class);
         PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
         AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
         alarmManager.cancel(sender);
     }

}

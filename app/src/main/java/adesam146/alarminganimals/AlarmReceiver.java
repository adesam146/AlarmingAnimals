package adesam146.alarminganimals;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String selectedAnimal = intent.getStringExtra(SetUpAlarmActivity
                .SELECTEDITEM);

        Log.i("onRecieve", "Testing");

        Intent stopAlarmIntent = new Intent(context, StopAlarmActivity
                .class);
        //add this flag is needed to start an activity from outside an
        //Activity's context according to an error you get if you don't add it.
        stopAlarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        stopAlarmIntent.putExtra(SetUpAlarmActivity.SELECTEDITEM,
                selectedAnimal);
        context.startActivity(stopAlarmIntent);


//        Intent serviceIntent = new Intent(context, AlarmSoundService.class);
//        //start the ringtone
//        context.startService(serviceIntent);
    }
}

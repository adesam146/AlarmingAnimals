package adesam146.alarminganimals;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class AlarmSoundService extends Service {

    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("onStartCommand", "Recieved start id " + startId + ", Intent: "
                + intent);

        mediaPlayer = MediaPlayer.create(this, R.raw.rooster);
        mediaPlayer.start();
        mediaPlayer.isLooping();

        return START_NOT_STICKY;
    }
}

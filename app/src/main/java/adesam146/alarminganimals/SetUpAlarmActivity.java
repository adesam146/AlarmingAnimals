package adesam146.alarminganimals;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

public class SetUpAlarmActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener {

    private static final int ANDROID_MARSHMALLOW = Build.VERSION_CODES.M;

    private TimePicker timePicker;
    private Spinner animalsSpinner;
    private ToggleButton alarmState;
    private AlarmManager alarmManager;
    private Intent intent;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //spinner is for the dropdown menu for picking the alarm animal
        animalsSpinner = (Spinner) findViewById(R.id.animals_spinner);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        intent = new Intent(this, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        // Creating an ArrayAdapter using the string array and a default
        // spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.alarm_animals, android.R.layout
                        .simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        //Applying the adapter to the spinner
        animalsSpinner.setAdapter(adapter);

        alarmState = (ToggleButton) findViewById(R.id.toggleButton);
        alarmState.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean
            isChecked) {

        if(isChecked) {
            Calendar calendar = Calendar.getInstance();
            //Todo: not sure f to us setTimeInMillis
            calendar.setTimeInMillis(System.currentTimeMillis());

            //This was done because the getHour method only works for AP1 >=23
            // (Marshmallow)
            if(Build.VERSION.SDK_INT < ANDROID_MARSHMALLOW){
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
            } else{
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
            }

            //pendingIntent to delay intent
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                    PendingIntent
                    .FLAG_UPDATE_CURRENT);

            //Set the alarm manager
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar
                    .getTimeInMillis(), pendingIntent);

        } else {
            alarmManager.cancel(pendingIntent);
            Log.e("onCheckChanged", "Alarm Canceled");
        }
    }
}

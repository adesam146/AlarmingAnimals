package adesam146.alarminganimals;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;

import adesam146.alarminganimals.utils.Constants;

public class SetUpAlarmActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener {

    public static final String SELECTEDITEM = "Selected Item";

    private TimePicker timePicker;
    private Spinner animalsSpinner;
    private Switch alarmState;
    private AlarmManager alarmManager;
    private Intent alarmIntent;
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
        alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        // Creating an ArrayAdapter using the string array and a default
        // spinner layout
        String[] animalsArray = getResources().getStringArray(R.array
                .alarm_animals);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout
                .simple_spinner_item, android.R.id.text1, animalsArray);
        adapter.sort(String.CASE_INSENSITIVE_ORDER);
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        //Applying the adapter to the spinner
        animalsSpinner.setAdapter(adapter);
        animalsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Telling the reciever which animal was selected
                alarmIntent.putExtra(SELECTEDITEM, (String) adapterView
                        .getItemAtPosition(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        alarmState = (Switch) findViewById(R.id.alarm_state);
        alarmState.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean
            isChecked) {

        //Todo: Add recurring feature
        if(isChecked) {
            Calendar calendar = Calendar.getInstance();
            //calendar.setTimeInMillis(System.currentTimeMillis());

            //This was done because the getHour method only works for AP1 >=23
            // (Marshmallow)
            if(Build.VERSION.SDK_INT < Constants.ANDROID_MARSHMALLOW){
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
            } else{
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
            }

            //pendingIntent to delay alarmIntent
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent,
                    PendingIntent
                    .FLAG_UPDATE_CURRENT);

            //Set the alarm manager
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar
                    .getTimeInMillis(), pendingIntent);
            Log.i("onCheckChanged", "Alarm Set Up");

        } else {
            alarmManager.cancel(pendingIntent);
            Log.i("onCheckChanged", "Alarm Canceled");
        }
    }
}

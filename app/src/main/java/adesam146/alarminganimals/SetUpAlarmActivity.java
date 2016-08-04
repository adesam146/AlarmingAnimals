package adesam146.alarminganimals;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SetUpAlarmActivity extends AppCompatActivity {

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

        Spinner animalsSpinner = (Spinner) findViewById(R.id.animals_spinner);
        // Creating an ArrayAdapter using the string array and a default
        // spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.alarm_animals, android.R.layout
                        .simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        //Applying the adapter to the spinner
        animalsSpinner.setAdapter(adapter);

    }

}

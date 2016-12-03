package com.boxfish.lsw.calendarview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.boxfish.lsw.c.CalendarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setStartByNowMonth(3);
        calendarView.setOnCanlendarDayClickListener(scheduleDate -> Toast.makeText(getApplicationContext(), scheduleDate.dateTime.toString(), Toast.LENGTH_SHORT).show()
        );
    }
}

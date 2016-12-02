package com.example.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

public class MonthView extends AppCompatActivity {
    CalendarView calender;
    String date;
    Button detailBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calender = (CalendarView) findViewById(R.id.calendarView);
        detailBtn = (Button) findViewById(R.id.detail);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                date = year + "년 " + (month + 1) + "월" + dayOfMonth + "일";
            }

        });

        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("date", date);
                startActivityForResult(intent, Integer.parseInt("0"));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addScedule:
                startActivity(new Intent(this, AddActivity.class));
                return true;
            case R.id.monthView:
                startActivity(new Intent(this, MonthView.class));
                return true;
            case R.id.weekView:
                startActivity(new Intent(this, WeekView.class));
                return true;
            case R.id.dayView:
                startActivity(new Intent(this, DayView.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

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
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CalendarView calender;
    String date;
    Button detailBtn;
    Button editBtn;
    TextView selectDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calender = (CalendarView) findViewById(R.id.calendarView);

        selectDate = (TextView) findViewById(R.id.selectDate);
        detailBtn = (Button) findViewById(R.id.detailDate);
        editBtn = (Button) findViewById(R.id.editDate);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                long dateLong = calender.getDate();
                //Log.i("Detail Test :" , dateLong);
                //Toast.makeText(MainActivity.this, "sadf", 0).show();
                date = year + "년 " + month + "월" + dayOfMonth + "일";
                selectDate.setText(year + "년 " + month + " 월" + dayOfMonth + " 일");
            }

        });

        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("onClick Test :" , date);
                Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);
                detailIntent.putExtra("detaildate", date);
                startActivityForResult(detailIntent, Integer.parseInt("0"));
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("onClick Test :" , date);
                if(date == null) {
                   // startActivity(new Intent(this, EditActivity.class));
                }
                else{
                    Intent editIntent = new Intent(getApplicationContext(), AddActivity.class);
                    editIntent.putExtra("editdate", date);
                    startActivityForResult(editIntent, Integer.parseInt("0"));
                }
            }
        });
    }

    @Override
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

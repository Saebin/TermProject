package com.example.termproject;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class AddActivity extends AppCompatActivity {
    private MyDBHelper helper;
    DatePicker datePicker;

    private String startime, endtime, date, atitle, acontent;
    private int Year;
    private int Month;
    private int day;

    private int hour, minute;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        helper = new MyDBHelper(this);

        datePicker = (DatePicker) findViewById(R.id.datepicker);

        datePicker.init(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                        Year = year;
                        Month = monthOfYear+1;
                        day = dayOfMonth;

                        TextView dateText = (TextView) findViewById(R.id.date);

                        date = Year + "-" + Month + "-" + day;
                        dateText.setText(date);

                    }
                });

        findViewById(R.id.startbtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddActivity.this, starttimeSetListener, hour, minute, false).show();
            }
        });

        findViewById(R.id.endbtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddActivity.this, endtimeSetListener, hour, minute, false).show();
            }
        });

        Button addBtn = (Button) findViewById(R.id.save);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText title = (EditText) findViewById(R.id.title);
                EditText content = (EditText) findViewById(R.id.content);
                TextView startT = (TextView) findViewById(R.id.start);
                TextView endT = (TextView) findViewById(R.id.end);


                startime = startT.getText().toString();
                endtime = endT.getText().toString();
                atitle = title.getText().toString();
                acontent = content.getText().toString();

                if(date.isEmpty()) {
                    Toast.makeText(AddActivity.this, "날짜를 선택하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(startime.isEmpty()) {
                    Toast.makeText(AddActivity.this, "시작 시간을 선택하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(endtime.isEmpty()) {
                    Toast.makeText(AddActivity.this, "종료 시간을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(atitle.isEmpty()) {
                    Toast.makeText(AddActivity.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(acontent.isEmpty()) {
                    Toast.makeText(AddActivity.this, "메모를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    String sql = String.format(
                            "INSERT INTO schedule (id, date, start, end, title, content, place)\n" +
                                    "VALUES (NULL, '%s', '%s', '%s', '%s', '%s', '%s')",
                            date, startime, endtime, atitle, acontent, "장소입력");
                    helper.getWritableDatabase().execSQL(sql);
                    Intent intent_result = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent_result);

                } catch (SQLException e) {
                    Log.e("ERROR : ", e.toString());
                }

            }
        });

        Button up = (Button) findViewById(R.id.update);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private TimePickerDialog.OnTimeSetListener starttimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            TextView start = (TextView)findViewById(R.id.start);
            start.setText(hourOfDay + "시 " + minute + "분");
            String msg = String.format("시작시간 : %d시 %d분 선택", hourOfDay, minute);
            Toast.makeText(AddActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener endtimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            TextView end = (TextView)findViewById(R.id.end);
            end.setText(hourOfDay + "시 " + minute + "분");
            String msg = String.format("종료시간 : %d시 %d분 선택", hourOfDay, minute);
            Toast.makeText(AddActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    };
/*    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.find:
                startActivity(new Intent(this, FindPlace.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

}

package com.example.termproject;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
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

public class ModifyActivity extends AppCompatActivity {
    private MyDBHelper helper;
    DatePicker datePicker;

    private int Year;
    private int Month;
    private int day;
    private int hour, minute;

    int id = 0;
    String mdate = "";
    String mtitle = "";
    String mcontent = "";
    private String date = "";
    private String detaildate;
    private String title;
    private String start, end;
    private String startime, endtime, atitle, acontent = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        helper = new MyDBHelper(this);

        datePicker = (DatePicker) findViewById(R.id.datepicker);

        Intent intent = getIntent();
        detaildate = intent.getStringExtra("modifydate");
        title = intent.getStringExtra("modifytitle");

        String sql = "Select * FROM schedule where date = '"+ detaildate +"' AND title = '"+ title +"';";
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            mdate = cursor.getString(1);
            start = cursor.getString(2);
            end = cursor.getString(3);
            mtitle = cursor.getString(4);
            mcontent = cursor.getString(5);
        }

        TextView edate = (TextView) findViewById(R.id.date);
        edate.setText(mdate);

        TextView estart = (TextView) findViewById(R.id.start);
        estart.setText(start);

        TextView eend = (TextView) findViewById(R.id.end);
        eend.setText(end);

        EditText etitle = (EditText) findViewById(R.id.title);
        etitle.setText(mtitle);

        EditText econtent = (EditText) findViewById(R.id.content);
        econtent.setText(mcontent);

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
                new TimePickerDialog(ModifyActivity.this, starttimeSetListener, hour, minute, false).show();
            }
        });

        findViewById(R.id.endbtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new TimePickerDialog(ModifyActivity.this, endtimeSetListener, hour, minute, false).show();
            }
        });

        Button addBtn = (Button) findViewById(R.id.save);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView dateM = (TextView) findViewById(R.id.date);
                EditText titleM = (EditText) findViewById(R.id.title);
                EditText contentM = (EditText) findViewById(R.id.content);
                TextView startM = (TextView) findViewById(R.id.start);
                TextView endM = (TextView) findViewById(R.id.end);


                startime = startM.getText().toString();
                endtime = endM.getText().toString();
                atitle = titleM.getText().toString();
                acontent = contentM.getText().toString();

                if(date.isEmpty()) {
                    Toast.makeText(ModifyActivity.this, "날짜를 수정하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(startime.isEmpty()) {
                    Toast.makeText(ModifyActivity.this, "시작 시간을 수정하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(endtime.isEmpty()) {
                    Toast.makeText(ModifyActivity.this, "종료 시간을 수정하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(atitle.isEmpty()) {
                    Toast.makeText(ModifyActivity.this, "제목을 수정하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(acontent.isEmpty()) {
                    Toast.makeText(ModifyActivity.this, "메모를 수정하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    String sql = String.format (
                            "UPDATE schedule\n"+
                                    "SET date = '%s', start = '%s', end = '%s', title = '%s', content = '%s'\n"+
                                    "WHERE id = '%s'",
                            date, startime, endtime, atitle, acontent, id) ;
                    helper.getWritableDatabase().execSQL(sql);
                    Intent intent_result = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent_result);

                } catch (SQLException e) {
                    Log.e("ERROR : ", e.toString());
                }

            }
        });
    }
    private TimePickerDialog.OnTimeSetListener starttimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            TextView start = (TextView)findViewById(R.id.start);
            start.setText(hourOfDay + "시 " + minute + "분");
            String msg = String.format("시작시간 : %d시 %d분 선택", hourOfDay, minute);
            Toast.makeText(ModifyActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener endtimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            TextView end = (TextView)findViewById(R.id.end);
            end.setText(hourOfDay + "시 " + minute + "분");
            String msg = String.format("종료시간 : %d시 %d분 선택", hourOfDay, minute);
            Toast.makeText(ModifyActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    };
}

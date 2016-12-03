package com.example.termproject;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddActivity extends AppCompatActivity {
    private MyDBHelper helper;
    DatePicker datePicker;

    private int Year;
    private int Month;
    private int day;

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
                        Month = monthOfYear;
                        day = dayOfMonth;
                        //String msg = String.format("%d / %d / %d", year,monthOfYear, dayOfMonth);
                        //Toast.makeText(AddActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        Button addBtn = (Button) findViewById(R.id.save);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText title = (EditText)findViewById(R.id.title1);
/*                Intent intent = getIntent();
                String date = intent.getStringExtra("editdate");
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                String datetime = df.format(Calendar.getInstance().getTime());

                SimpleDateFormat Year = new  SimpleDateFormat("yyyy", Locale.KOREA);
                SimpleDateFormat Month = new  SimpleDateFormat("MM", Locale.KOREA);
                SimpleDateFormat day = new  SimpleDateFormat("dd", Locale.KOREA);

                long now = System.currentTimeMillis();

                Date YearDate = new Date(now);
                String yearStr = Year.format(YearDate);
                Date MonthDate = new Date(now);
                String monthStr = Month.format(MonthDate);
                Date DayDate = new Date(now);
                String dayStr = day.format(DayDate);

                Log.i("Testssssssssssssssss : ", date +"///"+ yearStr+"///"+ monthStr+"///"+ dayStr);*/

                EditText text = (EditText) findViewById(R.id.subject);
                String textStr = text.getText().toString();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                String strDate = String.valueOf(Year) + "-" + String.valueOf(Month) + "-" + String.valueOf(day);
                Date addDate;
                DateFormat tt = DateFormat.getDateInstance(DateFormat.MEDIUM);

                Log.i("sfgaerg:", strDate);

                try {
                    addDate = dateFormat.parse(strDate);
                    String dateStr = dateFormat.format(addDate);
                    Log.i("SSSSSSSSSSSSSS:", addDate.toString());

                    try {
                        String sql = String.format(
                                "INSERT INTO schedule (id, title, date, place)\n" +
                                        "VALUES (NULL, '%s', '%s', '%s')",
                                textStr, dateStr, "장소입력");
                        helper.getWritableDatabase().execSQL(sql);
                        //Log.i("Test : ", date +"///"+ dateTime);
                    } catch (SQLException e) {
                        Log.e("ERROR : ", e.toString());
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        Button up = (Button) findViewById(R.id.update);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try {
            String sql = String.format ("DELETE FROM schedule\n");
            helper.getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e("Sdfsdfsdf","Error deleting recodes");
        }
            }
        });


        TextView test = (TextView) findViewById(R.id.textview);
        String sql = "Select * FROM schedule";
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append(cursor.getString(1)+"\t\t");
            buffer.append(cursor.getString(2)+"\n");
        }
        test.setText(buffer);
    }
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

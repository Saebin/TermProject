package com.example.termproject;

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

public class ModifyActivity extends AppCompatActivity {
    private MyDBHelper helper;
    DatePicker datePicker;

    private String date;
    private int Year;
    private int Month;
    private int day;

    int id = 0;
    String mdate = "";
    String mtitle = "";
    String mcontent = "";
    private String detaildate;
    private String title;

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
            mtitle = cursor.getString(2);
            mcontent = cursor.getString(3);
        }

        TextView edate = (TextView) findViewById(R.id.date);
        edate.setText(mdate);

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

        Button addBtn = (Button) findViewById(R.id.save);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView dateM = (TextView) findViewById(R.id.date);
                EditText titleM = (EditText) findViewById(R.id.title);
                EditText contentM = (EditText) findViewById(R.id.content);

                try {
                    String sql = String.format (
                            "UPDATE schedule\n"+
                                    "SET date = '%s', title = '%s', content = '%s'\n"+
                                    "WHERE id = '%s'",
                            dateM.getText(), titleM.getText(), contentM.getText(), id) ;
                    helper.getWritableDatabase().execSQL(sql);
                    Log.i("skjfdosjfosia:", dateM.getText().toString());
                    Log.i("skjfdosjfosia:", titleM.getText().toString());
                    Log.i("skjfdosjfosia:", contentM.getText().toString());
                    Log.i("skjfdosjfosia:", detaildate);
                    Log.i("skjfdosjfosia:", title);
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
}

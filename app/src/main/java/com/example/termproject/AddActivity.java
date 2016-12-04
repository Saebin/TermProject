package com.example.termproject;

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


public class AddActivity extends AppCompatActivity {
    private MyDBHelper helper;
    DatePicker datePicker;

    private String date;
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
                        Month = monthOfYear+1;
                        day = dayOfMonth;

                        TextView dateText = (TextView) findViewById(R.id.date);

                        date = Year + "" + Month + "" + day;
                        dateText.setText(date);

                    }
                });

        Button addBtn = (Button) findViewById(R.id.save);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText title = (EditText) findViewById(R.id.title);
                EditText content = (EditText) findViewById(R.id.content);

                try {
                    String sql = String.format(
                            "INSERT INTO schedule (id, date, title, content, place)\n" +
                                    "VALUES (NULL, '%s', '%s', '%s', '%s')",
                            date, title.getText(), content.getText(), "장소입력");
                    helper.getWritableDatabase().execSQL(sql);
                    Intent intent_result = new Intent(getApplicationContext(), DetailActivity.class);
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

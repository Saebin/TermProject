package com.example.termproject;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

public class MonthView extends Fragment {
    CalendarView calender;
    String date;
    private MyDBHelper helper;

    int Year = 0;
    int Month = 0;
    int Day = 0;

    public MonthView() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.activity_month, container, false);
        calender = (CalendarView) v.findViewById(R.id.calendarView);
        helper = new MyDBHelper(getActivity());

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Year = year;
                Month = month + 1;
                Day = dayOfMonth;
                date = Year + "" + Month + "" + Day;
                TextView test = (TextView) v.findViewById(R.id.textSC);

                Log.i("sssssssss:", date);

                String sql = "Select * FROM schedule where date = '"+ date +"';";
                Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    buffer.append(cursor.getString(1)+"\t\t");
                    buffer.append(cursor.getString(2)+"\n");
                }
                test.setText(buffer);

            }
        });
/*        TextView test = (TextView) v.findViewById(R.id.textSC);
        String sql = "Select * FROM schedule;";
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append(cursor.getString(1)+"\t\t");
            buffer.append(cursor.getString(2)+"\n");
        }
        test.setText(buffer);*/


/*        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("onClick Test :" , date);
                Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);
                detailIntent.putExtra("detaildate", date);
                startActivityForResult(detailIntent, Integer.parseInt("0"));
            }
        });*/

        return v;
    }



}

package com.example.termproject;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

public class DayView extends Fragment {
    DatePicker datePicker;
    String date;
    private MyDBHelper helper;

    int Year = 0;
    int Month = 0;
    int Day = 0;

    public DayView() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.activity_day, container, false);
        datePicker = (DatePicker) v.findViewById(R.id.datepicker);
        helper = new MyDBHelper(getActivity());

        datePicker.init(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                        Year = year;
                        Month = monthOfYear + 1;
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
        return v;
    }
}

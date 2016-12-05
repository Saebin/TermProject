package com.example.termproject;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class DayView extends Fragment {
    DatePicker datePicker;
    String date;
    private MyDBHelper helper;
    static ContactsAdapter adapter;
    ArrayList<MyItem> data = new ArrayList<MyItem>();

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
        adapter = new ContactsAdapter(getActivity(), R.layout.activity_list, data);

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
                        date = Year + "-" + Month + "-" + Day;

                        TextView list = (TextView) v.findViewById(R.id.list);
                        list.setText(Year+ "년 " + Month + "월 " + Day + "일 일정 리스트");

                        String sql = "Select * FROM schedule where date = '"+ date +"';";
                        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
                        data.clear();
                        while (cursor.moveToNext()) {
                            data.add(new MyItem(1, cursor.getString(1), cursor.getString(4)));
                        }
                        ListView listView = (ListView)v.findViewById(R.id.listView);
                        listView.setAdapter(adapter);
                    }
                });

        Calendar cal = Calendar.getInstance();

        int year = cal.get ( cal.YEAR );
        int month = cal.get ( cal.MONTH ) + 1 ;
        int date1 = cal.get ( cal.DATE ) ;

        TextView list = (TextView) v.findViewById(R.id.list);
        list.setText(year+ "년 " + month + "월 " + date1 + "일 일정 리스트");

        String getDate = year + "-" + month + "-" + date1;
        String sql = "Select * FROM schedule where date = '"+ getDate +"';";
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
        data.clear();
        while (cursor.moveToNext()) {
            data.add(new MyItem(1, cursor.getString(1), cursor.getString(4)));
        }
        ListView listView = (ListView)v.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return v;
    }
}

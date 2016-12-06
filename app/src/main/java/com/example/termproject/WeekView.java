package com.example.termproject;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekView extends Fragment {
    CalendarView calender;
    private MyDBHelper helper;
    static ContactsAdapter adapter;
    ArrayList<MyItem> data = new ArrayList<MyItem>();

    public WeekView() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.activity_month, container, false);
        calender = (CalendarView) v.findViewById(R.id.calendarView);
        helper = new MyDBHelper(getActivity());
        adapter = new ContactsAdapter(getActivity(), R.layout.activity_list, data);

        Calendar ccal = Calendar.getInstance();

        ccal.set(Calendar.DAY_OF_WEEK, 1);

        int year1 = ccal.get(Calendar.YEAR);
        int month1 = ccal.get(Calendar.MONTH);
        int day1 = ccal.get(Calendar.DAY_OF_MONTH);
        String startDay = year1 + "-" + month1 + "-" + day1;

        ccal.set(Calendar.DAY_OF_WEEK, 7);

        int year7 = ccal.get(Calendar.YEAR);
        int month7 = ccal.get(Calendar.MONTH);
        int day7 = ccal.get(Calendar.DAY_OF_MONTH);
        String endDay = year7 + "-" + month7 + "-" + day7;

        Log.i("ASfasdf", startDay + endDay);

        Time myTimeMax = new Time();
        myTimeMax.set(day7, month7, year7);
        //myTimeMax.setToNow();
        calender.setDate(myTimeMax.toMillis(true));
        calender.setMaxDate(calender.getDate());

        Time myTimeMin = new Time();
        myTimeMin.set(day1, month1, year1);
        calender.setDate(myTimeMin.toMillis(true));
        calender.setMinDate(calender.getDate());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, 1);
        int year = cal.get ( cal.YEAR );
        int month = cal.get ( cal.MONTH ) + 1 ;
        int startDate1 = cal.get ( cal.DATE ) ;
        int endDate1 = cal.get ( cal.DATE ) +6;
        String startDate = year + "-" + month + "-" + startDate1;
        String endDate = year + "-" + month + "-" + endDate1;

        TextView list = (TextView) v.findViewById(R.id.list);
        list.setText(year+ "년 " + month + "월 " + startDate1 + "일 ~ " + endDate + "일 일정 리스트");


        Log.i("sdfa", endDate);
        String sql = "Select * FROM schedule where date >= '"+ startDate +"' OR date <= '"+ endDate +"';";
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

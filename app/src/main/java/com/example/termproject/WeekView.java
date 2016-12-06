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
    String date;
    private MyDBHelper helper;
    static ContactsAdapter adapter;
    ArrayList<MyItem> data = new ArrayList<MyItem>();

    int Year = 0;
    int Month = 0;
    int Day = 0;

    public WeekView() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.activity_month, container, false);
        calender = (CalendarView) v.findViewById(R.id.calendarView);
        helper = new MyDBHelper(getActivity());
        adapter = new ContactsAdapter(getActivity(), R.layout.activity_list, data);

/*        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);//not sure this is needed
        long endOfMonth = calendar.getTimeInMillis();
        //may need to reinitialize calendar, not sure
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        long startOfMonth = calendar.getTimeInMillis();
        calender.setMaxDate(endOfMonth);
        calender.setMinDate(startOfMonth);*/

        Calendar ccal = Calendar.getInstance();

        int cyear = ccal.get ( ccal.YEAR );
        int cmonth = ccal.get ( ccal.MONTH ) ;
        int cdate = ccal.get ( ccal.DATE ) ;

        Time myTimeMax = new Time();
        myTimeMax.set(cdate+7, cmonth, cyear);
        //myTimeMax.setToNow();
        calender.setDate(myTimeMax.toMillis(true));
        calender.setMaxDate(calender.getDate());

        Time myTimeMin = new Time();
        myTimeMin.set(cdate, cmonth, cyear);
        calender.setDate(myTimeMin.toMillis(true));
        calender.setMinDate(calender.getDate());



        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

/*                Year = year;
                Month = month + 1;
                Day = dayOfMonth;
                date = Year + "-" + Month;

                TextView list = (TextView) v.findViewById(R.id.list);
                list.setText(Year+ "년 " + Month + "월달 일정 리스트");

                String getDate = Year + "-" + Month;
                String sql = "Select * FROM schedule where date LIKE '"+ getDate +"%';";
                Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
                data.clear();
                while (cursor.moveToNext()) {
                    data.add(new MyItem(1, cursor.getString(1), cursor.getString(4)));
                }
                ListView listView = (ListView)v.findViewById(R.id.listView);
                listView.setAdapter(adapter);*/


            }
        });

        Calendar cal = Calendar.getInstance();

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

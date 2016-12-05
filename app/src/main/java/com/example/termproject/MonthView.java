package com.example.termproject;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthView extends Fragment {
    CalendarView calender;
    String date;
    private MyDBHelper helper;
    static ContactsAdapter adapter;
    ArrayList<MyItem> data = new ArrayList<MyItem>();

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
        adapter = new ContactsAdapter(getActivity(), R.layout.activity_list, data);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Year = year;
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
                listView.setAdapter(adapter);


            }
        });

        Calendar cal = Calendar.getInstance();

        int year = cal.get ( cal.YEAR );
        int month = cal.get ( cal.MONTH ) + 1 ;
        int date = cal.get ( cal.DATE ) ;

        TextView list = (TextView) v.findViewById(R.id.list);
        list.setText(year+ "년 " + month + "월달 일정 리스트");

        String getDate = year + "-" + month;
        String sql = "Select * FROM schedule where date LIKE '"+ getDate +"%';";
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

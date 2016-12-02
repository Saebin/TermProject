package com.example.termproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class WeekView extends AppCompatActivity {
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addScedule:
                startActivity(new Intent(this, AddActivity.class));
                return true;
            case R.id.monthView:
                startActivity(new Intent(this, MonthView.class));
                return true;
            case R.id.weekView:
                startActivity(new Intent(this, WeekView.class));
                return true;
            case R.id.dayView:
                startActivity(new Intent(this, DayView.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

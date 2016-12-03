package com.example.termproject;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private MyDBHelper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        helper = new MyDBHelper(this);

/*        Intent intent = getIntent();
        intent.getStringExtra("date");*/

        TextView test = (TextView) findViewById(R.id.detailSC);
        String sql = "Select * FROM schedule";
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append(cursor.getString(1)+"\t\t");
            buffer.append(cursor.getString(2)+"\n");
        }
        test.setText(buffer);
    }
}

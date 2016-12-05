package com.example.termproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private MyDBHelper helper;
    String mdate = "";
    String mtitle = "";
    String mcontent = "";

    private String detaildate;
    private String title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        helper = new MyDBHelper(this);


        Intent intent = getIntent();
        detaildate = intent.getStringExtra("detaildate");
        title = intent.getStringExtra("title");

        String sql = "Select * FROM schedule where date = '"+ detaildate +"' AND title = '"+ title +"';";
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext()) {
            mdate = cursor.getString(1);
            mtitle = cursor.getString(2);
            mcontent = cursor.getString(3);
        }

        TextView dateText = (TextView) findViewById(R.id.date);
        dateText.setText(mdate);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText(mtitle);

        TextView contentText = (TextView) findViewById(R.id.content);
        contentText.setText(mcontent);

        Button modifyBtn = (Button) findViewById(R.id.modify);
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent modifyIntent = new Intent(getApplicationContext(), ModifyActivity.class);
                modifyIntent.putExtra("modifydate", detaildate);
                modifyIntent.putExtra("modifytitle", title);
                startActivityForResult(modifyIntent, 0);
            }
        });

        Button deleteBtn = (Button) findViewById(R.id.delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String sql = String.format (
                            "DELETE FROM schedule\n"+
                                    "WHERE date = '%s' AND title = '%s'",
                            detaildate, title);
                    helper.getWritableDatabase().execSQL(sql);
                    Intent intent_result = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent_result);
                } catch (SQLException e) {
                    Log.e("ss","Error deleting recodes");
                }
            }
        });

    }
}

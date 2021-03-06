package com.example.termproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    private MyDBHelper helper;
    String mdate = "";
    String mtitle = "";
    String mcontent = "";
    String start, end;

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
            start = cursor.getString(2);
            end = cursor.getString(3);
            mtitle = cursor.getString(4);
            mcontent = cursor.getString(5);
        }

        TextView dateText = (TextView) findViewById(R.id.date);
        dateText.setText(mdate);

        TextView startText = (TextView) findViewById(R.id.start);
        startText.setText(start);

        TextView endText = (TextView) findViewById(R.id.end);
        endText.setText(end);

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
                final EditText etEdit = new EditText(DetailActivity.this);
                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
                dialog.setTitle("삭제하시겠습니까?");

                dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String sql = String.format (
                                    "DELETE FROM schedule\n"+
                                            "WHERE date = '%s' AND title = '%s'",
                                    detaildate, title);
                            helper.getWritableDatabase().execSQL(sql);
                            Intent intent_result = new Intent(getApplicationContext(), MainActivity.class);
                            Toast.makeText(DetailActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            startActivity(intent_result);
                        } catch (SQLException e) {
                            Log.e("ss","Error deleting recodes");
                        }
                    }
                });

                dialog.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailActivity.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                dialog.show();




            }
        });

    }
}

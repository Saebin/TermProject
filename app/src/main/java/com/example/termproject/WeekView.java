package com.example.termproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeekView extends Fragment {
    public WeekView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_week, container, false);

        TextView weekView = (TextView) v.findViewById(R.id.weekView);
        weekView.setText("확인");
        return v;
    }
}

package com.caucse.seatchecker.seatchecker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentAlarm extends Fragment {

    private View view;
    private ArrayList<AlarmListElement> array;
    private String androidId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.alarm_fragment,container,false);

        androidId = "" + android.provider.Settings.Secure.getString(view.getContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        array = new ArrayList<>();

        DBController dbController = new DBController(view);
        dbController.getAlarmList(androidId);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DBController dbController = new DBController(view);
        dbController.getAlarmList(androidId);
    }
}

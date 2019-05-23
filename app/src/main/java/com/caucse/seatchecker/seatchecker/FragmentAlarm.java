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
    private ArrayList<AlarmRealm> array;
    private String androidId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.alarm_fragment,container,false);

        androidId = "" + android.provider.Settings.Secure.getString(view.getContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        array = new ArrayList<>();


        Viewer alarmView = new Viewer(view);
        alarmView.AlarmListViewer(array);

        DBController dbController = new DBController(view);
        dbController.getAlarmList(alarmView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}

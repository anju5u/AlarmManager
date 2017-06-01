package com.example.anju.alarmmanager;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class AlarmFragment extends Fragment {

    EditText txt_time,txt_date;
    Button btn_add_evnt;
    EditText txt_evnt_name;
    String get_evnt_name,get_date,get_time;
    private Calendar target_cal =Calendar.getInstance();
    // declare  the variables to show the date and time whenTime and Date Picker Dialog first appears
    private int mYear, mMonth, mDay, mHour, mMinute;
    // variables to save user selected date and time
    public  int yearSelected,monthSelected,daySelected,hourSelected,minuteSelected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         View rootView=inflater.inflate(R.layout.fragment_alarm,container,false);
        txt_evnt_name= (EditText)rootView.findViewById(R.id.edtxtEventName);
        txt_date=(EditText) rootView.findViewById(R.id.edtxtDate);
        txt_time=(EditText) rootView.findViewById(R.id.edtxtTime);
        btn_add_evnt=(Button)rootView.findViewById(R.id.btnAddEvent);


            txt_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            int display_month;
                            display_month = monthOfYear + 1;
                            txt_date.setText(dayOfMonth + "-" + (display_month) + "-" + year);
                            target_cal.set(Calendar.YEAR, year);

                            target_cal.set(Calendar.MONTH, monthOfYear);
                            target_cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                        }
                    }, mYear, mMonth, mDay);

                    datePickerDialog.show();
                }
            });

            txt_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);


                    TimePickerDialog timePickerDialog= new TimePickerDialog(getActivity(),new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            txt_time.setText(hourOfDay+" : "+minute);
                            target_cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            target_cal.set(Calendar.MINUTE,minute);
                        }
                    },mHour,mMinute,false);
                    timePickerDialog.show();


                }
            });
            btn_add_evnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            getActivity(), 1253, intent,PendingIntent.FLAG_UPDATE_CURRENT );

                    AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);

                    alarmManager.set(AlarmManager.RTC_WAKEUP,target_cal.getTimeInMillis(),pendingIntent);
                    Notification notification=new Notification.Builder(getActivity())
                            .setTicker("aaa")
                            .setContentTitle("bbb")
                            .setContentText("wedding")
                            .setContentIntent(pendingIntent).getNotification();
                    notification.flags=Notification.FLAG_AUTO_CANCEL;
                    NotificationManager notificationManager=(NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0,notification);
                }
            });


        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Event");
    }


}

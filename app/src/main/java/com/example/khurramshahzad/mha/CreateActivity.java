package com.example.khurramshahzad.mha;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CreateActivity extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private EditText editTextSid;
    private EditText editTextDrName;
    private EditText editTextMedicineName;

    private DatabaseHandler dbHandler;
    private ImageView imageViewSave;
    private CheckBox checkBoxMorning;
    private CheckBox checkBoxAfternoon;
    private CheckBox checkBoxNight;
    private EditText editTextQuantityMorning;
    private EditText editTextQuantityAfternoon;
    private EditText editTextQuantityNight;
    private EditText editTextTimeMorning;
    private EditText editTextTimeAfternoon;
    private EditText editTextTimeNight;
    private Spinner spinnerDays;
    private TextView textViewDaysMsg;
    private DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder().appendPattern("HH:mm").toFormatter();
    private EditText editTextComments;

    private Medication medication;
    private ImageView imageViewCancel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_layout);

        medication = getIntent().getParcelableExtra(Medication.class.getSimpleName());

        dbHandler = new DatabaseHandler(this);

        editTextSid = (EditText) findViewById(R.id.edit_text_sid);
        editTextDrName = (EditText) findViewById(R.id.edit_text_dr_name);
        editTextMedicineName = (EditText) findViewById(R.id.edit_text_medicine_name);

        spinnerDays = (Spinner)findViewById(R.id.spinner_days);
        textViewDaysMsg = (TextView)findViewById(R.id.textViewDaysError);
        checkBoxMorning = (CheckBox)findViewById(R.id.check_box_morning);
        checkBoxMorning.setOnCheckedChangeListener(this);
        checkBoxAfternoon = (CheckBox)findViewById(R.id.check_box_afternoon);
        checkBoxAfternoon.setOnCheckedChangeListener(this);
        checkBoxNight = (CheckBox)findViewById(R.id.check_box_night);
        checkBoxNight.setOnCheckedChangeListener(this);

        editTextQuantityMorning = (EditText)findViewById(R.id.edit_text_morning_quantity);
        editTextQuantityAfternoon = (EditText)findViewById(R.id.edit_text_quantity_afternoon);
        editTextQuantityNight =(EditText)findViewById(R.id.edit_text_night_quantity);

        editTextTimeMorning = (EditText)findViewById(R.id.edit_text_morning_time);
        editTextTimeAfternoon = (EditText)findViewById(R.id.edit_text_afternoon_time);
        editTextTimeNight = (EditText)findViewById(R.id.edit_text_night_time);
        editTextComments = (EditText)findViewById(R.id.edit_text_comments);

        spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textViewDaysMsg.setError(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editTextTimeMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker = new TimePickerDialog(CreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        LocalTime time = new LocalTime(selectedHour,selectedMinute);
                        String timeString = time.toString(timeFormatter);
                        editTextTimeMorning.setText(timeString);
                    }
                }, hour, minute,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });
        editTextTimeAfternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker = new TimePickerDialog(CreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        LocalTime time = new LocalTime(selectedHour,selectedMinute);
                        String timeString = time.toString(timeFormatter);
                        editTextTimeAfternoon.setText(timeString);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        editTextTimeNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker = new TimePickerDialog(CreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        LocalTime time = new LocalTime(selectedHour,selectedMinute);
                        String timeString = time.toString(timeFormatter);
                        editTextTimeNight.setText(timeString);
                    }
                }, hour, minute,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });

        editTextSid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextSid.setError(null);
                }
            }
        });

        editTextDrName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextDrName.setError(null);
                }
            }
        });

        editTextMedicineName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextMedicineName.setError(null);
                }
            }
        });


        imageViewSave = (ImageView) findViewById(R.id.button_save);
        imageViewCancel = (ImageView) findViewById(R.id.button_cancel);
        imageViewSave.setOnClickListener(this);
        imageViewCancel.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(medication != null) {
            editTextSid.setText(medication.getSid());
            editTextDrName.setText(medication.getRecommendedBy());
            editTextMedicineName.setText(medication.getMedicineName());

            int times = 0;
            if(medication.isMorning())times++;
            if(medication.isAfternoon())times++;
            if(medication.isNight())times++;

            checkBoxMorning.setChecked(medication.isMorning());
            checkBoxAfternoon.setChecked(medication.isAfternoon());
            checkBoxNight.setChecked(medication.isNight());

            editTextQuantityMorning.setText(medication.getMorningQuantity().toString());
            editTextQuantityAfternoon.setText(medication.getAfternoonQuantity().toString());
            editTextQuantityNight.setText(medication.getNightQuantity().toString());

            editTextTimeMorning.setText(LocalTime.fromMillisOfDay(medication.getMorningTime()).toString(timeFormatter));
            editTextTimeAfternoon.setText(LocalTime.fromMillisOfDay(medication.getAfternoonTime()).toString(timeFormatter));
            editTextTimeNight.setText(LocalTime.fromMillisOfDay(medication.getNightTime()).toString(timeFormatter));
            editTextComments.setText(medication.getComment());
        }
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_cancel: {
                finish();
                break;
            }

            case R.id.button_save: {

                if(medication == null){
                    medication = new Medication();
                }

                String sId = editTextSid.getText().toString();
                if (TextUtils.isEmpty(sId)) {
                    editTextSid.requestFocus();
                    editTextSid.setError("SID is required");
                    return;
                }

                medication.setSid(sId);

                String drName = editTextDrName.getText().toString();
                if (TextUtils.isEmpty(drName)) {
                    editTextDrName.requestFocus();
                    editTextDrName.setError("Doctor name is required");
                    return;
                }

                medication.setRecommendedBy(drName);

                String medicineName = editTextMedicineName.getText().toString();
                if (TextUtils.isEmpty(medicineName)) {
                    editTextMedicineName.requestFocus();
                    editTextMedicineName.setError("Medication Name required");
                    return;
                }
                medication.setMedicineName(medicineName);

                if(spinnerDays.getSelectedItemPosition() <1){

                    textViewDaysMsg.setVisibility(View.VISIBLE);
                    textViewDaysMsg.setError("Select days");
                }
                medication.setMorning(checkBoxMorning.isChecked());
                medication.setAfternoon(checkBoxAfternoon.isChecked());
                medication.setNight(checkBoxNight.isChecked());

                String[] days = getResources().getStringArray(R.array.Days);
                int counter = 0;
                for(int i=1;i<days.length;i++){
                    String day = days[i];
                    if(day.equals(spinnerDays.getSelectedItem().toString())){
                       if(medication.isMorning()) medication.setMorningCounter(i);
                        if(medication.isAfternoon()) medication.setAfternoonCounter(i);
                        if(medication.isNight()) medication.setNightCounter(i);
                    }
                }
                if(medication.isMorning()) {
                    medication.setMorningQuantity(Double.valueOf(editTextQuantityMorning.getText().toString()));
                    medication.setMorningTime((long)LocalTime.parse(editTextTimeMorning.getText().toString(),timeFormatter).getMillisOfDay());
                }
                if(medication.isAfternoon()) {
                    medication.setAfternoonQuantity(Double.valueOf(editTextQuantityAfternoon.getText().toString()));
                    medication.setAfternoonTime((long)LocalTime.parse(editTextTimeAfternoon.getText().toString(),timeFormatter).getMillisOfDay());
                }
                if(medication.isNight()) {
                    medication.setNightQuantity(Double.valueOf(editTextQuantityNight.getText().toString()));
                    medication.setNightTime((long)LocalTime.parse(editTextTimeNight.getText().toString(),timeFormatter).getMillisOfDay());
                }


                medication.setComment(editTextComments.getText().toString());
                if(medication.getId() != null){
                    if(dbHandler.updateMedication(medication)){
                        Snackbar.make(getCurrentFocus(), "Updated successfully", Snackbar.LENGTH_LONG)
                                .setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                }).show();
                    }
                }
                else {
                    if(dbHandler.addMedicine(medication)){
                        Snackbar.make(getCurrentFocus(), "Added successfully", Snackbar.LENGTH_LONG)
                                .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }).show();
                    }
                }
                finish();
                break;
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            if(buttonView.getId() == R.id.check_box_morning){
                editTextQuantityMorning.setEnabled(true);
                editTextTimeMorning.setEnabled(true);
            }
            if(buttonView.getId() == R.id.check_box_afternoon){
                editTextQuantityAfternoon.setEnabled(true);
                editTextTimeAfternoon.setEnabled(true);
            }
            if(buttonView.getId() == R.id.check_box_night){
                editTextQuantityNight.setEnabled(true);
                editTextTimeNight.setEnabled(true);
            }
        }
        else {
            if(buttonView.getId() == R.id.check_box_morning){
                editTextQuantityMorning.setEnabled(false);
                editTextTimeMorning.setEnabled(false);
            }
            if(buttonView.getId() == R.id.check_box_afternoon){
                editTextQuantityAfternoon.setEnabled(false);
                editTextTimeAfternoon.setEnabled(false);
            }
            if(buttonView.getId() == R.id.check_box_night){
                editTextQuantityNight.setEnabled(false);
                editTextTimeNight.setEnabled(false);
            }
        }
    }
}

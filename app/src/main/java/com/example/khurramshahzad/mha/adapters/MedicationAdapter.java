package com.example.khurramshahzad.mha.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.khurramshahzad.mha.Medication;
import com.example.khurramshahzad.mha.R;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.List;


/**
 * Created by NoorN on 5/26/2017.
 */

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MyViewHolder> {

    private List<Medication> medications;

    private DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder().appendPattern("HH:mm").toFormatter();
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView editTextMorningCounter;
        public TextView editTextAfternoonCounter;
        public TextView editTextNightCounter;

        public TextView textViewMedicineName, textViewDrName, textViewComment;


        public TextView editTextQuantityMorning;
        public TextView editTextQuantityAfternoon;
        public TextView editTextQuantityNight;
        public TextView editTextTimeMorning;
        public TextView editTextTimeAfternoon;
        public TextView editTextTimeNight;

        public  LinearLayout layoutMorning;
        public  LinearLayout layoutAfternoon;
        public  LinearLayout layoutNight;

        public MyViewHolder(View view) {
            super(view);
            textViewMedicineName = (TextView) view.findViewById(R.id.text_view_medicine_name);
            textViewDrName = (TextView) view.findViewById(R.id.text_view_dr_name);


            layoutMorning = (LinearLayout)view.findViewById(R.id.layout_morning);
            layoutAfternoon = (LinearLayout)view.findViewById(R.id.layout_afternoon);
            layoutNight = (LinearLayout)view.findViewById(R.id.layout_night);

            editTextQuantityMorning = (TextView)view.findViewById(R.id.edit_text_morning_quantity);
            editTextQuantityAfternoon = (TextView)view.findViewById(R.id.edit_text_quantity_afternoon);
            editTextQuantityNight =(TextView)view.findViewById(R.id.edit_text_night_quantity);

            editTextTimeMorning = (TextView)view.findViewById(R.id.edit_text_morning_time);
            editTextTimeAfternoon = (TextView)view.findViewById(R.id.edit_text_afternoon_time);
            editTextTimeNight = (TextView)view.findViewById(R.id.edit_text_night_time);


            editTextMorningCounter = (TextView)view.findViewById(R.id.edit_text_morning_counter);
            editTextAfternoonCounter = (TextView)view.findViewById(R.id.edit_text_afternoon_counter);
            editTextNightCounter = (TextView)view.findViewById(R.id.edit_text_night_counter);

            textViewComment = (TextView) view.findViewById(R.id.text_view_comments);
        }
    }

    public MedicationAdapter(List<Medication> topics) {
        this.medications = topics;
    }

    @Override
    public MedicationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medication_row, parent, false);

        return new MedicationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Medication item = medications.get(position);
        holder.textViewMedicineName.setText(item.getMedicineName());
        holder.textViewDrName.setText(item.getRecommendedBy());
        holder.layoutMorning.setVisibility(item.isMorning()?View.VISIBLE:View.GONE);
        holder.layoutAfternoon.setVisibility(item.isAfternoon()?View.VISIBLE:View.GONE);
        holder.layoutNight.setVisibility(item.isNight()?View.VISIBLE:View.GONE);

        holder.editTextQuantityMorning.setText(item.getMorningQuantity().toString());
        holder.editTextQuantityAfternoon.setText(item.getAfternoonQuantity().toString());
        holder.editTextQuantityNight.setText(item.getNightQuantity().toString());

        holder.editTextTimeMorning.setText(LocalTime.fromMillisOfDay(item.getMorningTime()).toString(timeFormatter));
        holder.editTextTimeAfternoon.setText(LocalTime.fromMillisOfDay(item.getAfternoonTime()).toString(timeFormatter));
        holder.editTextTimeNight.setText(LocalTime.fromMillisOfDay(item.getNightTime()).toString(timeFormatter));

        holder.editTextMorningCounter.setText(item.getMorningCounter().toString());
        holder.editTextAfternoonCounter.setText(item.getAfternoonCounter().toString());
        holder.editTextNightCounter.setText(item.getNightCounter().toString());
        holder.textViewComment.setText(item.getComment());
    }

    public void setItems(List<Medication> topics) {
        this.medications = topics;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }
}
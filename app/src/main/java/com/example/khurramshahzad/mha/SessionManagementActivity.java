package com.example.khurramshahzad.mha;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.khurramshahzad.mha.adapters.MedicationAdapter;

import java.util.ArrayList;
import java.util.List;

public class SessionManagementActivity extends Activity {

    private DatabaseHandler dbHandler;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private List<Medication> medications;
    private MedicationAdapter mAdapter;

    public void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.session_management_layout);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout_restaurant_list);

        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_medications);
        medications = new ArrayList<>();
        mAdapter = new MedicationAdapter(medications);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(
                recyclerView.getContext(),
                DividerItemDecoration.VERTICAL
        );
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider));
        recyclerView.addItemDecoration(divider);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SessionManagementActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final Medication medication = medications.get(position);

                final String[] options = new String[]{"Update", "Delete", "Cancel"};
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SessionManagementActivity.this);
                dialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (options[which].equals("Update")) {

                            Intent intent = new Intent(SessionManagementActivity.this,CreateActivity.class);
                            intent.putExtra(Medication.class.getSimpleName(),medication);
                            startActivity(intent);
                        } else if (options[which].equals("Delete")) {
                            if (dbHandler.deleteMedication(medication)) {
                                Snackbar.make(getCurrentFocus(), "Deleted successfully", Snackbar.LENGTH_LONG).show();
                                loadData();
                            }

                        } else {
                            dialog.dismiss();
                        }

                    }
                });
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        dbHandler = new DatabaseHandler(this);

        medications = dbHandler.getMedications();
        mAdapter.setItems(medications);

        refreshLayout.setRefreshing(false);
    }
}



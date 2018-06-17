package com.myandroid.caseyjohnson.sqlite3db;

import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowActivity extends BaseActivity {

    TextView txtShowName, txtShowDescription, txtShowPrice, txtShowRating;
    LiveData<Record> shownRecord;
    long recordID;
    Observer<Record> recordObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        txtShowName = findViewById(R.id.txtShowName);
        txtShowDescription = findViewById(R.id.txtShowDescription);
        txtShowPrice = findViewById(R.id.txtShowPrice);
        txtShowRating = findViewById(R.id.txtShowRating);

        recordObserver = new Observer<Record>() {
            @Override
            public void onChanged(@Nullable Record record) {
                if (record != null) {
                    toastIt(String.valueOf(record));
                    txtShowName.setText(record.getName());
                    txtShowDescription.setText(record.getDescription());
                    txtShowPrice.setText("$" + String.valueOf(record.getPrice()));
                    txtShowRating.setText(String.valueOf(record.getRating()));
                }
            }
        };

        recordID = getIntent().getLongExtra("RecordID", 0);
        shownRecord = recordDatabase.recordDao().findByRecordNum(recordID);
        shownRecord.observe(this, recordObserver);
    }

    public void deleteOnClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Perform something when they click YES
                        shownRecord.removeObserver(recordObserver);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Record record = shownRecord.getValue();
                                recordDatabase.recordDao().deleteRecord(record);
                            }
                        }).start();
                        toastIt("Record Deleted");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Perform something when they say NO
                dialog.cancel();
                toastIt("You pressed No");
            }
        }).create()
                .show();
    }

    public void editOnClick(View v) {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("RecordID", recordID);
        startActivity(intent);
    }
}

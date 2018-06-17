package com.myandroid.caseyjohnson.sqlite3db;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends BaseActivity {

    EditText edtAddName, edtAddDescription, edtAddPrice, edtAddRating;
    int modRating;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtAddName = findViewById(R.id.edtAddName);
        edtAddDescription = findViewById(R.id.edtAddDescription);
        edtAddPrice = findViewById(R.id.edtAddPrice);
        edtAddRating = findViewById(R.id.edtAddRating);
    }

    public void addOnClick(View v) {
        final String recordName = edtAddName.getText().toString();
        final String recordDescription = edtAddDescription.getText().toString();
        final float recordPrice = Float.parseFloat(edtAddPrice.getText().toString());
        final int recordRating = Integer.parseInt(edtAddRating.getText().toString());
        if(recordRating < 1){
            modRating = 1;
        } else if(recordRating > 5){
            modRating = 5;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Record record = new Record();
                record.setName(recordName);
                record.setDescription(recordDescription);
                record.setPrice(recordPrice);
                record.setRating(modRating);
                record.setDateCreated(new Date());
                record.setDateModified(new Date());

                recordDatabase.recordDao().addRecord(record);
            }
        }).start();
        toastIt("Record Added");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}

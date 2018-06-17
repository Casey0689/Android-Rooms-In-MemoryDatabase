package com.myandroid.caseyjohnson.sqlite3db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class EditActivity extends BaseActivity {

    LiveData<Record> record;
    EditText edtEditName, edtEditDescription, edtEditPrice, edtEditRating;
    long recordID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edtEditName = findViewById(R.id.edtEditName);
        edtEditDescription = findViewById(R.id.edtEditDescription);
        edtEditPrice = findViewById(R.id.edtEditPrice);
        edtEditRating = findViewById(R.id.edtEditRating);

        recordID = getIntent().getLongExtra("RecordID", 0);

        record = recordDatabase.recordDao().findByRecordNum(recordID);
        record.observe(this, new Observer<Record>() {
            @Override
            public void onChanged(@Nullable Record record) {
                edtEditName.setText(record.getName());
                edtEditDescription.setText(record.getDescription());
                edtEditPrice.setText(String.valueOf(record.getPrice()));
                edtEditRating.setText(String.valueOf(record.getRating()));
            }
        });
    }

    public void onEditClick(View v) {
        record = recordDatabase.recordDao().findByRecordNum(recordID);
        record.observe(this, new Observer<Record>() {
            @Override
            public void onChanged(@Nullable final Record record) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String recordName = edtEditName.getText().toString();
                        final String recordDescription = edtEditDescription.getText().toString();
                        final String recordPrice = edtEditPrice.getText().toString();
                        final String recordRating = edtEditRating.getText().toString();
                        record.setName(recordName);
                        record.setDescription(recordDescription);
                        record.setPrice(Float.parseFloat(recordPrice));
                        record.setRating(Integer.parseInt(recordRating));
                        record.setDateModified(new Date());
                        recordDatabase.recordDao().updateRecord(record);
                    }
                }).start();
            }
        });
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}

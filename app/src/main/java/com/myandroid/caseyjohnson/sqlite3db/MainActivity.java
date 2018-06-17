package com.myandroid.caseyjohnson.sqlite3db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private ListView listViewRecords;
    LiveData<List<Record>> recordList;
    List<String> recordNames = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewRecords = findViewById(R.id.listViewRecords);

        recordList = recordDatabase.recordDao().getAll();
        recordList.observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable final List<Record> records) {
                for (Record r : records) {
                    recordNames.add(r.getName());
                }
                listViewRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                        intent.putExtra("RecordID", records.get(position).getRecordID());
                        startActivity(intent);
                    }
                });
                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, recordNames);
                listViewRecords.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                listViewRecords.invalidateViews();
                listViewRecords.refreshDrawableState();
            }
        });
    }

    public void addNewOnClick(View v) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
}

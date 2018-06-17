package com.myandroid.caseyjohnson.sqlite3db;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    public AppDatabase recordDatabase;

    //----------------------------- ON CREATE --------------------------------------
    public void onCreate(@Nullable Bundle savedInstanceState) { //@Nullable PersistableBundle persistentState
        super.onCreate(savedInstanceState); // persistentState ;
        setContentView(R.layout.activity_base);

        if (recordDatabase == null) {
            recordDatabase = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "records.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    //------------------------------- OPTIONS MENU -------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate Menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuViewAll:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuShow:
                intent = new Intent(this, ShowActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuEdit:
                intent = new Intent(this, EditActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuAdd:
                intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //----------------------------- TOAST IT METHOD -----------------------------------
    public void toastIt(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

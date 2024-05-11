package com.example.firebasertdatabase;

import static com.example.firebasertdatabase.FBref.refStudents;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {

    /**
     * @param menu The options menu in which you place your items.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * @param item The menu item that was selected.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.enter) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.view) {
            Intent intent = new Intent(this, DataActivity.class);
            startActivity(intent);
        }
        if (id == R.id.sort) {
            Intent intent = new Intent(this, DataSortActivity.class);
            startActivity(intent);
        }
        if (id == R.id.credits) {
            Intent intent = new Intent(this, Credits.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    ArrayList<String> stuList = new ArrayList<>();
    ArrayList<Student> stuValues = new ArrayList<>();

    ValueEventListener stuListener;

    CustomAdapter adp;

    ListView list;

    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        list = findViewById(R.id.list2);
        ValueEventListener stuListener = new ValueEventListener() {
            /**
             * @param dS The current data at the location
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                stuList.clear();
                stuValues.clear();
                for (DataSnapshot data : dS.getChildren()) {
                    String str1 = data.getKey();
                    Student stuTemp = data.getValue(Student.class);
                    stuValues.add(stuTemp);
                    assert stuTemp != null;
                    String str2 = stuTemp.getStuName();
                    String str3 = String.valueOf(stuTemp.getGrade());
                    String str4 = String.valueOf(stuTemp.getStuClass());
                    String str5 = String.valueOf(stuTemp.isCanVaccinate());
                    String str6 = "No vaccination data";
                    if (str5.equals(String.valueOf(true))) {
                        str6 = stuTemp.getVaccination().toString();
                    }

                    stuList.add("Name: " + str2 + "\nID: " + str1 + "\nGrade: " + str3 + "\nClass: " + str4 + "\nCan Vaccinate: " + str5 + "\n" + str6);
                }
                adp = new CustomAdapter(DataActivity.this, stuList);
                list.setAdapter(adp);
            }

            /**
             * @param databaseError A description of the error that occurred
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        refStudents.addValueEventListener(stuListener);
    }

    @Override
    protected void onPause() {
        if(stuListener != null)
            refStudents.removeEventListener(stuListener);
        super.onPause();
    }
}

